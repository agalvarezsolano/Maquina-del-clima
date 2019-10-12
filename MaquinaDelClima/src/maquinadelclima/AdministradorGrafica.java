/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadelclima;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author AGAS
 */


public class AdministradorGrafica {
    
    public boolean validarArchivo(String ruta) throws FileNotFoundException, IOException{
        File archivo = new File(ruta);
        
        if(archivo.exists()){
            StringTokenizer tokens;
            FileReader reader = new FileReader(archivo);
            BufferedReader br = new BufferedReader(reader);
            String linea = br.readLine();
            
            if(linea.equals("Datos Diarios: ")){
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"Archivo no valido para graficar");
                return false;
                        }
        }else{
            JOptionPane.showMessageDialog(null,"Error al encontrar el archivo");
            return false;
        }
    }
    
    public DefaultCategoryDataset leerDatos(String ruta, DatosGrafica dg, int parametro) throws FileNotFoundException, IOException{
        
        File archivo = new File(ruta);
        List<String> datos = new ArrayList<String>();
        DefaultCategoryDataset lectura = new DefaultCategoryDataset();
        
        if(archivo.exists()){
            StringTokenizer tokens;
            FileReader reader = new FileReader(archivo);
            BufferedReader br = new BufferedReader(reader);
            String linea = br.readLine();
            int medidas  = 0;
            
            while((linea = br.readLine()) != null){
                tokens = new StringTokenizer(linea, "|");                
                while(tokens.hasMoreTokens()){
                    datos.add(tokens.nextToken());
                }
                medidas+= 1;
                
            }
            medidas = medidas/2;
            lectura =  crearData(parametro, datos,medidas);
            return lectura;
            
        }else{
            JOptionPane.showMessageDialog(null,"Error al recolectar los datos");
            return null;
        }
    }

    private DefaultCategoryDataset crearData(int parametro, List<String> datos, int medidas) {
        
        DefaultCategoryDataset lectura = new DefaultCategoryDataset();
        int luz = 2;
        int hum = 4;
        int temp = 6;
        int hora = 0;

        while(medidas != 0){

            switch (parametro) {
                case 4:
                    lectura.addValue(Integer.parseInt(datos.get(luz)), "Luz", datos.get(hora));
                    lectura.addValue(Integer.parseInt(datos.get(hum)), "Humedad", datos.get(hora));
                    lectura.addValue(Integer.parseInt(datos.get(temp)), "Temperatura", datos.get(hora));
                    luz += 8;
                    hum += 8;
                    temp += 8;
                    medidas -= 1;
                    hora +=8;
                    break;    
                case 2:
                    lectura.addValue(Integer.parseInt(datos.get(luz)), "Luz", datos.get(hora));
                    luz += 8;
                    medidas -= 1;
                    hora +=8;
                    break;
                case 1:
                    lectura.addValue(Integer.parseInt(datos.get(hum)), "Humedad", datos.get(hora));
                    hum += 8;
                    medidas -= 1;
                    hora +=8;
                    break;
                case 0:
                    lectura.addValue(Integer.parseInt(datos.get(temp)), "Temperatura", datos.get(hora));
                    temp += 8;
                    medidas -= 1;
                    hora +=8;
                    break;
                default:
                    break;
            }

        }
        return lectura;
    }
    
    
}
