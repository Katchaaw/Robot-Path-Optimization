# GR4_ALVES_VIEIRA_LU
Modélisation d'une "balade" d'un robot dans un grand magasin pour le transport d'objets.

## 📦 Prérequis

- Java 17+
- Gurob

## Compilation

Depuis la racine du projet :

```java
javac -cp ".:gurobi.jar" src/*.java
```

Cela compile l’ensemble des fichiers Java en prenant en compte Gurobi.

## 🚀 Exécution

Toujours depuis la racine :
```java
java -cp "src:.:gurobi.jar" Main
```

Le programme vous demandera ensuite :

- le nombre de lignes M
- le nombre de colonnes N
- le nombre d’obstacles P
- la position de départ (D1, D2)
- l’orientation initiale (0=EST, 1=NORD, 2=OUEST, 3=SUD)
- la position d’arrivée (F1, F2)

Il générera alors une grille satisfaisant les contraintes, puis calculera et affichera le plus court chemin.
