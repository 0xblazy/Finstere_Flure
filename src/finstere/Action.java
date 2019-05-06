/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.lang.reflect.Method;
import javax.swing.ImageIcon;

/**
 *
 * @author nKBlaZy
 */
public class Action {
    /* Chaine décrivant l'Action */
    private String action;
    /* Nom de la méthode */
    private String methodName;
    /* Méthode associée à l'Action */
    private Method methode;
    /* Paramètres à passer à la méthode */
    private Object[] param;
    /* Coût de l'Action */
    private int cout;
    
    /* Constructeur */
    public Action(String _action, Class _class, String _methodeName, 
            Object[] _param, int _cout) {
        this.action =_action;
        
        /* Définition des types des paramètres */
        Class[] types = new Class[_param.length];
        
        /* Pour chaque paramètre */
        for (int i = 0 ; i < _param.length ; i++) {
            
            /* Si le paramètre est de type Integer, on le défini de type int */
            if (_param[i].getClass().equals(Integer.class)) {
                types[i] = int.class;
            } else {
                types[i] = _param[i].getClass();
            }
        }
        
        this.methodName = _methodeName;
        
        /* Récupération de la méthode */
        try {
            this.methode = _class.getMethod(_methodeName, types);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        this.param = _param;
        this.cout = _cout;
    }
    
    /* Getters */
    public String getAction() {
        return this.action;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Method getMethode() {
        return this.methode;
    }

    public Object[] getParam() {
        return this.param;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon(getClass().getResource("/img/dep" + this.cout 
                + ".gif"));
    }
}
