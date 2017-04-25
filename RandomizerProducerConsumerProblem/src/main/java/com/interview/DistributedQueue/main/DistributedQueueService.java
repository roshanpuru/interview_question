package com.interview.DistributedQueue.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.interview.Constant.PrivateConstant;
import com.interview.DistributedQueue.InputQueueSingleton;
import com.interview.DistributedQueue.OutputQueueSingleton;
import com.interview.DistributedQueue.RemoteInterfaces.RemoteQueueInterface;
import com.interview.DistributedQueue.ResponsePojo.PrimeNumberCheckResponsePojo;
/**
 * This is a sample RMIServer for Which implement the remote interface with four remote methods for read and write operations on Input and output Blocking Queue.
 * @author roshanpuru
 *
 */
public class DistributedQueueService extends UnicastRemoteObject implements RemoteQueueInterface {

	private static final long serialVersionUID = -6019247855875908848L;

	protected DistributedQueueService() throws RemoteException { super();}
	/**
	 * Main method to create and start the RMI service.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
				Registry registry = LocateRegistry.createRegistry(PrivateConstant.PORT_NUMBER);
				registry.rebind(PrivateConstant.RMI_REGISTRY_SERVICE_NAME, new DistributedQueueService());
				System.out.println("RMI Server ready to taker resquest -----> " + new Date());
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * write to input blocking queue
	 */
	public boolean writeToInputQueue(AtomicInteger integerToWrite) throws RemoteException {
		return InputQueueSingleton.getInputQueue().getBlockingInputQueue().add(integerToWrite);
	}
	/**
	 * write to output blocking queue
	 */
	public boolean writeToOutputQueue(PrimeNumberCheckResponsePojo responseObjectToWrite) throws RemoteException {
		return OutputQueueSingleton.getOutputQueue().getBlockingOutputQueue().add(responseObjectToWrite);
	}
	/**
	 * Read input blocking queue
	 */
	public AtomicInteger readInputQueue() throws RemoteException {
		
		return InputQueueSingleton.getInputQueue().getBlockingInputQueue().poll();
	}
	/**
	 * read output bloking queue
	 */
	public PrimeNumberCheckResponsePojo readOutputQueue() throws RemoteException {
		// TODO Auto-generated method stub
		return OutputQueueSingleton.getOutputQueue().getBlockingOutputQueue().poll();
	}

}
