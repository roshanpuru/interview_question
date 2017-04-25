package com.interview.DistributedQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * This is a threadsafe singeton for Blocking InputQueueSingleton for RandomNumberGenerator program
 * @author roshanpuru
 *
 */
public class InputQueueSingleton {
	
	private static InputQueueSingleton singletonInputQueue = null;
	private BlockingQueue<AtomicInteger> blockingInputQueue = new LinkedBlockingQueue<AtomicInteger>(Integer.MAX_VALUE);
	/**
	 * 
	 * @return the singleton instance for the input queue
	 */
	public static InputQueueSingleton getInputQueue(){
		if (singletonInputQueue == null) {
			synchronized (InputQueueSingleton.class) {
				if (singletonInputQueue == null) {
					singletonInputQueue = new InputQueueSingleton();
				}
			}
		}
		return singletonInputQueue;
	}

	public BlockingQueue<AtomicInteger> getBlockingInputQueue() {
		return blockingInputQueue;
	}
	

}
