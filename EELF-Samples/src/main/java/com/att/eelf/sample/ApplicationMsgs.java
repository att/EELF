/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.sample;

import com.att.eelf.i18n.EELFResolvableErrorEnum;
import com.att.eelf.i18n.EELFResourceManager;

public enum ApplicationMsgs implements EELFResolvableErrorEnum {
	
	 /**
     * Application message which requires no arguments
     */
	MESSAGE_SAMPLE_NOARGS,
	
	 /**
     * Application message which requires one argument {0}
     */
	MESSAGE_SAMPLE_ONEARGUMENT,

    /**
     * Application message which requires two argument {0} and another argument {1}
     */
	MESSAGE_SAMPLE_TWOARGUMENTS,
	
	/**
     * Sample error exception
     */
    MESSAGE_SAMPLE_EXCEPTION,
	
	/**
     * Sample warning message
     */
	MESSAGE_WARNING_SAMPLE,
	
	/**
     * Sample exception in method {0}
     */
	MESSAGE_SAMPLE_EXCEPTION_ONEARGUMENT,
	
	/**
     * Sample trace message
     */
	MESSAGE_TRACE_SAMPLE,
	
	/**
     * Sample error message
     */
	MESSAGE_ERROR_SAMPLE;
	
    
    /**
     * Static initializer to ensure the resource bundles for this class are loaded...
     * Here this application loads messages from three bundles
     */
    static {
        EELFResourceManager.loadMessageBundle("com/att/sample/common/GenericMessages");
        EELFResourceManager.loadMessageBundle("com/att/sample/application1/App1Messages");
        EELFResourceManager.loadMessageBundle("com/att/sample/application2/App2Resources");
    }
}
