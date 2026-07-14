# Introduction to Naming Schemes, Networks, Clients and Services with Java

This repository contains the extensive and complete solution to the practical workshop for the **Software Architecture (ARSW)** course, focused on network programming in Java. The main goal of this project is to explore, understand, and apply the fundamental concepts that govern communication over the internet and local networks. Topics covered include connection-oriented (TCP) and connectionless (UDP) network protocols, URL parsing and manipulation, the implementation of basic Web Servers, and the development of distributed systems using RMI (Remote Method Invocation).

Below is a detailed breakdown of each exercise developed, specifying its pedagogical objective, an exhaustive explanation of its internal workings at the code and architecture level, and the precise instructions for compiling and running it correctly.

---

## Exercise 1: Exploring and Breaking Down the URL Class

**Objective:**
Understand the anatomy of a Uniform Resource Locator (URL) and learn how to programmatically extract each of its individual components using the standard networking API provided by Java (`java.net.URL`).

**How it works:**
The program defines a static string representing a complex URL, one that includes protocol, host name, port, path, query parameters, and a reference fragment (`http://ldbn.escuelaing.edu.co:80/index.html?param=value#section1`). A `URL` object is instantiated from that string. Although string-based constructors have been marked deprecated in recent Java versions in favor of the `URI` class, the exercise demonstrates their practical use for teaching purposes. Through the instantiated object, the program sequentially invokes the API's extraction methods (`getProtocol()`, `getAuthority()`, `getHost()`, `getPort()`, `getPath()`, `getQuery()`, `getFile()`, and `getRef()`), printing the detailed results to the standard console output. This allows one to validate how Java internally decomposes and interprets a web address.

**Execution Instructions:**
1. Navigate through the terminal to the `ejercicio1` directory.
2. Compile the source code by running: `javac URLInfoPrinter.java` (adjust the name if it was modified in your local environment).
3. Start the application by running: `java URLInfoPrinter`
4. Inspect the console for the full breakdown of the components that make up the test URL.

> **Evidence Placeholder - Exercise 1:**
> [Insert here a screenshot showing the console output of the URL breakdown]
> ![Evidence Exercise 1](./imagenes/ejercicio1.png)

---

## Exercise 2: Browser Application (URL Reader and Saver)

**Objective:**
Develop an application capable of establishing a basic HTTP connection to a URL provided by the user, reading the resulting data stream (typically HTML source code), and persisting that information to local storage.

**How it works:**
The application asks the user to enter a URL through the console using the `Scanner` class. Once the string has been read, the program builds a `URI` from it and converts it into a `URL` via `new URI(direccion).toURL()` — the modern, non-deprecated way of obtaining a `URL` instance — and then invokes `openStream()` on it. This method returns an input stream (`InputStream`) tied to the network connection. The program wraps this stream in a `BufferedReader` to read the remote document line by line. At the same time, it opens a `BufferedWriter` around a `FileWriter` pointing to a local file named `Resultado.html`. In a loop, the program reads each line coming from the remote server and immediately writes it to the local file (adding a newline after each one), effectively cloning the source code of the requested page.

**Execution Instructions:**
1. Navigate through the terminal to the `ejercicio2` directory.
2. Compile the source code by running: `javac AplicacionBrowser.java`
3. Start the application by running: `java AplicacionBrowser`
4. When prompted, enter a valid URL including the explicit protocol (for example, `http://www.google.com/`).
5. In your file explorer, go to the project folder and open the resulting `Resultado.html` file with your web browser of choice to view the cloned page.

> **Evidence Placeholder - Exercise 2:**
> [Insert here a screenshot showing the URL prompt in the console and the browser rendering the local Resultado.html file]
> ![Evidence Exercise 2](./imagenes/ejercicio2.png)

---

## Exercise 4.3.1: Basic TCP Client-Server Architecture (Square Calculator)

**Objective:**
Implement and understand bidirectional communication between a client and a server using the TCP (Transmission Control Protocol) protocol, which guarantees reliable, in-order delivery of data packets through Java Sockets.

