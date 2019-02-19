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
public class Stapel extends KaartHouder {
    
    public Stapel(){
        super();
        nieuweStapel();
    }
    
    public void nieuweStapel(){
        // maak een nieuw dek aan
        super.kaarten = new ArrayList<>();
        
        // vul het dek met de 52 kaarten
        for(int i=2;i<15;i++){
            for (int j=0;j<4;j++) {
                super.kaarten.add(new Kaart(i,j));
            }
        }
    }
    
}
