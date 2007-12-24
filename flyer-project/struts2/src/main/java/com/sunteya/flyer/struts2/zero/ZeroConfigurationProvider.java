/**
 * Created on 2007-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ResolverUtil;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import com.sunteya.flyer.struts2.zero.annotation.Ignore;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class ZeroConfigurationProvider implements ConfigurationProvider {

	private static final Logger logger = LoggerFactory.getLogger(ZeroConfigurationProvider.class);
//	private static final String DELIMITERS = ",; \t\n";

	private ObjectFactory objectFactory;
	private String actionPackage;
	private Configuration configuration;

	private ActionRoutingBuilder actionRoutingBuilder;
	private PackageNameBuilder packageNameBuilder;
	private ParentPackageNameBuilder parentPackageNameBuilder;
	private ResultBuilder resultBuilder;

	public void loadPackages() throws ConfigurationException {
		Set<Class<? extends Action>> actions = resolveActions(getActionPackage());
		for (Class<? extends Action> actionClass : actions) {
			notifyObjectFactory(actionClass);
			List<ActionRouting> routings = actionRoutingBuilder.build(actionClass, getActionPackage());
			for (ActionRouting routing : routings) {
				loadAction(routing, actionClass);
			}
		}
	}

	protected void loadAction(ActionRouting routing, Class<? extends Action> actionClass) {
		PackageConfig packageConfig = getPackageConfig(routing, actionClass);

		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setPackageName(packageConfig.getName());
		actionConfig.setClassName(actionClass.getName());
		actionConfig.setResults(resultBuilder.build(actionClass, packageConfig));
		packageConfig.addActionConfig(routing.getActionAndFormat(), actionConfig);

		if(logger.isDebugEnabled()) {
			logger.debug("load {} - {}", routing.getActionAndFormat(), actionConfig.getClassName());
		}
	}

	protected PackageConfig getPackageConfig(ActionRouting routing, Class<? extends Action> actionClass) {
		String packageName = packageNameBuilder.build(routing);
		PackageConfig packageConfig = configuration.getPackageConfig(packageName);
		if(packageConfig == null) {
			packageConfig = new PackageConfig();
			configuration.addPackageConfig(packageName, packageConfig);

			packageConfig.setName(packageName);
			String namespace = routing.getPackageAndController();
			if(!"/".equals(namespace)) {
				packageConfig.setNamespace(namespace);
			}

			String parentName = parentPackageNameBuilder.build(actionClass);
			if(packageName != null) {
				packageConfig.addParent(configuration.getPackageConfig(parentName));
			}
		}

		return packageConfig;
	}

	protected void notifyObjectFactory(Class<? extends Action> actionClass) {
		try {
			objectFactory.getClassInstance(actionClass.getName());
		} catch (ClassNotFoundException e) {
			new Throwable().printStackTrace();
			System.exit(1);
		}
	}

	protected Set<Class<? extends Action>> resolveActions(String basePackage) {
		ResolverUtil<Action> resolver = new ResolverUtil<Action>();
		resolver.find(new ResolverUtil.Test() {
			@SuppressWarnings("unchecked")
			public boolean matches(Class type) {
				if(!Action.class.isAssignableFrom(type)) {
					return false;
				}

				if(Modifier.isAbstract(type.getModifiers())) {
					return false;
				}

				if(type.isInterface()) {
					return false;
				}

				if(type.getAnnotation(Ignore.class) != null) {
					return false;
				}

				return true;
			}
		}, basePackage);

		return resolver.getClasses();
	}

	public void init(Configuration configuration) throws ConfigurationException {
		this.configuration = configuration;
	}

	public void destroy() {
	}

	public boolean needsReload() {
		return false;
	}

	public void register(ContainerBuilder builder, LocatableProperties props) {
	}


	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getActionPackage() {
		return actionPackage;
	}

	@Inject("flyer.action.package")
	public void setActionPackage(String pkg) {
		actionPackage = pkg;
	}

	public ActionRoutingBuilder getActionRoutingBuilder() {
		return actionRoutingBuilder;
	}

	@Inject
	public void setActionRoutingBuilder(ActionRoutingBuilder actionRoutingBuilder) {
		this.actionRoutingBuilder = actionRoutingBuilder;
	}

	public PackageNameBuilder getPackageNameBuilder() {
		return packageNameBuilder;
	}

	@Inject
	public void setPackageNameBuilder(PackageNameBuilder packageNameBuilder) {
		this.packageNameBuilder = packageNameBuilder;
	}

	public ParentPackageNameBuilder getParentPackageNameBuilder() {
		return parentPackageNameBuilder;
	}

	@Inject
	public void setParentPackageNameBuilder(ParentPackageNameBuilder parentPackageNameBuilder) {
		this.parentPackageNameBuilder = parentPackageNameBuilder;
	}

	public ResultBuilder getResultBuilder() {
		return resultBuilder;
	}

	@Inject
	public void setResultBuilder(ResultBuilder resultBuilder) {
		this.resultBuilder = resultBuilder;
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}
