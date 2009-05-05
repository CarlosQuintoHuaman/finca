package icaro.aplicaciones.recursos.visualizacionHistorial.imp.swt;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.ClaseGeneradoraVisualizacionHistorial;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario.UsoAgenteHistorial;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelLista extends Thread {

	// Variables
	private Composite listado;
	private Button bCerrar;
	private CLabel lMotivoTitulo;
	private CLabel lFechaTitulo;
	private CLabel lTitulo;
	
	private ArrayList<CLabel[]> listaLabels = new ArrayList<CLabel[]>();
	
	private ArrayList<String[]> lista = new ArrayList<String[]>();
	private ArrayList<InfoVisita> v = null;
	
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
	public PanelLista(ClaseGeneradoraVisualizacionHistorial visualizador){
		super("Lista Historial");
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
		shell.setLayout(shellLayout);
		shell.setText("Lista de Visitas del paciente Y");
		shell.setSize(400, 500);
		{
			listado = new Composite(shell, SWT.NONE);
			GridLayout listadoLayout = new GridLayout();
			listadoLayout.numColumns = 2;
			GridData listadoLData = new GridData();
			listadoLData.grabExcessHorizontalSpace = true;
			listadoLData.grabExcessVerticalSpace = true;
			listadoLData.horizontalAlignment = GridData.FILL;
			listadoLData.verticalAlignment = GridData.FILL;
			listado.setLayoutData(listadoLData);
			listado.setLayout(listadoLayout);
			{
				lTitulo = new CLabel(listado, SWT.NONE);
				GridData lTituloLData = new GridData();
				lTituloLData.horizontalAlignment = GridData.FILL;
				lTituloLData.grabExcessHorizontalSpace = true;
				lTituloLData.horizontalSpan = 2;
				lTitulo.setLayoutData(lTituloLData);
				lTitulo.setText("HISTORIAL DE ");
				lTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 14, 1, false, false));
				lTitulo.setAlignment(SWT.CENTER);
			}
			{
				lFechaTitulo = new CLabel(listado, SWT.SHADOW_OUT | SWT.BORDER);
				lFechaTitulo.setText("FECHA");
				GridData lFechaTituloLData = new GridData();
				lFechaTituloLData.horizontalAlignment = GridData.FILL;
				lFechaTitulo.setLayoutData(lFechaTituloLData);
				lFechaTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				lFechaTitulo.setAlignment(SWT.CENTER);
			}
			{
				lMotivoTitulo = new CLabel(listado, SWT.SHADOW_OUT | SWT.BORDER);
				lMotivoTitulo.setText("MOTIVO DE LA CONSULTA");
				GridData lMotivoTituloLData = new GridData();
				lMotivoTituloLData.horizontalAlignment = GridData.FILL;
				lMotivoTitulo.setLayoutData(lMotivoTituloLData);
				lMotivoTitulo.setFont(SWTResourceManager.getFont("Segoe UI", 9, 1, false, false));
				lMotivoTitulo.setAlignment(SWT.CENTER);
			}

			rellenaLista();
		}

		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent arg0) {
				usoAgente.cerrarVentanaLista();
			}
			
		});
		
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	
	public void rellenaLista() {
		disp.asyncExec(new Runnable() {
            public void run() {
				for(int i=0; i<lista.size(); i++) {
					CLabel t = new CLabel(listado, SWT.NONE);
					t.setText(lista.get(i)[0]);
					GridData tLData = new GridData();
					tLData.horizontalAlignment = GridData.FILL;
					tLData.verticalAlignment = GridData.BEGINNING;
					tLData.grabExcessVerticalSpace = false;
					t.setLayoutData(tLData);
					t.setBackground(SWTResourceManager.getColor(255, 255, 255));
					
					t.addMouseListener(new MouseAdapter() {
						public void mouseDoubleClick(MouseEvent evt) {
							lFechaMouseDoubleClick(evt);
						}
					});
					
					CLabel t1 = new CLabel(listado, SWT.NONE);
					t1.setText(lista.get(i)[1]);
					GridData t1LData = new GridData();
					t1LData.horizontalAlignment = GridData.FILL;
					t1LData.verticalAlignment = GridData.BEGINNING;
					
					if (i == lista.size()-1)
						t1LData.grabExcessVerticalSpace = true;
					
					t1.setLayoutData(t1LData);
					t1.setBackground(SWTResourceManager.getColor(255, 255, 255));
					
					CLabel tupla[] = new CLabel[2];
					tupla[0] = t;
					tupla[1] = t1;
					
					listaLabels.add(tupla);
				}
				
				if (lista.size() > 0) {
					{
						bCerrar = new Button(listado, SWT.PUSH | SWT.CENTER);
						GridData bCerrarLData = new GridData();
						bCerrarLData.verticalAlignment = GridData.END;
						bCerrarLData.horizontalSpan = 2;
						bCerrarLData.horizontalAlignment = GridData.CENTER;
						bCerrar.setLayoutData(bCerrarLData);
						bCerrar.setText("Cerrar Ventana");
						bCerrar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								bCerrarWidgetSelected(evt);
							}
						});
					}
				}
				
				listado.layout();
 	       }
          });
	}
	
	private void lFechaMouseDoubleClick(MouseEvent evt) {
		CLabel e = (CLabel)evt.getSource();
		
		int i;
		for (i=0; i<listaLabels.size(); i++)
			if (listaLabels.get(i)[0] == e)
				break;
		
		Date f = v.get(i%2).getFecha();
		
		usoAgente.mostrarVentanaHistorial(lTitulo.getText().substring(13), f);
	}
	
	public void mostrarDatos(ArrayList<InfoVisita> v) {
		String usuario = "";
		
		this.v = v;
		
		for (int i=0; i<v.size(); i++) {
			InfoVisita temp = v.get(i);
			usuario = temp.getUsuario();
			String t[] = new String[2];
			t[0] = temp.getFecha().toLocaleString();
			t[1] = temp.getMotivo();
			lista.add(t);
		}
		
		final String u = usuario;
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	lTitulo.setText(lTitulo.getText() + u);
            }
		});
		
		rellenaLista();
	}
	
	private void bCerrarWidgetSelected(SelectionEvent evt) {
		usoAgente.cerrarVentanaLista();
	}

}