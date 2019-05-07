/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nKBlaZy
 */
public class Partie extends Thread{

    /* Vue de la Partie */
    private Finstere finstere;
    /* true si la Partie se déroule dans le terminal */
    private boolean inTerm;
    /* Labyrinthe de la Partie */
    private Labyrinthe labyrinthe;
    /* Flaques d'hémoglobine */
    private Hemoglobine hemoCarree, hemoLineH;
    /* Monstre (placé en (0,0))*/
    private Monstre monstre;
    /* Liste des Murs */
    private List<Mur> murs;
    /* Joueurs de la Partie */
    private Joueur[] joueurs;
    /* Personnages de tous les Joueurs */
    private Personnage[][] personnages;
    /* Nombre de vrais Joueurs */
    private int nbJoueurs;
    /* Index du Joueur commencant le tour */
    private int premierJoueur;
    /* Scanner pour la saisie dans la console */
    private final Scanner sc;
    /* Compteur pour le classement des Personnages */
    private int classement;
    /* Paquet de Carte */
    private Paquet paquet;
    /* Compteur pour le nombre de Personnages de chaque Joueur sortis durant un tour */
    private int sortisJ1, sortisJ2;
    /* Numéro de Tour et de Manche */
    private int nbTour, nbManche;
    /* Déplacement du Monstre */
    private String deplacementMonstre;

    /* Constructeur */
    public Partie(Finstere _fin, boolean _inTerm) {
        super("Partie");
        this.finstere = _fin;
        this.inTerm = _inTerm;
        this.labyrinthe = new Labyrinthe(this);
        this.murs = new ArrayList<>();
        this.joueurs = new Joueur[2];
        this.personnages = new Personnage[2][4];
        this.premierJoueur = 0;
        this.sc = new Scanner(System.in);
        this.classement = 1;
        this.paquet = new Paquet();
        this.sortisJ1 = 0;
        this.sortisJ2 = 0;
    }

    /* Initialisation de la Partie (création des Joueurs) */
    public void initPartie(int _nbJoueurs, String[] _noms, int[] _couleurs) {
        this.nbJoueurs = _nbJoueurs;
        
        /* Création des Joueurs */
        for (int i = 0 ; i < this.nbJoueurs ; i++) {
            this.personnages[i][0] = new Personnage(1, 6, 
                    Finstere.COULEURS[_couleurs[i]], this);
            this.personnages[i][1] = new Personnage(3, 4, 
                    Finstere.COULEURS[_couleurs[i]], this);
            this.personnages[i][2] = new Personnage(4, 3, 
                    Finstere.COULEURS[_couleurs[i]], this);
            this.personnages[i][3] = new Personnage(5, 2, 
                    Finstere.COULEURS[_couleurs[i]], this);

            this.joueurs[i] = new Joueur(_noms[i], this, 
                    this.personnages[i]);
            
        }
        
        /* Création du Bot */
        if (_nbJoueurs == 1) {
            int couleur = 0;
            while (couleur == _couleurs[0]) {
                couleur++;
            }
            
            this.personnages[1][0] = new Personnage(1, 6, 
                    Finstere.COULEURS[couleur], this);
            this.personnages[1][1] = new Personnage(3, 4, 
                    Finstere.COULEURS[couleur], this);
            this.personnages[1][2] = new Personnage(4, 3, 
                    Finstere.COULEURS[couleur], this);
            this.personnages[1][3] = new Personnage(5, 2, 
                    Finstere.COULEURS[couleur], this);

            this.joueurs[1] = new Joueur("Bot", this, 
                    this.personnages[1]);
        }
        
        this.genLaby();
    }
    
