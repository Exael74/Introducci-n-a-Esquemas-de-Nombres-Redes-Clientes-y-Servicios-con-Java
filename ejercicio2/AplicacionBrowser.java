
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class AplicacionBrowser {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Por favor ingrese la url");
            String direccion = scanner.nextLine();

            URL url = new URI(direccion).toURL();

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter writer = new BufferedWriter(new FileWriter("Resultado.html"));

            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }

            reader.close();
            writer.close();

            System.out.println("Archivo Resultado.html creado correctamente.");

        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        scanner.close();
    }
}
