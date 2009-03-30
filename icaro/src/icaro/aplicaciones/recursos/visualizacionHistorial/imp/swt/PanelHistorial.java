package icaro.aplicaciones.recursos.visualizacionHistorial.imp.swt;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.ClaseGeneradoraVisualizacionHistorial;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario.UsoAgenteHistorial;


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
public class PanelHistorial extends Thread {

	// Variables
	private Button bGuardar;
	private Button bCerrar;
	private Button bMedBorrar;
	private Button bMedNuevo;
	private List listaMedicamentos;
	private Button bPruebaBorrar;
	private Button bPruebaNueva;
	private StyledText tMedNotas;
	private Group gMedNotas;
	private Group gMedicamentos;
	private Composite cMedicamentos;
	private CLabel lMotivo;
	private CLabel lFecha;
	private List listadoPruebas;
	private Canvas canvasPruebas;
	private StyledText tPruebasDesc;
	private Group cPruebasVista;
	private Composite cPruebas;
	private Group gPruebasDesc;
	private Group gPruebasListado;
	private Group gExpFunciones;
	private StyledText tExpDesc;
	private CLabel lExpDesc;
	private Composite cExploracion;
	private CTabItem cTabMedicamentos;
	private CTabItem cTabPruebas;
	private CTabItem cTabExploracion;
	private StyledText tTratamiento;
	private CLabel lTratamiento;
	private StyledText tDiagnostico;
	private CLabel lDiagnostico;
	private StyledText tExploracion;
	private CLabel lExploracion;
	private Group gDiagnostico;
	private Group gPruebas;
	private StyledText tDescripcion;
	private CLabel lDescripcion;
	private CLabel lEspacio;
	private StyledText tMotivo;
	private Group gDatos;
	private Composite cVisita;
	private CTabItem cTabItem2;
	private CTabItem cTabResumen;
	private CTabFolder historial;
	private Composite contenido;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteHistorial usoAgente;
	InfoVisita v = null;
	ArrayList<InfoPrueba> pruebas = null;
	ArrayList<InfoMedicamento> medicamentos = null;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private PanelHistorial este;

