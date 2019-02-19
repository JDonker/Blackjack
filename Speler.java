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
public class Speler {
    private String naam;
    private ArrayList<Hand> handen;
    private boolean plays;
    private int euros;
    

    Speler(String naam){
        this.naam= naam;
        this.plays=true;
        this.euros=100;
    }
    
    Speler(String naam,int euros){
        this.naam= naam;
        this.euros=euros;
        this.plays=true;
    }
    
    
    public void nieuweRonde(){
        this.handen= new ArrayList<Hand>();
        if (this.plays==true) {
            this.handen.add(new Hand());
        }
    }
    
    public ArrayList<Hand> getHanden(){
        return this.handen;
    }
    
    public Hand getHand(){
        return this.handen.get(0);
    }
    
    public String getName(){
        return this.naam;
        
    }
        
    public boolean speeltMee(){
        
        return this.plays;
    }
        
    public void stop(){
        this.plays=false;
    }
    
    public boolean checkSplit(Hand hand){
        if(hand.getKaarten().size()==2) {
            if(hand.getKaarten().get(0).getTypeValue()==hand.getKaarten().get(1).getTypeValue()) {
               return true;
            }
        }
        
      return false;
    }
    
    
    public void split(Hand hand){
        // check 
        if (checkSplit(hand)) {
            Kaart kaart = hand.getKaart();
            this.handen.add(new Hand());
            this.handen.get(this.handen.size()-1).add(kaart);
        }

    }
    
    public boolean meerdereHanden(){
        return (this.handen.size()>1);
        
    }
    
    public boolean hasMoney(int amount) {
        if(this.euros >=amount && amount>0) {
            return true;
        }
        return false;
    }
    
    public void addMoney(int amount){
        this.euros+=amount;
    }
    
    public boolean getMoney(int amount) {
        if(hasMoney(amount)) {
            this.euros-=amount;
            return true;
        } 
        return false;
    }
    
    public int showMoney(){
        return this.euros;
    }
    
    public void printSpeler() {
	    int handNr;
	    System.out.println(getName());
	    if (meerdereHanden()) {
	        handNr = 1;
	        for (Hand hand: getHanden()) {
	           System.out.println("Hand " + handNr);
	           hand.printKaarten();
	           handNr++;
	        }
	    } else {
	        getHand().printKaarten();
	    }
    }
    
}
