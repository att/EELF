/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */
package com.att.eelf.i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.MDC;
import static com.att.eelf.configuration.Configuration.MDC_ERROR_CODE;
import static com.att.eelf.configuration.Configuration.MDC_ERROR_DESC;

/**
 * The resource manager is used to retrieve and format resources from pluggable
 * resource bundles and is capable of managing sets of bundles over multiple
 * locales for message skeletons, message descriptions, message resolutions, and
 * just basic resources.
 * <p>
 * Resource Bundles are pluggable definitions of any type of resource that may
 * be needed by the application and can be externalized. In a typical GUI
 * application, for example, resources may be things like icons, bitmaps,
 * cursors, or other graphic character definitions. In many cases, resources are
 * also the messages that are issued by an application.
 * </p>
 * <p>
 * By externalizing the resources, and then providing a mechanism to load
 * different versions of the resources, the application can be made to support
 * multiple different languages and formatting customs. This is the essential
 * mechanism in java to support internationalization (i18n). The key concept in
 * the java support is that of a <em>resource bundle</em>. A resource bundle is
 * a set, or collection, of resources identified by a key, and where each bundle
 * represents a set of the same resource keys but with values specific to a
 * particular language or region. For example, a message with the resource key
 * of "ERROR_123" could have an entry in a US-English bundle as well as a
 * France-French bundle. Bundles are usually identified by both a language and
 * region code, although that is not required. For example, a bundle specifying
 * only the French language would be used for Canada French as well as France.
 * </p>
 * <p>
 * This class supports the loading of multiple types of resource bundles and the
 * formatting of messages from those bundles. A default bundle is loaded based
 * on the server locale for use by server-side implementation. However, for
 * client-side implementations, this resource manager also allows the locale to
 * be specified and a resource obtained from a locale-specific bundle. This
 * allows the code to format messages and resources appropriate to the client,
 * and not just the server.
 * </p>
 * <p>
 * This ResourceManager also allows for additional bundles to be loaded to
 * support bundles that may be needed for specific providers or extensions that
 * may be loaded dynamically. In these cases, these provider implementations can
 * request that their bundles be loaded and managed as part of the overall
 * resources. It is important to have each provider or extension identify their
 * resources using unique keys, because the resource manager will format the
 * first resource it finds using the specified key by scanning the bundles. If
 * two (or more) bundles define resources with the same key, the first
 * occurrence found will be used.
 * </p>
 * <p>
 * lastly, this resource manager has the ability to manage multiple different
 * sets of resource bundles, segregated from each other to support different
 * uses. for example, a set of bundles can be loaded for the messages, while
 * another set can be loaded for the resolutions, and yet another set loaded for
 * the descriptions. this support can be extended to support any number of
 * different sets of resource bundles in the future if needed.
 * </p>
 *
 */
public final class EELFResourceManager {

	/**
	 * Error message to be generated if a resource is requested that cannot be
	 * formatted because of invalid/illegal arguments
	 */
	private static final String BAD_ARGUMENTS = "EELF9997E Invalid arguments to format resource id [%s]!\n";

	/**
	 * The message to be generated if a resource is requested that does not
	 * exist in the bundle
	 */
	private static final String BAD_RESOURCE = "EELF9998E Resource id [%s] cannot be formatted - no resource with that id exists!\n";

	/**
	 * Error message that prefixes the partial stack trace when a resource
	 * cannot be formatted
	 */
	private static final String CALLED_FROM = "Request for resource was made from:\n";

	/**
	 * The set of all description bundle base names that have been requested to
	 * be loaded
	 */
	private static List<String> descriptionBaseNames = new ArrayList<String>();

	/**
	 * The set of all description resource bundles loaded for specific locales.
	 * The key of the map is a locale that has been requested. The first time a
	 * locale is requested, each bundle based on the set of base names, if they
	 * can be found, is loaded and inserted into the map. Each subsequent call
	 * will then return resources from the first bundle that contains the
	 * resource. If a different locale is requested that has not yet been
	 * loaded, then it is also loaded on first use. If no bundle can be found
	 * for the locale, the default bundle is loaded and used.
	 */
	private static Map<String, Map<String, ResourceBundle>> descriptionBundles = new HashMap<String, Map<String, ResourceBundle>>();

	/**
	 * The set of all messages bundle base names that have been requested to be
	 * loaded
	 */
	private static List<String> messageBaseNames = new ArrayList<String>();

