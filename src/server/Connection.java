package server;

import http.Request;
import http.Response;
import http.Http.Method;
import http.Http.StatusCode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
	
	private Server server;
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private boolean done;

	public Connection(Socket cl, Server s) {
		client = cl;
		server = s;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			client.setSoTimeout(10000); // 10 sec timeout
			in = client.getInputStream(); // in order to read the request
			out = client.getOutputStream(); // for response
			done = false; // wait until the client indicates that the connection should be closed
			
			while(!done) {
				Request request = Request.parseRequest(in);
				
				if (request != null) {
					System.out.println("Request for " + request.getUrl() + " is being processed " +
							"by socket at " + client.getInetAddress() +":"+ client.getPort());
						
					Response response;
						
					String method;
					if ((method = request.getMethod()).equals(Method.GET) 
								|| method.equals(Method.HEAD)) {
						String file = getFilePath(server.getFileOrDirectory(), request.getUrl());
						File f = new File(file);
						response = new Response(StatusCode.OK).withFile(f);
						if (method.equals(Method.HEAD)) {
							response.removeBody();
						}
					} else {
							response = new Response(StatusCode.NOT_IMPLEMENTED);
					}
					
					if ("close".equalsIgnoreCase(request.getHeaders().get("Connection")))  {
				          done = true;
				    }
					
					String valueConnectionHeader = request.getHeaders().get("Connection");
					if (valueConnectionHeader != null) {
						response.setConnectionType(valueConnectionHeader);
					} else {
						done = true;
					}
					
					respond(response);
				} else {
					System.err.println("Only HTTP is allowed here.");
					break;
				}
			}
			
//			Request request = Request.parseRequest(in);
//			
//			if (request != null) {
//				System.out.println("Request for " + request.getUrl() + " is being processed " +
//						"by socket at " + client.getInetAddress() +":"+ client.getPort());
//					
//				Response response;
//					
//				String method;
//				if ((method = request.getMethod()).equals(Method.GET) 
//							|| method.equals(Method.HEAD)) {
//						File f = new File(server.getFileOrDirectory() + request.getUrl());
//						response = new Response(StatusCode.OK).withFile(f);
//						if (method.equals(Method.HEAD)) {
//							response.removeBody();
//						}
//					} else {
//						response = new Response(StatusCode.NOT_IMPLEMENTED);
//					}
//					
//					respond(response);
//			} else {
//				System.err.println("Only HTTP is allowed here.");
//			}
			
			in.close();
			out.close();
		} catch (IOException e) {
			System.err.println("Error appeared while receiving the client connection.");
		} finally {
			closeConnection(client);
		}
	}
	
	public void closeConnection(Socket client) {
		try {
			client.close();
		} catch (IOException e) {
			System.err.println("Error while closing client socket.");
		}
	}
	
	public void respond(Response response) {
		String toSend = response.toString();
		System.out.println(toSend);
		PrintWriter writer = new PrintWriter(out);
		writer.write(toSend);
		writer.flush();
	}
	
	private String getFilePath(String fileOrDirectory, String url) {
		File f = new File(fileOrDirectory);
		
		if (f.isFile() && !url.equals("/")) {
			return "404/404.index.html";
		}
		
		if (f.isFile() && url.equals("/")) {
			return fileOrDirectory;
		}
		
		if (f.isDirectory() && url.equals("/")) {
			return fileOrDirectory.concat(url).concat("index.html");
		} else {
			return fileOrDirectory.concat(url);
		}
	}

}
