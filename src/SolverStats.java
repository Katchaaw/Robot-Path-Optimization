import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Classe utilitaire permettant de mesurer les performances du solver
 * sur différentes tailles de grilles et différents nombres d'obstacles.
 *
 * <p>
 * Deux types de tests sont proposés :
 * <ul>
 *     <li>Variation de la taille N de la grille carrée (N × N)</li>
 *     <li>Variation du nombre d'obstacles pour une taille de grille fixée</li>
 * </ul>
 * </p>
 *
 * <p>
 * Les résultats sont sauvegardés dans des fichiers CSV,
 * utilisables pour générer des graphiques de performance.
 * </p>
 */
public class SolverStats {

    /**
     * Mesure le temps moyen d'exécution du solver pour une grille carrée
     * de taille N × N.
     *
     *
     * @param N taille de la grille (N × N)
     * @return temps moyen (en millisecondes) de résolution
     */
    private static double stats_solver_N_size(int N){
        String file = "instances/instance" + String.valueOf(N) +".txt";
        String res_file = "instances/resultats_instances" + String.valueOf(N) + ".txt";

        int[] M_tab = new int[10];
        int[] N_tab = new int[10];
        int[] nb_Obstacle = new int[10];

        Arrays.fill(M_tab,N);
        Arrays.fill(N_tab,N);
        Arrays.fill(nb_Obstacle,N);

        return Solver.solve(M_tab,N_tab,file,res_file,true, nb_Obstacle);
    }

    /**
     * Mesure le temps moyen du solver pour un nombre donné d'obstacles N,
     * sur une grille fixe de taille 20 × 20.
     *
     * <p>
     * 10 instances sont générées, contenant chacune N obstacles.
     * </p>
     *
     * @param N nombre d'obstacles à placer
     * @return temps moyen de résolution (ms)
     */
    private static double stats_solver_N_obstacles(int N){
        String file = "instances/instance_obstacles" + String.valueOf(N) +".txt";
        String res_file = "instances/resultats_instances_obstacles" + String.valueOf(N) + ".txt";

        int[] M_tab = new int[10];
        int[] N_tab = new int[10];
        int[] nb_Obstacle = new int[10];

        Arrays.fill(M_tab,20);
        Arrays.fill(N_tab,20);
        Arrays.fill(nb_Obstacle,N);

        return Solver.solve(M_tab,N_tab,file,res_file,true, nb_Obstacle);
    }

    /**
     * Génère un fichier CSV contenant les performances du solver
     * pour des grilles carrées de tailles 10, 20, 30, 40, 50.
     */
    public static void csv_stats_solver_10_50() {
        try (FileWriter writer = new FileWriter("Performance_tests/csv_files/stats_10-50_obstacles.csv")) {
            writer.write("N duration(ms)\n");

            for(int i = 1; i < 6; i++){
                double time = stats_solver_N_size(i*10);
                writer.write(String.valueOf(i*10) + " " + time + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Génère un fichier CSV contenant les performances du solver
     * pour différents nombres d'obstacles (10, 20, 30, 40, 50),
     * sur des grilles fixes 20×20.
     */
    public static void csv_stats_solver_obstacles_10_50(){
        try (FileWriter writer = new FileWriter("Performance_tests/csv_files/stats_10-50.csv")) {

            writer.write("N duration(ms)\n");

            for(int i = 1; i < 6; i++){
                double time = stats_solver_N_obstacles(i*10);
                writer.write(String.valueOf(i*10) + " " + time + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
