/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.att.eelf.maven.wiki.Resource;


@SuppressWarnings("nls")
public class Helper {

    private static Class<?> eelfResourceManagerClass = null;
    private static Class<?> eelfResourceEnum = null;
    private static Object eelfResourceManager = null;
    static {
        try {
            eelfResourceManagerClass = Class.forName("com.att.eelf.i18n.EELFResourceManager");
            eelfResourceEnum = Class.forName("com.att.eelf.i18n.EELFResolvableResourceEnum");
            eelfResourceManager = Class.forName("com.att.eelf.i18n.EELFResourceManager").newInstance();
        } catch (Exception e) {
        }
    }

    /**
     * This method is called to process the resources for generation
     *
     * @param resource
     * @return
     */
    public static Class<?> processResources(Resource resource) {
        String className = validateResource(resource);
        if (className != null) {
            return processResource(className);
        }
        return null;
    }

    /**
     * This method is called to validate that a resource has been specified correctly.
     *
     * @param resource
     *            The resource to be validated
     * @return True if it is correct, false otherwise
     */
    private static String validateResource(Resource resource) {
        List<String> reasons = new ArrayList<String>();

        String className = resource.getMessageClass();
        if (className == null || className.length() == 0) {
            reasons.add("Message resource class name is null or empty.");
        }

        return className;
    }

    /**
     * @param resource
     *            The resource to be processed
     * @param first
     *            True if first time
     */
    private static Class<?> processResource(String enumClassName) {
        try {
            return Class.forName(enumClassName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            // String msg = String.format("Class %s cannot be loaded! (%s)", ex.getMessage());

        }
        return null;
    }

    /**
     * @param enumeration
     * @param object
     * @return
     */
    public static String getEnumName(Class<?> enumeration, Object object) {
        String value = null;
        try {
            value = ReflectionToStringBuilder.toString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * @param property
     * @param object
     * @return
     */
    public static String getUsingAccessor(String property, Object object) {
        String value = null;
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        try {
            Method method = eelfResourceManagerClass.getDeclaredMethod(methodName, new Class[] {
                eelfResourceEnum
            });
            if (method != null) {
                method.setAccessible(true);
                value = (String) method.invoke(eelfResourceManager, new Object[] {
                    object
                });
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return value;
    }
}
