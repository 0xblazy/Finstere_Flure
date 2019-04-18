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
public class Carte {
    /* Valeur de la Carte */
    private final int valeur;
    
    /* Constructeur */
    public Carte(int _valeur) {
        this.valeur = _valeur;
    }
    
    /* Retourne la Carte sous forme d'une chaîne de caractères */
    @Override
    public String toString() {
       if (this.valeur == Finstere.X) {
           return "X";
       } else if (this.valeur == Finstere.XX) {
           return "XX";
       } else {
           return "" + this.valeur;
       }
    }
    
    /* Getter */
    public int getValeur() {
        return this.valeur;
    }
}
