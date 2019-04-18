/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import static com.att.eelf.configuration.Configuration.AUDIT_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.DEBUG_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.ERROR_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.GENERAL_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.METRICS_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.PERF_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.POLICY_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.SECURITY_LOGGER_NAME;
import static com.att.eelf.configuration.Configuration.SERVER_LOGGER_NAME;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.eelf.configuration.EELFLogger.Level;
import com.att.eelf.i18n.EELFMsgs;
import com.att.eelf.i18n.EELFResourceManager;

import ch.qos.logback.classic.LoggerContext;

/**
 * This is a singleton class used to obtain a named Logger instance. The
 * EELFManager object can be retrieved using EELFManager.getInstance(). It is
 * created during class initialization and cannot subsequently be changed. At
 * startup the EELFManager loads the logging configuration file. If no external
 * logging configuration file is found, it will load the default logging
 * configuration available at com/att/eelf/logback.xml
 *
 *
 */

public final class EELFManager {

	/**
	 * The logger used to record general application log events
	 */
	private EELFLogger applicationLogger;

	/**
	 * The logger used to record audit events
	 */
	private EELFLogger auditLogger;

	/**
	 * The logger used to record metric events
	 */
	private EELFLogger metricsLogger;

	/**
	 * The logger used to record performance events
	 */
	private EELFLogger performanceLogger;

	/**
	 * The logger used to record policy manager application events
	 */
	private EELFLogger policyLogger;

	/**
	 * The logger used to record security events
	 */
	private EELFLogger securityLogger;

	/**
	 * The logger used to record server events
	 */
	private EELFLogger serverLogger;

	/**
	 * The logger used to record error events
	 */
	private EELFLogger errorLogger;

	/**
	 * The logger used to record debug events
	 */
	private EELFLogger debugLogger;

	/**
	 * Cache of all loggers instantiated via this EELFManager
	 */
	private Map<String, EELFLogger> loggerCache = new ConcurrentHashMap<String, EELFLogger>();

	/**
	 * The singleton instance returned by EELFManager.getInstance().
	 */
	private static EELFManager instance;

	static {
		instance = new EELFManager();
		try {
			instance.init();
		} catch (Exception e) {
			instance = null;
			throw e;
		}
	}

	private EELFManager() {
	}

	private void init() {
		List<String> messages;

		/*
		 * Now, we are ready to initialize logging. Check to see if logging has
		 * already been initialized and that the application logger exists
		 * already. If it does, then skip the logging configuration because it
		 * was already set up in the container that is calling us. If not, then
		 * we need to set it up.
		 */
		ILoggerFactory factory = LoggerFactory.getILoggerFactory();
		if (factory instanceof LoggerContext) {
			LoggerContext loggerContext = (LoggerContext) factory;
			if (loggerContext.exists(GENERAL_LOGGER_NAME) == null) {
				LogbackConfigHelper helper = new LogbackConfigHelper();
				helper.initialize();
				messages = helper.getMessages();
			} else {
				messages = Arrays.asList(
						EELFResourceManager.getMessage(EELFMsgs.LOGGING_ALREADY_INITIALIZED));
			}
		} else {
			messages = Arrays
					.asList(EELFResourceManager.getMessage(EELFMsgs.UNSUPPORTED_LOGGING_FRAMEWORK));
		}

		// copy all delayed logging messages to the logger
		Logger logger = getApplicationLogger();

		for (String message : messages) {
			// All messages are prefixed with a message code of the form
			// EELF####S
			// Where:
			// EELF -- is the product code
			// #### -- is the message number
			// S ----- is the severity code (I=INFO, D=DEBUG, W=WARN, E=ERROR)
			char severity = message.charAt(8);
			switch (severity) {
			case 'D':
				logger.debug(message);
				break;
			case 'I':
				logger.info(message);
				break;
			case 'W':
				logger.warn(message);
				break;
			case 'E':
				logger.error(message);
			}
		}
	}

	/**
	 * This method is used to obtain the EELFManager (as well as set it up if
	 * not already). This has been deprecated since all of the public methods
	 * are now static so no need to fetch the instance.
	 *
	 * @return The EELFManager object
	 */
	public static EELFManager getInstance() {
		return instance;
	}

	/**
	 * Returns the logger associated with the name
	 *
	 * @param name the name of the logger
	 * @return EELFLogger
	 */
	public static EELFLogger getLogger(String name) {
		EELFLogger logger = instance.loggerCache.get(name);
		if (logger == null) {
			EELFLogger newLogger = new LogbackEELFLogger(name);

			// if another thread created the logger since the above cache
			// lookup, it will be returned by putIfAbsent and we'll use that
			// one instead; otherwise, we'll use the one just created

			logger = instance.loggerCache.putIfAbsent(name, newLogger);
			if (logger == null) {
				logger = newLogger;
			}
		}

		return logger;
	}

