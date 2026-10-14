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







