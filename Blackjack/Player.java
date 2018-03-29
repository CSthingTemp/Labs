import java.util.ArrayList;
public class Player{
    private ArrayList<Card> hand = new ArrayList<Card>();
    String name; //doesn't really matter if it's private
    private long currentMoney = 100;
    
    public Player(String n){
        this.name = n;
    }
    
    public long getMoney(){
        return this.currentMoney;
    }
    
    public void loseMoney(long amount){
        this.currentMoney -= amount;
    }
    
    public void gainMoney(long amount){
        this.currentMoney += amount;
    }
    
    public void resetHand(){
        this.hand = new ArrayList<Card>();
    }
    
    public void addCard(Card c){
        this.hand.add(c);
    }
    
    public Card getCard(int num) /*throws ArrayIndexOutOfBoundsException*/{
        return this.hand.get(num);
    }
    
    public long numCards(){
        return this.hand.size();
    }
    
    public int getHandTotal(){
        int total = 0;
        for(byte i=0; i<this.hand.size(); i++)
            total += this.hand.get(i).getValue();
        
        for(byte i=0; i<this.hand.size(); i++)
            if(this.hand.get(i).getValue() == 1)
                if(total+10 <= 21) //there is space for the aces to have a higher value
                    total += 10;
        
        return total;
    }
    
    public String toString(){
        return this.name+" has "+this.hand.size()+" cards";
    }
    
    /*
    public int[] getHandTotal(){
        int aceTotal = 0, valueTotal = 0;
        for(int i=0; i<hand.size(); i++)
            if(hand.get(i).value == 1){
                aceTotal++;
            }else{
                valueTotal += hand.get(i).value;
            }
        
        int[] total = new int[(int)Math.pow(2,aceTotal+1)];
        for(int i=0; i<total.length; i++)
            total[i] = valueTotal;
        
        int aceNum = 0; //which ace is it
        for(int i=0; i<hand.size(); i++)
            if(hand.get(i).value == 1){
                for(int j=0; j<total.length; j++)
                    if(j<total.length%Math.pow(2,aceNum+1))
                        total[j] += 1;
                    else
                        total[j] += 11;
            }
        
        return total;
    }
    */
}
