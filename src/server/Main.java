package server;
import utils.ArgsChecker;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (!ArgsChecker.checkArgs(args)) {
			
		} else {
			int port = Integer.parseInt(args[0]);
			int noOfThreads = Integer.parseInt(args[1]);
			String fileName = args[2];
			
			new Thread(new Server(port, noOfThreads, fileName)).start();
		}
	}

}
