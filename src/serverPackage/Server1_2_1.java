package serverPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_2_1 {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket =new ServerSocket(1234);
            System.out.println("je suis un serveur en attente la connexion d'un client ");
            Socket clientSocket = serverSocket.accept();
            System.out.println("un client est connecté");
            DataInputStream  in  = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                int x = in.readInt();
                if (x == 0) {                      
                    System.out.println("le client a envoyé 0 : fin de la communication.");
                    break;
                }
                int resultat = x * 5;
                System.out.println("Reçu :" + x + " ->  envoye : " + resultat);
                out.writeInt(resultat);
                out.flush();
            }
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("sockets fermés côté serveur.");

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}