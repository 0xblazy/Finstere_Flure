/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
/**
 *
 * @author nKBlaZy
 */
public class Personnage extends Pion {

    /* Points de mouvement face clair/foncé et pour le tour */
    private final int pmC, pmF;
    private int pm;
    /* Couleur du Personnage (blue, brown, gray, green, purple, red, yellow) */
    private final String couleur;
    /* Vrai si le Personnage est tourné face clair */
    private boolean faceClair;
    /* Vrai si le Personnage a été joué durant le tour */
    private boolean joue;
    /* Vrai si le Personnage est mort */
    private boolean rip;
    /* Vrai si le Personnage est sorti du Labyrinthe */
    private boolean exit;
    /* Classement du Personnage */
    private int classement;
    /* Vrai si le Personnage est au dessus/en dessous d'un autre */
    private boolean auDessus, enDessous;

    /* Constructeur */
    public Personnage(int _pmC, int _pmF, String _couleur, Partie _partie) {
        super(Finstere.EXTERIEUR[0], Finstere.EXTERIEUR[1], _partie);
        this.pmC = _pmC;
        this.pmF = _pmF;
        this.pm = _pmC;
        this.couleur = _couleur;
        this.faceClair = true;
        this.joue = false;
        this.rip = false;
        this.exit = false;
        this.classement = 0;
        this.auDessus = false;
        this.enDessous = false;
    }

    /* Retourne le Personnage sous la forme d'une chaine de 3 caractères */
    public String shortString() {
        String s = "";

        /* Ajoute l'intiale de la couleur à la chaîne */
        if (this.couleur.equals(Finstere.COULEURS[0])) {
            s += "B";
        } else if (this.couleur.equals(Finstere.COULEURS[1])) {
            s += "V";
        } else if (this.couleur.equals(Finstere.COULEURS[2])) {
            s += "R";
        } else if (this.couleur.equals(Finstere.COULEURS[3])) {
            s += "J";
        }

        /* Ajoute les pm et la face du Personnage à la chaîne */
        if (this.faceClair) {
            s += this.pmC + "C";
        } else {
            s += this.pmF + "F";
        }

        return s;
    }
    
    /* Retourne le Personnage sous forme d'une chaine de caractères avec sa 
     * couleur et les pm de chaque face
     */
    public String infosPerso() {
        if (this.couleur.equals(Finstere.COULEURS[0])) {
            return "Bleu " + this.pmC + " " + this.pmF;
        } else if (this.couleur.equals(Finstere.COULEURS[1])) {
            return "Vert " + this.pmC + " " + this.pmF;
        } else if (this.couleur.equals(Finstere.COULEURS[2])) {
            return "Rouge " + this.pmC + " " + this.pmF;
        } else if (this.couleur.equals(Finstere.COULEURS[3])) {
            return "Jaune " + this.pmC + " " + this.pmF;
        } else {
            return "";
        }
    }

    /* Déplace le Personnage aux coordonnées _x, _y */
    public boolean deplacer(int _x, int _y, int _pm) {

        /* Si le Personnage est déjà sur le Labyrinthe */
        if (this.x < Finstere.EXTERIEUR[0] && this.y <= Finstere.EXTERIEUR[1]) {
            if (this.auDessus) {
                Personnage perso = this.partie.getPersonnageEnDessous(this.x, this.y);
                if (perso != null) {
                    perso.enDessous = false;
                } else {
                    this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
                }
                this.auDessus = false;
            } else if (this.enDessous) {
                Personnage perso = this.partie.getPersonnage(this.x, this.y);
                if (perso != null) {
                    perso.auDessus = false;
                } else {
                    this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
                }
                this.enDessous = false;
            } else {
                this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
            }
        }

        /* Si le Personnage se déplace sur une Case déjà occupée */
        Personnage perso = this.partie.getPersonnage(_x, _y);
        if (perso != null) {
            perso.enDessous = true;
            this.auDessus = true;
        }

        this.pm -= _pm;
        this.x = _x;
        this.y = _y;
        this.partie.getLabyrinthe().setPersonnage(_x, _y, true);
        return true;
    }

