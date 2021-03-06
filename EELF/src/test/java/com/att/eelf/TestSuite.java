/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.att.eelf.i18n.TestEELFResourceManager;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestEELFResourceManager.class,
	TestApplicationLogger.class,
	TestAuditLogger.class,
	TestDebugLogger.class,
	TestErrorLogger.class,
	TestMetricsLogger.class,
	TestPerformanceLogger.class,
	TestPolicyLogger.class,
	TestSecurityLogger.class,
	TestServerLogger.class,
	TestClassLogger.class,
	TestLogger.class,
	TestExceptionHierarchy.class})
public class TestSuite {

}
