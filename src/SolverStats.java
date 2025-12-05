import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SolverStats {
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

    public static void csv_stats_solver_obstables_10_50(){
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
