/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.sample.application1;

import static com.att.eelf.configuration.Configuration.MDC_ALERT_SEVERITY;
import static com.att.eelf.configuration.Configuration.MDC_INSTANCE_UUID;
import static com.att.eelf.configuration.Configuration.MDC_KEY_REQUEST_ID;
import static com.att.eelf.configuration.Configuration.MDC_PARTNER_NAME;
import static com.att.eelf.configuration.Configuration.MDC_PROCESS_KEY;
import static com.att.eelf.configuration.Configuration.MDC_REMOTE_HOST;
import static com.att.eelf.configuration.Configuration.MDC_RESPONSE_CODE;
import static com.att.eelf.configuration.Configuration.MDC_RESPONSE_DESC;
import static com.att.eelf.configuration.Configuration.MDC_SERVER_FQDN;
import static com.att.eelf.configuration.Configuration.MDC_SERVER_IP_ADDRESS;
import static com.att.eelf.configuration.Configuration.MDC_SERVICE_INSTANCE_ID;
import static com.att.eelf.configuration.Configuration.MDC_SERVICE_NAME;
import static com.att.eelf.configuration.Configuration.MDC_STATUS_CODE;
import static com.att.eelf.configuration.Configuration.MDC_TARGET_ENTITY;
import static com.att.eelf.configuration.Configuration.MDC_TARGET_SERVICE_NAME;
import static com.att.eelf.configuration.Configuration.MDC_TARGET_VIRTUAL_ENTITY;

import java.net.InetAddress;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.MDC;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.exception.EELFException;
import com.att.eelf.i18n.EELFResolvableErrorEnum;
import com.att.eelf.i18n.EELFResourceManager;
import com.att.eelf.sample.ApplicationMsgs;
import com.att.eelf.sample.exception.MySampleException1;
import com.att.eelf.sample.exception.MySampleException2;
import com.att.eelf.utils.Stopwatch;

/**
 * This class is a simple demonstration of how to use the EELF framework effectively.
 * <p>
 * The EELF framework provides a mechanism to load and format internationalized messages from multiple resource bundles,
 * thus allowing for multiple language support as well as generalized message formatting. The formatting allows for the
 * insertion of string values into the message text by using the position of the argument in the argument list. The
 * formatting of the insertion can be controlled from the message resource and not require any code changes. Since the
 * mechanism used to format the messages is the standard java class {@link java.text.MessageFormat} the user should be
 * familiar with it's capabilities.
 * </p>
 * <p>
 * Formatting of messages is performed by scanning the message skeleton for data insertion points, each identified by a
 * number that corresponds to the variable argument list. For example, {0} represents the first data argument to the
 * method call (the variable arguments array is zero-based). The reason the inserts are identified by the position of
 * the argument in the method call is because some languages may need to format the message with the inserts in
 * different locations in the message.
 * </p>
 * <p>
 * An example message skeleton may look something like:
 * </p>
 * 
 * <pre>
 *   Unexpected exception attempting to detach device {0}, volume definition {1}, from server {2} in stack {3}
 * </pre>
 * <p>
 * The message skeletons are loaded from property files which constitute the resource bundle that has been loaded. Each
 * bundle is identified by a base name. Different locale-specific resource files use the same base name plus a language
 * and optionally country code as part of the file name. This allows the appropriate resource file to be searched based
 * on the locale used when the message is requested.
 * </p>
 * <p>
 * For example, assume the base name was "Messages" and there is support for US English, UK English, and German, with
 * the default language being US English. To set that up, you would create three property files, each containing the
 * message text appropriate to the specific language. This would result in three files, something like:
 * </p>
 * <dl>
 * <dt>Messages.properties</dt>
 * <dd>This is the "default" resource file searched if none exist that match the requested locale</dd>
 * <dt>Messages_en-UK</dt>
 * <dd>This message resource file is searched when the locale is Great Britain.</dd>
 * <dt>Messages_de</dt>
 * <dd>This message resource is used if the current locale specifies German as the language. Since no country code is
 * present, this resource file would be used for ANY country locale that specifies the German language.</dd>
 * </dl>
 * <p>
 * When a message is requested, the locale is examined to determine which resource file to use to obtain the message
 * skeleton. If no resource file exists that matches the provided locale, then the default resource file (the one
 * without any language or country code suffix) is used. This means that all of the messages are present in all of the
 * resource files, each translated to the appropriate text for that specific language and/or country. It also means that
 * any specific message identity be the same for the same message in all of the files.
 * </p>
 * <p>
 * To make the use of EELF easier, the message identifiers (keys) are defined using a simple enumeration. The
 * application would create their own enumeration and create message identifiers within it. These identifiers are then
 * used as the key of the message skeleton in each resource file that makes up the resource bundle.
 * </p>
 * <p>
 * EELF also allows for the creation of any logger that you want, and it is possible to use the fully qualified class
 * name as the logger (as per many log4j examples). However, in an application where it may be necessary to isolate
 * different logging events because of security, audit, or other concerns, this is not a good approach to use. The
 * reason is that a specific class may emit both a security message and a performance related message (for example). If
 * the "class" logger example is used, both messages would go to the same logger and could not be individually managed.
 * </p>
 * <p>
 * Another approach often used in COTS applications is to assign loggers to "functionality". For example, there may be
 * an application, security, audit, and performance logger. There could be others as well, or fewer depending on the
 * applications actual needs. The EELF framework also supports the use of functional logging.
 * </p>
 * <p>
 * To use EELF, an application would perform the following basic operations:
 * </p>
 * <ol>
 * <li>Create the message enumeration which must extend {@link EELFResolvableErrorEnum}. The application enumerated
 * values are also the keys into the resource bundle(s) that get loaded.</li>
 * <li>Create a resource bundle or bundles per language that needs to be supported. It is better to have one resource
 * file in the bundle that uses only the base name to identify it, as this will be the default bundle if a resource in a
 * language that has not been provided is requested. Usually, the default resource file will be english.</li>
 * <li>Add a static initializer in the enumeration created above to call the {@link EELFResourceManager} to load the
 * bundle(s) that were created.</li>
 * </ol>
 * 
 */
