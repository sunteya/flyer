/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n;

import java.io.Serializable;

/**
 * @author Sunteya
 *
 */
public class FieldPathResolvable implements Serializable {

	private static final long serialVersionUID = -4597777400820929106L;
	private Object root;
	private String path;


	public static FieldPathResolvable newInstance(Object root, String path) {
		return new FieldPathResolvable(root, path);
	}

	public FieldPathResolvable(Object root, String path) {
		this.root = root;
		this.path = path;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
