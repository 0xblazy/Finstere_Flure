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
    
    /* Retourne le Personnage sous la forme d'une chaine de 3 caractères */
    public String shortString() {
        String s = "";
        
        /* Ajoute l'intiale de la couleur à la chaîne (P = Violet) */
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
        
        /* Ajoute les pm et la face du Personnage à la chaîne */
        if (this.faceClair) {
            s += this.pmC + "C";
        } else {
            s += this.pmF + "F";
        }
        
        return s;
    }
    
    /* Déplace le Personnage aux coordonnées _x, _y */
    public boolean deplacer(int _x, int _y, int _pm) {
        /* Si le Personnage est déjà sur le Labyrinthe */
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
        
        /* Pour chaque Case possible */
        for (int[] c : cases) {
            
            /* Si la Case est libre */
            if (super.partie.getLabyrinthe().isLibre(c[0], c[1])) {
                Action action = this.actionDeplacer(c[0], c[1]);
                
                /* Si l'Action n'est pas null (est donc réalisable) */
                if (action != null) {
                    actions.put(key, action);
                    key++;
                }
            }
        }
        
        /* Si le Personnage est déjà sur le Labyrinthe, on ajoute une Action 
         * pour terminer les Actions
         */
        if (super.x < 16) {
            actions.put(key, new Action("Terminer les Actions", this.getClass(),
                "finAction", new Object[] {}));
        }
        
        return actions;
    }
    
    /* Retourne les coordonnées des Case sur lequel le Personnage peut 
     * possiblement aller (cercle de rayon pm)
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
    
    /* Génère l'Action deplacer en fonction des contraintes du Labyrinthe
     * Retourne null si l'Action n'est pas réalisable
     */
    private Action actionDeplacer(int _x, int _y) {
        int pmA = Math.abs(_x - super.x) + Math.abs(_y - super.y);
                
        /* Défini la direction où vérifier la présence d'obstacle */
        int dir = Finstere.HAUT;
        int diff = Math.abs(_y - super.y);
        if (_y < super.y && Math.abs(_y - super.y) >= diff) {
            diff = Math.abs(_y - super.y);
            dir = Finstere.BAS;
        }
        if (_x > super.x && Math.abs(_x - super.x) >= diff) {
            diff = Math.abs(_x - super.x);
            dir = Finstere.GAUCHE;
        }
        if (_x < super.x && Math.abs(_x - super.x) >= diff) {
            diff = Math.abs(_x - super.x);
            dir = Finstere.DROITE;
        }
        
        /* Si il y a un obstacle entre le Personnage et la Case */
        if (super.partie.getLabyrinthe().obstacleAdj(_x, _y, dir)) {
            
            /* Si le Personnage est aligné verticalement ou horizontalement à la
             * Case
             */
            if (_x == super.x || _y == super.y) {
                
                /* Si le Personnage a assez de pm pour contourner l'obstacle */
                if (pmA + 2 <= this.pm) {
                    return new Action("Se Déplacer en (" + _x + "," + _y + ")", 
                        this.getClass(), "deplacer", new Object[] {_x, _y, pmA + 2});
                } else {
                    return null;
                }
            } else {
                 return new Action("Se Déplacer en (" + _x + "," + _y + ")", 
                    this.getClass(), "deplacer", new Object[] {_x, _y, pmA});
            }
        } else {
            return new Action("Se Déplacer en (" + _x + "," + _y + ")", 
                this.getClass(), "deplacer", new Object[] {_x, _y, pmA});
        }
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
    
    /* Retourne le Personnage sous forme d'une chaîne de caractère */
    @Override
    public String toString() {
        String s = "Personnage ";
        
        /* Ajout de la couleur à la chaîne */
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
        
        /* Ajout des pm et de la face à la chaîne */
        if (this.faceClair) {
            s += " " + this.pmC + " Face claire ";
        } else {
            s += " " + this.pmF + " Face foncée ";
        }
        
        /* Ajout des coordonnées du Personnage à la chaîne */
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
