/**
 * Created on 2007-11-2
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

/**
 * @author Sunteya
 *
 */
public class NestTransactionException extends RuntimeException {

	private static final long serialVersionUID = 5654884203323250628L;
	private Exception cause;

	public NestTransactionException(Exception cause) {
		setCause(cause);
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	@Override
	public Exception getCause() {
		return cause;
	}

	public void setCause(Exception cause) {
		this.cause = cause;
	}
}
