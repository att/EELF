/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Before;

import org.junit.Test;

import com.att.eelf.SampleApplicationMsgs;


public class TestEELFResourceManager {

    /**
     * A simple message skeleton to test the resource manager
     */
    public static final String testSkeleton =
        "Prior configuration has been cleared";

   
    /**
     * Make sure that the asList method works correctly
     */
    @Test
    public void testAsList() {
        assertEquals("[A,B,C,D]", EELFResourceManager.asList("A", "B", "C", "D"));
        assertEquals("[]", EELFResourceManager.asList());
    }

    /**
     * Create an exception and then test that we can format it
     */
    @Test
    public void testFormatException() {
        try {
            try {
                throw new NullPointerException("Dummy Exception");
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Dummy Exception", e);
            }
        } catch (IllegalArgumentException e1) {
            String msg = EELFResourceManager.format(e1);
            assertNotNull(msg);
            // System.out.println(msg);
            assertTrue(msg.startsWith("Exception in thread main java.lang.IllegalArgumentException: Dummy Exception"));
        }
    }

    /**
     * Test that we can format a message
     */
    @Test
    public void testFormatMessage() {
    	String msg  = "APP10001I Application initialization started at {0}";
        String pattern = msg.replaceAll("\\{[0-9]+\\}", "%s");
        String[] args = new String[] { "9/30/2015" };
        String expectedResults = String.format(pattern, (Object[]) args); 

        String resource = EELFResourceManager.format(SampleApplicationMsgs.MESSAGE_CONFIGURATION_STARTED, args);
        assertNotNull(resource);
        assertEquals(expectedResults, resource);
    }

    /**
     * Test the ability to lookup a resource
     */
    @Test
    public void testGetResource() {
        String resource = EELFResourceManager.getMessage(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertNotNull(resource);
        assertEquals(testSkeleton, resource);
    }

    /**
     * Verify that we can load additional bundles and resolve resources from them.
     */
    @Test
    public void testLoadAdditionalResource() {
    	EELFResourceManager.loadMessageBundle("TestAdditionalResources");
        String message = EELFResourceManager.getMessage("ADDITIONAL_RESOURCE");
        assertEquals("This is a message loaded from an additional resource bundle", message);
    }

    /**
     * This method is used to test that attempts to load a bad or unknown resource generate the appropriate response...
     */
    @Test
    public void testLoadBadResource() {
        String message = EELFResourceManager.getMessage("UnknownResource");
        assertTrue(message
            .contains("Resource id [UnknownResource] cannot be formatted - no resource with that id exists!"));
        // System.out.println(message);
    }

    /**
     * Test that if we attempt to load a bad resource bundle, the resource manager fails to load it, generates a stderr
     * message, and ignores it.
     */
    @Test
    public void testLoadBadResourceBundle() {
        String resource = EELFResourceManager.getMessage(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertNotNull(resource);
        assertEquals(testSkeleton, resource);
    }

    /**
     * See if the bundle was loaded. It should be the default bundle (en_US)
     */
    @Test
    public void testLoadEnglishUSBundle() {
        String message = EELFResourceManager.getMessage(SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertEquals(testSkeleton, message);
    }

    /**
     * Make sure that we can load the german bundle. Note, we are calling the resource manager to load the AUSTRIA
     * locale specifying both the language code and country code, de_AT), but no de_AT bundle exists. Instead, we
     * defined only a german language bundle which should get used because language takes precedence over country code.
     */
    @Test
    public void testLoadGermanATBundle() {
        String message = EELFResourceManager.getMessage(new Locale("de", "UST"), SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertEquals("Dies ist eine Testnachricht ohne Einsatze bearbeitet werden!", message);
    }

    /**
     * Make sure that we can load the german bundle. Note, we are calling the resource manager to load the GERMANY
     * locale specifying both the language code and country code, de_DE), but no de_DE bundle exists. Instead, we
     * defined only a german language bundle which should get used because language takes precedence over country code.
     */
    @Test
    public void testLoadGermanDEBundle() {
        String message = EELFResourceManager.getMessage(Locale.GERMAN, SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertEquals("Dies ist eine Testnachricht ohne Einsatze bearbeitet werden!", message);
    }

    /**
     * We dont have an it_IT bundle, so it should use the en_US bundle
     */
    @Test
    public void testLoadItalianITBundle() {
        String message = EELFResourceManager.getMessage(Locale.ITALY, SampleApplicationMsgs.MESSAGE_CONFIGURATION_CLEARED.name());
        assertEquals(testSkeleton, message);
    }
}
