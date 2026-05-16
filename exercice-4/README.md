# Exercice 4
## Objectif
Retirer la dépendance directe entre userside et domain (l'appel de l'implémentation de la classe xxxService).  
Et surtout entre domain et serverside.

## Travail à réaliser
Ouvrez le projet du repertoire **exercice-4**.

Dans le package domain, créer les packages api.business et api.serverside.  
Ces packages nous permettrons d'y mettre les interfaces qui éviteront les dépendances directes vers des implémentations et surtout de permettre les inversions de dépendances.
Et donc retirer toute dépendance du domain vers l'extérieur et notamment vers serverside.

Créer les interfaces sur les classes xxxService dans le package api.business.
Créer les interfaces sur les classes xxxRepository Créer les interfaces sur les classes.  
Les classes xxxService et xxxRepository doivent implémenter ces nouvelles interfaces.
On ajoutera le suffixe Impl aux noms des classes xxxService et xxxRepository pour ne pas les confondre avec leur interface.  

Vous trouverez la solution dans le répertoire **exercice-4-solution**. 