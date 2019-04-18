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
        Hemoglobine hemo;
        switch (_dir) {
            case Finstere.HAUT :
                hemo = this.partie.getHemoglobine(this.x, this.y - 1);
                if (hemo != null) {
                    hemo.glissadeMur(this,_dir, this.x, hemo.getY() - 1);
                } else {
                    this.y--;
                }
                break;
            case Finstere.BAS :
                hemo = this.partie.getHemoglobine(this.x, this.y + 1);
                if (hemo != null) {
                    switch (hemo.getType()) {
                        case Finstere.CARRE :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 2);
                            break;
                        case Finstere.LINEHORI :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 1);
                            break;
                        case Finstere.LINEVERT :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 4);
                            break;
                        default :
                            break;
                    }
                } else {
                    this.y++;
                }
                break;
            case Finstere.GAUCHE :
                hemo = this.partie.getHemoglobine(this.x - 1, this.y);
                if (hemo != null) {
                    hemo.glissadeMur(this,_dir, hemo.getX() - 1, this.y);
                } else {
                    this.x--;
                }
                break;
            case Finstere.DROITE :
                hemo = this.partie.getHemoglobine(this.x + 1, this.y);
                if (hemo != null) {
                    switch (hemo.getType()) {
                        case Finstere.CARRE :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 2, this.y);
                            break;
                        case Finstere.LINEHORI :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 4, this.y);
                            break;
                        case Finstere.LINEVERT :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 1, this.y);
                            break;
                        default :
                            break;
                    }
                } else {
                    this.x++;
                }
                break;
            default :
                break;
        }
        
        if ((this.x == 0 && this.y == 0) || (this.x == 15 && this.y == 10)) {
            this.partie.supprimerMur(this);
        } else {
            this.partie.getLabyrinthe().setMur(this.x, this.y, true);
        }
    }
    
    /* Déplace le Mur dans la direction donnée lorsqu'il est poussé par le Monstre */
    public Personnage pousserMonstre(int _dir) {
        this.partie.getLabyrinthe().setMur(this.x, this.y, false);
        
        /* Récupère le Mur, le Personnage ou la flaque d'Hemoglobine qui peut 
         * être derrière ce Mur
         */
        Mur mur = null;
        Personnage perso = null, persoMort = null;
        Hemoglobine hemo = null;
        switch (_dir) {
            case Finstere.HAUT :
                if (this.y > 0) {
                    mur = this.partie.getMur(this.x, this.y - 1);
                    perso = this.partie.getPersonnage(this.x, this.y - 1);
                    hemo = this.partie.getHemoglobine(this.x, this.y - 1);
                }
                this.y--;
                break;
            case Finstere.BAS :
                if (this.y < 10) {
                    mur = this.partie.getMur(this.x, this.y + 1);
                    perso = this.partie.getPersonnage(this.x, this.y + 1);
                    hemo = this.partie.getHemoglobine(this.x, this.y + 1);
                }
                this.y++;
                break;
            case Finstere.GAUCHE :
                if (this.x > 0) {
                    mur = this.partie.getMur(this.x - 1, this.y);
                    perso = this.partie.getPersonnage(this.x - 1, this.y);
                    hemo = this.partie.getHemoglobine(this.x - 1, this.y);
                }
                this.x--;
                break;
            case Finstere.DROITE :
                if (this.x < 15) {
                    mur = this.partie.getMur(this.x + 1, this.y);
                    perso = this.partie.getPersonnage(this.x + 1, this.y);
                    hemo = this.partie.getHemoglobine(this.x + 1, this.y);
                }
                this.x++;
                break;
            default :
                break;
        }
        
        /* Appel récursif si il y a un Mur derrière celui-ci */
        if (mur != null) {
            persoMort = mur.pousserMonstre(_dir);
        } else if (perso == null && hemo != null) {
            switch (_dir) {
                case Finstere.HAUT :
                    hemo.glissadeMur(this,_dir, this.x, hemo.getY() - 1);
                    break;
                case Finstere.BAS :
                    switch (hemo.getType()) {
                        case Finstere.CARRE :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 2);
                            break;
                        case Finstere.LINEHORI :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 1);
                            break;
                        case Finstere.LINEVERT :
                            hemo.glissadeMur(this,_dir, this.x, hemo.getY() + 4);
                            break;
                        default :
                            break;
                    }
                    break;
                case Finstere.GAUCHE :
                    hemo.glissadeMur(this,_dir, hemo.getX() - 1, this.y);
                    break;
                case Finstere.DROITE :
                    switch (hemo.getType()) {
                        case Finstere.CARRE :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 2, this.y);
                            break;
                        case Finstere.LINEHORI :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 4, this.y);
                            break;
                        case Finstere.LINEVERT :
                            hemo.glissadeMur(this,_dir, hemo.getX() + 1, this.y);
                            break;
                        default :
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        
        /* Déplacement ou suppression de ce Mur */
        if (this.x < 0 || this.x > 15 || this.y < 0 || this.y > 10 
                || (this.x == 0 && this.y == 0)
                || (this.x == 15 && this.y == 10)) {
            this.partie.supprimerMur(this);
        } else if (this.partie.getLabyrinthe().isBlocked(this.x, this.y)) {
            this.partie.supprimerMur(this);
        } else {
            this.partie.getLabyrinthe().setMur(this.x, this.y, true);
        }
        
        /* Si il y a un Personnage derrière le Mur, le supprime ou le déplace
         * Appel récursif si il y a un Mur ou un Personnage derrière le 
         * Personnage
         */
        while (perso != null) {
            switch (_dir) {
                case Finstere.HAUT :
                    if (perso.getY() == 0) {
                        return perso;
                    } else if (this.partie.getLabyrinthe().isBlocked(perso.getX(),
                            perso.getY() - 1)) {
                        return perso;
                    } else {
                        Personnage p = this.partie.getPersonnage(perso.getX(),
                                perso.getY() - 1);
                        Hemoglobine h = this.partie.getHemoglobine(perso.getX(),
                                perso.getY() - 1);
                        Mur m = this.partie.getMur(perso.getX(), perso.getY() - 1);
                        if (m != null) persoMort = m.pousserMonstre(_dir);
                        if (h != null) {
                            perso.glisser(h, _dir, 0);
                        } else {
                            perso.deplacer(perso.getX(), perso.getY() - 1, 0);
                        }
                        perso = p;
                    }
                    break;
                case Finstere.BAS :
                    if (perso.getY() == 10) {
                        return perso;
                    } else if (this.partie.getLabyrinthe().isBlocked(perso.getX(),
                            perso.getY() + 1)) {
                        return perso;
                    } else {
                        Personnage p = this.partie.getPersonnage(perso.getX(),
                                perso.getY() + 1);
                        perso.deplacer(perso.getX(), perso.getY() + 1, 0);
                        Mur m = this.partie.getMur(perso.getX(), perso.getY());
                        if (m != null) persoMort = m.pousserMonstre(_dir);
                        perso = p;
                    }
                    break;
                case Finstere.GAUCHE :
                    if (perso.getX() == 0) {
                        return perso;
                    } else if (this.partie.getLabyrinthe().isBlocked(perso.getX() - 1,
                            perso.getY())) {
                        return perso;
                    } else {
                        Personnage p = this.partie.getPersonnage(perso.getX() - 1,
                                perso.getY());
                        perso.deplacer(perso.getX() - 1, perso.getY(), 0);
                        Mur m = this.partie.getMur(perso.getX(), perso.getY());
                        if (m != null) persoMort = m.pousserMonstre(_dir);
                        perso = p;
                    }
                    break;
                case Finstere.DROITE :
                    if (perso.getX() == 15) {
                        return perso;
                    } else if (this.partie.getLabyrinthe().isBlocked(perso.getX() + 1,
                            perso.getY())) {
                        return perso;
                    } else  {
                        Personnage p = this.partie.getPersonnage(perso.getX() + 1,
                                perso.getY());
                        perso.deplacer(perso.getX() + 1, perso.getY(), 0);
                        Mur m = this.partie.getMur(perso.getX(), perso.getY());
                        if (m != null) persoMort = m.pousserMonstre(_dir);
                        perso = p;
                    }
                    break;
                default :
                    break;
            }
        }
        
        return persoMort;
    }
    
    /* Retourne true si le Mur est poussable par un Personnage dans la direction
     * donnée
     */
    public boolean poussable(int _dir) {
        switch (_dir) {
            case Finstere.HAUT :
                if (this.y == 0) {
                    return false;
                } else {
                    return !this.partie.getLabyrinthe().isBlocked(this.x, this.y - 1)
                            && !this.partie.getLabyrinthe().isMonstre(this.x, this.y - 1)
                            && !this.partie.getLabyrinthe().isMur(this.x, this.y - 1)
                            && !this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1);
                }
            case Finstere.BAS :
                if (this.y == 10) {
                    return false;
                } else {
                    return !this.partie.getLabyrinthe().isBlocked(this.x, this.y + 1)
                            && !this.partie.getLabyrinthe().isMonstre(this.x, this.y + 1)
                            && !this.partie.getLabyrinthe().isMur(this.x, this.y + 1)
                            && !this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1);
                }
            case Finstere.GAUCHE :
                if (this.x == 0) {
                    return false;
                } else {
                    return !this.partie.getLabyrinthe().isBlocked(this.x - 1, this.y)
                            && !this.partie.getLabyrinthe().isMonstre(this.x - 1, this.y)
                            && !this.partie.getLabyrinthe().isMur(this.x - 1, this.y)
                            && !this.partie.getLabyrinthe().isPersonnage(this.x - 1, this.y);
                }
            case Finstere.DROITE :
                if (this.x == 15) {
                    return false;
                } else {
                    return !this.partie.getLabyrinthe().isBlocked(this.x + 1, this.y)
                            && !this.partie.getLabyrinthe().isMonstre(this.x + 1, this.y)
                            && !this.partie.getLabyrinthe().isMur(this.x + 1, this.y)
                            && !this.partie.getLabyrinthe().isPersonnage(this.x + 1, this.y);
                }
            default :
                return false;
        }
    }
}
