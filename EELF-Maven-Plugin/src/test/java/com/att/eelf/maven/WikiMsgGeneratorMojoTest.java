/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.att.eelf.maven.wiki.Resource;



@Ignore
public class WikiMsgGeneratorMojoTest  {
    /**
     * Generate the wiki html document with error codes and messages
     * 
     * @throws Exception
     *             If anything fails
     */
    @Test
    public void testWikiGenerator() throws Exception {
    	  WikiMsgGenerator wg = new WikiMsgGenerator();
    	        Resource r =new Resource();
    	        r.setHeader("EELF Messages and Codes");
    		    r.setMessageClass("com.att.eelf.i18n.EELFMsgs");
    		    List<Resource> resources = new ArrayList<>();
    		    resources.add(r);
    		    wg.setResources(resources);
    	        try {
    	        wg.execute();
    	        } catch (Exception e){
    	        	e.printStackTrace();
    	        }
    	    }
    
}
