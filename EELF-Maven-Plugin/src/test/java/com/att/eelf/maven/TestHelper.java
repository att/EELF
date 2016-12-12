package com.att.eelf.maven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.att.eelf.maven.wiki.Resource;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Helper.class)
@SuppressStaticInitializationFor("com.att.eelf.maven.Helper")
	public class TestHelper {
	
	@Mock
	Resource resource;
	
	Helper helper = new Helper();
	
	@Test
	public void testProcessResource() throws ClassNotFoundException {
		when(resource.getMessageClass()).thenReturn("com.att.eelf.maven.TestClass");
		Class<?> expectedInstance = Class.forName("com.att.eelf.maven.TestClass");
		Class<?> classInstance = Helper.processResources(resource);
		assertEquals("Actual value should be com.att.eelf.maven.TestClass instance", expectedInstance, classInstance);
	}
	
	@Test
	public void testProcessResourceInvalidMessageClass() {
		when(resource.getMessageClass()).thenReturn("InvalidClass");
		Class<?> classInstance = Helper.processResources(resource);
		assertEquals("Actual value should be null because class does not exist", null, classInstance);
	}
	
	@Test
	public void testGetEnumName() {
		String actualValue;
		String expectedValue;
		Class<?> enumeration = null;
		Integer num = new Integer(0);
		
		actualValue = ReflectionToStringBuilder.toString(num);
		expectedValue = Helper.getEnumName(enumeration, num);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetUsingAccessor() {
		String message = "testResource";
		String actualValue = Helper.getUsingAccessor("message",message);
		String expectedValue = "testResource";
		assertEquals(expectedValue, actualValue);
	}

}

class TestClass {
	
}