public class HelloWorld {

    /*
     * The following loggers are examples of "functional" logging. These same loggers would be used by multiple classes
     * throughout the application. This ensures that any logging event that is considered a security event is presented
     * to the same logger regardless of the class that issued it.
     */
    private static EELFLogger applicationLogger = EELFManager.getInstance().getApplicationLogger();

    private static EELFLogger securityLogger = EELFManager.getInstance().getSecurityLogger();

    private static EELFLogger auditLogger = EELFManager.getInstance().getAuditLogger();

    private static EELFLogger metricsLogger = EELFManager.getInstance().getMetricsLogger();

    private static EELFLogger serverLogger = EELFManager.getInstance().getServerLogger();

    private static EELFLogger performanceLogger = EELFManager.getInstance().getPerformanceLogger();

    private static EELFLogger policyLogger = EELFManager.getInstance().getPolicyLogger();

    /*
     * The following two loggers are examples of creating the "typical" class level logger. This is maintained for
     * compatibility with log4j and slf4j examples and widespread usage. However, it is believed that functional logging
     * is better for use in applications.
     */
    private static EELFLogger classlogger = EELFManager.getInstance().getLogger(HelloWorld.class);

    private static EELFLogger mylogger = EELFManager.getInstance().getLogger("com.att.app");

