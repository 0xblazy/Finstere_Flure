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
public class Finstere {
    /* Constantes pour les directions */
    public final static int HAUT = 1, BAS = -1, GAUCHE = 2, DROITE = -2;
    /* Constantes pour les types de flaques d'hemoglobine (nom de l'image sans
     * l'extension)
     */
    public final static String CARRE = "tachesang_carree",
            LINEVERT = "tachesang_lineaireH",
            LINEHORI = "tachesang_lineaireV";
    /* Constantes pour les couleurs des personnages */
    public final static String[] COULEURS = {"blue", "brown", "gray", "green",
        "purple", "red", "yellow"};
    /* Constantes pour les valeurs des Carte "une proie" et "deux proies */
    public final static int X = 11, XX = 12;
    /* Constantes pour les coordonnées des Personnage à l'extérieur, sortis, morts */
    public final static int[] EXTERIEUR = {16,10}, SORTI = {-1,0}, MORT = {15, -1};
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("==== FINSTERE FLURE ====");
        
        Partie partie = new Partie();
        partie.initPartie();
        partie.jouer();
    }  
}
