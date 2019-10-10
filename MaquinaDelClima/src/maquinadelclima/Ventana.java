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

/*
*Librerias utilizadas en la ventana
*/

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.sun.org.apache.xpath.internal.axes.SelfIteratorNoPredicate;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Ventana extends javax.swing.JFrame {

    /**
     * Creates new form Ventana
     */
    PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    PanamaHitek_Arduino ArduinoSensor = new PanamaHitek_Arduino();
    PanamaHitek_Arduino ArduinoMotor = new PanamaHitek_Arduino();
    ControlVentanas cv = new ControlVentanas();
    InterpreteMensaje im = new InterpreteMensaje();
    AdministradorDatos ad = new AdministradorDatos();
    DatosPredeterminados pd = new DatosPredeterminados();
    
    
    public Ventana() throws IOException{
        initComponents();
        getPorts();
        revisarDatos();
        actualizarPredeterminados();
    }
        
    
    SerialPortEventListener escuchar = new SerialPortEventListener() {
        
        
        @Override
        public void serialEvent(SerialPortEvent spe) {
                try {
                    if (ArduinoSensor.isMessageAvailable()){
                        im.desfragmentador(ArduinoSensor.printMessage());
                        LuzValor.setText(im.luz + "%");
                        TInternaValor.setText(im.temp + " *C");
                        HInternaValor.setText(im.hum + "%"); 
                        verificar();
                        //guardarDatos();
                    }
                }catch(SerialPortException | ArduinoException ex){
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE,null, ex);
                }
        }
    };

    private void verificar() throws ArduinoException {
        
        //1 = apaga, 0 = enciende;
        int valor_Luz = Integer.parseInt(im.luz);
        int valor_Luz_Pre_Min = Integer.parseInt(pd.luzMin);
        int valor_Luz_Pre_Max = Integer.parseInt(pd.luzMax);
        
        if(valor_Luz > valor_Luz_Pre_Max){
            try {
                   ArduinoMotor.sendData("0");
               } catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
        } else if (valor_Luz < valor_Luz_Pre_Min){
            try {
                   ArduinoMotor.sendData("0");
               } catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
        }else{
            try {
                   ArduinoMotor.sendData("1");
               } catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
        
    }
    
    /*int valor_Luz = Integer.parseInt(im.luz);
        int valor_Luz_Pre_Min = Integer.parseInt(pd.luzMin);
        int valor_Luz_Pre_Max = Integer.parseInt(pd.luzMax);
        
           if(valor_Luz_Pre_Min <= valor_Luz && valor_Luz_Pre_Max <= valor_Luz){
               try {
                   ArduinoMotor.sendData("0");
               } catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
           }else{
               if(valor_Luz_Pre_Max < valor_Luz){
               try {
                   ArduinoMotor.sendData("1");
                }catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
               }else{
               try {
                   ArduinoMotor.sendData("1");
                   System.out.println("Minimo");
                }catch (SerialPortException ex) {
                   Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
               }
               }}*/
               



    private void guardarDatos()
    {
        DateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
        String hora = horaFormato.toString();
        StringTokenizer tokens;
        List<String> datoHora = new ArrayList<String>();
        tokens = new StringTokenizer(hora, ":");

        while(tokens.hasMoreTokens()){
            datoHora.add(tokens.nextToken());     
        }
        System.out.println(hora);
       // int minuto = Integer.parseInt(datoHora.get(1));
        //System.out.println(minuto);
        
    }
    
    private void revisarDatos() throws IOException{
        
        DatosPredeterminados dp = ad.ObtenerPredeterminados();
        
        PreLuzValorMin.setText(dp.luzMin);
        PreTempValorMin.setText(dp.tempMin);
        PreHumValorMin.setText(dp.humMin);
        PreLuzValorMax.setText(dp.luzMax);
        PreTempValorMax.setText(dp.tempMax);
        PreHumValorMax.setText(dp.humMax);
        
    }
    
    private void actualizarPredeterminados() throws IOException{
        
        pd.luzMin = PreLuzValorMin.getText();
        pd.tempMin = PreTempValorMin.getText();
        pd.humMin = PreHumValorMin.getText();
        pd.luzMax = PreLuzValorMax.getText();
        pd.tempMax = PreTempValorMax.getText();
        pd.humMax = PreHumValorMax.getText();
        
        ad.ActualizarDatosPredeterminados(pd);
    }
    
    public void getPorts() {
        PuertoSensores.removeAllItems();
        PuertoMotor.removeAllItems();
        if (Arduino.getPortsAvailable() > 0) {
            Arduino.getSerialPorts().forEach(i -> PuertoSensores.addItem(i));
            Arduino.getSerialPorts().forEach(i -> PuertoMotor.addItem(i));
            Conexion.setEnabled(true);
            Conexion.setBackground(new Color(255, 255, 255));
        } else {
            Conexion.setEnabled(false);
            Conexion.setBackground(new Color(204, 204, 204));
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Datos = new javax.swing.JPanel();
        TemperarutaTexto = new javax.swing.JLabel();
        HumedadTexto = new javax.swing.JLabel();
        LuminocidadTexto = new javax.swing.JLabel();
        VientoTexto = new javax.swing.JLabel();
        TInternaTexto = new javax.swing.JLabel();
        TExternaTexto = new javax.swing.JLabel();
        TExternaValor = new javax.swing.JTextField();
        TInternaValor = new javax.swing.JTextField();
        HInternaTexto = new javax.swing.JLabel();
        HExternaTexto = new javax.swing.JLabel();
        HInternaValor = new javax.swing.JTextField();
        HExternaValor = new javax.swing.JTextField();
        LuzValor = new javax.swing.JTextField();
        VelocidadTexto = new javax.swing.JLabel();
        DireccionTexto = new javax.swing.JLabel();
        VelocidadValor = new javax.swing.JTextField();
        DireccionValor = new javax.swing.JTextField();
        ConexionArduino = new javax.swing.JPanel();
        PuertoSensores = new javax.swing.JComboBox<>();
        SensoresTexto = new javax.swing.JLabel();
        MotoresTexto = new javax.swing.JLabel();
        PuertoMotor = new javax.swing.JComboBox<>();
        Actualizar = new javax.swing.JButton();
        Conexion = new javax.swing.JButton();
        Luz = new javax.swing.JLabel();
        Temp = new javax.swing.JLabel();
        Motores = new javax.swing.JPanel();
        Configuracion = new javax.swing.JPanel();
        ValoresPTexto = new javax.swing.JLabel();
        PreTempText = new javax.swing.JLabel();
        PreTempValorMin = new javax.swing.JTextField();
        PreHumText = new javax.swing.JLabel();
        PreHumValorMin = new javax.swing.JTextField();
        PreLuzText = new javax.swing.JLabel();
        PreLuzValorMin = new javax.swing.JTextField();
        ConfiguracionMotorTexto = new javax.swing.JLabel();
        GuardarPredeterminado = new javax.swing.JButton();
        EditarPredeterminado = new javax.swing.JButton();
        PreTempText1 = new javax.swing.JLabel();
        PreTempText2 = new javax.swing.JLabel();
        PreTempValorMax = new javax.swing.JTextField();
        PreHumValorMax = new javax.swing.JTextField();
        PreLuzValorMax = new javax.swing.JTextField();
        CrearCarpetas = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invernadero");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setName("Invernadero"); // NOI18N

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setToolTipText("Invernadero");
        jTabbedPane1.setDoubleBuffered(true);
        jTabbedPane1.setName(""); // NOI18N

        TemperarutaTexto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TemperarutaTexto.setText("Temperatura");

        HumedadTexto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        HumedadTexto.setText("Húmedad");

        LuminocidadTexto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LuminocidadTexto.setText("Intensidad Lumínica");

        VientoTexto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        VientoTexto.setText("Viento");

        TInternaTexto.setText("Interna");

        TExternaTexto.setText("Externa");

        TExternaValor.setEditable(false);
        TExternaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TExternaValorActionPerformed(evt);
            }
        });

        TInternaValor.setEditable(false);
        TInternaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TInternaValorActionPerformed(evt);
            }
        });

        HInternaTexto.setText("Interna");

        HExternaTexto.setText("Externa");

        HInternaValor.setEditable(false);
        HInternaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HInternaValorActionPerformed(evt);
            }
        });

        HExternaValor.setEditable(false);
        HExternaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HExternaValorActionPerformed(evt);
            }
        });

        LuzValor.setEditable(false);
        LuzValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuzValorActionPerformed(evt);
            }
        });

        VelocidadTexto.setText("Velocidad");

        DireccionTexto.setText("Dirección");

        VelocidadValor.setEditable(false);
        VelocidadValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VelocidadValorActionPerformed(evt);
            }
        });

        DireccionValor.setEditable(false);
        DireccionValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DireccionValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DatosLayout = new javax.swing.GroupLayout(Datos);
        Datos.setLayout(DatosLayout);
        DatosLayout.setHorizontalGroup(
            DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HInternaTexto)
                    .addComponent(HInternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VientoTexto)
                    .addComponent(TemperarutaTexto)
                    .addComponent(LuzValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LuminocidadTexto)
                    .addGroup(DatosLayout.createSequentialGroup()
                        .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VelocidadTexto)
                            .addComponent(VelocidadValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TInternaTexto)
                            .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TInternaValor)
                                .addComponent(HumedadTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(37, 37, 37)
                        .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DireccionValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DireccionTexto)
                            .addComponent(HExternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TExternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TExternaTexto)
                            .addComponent(HExternaTexto))))
                .addContainerGap(991, Short.MAX_VALUE))
        );
        DatosLayout.setVerticalGroup(
            DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(TemperarutaTexto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DatosLayout.createSequentialGroup()
                        .addComponent(TInternaTexto)
                        .addGap(8, 8, 8)
                        .addComponent(TInternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HumedadTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(HInternaTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HInternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DatosLayout.createSequentialGroup()
                        .addComponent(TExternaTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TExternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(HExternaTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HExternaValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(VientoTexto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VelocidadTexto)
                    .addComponent(DireccionTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VelocidadValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DireccionValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LuminocidadTexto)
                .addGap(5, 5, 5)
                .addComponent(LuzValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264))
        );

        jTabbedPane1.addTab("Datos", Datos);
        Datos.getAccessibleContext().setAccessibleName("");

        PuertoSensores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        PuertoSensores.setSelectedIndex(-1);
        PuertoSensores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuertoSensoresActionPerformed(evt);
            }
        });

        SensoresTexto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SensoresTexto.setText("Arduino Sensores");

        MotoresTexto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MotoresTexto.setText("Arduino Motores");

        PuertoMotor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        PuertoMotor.setSelectedIndex(-1);
        PuertoMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuertoMotorActionPerformed(evt);
            }
        });

        Actualizar.setText("Actualizar Puertos");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        Conexion.setText("Establecer Conexión");
        Conexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConexionActionPerformed(evt);
            }
        });

        Luz.setName("luz"); // NOI18N

        javax.swing.GroupLayout ConexionArduinoLayout = new javax.swing.GroupLayout(ConexionArduino);
        ConexionArduino.setLayout(ConexionArduinoLayout);
        ConexionArduinoLayout.setHorizontalGroup(
            ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConexionArduinoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SensoresTexto)
                    .addComponent(PuertoSensores, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Actualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Conexion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(128, 128, 128)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MotoresTexto)
                    .addComponent(PuertoMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Temp)
                    .addComponent(Luz, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConexionArduinoLayout.setVerticalGroup(
            ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConexionArduinoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SensoresTexto)
                    .addComponent(MotoresTexto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PuertoSensores)
                    .addComponent(PuertoMotor, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Luz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ConexionArduinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Conexion)
                    .addComponent(Temp))
                .addContainerGap(288, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Conexión Arduino", ConexionArduino);

        javax.swing.GroupLayout MotoresLayout = new javax.swing.GroupLayout(Motores);
        Motores.setLayout(MotoresLayout);
        MotoresLayout.setHorizontalGroup(
            MotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1209, Short.MAX_VALUE)
        );
        MotoresLayout.setVerticalGroup(
            MotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Motores", Motores);

        ValoresPTexto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ValoresPTexto.setText("Valores predeterminados:");

        PreTempText.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PreTempText.setText("Temperatura (*C):");

        PreTempValorMin.setEditable(false);
        PreTempValorMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreTempValorMin.setText("0");
        PreTempValorMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreTempValorMinActionPerformed(evt);
            }
        });

        PreHumText.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PreHumText.setText("Húmedad (%):");

        PreHumValorMin.setEditable(false);
        PreHumValorMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreHumValorMin.setText("0");
        PreHumValorMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreHumValorMinActionPerformed(evt);
            }
        });

        PreLuzText.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PreLuzText.setText("Intensidad Luminica (%):");

        PreLuzValorMin.setEditable(false);
        PreLuzValorMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreLuzValorMin.setText("0");
        PreLuzValorMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreLuzValorMinActionPerformed(evt);
            }
        });

        ConfiguracionMotorTexto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ConfiguracionMotorTexto.setText("Configuración de motores:");

        GuardarPredeterminado.setText("Guardar");
        GuardarPredeterminado.setEnabled(false);
        GuardarPredeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarPredeterminadoActionPerformed(evt);
            }
        });

        EditarPredeterminado.setText("Editar");
        EditarPredeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarPredeterminadoActionPerformed(evt);
            }
        });

        PreTempText1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PreTempText1.setText("Mínimo");

        PreTempText2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PreTempText2.setText("Máximo");

        PreTempValorMax.setEditable(false);
        PreTempValorMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreTempValorMax.setText("0");
        PreTempValorMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreTempValorMaxActionPerformed(evt);
            }
        });

        PreHumValorMax.setEditable(false);
        PreHumValorMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreHumValorMax.setText("0");
        PreHumValorMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreHumValorMaxActionPerformed(evt);
            }
        });

        PreLuzValorMax.setEditable(false);
        PreLuzValorMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PreLuzValorMax.setText("0");
        PreLuzValorMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreLuzValorMaxActionPerformed(evt);
            }
        });

        CrearCarpetas.setText("Crear carpeta de datos");
        CrearCarpetas.setToolTipText("");
        CrearCarpetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearCarpetasActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "60", "45", "30", "15", "10", "5" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Guardar datos cada:");

        jLabel2.setText("min");

        javax.swing.GroupLayout ConfiguracionLayout = new javax.swing.GroupLayout(Configuracion);
        Configuracion.setLayout(ConfiguracionLayout);
        ConfiguracionLayout.setHorizontalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfiguracionLayout.createSequentialGroup()
                .addContainerGap(144, Short.MAX_VALUE)
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ConfiguracionLayout.createSequentialGroup()
                        .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PreTempText)
                            .addComponent(PreHumText)
                            .addComponent(PreLuzText))
                        .addGap(32, 32, 32)
                        .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PreTempText1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(PreLuzValorMin)
                                .addComponent(PreHumValorMin)
                                .addComponent(PreTempValorMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(25, 25, 25)
                        .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PreTempText2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PreTempValorMax)
                            .addComponent(PreHumValorMax)
                            .addComponent(PreLuzValorMax, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(777, 777, 777))
                    .addGroup(ConfiguracionLayout.createSequentialGroup()
                        .addComponent(EditarPredeterminado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GuardarPredeterminado)
                        .addGap(750, 750, 750))))
            .addGroup(ConfiguracionLayout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfiguracionLayout.createSequentialGroup()
                        .addComponent(CrearCarpetas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(ValoresPTexto)
                    .addComponent(ConfiguracionMotorTexto))
                .addContainerGap(725, Short.MAX_VALUE))
        );
        ConfiguracionLayout.setVerticalGroup(
            ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfiguracionLayout.createSequentialGroup()
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfiguracionLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(ValoresPTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ConfiguracionLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ConfiguracionLayout.createSequentialGroup()
                                .addComponent(PreTempText1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PreTempValorMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PreTempText))
                                .addGap(23, 23, 23)
                                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PreHumValorMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PreHumText))
                                .addGap(18, 18, 18)
                                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PreLuzValorMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PreLuzText)))
                            .addGroup(ConfiguracionLayout.createSequentialGroup()
                                .addComponent(PreTempText2)
                                .addGap(13, 13, 13)
                                .addComponent(PreTempValorMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(PreHumValorMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(PreLuzValorMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(GuardarPredeterminado)
                            .addComponent(EditarPredeterminado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(ConfiguracionMotorTexto)
                        .addGap(141, 141, 141)))
                .addGroup(ConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrearCarpetas)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(39, 39, 39))
        );

        jTabbedPane1.addTab("Configuración", Configuracion);

        Mensaje.setText("Sin conexión");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Principal");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HExternaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HExternaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HExternaValorActionPerformed

    private void VelocidadValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VelocidadValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VelocidadValorActionPerformed

    private void ConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConexionActionPerformed
        if (Conexion.getText().equals("Desconectar")) {
            try {
                ArduinoMotor.killArduinoConnection();
                ArduinoSensor.killArduinoConnection();
                Conexion.setText("Conectar");
                cv.enableConnectionPanel(Actualizar, PuertoMotor, PuertoSensores);
                Mensaje.setText("Sin conexión");
            } catch (Exception ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                ArduinoSensor.arduinoRXTX(PuertoSensores.getSelectedItem().toString(), 9600, escuchar);
                Mensaje.setText("Conectado: ");
                Mensaje.setText(Mensaje.getText() + "A.Sensores: " + PuertoSensores.getSelectedItem().toString() + "; ");
                Conexion.setText("Desconectar");
                cv.disableConnectionPanel(Actualizar, PuertoMotor, PuertoSensores);
            } catch (Exception ex) {
                Mensaje.setText(Mensaje.getText() + "A.Sensores: " +  "No conectado.");
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }        
            try{
                
                ArduinoMotor.arduinoTX(PuertoMotor.getSelectedItem().toString(), 9600);
                Conexion.setText("Desconectar");
                cv.disableConnectionPanel(Actualizar, PuertoMotor, PuertoSensores);
                Mensaje.setText(Mensaje.getText() + "A.Motores: " +  PuertoMotor.getSelectedItem().toString() + ".");
            } catch (Exception ex) {
                Mensaje.setText(Mensaje.getText() + "A.Motores: " +  "No conectado.");
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ConexionActionPerformed

    private void TInternaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TInternaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInternaValorActionPerformed

    private void TExternaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TExternaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TExternaValorActionPerformed

    private void HInternaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HInternaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HInternaValorActionPerformed

    private void DireccionValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DireccionValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DireccionValorActionPerformed

    private void LuzValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LuzValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LuzValorActionPerformed

    private void PuertoSensoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuertoSensoresActionPerformed
        
    }//GEN-LAST:event_PuertoSensoresActionPerformed

    private void PuertoMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuertoMotorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuertoMotorActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        getPorts();
    }//GEN-LAST:event_ActualizarActionPerformed

    private void GuardarPredeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarPredeterminadoActionPerformed
        cv.disableTextField(PreLuzValorMin);
        cv.disableTextField(PreTempValorMin);
        cv.disableTextField(PreHumValorMin);
        cv.disableTextField(PreLuzValorMax);
        cv.disableTextField(PreTempValorMax);
        cv.disableTextField(PreHumValorMax);
        cv.enableButton(EditarPredeterminado);
        cv.disableButton(GuardarPredeterminado);
        try {
            actualizarPredeterminados();
        } catch (IOException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GuardarPredeterminadoActionPerformed

    private void EditarPredeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarPredeterminadoActionPerformed
        if(ad.VerficarCarpetaPrincipal()== 1){
            cv.enableTextField(PreLuzValorMin);
            cv.enableTextField(PreTempValorMin);
            cv.enableTextField(PreHumValorMin);
            cv.enableTextField(PreLuzValorMax);
            cv.enableTextField(PreTempValorMax);
            cv.enableTextField(PreHumValorMax);
            cv.disableButton(EditarPredeterminado);
            cv.enableButton(GuardarPredeterminado);
        }else{
         JOptionPane.showMessageDialog(null, "Debe crear archivos");   
        }
    }//GEN-LAST:event_EditarPredeterminadoActionPerformed

    private void PreTempValorMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreTempValorMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreTempValorMinActionPerformed

    private void PreHumValorMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreHumValorMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreHumValorMinActionPerformed

    private void PreLuzValorMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreLuzValorMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreLuzValorMinActionPerformed

    private void PreTempValorMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreTempValorMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreTempValorMaxActionPerformed

    private void PreHumValorMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreHumValorMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreHumValorMaxActionPerformed

    private void PreLuzValorMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreLuzValorMaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreLuzValorMaxActionPerformed

    private void CrearCarpetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearCarpetasActionPerformed
        
        ad.CrearCarpetaPrincipal();
        ad.CrearCarpetaSecundaria();
        ad.CrearDatosPredeterminados();
        
    }//GEN-LAST:event_CrearCarpetasActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Ventana().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton Conexion;
    private javax.swing.JPanel ConexionArduino;
    private javax.swing.JPanel Configuracion;
    private javax.swing.JLabel ConfiguracionMotorTexto;
    private javax.swing.JButton CrearCarpetas;
    private javax.swing.JPanel Datos;
    private javax.swing.JLabel DireccionTexto;
    private javax.swing.JTextField DireccionValor;
    private javax.swing.JButton EditarPredeterminado;
    private javax.swing.JButton GuardarPredeterminado;
    private javax.swing.JLabel HExternaTexto;
    private javax.swing.JTextField HExternaValor;
    private javax.swing.JLabel HInternaTexto;
    private javax.swing.JTextField HInternaValor;
    private javax.swing.JLabel HumedadTexto;
    private javax.swing.JLabel LuminocidadTexto;
    private javax.swing.JLabel Luz;
    private javax.swing.JTextField LuzValor;
    private javax.swing.JLabel Mensaje;
    private javax.swing.JPanel Motores;
    private javax.swing.JLabel MotoresTexto;
    private javax.swing.JLabel PreHumText;
    private javax.swing.JTextField PreHumValorMax;
    private javax.swing.JTextField PreHumValorMin;
    private javax.swing.JLabel PreLuzText;
    private javax.swing.JTextField PreLuzValorMax;
    private javax.swing.JTextField PreLuzValorMin;
    private javax.swing.JLabel PreTempText;
    private javax.swing.JLabel PreTempText1;
    private javax.swing.JLabel PreTempText2;
    private javax.swing.JTextField PreTempValorMax;
    private javax.swing.JTextField PreTempValorMin;
    private javax.swing.JComboBox<String> PuertoMotor;
    private javax.swing.JComboBox<String> PuertoSensores;
    private javax.swing.JLabel SensoresTexto;
    private javax.swing.JLabel TExternaTexto;
    private javax.swing.JTextField TExternaValor;
    private javax.swing.JLabel TInternaTexto;
    private javax.swing.JTextField TInternaValor;
    private javax.swing.JLabel Temp;
    private javax.swing.JLabel TemperarutaTexto;
    private javax.swing.JLabel ValoresPTexto;
    private javax.swing.JLabel VelocidadTexto;
    private javax.swing.JTextField VelocidadValor;
    private javax.swing.JLabel VientoTexto;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
