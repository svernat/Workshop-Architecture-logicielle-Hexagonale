# Exercice 2
## Objectif
Commencer à organiser le code sur le principe du **DDD** (Domain Driven Development)  

## Travail à réaliser
Ouvrez le projet du repertoire **exercice-2**.

Créer les packages et répartir les routes et CRUD pour chacun des objets du modèle de données :
- package article
  - article
- package stock
  - stock
- package order
  - order
  - articles_in_order

Séparer order et articles_in_order dans des classes différentes.  

Attention, order est un mot réservé en SQL. Ne pas l'utiliser en tant que nom de table ou de colonne.  

## Conclusion
Le code est déjà plus lisible et les modifications seront plus localisées.  
Mais techniquement, l'implémentation des tests unitaires n'a pas été facilité et les changements de technologies seront toujours aussi compliqués.  
Nous n'avons fait ici qu'un découpage fonctionnel.  

Vous trouverez la solution dans le répertoire **exercice-2-solution**.  
