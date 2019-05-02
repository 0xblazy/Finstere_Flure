/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finstere;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author nKBlaZy
 */
public class ChoiceListener extends MouseAdapter {
    /* Finstere */
    private Finstere finstere;
    
    /* Constructeur */
    public ChoiceListener(Finstere _finstere) {
        this.finstere = _finstere;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel label = (JLabel) e.getSource();
            this.finstere.setChoix(Integer.parseInt(label.getName()));
            Partie partie = this.finstere.getPartie();
            synchronized(partie) {
                partie.notify();
            }
        } else if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.isEnabled()) {
                this.finstere.setChoix(Integer.parseInt(button.getName()));
                Partie partie = this.finstere.getPartie();
                button.setEnabled(false);
                synchronized(partie) {
                    partie.notify();
                }
            }
        }
        
        
    }
}
