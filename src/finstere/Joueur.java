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
public class Joueur {
    /* Nom du joueur */
    private String name;
    /* Personnages du joueur */
    private Personnage[] personnages;
    /* Partie dans laquelle joue le Joueur */
    private Partie partie;
    /* Couleur des Personnage du Joueur */
    private String couleur;
    
    /* Constructeur */
    public Joueur(String _name, Partie _partie, String _couleur, Personnage[] _personnages) {
        this.name = _name;
        this.partie = _partie;
        this.couleur = _couleur;
        this.personnages = _personnages;
    }
    
    @Override
    public String toString() {
        String s = this.name + " :\n";
        
        for (int i = 0 ; i < this.personnages.length ; i++) {
            s += "  " + personnages[i];
            if (i < this.personnages.length - 1) s += "\n";
        }
        
        return s;
    }
}
