# GR4_ALVES_VIEIRA_LU
Modeling a robot's "walk" through a department store for object transportation.

## 📦 Requirements

- Java 17+
- Gurobi

## Compilation

From the project root:

```java
javac -cp ".:gurobi.jar" src/*.java
```

This compiles all Java files while taking Gurobi into account.

## 🚀 Execution

Still from the project root:
```java
java -cp "src:.:gurobi.jar" Main
```

The program will then ask you for:

- the number of rows M
- the number of columns N
- the number of obstacles P
- the starting position (D1, D2)
- the initial orientation (0=EAST, 1=NORTH, 2=WEST, 3=SOUTH)
- the destination position (F1, F2)

It will then generate a grid satisfying the constraints, and compute and display the shortest path.
