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
    private String type;
    
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
        
        if (this.type.equals(Finstere.CARRE)) {
            for (int i = 0 ; i < 2 ; i++) {
                zone.add(new int[] {this.x + i, this.y - 1});
                zone.add(new int[] {this.x + i, this.y + 2});
                zone.add(new int[] {this.x - 1, this.y + i});
                zone.add(new int[] {this.x + 2, this.y + i});
                zone.add(new int[] {this.x + i, this.y});
                zone.add(new int[] {this.x + i, this.y + 1});
            }
        } else if (this.type.equals(Finstere.LINEHORI)) {
            zone.add(new int[] {this.x - 1, this.y});
            for (int i = 0 ; i < 4 ; i++) {
                zone.add(new int[] {this.x + i, this.y - 1});
                zone.add(new int[] {this.x + i, this.y});
                zone.add(new int[] {this.x + i, this.y + 1});
            }
            zone.add(new int[] {this.x + 4, this.y});
        } else if (this.type.equals(Finstere.LINEVERT)) {
            zone.add(new int[] {this.x, this.y - 1});
            for (int j = 0 ; j < 4 ; j++) {
                zone.add(new int[] {this.x - 1, this.y + j});
                zone.add(new int[] {this.x, this.y + j});
                zone.add(new int[] {this.x + 1, this.y + j});
            }
            zone.add(new int[] {this.x, this.y + 4});
        }
        
        return zone;
    }
    
    /* Effectue la glissade jusqu'en (_x,_y) du Personnage passé en paramètre */
    public void glissade(Personnage _perso, int _dir, int _x, int _y) {
        if (_dir == Finstere.HAUT) {
            int y = _perso.getY() - 1;
            Mur mur = null;
            while (mur == null && y >= _y) {
                mur = this.partie.getMur(_x, y);
                y--;
            }
            if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY() + 1, 0);
                    _perso.pousserMur(mur, _dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() + 1, 1);
                }
            } else {
                _perso.deplacer(_x, _y, 1);
            }
        } else if (_dir == Finstere.BAS) {
            int y = _perso.getY() + 1;
            Mur mur = null;
            while (mur == null && y <= _y) {
                mur = this.partie.getMur(_x, y);
                y++;
            }
            if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY() - 1, 0);
                    _perso.pousserMur(mur, _dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() - 1, 1);
                }
            } else {
                _perso.deplacer(_x, _y, 1);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int x = _perso.getX() - 1;
            Mur mur = null;
            while (mur == null && x >= _x) {
                mur = this.partie.getMur(x, _y);
                x--;
            }
            if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX() + 1, mur.getY(), 0);
                    _perso.pousserMur(mur, _dir);
                } else {
                    _perso.deplacer(mur.getX() + 1, mur.getY(), 1);
                }
            } else {
                _perso.deplacer(_x, _y, 1);
            }
        } else if (_dir == Finstere.DROITE) {
            int x = _perso.getX() + 1;
            Mur mur = null;
            while (mur == null && x <= _x) {
                mur = this.partie.getMur(x, _y);
                x++;
            }
            if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX() - 1, mur.getY(), 0);
                    _perso.pousserMur(mur, _dir);
                } else {
                    _perso.deplacer(mur.getX() - 1, mur.getY(), 1);
                }
            } else {
                _perso.deplacer(_x, _y, 1);
            }
        }
    }
    
    /* Effectue la glissade du Mur passé en paramètre */
    public void glissadeMur(Mur _mur, int _dir, int _x, int _y) {
        if (_dir == Finstere.HAUT) {
            int y = _mur.getY() - 1;
            boolean isMur = false, isPerso = false;
            while (!isMur && !isPerso && y >= _y) {
                isMur = this.partie.getLabyrinthe().isMur(_x, y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(_x, y);
                y--;
            }
            if (isMur || isPerso) {
                _mur.setY(y + 2);
            } else {
                _mur.setY(_y);
            }
        } else if (_dir == Finstere.BAS) {
            int y = _mur.getY() + 1;
            boolean isMur = false, isPerso = false;
            while (!isMur && !isPerso && y <= _y) {
                isMur = this.partie.getLabyrinthe().isMur(_x, y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(_x, y);
                y++;
            }
            if (isMur || isPerso) {
                _mur.setY(y - 2);
            } else {
                _mur.setY(_y);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int x = _mur.getX() - 1;
            boolean isMur = false, isPerso = false;
            while (!isMur && !isPerso && x >= _x) {
                isMur = this.partie.getLabyrinthe().isMur(x, _y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(x, _y);
                x--;
            }
            if (isMur || isPerso) {
                _mur.setX(x + 2);
            } else {
                _mur.setX(_x);
            }
        } else if (_dir == Finstere.DROITE) {
            int x = _mur.getX() + 1;
            boolean isMur = false, isPerso = false;
            while (!isMur && !isPerso && x <= _x) {
                isMur = this.partie.getLabyrinthe().isMur(x, _y);
                isPerso = this.partie.getLabyrinthe().isPersonnage(x, _y);
                x++;
            }
            if (isMur || isPerso) {
                _mur.setX(x - 2);
            } else {
                _mur.setX(_x);
            }
        }
    }
    
    /* Retourne true si les coordonnées (_x,_y) sont sur la flaque */
    public boolean isHere(int _x, int _y) {
        if (this.type.equals(Finstere.CARRE)) {
            return (_x == this.x || _x == this.x + 1) 
                    && (_y == this.y || _y == this.y + 1);
        } else if (this.type.equals(Finstere.LINEHORI)) {
            return _y == this.y && _x >= this.x && _x <= this.x + 3;
        } else if (this.type.equals(Finstere.LINEVERT)) {
            return _y >= this.y && _y <= this.y + 3 && _x == this.x;
        }
        return false;
    }
    
    /* Getters */
    public String getType() {
        return this.type;
    }
}
