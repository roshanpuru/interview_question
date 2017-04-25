package com.interview.DistributedQueue.ResponsePojo;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Response Object from the PrimeNumberChecker Program
 * @author roshanpuru
 *
 */
public class PrimeNumberCheckResponsePojo implements Serializable{

	private static final long serialVersionUID = -1419732304289682789L;
	
	private AtomicInteger number = new AtomicInteger();
	private boolean isPrime;
	
	public AtomicInteger getNumber() {
		return number;
	}
	public void setNumber(AtomicInteger number) {
		this.number = number;
	}
	public boolean isPrime() {
		return isPrime;
	}
	public void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}
	
	
	

}
