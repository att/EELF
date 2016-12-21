package com.att.eelf.utils;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;


public class TestStopWatch {
	
	@Test
	public void testStart() {
		Stopwatch.start();
		assertTrue(Stopwatch.isRunning());
	}
	
	@Test
	public void testClear() {
		Stopwatch.clear();
		assertFalse(Stopwatch.isRunning());
	}
	
	@Test
	public void testStop() {
		Stopwatch.start();
		assertTrue(Stopwatch.isRunning());
		Stopwatch.stop();
		assertFalse(Stopwatch.isRunning());
	}
	
	@Test
	public void testGetCurrentDuration() throws InterruptedException {
		Stopwatch.start();
		Thread.sleep(500);
		Stopwatch.stop();
		Double currentDuration = Stopwatch.getCurrentDuration();
		assertNotSame(0,currentDuration);
	}
	
	@Test
	public void testGetCurrentDurationWatchStopped() throws InterruptedException {
		Stopwatch.start();
		Thread.sleep(500);
		Stopwatch.stop();
		double currentDuration = Stopwatch.getCurrentDuration();
		assertEquals(0,currentDuration,0.01);
	}
	

}
