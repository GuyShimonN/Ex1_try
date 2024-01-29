import java.util.ArrayList;

public class Position {
    private ArrayList<String> all_players_was_pos;
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.all_players_was_pos = new ArrayList<String>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<String> AllPlayersWasAtPostion() {
        return this.all_players_was_pos;
    }
}