/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

/**
 *
 * @author nKBlaZy
 */
public class Mur extends Pion {
    /* Constructeur */
    public Mur(int _x, int _y, Partie _partie) {
        super(_x, _y, _partie);
    }
    
    /* Déplace le Mur dans la direction donnée */
    public void pousser(int _dir) {
        this.partie.getLabyrinthe().setMur(this.x, this.y, false);
        if(_dir == Finstere.HAUT) {
            this.y--;
        } else if(_dir == Finstere.BAS) {
            this.y++;
        } else if(_dir == Finstere.GAUCHE) {
            this.x--;
        } else if(_dir == Finstere.DROITE) {
            this.x++;
        }
        if ((this.x == 0 && this.y == 0) || (this.x == 15 && this.y == 10)) {
            this.partie.supprimerMur(this);
        } else {
            this.partie.getLabyrinthe().setMur(this.x, this.y, true);
        }
    }
    
    /* Retourne true si le Mur est poussable par un Personnage dans la direction
     * donnée
     */
    public boolean poussable(int _dir) {
        if (_dir == Finstere.HAUT) {
            if (this.y == 0) {
                return false;
            } else {
                return !this.partie.getLabyrinthe().isBlocked(this.x, this.y - 1)
                        && !this.partie.getLabyrinthe().isMonstre(this.x, this.y - 1)
                        && !this.partie.getLabyrinthe().isMur(this.x, this.y - 1)
                        && !this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1);
            }
        } else if (_dir == Finstere.BAS) {
            if (this.y == 10) {
                return false;
            } else {
                return !this.partie.getLabyrinthe().isBlocked(this.x, this.y + 1)
                        && !this.partie.getLabyrinthe().isMonstre(this.x, this.y + 1)
                        && !this.partie.getLabyrinthe().isMur(this.x, this.y + 1)
                        && !this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1);
            }
        } else if (_dir == Finstere.GAUCHE) {
            if (this.x == 0) {
                return false;
            } else {
                return !this.partie.getLabyrinthe().isBlocked(this.x - 1, this.y)
                        && !this.partie.getLabyrinthe().isMonstre(this.x - 1, this.y)
                        && !this.partie.getLabyrinthe().isMur(this.x - 1, this.y)
                        && !this.partie.getLabyrinthe().isPersonnage(this.x - 1, this.y);
            }
        } else if (_dir == Finstere.DROITE) {
            if (this.x == 15) {
                return false;
            } else {
                return !this.partie.getLabyrinthe().isBlocked(this.x + 1, this.y)
                        && !this.partie.getLabyrinthe().isMonstre(this.x + 1, this.y)
                        && !this.partie.getLabyrinthe().isMur(this.x + 1, this.y)
                        && !this.partie.getLabyrinthe().isPersonnage(this.x + 1, this.y);
            }
        }
        return false;
    }
}
