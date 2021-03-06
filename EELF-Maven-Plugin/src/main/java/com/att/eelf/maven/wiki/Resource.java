/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven.wiki;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * This class represents the definition of a message resource to be generated for the wiki.
 * <p>
 * An example of the specification of a resource, including all optional elements, and all default values, is shown
 * below. The resource configuration allows the user to specify the class name of the resource to be processed, the
 * property names of the properties that provide the message code, message text, description, and resolution, and the
 * headings for these columns. Any properties that are empty are omitted from the output. For example, if the message
 * resource does not supply resolutions, the resolutions property can be set to an empty value and it will be suppressed
 * from the output.
 * </p>
 *
 * <pre>
 *    <resource>
 *      <messageClass>some.fully.qualified.ClassName</messageClass>
 *
 *      <codeProperty>code</codeProperty>                                               <!-- default -->
 *      <messageProperty>message</messageProperty>                                      <!-- default -->
 *      <descriptionProperty>description</descriptionProperty>                          <!-- default -->
 *      <resolutionProperty>resolution</resolutionProperty>                             <!-- default -->
 *      <codeHeading>Error Code</codeHeading>                                           <!-- default -->
 *      <codeWidth>10%</codeWidth>                                                      <!-- default -->
 *      <messageHeading>Message Text</messageHeading>                                   <!-- default -->
 *      <messageWidth>20%</messageWidth>                                                <!-- default -->
 *      <descriptionHeading>Detailed description</descriptionHeading>                   <!-- default -->
 *      <descriptionWidth>30%</descriptionWidth>                                        <!-- default -->
 *      <resolutionHeading>Suggested action(s) or resolution(s)</resolutionHeading>     <!-- default -->
 *      <resolutionWidth>30%</resolutionWidth>                                          <!-- default -->
 *      <severityHeading>Severity</severityHeading>                                     <!-- default -->
 *      <severityWidth>10%</severityWidth>                                              <!-- default -->
 *      <header></header>                                                               <!-- default -->
 *      <footer></footer>                                                               <!-- default -->
 *    </resource>
 * </pre>
 */
public class Resource {

    /**
     * The heading to be generated in the message documentation for the title of the code column
     */
    @Parameter(property = "codeHeading", defaultValue = "Error Code")
    protected String codeHeading = "Error Code";

    /**
     * The property name that contains the code of the resource. If not defined (null or empty), no table column for the
     * code will be created. If specified, the plugin will attempt to locate a field, or a getter for the property, with
     * this name to obtain the code.
     */
    @Parameter(property = "codeProperty", defaultValue = "identifier")
    protected String codeProperty = "identifier";

    /**
     * The desired width of the code column
     */
    @Parameter(property = "codeWidth", defaultValue = "10%")
    protected String codeWidth = "10%";

    /**
     * The heading to be generated in the message documentation for the title of the description column
     */
    @Parameter(property = "descriptionHeading", defaultValue = "Detailed description")
    protected String descriptionHeading = "Detailed description";

    /**
     * The property name that contains the description of the resource. If not defined (null or empty), no table column
     * for the description will be created. If specified, the plugin will attempt to locate a field, or a getter for the
     * property, with this name to obtain the description.
     */
    @Parameter(property = "descriptionProperty", defaultValue = "description")
    protected String descriptionProperty = "description";

    /**
     * The desired width of the message description column in the table
     */
    @Parameter(property = "descriptionWidth", defaultValue = "30%")
    protected String descriptionWidth = "30%";

    /**
     * An optional footer that is appended to the end of the generated contents.
     */
    @Parameter(property = "footer")
    protected String footer;

    /**
     * An optional header comment that is inserted prior to the generated contents
     */
    @Parameter(property = "header")
    protected String header;

    /**
     * The class name of the message resource to be generated
     */
    @Parameter(property = "messageClass")
    protected String messageClass;

    /**
     * The heading to be generated in the message documentation for the title of the message text column
     */
    @Parameter(property = "messageHeading", defaultValue = "Message Text")
    protected String messageHeading = "Message Text";

    /**
     * The property name that contains the message text of the resource. If not defined (null or empty), no table column
     * for the message text will be created. If specified, the plugin will attempt to locate a field, or a getter for
     * the property, with this name to obtain the message text.
     */
    @Parameter(property = "messageProperty", defaultValue = "message")
    protected String messageProperty = "message";

    /**
     * The desired width of the message text column in the table
     */
    @Parameter(property = "messageWidth", defaultValue = "20%")
    protected String messageWidth = "20%";

    /**
     * The heading to be generated in the message documentation for the title of the resolution column
     */
    @Parameter(property = "resolutionHeading", defaultValue = "Suggested action(s) or resolution(s)")
    protected String resolutionHeading = "Suggested action(s) or resolution(s)";

    /**
     * The property name that contains the resolution of the resource. If not defined (null or empty), no table column
     * for the resolution will be created. If specified, the plugin will attempt to locate a field, or a getter for the
     * property, with this name to obtain the resolution.
     */
    @Parameter(property = "resolutionProperty", defaultValue = "resolution")
    protected String resolutionProperty = "resolution";

    /**
     * The desired width of the suggested resolution column in the table
     */
    @Parameter(property = "resolutionWidth", defaultValue = "30%")
    protected String resolutionWidth = "30%";

