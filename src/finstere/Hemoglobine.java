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
    /* Type de flaque d'hémoglobine (nom de l'image sans l'extension) */
    private String type;
    
    /* Constructeur */
    public Hemoglobine(int _x, int _y, String _type, Partie _partie) {
        super(_x, _y, _partie);
        this.type = _type;
    }
}
