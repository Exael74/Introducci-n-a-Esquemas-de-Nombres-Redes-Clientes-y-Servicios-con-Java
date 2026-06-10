import java.net.*;
import java.io.*;
import java.nio.file.*;

public class HttpServerMulti {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor web iniciado en el puerto 35000...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                continue;
            }

            try {
                processRequest(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clientSocket.close();
            }
        }
    }

    private static void processRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream outStream = clientSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true);

        String inputLine;
        String requestLine = null;

        while ((inputLine = in.readLine()) != null) {
            if (requestLine == null) {
                requestLine = inputLine;
            }
            if (!in.ready()) {
                break;
            }
        }

        if (requestLine != null && requestLine.startsWith("GET")) {
            String[] tokens = requestLine.split(" ");
            String fileRequested = tokens[1];
            
            if (fileRequested.equals("/")) {
                fileRequested = "/index.html";
            }

            // Simple public directory simulated in the current folder
            Path filePath = Paths.get("public" + fileRequested);

            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                byte[] fileData = Files.readAllBytes(filePath);
                String contentType = getContentType(fileRequested);

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + contentType);
                out.println("Content-Length: " + fileData.length);
                out.println();
                out.flush();
                outStream.write(fileData);
                outStream.flush();
            } else {
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: text/html");
                out.println();
                out.println("<html><body><h1>404 Not Found</h1></body></html>");
                out.flush();
            }
        }
        in.close();
        outStream.close();
    }

    private static String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html") || fileRequested.endsWith(".htm")) return "text/html";
        if (fileRequested.endsWith(".css")) return "text/css";
        if (fileRequested.endsWith(".js")) return "application/javascript";
        if (fileRequested.endsWith(".png")) return "image/png";
        if (fileRequested.endsWith(".jpg") || fileRequested.endsWith(".jpeg")) return "image/jpeg";
        return "text/plain";
    }
}
