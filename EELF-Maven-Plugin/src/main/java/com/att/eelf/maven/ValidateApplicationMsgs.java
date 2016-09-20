/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.att.eelf.maven.wiki.Resource;




/**
 * This class is used to test the Application Msg class to ensure that all messages are defined.
 * 
 */
@Mojo(name = "ValidateApplicationMsgs")
@SuppressWarnings("nls")
public class ValidateApplicationMsgs extends AbstractMojo {
    
    private static final String REGEX = "EELF(-[^0-9]+)?[0-9]{4}(I|E|W|D)";

    /**
     * A list of class names to be loaded and processed that represent message definitions. Each of these classes must
     * implement the {@link com.att.eelf.i18n.EELFResolvableErrorEnum} interface, and must be enumerations of the required
     * format.
     */
    @Parameter(property = "resources")
    private List<Resource> resources;
    
    /**
     * Print all errors (if any). This method is "adaptive", in that it discovers the maximum length of each "error" and
     * formats the display of the errors into as many columns as will fit comfortably in a 120-character line.
     * 
     * @param heading
     *            An optional report heading
     * @param errors
     *            the list of errors (or an empty list)
     */
    private void printErrors(String heading, List<String> errors) {
        if (errors.isEmpty()) {
            return;
        }
        int maxLength = 0;
        int columns = 8;
        for (String error : errors) {
            if (error.length() > maxLength) {
                maxLength = error.length();
            }
        }

        /*
         * Try to keep the number of columns into a maximum line length of 120 characters or less
         */
        columns = 120 / (maxLength + 2);
        if (columns < 1) {
            columns = 1;
        }

        int count = errors.size();
        if (heading != null) {
        	getLog().info(heading);
        }
        getLog().info(count+" messages in error:\n");

        StringBuffer buffer = new StringBuffer();
        int loops = count / columns;
        if (count % columns > 0) loops++;

        int index = 0;
        for (int i = 0; i < loops; i++) {
            buffer.setLength(0);
            for (int j = 0; j < 120; j++) {
                buffer.append(" ");
            }

            for (int j = 0; j < columns; j++) {
                if (index >= count) {
                    break;
                }
                String error = errors.get(index) + ", ";
                int start = j * (maxLength + 2);
                int end = start + error.length();
                buffer.replace(start, end, error);
                index++;
            }

            getLog().info(buffer.toString());
            buffer.setLength(0);
            if (index >= count) {
                break;
            }
        }
    }

    /**
     * Test that all message identifiers are unique
     * 
     * @param values
     * @param enumMsg
     * @param resource
     */
    public void testUniqueness(Object[] values, Class<?> enumMsg, Resource resource) {
        HashSet<String> defined = new HashSet<String>();
        ArrayList<String> errors = new ArrayList<String>();
        for (Object value : values) {
            String name = Helper.getEnumName(enumMsg, value);
            String identifier = Helper.getUsingAccessor(resource.getCodeProperty(),  value);
            if (defined.contains(identifier)) {
                errors.add(identifier);
            } 
            defined.add(identifier);
        } 

        printErrors("\nMessages that have duplicated identifiers:", errors);

    }
    
    /**
     * Verify that all messages have content
     * 
     * @param values
     * @param enumMsg
     * @param resource
     */
    public void verifyAllMessagesExist(Object[] values, Class<?> enumMsg, Resource resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object value : values) {
        	String name = Helper.getEnumName(enumMsg, value);
        	String text = Helper.getUsingAccessor(resource.getMessageProperty(),  value);
            if (text.contains("EELF9998E")) {
                errors.add(name);
            }
        }

