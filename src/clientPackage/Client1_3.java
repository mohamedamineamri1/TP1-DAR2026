package clientPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1_3 {
    private static final int PORT = 1234;
    public static void main(String[] args) {
        // IP de la machine serveur : en argument, sinon saisie au clavier
        Scanner sc = new Scanner(System.in);
        String ipServeur;
        if (args.length > 0) {
            ipServeur = args[0];
        } else {
            System.out.print("Adresse IP du serveur : ");
            ipServeur = sc.nextLine().trim();
        }
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket =new Socket();
            socket.connect(new InetSocketAddress(ipServeur, PORT), 5000); // 5s
            System.out.println("Client connecté au serveur " + ipServeur + ":" + PORT);
            DataOutputStream out =new DataOutputStream(socket.getOutputStream());
            DataInputStream  in  =new DataInputStream(socket.getInputStream());
            int choix;
            do {
                System.out.println("\n/*-+ CALCULATRICE +-*/");
                System.out.println("1 - Addition");
                System.out.println("2 - Soustraction");
                System.out.println("3 - Multiplication");
                System.out.println("4 - Division");
                System.out.println("0 - Quitter");
                System.out.print("votre choix : ");
                choix = sc.nextInt();
                if (choix == 0) {
                    out.writeInt(0);              
                    out.flush();
                    break;
                }
                if (choix < 1 || choix > 4) {
                    System.out.println("choix invalide, recommencez.");
                    continue;
                }
                System.out.print("Entrez x : ");
                int x = sc.nextInt();
                System.out.print("Entrez y : ");
                int y = sc.nextInt();
                out.writeInt(choix);
                out.writeInt(x);
                out.writeInt(y);
                out.flush();
                int statut = in.readInt();
                if (statut == 0) {
                    double resultat = in.readDouble();
                    System.out.println("Résultat = " + resultat);
                } else if (statut == 1) {
                    System.out.println("Erreur : division par 0 impossible.");
                } else {
                    System.out.println("Erreur : operation inconnue.");
                }
            } while (true);

            System.out.println("Fin de la communication.");
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        sc.close();
    }
}