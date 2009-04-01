package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;

public class panelProximaCita extends Thread {

	// Variables
	private Composite principal;
	private CLabel cNombre;
	private CLabel cTelefono;
	private Table table1;
	private TableColumn tableColumn1;
	private Composite tabla;
	private Button bCancelar;
	private TableColumn tableColumn3;
	private TableColumn tableColumn2;
	private Button bBuscar;
	private Text tTelefono;
	private Text tnombre;
	private CLabel telefono;
	private CLabel Nombre;
	private CLabel Horas;
	
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteSecretaria usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private panelProximaCita este;

	/**
	 * 
	 * @param visualizador
	 */
	public panelProximaCita(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("ProximaCita");
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
	
	public void meteDatos(ArrayList<DatosCita> l){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.syncExec(new Runnable() {
            public void run() {
         	   //general los labels correspondientes de hora, medico y dia para las citas
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
			GridLayout shellLayout = new GridLayout();
			shell.setLayout(shellLayout);
			shell.setSize(315, 282);
			shell.setText("Consultar Citas");
					{
        		//Register as a resource user - SWTResourceManager will
        		//handle the obtaining and disposing of resources
        		SWTResourceManager.registerResourceUser(shell);
        	}
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	usoAgente.cerrarVentanaProximasCitas();
			    }
			});
			{
				principal = new Composite(shell, SWT.NONE);
				GridLayout principalLayout = new GridLayout();
				principalLayout.numColumns = 2;
				GridData principalLData = new GridData();
				principalLData.horizontalAlignment = GridData.FILL;
				principalLData.verticalAlignment = GridData.FILL;
				principalLData.grabExcessVerticalSpace = true;
				principalLData.grabExcessHorizontalSpace = true;
				principal.setLayoutData(principalLData);
				principal.setLayout(principalLayout);
				{
					cNombre = new CLabel(principal, SWT.NONE);
					GridData cNombreLData = new GridData();
					cNombreLData.heightHint = 19;
					cNombreLData.verticalAlignment = GridData.BEGINNING;
					cNombreLData.verticalSpan = 0;
					cNombre.setLayoutData(cNombreLData);
					cNombre.setText("Nombre");
				}
				{
					GridData tnombreLData = new GridData();
					tnombreLData.horizontalAlignment = GridData.FILL;
					tnombreLData.heightHint = 15;
					tnombreLData.grabExcessVerticalSpace = true;
					tnombreLData.verticalAlignment = GridData.BEGINNING;
					tnombreLData.verticalSpan = 0;
					tnombreLData.widthHint = 196;
					tnombre = new Text(principal, SWT.BORDER);
					tnombre.setLayoutData(tnombreLData);
				}
				{
					cTelefono = new CLabel(principal, SWT.NONE);
					GridData cTelefonoLData = new GridData();
					cTelefonoLData.grabExcessVerticalSpace = true;
					cTelefonoLData.verticalAlignment = GridData.BEGINNING;
					cTelefono.setLayoutData(cTelefonoLData);
					cTelefono.setText("Telefono");
				}
				{
					GridData tTelefonoLData = new GridData();
					tTelefonoLData.grabExcessVerticalSpace = true;
					tTelefonoLData.verticalAlignment = GridData.BEGINNING;
					tTelefonoLData.heightHint = 15;
					tTelefonoLData.horizontalAlignment = GridData.FILL;
					tTelefonoLData.grabExcessHorizontalSpace = true;
					tTelefono = new Text(principal, SWT.BORDER);
					tTelefono.setLayoutData(tTelefonoLData);
				}
				{
					bBuscar = new Button(principal, SWT.PUSH | SWT.CENTER);
					GridData bBuscarLData = new GridData();
					bBuscarLData.widthHint = 70;
					bBuscarLData.heightHint = 23;
					bBuscarLData.verticalAlignment = GridData.BEGINNING;
					bBuscarLData.horizontalAlignment = GridData.CENTER;
					bBuscarLData.grabExcessVerticalSpace = true;
					bBuscarLData.grabExcessHorizontalSpace = false;
					bBuscar.setLayoutData(bBuscarLData);
					bBuscar.setText("Buscar");
					bBuscar.addSelectionListener (new SelectionAdapter () {
						public void widgetSelected (SelectionEvent evt) {
							usoAgente.BuscarCitas(tnombre.getText(),tTelefono.getText());
							
						}                               
					});
				}
				{
					bCancelar = new Button(principal, SWT.PUSH | SWT.CENTER);
					GridData bCancelarLData = new GridData();
					bCancelarLData.verticalAlignment = GridData.BEGINNING;
					bCancelarLData.widthHint = 70;
					bCancelarLData.heightHint = 23;
					bCancelar.setLayoutData(bCancelarLData);
					bCancelar.setText("Cancelar");
				}
				{
					tabla = new Composite(principal, SWT.BORDER);
					GridLayout tablaLayout = new GridLayout();
					tablaLayout.numColumns = 3;
					GridData tablaLData1 = new GridData();
					tablaLData1.horizontalAlignment = GridData.FILL;
					tablaLData1.horizontalSpan = 2;
					tablaLData1.verticalAlignment = GridData.FILL;
					tablaLData1.grabExcessVerticalSpace = true;
					tablaLData1.grabExcessHorizontalSpace = true;
					GridData tablaLData = new GridData();
					tablaLData.grabExcessVerticalSpace = true;
					tabla.setLayoutData(tablaLData);
					tablaLData.widthHint = 277;
					tablaLData.heightHint = 111;
					tablaLData.horizontalSpan = 2;
					tabla.setLayout(tablaLayout);
				}
				{
					Nombre = new CLabel(tabla, SWT.NONE);
					Nombre.setText("FECHA");
					Nombre.setBackground(SWTResourceManager.getColor(220, 189, 224));
					Nombre.setForeground(SWTResourceManager.getColor(255, 255, 255));
					GridData NombreLData = new GridData();
					NombreLData.widthHint = 84;
					NombreLData.heightHint = 20;
					NombreLData.grabExcessHorizontalSpace = true;
					Nombre.setLayoutData(NombreLData);
					Nombre.setFont(SWTResourceManager.getFont("Palatino Linotype", 8, 1, false, false));
					Nombre.setAlignment(SWT.CENTER);
				}
				{
					Horas = new CLabel(tabla, SWT.NONE);
					GridData HorasLData = new GridData();
					HorasLData.widthHint = 84;
					HorasLData.heightHint = 19;
					Horas.setLayoutData(HorasLData);
					Horas.setText("HORA");
					Horas.setBackground(SWTResourceManager.getColor(220, 189, 224));
					Horas.setForeground(SWTResourceManager.getColor(255, 255, 255));
					Horas.setFont(SWTResourceManager.getFont("Palatino Linotype", 8, 1, false, false));
					Horas.setAlignment(SWT.CENTER);
				}
				{
					telefono = new CLabel(tabla, SWT.NONE);
					telefono.setText("MEDICO");
					telefono.setBackground(SWTResourceManager.getColor(220, 189, 224));
					telefono.setForeground(SWTResourceManager.getColor(255, 255, 255));
					telefono.setFont(SWTResourceManager.getFont("Palatino Linotype", 8, 1, false, false));
					GridData telefonoLData = new GridData();
					telefonoLData.heightHint = 20;
					telefonoLData.widthHint = 85;
					telefono.setLayoutData(telefonoLData);
					telefono.setAlignment(SWT.CENTER);
				}
			}
			
			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(shell);
			}

			shell.layout();
			
			while (!shell.isDisposed()) {
				if (!disp.readAndDispatch())
					disp.sleep();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}