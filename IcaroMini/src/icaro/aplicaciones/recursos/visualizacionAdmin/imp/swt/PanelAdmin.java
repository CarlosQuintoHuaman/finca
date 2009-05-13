package icaro.aplicaciones.recursos.visualizacionAdmin.imp.swt;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin.InfoUsuario;
import icaro.aplicaciones.recursos.visualizacionAdmin.imp.ClaseGeneradoraVisualizacionAdmin;
import icaro.aplicaciones.recursos.visualizacionAdmin.imp.usuario.UsoAgenteAdmin;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelAdmin extends Thread {

	// Variables
	private CTabFolder cTabAdmin;
	private Composite cUsuarios;
	private Button bOptimizar;
	private Button bResetear;
	private Button bCrear;
	private CLabel lMant;
	private CLabel lEstadoBD;
	private CLabel lEstado;
	private Composite cBase;
	private Button bEditar;
	private Button bBaja;
	private Button bAlta;
	private CLabel lAcciones;
	private Composite cAcciones;
	private List lUsuarios;
	private CLabel lListaUsuarios;
	private CTabItem cTabBase;
	private CTabItem cTabItem1;
	private Button bCancelar;
	private Button bAceptar;
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Admin" por el nombre del agente.
	 */
	final UsoAgenteAdmin usoAgente;
	
	ArrayList<InfoUsuario> usuarios = new ArrayList<InfoUsuario>();
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	/**
	 * 
	 * @param visualizador
	 */
	public PanelAdmin(ClaseGeneradoraVisualizacionAdmin visualizador){
		super("Admin");
		usoAgente = new UsoAgenteAdmin(visualizador);
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
		usuarios = usoAgente.getUsuarios();
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	actualizarLista();
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

	public UsoAgenteAdmin getAgente(){
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
		shell.setText("Insertar un Medicamento");
		shell.setSize(800, 600);
		{
			cTabAdmin = new CTabFolder(shell, SWT.NONE);
			{
				cTabItem1 = new CTabItem(cTabAdmin, SWT.NONE);
				cTabItem1.setText("Gestion de usuarios");
				{
					cUsuarios = new Composite(cTabAdmin, SWT.NONE);
					GridLayout cUsuariosLayout = new GridLayout();
					cUsuariosLayout.makeColumnsEqualWidth = true;
					cUsuariosLayout.numColumns = 2;
					cUsuarios.setLayout(cUsuariosLayout);
					cTabItem1.setControl(cUsuarios);
					{
						lListaUsuarios = new CLabel(cUsuarios, SWT.NONE);
						lListaUsuarios.setText("LISTA DE USUARIOS");
						GridData lListaUsuariosLData = new GridData();
						lListaUsuariosLData.horizontalSpan = 2;
						lListaUsuarios.setLayoutData(lListaUsuariosLData);
						lListaUsuarios.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					}
					{
						GridData lUsuariosLData = new GridData();
						lUsuariosLData.grabExcessHorizontalSpace = true;
						lUsuariosLData.verticalAlignment = GridData.FILL;
						lUsuariosLData.grabExcessVerticalSpace = true;
						lUsuariosLData.horizontalAlignment = GridData.FILL;
						lUsuarios = new List(cUsuarios, SWT.V_SCROLL | SWT.BORDER);
						lUsuarios.setLayoutData(lUsuariosLData);
						actualizarLista();
					}
					{
						cAcciones = new Composite(cUsuarios, SWT.NONE);
						GridLayout cAccionesLayout = new GridLayout();
						cAccionesLayout.makeColumnsEqualWidth = true;
						GridData cAccionesLData = new GridData();
						cAccionesLData.grabExcessHorizontalSpace = true;
						cAccionesLData.grabExcessVerticalSpace = true;
						cAccionesLData.horizontalAlignment = GridData.FILL;
						cAccionesLData.verticalAlignment = GridData.FILL;
						cAcciones.setLayoutData(cAccionesLData);
						cAcciones.setLayout(cAccionesLayout);
						{
							lAcciones = new CLabel(cAcciones, SWT.NONE);
							lAcciones.setText("ACCIONES");
							lAcciones.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
						}
						{
							bAlta = new Button(cAcciones, SWT.PUSH | SWT.CENTER);
							bAlta.setText("Dar de alta nuevo usuario");
							bAlta.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("bAlta.widgetSelected, event="+evt);
									//TODO add your code for bAlta.widgetSelected
								}
							});
						}
						{
							bBaja = new Button(cAcciones, SWT.PUSH | SWT.CENTER);
							bBaja.setText("Dar de baja usuario");
							bBaja.setSize(146, 25);
							bBaja.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("bBaja.widgetSelected, event="+evt);
									//TODO add your code for bBaja.widgetSelected
								}
							});
						}
						{
							bEditar = new Button(cAcciones, SWT.PUSH | SWT.CENTER);
							bEditar.setText("Editar datos del usuario");
							bEditar.setSize(146, 25);
							bEditar.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									System.out.println("bEditar.widgetSelected, event="+evt);
									//TODO add your code for bEditar.widgetSelected
								}
							});
						}
					}
				}
			}
			{
				cTabBase = new CTabItem(cTabAdmin, SWT.NONE);
				cTabBase.setText("Gestion de la BD");
				{
					cBase = new Composite(cTabAdmin, SWT.NONE);
					GridLayout cBaseLayout = new GridLayout();
					cBaseLayout.makeColumnsEqualWidth = true;
					cBaseLayout.numColumns = 2;
					cBase.setLayout(cBaseLayout);
					cTabBase.setControl(cBase);
					{
						lEstado = new CLabel(cBase, SWT.NONE);
						lEstado.setText("ESTADO DE LA BASE DE DATOS:");
						lEstado.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					}
					{
						lEstadoBD = new CLabel(cBase, SWT.NONE);
						lEstadoBD.setText("Conectado");
					}
					{
						lMant = new CLabel(cBase, SWT.NONE);
						lMant.setText("MANTENIMIENTO DE LA BASE DE DATOS");
						GridData lMantLData = new GridData();
						lMantLData.horizontalSpan = 2;
						lMant.setLayoutData(lMantLData);
						lMant.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
					}
					{
						bCrear = new Button(cBase, SWT.PUSH | SWT.CENTER);
						GridData bCrearLData = new GridData();
						bCrearLData.horizontalSpan = 2;
						bCrearLData.widthHint = 146;
						bCrearLData.heightHint = 25;
						bCrear.setLayoutData(bCrearLData);
						bCrear.setText("Crear BD");
						bCrear.setSize(146, 25);
						bCrear.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.crearBD();
							}
						});
					}
					{
						bResetear = new Button(cBase, SWT.PUSH | SWT.CENTER);
						GridData bResetearLData = new GridData();
						bResetearLData.horizontalSpan = 2;
						bResetearLData.widthHint = 146;
						bResetearLData.heightHint = 25;
						bResetear.setLayoutData(bResetearLData);
						bResetear.setText("Resetear");
						bResetear.setSize(146, 25);
						bResetear.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.resetearBD();
							}
						});
					}
					{
						bOptimizar = new Button(cBase, SWT.PUSH | SWT.CENTER);
						GridData bOptimizarLData = new GridData();
						bOptimizarLData.horizontalSpan = 2;
						bOptimizarLData.widthHint = 146;
						bOptimizarLData.heightHint = 25;
						bOptimizar.setLayoutData(bOptimizarLData);
						bOptimizar.setText("Optimizar");
						bOptimizar.setSize(146, 25);
						bOptimizar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.optimizarBD();
							}
						});
					}
				}
			}
			GridData cTabAdminLData = new GridData();
			cTabAdminLData.grabExcessHorizontalSpace = true;
			cTabAdminLData.grabExcessVerticalSpace = true;
			cTabAdminLData.horizontalAlignment = GridData.FILL;
			cTabAdminLData.verticalAlignment = GridData.FILL;
			cTabAdminLData.horizontalSpan = 2;
			cTabAdmin.setLayoutData(cTabAdminLData);
			cTabAdmin.setSelection(0);
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


		// Capturo el cierre de la ventana con el boton X
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent arg0) {
				// Es posible que haya que cambiar el nombre de este metodo
				usoAgente.cerrarVentanaAdmin();
			}
			
		});

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
	
	private void actualizarLista() {
		for (int i=0; i<usuarios.size(); i++) {
			lUsuarios.add(usuarios.get(i).getUsuario());
		}
	}
}