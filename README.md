# Projet COO & POO

Projet réalisé dans le cadre des cours de COO et POO. 

### Prerequisites

Le projet à été initialement dévelopé avec intelliJ, cependant il est parfaitement compatible avec n'importe qu'elle IDE, il suffit de rajouter le repertoire ressources en tant que répertoire de ressources. Il suffit ensuite de lancer le fichier main.java. 


### Installing
 
Pour lancer le chatSytem il suffit de lancer le fichier Controller.java. Cependant il y a deux configurations possible : 
  - Lancer le controlleur en mode "Parrot" (Perroquet), il suffit de rajouter la ligne setParrot() après la création du 
  Controller dans le main.
  - Lancer le controlleur en mode "Normal", il n'y a rien a changer. Cependant on peut lancer le chatSystem en mode "Text" ou"Graphique". Pour ce faire il faut utiliser la méthode chooseGraphique(), toujours dans le main de la classe Controller.java. 
  
Dans le cas ou l'on utilise le .jar il suffit de le lancer avec la commande : 

```
java -jar Projet_COO_POO.jar
```

## Running the tests

Nous avons lancé plusieurs types de test à l'aide de JUnit 4, ces derniers sont présent des les différentes classes du module Test.

Dans le cadre des tests nous avons aussi réalisé un "Parrot" devant être lancé sur une autre machine pour pouvoir 
effectuer des test sur la partie réseau.

### Break down into end to end tests

**Les tests du model**

Les test de la classe model vérifie les mises à jour d'informations du model, 
tel que le changement de status d'un utilisateur ou encore la mise à jour des 
flages lors de la connexion de l'utilisateur de l'application.

Cela correspond aux fichiers de test suivant :
```
AllUsersTest.java
ModelTest.java
```

**Les tests du Controller et du réseau**

Pour les tests réseaux nous avons utilisé le "Parrot" dévellopé dans ce but. 
Nous avons donc deux tests :
- Test de la reception et de l'envoie d'un message, déroulement du test :
```
On lance le parrot sur une autre machine,
 puis lance la méthode de test sur la machine local :
    - Crée une session du ChatSystem en mode Text avec un utilisateur de test.
    - Envoie un message "Coucou" au Parrot.
    - Récupére le message et le compare avec le message envoyé.
```
- Test de deconnection d'un utilisateur, déroulement du test :
```
On lance le parrot sur une autre machine, 
puis lance la méthode de test sur la machine local :
    - Crée une session du ChatSystem en mode Text avec un utilisateur de test.
    - Envoie un message "Disconnect" au Parrot.
    - Attend 1s est test le statut du Parrot, pour vérifier la déconnexion.
```
## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) -IDE utilisé

## Authors

* **CROS Camille** - *Co-develloper*

* **CORFA Sébastien** - *Co-develloper*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Ressources

* Nous avons utilisé des ressources provenant du jeu Portal2(http://store.steampowered.com/app/620/?l=french)
* Les Images utilisées sont toutes libres de droits.


