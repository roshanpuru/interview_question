package com.interview.multithread.randomprimeNumberChecker;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.interview.Constant.PrivateConstant;
import com.interview.DistributedQueue.RemoteInterfaces.RemoteQueueInterface;
import com.interview.DistributedQueue.ResponsePojo.PrimeNumberCheckResponsePojo;

/**
 * PrimeNumberChecker Program to take the input from the queue and test if it is prime or not
 * @author roshanpuru
 *
 */
public class PrimeNumberChecker {
	static ExecutorService executor = Executors.newFixedThreadPool(PrivateConstant.THREAD_POOL_SIZE);
	static PrimeNumberChecker primeNumberChecker = new PrimeNumberChecker();
	private static RemoteQueueInterface remoteQueueInterface;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException, ExecutionException  {
		Registry registry = 
	            LocateRegistry.getRegistry(PrivateConstant.HOST_NAME, PrivateConstant.PORT_NUMBER);
		remoteQueueInterface = (RemoteQueueInterface) registry.lookup(PrivateConstant.RMI_REGISTRY_SERVICE_NAME);
		executor.submit(primeNumberChecker.new InputQueueListener());

	}
/**
 * Prime Number algorithm task	
 * @author roshanpuru
 *
 */
class PimeNumberCheckerAndWriter implements Callable<Boolean>{
	AtomicInteger integer = new AtomicInteger();
	public PimeNumberCheckerAndWriter(AtomicInteger integer){
		this.integer = integer;
	}
	public Boolean call() throws Exception {
		if(integer!=null){
			PrimeNumberCheckResponsePojo pojo = new PrimeNumberCheckResponsePojo();
			pojo.setNumber(integer);
			pojo.setPrime(isPrimeNumber(integer));
			
			remoteQueueInterface.writeToOutputQueue(pojo);
		}
		return true;
	}
	/**
	 * 
	 * @param atomicInteger integet from the distributed queue
	 * @return true if prime false if not
	 */
	public boolean isPrimeNumber(AtomicInteger atomicInteger){
		if(atomicInteger == null || atomicInteger.get() < 0) return false;
		else
		{
			int number = atomicInteger.get();
		    if(number > 2 && (number & 1) == 0)
		       return false;
		    for(int i = 3; i * i <= number; i += 2) // squareroot test for faster exit
		        if (number % i == 0) 
		            return false;
		    return true;
		}
	}
	
}
/**
 * Listening to InputQueue for inputs
 * @author roshanpuru
 *
 */
class InputQueueListener implements Callable<AtomicInteger>{

	public AtomicInteger call() throws Exception {
		// polling the queue using RMI - Better implement the Publisher and Subscriber pattern
		while(true){
			executor.submit(PrimeNumberChecker.primeNumberChecker.new PimeNumberCheckerAndWriter(remoteQueueInterface.readInputQueue()));
		}

	}
	
}
}
