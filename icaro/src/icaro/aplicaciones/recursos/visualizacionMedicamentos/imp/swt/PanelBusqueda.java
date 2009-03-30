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

public class PanelBusqueda extends Thread {

	// Variables
	private Composite cContenido;
	private CLabel lNombre;
	private Text tNombre;
	private List listaMed;
	private CLabel lPActivo;
	private Text tPActivo;
	private CLabel lBuscar;
	private CLabel lLista;
	private Button bCancelar;
	private Button bAceptar;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Medicamentos" por el nombre del agente.
	 */
	final UsoAgenteMedicamentos usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private PanelBusqueda este;

	/**
	 * 
	 * @param visualizador
	 */
	public PanelBusqueda(ClaseGeneradoraVisualizacionMedicamentos visualizador){
		super("Agenda");
		este = this;
		
		usoAgente = new UsoAgenteMedicamentos(visualizador);
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

	public UsoAgenteMedicamentos getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		
		// Ahora va el codigo de la ventana.
		// �Ojo! Las variables de SWT deberian ser globales

		
		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}
		
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		shell.setLayout(shellLayout);
		shell.setText("Seleccionar Medicamento");
		shell.setSize(500, 400);
		{
			cContenido = new Composite(shell, SWT.NONE);
			GridLayout cContenidoLayout = new GridLayout();
			cContenidoLayout.numColumns = 2;
			GridData cContenidoLData = new GridData();
			cContenidoLData.horizontalAlignment = GridData.FILL;
			cContenidoLData.grabExcessHorizontalSpace = true;
			cContenidoLData.grabExcessVerticalSpace = true;
			cContenidoLData.verticalAlignment = GridData.FILL;
			cContenidoLData.horizontalSpan = 2;
			cContenido.setLayoutData(cContenidoLData);
			cContenido.setLayout(cContenidoLayout);
			{
				lBuscar = new CLabel(cContenido, SWT.NONE);
				GridData lBuscarLData = new GridData();
				lBuscarLData.horizontalSpan = 2;
				lBuscar.setLayoutData(lBuscarLData);
				lBuscar.setText("BUSCAR");
				lBuscar.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				lNombre = new CLabel(cContenido, SWT.NONE);
				lNombre.setText("Nombre:");
			}
			{
				GridData tNombreLData = new GridData();
				tNombreLData.grabExcessHorizontalSpace = true;
				tNombreLData.horizontalAlignment = GridData.FILL;
				tNombre = new Text(cContenido, SWT.BORDER);
				tNombre.setLayoutData(tNombreLData);
			}
			{
				lPActivo = new CLabel(cContenido, SWT.NONE);
				lPActivo.setText("Principio Activo:");
			}
			{
				GridData tPActivoLData = new GridData();
				tPActivoLData.grabExcessHorizontalSpace = true;
				tPActivoLData.horizontalAlignment = GridData.FILL;
				tPActivo = new Text(cContenido, SWT.BORDER);
				tPActivo.setLayoutData(tPActivoLData);
			}
			{
				lLista = new CLabel(cContenido, SWT.NONE);
				lLista.setText("LISTADO");
				lLista.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				GridData listaMedLData = new GridData();
				listaMedLData.horizontalSpan = 2;
				listaMedLData.grabExcessHorizontalSpace = true;
				listaMedLData.horizontalAlignment = GridData.FILL;
				listaMedLData.verticalAlignment = GridData.FILL;
				listaMedLData.grabExcessVerticalSpace = true;
				listaMed = new List(cContenido, SWT.BORDER);
				listaMed.setLayoutData(listaMedLData);
			}
		}
		{
			bAceptar = new Button(shell, SWT.PUSH | SWT.CENTER);
			GridData bAceptarLData = new GridData();
			bAceptarLData.horizontalAlignment = GridData.END;
			bAceptarLData.grabExcessHorizontalSpace = true;
			bAceptar.setLayoutData(bAceptarLData);
			bAceptar.setText("Aceptar");
			bAceptar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					bAceptarWidgetSelected(evt);
				}
			});
		}
		{
			bCancelar = new Button(shell, SWT.PUSH | SWT.CENTER);
			GridData bCancelarLData = new GridData();
			bCancelarLData.grabExcessHorizontalSpace = true;
			bCancelar.setLayoutData(bCancelarLData);
			bCancelar.setText("Cancelar");
			bCancelar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					bCancelarWidgetSelected(evt);
				}
			});
		}
		
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana

	private void bAceptarWidgetSelected(SelectionEvent evt) {
		System.out.println("bAceptar.widgetSelected, event="+evt);
		//TODO add your code for bAceptar.widgetSelected
	}
	
	private void bCancelarWidgetSelected(SelectionEvent evt) {
		System.out.println("bCancelar.widgetSelected, event="+evt);
		//TODO add your code for bCancelar.widgetSelected
	}
	
	public void mostrarDatos(ArrayList<InfoMedicamento> m) {
			
	}
}