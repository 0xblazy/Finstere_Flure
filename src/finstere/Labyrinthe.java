/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

/**
 *
 * @author nicoi
 */
public class Labyrinthe {
    /* Tableau à 2 dimensions représentant le labyrinthe */
    private Case[][] labyrinthe;
    
    public void initLaby() {
        this.labyrinthe = new Case[11][16];
    }
}
