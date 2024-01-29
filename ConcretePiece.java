public class ConcretePiece implements Piece{
    private final ConcretePlayer owner;
    private final String name;
    private  String s_postion;
    private int num_step;
    private int square;

    public ConcretePiece(ConcretePlayer p,String name){ //this mattod is share to Pawn and king
        this.owner = p;
        this.name= name;
        this.num_step=0;
        this.square=0;
        s_postion=null;
    }
    @Override
    public Player getOwner() {
        return owner;
    } //return the ConcretePiece

    public String getName(){
        return name;
    } //return the name of the  ConcretePiece like k7 ,A6
    @Override
    public String getType() {
//        String t = getName();
//        String s = t.charAt(0) + "";
//        if (s.equals("k")) {
//            return "King";
//        }
//        return "Pawn";
  return "";
    }
    public void inc_step(){
        num_step++;
    }
    public void num_square(int num){
    square=square+num;
    }
    public int get_num_square(){
      return square;
    }
    public int getNum_step() {
        return num_step;
    }

    public String getS_postion() {
        return s_postion;
    }

    public void Add_S_potion(Position a,Position b) {

        if (this.s_postion != null) {
            this.s_postion += "(" + b.getX() + ", " + b.getY() + "), ";
        }
        if (this.s_postion == null) {

            this.s_postion = this.getName() + ": [(" + a.getX() + ", " + a.getY() + "), ";
            this.s_postion += "(" + b.getX() + ", " + b.getY() + "), ";
        }
    }
    public void Close_S_potion()
    {
        this.s_postion=this.s_postion.substring(0,this.s_postion.length()-2);
        this.s_postion+="]";
    }

}


