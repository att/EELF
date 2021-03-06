/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.maven.wiki;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * This class is used to capture and manage the configuration of the wiki site where the output is to be placed.
 *
 */

public class Wiki {

    /**
     * The wiki credentials to authenticate to the API
     */
    @Parameter(property = "credentials")
    protected String credentials;

    /**
     * The wiki page name in the space
     */
    @Parameter(property = "page", defaultValue = "Messages and Codes")
    protected String page = "Messages and Codes";

    /**
     * The wiki principal to authenticate to the API
     */
    @Parameter(property = "principal")
    protected String principal;

    /**
     * The wiki space name
     */
    @Parameter(property = "space", defaultValue = "APP")
    protected String space = "APP";

    /**
     * The page title
     */
    @Parameter(property = "title", defaultValue = "Messages and Codes")
    protected String title = "Messages and Codes";

    /**
     * The wiki API url
     */
    @Parameter(property = "url")
    protected String url;

    /**
     * @return the value of credential
     */
    public String getCredentials() {
        return credentials;
    }

    /**
     * @return the value of page
     */
    public String getPage() {
        return page;
    }

    /**
     * @return the value of principal
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * @return the value of spaceName
     */
    public String getSpace() {
        return space;
    }

    /**
     * @return the value of pageTitle
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param credentials
     *            the value for credential
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     * @param page
     *            the value for page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @param principal
     *            the value for principal
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * @param spaceName
     *            the value for spaceName
     */
    public void setSpace(String spaceName) {
        this.space = spaceName;
    }

    /**
     * @param pageTitle
     *            the value for pageTitle
     */
    public void setTitle(String pageTitle) {
        this.title = pageTitle;
    }

    /**
     * @param url
     *            the value for url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