        printErrors("\nMessages that are missing resource definitions:", errors);

    }

    /**
     * Verifies that all messages have valid descriptions
     * 
     * @param values
     * @param enumMsg
     * @param resource
     */
    public void verifyDescriptionsExist(Object[] values, Class<?> enumMsg, Resource resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object value : values) {
        	String name = Helper.getEnumName(enumMsg, value);
        	String description = Helper.getUsingAccessor(resource.getDescriptionProperty(),  value);
            if (description == null || description.trim().length() == 0 || description.startsWith("EELF9998E")) {
                errors.add(name);
            }
        }

        printErrors("\nMessages that have invalid or mis-formatted descriptions:", errors);

    }

    /**
     * Verifies that all messages have valid resolutions
     * 
     * @param values
     * @param enumMsg
     * @param resource
     */
  
    public void verifyResolutionsExist(Object[] values, Class<?> enumMsg, Resource resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object value : values) {
        	String name = Helper.getEnumName(enumMsg, value);
        	String resolution = Helper.getUsingAccessor(resource.getResolutionProperty(),  value);
            if (resolution == null || resolution.trim().length() == 0 || resolution.startsWith("EELF9998E")) {
                errors.add(name);
            }
        }

        printErrors("\nMessages that have invalid or mis-formatted resolutions:", errors);

    }
    
    /**
 
  
   public void verifyResourcesExistInBundle(Object[] values, Class resource) {
       ArrayList<String> errors = new ArrayList<String>();
       for (Object msg : values()) {
           String description = EELFResourceManager.getMessage(msg);
           if (description == null || description.trim().length() == 0 || description.startsWith("EELF9998E")) {
               errors.add(msg.name());
           }
       }
      
       printErrors("\nResources that have defined constants but which do NOT have resources in the bundle.\n"
           + "This is an indication of a definition error.  Either the constant has been defined but\n"
           + "is not actually used, or the constant is defined and referenced in code, but the resource\n"
           + "has not been defined in the bundle.  If the constant was defined but not actually used,\n"
           + "remove it.  If the constant is defined and used, but no resource exists, define a message\n"
           + "resource for it:", errors);

   }

   /* *//**
     * Verify that all messages have content
     *//*
    public void verifyAllMessagesExist(Object[] values, Class resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object msg : values) {
        	String identifier = Helper.getEnumName(resource, msg);
        	Helper.getUsingAccessor(resource.getC, resource, eelfResourceManager);
            if (text.contains("EELF9998E")) {
                errors.add(msg.name());
            }
        }

        printErrors("\nMessages that are missing resource definitions:", errors);

        assertTrue(errors.isEmpty());
    }

    *//**
     * Verify that all messages have valid identifiers
     *//*
  
    public void verifyAllMessagesHaveValidIdentifiers(Object[] values, Class resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object msg : values) {
                if (!Pattern.matches(regex, EELFResourceManager.getIdentifier(msg))) {
                errors.add(msg.name());
            } 
        }

        printErrors("\nMessages that have invalid or mis-formatted error code:", errors);

        assertTrue(errors.isEmpty());
    }
 
    *//**
     * Verifies that all messages have valid descriptions
     *//*
    public void verifyDescriptionsExist(Object[] values, Class resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (SampleApplicationMsgs msg : SampleApplicationMsgs.values()) {
            String description = EELFResourceManager.getDescription(msg);
            if (description == null || description.trim().length() == 0 || description.startsWith("EELF9998E")) {
                errors.add(msg.name());
            }
        }

        printErrors("\nMessages that have invalid or mis-formatted descriptions:", errors);

        assertTrue(errors.isEmpty());
    }

    *//**
     * Verifies that all messages have valid resolutions
     *//*
  
    public void verifyResolutionsExist(Object[] values, Class resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object msg : values) {
            String resolution = EELFResourceManager.getResolution(msg);
            if (resolution == null || resolution.trim().length() == 0 || resolution.startsWith("EELF9998E")) {
                errors.add(msg.name());
            }
        }

        printErrors("\nMessages that have invalid or mis-formatted resolutions:", errors);

        assertTrue(errors.isEmpty());
    }

    *//**
     * Make sure that each message has its own resource and that resources are not accidentally being reused.
     *//*
    public void verifyMessageSkeletonsAreUnique(Object[] values, Class resource) {
        HashSet<String> resources = new HashSet<String>();
        ArrayList<String> errors = new ArrayList<String>();
        for (Object msg : values) {
            String resource = msg.name();
            if (resources.contains(resource)) {
                errors.add(msg.name());
            }
            resources.add(resource);
        }

        printErrors("\nMessages that have duplicated message bodies (refer to an already used resource):", errors);

    }

   
    public void verifyResourcesExistInBundle(Object[] values, Class resource) {
        ArrayList<String> errors = new ArrayList<String>();
        for (Object msg : values()) {
            String description = EELFResourceManager.getMessage(msg);
            if (description == null || description.trim().length() == 0 || description.startsWith("EELF9998E")) {
                errors.add(msg.name());
            }
        }
       
        printErrors("\nResources that have defined constants but which do NOT have resources in the bundle.\n"
            + "This is an indication of a definition error.  Either the constant has been defined but\n"
            + "is not actually used, or the constant is defined and referenced in code, but the resource\n"
            + "has not been defined in the bundle.  If the constant was defined but not actually used,\n"
            + "remove it.  If the constant is defined and used, but no resource exists, define a message\n"
            + "resource for it:", errors);

    }*/

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		 getLog().info("Started validating application messages...");
	        if (resources == null || resources.isEmpty()) {
	            getLog().error("No resources were defined to generate, generation skipped");
	        } else {
	        	for (Resource resource:resources) {
                Class<?> enumeration = Helper.processResources(resource);
	        		Object values[] = enumeration.getEnumConstants();
	        		testUniqueness(values, enumeration, resource);
	        		verifyAllMessagesExist(values, enumeration, resource);
	        		verifyDescriptionsExist(values, enumeration, resource);
	        		verifyResolutionsExist(values, enumeration, resource);
	        		getLog().info("Finished validating application messages...");
	        	}		
	     
	        }
	        getLog().info("Completed validating application message...");
		
	}
	
	

}
