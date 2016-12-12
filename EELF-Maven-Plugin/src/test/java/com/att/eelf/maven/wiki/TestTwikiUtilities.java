/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven.wiki;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTwikiUtilities {
	private static final String AC_DEFAULT_PARAMETER = "</ac:default-parameter>\n";
    private static final String AC_DEFAULT_PARAMETER2 = "<ac:default-parameter>";
    private static final String ANCHOR_END_TWIKI = "</ac:macro></p>\n";
    private static final String ANCHOR_START_TWIKI = "<p><ac:macro ac:name=\"anchor\">\n";
    private static final String CELL_END = "</td>\n";
    private static final String CELL_START = "<td>\n";
    private static final String PARAGRAPH_END = "</p>\n";
    private static final String PARAGRAPH_START = "<p>";
    private static final String ROW_END = "</tr>\n";
    private static final String ROW_START = "<tr>\n";
    
    TwikiUtilities twikiUtilities = new TwikiUtilities();

	@Test
	public void testRenderCellEnd() {
		assertEquals(CELL_END, TwikiUtilities.renderCellEnd()); 
	}
	
	@Test
	public void testRenderCellStart() {
		assertEquals(CELL_START, TwikiUtilities.renderCellStart());
	}
	
	@Test
	public void testRenderParagraphEnd() {
		assertEquals(PARAGRAPH_END, TwikiUtilities.renderParagraphEnd());
	}
	
	@Test
	public void testRenderParagraphStart() {
		assertEquals(PARAGRAPH_START, TwikiUtilities.renderParagraphStart());
	}
	
	@Test
	public void testRenderRowEnd() {
		assertEquals(ROW_END, TwikiUtilities.renderRowEnd());
	}
	
	@Test
	public void testRenderRowStart() {
		assertEquals(ROW_START, TwikiUtilities.renderRowStart());
	}
	
	@Test
	public void testRenderAcDefaultParameter() {
		assertEquals(AC_DEFAULT_PARAMETER, twikiUtilities.renderAcDefaultParameter());
	}
	
	@Test
	public void testRenderAcDefaultParameter2() {
		assertEquals(AC_DEFAULT_PARAMETER2, twikiUtilities.renderAcDefaultParameter2());
	}
	
	@Test
	public void testRenderAnchorBodyTwiki() {
		assertEquals("<ac:default-parameter>test</ac:default-parameter>\n", twikiUtilities.renderAnchorBodyTwiki("test"));
	}
	
	@Test
	public void testRenderAnchoredCell() {
		String expected = "<td>\n"
				+ "<p><ac:macro ac:name=\"anchor\">\n"
				+ "<ac:default-parameter>test</ac:default-parameter>\n"
				+ "</ac:macro></p>\n"
				+ "test</td>\n";
		assertEquals(expected, twikiUtilities.renderAnchoredCell("test"));
	}
	
	@Test
	public void testRenderAnchorEndTwiki() {
		assertEquals(ANCHOR_END_TWIKI,twikiUtilities.renderAnchorEndTwiki());
	}
	
	@Test
	public void testRenderAnchorStartTwiki() {
		assertEquals(ANCHOR_START_TWIKI, twikiUtilities.renderAnchorStartTwiki());
	}
	
	@Test
	public void testRenderAnchorTwiki() {
		String expected = "<p><ac:macro ac:name=\"anchor\">\n"
				+ "<ac:default-parameter>test</ac:default-parameter>\n"
				+ "</ac:macro></p>\n";
		assertEquals(expected, twikiUtilities.renderAnchorTwiki("test"));
	}
	
	@Test
	public void testRenderTableCellTwiki() {
		String expected = "<td>\n"
				+ "<p>test</p>\n"
				+ "</td>\n";
		assertEquals(expected, twikiUtilities.renderTableCellTwiki("test"));
	}

}
