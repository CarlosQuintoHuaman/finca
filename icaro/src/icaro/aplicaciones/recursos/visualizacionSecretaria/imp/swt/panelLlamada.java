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
import icaro.util.util;

public class panelLlamada extends Thread {

	// Variables
    private Text tNombre;
    private Button bBorrar;
    private Composite compoBotones;
    private Button bCancelar;
    private Button bAceptar;
    private Text tMensaje;
    private Text tTelefono;
    private Composite composite1;
    private Button bPaciente;
    private CLabel cMensaje;
    private CLabel cTelefono;
    private CLabel cNombre;
    private String hora;
    private util f;


	final UsoAgenteSecretaria usoAgente;
	
	// Variables de inicializacion de SWT
	
	private Display disp;
	private Shell shell;
	private panelLlamada este;
	private DatosLlamada llamada;

	/**
	 * 
	 * @param visualizador
	 */
	public panelLlamada(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("Llamada");
		este = this;
		
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

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		try {
			shell = new Shell(disp);
		
		// Ahora va el codigo de la ventana.
		// �Ojo! Las variables de SWT deberian ser globales
			GridLayout shellLayout = new GridLayout();
			shellLayout.makeColumnsEqualWidth = true;
			shell.setText("Llamada");
			shell.setLayout(shellLayout);
			shell.setSize(402, 276);
			{
        		//Register as a resource user - SWTResourceManager will
        		//handle the obtaining and disposing of resources
        		SWTResourceManager.registerResourceUser(shell);
        	}
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
				}
				{
					GridData tTelefonoLData = new GridData();
					tTelefonoLData.heightHint = 17;
					tTelefonoLData.widthHint = 66;
					tTelefono = new Text(composite1, SWT.BORDER);
					tTelefono.setLayoutData(tTelefonoLData);
				}
				{
					cMensaje = new CLabel(composite1, SWT.NONE);
					GridData cMensajeLData = new GridData();
					cMensajeLData.horizontalSpan = 2;
					cMensaje.setLayoutData(cMensajeLData);
					cMensaje.setText("Mensaje");
				}
				{
					GridData tmensajeLData = new GridData();
					tmensajeLData.widthHint = 351;
					tmensajeLData.heightHint = 69;
					tmensajeLData.horizontalSpan = 2;
					tMensaje = new Text(composite1, SWT.MULTI | SWT.WRAP | SWT.BORDER);
					tMensaje.setLayoutData(tmensajeLData);
				}
				{
					bPaciente = new Button(composite1, SWT.CHECK | SWT.LEFT);
					GridData bPacienteLData = new GridData();
					bPacienteLData.horizontalSpan = 2;
					bPaciente.setLayoutData(bPacienteLData);
					bPaciente.setText("Tiene Ficha");
				}
				{
					GridData composite2LData = new GridData();
					composite2LData.widthHint = 215;
					composite2LData.heightHint = 42;
					composite2LData.horizontalAlignment = GridData.CENTER;
					compoBotones = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.makeColumnsEqualWidth = true;
					composite2Layout.numColumns = 3;
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
								destruir();
								
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
	private void bAceptarWidgetSelected(SelectionEvent evt){
		//llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),indice);
		if (hora==null){
			 f=new util(); 
			hora=f.getStrTime();
		}
		if (tNombre.getText().equals("")){
			usoAgente.mostrarMensajeError("Debe rellenar un nombre", "Error en formato");
		}
		else{
		llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
		usoAgente.anadeLlamada(llamada);
		destruir();
		}
	}
	private void bBorrarWidgetSelected(SelectionEvent evt){
		//llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),indice);
		llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora);
		usoAgente.borraLlamada(llamada);
		destruir();
	}

}