    /* Retourne les coordonnées des Cases sur lequel le Personnage peut 
     * possiblement aller
     */
    private ArrayList<int[]> casesPossibles(int _x, int _y, int _rayon) {
        ArrayList<int[]> cases = new ArrayList<>();

        for (int j = -_rayon; j <= _rayon; j++) {
            for (int i = -_rayon; i <= _rayon; i++) {
                if (Math.abs(i) + Math.abs(j) <= _rayon
                        && _x + i < Finstere.EXTERIEUR[0]
                        && _x + i > -1
                        && _y + j <= Finstere.EXTERIEUR[1]
                        && _y + j > -1
                        && !(j == 0 && i == 0)) {
                    cases.add(new int[]{_x + i, _y + j});
                }
            }
        }

        return cases;
    }

    /* Retourne le coup en pm nécessaire pour aller sur une Case libre 
     * (retourne 10 si il n'y a pas de Case libre dans le rayon donné)
     */
    private int pmCaseLibre(int _x, int _y, int _rayon) {
        int coup = 10;

        /* Pour chaque Case possible */
        for (int[] c : this.casesPossibles(_x, _y, _rayon)) {
            int pmNecessaires = Math.abs(_x - c[0]) + Math.abs(_y - c[1]);

            /* Définition de la direction à tester (pour les Murs) */
            int direction;
            int diffY = Math.abs(_y - c[1]);
            int diffX = Math.abs(_x - c[0]);
            if (diffY > diffX) {
                if (_y > c[1]) {
                    direction = Finstere.HAUT;
                } else {
                    direction = Finstere.BAS;
                }
            } else {
                if (_x > c[0]) {
                    direction = Finstere.GAUCHE;
                } else {
                    direction = Finstere.DROITE;
                }
            }

            /* Si la Case est libre et les pm nécessaires sont inférieurs à ceux
             * déjà déterminés
             */
            if (this.partie.getLabyrinthe().isLibre(c[0], c[1], direction)
                    && pmNecessaires < coup) {
                coup = pmNecessaires;
            }
        }

        return coup;
    }

    /* Détermine si la Case (_x,_y) est bloquée (pas suffisament de pm pour en 
     * sortir avant la fin des actions) ou non
     * Appelée lorsque la Case en question est un Personnage
     */
    private boolean bloquee(int _x, int _y) {
        boolean bloquee = true;
        if (_y > 0) {
            if (!this.partie.getLabyrinthe().isMonstre(_x, _y - 1)
                    && !this.partie.getLabyrinthe().isBlocked(_x, _y - 1)) {
                if (this.partie.getLabyrinthe().isMur(_x, _y - 1)) {
                    if (this.partie.getMur(_x, _y - 1)
                            .poussable(Finstere.HAUT)) {
                        bloquee = false;
                    }
                } else {
                    bloquee = false;
                }
            }
        }
        if (_y < 10) {
            if (!this.partie.getLabyrinthe().isMonstre(_x, _y + 1)
                    && !this.partie.getLabyrinthe().isBlocked(_x, _y + 1)) {
                if (this.partie.getLabyrinthe().isMur(_x, _y + 1)) {
                    if (this.partie.getMur(_x, _y + 1)
                            .poussable(Finstere.BAS)) {
                        bloquee = false;
                    }
                } else {
                    bloquee = false;
                }
            }
        }
        if (_x > 0) {
            if (!this.partie.getLabyrinthe().isMonstre(_x - 1, _y)
                    && !this.partie.getLabyrinthe().isBlocked(_x - 1, _y)) {
                if (this.partie.getLabyrinthe().isMur(_x - 1, _y)) {
                    if (this.partie.getMur(_x - 1, _y)
                            .poussable(Finstere.GAUCHE)) {
                        bloquee = false;
                    }
                } else {
                    bloquee = false;
                }
            }
        }
        if (_x < 15) {
            if (!this.partie.getLabyrinthe().isMonstre(_x + 1, _y)
                    && !this.partie.getLabyrinthe().isBlocked(_x + 1, _y)) {
                if (this.partie.getLabyrinthe().isMur(_x + 1, _y)) {
                    if (this.partie.getMur(_x + 1, _y)
                            .poussable(Finstere.DROITE)) {
                        bloquee = false;
                    }
                } else {
                    bloquee = false;
                }
            }
        }
        return bloquee;
    }