    /**
     * This method demonstrates several ways that messages can be generated using EELF
     */
    public void applicationLoggerMessages() {

        /*
         * The primary mechanism suggested is to load all messages as skeletons from a resource bundle and to use
         * message formatting to generate the correct message. The next three lines of code demonstrate how to do that.
         */

        /*
         * The first call looks up the message skeleton for the key MESSAGE_SAMPLE_ONEARGUMENT from the bundle using the
         * current system default locale. If the default locale is en_US, then the resource file for that language and
         * country is used to find the message. The argument "Hello" is then inserted into the message skeleton at the
         * {0} insertion point.
         */
        applicationLogger.error(ApplicationMsgs.MESSAGE_SAMPLE_ONEARGUMENT, "Hello");

        /*
         * This call provides a specific locale to be used, as well as the message key (MESSAGE_SAMPLE_TWOARGUMENTS) and
         * two inserted argument values; "Hello", "Stranger" are inserted as {0} and {1} respectively. The provision of
         * the locale allows the code to switch to any locale dynamically without affecting the system default locale.
         * This may be needed to support web clients where each user may be operating in a different locale.
         */
        applicationLogger.info(Locale.GERMAN, ApplicationMsgs.MESSAGE_SAMPLE_TWOARGUMENTS, "Hello", "Stranger");

        /*
         * It is recommended to cache and reuse locales. The "Locale" class provides the ability to do this, causing the
         * creation of a locale only the first time it is referenced and then reusing the same locale object repeatedly.
         * This is done automatically for you when you use the static method "forLanguageTag", but you have to format
         * the language code as the two character language, dash, and two character country. The Locale class also has
         * some pre-defined static constants that you can reference, such as Locale.GERMAN above.
         */

        /*
         * This call also demonstrates formatting a message with no inserts. That is also perfectly legal, a message
         * skeleton may not have any variable data to insert into the message.
         */
        applicationLogger.info(Locale.forLanguageTag("es-ES"), ApplicationMsgs.MESSAGE_SAMPLE_NOARGS);

        /*
         * The next line are representative of using the EELF framework where the message text is NOT loaded
         * from a resource bundle, but rather provided inline as part of the call argument list. This is not
         * recommended, but it is supported for backwards compatibility. Also, the lack of ordinal numbers in the
         * in-line message skeleton for inserts is done to preserve backward compatibility with other logging
         * frameworks. This approach is NOT internationalized or localized, and the message text can't be easily changed
         * to support branding or repackaging, and should not be used for applications that may require these
         * capabilities.
         */
        applicationLogger.error("This is {} error message with args {}", "dummy", "args1");
        
        /*
         * Message formatter in EELFResourceManager is invoked to generate the correct message. The same formatted   
         * message can be utilized for writing to log and/or to perform other operations on it like save it a datastore 
         * uor se it in some response to be sent outside this system. Both the message/error number in this
         * application log and that handed off to another entity will match
         */
        String warningMsg=EELFResourceManager.format(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
        applicationLogger.warn(warningMsg);

        try {
            throw new Exception("test exception");
        } catch (Exception e) {
        	applicationLogger.error(ApplicationMsgs.MESSAGE_SAMPLE_EXCEPTION, e);
        }
    }

    /**
     * This method demonstrates writing messages to the "performance" logger. This may be configured to separate
     * performance timings from all other logging if desired.
     */
    public void performanceLoggerMessages() {

        performanceLogger.error(ApplicationMsgs.MESSAGE_SAMPLE_ONEARGUMENT, "Hello");
        performanceLogger.error("This is dummy error message");

        try {
            throw new Exception("test exception");
        } catch (Exception e) {
        	performanceLogger.error(ApplicationMsgs.MESSAGE_SAMPLE_EXCEPTION_ONEARGUMENT, e, "testPerformanceLogger");
        }
    }

    /**
     * This method demonstrates writing messages to the security logger. Typically, the security logger would be
     * configured to write messages to a separately controlled, and secured, log file.
     */
    public void securityLoggerMessages() {
        securityLogger.warn(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
    }

    public void serverLoggerMessages() {
        serverLogger.warn(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
    }

    public void auditLoggerMessages() {
    	Stopwatch.start();
    	try {
    		Thread.sleep(10000);
    	} catch (InterruptedException e) {
    		
    	}
    	Stopwatch.stop();
        auditLogger.warn(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
    }

    public void policyLoggerMessages() {
        policyLogger.info(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
    }

    public void metricsLoggerMessages() {
    	Stopwatch.start();
    	try {
    		Thread.sleep(10000);
    	} catch (InterruptedException e) {
    		
    	}
    	Stopwatch.stop();
        metricsLogger.info(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
    }

    public void myLoggerMessages() {
        mylogger.info("this is the generic application message - mylooger");
    }

    public void classLoggerMessages() {
        classlogger.info(ApplicationMsgs.MESSAGE_SAMPLE_ONEARGUMENT, "Hello");
        classlogger.debug("debug this is the class logger message warn");
        classlogger.warn(ApplicationMsgs.MESSAGE_WARNING_SAMPLE);
        classlogger.trace(ApplicationMsgs.MESSAGE_TRACE_SAMPLE);
        classlogger.error(ApplicationMsgs.MESSAGE_ERROR_SAMPLE);
        mylogger.disableLogging();
    }

    public void exceptionHierarchyLogging() {
        try {
            generateNPE(null);
        } catch (Exception rootException) {
            try {
                wrapThisException(rootException);
            } catch (MySampleException1 firstWrappedException) {
                try {
                    wrapExceptionAgain(firstWrappedException);
                } catch (MySampleException2 secondWrappedException) {
                    logError(secondWrappedException);
                }
            }

        }
    }

    public void wrapThisException(Exception ee) throws MySampleException1 {
        throw new MySampleException1(ee);
    }

    public void wrapExceptionAgain(Exception ee) throws MySampleException2 {
        throw new MySampleException2(ee);
    }

    public void logError(EELFException ee) {
        applicationLogger.error(ApplicationMsgs.MESSAGE_SAMPLE_EXCEPTION, ee);
    }

    public void generateNPE(String test) {
        test.length();
    }

    /**
     * This main method invokes all of the loggers to demonstrate the operation of EELF.
     * <p>
     * This method demonstrates the use of the MDC (Mapped Diagnostic Context). An MDC context is a hash map that allows
     * the caller to set properties (name-value pairs) that are specific to the current thread and will remain in scope
     * until they are cleared or changed. The benefit of the MDC is that it is also available to the logging framework,
     * and properties from the MDC may be extracted and automatically inserted into the log message.
     * </p>
     * <p>
     * This separation of contextual information from arguments passed to the message formatter means that in all of the
     * places in the application where messages are generated, the code does not have to track the context. The MDC does
     * that for you by being created and maintained in thread-local storage. Each thread of an application has its own
     * MDC, and once set, that thread-specific MDC is available to all classes and all methods that are processing the
     * specific service.
     * </p>
     * <p>
     * The access to the MDC properties is defined in the log4j, slf4j, or logback formatter definitions for the
     * specific appender(s) associated with the logger. This means that by using this facility, any property placed into
     * the MDC can be inserted into any message. It also means that all messages generated by the application,
     * regardless of the method or class, but processing on the same thread (presumably processing the same request)
     * would have the same MDC.
     * <li>At the start of a API call or a transaction,invoke Stopwatch.start() and invoke Stopwatch.stop() once the transaction 
     * or API call completes. This is required for any log statements written to audit or metrics logs that is used to time events 
     * to determine their duration. The stop watch allows for the same types of operations as a real stop watch, that is, it allows 
     * the timing to be stopped, started, cleared, and read. The accumulated time is the total of all times between start and stop 
     * events. The watch can be repeatedly stopped and restarted, and will accumulate all durations between start/stop pairs. 
     * </li>
     * </p>
     * 
     * @param args
     *            Unused, ignored
     */
    public static void main(String[] args) {
    	MDC.clear();
        MDC.put(MDC_KEY_REQUEST_ID, "g5nzehdoe8dx10zaablv8vpzb"); // eg. servletRequest.getSession().getId()

        MDC.put(MDC_REMOTE_HOST, "1.1.1.1");
        MDC.put(MDC_SERVICE_NAME, "TestService");
        MDC.put(MDC_SERVICE_INSTANCE_ID, "");
        try {
            MDC.put(MDC_SERVER_FQDN, InetAddress.getLocalHost().getHostName());
            MDC.put(MDC_SERVER_IP_ADDRESS, InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MDC.put(MDC_INSTANCE_UUID, UUID.randomUUID().toString());
        MDC.put(MDC_ALERT_SEVERITY, "0");
        
        MDC.put(MDC_PARTNER_NAME, "m12345");
        MDC.put(MDC_STATUS_CODE, "COMPLETE");
        MDC.put(MDC_RESPONSE_CODE, "200-OK");
        MDC.put(MDC_RESPONSE_DESC, "Request Completed Successfully");
        MDC.put(MDC_PROCESS_KEY, "ProcessKey");
        MDC.put(MDC_TARGET_ENTITY, "MSO");
        MDC.put(MDC_TARGET_SERVICE_NAME, "MSO-GET API");
        MDC.put(MDC_TARGET_VIRTUAL_ENTITY, "VM");
              
        HelloWorld hw = new HelloWorld();

        // Invoke all the different methods to see how the logger works
        hw.applicationLoggerMessages();
        hw.auditLoggerMessages();
        hw.classLoggerMessages();
        hw.exceptionHierarchyLogging();
        hw.metricsLoggerMessages();
        hw.myLoggerMessages();
        hw.performanceLoggerMessages();
        hw.policyLoggerMessages();
        hw.securityLoggerMessages();
        hw.serverLoggerMessages();
    	
    }
}
