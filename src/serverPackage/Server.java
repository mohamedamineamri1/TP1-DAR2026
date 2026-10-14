package serverPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            System.out.println("Serveur en attente d'un client...");

            try (Socket socket = serverSocket.accept();
                 DataInputStream in = new DataInputStream(
                         socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(
                         socket.getOutputStream())) {
                System.out.println("Client connecte !");
                while (true) {

                    int x = in.readInt();
                    if (x == 0) {
                        System.out.println("Arret de la communication.");
                        break;
                    }
                    int resultat = x * 5;
                    System.out.println("Valeur recue : " + x);
                    System.out.println("Resultat : " + resultat);
                    out.writeInt(resultat);
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
