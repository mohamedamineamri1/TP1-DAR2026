# TP1-DAR2026 — Les sockets en mode connecté TCP

Matière : Développement d'applications réparties Auditoire : LSI 3 — Faculté des Sciences de Sfax — 2026/2027 

Enseignante des TPs : Mme Zeineb DAOUD 

Étudiant : Mohamed Amine AMRI

# Sommaire
Activité 1-1 : Première connexion Client/Serveur

Activité 1-2 : Service de calculatrice

Activité 1-3 : Amélioration du service de calculatrice

# Activité 1-1 : Première connexion Client/Serveur

# Objectif

Etablir une premiere communication Client/Serveur en Java à l'aide des sockets TCP . Deux applications distinctes sont creees :

un serveur qui attend une connexion et un client qui s'y connecte. 

# Etape 1 : Preparation de l'environnement Git

La commande ( # git --version  )permet de vérifier que Git est installe.

# Etape 2 : Preparation de l'environnement Java

La commande java --version permet de vérifier la version du JDK. Le projet utilise JavaSE-21 

# Etape 3 : Creation du serveur
3.1 Création du projet et initialisation du dépôt Git

Un projet Java nommé TP1-DAR2026 a été créé dans Eclipse avec les réglages par défaut, 

puis un dépôt Git local a été initialisé et le premier commit réalisé :
<img width="551" height="695" alt="image" src="https://github.com/user-attachments/assets/831975f4-327f-4ee5-a3c1-4464328b19a3" />

# 3.2 Structure du projet

Deux packages ont été créés dans le dossier src :

serverPackage contenant la classe Server
clientPackage contenant la classe Client

<img width="365" height="257" alt="Capture d&#39;écran 2026-10-13 215203" src="https://github.com/user-attachments/assets/6f77937a-3cde-4fdf-b147-5cae3b347577" />

# 3.3 Code du serveur

<img width="892" height="508" alt="Capture d&#39;écran 2026-10-13 215211" src="https://github.com/user-attachments/assets/0d62a887-e670-400a-95c5-3a18672e3790" />

# Explication détaillée :

import java.net.ServerSocket; import java.net.Socket; : les classes sont dans le package java.net 


new ServerSocket(1234) : crée un socket d'écoute qui réserve le port 1234 sur la machine. Le serveur est alors prêt à recevoir des demandes de connexion.


serverSocket.accept() : méthode bloquante. Le programme s'arrête sur cette ligne tant qu'aucun client ne se connecte. 

Quand un client arrive, elle retourne un nouvel objet Socket (clientSocket) qui représente la communication avec ce client précis.
clientSocket.close() puis serverSocket.close() : libèrent les ressources (la connexion avec le client, puis le port d'écoute). 

try/catch (IOException e) : la création du socket, accept() et close() peuvent lever une IOException (port déjà utilisé, erreur réseau…). 
Elle doit donc être gérée. 
3.4 Exécution du serveur et interprétation  

<img width="507" height="88" alt="Capture d&#39;écran 2026-10-13 215241" src="https://github.com/user-attachments/assets/04ce4053-d6c7-4d66-accc-c78966b23941" />  

Le message Je suis un serveur en attente la connexion d'un client s'affiche : le ServerSocket a été créé avec succès sur le port 1234 et le serveur est bloqué sur accept().
Le message un client est connecté s'affiche ensuite : accept() a été débloqué car un client s'est connecté (le client de l'étape 4 a été lancé).
Le serveur ferme ses sockets et le programme se termine (<terminated> dans la console Eclipse). 

# Rmq: 

Si on lance le serveur sans lancer de client, seul le premier message apparait et le programme reste en attente indefiniment. 

# Etape 4 : Création du client 

4.1 Code du client 

<img width="748" height="527" alt="Capture d&#39;écran 2026-10-13 215223" src="https://github.com/user-attachments/assets/52af2b2a-b780-42fa-93ee-d78ee83438eb"/>  

# Explication détaillée :

new Socket("127.0.0.1", 1234) : le client demande une connexion TCP à la machine d'adresse 127.0.0.1 (adresse de boucle locale) sur le port 1234,le même que celui du serveur. 

Si le serveur n'est pas lancé,  
le constructeur lève une exception attrapée par le catch. 

socket.close() : ferme la connexion proprement.


# Résultat attendu et interprétation :


Je suis un client pas encore connecté…
Client connecté au serveur   



Le premier message est affiché avant la connexion. Le second prouve que le constructeur Socket(...) a réussi :   

le serveur était bien en attente (il a affiché de son côté un client est connecté).  

La communication TCP est donc établie. 

# Remplacement de localhost par une adresse IP 

L’adresse localhost a été remplacée par l’adresse IP réelle de la machine serveur afin d’établir la connexion à travers le réseau local.  
Cette modification permet au client de se connecter au serveur à condition que celui-ci écoute sur l’adresse IP utilisée et que le réseau autorise la communication sur le port choisi.  

# Activité 1-2 : Service de calculatrice 

# Objectif

Étendre l'application de l'activité 1-1 pour que le client envoie un entier x au serveur, que le serveur calcule x * 5 et renvoie le résultat.  

On utilise DataInputStream / DataOutputStream pour échanger les entiers.  

# Etape 1 : Préparation de l'espace de travail

On repart du projet TP1-DAR2026 de l'activite 1-1 (meme depôt Git). 
# Etape 2 : Création du service de multiplication 

# 2.1 Principe du protocole  

   CLIENT                                   SERVEUR
     |  --------- connexion TCP ---------->   | accept()
     |  1. saisit x au clavier                |
     |  2. writeInt(x) ---------------------> | 3. readInt()
     |                                        | 4. résultat = x * 5
     |  6. readInt()  <---------------------- | 5. writeInt(résultat)
     |  7. affiche le résultat     

     



# Explication détaillée :

socket.getInputStream() / getOutputStream() : donnent les flux bruts d'octets du socket (lecture /écriture).  

DataOutputStream / DataInputStream : enveloppent ces flux pour lire/ecrire des types primitifs Java directement (writeInt,readInt). 

readInt() est bloquant :le serveur attend que le client envoie x (c'est l'étape « le serveur était en attente de ce nombre »). 

flush() force l'envoi immédiat des données sans attendre que le tampon soit plein. 
 
Le calcul x * 5 est fait uniquement côté serveur : le client n'a pas de calculatrice. 


<img width="1402" height="190" alt="image" src="https://github.com/user-attachments/assets/7782f557-a1d1-44f9-ac00-abf65abecf17" />


<img width="1405" height="151" alt="image" src="https://github.com/user-attachments/assets/03e5d5f0-fffe-4564-aaae-fd6a5f60e04f" />



<img width="1427" height="195" alt="image" src="https://github.com/user-attachments/assets/e26969c3-5811-49d2-9871-6bdc8ec77c95" />



<img width="625" height="157" alt="image" src="https://github.com/user-attachments/assets/672fe24a-31a9-47e4-8666-4fe3182d6e8e" /> 




# 2.4 Pourquoi DataInputStream / DataOutputStream pour les entiers négatifs ?

Si l'on utilisait directement OutputStream.write(int),un seul octet serait envoyé(les 8 bits de poids faible) : un entier comme 300 ou -4 serait déformé  

à la réception (read() renvoie une valeur entre 0 et 255).

DataOutputStream.writeInt(x) envoie les 4 octets de l'entier (32 bits,complément à deux),et DataInputStream.readInt() les reconstitue exactement.  

Les valeurs négatives sont donc transmises correctement,sans perte.    

# avec boucle (Client1_2_1/Server1_2_1)

# Explication détaillee :

Le 0 sert de message de fin : le client l'envoie au serveur (sinon le serveur resterait bloqué sur readInt() pour toujours),puis sort de la boucle. 

Le serveur lit x, détecte 0 et sort de sa boucle while : aucun résultat n'est renvoyé pour 0, donc le client ne fait pas de readInt() dans ce cas  

(sinon il se bloquerait).  

Les deux côtés ferment ensuite leurs flux et leurs sockets : la fermeture est propre des deux côtés.  

Si le client était arrêté brutalement, le serveur recevrait une EOFException / SocketException, attrapée par le catch (IOException e).  

<img width="557" height="192" alt="image" src="https://github.com/user-attachments/assets/5a0a0c78-321b-42dd-8720-27bbb41064b6" />   

# Activité 1-3 : Amélioration du service de calculatrice 

# Etape 1 : Connexion Client/Serveur sur deux machines distinctes  

# 1.1 Principe 

Avec 127.0.0.1 /localhost, le client ne peut joindre que la machine locale.  

Pour utiliser deux machines du même réseau local (Wi-Fi/Ethernet) :

le serveur doit écouter sur l'adresse IP réelle de sa machine (exmp  192.168.1.20/192.168.2.2) , 

le client doit se connecter à cette adresse IP. 



<img width="432" height="146" alt="image" src="https://github.com/user-attachments/assets/53e7dbfc-c7c3-4b1b-97ba-c6e40ea14ca8" />  

ici serveur non disponible (ip invalide / n'est pas le meme reseau )  

Les deux machines doivent être dans le même sous-reseau (ex 192.168.1.x).

# 1.3 Classes utilisées 

 Classe	Rôle 
 
java.net.InetAddress	Représente une adresse IP (InetAddress.getByName("192.168.1.20")). 

java.net.InetSocketAddress	Représente le couple adresse IP + port.  

# Etape 2 : Service de calculatrice amélioré

# 2.1 Fonctionnalités

Le service propose quatre opérations : Addition, Soustraction, Multiplication, Division.  

Le client :

affiche un menu et lit le choix de l'opération ; 

lit les valeurs ; 

transmet le choix + les valeurs au serveur ; 

reçoit et affiche le résultat calculé par le serveur. 

# Explication (cote serveur) :

new ServerSocket() (sans argument) crée un socket non lié ; bind(new InetSocketAddress(adresse, PORT)) le lie ensuite à l'IP réelle + port.  

Le serveur n'accepte alors que les connexions arrivant par cette interface réseau. 

InetAddress.getLocalHost() donne l'adresse de la machine, mais sur une machine ayant plusieurs cartes réseau (Wi-Fi, VirtualBox)   

elle peut être incorrecte : on peut donc passer l'IP voulue en argument . 

clientSocket.getInetAddress() affiche l'IP de la machine cliente connectée.  

Le switch effectue le calcul côté serveur selon le code reçu.  

La division par zéro est détectée (statut = 1) pour ne pas renvoyer Infinity. 

Le cast (double) avant la multiplication évite un débordement d'entier.  

# Explication (cote client ) :

new Socket() + connect(new InetSocketAddress(ipServeur, PORT), 5000) : le client se connecte à l'IP réelle du serveur.  

Le timeout de 5 s évite de rester bloqué si la machine est injoignable .  

Le menu est affiché en boucle jusqu'au choix 0 qui sert de message de fin. 

Le client ne calcule rien : il envoie le choix et les opérandes, puis affiche le résultat . 

L'ordre des writeInt côté client doit être exactement celui des readInt côté serveur   sinon les valeurs sont décalées.


# Conclusion 

# Activité 1-1 :  

j’ai créé un serveur (ServerSocket et accept()) et un client (Socket) qui établissent une première connexion TCP sur la même machine, avec un dépôt Git pour le projet. 

# Activité 1-2 : 

le client envoie un entier au serveur avec DataOutputStream, le serveur calcule x * 5 et renvoie le résultat avec DataInputStream. Une boucle permet plusieurs saisies, et 0 termine la communication en fermant les sockets. 

# Activité 1-3 :   

l’application fonctionne entre deux machines distinctes grâce à l’adresse IP réelle du serveur (InetAddress, InetSocketAddress). La calculatrice propose quatre opérations (+, −, ×, ÷) avec un menu, et le calcul reste côté serveur.





