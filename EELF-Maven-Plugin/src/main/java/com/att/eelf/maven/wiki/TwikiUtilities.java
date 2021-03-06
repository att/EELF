/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven.wiki;

public class TwikiUtilities {
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

    public static String renderCellEnd() {
        return CELL_END;
    }

    public static String renderCellStart() {
        return CELL_START;
    }

    public static String renderParagraphEnd() {
        return PARAGRAPH_END;
    }

    public static String renderParagraphStart() {
        return PARAGRAPH_START;
    }

    public static String renderRowEnd() {
        return ROW_END;
    }

    public static String renderRowStart() {
        return ROW_START;
    }

    public String renderAcDefaultParameter() {
        return AC_DEFAULT_PARAMETER;
    }

    public String renderAcDefaultParameter2() {
        return AC_DEFAULT_PARAMETER2;
    }

    public String renderAnchorBodyTwiki(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(AC_DEFAULT_PARAMETER2);
        sb.append(content);
        sb.append(AC_DEFAULT_PARAMETER);
        return sb.toString();
    }

    public String renderAnchoredCell(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(CELL_START);
        sb.append(renderAnchorTwiki(content));
        sb.append(content);
        sb.append(CELL_END);
        return sb.toString();
    }

    public String renderAnchorEndTwiki() {
        return ANCHOR_END_TWIKI;
    }

    public String renderAnchorStartTwiki() {
        return ANCHOR_START_TWIKI;
    }

    public String renderAnchorTwiki(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(ANCHOR_START_TWIKI);
        sb.append(renderAnchorBodyTwiki(content));
        sb.append(ANCHOR_END_TWIKI);
        return sb.toString();
    }

    public String renderTableCellTwiki(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(CELL_START);
        sb.append(PARAGRAPH_START);
        sb.append(content);
        sb.append(PARAGRAPH_END);
        sb.append(CELL_END);
        return sb.toString();
    }
}
