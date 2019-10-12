/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadelclima;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author AGAS
 */
public class DatosGrafica {
    
    String nombre = "";
    public static DefaultCategoryDataset datosTemp = new DefaultCategoryDataset();
    public static DefaultCategoryDataset datosHum = new DefaultCategoryDataset();
    public static DefaultCategoryDataset datosLuz = new DefaultCategoryDataset();

    public static DefaultCategoryDataset getDatosTemp() {
        return datosTemp;
    }

    public static DefaultCategoryDataset getDatosHum() {
        return datosHum;
    }

    public static DefaultCategoryDataset getDatosLuz() {
        return datosLuz;
    }

    public DatosGrafica() {
    }
    
}
