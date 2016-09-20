/**
 * Copyright (c) 2016 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven.wiki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class TableEntry implements Comparable<TableEntry> {
    /**
     * The array of special characters to search for in the raw text, and the escape sequence to replace the text with.
     */
    private static final String[][] SPECIAL_STRINGS = {
        {
            "&", "&amp;"
        }
    };

    /**
     * This method is used to process the raw text and to replace any special characters in the text with the escaped
     * version of the character. This is used to prevent problems in rendering the displayed content on a web site.
     * 
     * @param raw
     *            The raw, un-escaped text
     * @return The processed text, with all special character sequences replaced with their escaped equivalents.
     */
    public static String processSpecialCharacters(String raw) {
        StringBuffer buffer = new StringBuffer(raw);
        Pattern escapePattern = Pattern.compile("^&[^&; ]+;");

        for (String[] special : SPECIAL_STRINGS) {

            Pattern pattern = Pattern.compile(special[0]);
            int position = 0;
            Matcher matcher = pattern.matcher(buffer);
            while (matcher.find(position)) {
                position = matcher.start();
                CharSequence subSequence = buffer.subSequence(position, buffer.length());
                Matcher escapeMatcher = escapePattern.matcher(subSequence);
                if (escapeMatcher.find()) {
                    position += escapeMatcher.end();
                    continue;
                }
                buffer.replace(position, position + special[0].length(), special[1]);
                position += special[1].length();
            }
        }

        return buffer.toString();
    }

    private String anchor;
    private String code;
    private String message;
    private String resolution;

    private String text;

    private TwikiUtilities utils = new TwikiUtilities();

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(TableEntry other) {
        return code.compareTo(other.code);
    }

    public String getAnchor() {
        return anchor;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getResolution() {
        return resolution;
    }

    public String getText() {
        return text;
    }

    public String renderCell(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(TwikiUtilities.renderCellStart());
        sb.append(TwikiUtilities.renderParagraphStart());
        sb.append(content);
        sb.append(TwikiUtilities.renderParagraphEnd());
        sb.append(TwikiUtilities.renderCellEnd());
        return sb.toString();
    }

    public String renderCodeCell() {
        return utils.renderAnchoredCell(getCode());
    }

    public String renderCodeTwiki() {
        return utils.renderAnchorBodyTwiki(getCode());
    }

    public String renderMessageTwiki() {
        return renderCell(getMessage());
    }

    public String renderResolutionTwiki() {
        return renderCell(getResolution());
    }

    public String renderSevCell() {
        String severity = getSeverityFromCode(getCode());
        return utils.renderAnchoredCell(severity);
    }

    public String renderSevTwiki() {
        String severity = getSeverityFromCode(getCode());
        return utils.renderAnchorBodyTwiki(severity);
    }

    public String renderTextTwiki() {
        return renderCell(getText());
    }

    public void setAnchor(String anchorText) {
        this.anchor = anchorText;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = processSpecialCharacters(message);
    }

    public void setResolution(String resolution) {
        this.resolution = processSpecialCharacters(resolution);
    }

    public void setText(String text) {
        this.text = processSpecialCharacters(text);
    }

    @Override
    public String toString() {
        return "ErrorDoco [anchor=" + anchor + ", code=" + code + ", text=" + text + ", message=" + message
            + ", resolution=" + resolution + "]";
    }

    /**
     * @param resource
     * @return
     */
    public String toTwiki(Resource resource) {
        StringBuilder sb = new StringBuilder();
        sb.append(TwikiUtilities.renderRowStart());
        if (resource.isCodeDefined()) {
            sb.append(renderCodeCell());
        }

        if (resource.isMessageDefined()) {
            sb.append(renderTextTwiki());
        }

        if (resource.isDescriptionDefined()) {
            sb.append(renderMessageTwiki());
        }

        if (resource.isResolutionDefined()) {
            sb.append(renderResolutionTwiki());
        }

        if (resource.isCodeDefined()) {
            sb.append(renderSevCell());
        }

        sb.append(TwikiUtilities.renderRowEnd());
        return sb.toString();
    }

    private String getSeverityFromCode(String code) {
        String sev = StringUtils.substring(code, code.length() - 1);
        String severity = null;
        if (StringUtils.equals(sev, "E")) {
            severity = "ERROR";
        }
        if (StringUtils.equals(sev, "W")) {
            severity = "WARNING";
        }
        if (StringUtils.equals(sev, "I")) {
            severity = "INFORMATION";
        }
        if (StringUtils.equals(sev, "D")) {
            severity = "DEBUG";
        }
        return severity;
    }
}
