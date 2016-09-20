/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.exception;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEELFException {
	
	private String message = "Just a test EELF message";

    @Test
    public void testEELFExceptionString() {
    	EELFException e = new EELFException(message);
        assertTrue(e.getMessage().equals(message));
    }

    @Test
    public void testEELFExceptionStringThrowable() {
        String CAUSE = "test cause";
        Throwable t = new Throwable(CAUSE);
        EELFException e = new EELFException(message, t);
        assertTrue(e.getCause().getMessage().equals(CAUSE));
    }

    @Test
    public void testEELFExceptionThrowable() {
        String CAUSE = "test cause";
        Throwable t = new Throwable(CAUSE);
        EELFException e = new EELFException(t);
        assertTrue(e.getCause().getMessage().equals(CAUSE));
    }


}
