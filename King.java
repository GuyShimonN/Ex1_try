public class King extends ConcretePiece{
    public King(ConcretePlayer p,String name) {
        super(p, name);
    }
    @Override
    public String getType() {
        return "♔";
    }
}
