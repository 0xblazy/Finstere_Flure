/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nKBlaZy
 */
public class Joueur {
    /* Nom du Joueur */
    private String name;
    /* Personnage du Joueur */
    private Personnage[] personnages;
    /* Nombre de Personnage restant */
    private int nbRestant;
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
        this.nbRestant = 4;
    }
    
    /* Retour la liste de Personnages encore jouables */
    public Map<Integer,String> persoJouables() {
        HashMap<Integer,String> perso = new HashMap<>();
        
        for (int i = 0 ; i < this.personnages.length ; i++) {
            if (!this.personnages[i].isJoue() && !this.personnages[i].isRip() 
                    && !this.personnages[i].isExit()) perso.put(i + 1, "" 
                            + this.personnages[i]);
        }
        
        return perso;
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
    
    /* Getters */
    public int getNbRestant() {
        return this.nbRestant;
    }
    
}
