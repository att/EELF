/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import static com.att.eelf.configuration.Configuration.PROPERTY_LOGGING_FILE_NAME;
import static com.att.eelf.configuration.Configuration.PROPERTY_LOGGING_FILE_PATH;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import com.att.eelf.i18n.EELFMsgs;
import com.att.eelf.i18n.EELFResolvableResourceEnum;
import com.att.eelf.i18n.EELFResourceManager;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

class LogbackConfigHelper {

	private static final String DEFAULT_CONFIG_PATH = "com/att/eelf/logback.xml";

	/**
	 * This is a string constant for the comma character. It's intended to be
	 * used a common string delimiter.
	 */
	private static final String COMMA = ",";

	/**
	 * Any log entries generated during init will be stored here so they can be
	 * fetched later and logged properly when initialization is complete.
	 */
	private final List<String> messages = new ArrayList<String>();

	/**
	 * Attempt to initialize the logging environment. Any generated messages can
	 * be fetched via getMessages().
	 */
	public void initialize() {
		messages.clear();

		/*
		 * See if we can find logback-test.xml first, unless a specific file has
		 * been provided
		 */
		final String path = System.getProperty(PROPERTY_LOGGING_FILE_PATH,
				"${user.home};etc;../etc");

		String filename = System.getProperty(PROPERTY_LOGGING_FILE_NAME, "logback-test.xml");

		addMessage(EELFMsgs.SEARCHING_LOG_CONFIGURATION, path, filename);

		if (scanAndLoadLoggingConfiguration(path, filename)) {
			return;
		}

		/*
		 * If the first attempt was for logback-test.xml and it failed to find
		 * it, look again for logback.xml
		 */
		if (filename.equals("logback-test.xml")) {
			filename = System.getProperty(PROPERTY_LOGGING_FILE_NAME, "logback.xml");

			if (scanAndLoadLoggingConfiguration(path, filename)) {
				return;
			}
		}

		/*
		 * If we reach here, then no external logging configurations were
		 * defined or found. In that case, we need to initialize the logging
		 * framework from hard-coded default values we load from resources.
		 */
		InputStream stream = EELFManager.class.getClassLoader()
				.getResourceAsStream(DEFAULT_CONFIG_PATH);
		try {
			if (stream != null) {
				addMessage(EELFMsgs.LOADING_DEFAULT_LOG_CONFIGURATION, DEFAULT_CONFIG_PATH);
				loadLoggingConfiguration(stream);
			} else {
				addMessage(EELFMsgs.NO_LOG_CONFIGURATION);
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

	public List<String> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	private void addMessage(String format, Object... arguments) {
		messages.add(String.format(format, arguments));
	}

	private void addMessage(EELFResolvableResourceEnum resource) {
		messages.add(EELFResourceManager.format(resource));
	}

	private void addMessage(EELFResolvableResourceEnum resource, String... arguments) {
		messages.add(EELFResourceManager.format(resource, arguments));
	}

	/**
	 * Loads the logging configuration from the specified stream.
	 *
	 * @param stream
	 *            The stream that contains the logging configuration document.
	 * @param delayedLogging
	 */
	private void loadLoggingConfiguration(final InputStream stream) {
		ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
		if (loggerFactory instanceof LoggerContext) {
			configureLogback((LoggerContext) loggerFactory, stream);
		} else {
			addMessage(EELFMsgs.UNSUPPORTED_LOGGING_FRAMEWORK);
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
	 * This method scans a set of directories specified by the path for an
	 * occurrence of a file of the specified filename, and when found, loads
	 * that file as a logging configuration file.
	 *
	 * @param path
	 *            The path to be scanned. This can be one or more directories,
	 *            separated by the platform specific path separator character.
	 * @param filename
	 *            The file name to be located. The file name examined within
	 *            each element of the path for the first occurrence of the file
	 *            that exists and which can be read and processed.
	 * @param delayedLogging
	 * @return True if a file was found and loaded, false if no files were
	 *         found, or none were readable.
	 */
	private boolean scanAndLoadLoggingConfiguration(final String path, final String filename) {
		String[] pathElements = path.split(COMMA);
		for (String pathElement : pathElements) {
			File file = new File(pathElement, filename);
			if (file.exists() && file.canRead() && !file.isDirectory()) {
				addMessage(EELFMsgs.LOADING_LOG_CONFIGURATION, file.getAbsolutePath());

				BufferedInputStream stream = null;
				try {
					stream = new BufferedInputStream(new FileInputStream(file));
					addMessage("EELF000I Loading logging configuration from %s",
							file.getAbsolutePath());
					loadLoggingConfiguration(stream);
				} catch (FileNotFoundException e) {
					addMessage(EELFResourceManager.format(e));
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							// not much we can do since logger may not be
							// configured yet
							e.printStackTrace(System.out);
						}
					}
				}

				return true;
			}
		}

		return false;
	}
}
