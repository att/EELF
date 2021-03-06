/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.exception;

public class EELFException extends Exception {
	/**
     * The serial version number for this class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create the exception, detailing the reason with a message
     *
     * @param message
     *            The message that details the reason for the exception
     */
    public EELFException(String message) {
        super(message);
    }

    /**
     * Create an exception by wrapping another exception
     *
     * @param message
     *            The message that details the exception
     * @param cause
     *            Any exception that was caught and was the reason for this failure
     */
    public EELFException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create the exception by wrapping another exception
     *
     * @param cause
     *            The cause of this exception
     */
    public EELFException(Throwable cause) {
        super(cause);
    }

}
