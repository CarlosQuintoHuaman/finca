package icaro.aplicaciones.recursos.visualizacionHistorial.imp.swt;

import java.io.File;
import java.sql.Timestamp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.ClaseGeneradoraVisualizacionHistorial;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario.UsoAgenteHistorial;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelPrueba extends Thread {

	// Variables SWT
	private Composite cContenido;
	private CLabel lNombre;
	private Text tNombre;
	private Button bCancelar;
	private Button bAceptar;
	private Text tDescripcion;
	private CLabel lDescripcion;
	private Button bExaminar;
	private Text tArchivo;
	private CLabel lArchivo;
	
	
	// Otras variables
	InfoVisita v;
	Text archivo;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteHistorial usoAgente;
	private ClaseGeneradoraVisualizacionHistorial visualizador;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	/**
	 * 
	 * @param visualizador
	 */
	public PanelPrueba(ClaseGeneradoraVisualizacionHistorial visualizador){
		super("Prueba Historial");
		usoAgente = new UsoAgenteHistorial(visualizador);
		this.visualizador = visualizador;
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
		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}
		

		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		shell.setLayout(shellLayout);
		shell.setText("Añadir prueba nueva");
		shell.setSize(500, 400);
		{
			cContenido = new Composite(shell, SWT.NONE);
			GridLayout cContenidoLayout = new GridLayout();
			cContenidoLayout.numColumns = 3;
			GridData cContenidoLData = new GridData();
			cContenidoLData.horizontalAlignment = GridData.FILL;
			cContenidoLData.grabExcessHorizontalSpace = true;
			cContenidoLData.grabExcessVerticalSpace = true;
			cContenidoLData.verticalAlignment = GridData.FILL;
			cContenidoLData.horizontalSpan = 2;
			cContenido.setLayoutData(cContenidoLData);
			cContenido.setLayout(cContenidoLayout);
			{
				lNombre = new CLabel(cContenido, SWT.NONE);
				lNombre.setText("Nombre:");
			}
			{
				GridData tNombreLData = new GridData();
				tNombreLData.grabExcessHorizontalSpace = true;
				tNombreLData.horizontalAlignment = GridData.FILL;
				tNombreLData.horizontalSpan = 2;
				tNombre = new Text(cContenido, SWT.BORDER);
				tNombre.setLayoutData(tNombreLData);
			}
			{
				lArchivo = new CLabel(cContenido, SWT.NONE);
				lArchivo.setText("Ruta del archivo:");
			}
			{
				GridData tArchivoLData = new GridData();
				tArchivoLData.grabExcessHorizontalSpace = true;
				tArchivoLData.horizontalAlignment = GridData.FILL;
				tArchivo = new Text(cContenido, SWT.BORDER);
				tArchivo.setLayoutData(tArchivoLData);
			}
			{
				bExaminar = new Button(cContenido, SWT.PUSH | SWT.CENTER);
				GridData bExaminarLData = new GridData();
				bExaminarLData.horizontalAlignment = GridData.END;
				bExaminar.setLayoutData(bExaminarLData);
				bExaminar.setText("Examinar");
				bExaminar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
				        FileDialog dialog = new FileDialog(shell, SWT.NULL);
				        String path = dialog.open();
				        if (path != null) {

				          File file = new File(path);
				          if (file.isFile())
				            displayFiles(new String[] { file.toString()});
				          else
				            displayFiles(file.list());

				        }
					}
				});
			}
			{
				lDescripcion = new CLabel(cContenido, SWT.NONE);
				GridData lDescripcionLData = new GridData();
				lDescripcionLData.horizontalSpan = 3;
				lDescripcion.setLayoutData(lDescripcionLData);
				lDescripcion.setText("Descripcion:");
			}
			{
				GridData tDescripcionLData = new GridData();
				tDescripcionLData.grabExcessHorizontalSpace = true;
				tDescripcionLData.horizontalAlignment = GridData.FILL;
				tDescripcionLData.horizontalSpan = 3;
				tDescripcionLData.grabExcessVerticalSpace = true;
				tDescripcionLData.verticalAlignment = GridData.FILL;
				tDescripcion = new Text(cContenido, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
				tDescripcion.setLayoutData(tDescripcionLData);
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
	
	/**
	 * Metodo para la seleccion de un archivo usando Examinar
	 */
	public void displayFiles(String[] files) {
		for (int i = 0; files != null && i < files.length; i++) {
			tArchivo.setText(files[i]);
			tArchivo.setEditable(true);
		}
	}

	public void setPaciente(InfoVisita v) {
		this.v = v;
	}
	
	private void bAceptarWidgetSelected(SelectionEvent evt) {        
		boolean correcto = true;
		String mensaje = "Faltan los siguientes campos por rellenar:\n\n";
		
		if (tNombre.getText() == "") {
			correcto = false;
			mensaje += "- Nombre de la prueba\n";
		}
		
		if (tDescripcion.getText() == "") {
			correcto = false;
			mensaje += "- Descripcion de la prueba\n";
		}
		
		if (correcto) {
			InfoPrueba p = new InfoPrueba(v.getUsuario(), tNombre.getText(), new Timestamp(v.getFecha().getTime()), "prueba", tArchivo.getText(), tDescripcion.getText());
			usoAgente.guardarPrueba(p);
			usoAgente.cerrarVentanaPrueba();
		}
		else
			usoAgente.mostrarMensajeError(mensaje, "Faltan campos por rellenar");
	}
	
	private void bCancelarWidgetSelected(SelectionEvent evt) {
		usoAgente.cerrarVentanaPrueba();
	}

}