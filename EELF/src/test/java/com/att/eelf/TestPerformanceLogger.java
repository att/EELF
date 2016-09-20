/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf;

import static com.att.eelf.configuration.Configuration.MDC_ALERT_SEVERITY;
import static com.att.eelf.configuration.Configuration.MDC_INSTANCE_UUID;
import static com.att.eelf.configuration.Configuration.MDC_KEY_REQUEST_ID;
import static com.att.eelf.configuration.Configuration.MDC_REMOTE_HOST;
import static com.att.eelf.configuration.Configuration.MDC_SERVER_FQDN;
import static com.att.eelf.configuration.Configuration.MDC_SERVER_IP_ADDRESS;
import static com.att.eelf.configuration.Configuration.MDC_SERVICE_INSTANCE_ID;
import static com.att.eelf.configuration.Configuration.MDC_SERVICE_NAME;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFLogger.Level;
import com.att.eelf.configuration.EELFManager;

 

public class TestPerformanceLogger {
	
	 private static EELFLogger performanceLogger = EELFManager.getInstance().getPerformanceLogger();
	 
	 @Before
	 public void setUpMDC() {
		 MDC.clear();
		 MDC.put(MDC_KEY_REQUEST_ID, "cc6965");
		 
		 String hostIpAddress = "";
		 String hostName = "";
		 try {
			 hostIpAddress = InetAddress.getLocalHost().getHostAddress();
			 hostName = InetAddress.getLocalHost().getHostName();
		 } catch (Exception e) {
			 
		 }
		 MDC.put(MDC_SERVER_IP_ADDRESS,hostIpAddress);
		 MDC.put(MDC_SERVER_FQDN, hostName);
		 MDC.put(MDC_ALERT_SEVERITY, "Nagios-0");
		 MDC.put(MDC_SERVICE_NAME, "Dummy Service");
		 MDC.put(MDC_REMOTE_HOST,"NA");
		 MDC.put(MDC_INSTANCE_UUID,UUID.randomUUID().toString());
		 MDC.put(MDC_SERVICE_INSTANCE_ID, "Service1"); 
		 
		 
		 
	 }
		
	 @Test
	 public void testInfoMessages() {
		 String msg = "This is info message for performance logger";
		 performanceLogger.info(msg);
		 //assertTrue(checkLogFile(msg, Level.INFO));
		 
		 performanceLogger.info("This is {} info message with args {}","dummy","args1");
		 msg = String.format("This is %s info message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg, Level.INFO)); 
		 
		 performanceLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED);
		 //assertTrue(checkLogFile("APP20002I",Level.INFO)); 
		 
		 performanceLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_STARTED, new Date().toString());
		 //assertTrue(checkLogFile("APP10001I", Level.INFO)); 
		 
	 }
	 
	 @Test
	 public void testWarnMessages() {
		 String msg = "This is warn message for performance logger";
		 performanceLogger.warn(msg);
		 //assertTrue(checkLogFile(msg, Level.WARN));
		 
		 performanceLogger.warn("This is {} warn message with args {}","dummy","args1");
		 msg = String.format("This is %s warn message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg, Level.WARN)); 
		 
		 performanceLogger.warn(SampleApplicationMsgs.WARN_MSG);
		 //assertTrue(checkLogFile("APP10007W", Level.WARN)); 
		
		 
	 }
	 
	 
	 
	 @Test
	 public void testErrorMessages() {
		 String msg = "This is error message for performance logger";
		 performanceLogger.error(msg);
		 //assertTrue(checkLogFile(msg,Level.ERROR));
		 
		 performanceLogger.error("This is {} error message with args {}","dummy","args1");
		 msg = String.format("This is %s error message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg,Level.ERROR)); 
		 
		 performanceLogger.error(SampleApplicationMsgs.ERROR_SERVICE_NOT_RUNNING);
		 //assertTrue(checkLogFile("APP30004E",Level.ERROR)); 
		 
		 performanceLogger.error(SampleApplicationMsgs.ERROR_USER_NOT_FOUND, new Date().toString());
		 //assertTrue(checkLogFile("APP30003E",Level.ERROR)); 
		 
		 
	 }
	 
	 
	 @Test
	 public void testDebugMessages() {
		 String msg = "This is debug message for performance logger not to be logged";
		 performanceLogger.debug(msg);
		 //assertFalse(checkLogFile(msg,Level.DEBUG));
		 
		 performanceLogger.debug("This is {} debug message with args {}","dummy","args1");
		 msg = String.format("This is %s debug message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.DEBUG)); 
		 
		 performanceLogger.debug(SampleApplicationMsgs.DEBUG_MSG);
		 //assertFalse(checkLogFile("APP10006D",Level.DEBUG)); 
	 }
	 
	 @Test
	 public void testTraceMessages() {
		 String msg = "This is trace message for performance logger not to be logged";
		 performanceLogger.trace(msg);
		 //assertFalse(checkLogFile(msg,Level.TRACE));
		 
		 performanceLogger.trace("This is {} trace message with args {}","dummy","args1");
		 msg = String.format("This is %s trace message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.TRACE)); 
		
		 performanceLogger.debug(SampleApplicationMsgs.TRACE_MSG);
		 //assertFalse(checkLogFile("APP10009T",Level.TRACE)); 
		 
	 }
	 
	 @Test
	 public void testChangeLevel() {
		 performanceLogger.setLevel(Level.DEBUG);
		 String msg = "This is debug message for performance logger to be logged";
		 performanceLogger.debug(msg);
		 //assertTrue(checkLogFile(msg,Level.DEBUG));
		 
		 performanceLogger.setLevel(Level.TRACE);
		 msg = "This is trace message for performance logger to be logged";
		 performanceLogger.trace(msg);
		 //assertTrue(checkLogFile(msg,Level.TRACE));
	}
	 
	 @Test
	 public void testDisableLevel() {
		 performanceLogger.disableLogging();
		 String msg = "This is info message for performance logger not to be logged";
		 performanceLogger.info(msg);
		 //assertFalse(checkLogFile(msg, Level.INFO));
		 
		 performanceLogger.info("This is {} info message with args {} not to be logged","dummy","args1");
		 msg = String.format("This is %s info message with args %s not to be logged","dummy","args1");
		 //assertFalse(checkLogFile(msg, Level.INFO)); 
	}
	 
	 private boolean checkLogFile(String line, Level level) {
		 BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader("logs/EELF/performance.log"));

				while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.contains(line) && sCurrentLine.contains(level.name())) {
						System.out.println(sCurrentLine);
						return true;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		 return false;
		 
	 }

}
