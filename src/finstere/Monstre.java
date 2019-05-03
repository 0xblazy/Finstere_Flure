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
 * @author TomWyso
 */
public class Monstre extends Pion {

    /* Direction du Monstre (Finstere.HAUT .BAS ...) */
    private int direction;

    /* Constructeur */
    public Monstre(Partie _partie) {
        super(0, 0, _partie);
        this.direction = Finstere.DROITE;
    }

    /* Appelée lorsque c'est au tour du Monstre de se déplacer */
    public List<Personnage> tour(Carte _carte) {
        if (_carte.getValeur() == Finstere.X || _carte.getValeur() == Finstere.XX) {
            return this.proies(_carte.getValeur() - 10);
        } else {
            return this.deplacement(_carte.getValeur());
        }
    }

    /* Appelée lorsque la Carte piochée est une "une proie" ou une "deux proies" */
    private List<Personnage> proies(int _nbProie) {
        ArrayList<Personnage> morts = new ArrayList<>();
        int nbDeplacement = 0;

        while (nbDeplacement < 20 && morts.size() < _nbProie) {
            this.trouverDirection(morts);
            for (Personnage mort : this.deplacer()) {
                if (!morts.contains(mort)) {
                    morts.add(mort);
                }
            }
            nbDeplacement++;
        }
        this.trouverDirection(morts);

        return morts;
    }

    /* Appelée lorsque la Carte piochée est un nombre de déplacement donné */
    private List<Personnage> deplacement(int _nbDeplacement) {
        ArrayList<Personnage> morts = new ArrayList<>();

        for (int i = 0; i < _nbDeplacement; i++) {
            this.trouverDirection(morts);
            for (Personnage mort : this.deplacer()) {
                if (!morts.contains(mort)) {
                    morts.add(mort);
                }
            }
        }
        this.trouverDirection(morts);

        return morts;
    }

    /* Déplace le Monstre */
    private List<Personnage> deplacer() {
        List<Personnage> morts = new ArrayList<>();

        /* Regarde si le Monstre doit être téléporté */
        if (this.partie.getLabyrinthe().isBordure(this.x, this.y)) {
            Map<Integer, int[]> tp = this.partie.getLabyrinthe().getTp(this.x, this.y);
            if (tp.containsKey(this.direction)) {
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
                this.x = tp.get(this.direction)[0];
                this.y = tp.get(this.direction)[1];
                this.partie.addDeplacement("Le Monstre se téléporte en (" 
                        + this.x + "," + this.y + ")");
                Mur mur = this.partie.getMur(this.x, this.y);
                if (mur != null) {
                    Personnage persoMort = mur.pousserMonstre(this.direction);
                    this.partie.addDeplacement("Le Monstre pousse le Mur");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                }
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
            } else {
                for (Personnage perso : this.deplacerSurCase()) {
                    if (!morts.contains(perso)) {
                        morts.add(perso);
                    }
                }
            }
        } else {
            for (Personnage perso : this.deplacerSurCase()) {
                if (!morts.contains(perso)) {
                    morts.add(perso);
                }
            }
        }

        Personnage perso = this.partie.getPersonnage(this.x, this.y);
        if (perso != null) {
            if (!morts.contains(perso)) {
                morts.add(perso);
                this.partie.addDeplacement("Le Monstre dévore le Personnage " 
                        + perso.infosPerso());
            }
        }

        return morts;
    }

    /* Réalise le déplacement du Monstre dans une direction donnée lorsqu'il ne 
     * se téléporte pas
     */
    private List<Personnage> deplacerSurCase() {
        List<Personnage> morts = new ArrayList<>();
        Personnage persoMort;
        Mur mur;

        this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
        
        /* Si le Monstre se dirige vers le HAUT */
        if (this.direction == Finstere.HAUT) {
            
            /* Si la Case au dessus est de l'Hemoglobine */
            if (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y - 1)) {
                y--;
                
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                
                /* Tant que la Case sur laquelle est le Monstre est de 
                 * l'Hemoglobine, il glisse
                 */
                while (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y)) {
                    mur = this.partie.getMur(this.x, this.y);
                    if (mur != null) {
                        persoMort = mur.pousserMonstre(this.direction);
                        this.partie.addDeplacement("Le Monstre pousse le Mur");
                        if (persoMort != null) {
                            if (!morts.contains(persoMort)) {
                                morts.add(persoMort);
                                this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                            }
                        }
                    }
                    persoMort = this.partie.getPersonnage(this.x, this.y);
                    this.partie.addDeplacement("Le Monstre glisse sur l'Hémoglobine");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre dévore le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                    this.y--;
                }
            } else {
                this.y--;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                mur = this.partie.getMur(this.x, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    this.partie.addDeplacement("Le Monstre pousse le Mur");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                }
            }
            
