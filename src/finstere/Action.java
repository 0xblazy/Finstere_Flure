/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * @author nKBlaZy
 */
public class Action {
    /* Chaine décrivant l'action */
    private String action;
    /* Nom de la méthode */
    private String methodName;
    /* Méthode associée à l'action */
    private Method methode;
    /* Paramètres à passer à la méthode */
    private Object[] param;
    
    /* Constructeur */
    public Action(String _action, Class _class, String _methodeName, Object[] _param) {
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
    }
    
    /* Getters */
    public String getAction() {
        return this.action;
    }

    public Method getMethode() {
        return this.methode;
    }

    public Object[] getParam() {
        return this.param;
    }
}
