package com.interview.DistributedQueue.RemoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicInteger;

import com.interview.DistributedQueue.ResponsePojo.PrimeNumberCheckResponsePojo;
/**
 * Remote interface for Producer and Consumer with Read and Write methods to blocking queue
 * @author roshanpuru
 *
 */
public interface RemoteQueueInterface extends Remote{

	public boolean writeToInputQueue(AtomicInteger integerToWrite) throws RemoteException;
	public boolean writeToOutputQueue(PrimeNumberCheckResponsePojo responseObjectToWrite) throws RemoteException;

	public AtomicInteger readInputQueue() throws RemoteException;
	public PrimeNumberCheckResponsePojo readOutputQueue() throws RemoteException;
	
}
