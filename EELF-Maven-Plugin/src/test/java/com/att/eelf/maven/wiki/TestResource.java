package com.att.eelf.maven.wiki;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestResource {
	Resource resource = new Resource();
	
	@Test
	public void testResource() {
		assertNotNull(resource);
		resource.setCodeHeading("heading");
		resource.setCodeProperty("codeProperty");
		resource.setCodeWidth("10");
		resource.setDescriptionHeading("descriptionHeading");
		resource.setDescriptionProperty("descriptionProperty");
		resource.setDescriptionWidth("20");
		resource.setFooter("footer");
		resource.setHeader("header");
		resource.setMessageClass("messageClass");
		resource.setMessageHeading("messageHeading");
		resource.setMessageProperty("messageProperty");
		resource.setMessageWidth("10");
		resource.setResolutionHeading("resolutionHeading");
		resource.setResolutionProperty("resolutionProperty");
		resource.setResolutionWidth("resolutionWidth");
		resource.setSeverityHeading("severityHeading");
		resource.setSeverityWidth("severityWidth");
		
		assertEquals("heading",resource.getCodeHeading());
		assertEquals("codeProperty", resource.getCodeProperty());
		assertEquals("10", resource.getCodeWidth());
		assertEquals("descriptionHeading", resource.getDescriptionHeading());
		assertEquals("descriptionProperty", resource.getDescriptionProperty());
		assertEquals("footer", resource.getFooter());
		assertEquals("header", resource.getHeader());
		assertEquals("messageClass", resource.getMessageClass());
		assertEquals("messageHeading", resource.getMessageHeading());
		assertEquals("messageProperty", resource.getMessageProperty());
		assertEquals("10", resource.getMessageWidth());
		assertEquals("resolutionHeading", resource.getResolutionHeading());
		assertEquals("resolutionProperty", resource.getResolutionProperty());
		assertEquals("resolutionWidth", resource.getResolutionWidth());
		assertEquals("severityHeading", resource.getSeverityHeading());
		assertEquals("severityWidth", resource.getSeverityWidth());
	}

}
