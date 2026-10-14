package clientPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        try (Scanner clavier = new Scanner(System.in);
             Socket socket = new Socket("localhost", 5000);
             DataOutputStream out = new DataOutputStream(
                     socket.getOutputStream());
             DataInputStream in = new DataInputStream(
                     socket.getInputStream())) {

            System.out.println("Connexion au serveur reussie !");

            while (true) {

                System.out.print("Saisir un entier (0 pour quitter) : ");
                int x = clavier.nextInt();
                out.writeInt(x);
                out.flush();
                if (x == 0) {
                    System.out.println("Fin de la communication.");
                    break;
                }
                int resultat = in.readInt();

                System.out.println("Resultat recu : " + resultat);
            }

        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
