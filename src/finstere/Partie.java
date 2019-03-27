/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    /* Joueurs de la partie */
    private Joueur[] joueurs;
    /* Nombre de vrais joueurs */
    private int nbJoueurs;
    
    /* Constructeur */
    public Partie() {
        this.labyrinthe = new Labyrinthe(this);
        this.murs = new ArrayList<>();
        this.joueurs = new Joueur[2];
    }
    
    /* Initialisation de la Partie (création des Joueur) */
    public void initPartie() {
        System.out.println("=== NOUVELLE PARTIE ===");
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Nombre de joueurs humain (1 ou 2) : ");
        this.nbJoueurs = sc.nextInt();
        while (this.nbJoueurs > 2 || this.nbJoueurs < 1) {
            System.out.print("Le nombre de joueurs humain doit être 1 ou 2\n"
                    + "Veuillez ressaisir un nombre : ");
            this.nbJoueurs = sc.nextInt();
        }
        
        int couleurPremier = 0;
        for (int i = 0 ; i < this.nbJoueurs ; i++) {
            System.out.println("Joueur " + (i+1) + " : ");
            System.out.print("Nom : ");
            String nom = sc.next();
            System.out.print("Couleurs :\n  1 - Bleu\n  2 - Marron\n"
                    + "  3 - Gris\n  4 - Vert\n  5 - Violet\n  6 - Rouge\n"
                    + "  7 - Jaune\nChoix : ");
            int couleur = sc.nextInt();
            while (couleur < 1 || couleur > 7) {
                System.out.print("Choix incorrecte, ressaisissez un nombre : ");
                couleur = sc.nextInt();
            }
            if (i == 0) {
                couleurPremier = couleur;
            } else {
                while (couleurPremier == couleur) {
                    System.out.print("Cette couleur est déjà prise...\n"
                            + "Choisissez en une autre : ");
                    couleur = sc.nextInt();
                }
            }
            this.joueurs[i] = new Joueur(nom, this, Finstere.COULEURS[couleur - 1]);
        }
        this.genLaby();
    }
    
    /* Génère le Labyrinthe et dispose les Pion dessus */
    private void genLaby() {
        System.out.println("...Génération du plateau de Jeu...");
        // Génération du Labyrinthe
        this.labyrinthe.initLaby();
        // Ajout de la flaque carrée
        this.hemoCarree = new Hemoglobine(8, 2, Finstere.CARRE, this);
        this.labyrinthe.setHemoglobine(8, 2, true);
        this.labyrinthe.setHemoglobine(9, 2, true);
        this.labyrinthe.setHemoglobine(8, 3, true);
        this.labyrinthe.setHemoglobine(9, 3, true);
        // Ajout de la flaque linéaire horizontale
        this.hemoLine = new Hemoglobine(4, 8, Finstere.LINEHORI, this);
        this.labyrinthe.setHemoglobine(4, 8, true);
        this.labyrinthe.setHemoglobine(5, 8, true);
        this.labyrinthe.setHemoglobine(6, 8, true);
        this.labyrinthe.setHemoglobine(7, 8, true);
        // Ajout des murs
        this.murs.add(new Mur(2, 2, this));
        this.labyrinthe.setMur(2, 2, true);
        this.murs.add(new Mur(12, 3, this));
        this.labyrinthe.setMur(12, 3, true);
        this.murs.add(new Mur(7, 4, this));
        this.labyrinthe.setMur(7, 4, true);
        this.murs.add(new Mur(8, 5, this));
        this.labyrinthe.setMur(8, 5, true);
        this.murs.add(new Mur(13, 5, this));
        this.labyrinthe.setMur(13, 5, true);
        this.murs.add(new Mur(6, 6, this));
        this.labyrinthe.setMur(6, 6, true);
        this.murs.add(new Mur(4, 7, this));
        this.labyrinthe.setMur(4, 7, true);
        this.murs.add(new Mur(12, 7, this));
        this.labyrinthe.setMur(12, 7, true);
        this.murs.add(new Mur(14, 8, this));
        this.labyrinthe.setMur(14, 8, true);
        this.murs.add(new Mur(5, 9, this));
        this.labyrinthe.setMur(5, 9, true);
        this.murs.add(new Mur(8, 9, this));
        this.labyrinthe.setMur(8, 9, true);
        //Ajout du monstre
        this.monstre = new Monstre(this);
        this.labyrinthe.setMonstre(0, 0, true);
    }
    
    public void afficherLaby() {
        System.out.println(this.labyrinthe);
    }
    
    public void afficherJoueurs() {
        for (Joueur joueur : this.joueurs) {
            System.out.println(joueur);
        }
    }
}
