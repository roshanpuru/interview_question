package com.interview.multithread.randomizer;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.interview.Constant.PrivateConstant;
import com.interview.DistributedQueue.RemoteInterfaces.RemoteQueueInterface;
import com.interview.DistributedQueue.ResponsePojo.PrimeNumberCheckResponsePojo;
/**
 * A Program to generate the positive random number
 * @author roshanpuru
 *
 */
public class RandomNumberGenerator {
	//smaller thread pool for generating the random number and listening to output/response queue
	static ExecutorService executor = Executors.newFixedThreadPool(PrivateConstant.SMALLER_THREAD_POOL_SIZE);
	
	static RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
	private static RemoteQueueInterface remoteQueueInterface;
	
	Random rand = new Random();
	

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException, ExecutionException  {
		Registry registry = 
	            LocateRegistry.getRegistry(PrivateConstant.HOST_NAME, PrivateConstant.PORT_NUMBER);
		remoteQueueInterface = (RemoteQueueInterface) registry.lookup(PrivateConstant.RMI_REGISTRY_SERVICE_NAME);
		executor.submit(randomNumberGenerator.new OutputQueueListener());
		executor.submit(randomNumberGenerator.new RandomGeneratorWriter()).get();

		
	}
/**
 * Random number Generator task	
 */
class RandomGeneratorWriter implements Callable<Boolean>{

	public Boolean call() throws Exception {
		// polling the queue using RMI - Better implement the Publisher and Subscriber pattern
		while(true){
			remoteQueueInterface.writeToInputQueue(new AtomicInteger(Math.abs(rand.nextInt(Integer.MAX_VALUE))));
		}
	}
	
}
/**
 * Response QueueListener class - 
 * @author roshanpuru
 *
 */
class OutputQueueListener implements Callable<Void>{

	public Void call() throws Exception {
		PrimeNumberCheckResponsePojo pojo = null;
		// polling the queue using RMI - Better implement the Publisher and Subscriber pattern
		while(true){
			pojo =  remoteQueueInterface.readOutputQueue();
			if(pojo!=null){
				System.out.println("============== OUTPUT ============================================ " );
				System.out.println("Number " + pojo.getNumber().get() +    "     -------> is Prime ?  " + pojo.isPrime());
			}
		}

	}
	
}

}