    /* Génère l'Action deplacer en fonction des contraintes du Labyrinthe
     * Retourne null si l'Action n'est pas réalisable
     */
    private Action actionDeplacer(int _x, int _y) {
        int pmA = Math.abs(_x - this.x) + Math.abs(_y - this.y);

        /* Si il y a un obstacle entre la Case et le Personnage */
        if (this.partie.getLabyrinthe().obstacle(_x, _y, this.x, this.y)) {

            /* Si le Personnage a assez de pm pour contourner l'obstacle */
            if (pmA + 2 <= this.pm) {
                if (this.partie.getLabyrinthe().isPersonnage(_x, _y)) {
                    if (!this.bloquee(_x, _y) && pmA + 2
                            + this.pmCaseLibre(_x, _y, this.pm - (pmA + 2)) <= this.pm) {
                        return new Action("Se Déplacer en (" + _x + "," + _y + ")",
                            this.getClass(), "deplacer", new Object[]{_x, _y, pmA + 2},
                                pmA + 2);
                    } else {
                        return null;
                    }
                } else {
                    return new Action("Se Déplacer en (" + _x + "," + _y + ")",
                        this.getClass(), "deplacer", new Object[]{_x, _y, pmA + 2}, 
                            pmA + 2);
                }
            } else {
                return null;
            }
        } else if (this.partie.getLabyrinthe().isPersonnage(_x, _y)) {
            if (!this.bloquee(_x, _y) && pmA
                    + this.pmCaseLibre(_x, _y, this.pm - pmA) <= this.pm) {
                return new Action("Se Déplacer en (" + _x + "," + _y + ")",
                        this.getClass(), "deplacer", new Object[]{_x, _y, pmA}, 
                        pmA);
            } else {
                return null;
            }
        } else {
            return new Action("Se Déplacer en (" + _x + "," + _y + ")",
                    this.getClass(), "deplacer", new Object[]{_x, _y, pmA}, 
                    pmA);
        }
    }

    /* Pousse _mur dans la direction donnée */
    public boolean pousserMur(Mur _mur, int _dir) {
        if (_dir == Finstere.HAUT) {
            _mur.pousser(_dir);
            this.deplacer(this.x, this.y - 1, 1);
        } else if (_dir == Finstere.BAS) {
            _mur.pousser(_dir);
            this.deplacer(this.x, this.y + 1, 1);
        } else if (_dir == Finstere.GAUCHE) {
            _mur.pousser(_dir);
            this.deplacer(this.x - 1, this.y, 1);
        } else if (_dir == Finstere.DROITE) {
            _mur.pousser(_dir);
            this.deplacer(this.x + 1, this.y, 1);
        }

        return true;
    }

    /* Glisse sur une flaque d'Hemoglobine dans la direction donnée */
    public boolean glisser(Hemoglobine _hemo, int _dir, int _pm) {
        _hemo.glissade(this, _dir);
        this.pm -= _pm;

        return true;
    }

