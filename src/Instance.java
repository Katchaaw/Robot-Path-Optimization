import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe représentant une instance du problème :
 *  <ul>
 *      <li>Une grille de taille M*N</li>
 *      <li>Une position de départ</li>
 *      <li>Une orientation de départ</li>
 *      <li>Une position d'arrivée</li>
 *  </ul>
 *
 * <p> La grille contient des cases valides (0) ou bloquées (1).
 *     Le robot ne peut se déplacer que sur les cases valides.</p>
 */
public class Instance {
    /** Nombre de lignes (resp. de colonnes) de la grille. */
    public int M, N;

    /** Grille de l'instance : 0 = libre, 1 = obstacle. */
    public int[][] grid;

    /** Ligne de départ (resp. de départ). */
    public int D1, D2;

    /** Orientation initiale (0= Est, 1= Nord, 2= Ouest, 3= Sud). */
    public int startDir;

    /** Ligne (resp. colonne) de départ. */
    public int F1, F2;

    /**
     * Construit une instance du problème en lisant la grille et les paramètres
     * dans un Scanner.
     *
     * @param M nombre de lignes
     * @param N nombre de colonnes
     * @param sc scanner des données
     */
    public Instance(int M, int N, Scanner sc){
        this.M = M+1;
        this.N = N+1;
        this.grid = new int[M+1][N+1];
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                int tmp = sc.nextInt();
                if(tmp == 1){
                    this.grid[i][j] = 1;
                    this.grid[i][j+1] = 1;
                    this.grid[i+1][j] = 1;
                    this.grid[i+1][j+1] = 1;
                }
            }
        }
        this.D1 = sc.nextInt();
        this.D2 = sc.nextInt();
        this.F1 = sc.nextInt();
        this.F2 = sc.nextInt();
        this.startDir = parse_dir(sc.next());
    }

    /**
     * Convertit une chaîne de caractères des directions en un code (de 0 à 3).
     *
     * @param dir chaîne ("est", "nord", "ouest", "sud")
     * @return code direction (0–3) ou -1 si invalide
     */
    private int parse_dir(String dir){
        return switch (dir) {
            case "est" -> 0;
            case "nord" -> 1;
            case "ouest" -> 2;
            case "sud" -> 3;
            default -> -1;
        };
    }

    private static String parse_dir(int dir){
        return switch (dir){
            case 0 -> "est";
            case 1 -> "nord";
            case 2 -> "ouest";
            case 3 -> "sud";
            default -> "Erreur";
        };
    }

    private static void generate_file(int [][]grid, int D1, int D2, int F1, int F2, int startDir, String fileName, boolean isLast){
        try (FileWriter writer = new FileWriter(fileName, true)) {
            int M = grid.length;
            if(M == 0){
                writer.write("0 0");
                return;
            }
            int N = grid[0].length;
            if(N == 0){
                writer.write("0 0");
                return;
            }
            writer.write(String.valueOf(M) + " " + String.valueOf(N) + "\n");
            for(int i = 0; i < M; i++){
                for(int j = 0; j< N; j++){
                    writer.write(String.valueOf(grid[i][j]));
                    if(j < N-1){
                        writer.write(" ");
                    }
                    else{
                        writer.write("\n");
                    }
                }
            }
            writer.write(String.valueOf(D1) + " " + String.valueOf(D2) + " " + String.valueOf(F1) + " " +
                    String.valueOf(F2) + " " + parse_dir(startDir) + "\n");
            if(isLast){
                writer.write("0 0\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void generate_random_grid_file(int[] tab_N, int tab_M[], String fileName, int[] nbObstacle){
        File f = new File(fileName);
        if(f.exists()){
            f.delete();
        }
        int nbGrid = tab_M.length;
        for (int k = 0; k <nbGrid; k++){
            int N = tab_N[k];
            int M = tab_M[k];
            Random r = new Random();
            record Position(int x, int y) {}
            ArrayList<Position> l = new ArrayList<>();

            // génération de nbObstacle obstacles
            while(l.size() != nbObstacle[k]){
                int i = r.nextInt(M);
                int j = r.nextInt(N);
                Position p = new Position(i,j);
                if(!l.contains(p)){
                    l.add(p);
                }
            }
            // remplissage des obstacles
            int [][]grid = new int[M][N];
            for (Position p: l){
                int i = p.x;
                int j = p.y;
                grid[i][j] = 1;
            }

            // Positions départ/arrivé
            int D1 = r.nextInt(M);
            int D2 = r.nextInt(N);
            int F1 = r.nextInt(M);
            int F2 = r.nextInt(N);
            int startDir = r.nextInt(4);

            generate_file(grid,D1,D2,F1,F2,startDir,fileName, (k == nbGrid-1));
        }
    }

}
