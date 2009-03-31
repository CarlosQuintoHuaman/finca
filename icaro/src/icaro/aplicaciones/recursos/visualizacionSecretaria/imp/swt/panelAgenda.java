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
import org.eclipse.swt.widgets.Shell;
import java.util.GregorianCalendar;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.Agenda;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosAgenda;
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
	
	// Variables de inicializacion de SWT
	private Button Manana;
	private Composite seleccionado;
	private CLabel cNomSel;
	private CLabel cSeleccionado;
	private CLabel nombreL;
	private Button Tarde;
	private Button agenda;
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
	private Label LMenu1;
	private Composite Menu1;
	private Composite principal;
	private Composite huecoAgenda;
	private boolean man=true;
	private int intervalo=15;
	
	private Time iniMan = new java.sql.Time(0000000);
	private Time iniTar=new java.sql.Time(0000000);
	private Time finMan=new java.sql.Time(0000000);
	private Time finTar=new java.sql.Time(0000000);
	private Button[] horas;
	private GridData[] aux1;
	private GridData[] aux2;
	private GridData[] aux3;
	private CLabel[] Nombres;
	private CLabel[] Telefonos;
	private boolean init=true;
	private int k=0;
	private int c=0;
	private DatosAgenda copiado, pegado;
	private ArrayList <DatosLlamada>extra=new ArrayList();
	private ArrayList <DatosLlamada>llamada=new ArrayList();
	protected Date fecha;
	protected Date fechaAnt;
	private int min;
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
	
	private Display disp;
	private Shell shell;
	private panelAgenda este;
	private ClaseGeneradoraVisualizacionSecretaria vis;
	//Datos persistencia
	private ArrayList<DatosCitaSinValidar> l;
	private String fechaAgenda;
	
	private Menu opciones;
	private boolean cumple;


	private DatosSecretaria datos;
	private ArrayList<DatosMedico> medatos;
	private int numM;
	private CTabItem[] TabNomMed;
	private Composite[] agendaDinamica;
	private ArrayList<Agenda> Ag;
	/**
	 * 
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
	
	
	public void meteDatos(final ArrayList<DatosCitaSinValidar> l1, String fecha, final ArrayList<String> lm1 ,final int numM){
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	
            	datos.setNumM(numM);
            	for(int i=0;i<lm1.size();i++){
            		DatosMedico med=new DatosMedico(lm1.get(i),intervalo);
            		datos.getMedicos().add(med);
            		
            	}
            	for(int i=0;i<l1.size();i++){
            		DatosCitaSinValidar dat=new DatosCitaSinValidar(l1.get(i).tomaNombre(), l1.get(i).tomaApell1(), 
            								l1.get(i).tomaTelf(), l1.get(i).tomaHora(), l1.get(i).tomaPeriodo());
            		l.add(dat);
            	}
            	
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

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		
		// Ahora va el codigo de la ventana.
		// ¡Ojo! Las variables de SWT deberian ser globales
		try {
			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(shell);
			}
			//VARIABLES

			medatos=new ArrayList<DatosMedico>();
			numM=0;
			datos=new DatosSecretaria(medatos, numM);
//			Medicos =new ArrayList<String>();
			l=new ArrayList<DatosCitaSinValidar> ();
			Ag= new ArrayList<Agenda>();
			
			shell.setLayout(new FillLayout());
			shell.setText("Consulta de Hoy");
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
						CrearFicha.setText("Crear Ficha");
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
								DarCitaWidgetSelected(e);
								
							}                               
						});
					}
					{
						Manana = new Button(Menu1, SWT.PUSH | SWT.CENTER);
						GridData MananaLData = new GridData();
						MananaLData.widthHint = 85;
						MananaLData.heightHint = 47;
						Manana.setLayoutData(MananaLData);
						Manana.setText("MAÑANA");
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
						button1LData.widthHint = 68;
						button1LData.heightHint = 57;
						button1LData.horizontalAlignment = GridData.CENTER;
						agenda.setLayoutData(button1LData);
						agenda.setText("AGENDA");
						agenda.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								agendaWidgetSelected(evt);
								
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
						
						Agenda = new CTabFolder(huecoAgenda, SWT.V_SCROLL);
					
						GridData AgendaLData = new GridData();
						AgendaLData.verticalAlignment = GridData.FILL;
						AgendaLData.horizontalAlignment = GridData.FILL;
						AgendaLData.grabExcessVerticalSpace = true;
						AgendaLData.grabExcessHorizontalSpace = true;
						Agenda.setLayoutData(AgendaLData);
						Agenda.setSelection(0);
						{
							GridData seleccionadoLData = new GridData();
							seleccionadoLData.verticalAlignment = GridData.BEGINNING;
							seleccionadoLData.horizontalAlignment = GridData.FILL;
							seleccionadoLData.heightHint = 29;
							seleccionadoLData.grabExcessVerticalSpace = true;
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
						DatosLlamada e = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
						extra.add(e);
						listaLlamadasE();
					}
					{
						anadirE = new Button(tablasDerecha, SWT.PUSH | SWT.CENTER);
						GridData añadirELData = new GridData();
						añadirELData.widthHint = 82;
						añadirELData.heightHint = 23;
						añadirELData.horizontalAlignment = GridData.CENTER;
						añadirELData.verticalAlignment = GridData.BEGINNING;
						anadirE.setLayoutData(añadirELData);
						anadirE.setText("Añadir");
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
						DatosLlamada l = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
						llamada.add(l);
						listaLlamadasL();
						

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
						AnadirL.setText("Añadir");
						AnadirL.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								anadirLWidgetSelected(evt);
								
							}
						});
					}
				}
			}
			}

			shell.layout();
		
			while (!shell.isDisposed()) {
				if (!disp.readAndDispatch())
					disp.sleep();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
		
		//RELLENADO DE LA AGENDA DE JULIO
		
		iniMan.setMinutes(0);
		iniMan.setHours(9);
		
		finMan.setHours(14);
		finMan.setMinutes(0);
		
		finTar.setHours(21);
		finTar.setMinutes(30);
		
		iniTar.setMinutes(30);
		iniTar.setHours(16);
		
			
/*		CTabItem aux=Agenda.getSelection();
		aux.getText();
		if (m>-1)
			RellenaTabla(iniMan, iniTar, finMan, finTar, man, init,m);*/
	}
	

	public void generaTabla(){
		TabNomMed= new CTabItem[datos.getNumM()];
		agendaDinamica= new Composite[datos.getNumM()];
		{
			Agenda.dispose();
			Agenda = new CTabFolder(huecoAgenda, SWT.V_SCROLL);
										
				for(int i=0;i<datos.getNumM();i++){
					TabNomMed[i] = new CTabItem(Agenda, SWT.NONE);
					TabNomMed[i].setText(datos.getMedicos().get(i).getNombre());
					agendaDinamica[i] = new Composite(Agenda, SWT.V_SCROLL | SWT.BORDER);
					GridLayout AgendaDinamicaLayout = new GridLayout();
					AgendaDinamicaLayout.numColumns = 3;
					agendaDinamica[i].setLayout(AgendaDinamicaLayout);
					agendaMedico(i);
				}

			GridData AgendaLData = new GridData();
			AgendaLData.verticalAlignment = GridData.FILL;
			AgendaLData.horizontalAlignment = GridData.FILL;
			AgendaLData.grabExcessVerticalSpace = true;
			AgendaLData.grabExcessHorizontalSpace = true;
			Agenda.setLayoutData(AgendaLData);
			Agenda.setSelection(0);
			
			int m=Agenda.getSelectionIndex();
			if (m>-1)
				RellenaTabla(iniMan, iniTar, finMan, finTar, man, init,m);
			huecoAgenda.layout();
			Agenda.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					int m=Agenda.getSelectionIndex();
					if (m>-1)
						RellenaTabla(iniMan, iniTar, finMan, finTar, man, init,m);
					huecoAgenda.layout();
					disp.update();
				}
			});
	}
	}

	// Aqui iran los metodos especificos de cada ventana
	private void RellenaTabla(Time iniMan, Time iniTar, Time finMan, Time finTar, boolean man, boolean init, int k){
		if (man)
			tablaAux(iniMan, finMan, k);
		else
			tablaAux(iniTar,finTar, k);
	}
	
	private void tablaAux(Time inicio,Time fin, int k){
		int minIni=inicio.getHours()*60+inicio.getMinutes();
		int minFin=fin.getHours()*60+fin.getMinutes();
		min=(minFin-minIni)/intervalo;
		if(!init){
			for (int i=0;i<min+1;i++){
				horas[i].dispose();
				
				Nombres[i].dispose();
				Telefonos[i].dispose();
			}
		}
		int a=min/4+1;
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
			while(j<60){
				if (i==0&&j==0){
					j=inicio.getMinutes();
				}
				
				if (i==a-1&& j>fin.getMinutes())
					break;
				horas[c] = new Button(agendaDinamica[k], SWT.PUSH | SWT.CENTER);
				aux1[c] = new GridData();
				aux1[c].horizontalAlignment = GridData.FILL;
				horas[c].setLayoutData(aux1[c]);
				horas[c].setAlignment(SWT.FILL);
				String aux=String.valueOf(i+inicio.getHours());
				if (Integer.valueOf(aux)<10)
					aux="0"+aux;
				if(j==0)
					horas[c].setText(aux+":00");
				else
					horas[c].setText(aux+":"+String.valueOf(j));
				
				j=j+intervalo;
				Nombres[c] = new CLabel(agendaDinamica[k], SWT.NONE);
				Telefonos[c] = new CLabel(agendaDinamica[k], SWT.NONE);
				
				opciones = new Menu(shell,SWT.POP_UP);
				Nombres[c].setMenu(opciones);

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
				/*MenuItem Estado = new MenuItem(opciones,SWT.CASCADE);
				Estado.setText("Estado");
				Menu estadoMenu = new Menu(opciones);
				Estado.setMenu(estadoMenu);*/
				//Nombres[0].setText("Pepita");
				
				
				MenuItem espera = new MenuItem(opciones,SWT.PUSH);
				espera.setText("En sala espera"); 
				espera.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						EsperaWidgetSelected(evt);
					}
				});
				MenuItem Siguiente = new MenuItem(opciones,SWT.PUSH);
				Siguiente.setText("El siguiente");
				Siguiente.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						SiguienteWidgetSelected(evt);
						
						
					}
				});
				MenuItem cobrado = new MenuItem(opciones,SWT.PUSH);
				cobrado.setText("Cobrado");
				cobrado.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						CobradoWidgetSelected(evt);
						
						
					}
				});
				// RELLENAR LA AGENDA