    /* Surcharge de initPartie lorsque la Partie se déroule dans le terminal */
    public void initPartie() {
        System.out.println("=== NOUVELLE PARTIE ===");

        /* Choix du nombre de Joueur humain */
        System.out.print("Nombre de joueurs humains (1 ou 2) : ");
        int nbJoueurs = this.scannerInt();
        while (nbJoueurs > 2 || nbJoueurs < 1) {
            System.out.print("Le nombre de joueurs humain doit être 1 ou 2\n"
                    + "Veuillez ressaisir un nombre : ");
            nbJoueurs = this.scannerInt();
        }

        /* Choix des couleurs */
        int couleurPremier = 0;
        String[] noms = new String[nbJoueurs];
        int[] couleurs = new int[nbJoueurs];
        for (int i = 0 ; i < nbJoueurs ; i++) {
            System.out.println("Joueur " + (i + 1) + " : ");
            System.out.print("Nom : ");
            noms[i] = this.sc.next();
            System.out.print("Couleurs\n   1 - Bleu\n   2 - Vert\n   3 - Rouge\n"
                    + "   4 - Jaune\nChoix : ");
            couleurs[i] = this.scannerInt();
            while (couleurs[i] < 1 || couleurs[i] > 7) {
                System.out.print("Choix incorrecte, ressaisissez un nombre : ");
                couleurs[i] = this.scannerInt();
            }
            if (i == 0) {
                couleurPremier = couleurs[i];
            } else {
                while (couleurPremier == couleurs[i]) {
                    System.out.print("Cette couleur est déjà prise...\n"
                            + "Choisissez en une autre : ");
                    couleurs[i] = this.scannerInt();
                }
            }
            couleurs[i]--;
        }
        this.initPartie(nbJoueurs, noms, couleurs);
    }

    /* Génère le Labyrinthe et dispose les Pions dessus */
    private void genLaby() {
        if (this.inTerm) {
            System.out.println("\n...Génération du plateau de Jeu...\n");
        }

        /* Génération du Labyrinthe */
        this.labyrinthe.initLaby();

        /* Ajout de la flaque carrée */
        this.hemoCarree = new Hemoglobine(8, 2, Finstere.CARRE, this);
        this.labyrinthe.setHemoglobine(8, 2, true);
        this.labyrinthe.setHemoglobine(9, 2, true);
        this.labyrinthe.setHemoglobine(8, 3, true);
        this.labyrinthe.setHemoglobine(9, 3, true);

        /* Ajout de la flaque linéaire horizontale */
        this.hemoLineH = new Hemoglobine(4, 8, Finstere.LINEHORI, this);
        this.labyrinthe.setHemoglobine(4, 8, true);
        this.labyrinthe.setHemoglobine(5, 8, true);
        this.labyrinthe.setHemoglobine(6, 8, true);
        this.labyrinthe.setHemoglobine(7, 8, true);

        /* Ajout des Murs */
        this.murs.add(new Mur(2, 2, this));
        this.labyrinthe.setMur(2, 2, true);
        this.murs.add(new Mur(12, 3, this));
        this.labyrinthe.setMur(12, 3, true);
        this.murs.add(new Mur(7, 4, this));
        this.labyrinthe.setMur(7, 4, true);
        this.murs.add(new Mur(8, 5, this));
        this.labyrinthe.setMur(8, 5, true);
        this.murs.add(new Mur(13, 5, this));
        this.labyrinthe.setMur(13, 5, true);
        this.murs.add(new Mur(6, 6, this));
        this.labyrinthe.setMur(6, 6, true);
        this.murs.add(new Mur(4, 7, this));
        this.labyrinthe.setMur(4, 7, true);
        this.murs.add(new Mur(12, 7, this));
        this.labyrinthe.setMur(12, 7, true);
        this.murs.add(new Mur(14, 8, this));
        this.labyrinthe.setMur(14, 8, true);
        this.murs.add(new Mur(5, 9, this));
        this.labyrinthe.setMur(5, 9, true);
        this.murs.add(new Mur(8, 9, this));
        this.labyrinthe.setMur(8, 9, true);

        /* Ajout du Monstre */
        this.monstre = new Monstre(this);
        this.labyrinthe.setMonstre(0, 0, true);
    }

