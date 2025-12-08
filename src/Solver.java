import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Contient l'algorithme de résolution du problème.
 *
 * <p>On utilisera une BFS sur l'état (r, c, dir), car chaque action
 * (avancer, tourner gauche, tourner droite) coûte 1.</p>
 *
 */
public class Solver {

    /**
     * Résultat d’une résolution :
     * <ul>
     *     <li>time : longueur minimale du chemin trouvé</li>
     *     <li>actions : séquence optimale des actions</li>
     * </ul>
     */
    public static class Result {
        /** Nombre minimal d'actions (distance BFS). */
        public int time;
        /** Liste des actions composant le chemin optimal. */
        public LinkedList<String> actions;

        public Result(int t, LinkedList<String> a) {
            this.time = t;
            this.actions = a;
        }
    }

    /** Décalages ligne selon la direction (0 = Est, 1 = Nord, 2 = Ouest, 3 = Sud). */
    private static final int[] dr = {0, -1, 0, 1};

    /** Décalages colonne selon la direction (0 = Est, 1 = Nord, 2 = Ouest, 3 = Sud). */
    private static final int[] dc = {1, 0, -1, 0};


    /**
     * Vérifie si le robot 2×2 peut être placé à l'intersection (i,j).
     *
     * @param inst instance contenant la grille
     * @param i    ligne de l'intersection
     * @param j    colonne de l'intersection
     * @return vrai si le robot peut occuper ce nœud
     */
    private static boolean isValid(Instance inst, int i, int j) {
        int M = inst.M;
        int N = inst.N;
        return !(i <= 0 || i >= M-1 || j <= 0 || j >= N-1 || inst.grid[i][j] == 1);

    }

    /**
     * Résout une instance en utilisant BFS.
     *
     * @param inst instance du problème
     * @return un {@link Result} contenant la distance minimale et la liste des actions ;
     *         ou (−1, null) si l’arrivée n’est pas atteignable
     */
    public static Result solve(Instance inst) {
        int M = inst.M;
        int N = inst.N;

        // Coordonnées de départ et d'arrivée
        int start_i = inst.D1;
        int start_j = inst.D2;
        int final_i = inst.F1;
        int final_j = inst.F2;
        int start_d = inst.startDir;


        // Vérifie que départ et arrivée sont accessibles
        if (!isValid(inst, start_i, start_j) || !isValid(inst, final_i, final_j))
            return new Result(-1, null);

        // visited[r][c][dir] pour éviter les re-visites
        boolean[][][] visited = new boolean[M][N][4];
        Queue<State> q = new LinkedList<>();

        State start = new State(start_i, start_j, start_d, 0, null, null);
        q.add(start);
        visited[start_i][start_j][start_d] = true;

        State goalState = null;

        // BFS
        while(!q.isEmpty()) {
            State curr = q.poll();

            // Objectif atteint : BFS ⇒ optimal
            if(curr.r == final_i && curr.c == final_j) {
                goalState = curr;
                break;
            }

            // Sens antihoraire (+3 = droite, +1 = gauche)
            int dRight = (curr.dir + 3) % 4; // Tourner à droite
            int dLeft  = (curr.dir + 1) % 4; // Tourner à gauche

            // Tourne à droite ("D")
            if (!visited[curr.r][curr.c][dRight]){
                visited[curr.r][curr.c][dRight] = true;
                q.add(new State(curr.r, curr.c, dRight, curr.time + 1, curr, "D"));
           }

            // Tourne à gauche ("G")
            if (!visited[curr.r][curr.c][dLeft]){
                visited[curr.r][curr.c][dLeft] = true;
                q.add(new State(curr.r, curr.c, dLeft, curr.time + 1, curr, "G"));
            }

            // Avance de 1 à 3 cases
            for(int n = 1; n <= 3; n++){
                int ni = curr.r;
                int nj = curr.c;
                boolean ok = true;

                // Vérifie toutes les positions intermédiaires
                for (int k = 1; k <= n; k++) {
                    ni += dr[curr.dir];
                    nj += dc[curr.dir];

                    if (!isValid(inst, ni, nj)) {
                        ok = false;
                        break;
                    }
                }

                if(ok && !visited[ni][nj][curr.dir]) {
                    visited[ni][nj][curr.dir] = true;
                    q.add(new State(ni, nj, curr.dir, curr.time + 1, curr, "a" + n));
                }
                else break;
            }
        }


        if (goalState == null) return new Result(-1, null);

        // Reconstruit le chemin optimal
        LinkedList<String> actions = new LinkedList<>();
        State curr = goalState;

        while(curr.parent != null) {
            actions.addFirst(curr.action);
            curr = curr.parent;
        }

        return new Result(goalState.time, actions);
    }

    /**
     * Résout une série d’instances stockées dans un fichier.
     *
     * @param M_tab      tailles M des grilles
     * @param N_tab      tailles N des grilles
     * @param file       fichier contenant les instances
     * @param res_file   fichier où écrire les résultats
     * @param random     si vrai → génère d’abord un fichier d’instances aléatoires
     * @param nbObstacle nombre d’obstacles par instance (si random = true)
     * @return temps moyen de résolution (ms) par instance
     */
    public static double solve(int []M_tab,int []N_tab, String file, String res_file, boolean random, int[] nbObstacle){
        if(random){
            Instance.generate_random_grid_file(M_tab,N_tab,file, nbObstacle);
        }

        try{
            Scanner sc = new Scanner(new File(file));
            FileWriter f = new FileWriter(res_file);
            double sum_ms = 0;

            while (true){
                int M = sc.nextInt();
                int N = sc.nextInt();
                if(N == 0 || M == 0) {
                    break;
                }


                Instance i = new Instance(M,N,sc);

                long start = System.nanoTime();
                Solver.Result res = Solver.solve(i);
                long end = System.nanoTime();

                sum_ms += (end - start)/1000000.0;

                if(res.time == -1) {
                    f.write("-1\n");
                }

                else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(res.time);
                    for(String a : res.actions) {
                        sb.append(" ").append(a);
                    }
                    f.write(sb.toString() + "\n");
                }
            }
            f.close();
            sc.close();

            return sum_ms/M_tab.length;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
