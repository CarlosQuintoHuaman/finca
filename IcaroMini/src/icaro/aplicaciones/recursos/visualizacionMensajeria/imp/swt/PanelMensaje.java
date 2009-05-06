package icaro.aplicaciones.recursos.visualizacionMensajeria.imp.swt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.visualizacionMensajeria.imp.ClaseGeneradoraVisualizacionMensajeria;
import icaro.aplicaciones.recursos.visualizacionMensajeria.imp.usuario.UsoAgenteMensajeria;

public class PanelMensaje extends Thread {

	// Variables
	private Composite cContenido;
	private CLabel lDestinatario;
	private CLabel lAsunto;
	private Text tAsunto;
	private Text tMensaje;
	private CCombo comboUsuarios;
	private CLabel lMensaje;
	private CLabel lBuscar;
	private Button bCancelar;
	private Button bAceptar;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Mensajeria" por el nombre del agente.
	 */
	final UsoAgenteMensajeria usoAgente;
	String usuario;
	ArrayList<String> usuarios = new ArrayList<String>();
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	/**
	 * 
	 * @param visualizador
	 */
	public PanelMensaje(ClaseGeneradoraVisualizacionMensajeria visualizador){
		super("Agenda");
		usoAgente = new UsoAgenteMensajeria(visualizador);
	}

	public void run(){
		// Crear el display y generar la ventana pero SIN MOSTRARLA
		// Es decir, no debe haber un shell.open en initGUI()
		disp = new Display();
		initGUI();
	}

	public void mostrar(String usuario){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		this.usuario = usuario;
		
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

	public UsoAgenteMensajeria getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		shell.setLayout(shellLayout);
		shell.setText("Mensajeria Interna");
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
				lBuscar.setText("ENVIAR UN NUEVO MENSAJE");
				lBuscar.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
			}
			{
				lDestinatario = new CLabel(cContenido, SWT.NONE);
				lDestinatario.setText("Destinatario:");
			}
			{
				comboUsuarios = new CCombo(cContenido, SWT.BORDER);
				GridData comboUsuariosLData = new GridData();
				comboUsuariosLData.verticalAlignment = GridData.BEGINNING;
				comboUsuariosLData.horizontalAlignment = GridData.FILL;
				comboUsuarios.setLayoutData(comboUsuariosLData);
				comboUsuarios.setText("Elegir Destinatario...");
			}
			{
				lAsunto = new CLabel(cContenido, SWT.NONE);
				lAsunto.setText("Asunto:");
			}
			{
				GridData tPActivoLData = new GridData();
				tPActivoLData.grabExcessHorizontalSpace = true;
				tPActivoLData.horizontalAlignment = GridData.FILL;
				tAsunto = new Text(cContenido, SWT.BORDER);
				tAsunto.setLayoutData(tPActivoLData);
			}
			{
				lMensaje = new CLabel(cContenido, SWT.NONE);
				lMensaje.setText("Mensaje:");
			}
			{
				GridData tDescLData = new GridData();
				tDescLData.horizontalAlignment = GridData.FILL;
				tDescLData.horizontalSpan = 2;
				tDescLData.heightHint = 76;
				tDescLData.verticalAlignment = GridData.FILL;
				tDescLData.grabExcessVerticalSpace = true;
				tMensaje = new Text(cContenido, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
				tMensaje.setLayoutData(tDescLData);
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
					InfoMensaje m = new InfoMensaje(usuario,comboUsuarios.getText(),new Timestamp(new Date().getTime()), tAsunto.getText(), tMensaje.getText());
					usoAgente.enviarMensaje(m);
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
					
				}
			});
		}
		
		shell.layout();

		


		// Capturo el cierre de la ventana con el boton X
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent arg0) {
				// Es posible que haya que cambiar el nombre de este metodo
				usoAgente.cerrarVentanaMensajeNuevo();
			}
			
		});

		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	public void setUsuarios(final ArrayList<String> u) {
		usuarios = u;
				
		disp.asyncExec(new Runnable() {
            public void run() {
            	comboUsuarios.removeAll();
            	comboUsuarios.setText("Elegir Destinatario...");
            	for (int i=0; i<u.size(); i++)
        			comboUsuarios.add(u.get(i));
	       }
         });
	}
}