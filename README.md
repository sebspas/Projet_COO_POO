# Projet COO & POO

Projet réalisé dans le cadre des cours de Conception et Programmation
 Orienté Objet 2017.

### Prerequisites

Le projet à été initialement développé avec intelliJ, cependant il est
parfaitement compatible avec n'importe quelle IDE : il suffit de rajouter
le répertoire *ressources* en tant que répertoire de ressources.
Pour exécuter, lancer le fichier *Controller.java*.

### Installing
 
Pour lancer le ChatSytem il suffit de lancer le fichier *Controller.java*.
Cependant il y a deux configurations possible :
  * Lancer le controlleur en mode "Parrot" (Perroquet), il suffit de
    rajouter la ligne setParrot() après la création du
  Controller dans le main.
  * Lancer le controlleur en mode "Normal", il n'y a rien a changer.
    Cependant on peut lancer le chatSystem en mode "Text" ou"Graphique".
    Pour ce faire il faut utiliser la méthode chooseGraphique(), toujours
    dans le main de la classe Controller.java.
  
Dans le cas ou l'on utilise le .jar il suffit de le lancer avec la commande : 

```
java -jar Projet_COO_POO.jar
```

***Remarque : il est nécessaire d'avoir installer JUnit4 afin de pouvoir
compiler le projet et lancer les tests.***

## Implementation

Nous avons réalisé ce projet en respectant du mieux que possible les
spécifications du cahier des charges fourni.

### Features

Nous vous proposons ci-dessus la liste des différentes fonctionnalités
que nous avons implémenté :
  * Nous avons codé et partagé à l'ensemble du groupe le protocole de
    messages établi entre nous préalablement
    (classes network.ControlMessage, pour la
    phase de connexion, et network.Message, pour la discussion entre
    utilisateurs).

  * La fenêtre pricipale de l'application est divisée en trois parties :
    l'utilisateur (icône, pseudo, bouton de déconnexion [Logout]), les
    utilisateurs connectés (Online) et ceux qui se sont déconnectés
    (Offline) durant la session courante.

  * Le bouton [Logout] permet à l'utilisateur de fermer l'application.

  * Tout utilisateur quittant l'application envoit un notification sur
    le réseau et sera vu par les autres utilisateurs courants comme
    "Offline". Il n'est pas possible d'envoyer de message à un
    utilisateur déconnecté.

  * Si l'utilisateur relance l'application
    en utilisant le même pseudo, les autres utilisateurs
    pourront à nouveau accéder à l'ensemble de leur dernière discussion
    avec celui-ci (et lui parler à nouveau).

  * Des sons de notification ont été ajoutés pour notifier l'utilisateur
    des événements suivants : essai de connexion avec un login vide,
    connexion avec entrée d'un login, apparition d'un nouvel utilisateur
    dans le ChatSystem, déconnexion d'un utilisateur, re-connexion d'un
    utilisateur connu, déconnexion.

  * A la réception d'un nouveau message, l'utilisateur est alerté par un
    son et le l'icône de l'émetteur change. Son icône est rétabli lorsque
    l'utilisateur clique sur le bouton [Talk !] pour accéder à la
    conversation.

  * Nous avons implémenté l'envoi et la réception de fichiers via notre
    application. Dans la fenêtre de discussion, l'utilisateur A peut choisir
    un fichier sur son disque à envoyer à l'utilisateur B en appuyant sur
    le bouton [Files].

  * Le System notifiera l'utilisateur A de l'envoi de son fichier et
    l'utilisateur B de la réception de ce dernier. Le fichier envoyé est
    automatiquement sauvegardé à la racine du projet sur le disque de B.

  * Si le fichier envoyé est une image, elle sera automatiquement
    redimmensionnée et affichée dans la fenêtre de discussion de B.


### Not implemented

Nous n'avons pas pu implémenter les features suivantes :
  * Les discussions de groupes n'ont pas pu être implémentées
    (faute de groupes assez avancés pour pouvoir tester).
  * La vérification de la disponibilité du login a finalement été
    abandonnée après avoir juger la procédure peut adapter à une
    application P2P tel que celle-ci.

**L'interface graphique**

![potato](https://image.spreadshirtmedia.net/image-server/v1/compositions/111312403/views/3,width=300,height=300,version=1385669094/portal-2-glados-because-i-m-a-potato-mug.jpg)
![GUI](http://hpics.li/69fe81a)

## Running the tests

Nous avons lancé plusieurs types de test à l'aide de JUnit 4, ces derniers
sont présents dans les différentes classes du module Test.

Dans le cadre des tests, nous avons aussi codé un "Parrot" devant être
lancé sur une autre machine pour pouvoir effectuer des tests sur la partie
réseau.

### Break down into end to end tests

**Les tests du model**

Les tests de la classe *Model* vérifie les mises à jour d'informations du model,
tel que le changement de status d'un utilisateur ou encore la mise à jour des 
flags lors de la connexion de l'utilisateur à l'application.

Cela correspond aux fichiers de test suivant :
```
AllUsersTest.java
ModelTest.java
```

**Les tests du Controller et du réseau**

Pour les tests réseaux nous avons utilisé le "Parrot" développé dans ce but.
Nous avons donc deux tests :
- Test de la réception et de l'envoi d'un message, déroulement du test :
```
On lance le parrot sur une autre machine,
 puis on lance la méthode de test sur la machine local :
    - Crée une session du ChatSystem en mode Text avec un utilisateur de test.
    - Envoie un message "Coucou" au Parrot.
    - Récupère le message et le compare avec le message envoyé.
```
- Test de déconnexion d'un utilisateur, déroulement du test :
```
On lance le parrot sur une autre machine, 
puis on lance la méthode de test sur la machine local :
    - Crée une session du ChatSystem en mode Text avec un utilisateur de test.
    - Envoie un message "Disconnect" au Parrot.
    - Attend 1s et test le statut du Parrot, pour vérifier la déconnexion.
```
## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) -IDE utilisée

## Authors

 **CROS Camille** - *Co-developer*

 **CORFA Sébastien** - *Co-developer*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Ressources

* Nous avons utilisé des ressources provenant du jeu Portal2 (http://store.steampowered.com/app/620/?l=french)
* Les Images utilisées sont toutes libres de droits.


