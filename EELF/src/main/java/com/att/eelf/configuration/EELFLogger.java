/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import java.util.Locale;

import com.att.eelf.i18n.EELFResolvableErrorEnum;

/**
 * The EELFLogger is the main interface to access loggers in EELF.
 * <p>
 * It defines the convenience methods that are available to the application to log messages based 
 * on the string or a key in the resource bundle(s).
 * </p>
 *  
 */
public interface EELFLogger  {

	 public enum Level {
	      TRACE, DEBUG, INFO, WARN, ERROR, OFF
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
    public void warn(String msg);
    
   
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
    public void warn(String msg, Object... arguments);
    
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
    public void warn(String msg, Throwable th);
    
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
    public void debug(String msg);
    
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
    public void debug(String msg, Object... arguments);
    
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
    public void debug(String msg, Throwable th);
    

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
    public void info(String msg);
    
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
    public void info(String msg, Object... arguments);
    
   
    
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
    public void trace(String msg);
    

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
    public void trace(String msg, Object... arguments);
    
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
    public void trace(String msg, Throwable th);
    
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
    public void error(String msg);
    
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
    public void error(String msg, Object... arguments);
    
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
    public void error(String msg, Throwable th);
    
    /**
     * Checks if the trace is enabled for the logger
     */
    public boolean isTraceEnabled();
    
    /**
     * Checks if the info is enabled for the logger
     */
    public boolean isInfoEnabled();
    
    /**
     * Checks if the error is enabled for the logger
     */
    public boolean isErrorEnabled();
    
    /**
     * Checks if the warn is enabled for the logger
     */
    public boolean isWarnEnabled();
    
    /**
     * Checks if the debug is enabled for the logger
     */
    public boolean isDebugEnabled();
    
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
    public void log(Level level, String msg, Throwable th, Object... arguments);
    
   
    /**
	 * Log a audit event using audit logger at info level. 
	 * 
	 * @param msg
     *            The string message 
     * @param  arguments
     * 			The list of arguments            
	 */
    public void auditEvent(String msg, Object... arguments);
    
    /**
	  * Log a audit event using audit logger at given  level.
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., WARN
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
    public void auditEvent(Level level, String msg, Object... arguments);
    
  
    
    /**
     * Log a metrics event using metrics logger at info level.
     * 
     * @param msg
     * @param arguments
     */
    public void metricsEvent(String msg, Object... arguments);
    
    /**
	  * Log a metrics event using metrics logger at info level at given level.
	  * 
	  * @param level
	  *            One of the message level identifiers, e.g., WARN
	  * @param msg
	  *            The string message 
	  * @param  arguments
	  * 			The list of arguments            
	  */	
    
    public void metricsEvent(Level level, String msg, Object... arguments);
    
   
    
    
    /**
     * Log a security event using security logger at info level.
     * 
     * @param msg
     * @param arguments
     */
    public void securityEvent(String msg, Object... arguments);
    
    /**
     * Log a security event using security logger at given level.
     * 
     * @param level
     * @param msg
     * @param arguments
     */
    public void securityEvent(Level level, String msg, Object... arguments);
    
   
    
    /**
     * Log a performance event using performance logger at info level.
     * 
     * @param msg
     * @param arguments
     */
    public void performanceEvent(String msg, Object... arguments);
    
    /**
     * Log a performance event using performance logger at a given level.
     * 
     * @param level
     * @param msg
     * @param arguments
     */
    public void performanceEvent(Level level, String msg, Object... arguments);
    
    
    
    /**
     * Log an application event using application logger at info.
     * 
     * @param msg
     * @param arguments
     */
    public void applicationEvent(String msg, Object... arguments);
    
    /**
     * Log an application event using application logger at a given level.
     * 
     * @param level
     * @param msg
     * @param arguments
     */
    public void applicationEvent(Level level, String msg, Object... arguments);
    
   
	/**
	 * Log a server event using server logger at info level.
	 * 
	 * @param msg
	 * @param arguments
	 */
    public void serverEvent(String msg, Object... arguments);
    
    /**
     * Log a server event using server logger at a given level.
     * 
     * @param level
     * @param arguments
     */
    public void serverEvent(Level level, String msg, Object... arguments);
    
  
    
    /**
     * Log a policy event using policy logger at info level.
     * 
     * @param msg
     * @param arguments
     */
    public void policyEvent(String msg, Object... arguments);
    
