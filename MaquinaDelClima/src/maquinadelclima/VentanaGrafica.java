/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadelclima;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtilities; 





/**
 *
 * @author AGAS
 */

public class VentanaGrafica extends javax.swing.JFrame {

    /**
     * Creates new form VentanaGrafica
     */
    
    AdministradorGrafica ad = new AdministradorGrafica();
    ControlVentanas cv = new ControlVentanas();
    String ruta = "";
    DatosGrafica dg = new DatosGrafica();
    JFreeChart  grafica;
    String nombre = "";
    String fecha = "";
    
    public VentanaGrafica() {
        initComponents();       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        panelPrincipal = new javax.swing.JPanel();
        etiquetaRuta = new javax.swing.JLabel();
        etiquetaDireccionDelArchivo = new javax.swing.JLabel();
        botonBuscar = new javax.swing.JButton();
        etiquetaTipoDeGrafica = new javax.swing.JLabel();
        etiquetaParametro = new javax.swing.JLabel();
        tipoDeDatos = new javax.swing.JComboBox<>();
        botonGraficar = new javax.swing.JButton();
        etiquetaLinealAPuntos = new javax.swing.JLabel();
        etiquetaRevidando = new javax.swing.JLabel();
        botonGuardarGrafica = new javax.swing.JButton();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Grafica");
        setName("VentanaPrincipal"); // NOI18N

        etiquetaRuta.setText("Buscar archivo...");

        etiquetaDireccionDelArchivo.setText("Dirección del archivo:");

        botonBuscar.setText("Buscar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        etiquetaTipoDeGrafica.setText("Tipo de Grafica: ");

        etiquetaParametro.setText("Parametro a graficar:");

        tipoDeDatos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Temperatura", "Humedad", "Luz", "Viento", "Todas" }));

        botonGraficar.setText("Graficar");
        botonGraficar.setEnabled(false);
        botonGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGraficarActionPerformed(evt);
            }
        });

        etiquetaLinealAPuntos.setText("Lineal a puntos");

        botonGuardarGrafica.setText("Guardar Grafico");
        botonGuardarGrafica.setEnabled(false);
        botonGuardarGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarGraficaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(botonGuardarGrafica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonGraficar))
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                            .addComponent(etiquetaParametro)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tipoDeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                            .addComponent(etiquetaTipoDeGrafica)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(etiquetaLinealAPuntos))
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                            .addComponent(etiquetaDireccionDelArchivo)
                            .addGap(18, 18, 18)
                            .addComponent(botonBuscar))
                        .addComponent(etiquetaRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(etiquetaRevidando)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaDireccionDelArchivo)
                    .addComponent(botonBuscar))
                .addGap(18, 18, 18)
                .addComponent(etiquetaRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaRevidando)
                .addGap(74, 74, 74)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaTipoDeGrafica)
                    .addComponent(etiquetaLinealAPuntos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoDeDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGraficar)
                    .addComponent(botonGuardarGrafica))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        
        //Creamos el objeto JFileChooser
        JFileChooser fc=new JFileChooser();
 
        //Indicamos lo que podemos seleccionar
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        //Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
 
        //Le indicamos el filtro
        fc.setFileFilter(filtro);

        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fc.showOpenDialog(jFileChooser1);

        //Si el usuario, pincha en aceptar
        if(seleccion==JFileChooser.APPROVE_OPTION){

            //Seleccionamos el fichero
            File fichero=fc.getSelectedFile();

            //Ecribe la ruta del fichero seleccionado en el campo de texto
            fecha = fichero.getName();
            ruta = fichero.getAbsolutePath();
            etiquetaRuta.setText(ruta);
            etiquetaRevidando.setText("Revisando...");
            try {
                if(ad.validarArchivo(ruta) == true){
                    etiquetaRevidando.setText("Archivo valido");
                    cv.enableButton(botonGraficar);
                    cv.enableButton(botonGuardarGrafica);
                }else{
                    etiquetaRevidando.setText("Archivo invalido");
                    cv.disableButton(botonGraficar);
                    cv.disableButton(botonGuardarGrafica);
                }
            } catch (IOException ex) {
                Logger.getLogger(VentanaGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_botonBuscarActionPerformed

    private void botonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGraficarActionPerformed
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        try {
            datos = ad.leerDatos(ruta, dg, tipoDeDatos.getSelectedIndex());
            
        } catch (IOException ex) {
            Logger.getLogger(VentanaGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switch(tipoDeDatos.getSelectedIndex()){
            case 0:
                nombre = "Temperatura";
                break;
            case 1:
                nombre = "Humedad";
                break;
            case 2:
                nombre ="Luz";
                break;
            case 3: 
                nombre = "Viento";
                break;
            case 4:
                nombre = "Todos los parametros";
                break;
            default:
                break;
            
        }
        crearGrafica(datos,nombre);       
    }//GEN-LAST:event_botonGraficarActionPerformed

    private void botonGuardarGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarGraficaActionPerformed
        //Creamos el objeto JFileChooser
        JFileChooser fc=new JFileChooser();
        //Indicamos lo que podemos seleccionar
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JPEG", "jpeg");
 
        //Le indicamos el filtro
        fc.setFileFilter(filtro);
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fc.showOpenDialog(jFileChooser1);
        //Si el usuario, pincha en aceptar
        if(seleccion==JFileChooser.APPROVE_OPTION){
            //Seleccionamos el fichero
            File fichero=fc.getSelectedFile();
            String ubicacion = fichero.getAbsolutePath();
            int width = 800;
            int height = 600;
           File lineChart = new File(ubicacion + "\\"+"Grafico de " + nombre + " "+ fecha+".jpeg");
            try {
                ChartUtilities.saveChartAsJPEG(lineChart, grafica, width,height);
            } catch (IOException ex) {
                Logger.getLogger(VentanaGrafica.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Grafica guardada");
        }
        
    }//GEN-LAST:event_botonGuardarGraficaActionPerformed
    
    public void crearGrafica(DefaultCategoryDataset datos, String nombre) { 
        
        grafica = ChartFactory.createLineChart("Datos diarios", "Hora", nombre, datos,PlotOrientation.VERTICAL,true,true,false);         
        ChartFrame Ventana = new ChartFrame("Grafica",grafica);
        Ventana.pack();
        Ventana.setVisible(true);
    }
    /**
     * @param args the command line arguments
    **/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonGraficar;
    private javax.swing.JButton botonGuardarGrafica;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JLabel etiquetaDireccionDelArchivo;
    private javax.swing.JLabel etiquetaLinealAPuntos;
    private javax.swing.JLabel etiquetaParametro;
    public javax.swing.JLabel etiquetaRevidando;
    private javax.swing.JLabel etiquetaRuta;
    private javax.swing.JLabel etiquetaTipoDeGrafica;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JPanel panelPrincipal;
    public javax.swing.JComboBox<String> tipoDeDatos;
    // End of variables declaration//GEN-END:variables


}
