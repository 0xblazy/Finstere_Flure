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
    
    /* Getters */
    public String getType() {
        return this.type;
    }
}
