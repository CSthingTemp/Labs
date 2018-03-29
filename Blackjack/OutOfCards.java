public class OutOfCards extends RuntimeException{
    
    public OutOfCards(){
        super("No more cards in deck.");
        //not supposed to be a serious error
    }
    
}