    /**
     * The heading to be generated in the message documentation for the title of the severity code column
     */
    @Parameter(property = "severityHeading", defaultValue = "Severity")
    protected String severityHeading = "Severity";

    /**
     * The desired width of the severity code column
     */
    @Parameter(property = "severityWidth", defaultValue = "10%")
    protected String severityWidth = "10%";

    /**
     * @return the value of codeHeading
     */
    public String getCodeHeading() {
        return codeHeading;
    }

    /**
     * @return the value of code
     */
    public String getCodeProperty() {
        return codeProperty;
    }

    /**
     * @return the value of codeWidth
     */
    public String getCodeWidth() {
        return codeWidth;
    }

    /**
     * @return the value of descriptionHeading
     */
    public String getDescriptionHeading() {
        return descriptionHeading;
    }

    /**
     * @return the value of description
     */
    public String getDescriptionProperty() {
        return descriptionProperty;
    }

    /**
     * @return the value of descriptionWidth
     */
    public String getDescriptionWidth() {
        return descriptionWidth;
    }

    /**
     * @return the value of footer
     */
    public String getFooter() {
        return footer;
    }

    /**
     * @return the value of header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @return the value of message resource class name to be processed
     */
    public String getMessageClass() {
        return messageClass;
    }

    /**
     * @return the value of messageHeading
     */
    public String getMessageHeading() {
        return messageHeading;
    }

    /**
     * @return the value of message
     */
    public String getMessageProperty() {
        return messageProperty;
    }

    /**
     * @return the value of messageWidth
     */
    public String getMessageWidth() {
        return messageWidth;
    }

    /**
     * @return the value of resolutionHeading
     */
    public String getResolutionHeading() {
        return resolutionHeading;
    }

    /**
     * @return the value of resolution
     */
    public String getResolutionProperty() {
        return resolutionProperty;
    }

    /**
     * @return the value of resolutionWidth
     */
    public String getResolutionWidth() {
        return resolutionWidth;
    }

    /**
     * @return the value of severityHeading
     */
    public String getSeverityHeading() {
        return severityHeading;
    }

    /**
     * @return the value of severityWidth
     */
    public String getSeverityWidth() {
        return severityWidth;
    }

    /**
     * @return True if the code property has a defined value
     */
    public boolean isCodeDefined() {
        return codeProperty != null && codeProperty.trim().length() > 0;
    }

    /**
     * @return True if the description property has a defined value
     */
    public boolean isDescriptionDefined() {
        return descriptionProperty != null && descriptionProperty.trim().length() > 0;
    }

    /**
     * @return True if the message property has a defined value
     */
    public boolean isMessageDefined() {
        return messageProperty != null && messageProperty.trim().length() > 0;
    }

    /**
     * @return True if the resolution property has a defined value
     */
    public boolean isResolutionDefined() {
        return resolutionProperty != null && resolutionProperty.trim().length() > 0;
    }

    /**
     * @param codeHeading
     *            the value for codeHeading
     */
    public void setCodeHeading(String codeHeading) {
        this.codeHeading = codeHeading;
    }

    /**
     * @param code
     *            the value for code
     */
    public void setCodeProperty(String code) {
        this.codeProperty = code;
    }

    /**
     * @param codeWidth
     *            the value for codeWidth
     */
    public void setCodeWidth(String codeWidth) {
        this.codeWidth = codeWidth;
    }

    /**
     * @param descriptionHeading
     *            the value for descriptionHeading
     */
    public void setDescriptionHeading(String descriptionHeading) {
        this.descriptionHeading = descriptionHeading;
    }

    /**
     * @param description
     *            the value for description
     */
    public void setDescriptionProperty(String description) {
        this.descriptionProperty = description;
    }

    /**
     * @param descriptionWidth
     *            the value for descriptionWidth
     */
    public void setDescriptionWidth(String descriptionWidth) {
        this.descriptionWidth = descriptionWidth;
    }

    /**
     * @param footer
     *            the value for footer
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

    /**
     * @param header
     *            the value for header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @param messageClass
     *            the message resource class name being processed
     */
    public void setMessageClass(String messageClass) {
        this.messageClass = messageClass;
    }

    /**
     * @param messageHeading
     *            the value for messageHeading
     */
    public void setMessageHeading(String messageHeading) {
        this.messageHeading = messageHeading;
    }

    /**
     * @param message
     *            the value for message
     */
    public void setMessageProperty(String message) {
        this.messageProperty = message;
    }

    /**
     * @param messageWidth
     *            the value for messageWidth
     */
    public void setMessageWidth(String messageWidth) {
        this.messageWidth = messageWidth;
    }

    /**
     * @param resolutionHeading
     *            the value for resolutionHeading
     */
    public void setResolutionHeading(String resolutionHeading) {
        this.resolutionHeading = resolutionHeading;
    }

    /**
     * @param resolution
     *            the value for resolution
     */
    public void setResolutionProperty(String resolution) {
        this.resolutionProperty = resolution;
    }

    /**
     * @param resolutionWidth
     *            the value for resolutionWidth
     */
    public void setResolutionWidth(String resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    /**
     * @param severityHeading
     *            the value for severityHeading
     */
    public void setSeverityHeading(String severityHeading) {
        this.severityHeading = severityHeading;
    }

    /**
     * @param severityWidth
     *            the value for severityWidth
     */
    public void setSeverityWidth(String severityWidth) {
        this.severityWidth = severityWidth;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return String.format("Resource %s", messageClass);
    }

}
