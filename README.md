# Workshop-Architecture-logicielle-Hexagonale
Travaux pratiques en plusieurs exercices. Énoncés et solutions.

## Prérequis
- Un accès à Internet
- Java : OpenJDK 25 (dernière LTS stable). Peut être téléchargé et installé par l'intermédiaire de IntelliJ IDEA.
- Maven : 3.9+ (inclus avec IntelliJ IDEA)
- Un IDE. Dans l'idéal IntelliJ IDEA avec une licence Ultimate (gratuit si étudiant) ou Community (gratuit)
- Git. Il est inclus dans IntelliJ IDEA

En tant qu'étudiant, JetBrains propose le [Student Pack](https://www.jetbrains.com/fr-fr/academy/student-pack/).  
Il faut en profiter. Il est gratuit et il est très utile. Il permet en plus d'obtenir la réduction maximum de 40% quand vous aurez terminé vos études.  
En tant qu'Ultimate pack, il contient presque tous les outils de JetBrains. Les différents IDE pour les langages les plus courants :
- Java
- Python
- Rust
- C/C++
- ...

Avec la licence Ultimate, il apporte aussi des extensions très utiles pour Spring Boot et autres pour Pythons, ....  

Cloner le repository du [workshop Architecture logicielle Hexagonale](https://github.com/svernat/Workshop-Architecture-logicielle-Hexagonale.git)  

## Exercice 1
### Objectif
Initialiser un backend d'application Spring Boot avec un serveur d'API REST pour les objets Article, Catalogue d'articles et stock d'articles.  

Ouvrez le projet du repertoire **exercice-1**.  
Le README.md contient l'énoncé.  

## Exercice 2
### Objectif
Commencer à organiser le code sur le principe du **DDD** (Domain Driven Development).  

Ouvrez le projet du repertoire **exercice-2**.  
Le README.md contient l'énoncé.

## Exercice 3
### Objectif
Pour chacun des domaines fonctionnels, appliquer un découpage technique sur le principe de l'architecture logicielle **Hexagonale**.  
Le métier est le plus important. Quand on développe une application, on devrait commencer par cette partie.  
On peut ensuite poursuivre sur les liaisons avec l'extérieur que sont les composants techniques.  
Les points d'entrées comme l'API REST qui réceptionne les requêtes de l'utilisateur. Mais aussi, les évennements depuis un broker de messages Kafka.  
Les points de sorties comme la base de données. Mais, aussi les appels à d'autres services, à des LLM, ....  

Les points d'entrées et de sorties ne sont que des **"détails"**. Dixit Bob (Robert C. Martin).

Ouvrez le projet du repertoire **exercice-3**.  
Le README.md contient l'énoncé.

## Exercice 4
### Objectif
Retirer la dépendance directe entre userside et domain par l'appel de l'implémentation de la classe xxxService.  
Et surtout entre domain et serverside.

Ouvrez le projet du repertoire **exercice-4**.  
Le README.md contient l'énoncé.

## Exercice 5
### Objectif
Implémenter les tests unitaires. Ils sont relativement simples comme il n'y a pas de code métier.  

Ouvrez le projet du repertoire **exercice-5**.  
Le README.md contient l'énoncé.

## Exercice Bonus
### Objectif
Créer des modules maven pour chacun des domaines fonctionnels et y déplacer le code correspondant.

Ouvrez le projet du repertoire **exercice-bonus**.  
Le README.md contient l'énoncé.

## Exercice Microservices
### Objectif
Utiliser chacun des modules maven fonctionnels dans des microservices.

Ouvrez le projet du repertoire **exercice-microservices**.  
Le README.md contient l'énoncé.

## Exercice Super bonus
### Objectif
Séparer chacun des modules fonctionnels en modules maven techniques (domain, userside, serverside).
Retirer la dépendance à Spring sur les modules métiers (domain).

Ouvrez le projet du repertoire **exercice-super-bonus**.  
Le README.md contient l'énoncé.

## Bibliographie
### De Robert C. Martin
- **Clean Architecture** : ce livre explique les principes vus précédemment. Mais en allant plus loin. C'est parfois un peu dur et long à lire. Mais le chapitre sur les principes SOLID est important.
- **Clean Code** : Ce livre est plus facile à lire et donne plein de petites astuces pour avoir un code plus lisible.

### Software craft - 2e édition :
De Cyrille Martraire, Arnaud Thiéfaine, Dorra Bartaguiz, Fabien Hiegel, Houssam Fakih.  
Ce livre détaille les principales techniques de la boîte à outils du craft. TDD, BDD, DDD, Clean Code, SOLID, pair programming, IA.

### Julien Topçu
Vulgarisateur sur le **Craftmanships** et l'**architecture Hexagonale** en particulier.  
https://julientopcu.com/  
https://beyondxscratch.com/  
https://julien-topcu.medium.com/  
https://github.com/julien-topcu  

### Martin Fowler
Vulgarisateur sur plusieurs sujets, notamment sur les architectures hexagonales et les principes SOLID. Mais, il réfléchit aussi sur l'IA en ce moment.  
https://martinfowler.com/

### Eric Evans
Vulgarisateur sur les domain-driven design et les architectures hexagonales.  
https://www.dddcommunity.org/