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
public class Hemoglobine extends Pion {
    /* Type de flaque d'hémoglobine (carré, linéaire v/h) */
    private String type;
    
    public Hemoglobine(int _x, int _y, String _type) {
        super(_x, _y);
        this.type = _type;
    }
}
