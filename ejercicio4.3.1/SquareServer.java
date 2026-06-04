
import java.io.*;
import java.net.*;

public class SquareServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(35000);
        System.out.println("Servidor iniciado en el puerto 35000");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado");

        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {

            System.out.println("Número recibido: " + inputLine);

            try {
                int numero = Integer.parseInt(inputLine);
                int cuadrado = numero * numero;

                out.println("Cuadrado: " + cuadrado);

            } catch (NumberFormatException e) {
                out.println("Error: valor no numérico");
            }
        }

        System.out.println("Cliente desconectado");

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
