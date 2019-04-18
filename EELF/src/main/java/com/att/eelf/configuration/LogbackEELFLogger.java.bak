/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.configuration;

import java.util.EnumMap;

public class LogbackEELFLogger extends AbstractEELFLogger {
	private static final EnumMap<Level, ch.qos.logback.classic.Level> levelMap;

	static {
		levelMap = new EnumMap<Level, ch.qos.logback.classic.Level>(Level.class);

		levelMap.put(Level.INFO, ch.qos.logback.classic.Level.INFO);
		levelMap.put(Level.ERROR, ch.qos.logback.classic.Level.ERROR);
		levelMap.put(Level.WARN, ch.qos.logback.classic.Level.WARN);
		levelMap.put(Level.DEBUG, ch.qos.logback.classic.Level.DEBUG);
		levelMap.put(Level.TRACE, ch.qos.logback.classic.Level.TRACE);
		levelMap.put(Level.OFF, ch.qos.logback.classic.Level.OFF);
	}

	/**
	 * @param name the name of the logger
	 */
	public LogbackEELFLogger(String name) {
		super(name);
	}

	@Override
	public void setLevel(Level level) {
		if (logger instanceof ch.qos.logback.classic.Logger) {
			ch.qos.logback.classic.Level logbackLevel = levelMap.get(level);
			if (logbackLevel != null) {
				((ch.qos.logback.classic.Logger) logger).setLevel(logbackLevel);
			}
		}
	}
}
