/**
 * Copyright (c) 2019 AT&T Intellectual Property. All rights reserved.
 */

package com.att.eelf.utils;

import static com.att.eelf.configuration.Configuration.MDC_BEGIN_TIMESTAMP;
import static com.att.eelf.configuration.Configuration.MDC_ELAPSED_TIME;
import static com.att.eelf.configuration.Configuration.MDC_END_TIMESTAMP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.MDC;


/**
 * This class is used to time events to determine their duration. The stop watch allows for the same types of operations
 * as a real stop watch, that is, it allows the timing to be stopped, started, cleared, and read. The accumulated time
 * is the total of all times between start and stop events. The watch can be repeatedly stopped and restarted, and will
 * accumulate all durations between start/stop pairs.
 *
 */
public class Stopwatch {

	public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final TimeZone utc = TimeZone.getTimeZone("UTC");
    public static final SimpleDateFormat isoFormatter = new SimpleDateFormat(ISO_FORMAT);

    /**
     * This is the object that maintains our state on the thread local storage
     */
    public static class StopwatchState {
        /**
         * The accumulated duration
         */
        private long duration;

        /**
         * Indicates that the watch is running
         */
        private boolean running = false;

        /**
         * The last recorded start time
         */
        private long startTime;

    }

    /**
     * Thread local storage wrapper
     */
    private static ThreadLocal<StopwatchState> tls = new ThreadLocal<>();

    static {
        isoFormatter.setTimeZone(utc);
    }

    /**
     * Looks up the Thread Local storage object containing the Stopwatch state, and creates it if it does not already
     * exist.
     *
     * @return The state object
     */
    private static StopwatchState getState() {
        StopwatchState state = tls.get();
        if (state == null) {
            state = new StopwatchState();
            tls.set(state);
        }
        return state;
    }

    /**
     * Clears (and possibly stops) the watch.
     */
    public static void clear() {
        StopwatchState state = getState();
        state.running = false;
        state.duration = 0L;
        state.startTime = 0L;
    }

    /**
     * The accumulated duration of the watch (in nano-seconds)
     *
     * @return The accumulated time
     */
    public static long getDuration() {
        StopwatchState state = getState();
        return state.duration;
    }

    /**
     * Determines if the stopwatch is currently running or not
     *
     * @return True if the watch is running
     */
    public static boolean isRunning() {
        StopwatchState state = getState();
        return state.running;
    }

    /**
     * Starts the watch if not already running.
     */
    public static void start() {
    	clear();
        StopwatchState state = getState();
        if (!state.running) {
            state.running = true;
            state.startTime = System.currentTimeMillis();
            MDC.put(MDC_BEGIN_TIMESTAMP, isoFormatter.format(new Date(state.startTime)));
        }
    }

    /**
     * Stops the accumulation of time on the watch if running
     */
    public static void stop() {
        StopwatchState state = getState();
        if (state.running) {
            long stopTime = System.currentTimeMillis();
            state.duration += (stopTime - state.startTime);
            state.running = false;
            MDC.put(MDC_END_TIMESTAMP,isoFormatter.format(new Date(stopTime)));
            MDC.put(MDC_ELAPSED_TIME, String.valueOf(state.duration));

        }
    }

    /**
     * Gets the amount of time since the stop watch was last started without stopping the watch or accumulating the
     * previous time.
     * 
     * @return the duration in milliseconds if still running, otherwise 0
     */
    public static double getCurrentDuration() {
        StopwatchState state = getState();
        if (state.running) {
            return (System.currentTimeMillis() - state.startTime);
        }
        return 0L;
    }
}
