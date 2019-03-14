/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.Map;

/**
 *
 * @author nKBlaZy
 */
public class Case {
    /* Coordonnées de la Case */
    private int x,y;
    /* Vrai si la case est en bordure */
    private boolean bordure;
    /* Coordonnées des cases où sera téléporté le monstre 
     * String : Haut, Bas, Gauche, Droite
     */
    private Map<String, int[]> tp;
    
    public Case(int _x, int _y, boolean _bordure, Map<String, int[]> _tp) {
        this.x = _x;
        this.y = _y;
        this.bordure = _bordure;
        this.tp = _tp;
    }
}
