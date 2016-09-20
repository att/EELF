/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.att.eelf.i18n.EELFResolvableErrorEnum;
import com.att.eelf.i18n.EELFResourceManager;

import static com.att.eelf.configuration.Configuration.*;

/**
 * This class provides the implementation of <code>EELFLogger</code> interface.
 * <p>
 * It is wrapper of a SLF4J logger with a logback implementation to redirect logging calls to SLF4J.
 *</p>
 */

public class SLF4jWrapper implements EELFLogger {
	private Logger logger;
	
	
	/**
     * Create the wrapper around the SLF4J logger
     * 
     * @param name
     *            The SLF4J logger to be wrapped as a SLF4j logger
     */
    public SLF4jWrapper(String name) {
    	this.logger = LoggerFactory.getLogger(name);
    }
    
    
    /**
     * Log a warn message, with no arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message
     */
    @Override
    public void warn(String msg) {
		 writeToLog(Level.WARN, msg);	
	}
    
    /**
     * Log a warn message, with arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param arguments
     *            The list of arguments
     */
    @Override
	public void warn(String msg, Object... arguments) {
		 writeToLog(Level.WARN, msg, null, arguments);	
	}

	/**
     * Log a exception at warn level.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param th
     *            The exception object 
     */
    @Override
	public void warn(String msg, Throwable th) {
		 writeToLog(Level.WARN, msg, th);	
	}

    /**
     * Log a debug message, with no arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     */
    @Override
	public void debug(String msg) {
		 writeToLog(Level.DEBUG, msg);	
	}
    
    /**
     * Log a debug message, with arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param arguments
     *            The list of arguments            
     */
    @Override
	public void debug(String msg, Object... arguments) {
		 writeToLog(Level.DEBUG, msg, null, arguments);	
	}
 
    /**
     * Log a exception at debug level.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param th
     *            The exception object            
     */
    @Override
	public void debug(String msg, Throwable th) {
		 writeToLog(Level.DEBUG, msg, th);	
	}

    /**
     * Log a info message, with no arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     */
    @Override
	public void info(String msg) {
		 writeToLog(Level.INFO, msg);	
	}
   
    /**
     * Log a info message, with arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param arguments
     * 			  The list of arguments         
     */
    @Override
	public void info(String msg, Object... arguments) {
		 writeToLog(Level.INFO, msg, null, arguments);	
	}

	/**
     * Log a trace message, with no arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     */
    @Override
	public void trace(String msg) {
		 writeToLog(Level.TRACE, msg);	
	}
   
    /**
     * Log a trace message, with arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param arguments
     *            The list of arguments            
     */
    @Override
	public void trace(String msg, Object... arguments) {
		 writeToLog(Level.TRACE, msg, null, arguments);	
	}


    /**
     * Log a exception at trace level.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param th
     *            The exception object            
     */
    @Override
	public void trace(String msg, Throwable th) {
		 writeToLog(Level.TRACE, msg, th);	
	}

    /**
     * Log a error message, with no arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     */
    @Override
	public void error(String msg) {
		 writeToLog(Level.ERROR, msg);	
	}
  
    /**
     * Log a error message, with arguments.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param arguments
     *            The list of arguments            
     */
    @Override
	public void error(String msg, Object... arguments) {
		 writeToLog(Level.ERROR, msg, null, arguments);	
	}


    /**
     * Log a exception at error level.
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param msg
     *            The string message 
     * @param th
     *            The exception object            
     */
    @Override
	public void error(String msg, Throwable th) {
		 writeToLog(Level.ERROR, msg, th);	
	}
	
	
    /**
     * Checks if the trace is enabled for the logger
     */
    @Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/**
	 * Checks if the info is enabled for the logger
	 */
    @Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	

