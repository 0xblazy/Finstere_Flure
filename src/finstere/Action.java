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
    /* Méthode associée à l'action */
    private Method methode;
    /* Paramètres à passer à la méthode */
    private Object[] param;
    
    /* Constructeur */
    public Action(String _action, Class _class, String _methodeName, Object[] _param) {
        this.action =_action;
        
        Class[] types = new Class[_param.length];
        for (int i = 0 ; i < _param.length ; i++) {
            if (_param[i].getClass().equals(Integer.class)) {
                types[i] = int.class;
            }
        }
        try {
            this.methode = _class.getMethod(_methodeName, types);
        } catch (Exception ex) {
            System.out.println(ex + Arrays.toString(_param));
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
