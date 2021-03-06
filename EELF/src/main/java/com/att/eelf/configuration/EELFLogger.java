/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import java.util.Locale;

import org.slf4j.Logger;

import com.att.eelf.i18n.EELFResolvableResourceEnum;

/**
 * The EELFLogger is the main interface to access loggers in EELF.
 * <p>
 * It defines the convenience methods that are available to the application to
 * log messages based on the string or a key in the resource bundle(s).
 * </p>
 *
 */
public interface EELFLogger extends Logger {

	public enum Level {
		TRACE,
		DEBUG,
		INFO,
		WARN,
		ERROR,
		OFF
	}

	/**
	 * Log a message or exception with arguments if the argument list is
	 * provided
	 * <P>
	 * If the logger is currently enabled for the given message level then the
	 * given message is forwarded to all the registered output Handler objects.
	 * </p>
	 *
	 * @param level One of the message level identifiers, e.g., SEVERE
	 * @param msg the message string to be logged
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void log(Level level, String msg, Throwable th, Object... arguments);

	/**
	 * Log a audit event using audit logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void auditEvent(String msg, Object... arguments);

	/**
	 * Log a audit event using audit logger at given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void auditEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a metrics event using metrics logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void metricsEvent(String msg, Object... arguments);

	/**
	 * Log a metrics event using metrics logger at info level at given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void metricsEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a security event using security logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void securityEvent(String msg, Object... arguments);

	/**
	 * Log a security event using security logger at given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void securityEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a performance event using performance logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void performanceEvent(String msg, Object... arguments);

	/**
	 * Log a performance event using performance logger at a given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void performanceEvent(Level level, String msg, Object... arguments);

	/**
	 * Log an application event using application logger at info.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void applicationEvent(String msg, Object... arguments);

	/**
	 * Log an application event using application logger at a given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void applicationEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a server event using server logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void serverEvent(String msg, Object... arguments);

	/**
	 * Log a server event using server logger at a given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void serverEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a policy event using policy logger at info level.
	 *
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void policyEvent(String msg, Object... arguments);

	/**
	 * Log a policy event using policy logger at a given level.
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 * @param msg the message string to be logged
	 * @param arguments list of arguments for message
	 */
	@Deprecated
	public void policyEvent(Level level, String msg, Object... arguments);

	/**
	 * Log a warn message based on message key as defined in resource bundle for
	 * the given locale along with exception.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void warn(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments);

	/**
	 * Log a info message based on message key as defined in resource bundle for
	 * the given locale along with exception.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void info(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments);

	/**
	 * Log a debug message based on message key as defined in resource bundle
	 * for the given locale along with exception.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void debug(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments);

	/**
	 * Log a error message based on message key as defined in resource bundle
	 * for the given locale along with exception.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void error(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments);

	/**
	 * Log a trace message based on message key as defined in resource bundle
	 * for the given locale along with exception.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void trace(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments);

	/**
	 * Log a warn message based on message key as defined in resource bundle for
	 * the given locale.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void warn(Locale locale, EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a info message based on message key as defined in resource bundle for
	 * the given locale.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void info(Locale locale, EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a debug message based on message key as defined in resource bundle
	 * for the given locale.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void debug(Locale locale, EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a error message based on message key as defined in resource bundle
	 * for the given locale.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void error(Locale locale, EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a trace message based on message key as defined in resource bundle
	 * for the given locale.
	 *
	 * @param locale the locale that we want to load the resource for
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void trace(Locale locale, EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a warn message based on message key as defined in resource bundle
	 * with arguments.
	 *
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void warn(EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a info message based on message key as defined in resource bundle
	 * with arguments.
	 *
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void info(EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a debug message based on message key as defined in resource bundle
	 * with arguments.
	 *
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void debug(EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a error message based on message key as defined in resource bundle
	 * with arguments.
	 *
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */

	public void error(EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a trace message based on message key as defined in resource bundle
	 * with arguments.
	 *
	 * @param resource identifies the resource to be used
	 * @param arguments list of arguments for message
	 */
	public void trace(EELFResolvableResourceEnum resource, String... arguments);

	/**
	 * Log a warn message based on message key as defined in resource bundle
	 * along with exception.
	 *
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void warn(EELFResolvableResourceEnum resource, Throwable th, String... arguments);

	/**
	 * Log a info message based on message key as defined in resource bundle
	 * along with exception.
	 *
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void info(EELFResolvableResourceEnum resource, Throwable th, String... arguments);

	/**
	 * Log a debug message based on message key as defined in resource bundle
	 * along with exception.
	 *
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void debug(EELFResolvableResourceEnum resource, Throwable th, String... arguments);

	/**
	 * Log a error message based on message key as defined in resource bundle
	 * along with exception.
	 *
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void error(EELFResolvableResourceEnum resource, Throwable th, String... arguments);

	/**
	 * Log a trace message based on message key as defined in resource bundle
	 * along with exception.
	 *
	 * @param resource identifies the resource to be used
	 * @param th the exception (throwable) to log
	 * @param arguments list of arguments for message
	 */
	public void trace(EELFResolvableResourceEnum resource, Throwable th, String... arguments);

	/**
	 * Change the logging level for the logger
	 *
	 * @param level One of the message level identifiers, e.g., WARN
	 */
	public void setLevel(Level level);

	/**
	 * Turn off the logging for the logger
	 */
	public void disableLogging();
}
