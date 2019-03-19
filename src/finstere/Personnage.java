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
public class Personnage extends Pion {
    /* Points de mouvement face clair/foncé */
    private final int pmC, pmF;
    /* Couleur du personnage (blue, brown, gray, green, purple, red, yellow) */
    private final String couleur;
    /* Vrai si le pion personnage est tourné face clair */
    private boolean faceClair;
    
    public Personnage(int _pmC, int _pmF, String _couleur) {
        super(-1, -1);
        this.pmC = _pmC;
        this.pmF = _pmF;
        this.couleur = _couleur;
        this.faceClair = true;
    } 
}
