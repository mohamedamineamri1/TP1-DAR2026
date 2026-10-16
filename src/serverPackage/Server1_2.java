package serverPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_2 {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("je suis un serveur en attente la connexion d'un client ");
            Socket clientSocket = serverSocket.accept();
            System.out.println("un client est connecte");
            DataInputStream  in  = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            int x = in.readInt();             
            System.out.println("Reçu du client : " + x);
            int resultat = x * 5;               
            out.writeInt(resultat);             
            out.flush();
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}