    /* Boucle de jeu */
    @Override
    public void run() {
        if (this.inTerm) {
            System.out.println("La partie commence !!!\nBonne chance à vous !!!\n");
        }
        this.nbTour = 1;
        this.nbManche = 1;
        Joueur gagnant = null;

        /* Boucle pour la Partie */
        while (this.nbTour < 15 && this.persoRestants() > 0 && gagnant == null) {
            this.deplacementMonstre = "";
            int maxJouer = 4;
            if (this.nbTour == 1) {
                maxJouer = 2;
            }

            if (this.nbTour == 1 || this.nbTour == 8) {
                this.paquet.melanger();
                this.paquet.resetPioche();
            }
            
            if (this.nbTour == 8) {
                this.nbManche++;
            }
            
            if (!this.inTerm) {
                if (this.premierJoueur == 0) {
                    this.finstere.premierJ1();
                } else {
                    this.finstere.premierJ2();
                }
            }

            /* Boucle pour un Tour */
            int nbJouer = 0;
            while (nbJouer < maxJouer && gagnant == null) {
                /* Condition en fonction du premier Joueur qui joue */
                if (this.premierJoueur == 0) {
                    gagnant = this.tourJ1(nbJouer);
                } else {
                    gagnant = this.tourJ2(nbJouer);
                }
                nbJouer++;
            }
            
            /* Décrémentation du nombre de Personnages jouables en fonction de
             * sortisJ1 et sortisJ2 
             */
            while (this.sortisJ1 > 0) {
                this.joueurs[0].setNbRestant();
                this.sortisJ1--;
            }
            while (this.sortisJ2 > 0) {
                this.joueurs[1].setNbRestant();
                this.sortisJ2--;
            }

            /* Fin de tour pour tous les Personnages */
            for (Personnage[] persos : this.personnages) {
                for (Personnage perso : persos) {
                    perso.finTour();
                }
            }

            /* Tour du Monstre uniquement si la Partie n'est pas encore gagnée */
            if (gagnant == null) {
                if (this.inTerm) System.out.println("Le Monstre se déplace...");
                Carte carte = this.paquet.tirerCarte();
                if (this.inTerm) {
                    System.out.println("Carte tirée : " + carte);
                } else {
                    this.finstere.updatePiocheDefausse(this.paquet);
                }
                List<Personnage> morts = this.monstre.tour(carte);
                
                
                if (this.inTerm) {
                    System.out.println(this.deplacementMonstre);
                } else {
                    this.finstere.deplacementMonstre(carte, 
                            this.deplacementMonstre, morts);
                }

                if (morts.size() > 0) {
                    if (this.inTerm) System.out.println("Morts :");
                    for (Personnage perso : morts) {
                        for (int indexJoueur = 0; indexJoueur < 2; indexJoueur++) {
                            for (int indexPerso = 0; indexPerso < 4; indexPerso++) {
                                if (this.personnages[indexJoueur][indexPerso].equals(perso)) {
                                    this.personnages[indexJoueur][indexPerso].tue(this.nbTour);
                                    if (nbTour > 7) {
                                        this.joueurs[indexJoueur].setNbRestant();
                                    }
                                }
                            }
                        }
                        if (this.inTerm) System.out.println("   " + perso);
                    }
                } else {
                    if (this.inTerm )System.out.println("Aucun mort");
                }
                
                if (!this.inTerm) {
                    this.finstere.updateMonstre();
                    this.finstere.updatePersos();
                    this.finstere.updateListesPersos();
                    this.finstere.updateMurs();
                }

            }
            
            if (this.inTerm) {
                System.out.println("");
                System.out.println("");
            }

            nbTour++;
            this.premierJoueur = (this.premierJoueur + 1) % 2;
        }
        
        /* Affichage ou récupération du gagnant en fonction de la situation */
        if (gagnant != null) {
            if (this.inTerm) {
                System.out.println("VICTOIREEEEEEEEE !!!!");
                System.out.println(gagnant.getName() + " a réussi à faire "
                        + "sortir 3 de ses Personnages et remporte donc la Partie");
            } else {
                this.finstere.victoire(gagnant.getName() + " a réussi à faire "
                        + "sortir 3 de ses Personnages et remporte donc la Partie",
                        gagnant);
            }
        } else {
            gagnant = this.gagnant();
            if (gagnant != null) {
                if (this.inTerm) {
                    System.out.println("VICTOIREEEEEEEEE !!!!");
                    System.out.println(gagnant.getName() + " a réussi à faire sortir "
                        + gagnant.nbSortis() + " de ses Personnages en premier"
                        + " et remporte donc la Partie");
                } else {
                    this.finstere.victoire(gagnant.getName() + " a réussi à faire sortir "
                        + gagnant.nbSortis() + " de ses Personnages en premier"
                        + " et remporte donc la Partie", gagnant);
                }
            } else {
                if (this.inTerm) {
                    System.out.println("ÉGALITÉ...");
                    System.out.println("Personne n'a réussi à faire sortir de Personnage...");
                } else {
                    this.finstere.victoire("Personne n'a réussi à faire sortir de Personnage...",
                            gagnant);
                }
            }
        }
        
        /* Demande de nouvelle Partie si la Partie se déroule dans le Terminal */
        if (this.inTerm) {
            System.out.print("Voulez-vous refaire une Partie ? (1) Oui (2) Non : ");
            int choix = this.scannerInt();
            while (choix < 1 || choix > 2) {
                System.out.print("Choix indisponible, veuillez en saisir un autre : ");
                choix = this.scannerInt();
            }
            if (choix == 1) {
                this.finstere.nouvellePartie();
            } else {
                System.exit(0);
            }
        }
    }

