/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
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
        }
        return this.partie.personnageAt(this.x, this.y);
    }
    
    /* Trouve la direction à donner au Monstre en fonction de la situation */
    private void trouverDirection() {
        
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
