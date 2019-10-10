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

import java.util.StringTokenizer;

public class InterpreteMensaje {
    
    String luz;
    String temp;
    String hum;
    

    public void desfragmentador(String datos) {
        StringTokenizer st = new StringTokenizer(datos);//Clase para dividar datos
        luz = st.nextToken();
        temp = st.nextToken();
        hum = st.nextToken();
    }
    
    
}
