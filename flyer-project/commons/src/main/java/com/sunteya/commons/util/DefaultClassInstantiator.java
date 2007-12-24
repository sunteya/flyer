package com.sunteya.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.SystemUtils;

/**
 * Default class instantiator that is pretty limited. It just hope that the
 * mocked class has a public empty constructor.
 */
public class DefaultClassInstantiator {

    public Object newInstance(Class<?> c) throws InstantiationException {

        if (Serializable.class.isAssignableFrom(c)) {
            try {
                return readObject(getSerializedBytes(c));
            } catch (IOException e) {
                throw new RuntimeException("Failed to instantiate "
                        + c.getName() + "'s mock: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to instantiate "
                        + c.getName() + "'s mock: " + e.getMessage());
            }
        }

        Constructor<?> constructor = getConstructorToUse(c);
        Object[] params = getArgsForTypes(constructor.getParameterTypes());
        try {
            return constructor.newInstance(params);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to instantiate " + c.getName()
                    + "'s mock: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + c.getName()
                    + "'s mock: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate " + c.getName()
                    + "'s mock: " + e.getMessage());
        }
    }

    /**
     * Return the constructor considered the best to use with this class.
     * Algorithm is: No args constructor and then first constructor defined in
     * the class
     *
     * @param clazz
     *            Class in which constructor is searched
     * @return Constructor to use
     */
    private Constructor<?> getConstructorToUse(Class<?> clazz) {
        // First try to use the empty constructor
        try {
            return clazz.getConstructor(new Class[0]);
        } catch (NoSuchMethodException e) {
            // If it fails just use the first one
            if (clazz.getConstructors().length == 0) {
                throw new IllegalArgumentException("No visible constructors in class " + clazz.getName());
            }
            return clazz.getConstructors()[0];
        }
    }

    /**
     * Get some default instances of provided classes
     *
     * @param methodTypes
     *            Classes to instantiate
     * @return Instances of methodTypes in order
     */
    private Object[] getArgsForTypes(Class<?>[] methodTypes)
            throws InstantiationException {
        Object[] methodArgs = new Object[methodTypes.length];

        for (int i = 0; i < methodTypes.length; i++) {
            Object mock = newInstance(methodTypes[i]) ;
            methodArgs[i] = mock;
        }
        return methodArgs;
    }

    private static byte[] getSerializedBytes(Class<?> clazz) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(baos);
        data.writeShort(ObjectStreamConstants.STREAM_MAGIC);
        data.writeShort(ObjectStreamConstants.STREAM_VERSION);
        data.writeByte(ObjectStreamConstants.TC_OBJECT);
        data.writeByte(ObjectStreamConstants.TC_CLASSDESC);
        data.writeUTF(clazz.getName());

        Long suid = getSerializableUID(clazz);

        data.writeLong(suid.longValue());

        data.writeByte(2); // classDescFlags (2 = Serializable)
        data.writeShort(0); // field count
        data.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);
        data.writeByte(ObjectStreamConstants.TC_NULL);
        return baos.toByteArray();
    }

    private static Long getSerializableUID(Class<?> clazz) {

        try {
            Field f = clazz.getDeclaredField("serialVersionUID");
            final int mask = Modifier.STATIC | Modifier.FINAL;
            if ((f.getModifiers() & mask) == mask) {
                f.setAccessible(true);
                return new Long(f.getLong(null));
            }
        } catch (NoSuchFieldException e) {
            // It's not there, compute it then
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Should have been able to get serialVersionUID since it's there");
        }
        return callLongMethod(clazz, SystemUtils.IS_JAVA_1_3 ? "computeSerialVersionUID"
                : "computeDefaultSUID");
    }

    private static Long callLongMethod(Class<?> clazz, String methodName) {

        Method method;
        try {
            method = ObjectStreamClass.class.getDeclaredMethod(methodName,
                    new Class[] { Class.class });
        } catch (NoSuchMethodException e) {
            throw new InternalError("ObjectStreamClass." + methodName
                    + " seems to have vanished");
        }
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Long suid;
        try {
            suid = (Long) method.invoke(null, new Object[] { clazz });
        } catch (IllegalAccessException e) {
            throw new InternalError("ObjectStreamClass." + methodName
                    + " should have been accessible");
        } catch (InvocationTargetException e) {
            throw new InternalError("ObjectStreamClass." + methodName
                    + " failled to be called: " + e.getMessage());
        }
        method.setAccessible(accessible);
        return suid;
    }

    private static Object readObject(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return in.readObject();
    }
}
