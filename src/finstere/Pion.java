/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

/**
 *
 * @author nKBlaZy
 */
abstract class Pion {
    /* Coordonnées d'un Pion */
    protected int x,y;
    /* Partie où est le Pion */
    protected Partie partie;
    
    /* Constructeur */
    public Pion(int _x, int _y, Partie _partie) {
        this.x = _x;
        this.y = _y;
        this.partie = _partie;
    }
    
    /* Getters */
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    /* Setters */
    public void setX(int _x) {
        this.x = _x;
    }

    public void setY(int _y) {
        this.y = _y;
    } 
}
