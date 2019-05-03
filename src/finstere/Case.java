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
public class Case {
    /* Coordonnées de la Case */
    private final int x,y;
    /* Vrai si la case est en bordure */
    private final boolean bordure;
    /* Vrai si il s'agit d'une case qui ne fait pas partie du labyrinthe */
    private final boolean blocked;
    /* Coordonnées des cases où sera téléporté le monstre 
     * Integer => Finstere.HAUT .BAS ...
     */
    private final Map<Integer, int[]> tp;
    /* Vrai si un mur, une plaque d'hémoglobine, un personnage ou le monstre est
     * sur la case
     */
    private boolean mur, hemoglobine, personnage, monstre;
    /* Partie de la Case */
    private Partie partie;
    
    /* Constructeur principal */
    public Case(int _x, int _y, boolean _bordure, Map<Integer, int[]> _tp, boolean _blocked, Partie _partie) {
        this.x = _x;
        this.y = _y;
        this.bordure = _bordure;
        this.tp = _tp;
        this.blocked = _blocked;
        this.partie = _partie;
        this.mur = false;
        this.hemoglobine = false;
        this.personnage = false;
        this.monstre = false;
    }
    
    /* Constructeur pour une bordure */
    public Case(int _x, int _y, Map<Integer, int[]> _tp, Partie _partie) {
        this(_x, _y, true, _tp, false, _partie);
    }
    
    /* Constructeur pour une case normale */
    public Case(int _x, int _y, Partie _partie) {
        this(_x, _y, false, new HashMap<>(), false, _partie);
    }
    
    /* Constructeur d'une case qui ne fait pas partie du Labyrinthe */
    public Case(int _x, int _y, boolean _blocked, Partie _partie) {
        this(_x, _y, false, new HashMap<>(), _blocked, _partie);
    }
    
    /* Retourne la Case sous forme d'une chaîne de caractère en fonction de son 
     * état
     */
    @Override
    public String toString() {
        if (this.blocked) {
            return "###";
        } else if (this.personnage) {
            Personnage perso = this.partie.getPersonnage(this.x, this.y);
            return perso.shortString();
        } else if (this.monstre) {
            return "" + this.partie.getMonstre();
        } else if (this.mur) {
            return " W ";
        } else if (this.hemoglobine) {
            return " H ";
        } else {
            return "   ";
        }
    }

    /* Setters */
    public void setMur(boolean _mur) {
        this.mur = _mur;
    }

    public void setHemoglobine(boolean _hemoglobine) {
        this.hemoglobine = _hemoglobine;
    }

    public void setPersonnage(boolean _personnage) {
        this.personnage = _personnage;
    }

    public void setMonstre(boolean _monstre) {
        this.monstre = _monstre;
    }
    
    /* Getters */
    public boolean isBordure() {
        return this.bordure;
    }
    
    public boolean isBlocked() {
        return this.blocked;
    }

    public boolean isHemoglobine() {
        return this.hemoglobine;
    }

    public boolean isMonstre() {
        return this.monstre;
    }

    public boolean isMur() {
        return this.mur;
    }

    public boolean isPersonnage() {
        return this.personnage;
    }

    public Map<Integer, int[]> getTp() {
        return this.tp;
    }
}
