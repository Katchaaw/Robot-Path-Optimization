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
        this.M = M;
        this.N = N;
        this.grid = new int[M][N];
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                grid[i][j] = sc.nextInt();
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
}
