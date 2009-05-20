package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario.UsoAgenteMedico;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;

import java.util.ArrayList;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;


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
public class panelMensajeria {
	private Composite cMensajes;
	private TableColumn colMensaje;
	private TableColumn colOrigen;
	private TableColumn colAsunto;
	private TableColumn colFecha;
	private ArrayList<TableItem> filasTabla = new ArrayList<TableItem>();
	private Table tablaMens;
	private List listadoPacientes;
	private List listaBusPaciente;
	private Text tBusApellido;
	private Label lBusApellido;
	private Text tBusNombre;
	private CLabel lBusNombre;
	private Composite cBuscar;
	private CTabItem cTabBuscar;
	private Composite izquierda;
	private CTabItem cTabMed;
	private CTabItem mensajeria;
	private Button bEnviarMensaje;
	private Button bActMensajes;
	private String usuario;
	private ArrayList<InfoMensaje> mensajes = new ArrayList<InfoMensaje>();
	
	private Display disp;
	private Shell shell;
	private panelMensajeria este;
	private ClaseGeneradoraVisualizacionSecretaria vis;
	
	final UsoAgenteSecretaria usoAgente;


	public panelMensajeria(ClaseGeneradoraVisualizacionSecretaria visualizador){
		
		este = this;
		vis =visualizador;
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
	private void initGUI() {
		try {

			shell = new Shell(disp);
			GridLayout shellLayout = new GridLayout();
			shellLayout.makeColumnsEqualWidth = true;
			shell.setLayout(shellLayout);
			shell.setSize(597, 395);
			shell.setText("Mensajeria");
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	usoAgente.cerrarVentanaMensajeria();
			    }
			});
			{
				cMensajes = new Composite(shell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.makeColumnsEqualWidth = true;
				cMensajes.setLayout(composite2Layout);
			}
			{
				tablaMens = new Table(cMensajes, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.BORDER);
				tablaMens.setHeaderVisible(true);
				tablaMens.setItemCount(0);
				tablaMens.setLinesVisible(true);
				GridData tablaMensLData = new GridData();
				tablaMensLData.verticalAlignment = GridData.FILL;
				tablaMensLData.grabExcessHorizontalSpace = true;
				tablaMensLData.grabExcessVerticalSpace = true;
				tablaMensLData.horizontalAlignment = GridData.FILL;
				tablaMensLData.horizontalSpan = 2;
				tablaMens.setLayoutData(tablaMensLData);
				
				{
					colFecha = new TableColumn(tablaMens, SWT.LEFT);
					colFecha.setText("Fecha");
					colFecha.setWidth(100);
				}
				{
					colAsunto = new TableColumn(tablaMens, SWT.LEFT);
					colAsunto.setText("Asunto");
					colAsunto.setWidth(100);
				}
				{
					colOrigen = new TableColumn(tablaMens, SWT.LEFT);
					colOrigen.setText("Remitente");
					colOrigen.setWidth(100);
				}						
				{
					colMensaje = new TableColumn(tablaMens, SWT.NONE);
					colMensaje.setText("Mensaje");
					colMensaje.setWidth(190);
				}

				actualizarMensajes();
				{
					bEnviarMensaje = new Button(cMensajes, SWT.PUSH | SWT.CENTER);
					GridData bEnviarMensajeLData = new GridData();
					bEnviarMensajeLData.verticalAlignment = GridData.BEGINNING;
					bEnviarMensajeLData.horizontalAlignment = GridData.FILL;
					bEnviarMensaje.setLayoutData(bEnviarMensajeLData);
					bEnviarMensaje.setText("Enviar un nuevo mensaje");
					bEnviarMensaje.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							usoAgente.enviarMensaje(usuario);
						}
					});
					
					bActMensajes = new Button(cMensajes, SWT.PUSH | SWT.CENTER);
					GridData bActMensajesLData = new GridData();
					bActMensajesLData.verticalAlignment = GridData.BEGINNING;
					bActMensajesLData.horizontalAlignment = GridData.FILL;
					bActMensajes.setLayoutData(bActMensajesLData);
					bActMensajes.setText("Comprobar si hay nuevos mensajes");
					bActMensajes.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							usoAgente.cargarMensajes(usuario);
						}
					});
					
				}
			}

		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}
		shell.layout();

		
		
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	
	private void actualizarMensajes() {
		for (int i=0; i<filasTabla.size(); i++)
			filasTabla.get(i).dispose();
		
		filasTabla.clear();
		
		for (int i=0; i<mensajes.size(); i++) {
			//TableItem t = mensajes.get(i);
			TableItem t = new TableItem(tablaMens, 0);
			filasTabla.add(t);
			
			InfoMensaje m = mensajes.get(i);
			t.setText(new String[]{m.getFecha().toString(), m.getAsunto(), m.getRemitente(), m.getContenido()});
		}
	}
}
