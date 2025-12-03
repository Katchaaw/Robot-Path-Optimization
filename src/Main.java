import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String file = "instances/instance1.txt";

        if (args.length > 0) {
            file = args[0];
        }
        try{
            Scanner sc = new Scanner(new File(file));
            while (true){
                int N = sc.nextInt();
                int M = sc.nextInt();
                if(N == 0 || M == 0){
                    break;
                }
                Instance i = new Instance(M,N,sc);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
