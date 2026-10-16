package serverPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_3 {
    private static final int PORT = 1234;
    public static void main(String[] args) {
        // IP reelle du serveur : passee en argument,sinon IP détectee automatiquement
        String ipServeur = (args.length > 0) ? args[0] : null;

        try {
            InetAddress adresse = (ipServeur != null)
                    ? InetAddress.getByName(ipServeur)
                    : InetAddress.getLocalHost();
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(adresse, PORT));
            System.out.println("serveur en ecoute sur " + adresse.getHostAddress() + ":" + PORT);
            System.out.println("se suis un serveur en attente la connexion d'un client ");
            Socket clientSocket = serverSocket.accept();
            System.out.println("un client est connecte : "
                    + clientSocket.getInetAddress().getHostAddress());
            DataInputStream  in  = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                int choix = in.readInt();
                if (choix == 0) {
                    System.out.println("Le client a quitté.");
                    break;
                }
                int x = in.readInt();
                int y = in.readInt();
                int statut = 0;
                double resultat = 0;
                switch (choix) {
                    case 1: resultat = x + y;
                    break;
                    case 2: resultat = x - y; 
                    break;
                    case 3: resultat =(double) x * y; 
                    break;
                    case 4:
                        if (y == 0) statut =1;               // division par 0
                        else resultat =(double) x / y;
                        break;
                    default: statut =2;                       // operation inconnue
                }

                System.out.println("Operation " + choix + ":" + x + "," + y
                        + "  ->  statut=" + statut + " resultat=" + resultat);
                out.writeInt(statut);
                if (statut == 0) {
                    out.writeDouble(resultat);
                }
                out.flush();
            }
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Sockets fermés côté serveur.");
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}