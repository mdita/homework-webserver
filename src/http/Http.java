package http;

public class Http {
	public static class Method {
		public static final String GET = "GET";
		public static final String HEAD = "HEAD";
	}
	
	public static class StatusCode {
		public static final String OK = "200 OK";
		public static final String NOT_FOUND = "404 Not Found";
		public static final String NOT_IMPLEMENTED = "501 Not Implemented";
	}
	
	public static class ContentType {
		public static final String TEXT = "text/plain";
		public static final String HTML = "text/html";
	}
}
