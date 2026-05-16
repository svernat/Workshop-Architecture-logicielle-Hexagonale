## Exercice Super bonus
### Objectif
Séparer chacun des modules fonctionnels en modules maven techniques (domain, userside, serverside).
Retirer la dépendance à Spring sur les modules métiers (domain).

### Travail à réaliser
Ouvrez le projet du repertoire **exercice-super-bonus**.  

Créer des modules parents pour les modules Spring et un autre pour les modules métier sans dépendances à des librairies d'accès externes (JPA, REST) ou au framework Spring.  
Séparer chacun des modules fonctionnels en sous-modules (domain, userside, serverside) et déplacer le code correspondant dans ces sous-modules.  
Supprimer les annotations Spring (comme @Service, @Component, @Repository) des classes du module **domain** pour le rendre totalement agnostique du framework.  
Mettre en place la configuration Spring dans les modules de l'application (ou via une classe de configuration dédiée) pour injecter les dépendances du domaine.  

### Conclusion
Vous avez là, un code bien découpé et modulaire avec une indépendance bien marquée des modules métiers.  
C'est peut-être un peu trop rigoriste mais ça permet d'aller jusqu'au bout de la mise en place de l'**Architecture Logicielle Hexagonale**.  

Vous trouverez la solution dans le répertoire **exercice-super-bonus-solution**.