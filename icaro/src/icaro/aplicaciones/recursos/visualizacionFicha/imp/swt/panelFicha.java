package icaro.aplicaciones.recursos.visualizacionFicha.imp.swt;

import java.text.ParseException;

import com.cloudgarden.resource.SWTResourceManager;


import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.ClaseGeneradoraVisualizacionFicha;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.usuario.UsoAgenteFicha;
import icaro.util.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class panelFicha extends Thread {

	// Variables
	//Componentes de la ventana
	private Composite compoLinea2;
    private Button bCerrar;
    private Button bBorrar;
    private Button bModificar;
    private Button bCancelar;
    private Button bGuardar;
    private Composite compoMenu;
    private Composite compoOtros;
    private Text text1;
    private Text tNotas;
    private Composite compoPLinea1;
    private CLabel cDrogas;
    private CLabel cTabaco;
    private CLabel cTransfusiones;
    private CLabel cMedicacion;
    private CLabel cAlergias;
    private CLabel cIntervenciones;
    private CLabel cEnfermedades;
    private CLabel cPeso;
    private CLabel cTalla;
    private CLabel cGRh;
    private Composite compoAntPersonales;
    private Text tEnferHederitarias;
    private CLabel cEnferHederitarias;
    private Text tAntOncologicos;
    private CLabel cAntOncologicos;
    private Text tDiabetes;
    private CLabel cDiabetes;
    private Text tNombre;
    private Composite compoAntDepLinea1;
    private Composite compoAntDepLinea2;
    private CLabel cNotas;
    private Text tHirtusismo;
    private Text tMetodoDep;
    private Text tCuandoDepila;
    private Text tFoliculitis;
    private Text tTrastHormonales;
    private Text tTratElectrolisis;
    private Text tTratPrevioLaser;
    private CLabel cAntHirsutismo;
    private CLabel cMetodoDepilacion;
    private CLabel cCuantoDepila;
    private CLabel cFoliculitis;
    private CLabel cTrastHormonales;
    private CLabel cTratEletrolisis;
    private CLabel cTratPrevio;
    private Composite compoAntDepilacion;
    private Text tDrogas;
    private Text tTabaco;
    private Text tTransfusiones;
    private Text tMedicacion;
    private Text tAlergias;
    private Composite compoPLinea3;
    private Text tIntervenciones;
    private Text tEnfermedades;
    private Composite compoPLinea2;
    private Text tPeso;
    private Text tTalla;

    private Composite compoAFamiliares;
    private Text tOtros;
    private Text tAseguradora;
    private Text tProfesion;
    private Text tmail;
    private Text tTelefono2;
    private Text tTelefono1;
    private Text tLocalidad;
    private Text tProvincia;
    private Text tCP;
    private Text tDireccion;
    private CLabel cEdadd;
    private Text tFNacimiento;
    private Text tApellidos;
    private Text tNif;
    private Composite compoLinea4;
    private Composite compoLinea3;
    private Composite linea1;
    private CLabel cLabel1;
    private CLabel cAseguradora;
    private CLabel cProfesion;
    private CLabel cMail;
    private CLabel cTelefonos;
    private CLabel cLocalidad;
    private CLabel cProvincia;
    private CLabel cCP;
    private CLabel cDireccion;
    private CLabel cEdad;
    private CLabel cFNacimiento;
    private CLabel cApellidos;
    private CLabel cNombre;
    private CLabel cNif;
    private Composite compoFicha;
    private CTabItem cOtros;
    private String nombre;
    private CTabItem cAntDepilacion;
    private CTabItem cAntPersonales;
    private CTabItem cAntFamiliares;
    private CTabItem cTabItem1;
    private CTabFolder cTablaFicha;
    private Composite composite1;
    private CCombo cCombo1;
    //Variables globales
    private Boolean nuevo;

	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteFicha usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private panelFicha este;

	/**
	 * Constructor de la ventana
	 * @param visualizador
	 */
	public panelFicha(ClaseGeneradoraVisualizacionFicha visualizador){
		super("Ficha");
		este = this;
		
		usoAgente = new UsoAgenteFicha(visualizador);
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
	 * Muestra la ventana y añade a los campos que corresponda los datos pasados por parametro
	 * @param d		:: datos que se deben mostrar en la ventana (nombre,telefono, hora, fecha, crear)
	 */
	public void mostrar(final DatosCita d){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.asyncExec(new Runnable() {
            public void run() {
            	DatosCita datos=d;
            	nuevo=datos.getCrear();
            	tNombre.setText(datos.getNombre());
            	tTelefono1.setText(datos.getTelf());
            	if (datos.getUsuario()==0){
            	    cAntDepilacion.dispose();
            	    cAntPersonales.dispose();
            	    cAntFamiliares.dispose();
            	}
         	   shell.open();
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

	public UsoAgenteFicha getAgente(){
		return usoAgente;
	}

	/**
	 * Codigo de la ventana
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		try {
		shell = new Shell(disp);
		GridLayout shellLayout = new GridLayout();
			shellLayout.makeColumnsEqualWidth = true;
			shell.setLayout(shellLayout);
        {
        	//Register as a resource user - SWTResourceManager will
        	//handle the obtaining and disposing of resources
        	SWTResourceManager.registerResourceUser(shell);
        }
        
		shell.addShellListener(new ShellAdapter() {
		    public void shellClosed(ShellEvent event) {
		    	usoAgente.cerrarVentanaFicha();
		    }
		});
        nuevo=true;
        GridLayout layout = new GridLayout(2,false);

		shell.setText("Crear Ficha");
        shell.setSize(765, 532);
		shell.setLayout(layout);
		{
			GridData composite1LData = new GridData();
			composite1LData.verticalAlignment = GridData.BEGINNING;
			composite1LData.horizontalAlignment = GridData.BEGINNING;
			composite1LData.widthHint = 742;
			composite1LData.heightHint = 511;
			composite1LData.grabExcessVerticalSpace = true;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1 = new Composite(shell, SWT.NONE);
			GridLayout composite1Layout = new GridLayout();
			composite1Layout.makeColumnsEqualWidth = true;
			composite1.setLayout(composite1Layout);
			composite1.setLayoutData(composite1LData);
			{
				cTablaFicha = new CTabFolder(composite1, SWT.BORDER);
				{
					cTabItem1 = new CTabItem(cTablaFicha, SWT.NONE);
					cTabItem1.setText("Ficha");
					cTabItem1.setFont(SWTResourceManager.getFont("Book Antiqua", 12, 1, false, false));
					{
						compoFicha = new Composite(cTablaFicha, SWT.BORDER);
						GridLayout compoFichaLayout = new GridLayout();
						compoFicha.setLayout(compoFichaLayout);
						cTabItem1.setControl(compoFicha);
						{
							GridData composite2LData = new GridData();
							composite2LData.verticalAlignment = GridData.BEGINNING;
							composite2LData.horizontalAlignment = GridData.BEGINNING;
							composite2LData.widthHint = 716;
							composite2LData.heightHint = 55;
							composite2LData.grabExcessVerticalSpace = true;
							linea1 = new Composite(compoFicha, SWT.NONE);
							GridLayout composite2Layout = new GridLayout();
							composite2Layout.numColumns = 5;
							linea1.setLayout(composite2Layout);
							linea1.setLayoutData(composite2LData);
							{
								cNif = new CLabel(linea1, SWT.NONE);
								cNif.setText("NIF.");
							}
							{
								cNombre = new CLabel(linea1, SWT.NONE);
								cNombre.setText("Nombre");
							}
							{
								cApellidos = new CLabel(linea1, SWT.NONE);
								cApellidos.setText("Apellidos");
							}
							{
								cFNacimiento = new CLabel(linea1, SWT.NONE);
								cFNacimiento.setText("F. Nacimiento");
							}
							{
								cEdad = new CLabel(linea1, SWT.NONE);
								cEdad.setText("Edad");
							}
							{
								GridData tNifLData = new GridData();
								tNifLData.verticalAlignment = GridData.BEGINNING;
								tNifLData.horizontalAlignment = GridData.BEGINNING;
								tNifLData.widthHint = 64;
								tNifLData.heightHint = 17;
								tNif = new Text(linea1, SWT.BORDER);
								tNif.setLayoutData(tNifLData);
							}
							{
								GridData tNombreLData = new GridData();
								tNombreLData.verticalAlignment = GridData.BEGINNING;
								tNombreLData.horizontalAlignment = GridData.BEGINNING;
								tNombreLData.widthHint = 145;
								tNombreLData.heightHint = 17;
								tNombre = new Text(linea1, SWT.BORDER);
								tNombre.setLayoutData(tNombreLData);
							}
							{
								GridData tApellidosLData = new GridData();
								tApellidosLData.verticalAlignment = GridData.BEGINNING;
								tApellidosLData.horizontalAlignment = GridData.BEGINNING;
								tApellidosLData.widthHint = 308;
								tApellidosLData.heightHint = 17;
								tApellidos = new Text(linea1, SWT.BORDER);
								tApellidos.setLayoutData(tApellidosLData);
							}
							{
								GridData tFNacimientoLData = new GridData();
								tFNacimientoLData.verticalAlignment = GridData.BEGINNING;
								tFNacimientoLData.horizontalAlignment = GridData.BEGINNING;
								tFNacimientoLData.widthHint = 64;
								tFNacimientoLData.heightHint = 17;
								tFNacimiento = new Text(linea1, SWT.BORDER);
								tFNacimiento.setLayoutData(tFNacimientoLData);
							}
							{
								cEdadd = new CLabel(linea1, SWT.BORDER);
								GridData cEdaddLData = new GridData();
								cEdaddLData.verticalAlignment = GridData.BEGINNING;
								cEdaddLData.horizontalAlignment = GridData.BEGINNING;
								cEdaddLData.widthHint = 34;
								cEdaddLData.heightHint = 23;
								cEdadd.setLayoutData(cEdaddLData);
								cEdadd.setBackground(SWTResourceManager.getColor(255, 255, 255));
							}
						}
						{
							GridData composite2LData1 = new GridData();
							composite2LData1.verticalAlignment = GridData.BEGINNING;
							composite2LData1.horizontalAlignment = GridData.BEGINNING;
							composite2LData1.widthHint = 606;
							composite2LData1.heightHint = 55;
							compoLinea2 = new Composite(compoFicha, SWT.NONE);
							GridLayout composite2Layout1 = new GridLayout();
							composite2Layout1.numColumns = 3;
							compoLinea2.setLayout(composite2Layout1);
							compoLinea2.setLayoutData(composite2LData1);
							{
								cDireccion = new CLabel(compoLinea2, SWT.NONE);
								cDireccion.setText("Dirección");
							}
							{
								cCP = new CLabel(compoLinea2, SWT.NONE);
								cCP.setText("C.P.");
							}
							{
								cProvincia = new CLabel(compoLinea2, SWT.NONE);
								cProvincia.setText("Provincia");
							}
							{
								GridData tDireccionLData = new GridData();
								tDireccionLData.verticalAlignment = GridData.BEGINNING;
								tDireccionLData.horizontalAlignment = GridData.BEGINNING;
								tDireccionLData.widthHint = 182;
								tDireccionLData.heightHint = 17;
								tDireccion = new Text(compoLinea2, SWT.BORDER);
								tDireccion.setLayoutData(tDireccionLData);
							}
							{
								GridData tCPLData = new GridData();
								tCPLData.verticalAlignment = GridData.BEGINNING;
								tCPLData.horizontalAlignment = GridData.BEGINNING;
								tCPLData.widthHint = 182;
								tCPLData.heightHint = 17;
								tCP = new Text(compoLinea2, SWT.BORDER);
								tCP.setLayoutData(tCPLData);
							}
							{
								GridData tProvinciaLData = new GridData();
								tProvinciaLData.verticalAlignment = GridData.BEGINNING;
								tProvinciaLData.horizontalAlignment = GridData.BEGINNING;
								tProvinciaLData.widthHint = 182;
								tProvinciaLData.heightHint = 17;
								tProvincia = new Text(compoLinea2, SWT.BORDER);
								tProvincia.setLayoutData(tProvinciaLData);
							}
						}
						{
							GridData composite2LData2 = new GridData();
							composite2LData2.verticalAlignment = GridData.BEGINNING;
							composite2LData2.horizontalAlignment = GridData.BEGINNING;
							composite2LData2.widthHint = 632;
							composite2LData2.heightHint = 54;
							composite2LData2.grabExcessVerticalSpace = true;
							compoLinea3 = new Composite(compoFicha, SWT.NONE);
							GridLayout composite2Layout2 = new GridLayout();
							composite2Layout2.numColumns = 3;
							compoLinea3.setLayout(composite2Layout2);
							compoLinea3.setLayoutData(composite2LData2);
							{
								cLocalidad = new CLabel(compoLinea3, SWT.NONE);
								cLocalidad.setText("Localidad");
							}
							{
								cTelefonos = new CLabel(compoLinea3, SWT.NONE);
								GridData cTelefonosLData = new GridData();
								cTelefonosLData.verticalAlignment = GridData.BEGINNING;
								cTelefonosLData.horizontalAlignment = GridData.BEGINNING;
								cTelefonosLData.horizontalSpan = 2;
								cTelefonos.setLayoutData(cTelefonosLData);
								cTelefonos.setText("Teléfonos");
							}
							{
								GridData tLocalidadLData = new GridData();
								tLocalidadLData.verticalAlignment = GridData.BEGINNING;
								tLocalidadLData.horizontalAlignment = GridData.BEGINNING;
								tLocalidadLData.heightHint = 17;
								tLocalidadLData.widthHint = 179;
								tLocalidad = new Text(compoLinea3, SWT.BORDER);
								tLocalidad.setLayoutData(tLocalidadLData);
							}
							{
								GridData tTelefono1LData = new GridData();
								tTelefono1LData.verticalAlignment = GridData.BEGINNING;
								tTelefono1LData.horizontalAlignment = GridData.BEGINNING;
								tTelefono1LData.heightHint = 17;
								tTelefono1LData.widthHint = 96;
								tTelefono1 = new Text(compoLinea3, SWT.BORDER);
								tTelefono1.setLayoutData(tTelefono1LData);
							}
							{
								GridData tTelefono2LData = new GridData();
								tTelefono2LData.verticalAlignment = GridData.BEGINNING;
								tTelefono2LData.horizontalAlignment = GridData.BEGINNING;
								tTelefono2LData.heightHint = 17;
								tTelefono2LData.widthHint = 98;
								tTelefono2 = new Text(compoLinea3, SWT.BORDER);
								tTelefono2.setLayoutData(tTelefono2LData);
							}
						}
						{
							GridData composite2LData3 = new GridData();
							composite2LData3.verticalAlignment = GridData.BEGINNING;
							composite2LData3.horizontalAlignment = GridData.BEGINNING;
							composite2LData3.widthHint = 570;
							composite2LData3.heightHint = 54;
							composite2LData3.grabExcessVerticalSpace = true;
							compoLinea4 = new Composite(compoFicha, SWT.NONE);
							GridLayout composite2Layout3 = new GridLayout();
							composite2Layout3.numColumns = 3;
							compoLinea4.setLayout(composite2Layout3);
							compoLinea4.setLayoutData(composite2LData3);
							{
								cMail = new CLabel(compoLinea4, SWT.NONE);
								cMail.setText("Email");
							}
							{
								cProfesion = new CLabel(compoLinea4, SWT.NONE);
								cProfesion.setText("Profesión");
							}
							{
								cAseguradora = new CLabel(compoLinea4, SWT.NONE);
								cAseguradora.setText("Aseguradora");
							}
							{
								GridData tmailLData = new GridData();
								tmailLData.verticalAlignment = GridData.BEGINNING;
								tmailLData.horizontalAlignment = GridData.BEGINNING;
								tmailLData.widthHint = 201;
								tmailLData.heightHint = 17;
								tmail = new Text(compoLinea4, SWT.BORDER);
								tmail.setLayoutData(tmailLData);
							}
							{
								GridData tProfesionLData = new GridData();
								tProfesionLData.verticalAlignment = GridData.BEGINNING;
								tProfesionLData.horizontalAlignment = GridData.BEGINNING;
								tProfesionLData.widthHint = 131;
								tProfesionLData.heightHint = 17;
								tProfesion = new Text(compoLinea4, SWT.BORDER);
								tProfesion.setLayoutData(tProfesionLData);
							}
							{
								GridData tAseguradoraLData = new GridData();
								tAseguradoraLData.verticalAlignment = GridData.BEGINNING;
								tAseguradoraLData.horizontalAlignment = GridData.BEGINNING;
								tAseguradoraLData.widthHint = 110;
								tAseguradoraLData.heightHint = 17;
								tAseguradora = new Text(compoLinea4, SWT.BORDER);
								tAseguradora.setLayoutData(tAseguradoraLData);
							}
						}
						{
							cLabel1 = new CLabel(compoFicha, SWT.NONE);
							cLabel1.setText("Otros datos de interes");
						}
						{
							GridData tOtrosLData = new GridData();
							tOtrosLData.verticalAlignment = GridData.BEGINNING;
							tOtrosLData.horizontalAlignment = GridData.BEGINNING;
							tOtrosLData.widthHint = 702;
							tOtrosLData.heightHint = 90;
							tOtros = new Text(compoFicha, SWT.MULTI | SWT.WRAP | SWT.BORDER);
							tOtros.setLayoutData(tOtrosLData);
						}
					}
				}
				{
					cAntFamiliares = new CTabItem(cTablaFicha, SWT.NONE);
					cAntFamiliares.setText("Ant. Familiares");
					cAntFamiliares.setFont(SWTResourceManager.getFont("Book Antiqua", 12, 1, false, false));
					{
						compoAFamiliares = new Composite(cTablaFicha, SWT.BORDER);
						GridLayout compoAFamiliaresLayout = new GridLayout();
						compoAFamiliaresLayout.makeColumnsEqualWidth = true;
						compoAFamiliares.setLayout(compoAFamiliaresLayout);
						cAntFamiliares.setControl(compoAFamiliares);
						{
							cDiabetes = new CLabel(compoAFamiliares, SWT.NONE);
							cDiabetes.setText("Diabetes");
						}
						{
							GridData tDiabetesLData = new GridData();
							tDiabetesLData.verticalAlignment = GridData.BEGINNING;
							tDiabetesLData.horizontalAlignment = GridData.BEGINNING;
							tDiabetesLData.heightHint = 17;
							tDiabetesLData.widthHint = 128;
							tDiabetes = new Text(compoAFamiliares, SWT.BORDER);
							tDiabetes.setLayoutData(tDiabetesLData);
						}
						{
							cAntOncologicos = new CLabel(compoAFamiliares, SWT.NONE);
							cAntOncologicos.setText("Enfermedades Oncológicas");
						}
						{
							GridData tAntOncologicosLData = new GridData();
							tAntOncologicosLData.verticalAlignment = GridData.BEGINNING;
							tAntOncologicosLData.horizontalAlignment = GridData.BEGINNING;
							tAntOncologicosLData.widthHint = 702;
							tAntOncologicosLData.heightHint = 96;
							tAntOncologicos = new Text(compoAFamiliares, SWT.MULTI | SWT.WRAP | SWT.BORDER);
							tAntOncologicos.setLayoutData(tAntOncologicosLData);
						}
						{
							cEnferHederitarias = new CLabel(compoAFamiliares, SWT.NONE);
							cEnferHederitarias.setText("Enfermedades Hederitarias");
						}
						{
							GridData tEnferHederitariasLData = new GridData();
							tEnferHederitariasLData.verticalAlignment = GridData.BEGINNING;
							tEnferHederitariasLData.horizontalAlignment = GridData.BEGINNING;
							tEnferHederitariasLData.widthHint = 703;
							tEnferHederitariasLData.heightHint = 100;
							tEnferHederitarias = new Text(compoAFamiliares, SWT.MULTI | SWT.WRAP | SWT.BORDER);
							tEnferHederitarias.setLayoutData(tEnferHederitariasLData);
						}
					}
				}
				{
					cAntPersonales = new CTabItem(cTablaFicha, SWT.NONE);
					cAntPersonales.setText("Ant. Personales");
					cAntPersonales.setFont(SWTResourceManager.getFont("Book Antiqua", 12, 1, false, false));
					{
						compoAntPersonales = new Composite(cTablaFicha, SWT.BORDER);
						GridLayout compoAntPersonalesLayout = new GridLayout();
						compoAntPersonalesLayout.makeColumnsEqualWidth = true;
						compoAntPersonales.setLayout(compoAntPersonalesLayout);
						cAntPersonales.setControl(compoAntPersonales);
						{
							GridData composite2LData4 = new GridData();
							composite2LData4.verticalAlignment = GridData.BEGINNING;
							composite2LData4.horizontalAlignment = GridData.BEGINNING;
							composite2LData4.widthHint = 604;
							composite2LData4.heightHint = 29;
							compoPLinea1 = new Composite(compoAntPersonales, SWT.NONE);
							GridLayout composite2Layout4 = new GridLayout();
							composite2Layout4.numColumns = 6;
							compoPLinea1.setLayout(composite2Layout4);
							compoPLinea1.setLayoutData(composite2LData4);
							{
								cGRh = new CLabel(compoPLinea1, SWT.NONE);
								cGRh.setText("G.Rh");
							}
							{
								cCombo1 = new CCombo(compoPLinea1, SWT.BORDER);
							}
							{
								cTalla = new CLabel(compoPLinea1, SWT.NONE);
								cTalla.setText("Talla (m)");
							}
							{
								GridData tTallaLData = new GridData();
								tTallaLData.verticalAlignment = GridData.BEGINNING;
								tTallaLData.horizontalAlignment = GridData.BEGINNING;
								tTallaLData.heightHint = 17;
								tTalla = new Text(compoPLinea1, SWT.BORDER);
								tTalla.setLayoutData(tTallaLData);
							}
							{
								cPeso = new CLabel(compoPLinea1, SWT.NONE);
								cPeso.setText("Peso (kg)");
							}
							{
								GridData tPesoLData = new GridData();
								tPesoLData.verticalAlignment = GridData.BEGINNING;
								tPesoLData.horizontalAlignment = GridData.BEGINNING;
								tPesoLData.heightHint = 17;
								tPeso = new Text(compoPLinea1, SWT.BORDER);
								tPeso.setLayoutData(tPesoLData);
							}
						}
						{
							GridData composite2LData5 = new GridData();
							composite2LData5.verticalAlignment = GridData.BEGINNING;
							composite2LData5.horizontalAlignment = GridData.BEGINNING;
							composite2LData5.widthHint = 712;
							composite2LData5.heightHint = 187;
							compoPLinea2 = new Composite(compoAntPersonales, SWT.NONE);
							GridLayout composite2Layout5 = new GridLayout();
							composite2Layout5.makeColumnsEqualWidth = true;
							compoPLinea2.setLayout(composite2Layout5);
							compoPLinea2.setLayoutData(composite2LData5);
							{
								cEnfermedades = new CLabel(compoPLinea2, SWT.NONE);
								cEnfermedades.setText("Enfermedades");
							}
							{
								GridData tEnfermedadesLData = new GridData();
								tEnfermedadesLData.verticalAlignment = GridData.BEGINNING;
								tEnfermedadesLData.horizontalAlignment = GridData.BEGINNING;
								tEnfermedadesLData.widthHint = 688;
								tEnfermedadesLData.heightHint = 58;
								tEnfermedades = new Text(compoPLinea2, SWT.MULTI | SWT.WRAP | SWT.BORDER);
								tEnfermedades.setLayoutData(tEnfermedadesLData);
							}
							{
								cIntervenciones = new CLabel(compoPLinea2, SWT.NONE);
								cIntervenciones.setText("Intervenciones Quirúrgicas");
							}
							{
								GridData tIntervencionesLData = new GridData();
								tIntervencionesLData.verticalAlignment = GridData.BEGINNING;
								tIntervencionesLData.horizontalAlignment = GridData.BEGINNING;
								tIntervencionesLData.widthHint = 686;
								tIntervencionesLData.heightHint = 58;
								tIntervenciones = new Text(compoPLinea2, SWT.MULTI | SWT.WRAP | SWT.BORDER);
								tIntervenciones.setLayoutData(tIntervencionesLData);
							}
						}
						{
							GridData composite2LData6 = new GridData();
							composite2LData6.verticalAlignment = GridData.BEGINNING;
							composite2LData6.horizontalAlignment = GridData.BEGINNING;
							composite2LData6.widthHint = 697;
							composite2LData6.heightHint = 144;
							compoPLinea3 = new Composite(compoAntPersonales, SWT.NONE);
							GridLayout composite2Layout6 = new GridLayout();
							composite2Layout6.numColumns = 2;
							compoPLinea3.setLayout(composite2Layout6);
							compoPLinea3.setLayoutData(composite2LData6);
							{
								cAlergias = new CLabel(compoPLinea3, SWT.NONE);
								cAlergias.setText("Alergias");
							}
							{
								GridData tAlergiasLData = new GridData();
								tAlergiasLData.verticalAlignment = GridData.BEGINNING;
								tAlergiasLData.horizontalAlignment = GridData.BEGINNING;
								tAlergiasLData.heightHint = 17;
								tAlergiasLData.widthHint = 492;
								tAlergias = new Text(compoPLinea3, SWT.BORDER);
								tAlergias.setLayoutData(tAlergiasLData);
							}
							{
								cMedicacion = new CLabel(compoPLinea3, SWT.NONE);
								cMedicacion.setText("Medicación");
							}
							{
								GridData tMedicacionLData = new GridData();
								tMedicacionLData.verticalAlignment = GridData.BEGINNING;
								tMedicacionLData.horizontalAlignment = GridData.BEGINNING;
								tMedicacionLData.heightHint = 17;
								tMedicacionLData.widthHint = 492;
								tMedicacion = new Text(compoPLinea3, SWT.BORDER);
								tMedicacion.setLayoutData(tMedicacionLData);
							}
							{
								cTransfusiones = new CLabel(compoPLinea3, SWT.NONE);
								cTransfusiones.setText("Transfusiones");
							}
							{
								GridData tTransfusionesLData = new GridData();
								tTransfusionesLData.verticalAlignment = GridData.BEGINNING;
								tTransfusionesLData.horizontalAlignment = GridData.BEGINNING;
								tTransfusionesLData.widthHint = 363;
								tTransfusionesLData.heightHint = 17;
								tTransfusiones = new Text(compoPLinea3, SWT.BORDER);
								tTransfusiones.setLayoutData(tTransfusionesLData);
							}
							{
								cTabaco = new CLabel(compoPLinea3, SWT.NONE);
								cTabaco.setText("Tabaco");
							}
							{
								GridData tTabacoLData = new GridData();
								tTabacoLData.verticalAlignment = GridData.BEGINNING;
								tTabacoLData.horizontalAlignment = GridData.BEGINNING;
								tTabacoLData.widthHint = 85;
								tTabacoLData.heightHint = 17;
								tTabaco = new Text(compoPLinea3, SWT.BORDER);
								tTabaco.setLayoutData(tTabacoLData);
							}
							{
								cDrogas = new CLabel(compoPLinea3, SWT.NONE);
								cDrogas.setText("Drogas");
							}
							{
								GridData tDrogasLData = new GridData();
								tDrogasLData.verticalAlignment = GridData.BEGINNING;
								tDrogasLData.horizontalAlignment = GridData.BEGINNING;
								tDrogasLData.heightHint = 17;
								tDrogasLData.widthHint = 169;
								tDrogas = new Text(compoPLinea3, SWT.BORDER);
								tDrogas.setLayoutData(tDrogasLData);
							}
						}
					}
				}
				{
					cAntDepilacion = new CTabItem(cTablaFicha, SWT.NONE);
					cAntDepilacion.setText("Ant. Depilacion");
					cAntDepilacion.setFont(SWTResourceManager.getFont("Book Antiqua", 12, 1, false, false));
					{
						compoAntDepilacion = new Composite(cTablaFicha, SWT.NONE);
						GridLayout compoAntDepilacionLayout = new GridLayout();
						compoAntDepilacionLayout.numColumns = 2;
						compoAntDepilacion.setLayout(compoAntDepilacionLayout);
						cAntDepilacion.setControl(compoAntDepilacion);
						{
							GridData composite2LData8 = new GridData();
							composite2LData8.verticalAlignment = GridData.BEGINNING;
							composite2LData8.horizontalAlignment = GridData.BEGINNING;
							composite2LData8.widthHint = 720;
							composite2LData8.heightHint = 202;
							compoAntDepLinea1 = new Composite(compoAntDepilacion, SWT.NONE);
							GridLayout composite2Layout8 = new GridLayout();
							composite2Layout8.numColumns = 2;
							compoAntDepLinea1.setLayout(composite2Layout8);
							compoAntDepLinea1.setLayoutData(composite2LData8);
							{
								cTratPrevio = new CLabel(compoAntDepLinea1, SWT.NONE);
								cTratPrevio.setText("Tratamiento previo con láser");
							}
							{
								GridData tTratPrevioLaserLData = new GridData();
								tTratPrevioLaserLData.verticalAlignment = GridData.BEGINNING;
								tTratPrevioLaserLData.horizontalAlignment = GridData.BEGINNING;
								tTratPrevioLaserLData.heightHint = 17;
								tTratPrevioLaserLData.widthHint = 510;
								tTratPrevioLaser = new Text(compoAntDepLinea1, SWT.BORDER);
								tTratPrevioLaser.setLayoutData(tTratPrevioLaserLData);
							}
							{
								cTratEletrolisis = new CLabel(compoAntDepLinea1, SWT.NONE);
								cTratEletrolisis.setText("Tratamiento previo con electrólisis");
							}
							{
								GridData tTratElectrolisisLData = new GridData();
								tTratElectrolisisLData.verticalAlignment = GridData.BEGINNING;
								tTratElectrolisisLData.horizontalAlignment = GridData.BEGINNING;
								tTratElectrolisisLData.heightHint = 17;
								tTratElectrolisisLData.widthHint = 510;
								tTratElectrolisis = new Text(compoAntDepLinea1, SWT.BORDER);
								tTratElectrolisis.setLayoutData(tTratElectrolisisLData);
							}
							{
								cTrastHormonales = new CLabel(compoAntDepLinea1, SWT.NONE);
								cTrastHormonales.setText("Trastornos Hormonales");
							}
							{
								GridData tTrastHormonalesLData = new GridData();
								tTrastHormonalesLData.verticalAlignment = GridData.BEGINNING;
								tTrastHormonalesLData.horizontalAlignment = GridData.BEGINNING;
								tTrastHormonalesLData.heightHint = 17;
								tTrastHormonalesLData.widthHint = 510;
								tTrastHormonales = new Text(compoAntDepLinea1, SWT.BORDER);
								tTrastHormonales.setLayoutData(tTrastHormonalesLData);
							}
							{
								cFoliculitis = new CLabel(compoAntDepLinea1, SWT.NONE);
								cFoliculitis.setText("Foliculitis quistes");
							}
							{
								GridData tFoliculitisLData = new GridData();
								tFoliculitisLData.verticalAlignment = GridData.BEGINNING;
								tFoliculitisLData.horizontalAlignment = GridData.BEGINNING;
								tFoliculitisLData.heightHint = 17;
								tFoliculitisLData.widthHint = 510;
								tFoliculitis = new Text(compoAntDepLinea1, SWT.BORDER);
								tFoliculitis.setLayoutData(tFoliculitisLData);
							}
							{
								cMetodoDepilacion = new CLabel(compoAntDepLinea1, SWT.NONE);
								cMetodoDepilacion.setText("Método habitual de depilación");
							}
							{
								GridData tMetodoDepLData = new GridData();
								tMetodoDepLData.verticalAlignment = GridData.BEGINNING;
								tMetodoDepLData.horizontalAlignment = GridData.BEGINNING;
								tMetodoDepLData.heightHint = 17;
								tMetodoDepLData.widthHint = 510;
								tMetodoDep = new Text(compoAntDepLinea1, SWT.BORDER);
								tMetodoDep.setLayoutData(tMetodoDepLData);
							}
							{
								cCuantoDepila = new CLabel(compoAntDepLinea1, SWT.NONE);
								cCuantoDepila.setText("Cada cuanto tiempo se depila");
							}
							{
								GridData tCuandoDepilaLData = new GridData();
								tCuandoDepilaLData.verticalAlignment = GridData.BEGINNING;
								tCuandoDepilaLData.horizontalAlignment = GridData.BEGINNING;
								tCuandoDepilaLData.heightHint = 17;
								tCuandoDepilaLData.widthHint = 510;
								tCuandoDepila = new Text(compoAntDepLinea1, SWT.BORDER);
								tCuandoDepila.setLayoutData(tCuandoDepilaLData);
							}
							{
								cAntHirsutismo = new CLabel(compoAntDepLinea1, SWT.NONE);
								cAntHirsutismo.setText("Antecedentes Hirsutismo en la familia");
							}
							{
								GridData tHirtusismoLData = new GridData();
								tHirtusismoLData.verticalAlignment = GridData.BEGINNING;
								tHirtusismoLData.horizontalAlignment = GridData.BEGINNING;
								tHirtusismoLData.heightHint = 17;
								tHirtusismoLData.widthHint = 510;
								tHirtusismo = new Text(compoAntDepLinea1, SWT.BORDER);
								tHirtusismo.setLayoutData(tHirtusismoLData);
							}
						}
						{
							GridData composite2LData7 = new GridData();
							composite2LData7.verticalAlignment = GridData.BEGINNING;
							composite2LData7.horizontalAlignment = GridData.BEGINNING;
							composite2LData7.widthHint = 721;
							composite2LData7.heightHint = 154;
							composite2LData7.horizontalSpan = 2;
							compoAntDepLinea2 = new Composite(compoAntDepilacion, SWT.NONE);
							GridLayout composite2Layout7 = new GridLayout();
							composite2Layout7.makeColumnsEqualWidth = true;
							compoAntDepLinea2.setLayout(composite2Layout7);
							compoAntDepLinea2.setLayoutData(composite2LData7);
							{
								cNotas = new CLabel(compoAntDepLinea2, SWT.NONE);
								cNotas.setText("Notas");
							}
							{
								GridData tNotasLData = new GridData();
								tNotasLData.verticalAlignment = GridData.BEGINNING;
								tNotasLData.horizontalAlignment = GridData.BEGINNING;
								tNotasLData.widthHint = 698;
								tNotasLData.heightHint = 117;
								tNotas = new Text(compoAntDepLinea2, SWT.MULTI | SWT.WRAP | SWT.BORDER);
								tNotas.setLayoutData(tNotasLData);
							}
						}
					}
				}
				{
					cOtros = new CTabItem(cTablaFicha, SWT.NONE);
					cOtros.setText("Otros");
					cOtros.setFont(SWTResourceManager.getFont("Book Antiqua", 12, 1, false, false));
					{
						compoOtros = new Composite(cTablaFicha, SWT.NONE);
						cOtros.setControl(compoOtros);
						GridLayout composite2Layout9 = new GridLayout();
						composite2Layout9.makeColumnsEqualWidth = true;
						compoOtros.setLayout(composite2Layout9);
						{
							GridData text1LData = new GridData();
							text1LData.verticalAlignment = GridData.BEGINNING;
							text1LData.horizontalAlignment = GridData.BEGINNING;
							text1LData.widthHint = 696;
							text1LData.heightHint = 357;
							text1LData.grabExcessVerticalSpace = true;
							text1LData.grabExcessHorizontalSpace = true;
							text1 = new Text(compoOtros, SWT.MULTI | SWT.WRAP | SWT.BORDER);
							text1.setLayoutData(text1LData);
						}
					}
				}
				GridData cTablaFichaLData = new GridData();
				cTablaFichaLData.verticalAlignment = GridData.BEGINNING;
				cTablaFichaLData.horizontalAlignment = GridData.BEGINNING;
				cTablaFichaLData.grabExcessVerticalSpace = true;
				cTablaFicha.setLayoutData(cTablaFichaLData);
				cTablaFichaLData.widthHint = 726;
				cTablaFichaLData.heightHint = 376;
				cTablaFicha.setSelection(0);
				cTablaFicha.setBackground(SWTResourceManager.getColor(220, 189, 244));
				cTablaFicha.setForeground(SWTResourceManager.getColor(255, 255, 255));
			}
			Edicion(nuevo);
			{
				compoMenu = new Composite(composite1, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				composite2Layout.numColumns = 5;
				GridData compoMenuLData = new GridData();
				compoMenuLData.verticalAlignment = GridData.BEGINNING;
				compoMenuLData.horizontalAlignment = GridData.BEGINNING;
				compoMenuLData.widthHint = 736;
				compoMenuLData.heightHint = 67;
				compoMenu.setLayoutData(compoMenuLData);
				compoMenu.setLayout(composite2Layout);
				compoMenu.setBackground(SWTResourceManager.getColor(220, 189, 244));
				{
					bGuardar = new Button(compoMenu, SWT.PUSH | SWT.CENTER);
					GridData bGuardarLData = new GridData();
					bGuardarLData.verticalAlignment = GridData.BEGINNING;
					bGuardarLData.horizontalAlignment = GridData.BEGINNING;
					bGuardarLData.widthHint = 66;
					bGuardarLData.heightHint = 56;
					bGuardar.setLayoutData(bGuardarLData);
					bGuardar.setText("Guardar");
					bGuardar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							bGuardarWidgetSelected(evt);
						}
					});
				}
				{
					bCancelar = new Button(compoMenu, SWT.PUSH | SWT.CENTER);
					GridData bCancelarLData = new GridData();
					bCancelarLData.verticalAlignment = GridData.BEGINNING;
					bCancelarLData.horizontalAlignment = GridData.BEGINNING;
					bCancelarLData.widthHint = 66;
					bCancelarLData.heightHint = 56;
					bCancelar.setLayoutData(bCancelarLData);
					bCancelar.setText("Cancelar");
				}
				{
					bModificar = new Button(compoMenu, SWT.PUSH | SWT.CENTER);
					GridData bModificarLData = new GridData();
					bModificarLData.verticalAlignment = GridData.BEGINNING;
					bModificarLData.horizontalAlignment = GridData.BEGINNING;
					bModificarLData.heightHint = 56;
					bModificar.setLayoutData(bModificarLData);
					bModificarLData.widthHint = 66;
					bModificar.setText("Modificar");
					bModificar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							bModificarWidgetSelected(evt);
						}
					});
				}
				{
					bBorrar = new Button(compoMenu, SWT.PUSH | SWT.CENTER);
					GridData bBorrarLData = new GridData();
					bBorrarLData.verticalAlignment = GridData.BEGINNING;
					bBorrarLData.horizontalAlignment = GridData.BEGINNING;
					bBorrarLData.heightHint = 56;
					bBorrar.setLayoutData(bBorrarLData);
					bBorrarLData.widthHint = 66;
					bBorrar.setText("Borrar");
					bBorrar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							bBorrarWidgetSelected(evt);
						}
					});
				}
				{
					bCerrar = new Button(compoMenu, SWT.PUSH | SWT.CENTER);
					GridData bCerrarLData = new GridData();
					bCerrarLData.verticalAlignment = GridData.BEGINNING;
					bCerrarLData.horizontalAlignment = GridData.BEGINNING;
					bCerrarLData.heightHint = 56;
					bCerrar.setLayoutData(bCerrarLData);
					bCerrarLData.widthHint = 66;
					bCerrar.setText("Cerrar");
					bCerrar.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							bCerrarWidgetSelected(evt);
						}
					});
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
	
	// Aqui iran los metodos especificos de cada ventana
    public String getNombre() {
		return nombre;
    	
    }
    
	/**
	 * Funcion que se encarga de activar o desactivar todos los campos de panelficha segun el
	 * valor booleano que se le pasa por parametro. Uso esta funcion como auxiliar a la accion
	 * del evento de modificar y guardar
	 * @param n
	 */
	public void Edicion(boolean n){
		tNif.setEditable(n);
		tNombre.setEditable(n);
		tApellidos.setEditable(n);
		tFNacimiento.setEditable(n);
		tDireccion.setEditable(n);
		tCP.setEditable(n);
		tProvincia.setEditable(n);
		tLocalidad.setEditable(n);
		tTelefono1.setEditable(n);
		tTelefono2.setEditable(n);
		tmail.setEditable(n);
		tProfesion.setEditable(n);
		tAseguradora.setEditable(n);
		tOtros.setEditable(n);
		tDiabetes.setEditable(n);
		tAntOncologicos.setEditable(n);
		tEnferHederitarias.setEditable(n);
		tPeso.setEditable(n);
		tEnfermedades.setEditable(n);
		tIntervenciones.setEditable(n);
		tAlergias.setEditable(n);
		tMedicacion.setEditable(n);
		tTransfusiones.setEditable(n);
		tTabaco.setEditable(n);
		tDrogas.setEditable(n);
		text1.setEditable(n);
		cCombo1.setEditable(n);
		tTalla.setEditable(n);
	    tHirtusismo.setEditable(n);
	    tMetodoDep.setEditable(n);
	    tCuandoDepila.setEditable(n);
	    tFoliculitis.setEditable(n);
	    tTrastHormonales.setEditable(n);
	    tTratElectrolisis.setEditable(n);
	    tTratPrevioLaser.setEditable(n);
	    tNotas.setEditable(n);
	}
	/**
	 * Accion asociada al evento del boton 'modificar'
	 * activamos todos los componentes de la ventana
	 * @param evt
	 */
	private void bModificarWidgetSelected(SelectionEvent evt) {
		Edicion(true);
		shell.layout();
	}
	
	/**
	 * Accion asociada al evento del boton 'guardar'
	 * desactivamos todos los componentes de la ventana
	 * @param evt
	 */
	private void bGuardarWidgetSelected(SelectionEvent evt) {
		
		DatosFicha ficha;
		try {
			ficha = new DatosFicha(tNombre.getText(),tApellidos.getText(),tNif.getText(),util.StrToDate(tFNacimiento.getText()),
			Integer.valueOf("4"),tDireccion.getText(),tCP.getText(),tProvincia.getText(),tLocalidad.getText(),tTelefono1.getText(),
			tTelefono2.getText(),tmail.getText(),tProfesion.getText(),tAseguradora.getText(),tOtros.getText(),
			text1.getText());
			usoAgente.guardarFicha(ficha);
			Edicion(false);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		shell.layout();
	}
	
	/**
	 * Accion asociada al evento del boton 'borrar'
	 * se borra la ficha de la bbdd
	 * @param evt
	 */
	private void bBorrarWidgetSelected(SelectionEvent evt){
		usoAgente.mostrarMensajeAvisoConfirmacion("¿Esta seguro que desea borrar la ficha de este paciente?", "Borrar Ficha");
		//Pendiente de preguntar confirmacion
		shell.dispose();
	}
	/**
	 * Accion asociada al evento del boton 'cerrar'
	 * Cierra la ventana
	 * @param evt
	 */
	private void bCerrarWidgetSelected(SelectionEvent evt){
		
		shell.dispose();
		usoAgente.cerrarVentanaFicha();
	}
	

}



