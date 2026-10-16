package clientPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client1_2_1 {

    public static void main(String[] args) {
        try {
            System.out.println("je suis un client pas encore connecte..");
            Socket socket =new Socket("127.0.0.1", 1234);
            System.out.println("client connecté au serveur");
            DataOutputStream out =new DataOutputStream(socket.getOutputStream());
            DataInputStream  in  =new DataInputStream(socket.getInputStream());
            Scanner sc =new Scanner(System.in);
            int x;
            do {
                System.out.print("entrez un entier (0 pour quitter) : ");
                x= sc.nextInt();
                out.writeInt(x);                  
                out.flush();
                if (x!= 0) {
                    int resultat = in.readInt();
                    System.out.println("Résultat (x * 5) = " + resultat);
                }
            } while (x!= 0);
            System.out.println("fin de la communication.");
            sc.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}