**How it works:**
The architecture is split into two independent components that communicate over a previously agreed-upon network port.
- **The Server (`SquareServer.java`):** Instantiates a `ServerSocket` bound to logical port `35000`. It then invokes the blocking method `accept()`, halting execution until a client attempts to connect. Once the connection is established, it obtains the input and output streams of the resulting `Socket`. It reads the text line sent by the client, parses it into an integer value (`Integer.parseInt`), computes its square by multiplying the value by itself, and returns the formatted result ("Cuadrado: X") through the output stream back to the client. If the received text cannot be parsed as an integer, it responds with an error message instead of crashing.
- **The Client (`SquareClient.java`):** Instantiates a `Socket` pointing at the local IP address (`127.0.0.1`) and the destination port (`35000`). It then enters a loop where it captures user input from the terminal, sends it through the socket's output stream to the server, and waits for the response. Once it receives the result computed by the server, it prints it to the screen.

**Execution Instructions:**
1. Open a terminal, navigate to the `ejercicio4.3.1` directory, and compile the server: `javac SquareServer.java`
2. Start the server process: `java SquareServer`
3. Open a **second terminal** (without closing the first one), navigate to the same directory, and compile the client: `javac SquareClient.java`
4. Start the client process: `java SquareClient`
5. In the client terminal, enter integer values and press Enter to see how the remote server processes the request and returns the result instantly.

> **Evidence Placeholder - Exercise 4.3.1:**
> [Insert here a screenshot showing both terminals (client and server) interacting and processing the entered numbers]
> ![Evidence Exercise 4.3.1](./imagenes/ejercicio4.3.1.png)

---

## Exercise 4.3.2: Dynamic Math Operations Server (Trigonometry)

**Objective:**
Extend the traditional TCP Client-Server model by incorporating persistent state within the server session, allowing the system's behavior to be dynamically altered based on specific commands sent by the client.

**How it works:**
This exercise is an advanced iteration of the previous one. The server starts with a default state configured to compute the "Cosine" math function. Inside the loop that reads data coming from the client, the server implements conditional routing logic. If the received string starts with a reserved special prefix (specifically `fun:` followed by `sin`, `cos`, or `tan` — the Spanish variants `seno`, `coseno`, and `tangente` are also accepted), the server does not attempt a math operation; instead it updates its internal state, recording the new function to apply, and confirms the change back to the client. If the input does not contain that prefix, the server assumes it is a numeric value, checks its current state, and applies the corresponding trigonometric function using Java's `Math` library. The result is formatted to show which operation was applied to the value and is returned to the client, demonstrating how a single connection can evolve its execution context over time.

**Execution Instructions:**
1. Open a terminal, navigate to the `ejercicio4.3.2` folder, and compile both files: `javac TrigServer.java TrigClient.java`
2. Run the server: `java TrigServer`
3. In a new terminal, run the client: `java TrigClient`
4. Enter numeric values to observe the default function (Cosine) being applied.
5. Explicitly enter commands such as `fun:sin` or `fun:tan` to change the server's state, then enter new numbers to verify the change in the math operation being applied.

> **Evidence Placeholder - Exercise 4.3.2:**
> [Insert here a screenshot of the client console showing the successful transition between the different trigonometric functions]
> ![Evidence Exercise 4.3.2](./imagenes/ejercicio4.3.2.png)

---

## Exercise 4.4: Basic Web Server (Static Single-Request Response)

**Objective:**
Demystify how modern web servers work by building a native Java program capable of parsing raw HTTP requests and responding with formatted documents that a commercial browser can properly render.

**How it works:**
The program `HttpServer.java` creates a `ServerSocket` bound to port `35000`. When accessed from a standard web browser (Chrome, Firefox, etc.) using the address `localhost:35000`, the browser opens a TCP connection and immediately sends a plain-text block that makes up the HTTP request (including the `GET` method, the path, the protocol version, and the various descriptive "Headers"). The Java server reads this block sequentially through a `BufferedReader` and prints it to the developer's console, illustrating what web transactions actually look like under the hood; it stops reading once the input stream reports no more data is immediately available (`in.ready()` returns false). After finishing the request, the server responds by carefully assembling a valid HTTP packet, which includes the status line `HTTP/1.1 200 OK`, the `Content-Type: text/html` header, and the required blank line separating headers from the body. Finally, it sends a small static HTML document and closes the streams, ending the connection. Because the server only calls `accept()` once (there is no surrounding loop), it serves exactly one request per run and then terminates.

