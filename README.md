# homework-webserver
A simple **webserver** implemented in Java.

**Description**
<p>This is a simple filebased webserver implemented in Java that supports <strong>GET</strong> and <strong>HEAD</strong> requests. Based on the provided arguments to the jar file, the webserver will start on a port, with <em><strong>n</strong></em> number of threads and with a file or directory that will be used for response.</p>
<p>The <em><strong>args</strong></em> that are provided to the jar file are checked to be valid, so we need a valid port, a reasonable number of threads and a file or directory that exists or it's not empty. For files please use <strong>.html</strong> or <strong>.txt</strong> otherwise an error message is provided.</p>
<p>So we have the server, a pool of threads via the **ExecutorService** and after that we are going to receive and parse the <em><strong>Request</em></strong>. Based on the request we can check the protocol, the headers and body. If the request is parsed properly and we have a valid <em>HTTP request</em> then we go to the <strong>Response</strong> part. For the response part we verify that the requested file exists and stuff. If everything is alright we are preparing the response and respond to the client. For response we need to add the proper headers, body, content type, content length etc. We support both, files and html responses.</p>
<p>There is also a part in this implementation where I tried to add some support for <em><strong>HTTP/1.1 keep-alive</strong></em>. I added a mechanism where at first I will check the request headers to see if <strong>Connection</strong> was provided. If this header was provided I will add the header to the response with the same value. The values for this header are <em>keep-alive</em> and <em>close</em>. I have also added a timeout for our server for the <strong>InputStream</strong> associated with the socket.</p>

**Examples**

<p>Start the webserver using this terminal command :</p>
<code>java -cp webserver.jar server.Main 8080 9 index.html</code>

<p>OR</p>

<code>java -cp webserver.jar server.Main 8080 9 webfiles</code>

<p>If you start with <em>index.html</em> only this file will be delivered on path root <strong>/</strong>. If you start with webfiles then all files that are in this folder will be delivered. 
You can also play with the args because there are some validations that need to be met.</p>
<p>For help try</p> 
<code>
java -cp webserver.jar server.Main -h (or --help)
</code>


**More samples**
<code>
java -cp webserver.jar server.Main 8080 9 empty // empty folder validation
</code>

<code>
java -cp webserver.jar server.Main 8080 9 index.pdf // pdf not supported
</code>

<code>
java -cp webserver.jar server.Main 8080 9 text.txt // supports text
</code>

<code>
java -cp webserver.jar server.Main 8080 15 empty // no more than 10 thread
</code>

<code>
java -cp webserver.jar server.Main 808080880 9 empty // port not valid
</code>
