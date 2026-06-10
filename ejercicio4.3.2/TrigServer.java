import java.net.*;
import java.io.*;

public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String inputLine;
        String currentFunction = "cos"; // Default function
        
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje recibido: " + inputLine);
            
            if (inputLine.startsWith("fun:")) {
                currentFunction = inputLine.substring(4).toLowerCase();
                out.println("Operacion cambiada a: " + currentFunction);
            } else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result = 0.0;
                    
                    switch (currentFunction) {
                        case "sin":
                        case "seno":
                            result = Math.sin(number);
                            break;
                        case "cos":
                        case "coseno":
                            result = Math.cos(number);
                            break;
                        case "tan":
                        case "tangente":
                            result = Math.tan(number);
                            break;
                        default:
                            out.println("Error: funcion desconocida (" + currentFunction + ")");
                            continue;
                    }
                    out.println("Respuesta (" + currentFunction + " de " + number + "): " + result);
                } catch (NumberFormatException e) {
                    out.println("Error: Entrada no valida. Ingrese un numero o un comando 'fun:[sin|cos|tan]'");
                }
            }
        }
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
