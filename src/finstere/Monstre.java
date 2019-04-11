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
            this.trouverDirection();
            Personnage mort = this.deplacer();
            if (mort != null) morts.add(mort);
            nbDeplacement++;
        }
        this.trouverDirection();
        
        return morts;
    }
    
    /* Appelée lorsque la Carte piochée est un nombre de déplacement donné */
    private List<Personnage> deplacement(int _nbDeplacement) {
        ArrayList<Personnage> morts = new ArrayList<>();
        
        for (int i = 0 ; i < _nbDeplacement ; i++) {
            this.trouverDirection();
            Personnage mort = this.deplacer();
            if (mort != null) morts.add(mort);
        }
        this.trouverDirection();
        
        return morts;
    }
    
    /* Déplace le Monstre */
    private Personnage deplacer() {
        if (this.partie.getLabyrinthe().getCase(this.x, this.y).isBordure()) {
            Map<Integer, int[]> tp = this.partie.getLabyrinthe()
                    .getCase(this.x, this.y).getTp();
            if (tp.containsKey(this.direction)) {
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
                this.x = tp.get(this.direction)[0];
                this.y = tp.get(this.direction)[1];
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
                System.out.println("   Le Monstre se téléporte en (" + this.x 
                        + "," + this.y +")");
            } else {
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
                if (this.direction == Finstere.HAUT) {
                    this.y--;
                } else if (this.direction == Finstere.BAS) {
                    this.y++;
                } else if (this.direction == Finstere.GAUCHE) {
                    this.x--;
                } else if (this.direction == Finstere.DROITE) {
                    this.x++;
                }
                this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
                System.out.println("   Le Monstre se déplace en (" + this.x 
                        + "," + this.y +")");
            }
        } else {
            this.partie.getLabyrinthe().setMonstre(this.x, this.y, false);
            if (this.direction == Finstere.HAUT) {
                this.y--;
            } else if (this.direction == Finstere.BAS) {
                this.y++;
            } else if (this.direction == Finstere.GAUCHE) {
                this.x--;
            } else if (this.direction == Finstere.DROITE) {
                this.x++;
            }
            this.partie.getLabyrinthe().setMonstre(this.x, this.y, true);
            System.out.println("   Le Monstre se déplace en (" + this.x 
                    + "," + this.y +")");
        }
        return this.partie.personnageAt(this.x, this.y);
    }
    
    /* Trouve la direction à donner au Monstre en fonction de la situation */
    private void trouverDirection() {
        int ancienneDirection = this.direction;
        int distance = 16;
        
        /* Pour chaque Personnage visible */
        for (Map.Entry<Integer,Personnage> entry : this.persoVisibles().entrySet()) {
            
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
    private Map<Integer,Personnage> persoVisibles() {
        HashMap<Integer,Personnage> persoV = new HashMap<Integer, Personnage>();
        
        List<Personnage> persos = this.partie.persoAlignes(this.x, this.y);
        
        /* Pour chaque Personnage aligné */
        for (Personnage perso : persos) {
            
            /* Si le Personnage est a GAUCHE */
            if (perso.getX() < this.x) {
                int direction = Finstere.GAUCHE;
                boolean visible = (direction != -this.direction);
                int i = this.x - 1;
                while (visible && i > perso.getX()) {
                     visible = !this.partie.getLabyrinthe().isMur(i, this.y);
                     i--;
                }
                if (visible) {
                    if (persoV.containsKey(direction)) {
                        if (Math.abs(this.x - perso.getX()) < 
                                Math.abs(this.x - persoV.get(direction)
                                        .getX())) 
                            persoV.put(direction, perso);
                    } else {
                        persoV.put(direction, perso);
                    }
                }
            
            /* Si le Personnage est a DROITE */
            } else if (perso.getX() > this.x) {
                int direction = Finstere.DROITE;
                boolean visible = (direction != -this.direction);
                int i = this.x + 1;
                while (visible && i < perso.getX()) {
                     visible = !this.partie.getLabyrinthe().isMur(i, this.y);
                     i++;
                }
                if (visible) {
                    if (persoV.containsKey(direction)) {
                        if (Math.abs(this.x - perso.getX()) < 
                                Math.abs(this.x - persoV.get(direction)
                                        .getX())) 
                            persoV.put(direction, perso);
                    } else {
                        persoV.put(direction, perso);
                    }
                }
                
            /* Si le Personnage est en HAUT */
            } else if (perso.getY() < this.y) {
                int direction = Finstere.HAUT;
                boolean visible = (direction != -this.direction);
                int j = this.y - 1;
                while (visible && j > perso.getY()) {
                     visible = !this.partie.getLabyrinthe().isMur(this.x, j);
                     j--;
                }
                if (visible) {
                    if (persoV.containsKey(direction)) {
                        if (Math.abs(this.y - perso.getY()) < 
                                Math.abs(this.y - persoV.get(direction)
                                        .getY())) 
                            persoV.put(direction, perso);
                    } else {
                        persoV.put(direction, perso);
                    }
                }
                
            /* Si le Personnage est en BAS */
            } else if (perso.getY() > this.y) {
                int direction = Finstere.BAS;
                boolean visible = (direction != -this.direction);
                int j = this.y + 1;
                while (visible && j < perso.getY()) {
                     visible = !this.partie.getLabyrinthe().isMur(this.x, j);
                     j++;
                }
                if (visible) {
                    if (persoV.containsKey(direction)) {
                        if (Math.abs(this.y - perso.getY()) < 
                                Math.abs(this.y - persoV.get(direction)
                                        .getY())) 
                            persoV.put(direction, perso);
                    } else {
                        persoV.put(direction, perso);
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
}
