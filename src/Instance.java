import gurobi.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 *     Le robot ne peut se déplacer que sur les cases valides.
 *     Le robot, ayant un diamètre de 1.6m, occupe en pratique un bloc de 2x2 croisements
 *     </p>
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

        // Coordonnées de départ et d'arrivée, et orientation initiale
        this.D1 = sc.nextInt();
        this.D2 = sc.nextInt();
        this.F1 = sc.nextInt();
        this.F2 = sc.nextInt();
        this.startDir = parse_dir(sc.next());
    }

    /**
     * Convertit une chaîne de caractères des directions en un code.
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

    /**
     * Convertit un code de direction entier en chaîne de caractères.
     *
     * @param dir code direction (0 = est, 1 = nord, 2 = ouest, 3 = sud)
     * @return chaîne de direction ("est", "nord", "ouest", "sud"), ou "Erreur" si le code est invalide
     */
    private static String parse_dir(int dir){
        return switch (dir){
            case 0 -> "est";
            case 1 -> "nord";
            case 2 -> "ouest";
            case 3 -> "sud";
            default -> "Erreur";
        };
    }


    /**
     * Écrit une instance dans un fichier au format demandé par l'énoncé.
     *
     * @param grid     grille de l'instance (0 = libre, 1 = obstacle)
     * @param D1       ligne de départ
     * @param D2       colonne de départ
     * @param F1       ligne d'arrivée
     * @param F2       colonne d'arrivée
     * @param startDir orientation initiale (0 = est, 1 = nord, 2 = ouest, 3 = sud)
     * @param fileName nom du fichier cible
     * @param isLast   vrai si cette instance est la dernière (on ajoute alors "0 0")
     */
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


    /**
     * Génère un fichier d'instances aléatoires au format du sujet.
     *
     * @param tab_N      tableau des nombres de colonnes pour chaque grille
     * @param tab_M      tableau des nombres de lignes pour chaque grille
     * @param fileName   nom du fichier de sortie
     * @param nbObstacle nombre d'obstacles pour chaque grille
     */
    public static void generate_random_grid_file(int[] tab_N, int[] tab_M, String fileName, int[] nbObstacle){
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

    /**
     * Génère une grille de poids aléatoires dans [0,1000] pour chaque case.
     *
     * @param M nombre de lignes
     * @param N nombre de colonnes
     * @return matrice M × N de poids entiers
     */
    private static int[][] generate_weighted_grid(int M, int N){
        int [][]grid = new int[M][N];
        Random r = new Random();
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                grid[i][j] = r.nextInt(1001);
            }
        }
        return grid;
    }


    /**
     * Génère aléatoirement une grille de taille (M, N) avec exactement P obstacles,
     * en résolvant un programme linéaire avec Gurobi pour minimiser la somme des
     * poids des cases contenant les obstacles, sous les contraintes :
     * <ul>
     *     <li>chaque ligne contient au plus 2P/M obstacles ;</li>
     *     <li>chaque colonne contient au plus 2P/N obstacles ;</li>
     *     <li>aucune ligne ni colonne ne contient le motif 101 (obstacle, libre, obstacle).</li>
     * </ul>
     *
     * @param M        nombre de lignes
     * @param N        nombre de colonnes
     * @param P        nombre d'obstacles à placer
     * @param D1       ligne de départ
     * @param D2       colonne de départ
     * @param F1       ligne d'arrivée
     * @param F2       colonne d'arrivée
     * @param startDir orientation initiale (0 = est, 1 = nord, 2 = ouest, 3 = sud)
     * @throws GRBException si une erreur survient lors de l'appel à Gurobi
     */
    public static void generate_random_grid_constrained_obs_pos(int M, int N, int P, int D1, int D2, int F1, int F2, int startDir) throws GRBException {
        int [][] weighted_grid = generate_weighted_grid(M,N);

        GRBEnv env = new GRBEnv(true);
        env.set("logFile", "grid_optimization.log");
        env.start();

        GRBModel model = new GRBModel(env);

        GRBVar [][] vars = new GRBVar[M][N];
        GRBLinExpr nbObst = new GRBLinExpr();
        GRBLinExpr obj = new GRBLinExpr();

        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                vars[i][j] = model.addVar(0.0,1.0,0.0, GRB.BINARY, "p_" + String.valueOf(i) + "_" + String.valueOf(j));
                nbObst.addTerm(1.0,vars[i][j]);
                obj.addTerm(weighted_grid[i][j],vars[i][j]);
            }
        }

        // Nombre total d'obstacles fixé à P
        model.addConstr(nbObst,GRB.EQUAL,P,"nbObstacles");

        // Minimisation de la somme des poids
        model.setObjective(obj,GRB.MINIMIZE);

        // Contraintes par ligne
        for(int i = 0; i < M; i ++){
            GRBLinExpr line_obst_lim = new GRBLinExpr();
            double []ones = new double[N];
            Arrays.fill(ones,1.0);
            line_obst_lim.addTerms(ones,vars[i]);
            model.addConstr(line_obst_lim,GRB.LESS_EQUAL, (double) (2 * P) / M, "line_" + String.valueOf(i) + "_obstLim");

            for(int j = 1; j < N-1; j++){
                GRBLinExpr one_zero_one_seq_line = new GRBLinExpr();
                double[] coeffs = new double[]{1,-1,-1};
                GRBVar[] sequence = new GRBVar[]{vars[i][j], vars[i][j-1], vars[i][j+1]};
                one_zero_one_seq_line.addTerms(coeffs,sequence);
                model.addConstr(one_zero_one_seq_line,GRB.GREATER_EQUAL,-1,"L101_" + String.valueOf(i) + "_" + String.valueOf(j));
            }
        }

        // Contraintes par colonne
        for(int j  = 0; j < N; j++){
            GRBLinExpr col_obst_lim = new GRBLinExpr();

            for(int i = 0; i < M; i++){

                if(i != 0 && i != M-1){
                    GRBLinExpr one_zero_one_seq_col = new GRBLinExpr();
                    double[] coeffs = new double[]{1,-1,-1};
                    GRBVar[] sequence = new GRBVar[]{vars[i][j], vars[i-1][j], vars[i+1][j]};
                    one_zero_one_seq_col.addTerms(coeffs,sequence);
                    model.addConstr(one_zero_one_seq_col,GRB.GREATER_EQUAL,-1,"C101_" + String.valueOf(i) + "_" + String.valueOf(j));
                }
                col_obst_lim.addTerm(1, vars[i][j]);
            }
            model.addConstr(col_obst_lim,GRB.LESS_EQUAL, (double) (2 * P) / N, "col_" + String.valueOf(j) + "_obstLim");

        }

        // Résolution
        model.optimize();

        int[][] resultGrid = new int[M][N];
        int optimStatus = model.get(GRB.IntAttr.Status);

        if (optimStatus == GRB.OPTIMAL) {
            for(int i = 0; i < M; i++){
                for(int j = 0; j < N; j++){
                    double val = vars[i][j].get(GRB.DoubleAttr.X);

                    if (val > 0.5) {
                        resultGrid[i][j] = 1;
                    } else {
                        resultGrid[i][j] = 0;
                    }
                }
            }
        } else {
            System.out.println("Aucune solution trouvée (Infeasible ou Unbounded).");
            return;
        }

        // Libération des ressources
        model.dispose();
        env.dispose();

        String fileName = "instances/constrained_obst_random";
        File f = new File(fileName);
        if(f.exists()){
            f.delete();
        }
        generate_file(resultGrid,D1,D2,F1,F2,startDir,fileName,true);
    }

}
