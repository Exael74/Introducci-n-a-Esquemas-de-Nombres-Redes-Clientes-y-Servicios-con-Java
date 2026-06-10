import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatApp implements ChatService {

    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        System.out.println("\n[" + sender + "]: " + message);
        System.out.print("Tu mensaje: "); // Re-prompt
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingresa tu nombre para el chat: ");
        String username = scanner.nextLine();
        
        System.out.print("Ingresa el puerto local para hospedar tu servicio (ej. 23000): ");
        int localPort = Integer.parseInt(scanner.nextLine());
        
        ChatApp chatApp = new ChatApp();
        
        // 1. Inicializar el Servidor RMI local
        try {
            ChatService stub = (ChatService) UnicastRemoteObject.exportObject(chatApp, 0);
            Registry registry = LocateRegistry.createRegistry(localPort);
            registry.rebind("ChatService", stub);
            System.out.println("Servicio de chat hospedado localmente en el puerto " + localPort);
        } catch (Exception e) {
            System.err.println("Excepcion al iniciar el servidor local: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        // 2. Conectarse a un par remoto
        System.out.println("\n--- Conectarse a otro usuario ---");
        System.out.print("Ingresa la IP del host destino (ej. 127.0.0.1): ");
        String targetIP = scanner.nextLine();
        System.out.print("Ingresa el puerto del host destino: ");
        int targetPort = Integer.parseInt(scanner.nextLine());
        
        ChatService remoteService = null;
        try {
            Registry remoteRegistry = LocateRegistry.getRegistry(targetIP, targetPort);
            remoteService = (ChatService) remoteRegistry.lookup("ChatService");
            System.out.println("Conectado exitosamente al chat remoto!");
        } catch (Exception e) {
            System.err.println("No se pudo conectar al host destino. Asegurese de que el otro lado ya este corriendo.");
        }
        
        // 3. Ciclo de chat
        System.out.println("\nPuedes empezar a escribir. Escribe 'salir' para terminar.");
        while (true) {
            System.out.print("Tu mensaje: ");
            String msg = scanner.nextLine();
            
            if (msg.equalsIgnoreCase("salir")) {
                break;
            }
            
            if (remoteService != null) {
                try {
                    remoteService.receiveMessage(username, msg);
                } catch (RemoteException e) {
                    System.err.println("Error al enviar mensaje. El otro usuario se desconecto?");
                }
            } else {
                System.out.println("No estas conectado a nadie.");
            }
        }
        
        scanner.close();
        System.exit(0);
    }
}