	/**
	 * The set of all message resource bundles loaded for specific locales. The
	 * key of the map is a locale that has been requested. The first time a
	 * locale is requested, each bundle based on the set of base names, if they
	 * can be found, is loaded and inserted into the map. Each subsequent call
	 * will then return resources from the first bundle that contains the
	 * resource. If a different locale is requested that has not yet been
	 * loaded, then it is also loaded on first use. If no bundle can be found
	 * for the locale, the default bundle is loaded and used.
	 */
	private static Map<String, Map<String, ResourceBundle>> messageBundles = new HashMap<String, Map<String, ResourceBundle>>();

	/**
	 * The message to be generated if a format is requested and no resource
	 * bundle was loaded
	 */
	private static final String NO_BUNDLE = "EELF9999E Resource id [%s] cannot be formatted - no bundle loaded!\n";

	/**
	 * The set of all resolution bundle base names that have been requested to
	 * be loaded
	 */
	private static List<String> resolutionBaseNames = new ArrayList<String>();

	/**
	 * The set of all resolution resource bundles loaded for specific locales.
	 * The key of the map is a locale that has been requested. The first time a
	 * locale is requested, each bundle based on the set of base names, if they
	 * can be found, is loaded and inserted into the map. Each subsequent call
	 * will then return resources from the first bundle that contains the
	 * resource. If a different locale is requested that has not yet been
	 * loaded, then it is also loaded on first use. If no bundle can be found
	 * for the locale, the default bundle is loaded and used.
	 */
	private static Map<String, Map<String, ResourceBundle>> resolutionBundles = new HashMap<String, Map<String, ResourceBundle>>();

	private static String delimiReqularExp = "\\|";

	private static enum RESOURCE_TYPES {
		code,
		msg,
		desc,
		resolution;
	}

	/**
	 * This is the actual formatter used to format the resource with the
	 * supplied arguments, if any. The name of the method has a leading
	 * underscore to indicate that it is an internal method not to be directly
	 * called by any client code.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param identifier The message identifier, if one is to be assigned to the
	 *            message, or null.
	 * @param resourceId The resource id to be formatted
	 * @param exception an optional exception to be formatted
	 * @param arguments The arguments inserted into the resource, if any
	 * @return The formatted resource
	 */
	private static String format(Locale locale, String identifier, String resourceId,
			Throwable exception,
			String... arguments) {
		String skeleton = getMessage(locale, resourceId);

		StringBuffer buffer = new StringBuffer();
		try {
			if (identifier != null) {
				buffer.append(identifier.toUpperCase());
				buffer.append(" ");
			}
			buffer.append(MessageFormat.format(skeleton, (Object[]) arguments));

			if (exception != null) {
				buffer.append(
						String.format("\nException %s: %s", exception.getClass().getSimpleName(),
								exception.getMessage()));
				StackTraceElement[] stack = exception.getStackTrace();
				buffer.append("\n" + formatPartialStackTrace(stack, 0, 5));
			}

		} catch (IllegalArgumentException e) {
			return String.format(BAD_ARGUMENTS, resourceId);
		}
		return buffer.toString();
	}

	/**
	 * Format a cause (nested exception). The formatted cause is appended to the
	 * current buffer.
	 *
	 * @param t The cause to be formatted.
	 * @param buffer The buffer to append the formatted output to.
	 */
	private static void formatCause(Throwable t, StringBuffer buffer) {
		buffer.append("Caused by: " + t.getClass().getName() + ": " + t.getMessage() + "\n");
		formatStackFrames(t, buffer);
		Throwable cause = t.getCause();
		if (cause != null) {
			formatCause(cause, buffer);
		}
	}

	/**
	 * Format a stack trace
	 *
	 * @param t The throwable containing the stack trace to be formatted
	 * @param buffer The buffer to append the stack trace output to
	 */
	private static void formatStackFrames(Throwable t, StringBuffer buffer) {
		StackTraceElement[] stack = t.getStackTrace();
		buffer.append(formatPartialStackTrace(stack, 0, 12));
	}

