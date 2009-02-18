package icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.imp.gui;

import icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.imp.ClaseGeneradoraVisualizacionTestPersonalidad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;




public class VentanaTestPersonalidad extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int PREGUNTAS_POR_PANEL = 6;
	private int panelActual = 0;
	private int[] respuestas = null;
	private String[] ayudas = null;
	private String[] textosDelTest = null;
	public ClaseGeneradoraVisualizacionTestPersonalidad visualizador;
	private JPanel panelPregunta = null;
	private JTextPane explicacion = null;
	private JLabel pregunta1 = null;
	private JLabel pregunta2 = null;
	private JLabel pregunta3 = null;
	private JLabel pregunta4 = null;
	private JLabel pregunta5 = null;
	private JLabel pregunta6 = null;
	private JLabel ayuda1 = null;
	private JLabel ayuda2 = null;
	private JLabel ayuda3 = null;
	private JLabel ayuda4 = null;
	private JLabel ayuda5 = null;
	private JLabel ayuda6 = null;
	private JRadioButton[][] matrizRadios = null;
	private ButtonGroup grupo0 = null;
	private ButtonGroup grupo1 = null;
	private ButtonGroup grupo2 = null;
	private ButtonGroup grupo3 = null;
	private ButtonGroup grupo4 = null;
	private ButtonGroup grupo5 = null;
	private JButton botonAceptar = null;
	private Action aceptar = null;
	private Action salir = null;
	private Action comenzar = null;
	private Action terminar = null;  //  @jve:decl-index=0:
	private Action reiniciar = null;
	private Action verInforme = null;
	private JPanel jPanelResultado = null;
	private JLabel jLabelTituloResultado = null;
	private JLabel jLabelGraficoResultado = null;
	private JTextPane jTextPaneResultado = null;
	private JButton botonSalir = null;
	private JPanel jPanelInstrucciones = null;
	private JTextPane jTextInstrucciones1 = null;
	private JTextPane jTextInstrucciones2 = null;
	private JTextPane jTextInstrucciones3 = null;
	private JTextPane jTextInstrucciones4 = null;
	private JTextPane jTextConclusion = null;
	private JButton jButtonComenzar = null;
	private JButton botonAceptarResultado = null;
	private JButton botonVerInforme = null;
	private JButton botonRepetirTest = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public VentanaTestPersonalidad(ClaseGeneradoraVisualizacionTestPersonalidad visualizador1, String[] textos) {
		super();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.textosDelTest = textos;
		this.visualizador = visualizador1;
		this.matrizRadios = new JRadioButton[PREGUNTAS_POR_PANEL][11];
		this.respuestas = new int[PREGUNTAS_POR_PANEL];
		this.grupo1 = new ButtonGroup();
		this.grupo2 = new ButtonGroup();
		this.grupo3 = new ButtonGroup();
		this.grupo4 = new ButtonGroup();
		this.grupo5 = new ButtonGroup();
		this.grupo0 = new ButtonGroup();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				visualizador.accionSalir();
		    }
		}
		);
		
		initialize();
	}
	/**
	 * Abre la ventana principal
	 */
	public void abrir(){
		this.setVisible(true);
	}
	
	/**
	 * Carga más preguntas del test en el panel
	 */
	public void cambiarPanel(int panel, String[] listaPreguntas, int[] resultadosPrevios){
		this.panelActual = panel;
		limpiarRadios();
		prepararPanel(listaPreguntas, resultadosPrevios);
	}
	/**
	 * Cierra la ventana principal
	 *
	 */
	public void cerrar() {
		this.dispose();
	}
	/**
	 * Carga en el panel las preguntas
	 */
	private void prepararPanel(String[] listaPreguntas, int[] resultadosPrevios){
		/*
		 * Se calcula el número de la primera pregunta según el panel
		 * teniendo en cuenta que cada panel tiene 6 preguntas
		 */
		int inc = 1 + (PREGUNTAS_POR_PANEL * (this.panelActual - 1));
		for(int i=0; i<listaPreguntas.length; i++){
			switch(i){
			case 0:
				this.pregunta1.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda1.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			case 1:
				this.pregunta2.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda2.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			case 2:
				this.pregunta3.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda3.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			case 3:
				this.pregunta4.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda4.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			case 4:
				this.pregunta5.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda5.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			case 5:
				this.pregunta6.setText((i+inc) + ".- "+ listaPreguntas[i]);
				this.ayuda6.setToolTipText(this.ayudas[((i+inc)-1)]);
				break;
			}
		}
		for(int j=0; j<resultadosPrevios.length; j++){
			if(resultadosPrevios[j] != -1){
				this.matrizRadios[j][resultadosPrevios[j]].setSelected(true);
			}
		}
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(517, 530));
        this.setTitle(this.textosDelTest[4]);
        this.setContentPane(getPanelPregunta());
        this.jPanelInstrucciones.setVisible(false);
        this.jPanelResultado.setVisible(false);
        this.escondePreguntas();
			
	}
	public void muestraInstrucciones(String[] listaAyudas){
		this.ayudas = listaAyudas;
		this.jPanelInstrucciones.setVisible(true);
		this.jPanelResultado.setVisible(false);
		this.escondePreguntas();
	}
	public void muestraPanelPreguntas(){
		this.jPanelInstrucciones.setVisible(false);
		this.jPanelResultado.setVisible(false);
		this.muestraPreguntas();
	}
	
	/**
	 * This method initializes panelPregunta	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelPregunta() {
		if (panelPregunta == null) {
			pregunta6 = new JLabel();
			pregunta6.setBounds(new Rectangle(11, 330, 180, 22));
			ayuda6 = new JLabel();
			ayuda6.setBounds(new Rectangle(191, 330, 22, 22));
			ayuda6.setIcon(new ImageIcon("help2.jpg"));
			pregunta5 = new JLabel();
			pregunta5.setBounds(new Rectangle(11, 276, 180, 22));
			ayuda5 = new JLabel();
			ayuda5.setBounds(new Rectangle(191, 276, 22, 22));
			ayuda5.setIcon(new ImageIcon("help2.jpg"));
			pregunta4 = new JLabel();
			pregunta4.setBounds(new Rectangle(11, 222, 180, 22));
			ayuda4 = new JLabel();
			ayuda4.setBounds(new Rectangle(191, 222, 22, 22));
			ayuda4.setIcon(new ImageIcon("help2.jpg"));
			pregunta3 = new JLabel();
			pregunta3.setBounds(new Rectangle(11, 168, 180, 22));
			ayuda3 = new JLabel();
			ayuda3.setBounds(new Rectangle(191, 168, 22, 22));
			ayuda3.setIcon(new ImageIcon("help2.jpg"));
			pregunta2 = new JLabel();
			pregunta2.setBounds(new Rectangle(11, 114, 180, 22));
			ayuda2 = new JLabel();
			ayuda2.setBounds(new Rectangle(191, 114, 22, 22));
			ayuda2.setIcon(new ImageIcon("help2.jpg"));
			pregunta1 = new JLabel();
			pregunta1.setBounds(new Rectangle(11, 60, 180, 22));
			ayuda1 = new JLabel();
			ayuda1.setBounds(new Rectangle(191, 60, 22, 22));
			ayuda1.setIcon(new ImageIcon("help2.jpg"));
			panelPregunta = new JPanel();
			panelPregunta.setLayout(null);
			panelPregunta.add(pregunta1, null);
			panelPregunta.add(pregunta2, null);
			panelPregunta.add(pregunta3, null);
			panelPregunta.add(pregunta4, null);
			panelPregunta.add(pregunta5, null);
			panelPregunta.add(pregunta6, null);
			panelPregunta.add(ayuda6);
			panelPregunta.add(ayuda5);
			panelPregunta.add(ayuda4);
			panelPregunta.add(ayuda3);
			panelPregunta.add(ayuda2);
			panelPregunta.add(ayuda1);
			panelPregunta.add(getJTextPaneExplicacion(), null);
			panelPregunta.add(getBotonAceptar(), null);
			panelPregunta.add(getJPanelResultado(), null);
			panelPregunta.add(getBotonSalir(), null);
			panelPregunta.add(getJPanelInstrucciones(), null);
			this.initializeRadios();
			for(int i=0; i<this.matrizRadios.length; i++){
				for(int j=0; j<this.matrizRadios[i].length; j++){
					panelPregunta.add(matrizRadios[i][j]);
				}
			}
		}
		return panelPregunta;
	}
	
	/**
	 * This method initializes jTextPaneResultado	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneExplicacion() {
		if (explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setBounds(new Rectangle(11, 5, 485, 39));
			explicacion.setEditable(false);
			explicacion.setText(this.textosDelTest[5]);
			explicacion.setBackground(new Color(238, 238, 238));
			explicacion.setFont(new Font("Dialog", Font.PLAIN, 14));
		}
		return explicacion;
	}
	
	private void initializeRadios(){
		int iniX = 26;
		int iniY = 87;
		int incX;
		int incY;
		int xPos;
		int yPos;
		int j = 0;
		
		for(int i=0; i<this.matrizRadios.length; i++){
			incY = 54 * i;
			yPos = iniY + incY;
			for(j=0; j<this.matrizRadios[i].length; j++){
				incX = 42 * j;
				xPos = iniX + incX;
				if(this.matrizRadios[i][j]==null){
					this.matrizRadios[i][j] = new JRadioButton();
					if(j==(this.matrizRadios.length - 1))
						this.matrizRadios[i][j].setBounds(new Rectangle(xPos, yPos, 35, 21));
					else
						this.matrizRadios[i][j].setBounds(new Rectangle(xPos, yPos, 45, 21));
					this.matrizRadios[i][j].setText((""+j));
					switch(i){
					case 0:
						grupo0.add(matrizRadios[i][j]);
						break;
					case 1:
						grupo1.add(matrizRadios[i][j]);
						break;
					case 2:
						grupo2.add(matrizRadios[i][j]);
						break;
					case 3:
						grupo3.add(matrizRadios[i][j]);
						break;
					case 4:
						grupo4.add(matrizRadios[i][j]);
						break;
					case 5:
						grupo5.add(matrizRadios[i][j]);
						break;
					}
				}
			}
		}
	}

	/**
	 * This method initializes botonAceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAceptar() {
		if (botonAceptar == null) {
			botonAceptar = new JButton();
			botonAceptar.setBounds(new Rectangle(400, 405, 105, 24));
			botonAceptar.setText(this.textosDelTest[7]);
			aceptar = new AbstractAction("Aceptar") {
				public void actionPerformed(ActionEvent e) {
					visualizador.aceptaResultado();
				}
			};
			botonAceptar.setAction(aceptar);
		}
		return botonAceptar;
	}

	/**
	 * This method initializes botonSalir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonSalir() {
		if (botonSalir == null) {
			botonSalir = new JButton();
			botonSalir.setBounds(new Rectangle(400, 443, 105, 24));
			botonSalir.setText(this.textosDelTest[8]);
			salir = new AbstractAction("Salir") {
				public void actionPerformed(ActionEvent e) {
					visualizador.accionSalir();
				} 
			};
			botonSalir.setAction(salir);
		}
		return botonSalir;
	}
	
	/**
	 * This method initializes jPanelResultado	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelResultado() {
		if (jPanelResultado == null) {
			jLabelTituloResultado = new JLabel();
			jLabelTituloResultado.setBounds(new Rectangle(19, 10, 200, 22));
			jLabelTituloResultado.setText(this.textosDelTest[6]);
			jLabelTituloResultado.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelGraficoResultado = new JLabel();
			jLabelGraficoResultado.setBounds(new Rectangle(12, 172, 465, 200));
			jPanelResultado = new JPanel();
			jPanelResultado.setLayout(null);
			jPanelResultado.setBounds(new Rectangle(0, 0, 509, 435));
			jPanelResultado.add(jLabelTituloResultado, null);
			jPanelResultado.add(jLabelGraficoResultado, null);
			jPanelResultado.add(getJTextPaneResultado(), null);
			jPanelResultado.add(getBotonAceptarResultado(), null);
			jPanelResultado.add(getBotonVerInforme(), null);
			jPanelResultado.add(getBotonRepetirTest(), null);
		}
		return jPanelResultado;
	}

	/**
	 * This method initializes jTextPaneResultado	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneResultado() {
		if (jTextPaneResultado == null) {
			jTextPaneResultado = new JTextPane();
			jTextPaneResultado.setBounds(new Rectangle(22, 45, 343, 118));
			jTextPaneResultado.setEditable(false);
			jTextPaneResultado.setBackground(new Color(238, 238, 238));
			jTextPaneResultado.setFont(new Font("Dialog", Font.PLAIN, 14));
		}
		return jTextPaneResultado;
	}
	
	/**
	 * This method initializes jPanelInstrucciones	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelInstrucciones() {
		if (jPanelInstrucciones == null) {
			jPanelInstrucciones = new JPanel();
			jPanelInstrucciones.setLayout(null);
			jPanelInstrucciones.setBounds(new Rectangle(0, 0, 509, 435));
			jPanelInstrucciones.add(getJTextInstrucciones1(), null);
			jPanelInstrucciones.add(getJTextInstrucciones2(), null);
			jPanelInstrucciones.add(getJTextInstrucciones3(), null);
			jPanelInstrucciones.add(getJTextInstrucciones4(), null);
			jPanelInstrucciones.add(getJTextConclusion(), null);
			jPanelInstrucciones.add(getJButtonComenzar(), null);
		}
		return jPanelInstrucciones;
	}

	/**
	 * This method initializes jTextInstrucciones1	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextInstrucciones1() {
		if (jTextInstrucciones1 == null) {
			jTextInstrucciones1 = new JTextPane();
			jTextInstrucciones1.setBounds(new Rectangle(14, 9, 481, 80));
			jTextInstrucciones1.setEditable(false);
			jTextInstrucciones1.setBackground(new Color(238, 238, 238));
			jTextInstrucciones1.setFont(new Font("Dialog", Font.PLAIN, 14));
			jTextInstrucciones1.setText(this.textosDelTest[0]);
		}
		return jTextInstrucciones1;
	}

	/**
	 * This method initializes jTextInstrucciones2	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextInstrucciones2() {
		if (jTextInstrucciones2 == null) {
			jTextInstrucciones2 = new JTextPane();
			jTextInstrucciones2.setBounds(new Rectangle(14, 88, 481, 83));
			jTextInstrucciones2.setEditable(false);
			jTextInstrucciones2.setBackground(new Color(238, 238, 238));
			jTextInstrucciones2.setFont(new Font("Dialog", Font.PLAIN, 14));
			jTextInstrucciones2.setText(this.textosDelTest[22]);
		}
		return jTextInstrucciones2;
	}

	/**
	 * This method initializes jTextInstrucciones3	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextInstrucciones3() {
		if (jTextInstrucciones3 == null) {
			jTextInstrucciones3 = new JTextPane();
			jTextInstrucciones3.setBounds(new Rectangle(14, 171, 481, 98));
			jTextInstrucciones3.setEditable(false);
			jTextInstrucciones3.setBackground(new Color(238, 238, 238));
			jTextInstrucciones3.setFont(new Font("Dialog", Font.PLAIN, 14));
			jTextInstrucciones3.setText(this.textosDelTest[1]);
		}
		return jTextInstrucciones3;
	}

	/**
	 * This method initializes jTextInstrucciones4	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextInstrucciones4() {
		if (jTextInstrucciones4 == null) {
			jTextInstrucciones4 = new JTextPane();
			jTextInstrucciones4.setBounds(new Rectangle(14, 268, 481, 67));
			jTextInstrucciones4.setEditable(false);
			jTextInstrucciones4.setBackground(new Color(238, 238, 238));
			jTextInstrucciones4.setFont(new Font("Dialog", Font.PLAIN, 14));
			jTextInstrucciones4.setText(this.textosDelTest[2]);
		}
		return jTextInstrucciones4;
	}

	/**
	 * This method initializes jTextConclusion	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextConclusion() {
		if (jTextConclusion == null) {
			jTextConclusion = new JTextPane();
			jTextConclusion.setBounds(new Rectangle(89, 345, 331, 53));
			jTextConclusion.setEditable(false);
			jTextConclusion.setFont(new Font("Dialog", Font.PLAIN, 18));
			jTextConclusion.setBackground(new Color(238, 238, 238));
			jTextConclusion.setText(this.textosDelTest[3]);
		}
		return jTextConclusion;
	}

	/**
	 * This method initializes jButtonComenzar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonComenzar() {
		if (jButtonComenzar == null) {
			jButtonComenzar = new JButton();
			jButtonComenzar.setBounds(new Rectangle(202, 402, 105, 24));
			jButtonComenzar.setText(this.textosDelTest[9]);
			comenzar = new AbstractAction("Comenzar") {
				public void actionPerformed(ActionEvent e) {
					visualizador.comenzar();;
				} 
			};
			jButtonComenzar.setAction(comenzar);
		}
		return jButtonComenzar;
	}
	
	/**
	 * This method initializes botonAceptarResultado	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonAceptarResultado() {
		if (botonAceptarResultado == null) {
			botonAceptarResultado = new JButton();
			botonAceptarResultado.setBounds(new Rectangle(372, 383, 105, 24));
			botonAceptarResultado.setText(this.textosDelTest[10]);
			terminar = new AbstractAction("Terminar") {
				public void actionPerformed(ActionEvent e){
					visualizador.accionSalir();
				}
			};
			botonAceptarResultado.setAction(terminar);
		}
		return botonAceptarResultado;
	}
	
	/**
	 * This method initializes botonAceptarResultado	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonVerInforme() {
		if (botonVerInforme == null) {
			botonVerInforme = new JButton();
			botonVerInforme.setBounds(new Rectangle(250, 383, 105, 24));
			botonVerInforme.setText(this.textosDelTest[11]);
			verInforme = new AbstractAction("Informe") {
				public void actionPerformed(ActionEvent e){
					System.out.println("Quieres informarte");
					try
					{
					   // directorio/ejecutable es el path del ejecutable y un nombre.
					   //Process p = Runtime.getRuntime().exec ("\"c:\\Archivos de Programa\\Internet Explorer\\iexplore.exe\"");
						String path = "\"c:\\Archivos de Programa\\Internet Explorer\\iexplore.exe\"";
						File f = new File("informe_personalidad.html");
						System.out.println(f.getAbsolutePath());
						String[] cmdStart = {path,"file://"+f.getAbsolutePath()};
						Process p = Runtime.getRuntime().exec (cmdStart);
					}
					catch (Exception i)
					{
					   // Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable.
						System.out.println("Error al lanzar la aplicación de la información relacionada.");
						i.printStackTrace();
					}
					/*Runtime r = Runtime.getRuntime();
				    //Ejecutams el comando de apertura de la información relacionada
				    String path = organizacion.infraestructura.configuracion.Configuracion.obtenerParametro("EXPLORER");
				    File f = new File("informe_personalidad.html");
				    String[] cmdStart = {path,"file://"+f.getAbsolutePath()};
				    try {
				    Process p = r.exec(cmdStart);
				    } catch (IOException ioe) {
				    System.err.println("Error al lanzar la aplicación de la información relacionada.");
				    }*/
				}
			};
			botonVerInforme.setAction(verInforme);
		}
		return botonVerInforme;
	}
	
	/**
	 * This method initializes botonAceptarResultado	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBotonRepetirTest() {
		if (botonRepetirTest == null) {
			botonRepetirTest = new JButton();
			botonRepetirTest.setBounds(new Rectangle(128, 383, 105, 24));
			botonRepetirTest.setText(this.textosDelTest[13]);
			reiniciar = new AbstractAction("Rehacer") {
				public void actionPerformed(ActionEvent e){
					visualizador.reiniciarTest();
				}
			};
			botonRepetirTest.setAction(reiniciar);
		}
		return botonRepetirTest;
	}
	private void limpiarRadios(){
		for(int i=0; i<this.matrizRadios.length; i++){
			for(int j=0; j<this.matrizRadios[i].length; j++){
				if(this.matrizRadios[i][j].isSelected()){
					switch(i){
					case 0:
						grupo0.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo0.add(matrizRadios[i][j]);
						break;
					case 1:
						grupo1.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo1.add(matrizRadios[i][j]);
						break;
					case 2:
						grupo2.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo2.add(matrizRadios[i][j]);
						break;
					case 3:
						grupo3.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo3.add(matrizRadios[i][j]);
						break;
					case 4:
						grupo4.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo4.add(matrizRadios[i][j]);
						break;
					case 5:
						grupo5.remove(matrizRadios[i][j]);
						this.matrizRadios[i][j].setSelected(false);
						grupo5.add(matrizRadios[i][j]);
						break;
					}
				}
			}
		}
	}
	
	public int[] darRespuestas(){
		boolean encontrado = false;
		for(int i=0; i<this.matrizRadios.length; i++){
			for(int j=0; j<this.matrizRadios[i].length; j++){
				if(this.matrizRadios[i][j].isSelected()){
					this.respuestas[i] = j;
					encontrado = true;
					break;
				}
			}
			if(!encontrado)
				this.respuestas[i] = -1;
			else
				encontrado = false;
			
		}
		return respuestas;
	}
	
	public int getPanelActual(){
		return this.panelActual;
	}

	public void mostrarResultado(String textoResultado, int[] resultados){
		escondePreguntas();
		jPanelResultado.setVisible(true);
		jTextPaneResultado.setText(textoResultado);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(resultados[1], "serie1", this.textosDelTest[14]);
		dataset.addValue(resultados[3], "serie1", this.textosDelTest[15]);
		dataset.addValue(resultados[4], "serie1", this.textosDelTest[16]);
		dataset.addValue(resultados[0], "serie1", this.textosDelTest[17]);
		dataset.addValue(resultados[2], "serie1", this.textosDelTest[18]);
		JFreeChart chart = ChartFactory.createBarChart(null, null, null, dataset, PlotOrientation.HORIZONTAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0.0, 100.0);
		
		BufferedImage image = chart.createBufferedImage(465,200);
		
		jLabelGraficoResultado.setIcon(new ImageIcon(image));
	}
	
	private void escondePreguntas(){
		this.explicacion.setVisible(false);
		this.pregunta1.setVisible(false);
		this.pregunta2.setVisible(false);
		this.pregunta3.setVisible(false);
		this.pregunta4.setVisible(false);
		this.pregunta5.setVisible(false);
		this.pregunta6.setVisible(false);
		this.ayuda1.setVisible(false);
		this.ayuda2.setVisible(false);
		this.ayuda3.setVisible(false);
		this.ayuda4.setVisible(false);
		this.ayuda5.setVisible(false);
		this.ayuda6.setVisible(false);
		for(int i=0; i<this.matrizRadios.length; i++){
			for(int j=0; j<this.matrizRadios[i].length; j++){
				matrizRadios[i][j].setVisible(false);
			}
		}
		this.botonAceptar.setVisible(false);
		this.botonSalir.setVisible(false);
	}
	
	private void muestraPreguntas(){
		this.explicacion.setVisible(true);
		this.pregunta1.setVisible(true);
		this.pregunta2.setVisible(true);
		this.pregunta3.setVisible(true);
		this.pregunta4.setVisible(true);
		this.pregunta5.setVisible(true);
		this.pregunta6.setVisible(true);
		this.ayuda1.setVisible(true);
		this.ayuda2.setVisible(true);
		this.ayuda3.setVisible(true);
		this.ayuda4.setVisible(true);
		this.ayuda5.setVisible(true);
		this.ayuda6.setVisible(true);
		for(int i=0; i<this.matrizRadios.length; i++){
			for(int j=0; j<this.matrizRadios[i].length; j++){
				matrizRadios[i][j].setVisible(true);
			}
		}
		this.botonAceptar.setVisible(true);
		this.botonSalir.setVisible(true);
	}
	
	public int panelMostrado(){
		int panelMostrado = 0;
		if(this.jPanelInstrucciones.isVisible())
			panelMostrado = 0;
		else if(this.jPanelResultado.isVisible())
			panelMostrado = 2;
		else
			panelMostrado = 1;
		return panelMostrado;
	}
}  //  @jve:decl-index=0:visual-constraint="385,54"