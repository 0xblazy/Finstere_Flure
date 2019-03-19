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
    private int x,y;
    /* Vrai si la case est en bordure */
    private boolean bordure;
    /* Vrai si il s'agit d'une case qui ne fait pas partie de labyrinthe */
    private boolean blocked;
    /* Coordonnées des cases où sera téléporté le monstre 
     * Integer => Finstere.HAUT .BAS...
     */
    private Map<Integer, int[]> tp;
    
    public Case(int _x, int _y, boolean _bordure, Map<Integer, int[]> _tp, boolean _blocked) {
        this.x = _x;
        this.y = _y;
        this.bordure = _bordure;
        this.tp = _tp;
        this.blocked = _blocked;
    }
    
    public Case(int _x, int _y, Map<Integer, int[]> _tp) {
        this(_x, _y, true, _tp, false);
    }
    
    public Case(int _x, int _y) {
        this(_x, _y, false, new HashMap<>(), false);
    }
    
    public Case(int _x, int _y, boolean _blocked) {
        this(_x, _y, false, new HashMap<>(), _blocked);
    }
    
    @Override
    public String toString() {
        String s = "Case (" + this.x + "," + this.y + ")";
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
}
