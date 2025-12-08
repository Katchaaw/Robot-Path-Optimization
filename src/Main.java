import gurobi.*;

import java.util.Scanner;

/**
 * Point d’entrée du programme.
 *
 * <p>Charge une ou plusieurs instances depuis un fichier texte, puis crée
 * un objet {@link Instance} pour chacune.</p>
 *
 * <p>
 * Ce main propose également (en commentaires) l'exécution de tests de performance :
 * {@link SolverStats#csv_stats_solver_10_50()} et
 * {@link SolverStats#csv_stats_solver_obstacles_10_50()}.
 * </p>
 */
public class Main {

    /**
     * Lit un fichier d'instances dont le chemin peut être passé en argument.
     * Chaque instance commence par N et M, et une paire (0 0) indique la fin.
     *
     * @param args : chemin du fichier d'instances (optionnel)
     */
    public static void main(String[] args){
        //SolverStats.csv_stats_solver_10_50();
        //SolverStats.csv_stats_solver_obstacles_10_50();
        Scanner sc = new Scanner(System.in);
        int M = 0;
        int N = 0;
        int P = -1;
        int D1 = -1;
        int D2 = -1;
        int F1 = -1;
        int F2 = -1;
        int startDir = -1;

        while(M <= 0){
            System.out.println("Veuillez choisir le nombre de lignes strictement positif");
            M = sc.nextInt();
        }

        while(N <= 0){
            System.out.println("Veuillez choisir le nombre de colonnes strictement positif");
            N = sc.nextInt();
        }

        while(P < 0){
            System.out.println("Veuillez choisir le nombre d'obstacles positif");
            P = sc.nextInt();
        }

        while(D1 < 0){
            System.out.println("Veuillez choisir la position (ligne) de départ");
            D1 = sc.nextInt();
        }

        while(D2 < 0){
            System.out.println("Veuillez choisir la position (colonne) de départ");
            D2 = sc.nextInt();
        }

        while(F1 < 0){
            System.out.println("Veuillez choisir la position (ligne) d'arrivée");
            F1 = sc.nextInt();
        }

        while(F2 < 0){
            System.out.println("Veuillez choisir la position (colonne) d'arrivée");
            F2 = sc.nextInt();
        }

        while(startDir < 0){
            System.out.println("Veuillez choisir l'orientation de départ (0: EST, 1: NORD, 2: OUEST, 3: SUD)'");
            startDir = sc.nextInt();
        }

        try{
            Instance.generate_random_grid_constrained_obs_pos(M,N,P,D1,D2,F1,F2,startDir);

            int[] M_tab = new int[]{M};
            int[] N_tab = new int[]{N};
            int[] nbObstacles = new int []{P};

            Solver.solve(M_tab,N_tab,"instances/constrained_obst_random","instances/res_constrained_obst_random",false,nbObstacles);
        }
        catch (GRBException e) {
            throw new RuntimeException(e);
        }

    }

}