**Execution Instructions:**
1. Navigate to the `ejercicio4.4` folder and compile the server: `javac HttpServer.java`
2. Run the server: `java HttpServer`
3. Open your preferred web browser and go to the address bar, entering: `http://localhost:35000/`
4. Check both the browser screen (to view the rendered HTML) and the terminal running the server (to examine the headers the browser sent behind the scenes).

> **Evidence Placeholder - Exercise 4.4:**
> [Insert here a combined image showing the browser rendering the title and the console showing the GET request block]
> ![Evidence Exercise 4.4](./imagenes/ejercicio4.4.png)

---

## Exercise 4.5.1: Multi-Request Web Server with Static File Support

**Objective:**
Evolve the static web server into a long-running service capable of dynamically parsing requested paths, locating the corresponding physical resources on disk, and returning the matching bytes along with their proper MIME types, all while remaining continuously active.

**How it works:**
The code in `HttpServerMulti.java` wraps the connection lifecycle inside a `while(true)` loop, ensuring the `ServerSocket` immediately calls `accept()` again after finishing a transaction, preventing the application from shutting down automatically. When it receives an HTTP request, the server splits (using `split(" ")`) the initial request line (e.g. `GET /recurso.png HTTP/1.1`) to isolate the requested path (defaulting `/` to `/index.html`). It then prepends this path with an internal directory named `public/` and uses the `java.nio.file.Files` API to check whether the file exists on the filesystem and is not a directory. If found, the server reads the raw bytes, calls an internal method to determine the file's nature based on its extension (assigning the correct Content-Type such as `text/css`, `image/png`, `image/jpeg`, `application/javascript`, or defaulting to `text/plain`), sends the headers (`200 OK`, `Content-Type`, `Content-Length`) followed by a blank line, and finally dispatches the raw bytes through the `OutputStream`. If the file does not exist, it gracefully returns an HTTP `404 Not Found` response with a small HTML body.

**Execution Instructions:**
1. Go to the `ejercicio4.5.1` directory and make sure the code is compiled: `javac HttpServerMulti.java`
2. Verify that the `public` folder exists and contains test files (such as `index.html` or image resources).
3. Start the server: `java HttpServerMulti`
4. Open your web browser and go to `http://localhost:35000/index.html`.
5. Try reloading the page several times or requesting nonexistent resources to verify how the server handles and remains stable across consecutive requests and 404 errors.

> **Evidence Placeholder - Exercise 4.5.1:**
> [Insert here screenshots of the rendered web page in the browser and the console output showing the consecutive requests handled by the while loop]
> ![Evidence Exercise 4.5.1](./imagenes/ejercicio4.5.1.png)

---

## Exercise 5.2.1: Time Synchronization Using UDP Datagrams

**Objective:**
Build and interact with applications based on the UDP (User Datagram Protocol) protocol. Unlike TCP, UDP does not keep an open connection and does not verify the order or arrival of data. This exercise aims to build resilience in a client facing packet loss or server outages.

**How it works:**
- **The Server (`DatagramTimeServer.java`):** Instantiates a `DatagramSocket` listening on port `4445`. Through an infinite loop, it passively waits to receive a packet (`DatagramPacket`). Upon a packet's arrival, it instantly extracts the sender's IP address and origin port, obtains the current system time via the `Date` object, converts it to a byte array, packages the information into a new `DatagramPacket` addressed to the sender, and sends it back.
- **The Client (`DatagramTimeClient.java`):** Implements a controlled loop that sends an empty request to the server's port every five seconds. The critical feature of this client is the call to `socket.setSoTimeout(2000)`. Since UDP does not guarantee a connection, if the server were to suffer a critical failure or become disconnected mid-transaction, the client would otherwise block forever waiting for a packet that will never arrive. The configured timeout ensures that, after two seconds of unsuccessful waiting, the system throws a `SocketTimeoutException`. The `catch` block traps that exception, prevents the program from crashing unexpectedly, and keeps displaying the last valid time previously stored in memory, resuming normal operation once the server comes back online.

