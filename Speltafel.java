/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jasper
 */
public class Speltafel {
    private Scanner reader;
    private Stapel stapel;
    private ArrayList<Speler> spelers;
    private Speler bank;
    
    public Speltafel(){
        this.reader = new Scanner(System.in);
        this.stapel = new Stapel();
        leesSpelers();
        this.bank = new Speler("Bank");
        if ( this.spelers.size()==0) {
        	this.spelers.add(new Speler("Jack de Zwart"));
        } 
    }
    
    public void run(){
        
        String input;
        
        // terwijl het spel gaande is
        outerloop:
        while(true) {
        	
            // nieuwe ronde nieuwe kansen! (lege handen en geschud vers dek)
            nieuweRonde();
           
            // inzetten
            inzetten();

            // Bank krijgt 1 kaart
            pakKaart(this.bank.getHand(),this.stapel);


            // spelers krijgen 2 kaarten
            for (Speler speler : this.spelers) {
                pakKaart(speler.getHand(),this.stapel);
                pakKaart(speler.getHand(),this.stapel);
            }

            // Bank krijgt 2e kaart
            pakKaart(this.bank.getHand(),this.stapel);

            // print bank en spelers
            System.out.println("----------------------------");
            printBank();
            printSpelers();
            System.out.println("----------------------------");

            // Nu mogen de spelers splitsen, kaarten pakken of passen
            spelersPeriode();
    
            // Bank pakt een kaart todat hij 17 of meer heeft
            while(this.bank.getHand().getScore()<17){
                pakKaart(this.bank.getHand(),this.stapel);
            }
            
            // print nu de uitslag
            uitslag();
            
            // Check of er nog spelers meespelen en verwijder gestopte spelers
            ArrayList<Speler> gestopt = new ArrayList<Speler>();
            for (Speler speler : this.spelers) {
               if (!speler.speeltMee()) {
                   gestopt.add(speler);
               } 
               
               if (!speler.hasMoney(1)) {
                   // blutte spelers  verwijderen
                     gestopt.add(speler);
               }
            }
            
            this.spelers.removeAll(gestopt);
            
            if(this.spelers.size()==0){
                break;
            }
            
            // vraag of er nog een spel gespeeld moet worden
            System.out.println("Nog een spel? Type [n] voor nee.");
            input = reader.nextLine();
            switch(input) {
                case "n":
                    break outerloop;
                default:
                    break;
            }
        }
    }
    
    
    private void leesSpelers(){
        
        this.spelers = new ArrayList<>();
        int i = 1;
        String naam;  

        while(true) {
            System.out.println("Geef de naam van speler " + i + " en druk op [enter]. Of druk op [enter] om te starten.");
            naam = reader.nextLine();
            
            // maak speler met ingelezen naam of in geval van geen naam start spel 
            if (naam.length()==0) {
                break;
            } 
            this.spelers.add(new Speler(naam));   
            i++;
        }
    }
    
       
    public void pakKaart(Hand hand,Stapel stapel) {
            Kaart kaart= stapel.getKaart();
            hand.add(kaart);
    }
    
    
    public void nieuweRonde(){
        stapel.nieuweStapel();
        stapel.schudKaarten();
        this.spelers.forEach((speler) -> speler.nieuweRonde());
        this.bank.nieuweRonde();
    }
    
    
    
    public void printSpelers() {
         for (Speler speler : this.spelers) {
        	 speler.printSpeler();
        }
    }
    

    public void printBank(){
        // print alleen de tweede kaart van de Bank
        System.out.println("Bank");
        System.out.println(this.bank.getHand().getKaarten().get(1).toString());
        System.out.println("");
    }
    
    
    public void spelersPeriode(){
        String input;
        int handID;
        
        // roteer over spelers vraag wat ze willen doen
        for (Speler speler : this.spelers) {
            // Check of er kaarten gesplitst kunnen worden en vraag of dat gedaan moet worden
            checkSplit(speler);
            
            
            handID=0;
            for(Hand hand: speler.getHanden()) {
                // spelronde voor alle handen
                while (hand.speeltRonde()) {
                    System.out.println(speler.getName());
                    if (speler.meerdereHanden()) {
                        System.out.println("Hand " + (handID+1));
                    }
                    hand.printKaarten();;
                     System.out.println(speler.getName() + " is aan de beurt: [k] kaart; [p] pas; [q] stoppen; [b] print alles");
                    if (speler.hasMoney(hand.getInzet()) && hand.aantalKaarten()==2) {
                        System.out.println("Extra optie:  [v] verdubbel (nog 1 kaart en inzet x 2)");
                    } 
                    
                    input = this.reader.nextLine();
                    switch(input) {
                        case "k":
                           pakKaart(hand,this.stapel);
                           break;
                        case "p":
                            hand.pas();
                            break;
                        case "q":
                            hand.pas(); // wel deze ronde afmaken natuurlijkk
                            speler.stop();
                            break;
                        case "b":
                            System.out.println("----------------------------");
                            printBank();
                            printSpelers();
                            System.out.println("----------------------------");
                            break;
                        case "v":
                            if (speler.hasMoney(hand.getInzet())) {
                                speler.getMoney(hand.getInzet());
                                hand.putInzet(hand.getInzet()*2);
                                pakKaart(hand,this.stapel);
                                hand.pas();
                                hand.printKaarten();;
                                break;
                            }
                        case "default":
                            System.out.println("Verkeerde input");
                    }
                }
                
                if (speler.meerdereHanden()) {
                    System.out.println(speler.getName() + "(hand" + (handID+1) +") " +  hand.scoreString());
                } else { 
                    System.out.println(speler.getName() +  hand.scoreString());
                }
                System.out.println("");
                handID++;
            }
        }
    }
    
