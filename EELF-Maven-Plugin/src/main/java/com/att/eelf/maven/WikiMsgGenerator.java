/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.maven ;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.att.eelf.maven.wiki.MessageTable;
import com.att.eelf.maven.wiki.Resource;
import com.att.eelf.maven.wiki.Wiki;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

/**
 * This class implements a WikiGenerator to be used as a maven plugin to construct documentation of all messages and
 * codes for inclusion on the wiki.
 * <p>
 * This maven plugin is configured to dynamically load any number of message enumerations and process them. Each
 * enumeration class must implement the {@link ResolvableErrorEnum} interface. This interface defines the common
 * contract that any class requested must implement, and this plugin will check that it does.
 * </p>
 * <p>
 * Each message resource can be specifically named and a separate heading and/or footing provided. Additionally, the
 * output can specify the same file as any other definition. If more than one resource is named, the first will
 * overwrite the output file and all subsequent resources will append to that file.
 * </p>
 * <p>
 * In addition to an output file that can be specified, the file can optionally be uploaded to the wiki site as part of
 * the plugin process.
 * </p>
 *
 *
 */

@Mojo(name = "WikiMsgGenerator")
@SuppressWarnings("nls")
public class WikiMsgGenerator extends AbstractMojo  {


    /**
     * The output directory where the generated results are stored
     */
    @Parameter(property = "outputDirectory", defaultValue = "target/messages")
    protected String outputDirectory = "target/messages";

    /**
     * The output file name the results are stored into
     */
    @Parameter(property = "outputFile", defaultValue = "cdp_messages.html")
    protected String outputFile = "cdp_messages.html";

    /**
     * The configuration of the wiki page where the output is to be placed, if defined
     */
    @Parameter(property = "wiki")
    protected Wiki wiki;

    /**
     * A list of class names to be loaded and processed that represent message definitions. Each of these classes must
     * implement the {@link com.att.eelf.i18n.ResolvableErrorEnum} interface, and must be enumerations of the required
     * format.
     */
    @Parameter(property = "resources")
    private List<Resource> resources;

    /**
     * A random number generator to use for generating unique id's
     */
    private static final Random RANDOM = new java.util.Random();

    /**
     * The maximum number randomly generated
     */
    private static final int MAX_BOUND = 999;

    /**
     * An optional Http proxy host
     */
    private String proxyHost;

    /**
     * An optional http proxy host port
     */
    private int proxyPort = 8080;

