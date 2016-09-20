/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;


import com.att.eelf.i18n.EELFMsgs;
import com.att.eelf.i18n.EELFResourceManager;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import static com.att.eelf.configuration.Configuration.*;

/**
 * This is a singleton class used to obtain a named Logger instance.
 * The EELFManager object can be retrieved using EELFManager.getInstance(). It is created during class initialization and cannot subsequently be changed.
 * At startup the EELFManager loads the logging configuration file.
 * If no external logging configuration file is found, it will load the default logging configuration available at com/att/eelf/logback.xml
 */

public final class EELFManager {
	
	/**
     * This is a string constant for the comma character. It's intended to be used a common string delimiter.
     */
    private static final String COMMA = ",";
	
	/**
     * The logger to be used to record general application log events
     */
    private EELFLogger applicationLogger;

    /**
     * The logger to be used to record audit events
     */
    private EELFLogger auditLogger;

    /**
     * The logger to be used to record metric events
     */
    private EELFLogger metricsLogger;

   
    /**
     * The logger to be used to record performance events
     */
    private EELFLogger performanceLogger;

    /**
     * The logger to be used to record policy manager application events
     */
    private EELFLogger policyLogger;
	
    /**
     * The logger to be used to record security events
     */
	private EELFLogger securityLogger;
    
    /**
     * The logger to be used to record server events
     */
    private EELFLogger serverLogger;
    
    /**
     * The logger to be used to record error only
     */
    private EELFLogger errorLogger;
    
    /**
     * The logger to be used to record debug logs
     */
    private EELFLogger debugLogger;
	
    /**
     * Cache of all other loggers used in application
     */
	private Map<String,EELFLogger> loggerCache = new ConcurrentHashMap<String,EELFLogger>();
	
	/**
     * This lock is used to serialize access to create the loggers
     */
    private Object loggerLock = new Object();
    
    /**
     * This lock is used to serialize access to create the loggers
     */
    private static final EELFManager logManager = new EELFManager();
    
    private EELFManager() {
    	 ArrayList<String> delayedLogging = new ArrayList<String>();
    	 /*
         * Now, we are ready to initialize logging. Check to see if logging has already been initialized and that the
         * application logger exists already. If it does, then skip the logging configuration because it was already set
         * up in the container that is calling us. If not, then we need to set it up.
         */
        ILoggerFactory factory = LoggerFactory.getILoggerFactory();
        if (factory instanceof LoggerContext) {
        	LoggerContext loggerContext = (LoggerContext) factory;
            if (loggerContext.exists(GENERAL_LOGGER_NAME) == null) {
                initializeLogging(delayedLogging);
            } else {
            	delayedLogging.add(EELFResourceManager.getMessage(EELFMsgs.LOGGING_ALREADY_INITIALIZED));             
            }
        }
        
        /*
         * Copy all delayed logging messages to the logger
         */
        for (String message : delayedLogging) {
            // All messages are prefixed with a message code of the form EELF####S
            // Where:
            // EELF --- is the product code
            // #### -- Is the message number
            // S ----- Is the severity code (I=INFO, D=DEBUG, W=WARN, E=ERROR)
            char severity = message.charAt(8);
            switch (severity) {
                case 'D':
                	getApplicationLogger().debug(message);
                    break;
                case 'I':
                	getApplicationLogger().info(message);
                    break;
                case 'W':
                	getApplicationLogger().warn(message);
                    break;
                case 'E':
                	getApplicationLogger().error(message);
            }
        }
        
        delayedLogging.clear();
    	
    }
    
