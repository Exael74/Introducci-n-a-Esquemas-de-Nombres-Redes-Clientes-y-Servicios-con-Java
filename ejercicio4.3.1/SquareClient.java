
import java.io.*;
import java.net.*;

public class SquareClient {

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("127.0.0.1", 35000);

            out = new PrintWriter(socket.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo establecer la conexión.");
            System.exit(1);
        }

        BufferedReader stdIn
                = new BufferedReader(
                        new InputStreamReader(System.in));

        String userInput;

        System.out.println("Ingrese números para calcular su cuadrado:");
        System.out.println("(Ctrl + Z y Enter para salir en Windows)");

        while ((userInput = stdIn.readLine()) != null) {

            out.println(userInput);

            String respuesta = in.readLine();

            System.out.println("Servidor: " + respuesta);
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
