/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

/**
 *
 * @author S15
 */
public class Monstre extends Pion {
    private int direction;
    
    /*Création de Monstre qui hérite de Pion*/
    public Monstre() {
        super(0, 0);
        this.direction = Finstere.DROITE;
    }
    
}
