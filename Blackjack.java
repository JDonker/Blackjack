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
public class Blackjack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Welkom bij Blackjack! Type je naam, krijg 100 euro speelgeld, en doe mee!");
        Speltafel tafel = new Speltafel();
        tafel.run();
    }
}
