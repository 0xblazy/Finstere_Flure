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
public class Joueur {
    /* Nom du Joueur */
    private final String name;
    /* Personnages du Joueur */
    private Personnage[] personnages;
    /* Nombre de Personnage restant */
    private int nbRestant;
    /* Partie dans laquelle joue le Joueur */
    private Partie partie;
    
    /* Constructeur */
    public Joueur(String _name, Partie _partie, Personnage[] _personnages) {
        this.name = _name;
        this.partie = _partie;
        this.personnages = _personnages;
        this.nbRestant = 4;
    }
    
    /* Retour la liste de Personnages encore jouables */
    public Map<Integer,String> persoJouables() {
        HashMap<Integer,String> perso = new HashMap<>();
        
        /* Pour chaque Personnage du Joueur */
        for (int i = 0 ; i < this.personnages.length ; i++) {
            
            /* Si le Personnage n'est pas joué, pas mort, pas sorti */
            if (!this.personnages[i].isJoue() && !this.personnages[i].isRip() 
                    && !this.personnages[i].isExit()) perso.put(i + 1, "" 
                            + this.personnages[i]);
        }
        
        return perso;
    }
    
    /* Retourne le nombre de Personnage sortis */
    public int nbSortis() {
        int nb = 0;
        
        for (Personnage perso : this.personnages)  {
            if (perso.isExit()) nb++;
        }
        
        return nb;
    }
    
    /* Retourne le classement du dernier Personnage sorti */
    public int classementDernier() {
        int classement = 0;
        
        for (Personnage perso : this.personnages) {
            if (perso.getClassement() > classement) classement = perso.getClassement();
        }
        
        return classement;
    }
    
    /* Retourne le Joueur sous forme d'une chaîne de caractère */
    @Override
    public String toString() {
        /* Ajout du nom à la chaîne */
        String s = this.name + " :\n";
        
        /* Ajout des Personnages du Joueur à la chaîne */
        for (int i = 0 ; i < this.personnages.length ; i++) {
            s += "   " + personnages[i];
            if (i < this.personnages.length - 1) s += "\n";
        }
        
        return s;
    }
    
    /* Setters */
    public void setNbRestant() {
        this.nbRestant--;
    }
    
    
    /* Getters */
    public int getNbRestant() {
        return this.nbRestant;
    }

    public String getName() {
        return name;
    }
    
    public List<ImageIcon> getSortis() {
        ArrayList<ImageIcon> sortis = new ArrayList<>();
        
        for (Personnage perso : this.personnages)  {
            if (perso.isExit()) sortis.add(perso.getImageIconClair());
        }
        
        return sortis;
    }
}
