package icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.swt;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.ClaseGeneradoraVisualizacionMedicamentos;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.usuario.UsoAgenteMedicamentos;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelMedicamentos extends Composite {

	// Variables
	private CLabel lTitulo;
	private List listaMed;
	private Text tBusNombre;
	private CLabel lBusPActivo;
	private Text tBusPActivo;
	private CLabel lPActivo;
	private Text tPActivo;
	private Button bGuardar;
	private Button bBorrar;
	private Button bNuevo;
	private CLabel lAcciones;
	private Text tIndicaciones;
	private CLabel lIndicaciones;
	private Text tDesc;
	private CLabel lDesc;
	private Composite cSeleccionado;
	private CLabel lSeleccionado;
	private Composite cBuscar;
	private CLabel lBusNombre;
	private CLabel lBuscar;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Medicamentos" por el nombre del agente.
	 */
	final UsoAgenteMedicamentos usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	
	private ArrayList<InfoMedicamento> medicamentos;
	String paciente;

	/**
	 * - En fase de pruebas -
	 * @param visualizador
	 * @param padre
	 * @param estilo
	 */
	public PanelMedicamentos(ClaseGeneradoraVisualizacionMedicamentos visualizador, Composite padre, final int estilo){
		super(padre, estilo);
		
		
//		this.setParent(padre);
		
		usoAgente = new UsoAgenteMedicamentos(visualizador);
		initGUI();
	}

//	public void run(){
//		// Crear el display y generar la ventana pero SIN MOSTRARLA
//		// Es decir, no debe haber un shell.open en initGUI()
//		disp = new Display();
//		initGUI();
//	}

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

	public UsoAgenteMedicamentos getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = true;
		thisLayout.numColumns = 2;
		this.setLayout(thisLayout);
		this.setSize(535, 500);
		{
			lTitulo = new CLabel(this, SWT.NONE);
			GridData lTituloLData = new GridData();
			lTituloLData.verticalAlignment = GridData.BEGINNING;
			lTituloLData.horizontalSpan = 2;
			lTitulo.setLayoutData(lTituloLData);
			lTitulo.setText("MEDICAMENTOS EN LA BASE DE DATOS");
			lTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
		}
		{
			GridData listaMedLData = new GridData();
			listaMedLData.verticalAlignment = GridData.FILL;
			listaMedLData.horizontalAlignment = GridData.FILL;
			listaMedLData.grabExcessHorizontalSpace = true;
			listaMedLData.grabExcessVerticalSpace = true;
			listaMed = new List(this, SWT.V_SCROLL | SWT.BORDER);
			listaMed.setLayoutData(listaMedLData);
		}
		{
			cBuscar = new Composite(this, SWT.NONE);
			GridLayout cBuscarLayout = new GridLayout();
			cBuscarLayout.makeColumnsEqualWidth = true;
			GridData cBuscarLData = new GridData();
			cBuscarLData.grabExcessHorizontalSpace = true;
			cBuscarLData.grabExcessVerticalSpace = true;
			cBuscarLData.horizontalAlignment = GridData.FILL;
			cBuscarLData.verticalAlignment = GridData.FILL;
			cBuscar.setLayoutData(cBuscarLData);
			cBuscar.setLayout(cBuscarLayout);
			{
				lBuscar = new CLabel(cBuscar, SWT.NONE);
				lBuscar.setText("BUSCAR:");
				lBuscar.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				lBusNombre = new CLabel(cBuscar, SWT.NONE);
				lBusNombre.setText("Por nombre:");
			}
			{
				GridData tNombreLData = new GridData();
				tNombreLData.grabExcessHorizontalSpace = true;
				tNombreLData.horizontalAlignment = GridData.FILL;
				tBusNombre = new Text(cBuscar, SWT.BORDER);
				tBusNombre.setLayoutData(tNombreLData);
			}
			{
				lBusPActivo = new CLabel(cBuscar, SWT.NONE);
				lBusPActivo.setText("Por  Principio Activo:");
			}
			{
				GridData tPActivoLData = new GridData();
				tPActivoLData.horizontalAlignment = GridData.FILL;
				tBusPActivo = new Text(cBuscar, SWT.BORDER);
				tBusPActivo.setLayoutData(tPActivoLData);
			}
			{
				lAcciones = new CLabel(cBuscar, SWT.NONE);
				lAcciones.setText("ACCIONES:");
				lAcciones.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				bNuevo = new Button(cBuscar, SWT.PUSH | SWT.CENTER);
				bNuevo.setText("Nuevo Medicamento");
				bNuevo.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bNuevo.widgetSelected, event="+evt);
						//TODO add your code for bNuevo.widgetSelected
					}
				});
			}
			{
				bBorrar = new Button(cBuscar, SWT.PUSH | SWT.CENTER);
				bBorrar.setText("Borrar Medicamento");
				bBorrar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						System.out.println("bBorrar.widgetSelected, event="+evt);
						//TODO add your code for bBorrar.widgetSelected
					}
				});
			}
		}
		{
			cSeleccionado = new Composite(this, SWT.NONE);
			GridLayout cSeleccionadoLayout = new GridLayout();
			cSeleccionadoLayout.numColumns = 2;
			GridData cSeleccionadoLData = new GridData();
			cSeleccionadoLData.grabExcessHorizontalSpace = true;
			cSeleccionadoLData.horizontalAlignment = GridData.FILL;
			cSeleccionadoLData.horizontalSpan = 2;
			cSeleccionado.setLayoutData(cSeleccionadoLData);
			cSeleccionado.setLayout(cSeleccionadoLayout);
			{
				lSeleccionado = new CLabel(cSeleccionado, SWT.NONE);
				lSeleccionado.setText("MEDICAMENTO SELECCIONADO");
				GridData lSeleccionadoLData = new GridData();
				lSeleccionadoLData.horizontalSpan = 2;
				lSeleccionado.setLayoutData(lSeleccionadoLData);
				lSeleccionado.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				lPActivo = new CLabel(cSeleccionado, SWT.NONE);
				lPActivo.setText("Principio Activo:");
			}
			{
				GridData tPActivoLData = new GridData();
				tPActivoLData.grabExcessHorizontalSpace = true;
				tPActivoLData.horizontalAlignment = GridData.FILL;
				tPActivo = new Text(cSeleccionado, SWT.BORDER);
				tPActivo.setLayoutData(tPActivoLData);
			}
			{
				lDesc = new CLabel(cSeleccionado, SWT.NONE);
				GridData lDescLData = new GridData();
				lDescLData.verticalAlignment = GridData.BEGINNING;
				lDesc.setLayoutData(lDescLData);
				lDesc.setText("Descripcion:");
			}
			{
				GridData tDescLData = new GridData();
				tDescLData.horizontalAlignment = GridData.FILL;
				tDescLData.heightHint = 54;
				tDesc = new Text(cSeleccionado, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
				tDesc.setLayoutData(tDescLData);
			}
			{
				lIndicaciones = new CLabel(cSeleccionado, SWT.NONE);
				lIndicaciones.setText("Indicaciones:");
			}
			{
				GridData text1LData = new GridData();
				text1LData.horizontalAlignment = GridData.FILL;
				text1LData.heightHint = 54;
				tIndicaciones = new Text(cSeleccionado, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
				tIndicaciones.setLayoutData(text1LData);
			}
			{
				bGuardar = new Button(cSeleccionado, SWT.PUSH | SWT.CENTER);
				GridData bGuardarLData = new GridData();
				bGuardarLData.horizontalAlignment = GridData.CENTER;
				bGuardarLData.horizontalSpan = 2;
				bGuardar.setLayoutData(bGuardarLData);
				bGuardar.setText("Guardar Cambios");
			}
		}
		this.layout();
		
		//usoAgente.cargaFinalizada(this);
	}


	// Aqui iran los metodos especificos de cada ventana
}