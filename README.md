# homework-webserver
A simple **webserver** implemented in Java.

**Description**
<p>This a simple filebased webserver implemented in Java that supports <strong>GET</strong> and <strong>HEAD</strong> methods. Based on the provided arguments to the jar file, the webserver will start on a port, with <em><strong>n</strong></em> number of threads and with a file or directory that will be used for response.</p>
<p>The <em><strong>args</strong></em> that are provided to the jar file are checked to be valid, so we need a valid port, a reasonable number of threads and a file or directory that exists or it's not empty. For files please use <strong>.html</strong> or <strong>.txt</strong> otherwise an error message is provided.</p>
<p>So we have the server, a pool of threads via the **ExecutorService** and after that we receive and parse the <em><strong>Response</em><strong>.</p>
