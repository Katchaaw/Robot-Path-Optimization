import java.util.LinkedList;
import java.util.Queue;

/**
 * Contient l'algorithme de résolution du problème.
 *
 * <p>On utilisera une BFS sur l'état (r, c, dir), car chaque action
 * (avancer, tourner gauche, tourner droite) coûte 1.</p>
 *
 */
public class Solver {

    public static class Result {
        public int time;
        public LinkedList<String> actions;
        public Result(int t, LinkedList<String> a) {
            this.time = t;
            this.actions = a;
        }
    }

    // directions : 0 = est, 1 = nord, 2 = ouest, 3 = sud
    private static final int[] dr = {0, -1, 0, 1};
    private static final int[] dc = {1, 0, -1, 0};


    // Vérifie si le robot 2x2 peut se placer à l'intersection (i,j)
    private static boolean isValid(Instance inst, int i, int j) {
        int M = inst.M;
        int N = inst.N;
        if (i < 0 || i >= M-1 || j < 0 || j >= N-1) return false;
        return inst.grid[i][j] == 0 && inst.grid[i+1][j] == 0 &&
                inst.grid[i][j+1] == 0 && inst.grid[i+1][j+1] == 0;
    }

    /**
     * Résout une instance avec BFS et affiche le chemin optimal.
     *
     * @param inst instance du problème
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


        /*On calculait toutes les positions valides . Es-ce utile ? parfois on ne les utilise pas,
        mieux de faire dynamiquement sachant qu'on ne revient jamais sur une intersection, donc pas de check en double

        // valid[i][j] : true si l'intersection (i,j) n'est pas bloquée.
        boolean[][] valid = new boolean[M-1][N-1];


        // Une intersection (i = ligne, j = colonne) est valide si les 4 cases
        // (i-1,j-1),(i-1,j),(i,j-1),(i,j) existent et sont libres (==0)
        // On à des +1 dans l'implémentation et non des -1 sinon on vérifierait des indices négatifs (OOB !)
        // D'où les M-2 et N-2 (M-1 et N-1 seraient OOB)
        for(int i = 0; i <= M-2; i++){
            for(int j = 0; j <= N-2; j++){
                valid[i][j] = inst.grid[i][j] == 0 && inst.grid[i+1][j] == 0 &&
                        inst.grid[i][j+1] == 0 && inst.grid[i+1][j+1] == 0;
            }
        }
         */


        // Vérifie que départ et arrivée sont valides
        if (!isValid(inst, start_i, start_j) || !isValid(inst, final_i, final_j))
            return new Result(-1, null);

        boolean[][][] visited = new boolean[M-1][N-1][4];
        Queue<State> q = new LinkedList<>();
        State start = new State(start_i, start_j, start_d, 0, null, null);
        q.add(start);
        visited[start_i][start_j][start_d] = true;

        State goalState = null;

        while(!q.isEmpty()) {
            State curr = q.poll();

            // Print de l'état courant
            System.out.printf("Exploration: (%d,%d) dir=%d time=%d\n", curr.r, curr.c, curr.dir, curr.time);


            // Si on atteint la position finale : on est optimal grâce au BFS
            if(curr.r == final_i && curr.c == final_j) {
                goalState = curr;
                System.out.println("Goal atteint!");
                break;
            }

            // Sens horaire (+1 = droite, +3 = gauche)
            int dRight = (curr.dir + 1) % 4;
            int dLeft  = (curr.dir + 3) % 4;

            // Tourne à droite (D)
            if (!visited[curr.r][curr.c][dRight]){
                visited[curr.r][curr.c][dRight] = true;
                q.add(new State(curr.r, curr.c, dRight, curr.time + 1, curr, "D"));
                System.out.printf("  Turn D -> dir=%d\n", dRight);
           }

            // Tourne à gauche (G)
            if (!visited[curr.r][curr.c][dLeft]){
                visited[curr.r][curr.c][dLeft] = true;
                q.add(new State(curr.r, curr.c, dLeft, curr.time + 1, curr, "G"));
                System.out.printf("  Turn G -> dir=%d\n", dLeft);
            }

            // Avance n (n = 1..3) : pour chaque n, on vérifie que chaque position intermédiaire est valide
            for(int n = 1; n <= 3; n++){
                int ni = curr.r;
                int nj = curr.c;
                boolean ok = true;

                // Vérifie chaque étape intermédiaire
                for (int k = 1; k <= n; k++) {
                    ni += dr[curr.dir];
                    nj += dc[curr.dir];

                    System.out.printf("    Check step %d à (%d,%d)\n", k, ni, nj);
                    if (!isValid(inst, ni, nj)) {
                        ok = false;
                        System.out.printf("      Bloqué en (%d,%d)\n", ni, nj);
                        break;
                    }
                }

                if(ok && !visited[ni][nj][curr.dir]) {
                    visited[ni][nj][curr.dir] = true;
                    q.add(new State(ni, nj, curr.dir, curr.time + 1, curr, "a" + n));
                    System.out.printf("    Move a%d en (%d,%d)\n", n, ni, nj);
                }
            }
        }


        if (goalState == null) return new Result(-1, null);

        LinkedList<String> actions = new LinkedList<>();
        State curr = goalState;
        while(curr.parent != null) {
            actions.addFirst(curr.action);
            curr = curr.parent;
        }

        return new Result(goalState.time, actions);
    }

}
