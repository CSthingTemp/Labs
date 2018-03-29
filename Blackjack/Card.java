public class Card{
    private byte value; //[1,10]
    private byte suit; //[1,4]
    private short type; //which image to draw
    
    private static final String[] valueString = {"Ace","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
    private static final String[] suitString = {"Diamonds","Clubs","Hearts","Spades"};
    
    public Card(int v, int s) throws BadInput{
        if(v<=0 || s<=0 || v>13 || s>4)
            throw new BadInput("value="+v,"suit="+s);
        
        this.type = (short)((s-1)*13 + v-1);
        this.value = (byte)(v>10 ? 10 : v);
        this.suit = (byte)s;
    }
    
    public byte getValue(){
        return this.value;
    }
    
    public String toString(){
        return valueString[this.type%13]+" of "+suitString[this.type/13];
        //return "["+this.type+", "+this.value+", "+this.suit+", "+valueString[this.type%13]+", "+suitString[this.type/13]+"]";
    }
    
}
