/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven.wiki;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWiki {
	Wiki wiki = new Wiki();
	
	@Test
	public void testWiki() {
		assertNotNull(wiki);
		
		wiki.setCredentials("credentials");
		wiki.setPage("page");
		wiki.setPrincipal("principal");
		wiki.setSpace("space");
		wiki.setTitle("title");
		wiki.setUrl("url");
		
		assertEquals("credentials", wiki.getCredentials());
		assertEquals("page", wiki.getPage());
		assertEquals("principal", wiki.getPrincipal());
		assertEquals("space", wiki.getSpace());
		assertEquals("title", wiki.getTitle());
		assertEquals("url", wiki.getUrl());
	}

}
