package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;
import http.Http.StatusCode;
import http.Http.ContentType;

public class Response {
	private static final String protocol = "HTTP/1.0";

	private String status;
	private NavigableMap<String, String> headers = new TreeMap<String, String>();
	private byte[] body = null;

	public Response(String status) {
		this.status = status;
		setDate(new Date());
	}
	
	public Response withFile(File f) {
		if (f.isFile()) {
			try {
				FileInputStream reader = new FileInputStream(f);
				int length = reader.available();
				body = new byte[length];
				reader.read(body);
				reader.close();
				
				setContentLength(length);
				setContentTypeBasedOnFile(f);
			} catch (IOException e) {
				System.err.println("Error while reading " + f);
			}
			return this;
		} else {
			return setResponseErrorPage("404/404.index");
		}
	}
	
	public Response withHtmlBody(String msg) {
		setContentLength(msg.getBytes().length);
		setContentType(ContentType.HTML);
		body = msg.getBytes();
		return this;
	}

	public void setDate(Date date) {
		headers.put("Date", date.toString());
	}

	public void setContentLength(long value) {
		headers.put("Content-Length", String.valueOf(value));
	}

	public void setContentType(String value) {
		headers.put("Content-Type", value);
	}
	
	public void setContentTypeBasedOnFile(File f) {
		if (f.getName().endsWith(".htm") || f.getName().endsWith(".html")) {
			setContentType(ContentType.HTML);
		} else {
			setContentType(ContentType.TEXT);
		}
	}
	
	public Response setResponseErrorPage(String file) {
		File page = new File(file);
		return new Response(StatusCode.NOT_FOUND)
			.withFile(page);
	}
	
	public void setConnectionType(String value) {
		headers.put("Connection", value);
	}

	public void removeBody() {
		body = null;
	}
	
	@Override
	public String toString() {
		String result = protocol + " " + status +"\n";
		for (String key : headers.descendingKeySet()) {
			result += key + ": " + headers.get(key) + "\n";
		}
		result += "\r\n";
		if (body != null) {
			result += new String(body);
		}
		return result;
	}
}
