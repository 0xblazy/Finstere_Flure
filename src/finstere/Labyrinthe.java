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
public class Labyrinthe {
    /* Tableau à 2 dimensions représentant le labyrinthe */
    private Case[][] labyrinthe;
    /* Partie du Labyrinthe */
    private Partie partie;
    
    /* Constructeur */
    public Labyrinthe(Partie _partie) {
        this.partie = _partie;
    }
    
    /* Génère le labyrinthe */
    public void initLaby() {
        this.labyrinthe = new Case[11][16];
        for (int j = 0 ; j < this.labyrinthe.length ; j++) {
            for (int i = 0 ; i < this.labyrinthe[0].length ; i++) {
                Map<Integer, int[]> tp = new HashMap<>();
                if (j == 0) {
                    if (i == 0) {
                        tp.put(Finstere.HAUT, new int[]{15,10});
                        tp.put(Finstere.GAUCHE, new int[]{15,10});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 11) {
                        tp.put(Finstere.HAUT, new int[]{4, 10});
                        tp.put(Finstere.DROITE, new int[]{4, 10});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else if (i > 11) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else {
                        tp.put(Finstere.HAUT, new int[]{this.labyrinthe[0].length - 1 - i,10});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    }
                } else if (j == 1) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,9});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 12) {
                        tp.put(Finstere.HAUT, new int[]{3,9});
                        tp.put(Finstere.DROITE, new int[]{3,9});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else if (i > 12) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 2) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,8});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 13) {
                        tp.put(Finstere.HAUT, new int[]{2,8});
                        tp.put(Finstere.DROITE, new int[]{2,8});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else if (i > 13) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 3) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,7});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 14) {
                        tp.put(Finstere.HAUT, new int[]{1,7});
                        tp.put(Finstere.DROITE, new int[]{1,7});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else if (i > 14) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 4) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,6});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 14) {
                        tp.put(Finstere.HAUT, new int[]{0,6});
                        tp.put(Finstere.DROITE, new int[]{0,6});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 5) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,5});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,5});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 6) {
                    if (i == 0) {
                        tp.put(Finstere.GAUCHE, new int[]{15,4});
                        tp.put(Finstere.BAS, new int[]{15,4});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,4});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 7) {
                    if (i < 1) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else if (i == 1) {
                        tp.put(Finstere.GAUCHE, new int[]{14,3});
                        tp.put(Finstere.BAS, new int[]{14,3});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,3});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 8) {
                    if (i < 2) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else if (i == 2) {
                        tp.put(Finstere.GAUCHE, new int[]{13,2});
                        tp.put(Finstere.BAS, new int[]{13,2});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,2});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else if (j == 9) {
                    if (i < 3) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else if (i == 3) {
                        tp.put(Finstere.GAUCHE, new int[]{13,1});
                        tp.put(Finstere.BAS, new int[]{13,1});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,1});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        this.labyrinthe[j][i] = new Case(i,j, this.partie);
                    }
                } else {
                    if (i < 4) {
                        this.labyrinthe[j][i] = new Case(i,j,true, this.partie);
                    } else if (i == 4) {
                        tp.put(Finstere.GAUCHE, new int[]{12,0});
                        tp.put(Finstere.BAS, new int[]{12,0});
                        this.labyrinthe[j][i] = new Case(i, j, tp, this.partie);
                    } else if (i == 15) {
                        tp.put(Finstere.DROITE, new int[]{0,0});
                        tp.put(Finstere.BAS, new int[]{0,0});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    } else {
                        tp.put(Finstere.BAS, new int[]{-(i + 1 - this.labyrinthe[0].length),0});
                        this.labyrinthe[j][i] = new Case(i,j,tp, this.partie);
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        String s = "";
        
        for (int j = 0 ; j < this.labyrinthe.length ; j++) {
            for (int i = 0 ; i < this.labyrinthe[0].length ; i++) {
                //s += this.labyrinthe[j][i] + "\n";
                s += "|" + this.labyrinthe[j][i];
            }
            if ( j < this.labyrinthe.length - 1) {
                s += "|\n";
            } else {
                s += "|";
            }
        }
        
        return s;
    }
    
    /* Vérifie si la Case adjacente à la Case (x,y) dans une direction donnée 
     * est un Mur, une flaque d'Hemoglobine ou non
     */
    public boolean obstacleAdj(int _x, int _y, int _dir) {
        if (_dir == Finstere.HAUT && _y > 0) {
            return this.labyrinthe[_y - 1][_x].isMur() ||
                    this.labyrinthe[_y - 1][_x].isHemoglobine();
        } else if (_dir == Finstere.BAS && _y < 10) {
            return this.labyrinthe[_y + 1][_x].isMur() ||
                    this.labyrinthe[_y + 1][_x].isHemoglobine();
        } else if (_dir == Finstere.GAUCHE && _x > 0) {
            return this.labyrinthe[_y][_x - 1].isMur() ||
                    this.labyrinthe[_y][_x - 1].isHemoglobine();
        } else if (_dir == Finstere.DROITE && _x < 15) {
            return this.labyrinthe[_y][_x + 1].isMur() ||
                    this.labyrinthe[_y ][_x + 1].isHemoglobine();
        }
        return false;
    }
    
    /* Setters pour définir si un élément est sur la Case (x,y) */
    public void setMur(int _x, int _y, boolean _mur) {
        this.labyrinthe[_y][_x].setMur(_mur);
    }

    public void setHemoglobine(int _x, int _y, boolean _hemoglobine) {
        this.labyrinthe[_y][_x].setHemoglobine(_hemoglobine);
    }

    public void setPersonnage(int _x, int _y, boolean _personnage) {
         this.labyrinthe[_y][_x].setPersonnage(_personnage);
    }

    public void setMonstre(int _x, int _y, boolean _monstre) {
         this.labyrinthe[_y][_x].setMonstre(_monstre);
    }
    
    /* Getters pour la Case (x,y) */
    public boolean isBlocked(int _x, int _y) {
        return this.labyrinthe[_y][_x].isBlocked();
    }
    
    public boolean isHemoglobine(int _x, int _y) {
        return this.labyrinthe[_y][_x].isHemoglobine();
    }
    
    public boolean isMonstre(int _x, int _y) {
        return this.labyrinthe[_y][_x].isMonstre();
    }
    
    public boolean isMur(int _x, int _y) {
        return this.labyrinthe[_y][_x].isMur();
    }
    
    public boolean isPersonnage(int _x, int _y) {
        return this.labyrinthe[_y][_x].isPersonnage();
    }
    
    public boolean isLibre(int _x, int _y) {
        return !this.isBlocked(_x, _y) && !this.isHemoglobine(_x, _y) 
                && !this.isMonstre(_x, _y) && !this.isMur(_x, _y)
                && !this.isPersonnage(_x, _y);
                
    }
}
