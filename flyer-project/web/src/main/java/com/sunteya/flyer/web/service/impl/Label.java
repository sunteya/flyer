/**
 * Created on 2007-3-8
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service.impl;

/**
 * @author Sunteya
 *
 */
public class Label {

	private String key;
	private String value;

	public Label() {
		super();
	}

	public Label(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
