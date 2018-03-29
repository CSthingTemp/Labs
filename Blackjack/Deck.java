import java.util.ArrayList;
public class Deck{
    private Card[] cards = new Card[52];
    
    public Deck(){
        //make a new deck with 52 cards
        ArrayList<Card> tempHolder = new ArrayList<Card>();
        for(int j=0; j<4; j++){ //the suits
            for(int i=0; i<13; i++)
                tempHolder.add(new Card(i+1,j+1));
        }
        Card[] tempArray = new Card[52];
        this.cards = tempHolder.toArray(tempArray);
    }
    
    public Card getCard() throws OutOfCards{
        //gets a random card from the deck and returns it
        //old card is replaced with null to avoid duplicates
        
        if(!this.hasCards())
            throw new OutOfCards();
        //<i>when</i> would the deck lose all of the cards?
        
        
        byte num;
        do{
            num = (byte)(Math.random()*this.cards.length);
        }while(this.cards[num] == null);
        
        Card temp = this.cards[num];
        this.cards[num] = null;
        return temp;
    }
    public boolean hasCards(){
        boolean enoughCards = false;
        for(Card c : this.cards){
            if(c != null){
                enoughCards = true;
                break;
            }
        }
        return enoughCards;
    }
    
    public String toString(){
        String allCards = "";
        for(int i=0; i<this.cards.length-1; i++)
            allCards += this.cards[i]+"\n";
        allCards += this.cards[this.cards.length-1];
        
        return allCards;
    }
}
