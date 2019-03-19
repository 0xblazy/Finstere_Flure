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
public class Partie {
    /* Labyrinthe de la partie */
    private Labyrinthe labyrinthe;
    /* Flaques d'hémoglobine */
    private Hemoglobine hemoCarree, hemoLine;
    /* Monstre (placé en (0,0))*/
    private Monstre monstre;
    /* Liste des murs */
    private List<Mur> murs;
    
    /* Constructeur */
    public Partie() {
        this.labyrinthe = new Labyrinthe();
        this.labyrinthe.initLaby();
        this.murs = new ArrayList<>();
    }
    
    /* Initialise la Partie (dispo les pions sur le labyrinthe) */
    public void initPartie() {
        // Ajout de la flaque carrée
        this.hemoCarree = new Hemoglobine(8, 2, Finstere.CARRE, this.labyrinthe);
        this.labyrinthe.setHemoglobine(8, 2, true);
        this.labyrinthe.setHemoglobine(9, 2, true);
        this.labyrinthe.setHemoglobine(8, 3, true);
        this.labyrinthe.setHemoglobine(9, 3, true);
        // Ajout de la flaque linéaire horizontale
        this.hemoLine = new Hemoglobine(4, 8, Finstere.LINEHORI, this.labyrinthe);
        this.labyrinthe.setHemoglobine(4, 8, true);
        this.labyrinthe.setHemoglobine(5, 8, true);
        this.labyrinthe.setHemoglobine(6, 8, true);
        this.labyrinthe.setHemoglobine(7, 8, true);
        // Ajout des murs
        this.murs.add(new Mur(2, 2, this.labyrinthe));
        this.labyrinthe.setMur(2, 2, true);
        this.murs.add(new Mur(12, 3, this.labyrinthe));
        this.labyrinthe.setMur(12, 3, true);
        this.murs.add(new Mur(7, 4, this.labyrinthe));
        this.labyrinthe.setMur(7, 4, true);
        this.murs.add(new Mur(8, 5, this.labyrinthe));
        this.labyrinthe.setMur(8, 5, true);
        this.murs.add(new Mur(13, 5, this.labyrinthe));
        this.labyrinthe.setMur(13, 5, true);
        this.murs.add(new Mur(6, 6, this.labyrinthe));
        this.labyrinthe.setMur(6, 6, true);
        this.murs.add(new Mur(4, 7, this.labyrinthe));
        this.labyrinthe.setMur(4, 7, true);
        this.murs.add(new Mur(12, 7, this.labyrinthe));
        this.labyrinthe.setMur(12, 7, true);
        this.murs.add(new Mur(14, 8, this.labyrinthe));
        this.labyrinthe.setMur(14, 8, true);
        this.murs.add(new Mur(5, 9, this.labyrinthe));
        this.labyrinthe.setMur(5, 9, true);
        this.murs.add(new Mur(8, 9, this.labyrinthe));
        this.labyrinthe.setMur(8, 9, true);
        //Ajout du monstre
        this.monstre = new Monstre(this.labyrinthe);
        this.labyrinthe.setMonstre(0, 0, true);
    }
    
    public void afficherLaby() {
        System.out.println(this.labyrinthe);
    }
}
