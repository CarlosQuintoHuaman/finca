package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import java.util.GregorianCalendar;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.Agenda;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;
import icaro.util.util;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class panelAgenda extends Thread {

	// Variables
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteSecretaria usoAgente;
	
	
	//componentes de la ventana
	private Button Manana;
	private Composite seleccionado;
	private CLabel cNomSel;
	private CLabel cSeleccionado;
	private CLabel nombreL;
	private Button Tarde;
	private Button agenda;
	private Button actualizar;
	private Button cerrar;
	private Button anadirE;
	private Button AnadirL;
	private CLabel llamadas;
	private Composite tablaLlamadas;
	private Composite tablasDerecha;
	private Composite tablaExtras;
	private CLabel horaE;
	private CLabel NombreE;
	private CLabel Extras;
	private CLabel telefono;
	private CLabel Nombre;
	private CLabel Horas;
	private Composite AgendaDinamica;
	private CTabItem ItemJulio;
	private Button DarCita;
	private CTabItem cTabItem1;
	private CTabFolder Agenda;
	private Button CrearFicha;
	private Button Borrar;
	private Button Pegar;
	private Button Copiar;
	private Button ConsultarCitas;
	private Button AgendaHoy;
	private Button Mensajeria;
	private Label LMenu1;
	private Composite Menu1;
	private Composite principal;
	private Composite huecoAgenda;
	private Button[] horas;
	private GridData[] aux1;
	private GridData[] aux2;
	private GridData[] aux3;
	private CLabel[] Nombres;
	private CLabel[] Telefonos;
	private CTabItem[] TabNomMed;
	private Composite[] agendaDinamica;
	
	//DatosLlamada
	private CLabel[] NombresL;
	private String[] TelefonosL;
	private String[] MensajeL;
	private boolean[] PacienteL;
	private String[] horasL;
	
	//DatosExtra
	private CLabel[] NombresE;
	private String[] TelefonosE;
	private String[] MensajeE;
	private boolean[] PacienteE;
	private CLabel[] horasE;
	private Menu opciones;
	
	//variables globales
	private boolean man=true;
	private boolean init=true;
	private int k=0;
	private int c=0;
	private DatosCitaSinValidar pegado, copiado;
	private ArrayList <DatosLlamada>extra=new ArrayList();
	private ArrayList <DatosLlamada>llamada=new ArrayList();
	private Date fecha;
	protected Date fechaAnt;
	private int min;
	private boolean ok;

	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private panelAgenda este;
	private ClaseGeneradoraVisualizacionSecretaria vis;
	
	private ArrayList<DatosCitaSinValidar> l;
	private String fechaAgenda;
	
	
	private boolean cumple;
	//Datos persistencia
	//datos :: Variable que contiene todos los datos de la agenda del usuario(secretaria) para el dia consultado. Por defecto la fecha actual
	private DatosSecretaria datos;
	private ArrayList<DatosMedico> medatos;
	private int numM;

	private String usuEste;

	private String fd;
	private String fy;
	private String fant;
	private CLabel seleccion;
	/**
	 * Constructor de la ventana
	 * @param visualizador
	 */
	public panelAgenda(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("Agenda");
		este = this;
		vis =visualizador;
		usoAgente = new UsoAgenteSecretaria(visualizador);
	}

	public void run(){
		// Crear el display y generar la ventana pero SIN MOSTRARLA
		// Es decir, no debe haber un shell.open en initGUI()
		disp = new Display();
		initGUI();
	}

	public void mostrar(){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.open();
	       }
         });
	}
	/**
	 * Funcion que una vez mostrada la ventana (vacia) nos rellena la ventana con los datos sacados de la persistencia
	 * @param fecha :: fecha de la agenda que se muestra
	 * @param lm1	:: Lista de citas de todos los medicos a los que les corresponde esta secretaria
	 * @param numM  :: Numero de medicos para los que tiene agenda este usuario (secretaria)
	 * @param s		:: Nombre del usuario actual
	 */
	public void meteDatos(String fecha, final ArrayList<DatosMedico> lm1 ,final int numM, final String s){
		disp.syncExec(new Runnable() {
            public void run() {
            	datos.getMedicos().clear();
            	datos.setNumM(numM);
            	for(int i=0;i<lm1.size();i++){
            		DatosMedico med=new DatosMedico(lm1.get(i).getNombre(),lm1.get(i).getIntervalo(), lm1.get(i).getDatos(), lm1.get(i).getLlamadas(),
            					lm1.get(i).getExtras(),lm1.get(i).getUsuario(),lm1.get(i).getMa�ana().getHInicio(),lm1.get(i).getTarde().getHInicio(),
            					lm1.get(i).getMa�ana().getHFin(),lm1.get(i).getTarde().getHFin());            		
            		datos.getMedicos().add(med);
            		
            	}
            	usuEste=s;
            	//Genera la agenda dinamicamente segun medicos (en pesta�as) y pacientes (en horas)
            	generaTabla();
            
            }
        });
	}

	public void ocultar(){
		// NO CONFIRMADO
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.close();
	       }
         });
	}

	public void destruir(){
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.dispose();
	       }
         });
	}

	public UsoAgenteSecretaria getAgente(){
		return usoAgente;
	}

	/** Codigo de la ventana
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		

		try {
			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(shell);
			}
			//VARIABLES
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	//usoAgente.guardarAgenda(datos);
			    	usoAgente.cerrarVentanaSecretaria();
			    }
			});
			medatos=new ArrayList<DatosMedico>();
			numM=0;
			datos=new DatosSecretaria(medatos, numM);

			l=new ArrayList<DatosCitaSinValidar> ();			
			shell.setLayout(new FillLayout());
			//Genera fecha actual
			util f=new util();
			fd=util.getStrDateSQL2();
			fy=util.getStrDateSQL();
			datos.setFecha(fy);
			fant=fy;
			seleccion=cNomSel;
			shell.setText("Consulta de Hoy "+fd);
			shell.setSize(800, 600);
			{
				principal = new Composite(shell, SWT.NONE);
				GridLayout thisLayout = new GridLayout();
				thisLayout.numColumns = 3;
				principal.setSize(800, 660);
				principal.setBackground(SWTResourceManager.getColor(192, 192, 192));
				principal.setLayout(thisLayout);
				{
					Menu1 = new Composite(principal, SWT.NONE);
					GridLayout Menu1Layout = new GridLayout();
					Menu1Layout.makeColumnsEqualWidth = true;
					GridData Menu1LData = new GridData();
					Menu1LData.verticalAlignment = GridData.FILL;
					Menu1LData.horizontalAlignment = GridData.FILL;
					Menu1.setLayoutData(Menu1LData);
					Menu1.setLayout(Menu1Layout);
					Menu1.setBackground(SWTResourceManager.getColor(220, 189, 244));
					{
						LMenu1 = new Label(Menu1, SWT.NONE);
						LMenu1.setText("Menu");
						GridData LMenu1LData = new GridData();
						LMenu1LData.widthHint = 32;
						LMenu1LData.heightHint = 17;
						LMenu1.setLayoutData(LMenu1LData);
						LMenu1.setForeground(SWTResourceManager.getColor(0, 0, 255));
						LMenu1.setBackground(SWTResourceManager.getColor(220, 189, 244));
					}
					{
						ConsultarCitas = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData ConsultarCitasLData = new GridData();
						ConsultarCitasLData.horizontalAlignment = GridData.FILL;
						ConsultarCitas.setLayoutData(ConsultarCitasLData);
						ConsultarCitas.setText("Consultar Citas");
						ConsultarCitas.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								usoAgente.mostrarVentanaProximasCitas();
								
							}                               
						});

					}
					{
						Copiar = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData CopiarLData = new GridData();
						CopiarLData.horizontalAlignment = GridData.FILL;
						Copiar.setLayoutData(CopiarLData);
						Copiar.setText("Copiar");
						Copiar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								CopiarWidgetSelected(evt);
								
							}                               
						});
					}
					{
						Pegar = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData PegarLData = new GridData();
						PegarLData.horizontalAlignment = GridData.FILL;
						Pegar.setLayoutData(PegarLData);
						Pegar.setText("Pegar");
						Pegar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								PegarWidgetSelected(evt);
								
							}                               
						});
					}
					{
						Borrar = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData BorrarLData = new GridData();
						BorrarLData.horizontalAlignment = GridData.FILL;
						Borrar.setLayoutData(BorrarLData);
						Borrar.setText("Borrar");
						Borrar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent e) {
								BorrarWidgetSelected(e);
								
							}                               
						});
					}
					{
						CrearFicha = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData CrearFichaLData = new GridData();
						CrearFichaLData.horizontalAlignment = GridData.FILL;
						CrearFicha.setLayoutData(CrearFichaLData);
						CrearFicha.setText("Ficha");
						CrearFicha.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent e) {
								CrearFichaWidgetSelected(e);
								
							}                               
						});

					}
					{
						DarCita = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData DarCitaLData = new GridData();
						DarCitaLData.horizontalAlignment = GridData.FILL;
						DarCita.setLayoutData(DarCitaLData);
						DarCita.setText("Dar Cita");
						DarCita.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent e) {
								DarCitaWidgetSelectedB(e);
								
							}                               
						});
					}
					{
						Manana = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData MananaLData = new GridData();
						MananaLData.widthHint = 85;
						MananaLData.heightHint = 47;
						Manana.setLayoutData(MananaLData);
						Manana.setText("MA�ANA");
						Manana.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								MananaWidgetSelected(evt);
							}
						});

					}
					{
						Tarde = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData TardeLData = new GridData();
						TardeLData.widthHint = 85;
						TardeLData.heightHint = 47;
						Tarde.setLayoutData(TardeLData);
						Tarde.setText("TARDE");
						Tarde.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								TardeWidgetSelected(evt);
								
							}
						});
						
					}
					{
						agenda = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
//						button1LData.widthHint = 68;
						button1LData.heightHint = 57;
						//button1LData.horizontalAlignment = GridData.CENTER;
						agenda.setLayoutData(button1LData);
						agenda.setText("CAMBIO FECHA");
						agenda.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								agendaWidgetSelected(evt);
								
							}
						});
					}
					{
						AgendaHoy = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
//						button1LData.widthHint = 68;
						button1LData.heightHint = 57;
						//button1LData.horizontalAlignment = GridData.CENTER;
						AgendaHoy.setLayoutData(button1LData);
						AgendaHoy.setText("Agenda Hoy");
						AgendaHoy.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								agendaHoyWidgetSelected(evt);
								
							}
						});
					}
					{
						Mensajeria = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
						button1LData.heightHint = 57;
						Mensajeria.setLayoutData(button1LData);
						Mensajeria.setText("Mensajeria");
						Mensajeria.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.mostrarVentanaMensajeria(usuEste);
								
							}
						});
					}

					{
						actualizar = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
	//					button1LData.widthHint = 68;
						button1LData.heightHint = 57;
						//button1LData.horizontalAlignment = GridData.CENTER;
						actualizar.setLayoutData(button1LData);
						actualizar.setText("Actualizar");
						actualizar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								//usoAgente.guardarAgenda(datos);
								usoAgente.mostrarAgendaSecretaria(fy, usuEste);
							}
						});
					}
					
					{
						cerrar = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
	//					button1LData.widthHint = 68;
						button1LData.heightHint = 57;
						//button1LData.horizontalAlignment = GridData.CENTER;
						cerrar.setLayoutData(button1LData);
						cerrar.setText("Cerrar");
						cerrar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								//usoAgente.guardarAgenda(datos);
								usoAgente.cerrarVentanaSecretaria();
							}
						});
					}
					
				}
				{
					huecoAgenda = new Composite(principal, SWT.NONE);
					GridLayout huecoAgendaLayout = new GridLayout();
					GridData huecoAgendaLData = new GridData();
					huecoAgendaLData.grabExcessHorizontalSpace = true;
					huecoAgendaLData.grabExcessVerticalSpace = true;
					huecoAgendaLData.verticalAlignment = GridData.FILL;
					huecoAgendaLData.horizontalAlignment = GridData.FILL;
					huecoAgenda.setLayoutData(huecoAgendaLData);
					huecoAgenda.setLayout(huecoAgendaLayout);
					huecoAgenda.setDragDetect(false);
					{
						
						Agenda = new CTabFolder(huecoAgenda, SWT.NONE);
					
						GridData AgendaLData = new GridData();
						AgendaLData.verticalAlignment = GridData.FILL;		
						AgendaLData.horizontalAlignment = GridData.FILL;
						AgendaLData.grabExcessVerticalSpace = true;
						AgendaLData.grabExcessHorizontalSpace = true;
						Agenda.setLayoutData(AgendaLData);
						Agenda.setSelection(0);
						{
							GridData seleccionadoLData = new GridData();
							seleccionadoLData.verticalAlignment = GridData.FILL;	
							seleccionadoLData.horizontalAlignment = GridData.FILL;
							seleccionadoLData.heightHint = 29;
							//seleccionadoLData.grabExcessVerticalSpace = true;
							seleccionadoLData.grabExcessHorizontalSpace = true;
							seleccionado = new Composite(huecoAgenda, SWT.NONE);
							GridLayout seleccionadoLayout = new GridLayout();
							seleccionadoLayout.numColumns = 2;
							seleccionado.setLayout(seleccionadoLayout);
							seleccionado.setLayoutData(seleccionadoLData);
							{
								cSeleccionado = new CLabel(seleccionado, SWT.NONE);
								GridData cSeleccionadoLData = new GridData();
								cSeleccionadoLData.horizontalAlignment = GridData.FILL;
								cSeleccionadoLData.verticalAlignment = GridData.FILL;
								cSeleccionado.setLayoutData(cSeleccionadoLData);
								cSeleccionado.setText("Seleccionado");
							}
							{
								GridData cNomSelLData = new GridData();
								cNomSelLData.horizontalAlignment = GridData.FILL;
								cNomSelLData.verticalAlignment = GridData.FILL;
								cNomSelLData.grabExcessVerticalSpace = true;
								cNomSelLData.grabExcessHorizontalSpace = true;
								cNomSel = new CLabel(seleccionado, SWT.NONE);
								cNomSel.setLayoutData(cNomSelLData);
								cNomSel.setBackground(SWTResourceManager.getColor(255, 255, 255));
								cNomSel.setText("");
						
							}
						}
					}
				
				{
					GridData tablasDerechaLData = new GridData();
					tablasDerechaLData.verticalAlignment = GridData.FILL;
					tablasDerechaLData.horizontalAlignment = GridData.BEGINNING;
					tablasDerecha = new Composite(principal, SWT.NONE);
					GridLayout tablasDerechaLayout = new GridLayout();
					tablasDerecha.setLayout(tablasDerechaLayout);
					tablasDerechaLData.widthHint = 250;
					tablasDerecha.setLayoutData(tablasDerechaLData);
					
					{
						tablaExtras = new Composite(tablasDerecha, SWT.BORDER);
						GridLayout tablaExtrasLayout = new GridLayout();
						tablaExtrasLayout.numColumns = 2;
						GridData tablaExtrasLData = new GridData();
						tablaExtrasLData.verticalSpan = 0;
						tablaExtrasLData.horizontalSpan = 0;
						tablaExtrasLData.horizontalAlignment = GridData.FILL;
						tablaExtrasLData.verticalAlignment = GridData.FILL;
						tablaExtrasLData.grabExcessVerticalSpace = true;
						tablaExtrasLData.grabExcessHorizontalSpace = true;
						tablaExtras.setLayoutData(tablaExtrasLData);
						tablaExtras.setLayout(tablaExtrasLayout);
						{
							Extras = new CLabel(tablaExtras, SWT.NONE);
							Extras.setText("EXTRAS");
							Extras.setBackground(SWTResourceManager.getColor(220,189,224));
							Extras.setFont(SWTResourceManager.getFont("Palatino Linotype",12,1,false,false));
							Extras.setForeground(SWTResourceManager.getColor(255,255,255));
							Extras.setAlignment(SWT.CENTER);
							GridData ExtrasLData = new GridData();
							ExtrasLData.horizontalAlignment = GridData.FILL;
							ExtrasLData.verticalAlignment = GridData.BEGINNING;
							ExtrasLData.horizontalSpan = 2;
							ExtrasLData.widthHint = 199;
							ExtrasLData.heightHint = 28;
							ExtrasLData.grabExcessHorizontalSpace = true;
							Extras.setLayoutData(ExtrasLData);		
						}
						
						{
							NombreE = new CLabel(tablaExtras, SWT.NONE);
							NombreE.setText("NOMBRE");
							NombreE.setBackground(SWTResourceManager.getColor(220,189,224));
							NombreE.setForeground(SWTResourceManager.getColor(255,255,255));
							GridData NombreELData = new GridData();
							NombreELData.heightHint = 17;
							NombreELData.grabExcessHorizontalSpace = true;
							NombreELData.horizontalAlignment = GridData.FILL;
							NombreE.setLayoutData(NombreELData);
							NombreE.setAlignment(SWT.CENTER);
						}
						{
							horaE = new CLabel(tablaExtras, SWT.NONE);
							horaE.setText("HORA");
							horaE.setBounds(157, 34, 38, 17);
							horaE.setAlignment(SWT.CENTER);
							horaE.setBackground(SWTResourceManager.getColor(220, 189, 224));
							GridData horaELData = new GridData();
							horaELData.widthHint = 38;
							horaELData.heightHint = 17;
							horaE.setLayoutData(horaELData);
							horaE.setForeground(SWTResourceManager.getColor(255, 255, 255));
						}
/*						DatosLlamada e = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
						extra.add(e);
						listaLlamadasE();*/
					}
					{
						anadirE = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
						GridData a�adirELData = new GridData();
						a�adirELData.widthHint = 82;
						a�adirELData.heightHint = 23;
						a�adirELData.horizontalAlignment = GridData.CENTER;
						a�adirELData.verticalAlignment = GridData.BEGINNING;
						anadirE.setLayoutData(a�adirELData);
						anadirE.setText("A�adir");
						anadirE.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								anadirEWidgetSelected(evt);
								
							}
						});
					}
					{
						tablaLlamadas = new Composite(tablasDerecha, SWT.BORDER);
						GridLayout tablaLlamadasLayout = new GridLayout();
						GridData tablaLlamadasLData = new GridData();
						tablaLlamadasLData.verticalSpan = 0;
						tablaLlamadasLData.horizontalSpan = 0;
						tablaLlamadasLData.horizontalAlignment = GridData.FILL;
						tablaLlamadasLData.grabExcessVerticalSpace = true;
						tablaLlamadasLData.grabExcessHorizontalSpace = true;
						tablaLlamadasLData.verticalAlignment = GridData.FILL;
						tablaLlamadas.setLayoutData(tablaLlamadasLData);
						tablaLlamadas.setLayout(tablaLlamadasLayout);
						{
							llamadas = new CLabel(tablaLlamadas, SWT.NONE);
							GridData llamadasLData = new GridData();
							llamadasLData.horizontalAlignment = GridData.FILL;
							llamadasLData.verticalAlignment = GridData.BEGINNING;
							llamadasLData.verticalSpan = 0;
							llamadasLData.horizontalSpan = 0;
							llamadasLData.grabExcessHorizontalSpace = true;
							llamadas.setLayoutData(llamadasLData);
							llamadas.setText("LLAMADAS");
							llamadas.setAlignment(SWT.CENTER);
							llamadas.setBackground(SWTResourceManager.getColor(220, 189, 224));
							llamadas.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
							llamadas.setForeground(SWTResourceManager.getColor(255, 255, 255));
						}
						{
							nombreL = new CLabel(tablaLlamadas, SWT.NONE);
							GridData nombreLLData = new GridData();
							nombreLLData.verticalAlignment = GridData.BEGINNING;
							nombreLLData.horizontalAlignment = GridData.FILL;
							nombreL.setLayoutData(nombreLLData);
							nombreL.setText("NOMBRE");
							nombreL.setAlignment(SWT.CENTER);
							nombreL.setBackground(SWTResourceManager.getColor(220, 189, 244));
							nombreL.setForeground(SWTResourceManager.getColor(255, 255, 255));
						}
/*						DatosLlamada l = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
						llamada.add(l);
						listaLlamadasL();*/
						

					}
					{
						AnadirL = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
						GridData AnadirLLData = new GridData();
						AnadirLLData.widthHint = 79;
						AnadirLLData.heightHint = 23;
						AnadirLLData.horizontalSpan = 0;
						AnadirLLData.verticalSpan = 0;
						AnadirLLData.grabExcessVerticalSpace = true;
						AnadirLLData.grabExcessHorizontalSpace = true;
						AnadirLLData.horizontalAlignment = GridData.CENTER;
						AnadirLLData.verticalAlignment = GridData.BEGINNING;
						AnadirL.setLayoutData(AnadirLLData);
						AnadirL.setText("A�adir");
						AnadirL.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								anadirLWidgetSelected(evt);
								
							}
						});
					}
				}
			}
			}
			copiado=new DatosCitaSinValidar(false);
			ok=false;

			shell.layout();
		
			while (!shell.isDisposed()) {
				if (!disp.readAndDispatch())
					disp.sleep();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Aqui iran los metodos especificos de cada ventana
	/**
	 * Se llama desde generaTabla.
	 * genera la agenda (horas y pacientes) del medico que se le pasa por paramentro
	 * @param i :: indice del medico del que se quiere generar la agenda.
	 * Se generan las 'cabeceras' que indican las columnas de Horas, Nombre y Telefono para el medico que se especifica como parametro
	 */
	public void agendaMedico(int i){
		TabNomMed[i].setControl(agendaDinamica[i]);
		{
			Horas = new CLabel(agendaDinamica[i], SWT.NONE);
			GridData HorasLData = new GridData();
			HorasLData.widthHint = 70;
			HorasLData.heightHint = 28;
			Horas.setLayoutData(HorasLData);
			Horas.setText("HORAS");
			Horas.setBackground(SWTResourceManager.getColor(220, 189, 224));
			Horas.setForeground(SWTResourceManager.getColor(255, 255, 255));
			Horas.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
			Horas.setAlignment(SWT.CENTER);
		}
		{
			Nombre = new CLabel(agendaDinamica[i], SWT.NONE);
			Nombre.setText("NOMBRE");
			Nombre.setBackground(SWTResourceManager.getColor(220, 189, 224));
			Nombre.setForeground(SWTResourceManager.getColor(255, 255, 255));
			GridData NombreLData = new GridData();
			NombreLData.widthHint = 484;
			NombreLData.heightHint = 28;
			NombreLData.horizontalAlignment = GridData.FILL;
			NombreLData.grabExcessVerticalSpace = true;
			NombreLData.grabExcessHorizontalSpace = true;
			
			Nombre.setLayoutData(NombreLData);
			Nombre.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
			Nombre.setAlignment(SWT.CENTER);
		}
		{
			telefono = new CLabel(agendaDinamica[i], SWT.NONE);
			telefono.setText("TELEFONO");
			telefono.setBackground(SWTResourceManager.getColor(220, 189, 224));
			telefono.setForeground(SWTResourceManager.getColor(255, 255, 255));
			telefono.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
			GridData telefonoLData = new GridData();
			telefonoLData.heightHint = 28;
			telefonoLData.horizontalAlignment = GridData.FILL;
			telefonoLData.grabExcessVerticalSpace = true;
			telefonoLData.grabExcessHorizontalSpace = true;
			telefono.setLayoutData(telefonoLData);
			telefono.setAlignment(SWT.CENTER);
		}
		
	}
	
	/**
	 * Genera la agenda dinamicamente segun los datos obtenidos de la persistencia y que se alamacenan el a variable 'datos'
	 * TabNomMed		:: genera una pesta�a para cada medico
	 * agendaDinamica 	:: Dentro de cada pesta�a se genera la tabla de horas y pacientes segun corresponda
	 */
	public void generaTabla(){
		TabNomMed= new CTabItem[datos.getNumM()];
		agendaDinamica= new Composite[datos.getNumM()];
		{
			Agenda.dispose();
			Agenda = new CTabFolder(huecoAgenda, SWT.V_SCROLL);
										
				for(int i=0;i<datos.getNumM();i++){
					TabNomMed[i] = new CTabItem(Agenda, SWT.NONE);
					TabNomMed[i].setText(datos.getMedicos().get(i).getNombre());
		
					agendaDinamica[i] = new Composite(Agenda, SWT.SCROLL_PAGE|SWT.V_SCROLL|SWT.BORDER);
					GridLayout AgendaDinamicaLayout = new GridLayout();
					AgendaDinamicaLayout.numColumns = 3;
					GridData AgendaDinamicaLData = new GridData();				//Todo esto es nuevo
					AgendaDinamicaLData.verticalAlignment=GridData.FILL;
					AgendaDinamicaLData.horizontalAlignment = GridData.FILL;
					AgendaDinamicaLData.grabExcessHorizontalSpace = true;
					AgendaDinamicaLData.grabExcessVerticalSpace = false;
					agendaDinamica[i].setLayoutData(AgendaDinamicaLData);
					agendaDinamica[i].setLayout(AgendaDinamicaLayout);
					
					//genera la agenda (horas y pacientes) del medico que se le pasa por paramentro
					agendaMedico(i);
				}

			GridData AgendaLData = new GridData();
			AgendaLData.verticalAlignment = GridData.FILL;
			AgendaLData.horizontalAlignment = GridData.FILL;
			//AgendaLData.grabExcessVerticalSpace = true;
			AgendaLData.grabExcessHorizontalSpace = true;
			Agenda.setLayoutData(AgendaLData);
			Agenda.setSelection(0);
			
			int m=Agenda.getSelectionIndex();
			if (m>-1){
				boolean cu=false;
				int i=0;
				String medico=Agenda.getSelection().getText();
				while(i<datos.getNumM() && !cu){
					if (datos.getMedicos().get(i).getNombre().equals(medico)){
						RellenaTabla(datos.getMedicos().get(i).getMa�ana().getHInicio(), datos.getMedicos().get(i).getTarde().getHInicio(),
								datos.getMedicos().get(i).getMa�ana().getHFin(), datos.getMedicos().get(i).getTarde().getHFin(), man,m);
					}
					i++;
				}
				
				if (agenda.getText().equals("AGENDA")){
				cu=false;
				i=0;
				while(i<datos.getNumM() && !cu){
					if (datos.getMedicos().get(i).getNombre().equals(medico)){
						cu=true;
						for(int j=0;j<llamada.size();j++){
							NombresL[j].dispose();
						}
						for(int j=0;j<extra.size();j++){
							NombresE[j].dispose();
							horasE[j].dispose();
						}
						llamada=datos.getMedicos().get(i).getLlamadas();
						if(llamada==null){
							llamada=new ArrayList<DatosLlamada>();
						}
						extra=datos.getMedicos().get(i).getExtras();
						if(extra==null)
							extra=new ArrayList<DatosLlamada>();
						
						if (agenda.getText().equals("CAMBIO FECHA")){
						listaLlamadasL();
						listaLlamadasE();
						}
					}
					i++;	
				}
				}
			}
			huecoAgenda.layout();
			tablaLlamadas.layout();
			tablaExtras.layout();
			disp.update();
			Agenda.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					int m=Agenda.getSelectionIndex();
					if (m>-1){
						boolean cu=false;
						int i=0;
						String medico=Agenda.getSelection().getText();
						while(i<datos.getNumM() && !cu){
							if (datos.getMedicos().get(i).getNombre().equals(medico)){
								RellenaTabla(datos.getMedicos().get(i).getMa�ana().getHInicio(), datos.getMedicos().get(i).getTarde().getHInicio(),
										datos.getMedicos().get(i).getMa�ana().getHFin(), datos.getMedicos().get(i).getTarde().getHFin(), man,m);
							}
							i++;
						}
						if (agenda.getText().equals("CAMBIO FECHA")){
							cu=false;
							i=0;
							
							while(i<datos.getNumM() && !cu){
								if (datos.getMedicos().get(i).getNombre().equals(medico)){
									cu=true;
									for(int j=0;j<llamada.size();j++){
										NombresL[j].dispose();
									}
									for(int j=0;j<extra.size();j++){
										NombresE[j].dispose();
										horasE[j].dispose();
									}
									llamada=datos.getMedicos().get(i).getLlamadas();
									if(llamada==null){
										llamada=new ArrayList<DatosLlamada>();
									}
									extra=datos.getMedicos().get(i).getExtras();
									if(extra==null)
										extra=new ArrayList<DatosLlamada>();
									
									
									listaLlamadasL();
									listaLlamadasE();
									
								}
								i++;	
							}
						}
					}
					huecoAgenda.layout();
					tablaLlamadas.layout();
					tablaExtras.layout();
					disp.update();
				}
			});
			disp.update();
	}
	}
	/**
	 * Se llama desde RellenaTabla.
	 * Con esta funcion se rellenan los campos de nombre y telefono que correspondan segun lo extraido de la persistencia para el medico 
	 * pasado como parametrom y la fecha consultada
	 * @param medico	:: Medico que se consulta
	 * @param cc		:: longitud de la tabla
	 */
	private void agendaPersistencia(String medico, int cc){

		int i=0;
		boolean cu=false;
		ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
		while(i<datos.getNumM() && !cu){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				cu=true;
				ll=datos.getMedicos().get(i).getDatos();
				i--;
			}
			i++;	
		}
		
		for(i=0;i<ll.size();i++){
			for(int j=0; j<cc; j++){
			if (horas[j].getText().equals((ll.get(i).tomaHora()))){
					Nombres[j].setText(ll.get(i).tomaNombre()+" "+ll.get(i).tomaApell1()+" "+ll.get(i).getApell2());
					Telefonos[j].setText(ll.get(i).tomaTelf());
					if(ll.get(i).getEstado()==0){
						Nombres[j].setBackground(SWTResourceManager.getColor(255, 255, 255));
						Telefonos[j].setBackground(SWTResourceManager.getColor(255, 255, 255));
					}
					if(ll.get(i).getEstado()==1){
						Nombres[j].setBackground(SWTResourceManager.getColor(173, 216, 167));
						Telefonos[j].setBackground(SWTResourceManager.getColor(173, 216, 167));
					}
					if(ll.get(i).getEstado()==2){
						Nombres[j].setBackground(SWTResourceManager.getColor(255, 111, 111));
						Telefonos[j].setBackground(SWTResourceManager.getColor(255, 111, 111));
					}
					if(ll.get(i).getEstado()==3){
						Nombres[j].setBackground(SWTResourceManager.getColor(255, 184, 113));
						Telefonos[j].setBackground(SWTResourceManager.getColor(255, 184, 113));
					}
					if(ll.get(i).getEstado()==4){
						Nombres[j].setBackground(SWTResourceManager.getColor(209, 209, 209));
						Telefonos[j].setBackground(SWTResourceManager.getColor(209, 209, 209));
					}
				}
			}
		}		
	}
	
	/**
	 * * Se genera la tabla vacia de las horas (segun lo indicado en la persistencia para su intervalo de consulta, hora inicio y hora fin para ma�ana y tarde),
	 *  los nombres y los telefonos de la agenda de ese medico para el dia que se ha consultado
	 * @param iniMan	:: hora de inicio de la ma�ana
	 * @param iniTar	:: hora de inicio de la tarde
	 * @param finMan	:: Hora de fin de la ma�ana
	 * @param finTar	:: Hora de fin de la tarde
	 * @param man		:: indica si se esta rellenando el horario de ma�ana o de tarde
	 * @param k			:: Indica la agenda del medico que se esta rellenando
	 */

	private void RellenaTabla(Time iniMan, Time iniTar, Time finMan, Time finTar, boolean man, int k){
		if (man)
			tablaAux(iniMan, finMan, k);
		else
			tablaAux(iniTar,finTar, k);
	}
	
	private void tablaAux(Time inicio,Time fin, int k){
		int minIni=inicio.getHours()*60+inicio.getMinutes();
		int minFin=fin.getHours()*60+fin.getMinutes();
		String medico=Agenda.getItem(k).getText();
		boolean cu=false;
		int l=0;
		while(l<datos.getNumM() && !cu){
			if (datos.getMedicos().get(l).getNombre().equals(medico)){
				cu=true;
				l--;
			}
			l++;	
		}
		min=(minFin-minIni)/datos.getMedicos().get(l).getIntervalo();
		if(!init){
			//for (int i=0;i<min+1;i++){
			for (int i=0;i<horas.length;i++){
				horas[i].dispose();
				Nombres[i].dispose();
				Telefonos[i].dispose();
			}
		}
		int a=min/(60/datos.getMedicos().get(l).getIntervalo())+1;
		horas= new Button[min+1];
		aux1= new GridData[min+1];
		aux2= new GridData[min+1];
		aux3= new GridData[min+1];
		Nombres= new CLabel[min+1];
		Telefonos= new CLabel[min+1];
		 c=0;
		 int m=0;
		for (int i=0;i<a;i++){
			int j=0;
			while(j<60&& c<=min){
				if (i==0&&j==0){
					j=inicio.getMinutes();
				}
				
				if (i==a-1&& j>fin.getMinutes())
					break;
				horas[c] = new Button(agendaDinamica[k], SWT.PUSH | SWT.CENTER);
				aux1[c] = new GridData();
				aux1[c].horizontalAlignment = GridData.FILL;
				aux1[c].verticalAlignment = GridData.BEGINNING;      //nuevo
				horas[c].setLayoutData(aux1[c]);
				horas[c].setAlignment(SWT.FILL);
				String aux=String.valueOf(i+inicio.getHours());
				if (Integer.valueOf(aux)<10)
					aux="0"+aux;
				if(j==0)
					horas[c].setText(aux+":00");
				else
					horas[c].setText(aux+":"+String.valueOf(j));
				
				j=j+datos.getMedicos().get(l).getIntervalo();
				Nombres[c] = new CLabel(agendaDinamica[k], SWT.NONE);
				Telefonos[c] = new CLabel(agendaDinamica[k], SWT.NONE);
				
				opciones = new Menu(shell,SWT.POP_UP);
				Nombres[c].setMenu(opciones);
				
				Nombres[c].addMouseListener(new MouseAdapter() {
					public void mouseDoubleClick(MouseEvent evt) {
						
						nombreMouseDown(evt);
						
						
					}
				});
				
				Nombres[c].addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent evt) {
						nombreMouseDown2(evt);
					}
				});
					

				//se contruye un menu contextual asociado a cada campo nombre de la agenda
				MenuItem copiar = new MenuItem(opciones,SWT.PUSH);
				copiar.setText("Copiar");
				copiar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						CopiarWidgetSelected(evt);
						
					}
				});
			
				MenuItem pegar = new MenuItem(opciones,SWT.PUSH);
				pegar.setText("Pegar");
				pegar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						PegarWidgetSelected(evt);
						
					}
				});
				MenuItem Estado = new MenuItem(opciones,SWT.CASCADE);
				Estado.setText("Estado");
				Menu estadoMenu = new Menu(opciones);
				Estado.setMenu(estadoMenu);
				
				MenuItem espera = new MenuItem(estadoMenu,SWT.PUSH);
				espera.setText("En sala espera"); 
				espera.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EstadoWidgetSelected(evt,1);
					}
				});
				
				/* MenuItem Siguiente = new MenuItem(estadoMenu,SWT.PUSH);
				Siguiente.setText("El siguiente");
				Siguiente.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						SiguienteWidgetSelected(evt);
					}
				});*/
				
				MenuItem cobrado = new MenuItem(estadoMenu,SWT.PUSH);
				cobrado.setText("Cobrado");
				cobrado.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EstadoWidgetSelected(evt,4);
					}
				});		
				
				MenuItem EnConsulta = new MenuItem(estadoMenu,SWT.PUSH);
				EnConsulta.setText("Dentro Consulta");
				EnConsulta.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EstadoWidgetSelected(evt,2);
					}
				});	
				
				MenuItem visitado = new MenuItem(estadoMenu,SWT.PUSH);
				visitado.setText("Atendido");
				visitado.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EstadoWidgetSelected(evt,3);
					}
				});	
				
				MenuItem noEstablecido = new MenuItem(estadoMenu,SWT.PUSH);
				noEstablecido.setText("Ninguno");
				noEstablecido.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EstadoWidgetSelected(evt,0);
					}
				});
				
				Nombres[c].setText("");
				Telefonos[c].setText("");
				//}
				
				Nombres[c].setBackground(SWTResourceManager.getColor(255, 255, 255));
				Nombres[c].setAlignment(SWT.CENTER);
				aux2[c] = new GridData();
				aux2[c].horizontalAlignment = GridData.FILL;
				aux2[c].verticalAlignment = GridData.BEGINNING;      //nuevo
				aux2[c].widthHint = 484;

				aux2[c].grabExcessHorizontalSpace = true;
				Nombres[c].setLayoutData(aux2[c]);
				Telefonos[c].setBackground(SWTResourceManager.getColor(255, 255, 255));
				Telefonos[c].setAlignment(SWT.CENTER);
				aux3[c] = new GridData();
				aux3[c].horizontalAlignment = GridData.FILL;
				aux3[c].verticalAlignment = GridData.BEGINNING;      //nuevo
				aux3[c].grabExcessHorizontalSpace = true;
				Telefonos[c].setLayoutData(aux3[c]);

				c++;
				m++;
			}
			
		}
		int cc=c;
		agendaPersistencia(Agenda.getItem(k).getText(), cc);
		this.init=false;
		agendaDinamica[k].layout();
	}

	/**
	 * Accion del evento asociado al boton 'ma�ana'
	 * resetea el rellenado de la agenda con la previa actualizacion de la variable global que indica si se consulta
	 * el horario de ma�ana o de tarde
	 * @param evt
	 */
	private void MananaWidgetSelected(SelectionEvent evt) {
		man=true;
		int m=Agenda.getSelectionIndex();
		if (m>-1){
			boolean cu=false;
			int i=0;
			String medico=Agenda.getSelection().getText();
			while(i<datos.getNumM() && !cu){
				if (datos.getMedicos().get(i).getNombre().equals(medico)){
					RellenaTabla(datos.getMedicos().get(i).getMa�ana().getHInicio(), datos.getMedicos().get(i).getTarde().getHInicio(),
							datos.getMedicos().get(i).getMa�ana().getHFin(), datos.getMedicos().get(i).getTarde().getHFin(), man,m);
				}
				i++;
			}
		cu=false;
		i=0;
		while(i<datos.getNumM() && !cu){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				cu=true;
				for(int j=0;j<llamada.size();j++){
					NombresL[j].dispose();
				}
				for(int j=0;j<extra.size();j++){
					NombresE[j].dispose();
					horasE[j].dispose();
				}
				llamada=datos.getMedicos().get(i).getLlamadas();
				if(llamada==null){
					llamada=new ArrayList<DatosLlamada>();
				}
				extra=datos.getMedicos().get(i).getExtras();
				if(extra==null)
					extra=new ArrayList<DatosLlamada>();
				listaLlamadasL();
				listaLlamadasE();
			}
			i++;	
		}
		}
		huecoAgenda.layout();
		tablaLlamadas.layout();
		tablaExtras.layout();
		disp.update();
	}
	
	/**
	 * Accion del evento asociado al boton 'tarde'
	 * resetea el rellenado de la agenda con la previa actualizacion de la variable global que indica si se consulta
	 * el horario de ma�ana o de tarde
	 * @param evt
	 */
	private void TardeWidgetSelected(SelectionEvent evt) {
		man=false;
		int m=Agenda.getSelectionIndex();
		if (m>-1){
			boolean cu=false;
			int i=0;
			String medico=Agenda.getSelection().getText();
			while(i<datos.getNumM() && !cu){
				if (datos.getMedicos().get(i).getNombre().equals(medico)){
					RellenaTabla(datos.getMedicos().get(i).getMa�ana().getHInicio(), datos.getMedicos().get(i).getTarde().getHInicio(),
							datos.getMedicos().get(i).getMa�ana().getHFin(), datos.getMedicos().get(i).getTarde().getHFin(), man,m);
				}
				i++;
			}
			cu=false;
			i=0;
			
			while(i<datos.getNumM() && !cu){
				if (datos.getMedicos().get(i).getNombre().equals(medico)){
					cu=true;
					for(int j=0;j<llamada.size();j++){
						NombresL[j].dispose();
					}
					for(int j=0;j<extra.size();j++){
						NombresE[j].dispose();
						horasE[j].dispose();
					}
					llamada=datos.getMedicos().get(i).getLlamadas();
					if(llamada==null){
						llamada=new ArrayList<DatosLlamada>();
					}
					extra=datos.getMedicos().get(i).getExtras();
					if(extra==null)
						extra=new ArrayList<DatosLlamada>();
					listaLlamadasL();
					listaLlamadasE();
				}
				i++;	
			}
			}
		huecoAgenda.layout();
		tablaLlamadas.layout();
		tablaExtras.layout();
		disp.update();
		
		/**
		 * Accion del evento asociado al boton 'copiar'
		 * Copia el contenido del paciente seleccionado para posibilidad de pegado posterior usando la variable global 'copiado'
		 * @param evt
		 */	
	}
	private void CopiarWidgetSelected(SelectionEvent evt){
		
		//if (cNomSel.getText()=="")
		if(seleccion.getText().equals("")){
			usoAgente.mostrarMensajeError("Debe seleccionar un paciente", "Atenci�n");
			copiado=new DatosCitaSinValidar(false);
			
		}else{
			cNomSel.setBackground(SWTResourceManager.getColor(249, 120, 106));
			copiado=buscarSeleccionadoA(seleccion);
			copiado.setNuevo(true);
		}
	}
	/**
	 * Accion del evento asociado al boton 'pegar'
	 * pega el contenido del paciente copiado previamente en el lugar seleccionado'
	 * @param evt
	 */	
	private void PegarWidgetSelected(SelectionEvent evt){
		
		if (!copiado.getNuevo())
			usoAgente.mostrarMensajeError("No hay ningun paciente copiado", "Atenci�n");
		else{
			
				cNomSel.setBackground(SWTResourceManager.getColor(255, 255, 255));
				pegado=buscarSeleccionadoA(seleccion);
				String nombre=pegado.tomaNombre();
				//si la cita esta rellena de antes
				if (!nombre.equals("")){
					boolean cc=vis.mostrarMensajeAvisoC("Atenci�n", "�Esta seguro que desea reemplazar esta cita?");
					if (cc){
						int i=0;
						boolean Es =false;
						while (i<c & !Es){
							if(Nombres[i]==seleccion){
								Telefonos[i].setText(copiado.tomaTelf());
								Nombres[i].setText(copiado.tomaNombre());
								Es=true;
							}
							i++;
						}
						String m=Agenda.getSelection().getText();
						int w=0;
						boolean cu=false;
						ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
						while(w<datos.getNumM() && !cu){
							if (datos.getMedicos().get(w).getNombre().equals(m)){
								cu=true;
								ll=datos.getMedicos().get(w).getDatos();
								w--;
							}
							w++;	
						}
						
						cu=false;
						int v=0;
						int g=0;
						while(v<ll.size() &&!cu){
							if(ll.get(v).tomaHora().equals(pegado.tomaHora())){
								datos.getMedicos().get(w).getDatos().remove(v);
							}
							v++;
						
						}
						
						int j=0;
						cu=false;
						String u="";
						while(j<ll.size() && !cu){
							if (ll.get(j).tomaHora().equals(copiado.tomaHora())){
								u=ll.get(j).getUsuario();
								cu=true;
							}
							j++;
						}
						String n=copiado.tomaNombre();
						String[]aux=n.split(" ");
						String mm="";
						String ape1="";

						if (aux.length>1){
							ape1=aux[1];
						}
						for(int k=2;k<aux.length;k++){
							mm=mm+aux[k];
						}
						DatosCitaSinValidar d=new DatosCitaSinValidar(aux[0],ape1,mm,copiado.tomaTelf(), fd, pegado.tomaHora(),0,u);
						datos.getMedicos().get(w).getDatos().add(d);
						
						DatosCitaSinValidar c=new DatosCitaSinValidar(aux[0],ape1,mm,copiado.tomaTelf(), fy, pegado.tomaHora(),0,u);
	        			c.setMedico(datos.getMedicos().get(w).getUsuario());
	        			usoAgente.pegaCita(c);
	        			
					}
					//Si el expacio de la cita esta libre
				}else{
					int i=0;
					boolean Es =false;
					while (i<c & !Es){
						if(Nombres[i]==seleccion){
							Telefonos[i].setText(copiado.tomaTelf());
							Nombres[i].setText(copiado.tomaNombre());
							Es=true;
						}
						i++;
					}
					String m=Agenda.getSelection().getText();
					int w=0;
					boolean cu=false;
					ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
					while(w<datos.getNumM() && !cu){
						if (datos.getMedicos().get(w).getNombre().equals(m)){
							cu=true;
							ll=datos.getMedicos().get(w).getDatos();
							w--;
						}
						w++;	
					}
					int j=0;
					cu=false;
					String u="";
					while(j<ll.size() && !cu){
						if (ll.get(j).tomaHora().equals(copiado.tomaHora())){
							u=ll.get(j).getUsuario();
							cu=true;
						}
						j++;
					}
					
					String n=copiado.tomaNombre();
					String[]aux=n.split(" ");
					String mm="";
					String ape1="";
					if (aux.length>1){
						ape1=aux[1];
					}
					for(int k=2;k<aux.length;k++){
						mm=mm+aux[k];
					}
					DatosCitaSinValidar d=new DatosCitaSinValidar(aux[0],ape1,mm,copiado.tomaTelf(), fd, horas[i].getText(),0,u);
					datos.getMedicos().get(w).getDatos().add(d);
					
					DatosCitaSinValidar c=new DatosCitaSinValidar(aux[0],ape1,mm,copiado.tomaTelf(), fy, pegado.tomaHora(),0,u);
        			c.setMedico(datos.getMedicos().get(w).getUsuario());
        			usoAgente.pegaCita(c);
				}
				

		
		}
	}
	/**
	 * Accion del evento asociado al boton 'borrar'
	 * borra el contenido del paciente seleccionado tanto visualmente de la ventana como de la variable que contiene los datos de la persistencia (datos)
	 * @param evt
	 */	
	private void BorrarWidgetSelected(SelectionEvent e){
		if (seleccion.getText()=="")
			usoAgente.mostrarMensajeError("Debe seleccionar un paciente", "Atenci�n");
		else{
			//mostrar mensaje confirmacion de borrado
			boolean cc=vis.mostrarMensajeAvisoC("Atenci�n", "�Esta seguro que desea borrar esta cita?");
			if (cc){
				int i=0;
				boolean Es =false;
				while (i<c & !Es){
					if(Nombres[i]==seleccion){
						Telefonos[i].setText("");
						Nombres[i].setText("");
						Es=true;
						i--;
						
					}
					i++;
				}
				String m=Agenda.getSelection().getText();
				int w=0;
				boolean cu=false;
				ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
				while(w<datos.getNumM() && !cu){
					if (datos.getMedicos().get(w).getNombre().equals(m)){
						cu=true;
						ll=datos.getMedicos().get(w).getDatos();
						w--;
					}
					w++;	
				}
				cu=false;
				int v=0;
				int g=0;
				while(v<ll.size() &&!cu){
					if(ll.get(v).tomaHora().equals(horas[i].getText())){
						
						DatosCitaSinValidar d= datos.getMedicos().get(w).getDatos().get(v);
						d.setFecha(fy);
						d.setMedico(datos.getMedicos().get(w).getUsuario());
						datos.getMedicos().get(w).getDatos().remove(v);
						usoAgente.borraCita(d);
					}
					v++;
				
				}
			}
		}
	}
	
	/**
	 * Accion del evento asociado al boton 'crear Ficha'
	 * Muestra la ventana ficha. Si hay algun paciente seleccionado, rellena la nueva ventana con esos datos, si no, vacia.
	 * @param evt
	 */	
	private void CrearFichaWidgetSelected(SelectionEvent evt){
		//busqueda del paciente que se le pasa como parametro para recoger todos sus datos
		DatosCitaSinValidar d= buscarSeleccionado(seleccion);
		DatosCitaSinValidar a= new DatosCitaSinValidar(d.tomaNombre(), d.tomaTelf(), true,d.getUsuario());
		if (!d.tomaNombre().equals(""))
			usoAgente.mostrarVentanaFicha(a);
		else
			usoAgente.mostrarMensajeAviso( "Debe seleccionar un paciente","Error");

	}
	
	/**
	 * Accion del evento asociado al boton 'Dar Cita'
	* Muestra la ventana cita. Si hay algun paciente seleccionado, rellena la nueva ventana con esos datos, si no, vacia.
	 * @param evt
	 */	
	private void DarCitaWidgetSelectedB(SelectionEvent evt){
		if (!(seleccion==null)){
		DatosCitaSinValidar d= buscarSeleccionado(seleccion);
			usoAgente.mostrarVentanaCita(d.tomaNombre(), d.tomaApell1(),d.getApell2(), d.tomaTelf(), d.tomaHora(),fd,d.getUsuario());
		}else{
			usoAgente.mostrarMensajeError("Debe seleccionar una cita", "Atenci�n");
		}
	}
	
	/**
	 * Accion del evento asociado al boton 'a�adir extra'
	* Muestra la ventana extra. Si hay algun paciente seleccionado, rellena la nueva ventana con esos datos, si no, vacia.
	 * @param evt
	 */	
	private void anadirEWidgetSelected(SelectionEvent evt){
		boolean cu=false;
		int i=0;
		String u="";
		String medico=Agenda.getSelection().getText();
		while(i<datos.getNumM() && !cu){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				u=datos.getMedicos().get(i).getUsuario();
				
			}
			i++;
		}
		usoAgente.mostrarVentanaExtra(u,fy);
	}
	
	/**
	 * Accion del evento asociado al boton 'a�adir Llamada'
	* Muestra la ventana Llamada. Si hay algun paciente seleccionado, rellena la nueva ventana con esos datos, si no, vacia.
	 * @param evt
	 */	
	private void anadirLWidgetSelected(SelectionEvent evt){
		boolean cu=false;
		int i=0;
		String u="";
		String medico=Agenda.getSelection().getText();
		while(i<datos.getNumM() && !cu){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				u=datos.getMedicos().get(i).getUsuario();
			}
			i++;
		}
		usoAgente.mostrarVentanaLlamadas(u,fy);
	}
	/**
	 * Accion del evento asociado al hacer click sobre alguno de los nombres de la tabla, tanto si esta vacio como si no
	* Muestra la ventana Cita. Si hay algun paciente inscrito, rellena la nueva ventana con esos datos, si no, vacia.
	* Ademas muestra que se ha seleccionado cambiando de color dicha fila de la tabla
	 * @param evt
	 */	
	private void nombreMouseDown(MouseEvent evt) {
		String nombre=""; 
		String apellido="";
		String Telf="";
		String Hora="";
		boolean Es =false;
		CLabel lsel=(CLabel)evt.getSource();
		nombre=lsel.getText();

		if (!cNomSel.getText().equals(nombre)|| nombre.equals("")){
			DatosCitaSinValidar d= buscarSeleccionado(lsel);
			usoAgente.mostrarVentanaCita(d.tomaNombre(), d.tomaApell1(),d.getApell2(), d.tomaTelf(), d.tomaHora(),fd,d.getUsuario());
		}
		seleccion=lsel;
		
	}
	
	private void nombreMouseDown2(MouseEvent evt) {
		CLabel lsel=(CLabel)evt.getSource();
		String nombre=lsel.getText();
		for (int i=0;i<c;i++){
			Nombres[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			Telefonos[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			agendaPersistencia(Agenda.getItem(Agenda.getSelectionIndex()).getText(),c);
		}
		for(int i=0;i<llamada.size();i++){
			NombresL[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		
		for(int i=0;i<extra.size();i++){
			NombresE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			horasE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		
		if (!cNomSel.getText().equals(nombre)|| nombre.equals("")){
			
			lsel.setBackground(SWTResourceManager.getColor(123, 114, 211));
			buscarSeleccionado(lsel);
			cNomSel.setText(nombre);
		}
		else{
			cNomSel.setText("");
		}
		seleccion=lsel;
	}

	/**
	 * Busca el paciente seleccionado de la agenda visible actualmente buscandolo en la tabla.
	 * Normalmente esta funcion se usa para poder acceder a sus otros datos (hora de cita, telefono)
	 * @param nombre 	:: nombre del paciente a buscar
	 * @return d 	:: nos da informacion sobre el nombre, apellidos, telf, hora del paciente consultado
	 */
	
	public DatosCitaSinValidar buscarSeleccionado(String nombre){
		int i=0;
		int p=1;
		boolean Es =false;
		String apell2="";
		String ap1="";;
		String Telf="";
		String Hora="";
		String [] aux;
		while (i<c & !Es){
			if(Nombres[i].getText()==nombre){
				Telf=Telefonos[i].getText();
				Hora=horas[i].getText();
				Telefonos[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				aux=nombre.split(" ");
				if (aux.length>1){
					ap1=aux[1];
				}
				for(int k=2;k<aux.length;k++){
						apell2=apell2+aux[k];
				}
				Es=true;
			}
			i++;
		}
		DatosCitaSinValidar d= new DatosCitaSinValidar(nombre, ap1,apell2, Telf, Hora);
		return d;
		
	}
	
	/**
	 * Busca el paciente seleccionado de la agenda visible actualmente buscandolo en la tabla.
	 * Normalmente esta funcion se usa para poder acceder a sus otros datos (hora de cita, telefono)
	 * @param nombre 	:: nombre del paciente a buscar
	 * @return d	 	:: nos da informacion sobre el nombre, apellidos, telf, hora del paciente consultado
	 */
	public DatosCitaSinValidar buscarSeleccionado(CLabel nombre){
		int i=0;
		boolean Es =false;
		String apell2="";
		String ap1="";
		String Telf="";
		String Hora="";
		String [] aux;
		while (i<c & !Es){
			if(Nombres[i]==nombre){
				Telf=Telefonos[i].getText();
				Hora=horas[i].getText();
				Nombres[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				Telefonos[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				aux=nombre.getText().split(" ");
				
				if (aux.length>1){
					ap1=aux[1];
				}
				for(int k=2;k<aux.length;k++){
						apell2=apell2+aux[k];
				}
				Es=true;
			}
			i++;
		}
		int n=0;
		int t=0;
		boolean cumplee=false;
		//Buscar en los datos de la agenda el usuario
		for(int m=0;m<datos.getNumM();m++){
			if (datos.getMedicos().get(m).getNombre().equals(Agenda.getSelection().getText())){
				 n=0;
				while(n<datos.getMedicos().get(m).getDatos().size() && !cumplee){
					if (datos.getMedicos().get(m).getDatos().get(n).tomaHora().equals(Hora)){
						cumplee=true;
						t=m;
					}
					n++;
				}
			}
		}
		
		if (cumplee)
			n--;
		DatosCitaSinValidar d;
		if (c==i)
			d= new DatosCitaSinValidar("", ap1,apell2, Telf, Hora,false,"");
		else
			if(nombre.getText().equals(""))
				d= new DatosCitaSinValidar(nombre.getText(), ap1, apell2, Telf, Hora,false,"");
			else
				d= new DatosCitaSinValidar(nombre.getText(), ap1, apell2, Telf, Hora,false,datos.getMedicos().get(t).getDatos().get(n).getUsuario());
		return d;
	}
	
	/**
	 * Busca el paciente seleccionado de la agenda visible actualmente buscandolo en la tabla.
	 * Normalmente esta funcion se usa para poder acceder a sus otros datos (hora de cita, telefono)
	 * @param nombre 	:: nombre del paciente a buscar
	 * @return d		:: nos da informacion sobre nombre, telefono, hora y fecha del paciente buscado
	 */
	public DatosCitaSinValidar buscarSeleccionadoA(CLabel label){
		int i=0;
		int p=1;
		boolean Es =false;
		String nombre="";
		String apell1="";
		String Telf="";
		String Hora="";
		String fecha="";
		String [] aux;
		while (i<c & !Es){
			if(Nombres[i]==label){
				Telf=Telefonos[i].getText();
				Hora=horas[i].getText();
				Telefonos[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				aux=label.getText().split(" ");
				while (p<aux.length){
					if (p==1)
						apell1=aux[p];
					else
						apell1=apell1+" "+aux[p];
					p++;
				}
				Es=true;
			}
			i++;
		}
		DatosCitaSinValidar d= new DatosCitaSinValidar(label.getText(), Telf, Hora, fy);
	return d;
	}
	
	/**
	 * Busca el paciente seleccionado de la agenda visible actualmente buscandolo en la tabla.
	 * Normalmente esta funcion se usa para poder acceder a sus otros datos (hora de cita, telefono, fecha)
	 * @param nombre 	:: nombre del paciente a buscar
	 * @return d		:: nos da informacion sobre nombre, telefono, hora y fecha del paciente buscado
	 */
	public DatosCitaSinValidar buscarSeleccionado2(String nombre){
		int i=0;
		boolean Es =false;
		String apell1="";
		String Telf="";
		String Hora="";
		while (i<c & !Es){
			if(Nombres[i].getText()==nombre){
				Telf=Telefonos[i].getText();
				Hora=horas[i].getText();
				Telefonos[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				
				Es=true;
			}
			i++;
		}

		DatosCitaSinValidar d= new DatosCitaSinValidar(nombre, Telf, Hora, fy);
		return d;
		
	}
	
	public void setNombre(String nombre, String apellido) {
		Nombres[1].setText(nombre+" "+apellido);
		
	}
	/**
	 * Esta funcion se llama desde fuera (Cita) para comprobar a la hora de ampliar el periodo de una cita, si la agenda tiene hueco libre posterior para que sea
	 * factible.
	 * Ejemplo: Sea el intervalo= 15 min 
	 * 			Sea un paciente con cita de 9:00 a 9:15 (por defecto con duracion de un intervalo)
	 * 			Si hacemos click en la ventana de la cita en el boton '+' se aumenta el horario de cita en un intervalo (de 9:00 a 9:30) pero para
	 * 			que ello sea factible la cita de 9:15 a 9:30 debe estar libre. De esta comprobacion es de lo que se encarga esta funcion  
	 * @param d		:: datos de la cita que se quiere comprobar con los datos que se tienen de la agenda
	 */
	public boolean comprobarCita(final DatosCitaSinValidar d) {
		try{
			
			disp.syncExec(new Runnable() {
	            public void run() {
			         DatosCitaSinValidar d1=d;	   
			         int i=0;
			         while (i<c && !d1.tomaHora().equals(horas[i].getText())){
			        	 i++;
			         }
			         int j=0;
			         while (j<d1.tomaPeriodo() && Nombres[i].getText()=="" && Telefonos[i].getText()==""){
			        	 j++;
			         }
			         if(j==d1.tomaPeriodo()){
			         //Si acierto relleno la agenda con los datos y llamo a persistencia
			        	 for(int k=i;k<=i+d1.tomaPeriodo()-1;k++){
			        		 Nombres[i].setText(d1.tomaNombre()+" "+d1.tomaApell1()+" "+d1.getApell2());
			        		 Telefonos[i].setText(d1.tomaTelf());
			        		 String m=Agenda.getSelection().getText();
			        			int w=0;
			        			boolean cu=false;
			        			ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
			        			while(w<datos.getNumM() && !cu){
			        				if (datos.getMedicos().get(w).getNombre().equals(m)){
			        					cu=true;
			        					ll=datos.getMedicos().get(w).getDatos();
			        					w--;
			        				}
			        				w++;	
			        			}
			        			cu=false;
			        			int v=0;
			        			int g=0;
			        			while(v<ll.size() &&!cu){
			        				if(ll.get(v).tomaHora().equals(d1.tomaHora())){
			        					datos.getMedicos().get(w).getDatos().get(v).setNombre(d1.tomaNombre());
			        					datos.getMedicos().get(w).getDatos().get(v).setTelf(d1.tomaTelf());
			        				}
			        			
			        				v++;
			        			}
			        			DatosCitaSinValidar c=new DatosCitaSinValidar(d1.tomaNombre(),d1.tomaApell1(),d1.getApell2(), d1.tomaTelf(), fy, d1.tomaHora(),0,d1.getUsuario());
			        			datos.getMedicos().get(w).getDatos().add(c);
			        			d.setMedico(datos.getMedicos().get(w).getUsuario());
			        			d.setFecha(fy);
			        	 }
			        	 ok=true;
			         }
			         else{
			        	 if(usoAgente.mostrarMensajeAvisoC("Al insertar esta cita se sobreescribe sobre la agenda actual. �Esta seguro de que desea continuar?", "Atencion")){
			        		 for(int k=i;k<=i+d1.tomaPeriodo()-1;k++){
				        		 Nombres[i].setText(d1.tomaNombre()+" "+d1.tomaApell1()+" "+d1.getApell2());
				        		 Telefonos[i].setText(d1.tomaTelf());
				        		 String m=Agenda.getSelection().getText();
				        			int w=0;
				        			boolean cu=false;
				        			ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
				        			while(w<datos.getNumM() && !cu){
				        				if (datos.getMedicos().get(w).getNombre().equals(m)){
				        					cu=true;
				        					ll=datos.getMedicos().get(w).getDatos();
				        					w--;
				        				}
				        				w++;	
				        			}
				        			cu=false;
				        			int v=0;
				        			int g=0;
				        			while(v<ll.size() &&!cu){
				        				if(ll.get(v).tomaHora().equals(d1.tomaHora())){
				        					datos.getMedicos().get(w).getDatos().get(v).setNombre(d1.tomaNombre());
				        					datos.getMedicos().get(w).getDatos().get(v).setTelf(d1.tomaTelf());
				        				}
				        			
				        				v++;
				        			}
				        			DatosCitaSinValidar c=new DatosCitaSinValidar(d1.tomaNombre(),d1.tomaApell1(),d1.getApell2(), d1.tomaTelf(), fy, d1.tomaHora(),0,d1.getUsuario());
				        			datos.getMedicos().get(w).getDatos().add(c);
				        			d.setMedico(datos.getMedicos().get(w).getUsuario());
				        			d.setFecha(fy);
				        	 }
			        		 ok=true;
			        	 }
			        	 else{
			        		 ok=false;
			        		 //usoAgente.mostrarVentanaCita(d.tomaNombre(), d.tomaApell1(), d.getApell2(), d.tomaTelf(), d.tomaHora(), fd);
			        	 }
			         }
			         
	            }
	         });
			return ok;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Funcion que se llama externamente (ventana de llamadas tras darle al boton 'aceptar')que se encarga de insertar una nueva llamada en la tabla 
	 * de llamadas de la agenda
	 * @param d		:: llamada que se quiere insertar
	 */
	public void insertaLlamada(final DatosLlamada d){
		
		try{
			disp.asyncExec(new Runnable() {
	            public void run() {
	            	boolean esta=false;
	            	if (!llamada.isEmpty()){
	            		for (int i=0;i<llamada.size();i++){	            		
	            			NombresL[i].dispose();
	            			if (llamada.get(i).getHora().equals(d.getHora()) && llamada.get(i).getNombre().equals(d.getNombre()))
	            				esta=true;
	            		}
	            	}
	            	
	            	//Y ESTO QUE HAGO?????
            		if(!esta){
            			llamada.add(d);
            		}
            		int m=Agenda.getSelectionIndex();
            		datos.getMedicos().get(m).setLlamadas(llamada);
	            	listaLlamadasL();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
	/**
	 * Funcion que se llama externamente (ventana de llamadas)que se encarga de modificar una llamada de la tabla 
	 * de llamadas de la agenda
	 * @param d		:: llamada que se quiere modificar
	 */
	public void modificaLlamada(final DatosLlamada dA, final DatosLlamada dP){
		
		try{
			disp.asyncExec(new Runnable() {
	            public void run() {
	            	boolean esta=false;
	            	if (!llamada.isEmpty()){
	            		for (int i=0;i<llamada.size();i++){	            		
	            			NombresL[i].dispose();
	            			if (llamada.get(i).getHora().equals(dA.getHora()) && llamada.get(i).getNombre().equals(dA.getNombre())){
	            				esta=true;
	            				llamada.get(i).setNombre(dP.getNombre());
	            				llamada.get(i).setMensaje(dP.getMensaje());
	            				llamada.get(i).setTelf(dP.getTelf());
	            				llamada.get(i).setPaciente(dP.getPaciente());
	            				llamada.get(i).setUsuario(dP.getUsuario());
	            				dP.setHora(dA.getHora());
	            			}
	            		}
	            	}
	    
            		if(!esta){
            			llamada.add(dP);
            			//a�adir fecha y mandar a persistencia
            		}
            		int m=Agenda.getSelectionIndex();
            		datos.getMedicos().get(m).setLlamadas(llamada);
	            	listaLlamadasL();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	/**
	 * Funcion que se llama externamente (ventana de llamadas)que se encarga de borrar unallamada en la tabla 
	 * de llamadas de la agenda
	 * @param d		:: llamada que se quiere borrar
	 */
	public void borraLlamada(final DatosLlamada d){
		try{
			disp.asyncExec(new Runnable() {
				int j=0;
				boolean esta=false;
	            public void run() {
	            	if (!llamada.isEmpty()){
	            		for (int i=0;i<llamada.size();i++){	            		
	            			NombresL[i].dispose();
	            			if (llamada.get(i).getHora().equals(d.getHora()) && llamada.get(i).getNombre().equals(d.getNombre())){
	            				esta=true;
	            				j=i;
	            			}
	            		}
	            	}
	            	if(esta)
            			llamada.remove(j);
            		
	            	listaLlamadasL();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
	/**
	 * Funcion que se llama externamente (ventana de Extras tras darle al boton 'aceptar')que se encarga de insertar un nuevo extra en la tabla 
	 * de extras de la agenda
	 * @param d		:: extra que se quiere insertar
	 */
	public void insertaExtra(final DatosLlamada d){
		
		try{
			disp.asyncExec(new Runnable() {
	            public void run() {
	            	boolean esta=false;
	            	if (!extra.isEmpty()){
	            		for (int i=0;i<extra.size();i++){	            		
	            			NombresE[i].dispose();
	            			horasE[i].dispose();
	            			if (extra.get(i).getHora().equals(d.getHora()) && extra.get(i).getNombre().equals(d.getNombre()))
	            				esta=true;
	            		}
	            	}
	            	
	            	
            		if(!esta){
            			extra.add(d);
            		}
            		int m=Agenda.getSelectionIndex();
            		datos.getMedicos().get(m).setExtras(extra);
            		listaLlamadasE();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
		
	/**
	 * Funcion que se llama externamente (ventana de extras)que se encarga de modificar un extra en la tabla 
	 * de extras de la agenda
	 * @param d		:: extra que se quiere modificar
	 */
	public void modificaExtra(final DatosLlamada dA, final DatosLlamada dP){
		
		try{
			disp.asyncExec(new Runnable() {
	            public void run() {
	            	boolean esta=false;
	            	if (!extra.isEmpty()){
	            		for (int i=0;i<extra.size();i++){	            		
	            			NombresE[i].dispose();
	            			horasE[i].dispose();
	            			//if (extra.get(i).getHora().equals(dA.getHora()) && extra.get(i).getNombre().equals(dA.getNombre())){
	            			if (extra.get(i).getHora().equals(dA.getHora()) && extra.get(i).getUsuario().equals(dA.getUsuario())){
	            				esta=true;
	            				extra.get(i).setNombre(dP.getNombre());
	            				extra.get(i).setMensaje(dP.getMensaje());
	            				extra.get(i).setTelf(dP.getTelf());
	            				extra.get(i).setPaciente(dP.getPaciente());
	            				extra.get(i).setUsuario(dP.getUsuario());
	            				dP.setHora(dA.getHora());
	            			}
	            		}
	            	}
	            	
	            	//Y ESTO QUE HAGO?????
            		if(!esta){
            			//mandar a persistencia con fecha y tipo
            			extra.add(dP);
            		}
            		int m=Agenda.getSelectionIndex();
            		datos.getMedicos().get(m).setExtras(extra);
	            	listaLlamadasE();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
	/**
	 * Funcion que se llama externamente (ventana de extras)que se encarga de borrar un extra en la tabla 
	 * de extras de la agenda
	 * @param d		:: extra que se quiere insertar
	 */
	public void borraExtra(final DatosLlamada d){
		try{
			disp.asyncExec(new Runnable() {
				int j=0;
				boolean esta=false;
	            public void run() {
	            	if (!extra.isEmpty()){
	            		for (int i=0;i<extra.size();i++){	            		
	            			NombresE[i].dispose();
	            			horasE[i].dispose();
	            			if (extra.get(i).getHora().equals(d.getHora()) && extra.get(i).getNombre().equals(d.getNombre())){
	            				esta=true;
	            				j=i;
	            			}
	            		}
	            	}
	            	if(esta)
            			extra.remove(j);
            		
	            	listaLlamadasE();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
	/**
	 * Funcion que genera y rellena la tabla de llamadas de la agenda
	 */
	public void listaLlamadasL(){
		if (!(llamada==null)){
		NombresL= new CLabel[llamada.size()];
		TelefonosL= new String[llamada.size()];
		MensajeL= new String [llamada.size()];
		PacienteL = new boolean[llamada.size()];
		horasL = new String[llamada.size()];
		for(int i=0;i<llamada.size();i++){
			NombresL[i]=new CLabel(tablaLlamadas, SWT.NONE);
			NombresL[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			NombresL[i].setText(llamada.get(i).getNombre());
			TelefonosL[i]=llamada.get(i).getTelf();
			MensajeL[i]=llamada.get(i).getMensaje();
			PacienteL[i]=llamada.get(i).getPaciente();
			horasL[i]=llamada.get(i).getHora();
			tablaLlamadas.layout();
			NombresL[i].addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent evt) {
					nombreLMouseDown(evt);
				}
			});
		}
		}
		tablasDerecha.layout();
		disp.update();
	}
	
	/**
	 * Funcion que genera y rellena la tabla de extras de la agenda
	 */
	public void listaLlamadasE(){
		if (!(extra==null)){
		NombresE= new CLabel[extra.size()];
		TelefonosE= new String[extra.size()];
		MensajeE= new String [extra.size()];
		PacienteE = new boolean[extra.size()];
		horasE = new CLabel[extra.size()];
		for(int i=0;i<extra.size();i++){
			NombresE[i]=new CLabel(tablaExtras, SWT.NONE);
			horasE[i]=new CLabel(tablaExtras, SWT.NONE);
			NombresE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			NombresE[i].setText(extra.get(i).getNombre());
			TelefonosE[i]=extra.get(i).getTelf();
			MensajeE[i]=extra.get(i).getMensaje();
			PacienteE[i]=extra.get(i).getPaciente();
			horasE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			horasE[i].setText(extra.get(i).getHora());
			tablaExtras.layout();
			NombresE[i].addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent evt) {
					nombreEMouseDown(evt);
				}
			});
		}
		}
		tablasDerecha.layout();
		disp.update();
	}
	
	/**
	 * Busca el elemento que se le pasa (paciente) en la tabla de llamadas
	 * @param nombre
	 * @return d	:: devuelve los datos asociados al paciente buscado
	 */
	public DatosLlamada buscarSeleccionadoL(String nombre){
		int i=0;
		boolean Es =false;
		Boolean paciente =false;
		String Telf="";
		String mensaje="";
		String h="";
		while (i<llamada.size() & !Es){
			if(NombresL[i].getText()==nombre){
				Telf=TelefonosL[i];
				mensaje=MensajeL[i];
				NombresL[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				paciente=PacienteL[i];
				h=horasL[i];
				Es=true;
			}
			i++;
		}
		DatosLlamada d= new DatosLlamada(nombre, mensaje, Telf, paciente,h);
		return d;
		
	}
	
	/**
	 * Funcion del evento asociado al seleccionar un paciente de la tabla de llamadas
	 * Como resultados mostramos una ventana de llamadas con los datos del paciente seleccionado
	 * @param evt
	 */
	private void nombreLMouseDown(MouseEvent evt) {
		CLabel lsel=(CLabel)evt.getSource();
		String nombre=lsel.getText();
		
		for(int i=0;i<llamada.size();i++){
			NombresL[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		for(int i=0;i<extra.size();i++){
			NombresE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			horasE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		int i;
		for (i=0;i<c;i++){
			Nombres[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			Telefonos[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		if (cNomSel.getText()!=nombre){
			
			lsel.setBackground(SWTResourceManager.getColor(123, 114, 211));
			DatosLlamada d= buscarSeleccionadoL(nombre);
			cNomSel.setText(nombre);

			boolean cu=false;
			i=0;
			String u="";
			String uPaciente="";
			
			ArrayList<DatosLlamada> ll=new ArrayList<DatosLlamada>();
			String medico=Agenda.getSelection().getText();
			while(i<datos.getNumM() && !cu){
				if (datos.getMedicos().get(i).getNombre().equals(medico)){
					u=datos.getMedicos().get(i).getUsuario();
					ll=datos.getMedicos().get(i).getLlamadas();
					cu=true;
					i--;
				}
				
				i++;
			}

			cu=false;
			int v=0;
			String n=d.getNombre();
			String[] aux;
			aux=n.split(" ");
			//n=aux[0];
			String ap1="";
			String ap2="";
			if (aux.length>1){
				ap1=aux[1];
			}
			for(int k=2;k<aux.length;k++){
					ap2=ap2+aux[k];
			}
			while(v<ll.size() &&!cu){
				if(ll.get(v).getNombre().equals(n)){
					uPaciente=ll.get(v).getUsuario();
				}
			
				v++;
			}

			usoAgente.mostrarVentanaLlamada(d.getNombre(), d.getMensaje(), d.getTelf(), d.getPaciente(),d.getHora(),u,fy,uPaciente);
		}
		else{
			cNomSel.setText("");
		}
		seleccion=lsel;
	}
	
	/**
	 * Busca el elemento que se le pasa (paciente) en la tabla de extras
	 * @param nombre
	 * @return d	:: devuelve los datos asociados al paciente buscado
	 */
	public DatosLlamada buscarSeleccionadoE(String nombre){
		int i=0;
		boolean Es =false;
		Boolean paciente =false;
		String Telf="";
		String mensaje="";
		String h="";
		while (i<extra.size() & !Es){
			if(NombresE[i].getText()==nombre){
				Telf=TelefonosE[i];
				mensaje=MensajeE[i];
				NombresE[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				horasE[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				paciente=PacienteE[i];
				h=horasE[i].getText();
				Es=true;
			}
			i++;
		}
		DatosLlamada d= new DatosLlamada(nombre, mensaje, Telf, paciente,h);
		return d;
		
	}
	
	/**
	 * Funcion del evento asociado al seleccionar un paciente de la tabla de llamadas
	 * Como resultados mostramos una ventana de llamadas con los datos del paciente seleccionado
	 * @param evt
	 */
	private void nombreEMouseDown(MouseEvent evt) {
		CLabel lsel=(CLabel)evt.getSource();
		String nombre=lsel.getText();
		
		for(int i=0;i<llamada.size();i++){
			NombresL[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		
		for(int i=0;i<extra.size();i++){
			NombresE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			horasE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		int i;
		for (i=0;i<c;i++){
			Nombres[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			Telefonos[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		if (cNomSel.getText()!=nombre){
			
			lsel.setBackground(SWTResourceManager.getColor(123, 114, 211));
			DatosLlamada d= buscarSeleccionadoE(nombre);
			cNomSel.setText(nombre);
			boolean cu=false;
			i=0;
			String u="";
			String uPaciente="";
			
			ArrayList<DatosLlamada> ll=new ArrayList<DatosLlamada>();
			String medico=Agenda.getSelection().getText();
			while(i<datos.getNumM() && !cu){
				if (datos.getMedicos().get(i).getNombre().equals(medico)){
					u=datos.getMedicos().get(i).getUsuario();
					ll=datos.getMedicos().get(i).getExtras();
					cu=true;
					i--;
				}
				
				i++;
			}

			cu=false;
			int v=0;
			String n=d.getNombre();
			String[] aux;
			aux=n.split(" ");
			//n=aux[0];
			String ap1="";
			String ap2="";
			if (aux.length>1){
				ap1=aux[1];
			}
			for(int k=2;k<aux.length;k++){
					ap2=ap2+aux[k];
			}
			while(v<ll.size() &&!cu){
				if(ll.get(v).getNombre().equals(n)){
					uPaciente=ll.get(v).getUsuario();
				}
			
				v++;
			}
			usoAgente.mostrarVentanaExtra(d.getNombre(), d.getMensaje(), d.getTelf(), d.getPaciente(),d.getHora(),u,fy,uPaciente);
		}
		else{
			cNomSel.setText("");
		}
		seleccion=lsel;
	}
	
	
	/**
	 * Funcion asociada al evento del boton 'agenda'
	 * Cambia el boton de nombre
	 * Cambiar el titulo del shell
	 * regenera la parte derecha de la ventana con los calendarios o las tablas de llamadas y extras segun corresponda
	 * @param evt
	 */
	private void agendaWidgetSelected(SelectionEvent evt){
		if (agenda.getText().equals("AGENDA")){
			agenda.setText("CAMBIO FECHA");
			GridData button1LData = new GridData();
			button1LData.horizontalAlignment = GridData.FILL;
			agenda.setLayoutData(button1LData);	
		}else{
			agenda.setText("AGENDA");	
		}
		fant=fy;
		//Regenera la parte derecha de la ventana de la agenda segun corresponda
		inicializa(agenda.getText());
	}
	/**
	 * Regenera la parte derecha de la ventana de la agenda segun corresponda
	 * @param a		:: su valor nos identifica si se deben mostrar los calendarios ('agenda hoy') o las tablas de llamadas y extras ('agenda')
	 */
	public void inicializa(String a){
		
		//BORRAR
		tablaExtras.dispose();
		tablaLlamadas.dispose();
		Extras.dispose();
		NombreE.dispose();
		horaE.dispose();
		anadirE.dispose();
		nombreL.dispose();
		AnadirL.dispose();
/*		llamada.clear();
		extra.clear();*/
				
		if(a.equals("AGENDA")){
			
			shell.setText("Agenda "+fd);
		{

		//inicializar tabla extras
			tablaExtras = new Composite(tablasDerecha, SWT.NONE);
			GridLayout tablaExtrasLayout = new GridLayout();
			tablaExtrasLayout.numColumns = 2;
			GridData tablaExtrasLData = new GridData();
			tablaExtrasLData.verticalSpan = 0;
			tablaExtrasLData.horizontalSpan = 0;
			tablaExtrasLData.horizontalAlignment = GridData.FILL;
			tablaExtrasLData.verticalAlignment = GridData.FILL;
			tablaExtrasLData.grabExcessVerticalSpace = true;
			tablaExtrasLData.grabExcessHorizontalSpace = true;
			tablaExtras.setLayoutData(tablaExtrasLData);
			tablaExtras.setLayout(tablaExtrasLayout);
			
				final DateTime calendario = new DateTime(tablaExtras, SWT.CALENDAR);
				
				GridData calendarioLData = new GridData();
				calendarioLData.verticalAlignment = GridData.BEGINNING;
				calendarioLData.horizontalSpan = 2;
				calendarioLData.grabExcessVerticalSpace = true;
				calendario.setLayoutData(calendarioLData);
				calendario.addMouseListener(new MouseListener () {
					public void mouseDoubleClick(MouseEvent e) {
						// TODO �Y esto por qu� no va?
								man=true;
								fecha = new Date(calendario.getYear()-1900,calendario.getMonth(),calendario.getDay());
								fd=fecha.getDate()+"-"+(fecha.getMonth()+1)+"-"+(fecha.getYear()+1900);
								shell.setText("Agenda "+fd);
								fy=(fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-"+fecha.getDate()+" 00:00:00";
								datos.setFecha(fant);
								//usoAgente.guardarAgenda(datos);
								usoAgente.mostrarAgendaSecretaria(fy,usuEste);
								fant=fy;
						//shell.dispose();
					}
					public void mouseUp(MouseEvent e) {};
					public void mouseDown(MouseEvent e) {};
				});
			}
		
		//inicializar tabla llamadas
		
		tablaLlamadas = new Composite(tablasDerecha, SWT.NONE);
		GridLayout tablaLlamadasLayout = new GridLayout();
		tablaLlamadasLayout.numColumns = 2;
		GridData tablaLlamadasLData = new GridData();
		tablaLlamadasLData.verticalSpan = 0;
		tablaLlamadasLData.horizontalSpan = 0;
		tablaLlamadasLData.horizontalAlignment = GridData.BEGINNING;
		tablaLlamadasLData.grabExcessVerticalSpace = true;
		tablaLlamadasLData.grabExcessHorizontalSpace = true;
		tablaLlamadasLData.verticalAlignment = GridData.BEGINNING;
		tablaLlamadasLData.horizontalAlignment = GridData.BEGINNING;

		tablaLlamadas.setLayoutData(tablaLlamadasLData);
		tablaLlamadas.setLayout(tablaLlamadasLayout);

        
        final DateTime calendario2 = new DateTime (tablaLlamadas, SWT.CALENDAR);
        calendario2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 2, 1));
        calendario2.setMonth(4);
        calendario2.addMouseListener(new MouseListener () {
                public void mouseDoubleClick(MouseEvent e) {
                	man=true;
					fecha = new Date(calendario2.getYear()-1900,calendario2.getMonth(),calendario2.getDay());
					calendario2.setForeground(SWTResourceManager.getColor(255, 0, 0));
					fd=fecha.getDate()+"-"+(fecha.getMonth()+1)+"-"+(fecha.getYear()+1900);
					shell.setText("Agenda "+fd);
					fy=(fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-"+fecha.getDate()+" 00:00:00";
					datos.setFecha(fant);
					//usoAgente.guardarAgenda(datos);
					usoAgente.mostrarAgendaSecretaria(fy,usuEste);
					fant=fy;    
                }
                public void mouseUp(MouseEvent e) {};
                public void mouseDown(MouseEvent e) {};
        });
		}else{

			//redefinir tabla extras
			tablaExtras = new Composite(tablasDerecha, SWT.BORDER);
			GridLayout tablaExtrasLayout = new GridLayout();
			tablaExtrasLayout.numColumns = 2;
			GridData tablaExtrasLData = new GridData();
			tablaExtrasLData.verticalSpan = 0;
			tablaExtrasLData.horizontalSpan = 0;
			tablaExtrasLData.horizontalAlignment = GridData.FILL;
			tablaExtrasLData.verticalAlignment = GridData.FILL;
			tablaExtrasLData.grabExcessVerticalSpace = true;
			tablaExtrasLData.grabExcessHorizontalSpace = true;
			tablaExtras.setLayoutData(tablaExtrasLData);
			tablaExtras.setLayout(tablaExtrasLayout);
			
			shell.setText("Consulta de Hoy  "+fd);
			
			anadirE = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
			tablaLlamadas = new Composite(tablasDerecha, SWT.BORDER);
			AnadirL = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
			
			GridLayout tablaLlamadasLayout = new GridLayout();
			GridData tablaLlamadasLData = new GridData();
			tablaLlamadasLData.verticalSpan = 0;
			tablaLlamadasLData.horizontalSpan = 0;
			tablaLlamadasLData.horizontalAlignment = GridData.FILL;
			tablaLlamadasLData.grabExcessVerticalSpace = true;
			tablaLlamadasLData.grabExcessHorizontalSpace = true;
			tablaLlamadasLData.verticalAlignment = GridData.FILL;
			tablaLlamadas.setLayoutData(tablaLlamadasLData);
			tablaLlamadas.setLayout(tablaLlamadasLayout);
			{
				Extras = new CLabel(tablaExtras, SWT.NONE);
				Extras.setText("EXTRAS");
				Extras.setBackground(SWTResourceManager.getColor(220,189,224));
				Extras.setFont(SWTResourceManager.getFont("Palatino Linotype",12,1,false,false));
				Extras.setForeground(SWTResourceManager.getColor(255,255,255));
				Extras.setAlignment(SWT.CENTER);
				GridData ExtrasLData = new GridData();
				ExtrasLData.horizontalAlignment = GridData.FILL;
				ExtrasLData.verticalAlignment = GridData.BEGINNING;
				ExtrasLData.horizontalSpan = 2;
				ExtrasLData.widthHint = 199;
				ExtrasLData.heightHint = 28;
				ExtrasLData.grabExcessHorizontalSpace = true;
				Extras.setLayoutData(ExtrasLData);		
			}
			{
				NombreE = new CLabel(tablaExtras, SWT.NONE);
				NombreE.setText("NOMBRE");
				NombreE.setBackground(SWTResourceManager.getColor(220,189,224));
				NombreE.setForeground(SWTResourceManager.getColor(255,255,255));
				GridData NombreELData = new GridData();
				NombreELData.heightHint = 17;
				NombreELData.grabExcessHorizontalSpace = true;
				NombreELData.horizontalAlignment = GridData.FILL;
				NombreE.setLayoutData(NombreELData);
				NombreE.setAlignment(SWT.CENTER);
			}
			{
				horaE = new CLabel(tablaExtras, SWT.NONE);
				horaE.setText("HORA");
				horaE.setBounds(157, 34, 38, 17);
				horaE.setAlignment(SWT.CENTER);
				horaE.setBackground(SWTResourceManager.getColor(220, 189, 224));
				GridData horaELData = new GridData();
				horaELData.widthHint = 38;
				horaELData.heightHint = 17;
				horaE.setLayoutData(horaELData);
				horaE.setForeground(SWTResourceManager.getColor(255, 255, 255));
			}

			{
			
			GridData a�adirELData = new GridData();
			a�adirELData.widthHint = 82;
			a�adirELData.heightHint = 23;
			a�adirELData.horizontalAlignment = GridData.CENTER;
			a�adirELData.verticalAlignment = GridData.BEGINNING;
			anadirE.setLayoutData(a�adirELData);
			anadirE.setText("A�adir");
			anadirE.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					anadirEWidgetSelected(evt);
					
				}
			});
		}
		{
			
			{
				llamadas = new CLabel(tablaLlamadas, SWT.NONE);
				GridData llamadasLData = new GridData();
				llamadasLData.horizontalAlignment = GridData.FILL;
				llamadasLData.verticalAlignment = GridData.BEGINNING;
				llamadasLData.verticalSpan = 0;
				llamadasLData.horizontalSpan = 0;
				llamadasLData.grabExcessHorizontalSpace = true;
				llamadas.setLayoutData(llamadasLData);
				llamadas.setText("LLAMADAS");
				llamadas.setAlignment(SWT.CENTER);
				llamadas.setBackground(SWTResourceManager.getColor(220, 189, 224));
				llamadas.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
				llamadas.setForeground(SWTResourceManager.getColor(255, 255, 255));
			}
			{
				nombreL = new CLabel(tablaLlamadas, SWT.NONE);
				GridData nombreLLData = new GridData();
				nombreLLData.verticalAlignment = GridData.BEGINNING;
				nombreLLData.horizontalAlignment = GridData.FILL;
				nombreL.setLayoutData(nombreLLData);
				nombreL.setText("NOMBRE");
				nombreL.setAlignment(SWT.CENTER);
				nombreL.setBackground(SWTResourceManager.getColor(220, 189, 244));
				nombreL.setForeground(SWTResourceManager.getColor(255, 255, 255));
			}
			
			{
				
				GridData AnadirLLData = new GridData();
				AnadirLLData.widthHint = 79;
				AnadirLLData.heightHint = 23;
				AnadirLLData.horizontalSpan = 0;
				AnadirLLData.verticalSpan = 0;
				AnadirLLData.grabExcessVerticalSpace = true;
				AnadirLLData.grabExcessHorizontalSpace = true;
				AnadirLLData.horizontalAlignment = GridData.CENTER;
				AnadirLLData.verticalAlignment = GridData.BEGINNING;
				AnadirL.setLayoutData(AnadirLLData);
				AnadirL.setText("A�adir");
				AnadirL.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						anadirLWidgetSelected(evt);
						
					}
				});
			}

		}
		boolean cu=false;
		int i=0;
		String medico=Agenda.getSelection().getText();
		while(i<datos.getNumM() && !cu){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				cu=true;
				for(int j=0;j<llamada.size();j++){
					NombresL[j].dispose();
				}
				for(int j=0;j<extra.size();j++){
					NombresE[j].dispose();
					horasE[j].dispose();
				}
				llamada=datos.getMedicos().get(i).getLlamadas();
				if(llamada==null){
					llamada=new ArrayList<DatosLlamada>();
				}
				extra=datos.getMedicos().get(i).getExtras();
				if(extra==null)
					extra=new ArrayList<DatosLlamada>();
				listaLlamadasL();
				listaLlamadasE();
			}
			i++;	
		}
		}
		tablasDerecha.layout();
	}
	
	private void agendaHoyWidgetSelected(SelectionEvent evt){
		datos.setFecha(fant);
		//BORRAR
		tablaExtras.dispose();
		tablaLlamadas.dispose();
		Extras.dispose();
		NombreE.dispose();
		horaE.dispose();
		anadirE.dispose();
		nombreL.dispose();
		AnadirL.dispose();
		
		//usoAgente.guardarAgenda(datos);
		util f=new util();
		String f1=util.getStrDateSQL2();
		String f2=util.getStrDateSQL();
		fy=f2;
		usoAgente.mostrarAgendaSecretaria(f2,usuEste);
		fant=fy; 
		fd=f1;

		
		//redefinir tabla extras
		tablaExtras = new Composite(tablasDerecha, SWT.BORDER);
		GridLayout tablaExtrasLayout = new GridLayout();
		tablaExtrasLayout.numColumns = 2;
		GridData tablaExtrasLData = new GridData();
		tablaExtrasLData.verticalSpan = 0;
		tablaExtrasLData.horizontalSpan = 0;
		tablaExtrasLData.horizontalAlignment = GridData.FILL;
		tablaExtrasLData.verticalAlignment = GridData.FILL;
		tablaExtrasLData.grabExcessVerticalSpace = true;
		tablaExtrasLData.grabExcessHorizontalSpace = true;
		tablaExtras.setLayoutData(tablaExtrasLData);
		tablaExtras.setLayout(tablaExtrasLayout);
		
		shell.setText("Consulta de Hoy  "+f1);
		
		anadirE = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
		tablaLlamadas = new Composite(tablasDerecha, SWT.BORDER);
		AnadirL = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
		
		GridLayout tablaLlamadasLayout = new GridLayout();
		GridData tablaLlamadasLData = new GridData();
		tablaLlamadasLData.verticalSpan = 0;
		tablaLlamadasLData.horizontalSpan = 0;
		tablaLlamadasLData.horizontalAlignment = GridData.FILL;
		tablaLlamadasLData.grabExcessVerticalSpace = true;
		tablaLlamadasLData.grabExcessHorizontalSpace = true;
		tablaLlamadasLData.verticalAlignment = GridData.FILL;
		tablaLlamadas.setLayoutData(tablaLlamadasLData);
		tablaLlamadas.setLayout(tablaLlamadasLayout);
		{
			Extras = new CLabel(tablaExtras, SWT.NONE);
			Extras.setText("EXTRAS");
			Extras.setBackground(SWTResourceManager.getColor(220,189,224));
			Extras.setFont(SWTResourceManager.getFont("Palatino Linotype",12,1,false,false));
			Extras.setForeground(SWTResourceManager.getColor(255,255,255));
			Extras.setAlignment(SWT.CENTER);
			GridData ExtrasLData = new GridData();
			ExtrasLData.horizontalAlignment = GridData.FILL;
			ExtrasLData.verticalAlignment = GridData.BEGINNING;
			ExtrasLData.horizontalSpan = 2;
			ExtrasLData.widthHint = 199;
			ExtrasLData.heightHint = 28;
			ExtrasLData.grabExcessHorizontalSpace = true;
			Extras.setLayoutData(ExtrasLData);		
		}
		{
			NombreE = new CLabel(tablaExtras, SWT.NONE);
			NombreE.setText("NOMBRE");
			NombreE.setBackground(SWTResourceManager.getColor(220,189,224));
			NombreE.setForeground(SWTResourceManager.getColor(255,255,255));
			GridData NombreELData = new GridData();
			NombreELData.heightHint = 17;
			NombreELData.grabExcessHorizontalSpace = true;
			NombreELData.horizontalAlignment = GridData.FILL;
			NombreE.setLayoutData(NombreELData);
			NombreE.setAlignment(SWT.CENTER);
		}
		{
			horaE = new CLabel(tablaExtras, SWT.NONE);
			horaE.setText("HORA");
			horaE.setBounds(157, 34, 38, 17);
			horaE.setAlignment(SWT.CENTER);
			horaE.setBackground(SWTResourceManager.getColor(220, 189, 224));
			GridData horaELData = new GridData();
			horaELData.widthHint = 38;
			horaELData.heightHint = 17;
			horaE.setLayoutData(horaELData);
			horaE.setForeground(SWTResourceManager.getColor(255, 255, 255));
		}
		{
		
		GridData a�adirELData = new GridData();
		a�adirELData.widthHint = 82;
		a�adirELData.heightHint = 23;
		a�adirELData.horizontalAlignment = GridData.CENTER;
		a�adirELData.verticalAlignment = GridData.BEGINNING;
		anadirE.setLayoutData(a�adirELData);
		anadirE.setText("A�adir");
		anadirE.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				anadirEWidgetSelected(evt);
				
			}
		});
	}
	{
		
		{
			llamadas = new CLabel(tablaLlamadas, SWT.NONE);
			GridData llamadasLData = new GridData();
			llamadasLData.horizontalAlignment = GridData.FILL;
			llamadasLData.verticalAlignment = GridData.BEGINNING;
			llamadasLData.verticalSpan = 0;
			llamadasLData.horizontalSpan = 0;
			llamadasLData.grabExcessHorizontalSpace = true;
			llamadas.setLayoutData(llamadasLData);
			llamadas.setText("LLAMADAS");
			llamadas.setAlignment(SWT.CENTER);
			llamadas.setBackground(SWTResourceManager.getColor(220, 189, 224));
			llamadas.setFont(SWTResourceManager.getFont("Palatino Linotype", 12, 1, false, false));
			llamadas.setForeground(SWTResourceManager.getColor(255, 255, 255));
		}
		{
			nombreL = new CLabel(tablaLlamadas, SWT.NONE);
			GridData nombreLLData = new GridData();
			nombreLLData.verticalAlignment = GridData.BEGINNING;
			nombreLLData.horizontalAlignment = GridData.FILL;
			nombreL.setLayoutData(nombreLLData);
			nombreL.setText("NOMBRE");
			nombreL.setAlignment(SWT.CENTER);
			nombreL.setBackground(SWTResourceManager.getColor(220, 189, 244));
			nombreL.setForeground(SWTResourceManager.getColor(255, 255, 255));
		}		
		{
			
			GridData AnadirLLData = new GridData();
			AnadirLLData.widthHint = 79;
			AnadirLLData.heightHint = 23;
			AnadirLLData.horizontalSpan = 0;
			AnadirLLData.verticalSpan = 0;
			AnadirLLData.grabExcessVerticalSpace = true;
			AnadirLLData.grabExcessHorizontalSpace = true;
			AnadirLLData.horizontalAlignment = GridData.CENTER;
			AnadirLLData.verticalAlignment = GridData.BEGINNING;
			AnadirL.setLayoutData(AnadirLLData);
			AnadirL.setText("A�adir");
			AnadirL.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					anadirLWidgetSelected(evt);
					
				}
			});
		}

	}
	boolean cu=false;
	int i=0;
	String medico=Agenda.getSelection().getText();
	while(i<datos.getNumM() && !cu){
		if (datos.getMedicos().get(i).getNombre().equals(medico)){
			cu=true;
			for(int j=0;j<llamada.size();j++){
				NombresL[j].dispose();
			}
			for(int j=0;j<extra.size();j++){
				NombresE[j].dispose();
				horasE[j].dispose();
			}
			llamada=datos.getMedicos().get(i).getLlamadas();
			if(llamada==null){
				llamada=new ArrayList<DatosLlamada>();
			}
			extra=datos.getMedicos().get(i).getExtras();
			if(extra==null)
				extra=new ArrayList<DatosLlamada>();
			listaLlamadasL();
			listaLlamadasE();
		}
		i++;	
	}
	
	tablasDerecha.layout();
	}
	
	/**
	 * Codigo asociado al evento del menu contextual 'sala de espera'
	 * EN PRUEBAS
	 * @param evt
	 */
	private void EstadoWidgetSelected(SelectionEvent evt, int estado){
		int i=0;
		boolean Es=false;
		while (i<c & !Es){
			if(Nombres[i]==seleccion){
				Nombres[i].setBackground(SWTResourceManager.getColor(173, 216, 167));
				Telefonos[i].setBackground(SWTResourceManager.getColor(173, 216, 167));
				Es=true;
				i--;
			}
			i++;
		}
		Es=false;
		i=0;
		String medico=Agenda.getSelection().getText();
		ArrayList<DatosCitaSinValidar> ll=new ArrayList<DatosCitaSinValidar>();
		while(i<datos.getNumM() && !Es){
			if (datos.getMedicos().get(i).getNombre().equals(medico)){
				ll=datos.getMedicos().get(i).getDatos();
				Es=true;
				i--;
			}
			i++;
		}
		Es=false;
		int v=0;
		DatosCitaSinValidar cita=buscarSeleccionado(seleccion);
		while(v<ll.size() &&!Es){
			if(ll.get(v).tomaHora().equals(cita.tomaHora())){
				datos.getMedicos().get(i).getDatos().get(v).setEstado(1);
				DatosCitaSinValidar aux=datos.getMedicos().get(i).getDatos().get(v);
				aux.setFecha(fy);
				aux.setEstado(estado);
				aux.setMedico(datos.getMedicos().get(i).getUsuario());
				usoAgente.anadeEstado(aux);
				Es=true;
			}
			v++;
		}
		v--;
	}
	
	/**
	 * Funcion auxiliar en PRUEBAS
	 * @param hora
	 * @return
	 */
	public boolean estaLibre(final HorasCita hora){
		
		cumple=true;
		disp.syncExec(new Runnable() {
            public void run() {
       
		String h=hora.getHInicio().toString().substring(0,5);
		String haux=hora.getHFin().toString().substring(0,5);;
		
		while(!haux.equals(h)& cumple){
			int i=c-1;
			while(i>=0 & !horas[i].getText().equals(h)){
				if (horas[i].getText().equals(haux)& !Nombres[i].getText().equals(""))
					cumple=false;
				i--;
			}
			
			String min=haux.substring(3, 5);
			String h1=haux.substring(0,2);
			if (min.equals("00")){
				h1=String.valueOf((Integer.valueOf(h1)-1));
				min=String.valueOf(60);
			}
			//min=String.valueOf((Integer.valueOf(min)-intervalo));
			if (min.equals("0")){
				min=min+"0";
			}
			haux=h1+":"+min;
		}
		
            }
            
        });
		return cumple;
		
	}
	
	public boolean mostrarMensajeAvisoC(final String titulo,final String mensaje){
		
		cumple=true;
		disp.syncExec(new Runnable() {
            public void run() {
				MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK |SWT.CANCEL| SWT.ICON_WARNING);
				messageBox.setText (titulo);
				messageBox.setMessage (mensaje);
				if (messageBox.open() == SWT.OK){
					cumple= true;
				}else
					cumple= false;
            }
            
        });
		return cumple;
	
	}
	
	public boolean actualizaAgenda(){
		
		cumple=true;
		disp.syncExec(new Runnable() {
            public void run() {
            	usoAgente.mostrarAgendaSecretaria(fy,usuEste);
            }
            
        });
		return cumple;
	
	}

}