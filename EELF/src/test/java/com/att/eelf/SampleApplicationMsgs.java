/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf;

import com.att.eelf.i18n.EELFResolvableResourceEnum;
import com.att.eelf.i18n.EELFResourceManager;


public enum SampleApplicationMsgs implements EELFResolvableResourceEnum {

    /**
     * Application initialization started at {0}
     */
	MESSAGE_CONFIGURATION_STARTED,

    /**
     * Prior configuration has been cleared
     */
	MESSAGE_CONFIGURATION_CLEARED,
   

    /**
     * User {0} not found.
     */
  
    ERROR_USER_NOT_FOUND,

	/**
     * Service {0} is not running
     */
    ERROR_SERVICE_NOT_RUNNING,
    
    /**
     * Sample warn msg
     */
    WARN_MSG,
	
	
	/**
     * Sample debug msg
     */
    DEBUG_MSG,
    
    /**
     * Sample trace msg
     */
    TRACE_MSG,
    
    
	
	/**
     * Sample error exception
     */
    MY_SAMPLE_EXCEPTION;
    
	
	
    /**
     * Static initializer to ensure the resource bundles for this class are loaded...
     */
    static {
        EELFResourceManager.loadMessageBundle("com/att/app/app1/Resources");
        EELFResourceManager.loadMessageBundle("com/att/app/app2/Resources");
        EELFResourceManager.loadMessageBundle("com/att/app/app3/Resources");
       
        
    }
    

      
}