        /* Si le Monstre se dirige vers le BAS */
        } else if (this.direction == Finstere.BAS) {
            
            /* Si la Case en dessous est de l'Hemoglobine */
            if (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y + 1)) {
                y++;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                
                /* Tant que la Case sur laquelle est le Monstre est de 
                 * l'Hemoglobine, il glisse
                 */
                while (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y)) {
                    mur = this.partie.getMur(this.x, this.y);
                    if (mur != null) {
                        persoMort = mur.pousserMonstre(this.direction);
                        this.partie.addDeplacement("Le Monstre pousse le Mur");
                        if (persoMort != null) {
                            if (!morts.contains(persoMort)) {
                                morts.add(persoMort);
                                this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                            }
                        }
                    }
                    persoMort = this.partie.getPersonnage(this.x, this.y);
                    this.partie.addDeplacement("Le Monstre glisse sur l'Hémoglobine");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre dévore le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                    this.y++;
                }
            } else {
                this.y++;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                mur = this.partie.getMur(this.x, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    this.partie.addDeplacement("Le Monstre pousse le Mur");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    } 
                }
            }
            
        /* Si le Monstre se dirige vers la GAUCHE */
        } else if (this.direction == Finstere.GAUCHE) {
            
            /* Si la Case à gauche est de l'Hemoglobine */
            if (this.partie.getLabyrinthe().isHemoglobine(this.x - 1, this.y)) {
                x--;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                
                /* Tant que la Case sur laquelle est le Monstre est de 
                 * l'Hemoglobine, il glisse
                 */
                while (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y)) {
                    mur = this.partie.getMur(this.x, this.y);
                    if (mur != null) {
                        persoMort = mur.pousserMonstre(this.direction);
                        this.partie.addDeplacement("Le Monstre pousse le Mur");
                        if (persoMort != null) {
                            if (!morts.contains(persoMort)) {
                                morts.add(persoMort);
                                this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                            }
                        }
                    }
                    persoMort = this.partie.getPersonnage(this.x, this.y);
                    this.partie.addDeplacement("Le Monstre glisse sur l'Hémoglobine");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre dévore le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                    this.x--;
                }
            } else {
                this.x--;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                mur = this.partie.getMur(this.x, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    this.partie.addDeplacement("Le Monstre pousse le Mur");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                }
            }
            
        /* Si le Monstre se dirige vers la DROITE */
        } else if (this.direction == Finstere.DROITE) {
            
            /* Si la Case en dessous est de l'Hemoglobine */
            if (this.partie.getLabyrinthe().isHemoglobine(this.x + 1, this.y)) {
                x++;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                
                /* Tant que la Case sur laquelle est le Monstre est de 
                 * l'Hemoglobine, il glisse
                 */
                while (this.partie.getLabyrinthe().isHemoglobine(this.x, this.y)) {
                    mur = this.partie.getMur(this.x, this.y);
                    if (mur != null) {
                        persoMort = mur.pousserMonstre(this.direction);
                        this.partie.addDeplacement("Le Monstre pousse le Mur");
                        if (persoMort != null) {
                            if (!morts.contains(persoMort)) {
                                morts.add(persoMort);
                                this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                            }
                        }
                    }
                    persoMort = this.partie.getPersonnage(this.x, this.y);
                    this.partie.addDeplacement("Le Monstre glisse sur l'Hémoglobine");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre dévore le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                    this.x++;
                }
            } else {
                this.x++;
                this.partie.addDeplacement("Le Monstre se déplace en (" + this.x
                        + "," + this.y + ")");
                mur = this.partie.getMur(this.x, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    this.partie.addDeplacement("Le Monstre pousse le Mur");
                    if (persoMort != null) {
                        if (!morts.contains(persoMort)) {
                            morts.add(persoMort);
                            this.partie.addDeplacement("Le Monstre écrase le Personnage " 
                                    + persoMort.infosPerso());
                        }
                    }
                }
            }
        }
        this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);

        return morts;
    }

    /* Trouve la direction à donner au Monstre en fonction de la situation */
    private void trouverDirection(List<Personnage> _morts) {
        int ancienneDirection = this.direction;
        int distance = 16;

        /* Pour chaque Personnage visible */
        for (Map.Entry<Integer, Personnage> entry : this.persoVisibles(_morts).entrySet()) {

            /* Si le Personnage est aligné horizontalement */
            if (entry.getKey() == Finstere.GAUCHE
                    || entry.getKey() == Finstere.DROITE) {
                if (Math.abs(this.x - entry.getValue().getX()) < distance) {
                    distance = Math.abs(this.x - entry.getValue().getX());
                    this.direction = entry.getKey();
                } else if (Math.abs(this.x - entry.getValue().getX()) == distance) {
                    this.direction = ancienneDirection;
                }

                /* Si le Personnage est aligné verticalement */
            } else if (entry.getKey() == Finstere.HAUT
                    || entry.getKey() == Finstere.BAS) {
                if (Math.abs(this.y - entry.getValue().getY()) < distance) {
                    distance = Math.abs(this.y - entry.getValue().getY());
                    this.direction = entry.getKey();
                } else if (Math.abs(this.y - entry.getValue().getY()) == distance) {
                    this.direction = ancienneDirection;
                }
            }
        }

        if (this.direction != ancienneDirection) {
            if (this.direction == Finstere.HAUT) {
                this.partie.addDeplacement("Le Monstre se tourne vers le HAUT");
            }
            if (this.direction == Finstere.BAS) {
                this.partie.addDeplacement("Le Monstre se tourne vers le BAS");
            }
            if (this.direction == Finstere.GAUCHE) {
                this.partie.addDeplacement("Le Monstre se tourne vers la GAUCHE");
            }
            if (this.direction == Finstere.DROITE) {
                this.partie.addDeplacement("Le Monstre se tourne vers la DROITE");
            }
        }
    }

    /* Retourne les Personnages visibles avec leur direction par rapport au Monstre */
    private Map<Integer, Personnage> persoVisibles(List<Personnage> _morts) {
        HashMap<Integer, Personnage> persoV = new HashMap<>();

        /* Pour chaque Personnage aligné */
        for (Personnage perso : this.partie.persoAlignes(this.x, this.y)) {

            /* Si le Personnage n'est pas dans _morts */
            if (!_morts.contains(perso)) {
                /* Si le Personnage est a GAUCHE */
                if (perso.getX() < this.x) {
                    int dir = Finstere.GAUCHE;
                    boolean visible = (dir != -this.direction);
                    int i = this.x - 1;
                    while (visible && i > perso.getX()) {
                        visible = !this.partie.getLabyrinthe().isMur(i, this.y);
                        i--;
                    }
                    if (visible) {
                        if (persoV.containsKey(dir)) {
                            if (Math.abs(this.x - perso.getX())
                                    < Math.abs(this.x - persoV.get(dir)
                                            .getX())) {
                                persoV.put(dir, perso);
                            }
                        } else {
                            persoV.put(dir, perso);
                        }
                    }

                /* Si le Personnage est a DROITE */
                } else if (perso.getX() > this.x) {
                    int dir = Finstere.DROITE;
                    boolean visible = (dir != -this.direction);
                    int i = this.x + 1;
                    while (visible && i < perso.getX()) {
                        visible = !this.partie.getLabyrinthe().isMur(i, this.y);
                        i++;
                    }
                    if (visible) {
                        if (persoV.containsKey(dir)) {
                            if (Math.abs(this.x - perso.getX())
                                    < Math.abs(this.x - persoV.get(dir)
                                            .getX())) {
                                persoV.put(dir, perso);
                            }
                        } else {
                            persoV.put(dir, perso);
                        }
                    }

                /* Si le Personnage est en HAUT */
                } else if (perso.getY() < this.y) {
                    int dir = Finstere.HAUT;
                    boolean visible = (dir != -this.direction);
                    int j = this.y - 1;
                    while (visible && j > perso.getY()) {
                        visible = !this.partie.getLabyrinthe().isMur(this.x, j);
                        j--;
                    }
                    if (visible) {
                        if (persoV.containsKey(dir)) {
                            if (Math.abs(this.y - perso.getY())
                                    < Math.abs(this.y - persoV.get(dir)
                                            .getY())) {
                                persoV.put(dir, perso);
                            }
                        } else {
                            persoV.put(dir, perso);
                        }
                    }

                /* Si le Personnage est en BAS */
                } else if (perso.getY() > this.y) {
                    int dir = Finstere.BAS;
                    boolean visible = (dir != -this.direction);
                    int j = this.y + 1;
                    while (visible && j < perso.getY()) {
                        visible = !this.partie.getLabyrinthe().isMur(this.x, j);
                        j++;
                    }
                    if (visible) {
                        if (persoV.containsKey(dir)) {
                            if (Math.abs(this.y - perso.getY())
                                    < Math.abs(this.y - persoV.get(dir)
                                            .getY())) {
                                persoV.put(dir, perso);
                            }
                        } else {
                            persoV.put(dir, perso);
                        }
                    }
                }
            }
        }

        return persoV;
    }

    /* Retourne le Monstre sous la forme d'une chaîne de caractère indiquant sa 
     * direction
     */
    @Override
    public String toString() {
        if (this.direction == Finstere.HAUT) {
            return "M/\\";
        } else if (this.direction == Finstere.BAS) {
            return "M\\/";
        } else if (this.direction == Finstere.GAUCHE) {
            return "<<M";
        } else if (this.direction == Finstere.DROITE) {
            return "M>>";
        }
    
        return " M ";
    }
    
    /* Getters */
    public ImageIcon getImageIcon() {
        if (this.direction == Finstere.HAUT) {
            return new ImageIcon(getClass().getResource("/img/monstre1.gif"));
        } else if (this.direction == Finstere.BAS) {
            return new ImageIcon(getClass().getResource("/img/monstre3.gif"));
        } else if (this.direction == Finstere.GAUCHE) {
            return new ImageIcon(getClass().getResource("/img/monstre4.gif"));
        } else if (this.direction == Finstere.DROITE) {
            return new ImageIcon(getClass().getResource("/img/monstre2.gif"));
        }
        
        return null;
    }
}
