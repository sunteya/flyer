/**
 * Created on 2007-01-16
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.mail;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class AsynchronousJavaMailSenderImpl implements Runnable, JavaMailSender {

	private Logger logger = LoggerFactory.getLogger(AsynchronousJavaMailSenderImpl.class);

	private JavaMailSender delegate;

	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();

	private Queue<Object> sendArgumentQueue = new LinkedList<Object>();

	private ExceptionHandler exceptionHandler = new ExceptionHandler() {
		public void sendMailException(Throwable e) {
			logger.error("send mail error", e);
		}
	};

	private Thread sendThread;

	public void init() {
		sendThread = new Thread(this);
		sendThread.start();
	}

	protected void appendAsynchronousMails(Object sendArgument) {
		lock.lock();
		try {
			sendArgumentQueue.add(sendArgument);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public void run() {
		while (true) {
			Object sendParam = null;
			lock.lock();
			try {
				if (sendArgumentQueue.isEmpty()) {
					notEmpty.await();
				}

				sendParam = sendArgumentQueue.poll();

			} catch (InterruptedException e) {
				logger.error("send mail await error", e);
				continue;
			} finally {
				lock.unlock();
			}

			try {
				sendMail(sendParam);
				if(logger.isInfoEnabled()) {
					logger.info("mail {} is sent.", sendParam);
				}

			} catch (Exception e) {
				exceptionHandler.sendMailException(e);
			}
		}

	}

	protected void sendMail(Object sendParam) throws MessagingException {
		if (sendParam instanceof SimpleMailMessage) {
			delegate.send((SimpleMailMessage) sendParam);

		} else if (sendParam instanceof SimpleMailMessage[]) {
			delegate.send((SimpleMailMessage[]) sendParam);

		} else if (sendParam instanceof MimeMessage) {
			delegate.send((MimeMessage) sendParam);

		} else if (sendParam instanceof MimeMessage[]) {
			delegate.send((MimeMessage[]) sendParam);

		} else if (sendParam instanceof MimeMessagePreparator) {
			delegate.send((MimeMessagePreparator) sendParam);

		} else {
			delegate.send((MimeMessagePreparator[]) sendParam);
		}
	}

	public Thread getSendThread() {
		return sendThread;
	}

	protected class MessagesWrapper {
		private MimeMessage[] mimeMessages;
		private Object[] originalMessages;

		public MessagesWrapper(MimeMessage[] mimeMessages,
				Object[] originalMessages) {
			this.mimeMessages = mimeMessages;
			this.originalMessages = originalMessages;
		}

		public MimeMessage[] getMimeMessages() {
			return mimeMessages;
		}

		public Object[] getOriginalMessages() {
			return originalMessages;
		}
	}

	public void send(MimeMessage mimeMessage) throws MailException {
		appendAsynchronousMails(mimeMessage);
	}

	public void send(MimeMessage[] mimeMessages) throws MailException {
		appendAsynchronousMails(mimeMessages);
	}

	public void send(MimeMessagePreparator mimeMessagePreparator)
			throws MailException {
		appendAsynchronousMails(mimeMessagePreparator);
	}

	public void send(MimeMessagePreparator[] mimeMessagePreparators)
			throws MailException {
		appendAsynchronousMails(mimeMessagePreparators);
	}

	public void send(SimpleMailMessage simpleMessage) throws MailException {
		appendAsynchronousMails(simpleMessage);
	}

	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		appendAsynchronousMails(simpleMessages);
	}

	// =====================================================
	// delegate
	// -----------------------------------------------------
	public MimeMessage createMimeMessage() {
		return delegate.createMimeMessage();
	}

	public MimeMessage createMimeMessage(InputStream contentStream)
			throws MailException {
		return delegate.createMimeMessage(contentStream);
	}

	// =====================================================
	// Ioc
	// -----------------------------------------------------
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public void setDelegate(JavaMailSender delegate) {
		this.delegate = delegate;
	}
}
