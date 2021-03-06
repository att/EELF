/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven.wiki;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.att.eelf.maven.Helper;

/**
 * This class represents the contents of one message enumeration that is being processed by the wiki generator maven
 * plugin.
 *
 */
public class MessageTable {
    private static final String TABLE_HEADER_END = "</th>\n";
    private static final String TABLE_HEADER_START = "<th>\n";
    private static final String TABLE_HEADER_START_WITH_WIDTH = "<th width=\"%s\">\n";

    /**
     * The contents of the error table that we will be generating
     */
    private List<TableEntry> tableContents = new ArrayList<TableEntry>();
    private String footing = "";
    private String heading = "";
    private String name = "";

    /**
     * The defined resource we are processing from the plugin configuration
     */
    private Resource resource;

    /**
     * Default constructor
     *
     * @param resource
     *            the resource definition from the plugin configuration
     */
    public MessageTable(Resource resource) {
        this.resource = resource;
    }

    /**
     * @return The contents of the message table for this resource
     */
    public List<TableEntry> getTableContents() {
        return tableContents;
    }

    /**
     * @return the value of footing
     */
    public String getFooting() {
        return footing;
    }

    public String getHeading() {
        return heading;
    }

    public String getName() {
        return name;
    }

    /**
     * This method loads and parses the definition of the message resource (an enumeration) and constructs the table for
     * this resource.
     *
     * @param enumeration
     *            The enumeration class to be processed which defines the message resources for the application.
     * @throws IOException
     *             If the enumeration cannot be processed
     */
    public void parseEnumeration(Class<?> enumeration) throws IOException {
        setName(enumeration.getSimpleName());
        setHeading(resource.getHeader());
        setFooting(resource.getFooter());

        boolean generateCode = resource.isCodeDefined();
        boolean generateMessage = resource.isMessageDefined();
        boolean generateDescription = resource.isDescriptionDefined();
        boolean generateResolution = resource.isResolutionDefined();

        Object values[] = enumeration.getEnumConstants();
        for (Object value : values) {
            TableEntry entry = new TableEntry();
            if (generateCode) {
                entry.setCode(getValue(resource.getCodeProperty(), enumeration, value));
                entry.setAnchor(entry.getCode());
            }

            if (generateMessage) {
                entry.setText(getValue(resource.getMessageProperty(), enumeration, value));
            }

            if (generateDescription) {
                entry.setMessage(getValue(resource.getDescriptionProperty(), enumeration, value));
            }

            if (generateResolution) {
                entry.setResolution(getValue(resource.getResolutionProperty(), enumeration, value));
            }

            tableContents.add(entry);
        }

        Collections.sort(tableContents);
    }

    /**
     * @param tableContents
     *            The contents for this message table
     */
    public void setTableContents(List<TableEntry> tableContents) {
        this.tableContents = tableContents;
    }

    /**
     * @param footing
     *            the value for footing
     */
    public void setFooting(String footing) {
        this.footing = footing;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setName(String nameToUse) {
        this.name = nameToUse;
    }

    @Override
    public String toString() {
        return "MessageTable [name=" + name + ", resource=" + resource.getMessageClass() + "]";
    }

    public String toTwiki() {
        StringBuilder sb = new StringBuilder();
        sb.append(renderHeadingTwiki());
        sb.append(renderTableHeaderTwiki());
        for (TableEntry ed : tableContents) {
            sb.append(ed.toTwiki(resource));
        }
        sb.append(renderTableEnd());
        sb.append(renderFootingTwiki());
        return sb.toString();
    }

    /**
     * @param property
     * @param enumeration
     * @param object
     * @return
     */
    private String getUsingAccessor(String property, Class<?> enumeration, Object object) {
        String value = null;
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        try {
            Method method = enumeration.getDeclaredMethod(methodName, new Class[] {});
            if (method != null) {
                method.setAccessible(true);
                value = (String) method.invoke(object, new Object[] {});
            } else {
                Class<?> parent = enumeration.getSuperclass();
                if (!parent.getSimpleName().equals("Object")) {
                    return getUsingAccessor(property, parent, object);
                }
            }
        } catch (NoSuchMethodException e) {
            // ignore
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * @param property
     * @param enumeration
     * @param object
     * @return
     */
    private String getUsingField(String property, Class<?> enumeration, Object object) {
        String value = null;

        try {
            Field field = enumeration.getDeclaredField(property);
            if (field != null) {
                field.setAccessible(true);
                value = (String) field.get(object);
            } else {
                Class<?> parent = enumeration.getSuperclass();
                if (!parent.getSimpleName().equals("Object")) {
                    return getUsingField(property, parent, object);
                }
            }
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Object renderFootingTwiki() {
        String footing = getFooting();
        StringBuilder sb = new StringBuilder();

        if (footing != null && footing.length() > 0) {
            sb.append(TwikiUtilities.renderParagraphStart());
            sb.append("\n");
            sb.append(getFooting());
            sb.append(TwikiUtilities.renderParagraphEnd());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Creates the table header cell with an optional width specification
     *
     * @param contents
     * @param width
     * @return
     */
    private Object renderHeaderCell(String contents, String width) {
        StringBuilder sb = new StringBuilder();
        if (width != null) {
            sb.append(String.format(TABLE_HEADER_START_WITH_WIDTH, width));
        } else {
            sb.append(TABLE_HEADER_START);
        }
        sb.append(TwikiUtilities.renderParagraphStart());
        sb.append("<strong>");
        sb.append(contents);
        sb.append("</strong>\n");
        sb.append(TwikiUtilities.renderParagraphEnd());
        sb.append(TABLE_HEADER_END);
        return sb.toString();
    }

    private Object renderHeadingTwiki() {
        StringBuilder sb = new StringBuilder();
        sb.append(TwikiUtilities.renderParagraphStart());
        sb.append("\n");
        sb.append(getHeading());
        sb.append(TwikiUtilities.renderParagraphEnd());
        sb.append("\n");
        return sb.toString();
    }

    private Object renderTableEnd() {
        StringBuilder sb = new StringBuilder();
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        return sb.toString();
    }

    private String renderTableHeaderTwiki() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        sb.append("<tbody>\n");
        sb.append(TwikiUtilities.renderRowStart());
        if (resource.isCodeDefined()) {
            sb.append(renderHeaderCell(resource.getCodeHeading(), resource.getCodeWidth()));
        }

        if (resource.isMessageDefined()) {
            sb.append(renderHeaderCell(resource.getMessageHeading(), resource.getMessageWidth()));
        }

        if (resource.isDescriptionDefined()) {
            sb.append(renderHeaderCell(resource.getDescriptionHeading(), resource.getDescriptionWidth()));
        }

        if (resource.isResolutionDefined()) {
            sb.append(renderHeaderCell(resource.getResolutionHeading(), resource.getResolutionWidth()));
        }

        if (resource.isCodeDefined()) {
            sb.append(renderHeaderCell(resource.getSeverityHeading(), resource.getSeverityWidth()));
        }

        sb.append(TwikiUtilities.renderRowEnd());
        return sb.toString();
    }

    protected String getValue(String property, Class<?> enumeration, Object object) {
        String value = null;

        value = Helper.getUsingAccessor(property,  object);
        if (value == null) {
            value = getUsingField(property, enumeration, object);
        }

        return value;
    }

}