	/**
	 * Checks if the error is enabled for the logger
	 */
    @Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/**
	 * Checks if the warn is enabled for the logger
	 */
    @Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}


	/**
	 * Checks if the debug is enabled for the logger
	 */
    @Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	

    /**
     * Log a message or exception with arguments if the argument list is provided
     * <P>
     * If the logger is currently enabled for the given message level then the given message is forwarded to all the
     * registered output Handler objects.
     * </p>
     * 
     * @param level
     *            One of the message level identifiers, e.g., SEVERE
     * @param msg
     *            The string message 
     * @param th
     *            The exception object
     * @param  arguments
     * 			The list of arguments                      
     */
	@Override
	public void log(Level level, String msg, Throwable th, Object... arguments) {
		writeToLog(level, msg, th, arguments);	
		
	}
			

	 /**
	  * Used by audit logger to record audit event with arguments at info level
	  * 
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
	@Override
	public void auditEvent(String msg, Object... arguments) {
		if (checkLoggerExists(AUDIT_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}	
	}

	 /**
	  * Used by audit logger to record audit event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
	@Override
	public void auditEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(AUDIT_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}	
		
	}
	

	/**
	  * Used by metrics logger to record metrics event with arguments at info level
	  * 
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
	@Override
	public void metricsEvent(String msg, Object... arguments) {
		if (checkLoggerExists(METRICS_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}
		
	}

	 /**
	  * Used by metrics logger to record audit event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */
	@Override
	public void metricsEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(METRICS_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}	
		
	}

	

	/**
	  * Used by security logger to record security event with arguments at info level
	  * 
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */
	@Override
	public void securityEvent(String msg, Object... arguments) {
		if (checkLoggerExists(SECURITY_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}	
		
	}

	 /**
	  * Used by security logger to record security event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */
	@Override
	public void securityEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(SECURITY_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}	
		
	}

	
	@Override
	public void performanceEvent(String msg, Object... arguments) {
		if (checkLoggerExists(PERF_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}	
		
	}

	 /**
	  * Used by performance logger to record performance event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */
	@Override
	public void performanceEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(PERF_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}	
		
	}

	
	@Override
	public void applicationEvent(String msg, Object... arguments) {
		if (checkLoggerExists(GENERAL_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);	
		}	
		
	}

	 /**
	  * Used by application logger to record application generic event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */
	@Override
	public void applicationEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(GENERAL_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
			
		}
	}

	
	@Override
	public void serverEvent(String msg, Object... arguments) {
		if (checkLoggerExists(SERVER_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}	
		
	}

	/**
	  * Used by server logger to record server event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
	public void serverEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(SERVER_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}
		
	}

	
	@Override
	public void policyEvent(String msg, Object... arguments) {
		if (checkLoggerExists(POLICY_LOGGER_NAME)) {
			writeToLog(Level.INFO, msg, null, arguments);
		}	
		
	}

	/**
	  * Used by policy logger to record policy event with arguments at given level
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., SEVERE
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
	public void policyEvent(Level level, String msg, Object... arguments) {
		if (checkLoggerExists(POLICY_LOGGER_NAME)) {
			writeToLog(level, msg, null, arguments);
		}	
		
	}
	
	/**
     * This method is called by each logging method to determine if the specified level is active, format the message,
     * and write it to the slf4j logger.
     * 
     * @param level
     *            The level as defined by EELFLogger.
     * @param msg
     *            The message to be written, possibly formatted with parameters
     * @param th
     *            Any throwable to be recorded as part of the logging event, or null
     * @param arguments
     *            The optional format parameters for the message
     */

	private void writeToLog(Level level, String msg, Throwable th, Object... arguments) {
		if (level.equals(Level.TRACE)) {
            if (logger.isTraceEnabled()) {
                if (th != null) {
                    if (arguments == null || arguments.length == 0) {
                        logger.trace(msg, th);
                    } else {
                        logger.trace(msg, arguments, th);
                    }
                } else {
                    if (arguments == null || arguments.length == 0) {
                        logger.trace(msg);
                    } else {
                        logger.trace(msg, arguments);
                    }
                }
            }
        }  else if (level.equals(Level.INFO)) {
            if (logger.isInfoEnabled()) {
                if (th != null) {
                    if (arguments == null || arguments.length == 0) {
                        logger.info(msg, th);
                    } else {
                        logger.info(msg, arguments, th);
                    }
                } else {
                    if (arguments == null || arguments.length == 0) {
                        logger.info(msg);
                    } else {
                        logger.info(msg, arguments);
                    }
                }
            }
        } else if (level.equals(Level.WARN)) {
            if (logger.isWarnEnabled()) {
                if (th != null) {
                    if (arguments == null || arguments.length == 0) {
                        logger.warn(msg, th);
                    } else {
                        logger.warn(msg, arguments, th);
                    }
                } else {
                    if (arguments == null || arguments.length == 0) {
                        logger.warn(msg);
                    } else {
                        logger.warn(msg, arguments);
                    }
                }
            }
        } else if (level.equals(Level.ERROR)) {
            if (logger.isErrorEnabled()) {
                if (th != null) {
                    if (arguments == null || arguments.length == 0) {
                        logger.error(msg, th);
                    } else {
                        logger.error(msg, arguments, th);
                    }
                } else {
                    if (arguments == null || arguments.length == 0) {
                        logger.error(msg);
                    } else {
                        logger.error(msg, arguments);
                    }
                }
            }
        } else if (level.equals(Level.DEBUG)) {
            if (logger.isDebugEnabled()) {
                if (th != null) {
                    if (arguments == null || arguments.length == 0) {
                        logger.debug(msg, th);
                    } else {
                        logger.debug(msg, arguments, th);
                    }
                } else {
                    if (arguments == null || arguments.length == 0) {
                        logger.debug(msg);
                    } else {
                        logger.debug(msg, arguments);
                    }
                }
            }
       }
	}
	
	 /**
     * This method is called by each logging method to determine if the specified level is active, format the message,
     * and write it to the slf4j logger.
     * 
     * @param level
     *            The level as defined by EELFLogger.
     * @param msg
     *            The message to be written, possibly formatted with parameters
     * @param arguments
     *            The optional format parameters for the message
     */

	private void writeToLog(Level level, String msg, Object... arguments) {
		if (level.equals(Level.TRACE)) {
            if (logger.isTraceEnabled()) {
                  if (arguments == null || arguments.length == 0) {
                        logger.trace(msg);
                  } else {
                        logger.trace(msg, arguments);
                  }
            }
        }  else if (level.equals(Level.INFO)) {
            if (logger.isInfoEnabled()) {
               
                    if (arguments == null || arguments.length == 0) {
                        logger.info(msg);
                    } else {
                        logger.info(msg, arguments);
                    }
            }
        } else if (level.equals(Level.WARN)) {
            if (logger.isWarnEnabled()) {
               
                    if (arguments == null || arguments.length == 0) {
                        logger.warn(msg);
                    } else {
                        logger.warn(msg, arguments);
                    }
              
            }
        } else if (level.equals(Level.ERROR)) {
            if (logger.isErrorEnabled()) {
               
                    if (arguments == null || arguments.length == 0) {
                        logger.error(msg);
                    } else {
                        logger.error(msg, arguments);
                    }
                
            }
        } else if (level.equals(Level.DEBUG)) {
            if (logger.isDebugEnabled()) {
                
                    if (arguments == null || arguments.length == 0) {
                        logger.debug(msg);
                    } else {
                        logger.debug(msg, arguments);
                    }
               
            }
       }
	}
	
	@Override
	public void warn(Locale locale,EELFResolvableErrorEnum errorCode,  Throwable th, String... args) {
		writeToLog(Level.WARN, errorCode, locale, th, args);	
		
	}
	
	@Override
	public void info(Locale locale, EELFResolvableErrorEnum errorCode,  Throwable th, String... args) {
		writeToLog(Level.INFO, errorCode, locale, th, args);		
		
	}
	
	@Override
	public void debug(Locale locale, EELFResolvableErrorEnum errorCode, Throwable th, String... args) {
		writeToLog(Level.DEBUG, errorCode, locale, th, args);	
		
	}
	
	@Override
	public void trace(Locale locale, EELFResolvableErrorEnum errorCode, Throwable th, String... args) {
		writeToLog(Level.TRACE, errorCode, locale, th, args);	
		
	}
	
	@Override
	public void error(Locale locale, EELFResolvableErrorEnum errorCode, Throwable th, String... args) {
		writeToLog(Level.ERROR, errorCode, locale, th, args);	
		
	}
	
	
	 /**
     * This method is called by each logging method to determine if the specified level is active, format the message,
     * and write it to the slf4j logger.
     * 
     * @param level
     *            The level as defined by EELFLogger.
     * @param resource
     *             Retrieves the error code from the loaded bundle(s) 
     * @param locale 
     * 			   The locale to use when selecting and formatting the message          
     * @param th
     *            Any throwable to be recorded as part of the logging event, or null
     * @param arguments
     *            The optional format parameters for the message
     */ 

	
	private void writeToLog(Level level, EELFResolvableErrorEnum resource, Locale locale, Throwable th, String... arguments) {
		if (level.equals(Level.TRACE)) {
	            if (logger.isTraceEnabled()) {
	                if (th != null) {
	                    	if (locale == null) {
	                    		logger.trace(EELFResourceManager.format(resource, th, arguments));
	                    	} else {
	                    		logger.trace(EELFResourceManager.format(locale, resource, th, arguments));
	                    	}	
	                 } else {
	                    	if (locale == null) {
	                    		logger.trace(EELFResourceManager.format(resource, arguments));
	                    	} else {
	                    		logger.trace(EELFResourceManager.format(locale, resource, arguments));
	                    	}	
	                 }
	              } 
	        }  else if (level.equals(Level.INFO)) {
	            if (logger.isInfoEnabled()) {
	            	 if (th != null) {
	                    	if (locale == null) {
	                    		logger.info(EELFResourceManager.format(resource, th, arguments));
	                    	} else {
	                    		logger.info(EELFResourceManager.format(locale, resource, th, arguments));
	                    	}	
	                 } else {
	                    	if (locale == null) {
	                    		logger.info(EELFResourceManager.format(resource, arguments));
	                    	} else {
	                    		logger.info(EELFResourceManager.format(locale, resource, arguments));
	                    	}	
	                 }
	            }
	        } else if (level.equals(Level.WARN)) {
	            if (logger.isWarnEnabled()) {
	            	if (th != null) {
                    	if (locale == null) {
                    		logger.warn(EELFResourceManager.format(resource, th, arguments));
                    	} else {
                    		logger.warn(EELFResourceManager.format(locale, resource, th, arguments));
                    	}	
	            	} else {
                    	if (locale == null) {
                    		logger.warn(EELFResourceManager.format(resource, arguments));
                    	} else {
                    		logger.warn(EELFResourceManager.format(locale, resource, arguments));
                    	}	
	            	}
	            }
	        } else if (level.equals(Level.ERROR)) {
	            if (logger.isErrorEnabled()) {
	            	if (th != null) {
                    	if (locale == null) {
                    		logger.error(EELFResourceManager.format(resource, th, arguments));
                    	} else {
                    		logger.error(EELFResourceManager.format(locale, resource, th, arguments));
                    	}	
	            	} else {
                    	if (locale == null) {
                    		logger.error(EELFResourceManager.format(resource, arguments));
                    	} else {
                    		logger.error(EELFResourceManager.format(locale, resource, arguments));
                    	}	
	            	}
	            }
	        } else if (level.equals(Level.DEBUG)) {
	            if (logger.isDebugEnabled()) {
	            	if (th != null) {
                    	if (locale == null) {
                    		logger.debug(EELFResourceManager.format(resource, th, arguments));
                    	} else {
                    		logger.debug(EELFResourceManager.format(locale, resource, th, arguments));
                    	}	
	            	} else {
                    	if (locale == null) {
                    		logger.debug(EELFResourceManager.format(resource, arguments));
                    	} else {
                    		logger.debug(EELFResourceManager.format(locale, resource, arguments));
                    	}	
	            	}
	            }
	       }
		
	}



	@Override
	public void warn(Locale locale, EELFResolvableErrorEnum errorCode,  String... args) {
		writeToLog(Level.WARN, errorCode, locale, null, args);
		
	}



	@Override
	public void info(Locale locale, EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.INFO, errorCode, locale, null, args);
		
	}



	@Override
	public void debug(Locale locale, EELFResolvableErrorEnum errorCode,  String... args) {
		writeToLog(Level.DEBUG, errorCode, locale, null, args);
		
	}



	@Override
	public void error(Locale locale, EELFResolvableErrorEnum errorCode,  String... args) {
		writeToLog(Level.ERROR, errorCode, locale, null, args);
	}



	@Override
	public void trace( Locale locale, EELFResolvableErrorEnum errorCode,String... args) {
		writeToLog(Level.TRACE, errorCode, locale, null, args);
		
	}



	@Override
	public void warn(EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.WARN, errorCode, null, null, args);
		
	}




	public void info(EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.INFO, errorCode, null, null, args);
		
	}




	public void debug(EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.DEBUG, errorCode, null, null, args);
		
	}




	public void error(EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.ERROR, errorCode, null, null, args);
		
	}




	public void trace(EELFResolvableErrorEnum errorCode, String... args) {
		writeToLog(Level.TRACE, errorCode, null, null, args);
		
	}




	public void warn(EELFResolvableErrorEnum errorCode, Throwable th,
			String... args) {
		writeToLog(Level.WARN, errorCode, null, th, args);
		
	}




	public void info(EELFResolvableErrorEnum errorCode, Throwable th,
			String... args) {
		writeToLog(Level.INFO, errorCode, null, th, args);
		
	}




	public void debug(EELFResolvableErrorEnum errorCode, Throwable th,
			String... args) {
		writeToLog(Level.DEBUG, errorCode, null, th, args);
		
	}




	public void error(EELFResolvableErrorEnum errorCode, Throwable th,
			String... args) {
		writeToLog(Level.ERROR, errorCode, null, th, args);
		
	}




	public void trace(EELFResolvableErrorEnum errorCode, Throwable th,
			String... args) {
		writeToLog(Level.TRACE, errorCode, null, th, args);
		
	}


	@Override
	public void setLevel(Level level) {
		if (logger instanceof ch.qos.logback.classic.Logger) {
			switch (level) {
				case INFO: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.INFO); break;
				case ERROR: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.ERROR); break;
				case DEBUG: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.DEBUG); break;
				case TRACE: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.TRACE); break;
				case WARN: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.WARN); break;
				case OFF: ((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.OFF); break;
			
			}
			
		}	
		
	}


	@Override
	public void disableLogging() {
		if (logger instanceof ch.qos.logback.classic.Logger) {
			((ch.qos.logback.classic.Logger)logger).setLevel(ch.qos.logback.classic.Level.OFF); 
		}	
		
	}
	

	private boolean checkLoggerExists(String name) {
		if (logger.getName().equals(name)) {
			return true;
			
		} else {
			return false;
		}
	}
		
	
}
