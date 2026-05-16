# Exercice 1
## Objectif
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

## Travail à réaliser 
Récupérer le code depuis le repository [**Workshop-Architecture-logicielle-Hexagonale**](https://github.com/svernat/Workshop-Architecture-logicielle-Hexagonale).  
Et ouvrez le projet du repertoire **exercice-1**.  

En tant que **développeur débutant**, n'ayant pas de notion de structuration de code et de Clean Code.  
Vous devez générer le **CRUD** et les **routes REST** avec **Spring Boot** pour chacun des objets du modèle.
CRUD : Create, Read (Get et GetAll), Update, Delete  

Le but ici est d'avoir le code le plus simple dans un seul package en une ou plusieurs classes.  
Une seule classe ici est très bien et permettra encore mieux de voir, par la suite, l'intérêt de structurer son code.  
Après, si vous séparez les classes par objet et les routes par type d'objet, ça va aussi.  

Les **ID** seront des String générés par **uuid**.  

## Astuce
Aidez-vous d'un assistant de code comme Junie dans IntelliJ ou Github Copilot, Gemini, Claude Code.  
IntelliJ permet d'ajouter des plugins pour ces assistants.  
Cela nous permettra de discuter de l'intérêt de ces assistants et d'aborder comment les utiliser au mieux.  

## Conclusion
Le code n'est pas forcément très lisible surtout quand le nombre de types d'objet se multiplient.  

Il y a d'autres inconvénients comme la difficulté d'implémenter les tests unitaires, d'apporter des changements techniques pour remplacer une base de données SQL par une base de données NoSQL, ....    

Essayez de voir comment implémenter les TU. Vous verrez que c'est impossible, car vous devrez instancier une base de données. Du coup, ce ne sont plus des TU.  

Vous trouverez la solution dans le répertoire **exercice-1-solution**.  
