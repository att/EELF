/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.att.eelf.i18n.EELFResolvableResourceEnum;
import com.att.eelf.i18n.EELFResourceManager;

public abstract class AbstractEELFLogger implements EELFLogger {
	protected Logger logger;

	/**
	 * @param name the name of the logger
	 */
	public AbstractEELFLogger(String name) {
		this.logger = LoggerFactory.getLogger(name);
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		logger.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		logger.trace(format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		logger.trace(format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object... arguments) {
		logger.trace(format, arguments);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	@Override
	public void trace(Marker marker, String msg) {
		logger.trace(marker, msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		logger.trace(marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		logger.trace(marker, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		logger.trace(marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		logger.trace(marker, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		logger.debug(format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		logger.debug(format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object... arguments) {
		logger.debug(format, arguments);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	@Override
	public void debug(Marker marker, String msg) {
		logger.debug(marker, msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		logger.debug(marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		logger.debug(marker, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... argArray) {
		logger.debug(marker, format, argArray);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		logger.debug(marker, msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		logger.info(format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		logger.info(format, arg1, arg2);
	}

	@Override
	public void info(String format, Object... arguments) {
		logger.info(format, arguments);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		logger.info(marker, msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		logger.info(marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		logger.info(marker, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... argArray) {
		logger.info(marker, format, argArray);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		logger.info(marker, msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	@Override
	public void warn(String format, Object arg) {
		logger.warn(format, arg);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		logger.warn(format, arg1, arg2);
	}

	@Override
	public void warn(String format, Object... arguments) {
		logger.warn(format, arguments);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	@Override
	public void warn(Marker marker, String msg) {
		logger.warn(marker, msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		logger.warn(marker, format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		logger.warn(marker, format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object... argArray) {
		logger.warn(marker, format, argArray);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		logger.warn(marker, msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	@Override
	public void error(String format, Object arg) {
		logger.error(format, arg);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		logger.error(format, arg1, arg2);
	}

	@Override
	public void error(String format, Object... arguments) {
		logger.error(format, arguments);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return logger.isErrorEnabled(marker);
	}

	@Override
	public void error(Marker marker, String msg) {
		logger.error(marker, msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		logger.error(marker, format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		logger.error(marker, format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... argArray) {
		logger.error(marker, format, argArray);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		logger.error(marker, msg, t);
	}

	@Override
	public void log(Level level, String msg, Throwable th, Object... arguments) {
		writeEvent(level, msg, th, arguments);
	}

	@Override
	public void auditEvent(String msg, Object... arguments) {
		writeEvent(Configuration.AUDIT_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void auditEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.AUDIT_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void metricsEvent(String msg, Object... arguments) {
		writeEvent(Configuration.METRICS_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void metricsEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.METRICS_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void securityEvent(String msg, Object... arguments) {
		writeEvent(Configuration.SECURITY_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void securityEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.SECURITY_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void performanceEvent(String msg, Object... arguments) {
		writeEvent(Configuration.PERF_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void performanceEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.PERF_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void applicationEvent(String msg, Object... arguments) {
		writeEvent(Configuration.GENERAL_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void applicationEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.GENERAL_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void serverEvent(String msg, Object... arguments) {
		writeEvent(Configuration.SERVER_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void serverEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.SERVER_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void policyEvent(String msg, Object... arguments) {
		writeEvent(Configuration.POLICY_LOGGER_NAME, Level.INFO, msg, arguments);
	}

	@Override
	public void policyEvent(Level level, String msg, Object... arguments) {
		writeEvent(Configuration.POLICY_LOGGER_NAME, level, msg, arguments);
	}

	@Override
	public void warn(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		writeEvent(Level.WARN, locale, resource, th, arguments);
	}

	@Override
	public void info(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		writeEvent(Level.INFO, locale, resource, th, arguments);
	}

	@Override
	public void debug(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		writeEvent(Level.DEBUG, locale, resource, th, arguments);
	}

	@Override
	public void error(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		writeEvent(Level.ERROR, locale, resource, th, arguments);
	}

	@Override
	public void trace(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		writeEvent(Level.TRACE, locale, resource, th, arguments);
	}

	@Override
	public void warn(Locale locale, EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.WARN, locale, resource, null, arguments);
	}

	@Override
	public void info(Locale locale, EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.INFO, locale, resource, null, arguments);
	}

	@Override
	public void debug(Locale locale, EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.DEBUG, locale, resource, null, arguments);
	}

	@Override
	public void error(Locale locale, EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.ERROR, locale, resource, null, arguments);
	}

	@Override
	public void trace(Locale locale, EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.TRACE, locale, resource, null, arguments);
	}

	@Override
	public void warn(EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.WARN, null, resource, null, arguments);
	}

	@Override
	public void info(EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.INFO, null, resource, null, arguments);
	}

	@Override
	public void debug(EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.DEBUG, null, resource, null, arguments);
	}

	@Override
	public void error(EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.ERROR, null, resource, null, arguments);
	}

	@Override
	public void trace(EELFResolvableResourceEnum resource, String... arguments) {
		writeEvent(Level.TRACE, null, resource, null, arguments);
	}

	@Override
	public void warn(EELFResolvableResourceEnum resource, Throwable th, String... arguments) {
		writeEvent(Level.WARN, null, resource, th, arguments);
	}

	@Override
	public void info(EELFResolvableResourceEnum resource, Throwable th, String... arguments) {
		writeEvent(Level.INFO, null, resource, th, arguments);
	}

	@Override
	public void debug(EELFResolvableResourceEnum resource, Throwable th, String... arguments) {
		writeEvent(Level.DEBUG, null, resource, th, arguments);
	}

	@Override
	public void error(EELFResolvableResourceEnum resource, Throwable th, String... arguments) {
		writeEvent(Level.ERROR, null, resource, th, arguments);
	}

	@Override
	public void trace(EELFResolvableResourceEnum resource, Throwable th, String... arguments) {
		writeEvent(Level.TRACE, null, resource, th, arguments);
	}

	@Override
	public void setLevel(Level level) {
		// slf4j api doesn't actually support changing the level but the
		// underlying logging implementation might
	}

	@Override
	public void disableLogging() {
		setLevel(Level.OFF);
	}

	private void writeEvent(String loggerName, Level level, String msg, Object... arguments) {
		if (logger.getName().equals(loggerName)) {
			writeEvent(level, msg, null, arguments);
		}
	}

	private void writeEvent(Level level, String msg, Throwable th, Object... arguments) {
		if (level == Level.DEBUG) {
			if (logger.isDebugEnabled()) {
				if (arguments != null && arguments.length > 0) {
					if (th == null) {
						logger.debug(msg, arguments);
					} else {
						logger.debug(msg, arguments, th);
					}
				} else if (th == null) {
					logger.debug(msg);
				} else {
					logger.debug(msg, th);
				}
			}
		} else if (level == Level.INFO) {
			if (logger.isInfoEnabled()) {
				if (arguments != null && arguments.length > 0) {
					if (th == null) {
						logger.info(msg, arguments);
					} else {
						logger.info(msg, arguments, th);
					}
				} else if (th == null) {
					logger.info(msg);
				} else {
					logger.info(msg, th);
				}
			}
		} else if (level == Level.TRACE) {
			if (logger.isTraceEnabled()) {
				if (arguments != null && arguments.length > 0) {
					if (th == null) {
						logger.trace(msg, arguments);
					} else {
						logger.trace(msg, arguments, th);
					}
				} else if (th == null) {
					logger.trace(msg);
				} else {
					logger.trace(msg, th);
				}
			}
		} else if (level == Level.WARN) {
			if (logger.isWarnEnabled()) {
				if (arguments != null && arguments.length > 0) {
					if (th == null) {
						logger.warn(msg, arguments);
					} else {
						logger.warn(msg, arguments, th);
					}
				} else if (th == null) {
					logger.warn(msg);
				} else {
					logger.warn(msg, th);
				}
			}
		} else if (level == Level.ERROR) {
			if (logger.isErrorEnabled()) {
				if (arguments != null && arguments.length > 0) {
					if (th == null) {
						logger.error(msg, arguments);
					} else {
						logger.error(msg, arguments, th);
					}
				} else if (th == null) {
					logger.error(msg);
				} else {
					logger.error(msg, th);
				}
			}
		}
	}

	private void writeEvent(Level level, Locale locale, EELFResolvableResourceEnum resource,
			Throwable th, String... arguments) {
		if (level == Level.DEBUG) {
			if (logger.isDebugEnabled()) {
				logger.debug(format(locale, resource, th, arguments));
			}
		} else if (level == Level.INFO) {
			if (logger.isInfoEnabled()) {
				logger.info(format(locale, resource, th, arguments));
			}
		} else if (level == Level.TRACE) {
			if (logger.isTraceEnabled()) {
				logger.trace(format(locale, resource, th, arguments));
			}
		} else if (level == Level.WARN) {
			if (logger.isWarnEnabled()) {
				logger.warn(format(locale, resource, th, arguments));
			}
		} else if (level == Level.ERROR) {
			if (logger.isErrorEnabled()) {
				logger.error(formatError(locale, resource, th, arguments));
			}
		}
	}

	private String format(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		if (locale == null) {
			if (th == null) {
				return EELFResourceManager.format(resource, arguments);
			} else {
				return EELFResourceManager.format(resource, th, arguments);
			}
		} else if (th == null) {
			return EELFResourceManager.format(locale, resource, arguments);
		} else {
			return EELFResourceManager.format(locale, resource, th, arguments);
		}
	}

	private String formatError(Locale locale, EELFResolvableResourceEnum resource, Throwable th,
			String... arguments) {
		if (locale == null) {
			if (th == null) {
				return EELFResourceManager.formatError(resource, arguments);
			} else {
				return EELFResourceManager.formatError(resource, th, arguments);
			}
		} else if (th == null) {
			return EELFResourceManager.formatError(locale, resource, arguments);
		} else {
			return EELFResourceManager.formatError(locale, resource, th, arguments);
		}
	}
}