	/**
	 * 
	 * @param visualizador
	 */
	public PanelHistorial(ClaseGeneradoraVisualizacionHistorial visualizador){
		super("Historial");
		este = this;
		
		usoAgente = new UsoAgenteHistorial(visualizador);
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

	public UsoAgenteHistorial getAgente(){
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
		GridLayout shellLayout = new GridLayout();
		shellLayout.makeColumnsEqualWidth = true;
		shell.setLayout(shellLayout);
		shell.setSize(800, 600);
		shell.setText("Historial de Paciente1");

		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}


		{
			contenido = new Composite(shell, SWT.NONE);
			GridLayout contenidoLayout = new GridLayout();
			contenidoLayout.numColumns = 2;
			contenido.setLayout(contenidoLayout);
			GridData contenidoLData = new GridData();
			contenidoLData.grabExcessHorizontalSpace = true;
			contenidoLData.grabExcessVerticalSpace = true;
			contenidoLData.horizontalAlignment = GridData.FILL;
			contenidoLData.verticalAlignment = GridData.FILL;
			
			contenido.setLayoutData(contenidoLData);
			{
				historial = new CTabFolder(contenido, SWT.BORDER);
				historial.setSimple(false);
				GridData HistorialLData = new GridData();
				HistorialLData.verticalAlignment = GridData.FILL;
				HistorialLData.horizontalAlignment = GridData.FILL;
				HistorialLData.grabExcessVerticalSpace = true;
				HistorialLData.grabExcessHorizontalSpace = true;
				HistorialLData.horizontalSpan = 2;
				historial.setLayoutData(HistorialLData);
				{
					cTabResumen = new CTabItem(historial, SWT.NONE);
					cTabResumen.setText("Resumen");
					{
						cVisita = new Composite(historial, SWT.NONE);
						GridLayout cVisitaLayout = new GridLayout();
						cVisitaLayout.numColumns = 4;
						cVisitaLayout.makeColumnsEqualWidth = true;
						cVisita.setLayout(cVisitaLayout);
						cTabResumen.setControl(cVisita);
						{
							lFecha = new CLabel(cVisita, SWT.NONE);
							lFecha.setText("FECHA:");
							lFecha.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							GridLayout lFechaLayout = new GridLayout();
							GridData lFechaLData = new GridData();
							lFechaLData.verticalAlignment = GridData.BEGINNING;
							lFechaLData.horizontalAlignment = GridData.BEGINNING;
							lFechaLData.horizontalSpan = 4;
							lFecha.setLayoutData(lFechaLData);
							lFecha.setLayout(lFechaLayout);
						}
						{
							gDatos = new Group(cVisita, SWT.NONE);
							GridLayout group1Layout = new GridLayout();
							group1Layout.makeColumnsEqualWidth = true;
							gDatos.setLayout(group1Layout);
							GridData group1LData = new GridData();
							group1LData.verticalAlignment = GridData.BEGINNING;
							group1LData.horizontalAlignment = GridData.BEGINNING;
							group1LData.widthHint = 358;
							group1LData.heightHint = 205;
							group1LData.horizontalSpan = 2;
							gDatos.setLayoutData(group1LData);
							gDatos.setText("Datos Iniciales");
							{
								lMotivo = new CLabel(gDatos, SWT.NONE);
								lMotivo.setText("MOTIVO DE LA CONSULTA");
								lMotivo.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							}
							{
								GridData tMotivoLData = new GridData();
								tMotivoLData.horizontalAlignment = GridData.FILL;
								tMotivoLData.grabExcessHorizontalSpace = true;
								tMotivoLData.heightHint = 35;
								tMotivo = new StyledText(gDatos, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
								tMotivo.setLayoutData(tMotivoLData);
							}
							{
								lEspacio = new CLabel(gDatos, SWT.NONE);
							}
							{
								lDescripcion = new CLabel(gDatos, SWT.NONE);
								lDescripcion.setText("DESCRIPCION DE LA ENFERMEDAD");
								lDescripcion.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							}
							{
								GridData tDescripcionLData = new GridData();
								tDescripcionLData.horizontalAlignment = GridData.FILL;
								tDescripcionLData.heightHint = 51;
								tDescripcion = new StyledText(gDatos, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
								tDescripcion.setLayoutData(tDescripcionLData);
							}
						}
						{
							gPruebas = new Group(cVisita, SWT.NONE);
							GridLayout gPruebasLayout = new GridLayout();
							gPruebasLayout.makeColumnsEqualWidth = true;
							gPruebas.setLayout(gPruebasLayout);
							GridData gPruebasLData = new GridData();
							gPruebasLData.verticalAlignment = GridData.BEGINNING;
							gPruebasLData.horizontalAlignment = GridData.BEGINNING;
							gPruebasLData.widthHint = 358;
							gPruebasLData.heightHint = 205;
							gPruebasLData.horizontalSpan = 2;
							gPruebas.setLayoutData(gPruebasLData);
							gPruebas.setText("Exploracion Fisica");
							{
								lExploracion = new CLabel(gPruebas, SWT.NONE);
								lExploracion.setText("EXPLORACION");
								lExploracion.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							}
							{
								GridData tExploracionLData = new GridData();
								tExploracionLData.horizontalAlignment = GridData.FILL;
								tExploracionLData.heightHint = 146;
								tExploracionLData.grabExcessHorizontalSpace = true;
								tExploracion = new StyledText(gPruebas, SWT.V_SCROLL | SWT.BORDER);
								tExploracion.setLayoutData(tExploracionLData);
							}
						}
						{
							gDiagnostico = new Group(cVisita, SWT.NONE);
							GridLayout gDiagnosticoLayout = new GridLayout();
							gDiagnosticoLayout.makeColumnsEqualWidth = true;
							gDiagnostico.setLayout(gDiagnosticoLayout);
							GridData gDiagnosticoLData = new GridData();
							gDiagnosticoLData.horizontalAlignment = GridData.FILL;
							gDiagnosticoLData.grabExcessVerticalSpace = true;
							gDiagnosticoLData.verticalAlignment = GridData.FILL;
							gDiagnosticoLData.horizontalSpan = 4;
							gDiagnostico.setLayoutData(gDiagnosticoLData);
							gDiagnostico.setText("Diagnostico y Tratamientos");
							{
								lDiagnostico = new CLabel(gDiagnostico, SWT.NONE);
								lDiagnostico.setText("DIAGNOSTICO");
								lDiagnostico.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							}
							{
								tDiagnostico = new StyledText(gDiagnostico, SWT.V_SCROLL | SWT.BORDER);
								GridData tDiagnosticoLData = new GridData();
								tDiagnosticoLData.horizontalAlignment = GridData.FILL;
								tDiagnosticoLData.grabExcessHorizontalSpace = true;
								tDiagnosticoLData.heightHint = 61;
								tDiagnostico.setLayoutData(tDiagnosticoLData);
							}
							{
								lTratamiento = new CLabel(gDiagnostico, SWT.NONE);
								lTratamiento.setText("TRATAMIENTO");
								lTratamiento.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
							}
							{
								tTratamiento = new StyledText(gDiagnostico, SWT.V_SCROLL | SWT.BORDER);
								GridData tTratamientoLData = new GridData();
								tTratamientoLData.grabExcessHorizontalSpace = true;
								tTratamientoLData.horizontalAlignment = GridData.FILL;
								tTratamientoLData.heightHint = 61;
								tTratamiento.setLayoutData(tTratamientoLData);
								tTratamiento.setSize(707, 61);
							}
						}
					}
				}
				{
					cTabExploracion = new CTabItem(historial, SWT.NONE);
					cTabExploracion.setText("Exploracion");
					{
						cExploracion = new Composite(historial, SWT.NONE);
						GridLayout cExploracionLayout = new GridLayout();
						cExploracionLayout.makeColumnsEqualWidth = true;
						cExploracion.setLayout(cExploracionLayout);
						cTabExploracion.setControl(cExploracion);
						{
							lExpDesc = new CLabel(cExploracion, SWT.NONE);
							lExpDesc.setText("DESCRIPCION DE LA EXPLORACION");
							lExpDesc.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
						}
						{
							GridData tExpDescLData = new GridData();
							tExpDescLData.horizontalAlignment = GridData.FILL;
							tExpDescLData.grabExcessHorizontalSpace = true;
							tExpDescLData.grabExcessVerticalSpace = true;
							tExpDescLData.verticalAlignment = GridData.FILL;
							tExpDesc = new StyledText(cExploracion, SWT.V_SCROLL | SWT.BORDER);
							tExpDesc.setLayoutData(tExpDescLData);
						}
						{
							gExpFunciones = new Group(cExploracion, SWT.NONE);
							GridLayout gExpFuncionesLayout = new GridLayout();
							gExpFuncionesLayout.makeColumnsEqualWidth = true;
							gExpFunciones.setLayout(gExpFuncionesLayout);
							GridData gExpFuncionesLData = new GridData();
							gExpFuncionesLData.grabExcessHorizontalSpace = true;
							gExpFuncionesLData.horizontalAlignment = GridData.FILL;
							gExpFuncionesLData.grabExcessVerticalSpace = true;
							gExpFuncionesLData.verticalAlignment = GridData.FILL;
							gExpFunciones.setLayoutData(gExpFuncionesLData);
							gExpFunciones.setText("Documentos");
						}
					}
				}
				{
					cTabPruebas = new CTabItem(historial, SWT.NONE);
					cTabPruebas.setText("Pruebas");
					{
						cPruebas = new Composite(historial, SWT.NONE);
						GridLayout cPruebasLayout = new GridLayout();
						cPruebasLayout.makeColumnsEqualWidth = true;
						cPruebasLayout.numColumns = 2;
						cPruebas.setLayout(cPruebasLayout);
						cTabPruebas.setControl(cPruebas);
						{
							gPruebasListado = new Group(cPruebas, SWT.NONE);
							GridLayout gPruebasListadoLayout = new GridLayout();
							gPruebasListadoLayout.makeColumnsEqualWidth = true;
							gPruebasListadoLayout.numColumns = 2;
							gPruebasListado.setLayout(gPruebasListadoLayout);
							GridData gPruebasListadoLData = new GridData();
							gPruebasListadoLData.horizontalAlignment = GridData.FILL;
							gPruebasListadoLData.grabExcessHorizontalSpace = true;
							gPruebasListadoLData.grabExcessVerticalSpace = true;
							gPruebasListadoLData.verticalAlignment = GridData.FILL;
							gPruebasListado.setLayoutData(gPruebasListadoLData);
							gPruebasListado.setText("Listado de pruebas");
							{
								GridData listadoPruebasLData = new GridData();
								listadoPruebasLData.horizontalSpan = 2;
								listadoPruebasLData.horizontalAlignment = GridData.FILL;
								listadoPruebasLData.grabExcessHorizontalSpace = true;
								listadoPruebasLData.verticalAlignment = GridData.FILL;
								listadoPruebasLData.grabExcessVerticalSpace = true;
								listadoPruebas = new List(gPruebasListado, SWT.V_SCROLL | SWT.BORDER);
								listadoPruebas.setLayoutData(listadoPruebasLData);
								listadoPruebas.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										tPruebasDesc.setText(pruebas.get(listadoPruebas.getSelectionIndex()).getDescripcion());
									}
								});
							}
							{
								bPruebaNueva = new Button(gPruebasListado, SWT.PUSH | SWT.CENTER);
								GridData bPruebaNuevaLData = new GridData();
								bPruebaNuevaLData.horizontalAlignment = GridData.END;
								bPruebaNueva.setLayoutData(bPruebaNuevaLData);
								bPruebaNueva.setText("Nueva Prueba");
								
								bPruebaNueva.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										bPruebaNuevaWidgetSelected(evt);
									}
								});
							}
							{
								bPruebaBorrar = new Button(gPruebasListado, SWT.PUSH | SWT.CENTER);
								bPruebaBorrar.setText("Borrar");
								bPruebaBorrar.setSize(86, 25);
								bPruebaBorrar.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										usoAgente.borrarPrueba(pruebas.get(listadoPruebas.getSelectionIndex()));
									}
								});
							}
						}
						{
							cPruebasVista = new Group(cPruebas, SWT.NONE);
							GridLayout cPruebasVistaLayout = new GridLayout();
							cPruebasVistaLayout.makeColumnsEqualWidth = true;
							cPruebasVista.setLayout(cPruebasVistaLayout);
							GridData cPruebasVistaLData = new GridData();
							cPruebasVistaLData.verticalSpan = 2;
							cPruebasVistaLData.grabExcessHorizontalSpace = true;
							cPruebasVistaLData.grabExcessVerticalSpace = true;
							cPruebasVistaLData.horizontalAlignment = GridData.FILL;
							cPruebasVistaLData.verticalAlignment = GridData.FILL;
							cPruebasVista.setLayoutData(cPruebasVistaLData);
							cPruebasVista.setText("Vista Previa");
							{
								GridData canvasPruebasLData = new GridData();
								canvasPruebasLData.grabExcessHorizontalSpace = true;
								canvasPruebasLData.horizontalAlignment = GridData.FILL;
								canvasPruebas = new Canvas(cPruebasVista, SWT.NONE);
								canvasPruebas.setLayoutData(canvasPruebasLData);
								canvasPruebas.setBackground(SWTResourceManager.getColor(128, 255, 128));
							}
						}
						{
							gPruebasDesc = new Group(cPruebas, SWT.NONE);
							GridLayout gPruebasDescLayout = new GridLayout();
							gPruebasDescLayout.makeColumnsEqualWidth = true;
							gPruebasDesc.setLayout(gPruebasDescLayout);
							GridData gPruebasDescLData = new GridData();
							gPruebasDescLData.grabExcessHorizontalSpace = true;
							gPruebasDescLData.horizontalAlignment = GridData.FILL;
							gPruebasDescLData.verticalAlignment = GridData.FILL;
							gPruebasDescLData.grabExcessVerticalSpace = true;
							gPruebasDesc.setLayoutData(gPruebasDescLData);
							gPruebasDesc.setText("Descripcion");
							{
								GridData tPruebasDescLData = new GridData();
								tPruebasDescLData.horizontalAlignment = GridData.FILL;
								tPruebasDescLData.grabExcessHorizontalSpace = true;
								tPruebasDescLData.verticalAlignment = GridData.FILL;
								tPruebasDescLData.grabExcessVerticalSpace = true;
								tPruebasDesc = new StyledText(gPruebasDesc, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
								tPruebasDesc.setLayoutData(tPruebasDescLData);
							}
						}
					}
				}
				{
					cTabMedicamentos = new CTabItem(historial, SWT.NONE);
					cTabMedicamentos.setText("Medicamentos");
					{
						cMedicamentos = new Composite(historial, SWT.NONE);
						GridLayout cMedicamentosLayout = new GridLayout();
						cMedicamentosLayout.makeColumnsEqualWidth = true;
						cMedicamentos.setLayout(cMedicamentosLayout);
						cTabMedicamentos.setControl(cMedicamentos);
						{
							gMedicamentos = new Group(cMedicamentos, SWT.NONE);
							GridLayout gMedicamentosLayout = new GridLayout();
							gMedicamentosLayout.makeColumnsEqualWidth = true;
							gMedicamentosLayout.numColumns = 2;
							gMedicamentos.setLayout(gMedicamentosLayout);
							GridData gMedicamentosLData = new GridData();
							gMedicamentosLData.grabExcessHorizontalSpace = true;
							gMedicamentosLData.horizontalAlignment = GridData.FILL;
							gMedicamentosLData.grabExcessVerticalSpace = true;
							gMedicamentosLData.verticalAlignment = GridData.FILL;
							gMedicamentos.setLayoutData(gMedicamentosLData);
							gMedicamentos.setText("Medicamentos Prescritos");
							{
								GridData listaMedicamentosLData = new GridData();
								listaMedicamentosLData.horizontalAlignment = GridData.FILL;
								listaMedicamentosLData.grabExcessHorizontalSpace = true;
								listaMedicamentosLData.grabExcessVerticalSpace = true;
								listaMedicamentosLData.verticalAlignment = GridData.FILL;
								listaMedicamentosLData.horizontalSpan = 2;
								listaMedicamentos = new List(gMedicamentos, SWT.V_SCROLL | SWT.BORDER);
								listaMedicamentos.setLayoutData(listaMedicamentosLData);
								listaMedicamentos.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										tMedNotas.setText(medicamentos.get(listaMedicamentos.getSelectionIndex()).getIndicaciones());
									}
								});
							}
							{
								bMedNuevo = new Button(gMedicamentos, SWT.PUSH | SWT.CENTER);
								GridData bMedNuevoLData = new GridData();
								bMedNuevoLData.horizontalAlignment = GridData.END;
								bMedNuevo.setLayoutData(bMedNuevoLData);
								bMedNuevo.setText("Añadir Medicamento");
								bMedNuevo.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										bMedNuevoWidgetSelected(evt);
									}
								});
							}
							{
								bMedBorrar = new Button(gMedicamentos, SWT.PUSH | SWT.CENTER);
								bMedBorrar.setText("Borrar Medicamento");
								bMedBorrar.setSize(124, 25);
								bMedBorrar.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent evt) {
										usoAgente.borrarMed(medicamentos.get(listaMedicamentos.getSelectionIndex()));
									}
								});
							}
						}
						{
							gMedNotas = new Group(cMedicamentos, SWT.NONE);
							GridLayout gMedNotasLayout = new GridLayout();
							gMedNotasLayout.makeColumnsEqualWidth = true;
							gMedNotas.setLayout(gMedNotasLayout);
							GridData gMedNotasLData = new GridData();
							gMedNotasLData.horizontalAlignment = GridData.FILL;
							gMedNotasLData.grabExcessVerticalSpace = true;
							gMedNotasLData.verticalAlignment = GridData.FILL;
							gMedNotas.setLayoutData(gMedNotasLData);
							gMedNotas.setText("Notas");
							{
								tMedNotas = new StyledText(gMedNotas, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
								GridData tMedNotasLData = new GridData();
								tMedNotasLData.horizontalAlignment = GridData.FILL;
								tMedNotasLData.grabExcessHorizontalSpace = true;
								tMedNotasLData.grabExcessVerticalSpace = true;
								tMedNotasLData.verticalAlignment = GridData.FILL;
								tMedNotas.setLayoutData(tMedNotasLData);
							}
						}
					}
				}
				{
					cTabItem2 = new CTabItem(historial, SWT.NONE);
					cTabItem2.setText("Documentos");
				}
				historial.setSelection(0);
			}
			{
				bGuardar = new Button(contenido, SWT.PUSH | SWT.CENTER);
				bGuardar.setText("Guardar Cambios");
				bGuardar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						bGuardarWidgetSelected(evt);
					}
				});
			}
			{
				bCerrar = new Button(contenido, SWT.PUSH | SWT.CENTER);
				bCerrar.setText("Cerrar Historial");
				bCerrar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						bCerrarWidgetSelected(evt);
					}
				});
			}
		}

		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent arg0) {
				usoAgente.cerrarVentanaHistorial();
			}
			
		});
		
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	
	/**
	 * Se comprueba que todos los campos esten rellandos con datos validos
	 */
	private void bGuardarWidgetSelected(SelectionEvent evt) {
		System.out.println("bGuardar.widgetSelected, event="+evt);

		boolean correcto = true;
		String mensaje = "Faltan los siguientes campos por rellenar:\n\n";
		
		if (tMotivo.getText() == "") {
			correcto = false;
			mensaje += "- Motivo de la consulta\n";
		}
		
		if (tDescripcion.getText() == "") {
			correcto = false;
			mensaje += "- Descripcion de la enfermedad\n";
		}
		
		if (tExploracion.getText() == "") {
			correcto = false;
			mensaje += "- Exploracion fisica\n";
		}
		
		if (tDiagnostico.getText() == "") {
			correcto = false;
			mensaje += "- Diagnostico\n";
		}
		
		if (tTratamiento.getText() == "") {
			correcto = false;
			mensaje += "- Tratamiento\n";
		}

		v.setMotivo(tMotivo.getText());
		v.setDescripcion(tDescripcion.getText());
		v.setExploracion(tExploracion.getText());
		v.setDiagnostico(v.getDiagnostico());
		
		if (correcto)
			//usoAgente.insertaDatos(tMotivo.getText(), tDescripcion.getText(), tExploracion.getText(), tDiagnostico.getText(), tTratamiento.getText());
			usoAgente.guardarVisita(v);
		else
			usoAgente.mostrarMensajeError(mensaje, "Faltan campos por rellenar");
		
		//usoAgente.validaHistorial();
		
	}
	
	private void bCerrarWidgetSelected(SelectionEvent evt) {
		System.out.println("bCerrar.widgetSelected, event="+evt);
		
		usoAgente.cerrarVentanaHistorial();
		//shell.close();
	}
	
	private void bPruebaNuevaWidgetSelected(SelectionEvent evt) {
		System.out.println("bCerrar.widgetSelected, event="+evt);
		
		usoAgente.mostrarVentanaPrueba(v.getUsuario());
		//shell.close();
	}
	
	public void mostrarDatos(ArrayList<InfoVisita> historial) {
		v = historial.get(0);
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	tMotivo.setText(v.getMotivo());
            	tDescripcion.setText(v.getDescripcion());
            	tExploracion.setText(v.getExploracion());
            	tDiagnostico.setText(v.getDiagnostico());
            	
            	tExpDesc.setText(v.getExploracion());
	       }
         });
	}
	
	public void mostrarDatosPrueba(final ArrayList<InfoPrueba> p) {
		pruebas = p;
		
		disp.asyncExec(new Runnable() {
			public void run() {
				listadoPruebas.removeAll();
				for (int i=0; i<p.size(); i++) {
					listadoPruebas.add(p.get(i).getNombre());
					tPruebasDesc.setText(p.get(i).getDescripcion());
				}
			}
		});
	}
	
	public void mostrarDatosMed(final ArrayList<InfoMedicamento> m) {
		medicamentos = m;

		disp.asyncExec(new Runnable() {
			public void run() {
				listaMedicamentos.removeAll();
				for (int i=0; i<m.size(); i++) {
					listaMedicamentos.add(m.get(i).getNombre());
					tMedNotas.setText(m.get(i).getIndicaciones());
				}
			}
		});
	}
	
	private void bMedNuevoWidgetSelected(SelectionEvent evt) {
		System.out.println("bMedNuevo.widgetSelected, event="+evt);
		//TODO add your code for bMedNuevo.widgetSelected
		usoAgente.mostrarVentanaBusquedaMed();
	}
}