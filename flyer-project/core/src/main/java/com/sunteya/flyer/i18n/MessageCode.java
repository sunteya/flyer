/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sunteya
 *
 */
public class MessageCode implements Serializable {

	private static final long serialVersionUID = 6592731225345563602L;

	private List<String> codes = new ArrayList<String>();
	private List<Object> contexts = new LinkedList<Object>();
	private String defaultMessage = null;

	public MessageCode() {
	}

	public MessageCode(List<String> codes) {
		this(codes, (String) null);
	}

	public MessageCode(List<String> codes, Collection<Object> contexts) {
		this(codes, contexts, null);
	}

	public MessageCode(List<String> codes, String defaultMessage) {
		this(codes, new ArrayList<Object>(), defaultMessage);
	}

	public MessageCode(List<String> codes, Collection<Object> contexts, String defaultMessage) {
		setCodes(codes);
		setContexts(new LinkedList<Object>(contexts));
		setDefaultMessage(defaultMessage);
	}

	public void appendCodesPrefix(String prefix) {
		List<String> newCodes = new ArrayList<String>();
		for (String oldCode : getCodes()) {
			newCodes.add(prefix + oldCode);
		}

		setCodes(newCodes);
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public List<Object> getContexts() {
		return contexts;
	}

	public void setContexts(List<Object> contexts) {
		this.contexts = contexts;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
}
