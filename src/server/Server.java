package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
	private int port;
	private int numberOfThreads;
	private String fileOrDirectory;
	
	private ServerSocket  server;
	private ExecutorService poolOfThreads; //https://www.baeldung.com/java-executor-service-tutorial
	
	// constructor
	public Server(int port, int numberOfThreads, String fileName) {
		this.setPort(port);
		this.setNumberOfThreads(numberOfThreads);
		this.setFileOrDirectory(fileName);
	}
	
	// Getters and Setters
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}
	
	public String getFileOrDirectory() {
		return fileOrDirectory;
	}

	public void setFileOrDirectory(String fileOrDirectory) {
		this.fileOrDirectory = fileOrDirectory;
	}
	
	
	// Runnable method
	public void run() {
		try {
			server = new ServerSocket(port);
			poolOfThreads = Executors.newFixedThreadPool(numberOfThreads);
			
			System.out.println("Server started on port: " + port);
		} catch (IOException e) {
			System.err.println("Something went wrong when trying to create server on port: " + port);
			System.exit(1);
		}

		while (!Thread.interrupted()) { // infinite loop until interrupted
			try {
				poolOfThreads.execute(new Thread(new Connection(server.accept(), this)));
			} catch (IOException e) {
				System.err.println("Connection dropped ...");
			}
		}
		
		closeServer();
	}
	
	public void closeServer() {
		try {
			server.close();
		} catch (IOException e) {
			System.err.println("Error while closing server socket.");
		}
		poolOfThreads.shutdown();
		try {
			// trying to close the pool threads again if not terminated already.
			if (!poolOfThreads.awaitTermination(10, TimeUnit.SECONDS)) 
				poolOfThreads.shutdownNow();
		} catch (InterruptedException e) {
			System.err.println("A error appeared while trying to close the pool of threads.");
		}
	}
}
