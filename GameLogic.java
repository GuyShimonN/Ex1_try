import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class GameLogic implements PlayableLogic {
    private ArrayList<ConcretePiece> arry_sort = new ArrayList<ConcretePiece>();
    private ArrayList<Position> undo = new ArrayList<Position>();
    private ArrayList<Position> array_sort_postion = new ArrayList<Position>();
    private ConcretePlayer atck;
    private ConcretePlayer def;
    private Position live_king = new Position(5, 5);
    private Piece[][] Board = new Piece[11][11];
    private boolean undo_lest_move = true;
    private boolean atck_turn = true;
    private boolean killKing = false;
    private boolean atck_win;
    private boolean enteruonce= false;

    public GameLogic() {
        create_players();
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        if (atck_turn) {
            if (from==null){
                return false; //need to check why
            }
            if (from.getOwner() != this.atck) {
                return false;
            } else {
                boolean ans = logic_move(a, b);
                if (ans) {
                    if (undo_lest_move) {
                        undo.add(a);
                        undo.add(b);
                    }
                    atck_turn = false;
                    int size =0;
                    boolean Postion_no_jumped = true;
                    for (int i=0;i<array_sort_postion.size();i++)
                    {
                        if(array_sort_postion.get(i).getX()==b.getX()&&array_sort_postion.get(i).getY()==b.getY())
                        {
                            Postion_no_jumped=false;
                            size=i;
                        }
                    }
                    if (Postion_no_jumped)
                    {

                        array_sort_postion.add(b);
                        int length=array_sort_postion.size()-1;
                        array_sort_postion.get(length).AllPlayersWasAtPostion().add(((ConcretePiece) this.getPieceAtPosition(b)).getName());
                    }
                    boolean pawn_at_postion=false;
                    if (!Postion_no_jumped) {

                        for (int j=0;j<array_sort_postion.get(size).AllPlayersWasAtPostion().size();j++) {
                            if (array_sort_postion.get(size).AllPlayersWasAtPostion().get(j).equals(((ConcretePiece) this.getPieceAtPosition(b)).getName())) {
                                pawn_at_postion = true;
                            }
                        }

                    }
                    if(!pawn_at_postion&&Postion_no_jumped==false)
                    {

                        array_sort_postion.get(size).AllPlayersWasAtPostion().add(((ConcretePiece) this.getPieceAtPosition(b)).getName());
                    }

                }
                if (this.getPieceAtPosition(b) != null) {
                    if (this.getPieceAtPosition(b).getType().equals("♙")) {
                        kill(b, atck);
                        killKing = kill_King(b, live_king, def);
                        if (killKing){
                            ans=true;
                            printQ1(true);
                            print_q2();
                            print_q3();
                            print_q4();
                        }
                    }

                    //   kill2(b,atck);
//                    if (isGameFinished()){
//
//                    }
                    return ans;

                }
            }
        }
        else {
            if (from==null){
                return false; //need to check why
            }
            if (from.getOwner() != this.def) {
                return false;
            } else {
                boolean ans = logic_move(a, b);
                if (ans) {

                    if (undo_lest_move) {
                        undo.add(a);
                        undo.add(b);
                    }
                    atck_turn = true;

                    int size =0;
                    boolean Postion_no_jumped = true;
                    for (int i=0;i<array_sort_postion.size();i++)
                    {
                        if(array_sort_postion.get(i).getX()==b.getX()&&array_sort_postion.get(i).getY()==b.getY())
                        {
                            Postion_no_jumped=false;
                            size=i;
                        }
                    }
                    if (Postion_no_jumped)
                    {

                        array_sort_postion.add(b);
                        int length=array_sort_postion.size()-1;
                        array_sort_postion.get(length).AllPlayersWasAtPostion().add(((ConcretePiece) this.getPieceAtPosition(b)).getName());
                    }
                    boolean pawn_at_postion=false;
                    if (!Postion_no_jumped) {

                        for (int j=0;j<array_sort_postion.get(size).AllPlayersWasAtPostion().size();j++) {
                            if (array_sort_postion.get(size).AllPlayersWasAtPostion().get(j).equals(((ConcretePiece) this.getPieceAtPosition(b)).getName())) {
                                pawn_at_postion = true;
                            }
                        }

                    }
                    if(!pawn_at_postion&&Postion_no_jumped==false)
                    {

                        array_sort_postion.get(size).AllPlayersWasAtPostion().add(((ConcretePiece) this.getPieceAtPosition(b)).getName());
                    }
                }
                if (this.getPieceAtPosition(b) != null) {
                    if (this.getPieceAtPosition(b).getType().equals("♙")) {
                        kill(b, def);
                    }
                    if (isGameFinished()){
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                    return ans;

                }
            }
        }
        return false; //if we have a problame is here .
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return Board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return atck;
    }

    @Override
    public Player getSecondPlayer() {
        return def;
    }

    @Override
    public boolean isGameFinished() {
        if ((this.Board[0][0] != null || this.Board[0][10] != null || this.Board[10][0] != null || this.Board[10][10] != null)&&enteruonce==false) {
            def.inc_wins();
            atck_win=false;
             printQ1(false);
             print_q2();
            print_q3();
            print_q4();
            enteruonce=true;
            undo.clear();
            return true;
        }
       else if (killKing&&(enteruonce==false)) {
            atck.inc_wins();
            atck_win=true;
            enteruonce=true;
            undo.clear();
            return true;
        }
        if (enteruonce){
            return true;
        }
        return false;

    }



    @Override
    public boolean isSecondPlayerTurn() {
        return atck_turn;
    }

    @Override
    public void reset() {
        array_sort_postion.clear();
        enteruonce=false;
        atck_turn = true;
        Board = new Piece[11][11];
        for (int i = 3; i < 8; i++) {
            this.Board[i][0] = new Pawn((this.atck), "A" + (i - 2));
            arry_sort.add((ConcretePiece) this.Board[i][0]);
            this.Board[i][10] = new Pawn((this.atck), "A" + (i + 17));
            arry_sort.add((ConcretePiece) this.Board[i][10]);
        }
        Board[5][1] = new Pawn((this.atck), "A6");
        arry_sort.add((ConcretePiece) this.Board[5][1]);
        this.Board[0][3] = new Pawn((this.atck), "A7");
        arry_sort.add((ConcretePiece) this.Board[0][3]);
        this.Board[10][3] = new Pawn((this.atck), "A8");
        arry_sort.add((ConcretePiece) this.Board[10][3]);
        this.Board[0][4] = new Pawn((this.atck), "A9");
        arry_sort.add((ConcretePiece) this.Board[0][4]);
        this.Board[10][4] = new Pawn((this.atck), "A10");
        arry_sort.add((ConcretePiece) this.Board[10][4]);
        this.Board[0][5] = new Pawn((this.atck), "A11");
        arry_sort.add((ConcretePiece) this.Board[0][5]);
        this.Board[1][5] = new Pawn((this.atck), "A12");
        arry_sort.add((ConcretePiece) this.Board[1][5]);
        this.Board[9][5] = new Pawn((this.atck), "A13");
        arry_sort.add((ConcretePiece) this.Board[9][5]);
        this.Board[10][5] = new Pawn((this.atck), "A14");
        arry_sort.add((ConcretePiece) this.Board[10][5]);
        this.Board[0][6] = new Pawn((this.atck), "A15");
        arry_sort.add((ConcretePiece) this.Board[0][6]);
        this.Board[10][6] = new Pawn((this.atck), "A16");
        arry_sort.add((ConcretePiece) this.Board[10][6]);
        this.Board[0][7] = new Pawn((this.atck), "A17");
        arry_sort.add((ConcretePiece) this.Board[0][7]);
        this.Board[10][7] = new Pawn((this.atck), "A18");
        arry_sort.add((ConcretePiece) this.Board[10][7]);
        this.Board[5][9] = new Pawn((this.atck), "A19");
        arry_sort.add((ConcretePiece) this.Board[5][9]);
        this.Board[5][3] = new Pawn((this.def), "D1");
        arry_sort.add((ConcretePiece) this.Board[5][3]);
        this.Board[4][4] = new Pawn((this.def), "D2");
        arry_sort.add((ConcretePiece) this.Board[4][4]);

        this.Board[5][4] = new Pawn((this.def), "D3");
        arry_sort.add((ConcretePiece) this.Board[5][4]);
        this.Board[6][4] = new Pawn((this.def), "D4");
        arry_sort.add((ConcretePiece) this.Board[6][4]);
        this.Board[3][5] = new Pawn((this.def), "D5");
        arry_sort.add((ConcretePiece) this.Board[3][5]);
        this.Board[4][5] = new Pawn((this.def), "D6");
        arry_sort.add((ConcretePiece) this.Board[4][5]);
        this.Board[5][5] = new King((this.def), "K7");
        arry_sort.add((ConcretePiece) this.Board[5][5]);
        this.Board[6][5] = new Pawn((this.def), "D8");
        arry_sort.add((ConcretePiece) this.Board[6][5]);
        this.Board[7][5] = new Pawn((this.def), "D9");
        arry_sort.add((ConcretePiece) this.Board[7][5]);
        this.Board[4][6] = new Pawn((this.def), "D10");
        arry_sort.add((ConcretePiece) this.Board[4][6]);
        this.Board[5][6] = new Pawn((this.def), "D11");
        arry_sort.add((ConcretePiece) this.Board[5][6]);
        this.Board[6][6] = new Pawn((this.def), "D12");
        arry_sort.add((ConcretePiece) this.Board[6][6]);
        this.Board[5][7] = new Pawn((this.def), "D13");
        arry_sort.add((ConcretePiece) this.Board[5][7]);
        int counter=0;
        for (int i=0;i<this.Board.length;i++)
        {
            for (int j=0;j<this.Board.length;j++)
            {
                Position p1=new Position(i,j);
                if (this.getPieceAtPosition(p1)!=null)
                {
                    array_sort_postion.add(p1);
                    array_sort_postion.get(counter).AllPlayersWasAtPostion().add(((ConcretePiece) this.Board[i][j]).getName());
                    counter++;
                }
            }

        }

    }

    @Override
    public void undoLastMove() {
        if (undo.size() >= 2) {
            undo_lest_move = false;
            undo.removeLast();
            undo.removeLast();
            reset();
            for (int i = 0; i < undo.size(); i = i + 2) {
                move(undo.get(i), undo.get(i + 1));
            }
            undo_lest_move = true;
        }
    }

    @Override
    public int getBoardSize() {
        return Board.length;
    }

    //////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////
    public void SetBoard(Position a, Piece l) {
        Board[a.getX()][a.getY()] = l;
    }


    public boolean checkbounds(Position b) {
        if (b.getX() > 10 || b.getX() < 0) {
            return false;
        }
        if (b.getY() > 10 || b.getY() < 0) {
            return false;
        }
        return true;
    }


    private void create_players() {
        this.atck = new ConcretePlayer(false);
        this.def = new ConcretePlayer(true);

    }

    private boolean logic_move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        Piece to = getPieceAtPosition(b);
        String s = from.getType();
        if (s.equals("♙")) {
            if ((b.getX() == 0 && b.getY() == 0) || (b.getX() == 0 && b.getY() == 10) || (b.getX() == 10 && b.getY() == 0) || (b.getX() == 10 && b.getY() == 10))
                return false;
        }
        if (a.getX() == b.getX()) {
            if (a.getY() - b.getY() < 0) {
                for (int i = a.getY() + 1; i <= b.getY(); i++) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(a,b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((b.getY()-a.getY()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getY() - b.getY() > 0) {
                for (int i = a.getY() - 1; i >= b.getY(); i--) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if (!s.equals("♙")) {

                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(a,b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((a.getY()-b.getY()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }
        if (a.getY() == b.getY()) {
            if (a.getX() - b.getX() < 0) {
                for (int i = a.getX() + 1; i <= b.getX(); i++) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }


                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(a,b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((b.getX()-a.getX()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getX() - b.getX() > 0) {   //a.x > b.x
                for (int i = a.getX() - 1; i >= b.getX(); i--) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }
                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(a,b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((a.getX()-b.getX()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }

        return false;
    }

    public void kill(Position b, ConcretePlayer p) {
        Position n1 = new Position(b.getX(), b.getY() - 1);
        if ((checkbounds(n1)) && this.getPieceAtPosition(n1) != null) {
            if (this.getPieceAtPosition(n1).getOwner() != p) {
                Position n2 = new Position(b.getX(), b.getY() - 2);
                if ((checkbounds(n2)) && this.getPieceAtPosition(n2) != null && this.getPieceAtPosition(n2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(n2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() - 1] = null;
                        }
                    }
                }
                if ((n1.getY() == 0)||(n1.getY()==1)&&((n1.getX()==0)||(n1.getX()==10))) {
//               if (n1.getY() == 0) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() - 1] = null;
                    }
                }
            }
        }


        Position s1 = new Position(b.getX(), b.getY() + 1);
        if ((checkbounds(s1)) && this.getPieceAtPosition(s1) != null) {
            if (this.getPieceAtPosition(s1).getOwner() != p) {
                Position s2 = new Position(b.getX(), b.getY() + 2);
                if ((checkbounds(s2)) && this.getPieceAtPosition(s2) != null && this.getPieceAtPosition(s2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(s2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() + 1] = null;
                        }
                    }
                }
//                if (s1.getY() == 10){
                if ((s1.getY() == 10)||(s1.getY()==9)&&((s1.getX()==0)||(s1.getX()==10))) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() + 1] = null;
                    }
                }
            }
        }

        Position e1 = new Position(b.getX() + 1, b.getY());
        if ((checkbounds(e1)) && this.getPieceAtPosition(e1) != null) {
            if (this.getPieceAtPosition(e1).getOwner() != p) {
                Position e2 = new Position(b.getX() + 2, b.getY());
                if ((checkbounds(e2)) && this.getPieceAtPosition(e2) != null && this.getPieceAtPosition(e2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(e2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() + 1][b.getY()] = null;
                        }
                    }
                }
             //   if (e1.getX() == 10){
                 if ((e1.getX() == 10)||(e1.getX()==9)&&((e1.getY()==0)||(e1.getY()==10))) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() + 1][b.getY()] = null;
                    }
                }
            }
        }
        Position w1 = new Position(b.getX() - 1, b.getY());
        if ((checkbounds(w1)) && this.getPieceAtPosition(w1) != null) {
            if (this.getPieceAtPosition(w1).getOwner() != p) {
                Position w2 = new Position(b.getX() - 2, b.getY());
                if ((checkbounds(w2)) && this.getPieceAtPosition(w2) != null && this.getPieceAtPosition(w2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(w2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() - 1][b.getY()] = null;
                        }
                    }
                }
 //               if (w1.getX() == 0){
               if ((w1.getX() == 0)||(w1.getX()==1)&&((w1.getY()==0)||(w1.getY()==10))) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() - 1][b.getY()] = null;
                    }
                }
            }
        }

    }

    private boolean kill_King(Position b, Position liveKing, ConcretePlayer def) {
        boolean n = false;
        boolean s = false;
        boolean e = false;
        boolean w = false;
        Position w1 = new Position(live_king.getX() - 1, live_king.getY());
        Position e1 = new Position(live_king.getX() + 1, live_king.getY());
        Position s1 = new Position(live_king.getX(), live_king.getY() + 1);
        Position n1 = new Position(live_king.getX(), live_king.getY() - 1);
        if (!checkbounds(w1)) {
            w = true;
        } else {
            if (this.getPieceAtPosition(w1) != null) {
                if (this.getPieceAtPosition(w1).getOwner() != def) {
                    w = true;
                }
            }
        }
        if (!checkbounds(e1)) {
            e = true;
        } else {
            if (this.getPieceAtPosition(e1) != null) {
                if (this.getPieceAtPosition(e1).getOwner() != def) {
                    e = true;
                }
            }
        }
        if (!checkbounds(s1)) {
            s = true;
        } else {
            if (this.getPieceAtPosition(s1) != null) {
                if (this.getPieceAtPosition(s1).getOwner() != def) {
                    s = true;
                }
            }
        }
        if (!checkbounds(n1)) {
            n = true;
        } else {
            if (this.getPieceAtPosition(n1) != null) {

                if (this.getPieceAtPosition(n1).getOwner() != def) {
                    n = true;
                }
            }
        }

        if (n && s && e && w) {

           // Pawn pieceAtPosition = (Pawn) this.getPieceAtPosition(b); //no need to caunt the kill of the king
           // pieceAtPosition.inc_kill();
            return true;
        }
        return false;

    }
    public void printQ1(boolean whowin) {

        for (int i=0;i<arry_sort.size();i++)
        {
            if (arry_sort.get(i).getNum_step()>0) {
                arry_sort.get(i).Close_S_potion();
            }
        }
        Collections.sort(arry_sort,q1);
        if(whowin==true)
        {
            for (int i=0;i<arry_sort.size();i++) {
                if (arry_sort.get(i).getOwner() == this.atck) {
                    if (arry_sort.get(i).getNum_step() > 0)
                        System.out.println(arry_sort.get(i).getS_postion());
                }
            }
            for (int i=0;i<arry_sort.size();i++) {
                if (arry_sort.get(i).getOwner()==this.def)
                {
                    if (arry_sort.get(i).getNum_step()>0)
                        System.out.println(arry_sort.get(i).getS_postion());
                }
            }
        }
        if (whowin==false)
        {
            for (int i=0;i<arry_sort.size();i++) {
                if (arry_sort.get(i).getOwner() == this.def) {
                    if (arry_sort.get(i).getNum_step() > 0)
                        System.out.println(arry_sort.get(i).getS_postion());
                }
            }
            for (int i=0;i<arry_sort.size();i++) {
                if (arry_sort.get(i).getOwner()==this.atck)
                {
                    if (arry_sort.get(i).getNum_step()>0)
                        System.out.println(arry_sort.get(i).getS_postion());
                }
            }


        }
        System.out.println("***************************************************************************");
    }
    private void print_q2() {
        Collections.sort(arry_sort, q2);
        for (int i=arry_sort.size()-1;i>=0;i--) {
            if (arry_sort.get(i) instanceof Pawn) {
                if (((Pawn) arry_sort.get(i)).getKill() != 0) {
                    System.out.println(arry_sort.get(i).getName() + ": " + ((Pawn) arry_sort.get(i)).getKill() + " kills");
                }

            }
        }
        System.out.println("***************************************************************************");
    }
    private void print_q3() {
        Collections.sort(arry_sort, q3);
        for (int i=arry_sort.size()-1;i >=0 ;i--){
            if (arry_sort.get(i).get_num_square()!=0){
                System.out.println(arry_sort.get(i).getName()+": "+arry_sort.get(i).get_num_square()+" squares");
            }
        }
        System.out.println("***************************************************************************");
    }
    private void print_q4() {
        Collections.sort(array_sort_postion,q4);
        {
            for (int i =0;i<array_sort_postion.size();i++)
            {
                if (array_sort_postion.get(i).AllPlayersWasAtPostion().size()>1) {
                    System.out.println("(" + array_sort_postion.get(i).getX() + ", " + array_sort_postion.get(i).getY() + ")" + array_sort_postion.get(i).AllPlayersWasAtPostion().size() + " pieces");
                }
            }
        }
        System.out.println("***************************************************************************");    }

    Comparator<ConcretePiece> q1 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            int result = Integer.compare(o1.getNum_step(), o2.getNum_step());
            if (result==0){
                int o1_s = Integer.parseInt(o1.getName().substring(1,o1.getName().length()));
                int o2_s = Integer.parseInt(o2.getName().substring(1,o2.getName().length()));
                result = Integer.compare(o1_s,o2_s);
            }
            return result;
        }
    };
    Comparator<ConcretePiece> q2 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            int result = 0;
            if ((o2.getType().equals("♔"))&&(o1.getType().equals("♙"))){
               if (((Pawn) o1).getKill()>0){
                   return 1;
               }
                int o1_s = Integer.parseInt(o1.getName().substring(1,o1.getName().length()));
                int o2_s = Integer.parseInt(o2.getName().substring(1,o2.getName().length()));
                return (-1)*(Integer.compare(o1_s,o2_s));

            }
            else if  ((o1.getType().equals("♔"))&&(o2.getType().equals("♙"))){

                if (((Pawn) o2).getKill()>0){
                    return 1;
                }
                int o1_s = Integer.parseInt(o1.getName().substring(1,o1.getName().length()));
                int o2_s = Integer.parseInt(o2.getName().substring(1,o2.getName().length()));
                return (-1)*(Integer.compare(o1_s,o2_s));

            }
            else if ((o1.getType().equals("♙"))&&(o2.getType().equals("♙"))) {
                result = Integer.compare( ((Pawn) o1).getKill(), ((Pawn) o2).getKill());
            }

           // result = Integer.compare( ((Pawn) o1).getKill(), ((Pawn) o2).getKill());

            if (result==0){
                int o1_s = Integer.parseInt(o1.getName().substring(1,o1.getName().length()));
                int o2_s = Integer.parseInt(o2.getName().substring(1,o2.getName().length()));
                result = (-1)*(Integer.compare(o1_s,o2_s));
            }
            if (result==0){
                if (o1.getOwner()==atck&&atck_win){
                    return -1;
                }
                return 1;
            }
            return result;
        }
    };
    Comparator<ConcretePiece> q3 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            int result = 0;
            result = Integer.compare(o1.get_num_square(), o2.get_num_square());

        if (result==0){
            int o1_s = (Integer.parseInt(o1.getName().substring(1,o1.getName().length())))*(-1);
            int o2_s = (Integer.parseInt(o2.getName().substring(1,o2.getName().length())))*(-1);
            result = Integer.compare(o1_s,o2_s);
            }
            if (result==0){
                if (o1.getOwner()==atck&&atck_win){
                  return -1;
                }
                return 1;
            }
            return result;
        }
    };
    Comparator<Position> q4 = new Comparator<Position>() {
        @Override
        public int compare(Position o1, Position o2) {
            int result = 0;
            result = Integer.compare(o2.AllPlayersWasAtPostion().size(), o1.AllPlayersWasAtPostion().size());

            if (result==0){

                result = Integer.compare(o1.getX(), o2.getX());
                {
                    if (result==0)
                    {
                        result = Integer.compare(o1.getY(), o2.getY());
                    }
                }
            }
            return result;
        }
    };

}


