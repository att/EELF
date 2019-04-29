/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
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
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFLogger.Level;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.exception.EELFException;


public class TestApplicationLogger {
	
	 private static EELFLogger applicationLogger = EELFManager.getApplicationLogger();
	 
	 @Test
	 public void testInfoMessages() {
		 String msg = "This is info message for application logger";
		 applicationLogger.info(msg);
		 
		 applicationLogger.info("This is {} info message with args {}","args0","args1");
		 msg = String.format("This is %s info message with args %s","dummy","args1");
		 
		 applicationLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED);
		 
		 applicationLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_STARTED, new Date().toString());
		 
	 }
	 
	 
	 
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
	 public void testWarnMessages() {
		 String msg = "This is warn message for application logger";
		 applicationLogger.warn(msg);
		 //assertTrue(checkLogFile(msg, Level.WARN));
		 
		 applicationLogger.warn("This is {} warn message with args {}","dummy","args1");
		 msg = String.format("This is %s warn message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg, Level.WARN)); 
		 
		 applicationLogger.warn(SampleApplicationMsgs.WARN_MSG);
		 //assertTrue(checkLogFile("APP10007W", Level.WARN)); 
		
		 
	 }
	 
	 
	 
	 @Test
	 public void testErrorMessages() {
		 String msg = "This is error message for application logger";
		 applicationLogger.error(msg);
		 //assertTrue(checkLogFile(msg,Level.ERROR));
		 
		 applicationLogger.error("This is {} error message with args {}","dummy","args1");
		 msg = String.format("This is %s error message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg,Level.ERROR)); 
		 
		 applicationLogger.error(SampleApplicationMsgs.ERROR_SERVICE_NOT_RUNNING);
		 //assertTrue(checkLogFile("APP30004E",Level.ERROR)); 
		 
		 applicationLogger.error(SampleApplicationMsgs.ERROR_USER_NOT_FOUND, new Date().toString());
		 //assertTrue(checkLogFile("APP30003E",Level.ERROR)); 
		 
		 applicationLogger.info(new Locale("es", "ES"), SampleApplicationMsgs.ERROR_SERVICE_NOT_RUNNING,"chitwan");
		 
		 
	 }
	 
	 
	 @Test
	 public void testDebugMessages() {
		 String msg = "This is debug message for application logger not to be logged";
		 applicationLogger.debug(msg);
		 //assertFalse(checkLogFile(msg,Level.DEBUG));
		 
		 applicationLogger.debug("This is {} debug message with args {}","dummy","args1");
		 msg = String.format("This is %s debug message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.DEBUG)); 
		 
		 applicationLogger.debug(SampleApplicationMsgs.DEBUG_MSG);
		 //assertFalse(checkLogFile("APP10006D",Level.DEBUG)); 
	 }
	 
	 @Test
	 public void testTraceMessages() {
		 String msg = "This is trace message for application logger not to be logged";
		 applicationLogger.trace(msg);
		 //assertFalse(checkLogFile(msg,Level.TRACE));
		 
		 applicationLogger.trace("This is {} trace message with args {}","dummy","args1");
		 msg = String.format("This is %s trace message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.TRACE)); 
		
		 applicationLogger.debug(SampleApplicationMsgs.TRACE_MSG);
		 //assertFalse(checkLogFile("APP10009T",Level.TRACE)); 
		 
	 }
	 
	 @Test
	 public void testChangeLevel() {
		 applicationLogger.setLevel(Level.DEBUG);
		 String msg = "This is debug message for application logger to be logged";
		 applicationLogger.debug(msg);
		 applicationLogger.debug(SampleApplicationMsgs.DEBUG_MSG);
		 //assertTrue(checkLogFile(msg,Level.DEBUG));
		 
		 applicationLogger.setLevel(Level.TRACE);
		 msg = "This is trace message for application logger to be logged";
		 applicationLogger.trace(msg);
		 applicationLogger.debug(SampleApplicationMsgs.TRACE_MSG);
		 //assertTrue(checkLogFile(msg,Level.TRACE));
	}
	 
	 @Test
	 public void testDisableLevel() {
		 applicationLogger.disableLogging();
		 String msg = "This is info message for application logger not to be logged";
		 applicationLogger.info(msg);
		 //assertFalse(checkLogFile(msg, Level.INFO));
		 
		 applicationLogger.info("This is {} info message with args {} not to be logged","dummy","args1");
		 msg = String.format("This is %s info message with args %s not to be logged","dummy","args1");
		 //assertFalse(checkLogFile(msg, Level.INFO)); 
	}
	 
	 @Test
	 public void testException() {
		 try {
			 throw new EELFException("Tet exception generated");
		 } catch (EELFException ee) {
			 applicationLogger.error(SampleApplicationMsgs.MY_SAMPLE_EXCEPTION, ee);
		 }
	 }
	 
	 @Test
	 public void testApplicationEvent() {
		 applicationLogger.applicationEvent("The is generated  event");
		 applicationLogger.applicationEvent(Level.WARN,"The is generated  event");
	 }
	 
	 
	 private boolean checkLogFile(String line, Level level) {
		 BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader("logs/EELF/application.log"));

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
