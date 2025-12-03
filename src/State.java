/**
 * Représente un état dans la recherche BFS :
 * <ul>
 *     <li>(r, c) : position dans la grille</li>
 *     <li>dir : orientation actuelle</li>
 *     <li>time : distance cumulée depuis le départ</li>
 *     <li>parent : état précédent (pour reconstruire le chemin)</li>
 *     <li>action : nom de l'action effectuée depuis le parent</li>
 * </ul>
 */
public class State {
    /** Ligne (resp. colonne) actuelle. */
    public int r,c;

    /** Direction actuelle (0= Est, 1= Nord, 2= Ouest, 3= Sud). */
    public int dir;

    // Pour le BFS vu que toutes nos arêtes sont de poids 1.
    /** Temps ou distance (nombre d'actions) depuis le départ. */
    public int time;

    /** État parent dans le BFS, pour reconstruire le chemin. */
    State parent;

    /** Action effectuée pour venir du parent à cet état. */
    String action;


    /**
     * Construit un état complet pour le BFS.
     *
     * @param r ligne
     * @param c colonne
     * @param dir orientation
     * @param time nombre d'actions depuis le départ
     * @param parent état précédent
     * @param action action effectuée depuis le parent
     */
    public State(int r, int c, int dir, int time, State parent, String action){
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.time = time;
        this.parent = parent;
        this.action = action;
    }
}