    /**
     * Initialize the logging environment, record all logging messages to the provided list for delayed processing.
     *
     * @param delayedLogging
     *            The list to record logging messages to for delayed processing after the logging environment is
     *            created.
     */
    private static void initializeLogging(final ArrayList<String> delayedLogging) {
    	
    	 /*
         * See if we can find logback-test.xml first, unless a specific file has been provided
         */
        String filename = System.getProperty(PROPERTY_LOGGING_FILE_NAME, "logback-test.xml");
       
        String path = System.getProperty(PROPERTY_LOGGING_FILE_PATH,  "${user.home};etc;../etc");
        
        String msg = EELFResourceManager.format(EELFMsgs.SEARCHING_LOG_CONFIGURATION,path, filename);  
        delayedLogging.add(msg);

        if (scanAndLoadLoggingConfiguration(path, filename, delayedLogging)) {
            return;
        }

        /*
         * If the first attempt was for logback-test.xml and it failed to find it, look again for logback.xml
         */
        if (filename.equals("logback-test.xml")) {
            filename = System.getProperty(PROPERTY_LOGGING_FILE_NAME, "logback.xml");
          
            if (scanAndLoadLoggingConfiguration(path, filename, delayedLogging)) {
                return;
            }
        }
       

        /*
         * If we reach here, then no external logging configurations were defined or found. In that case, we need to
         * initialize the logging framework from hard-coded default values we load from resources.
         */
        InputStream stream = EELFManager.class.getClassLoader().getResourceAsStream("com/att/eelf/logback.xml");
        try {
            if (stream != null) {
                delayedLogging.add(EELFResourceManager.getMessage(EELFMsgs.LOADING_DEFAULT_LOG_CONFIGURATION,"com/att/eelf/logback.xml"));
                loadLoggingConfiguration(stream, delayedLogging);
            } else {
            	 delayedLogging.add(EELFResourceManager.format(EELFMsgs.NO_LOG_CONFIGURATION));               
            	 }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // not much we can do since logger may not be configured yet
                    e.printStackTrace(System.out);
                }
            }
        }
        
    }
    

    /**
     * Loads the logging configuration from the specified stream.
     *
     * @param stream
     *            The stream that contains the logging configuration document.
     * @param delayedLogging
     */
    private static void loadLoggingConfiguration(final InputStream stream, final ArrayList<String> delayedLogging) {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory instanceof LoggerContext) {
            configureLogback((LoggerContext) loggerFactory, stream);
        } else {
        	 delayedLogging.add((EELFResourceManager.format(EELFMsgs.UNSUPPORTED_LOGGING_FRAMEWORK)));        	
        }
        
    }


    
    /**
     * @param loggerFactory
     *            the logger factory context
     * @param stream
     *            The input stream to be configured
     */
    private static void configureLogback(final LoggerContext context, final InputStream stream) {
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        try {
            configurator.doConfigure(stream);
        } catch (JoranException e) {
            // not much we can do since logger may not be configured yet
            e.printStackTrace(System.out);
        }

       
    }
    
    
    /**
     * This method scans a set of directories specified by the path for an occurrence of a file of the specified
     * filename, and when found, loads that file as a logging configuration file.
     *
     * @param path
     *            The path to be scanned. This can be one or more directories, separated by the platform specific path
     *            separator character.
     * @param filename
     *            The file name to be located. The file name examined within each element of the path for the first
     *            occurrence of the file that exists and which can be read and processed.
     * @param delayedLogging
     * @return True if a file was found and loaded, false if no files were found, or none were readable.
     */
    private static boolean scanAndLoadLoggingConfiguration(final String path, final String filename,
        final ArrayList<String> delayedLogging) {
        String[] pathElements = path.split(COMMA);
        for (String pathElement : pathElements) {
            File file = new File(pathElement, filename);
            if (file.exists() && file.canRead() && !file.isDirectory()) {
                String msg = EELFResourceManager.getMessage(EELFMsgs.LOADING_LOG_CONFIGURATION,file.getAbsolutePath());	                
                delayedLogging.add(msg);

                BufferedInputStream stream = null;
                try {
                    stream = new BufferedInputStream(new FileInputStream(file));
                    delayedLogging.add(String.format("EELF000I Loading logging configuration from %s",
                        file.getAbsolutePath()));
                     loadLoggingConfiguration(stream, delayedLogging);
                } catch (FileNotFoundException e) {
                    delayedLogging.add(EELFResourceManager.format(e));
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            // not much we can do since logger may not be configured yet
                            e.printStackTrace(System.out);
                        }
                    }
                }

                return true;
            }
        }
        return false;
    }

    
    /**
     * This method is used to obtain the EELFManager (as well as set it up if not already).
     *
     * @return The EELFManager object 
     */
    public static EELFManager getInstance() {
    		
    	return logManager;
    }
    
    /**
	 * Returns the logger associated with the name 
	 * @return EELFLogger
	 */
	public EELFLogger getLogger(String name) {
		synchronized (loggerLock) {
			if (!loggerCache.containsKey(name)) {
				loggerCache.put(name,new SLF4jWrapper(name));
			}	
		}	
		return loggerCache.get(name); 
		 
	 }
	
	    /**
     * Returns the logger associated with the clazz
     * 
     * @param clazz
     *            The class that we are obtaining the logger for
     * @return EELFLogger The logger
     */
    public EELFLogger getLogger(Class<?> clazz) {
		synchronized (loggerLock) {
			if (!loggerCache.containsKey(clazz.getName())) {
				loggerCache.put(clazz.getName(), new SLF4jWrapper(clazz.getName()));
			}	
		}	
		return loggerCache.get(clazz.getName());
			 
	}
	
	/**
	 * Returns the  application logger 
	 * @return EELFLogger
	 */
	public EELFLogger getApplicationLogger() {
		 synchronized (loggerLock) {
	          if (applicationLogger == null) {
	        	   applicationLogger = new SLF4jWrapper(GENERAL_LOGGER_NAME);
	          }
	      }
		return applicationLogger; 
	}
	
	/**
	 * Returns the  metrics logger
	 * @return EELFLogger
	 */
	public EELFLogger getMetricsLogger() {
		synchronized (loggerLock) {
	          if (metricsLogger == null) {
	        	  metricsLogger = new SLF4jWrapper(METRICS_LOGGER_NAME);
	          }
	      }
		return metricsLogger; 
	}

	  
	/**
	 * Returns the  audit logger 
	 * @return EELFLogger
	 */
	public EELFLogger getAuditLogger() {
		synchronized (loggerLock) {
	          if (auditLogger == null) {
	        	  auditLogger = new SLF4jWrapper(AUDIT_LOGGER_NAME);
	          }
	      }
		return auditLogger; 
	 }

	/**
	 * Returns the  performance logger 
	 * @return EELFLogger
	 */
	public EELFLogger getPerformanceLogger() {
		synchronized (loggerLock) {
	          if (performanceLogger == null) {
	        	  performanceLogger = new SLF4jWrapper(PERF_LOGGER_NAME);
	          }
	      }
		return performanceLogger; 
	}	
	  
	  /**
	   * Returns the  server logger 
	   * @return EELFLogger
	   */
	public EELFLogger getServerLogger() {
		synchronized (loggerLock) {
	          if (serverLogger == null) {
	        	  serverLogger = new SLF4jWrapper(SERVER_LOGGER_NAME);
	          }
	      }
		return serverLogger; 
	}	
    
	  
	  /**
	   * Returns the  security logger 
	   * @return EELFLogger
	   */
	public  EELFLogger getSecurityLogger() {
		synchronized (loggerLock) {
	          if (securityLogger == null) {
	        	  securityLogger = new SLF4jWrapper(SECURITY_LOGGER_NAME);
	          }
	      }
		return securityLogger; 
	}	
	  
	/**
	  * Returns the policy logger 
	  * @return EELFLogger
	  */
	public EELFLogger getPolicyLogger() {
		synchronized (loggerLock) {
	          if (policyLogger == null) {
	        	  policyLogger = new SLF4jWrapper(POLICY_LOGGER_NAME);
	          }
	      }
		return policyLogger; 
	}
	  
	
	/**
	 * Returns the  error logger 
	 * @return EELFLogger
	 */
	public EELFLogger getErrorLogger() {
		 synchronized (loggerLock) {
	          if (errorLogger == null) {
	        	  errorLogger = new SLF4jWrapper(ERROR_LOGGER_NAME);
	          }
	      }
		return errorLogger; 
	}
		
	/**
	 * Returns the  error logger 
	 * @return EELFLogger
	 */
	public EELFLogger getDebugLogger() {
		 synchronized (loggerLock) {
	          if (debugLogger == null) {
	        	  debugLogger = new SLF4jWrapper(DEBUG_LOGGER_NAME);
	          }
	      }
		return debugLogger; 
	}
	
}