	/**
	 * This is a private method used to actually return a resource from a map of
	 * bundles loaded for a specific purpose.
	 *
	 * @param resourceId The resource ID that we are looking for
	 * @param bundles A map of the bundles loaded for the specific locale
	 * @return the specified resource, or if no bundles are loaded, a default
	 *         message. If the resource cannot be found, then a diagnostic
	 *         message is returned indicating the failure to load the resource
	 *         and where it was called from.
	 */
	private static String getResourceTemplateFromBundle(String resourceId,
			Map<String, ResourceBundle> bundles, RESOURCE_TYPES type) {
		String output = null;
		if (bundles == null) {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			return String.format(NO_BUNDLE, resourceId) + CALLED_FROM
					+ formatPartialStackTrace(stack, 3, 5);
		} else {
			for (Map.Entry<String, ResourceBundle> entry : bundles.entrySet()) {
				try {
					String str = entry.getValue().getString(resourceId);
					if (str != null) {
						String[] values = str.split(delimiReqularExp);
						switch (type) {
						case code:
							if (values.length >= 1)
								output = values[0];
							break;
						case msg:
							if (values.length >= 2)
								output = values[1];
							break;
						case resolution:
							if (values.length >= 3)
								output = values[2];
							break;
						case desc:
							if (values.length >= 4)
								output = values[3];
							break;
						default:
							output = entry.getValue().getString(resourceId);
						}

					}
					return output;
				} catch (MissingResourceException e) {
					continue;
				}
			}
		}

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		return String.format(BAD_RESOURCE, resourceId) + CALLED_FROM
				+ formatPartialStackTrace(stack, 4, 9);
	}

