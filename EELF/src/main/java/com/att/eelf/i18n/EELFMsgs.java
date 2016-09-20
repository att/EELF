/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.i18n;


import com.att.eelf.i18n.EELFResolvableErrorEnum;
import com.att.eelf.i18n.EELFResourceManager;


public enum EELFMsgs implements EELFResolvableErrorEnum {

	 /**
     * Loading default logging configuration from system resource file "{0}"
     */
	LOADING_DEFAULT_LOG_CONFIGURATION,

	 /**
     * No log configuration could be found or defaulted!
     */
    NO_LOG_CONFIGURATION,
    
    /**
     * Logging has already been initialized, check the container logging definitions to ensure they represent your
     * desired logging configuration.
     */
    LOGGING_ALREADY_INITIALIZED,
    
    /**
     * Searching path "{0}" for log configuration file "{1}"
     */
    SEARCHING_LOG_CONFIGURATION,
    
    /**
     * Loading logging configuration from file "{0}"
     */
    LOADING_LOG_CONFIGURATION,
    
  
    /**
     * An unsupported logging framework is bound to SLF4J. 
     */
    UNSUPPORTED_LOGGING_FRAMEWORK;
    
	
	
    /**
     * Static initializer to ensure the resource bundles for this class are loaded...
     */
    static {
        EELFResourceManager.loadMessageBundle("com/att/eelf/Resources");
   }
    

      
}
