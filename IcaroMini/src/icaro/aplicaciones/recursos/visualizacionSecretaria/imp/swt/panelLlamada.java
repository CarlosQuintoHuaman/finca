package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import java.util.ArrayList;
import java.util.Date;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
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
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

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
public class panelLlamada extends Thread {

	// Variables
	//componentes de la ventana
    private Text tNombre;
    private Composite composite2;
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
    private Button bEditar;
    
    //variables globales
    private String hora;
    private util f;
    private CCombo cPacientes;
    private CLabel LPacientes;
    private Button bBuscar;
	private DatosLlamada llamada;
	private DatosLlamada llamadaAnterior;
	private boolean buscado;
	private ArrayList<InfoPaciente> ListaPacientes;
	private String medico;
	private String fecha;
	private String usuario;
	private Boolean p;
	private String udesp;
	
	final UsoAgenteSecretaria usoAgente;
	
	// Variables de inicializacion de SWT
	
	private Display disp;
	private Shell shell;
	private panelLlamada este;
	private ClaseGeneradoraVisualizacionSecretaria vis;

	/**
	 * Constructor de la ventana
	 * @param visualizador
	 */
	public panelLlamada(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("Llamada");
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
	 * Muestra la ventana igual que la funcion anterior pero mostrando los datos en los campos correspondientes
	 * @param dat contiene los datos que se deben mostrar en la ventana
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
            	medico=dat.getMedico();
            	usuario=dat.getUsuario();
            	udesp=usuario;
            	fecha=dat.getFecha();
    			tNombre.setEditable(false);
    			tTelefono.setEditable(false);
    			tMensaje.setEditable(false);
		    }
        });
	}

	/**
	 * Muestra la ventana vacia pero conociendo los datos del medico y la fecha que le corresponden
	 * @param dat	:: DatosLlamada(medico,fecha)
	 */
	public void mostrarVacia(final DatosLlamada dat){
		disp.asyncExec(new Runnable() {
            public void run() {   
            	shell.open();
            	medico=dat.getMedico();
            	fecha=dat.getFecha();
    			tNombre.setEditable(true);
    			tTelefono.setEditable(true);
    			tMensaje.setEditable(true);
    			bBuscar.setEnabled(true);
    			bEditar.setEnabled(false);
    			bPaciente.setEnabled(true);
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
		
		// Ahora va el codigo de la ventana.
		// �Ojo! Las variables de SWT deberian ser globales
			GridLayout shellLayout = new GridLayout();
			shellLayout.makeColumnsEqualWidth = true;
			shell.setText("Llamada");
			shell.setLayout(shellLayout);
			shell.setSize(402, 305);
			{
        		//Register as a resource user - SWTResourceManager will
        		//handle the obtaining and disposing of resources
        		SWTResourceManager.registerResourceUser(shell);
        	}
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	usoAgente.cerrarVentanaLlamada();
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
					bPaciente = new Button(composite1, SWT.CHECK | SWT.LEFT);
					GridData bPacienteLData = new GridData();
					bPacienteLData.horizontalSpan = 2;
					bPaciente.setLayoutData(bPacienteLData);
					bPaciente.setText("Primera vez");
					bPaciente.setEnabled(false);
					bPaciente.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							bPrimeraVezWidgetSelected(evt);							
						}
					});
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
					tMensaje.setEditable(false);
				}
				{
					GridData composite2LData1 = new GridData();
					composite2LData1.widthHint = 369;
					composite2LData1.heightHint = 28;
					composite2LData1.horizontalSpan = 2;
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout1 = new GridLayout();
					composite2Layout1.numColumns = 2;
					composite2.setLayout(composite2Layout1);
					composite2.setLayoutData(composite2LData1);
					{
						LPacientes = new CLabel(composite2, SWT.NONE);
						GridData LPacientesLData = new GridData();
						LPacientes.setLayoutData(LPacientesLData);
						LPacientes.setText("Pacientes");
					}
					{
						GridData cPacientesLData = new GridData();
						cPacientesLData.widthHint = 292;
						cPacientesLData.heightHint = 17;
						cPacientes = new CCombo(composite2, SWT.BORDER);
						cPacientes.setLayoutData(cPacientesLData);
						cPacientes.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								bPacientesWidgetSelected(evt);
								
							}
						});
					}
				}
				{
					GridData composite2LData = new GridData();
					composite2LData.widthHint = 357;
					composite2LData.heightHint = 42;
					composite2LData.horizontalSpan = 2;
					compoBotones = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.makeColumnsEqualWidth = true;
					composite2Layout.numColumns = 5;
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
								usoAgente.cerrarVentanaLlamada();
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
					{
						bBuscar = new Button(compoBotones, SWT.PUSH | SWT.CENTER);
						GridData bBuscarLData = new GridData();
						bBuscarLData.heightHint = 29;
						bBuscarLData.widthHint = 63;
						bBuscar.setLayoutData(bBuscarLData);
						bBuscar.setText("Buscar");
						bBuscar.addSelectionListener (new SelectionAdapter () {
							public void widgetSelected (SelectionEvent evt) {
								bBuscarWidgetSelected(evt);	
							}  
						});
					}
				}
			}
			buscado=false;
			ListaPacientes=new ArrayList<InfoPaciente>();
			bBuscar.setEnabled(false);
			usuario="";
			udesp=usuario;
			p=false;
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
	 * Evento del boton aceptar de la ventana
	 */
	private void bAceptarWidgetSelected(SelectionEvent evt){
		
		if (hora==null){
			 f=new util(); 
			hora=f.getStrTime();
		}
		p=false;
		String mensaje="Faltan por rellenar los siguientes campos:"+"\n";
		if(tNombre.getText().equals("")){
			mensaje=mensaje+"Nombre"+"\n";
			p=true;
		}
		if(tMensaje.getText().equals("")){
			mensaje=mensaje+"Mensaje"+"\n";
			p=true;
		}
		
		if(tTelefono.getText().equals("")){
			mensaje=mensaje+"Telefono"+"\n";
			p=true;
		}
		if(p)
			usoAgente.mostrarMensajeAviso(mensaje, "Aviso");
		else{
			llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora,medico,fecha);
			llamada.setTipo("llamada");
			if(bPaciente.getSelection()){
				llamada.setUsuario(tNombre.getText());
				llamada.setPaciente(true);
				
			}
		
		    if(buscado){
		    	int i=cPacientes.getSelectionIndex()-1;
		    	llamada.setUsuario(ListaPacientes.get(cPacientes.getSelectionIndex()-1).getUsuario());
		    	llamada.setPaciente(false);
		    }
		    if(!bPaciente.getSelection()&&!buscado){
		    	llamada.setPaciente(false);
		    }

		
			if(!bEditar.getEnabled())
				//llamada al agente para mandar un evento que a�ada el extra que se le pasa por parametro a la tabla de extras
				usoAgente.anadeLlamada(llamada);
			usoAgente.cerrarVentanaLlamada();
		}
	}
	
	/**
	 * Evento del boton borrar de la ventana
	 * @param evt
	 */
	private void bBorrarWidgetSelected(SelectionEvent evt){
		llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora,medico,fecha);
		llamada.setTipo("llamada");
		boolean cc=vis.mostrarMensajeAvisoC("Atenci�n", "�Esta seguro que desea borrar esta cita?");
		if (cc){
			//llama al agente para enviar un evento que le permita borrar el extra que se le pasa por parametro
			if(!bBuscar.getEnabled()&& !bPaciente.getEnabled()){
				llamada.setUsuario(udesp);
				usoAgente.borraLlamada(llamada);
			}
			usoAgente.cerrarVentanaLlamada();
		}
	}
	
	/**
	 * Evento del boton editar de la ventana
	 * @param evt
	 */
	private void bEditarWidgetSelected(SelectionEvent evt){
		if(bEditar.getText().equals("Editar")){
			tNombre.setEditable(true);
			tTelefono.setEditable(true);
			tMensaje.setEditable(true);
			bPaciente.setEnabled(true);
			if(!p){
				llamadaAnterior=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora,medico,fecha);
				llamadaAnterior.setTipo("llamada");
			//meterle el usuario a la llamada anterior
				llamadaAnterior.setUsuario(usuario);				
			}

			bBuscar.setEnabled(true);
		}
		else{
			if (hora==null){
				 f=new util(); 
				hora=f.getStrTime();
			}

			
			p=false;

			String mensaje="Faltan por rellenar los siguientes campos:"+"\n";
			if(tNombre.getText().equals("")){
				mensaje=mensaje+"Nombre"+"\n";
				p=true;
			}
			if(tMensaje.getText().equals("")){
				mensaje=mensaje+"Mensaje"+"\n";
				p=true;
			}
			
			if(tTelefono.getText().equals("")){
				mensaje=mensaje+"Telefono"+"\n";
				p=true;
			}
			if(p)
				usoAgente.mostrarMensajeAviso(mensaje, "Aviso");
			else{
				tNombre.setEditable(false);
				tTelefono.setEditable(false);
				tMensaje.setEditable(false);
				bPaciente.setEnabled(false);
				bBuscar.setEnabled(false);
				
				llamada=new DatosLlamada(tNombre.getText(),tMensaje.getText(),tTelefono.getText(),bPaciente.getSelection(),hora,medico,fecha);
				llamada.setTipo("llamada");
				if(bPaciente.getSelection()){
					llamada.setUsuario(tNombre.getText());
					llamada.setPaciente(true);
					udesp=llamada.getUsuario();
					
					
				}
			
			    if(buscado){
			    	int i=cPacientes.getSelectionIndex()-1;
			    	llamada.setUsuario(ListaPacientes.get(cPacientes.getSelectionIndex()-1).getUsuario());
			    	llamada.setPaciente(false);
			    	udesp=llamada.getUsuario();
			    }
			    if(!bPaciente.getSelection()&&!buscado){
			    	llamada.setPaciente(false);
			    	llamada.setUsuario(llamadaAnterior.getUsuario());
			    	udesp=llamada.getUsuario();
			    	
			    }
				//llama al agente para enviar un evento que le permita modificar el extra que se le pasa por parametro
			    usoAgente.modificaLlamada(llamadaAnterior, llamada);
	
			}
		}
		
		if(bEditar.getText().equals("Editar"))
			bEditar.setText("Guardar");
		else if(bEditar.getText().equals("Guardar")&&!p)
			bEditar.setText("Editar");		
	}
	
	/**
	 * Evento del check box para detectar si es un nuevo paciente
	 * @param evt
	 */
	private void bPrimeraVezWidgetSelected(SelectionEvent evt){
		if (bPaciente.getSelection()){
			tNombre.setEditable(true);
			tTelefono.setEditable(true);
			tMensaje.setEditable(true);
			bBuscar.setEnabled(false);
			cPacientes.setEnabled(false);
			tNombre.setText("");
			tMensaje.setText("");
			tTelefono.setText("");
			buscado=false;
		}
		else{
			tNombre.setEditable(false);
			tTelefono.setEditable(false);
			//tMensaje.setEditable(true);
			bBuscar.setEnabled(true);
			cPacientes.setEnabled(true);

		}
			
	}
	/**
	 * Evento del boton buscar de la ventana
	 * @param evt
	 */
	private void bBuscarWidgetSelected(SelectionEvent evt){
			usoAgente.buscarPacientesL();
	}
	
	private void bPacientesWidgetSelected(SelectionEvent evt){
		CCombo lsel=(CCombo)evt.getSource();
		int i=lsel.getSelectionIndex();
		if(i>0){
			InfoPaciente p=ListaPacientes.get(i-1);
			tNombre.setEditable(false);
			//tMensaje.setEditable(true);
			tTelefono.setEditable(false);
			bBuscar.setEnabled(true);
			cPacientes.setEnabled(true);
			tNombre.setText(p.getNombre()+" "+p.getApellido1()+" "+p.getApellido2());
			tTelefono.setText(p.getTelefono());
			tMensaje.setText("");
			buscado=true;
		}
		else{
			tNombre.setText("");
			tMensaje.setText("");
			tTelefono.setText("");
			buscado=false;
		}
	}
	
	public void rellenaTabla(final ArrayList<InfoPaciente> l){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.asyncExec(new Runnable() {
            public void run() {
            	ListaPacientes=l;
            	cPacientes.removeAll();
            	cPacientes.add("Seleccionar...");
            	cPacientes.select(0);
            	for(int i=0;i<l.size();i++){
            		cPacientes.add(l.get(i).getNombre()+" "+l.get(i).getApellido1()+" "+l.get(i).getApellido2());
            	}
 
	       }
         });
	}

}