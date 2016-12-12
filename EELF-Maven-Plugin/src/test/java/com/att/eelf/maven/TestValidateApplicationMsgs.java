/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.att.eelf.maven.wiki.Resource;

enum EnumClass {
    INSTANCE;
    private String num ="test";

    protected void setNum(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }
}

public class TestValidateApplicationMsgs {
	
	Class<?> enumeration;
	ValidateApplicationMsgs validateApplicationMsgs;
	private Resource resource;
	
	@Before
    public void setUp() {
		validateApplicationMsgs = new ValidateApplicationMsgs();
		enumeration = EnumClass.class;
		resource = Mockito.mock(Resource.class);
    }
	
	@Test(expected = NullPointerException.class)
	public void testTestUniqueness() {
		Object[] objects = {new Object()};
		validateApplicationMsgs.testUniqueness(objects, enumeration, resource);
	}
	
	@Test(expected = NullPointerException.class)
	public void testVerifyAllMessagesExist() {
		Object[] objects = {new Object()};
		validateApplicationMsgs.verifyAllMessagesExist(objects, enumeration, resource);
	}
	
	@Test(expected = NullPointerException.class)
	public void testVerifyDescriptionsExist() {
		Object[] objects = {new Object()};
		validateApplicationMsgs.verifyDescriptionsExist(objects, enumeration, resource);
	}
	
	@Test(expected = NullPointerException.class)
	public void testVerifyResolutionsExist() {
		Object[] objects = {new Object()};
		validateApplicationMsgs.verifyResolutionsExist(objects, enumeration, resource);
	}

}