    /**
     * This method is called by Maven when the plugin is executed.
     * <p>
     * This method will first verify that at least one resource has been defined. Any number of resources may be
     * defined, and in any order. However, at least one is required.
     * </p>
     *
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Started generating wiki documentation...");
        if (resources == null || resources.isEmpty()) {
            getLog().error("No resources were defined to generate, generation skipped");
        } else {
            processResources();
            getLog().info("Finished generating wiki documentation...");
            /*if (wiki != null) {
                getLog().info("Uploading generated wiki documentation...");
                upload();
                getLog().info("Uploaded generated wiki documentation...");
            }*/
        }
        getLog().info("Completed generating wiki documentation...");
    }



    /**
     * This method is called to process the resources for generation
     */
    private void processResources() {
        boolean first = true;
        for (Resource resource : resources) {
            if (validateResource(resource)) {
                processResource(resource, first);
                first = false;
            }
        }
    }

    /**
     * This method is called to validate that a resource has been specified correctly.
     *
     * @param resource
     *            The resource to be validated
     * @return True if it is correct, false otherwise
     */
    private boolean validateResource(Resource resource) {
        Boolean result = true;
        List<String> reasons = new ArrayList<String>();

        String className = resource.getMessageClass();

        if (className == null || className.length() == 0) {
            result = false;
            reasons.add("Message resource class name is null or empty.");
        }

        if (outputDirectory == null || outputDirectory.length() == 0) {
            outputDirectory = ".";
        }
        File dir = new File(outputDirectory);
        try {
            dir.getCanonicalPath();
        } catch (IOException e) {
            result = false;
            reasons
                .add(String.format("Output directory cannot be converted to a valid path name - %s", e.getMessage()));
        }

        File file = new File(outputFile);
        try {
            file.getCanonicalFile();
        } catch (IOException e) {
            result = false;
            reasons.add(String.format("Output file cannot be converted to a valid name - %s", e.getMessage()));
        }

        if (!result) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(String.format("Validation of resource %s failed, reason(s) are:\n", className));
            for (String reason : reasons) {
                buffer.append(String.format("\t%s\n", reason));
            }
            getLog().error(buffer.toString());
        }

        return result;
    }

    /**
     * @param resource
     *            The resource to be processed
     * @param first
     *            True if first time
     */
    private void processResource(Resource resource, boolean first) {
        Class<?> enumClass;
        try {
            enumClass = Class.forName(resource.getMessageClass());
            getLog().info("the enumclass is "+enumClass.getCanonicalName());
            generateContentForResource(resource, enumClass, first);
        } catch (ClassNotFoundException ex) {
            String msg = String.format("Class %s cannot be loaded! (%s)", resource.getMessageClass(), ex.getMessage());
            getLog().error(msg);
        }

    }

    /**
     * @param resource
     *            The resource to be processed
     * @param resourceClass
     *            The class of the resource to be processed
     * @param first
     *            True if first time
     */
    private void generateContentForResource(Resource resource, Class<?> resourceClass, boolean first) {
        MessageTable table = new MessageTable(resource);
        try {
            table.parseEnumeration(resourceClass);
            String content = table.toTwiki();
            File directory = new File(getOutputDirectory());
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    String msg = String.format("Unable to create output directory %s", getOutputDirectory());
                    getLog().error(msg);
                    return;
                }
            }
            File output = new File(directory, getOutputFile());
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(output, !first))) {
                IOUtils.write(content, stream);
            } catch (FileNotFoundException e) {
                String msg =
                    String.format("The output file %s exists but is a directory rather "
                        + "than a regular file, does not exist but cannot be created, "
                        + "or cannot be opened for any other reason.", output.getAbsolutePath());
                getLog().error(msg);
                getLog().debug(e);
            }
        } catch (IOException e) {
            String msg =
                String.format("Unable to generate wiki documentation for resource %s, exception %s, message=%s.",
                    resource.getMessageClass(), e.getClass().getSimpleName(), e.getMessage());
            getLog().error(msg);
            getLog().debug(e);
        }
    }

    /**
     * @return The unique id
     */
    public static String generateUniqueId() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhmmss");
        return fmt.format(new java.util.Date()) + RANDOM.nextInt(MAX_BOUND);
    }

    /**
     * @return the value of outputDirectory
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @param outputDirectory
     *            the value for outputDirectory
     */
    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * @return the value of outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile
     *            the value for outputFile
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @return the value of wiki
     */
    public Wiki getWiki() {
        return wiki;
    }

    /**
     * @param wiki
     *            the value for wiki
     */
    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    /**
     * @return the value of resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * @param resources
     *            the value for resources
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    /**
     * This method is used to upload the generated contents to the specified wiki site
     */
    private void upload() {
        Client client = connect();
        WebResource resource = client.resource(wiki.getUrl());
    }

    /**
     * Creates and returns a client configured to connect to the specified Wiki api
     *
     * @return The client to connect to the wiki
     */
    private Client connect() {
        Client client;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);

        /*
         * DLH: Added code to detect if an HTTP proxy has been defined and to use it. The code looks for a configuration
         * property of "http.proxyHost" and "http.proxyPort" as the highest level of precedence. If that is not defined,
         * it looks in the environment table (system environment variables) to see if "HTTP_PROXY" has been defined. If
         * it has, then it is parsed and used. If no proxy has been defined, then the connection is a direct connection
         * to the url.
         */
        proxyHost = System.getProperty("http.proxyHost");
        proxyPort = 8080;
        try {
            proxyPort = Integer.parseInt(System.getProperty("http.proxyPort"));
        } catch (NumberFormatException e) {
            // ignore
        }

        if (proxyHost == null) {
            proxyHost = System.getenv("HTTP_PROXY");
            if (proxyHost != null) {
                Pattern pattern = Pattern.compile("http[s]?://([^:/]+):([0-9]+){0,1}.*", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(proxyHost);
                if (matcher.matches()) {
                    proxyHost = matcher.group(1);
                    if (matcher.group(2) != null) {
                        try {
                            proxyPort = Integer.parseInt(matcher.group(2));
                        } catch (NumberFormatException e) {
                            proxyPort = Integer.valueOf(8080);
                        }
                    }
                } else {
                    getLog().error(String.format("Invalid HTTP_PROXY URL: \"%s\" Cannot be parsed", proxyHost));
                    proxyHost = null;
                }
            }
        }

        /*
         * If no proxy host+port was defined, then use a direct connection. Otherwise, set up the connection via the
         * proxy.
         */
        if (proxyHost == null) {
            getLog().debug("Connecting without using a proxy");
            client = Client.create(clientConfig);
        } else {
            getLog().debug(String.format("Connecting using proxy %s:%d", proxyHost, proxyPort));
            client = new Client(new URLConnectionClientHandler(new HttpURLConnectionFactory() {
                private Proxy p = null;

                @Override
                public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
                    if (p == null) {
                        if (proxyHost != null) {
                            p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                        } else {
                            p = Proxy.NO_PROXY;
                        }
                    }
                    return (HttpURLConnection) url.openConnection(p);
                }
            }), clientConfig);
        }

        return client;
    }
}
