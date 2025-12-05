import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        SolverStats.csv_stats_solver_10_50();
        SolverStats.csv_stats_solver_obstables_10_50();



    }

}
