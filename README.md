# Workshop-Architecture-logicielle-Hexagonale
Travaux pratiques en plusieurs exercices. Énoncés et solutions.

## Prérequis
- Un accès à Internet
- Java 25
- Un IDE. Dans l'idéal IntelliJ IDEA Community ou avec une licence Ultimate
- Git. Il est inclus dans IntelliJ IDEA

En tant qu'étudiant, JetBrains propose le [Student Pack](https://www.jetbrains.com/fr-fr/academy/student-pack/).  
Il faut en profiter. Il est gratuit et il est très utile. Il permet en plus d'obtenir la réduction maximum de 40% quand vous aurez terminé vos études.  
En tant qu'Ultimate pack, il contient tous preque tous les outils de JetBrains. Les différents IDE pour les langages les plus courants :
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
Les objets seront stockés dans une base de données SQLite.  

Le modèle de données :
- article
  - id
  - name
  - description
  - price
- stock
  - article_id
  - quantity
- order (commandes)
  - id
  - username
  - status
- articles_in_order
  - order_id
  - article_id
  - quantity

### Travail à réaliser 
En tant que **développeur débutant**, n'ayant pas de notion de structuration de code et de Clean Code.  
Vous devez générer le **CRUD** et les **routes REST** avec **Spring Boot** pour chacun des objets du modèle.
CRUD : Create, Read (Get et GetAll), Update, Delete  

Le but ici est d'avoir le code le plus simple dans un seul package en une ou plusieurs classes.  
Une seule classe ici est très bien et permettra encore mieux de voir, par la suite, l'intérêt de structurer son code.  
Après si vous séparez les classes par objet et les routes par type d'objet, ça va aussi.  

Les **ID** seront des String générés par **uuid**.  

### Astuce
Aidez-vous d'un assistant de code comme Junie dans IntelliJ ou Github Copilot, Gemini, Claude Code.  
IntelliJ permet d'ajouter des plugins pour ces assistants.  
Cela nous permettra de discuter de l'intérêt de ces assistants et d'aborder comment les utiliser au mieux.  

### Conclusion
Le code n'est pas forcément très lisible surtout quand le nombre de types d'objet se multiplient.  

Il y a d'autres inconvénients comme la difficulté d'implémenter les tests unitaires, d'apporter des changements techniques pour remplacer une base de données SQL par une base de données NoSQL, ....    

Essayez de voir comment implémenter les TU. Vous verrez que c'est impossible, car vous devrez instancier une base de données. Du coup, ce ne sont plus des TU.  

## Exercice 2
### Objectif
Commencer à organiser le code sur le principe du **DDD** (Domain Driven Development)  

### Travail à réaliser
Créer les packages et répartir les routes et CRUD pour chacun des objets du modèle de données :
- article
  - article
- stock
  - stock
- order
  - order
  - articles_in_order

Séparer order et articles_in_order dans des classes différentes.  

### Conclusion
Le code est déjà plus lisible et les modifications seront plus localisées.  
Mais techniquement, l'implémentation des tests unitaires n'a pas été facilité et les changements de technologies seront toujours aussi compliqués.  
Nous n'avons fait ici qu'un découpage fonctionnel.  

## Exercice 3
### Objectif
Pour chacun des domaines fonctionnels, appliquer un découpage technique sur le principe de l'architecture logicielle **Hexagonale**.  
Le métier est le plus important. Quand on développe une application, on devrait commencer par cette partie.  
On peut ensuite poursuivre sur les liaisons avec l'extérieur que sont les composants techniques.  
Les points d'entrées comme l'API REST qui réceptionne les requêtes de l'utilisateur. Mais aussi, les évennements depuis un broker de messages Kafka.  
Les points de sorties comme la base de données. Mais, aussi les appels à d'autres services, à des LLM, ....  

Les points d'entrées et de sorties ne sont que des **"détails"**. Dixit Bob (Robert C. Martin).

### Travail à réaliser
Découper chaque domaine fonctionnel en trois packages :
- domain
- userside
- serverside

#### domain
Contient le **métier**, le modèle de données, les règles de gestion, les calculs, les algorithmes, les filtres qui n'auront pas pu être mis dans les requêtes SQL.  

Il n'est censé n'avoir **aucune adhérence à des contraintes techniques** comme Hibernate / JPA.  
Le modèle de données ne doit donc plus avoir d'annotation @Entity et @JsonProperty.  
On peut exceptionnellement accepter l'anottation @Service sur la classe contenant les règles de gestion.  

Dans le cadre d'un CRUD, le code de la classe de service peut être très pauvre et n'être qu'un passe plat vers le composant server-side.  
Quand bien même, il faut se contraindre à l'implémenter et ne pas faire de raccourci.  
Il faut penser à la maintenance future.  
Si un jour, on décide d'ajouter des règles de gestion. Comme un contrôle d'accès fin.  

#### userside
Contient les **points d'entrée** de l'application. En gros, ici, les controllers contenant les routes des API REST.  
Des classes dédiées sont mises en place pour les paramètres d'entrée xxxRequest et les réponses xxxResponse.  
Des mappers permettent de traduire ces paramètres d'entrée et réponses vers où depuis les objets métiers.  

On peut réceptionner les erreurs techniques et fonctionnelles provenant des autres composants server-side ou domain et les traduire en erreur appropriée à la technologie. → **ErrorHandler**  
Les codes d'erreur HTTP dans le cadre d'une API REST.  
Cela permet au passage de filtrer le contenu des erreurs pour ne pas envoyer trop d'informations à l'utilisateur.  
Information qui pourrait aider un hacker en donnant des détails techniques.  

