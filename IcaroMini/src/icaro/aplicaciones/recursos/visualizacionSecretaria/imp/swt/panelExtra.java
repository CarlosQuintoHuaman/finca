package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import java.util.Date;

import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;
import icaro.util.util;

import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

import icaro.util.util;

public class panelExtra extends Thread {

	// Variables
	// Componentes de la ventana
    private Text tNombre;
    private Button bBorrar;
    private Composite compoBotones;
    private Button bCancelar;
    private Button bAceptar;
    private Button bEditar;
    private Text tMensaje;
    private Text tTelefono;
    private Composite composite1;
    private Button bPaciente;
    private CLabel cMensaje;
    private CLabel cTelefono;
    private CLabel cNombre;
    
    // Variables globales
    private String hora;
    private util f;
	private DatosLlamada llamada;
	private DatosLlamada llamadaAnterior;

	final UsoAgenteSecretaria usoAgente;
	
	// Variables de inicializacion de SWT
	
	private Display disp;
	private Shell shell;
	private panelExtra este;

	private ClaseGeneradoraVisualizacionSecretaria vis;
	/**
	 * Constructor de la ventana
	 * @param visualizador
	 */
	public panelExtra(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("Extra");
		este = this;
		vis=visualizador;
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
	 * Muestra la ventana rellenando los campos que corresponda con los datos que se le pasa por parametro
	 * @param dat	:: DatosLlamada(nombre, mensaje, telefono,EsPaciente, hora)
	 */
	public void mostrar(final DatosLlamada dat){
		disp.asyncExec(new Runnable() {
            public void run() {   
            	shell.open();
            	tNombre.setText(dat.getNombre());
            	tTelefono.setText(dat.getTelf());
            	tMensaje.setText(dat.getMensaje());
            	bPaciente.setSelection(dat.getPaciente());
            	hora=dat.getHora();
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
		try {
			shell = new Shell(disp);
			GridLayout shellLayout = new GridLayout();
			shellLayout.makeColumnsEqualWidth = true;
			shell.setText("Extra");
			shell.setLayout(shellLayout);
			shell.setSize(402, 276);
			{
        		//Register as a resource user - SWTResourceManager will
        		//handle the obtaining and disposing of resources
        		SWTResourceManager.registerResourceUser(shell);
        	}
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	usoAgente.cerrarVentanaExtra();
			    }
			});
			{
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1 = new Composite(shell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1.setLayout(composite1Layout);
				composite1.setLayoutData(composite1LData);
				{
					cNombre = new CLabel(composite1, SWT.NONE);
					cNombre.setText("Nombre");
				}
				{
					cTelefono = new CLabel(composite1, SWT.NONE);
					cTelefono.setText("Telefono");
				}
				{
					GridData tNombreLData = new GridData();
					tNombreLData.heightHint = 17;
					tNombreLData.widthHint = 269;
					tNombre = new Text(composite1, SWT.BORDER);
					tNombre.setLayoutData(tNombreLData);
					tNombre.setEditable(false);
				}
				{
					GridData tTelefonoLData = new GridData();
					tTelefonoLData.heightHint = 17;
					tTelefonoLData.widthHint = 66;
					tTelefono = new Text(composite1, SWT.BORDER);
					tTelefono.setLayoutData(tTelefonoLData);
					tTelefono.setEditable(false);
				}
				{
					cMensaje = new CLabel(composite1, SWT.NONE);
					GridData cMensajeLData = new GridData();
					cMensajeLData.horizontalSpan = 2;
					cMensaje.setLayoutData(cMensajeLData);
					cMensaje.setText("Informacion adicional");
					
				}
				{
					GridData tmensajeLData = new GridData();
					tmensajeLData.widthHint = 351;
					tmensajeLData.heightHint = 69;
					tmensajeLData.horizontalSpan = 2;
					tMensaje = new Text(composite1, SWT.MULTI | SWT.WRAP | SWT.BORDER);
					tMensaje.setLayoutData(tmensajeLData);
					tMensaje.setEditable(false);
				}
				{
					bPaciente = new Button(composite1, SWT.CHECK | SWT.LEFT);
					GridData bPacienteLData = new GridData();
					bPacienteLData.horizontalSpan = 2;
					bPaciente.setLayoutData(bPacienteLData);
					bPaciente.setText("Tiene Ficha");
					bPaciente.setEnabled(false);
				}
				{
					GridData composite2LData = new GridData();
					composite2LData.widthHint = 250;
					composite2LData.heightHint = 42;
					composite2LData.horizontalAlignment = GridData.CENTER;
					compoBotones = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.makeColumnsEqualWidth = true;
					composite2Layout.numColumns = 4;
					compoBotones.setLayout(composite2Layout);
					compoBotones.setLayoutData(composite2LData);
					{
						bAceptar = new Button(compoBotones, SWT.PUSH | SWT.CENTER);
						GridData bAceptarLData = new GridData();
						bAceptarLData.widthHint = 63;
						bAceptarLData.heightHint = 29;
						bAceptar.setLayoutData(bAceptarLData);
						bAceptar.setText("Aceptar");
						bAceptar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								bAceptarWidgetSelected(evt);
								
							}                               
						});
					}
					{
						bCancelar = new Button(compoBotones, SWT.PUSH | SWT.CENTER);
						GridData bCancelarLData = new GridData();
						bCancelarLData.widthHint = 63;
						bCancelarLData.heightHint = 29;
						bCancelar.setLayoutData(bCancelarLData);
						bCancelar.setText("Cancelar");
						bCancelar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								
									usoAgente.cerrarVentanaExtra();
								
								
							}                               
						});
					}
					{
						bBorrar = new Button(compoBotones, SWT.PUSH | SWT.CENTER);
						GridData bBorrarLData = new GridData();
						bBorrarLData.heightHint = 29;
						bBorrarLData.widthHint = 63;
						bBorrar.setLayoutData(bBorrarLData);
						bBorrar.setText("Borrar");
						bBorrar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								bBorrarWidgetSelected(evt);
								
							}                               
						});
					}
					
					{
						bEditar = new Button(compoBotones, SWT.PUSH | SWT.CENTER);
						GridData bEditarLData = new GridData();
						bEditarLData.heightHint = 29;
						bEditarLData.widthHint = 63;
						bEditar.setLayoutData(bEditarLData);
						bEditar.setText("Editar");
						bEditar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								bEditarWidgetSelected(evt);
								
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	/**
	 * Accion asociada al evento del boton 'aceptar'
	 * Nos permite inserta un nuevo extra en la tabla de extras de la ventana 'agenda'
	 */
	private void bAceptarWidgetSelected(SelectionEvent evt){
		
		if (hora==null){
			 f=new util(); 
			hora=f.getStrTime();
		}
		if (tNombre.getText().equals("")){
			usoAgente.mostrarMensajeError("Debe rellenar un nombre", "Error en formato");
		}
		else{
		llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
		if(!tNombre.getEditable())
			//llamada al agente para mandar un evento que añada el extra que se le pasa por parametro a la tabla de extras
			usoAgente.anadeExtra(llamada);
		usoAgente.cerrarVentanaExtra();
		}
	}
	/**
	 * Accion asociada al evento del boton 'borrar'
	 * Nos permite borrar un extra en la tabla de extras de la ventana 'agenda'
	 */
	private void bBorrarWidgetSelected(SelectionEvent evt){
		
		llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
		boolean cc=vis.mostrarMensajeAvisoC("Atención", "¿Esta seguro que desea borrar esta cita?");
		if (cc){
		//llama al agente para enviar un evento que le permita borrar el extra que se le pasa por parametro
			usoAgente.borraExtra(llamada);
			usoAgente.cerrarVentanaExtra();
		}
	}
	
	/**
	 * Accion asociada al evento del boton 'Editar'
	 * Nos permite editar un extra de la tabla de extras de la ventana 'agenda'
	 */
	private void bEditarWidgetSelected(SelectionEvent evt){
		if(bEditar.getText().equals("Editar"))
			bEditar.setText("Guardar");
		else
			bEditar.setText("Editar");
		
		if(bEditar.getText().equals("Guardar")){
			tNombre.setEditable(true);
			tTelefono.setEditable(true);
			tMensaje.setEditable(true);
			bPaciente.setEnabled(true);
			llamadaAnterior=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
		}
		else{
			if (hora==null){
				 f=new util(); 
				hora=f.getStrTime();
			}
			tNombre.setEditable(false);
			tTelefono.setEditable(false);
			tMensaje.setEditable(false);
			bPaciente.setEnabled(false);
			if (tNombre.getText().equals("")){
				usoAgente.mostrarMensajeError("Debe rellenar un nombre", "Error en formato");
			}
			else{
			llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
			//llama al agente para enviar un evento que le permita modificar el extra que se le pasa por parametro
			usoAgente.modificaExtra(llamadaAnterior, llamada);

			}
		}

	}
}