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
    /* Couleur du Personnage (blue, brown, gray, green, purple, red, yellow) */
    private final String couleur;
    /* Vrai si le Personnage est tourné face clair */
    private boolean faceClair;
    /* Vrai si le Personnage est mort */
    private boolean rip;
    /* Vrai si le Personnage est sorti du Labyrinthe */
    private boolean exit;
    /* Classement du Personnage */
    private int classement;
    
    /* Constructeur */
    public Personnage(int _pmC, int _pmF, String _couleur, Partie _partie) {
        super(-1, -1, _partie);
        this.pmC = _pmC;
        this.pmF = _pmF;
        this.couleur = _couleur;
        this.faceClair = true;
        this.rip = false;
        this.exit = false;
        this.classement = 0;
    }
    
    @Override
    public String toString() {
        String s = "Personnage ";
        
        if (this.couleur.equals(Finstere.COULEURS[0])) {
            s += "Bleu";
        } else if (this.couleur.equals(Finstere.COULEURS[1])) {
            s += "Marron";
        } else if (this.couleur.equals(Finstere.COULEURS[2])) {
            s += "Gris";
        } else if (this.couleur.equals(Finstere.COULEURS[3])) {
            s += "Vert";
        } else if (this.couleur.equals(Finstere.COULEURS[4])) {
            s += "Violet";
        } else if (this.couleur.equals(Finstere.COULEURS[5])) {
            s += "Rouge";
        } else if (this.couleur.equals(Finstere.COULEURS[6])) {
            s += "Jaune";
        }
        
        s += " " + this.pmC + "," + this.pmF + " ";
        
        if (faceClair) {
            s += "Face claire ";
        } else {
            s += "Face foncée ";
        }
        
        s += "(" + super.x + "," + super.y + ")";
               
        return s;
    }
}
