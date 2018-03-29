public class BadInput extends RuntimeException{
    
    public BadInput(){
        super("Bad input! Don't do that!");
    }
    
    public BadInput(Object... messages){
        super("Bad input! "+(messages.length==0 ? "Don't do that!" : stringify(messages)));
        //is it even possible to call this with messages.length==0?
    }
    
    private static String stringify(Object... m){
        String stuff = "Information: ";
        for(byte i=0; i<m.length-1; i++)
            stuff += m[i]+", ";
        return stuff + m[m.length-1];
    }
}
