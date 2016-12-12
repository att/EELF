/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven.wiki;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import com.att.eelf.maven.Helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;


import java.io.IOException;


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


@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageTable.class, Helper.class})
public class TestMessageTable {
	
    Class<?> enumeration;
	
    private Resource resource;
    private MessageTable messageTable;
	
	@Before
	public void setUp() {
		enumeration = EnumClass.class;
		resource = Mockito.mock(Resource.class);
		messageTable = new MessageTable(resource);
		when(resource.getHeader()).thenReturn("resourceHeader");
		when(resource.getFooter()).thenReturn("resouceFooter");
		when(resource.isCodeDefined()).thenReturn(true);
		when(resource.isMessageDefined()).thenReturn(true);
		when(resource.isDescriptionDefined()).thenReturn(true);
		when(resource.isResolutionDefined()).thenReturn(true);
		when(resource.getCodeHeading()).thenReturn("codeHeading");
		when(resource.getMessageHeading()).thenReturn("messageHeading");
		when(resource.getDescriptionHeading()).thenReturn("descriptionHeading");
		when(resource.getResolutionHeading()).thenReturn("resolutionHeading");
		when(resource.getSeverityHeading()).thenReturn("severityHeading");
		when(resource.getCodeProperty()).thenReturn("num");
		when(resource.getMessageProperty()).thenReturn("messageProperty");
		when(resource.getDescriptionProperty()).thenReturn("descriptionProperty");
		when(resource.getResolutionProperty()).thenReturn("resolutionProperty");

		messageTable.setHeading("heading");
		messageTable.setFooting("footing");
		messageTable.setName("messageTableName");
		
	}

	@Test
	public void testParseEnumeration() throws IOException {
		
		PowerMockito.mockStatic(Helper.class);
		//when(enumeration.getSimpleName()).thenReturn("TestClassName");
		when(resource.getHeader()).thenReturn("resourceHeader");
		when(resource.getFooter()).thenReturn("resouceFooter");
		when(resource.isCodeDefined()).thenReturn(false);
		when(resource.isMessageDefined()).thenReturn(false);
		when(resource.isDescriptionDefined()).thenReturn(false);
		when(resource.isResolutionDefined()).thenReturn(false);
		when(Helper.getUsingAccessor("num", new Object())).thenReturn("testValue");
		
		messageTable.parseEnumeration(enumeration);
	}
	
	@Test
	public void testToTwiki() {
		
		String expectedValue = "<p>\n"
								+ "heading</p>\n\n"
								+ "<table>\n"
								+ "<tbody>\n"
								+ "<tr>\n"
								+ "<th>\n"
								+ "<p><strong>codeHeading</strong>\n"
								+ "</p>\n"
								+ "</th>\n"
								+ "<th>\n"
								+ "<p><strong>messageHeading</strong>\n"
								+ "</p>\n"
								+ "</th>\n"
								+ "<th>\n"
								+ "<p><strong>descriptionHeading</strong>\n"
								+ "</p>\n"
								+ "</th>\n"
								+ "<th>\n"
								+ "<p><strong>resolutionHeading</strong>\n"
								+ "</p>\n"
								+ "</th>\n"
								+ "<th>\n"
								+ "<p><strong>severityHeading</strong>\n"
								+ "</p>\n"
								+ "</th>\n"
								+ "</tr>\n"
								+ "</tbody>\n"
								+ "</table>\n"
								+ "<p>\n"
								+ "footing</p>\n\n";
		
		String actualValue = messageTable.toTwiki();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testProperties() { 
		assertEquals("heading", messageTable.getHeading());
		assertEquals("footing", messageTable.getFooting());
		assertEquals("messageTableName", messageTable.getName());

	}
}
