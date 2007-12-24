package com.sunteya.flyer.struts2.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Created on 2007-12-29
 * Created by Sunteya
 */

/**
 * @author Sunteya
 *
 */
public class OpenHibernateSessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 3127647779030660395L;

	private static final Logger logger = LoggerFactory.getLogger(OpenHibernateSessionInterceptor.class);

	private SessionFactory sessionFactory;
	private FlushMode flushMode = FlushMode.MANUAL;


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		openSession();
		return invocation.invoke();
	}

	public void openSession() {
		if(TransactionSynchronizationManager.hasResource(getSessionFactory())) {
			return;
		}
		logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
		Session session = getSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
	}

	public void closeSession() {
		if(TransactionSynchronizationManager.hasResource(getSessionFactory())) {
			SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
			closeSession(sessionHolder.getSession());
		}
	}

	protected Session getSession() throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		FlushMode flushMode = getFlushMode();
		if (flushMode != null) {
			session.setFlushMode(flushMode);
		}
		return session;
	}

	protected void closeSession(Session session) {
		SessionFactoryUtils.closeSession(session);
	}


	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}

	protected FlushMode getFlushMode() {
		return this.flushMode;
	}
}