#### serverside
Contient les **points de sortie** de l'application.
Les classes du modèle sont mappées vers des classes adaptées à la technologie.
Dans le cadre de la technologie SQL avec Hibernate et JPA, des entities avec les annotations @Table, @Entity, ....  
→ ArticleEntity (table article), ArticleInStockEntity (table stock), OrderEntity (table order), ArticleInOrderEntity (table articles_in_order).  

On aura un classe **xxxRepository** exposée au domain. Elle sera en charge du mapping et appellera une classe **xxxDAO** qui fera les actions sur la base de données.  
Dans le cadre de Spring Boot, cette classe DAO ne sera en fait qu'une interface qui héritera de JpaRepository.  

### Conclusion
On peut maintenant, implémenter des tests unitaires facilement et uniquement sur la partie métier (domain).

On ne fait pas de TU sur les bases de données, non plus sur les routes REST.
Si on veut faire des tests sur ces composants, ce seront des tests d'intégration.
On peut exclure les packages userside et serverside du calcul de la couverture de tests dans SonarQube.

On peut rapidement changer de technologie (base de donnée SQL → NoSQL, REST → GraphQL, SOAP, Kafka).  
Sans réécrire toute l'application.

domain a encore toutefois des dépendances vers le package serverside.
userside dépend directement de domain pour appeler le service.
domain dépend de serverside pour appeler le repository.
Ça contrevient au principe SOLID et à la définition de l'architecture Hexagonale.

Nous allons voir dans le prochain exercice comment résoudre ce problème grâce à l'inversion de dépendances.

## Exercice 4
### Objectif
Retirer la dépendance directe entre userside et domain par l'appel de l'implémentation de la classe xxxService.  
Et surtout entre domain et serverside.

### Travail à réaliser
Dans le package domain, créer les packages api.business et api.serverside.  
Ces packages nous permettrons d'y mettre les interfaces qui éviteront les dépendances directes vers des implémentations et surtout de permettre les inversions de dépendances.
Et donc retirer toute dépendance du domain vers l'extérieur et notamment vers serverside.

Créer les interfaces sur les classes xxxService dans le package api.business.
Créer les interfaces sur les classes xxxRepository Créer les interfaces sur les classes.  
Les classes xxxService et xxxRepository doivent implémenter ces nouvelles interfaces.
On ajoutera le suffixe Impl aux noms des classes xxxService et xxxRepository pour ne pas les confondre avec leur interface.  

## Exercice 5
### Objectif
Implémenter les tests unitaires. Ils sont relativement simple comme il n'y a pas de code métier.  

### Travail à réaliser
Implémenter dans le répertoire des tests des fake repositories.  
Les fake repositories doivent imiter le fonctionnement d'une base de données pour les fonctions CRUD à l'aide d'une HashMap.

Implémenter les TU en utilisant les fake repositories.
Ils doivent simplement vérifier les fonctionnalités CRUD et vérifier le comportement en cas d'erreur.
Pour vérifier le comportement en cas d'erreur, il faut que les fake repositories puissent se mettre en état de générer des erreurs.  
En utilisant un flag booléen **inError** avec un setter utilisé dans les cas de tests correspondant.
Si inError est à vrai, le fake repository est censé renvoyer une erreur technique.  

### Conclusion
Il est beaucoup plus facile de focaliser les TU sur les fonctions métiers et de remplacer les accès à la base de données par des fake repositories.  
Les fake repositories sont mieux que les mocks car on n'est plus obligé d'imaginer et simuler chacun des comportements. C'est implicite. Et on peut mieux vérifer les attendus.  

## Exercice Bonus
### Objectif
Créer des modules maven pour chacun des domaines fonctionnels et y déplacer le code correspondant.

### Travail à réaliser
Créer les modules maven en tant que librairies :
- article
- stock
- order

Y déplacer le code précédemment produit avec le découpage technique Hexagonale en packages.

Importer les nouveaux modules dans le module principal de l'application et déclarer les packages à injecter.

### Conclusion
Nous avons un découpage modulaire plus clair.  
Les modules peuvent alors être utilisés dans une seule application monolithique ou dans des microservices très facilement.  

## Exercice Microservices
### Objectif
Utiliser chacun des modules maven fonctionnels dans des microservices.

### Travail à réaliser
Créer des microservices en tant qu'application Spring Boot.  
Avec un nouveau module maven pour chacun.

## Exercice Super bonus
### Objectif
Séparer chacun des modules fonctionnels en modules maven techniques (domain, userside, serverside).
Retirer la dépendance à Spring sur les modules métiers (domain).

## Bibliographie
### De Robert C. Martin
- **Clean Architecture** : ce livre explique les principes vus précédemment. Mais en allant plus loin. C'est parfois un peu dur et long à lire. Mais le chapitre sur les principes SOLID est important.
- **Clean Code** : Ce livre est plus facile à lire et donne plein de petites astuces pour avoir un code plus lisible.

### **Software craft - 2e édition** :
De Cyrille Martraire, Arnaud Thiéfaine, Dorra Bartaguiz, Fabien Hiegel, Houssam Fakih.  
Ce livre détaille les principales techniques de la boîte à outils du craft. TDD, BDD, DDD, Clean Code, SOLID, pair programming, IA.
