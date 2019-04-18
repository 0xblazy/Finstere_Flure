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
    
    /* Appelée lorsque la Carte piochée est une "une proie" */
    private List<Personnage> proies(int _nbProie) {
        ArrayList<Personnage> morts = new ArrayList<>();
        int nbDeplacement = 0;
        
        while (nbDeplacement < 20 && morts.size() < _nbProie) {
            this.trouverDirection(morts);
            Personnage mort = this.deplacer();
            if (mort != null) morts.add(mort);
            nbDeplacement++;
        }
        this.trouverDirection(morts);
        
        return morts;
    }
    
    /* Appelée lorsque la Carte piochée est un nombre de déplacement donné */
    private List<Personnage> deplacement(int _nbDeplacement) {
        ArrayList<Personnage> morts = new ArrayList<>();
        
        for (int i = 0 ; i < _nbDeplacement ; i++) {
            this.trouverDirection(morts);
            Personnage mort = this.deplacer();
            if (mort != null) {
                if (!morts.contains(mort)) morts.add(mort);
            }
        }
        this.trouverDirection(morts);
        
        return morts;
    }
    
    /* Déplace le Monstre */
    private Personnage deplacer() {
        Personnage persoMort = null;
        
        if (this.partie.getLabyrinthe().getCase(this.x, this.y).isBordure()) {
            Map<Integer, int[]> tp = this.partie.getLabyrinthe()
                    .getCase(this.x, this.y).getTp();
            if (tp.containsKey(this.direction)) {
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
                this.x = tp.get(this.direction)[0];
                this.y = tp.get(this.direction)[1];
                Mur mur = this.partie.getMur(this.x, this.y);
                if (mur != null) persoMort = mur.pousserMonstre(this.direction);
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
                System.out.println("   Le Monstre se téléporte en (" + this.x 
                        + "," + this.y +")");
            } else {
                persoMort = this.deplacerSurCase();
            }
        } else {
            persoMort = this.deplacerSurCase();
        }
        
        if (persoMort != null) {
            return persoMort;
        } else {
            return this.partie.getPersonnage(this.x, this.y);
        }
    }
    
    /* Réalise le déplacement du Monstre dans une direction donnée lorsqu'il ne 
     * se téléporte pas
     */
    private Personnage deplacerSurCase() {
        Personnage persoMort = null;
        Mur mur;
        
        this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
        switch (this.direction) {
            case Finstere.HAUT :
                mur = this.partie.getMur(this.x, this.y - 1);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    System.out.println("   Le Monstre pousse le Mur");
                }   this.y--;
                break;
            case Finstere.BAS :
                mur = this.partie.getMur(this.x, this.y + 1);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    System.out.println("   Le Monstre pousse le Mur");
                }   this.y++;
                break;
            case Finstere.GAUCHE :
                mur = this.partie.getMur(this.x - 1, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    System.out.println("   Le Monstre pousse le Mur");
                }   this.x--;
                break;
            case Finstere.DROITE :
                mur = this.partie.getMur(this.x + 1, this.y);
                if (mur != null) {
                    persoMort = mur.pousserMonstre(this.direction);
                    System.out.println("   Le Monstre pousse le Mur");
                }   this.x++;
                break;
            default :
                break;
        }
        this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
        System.out.println("   Le Monstre se déplace en (" + this.x 
            + "," + this.y +")");
            
        return persoMort;
    }
    
    /* Trouve la direction à donner au Monstre en fonction de la situation */
    private void trouverDirection(List<Personnage> _morts) {
        int ancienneDirection = this.direction;
        int distance = 16;
        
        /* Pour chaque Personnage visible */
        for (Map.Entry<Integer,Personnage> entry : this.persoVisibles(_morts).entrySet()) {
            
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
            if (this.direction == Finstere.HAUT)
                System.out.println("   Le Monstre se tourne vers le HAUT");
            if (this.direction == Finstere.BAS)
                System.out.println("   Le Monstre se tourne vers le BAS");
            if (this.direction == Finstere.GAUCHE)
                System.out.println("   Le Monstre se tourne vers la GAUCHE");
            if (this.direction == Finstere.DROITE)
                System.out.println("   Le Monstre se tourne vers la DROITE");
        }
    }
    
    /* Retourne les Personnage visibles avec leur direction par rapport au Monstre */
    private Map<Integer,Personnage> persoVisibles(List<Personnage> _morts) {
        HashMap<Integer,Personnage> persoV = new HashMap<>();

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
                            if (Math.abs(this.x - perso.getX()) < 
                                    Math.abs(this.x - persoV.get(dir)
                                        .getX())) 
                                persoV.put(dir, perso);
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
                            if (Math.abs(this.x - perso.getX()) < 
                                    Math.abs(this.x - persoV.get(dir)
                                        .getX())) 
                                persoV.put(dir, perso);
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
                            if (Math.abs(this.y - perso.getY()) < 
                                    Math.abs(this.y - persoV.get(dir)
                                        .getY())) 
                                persoV.put(dir, perso);
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
                            if (Math.abs(this.y - perso.getY()) < 
                                    Math.abs(this.y - persoV.get(dir)
                                        .getY())) 
                                persoV.put(dir, perso);
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
        switch (this.direction) {
            case Finstere.HAUT :
                return "M/\\";
            case Finstere.BAS :
                return "M\\/";
            case Finstere.GAUCHE :
                return "<<M";
            case Finstere.DROITE :
                return "M>>";
            default :
                return " M ";
        }
    }
}
