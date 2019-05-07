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
    /* Tableau à 2 dimensions représentant le Labyrinthe */
    private Case[][] labyrinthe;
    /* Partie du Labyrinthe */
    private Partie partie;
    
    /* Constructeur */
    public Labyrinthe(Partie _partie) {
        this.partie = _partie;
    }
    
    /* Génère le Labyrinthe */
    public void initLaby() {
        this.labyrinthe = new Case[11][16];
        
        /* Définition des Cases de téléportation pour le Monstre */
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
                    } else if (i == 15) {
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
                        tp.put(Finstere.GAUCHE, new int[]{12,1});
                        tp.put(Finstere.BAS, new int[]{12,1});
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
                        tp.put(Finstere.GAUCHE, new int[]{11,0});
                        tp.put(Finstere.BAS, new int[]{11,0});
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
    
    /* Vérifie si il y a un obstacle entre la Case (_x,_y) et le Personnage en 
     * (_xP,_yP)
     */
    public boolean obstacle(int _x, int _y, int _xP, int _yP) {
        boolean obstacle = false;
        
        if (_x == _xP) {
            if (_y < _yP) {
                int j = _yP - 1;
                while (!obstacle && j > _y) {
                    obstacle = this.labyrinthe[j][_x].isHemoglobine() 
                            || this.labyrinthe[j][_x].isMonstre() 
                            || this.labyrinthe[j][_x].isMur();
                    j--;
                }
            } else if (_y > _yP) {
                int j = _yP + 1;
                while (!obstacle && j < _y) {
                    obstacle = this.labyrinthe[j][_x].isHemoglobine() 
                            || this.labyrinthe[j][_x].isMonstre() 
                            || this.labyrinthe[j][_x].isMur();
                    j++;
                }
            }
        } else if (_y == _yP) {
            if (_x < _xP) {
                int i = _xP - 1;
                while (!obstacle && i > _x) {
                    obstacle = this.labyrinthe[_y][i].isHemoglobine() 
                            || this.labyrinthe[_y][i].isMonstre() 
                            || this.labyrinthe[_y][i].isMur();
                    i--;
                }
            } else if (_x > _xP) {
                int i = _xP + 1;
                while (!obstacle && i < _x) {
                    obstacle = this.labyrinthe[_y][i].isHemoglobine() 
                            || this.labyrinthe[_y][i].isMonstre() 
                            || this.labyrinthe[_y][i].isMur();
                    i++;
                }
            }
        }
        
        return obstacle;
    }
    
    /* Retourne le Labyrinthe sous forme d'une chaîne de caractère */
    @Override
    public String toString() {
        String s = "";
        
        for (int j = 0 ; j < this.labyrinthe.length ; j++) {
            for (int i = 0 ; i < this.labyrinthe[0].length ; i++) {
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
    
    /* Setters pour définir si un élément est sur la Case (_x,_y) */
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
    
    /* Getters pour la Case (_x,_y) */
    public Map<Integer, int[]> getTp(int _x, int _y) {
        return this.labyrinthe[_y][_x].getTp();
    }
    
    public boolean isBlocked(int _x, int _y) {
        return this.labyrinthe[_y][_x].isBlocked();
    }
    
    public boolean isBordure(int _x, int _y) {
        return this.labyrinthe[_y][_x].isBordure();
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
    
    public boolean isDisponible(int _x, int _y) {
        return !this.isBlocked(_x, _y) && !this.isHemoglobine(_x, _y) 
                && !this.isMonstre(_x, _y) && !this.isMur(_x, _y);                
    }
    
    public boolean isLibre(int _x, int _y, int _dir) {
        boolean libre = !this.isBlocked(_x, _y) && !this.isMonstre(_x, _y) 
                && !this.isPersonnage(_x, _y) && !this.isHemoglobine(_x, _y);
        
        Mur mur = this.partie.getMur(_x, _y);
        if (mur != null && libre) {
            libre = mur.poussable(_dir);
        }
        
        return libre;
    }
    
    public boolean isObstacle(int _x, int _y) {
        if (_x > 15 || _x < 0 || _y > 10 || _y < 0) {
            return false;
        } else {
            return this.isMur(_x, _y) || this.isHemoglobine(_x, _y) 
                || this.isMonstre(_x, _y);
        }
    }
}
