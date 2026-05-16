# Exercice 5
## Objectif
Implémenter les tests unitaires. Ils sont relativement simples comme il n'y a pas de code métier.  

## Travail à réaliser
Ouvrez le projet du repertoire **exercice-5**.

Implémenter dans le répertoire des tests des fake repositories.  
Les fake repositories doivent imiter le fonctionnement d'une base de données pour les fonctions CRUD à l'aide d'une HashMap.

Implémenter les TU en utilisant les fake repositories.
Ils doivent simplement vérifier les fonctionnalités CRUD et vérifier le comportement en cas d'erreur.
Pour vérifier le comportement en cas d'erreur, il faut que les fake repositories puissent se mettre en état de générer des erreurs.  
En utilisant un flag booléen **inError** avec un setter utilisé dans les cas de tests correspondant.
Si inError est à vrai, le fake repository est censé renvoyer une erreur technique.  

## Conclusion
Il est beaucoup plus facile de focaliser les TU sur les fonctions métiers et de remplacer les accès à la base de données par des fake repositories.  
Les fake repositories sont mieux que les mocks car on n'est plus obligé d'imaginer et simuler chacun des comportements. C'est implicite. Et on peut mieux vérifer les attendus.  

Vous trouverez la solution dans le répertoire **exercice-5-solution**.