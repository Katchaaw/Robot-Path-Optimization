import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Point d’entrée du programme.
 *
 * <p>Charge une ou plusieurs instances depuis un fichier texte, puis crée
 * un objet {@link Instance} pour chacune.</p>
 */
public class Main {

    /**
     * Lit un fichier d'instances dont le chemin peut être passé en argument.
     * Chaque instance commence par N et M, et une paire (0 0) indique la fin.
     *
     * @param args : chemin du fichier d'instances (optionnel)
     */
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
                //Solver.solve(i);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