    public void uitslag(){
        // Output bank en winst verlies van de spelers
       System.out.println("Bank");
       this.bank.getHand().printKaarten();;
         int handID;

       String spelerString;
        for (Speler speler : this.spelers) { 
            handID=0;
            for(Hand hand:speler.getHanden()) {
                spelerString=speler.getName();
                 if (speler.meerdereHanden()){
                    spelerString+= " Hand" + (handID+1);
                }
                if (hand.getScore()>21) {
                    // Jij bent kapot dus hebt altijd verloren
                    System.out.println(spelerString + " heeft verloren (-" + hand.getInzet() + ")." );
                } else if(this.bank.getHand().getScore()>21 ) {
                    // bank is kapot en jij niet dus je hebt gewonnen
                    System.out.println(spelerString + " heeft gewonnen (+" + hand.getInzet() + ")." );
                    speler.addMoney(hand.getInzet()*2);
                } else {
                    if (this.bank.getHand().getScore()>hand.getScore()){
                        // bank heeft een hogere score dan jij 
                        System.out.println(spelerString + " heeft verloren (-" + hand.getInzet() + ")." );
                    } else if(this.bank.getHand().getScore()<hand.getScore()) {
                        // bank heeft een lagere score dan jij
                        System.out.println(spelerString + " heeft gewonnen (+" + hand.getInzet() + ")." );
                        speler.addMoney(hand.getInzet()*2);
                    } else {
                        // gelijke score check Blackjack
                        if(hand.getScore()==21 && speler.getHand().getKaarten().size()==2 && this.bank.getHand().getKaarten().size()>2) {
                            // Jij en bank hebben 21 maar jij had Blackjack!
                            System.out.println(spelerString + " heeft gewonnen en had Blackjack(+" + hand.getInzet()*3 + ")." );
                            speler.addMoney(hand.getInzet()*3);
                        } else if(hand.getScore()==21 && speler.getHand().getKaarten().size()>2 && this.bank.getHand().getKaarten().size()==2) {
                            // Jij en bank hebben 21 maar bank had Blackjack!
                            System.out.println(spelerString + " heeft verloren(-" + hand.getInzet() + ")." );
                        }
                        else {
                            // Jij en bank hebben beide dezelfde score dus gelijkgespeeld.
                            System.out.println(spelerString + " heeft gelijkgespeeld.");
                            speler.addMoney(hand.getInzet());
                        }
                    } 
                }
                handID++;
            }
        }
    }
    
    
    public void checkSplit(Speler speler){
        int handID=0;
        Hand huidigeHand;
        String input;
            // check split meerdere splits mogelijk
        while (handID<speler.getHanden().size()) {
            huidigeHand=speler.getHanden().get(handID);
            // als speler kan splitsen en er het geld voor heeft bied mogelijkheid tot splitsen
            if (speler.checkSplit(huidigeHand) && speler.hasMoney(huidigeHand.getInzet())) {
                System.out.println(speler.getName());
                huidigeHand.printKaarten();;
                System.out.println(speler.getName() + " heeft twee dezelfde kaarten wil je splitsen? [j]a/[n]ee (inzet verdubbelen)");
                input = reader.nextLine();
                switch(input){
                    case "j":
                        speler.split(huidigeHand);
                        speler.getMoney(huidigeHand.getInzet());
                        speler.getHanden().get(handID+1).putInzet(huidigeHand.getInzet());
                        pakKaart(huidigeHand,stapel);
                        pakKaart(speler.getHanden().get(handID+1),stapel);       
                        continue;
                    case "n":
                        break;
                    default :
                        System.out.println("Verkeerde invoer");
                        continue;
                }
            }
            handID++;
        }
    }
    
    public void inzetten(){
        int inzet;
        for (Speler speler : this.spelers) {
            while(true) {
                System.out.println(speler.getName() + "(" + speler.showMoney() + " euro) mag inzetten (minimaal 1):");
                
                // try catchje voor als iemand strings gaat invoeren of een verkeerd getal
                
                try {
                    inzet = Integer.parseInt(reader.nextLine());
                    if(speler.getMoney(inzet)) {    
                        speler.getHand().putInzet(inzet);
                        break;
                    } else {
                        throw new IllegalArgumentException("Verkeerde invoer!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        
    }
    
    
}
