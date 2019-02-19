/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Jasper
 */
public class Kaart implements Comparable<Kaart>{
    int suit;
    int type;
    
    public Kaart(int type,int suit) throws IllegalArgumentException{
        // check of een geldige speelkaart wordt aangemaakt en maak deze aan.
        if (type>1 && type<15 && suit>-1 && suit<4) {
	        this.type=type;
	        this.suit=suit;
        } else {
            throw new IllegalArgumentException("Deze kaart bestaat niet!");
        }
    }
    
    public int getSuitValue(){
        // geeft de suit waarde van de kaart (ruiten,harten,schoppen,klaveren)
        return this.suit;
    }
    
    public int getTypeValue(){
        // geeft de type waarde van de kaart (2 tot 14)
        return this.type;
    }
    
    public String toString() {
        // geeft een string die de kaart beschrijft
        return getSuitString() + " " + getTypeString();
    }
    
    public String getSuitString(){
        // geeft het suit door van de kaart
        switch(suit) {
            case 0:
                return "ruiten";
            case 1: 
                return "harten";
            case 2:
                return "schoppen";
            default: 
                return "klaveren";
        } 
    
    }
    
    public String getTypeString(){
        // geef een string met kaart type (voor nrs natuurlljk gewoon het nr)
        switch(type) {
                case 11:
                    return "boer";
                case 12:
                    return "vrouw";
                case 13: 
                    return "heer";
                case 14: 
                    return "aas";
                default:    
                    return "" + this.type;
        }
    }
    
    @Override
    public int compareTo(Kaart kaart) {
        if (this.type==kaart.type) {
            return 0;
        } else if(this.type>kaart.type) {
            return 1;
        }
        return -1;
    }

}