	/**
	 * This method is a helper that allows a caller to format a variable set of
	 * strings as a list of the form "[value,...,value]"
	 *
	 * @param values The variable argument list of string values to be formatted
	 *            as a list
	 * @return The list of values
	 */
	public static String asList(String... values) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (String value : values) {
			buffer.append(value);
			buffer.append(",");
		}
		if (buffer.length() > 1) {
			buffer.delete(buffer.length() - 1, buffer.length());
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The resource to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String format(Locale locale, EELFResolvableResourceEnum resourceId,
			String... arguments) {
		return format(locale, getIdentifier(resourceId), resourceId.toString(), null, arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The resource to be formatted
	 * @param exception The exception to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String format(Locale locale, EELFResolvableResourceEnum resourceId,
			Throwable exception, String... arguments) {
		return format(locale, getIdentifier(resourceId), resourceId.toString(), exception,
				arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param resourceId The resource to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String format(EELFResolvableResourceEnum resourceId, String... arguments) {
		Locale locale = Locale.getDefault();
		return format(locale, getIdentifier(resourceId), resourceId.toString(), null, arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param resourceId The resource to be formatted
	 * @param exception the exception to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String format(EELFResolvableResourceEnum resourceId, Throwable exception,
			String... arguments) {
		Locale locale = Locale.getDefault();
		return format(locale, resourceId, exception, arguments);
	}

	/**
	 * This method formats an exception object (throwable) in a standard way and
	 * returns the result as a string. This enables the exception to be written
	 * to a log file easily.
	 *
	 * @param t The throwable to be formatted
	 * @return The formatted exception
	 */
	public static String format(Throwable t) {
		StringBuffer buffer = new StringBuffer();
		Thread currentThread = Thread.currentThread();

		buffer.append("Exception in thread " + currentThread.getName() + " "
				+ t.getClass().getName() + ": "
				+ t.getMessage() + "\n");
		formatStackFrames(t, buffer);
		Throwable cause = t.getCause();
		if (cause != null) {
			formatCause(cause, buffer);
		}

		return buffer.toString();
	}

	// TODO The format(throwable) currently only supports formatting of
	// exceptions in en_US (the default based on the
	// encoding used to create the source file. In the future, this could/should
	// be changed to format exceptions using a
	// specified locale if needed. This is a low priority, as exception
	// formatting is likely a server-side output only,
	// and the end user would not be aware of these outputs.

	/**
	 * This method formats an exception object (throwable) in a standard way and
	 * returns the result as a string. This enables the exception to be written
	 * to a log file easily.
	 *
	 * @param t The throwable to be formatted
	 * @param contextMsg Message to provide application context of this
	 *            exception
	 * @return The formatted exception
	 */
	public static String format(Throwable t, String contextMsg) {
		StringBuffer buffer = new StringBuffer();
		Thread currentThread = Thread.currentThread();

		buffer.append("Exception in thread " + currentThread.getName() + " "
				+ t.getClass().getName() + ": "
				+ t.getMessage() + " [contextMsg:" + contextMsg + "]\n");
		formatStackFrames(t, buffer);
		Throwable cause = t.getCause();
		if (cause != null) {
			formatCause(cause, buffer);
		}

		return buffer.toString();
	}

	/**
	 * Generates a partial stack trace showing the path taken to get to the
	 * caller. This method is not shown in the stack trace and does not count
	 * toward the limit number.
	 *
	 * @trace The stack trace array
	 * @param start the index into the array where the stack trace is to be
	 *            started...
	 * @param limit The maximum number of entries to display in the stack
	 *            trace...
	 * @return A string formatted result of the partial stack trace
	 */
	private static String formatPartialStackTrace(StackTraceElement[] trace, int start, int limit) {
		StringBuffer buffer = new StringBuffer();

		if (trace != null && trace.length > start) {
			for (int i = start; i < trace.length && i <= limit; i++) {
				StackTraceElement frame = trace[i];
				buffer.append("    at " + frame.getClassName() + "." + frame.getMethodName() + "("
						+ frame.getFileName() + ":" + frame.getLineNumber() + ")\n");
			}
			if (limit < trace.length) {
				buffer.append(
						"    ... " + Integer.toString(trace.length - limit) + " more frames\n");
			}
		}
		return buffer.toString();
	}

	/**
	 * Obtains the specified description from description resource bundles that
	 * are loaded based on the resource id.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The EELFResolvableResourceEnum to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found, or a default error message
	 *         indicating that no bundles could be loaded.
	 */
	public static String getDescription(Locale locale, EELFResolvableResourceEnum resourceId) {
		Map<String, ResourceBundle> localBundles = getMessageBundle(locale, descriptionBundles,
				descriptionBaseNames);
		return getResourceTemplateFromBundle(resourceId.toString(), localBundles,
				RESOURCE_TYPES.desc);
	}

	/**
	 * This is a convenience method that returns the message description
	 * indicated by the specified id, or key, in the resource bundle.
	 *
	 * @param resourceId The EELFResolvableResourceEnum to be located
	 * @return The requested description, or a default message indicating that
	 *         the resource id could not be found.
	 */
	public static String getDescription(EELFResolvableResourceEnum resourceId) {
		return getDescription(Locale.getDefault(), resourceId);
	}

	/**
	 * Returns the message resource indicated by the specified id, or key, in
	 * the first message resource bundle loaded for the locale that contains the
	 * resource. If no bundle can be found that contains the resource, a
	 * "BAD_RESOURCE" resource is returned. If no bundles can be found for the
	 * specified locale, including the default bundle, then a "NO_BUNDLE" error
	 * resource is returned.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The resource to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found, or a default error message
	 *         indicating that no bundles could be loaded.
	 */
	public static String getMessage(Locale locale, String resourceId) {
		Map<String, ResourceBundle> localBundles = getMessageBundle(locale, messageBundles,
				messageBaseNames);
		return getResourceTemplateFromBundle(resourceId, localBundles, RESOURCE_TYPES.msg);
	}

	/**
	 * This is a convenience method that returns the resource indicated by the
	 * specified id, or key, in the resource bundle.
	 *
	 * @param resourceId The resource to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found.
	 */
	public static String getMessage(String resourceId) {
		return getMessage(Locale.getDefault(), resourceId);
	}

	/**
	 * This is a method that returns the resource indicated by the specified id,
	 * or key, in the resource bundle.
	 *
	 * @param resourceId The resource to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found.
	 */
	public static String getMessage(EELFResolvableResourceEnum resourceId) {
		return getMessage(Locale.getDefault(), resourceId.toString());
	}

	/**
	 * This is a method that returns the resource indicated by the specified id,
	 * or key, in the resource bundle.
	 *
	 * @param resourceId The resource to be located
	 * @param arguments The arguments to the message
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found.
	 */
	public static String getMessage(EELFResolvableResourceEnum resourceId, String... arguments) {
		return format(Locale.getDefault(), resourceId, arguments);
	}

	/**
	 * This method is used to load resource bundles by locale and cache them. It
	 * can be called any number of times, but will load a specific resource
	 * bundle for a specific locale only once.
	 *
	 * @param locale The locale to be used to locate the appropriate bundle
	 * @param bundles The map of maps of bundles by locales. The outer map is
	 *            keyed by locale. The inner map is keyed by bundle base names,
	 *            which allows multiple bundles to be loaded and searched for
	 *            the same locale, allowing bundles to be segregated by
	 *            functional group or other uses.
	 * @return The ResourceBundle that applies to the given locale
	 */
	private static Map<String, ResourceBundle> getMessageBundle(Locale locale,
			Map<String, Map<String, ResourceBundle>> bundles, List<String> baseNames) {
		synchronized (messageBundles) {
			List<String> failed = new ArrayList<String>();
			Map<String, ResourceBundle> bundleMap = bundles.get(locale.toLanguageTag());
			if (bundleMap == null) {
				bundleMap = new HashMap<String, ResourceBundle>();
				String languageTag = locale.toLanguageTag();
				messageBundles.put(languageTag, bundleMap);
				for (String baseName : baseNames) {
					if (!loadResourceBundle(bundleMap, baseName, locale)) {
						failed.add(baseName);
					}
				}
			} else {
				if (!bundleMap.keySet().containsAll(baseNames)) {
					HashSet<String> differences = new HashSet<String>(baseNames);
					differences.removeAll(bundleMap.keySet());
					for (String baseName : differences) {
						if (!loadResourceBundle(bundleMap, baseName, locale)) {
							failed.add(baseName);
						}
					}
				}
			}

			if (!failed.isEmpty()) {
				for (String baseName : failed) {
					messageBaseNames.remove(baseName);
				}
			}
			return bundleMap;
		}
	}

	/**
	 * Obtains the specified resolution from the resource bundles that are
	 * loaded based on the resource id.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resource The resource to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found, or a default error message
	 *         indicating that no bundles could be loaded.
	 */
	public static String getResolution(Locale locale, EELFResolvableResourceEnum resource) {
		Map<String, ResourceBundle> localBundles = getMessageBundle(locale, resolutionBundles,
				resolutionBaseNames);
		return getResourceTemplateFromBundle(resource.toString(), localBundles,
				RESOURCE_TYPES.resolution);
	}

	/**
	 * This is a convenience method that returns the message resolution
	 * indicated by the specified id, or key, in the resource bundle.
	 *
	 * @param resource The resource to be located
	 * @return The requested resolution, or a default message indicating that
	 *         the resource id could not be found.
	 */
	public static String getResolution(EELFResolvableResourceEnum resource) {
		return getResolution(Locale.getDefault(), resource);
	}

	/**
	 * Called to request that a specified description bundle base name be added
	 * to the set of resources managed by the resource manager.
	 *
	 * @param baseName The bundle base name to be added to the set of bundle
	 *            base names that are being managed.
	 */
	public static void loadDescriptionBundle(String baseName) {
		if (!descriptionBaseNames.contains(baseName)) {
			descriptionBaseNames.add(baseName);
		}
	}

	/**
	 * Called to request that a specified message bundle base name be added to
	 * the set of resources managed by the resource manager.
	 *
	 * @param baseName The bundle base name to be added to the set of bundle
	 *            base names that are being managed.
	 */
	public static void loadMessageBundle(String baseName) {
		if (!messageBaseNames.contains(baseName)) {
			messageBaseNames.add(baseName);
		}
		loadDescriptionBundle(baseName);
		loadResolutionBundle(baseName);
	}

	/**
	 * Called to request that a specified resolution bundle base name be added
	 * to the set of resources managed by the resource manager.
	 *
	 * @param baseName The bundle base name to be added to the set of bundle
	 *            base names that are being managed.
	 */
	public static void loadResolutionBundle(String baseName) {
		if (!resolutionBaseNames.contains(baseName)) {
			resolutionBaseNames.add(baseName);
		}
	}

	/**
	 * Load the specified resource bundle identified by the base name and
	 * locale, and insert it into the provided map, where the key is the bundle
	 * base name. The map provided will be specific to the indicated locale.
	 *
	 * @param bundleMap the locale-specific HashMap to contain all loaded
	 *            resource bundles for that locale
	 * @param baseName The base name of the bundle to be loaded
	 * @param locale The locale to load the bundle for
	 * @return True if the bundle was loaded, false if it failed
	 */
	private static boolean loadResourceBundle(Map<String, ResourceBundle> bundleMap,
			String baseName, Locale locale) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
			bundleMap.put(baseName, bundle);
			return true;
		} catch (MissingResourceException e) {
			System.err.println(
					String.format("Unable to load resource bundle %s for locale %s", baseName,
							locale.toLanguageTag()));
		}
		return false;
	}

	/**
	 * A private default constructor to prevent anyone else from instantiating
	 * the object. We always load the default bundle identified by the default
	 * base name. We can optionally load and manage additional bundles as well.
	 */
	private EELFResourceManager() {
	}

	/**
	 * This is a convenience method that returns the message error code
	 * indicated by the specified id, or key, in the resource bundle.
	 *
	 * @param resourceId The EELFResolvableResourceEnum to be located
	 * @return The requested description, or a default message indicating that
	 *         the resource id could not be found.
	 */
	public static String getIdentifier(EELFResolvableResourceEnum resourceId) {
		return getIdentifier(Locale.getDefault(), resourceId);
	}

	/**
	 * Obtains the specified description from description resource bundles that
	 * are loaded based on the resource id.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The EELFResolvableResourceEnum to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found, or a default error message
	 *         indicating that no bundles could be loaded.
	 */
	public static String getIdentifier(Locale locale, EELFResolvableResourceEnum resourceId) {
		Map<String, ResourceBundle> localBundles = getMessageBundle(locale, messageBundles,
				messageBaseNames);
		return getResourceTemplateFromBundle(resourceId.toString(), localBundles,
				RESOURCE_TYPES.code);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param resourceId The resource to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String formatError(EELFResolvableResourceEnum resourceId, String... arguments) {
		Locale locale = Locale.getDefault();
		return formatError(locale, getIdentifier(resourceId), resourceId.toString(), null,
				arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The resource to be formatted
	 * @param exception The exception to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String formatError(Locale locale, EELFResolvableResourceEnum resourceId,
			Throwable exception, String... arguments) {
		return formatError(locale, getIdentifier(resourceId), resourceId.toString(), exception,
				arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param resourceId The resource to be formatted
	 * @param exception the exception to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String formatError(EELFResolvableResourceEnum resourceId, Throwable exception,
			String... arguments) {
		Locale locale = Locale.getDefault();
		return formatError(locale, resourceId, exception, arguments);
	}

	/**
	 * Formats a message identified by the application msg enumeration
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The resource to be formatted
	 * @param arguments The arguments to the message
	 * @return The formatted message
	 */
	public static String formatError(Locale locale, EELFResolvableResourceEnum resourceId,
			String... arguments) {
		return formatError(locale, getIdentifier(resourceId), resourceId.toString(), null,
				arguments);
	}

	/**
	 * This is the actual formatter used to format the resource with the
	 * supplied arguments, if any. It also sets the values for MDC:ErrorCode and
	 * MDC:ErrorDescription
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param identifier The message identifier, if one is to be assigned to the
	 *            message, or null.
	 * @param resourceId The resource id to be formatted
	 * @param exception an optional exception to be formatted
	 * @param arguments The arguments inserted into the resource, if any
	 * @return The formatted resource
	 */
	private static String formatError(Locale locale, String identifier, String resourceId,
			Throwable exception,
			String... arguments) {
		String skeleton = getMessage(locale, resourceId);
		String errorDescription = getErrorDescription(locale, resourceId);
		StringBuffer buffer = new StringBuffer();

		try {
			if (identifier != null) {

				MDC.put(MDC_ERROR_CODE, identifier.toUpperCase());
			}

			if (errorDescription != null) {
				MDC.put(MDC_ERROR_DESC, errorDescription);
			}

			buffer.append(MessageFormat.format(skeleton, (Object[]) arguments));

			if (exception != null) {
				buffer.append(
						String.format("\nException %s: %s", exception.getClass().getSimpleName(),
								exception.getMessage()));
				StackTraceElement[] stack = exception.getStackTrace();
				buffer.append("\n" + formatPartialStackTrace(stack, 0, 5));
			}

		} catch (IllegalArgumentException e) {
			return String.format(BAD_ARGUMENTS, resourceId);
		}
		return buffer.toString();
	}

	/**
	 * Obtains the specified description from description resource bundles that
	 * are loaded based on the resource id.
	 *
	 * @param locale The locale that we want to load the resource for
	 * @param resourceId The EELFResolvableResourceEnum to be located
	 * @return The requested resource, or a default message indicating that the
	 *         resource id could not be found, or a default error message
	 *         indicating that no bundles could be loaded.
	 */
	private static String getErrorDescription(Locale locale, String resourceId) {
		Map<String, ResourceBundle> localBundles = getMessageBundle(locale, messageBundles,
				messageBaseNames);
		return getResourceTemplateFromBundle(resourceId, localBundles, RESOURCE_TYPES.desc);
	}
}
