package icaro.aplicaciones.recursos.visualizacionMedico.imp.swt;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

//import visualizacion.Gradiente;

//import visualizacion.Gradiente;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.recursos.visualizacionMedico.imp.ClaseGeneradoraVisualizacionMedico;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario.UsoAgenteMedico;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelVisita extends Thread {

	// Variables
	private Composite izquierda;
	private Composite derecha;
	private Label lMotivo;
	private Label label1;
	private Button bSiguiente;
	private Label lAntecedentes;
	private Label lEspacio3;
	private Text tDescripcion;
	private Label lEspacio2;
	private Label lDescripcion;
	private Label lEspacio;
	private Label lNombrePaciente;
	private Label lPaciente;
	private Text tMotivo;
	
	private Composite izquierda2;		
	private Label lExploracion;
	private Button bDocumento;
	private Button bAnterior;
	private Label lAnalitica;
	private Text tExploracion;
	
	private Composite izquierda3;
	private Label lDiagnostico;
	private Button bAceptar;
	private Button bTratamiento;
	private Label lTratamientos;

	private Composite paso1;
	private Composite paso2;
	private Composite paso3;	
	
	private Text tAntGeneticos;
	private Text tAntPersonales;
	private CTabItem cAntPersonales;
	private CTabItem cAntGeneticos;
	private Text tAntFamiliares;
	private CTabItem cTabItem1;
	private CTabFolder cTabAntecedentes;

	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Medico" por el nombre del agente.
	 */
	final UsoAgenteMedico usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	/**
	 * 
	 * @param visualizador
	 */
	public PanelVisita(ClaseGeneradoraVisualizacionMedico visualizador){
		super("Agenda");
		usoAgente = new UsoAgenteMedico(visualizador);
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
         	  paso2();
         	  paso3();
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

	public UsoAgenteMedico getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		paso1 = new Composite(shell, SWT.NONE);
		paso2 = new Composite(shell, SWT.NONE);		
		paso2.setVisible(false);		
		paso3 = new Composite(shell, SWT.NONE);
		paso3.setVisible(false);
		
		shell.setSize(700, 600);
		shell.setLayout(new FillLayout());
		shell.layout();
				
		// Ahora va el codigo de la ventana.
		// ¡Ojo! Las variables de SWT deberian ser globales
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			paso1.setLayout(thisLayout);
			paso1.setSize(700, 600);
			{
				GridData izquierdaLData = new GridData();
				izquierdaLData.verticalAlignment = GridData.FILL;
				izquierdaLData.grabExcessHorizontalSpace = true;
				izquierdaLData.grabExcessVerticalSpace = true;
				izquierdaLData.widthHint = 176;
				izquierda = new Composite(paso1, SWT.NONE);
				
				izquierda.addListener(SWT.Resize,new Listener() {
					public void handleEvent(Event event) {
						//Gradiente.applyGradientBG(izquierda);
					}					
				});
				
				GridLayout izquierdaLayout = new GridLayout();
				izquierda.setLayout(izquierdaLayout);
				izquierda.setLayoutData(izquierdaLData);
				//izquierda.setBackground(SWTResourceManager.getColor(112, 150, 252));
				izquierda.setSize(176, 550);
			}
			{
				derecha = new Composite(paso1, SWT.NONE);
				GridLayout derechaLayout = new GridLayout();
				GridData derechaLData = new GridData();
				derechaLData.grabExcessVerticalSpace = true;
				derechaLData.widthHint = 501;
				derechaLData.grabExcessHorizontalSpace = true;
				derechaLData.heightHint = 551;
				derecha.setLayoutData(derechaLData);
				derecha.setLayout(derechaLayout);
				{
					lPaciente = new Label(derecha, SWT.NONE);
					lPaciente.setText("Paciente:");
					lPaciente.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				}
				{
					lNombrePaciente = new Label(derecha, SWT.NONE);
					lNombrePaciente.setText("Pepito Perez");
				}
				{
					lEspacio = new Label(derecha, SWT.NONE);
				}
				{
					lMotivo = new Label(derecha, SWT.NONE);
					GridData lMotivoLData = new GridData();
					lMotivoLData.horizontalAlignment = GridData.FILL;
					lMotivo.setLayoutData(lMotivoLData);
					lMotivo.setText("MOTIVO DE LA CONSULTA:");
					lMotivo.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				}
				{
					tMotivo = new Text(derecha, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
					GridData tMotivoLData = new GridData();
					tMotivoLData.horizontalAlignment = GridData.FILL;
					tMotivoLData.grabExcessHorizontalSpace = true;
					tMotivoLData.heightHint = 100;
					tMotivo.setLayoutData(tMotivoLData);
					tMotivo.setText("Texto....");
				}
				{
					GridData label1LData = new GridData();
					label1LData.widthHint = 60;
					lEspacio2 = new Label(derecha, SWT.NONE);
					lEspacio2.setLayoutData(label1LData);
				}
				{
					lDescripcion = new Label(derecha, SWT.NONE);
					lDescripcion.setText("DESCRIPCION DE LA ENFERMEDAD ACTUAL:");
					lDescripcion.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				}
				{
					GridData tDescripcionLData = new GridData();
					tDescripcionLData.horizontalAlignment = GridData.FILL;
					tDescripcionLData.heightHint = 100;
					tDescripcion = new Text(derecha, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
					tDescripcion.setLayoutData(tDescripcionLData);
				}
				{
					lEspacio3 = new Label(derecha, SWT.NONE);
				}
				{
					lAntecedentes = new Label(derecha, SWT.NONE);
					GridData lAntecedentesLData = new GridData();
					lAntecedentesLData.horizontalAlignment = GridData.FILL;
					lAntecedentes.setLayoutData(lAntecedentesLData);
					lAntecedentes.setText("ANTECEDENTES:");
					lAntecedentes.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				}
				{
					cTabAntecedentes = new CTabFolder(derecha, SWT.BORDER);
					{
						cTabItem1 = new CTabItem(cTabAntecedentes, SWT.NONE);
						cTabItem1.setText("Familiares");
						{
							tAntFamiliares = new Text(cTabAntecedentes, SWT.MULTI | SWT.WRAP);
							cTabItem1.setControl(tAntFamiliares);
							tAntFamiliares.setText("Antecedentes Familiares");
						}
					}
					{
						cAntGeneticos = new CTabItem(cTabAntecedentes, SWT.NONE);
						cAntGeneticos.setText("Geneticos");
						{
							tAntGeneticos = new Text(cTabAntecedentes, SWT.MULTI | SWT.WRAP);
							cAntGeneticos.setControl(tAntGeneticos);
							tAntGeneticos.setText("Antecedentes Geneticos");
						}
					}
					{
						cAntPersonales = new CTabItem(cTabAntecedentes, SWT.NONE);
						cAntPersonales.setText("Personales");
						{
							tAntPersonales = new Text(cTabAntecedentes, SWT.MULTI | SWT.WRAP);
							cAntPersonales.setControl(tAntPersonales);
							tAntPersonales.setText("Antecedentes Personales");
						}
					}
					GridData cTabAntecedentesLData = new GridData();
					cTabAntecedentesLData.verticalAlignment = GridData.BEGINNING;
					cTabAntecedentesLData.horizontalAlignment = GridData.FILL;
					//cTabAntecedentesLData.widthHint = 209;
					cTabAntecedentesLData.heightHint = 82;
					cTabAntecedentes.setLayoutData(cTabAntecedentesLData);
					cTabAntecedentes.setSelection(0);
				}
				{
					GridData label1LData1 = new GridData();
					label1LData1.widthHint = 60;
					label1 = new Label(derecha, SWT.NONE);
					label1.setLayoutData(label1LData1);
				}
				{
					bSiguiente = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bAceptarLData = new GridData();
					bAceptarLData.horizontalAlignment = GridData.CENTER;
					bSiguiente.setLayoutData(bAceptarLData);
					bSiguiente.setText("Siguiente >>");
					bSiguiente.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							System.out.println("bSiguiente.widgetSelected, event="+evt);
							//paso1 = new Paso2(shell, SWT.NONE);
							paso1.setVisible(false);
							paso1.setBounds(228, 0, 228, 564);
							paso2.setBounds(0, 0, 228, 564);
							paso2.setSize(700,600);
							paso2.setVisible(true);
						}
					});
				}
			}
			paso1.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	private void paso2() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			paso2.setLayout(thisLayout);
			paso2.setSize(700, 600);
			{
				GridData izquierda2LData = new GridData();
				izquierda2LData.verticalAlignment = GridData.FILL;
				izquierda2LData.widthHint = 176;
				izquierda2LData.grabExcessHorizontalSpace = true;
				izquierda2LData.grabExcessVerticalSpace = true;
				izquierda2 = new Composite(paso2, SWT.NONE);
				GridLayout izquierda2Layout = new GridLayout();
				izquierda2Layout.makeColumnsEqualWidth = true;
				izquierda2.setLayout(izquierda2Layout);
				izquierda2.setLayoutData(izquierda2LData);
				//izquierda.setBackground(SWTResourceManager.getColor(112,150,252));
				izquierda2.addListener(SWT.Resize,new Listener() {
					public void handleEvent(Event event) {
						//Gradiente.applyGradientBG(izquierda2);
					}					
				});
			}
			{
				GridData derechaLData = new GridData();
				derechaLData.widthHint = 501;
				derechaLData.heightHint = 550;
				derechaLData.grabExcessVerticalSpace = true;
				derechaLData.grabExcessHorizontalSpace = true;
				derecha = new Composite(paso2, SWT.NONE);
				GridLayout derechaLayout = new GridLayout();
				derechaLayout.numColumns = 2;
				derechaLayout.makeColumnsEqualWidth = true;
				derecha.setLayout(derechaLayout);
				derecha.setLayoutData(derechaLData);
				{
					lPaciente = new Label(derecha, SWT.NONE);
					lPaciente.setText("Paciente:");
					GridData lPacienteLData = new GridData();
					lPacienteLData.horizontalSpan = 2;
					lPaciente.setLayoutData(lPacienteLData);
					lPaciente.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					lNombrePaciente = new Label(derecha, SWT.NONE);
					GridData lNombrePacienteLData = new GridData();
					lNombrePacienteLData.horizontalSpan = 2;
					lNombrePaciente.setLayoutData(lNombrePacienteLData);
					lNombrePaciente.setText("Pepito Perez");
				}
				{
					lEspacio = new Label(derecha, SWT.NONE);
				}
				{
					lExploracion = new Label(derecha, SWT.NONE);
					GridData lMotivoLData = new GridData();
					lMotivoLData.horizontalAlignment = GridData.FILL;
					lMotivoLData.horizontalSpan = 2;
					lExploracion.setLayoutData(lMotivoLData);
					lExploracion.setText("EXPLORACION FISICA:");
					lExploracion.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					tExploracion = new Text(derecha, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
					GridData tExploracionLData = new GridData();
					tExploracionLData.horizontalAlignment = GridData.FILL;
					tExploracionLData.heightHint = 100;
					tExploracionLData.grabExcessHorizontalSpace = true;
					tExploracionLData.horizontalSpan = 2;
					tExploracion.setLayoutData(tExploracionLData);
				}
				{
					GridData label1LData = new GridData();
					label1LData.widthHint = 60;
					lEspacio2 = new Label(derecha, SWT.NONE);
					lEspacio2.setLayoutData(label1LData);
				}
				{
					lAnalitica = new Label(derecha, SWT.NONE);
					lAnalitica.setText("ANALITICA:");
					GridData lDescripcionLData = new GridData();
					lDescripcionLData.horizontalSpan = 2;
					lAnalitica.setLayoutData(lDescripcionLData);
					lAnalitica.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					GridData lEspacio3LData = new GridData();
					lEspacio3LData.horizontalSpan = 2;
					lEspacio3 = new Label(derecha, SWT.NONE);
					lEspacio3.setLayoutData(lEspacio3LData);
				}
				{
					bDocumento = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bDocumentoLData = new GridData();
					bDocumentoLData.horizontalSpan = 2;
					bDocumento.setLayoutData(bDocumentoLData);
					bDocumento.setText("Añadir Documento");
				}
				{
					bAnterior = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bAnteriorLData = new GridData();
					bAnteriorLData.verticalAlignment = GridData.END;
					bAnteriorLData.grabExcessVerticalSpace = true;
					bAnteriorLData.horizontalAlignment = GridData.END;
					bAnterior.setLayoutData(bAnteriorLData);
					bAnterior.setText("<< Anterior");
				}
				bAnterior.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bSiguiente.widgetSelected, event="+evt);
						//paso1 = new Paso2(shell, SWT.NONE);
						paso2.setVisible(false);
						paso2.setBounds(228, 0, 228, 564);						
						paso1.setVisible(true);						
						paso1.setBounds(0, 0, 228, 564);
						paso1.setSize(700, 600);						
					}
				});
				{
					bSiguiente = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bAceptarLData = new GridData();
					bAceptarLData.verticalAlignment = GridData.END;
					bAceptarLData.grabExcessVerticalSpace = true;
					bSiguiente.setLayoutData(bAceptarLData);
					bSiguiente.setText("Siguiente >>");
				}
				bSiguiente.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bSiguiente.widgetSelected, event="+evt);
						//paso1 = new Paso2(shell, SWT.NONE);
						paso2.setVisible(false);
						paso2.setBounds(228, 0, 228, 564);
						paso3.setBounds(0, 0, 228, 564);
						paso3.setVisible(true);
						paso3.setSize(700, 600);
					}
				});
			}
			paso2.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void paso3() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			paso3.setLayout(thisLayout);
			paso3.setSize(700, 550);
			{
				GridData izquierda3LData = new GridData();
				izquierda3LData.verticalAlignment = GridData.FILL;
				izquierda3LData.widthHint = 176;
				izquierda3LData.grabExcessHorizontalSpace = true;
				izquierda3LData.grabExcessVerticalSpace = true;
				izquierda3 = new Composite(paso3, SWT.NONE);
				GridLayout izquierda3Layout = new GridLayout();
				izquierda3Layout.makeColumnsEqualWidth = true;
				izquierda3.setLayout(izquierda3Layout);
				izquierda3.setLayoutData(izquierda3LData);
				//izquierda.setBackground(SWTResourceManager.getColor(112,150,252));
				izquierda3.addListener(SWT.Resize,new Listener() {
					public void handleEvent(Event event) {
						//Gradiente.applyGradientBG(izquierda3);
					}					
				});
			}
			{
				GridData derechaLData = new GridData();
				derechaLData.widthHint = 501;
				derechaLData.heightHint = 550;
				derechaLData.grabExcessHorizontalSpace = true;
				derechaLData.grabExcessVerticalSpace = true;
				derecha = new Composite(paso3, SWT.NONE);
				GridLayout derechaLayout = new GridLayout();
				derechaLayout.numColumns = 2;
				derechaLayout.makeColumnsEqualWidth = true;
				derecha.setLayout(derechaLayout);
				derecha.setLayoutData(derechaLData);
				{
					lPaciente = new Label(derecha, SWT.NONE);
					GridData lPacienteLData = new GridData();
					lPacienteLData.horizontalSpan = 2;
					lPaciente.setLayoutData(lPacienteLData);
					lPaciente.setText("Paciente:");
					lPaciente.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					lNombrePaciente = new Label(derecha, SWT.NONE);
					GridData lNombrePacienteLData = new GridData();
					lNombrePacienteLData.horizontalSpan = 2;
					lNombrePaciente.setLayoutData(lNombrePacienteLData);
					lNombrePaciente.setText("Pepito Perez");
				}
				{
					lEspacio = new Label(derecha, SWT.NONE);
				}
				{
					lDiagnostico = new Label(derecha, SWT.NONE);
					GridData lMotivoLData = new GridData();
					lMotivoLData.horizontalAlignment = GridData.FILL;
					lMotivoLData.horizontalSpan = 2;
					lDiagnostico.setLayoutData(lMotivoLData);
					lDiagnostico.setText("DIAGNOSTICO:");
					lDiagnostico.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					GridData tMotivoLData = new GridData();
					tMotivoLData.horizontalAlignment = GridData.FILL;
					tMotivoLData.heightHint = 100;
					tMotivoLData.horizontalSpan = 2;
					tMotivoLData.grabExcessHorizontalSpace = true;
					tMotivo = new Text(derecha, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
					tMotivo.setLayoutData(tMotivoLData);
				}
				{
					GridData label1LData = new GridData();
					label1LData.widthHint = 60;
					lEspacio2 = new Label(derecha, SWT.NONE);
					lEspacio2.setLayoutData(label1LData);
				}
				{
					lTratamientos = new Label(derecha, SWT.NONE);
					GridData lDescripcionLData = new GridData();
					lDescripcionLData.horizontalSpan = 2;
					lTratamientos.setLayoutData(lDescripcionLData);
					lTratamientos.setText("TRATAMIENTOS:");
					lTratamientos.setFont(SWTResourceManager.getFont("Segoe UI",9,1,false,false));
				}
				{
					GridData lEspacio3LData = new GridData();
					lEspacio3LData.horizontalSpan = 2;
					lEspacio3 = new Label(derecha, SWT.NONE);
					lEspacio3.setLayoutData(lEspacio3LData);
				}
				{
					bTratamiento = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bDocumentoLData = new GridData();
					bDocumentoLData.horizontalSpan = 2;
					bTratamiento.setLayoutData(bDocumentoLData);
					bTratamiento.setText("Añadir Tratamiento");
				}
				{
					bAnterior = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bAnteriorLData = new GridData();
					bAnteriorLData.verticalAlignment = GridData.END;
					bAnteriorLData.horizontalAlignment = GridData.END;
					bAnteriorLData.grabExcessVerticalSpace = true;
					bAnterior.setLayoutData(bAnteriorLData);
					bAnterior.setText("<< Anterior");
				}
				bAnterior.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bSiguiente.widgetSelected, event="+evt);
						//paso1 = new Paso2(shell, SWT.NONE);
						paso3.setVisible(false);
						paso3.setBounds(228, 0, 228, 564);						
						paso2.setVisible(true);						
						paso2.setBounds(0, 0, 228, 564);
						paso2.setSize(700, 600);						
					}
				});				
				{
					bSiguiente = new Button(derecha, SWT.PUSH | SWT.CENTER);
					GridData bSiguienteLData = new GridData();
					bSiguienteLData.verticalAlignment = GridData.END;
					bSiguienteLData.grabExcessVerticalSpace = true;
					bSiguiente.setLayoutData(bSiguienteLData);
					bSiguiente.setText("Finalizar >>");
				}
				bSiguiente.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bSiguiente.widgetSelected, event="+evt);
						//paso1 = new Paso2(shell, SWT.NONE);
						shell.dispose();
					}
				});
			}
			paso3.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}