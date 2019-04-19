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
        if (_dir == Finstere.HAUT) {
            hemo = this.partie.getHemoglobine(this.x, this.y - 1);
            if (hemo != null) {
                hemo.glissadeMur(this, _dir);
            } else {
                this.y--;
            }
        } else if (_dir == Finstere.BAS) {
            hemo = this.partie.getHemoglobine(this.x, this.y + 1);
            if (hemo != null) {
                hemo.glissadeMur(this, _dir);
            } else {
                this.y++;
            }
        } else if (_dir == Finstere.GAUCHE) {
            hemo = this.partie.getHemoglobine(this.x - 1, this.y);
            if (hemo != null) {
                hemo.glissadeMur(this, _dir);
            } else {
                this.x--;
            }
        } else if (_dir == Finstere.DROITE) {
            hemo = this.partie.getHemoglobine(this.x + 1, this.y);
            if (hemo != null) {
                hemo.glissadeMur(this, _dir);
            } else {
                this.x++;
            }
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
        if (_dir == Finstere.HAUT) {
            if (this.y > 0) {
                mur = this.partie.getMur(this.x, this.y - 1);
                perso = this.partie.getPersonnage(this.x, this.y - 1);
                hemo = this.partie.getHemoglobine(this.x, this.y - 1);
            }
            this.y--;
        } else if (_dir == Finstere.BAS) {
            if (this.y < 10) {
                mur = this.partie.getMur(this.x, this.y + 1);
                perso = this.partie.getPersonnage(this.x, this.y + 1);
                hemo = this.partie.getHemoglobine(this.x, this.y + 1);
            }
            this.y++;
        } else if (_dir == Finstere.GAUCHE) {
            if (this.x > 0) {
                mur = this.partie.getMur(this.x - 1, this.y);
                perso = this.partie.getPersonnage(this.x - 1, this.y);
                hemo = this.partie.getHemoglobine(this.x - 1, this.y);
            }
            this.x--;
        } else if (_dir == Finstere.DROITE) {
            if (this.x < 15) {
                mur = this.partie.getMur(this.x + 1, this.y);
                perso = this.partie.getPersonnage(this.x + 1, this.y);
                hemo = this.partie.getHemoglobine(this.x + 1, this.y);
            }
            this.x++;
        }

        /* Appel récursif si il y a un Mur derrière celui-ci */
        if (mur != null) {
            persoMort = mur.pousserMonstre(_dir);
        } else if (perso == null && hemo != null) {
            hemo.glissadeMur(this, _dir);
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
            if (_dir == Finstere.HAUT) {
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
                    if (m != null) {
                        persoMort = m.pousserMonstre(_dir);
                    }
                    if (h != null && p == null) {
                        perso.glisser(h, _dir, 0);
                    } else {
                        perso.deplacer(perso.getX(), perso.getY() - 1, 0);
                    }
                    perso = p;
                }
            } else if (_dir == Finstere.BAS) {
                if (perso.getY() == 10) {
                    return perso;
                } else if (this.partie.getLabyrinthe().isBlocked(perso.getX(),
                        perso.getY() + 1)) {
                    return perso;
                } else {
                    Personnage p = this.partie.getPersonnage(perso.getX(),
                            perso.getY() + 1);
                    Hemoglobine h = this.partie.getHemoglobine(perso.getX(),
                            perso.getY() + 1);
                    Mur m = this.partie.getMur(perso.getX(), perso.getY() + 1);
                    if (m != null) {
                        persoMort = m.pousserMonstre(_dir);
                    }
                    if (h != null && p == null) {
                        perso.glisser(h, _dir, 0);
                    } else {
                        perso.deplacer(perso.getX(), perso.getY() + 1, 0);
                    }
                    perso = p;
                }
            } else if (_dir == Finstere.GAUCHE) {
                if (perso.getX() == 0) {
                    return perso;
                } else if (this.partie.getLabyrinthe().isBlocked(perso.getX() - 1,
                        perso.getY())) {
                    return perso;
                } else {
                    Personnage p = this.partie.getPersonnage(perso.getX() - 1,
                            perso.getY());
                    Hemoglobine h = this.partie.getHemoglobine(perso.getX() - 1,
                            perso.getY());
                    Mur m = this.partie.getMur(perso.getX() - 1, perso.getY());
                    if (m != null) {
                        persoMort = m.pousserMonstre(_dir);
                    }
                    if (h != null && p == null) {
                        perso.glisser(h, _dir, 0);
                    } else {
                        perso.deplacer(perso.getX() - 1, perso.getY(), 0);
                    }
                    perso = p;
                }
            } else if (_dir == Finstere.DROITE) {
                if (perso.getX() == 15) {
                    return perso;
                } else if (this.partie.getLabyrinthe().isBlocked(perso.getX() + 1,
                        perso.getY())) {
                    return perso;
                } else {
                    Personnage p = this.partie.getPersonnage(perso.getX() + 1,
                            perso.getY());
                    Hemoglobine h = this.partie.getHemoglobine(perso.getX() + 1,
                            perso.getY());
                    Mur m = this.partie.getMur(perso.getX() + 1, perso.getY());
                    if (m != null) {
                        persoMort = m.pousserMonstre(_dir);
                    }
                    if (h != null && p == null) {
                        perso.glisser(h, _dir, 0);
                    } else {
                        perso.deplacer(perso.getX() + 1, perso.getY(), 0);
                    }
                    perso = p;
                }
            }
        }

        return persoMort;
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
