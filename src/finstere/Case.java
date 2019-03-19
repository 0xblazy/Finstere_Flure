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
    
    /* Constructeur principal */
    public Case(int _x, int _y, boolean _bordure, Map<Integer, int[]> _tp, boolean _blocked) {
        this.x = _x;
        this.y = _y;
        this.bordure = _bordure;
        this.tp = _tp;
        this.blocked = _blocked;
        this.mur = false;
        this.hemoglobine = false;
        this.personnage = false;
        this.monstre = false;
    }
    
    /* Constructeur pour une bordure */
    public Case(int _x, int _y, Map<Integer, int[]> _tp) {
        this(_x, _y, true, _tp, false);
    }
    
    /* Constructeur pour une case normale */
    public Case(int _x, int _y) {
        this(_x, _y, false, new HashMap<>(), false);
    }
    
    /* Constructeur d'une case qui ne fait pas partie du labyrinthe */
    public Case(int _x, int _y, boolean _blocked) {
        this(_x, _y, false, new HashMap<>(), _blocked);
    }
    
    @Override
    public String toString() {
        String s = "Case (" + this.x + "," + this.y + ")";

        if (this.mur) {
            s += " [MUR]";
        } else if (this.hemoglobine) {
            s += " [HÉMOGLOBINE]";
        } else if (this.personnage) {
            s += " [PERSONNAGE]";
        } else if (this.monstre) {
            s += " [MONSTRE]";
        }
        
        if (this.blocked) {
            s += " [BLOCKED]";
        } else if (this.bordure) {
            s += " [BORDURE] : ";
            for (Map.Entry<Integer, int[]> entry : tp.entrySet()) {
                s += entry.getKey() + " => (" + entry.getValue()[0] + "," 
                        + entry.getValue()[1] + ") ";
            }
        } 
        
        return s;
    }

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
}
