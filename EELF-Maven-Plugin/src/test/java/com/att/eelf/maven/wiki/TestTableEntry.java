/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven.wiki;

import static org.junit.Assert.*;

import org.junit.Test;



public class TestTableEntry {

    /**
     * Test the escape processing logic to make sure that it handles special characters correctly
     */
    @Test
    public void testEscapeProcessing() {

        assertEquals("abcdefghijklmnopqrstuvwxyz", TableEntry.processSpecialCharacters("abcdefghijklmnopqrstuvwxyz"));
        assertEquals("An &amp; character", TableEntry.processSpecialCharacters("An &amp; character"));
        assertEquals("An &amp; character", TableEntry.processSpecialCharacters("An & character"));
        assertEquals("This test has the special character at the end &amp;",
            TableEntry.processSpecialCharacters("This test has the special character at the end &"));
        assertEquals("&amp;This test has the special character at the begining",
            TableEntry.processSpecialCharacters("&This test has the special character at the begining"));
        assertEquals("&amp;some sequences already escaped &amp; &lt; &gt; &amp; < > &amp; &lt; &gt;",
            TableEntry
                .processSpecialCharacters("&amp;some sequences already escaped &amp; &lt; &gt; & < > &amp; &lt; &gt;"));
    }
}
