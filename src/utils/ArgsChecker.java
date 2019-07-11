package utils;

import java.io.File;

public class ArgsChecker {

	public static boolean checkArgs(String[] args) {
		boolean isValid = true;
		
		if (!checkNoOfArgs(args) || 
				!checkPort(Integer.parseInt(args[0])) || 
				!checkNoOfThreads(Integer.parseInt(args[1])) || 
				!checkFile(args[2])) {
			isValid = false;

			return isValid;
		}
		
		return isValid;
	}
	
	private static boolean checkPort(int port) {
		if (port > 65535 || port < 0) {
			System.err.println("keep it real ... the port you provied is no good.");
			return false;
		}
		
		return true;
	}
	
	private static boolean checkNoOfArgs(String[] args) {
		if (args.length > 3 || args.length < 3) {
			System.out.println("please provide valid args: " + "port, " + "threads no., " + "file | folder. ");
			
			return false;
		}
		
		if (args[0] == "-h" || args[0] == "--help") {
			System.out.println("Example: java -cp webserver.jar server.Main <PORT> <THREADS> <FILE | DIRECTORY>");
			return false;
		}
		
		return true;
	}
	
	private static boolean checkNoOfThreads(int threads) {
		if (threads > 10) {
			System.err.println("keep it under 10 threads for the moment");
			return false;
		}
		
		return true;
	}
	
	private static boolean checkFile(String file) {
		File f = new File(file);
		String extension = getFileExtension(f);
		
		if (!f.exists()) {
			System.err.println("the file or folder you provided does not exist.");
			return false;
		}
		
		if (f.isDirectory() && f.list().length==0) {
			System.err.println("you provided an empty directory");
			return false;
		}
		
		if (f.isFile() && (!extension.equals("html") && !extension.equals("txt"))) {
			System.err.println("please provide a file with extension .html or .txt");
			return false;
		}
		
		return true;
	}
	
	private static String getFileExtension(File f) {
        String name = f.getName();
        if(name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
        	 return name.substring(name.lastIndexOf(".")+1);
        } else {
        	return "";
        }
        
    }
}
