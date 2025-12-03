public class State {
    public int r,c,dir;

    // Pour le BFS vu que tous nos arêtes sont de poids 1
    public int time;
    State parent;
    String action;

    public State(int r, int c, int dir, int time, State parent, String action){
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.time = time;
        this.parent = parent;
        this.action = action;
    }
}
