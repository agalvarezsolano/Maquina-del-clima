/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadelclima;

/**
 *
 * @author AGAS
 */

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ControlVentanas {
    public void enableButton(JButton boton) {
        boton.setEnabled(true);
    }
    
        public void disableButton(JButton boton) {
        boton.setEnabled(false);
    }
        
        public void enableTextField(JTextField text) {
        text.setEditable(true);
    }
    
        public void disableTextField(JTextField text) {
        text.setEditable(false);
    }
        
    public void disableConnectionPanel(JButton boton, JComboBox combo,JComboBox combo1) {
        combo.setEnabled(false);
        combo1.setEnabled(false);
        boton.setEnabled(false);
    }
    
    public void enableConnectionPanel(JButton boton, JComboBox combo, JComboBox combo1) {
        combo.setEnabled(true);
        combo1.setEnabled(true);
        boton.setEnabled(true);
    }
    
}
