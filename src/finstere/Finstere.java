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
    /* Constantes des directions */
    public final static int HAUT = 1, BAS = 2, GAUCHE = 3, DROITE = 4;
    /* Constantes des types de flaques d'hemoglobine (nom de l'image sans
     * l'extension)
     */
    public final static String CARRE = "tachesang_carree",
            LINEVERT = "tachesang_lineaireH",
            LINEHORI = "tachesang_lineaireV";
    /* Constantes pour les couleurs des personnages */
    public final static String[] COULEURS = {"blue", "brown", "gray", "green",
        "purple", "red", "yellow"};
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("==== FINSTERE FLURE ====");
        
        Partie partie = new Partie();
        partie.initPartie();
        partie.jouer();
        partie.test();
    }
    
}
