public class ConcretePlayer implements Player{
    private boolean atck;
    private int counter_win; //score
    public ConcretePlayer(boolean atck){
     this.atck = atck;
        counter_win = 0;
    }

    @Override
    public boolean isPlayerOne() {
        return atck;
    }
    public void inc(){
        counter_win += 1;
    }
    @Override
    public int getWins() {
        return counter_win;
    }


    public  void inc_wins()
     {
         this.counter_win++;
     }

}
