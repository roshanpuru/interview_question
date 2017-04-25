package com.interview.DistributedQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.interview.DistributedQueue.ResponsePojo.PrimeNumberCheckResponsePojo;
/**
 * This is a threadsafe singeton for Blocking OutputQueueSingleton for RandomNumberGenerator program
 * @author roshanpuru
 *
 */
public class OutputQueueSingleton {
	private static OutputQueueSingleton singletonOututQueue = null;
	private BlockingQueue<PrimeNumberCheckResponsePojo> blockingOutputQueue = new LinkedBlockingQueue<PrimeNumberCheckResponsePojo>(Integer.MAX_VALUE);
	/**
	 * 
	 * @return the singleton instance for the output queue
	 */
	public static OutputQueueSingleton getOutputQueue(){
		if (singletonOututQueue == null) {
			synchronized (OutputQueueSingleton.class) {
				if (singletonOututQueue == null) {
					singletonOututQueue = new OutputQueueSingleton();
				}
			}
		}
		return singletonOututQueue;
	}

	public BlockingQueue<PrimeNumberCheckResponsePojo> getBlockingOutputQueue() {
		return blockingOutputQueue;
	}

}