	/**
	 * Returns the logger associated with the clazz
	 *
	 * @param clazz The class that we are obtaining the logger for
	 * @return EELFLogger The logger
	 */
	public static EELFLogger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * Returns the application logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getApplicationLogger() {
		if (instance.applicationLogger == null) {
			instance.applicationLogger = getLogger(GENERAL_LOGGER_NAME);
		}

		return instance.applicationLogger;
	}

	/**
	 * Returns the metrics logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getMetricsLogger() {
		if (instance.metricsLogger == null) {
			instance.metricsLogger = getLogger(METRICS_LOGGER_NAME);
		}

		return instance.metricsLogger;
	}

	/**
	 * Returns the audit logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getAuditLogger() {
		if (instance.auditLogger == null) {
			instance.auditLogger = getLogger(AUDIT_LOGGER_NAME);
		}

		return instance.auditLogger;
	}

	/**
	 * Returns the performance logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getPerformanceLogger() {
		if (instance.performanceLogger == null) {
			instance.performanceLogger = getLogger(PERF_LOGGER_NAME);
		}

		return instance.performanceLogger;
	}

	/**
	 * Returns the server logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getServerLogger() {
		if (instance.serverLogger == null) {
			instance.serverLogger = getLogger(SERVER_LOGGER_NAME);
		}

		return instance.serverLogger;
	}

	/**
	 * Returns the security logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getSecurityLogger() {
		if (instance.securityLogger == null) {
			instance.securityLogger = getLogger(SECURITY_LOGGER_NAME);
		}

		return instance.securityLogger;
	}

	/**
	 * Returns the policy logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getPolicyLogger() {
		if (instance.policyLogger == null) {
			instance.policyLogger = getLogger(POLICY_LOGGER_NAME);
		}

		return instance.policyLogger;
	}

	/**
	 * Returns the error logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getErrorLogger() {
		if (instance.errorLogger == null) {
			instance.errorLogger = getLogger(ERROR_LOGGER_NAME);
		}

		return instance.errorLogger;
	}

	/**
	 * Returns the error logger
	 *
	 * @return EELFLogger
	 */
	public static EELFLogger getDebugLogger() {
		if (instance.debugLogger == null) {
			instance.debugLogger = getLogger(DEBUG_LOGGER_NAME);
		}

		return instance.debugLogger;
	}

	/**
	 * Log a audit event using audit logger at info level.
	 *
	 * @param msg the message string to be logged The string message
	 * @param arguments list of arguments for message The list of arguments
	 */
	public static void auditEvent(String msg, Object... arguments) {
		getAuditLogger().info(msg, arguments);
	}

	/**
	 * Log a audit event using audit logger at given level.
	 *
	 * @param level the severity level of the message, e.g., WARN One of the
	 *            message level identifiers, e.g., WARN
	 * @param msg the message string to be logged The string message
	 * @param arguments list of arguments for message The list of arguments
	 */
	public static void auditEvent(Level level, String msg, Object... arguments) {
		getAuditLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log a metrics event using metrics logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void metricsEvent(String msg, Object... arguments) {
		getMetricsLogger().info(msg, arguments);
	}

	/**
	 * Log a metrics event using metrics logger at info level at given level.
	 *
	 * @param level the severity level of the message, e.g., WARN One of the
	 *            message level identifiers, e.g., WARN
	 * @param msg the message string to be logged The string message
	 * @param arguments list of arguments for message The list of arguments
	 */
	public static void metricsEvent(Level level, String msg, Object... arguments) {
		getMetricsLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log a security event using security logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void securityEvent(String msg, Object... arguments) {
		getSecurityLogger().info(msg, arguments);
	}

	/**
	 * Log a security event using security logger at given level.
	 *
	 * @param level the severity level of the message, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void securityEvent(Level level, String msg, Object... arguments) {
		getSecurityLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log a performance event using performance logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void performanceEvent(String msg, Object... arguments) {
		getPerformanceLogger().info(msg, arguments);
	}

	/**
	 * Log a performance event using performance logger at a given level.
	 *
	 * @param level the severity level of the message, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void performanceEvent(Level level, String msg, Object... arguments) {
		getPerformanceLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log an application event using application logger at info.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void applicationEvent(String msg, Object... arguments) {
		getApplicationLogger().info(msg, arguments);
	}

	/**
	 * Log an application event using application logger at a given level.
	 *
	 * @param level the severity level of the message, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void applicationEvent(Level level, String msg, Object... arguments) {
		getApplicationLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log a server event using server logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void serverEvent(String msg, Object... arguments) {
		getServerLogger().info(msg, arguments);
	}

	/**
	 * Log a server event using server logger at a given level.
	 *
	 * @param level the severity level of the message, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void serverEvent(Level level, String msg, Object... arguments) {
		getServerLogger().log(level, msg, null, arguments);
	}

	/**
	 * Log a policy event using policy logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void policyEvent(String msg, Object... arguments) {
		getPolicyLogger().info(msg, arguments);
	}

	/**
	 * Log a policy event using policy logger at a given level.
	 *
	 * @param level the severity level of the message, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	public static void policyEvent(Level level, String msg, Object... arguments) {
		getPolicyLogger().log(level, msg, null, arguments);
	}
}
