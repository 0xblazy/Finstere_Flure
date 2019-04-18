/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nKBlaZy
 */
public class Hemoglobine extends Pion {
    /* Type de flaque d'hémoglobine (nom de l'image sans l'extension) */
    private final String type;
    
    /* Constructeur */
    public Hemoglobine(int _x, int _y, String _type, Partie _partie) {
        super(_x, _y, _partie);
        this.type = _type;
    }
    
    /* Retourne la liste des coordonnées à partir desquelles un Pion peut 
     * interagir avec la flaque d'Hemoglobine
     */
    public List<int[]> zoneInteraction() {
        ArrayList<int[]> zone = new ArrayList<>();
        
        switch (this.type) {
            case Finstere.CARRE :
                for (int i = 0 ; i < 2 ; i++) {
                    zone.add(new int[] {this.x + i, this.y - 1});
                    zone.add(new int[] {this.x + i, this.y + 2});
                    zone.add(new int[] {this.x - 1, this.y + i});
                    zone.add(new int[] {this.x + 2, this.y + i});
                    zone.add(new int[] {this.x + i, this.y});
                    zone.add(new int[] {this.x + i, this.y + 1});
                }
                break;
            case Finstere.LINEHORI :
                zone.add(new int[] {this.x - 1, this.y});
                for (int i = 0 ; i < 4 ; i++) {
                    zone.add(new int[] {this.x + i, this.y - 1});
                    zone.add(new int[] {this.x + i, this.y});
                    zone.add(new int[] {this.x + i, this.y + 1});
                }   zone.add(new int[] {this.x + 4, this.y});
                break;
            case Finstere.LINEVERT :
                zone.add(new int[] {this.x, this.y - 1});
                for (int j = 0 ; j < 4 ; j++) {
                    zone.add(new int[] {this.x - 1, this.y + j});
                    zone.add(new int[] {this.x, this.y + j});
                    zone.add(new int[] {this.x + 1, this.y + j});
                }   zone.add(new int[] {this.x, this.y + 4});
                break;
            default :
                break;
        }
        
        return zone;
    }
    
    /* Effectue la glissade jusqu'en (_x,_y) du Personnage passé en paramètre */
    public void glissade(Personnage _perso, int _dir, int _x, int _y) {
        if (_dir == Finstere.HAUT) {
            int j = _perso.getY() - 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && j >= _y) {
                mur = this.partie.getMur(_x, j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(_x, j);
                j--;
            }
            
            if (isMonstre) {
                _perso.deplacer(_x, j + 2, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() + 1, 0);
                }
            } else {
                _perso.deplacer(_x, _y, 0);
            }
        } else if (_dir == Finstere.BAS) {
            int j = _perso.getY() + 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && j <= _y) {
                mur = this.partie.getMur(_x, j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(_x, j);
                j++;
            }
            
            if (isMonstre) {
                _perso.deplacer(_x, j - 2, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() - 1, 0);
                }
            } else {
                _perso.deplacer(_x, _y, 0);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int i = _perso.getX() - 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && i >= _x) {
                mur = this.partie.getMur(i, _y);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, _y);
                i--;
            }
            
            if (isMonstre) {
                _perso.deplacer(i + 2, _y, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX() + 1, mur.getY(), 0);
                }
            } else {
                _perso.deplacer(_x, _y, 0);
            }
        } else if (_dir == Finstere.DROITE) {
            int i = _perso.getX() + 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && i <= _x) {
                mur = this.partie.getMur(i, _y);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, _y);
                i++;
            }
            
            if (isMonstre) {
                _perso.deplacer(_x - 2, _y, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX() - 1, mur.getY(), 0);
                }
            } else {
                _perso.deplacer(_x, _y, 0);
            }
        }
    }
    
    /* Effectue la glissade du Mur passé en paramètre */
    public void glissadeMur(Mur _mur, int _dir, int _x, int _y) {
        if (_dir == Finstere.HAUT) {
            int j = _mur.getY() - 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && j >= _y) {
                isMur = this.partie.getLabyrinthe().isMur(_x, j);
                isPerso = this.partie.getLabyrinthe().isPersonnage(_x, j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(_x, j);
                j--;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setY(j + 2);
            } else {
                _mur.setY(_y);
            }
        } else if (_dir == Finstere.BAS) {
            int j = _mur.getY() + 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && j <= _y) {
                isMur = this.partie.getLabyrinthe().isMur(_x, j);
                isPerso = this.partie.getLabyrinthe().isPersonnage(_x, j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(_x, j);
                j++;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setY(j - 2);
            } else {
                _mur.setY(_y);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int i = _mur.getX() - 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && i >= _x) {
                isMur = this.partie.getLabyrinthe().isMur(i, _y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(i, _y);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, _y);
                i--;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setX(i + 2);
            } else {
                _mur.setX(_x);
            }
        } else if (_dir == Finstere.DROITE) {
            int i = _mur.getX() + 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && i <= _x) {
                isMur = this.partie.getLabyrinthe().isMur(i, _y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(i, _y);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, _y);
                i++;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setX(i - 2);
            } else {
                _mur.setX(_x);
            }
        }
    }
    
    /* Retourne true si les coordonnées (_x,_y) sont sur la flaque */
    public boolean isHere(int _x, int _y) {
        switch (this.type) {
            case Finstere.CARRE :
                return (_x == this.x || _x == this.x + 1)
                        && (_y == this.y || _y == this.y + 1);
            case Finstere.LINEHORI :
                return _y == this.y && _x >= this.x && _x <= this.x + 3;
            case Finstere.LINEVERT :
                return _y >= this.y && _y <= this.y + 3 && _x == this.x;
            default :
                return false;
        }
    }
    
    /* Getters */
    public String getType() {
        return this.type;
    }
}