/*				if (m<l.size()){
					Nombres[c].setText(l.get(m).tomaNombre());
					Telefonos[c].setText(l.get(m).tomaTelf());
					
				}else{
				
				Nombres[c].setText("");
				Telefonos[c].setText("");
				}*/
				
				Nombres[c].setBackground(SWTResourceManager.getColor(255, 255, 255));
				Nombres[c].setAlignment(SWT.CENTER);
				aux2[c] = new GridData();
				aux2[c].horizontalAlignment = GridData.FILL;
				aux2[c].widthHint = 484;

				aux2[c].grabExcessHorizontalSpace = true;
				Nombres[c].setLayoutData(aux2[c]);
				Telefonos[c].setBackground(SWTResourceManager.getColor(255, 255, 255));
				Telefonos[c].setAlignment(SWT.CENTER);
				aux3[c] = new GridData();
				aux3[c].horizontalAlignment = GridData.FILL;
				aux3[c].grabExcessHorizontalSpace = true;
				Telefonos[c].setLayoutData(aux3[c]);
				Nombres[c].addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent evt) {
						
						nombreMouseDown(evt);
						
						
					}
				});
				c++;
				m++;
			}
			
		}
		this.init=false;
		agendaDinamica[k].layout();
	}

	
	private void MananaWidgetSelected(SelectionEvent evt) {
		man=true;
		int m=Agenda.getSelectionIndex();
		if (m>-1)
			RellenaTabla(iniMan, iniTar, finMan, finTar, man, init,m);
		disp.update();
	}
	
	private void TardeWidgetSelected(SelectionEvent evt) {
		man=false;
		int m=Agenda.getSelectionIndex();
		if (m>-1)
			RellenaTabla(iniMan, iniTar, finMan, finTar, man, init,m);
		disp.update();
		
		
	}
	private void CopiarWidgetSelected(SelectionEvent evt){
		
		if (cNomSel.getText()=="")
			usoAgente.mostrarMensajeError("Debe seleccionar un paciente", "Atención");
		else
			cNomSel.setBackground(SWTResourceManager.getColor(249, 120, 106));
			copiado=buscarSeleccionado2(cNomSel.getText());
			copiado.setCrear(true);
	}
	
	private void PegarWidgetSelected(SelectionEvent evt){
		
		if (!copiado.tomaCrear())
			usoAgente.mostrarMensajeError("No hay ningun paciente copiado", "Atención");
		else{
			cNomSel.setBackground(SWTResourceManager.getColor(255, 255, 255));
			pegado=buscarSeleccionado2(cNomSel.getText());
			String nombre=copiado.tomaNombre();
			copiado.setCrear(false);
			int i=0;
			boolean Es =false;
			while (i<c & !Es){
				if(Nombres[i].getText()==nombre){
					Telefonos[i].setText(pegado.tomatelf());
					Nombres[i].setText(pegado.tomaNombre());
					Es=true;
				}
				i++;
			}
		}
	}
	
	private void BorrarWidgetSelected(SelectionEvent e){
		if (cNomSel.getText()=="")
			usoAgente.mostrarMensajeError("Debe seleccionar un paciente", "Atención");
		else{
			//mostrar mensaje confirmacion de borrado
			
			int i=0;
			boolean Es =false;
			while (i<c & !Es){
				if(Nombres[i].getText()==cNomSel.getText()){
					Telefonos[i].setText("");
					Nombres[i].setText("");
					Es=true;
				}
				i++;
			}
		}
	}
	
	private void CrearFichaWidgetSelected(SelectionEvent evt){
		DatosCitaSinValidar d= buscarSeleccionado(cNomSel.getText());
		DatosAgenda a= new DatosAgenda(d.tomaNombre(), d.tomaTelf(), true);
		usoAgente.mostrarVentanaFicha(a);
	}
	
	private void DarCitaWidgetSelected(SelectionEvent evt){
		DatosCitaSinValidar d= buscarSeleccionado(cNomSel.getText());
		if (d.tomaNombre()=="")
			usoAgente.mostrarVentanaCita();
		else
			usoAgente.mostrarVentanaCita(d.tomaNombre(), d.tomaApell1(), d.tomaTelf(), d.tomaHora());
	
	}
	
	private void anadirEWidgetSelected(SelectionEvent evt){
		usoAgente.mostrarVentanaExtra();
	}
	
	private void anadirLWidgetSelected(SelectionEvent evt){
		usoAgente.mostrarVentanaLlamadas();
	}
	private void nombreMouseDown(MouseEvent evt) {
		String nombre=""; 
		String apellido="";
		String Telf="";
		String Hora="";
		boolean Es =false;
		CLabel lsel=(CLabel)evt.getSource();
		nombre=lsel.getText();

		int i;
		for (i=0;i<c;i++){
			Nombres[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			Telefonos[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		for(i=0;i<llamada.size();i++){
			NombresL[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		
		for(i=0;i<extra.size();i++){
			NombresE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
			horasE[i].setBackground(SWTResourceManager.getColor(255, 255, 255));
		}
		if (cNomSel.getText()!=nombre){
			
			lsel.setBackground(SWTResourceManager.getColor(123, 114, 211));
			DatosCitaSinValidar d= buscarSeleccionado(nombre);
			cNomSel.setText(nombre);
			usoAgente.mostrarVentanaCita(d.tomaNombre(), d.tomaApell1(), d.tomaTelf(), d.tomaHora());
		}
		else{
			cNomSel.setText("");
		}
		
	}
	
	public DatosCitaSinValidar buscarSeleccionado(String nombre){
		int i=0;
		int p=1;
		boolean Es =false;
		String apell1="";
		String Telf="";
		String Hora="";
		String [] aux;
		while (i<c & !Es){
			if(Nombres[i].getText()==nombre){
				Telf=Telefonos[i].getText();
				Hora=horas[i].getText();
				Telefonos[i].setBackground(SWTResourceManager.getColor(123, 114, 211));
				aux=nombre.split(" ");
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
		DatosCitaSinValidar d= new DatosCitaSinValidar(nombre, apell1, Telf, Hora);
		return d;
		
	}
	
	public DatosAgenda buscarSeleccionado2(String nombre){
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
		util f=new util(); 
		String fecha=f.getStrDate();
		DatosAgenda d= new DatosAgenda(nombre, Telf, Hora, fecha);
		return d;
		
	}
	
	public void setNombre(String nombre, String apellido) {
		Nombres[1].setText(nombre+" "+apellido);
		
	}

	public void comprobarCita(final DatosCitaSinValidar d) {
		
		try{
			disp.asyncExec(new Runnable() {
	            public void run() {
	         DatosCitaSinValidar datos=d;	   
	         int i=0;
	         while (i<c && !datos.tomaHora().equals(horas[i].getText())){
	        	 i++;
	         }
	         int j=0;
	         while (j<datos.tomaPeriodo() && Nombres[i].getText()=="" && Telefonos[i].getText()==""){
	        	 j++;
	         }
	         if(j==datos.tomaPeriodo()-1){
	         //Si acierto relleno la agenda con los datos y llamo a persistencia
	        	 for(int k=i;k<i+datos.tomaPeriodo();k++){
	        		 Nombres[i].setText(datos.tomaNombre());
	        		 Telefonos[i].setText(datos.tomaTelf());
	        	 }
	         }
	         else{
			//Si fallo muestro la cita otra vez y mensaje error
/*	        	 f=new panelCita(vis);
	        	 f.meteDatos(datos);
	        	 f.mostrar();*/
	         }
	         
			       }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
		
	}
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
	            	listaLlamadasL();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
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
	            			}
	            		}
	            	}
	            	
	            	//Y ESTO QUE HAGO?????
            		if(!esta){
            			llamada.add(dP);
            		}
	            	listaLlamadasL();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
	
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
	            	listaLlamadasE();
	            }
	         });
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
	            
	}
		
		public void modificaExtra(final DatosLlamada dA, final DatosLlamada dP){
			
			try{
				disp.asyncExec(new Runnable() {
		            public void run() {
		            	boolean esta=false;
		            	if (!extra.isEmpty()){
		            		for (int i=0;i<extra.size();i++){	            		
		            			NombresE[i].dispose();
		            			horasE[i].dispose();
		            			if (extra.get(i).getHora().equals(dA.getHora()) && extra.get(i).getNombre().equals(dA.getNombre())){
		            				esta=true;
		            				extra.get(i).setNombre(dP.getNombre());
		            				extra.get(i).setMensaje(dP.getMensaje());
		            				extra.get(i).setTelf(dP.getTelf());
		            				extra.get(i).setPaciente(dP.getPaciente());
		            			}
		            		}
		            	}
		            	
		            	//Y ESTO QUE HAGO?????
	            		if(!esta){
	            			extra.add(dP);
	            		}
		            	listaLlamadasE();
		            }
		         });
			}
			catch(Exception e){
				e.printStackTrace();
			
			}
		            
		}
	
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
	public void listaLlamadasL(){

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
	
		public void listaLlamadasE(){

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
			usoAgente.mostrarVentanaLlamada(d.getNombre(), d.getMensaje(), d.getTelf(), d.getPaciente(),d.getHora());
		}
		else{
			cNomSel.setText("");
		}
	}
	
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
			usoAgente.mostrarVentanaExtra(d.getNombre(), d.getMensaje(), d.getTelf(), d.getPaciente(),d.getHora());
		}
		else{
			cNomSel.setText("");
		}
	}
	private void agendaWidgetSelected(SelectionEvent evt){
		if (agenda.getText().equals("AGENDA")){
			agenda.setText("AGENDA DE HOY");
			agenda.setSize(100, 57);
		}else{
			agenda.setText("AGENDA");
			agenda.setSize(68, 57);
	}
			inicializa(agenda.getText());
	}
	
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
		llamada.clear();
		extra.clear();
				
		if(a.equals("AGENDA DE HOY")){
		shell.setText("Consulta de Hoy");
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
						// TODO ¿Y esto por qué no va?
								fecha = new Date(calendario.getYear()-1900,calendario.getMonth(),calendario.getDay());
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

        //Introducimos los valores y eventos de Fecha Inicio
        final DateTime calendario2 = new DateTime (tablaLlamadas, SWT.CALENDAR);
        calendario2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 2, 1));
        
        calendario2.addMouseListener(new MouseListener () {
                public void mouseDoubleClick(MouseEvent e) {
                        // TODO ¿Y esto por qué no va?
                		int m=0;
                        fecha = new Date(calendario2.getYear()-1900,calendario2.getMonth(), calendario2.getDay());
                        
                        m++;
                        //shell.dispose();
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
			
			shell.setText("Agenda");
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
			DatosLlamada e = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
			extra.add(e);
			listaLlamadasE();
			{
			
			GridData añadirELData = new GridData();
			añadirELData.widthHint = 82;
			añadirELData.heightHint = 23;
			añadirELData.horizontalAlignment = GridData.CENTER;
			añadirELData.verticalAlignment = GridData.BEGINNING;
			anadirE.setLayoutData(añadirELData);
			anadirE.setText("Añadir");
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
			DatosLlamada l = new DatosLlamada("pedro", "Quiere hablar contigo urgentemente", "91875432", true, "9:00");
			llamada.add(l);
			listaLlamadasL();
			
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
				AnadirL.setText("Añadir");
				AnadirL.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						anadirLWidgetSelected(evt);
						
					}
				});
			}

		}
		}
		tablasDerecha.layout();
	}
	
	private void EsperaWidgetSelected(SelectionEvent evt){
/*		MenuItem lsel=(MenuItem)evt.getSource();
		Menu m=lsel.getParent();
		//lsel.get
		CLabel llLabel=(CLabel)lsel.getData();
		CLabel llLabel1=(CLabel)m.getData();
		Menu m1=lsel.getParent();
		
		Decorations ll=(Decorations)m.getParent();
	
		String nombre=ll.getText();

		int i;
		for (i=0;i<c;i++){
			if (Nombres[i].getText().equals(nombre))
				Nombres[i].setBackground(SWTResourceManager.getColor(116, 193, 30));
		}*/
	}
	private void CobradoWidgetSelected(SelectionEvent evt){
		CLabel lsel=(CLabel)evt.getSource();
		String nombre=lsel.getText();

		int i;
		for (i=0;i<c;i++){
			if (Nombres[i].getText().equals(nombre))
				Nombres[i].setBackground(SWTResourceManager.getColor(218, 101, 101));
		}
		
	}
	private void SiguienteWidgetSelected(SelectionEvent evt){
		CLabel lsel=(CLabel)evt.getSource();
		String nombre=lsel.getText();

		int i;
		for (i=0;i<c;i++){
			if (Nombres[i].getText().equals(nombre))
				Nombres[i].setBackground(SWTResourceManager.getColor(48, 175, 175));
		}
		
	}
	
	public boolean estaLibre(final HorasCita hora){
		
		cumple=true;
		disp.syncExec(new Runnable() {
            public void run() {
       
		String h=hora.getHInicio();
		String haux=hora.getHFin();
		
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
			min=String.valueOf((Integer.valueOf(min)-intervalo));
			if (min.equals("0")){
				min=min+"0";
			}
			haux=h1+":"+min;
		}
		
            }
            
        });
		return cumple;
		
	}
	
	

}