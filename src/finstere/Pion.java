/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

/**
 *
 * @author nK_BlaZy
 */
abstract class Pion {
    /* Coordonnées d'un pion */
    protected int x,y;
    
    public Pion(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }
}
