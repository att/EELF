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

import java.net.InetAddress;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import com.att.eelf.configuration.EELFLogger;
import com.att.eelf.configuration.EELFManager;
import com.att.eelf.exception.EELFException;
import com.att.eelf.exception.MySampleException1;
import com.att.eelf.exception.MySampleException2;

 

public class TestExceptionHierarchy {
	
	 private static EELFLogger applicationLogger = EELFManager.getApplicationLogger();
	 
	 @Before
	 public void setUpMDC() {
		 MDC.clear();
		 MDC.put(MDC_KEY_REQUEST_ID, "cc69");
		 
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
	 public void testExceptionHierarchyLogging() {
		 try {
			 generateNPE(null);
		 } catch (Exception rootException) {
			 try {
				 wrapThisException(rootException);
			} catch (MySampleException1 firstWrappedException) {
				try {
					wrapExceptionAgain(firstWrappedException);
				} catch (MySampleException2 secondWrappedException) {
					logError(secondWrappedException);
				}
			}
			
		 }
	 }
	 
	 public void wrapThisException(Exception ee) throws MySampleException1 {
		 throw new MySampleException1(ee);
	 }
	 
	 public void wrapExceptionAgain(Exception ee) throws MySampleException2 {
		 throw new MySampleException2(ee);
	 }
	 
	 public void logError(EELFException ee) {
		 applicationLogger.error(SampleApplicationMsgs.MY_SAMPLE_EXCEPTION, ee);
	 }
	 
	 public void generateNPE(String test) {
		 test.length();
	 }

}
