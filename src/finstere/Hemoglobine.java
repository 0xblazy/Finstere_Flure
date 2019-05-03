/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author nKBlaZy
 */
public class Hemoglobine extends Pion {

    /* Type de flaque d'Hemoglobine (nom de l'image sans l'extension) */
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

        if (this.type.equals(Finstere.CARRE)) {
            for (int i = 0; i < 2; i++) {
                zone.add(new int[]{this.x + i, this.y - 1});
                zone.add(new int[]{this.x + i, this.y + 2});
                zone.add(new int[]{this.x - 1, this.y + i});
                zone.add(new int[]{this.x + 2, this.y + i});
                zone.add(new int[]{this.x + i, this.y});
                zone.add(new int[]{this.x + i, this.y + 1});
            }
        } else if (this.type.equals(Finstere.LINEHORI)) {
            zone.add(new int[]{this.x - 1, this.y});
            for (int i = 0; i < 4; i++) {
                zone.add(new int[]{this.x + i, this.y - 1});
                zone.add(new int[]{this.x + i, this.y});
                zone.add(new int[]{this.x + i, this.y + 1});
            }
            zone.add(new int[]{this.x + 4, this.y});
        }

        return zone;
    }

    /* Effectue la glissade jusqu'en (_x,_y) du Personnage passé en paramètre */
    public void glissade(Personnage _perso, int _dir) {
        int[] arrivee = this.arrivee(_perso, _dir);
        
        if (_dir == Finstere.HAUT) {
            int j = _perso.getY() - 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && j >= arrivee[1]) {
                mur = this.partie.getMur(arrivee[0], j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(arrivee[0], j);
                j--;
            }

            if (isMonstre) {
                _perso.deplacer(arrivee[0], j + 2, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() + 1, 0);
                }
            } else {
                _perso.deplacer(arrivee[0], arrivee[1], 0);
            }
        } else if (_dir == Finstere.BAS) {
            int j = _perso.getY() + 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && j <= arrivee[1]) {
                mur = this.partie.getMur(arrivee[0], j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(arrivee[0], j);
                j++;
            }

            if (isMonstre) {
                _perso.deplacer(arrivee[0], j - 2, 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX(), mur.getY() - 1, 0);
                }
            } else {
                _perso.deplacer(arrivee[0], arrivee[1], 0);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int i = _perso.getX() - 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && i >= arrivee[0]) {
                mur = this.partie.getMur(i, arrivee[1]);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, arrivee[1]);
                i--;
            }

            if (isMonstre) {
                _perso.deplacer(i + 2, arrivee[1], 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX() + 1, mur.getY(), 0);
                }
            } else {
                _perso.deplacer(arrivee[0], arrivee[1], 0);
            }
        } else if (_dir == Finstere.DROITE) {
            int i = _perso.getX() + 1;
            Mur mur = null;
            boolean isMonstre = false;
            while (mur == null && !isMonstre && i <= arrivee[0]) {
                mur = this.partie.getMur(i, arrivee[1]);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, arrivee[1]);
                i++;
            }

            if (isMonstre) {
                _perso.deplacer(arrivee[0] - 2, arrivee[1], 0);
            } else if (mur != null) {
                if (mur.poussable(_dir)) {
                    _perso.deplacer(mur.getX(), mur.getY(), 0);
                    mur.pousser(_dir);
                } else {
                    _perso.deplacer(mur.getX() - 1, mur.getY(), 0);
                }
            } else {
                _perso.deplacer(arrivee[0], arrivee[1], 0);
            }
        }
    }

    /* Effectue la glissade du Mur passé en paramètre */
    public void glissadeMur(Mur _mur, int _dir) {
        int[] arrivee = this.arrivee(_mur, _dir);
        
        if (_dir == Finstere.HAUT) {
            int j = _mur.getY() - 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && j >= arrivee[1]) {
                isMur = this.partie.getLabyrinthe().isMur(arrivee[0], j);
                isPerso = this.partie.getLabyrinthe().isPersonnage(arrivee[0], j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(arrivee[0], j);
                j--;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setY(j + 2);
            } else {
                _mur.setY(arrivee[1]);
            }
        } else if (_dir == Finstere.BAS) {
            int j = _mur.getY() + 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && j <= arrivee[1]) {
                isMur = this.partie.getLabyrinthe().isMur(arrivee[0], j);
                isPerso = this.partie.getLabyrinthe().isPersonnage(arrivee[0], j);
                isMonstre = this.partie.getLabyrinthe().isMonstre(arrivee[0], j);
                j++;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setY(j - 2);
            } else {
                _mur.setY(arrivee[1]);
            }
        } else if (_dir == Finstere.GAUCHE) {
            int i = _mur.getX() - 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && i >= arrivee[0]) {
                isMur = this.partie.getLabyrinthe().isMur(i, arrivee[1]);
                isPerso = this.partie.getLabyrinthe().isPersonnage(i, arrivee[1]);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, arrivee[1]);
                i--;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setX(i + 2);
            } else {
                _mur.setX(arrivee[0]);
            }
        } else if (_dir == Finstere.DROITE) {
            int i = _mur.getX() + 1;
            boolean isMur = false, isPerso = false, isMonstre = false;
            while (!isMur && !isPerso && !isMonstre && i <= arrivee[0]) {
                isMur = this.partie.getLabyrinthe().isMur(i, arrivee[1]);
                isPerso = this.partie.getLabyrinthe().isPersonnage(i, arrivee[1]);
                isMonstre = this.partie.getLabyrinthe().isMonstre(i, arrivee[1]);
                i++;
            }
            if (isMur || isPerso || isMonstre) {
                _mur.setX(i - 2);
            } else {
                _mur.setX(arrivee[0]);
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
        }
        
        return false;
    }
    
    /* Détermine les coordonnées d'arrivée d'un Pion */
    private int[] arrivee(Pion _p, int _dir) {
        int[] arrivee = new int[2];
        
        if (_dir == Finstere.HAUT) {
            arrivee[0] = _p.getX();
            arrivee[1] = this.y - 1;
        } else if (_dir == Finstere.BAS) {
            if (this.type.equals(Finstere.CARRE)) {
                arrivee[0] = _p.getX();
                arrivee[1] = this.y + 2;
            } else if (this.type.equals(Finstere.LINEHORI)) {
                arrivee[0] = _p.getX();
                arrivee[1] = this.y + 1;
            }
        } else if (_dir == Finstere.GAUCHE) {
            arrivee[0] = this.x - 1;
            arrivee[1] = _p.getY();
        } else if (_dir == Finstere.DROITE) {
            if (this.type.equals(Finstere.CARRE)) {
                arrivee[0] = this.x + 2;
                arrivee[1] = _p.getY();
            } else if (this.type.equals(Finstere.LINEHORI)) {
                arrivee[0] = this.x + 4;
                arrivee[1] = _p.getY();
            }
        }
        
        return arrivee;
    }

    /* Getters */
    public String getType() {
        return this.type;
    }
    
    public ImageIcon getImageIcon() {
        return new ImageIcon(getClass().getResource("/img/" + this.type + ".png"));
    }
}
