package icaro.aplicaciones.recursos.visualizacionAlta.imp.gui;

import icaro.aplicaciones.recursos.visualizacionAlta.imp.ClaseGeneradoraVisualizacionAlta;
import icaro.aplicaciones.recursos.visualizacionAlta.imp.swing.VentanaEstandar;
import icaro.aplicaciones.recursos.visualizacionAlta.imp.usuario.UsoAgenteAlta;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class PanelAltaUsuario extends VentanaEstandar {

			private static final long serialVersionUID = 1L;
		
			private UsoAgenteAlta usoAgente; //comunicaci�n con el agente (control)
			
			
			public PanelAltaUsuario(ClaseGeneradoraVisualizacionAlta visualizador) {
				usoAgente = new UsoAgenteAlta(visualizador);
				initComponents();
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}


		    /** This method is called from within the constructor to
		     * initialize the form.
		     * WARNING: Do NOT modify this code. The content of this method is
		     * always regenerated by the Form Editor.
		     */
		    // <editor-fold defaultstate="collapsed" desc=" C�digo Generado  ">
		    private void initComponents() {
		    	this.addWindowListener(new WindowAdapter()
				{
		    		public void windowClosing(WindowEvent e)
		    		{
		    			JOptionPane.showMessageDialog(null,"Como el control est� en el agente, la ventana no podr� cerrar el sistema directamente...");
		    		}
				});
		    	
		        botonAlta = new javax.swing.JButton();
		        etiquetaUser = new javax.swing.JLabel();
		        etiquetaPwd = new javax.swing.JLabel();
		        textoUsr = new javax.swing.JTextField();
		        textoPwd = new javax.swing.JPasswordField();
		        etiquetaConfirm = new javax.swing.JLabel();
		        textoConfirm = new javax.swing.JPasswordField();
		        botonCancelar = new javax.swing.JButton();

		        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		        botonAlta.setText("Sign up!");
		        botonAlta.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                botonAltaActionPerformed(evt);
		            }
		        });

		        etiquetaUser.setText("User:");

		        etiquetaPwd.setText("Password:");

		        etiquetaConfirm.setText("Confirm Password:");

		        botonCancelar.setText("Cancel");
		        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                botonCancelarActionPerformed(evt);
		            }
		        });

		        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		        getContentPane().setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                    .add(layout.createSequentialGroup()
		                        .add(55, 55, 55)
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(layout.createSequentialGroup()
		                                .add(etiquetaUser)
		                                .add(28, 28, 28))
		                            .add(layout.createSequentialGroup()
		                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                                    .add(etiquetaPwd, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
		                                    .add(org.jdesktop.layout.GroupLayout.LEADING, etiquetaConfirm))
		                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
		                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(textoPwd, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
		                            .add(textoUsr, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
		                            .add(textoConfirm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
		                    .add(layout.createSequentialGroup()
		                        .addContainerGap()
		                        .add(botonAlta, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                        .add(61, 61, 61)))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(botonCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                .add(118, 118, 118))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
		                .add(62, 62, 62)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(etiquetaUser)
		                    .add(textoUsr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(26, 26, 26)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(textoPwd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(etiquetaPwd))
		                .add(28, 28, 28)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(etiquetaConfirm)
		                    .add(textoConfirm, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(46, 46, 46)
		                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
		                    .add(botonAlta, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(botonCancelar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(52, 52, 52))
		        );
		        pack();
		        
		        this.setTitle("Signing-up Agent");
		        this.setPosicionCentrada(400,500);
		        this.setOpcionMaximizar(false);
		        
		    }// </editor-fold>

		    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		    	usoAgente.notificacionCierreSistema();
		    }

		    private void botonAltaActionPerformed(java.awt.event.ActionEvent evt) {                                          
		
		        String usr = textoUsr.getText();
		        String pwd = textoPwd.getText();
		        String confirm = textoConfirm.getText();
		        
		        if((pwd.equals(""))||(confirm.equals("")))
		        	usoAgente.getVisualizador().mostrarMensajeError("Failure", "Please check you wrote properly your password.");
		        else if(new String(textoPwd.getPassword()).equals(new String(textoConfirm.getPassword()))){
		            usoAgente.darAlta(usr,pwd);
		        }
		        else
		        	usoAgente.getVisualizador().mostrarMensajeAviso("Watch out!","Password fields don�t match." );
		        
		        
		    }                                         
		    
		    /**
		     * @param args the command line arguments
		     */
		    public static void main(String args[]) {
		        java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		            //    new PanelAccesoUsuario().setVisible(true);
		            }
		        });
		    }
		    
		    // Declaraci�n de varibales -no modificar
		    private javax.swing.JButton botonAlta;
		    private javax.swing.JButton botonCancelar;
		    private javax.swing.JLabel etiquetaConfirm;
		    private javax.swing.JLabel etiquetaPwd;
		    private javax.swing.JLabel etiquetaUser;
		    private javax.swing.JPasswordField textoConfirm;
		    private javax.swing.JPasswordField textoPwd;
		    private javax.swing.JTextField textoUsr;
		    // Fin de declaraci�n de variables
		   
		}
