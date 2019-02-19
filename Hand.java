/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;


/**
 *
 * @author Jasper
 */
public class Hand extends KaartHouder {
    private boolean playsRound;
    private int inzet;
    
    Hand(){
        super();
        this.inzet=0;
        this.playsRound=true;
    }
    
    Hand(Kaart kaart){
        this();
        super.kaarten.add(kaart);
    }
    
    Hand(ArrayList<Kaart> kaarten) {
        this();
        super.kaarten.addAll(kaarten);
    }
    
    public boolean speeltRonde(){
        if (getScore()>20) {
            this.playsRound=false;
        }
        return this.playsRound;
    }
    
    public void pas(){
        this.playsRound=false;
    }
       
    public int getScore(){
        return berekenScore();
    }
    
    public int berekenScore(){
        int maxScore=21;
        int som = 0;
        int aantalAzen = 0;
        
        // tel alle waarden van de kaarten op neem aas=1
        for(Kaart kaart : getKaarten()){
            if (kaart.getTypeValue()<11) {
            som+=kaart.getTypeValue();
            } else if (kaart.getTypeValue()==14){
                som+=1;
                aantalAzen+=1;
            } else {
                som+=10;
            }
        }
        
        // tot slot kijken of we de aas waarde kunnen verhogen
        for(int i=0;i<aantalAzen;i++){
            if (som+10<=maxScore) {
                som+=10;
            } else {
                break;
            }
        }
        
        return som;
    }
    
    public String scoreString() {
        if (getScore()>20) {
            if (hasBlackjack()) {
                return " heeft Blackjack!";
            } else if (getScore() ==21) {
                return " heeft 21!";
            } else {
               return " is kapot!";
            }
        } else {
           return " heeft een score van " + getScore();        
        }
    }
    
    public boolean hasBlackjack(){
        if (this.kaarten.size()==2 && berekenScore()==21){
            return true;
        }
        
        return false;
        
    }
    
    public void putInzet(int amount){
       this.inzet = amount;
    }
    
    public int getInzet() {
        return this.inzet;
    }
    
    public void removeInzet(){
        this.inzet=0;
    }
    
    public void printKaarten() {
        // print alle kaarten
        getKaarten().forEach((kaart) -> System.out.println(kaart.toString()));
        // print score
        System.out.println("score: " + getScore());
        // print witregel
        System.out.println("");
    }
}
