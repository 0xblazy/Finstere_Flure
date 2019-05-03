/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nKBlaZy
 */
public class Paquet {
    /* Paquet de Cartes */
    private Carte[] paquet;
    /* Liste des Cartes de la Pioche */
    private List<String> pioche;
    /* Liste des Cartes de la Défausse */
    private List<String> defausse;
    
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
        this.pioche = new ArrayList<>();
        this.defausse = new ArrayList<>();
    }
    
    /* Réinitialise la Pioche et la Defausse */
    public void resetPioche() {
        this.pioche.clear();
        this.defausse.clear();
        this.pioche.add("5");
        this.pioche.add("7");
        this.pioche.add("7");
        this.pioche.add("8");
        this.pioche.add("8");
        this.pioche.add("10");
        this.pioche.add("X");
        this.pioche.add("XX");
    }
    
    /* Mélange le Paquet */
    public void melanger() {
        /* Échange deux Cartes 500 fois */
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
    
    /* Échange deux Cartes du Paquet */
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
        
        /* Décale les Cartes dans le Paquet */
        for (int i = 0 ; i < 7 ; i++) {
            this.paquet[i] = this.paquet[i + 1];
        }
        this.paquet[7] = carte;
        
        if (carte.getValeur() == Finstere.X) {
            this.pioche.remove("X");
            this.defausse.add("X");
        } else if (carte.getValeur() == Finstere.XX) {
            this.pioche.remove("XX");
            this.defausse.add("XX");
        } else {
            this.pioche.remove("" + carte.getValeur());
            this.defausse.add("" + carte.getValeur());
        }
        
        return carte;
    }
    
    /* Getters */
    public List<String> getPioche() {
        return this.pioche;
    }

    public List<String> getDefausse() {
        return this.defausse;
    }
}