    /* Tour si le Joueur 1 commence */
    private Joueur tourJ1(int _nbJouer) {
        /* Boucle pour chaque Joueur */
        for (int indexJoueur = 0; indexJoueur < this.joueurs.length; indexJoueur++) {
            Joueur joueur = this.coupJoueur(_nbJouer, indexJoueur);
            if (joueur != null) {
                return joueur;
            }
        }
        return null;
    }

    /* Tour si le Joueur 2 commence */
    private Joueur tourJ2(int _nbJouer) {
        /* Boucle pour chaque Joueur */
        for (int indexJoueur = 1; indexJoueur > -1; indexJoueur--) {
            Joueur joueur = this.coupJoueur(_nbJouer, indexJoueur);
            if (joueur != null) {
                return joueur;
            }
        }
        return null;
    }

    /* Coup pour un Joueur */
    private synchronized Joueur coupJoueur(int _nbJouer, int _indexJoueur) {
        /* Si le Joueur a encore des Personnages jouables */
        if (_nbJouer < this.joueurs[_indexJoueur].getNbRestant()) {

            /* Affichage des informations */
            if (this.inTerm) {
                if (this.nbTour < 8) {
                System.out.println("== MANCHE 1 - TOUR " + this.nbTour
                        + " ==\n");
                } else {
                System.out.println("== MANCHE 2 - TOUR "
                        + (this.nbTour - 7) + " ==\n");
                }
                this.afficherLaby();
                System.out.println("\n" + this.joueurs[_indexJoueur] + "\n");
            } else {
                this.finstere.updateInfo();
                if (_indexJoueur == 0) {
                    this.finstere.tourJ1();
                } else {
                    this.finstere.tourJ2();
                }
            }
            

            /* Choix du Personnage */
            int indexPerso;
            if (this.inTerm) {
                System.out.println("Choix du Personnage");
                Map<Integer, String> persoJouables = this.joueurs[_indexJoueur].persoJouables();
                for (Map.Entry<Integer, String> entry : persoJouables.entrySet()) {
                    System.out.println("   " + entry.getKey() + " => "
                        + entry.getValue());
                }
                System.out.print("Choix : ");
                indexPerso = this.scannerInt();
                while (!persoJouables.containsKey(indexPerso)) {
                    System.out.print("Personnage non disponible, veuillez"
                            + " en choisir un autre : ");
                    indexPerso = this.scannerInt();
                }
                while (this.personnages[_indexJoueur][indexPerso - 1].getActions().isEmpty()) {
                    System.out.print("Aucune action possible avec ce Personnage "
                            + "pour le moment, veuillez en choisir un autre : ");
                    indexPerso = this.scannerInt();
                }
                indexPerso--;
                System.out.println("");
            } else {
                this.finstere.afficherChoixPerso(_indexJoueur);
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Partie.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.finstere.cleanChoixPerso();
                indexPerso = this.finstere.getChoix();
            }

            /* Actions avec le Personnage */
            boolean continuer = true;
            while (continuer) {
                Map<Integer, Action> actions = this.personnages[_indexJoueur][indexPerso].getActions();
                int choix = 1;
                if (this.inTerm) {
                    System.out.println("Actions possibles ("
                        + this.personnages[_indexJoueur][indexPerso].getPm()
                        + " pm restants)");
                    for (Map.Entry<Integer, Action> entry : actions.entrySet()) {
                        System.out.println("   " + entry.getKey()
                                + " => " + entry.getValue().getAction());
                    }
                    System.out.print("Choix : ");
                    choix = this.scannerInt();
                    while (!actions.containsKey(choix)) {
                        System.out.print("Action non disponible, veuillez"
                                + " en choisir une autre : ");
                        choix = this.scannerInt();
                    }
                } else {
                    this.finstere.afficherChoixAction(this.personnages[_indexJoueur][indexPerso]);
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Partie.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.finstere.cleanChoixAction();
                    choix = this.finstere.getChoix();
                }
                Action action = actions.get(choix);
                try {
                    continuer = (boolean) action.getMethode()
                            .invoke(this.personnages[_indexJoueur][indexPerso],
                                    action.getParam());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                
                if (!this.inTerm) {
                    this.finstere.updatePersos();
                    if (action.getMethodName().equals("pousserMur") ||
                            action.getMethodName().equals("glisser")) {
                        this.finstere.updateMurs();
                    }
                }
                
                if (this.personnages[_indexJoueur][indexPerso].isExit()) {
                    if (_indexJoueur == 0) {
                        this.sortisJ1++;
                    } else {
                        this.sortisJ2++;
                    }
                }

                if (continuer && this.inTerm) {
                    System.out.println("");
                    this.afficherLaby();
                }
                
                if (!continuer && !this.inTerm) {
                    this.finstere.updateListesPersos();
                }
                
                if (this.inTerm) System.out.println("");
            }
        }
        return this.gagnant();
    }

    /* Retourne le nombre de Personnages restants */
    private int persoRestants() {
        return this.joueurs[0].getNbRestant() + this.joueurs[1].getNbRestant();
    }

    /* Retourne le Joueur gagnant (null sinon) */
    private Joueur gagnant() {
        int nbJ1 = this.joueurs[0].nbSortis();
        int nbJ2 = this.joueurs[1].nbSortis();

        /* Si tous les Personnages sont sortis ou morts ou si la Partie est finie */
        if (this.persoRestants() == 0 || this.nbTour == 15) {

            /* Si les 2 Joueurs ont sortis autant de Personnage */
            if (nbJ1 == nbJ2) {
                if (this.joueurs[0].classementDernier() < this.joueurs[1].classementDernier()) {
                    return this.joueurs[0];
                } else if (this.joueurs[0].classementDernier() > this.joueurs[1].classementDernier()){
                    return this.joueurs[1];
                } else {
                    return null;
                }
            } else if (nbJ1 > nbJ2) {
                return this.joueurs[0];
            } else {
                return this.joueurs[1];
            }

        /* Si le Joueur 1 a sorti 3 Personnages */
        } else if (nbJ1 == 3) {
            return this.joueurs[0];

        /* Si le Joueur 2 a sorti 3 Personnages */
        } else if (nbJ2 == 3) {
            return this.joueurs[1];
        }

        return null;
    }

    /* Retourne la liste des Personnages alignés avec les coordonnées (_x,_y) */
    public List<Personnage> persoAlignes(int _x, int _y) {
        ArrayList<Personnage> persos = new ArrayList<>();

        for (int indexJoueur = 0; indexJoueur < 2; indexJoueur++) {
            for (int indexPerso = 0; indexPerso < 4; indexPerso++) {
                if ((this.personnages[indexJoueur][indexPerso].getX() == _x
                        || this.personnages[indexJoueur][indexPerso].getY() == _y)
                        && this.personnages[indexJoueur][indexPerso].getX() > -1
                        && this.personnages[indexJoueur][indexPerso].getX() < 16
                        && this.personnages[indexJoueur][indexPerso].getY() > -1
                        && this.personnages[indexJoueur][indexPerso].getY() < 11) {
                    persos.add(this.personnages[indexJoueur][indexPerso]);
                }
            }
        }

        return persos;
    }

    /* Supprime le Mur passé en paramètre */
    public void supprimerMur(Mur _mur) {
        this.murs.remove(_mur);
    }

    /* Retourne la liste des flaques d'Hemoglobine avec lesquelles un Pion aux 
     * coordonnées (_x,_y) peut interagir
     */
    public List<Hemoglobine> interactionsHemoglobine(int _x, int _y) {
        ArrayList<Hemoglobine> hemo = new ArrayList<>();

        if (this.hemoCarree != null) {
            if (Finstere.isInList(this.hemoCarree.zoneInteraction(), new int[]{_x, _y})) {
                hemo.add(this.hemoCarree);
            }
        }
        if (this.hemoLineH != null) {
            if (Finstere.isInList(this.hemoLineH.zoneInteraction(), new int[]{_x, _y})) {
                hemo.add(this.hemoLineH);
            }
        }
        
        return hemo;
    }

    /* Ajoute la chaine _dep aux déplacements du Monstre */
    public void addDeplacement(String _dep) {
        if (this.deplacementMonstre.equals("")) {
            this.deplacementMonstre += "   " + _dep;
        } else {
            this.deplacementMonstre += "\n   " + _dep;
        }
    }
    
    /* Affiche le Labyrinthe */
    public void afficherLaby() {
        System.out.println(this.labyrinthe);
    }

    /* Affiche la liste des Joueurs avec leurs Personnages */
    public void afficherJoueurs() {
        for (Joueur joueur : this.joueurs) {
            System.out.println(joueur);
        }
    }

    /* Protection du Scanner (saisie obligatoire d'un int) */
    private int scannerInt() {
        int n = -1;
        boolean valide = false;

        do {
            if (this.sc.hasNextInt()) {
                n = this.sc.nextInt();
                valide = true;
            } else {
                System.out.print("Veuillez saisir un nombre : ");
                this.sc.next();
            }
        } while (!valide);

        return n;
    }

    /* Getters */
    public Personnage getPersonnage(int _x, int _y) {
        for (Personnage[] persos : this.personnages) {
            for (Personnage perso : persos) {
                if (perso.getX() == _x && perso.getY() == _y && !perso.isEnDessous()) {
                    return perso;
                }
            }
        }
        return null;
    }

    public Personnage getPersonnageEnDessous(int _x, int _y) {
        for (Personnage[] persos : this.personnages) {
            for (Personnage perso : persos) {
                if (perso.getX() == _x && perso.getY() == _y && perso.isEnDessous()) {
                    return perso;
                }
            }
        }
        return null;
    }

    public Mur getMur(int _x, int _y) {
        for (int i = 0; i < this.murs.size(); i++) {
            if (this.murs.get(i).getX() == _x
                    && this.murs.get(i).getY() == _y) {
                return this.murs.get(i);
            }
        }
        return null;
    }

    public List<Mur> getMurs() {
        return this.murs;
    }
    
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }

    public Monstre getMonstre() {
        return this.monstre;
    }

    public int getClassement() {
        return this.classement;
    }

    public Hemoglobine getHemoglobine(int _x, int _y) {
        if (this.hemoCarree != null) {
            if (this.hemoCarree.isHere(_x, _y)) {
                return this.hemoCarree;
            }
        }
        if (this.hemoLineH != null) {
            if (this.hemoLineH.isHere(_x, _y)) {
                return this.hemoLineH;
            }
        }
        return null;
    }

    public Hemoglobine getHemoCarree() {
        return this.hemoCarree;
    }

    public Hemoglobine getHemoLineH() {
        return this.hemoLineH;
    }
    
    public Personnage[][] getPersonnages() {
        return this.personnages;
    }

    public int getNbManche() {
        return this.nbManche;
    }

    public int getNbTour() {
        if (this.nbManche == 1) {
            return this.nbTour;
        } else {
            return this.nbTour - 7;
        }
    }

    /* Setters */
    public void setClassement() {
        this.classement++;
    }
}
