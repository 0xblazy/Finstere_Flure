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
public class Paquet {
    /* Paquet de Carte */
    private Carte[] paquet;
    
    /* Constructeur */
    public Paquet() {
        this.paquet = new Carte[8];
        this.paquet[0] = new Carte(5);
        this.paquet[1] = new Carte(7);
        this.paquet[2] = new Carte(7);
        this.paquet[3] = new Carte(8);
        this.paquet[4] = new Carte(8);
        this.paquet[5] = new Carte(10);
        this.paquet[6] = new Carte(Finstere.X);
        this.paquet[7] = new Carte(Finstere.XX);
    }
    
    /* Mélange le paquet */
    public void melanger() {
        /* Échange deux Carte 500 fois */
        for (int i = 0 ; i < 500 ; i++) {
            this.echangerDeuxCartes();
        }
        
        /* Continue le mélange tant que la première Carte est une "une proie" ou
         * une "deux proies"
         */
        while (this.paquet[0].getValeur() == Finstere.X
                || this.paquet[0].getValeur() == Finstere.XX) {
            this.echangerDeuxCartes();
        }
    }
    
    /* Échange deux Carte du paquet */
    private void echangerDeuxCartes() {
        int a = (int) (Math.random() * 8);
        int b = (int) (Math.random() * 8);
        
        Carte temp = this.paquet[a];
        this.paquet[a] = this.paquet[b];
        this.paquet[b] = temp;                
    }
    
    /* Tire une Carte */
    public Carte tirerCarte() {
        Carte carte = this.paquet[0];
        
        /* Décale les Carte dans le paquet */
        for (int i = 0 ; i < 7 ; i++) {
            this.paquet[i] = this.paquet[i + 1];
        }
        this.paquet[7] = carte;
        
        return carte;
    }
}
