/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

/**
 * This class was previously the logback implementation of the
 * <code>EELFLogger</code> interface. That is now implemented in the more
 * appropriately named <code>LogbackEELFLogger</code>. It remains only for
 * backward compatibility with existing code and should no longer be used.
 */
@Deprecated
public class SLF4jWrapper extends LogbackEELFLogger {

	/**
	 * Create the wrapper around the SLF4J logger
	 *
	 * @param name
	 *            The SLF4J logger to be wrapped as a SLF4j logger
	 */
	public SLF4jWrapper(String name) {
		super(name);
	}
}
