package icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.gui;

import icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.ClaseGeneradoraVisualizacionAccesoAlta;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.swing.VentanaEstandar;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.NotificacionesAgenteAccesoAlta;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class PanelAccesoUsuario extends VentanaEstandar {

			private static final long serialVersionUID = 1L;
		
			private NotificacionesAgenteAccesoAlta notifAgente; //comunicacion con el agente (control)
			
			
			public PanelAccesoUsuario(ClaseGeneradoraVisualizacionAccesoAlta visualizador) throws Exception {
				notifAgente = new NotificacionesAgenteAccesoAlta(visualizador);
				initComponents();
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}

		
		    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">
		    private void initComponents() {
		    	this.addWindowListener(new WindowAdapter()
				{
		    		public void windowClosing(WindowEvent e)
		    		{
		    			JOptionPane.showMessageDialog(null,"Como el control está en el agente, la ventana no podrá cerrar el sistema directamente...");
		    		}
				});
		    	
		        labelUsr = new javax.swing.JLabel();
		        labelPwd = new javax.swing.JLabel();
		        textoUsr = new javax.swing.JTextField();
		        textoPwd = new javax.swing.JPasswordField();
		        botonAccess = new javax.swing.JButton();
		        botonCancel = new javax.swing.JButton();

		        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		        labelUsr.setText("Usuario:");

		        labelPwd.setText("Contraseña:");

		        botonAccess.setText("Acceso");
		        botonAccess.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    botonAccessActionPerformed(evt);
                } catch (Exception ex) {
                    Logger.getLogger(PanelAccesoUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
		            }
		        });

		        botonCancel.setText("Cancelar");
		        botonCancel.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    botonCancelActionPerformed(evt);
                } catch (Exception ex) {
                    Logger.getLogger(PanelAccesoUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
		            }
		        });

		        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		        getContentPane().setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(layout.createSequentialGroup()
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(layout.createSequentialGroup()
		                        .add(65, 65, 65)
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(labelUsr)
		                            .add(labelPwd))
		                        .add(15, 15, 15)
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(textoPwd)
		                            .add(textoUsr, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
		                    .add(layout.createSequentialGroup()
		                        .add(97, 97, 97)
		                        .add(botonAccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                        .add(41, 41, 41)
		                        .add(botonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
		                .addContainerGap(107, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(layout.createSequentialGroup()
		                .add(73, 73, 73)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(labelUsr)
		                    .add(textoUsr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(49, 49, 49)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(textoPwd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(labelPwd))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 61, Short.MAX_VALUE)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(botonAccess, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(botonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(46, 46, 46))
		        );
		        pack();
		        
		        this.setOpcionMaximizar(false);
		        this.setTitle(" Acceso al Sistema");
		        this.setPosicionCentrada(300,100);
		        this.setDimension(350,350);
		        
		        
		    }// </editor-fold>

		    private void botonCancelActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
		    	notifAgente.notificacionCierreSistema();
		    }

		    private void botonAccessActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
		    	String usr = textoUsr.getText();
		        String pwd = textoPwd.getText();
		    	if((usr.equals(""))||(pwd.equals("")))
		    		notifAgente.getVisualizador().mostrarMensajeError("Error", "Verfique el nombre de usuario  y / o la clave de acceso.");
		    	else
		    		notifAgente.peticionAutentificacion(textoUsr.getText(),textoPwd.getText());
		    		
		    }
		    
		    /**
		     * @param args the command line arguments
		     */
		    public static void main(String args[]) {
		        java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		               // new VentanaAcceso().setVisible(true);
		            }
		        });
		    }
		    
		    // Declaraci�n de varibales -no modificar
		    private javax.swing.JButton botonAccess;
		    private javax.swing.JButton botonCancel;
		    private javax.swing.JLabel labelPwd;
		    private javax.swing.JLabel labelUsr;
		    private javax.swing.JPasswordField textoPwd;
		    private javax.swing.JTextField textoUsr;
		    // Fin de declaraci�n de variables
		    
		}