/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import com.att.eelf.SampleApplicationMsgs;
import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.configuration.EELFLogger.Level;
import com.att.eelf.exception.EELFException;

import static com.att.eelf.configuration.Configuration.*;

 

public class TestDebugLogger {
	
	 private static EELFLogger debugLogger = EELFManager.getInstance().getDebugLogger();
	 
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
		 String msg = "This is info message for debug logger";
		 debugLogger.info(msg);
		 //assertFalse(checkLogFile(msg, Level.INFO));
		 
		 debugLogger.info("This is {} info message with args {}","dummy","args1");
		 msg = String.format("This is %s info message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg, Level.INFO)); 
		 
		 debugLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED);
		 //assertFalse(checkLogFile("APP20002I",Level.INFO)); 
		 
		 debugLogger.info(SampleApplicationMsgs.MESSAGE_CONFIGURATION_STARTED, new Date().toString());
		 //assertFalse(checkLogFile("APP10001I", Level.INFO)); 
		 
	 }
	 
	 @Test
	 public void testWarnMessages() {
		 String msg = "This is warn message for debug logger";
		 debugLogger.warn(msg);
		 //assertFalse(checkLogFile(msg, Level.WARN));
		 
		 debugLogger.warn("This is {} warn message with args {}","dummy","args1");
		 msg = String.format("This is %s warn message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg, Level.WARN)); 
		 
		 debugLogger.warn(SampleApplicationMsgs.WARN_MSG);
		 //assertFalse(checkLogFile("APP10007W", Level.WARN)); 
		
		 
	 }
	 
	 
	 
	 @Test
	 public void testErrorMessages() {
		 String msg = "This is error message for debug logger";
		 debugLogger.error(msg);
		 //assertTrue(checkLogFile(msg,Level.ERROR));
		 
		 debugLogger.error("This is {} error message with args {}","dummy","args1");
		 msg = String.format("This is %s error message with args %s","dummy","args1");
		 //assertTrue(checkLogFile(msg,Level.ERROR)); 
		 
		 debugLogger.error(SampleApplicationMsgs.ERROR_SERVICE_NOT_RUNNING);
		 //assertTrue(checkLogFile("APP30004E",Level.ERROR)); 
		 
		 debugLogger.error(SampleApplicationMsgs.ERROR_USER_NOT_FOUND, new Date().toString());
		 //assertTrue(checkLogFile("APP30003E",Level.ERROR)); 
		 
		 
	 }
	 
	 
	 @Test
	 public void testDebugMessages() {
		 String msg = "This is debug message for debug logger to be logged";
		 debugLogger.debug(msg);
		 //assertFalse(checkLogFile(msg,Level.DEBUG));
		 
		 debugLogger.debug("This is {} debug message with args {}","dummy","args1");
		 msg = String.format("This is %s debug message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.DEBUG)); 
		 
		 debugLogger.debug(SampleApplicationMsgs.DEBUG_MSG);
		 //assertFalse(checkLogFile("APP10006D",Level.DEBUG)); 
	 }
	 
	 @Test
	 public void testTraceMessages() {
		 String msg = "This is trace message for debug logger not to be logged";
		 debugLogger.trace(msg);
		 //assertFalse(checkLogFile(msg,Level.TRACE));
		 
		 debugLogger.trace("This is {} trace message with args {}","dummy","args1");
		 msg = String.format("This is %s trace message with args %s","dummy","args1");
		 //assertFalse(checkLogFile(msg,Level.TRACE)); 
		
		 debugLogger.trace(SampleApplicationMsgs.TRACE_MSG);
		 //assertFalse(checkLogFile("APP10009T",Level.TRACE)); 
		 
	 }
	 
	 @Test
	 public void testChangeLevel() {
		 debugLogger.setLevel(Level.TRACE);
		 String msg = "This is trace message for debug logger to be logged";
		 debugLogger.trace(msg);
		 //assertTrue(checkLogFile(msg,Level.TRACE));
		 debugLogger.setLevel(Level.DEBUG);
	}
	 
	 @Test
	 public void testDisableLevel() {
		 debugLogger.disableLogging();
		 String msg = "This is error message for debug logger not to be logged";
		 debugLogger.debug(msg);
		 //assertFalse(checkLogFile(msg, Level.INFO));
		 
		 debugLogger.debug("This is {} error message with args {} not to be logged","dummy","args1");
		 msg = String.format("This is %s error message with args %s not to be logged","dummy","args1");
		 //assertFalse(checkLogFile(msg, Level.INFO)); 
	}

	 @Test
	 public void testException() {
		 try {
			 throw new EELFException("Tet exception generated");
		 } catch (EELFException ee) {
			 debugLogger.error(SampleApplicationMsgs.MY_SAMPLE_EXCEPTION, ee);
			 debugLogger.debug(SampleApplicationMsgs.MY_SAMPLE_EXCEPTION, ee);
		 }
	 }
	 
	 
	 private boolean checkLogFile(String line, Level level) {
		 BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader("logs/EELF/error.log"));

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
