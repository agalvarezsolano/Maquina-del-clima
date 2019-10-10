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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AdministradorDatos {
    
    String fecha = getFechaActual();
    String hora = getHoraActual();
    String ruta =  "C:\\Datos del invernadero";
    String rutaC1 = ruta + "\\Datos predeterminados.txt";
    String rutaC2 = ruta + "\\Datos Diarios";
    String rutaC3 =  rutaC2 + "\\" + fecha + ".txt";
    
    public int VerficarCarpetaPrincipal(){
        File carpetaPrincipal= new File(ruta);
        
        if(carpetaPrincipal.exists()){
            //JOptionPane.showMessageDialog(null, "La carpeta principal ya existe");
            return 1;
            //verificacion de que exista carpeta principal.
                    
        }else{
            return 0;                   
        }
    }
    
    public void CrearCarpetaPrincipal(){
             
        File carpetaPrincipal= new File(ruta);
        
        if(VerficarCarpetaPrincipal() == 1){
            JOptionPane.showMessageDialog(null, "La carpeta principal ya existe");
            //verificacion de que exista carpeta principal.
                    
        }else{
            carpetaPrincipal.mkdirs();
            JOptionPane.showMessageDialog(null, "La carpeta principal creada");
                   
        }
    }
    
    public void CrearDatosPredeterminados() {
        
        File datosPredeterminados= new File(rutaC1);
        
        if(datosPredeterminados.exists()){
            JOptionPane.showMessageDialog(null,"El archivo ya existe");
            //verificacion de que carpeta principal.
                    
        }else{
            try {
                datosPredeterminados.createNewFile();
                JOptionPane.showMessageDialog(null, "Datos predeterminados creados");
                
            } catch (IOException ex) {
                Logger.getLogger(AdministradorDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        
    }
    
    public void CrearCarpetaSecundaria() {
        
        
        File carpetaSecundaria= new File(rutaC2);
        
        if(carpetaSecundaria.exists()){
           JOptionPane.showMessageDialog(null,"La carpeta secundaria ya existe");
            //verificacion de que carpeta principal.
                    
        }else{
            carpetaSecundaria.mkdirs();
            JOptionPane.showMessageDialog(null, "La carpeta secundaria creada");
        
        }
    }
    
    public void CrearDatosDiarios() {
        
        File datosDiarios = new File(rutaC3);
        
        if(datosDiarios.exists()){
            JOptionPane.showMessageDialog(null,"El archivo ya existe");
            //verificacion de que carpeta principal.
                    
        }else{
            try {
                datosDiarios.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(AdministradorDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        
    }
    
    public static String getFechaActual() {
    Date fecha= new Date();
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    String fecha1= formato.format(fecha);
    return fecha1;
    }
    
    public String getHoraActual() {
    LocalDateTime locaDate = LocalDateTime.now();
    int hours  = locaDate.getHour();
    int minutes = locaDate.getMinute();
    
    if (minutes < 10){
        String hora = (hours  + ":"+ "0" + minutes); 
        return hora;
    }
    else{
        String hora = (hours  + ":"+ minutes); 
        return hora;
    }
    }

    public void ActualizarDatosPredeterminados(Datos pd) throws IOException {
        
        File datosPredeterminados= new File(rutaC1);

        
        if(datosPredeterminados.exists()){
            
            String datos = ("Luz Min |" + pd.luzMin + "| Luz Max |" + pd.luzMax + "| Hum Min |" + pd.humMin + "| Hum Max |" + pd.humMax + "| Temp Min |" + pd.tempMin + "| Temp Max |" + pd.tempMax)+"|";
        
            try{
                FileWriter fw = new FileWriter(datosPredeterminados);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(datos);
                bw.close();

            }catch (IOException ex){
                JOptionPane.showMessageDialog(null,"Error al guardar datos predeterminados");
            }
                    
        }else{
            JOptionPane.showMessageDialog(null,"No existen predeterminados");
          
        }
    }
    
    public Datos ObtenerPredeterminados() throws FileNotFoundException, IOException{
        File datosPredeterminados = new File(rutaC1);
        Datos pd = new Datos();
        
        if(datosPredeterminados.exists()){
            StringTokenizer tokens;
            List<String> datos = new ArrayList<String>();

            FileReader reader = new FileReader(datosPredeterminados);
            BufferedReader br = new BufferedReader(reader);
            String linea = br.readLine();

            tokens = new StringTokenizer(linea, "|");

            while(tokens.hasMoreTokens()){
                datos.add(tokens.nextToken());     
            }
           
            pd.luzMin = datos.get(1);
            pd.luzMax = datos.get(3);
            pd.humMin = datos.get(5);
            pd.humMax = datos.get(7);
            pd.tempMin = datos.get(9);
            pd.tempMax = datos.get(11);
          
            return pd;
            
        }else{
            
            pd.luzMin = "0";
            pd.luzMax = "0";
            pd.humMin = "0";
            pd.humMax = "0";
            pd.tempMin = "0";
            pd.tempMax = "0";
            
            return pd;
        }
    }
    
    public void guardarDatos(Datos ac)
    {
        File datosActuales = new File(rutaC3);
        String horaGuardar = getHoraActual();
        
        if(datosActuales.exists()){
            
            String datos = (  horaGuardar + " | Luz |" + ac.luzMax + "| Humedad |" + ac.humMax + "| Temperatura |" + ac.tempMax +"| " + '\n' ) ; 
        
            try{
                FileWriter fw = new FileWriter(datosActuales, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append("\r\n");
                bw.write(datos);
                bw.close();
                

            }catch (IOException ex){
                JOptionPane.showMessageDialog(null,"Error al guardar datos");
            }
                    
        }else{
            JOptionPane.showMessageDialog(null,"No existen archivo de datos diarios");
          
        }

        
    }
    
}
