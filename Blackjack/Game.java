import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JFrame{
    private Player thePlayer;
    private long bettingMoney = 0;
    private Deck theDeck;
    private ArrayList<Card> dealerHand = new ArrayList<Card>(); //the game holds the logic for the dealer
    
    public Game(){
        System.out.print("Welcome to Blackjack! What's your name? ");
        String tempName = Input.scan.nextLine();
        
        this.thePlayer = new Player(tempName);
        System.out.println();
        
        this.resetGame();
    }
    
    public void resetGame(){
        this.theDeck = new Deck();
        //System.out.println(theDeck);
        bettingMoney = 0;
        thePlayer.resetHand();
        this.resetDealerHand();
    }
    
    private void resetDealerHand(){
        this.dealerHand = new ArrayList<Card>();
    }
    
    public void hit(){
        try{
            Card temp = theDeck.getCard();
            thePlayer.addCard(temp);
        }catch(OutOfCards e){
            System.out.println(e+" . . . How did this happen?");
        }
    }
    public void newDealerCard(){
        this.dealerHand.add(theDeck.getCard());
    }
    
    public void bet(long m){
        if(thePlayer.getMoney() < m){
            bettingMoney += thePlayer.getMoney();
            thePlayer.loseMoney(thePlayer.getMoney());
        }else{
            bettingMoney += m;
            thePlayer.loseMoney(m);
        }
    }
    public void startingBet(){
        this.bet(10);
    }
    
    public long getBettingMoney(){
        return this.bettingMoney;
    }
    
    public void printHands(boolean showCard){
        System.out.print("Dealer hand: [" + (showCard ? this.dealerHand.get(0) : "UNKNOWN"));
        for(byte i=1; i<this.dealerHand.size(); i++)
            System.out.print(", " + this.dealerHand.get(i));
        
        System.out.println("]");
        
        System.out.print(thePlayer.name + " hand: [" + thePlayer.getCard(0));
        for(byte i=1; i<thePlayer.numCards(); i++)
            System.out.print(", " + thePlayer.getCard(i));
            
        System.out.println("]");
    }
    public void printHands(){
        printHands(false);
    }
    
    public void printInfo(){
        System.out.println("Betting money: "+bettingMoney);
        System.out.println(thePlayer.name+" money: "+thePlayer.getMoney());
    }
    
    public int getDealerHandTotal(){
        int total = 0, numAces = 0;
        for(byte i=0; i<this.dealerHand.size(); i++)
            if(this.dealerHand.get(i).getValue() != 1)
                total += this.dealerHand.get(i).getValue();
            else
                numAces++;
        
        total += numAces; //every ace must add at least 1
        for(byte i=0; i<this.dealerHand.size(); i++)
            if(this.dealerHand.get(i).getValue() == 1)
                if(total-11 <= 21) //there is space for the aces to have a higher value
                    total += 10;
        
        return total;
    }
    
    public void tie(){
        System.out.println("A tie! You get your money back.");
        thePlayer.gainMoney(bettingMoney);
    }
    
    public void win(){
        System.out.println("You won! You get twice your betting money!");
        thePlayer.gainMoney(bettingMoney*2);
    }
    
    public void lose(){
        System.out.println("You lost! You gain no money.");
    }
    
    public void endGame(){
        //contains the check for ending the game, so this is called if the game might be lost
        
        if(thePlayer.getMoney() <= 0){
            System.out.println("\nYou're bankrupt. Play again soon!");
            System.exit(0);
        }
    }
    
    public void blackjack(){
        System.out.println("Blackjack! You get 1.5× your betting money!");
        thePlayer.gainMoney((long)(bettingMoney*1.5));
    }
    
    public Player getPlayer(){
        return this.thePlayer;
    }
    
    public boolean cardsCanBeDealt(){
        return theDeck.hasCards();
    }
    
    public static void main(String[] args){
        Game g = new Game();
        boolean keepPlaying = true;
        while(keepPlaying){
            g.beginGame();
            
            g.takeBettingMoney();
            
            g.dealTheCards();
            
            g.winCalculation();
            
            g.endGame();
            
            System.out.print("\nKeep playing? ");
            String temp = Input.scan.nextLine();
            if(temp.toLowerCase().charAt(0) != 'y')
                break;
        }
    }
    
    public void beginGame(){
        this.resetGame();
        
        this.newDealerCard();
        this.newDealerCard();
        this.hit();
        this.hit();
        
        this.startingBet();
        
        this.printHands();
        this.printInfo();
        System.out.println();
    }
    
    public void takeBettingMoney(){
        boolean checkIsComplete = false;
        long num = 0;
        while(!checkIsComplete){
            System.out.print("How much more money do you want to bet? ");
            String temp = Input.scan.nextLine();
            try{
                num = Long.parseLong(temp);
                if(num < 0){
                    num = 0;
                    System.out.println("You can't bet negative money.");
                }else
                    checkIsComplete = true;
            }catch(NumberFormatException e){
                System.out.println("That's not a number!");
            }
        }
        this.bet(num);
    }
    
    public void dealTheCards(){
        boolean moreCards = true;
        while(moreCards && thePlayer.getHandTotal()<21 && this.cardsCanBeDealt()){
            System.out.print("Another card? ");
            String temp = Input.scan.nextLine();
            if(temp.toLowerCase().charAt(0) == 'y'){
                this.hit();
                System.out.println();
                this.printHands();
            }else
                moreCards = false;
            
            this.printInfo();
        }
        System.out.println();
    }
    
    public void winCalculation(){
        if(thePlayer.getHandTotal() > 21){
            this.printHands(true);
            this.lose();
        }else{
            boolean needToCheck = true;
            if(thePlayer.getHandTotal() == 21){
                System.out.print("Call blackjack? ");
                String temp = Input.scan.nextLine();
                if(temp.toLowerCase().charAt(0) == 'y'){
                    this.printHands(true);
                    this.blackjack();
                    needToCheck = false;
                }
            }
            if(needToCheck){
                while(this.getDealerHandTotal() < 17)
                    this.newDealerCard();
                
                if(this.getDealerHandTotal() > 21){
                    this.win();
                }else{
                    if(this.getDealerHandTotal() > this.getPlayer().getHandTotal())
                        this.lose();
                    else if(this.getDealerHandTotal() == this.getPlayer().getHandTotal())
                        this.tie();
                    else
                        this.win();
                }
                this.printHands(true);
            }
        }
    }
    
}
