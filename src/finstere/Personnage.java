/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nKBlaZy
 */
public class Personnage extends Pion {
    /* Points de mouvement face clair/foncé et pour le tour */
    private final int pmC, pmF;
    private int pm;
    /* Couleur du Personnage (blue, brown, gray, green, purple, red, yellow) */
    private final String couleur;
    /* Vrai si le Personnage est tourné face clair */
    private boolean faceClair;
    /* Vrai si le Personnage a été joué durant le tour */
    private boolean joue;
    /* Vrai si le Personnage est mort */
    private boolean rip;
    /* Vrai si le Personnage est sorti du Labyrinthe */
    private boolean exit;
    /* Classement du Personnage */
    private int classement;
    
    /* Constructeur */
    public Personnage(int _pmC, int _pmF, String _couleur, Partie _partie) {
        super(16, 10, _partie);
        this.pmC = _pmC;
        this.pmF = _pmF;
        this.pm = _pmC;
        this.couleur = _couleur;
        this.faceClair = true;
        this.joue = false;
        this.rip = false;
        this.exit = false;
        this.classement = 0;
    }
    
    /* Retourne le Personnage sous la forme d'une chaine de 4 caractères */
    public String shortString() {
        String s = "";
        
        if (this.couleur.equals(Finstere.COULEURS[0])) {
            s += "B";
        } else if (this.couleur.equals(Finstere.COULEURS[1])) {
            s += "M";
        } else if (this.couleur.equals(Finstere.COULEURS[2])) {
            s += "G";
        } else if (this.couleur.equals(Finstere.COULEURS[3])) {
            s += "V";
        } else if (this.couleur.equals(Finstere.COULEURS[4])) {
            s += "P";
        } else if (this.couleur.equals(Finstere.COULEURS[5])) {
            s += "R";
        } else if (this.couleur.equals(Finstere.COULEURS[6])) {
            s += "J";
        }
        
        if (this.faceClair) {
            s += this.pmC + "C";
        } else {
            s += this.pmF + "F";
        }
        
        return s;
    }
    
    /* Déplace le Personnage aux coordonnées _x, _y */
    public boolean deplacer(int _x, int _y, int _pm) {
        if (super.x < 16 && super.y <= 10) {
            super.partie.getLabyrinthe().setPersonnage(super.x, super.y, false);
        }
        this.pm -= _pm;
        super.x = _x;
        super.y = _y;
        super.partie.getLabyrinthe().setPersonnage(_x, _y, true);
        return true;
    }
    
    /* Termine l'action d'un Personnage */
    public boolean finAction() {
        this.faceClair = !this.faceClair;
        this.joue = true;
        return false;
    }
    
    /* Retourne la liste des Action que peut faire le Personnage */
    public Map<Integer,Action> getActions() {
        HashMap<Integer,Action> actions = new HashMap<>();
        ArrayList<int[]> cases = this.casesPossibles();
        int key = 1;
        for (int[] c : cases) {
            if (super.partie.getLabyrinthe().isLibre(c[0], c[1])) {
                
                int pmA = Math.abs(c[0] - super.x) + Math.abs(c[1] - super.y);
                
                /* Défini la direction où vérifier la présence d'obstacle */
                int dir = Finstere.HAUT;
                int diff = Math.abs(c[1] - super.y);
                if (c[1] < super.y && Math.abs(c[1] - super.y) >= diff) {
                    diff = Math.abs(c[1] - super.y);
                    dir = Finstere.BAS;
                }
                if (c[0] > super.x && Math.abs(c[0] - super.x) >= diff) {
                    diff = Math.abs(c[0] - super.x);
                    dir = Finstere.GAUCHE;
                }
                if (c[0] < super.x && Math.abs(c[0] - super.x) >= diff) {
                    diff = Math.abs(c[0] - super.x);
                    dir = Finstere.DROITE;
                }
                
                if (super.partie.getLabyrinthe().obstacleAdj(c[0], c[1], dir)) {
                    if (c[0] == super.x || c[1] == super.y) {
                        if (pmA + 2 <= this.pm) {
                            actions.put(key, new Action(
                                "Se Déplacer en (" + c[0] + "," + c[1] + ")", 
                                this.getClass(), "deplacer", 
                                new Object[] {c[0], c[1], pmA + 2}));
                            key++;
                        }
                    } else {
                        actions.put(key, new Action(
                            "Se Déplacer en (" + c[0] + "," + c[1] + ")", 
                            this.getClass(), "deplacer", 
                            new Object[] {c[0], c[1], pmA}));
                        key++;
                    }
                } else {
                    actions.put(key, new Action(
                        "Se Déplacer en (" + c[0] + "," + c[1] + ")", 
                        this.getClass(), "deplacer", 
                        new Object[] {c[0], c[1], pmA}));
                    key++;
                }
            }
        }
        
        if (super.x < 16) {
            actions.put(key, new Action("Terminer les Actions", this.getClass(),
                "finAction", new Object[] {}));
        }
        
        return actions;
    }
    
    /* Retourne les coordonnées des Case sur lequel le Personnage peut 
     * possiblement aller
     */
    private ArrayList<int[]> casesPossibles() {
        ArrayList<int[]> cases = new ArrayList<>();
        
        for (int j = -this.pm ; j <= this.pm ; j++) {
            for (int i = -this.pm ; i <= this.pm ; i++) {
                if (Math.abs(i) + Math.abs(j) <= this.pm 
                        && super.x + i < 16 && super.x + i > -1 
                        && super.y + j < 11 && super.y + j > -1) {
                    cases.add(new int[] {super.x + i, super.y + j});
                }
            }
        }        
        
        return cases;
    }
    
    /* Appeler en fin de tour pour retourner le Personnage si il n'a pas été 
     * joué et redonner les pm à celui-ci pour le tour suivant
     */
    public void finTour() {
        if (!this.joue) this.faceClair = !this.faceClair;
        if (this.faceClair) {
            this.pm = this.pmC;
        } else {
            this.pm = this.pmF;
        }
        this.joue = false;
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
        
        if (this.faceClair) {
            s += " " + this.pmC + " Face claire ";
        } else {
            s += " " + this.pmF + " Face foncée ";
        }
        
        if (super.x < 16) {
            s += "(" + super.x + "," + super.y + ")";
        } else {
            s += "(Extérieur)";
        }
           
        return s;
    }
    
    /* Getters */
    public boolean isJoue() {
        return this.joue;
    }

    public boolean isRip() {
        return this.rip;
    }

    public boolean isExit() {
        return this.exit;
    }
    
}
