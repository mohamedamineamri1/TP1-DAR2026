package clientPackage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client1_2 {

    public static void main(String[] args) {
        try {
            System.out.println("je suis un client pas encore connecte..");
            Socket socket = new Socket("127.0.0.1", 1234);
            System.out.println("client connecte au serveur");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream  in  = new DataInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);
            System.out.print("entrez un entier x : ");
            int x = sc.nextInt();
            out.writeInt(x);                   
            out.flush();
            int resultat = in.readInt();      
            System.out.println("Resultat (x * 5) = " + resultat);
            sc.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}