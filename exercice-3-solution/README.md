# Exercice 3
## Objectif
Pour chacun des domaines fonctionnels, appliquer un découpage technique sur le principe de l'architecture logicielle **Hexagonale**.  
Le métier est le plus important. Quand on développe une application, on devrait commencer par cette partie.  
On peut ensuite poursuivre sur les liaisons avec l'extérieur que sont les composants techniques.  
Les points d'entrées comme l'API REST qui réceptionne les requêtes de l'utilisateur. Mais aussi, les évennements depuis un broker de messages Kafka.  
Les points de sorties comme la base de données. Mais, aussi les appels à d'autres services, à des LLM, ....  

Les points d'entrées et de sorties ne sont que des **"détails"**. Dixit Bob (Robert C. Martin).

## Travail à réaliser
Ouvrez le projet du repertoire **exercice-3**.

Découper chaque domaine fonctionnel en trois packages :
- domain
- userside
- serverside

### domain
Contient le **métier**, le modèle de données, les règles de gestion, les calculs, les algorithmes, les filtres qui n'auront pas pu être mis dans les requêtes SQL.  

Il n'est censé n'avoir **aucune adhérence à des contraintes techniques** comme Hibernate / JPA.  
Le modèle de données ne doit donc plus avoir d'annotation @Entity et @JsonProperty.  
On peut exceptionnellement accepter l'anottation @Service sur la classe contenant les règles de gestion.  

Dans le cadre d'un CRUD, le code de la classe de service peut être très pauvre et n'être qu'un passe plat vers le composant server-side.  
Quand bien même, il faut se contraindre à l'implémenter et ne pas faire de raccourci.  
Il faut penser à la maintenance future.  
Si un jour, on décide d'ajouter des règles de gestion. Comme un contrôle d'accès fin.  

### userside
Contient les **points d'entrée** de l'application. En gros, ici, les controllers contenant les routes des API REST.  
Des classes dédiées sont mises en place pour les paramètres d'entrée xxxRequest et les réponses xxxResponse.  
Des mappers permettent de traduire ces paramètres d'entrée et réponses vers où depuis les objets métiers.  

On peut réceptionner les erreurs techniques et fonctionnelles provenant des autres composants server-side ou domain et les traduire en erreur appropriée à la technologie. → **ErrorHandler**  
Les codes d'erreur HTTP dans le cadre d'une API REST.  
Cela permet au passage de filtrer le contenu des erreurs pour ne pas envoyer trop d'informations à l'utilisateur.  
Information qui pourrait aider un hacker en donnant des détails techniques.  

### serverside
Contient les **points de sortie** de l'application.
Les classes du modèle sont mappées vers des classes adaptées à la technologie.
Dans le cadre de la technologie SQL avec Hibernate et JPA, des entities avec les annotations @Table, @Entity, ....  
→ ArticleEntity (table article), ArticleInStockEntity (table stock), OrderEntity (table order), ArticleInOrderEntity (table articles_in_order).  

On aura un classe **xxxRepository** exposée au domain. Elle sera en charge du mapping et appellera une classe **xxxDAO** qui fera les actions sur la base de données.  
Dans le cadre de Spring Boot, cette classe DAO ne sera en fait qu'une interface qui héritera de JpaRepository.  

## Conclusion
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

Vous trouverez la solution dans le répertoire **exercice-3-solution**.  