import java.util.Scanner;

public class Instance {
    public int M, N;
    public int[][] grid;
    public int D1, D2, startDir;
    public int F1, F2;


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