**Execution Instructions:**
1. Navigate to the `ejercicio5.2.1` directory and compile both components: `javac DatagramTimeServer.java DatagramTimeClient.java`
2. In one terminal, start the time server: `java DatagramTimeServer`
3. In a second, independent terminal, start the client: `java DatagramTimeClient`
4. Let the system synchronize the time over several iterations.
5. Simulate an abrupt interruption by terminating the server process (using `Ctrl+C`). Observe the resilient behavior of the client terminal, which should report that the server is unreachable but keep running using its last known reading, surviving the outage. Start the server again to observe its automatic recovery.

> **Evidence Placeholder - Exercise 5.2.1:**
> [Insert here screenshots showing normal operation followed by a screenshot showing the error handling when the server is disconnected]
> ![Evidence Exercise 5.2.1](./imagenes/ejercicio5.2.1.png)

---

## Exercise 6.4.1: Transparent P2P Chat System Using Java RMI

**Objective:**
Apply advanced Remote Method Invocation (RMI) concepts to fully abstract away the inherent complexity of sending byte streams over the network. RMI allows a program to invoke methods on objects that live in a remote Java Virtual Machine (JVM) as if they were plain local invocations.

**How it works:**
The system abandons the classic central-server paradigm. The `ChatApp.java` class follows a Peer-to-Peer (P2P) design, acting simultaneously in two indispensable roles:
- **As a Server:** It implements the shared `ChatService` interface, which extends `java.rmi.Remote` and declares a single remote method, `receiveMessage(String sender, String message)`. During initialization, the application exports its own local instance as a "stub" (a proxy object responsible for serialization) using `UnicastRemoteObject.exportObject(chatApp, 0)`. It then programmatically creates its own registry (`LocateRegistry.createRegistry(localPort)`) on a local port specified dynamically by the user, and binds the stub under the name "ChatService" via `rebind`.
- **As a Client:** It interactively asks for the desired recipient's data (target IP address and port). Through the static method `LocateRegistry.getRegistry(targetIP, targetPort)`, the application locates the remote JVM's registry and uses the `lookup("ChatService")` function to find the remote object.
- **Data flow:** Once both interfaces are set up, when the user types a text string into the console, the system does not manage sockets manually. It simply executes the statement `remoteService.receiveMessage(username, msg)`. The Java RMI framework serializes the objects under the hood, transmits them over the network, executes the code on the recipient's remote machine transparently, and produces output on their terminal in real time (including a re-prompt of "Tu mensaje: " so the recipient's input line is not disrupted).

**Execution Instructions:**
*(For this scenario, using two consoles to simulate separate environments is recommended)*
1. Go to the `ejercicio6.4.1` directory and compile the class package: `javac ChatService.java ChatApp.java`
2. In your first console (representing one user, e.g., "Alice"), run the application: `java ChatApp`
   - **Prompt 1 (Name):** Enter an identifying name (e.g., Alice)
   - **Prompt 2 (Local Port):** Enter an available port (e.g., 23000)
   - **Prompt 3 (Target IP):** Enter `127.0.0.1`
   - *Operational note:* Do not press Enter for the target port prompt until the second client is fully running, or the application will report that it could not connect.
3. In your second console (representing "Bob"), start a parallel instance: `java ChatApp`
   - **Prompt 1 (Name):** Enter an identifying name (e.g., Bob)
   - **Prompt 2 (Local Port):** Enter a different port to avoid collisions (e.g., 23001)
   - **Prompt 3 (Target IP):** Enter `127.0.0.1`
   - **Prompt 4 (Target Port):** Enter the port assigned to Alice (`23000`)
4. Quickly go back to the first console and complete the setup by entering Bob's assigned port (`23001`).
5. Write and exchange text messages between both terminals. Observe how the complex String parameters travel through the abstract RMI objects to trigger local notifications on the other JVM environment instantly.

> **Evidence Placeholder - Exercise 6.4.1:**
> [Insert here combined screenshots of both consoles (Alice and Bob) sharing and responding to messages in real time using the RMI architecture]
> ![Evidence Exercise 6.4.1](./imagenes/ejercicio6.4.1.png)

---

**Project documented for the Software Architecture course.**
