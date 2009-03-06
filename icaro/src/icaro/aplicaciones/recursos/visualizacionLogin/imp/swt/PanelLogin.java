package icaro.aplicaciones.recursos.visualizacionLogin.imp.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.recursos.visualizacionLogin.imp.ClaseGeneradoraVisualizacionLogin;
import icaro.aplicaciones.recursos.visualizacionLogin.imp.usuario.UsoAgenteLogin;

public class PanelLogin extends Thread {

	// Variables
	private Composite cTitulo;
	private CLabel lTitulo;
	private CLabel lPassword;
	private Composite cAcepCanc;
	private Button bCancelar;
	private Button bAceptar;
	private Text tPassword;
	private Text tUsuario;
	private CLabel lUsuario;
	private Composite cDatos;
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Login" por el nombre del agente.
	 */
	final UsoAgenteLogin usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private PanelLogin este;

	/**
	 * 
	 * @param visualizador
	 */
	public PanelLogin(ClaseGeneradoraVisualizacionLogin visualizador){
		super("Login");
		este = this;
		
		usoAgente = new UsoAgenteLogin(visualizador);
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

	public UsoAgenteLogin getAgente(){
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
		shell.setLayout(shellLayout);
		shell.setText("Bienvenido a Doctoris");
		shell.setSize(400, 335);
		{
			cTitulo = new Composite(shell, SWT.BORDER);
			GridLayout cTituloLayout = new GridLayout();
			cTituloLayout.makeColumnsEqualWidth = true;
			GridData cTituloLData = new GridData();
			cTituloLData.grabExcessHorizontalSpace = true;
			cTituloLData.horizontalAlignment = GridData.FILL;
			cTituloLData.verticalAlignment = GridData.BEGINNING;
			cTituloLData.heightHint = 160;
			cTitulo.setLayoutData(cTituloLData);
			cTitulo.setLayout(cTituloLayout);
			cTitulo.setSize(374, 160);
			cTitulo.setBackground(SWTResourceManager.getColor(255, 128, 0));
			{
				lTitulo = new CLabel(cTitulo, SWT.NONE);
				lTitulo.setText("DOCTORIS");
				lTitulo.setBackground(SWTResourceManager.getColor(255, 128, 0));
				GridData lTituloLData = new GridData();
				lTituloLData.horizontalAlignment = GridData.CENTER;
				lTituloLData.grabExcessHorizontalSpace = true;
				lTituloLData.grabExcessVerticalSpace = true;
				lTitulo.setLayoutData(lTituloLData);
				lTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 36, 3, false, false));
			}
		}
		{
			cDatos = new Composite(shell, SWT.NONE);
			GridLayout cDatosLayout = new GridLayout();
			cDatosLayout.numColumns = 2;
			cDatosLayout.marginTop = 20;
			GridData cDatosLData = new GridData();
			cDatosLData.grabExcessHorizontalSpace = true;
			cDatosLData.grabExcessVerticalSpace = true;
			cDatosLData.horizontalAlignment = GridData.FILL;
			cDatosLData.verticalAlignment = GridData.FILL;
			cDatos.setLayoutData(cDatosLData);
			cDatos.setLayout(cDatosLayout);
			{
				lUsuario = new CLabel(cDatos, SWT.NONE);
				lUsuario.setText("Nombre de usuario:");
			}
			{
				GridData tUsuarioLData = new GridData();
				tUsuarioLData.grabExcessHorizontalSpace = true;
				tUsuarioLData.horizontalAlignment = GridData.FILL;
				tUsuario = new Text(cDatos, SWT.BORDER);
				tUsuario.setLayoutData(tUsuarioLData);
			}
			{
				lPassword = new CLabel(cDatos, SWT.NONE);
				GridData tPasswordLData = new GridData();
				tPasswordLData.horizontalAlignment = GridData.FILL;
				lPassword.setLayoutData(tPasswordLData);
				lPassword.setText("Contraseña:");
			}
			{
				GridData tPasswordLData = new GridData();
				tPasswordLData.horizontalAlignment = GridData.FILL;
				tPassword = new Text(cDatos, SWT.BORDER);
				tPassword.setLayoutData(tPasswordLData);
			}
		}
		{
			cAcepCanc = new Composite(shell, SWT.NONE);
			GridLayout cAcepCancLayout = new GridLayout();
			cAcepCancLayout.makeColumnsEqualWidth = true;
			cAcepCancLayout.numColumns = 2;
			GridData cAcepCancLData = new GridData();
			cAcepCancLData.horizontalAlignment = GridData.FILL;
			cAcepCancLData.verticalAlignment = GridData.FILL;
			cAcepCanc.setLayoutData(cAcepCancLData);
			cAcepCanc.setLayout(cAcepCancLayout);
			{
				bAceptar = new Button(cAcepCanc, SWT.PUSH | SWT.CENTER);
				GridData bAceptarLData = new GridData();
				bAceptarLData.horizontalAlignment = GridData.FILL;
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
				bCancelar = new Button(cAcepCanc, SWT.PUSH | SWT.CENTER);
				GridData bCancelarLData = new GridData();
				bCancelarLData.grabExcessHorizontalSpace = true;
				bCancelarLData.horizontalAlignment = GridData.FILL;
				bCancelar.setLayoutData(bCancelarLData);
				bCancelar.setText("Cancelar");
				bCancelar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						bCancelarWidgetSelected(evt);
					}
				});
			}
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
		usoAgente.validaUsuario(tUsuario.getText(), tPassword.getText());
	}
	
	private void bCancelarWidgetSelected(SelectionEvent evt) {
		System.out.println("bCancelar.widgetSelected, event="+evt);
	}
	
}