    /* Retourne la liste des Actions possibles avec l'Hemoglobine */
    private List<Action> actionsHemoglobine() {
        ArrayList<Action> actions = new ArrayList<>();

        for (Hemoglobine hemo : this.partie.interactionsHemoglobine(this.x,
                this.y)) {

            /* Si l'Hemoglobine est carrée */
            if (hemo.getType().equals(Finstere.CARRE)) {

                /* Si le Personnage est en BAS ou en HAUT de la flaque */
                if (hemo.getX() == this.x || hemo.getX() + 1 == this.x) {
                    if (hemo.getY() < this.y) {
                        if (this.partie.getLabyrinthe().isPersonnage(this.x,
                                hemo.getY() - 1)) {
                            if (1 + this.pmCaseLibre(this.x, hemo.getY() - 1,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le HAUT",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.HAUT, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else if (hemo.getY() >= this.y) {
                        if (this.partie.getLabyrinthe().isPersonnage(this.x,
                                hemo.getY() + 2)) {
                            if (1 + this.pmCaseLibre(this.x, hemo.getY() + 2,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le BAS",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.BAS, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    }
                }

                /* Si le Personnage est à GAUCHE ou à DROITE de la flaque */
                if (hemo.getY() == this.y || hemo.getY() + 1 == this.y) {
                    if (hemo.getX() < this.x) {
                        if (this.partie.getLabyrinthe().isPersonnage(hemo.getX() - 1,
                                this.y)) {
                            if (1 + this.pmCaseLibre(hemo.getX() - 1, this.y,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le GAUCHE",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le GAUCHE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                        }
                    } else if (hemo.getX() >= this.x) {
                        if (this.partie.getLabyrinthe().isPersonnage(hemo.getX() + 2,
                                this.y)) {
                            if (1 + this.pmCaseLibre(hemo.getX() + 2, this.y,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le DROITE",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.DROITE, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le DROITE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.DROITE, 1}, 1));
                        }
                    }
                }

                /* Si le Personnage est en HAUT à GAUCHE */
                if (hemo.getX() == this.x && hemo.getY() == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y - 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le HAUT",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.HAUT, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x - 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x - 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la GAUCHE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la GAUCHE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                    }
                }

                /* Si le Personnage est en HAUT à DROITE */
                if (hemo.getX() + 1 == this.x && hemo.getY() == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y - 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le HAUT",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.HAUT, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x + 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x + 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la DROITE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.DROITE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la DROITE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.DROITE, 1}, 1));
                    }
                }

                /* Si le Personnage est en BAS à GAUCHE */
                if (hemo.getX() == this.x && hemo.getY() + 1 == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y + 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le BAS",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.BAS, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x - 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x - 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la GAUCHE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la GAUCHE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                    }
                }

                /* Si le Personnage est en BAS à DROITE */
                if (hemo.getX() + 1 == this.x && hemo.getY() + 1 == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y + 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le BAS",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.BAS, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x + 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x + 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la DROITE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.DROITE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la DROITE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.DROITE, 1}, 1));
                    }
                }
            }

            /* Si l'Hemoglobine est linéaire horizontale */
            if (hemo.getType().equals(Finstere.LINEHORI)) {

                /* Si le Personnage est en BAS ou en HAUT de la flaque */
                if (hemo.getX() <= this.x && hemo.getX() + 3 >= this.x) {
                    if (hemo.getY() < this.y) {
                        if (this.partie.getLabyrinthe().isPersonnage(this.x,
                                hemo.getY() - 1)) {
                            if (1 + this.pmCaseLibre(this.x, hemo.getY() - 1,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le HAUT",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.HAUT, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else if (hemo.getY() > this.y) {
                        if (this.partie.getLabyrinthe().isPersonnage(this.x,
                                hemo.getY() + 1)) {
                            if (1 + this.pmCaseLibre(this.x, hemo.getY() + 1,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le BAS",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.BAS, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    }
                }

                /* Si le Personnage est à GAUCHE ou à DROITE de la flaque */
                if (hemo.getY() == this.y) {
                    if (hemo.getX() < this.x) {
                        if (this.partie.getLabyrinthe().isPersonnage(hemo.getX() - 1,
                                this.y)) {
                            if (1 + this.pmCaseLibre(hemo.getX() - 1, this.y,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le GAUCHE",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le GAUCHE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                        }
                    } else if (hemo.getX() + 3 > this.x) {
                        if (this.partie.getLabyrinthe().isPersonnage(hemo.getX() + 4,
                                this.y)) {
                            if (1 + this.pmCaseLibre(hemo.getX() + 4, this.y,
                                    this.pm - 1) <= this.pm) {
                                actions.add(new Action("Glisser vers le DROITE",
                                        this.getClass(), "glisser",
                                        new Object[]{hemo, Finstere.DROITE, 1}, 1));
                            }
                        } else {
                            actions.add(new Action("Glisser vers le DROITE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.DROITE, 1}, 1));
                        }
                    }
                }

                /* Si le Personnage est à GAUCHE */
                if (hemo.getX() == this.x && hemo.getY() == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y - 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le HAUT",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.HAUT, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x - 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x - 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la GAUCHE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la GAUCHE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.GAUCHE, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y + 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le BAS",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.BAS, 1}, 1));
                    }
                }

                /* Si le Personnage est au CENTRE */
                if ((hemo.getX() + 1 == this.x || hemo.getX() + 2 == this.x)
                        && hemo.getY() == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y - 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le HAUT",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.HAUT, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y + 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le BAS",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.BAS, 1}, 1));
                    }
                }

                /* Si le Personnage est à DROITE */
                if (hemo.getX() + 3 == this.x && hemo.getY() == this.y) {
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y - 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y - 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le HAUT",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.HAUT, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le HAUT",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.HAUT, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x + 1, this.y)) {
                        if (1 + this.pmCaseLibre(this.x + 1, this.y, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par la DROITE",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.DROITE, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par la DROITE",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.DROITE, 1}, 1));
                    }
                    if (this.partie.getLabyrinthe().isPersonnage(this.x, this.y + 1)) {
                        if (1 + this.pmCaseLibre(this.x, this.y + 1, this.pm - 1) <= this.pm) {
                            actions.add(new Action("Sortir par le BAS",
                                    this.getClass(), "glisser",
                                    new Object[]{hemo, Finstere.BAS, 1}, 1));
                        }
                    } else {
                        actions.add(new Action("Sortir par le BAS",
                                this.getClass(), "glisser",
                                new Object[]{hemo, Finstere.BAS, 1}, 1));
                    }
                }
            }
        }

        return actions;
    }

    /* Fait sortir le Personnage du Labyrinthe */
    public boolean sortir() {
        this.pm -= (this.x + this.y + 1);
        this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
        this.x = Finstere.SORTI[0];
        this.y = Finstere.SORTI[1];
        this.exit = true;
        this.classement = this.partie.getClassement();
        this.partie.setClassement();
        this.joue = true;
        return false;
    }

    /* Termine l'action d'un Personnage */
    public boolean finAction() {
        this.faceClair = !this.faceClair;
        this.joue = true;
        return false;
    }

    /* Retourne la liste des Actions que peut faire le Personnage */
    public Map<Integer, Action> getActions() {
        HashMap<Integer, Action> actions = new HashMap<>();
        int key = 1;

        /* Déplacement */
        /* Pour chaque Case possible */
        for (int[] c : this.casesPossibles(this.x, this.y, this.pm)) {

            /* Si la Case est disponible */
            if (this.partie.getLabyrinthe().isDisponible(c[0], c[1])) {
                Action action = this.actionDeplacer(c[0], c[1]);

                /* Si l'Action n'est pas null (est donc réalisable) */
                if (action != null) {
                    actions.put(key, action);
                    key++;
                }
            }
        }

        /* Pousser un Mur */
        if (this.pm > 0) {
            if (this.y > 0) {
                Mur mur = this.partie.getMur(this.x, this.y - 1);
                if (mur != null) {
                    if (mur.poussable(Finstere.HAUT)) {
                        actions.put(key, new Action("Pousser le Mur vers le HAUT",
                                this.getClass(), "pousserMur",
                                new Object[]{mur, Finstere.HAUT}, 1));
                        key++;
                    }
                }
            }
            if (this.y < 10) {
                Mur mur = this.partie.getMur(this.x, this.y + 1);
                if (mur != null) {
                    if (mur.poussable(Finstere.BAS)) {
                        actions.put(key, new Action("Pousser le Mur vers le BAS",
                                this.getClass(), "pousserMur",
                                new Object[]{mur, Finstere.BAS}, 1));
                        key++;
                    }
                }
            }
            if (this.x > 0) {
                Mur mur = this.partie.getMur(this.x - 1, this.y);
                if (mur != null) {
                    if (mur.poussable(Finstere.GAUCHE)) {
                        actions.put(key, new Action("Pousser le Mur vers la GAUCHE",
                                this.getClass(), "pousserMur",
                                new Object[]{mur, Finstere.GAUCHE}, 1));
                        key++;
                    }
                }
            }
            if (this.x < 15) {
                Mur mur = this.partie.getMur(this.x + 1, this.y);
                if (mur != null) {
                    if (mur.poussable(Finstere.DROITE)) {
                        actions.put(key, new Action("Pousser le Mur vers la DROITE",
                                this.getClass(), "pousserMur",
                                new Object[]{mur, Finstere.DROITE}, 1));
                        key++;
                    }
                }
            }
        }

        /* Glisser sur l'Hemoglobine */
        if (this.pm > 0) {
            for (Action action : this.actionsHemoglobine()) {
                actions.put(key, action);
                key++;
            }
        }

        /* Si le Personnage a suffisament de pm pour sortir */
        if (this.x + this.y + 1 <= this.pm) {
            actions.put(key, new Action("Sortir du Labyrinthe", this.getClass(),
                    "sortir", new Object[]{}, this.x + this.y + 1));
            key++;
        }

        /* Si le Personnage est déjà sur le Labyrinthe et qu'il n'est pas au 
         * dessus d'un autre Personnage, on ajoute une Action pour terminer les
         * Actions
         */
        if (this.x < Finstere.EXTERIEUR[0] && this.y <= Finstere.EXTERIEUR[1]
                && !this.auDessus) {
            actions.put(key, new Action("Terminer les Actions", this.getClass(),
                    "finAction", new Object[]{}, 0));
        }

        return actions;
    }

    /* Appelée en fin de tour pour retourner le Personnage si il n'a pas été 
     * joué et redonner les pm à celui-ci pour le tour suivant
     */
    public void finTour() {
        if (!this.joue) {
            this.faceClair = !this.faceClair;
        }
        if (this.faceClair) {
            this.pm = this.pmC;
        } else {
            this.pm = this.pmF;
        }
        this.joue = false;
    }

    /* Appelée lorsque le Personnage est tué par le Monstre */
    public void tue(int _nbTour) {
        if (this.auDessus) {
            Personnage perso = this.partie.getPersonnageEnDessous(this.x, this.y);
            if (perso != null) {
                perso.enDessous = false;
            } else {
                this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
            }
            this.auDessus = false;
        } else if (this.enDessous) {
            Personnage perso = this.partie.getPersonnage(this.x, this.y);
            if (perso != null) {
                perso.auDessus = false;
            } else {
                this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
            }
            this.enDessous = false;
        } else {
            this.partie.getLabyrinthe().setPersonnage(this.x, this.y, false);
        }
        if (_nbTour < 8) {
            this.x = Finstere.EXTERIEUR[0];
            this.y = Finstere.EXTERIEUR[1];
        } else {
            this.x = Finstere.MORT[0];
            this.y = Finstere.MORT[1];
            this.rip = true;
        }
    }

    /* Retourne le Personnage sous forme d'une chaîne de caractère */
    @Override
    public String toString() {
        String s = "Personnage ";

        /* Ajout de la couleur à la chaîne */
        if (this.couleur.equals(Finstere.COULEURS[0])) {
            s += "Bleu";
        } else if (this.couleur.equals(Finstere.COULEURS[1])) {
            s += "Vert";
        } else if (this.couleur.equals(Finstere.COULEURS[2])) {
            s += "Rouge";
        } else if (this.couleur.equals(Finstere.COULEURS[3])) {
            s += "Jaune";
        }

        /* Ajout des pm et de la face à la chaîne */
        if (this.faceClair) {
            s += " " + this.pmC + " Face claire ";
        } else {
            s += " " + this.pmF + " Face foncée ";
        }

        /* Ajout des coordonnées du Personnage à la chaîne */
        if (this.x == Finstere.EXTERIEUR[0] && this.y == Finstere.EXTERIEUR[1]) {
            s += "(Extérieur)";
        } else if (this.x == Finstere.SORTI[0] && this.y == Finstere.SORTI[1]) {
            s += "(Sortie ";
            if (this.classement == 1) {
                s += "1er";
            } else {
                s += this.classement + "ème";
            }
            s += ")";
        } else if (this.x == Finstere.MORT[0] && this.y == Finstere.MORT[1]) {
            s += "(Mort)";
        } else {
            s += "(" + this.x + "," + this.y + ")";
        }

        return s;
    }

    /* Retourne true si le Personnage passé en paramètre est le même que ce 
     * Personnage
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Personnage) {
            Personnage perso = (Personnage) obj;
            return perso.couleur.equals(this.couleur) && perso.pmC == this.pmC
                    && perso.pmF == this.pmF;
        } else {
            return false;
        }
    }

    /* Getters */
    public int getClassement() {
        return this.classement;
    }

    public boolean isJoue() {
        return this.joue;
    }

    public boolean isRip() {
        return this.rip;
    }

    public boolean isExit() {
        return this.exit;
    }

    public boolean isAuDessus() {
        return this.auDessus;
    }

    public boolean isEnDessous() {
        return this.enDessous;
    }

    public int getPm() {
        return this.pm;
    }
    
    public ImageIcon getImageIcon() {
        if (this.rip) {
            return new ImageIcon(getClass().getResource("/img/rip.gif"));
        } else if (this.exit) {
            return new ImageIcon(getClass().getResource("/img/lauriers" 
                    + this.classement + ".gif"));
        } else if (this.faceClair) {
            return new ImageIcon(getClass().getResource("/img/pion" 
                    + this.couleur + "_" + this.pmC + "_" + this.pmF 
                    + "_clair.gif"));
        } else if (!this.faceClair) {
            return new ImageIcon(getClass().getResource("/img/pion" 
                    + this.couleur + "_" + this.pmC + "_" + this.pmF 
                    + "_fonce.gif"));
        }
        return null;
    }
    
    public ImageIcon getImageIconClair() {
        return new ImageIcon(getClass().getResource("/img/pion" 
                + this.couleur + "_" + this.pmC + "_" + this.pmF 
                + "_clair.gif"));
    }
    
    public boolean isJouable() {
        return !this.exit && !this.rip && !this.joue && !this.getActions().isEmpty();
    }
    
    public boolean isOnLaby() {
        return !this.exit && !this.rip && this.x < Finstere.EXTERIEUR[0] 
                && this.y <= Finstere.EXTERIEUR[1];
    }
}
