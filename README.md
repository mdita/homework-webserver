# homework-webserver
A simple **webserver** implemented in Java.

**Description**
<p>This a simple filebased webserver implemented in Java that supports **GET** and **HEAD** methods. Based on the provided arguments to the jar file, the webserver will start on a port, with ***n*** number of threads and with a file or directory that will be used for response.</p>
<p>The ***args*** that are provided to the jar file are checked to be valid, so we need a valid port, a reasonable number of threads and a file or directory that exists or it's not empty. For files please use **.html** or **.txt** otherwise an error message is provided.</p>
<p>So we have the server, a pool of threads via the **ExecutorService** and after that we receive and parse the ***Response***.</p>
