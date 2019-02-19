/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Jasper
 */
public class KaartHouder {
    protected ArrayList<Kaart> kaarten;
    protected Random random;
        
    public KaartHouder(){
        this.kaarten = new ArrayList<>();
        this.random = new Random();
    }
    
    public void printKaarten(){
        // print kaarten op de stapel naar het scherm
        for(Kaart kaart: this.kaarten){
            System.out.println(kaart.toString());
        }
    }
    
    public ArrayList<Kaart>  getKaarten(){
        return this.kaarten;
    }
    
    public void add(Kaart kaart){
        this.kaarten.add(kaart);
    }
     
    public void add(ArrayList<Kaart> kaarten) {
         this.kaarten.addAll(kaarten);
     }
    
    public void sorteerKaarten(){
        Collections.sort(this.kaarten); 
    }
    
    public Kaart getKaart() {
        Kaart bovenste = this.kaarten.get(0);
        this.kaarten.remove(0);
        return bovenste;
    }
    
    public void schudKaarten(){
        /* schud de kaarten door uit de stapel een random kaart te pakken 
        en deze op een andere stapel te leggen */
        int kaartnr;
        ArrayList<Kaart> geschud = new ArrayList<Kaart>();
        while(this.kaarten.size()>0)  {
            kaartnr = random.nextInt(this.kaarten.size());
            geschud.add(this.kaarten.get(kaartnr));
            this.kaarten.remove(kaartnr);
        }
        
        // het dek wordt nu het dek dat is geschud 
        this.kaarten=geschud;  
    }
    
    public int aantalKaarten(){
        // tel het aantal kaarten dat nog in het dek zit
        return this.kaarten.size();  
    }

    
}