    /**
     * Log a policy event using policy logger at a given level.
     * 
     * @param level
     * @param msg
     * @param arguments
     */
    public void policyEvent(Level level, String msg, Object... arguments);
    
    /**
     * Log a warn message based on message key as defined in resource bundle for the given locale 
     * along with exception.
     * 
     * @param locale
     * @param errorCode
     * @param th
     * @param args
     */
    public void warn(Locale locale,EELFResolvableErrorEnum errorCode,  Throwable th, String...  args);
    
    /**
     * Log a info message based on message key as defined in resource bundle for the given locale 
     * along with exception.
     * 
     * @param locale
     * @param errorCode
     * @param th
     * @param args
     */
    public void info(Locale locale, EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Log a debug message based on message key as defined in resource bundle for the given locale 
     * along with exception.
     * 
     * @param locale
     * @param errorCode
     * @param th
     * @param args
     */
    public void debug(Locale locale, EELFResolvableErrorEnum errorCode,  Throwable th, String...  args);
    
    /**
     * Log a error message based on message key as defined in resource bundle for the given locale 
     * along with exception.
     * 
     * @param locale
     * @param errorCode
     * @param th
     * @param args
     */
    public void error(Locale locale, EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Log a trace message based on message key as defined in resource bundle for the given locale 
     * along with exception.
     * 
     * @param locale
     * @param errorCode
     * @param th
     * @param args
     */
    public void trace(Locale locale,EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Log a warn message based on message key as defined in resource bundle for the given locale.
     * 
     * @param locale
     * @param errorCode
     * @param args
     */
    public void warn(Locale locale, EELFResolvableErrorEnum errorCode, String...  args);
    
    /**
     * Log a info message based on message key as defined in resource bundle for the given locale.
     * 
     * @param locale
     * @param errorCode
     * @param args
     */
    public void info(Locale locale, EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a debug message based on message key as defined in resource bundle for the given locale.
     * 
     * @param locale
     * @param errorCode
     * @param args
     */
    public void debug(Locale locale, EELFResolvableErrorEnum errorCode,   String...  args);
    
    /**
     * Log a error message based on message key as defined in resource bundle for the given locale.
     * 
     * @param locale
     * @param errorCode
     * @param args
     */
    public void error(Locale locale, EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a trace message based on message key as defined in resource bundle for the given locale.  
     * 
     * @param locale
     * @param errorCode
     * @param args
     */
    public void trace(Locale locale, EELFResolvableErrorEnum errorCode, String...  args);
    
    /**
     * Log a warn message based on message key as defined in resource bundle with arguments.
     * 
     * @param errorCode
     * @param args
     */
    public void warn(EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a info message based on message key as defined in resource bundle with arguments.
     * 
     * @param errorCode
     * @param args
     */
    public void info(EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a debug message based on message key as defined in resource bundle with arguments.
     * 
     * @param errorCode
     * @param args
     */
    public void debug(EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a error message based on message key as defined in resource bundle with arguments.
     * 
     * @param errorCode
     * @param args
     */
    
    public void error(EELFResolvableErrorEnum errorCode,  String...  args);
    
    /**
     * Log a trace message based on message key as defined in resource bundle with arguments.
     * 
     * @param errorCode
     * @param args
     */
    public void trace(EELFResolvableErrorEnum errorCode, String...  args);
 
    /**
     * Log a warn  message based on message key as defined in resource bundle along with exception.
     * 
     * @param errorCode
     * @param th
     * @param args
     */
    public void warn(EELFResolvableErrorEnum errorCode,  Throwable th, String...  args);
    
    
    /**
     * Log a info message based on message key as defined in resource bundle along with exception.
     * 
     * @param errorCode
     * @param th
     * @param args
     */
    public void info(EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Log a debug message based on message key as defined in resource bundle along with exception.
     * 
     * @param errorCode
     * @param th
     * @param args
     */
    public void debug(EELFResolvableErrorEnum errorCode,  Throwable th, String...  args);
    
    /**
     * Log a error message based on message key as defined in resource bundle along with exception.
     * 
     * @param errorCode
     * @param th
     * @param args
     */
    public void error(EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Log a trace message based on message key as defined in resource bundle along with exception.
     * 
     * @param errorCode
     * @param th
     * @param args
     */
    public void trace(EELFResolvableErrorEnum errorCode, Throwable th, String...  args);
    
    /**
     * Change the logging level for the logger 
     * 
     * @param level
     */
    public void setLevel(Level level);
    
    /**
     * Turn off the logging for the logger
     */
    public void disableLogging();
    
    
    
   }
