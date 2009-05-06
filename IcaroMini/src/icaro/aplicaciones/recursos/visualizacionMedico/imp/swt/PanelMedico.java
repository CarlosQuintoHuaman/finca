package icaro.aplicaciones.recursos.visualizacionMedico.imp.swt;

import java.util.Date;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.swt.PanelMedicamentos;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.ClaseGeneradoraVisualizacionMedico;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario.UsoAgenteMedico;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class PanelMedico extends Thread {

	// Variables
	private Menu menu1;
	private Button bEnviarMensaje;
	private Button bActMensajes;
	private Composite cMensajes;
	private TableColumn colMensaje;
	private TableColumn colOrigen;
	private TableColumn colAsunto;
	private TableColumn colFecha;
	// Para guardar dinamicamente los objetos de cada fila de la tabla
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
	private Button opcion1;
	private Button opcion2;
	private Button opcion3;
	private Button opcion4;
	private Button opcion5;
	private Button opcion6;
	private Button bActualizar;
	private Composite composite1;
	private CTabItem cTabItem2;
	private CTabFolder panelContenido;
	private CTabItem cTabItem1;
	private CTabFolder barraLateral;
	private MenuItem aboutMenuItem;
	private MenuItem contentsMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;
	
	private Composite cMedicamentos;

	protected Date fecha;
	private ArrayList<InfoPaciente> pacientes;
	private ArrayList<InfoPaciente> filtro = new ArrayList<InfoPaciente>();
	private ArrayList<InfoCita> citas;
	private ArrayList<InfoMensaje> mensajes = new ArrayList<InfoMensaje>();
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Medico" por el nombre del agente.
	 */
	final UsoAgenteMedico usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	PanelVisita v;
	

	private ClaseGeneradoraVisualizacionMedico visualizador;
	private String usuario;
	private Date fechaActual;
	
	/**
	 * 
	 * @param visualizador
	 */
	public PanelMedico(ClaseGeneradoraVisualizacionMedico visualizador){
		super("Medico");
		this.visualizador = visualizador;
		usoAgente = new UsoAgenteMedico(visualizador);
		v = new PanelVisita(visualizador);
		v.start();
		 
		 
	}

	public void run(){
		// Crear el display y generar la ventana pero SIN MOSTRARLA
		// Es decir, no debe haber un shell.open en initGUI()
		disp = new Display();
		
		pacientes = new ArrayList<InfoPaciente>();
		citas = new ArrayList<InfoCita>();
		
		initGUI();
	}

	public void mostrar(final String usuario){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		this.usuario = usuario;
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	pacientes = visualizador.getPacientes(usuario);
            	citas = visualizador.getCitas(usuario);
            	usoAgente.cargarMedicamentos();
            	usoAgente.cargarMensajes(usuario);
            	
            	shell.setText("Bienvenido Dr."+usuario);
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

	public UsoAgenteMedico getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		
		Composite comp = new Composite(shell, SWT.NONE);
		shell.setLayout(new FillLayout());
		shell.setText("Bienvenido Dr. X");
		shell.setSize(800, 600);
		shell.layout();
		
		comp.setSize(800, 600);
		comp.setBackground(SWTResourceManager.getColor(192, 192, 192));
		GridLayout thisLayout = new GridLayout();
		thisLayout.numColumns = 2;
		comp.setLayout(thisLayout);
		{
			izquierda = new Composite(comp, SWT.NONE);
			GridLayout izquierdaLayout = new GridLayout();
			izquierdaLayout.makeColumnsEqualWidth = true;
			GridData izquierdaLData = new GridData();
			izquierdaLData.verticalAlignment = GridData.FILL;
			izquierdaLData.grabExcessVerticalSpace = true;
			izquierdaLData.widthHint = 250;
			izquierda.setLayoutData(izquierdaLData);
			izquierda.setLayout(izquierdaLayout);
			{
				barraLateral = new CTabFolder(izquierda, SWT.BORDER);
				{
					cTabItem1 = new CTabItem(barraLateral, SWT.NONE);
					cTabItem1.setText("Pacientes del dia");
					{
						listadoPacientes = new List(barraLateral, SWT.NONE);
						
						fechaActual = new Date();
						actualizarCitas(fechaActual);
						cTabItem1.setControl(listadoPacientes);
						
						listadoPacientes.addMouseListener(new MouseAdapter() {
							public void mouseDoubleClick(MouseEvent evt) {
								if (listadoPacientes.getSelectionIndex() != -1)
									usoAgente.abrirFicha(usuario);
								else
									usoAgente.mostrarMensajeAviso("Debe seleccionar un paciente de la lista", "Atencion");
							}
						});
					}
				}
				{
					cTabBuscar = new CTabItem(barraLateral, SWT.NONE);
					cTabBuscar.setText("Buscar");
					{
						cBuscar = new Composite(barraLateral, SWT.NONE);
						GridLayout cBuscarLayout = new GridLayout();
						cBuscarLayout.numColumns = 2;
						cBuscar.setLayout(cBuscarLayout);
						cTabBuscar.setControl(cBuscar);
						{
							lBusNombre = new CLabel(cBuscar, SWT.NONE);
							lBusNombre.setText("Nombre:");
						}
						{
							GridData tBusNombreLData = new GridData();
							tBusNombreLData.verticalAlignment = GridData.BEGINNING;
							tBusNombreLData.horizontalAlignment = GridData.FILL;
							tBusNombre = new Text(cBuscar, SWT.BORDER);
							tBusNombre.setLayoutData(tBusNombreLData);
							tBusNombre.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent evt) {
									tBusNombreModifyText(evt);
								}
							});
						}
						{
							lBusApellido = new Label(cBuscar, SWT.NONE);
							GridData lBusApellidoLData = new GridData();
							lBusApellidoLData.verticalAlignment = GridData.BEGINNING;
							lBusApellidoLData.horizontalAlignment = GridData.BEGINNING;
							lBusApellido.setLayoutData(lBusApellidoLData);
							lBusApellido.setText("Apellido:");
						}
						{
							GridData tBusApellidoLData = new GridData();
							tBusApellidoLData.verticalAlignment = GridData.BEGINNING;
							tBusApellidoLData.horizontalAlignment = GridData.FILL;
							tBusApellidoLData.grabExcessHorizontalSpace = true;
							tBusApellido = new Text(cBuscar, SWT.BORDER);
							tBusApellido.setLayoutData(tBusApellidoLData);
							tBusApellido.addModifyListener(new ModifyListener() {
								public void modifyText(ModifyEvent evt) {
									tBusApellidoModifyText(evt);
								}
							});
						}
						{
							GridData listaBusPacienteLData = new GridData();
							listaBusPacienteLData.verticalAlignment = GridData.FILL;
							listaBusPacienteLData.horizontalAlignment = GridData.FILL;
							listaBusPacienteLData.horizontalSpan = 2;
							listaBusPacienteLData.grabExcessVerticalSpace = true;
							listaBusPaciente = new List(cBuscar, SWT.V_SCROLL | SWT.BORDER);
							listaBusPaciente.setLayoutData(listaBusPacienteLData);

							actualizarLista();
							
							listaBusPaciente.addMouseListener(new MouseAdapter() {
								public void mouseDoubleClick(MouseEvent evt) {
									if (listaBusPaciente.getSelectionIndex() != -1)
										usoAgente.abrirFicha(usuario);
									else
										usoAgente.mostrarMensajeAviso("Debe seleccionar un paciente de la lista", "Atencion");
								}
							});
						}
					}
				}
				GridData barraLateralLData = new GridData();
				barraLateralLData.horizontalAlignment = GridData.FILL;
				barraLateralLData.grabExcessHorizontalSpace = true;
				barraLateralLData.grabExcessVerticalSpace = true;
				barraLateralLData.verticalAlignment = GridData.FILL;
				barraLateral.setLayoutData(barraLateralLData);
				barraLateral.setSimple(false);
				//barraLateral.setBackground(new Color(display, 255, 255, 255));
				barraLateral.setSelection(0);
			}
			
			{
				bActualizar = new Button(izquierda, SWT.PUSH | SWT.CENTER);
				GridData bActualizarLData = new GridData();
				bActualizarLData.horizontalAlignment = GridData.FILL;
				bActualizarLData.heightHint = 20;
				bActualizar.setLayoutData(bActualizarLData);
				bActualizar.setText("Actualizar");
				//opcion2.setSize(180, 100);
				bActualizar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						citas = visualizador.getCitas(usuario);
						actualizarCitas(fechaActual);
					}
				});
			}

			// Calendario
			{
				//Introducimos los textos a los botones
				final Composite cAcepCanc = new Composite(izquierda, SWT.BORDER);
				GridData cAcepCancLData = new GridData();
				cAcepCancLData.verticalAlignment = GridData.FILL;
				cAcepCancLData.horizontalAlignment = GridData.FILL;
				cAcepCanc.setLayoutData(cAcepCancLData);
				GridLayout lAcepCanc = new GridLayout();
				lAcepCanc.numColumns = 2;
				cAcepCanc.setLayout(lAcepCanc);

				
				final DateTime calendario = new DateTime (cAcepCanc, SWT.CALENDAR);
				{
					GridData calendarioLData = new GridData();
					calendarioLData.verticalAlignment = GridData.BEGINNING;
					calendarioLData.horizontalAlignment = GridData.BEGINNING;
					calendarioLData.horizontalSpan = 2;
					calendarioLData.grabExcessVerticalSpace = true;
					calendario.setLayoutData(calendarioLData);
					calendario.addMouseListener(new MouseListener () {
						public void mouseDoubleClick(MouseEvent e) {
							// TODO ¿Y esto por qué no va?
									fechaActual = new Date(calendario.getYear()-1900,calendario.getMonth(),calendario.getDay());
									actualizarCitas(fechaActual);
						}
						public void mouseUp(MouseEvent e) {};
						public void mouseDown(MouseEvent e) {};
					});
				}
			}
		}
		{
			panelContenido = new CTabFolder(comp, SWT.BORDER);
			panelContenido.setSimple(false);
			{
				cTabItem2 = new CTabItem(panelContenido, SWT.CLOSE);
				cTabItem2.setText("Principal");
				{
					composite1 = new Composite(panelContenido, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.numColumns = 3;
					composite1Layout.makeColumnsEqualWidth = true;
					composite1.setLayout(composite1Layout);
					cTabItem2.setControl(composite1);
					{
						opcion1 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData opcion1LData = new GridData();
						opcion1LData.grabExcessHorizontalSpace = true;
						opcion1LData.horizontalAlignment = GridData.FILL;
						opcion1LData.grabExcessVerticalSpace = true;
						opcion1LData.heightHint = 100;
						opcion1.setLayoutData(opcion1LData);
						opcion1.setText("ATENDER A UN PACIENTE");
						opcion1.setSize(180, 100);
						opcion1.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								opcion1WidgetSelected(evt);
							}
						});
					}
					{
						opcion2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData Opcion2LData = new GridData();
						Opcion2LData.horizontalAlignment = GridData.FILL;
						Opcion2LData.heightHint = 100;
						opcion2.setLayoutData(Opcion2LData);
						opcion2.setText("CONSULTAR HISTORIAL");
						opcion2.setSize(180, 100);
						opcion2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								opcion2WidgetSelected(evt);
							}
						});
					}
					{
						opcion3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData button1LData = new GridData();
						button1LData.horizontalAlignment = GridData.FILL;
						button1LData.heightHint = 100;
						opcion3.setLayoutData(button1LData);
						opcion3.setText("Opcion3");
						opcion3.setSize(-1, 100);
						opcion3.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								//panelContenido.setSelection(1);
								usoAgente.abrirNuevoMedicamento();
							}
						});
					}
					{
						opcion4 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData Opcion4LData = new GridData();
						Opcion4LData.horizontalAlignment = GridData.FILL;
						Opcion4LData.grabExcessVerticalSpace = true;
						Opcion4LData.grabExcessHorizontalSpace = true;
						Opcion4LData.heightHint = 100;
						opcion4.setLayoutData(Opcion4LData);
						opcion4.setText("INTERCONSULTA");
						opcion4.setSize(200, 100);
					}
					{
						opcion5 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData opcion5LData = new GridData();
						opcion5LData.horizontalAlignment = GridData.FILL;
						opcion5LData.heightHint = 100;
						opcion5.setLayoutData(opcion5LData);
						opcion5.setText("Opcion5");
						opcion5.setSize(200, 100);
					}
					{
						opcion6 = new Button(composite1, SWT.PUSH | SWT.CENTER);
						GridData opcion6LData = new GridData();
						opcion6LData.horizontalAlignment = GridData.FILL;
						opcion6LData.heightHint = 100;
						opcion6.setLayoutData(opcion6LData);
						opcion6.setText("CERRAR SESION");
						opcion6.setSize(-1, 100);
						opcion6.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.cerrarSesion();
							}
						});
					}
				}
			}
			{
				mensajeria = new CTabItem(panelContenido, SWT.CLOSE);
				mensajeria.setText("Mensajeria");
			}
			{
				cMensajes = new Composite(panelContenido, SWT.NONE);
				mensajeria.setControl(cMensajes);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.makeColumnsEqualWidth = true;
				cMensajes.setLayout(composite2Layout);
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
			}
			{
				cTabMed = new CTabItem(panelContenido, SWT.CLOSE);
				cTabMed.setText("Medicamentos");
				{
					//cMedicamentos = new Composite(panelContenido, SWT.NONE);
					cMedicamentos = new PanelMedicamentos(visualizador, panelContenido, SWT.NONE);
					cTabMed.setControl(cMedicamentos);
										
//					disp.syncExec(new Runnable() {
//						public void run() {
//							usoAgente.cargarTabMed((Composite)panelContenido, SWT.NONE);
//						}
//					});					
				}
			}
			GridData cTabFolder1LData = new GridData();
			cTabFolder1LData.verticalAlignment = GridData.FILL;
			cTabFolder1LData.horizontalAlignment = GridData.FILL;
			cTabFolder1LData.grabExcessHorizontalSpace = true;
			panelContenido.setLayoutData(cTabFolder1LData);
			panelContenido.setSelection(0);
		}
		{
			menu1 = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menu1);
			{
				fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
				fileMenuItem.setText("File");
				{
					fileMenu = new Menu(fileMenuItem);
					{
						openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
						openFileMenuItem.setText("Open");
					}
					{
						newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
						newFileMenuItem.setText("New");
					}
					{
						saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
						saveFileMenuItem.setText("Save");
					}
					{
						closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
						closeFileMenuItem.setText("Close");
					}
					{
						exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
						exitMenuItem.setText("Exit");
					}
					fileMenuItem.setMenu(fileMenu);
				}
			}
			{
				helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
				helpMenuItem.setText("Help");
				{
					helpMenu = new Menu(helpMenuItem);
					{
						contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
						contentsMenuItem.setText("Contents");
					}
					{
						aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
						aboutMenuItem.setText("About");
					}
					helpMenuItem.setMenu(helpMenu);
				}
			}
		}
					
		comp.layout();
	
		// Ahora va el codigo de la ventana.
		// ¡Ojo! Las variables de SWT deberian ser globales
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
	}


	// Aqui iran los metodos especificos de cada ventana
	
	private void opcion1WidgetSelected(SelectionEvent evt) {
		if (listadoPacientes.getSelectionIndex() == -1) {
			usoAgente.mostrarMensajeError("Debe seleccionar un paciente de la lista", "Paciente no seleccionado");
		} else {
			String hora = listadoPacientes.getSelection()[0].substring(0,5) + ":00";
			String dia = fechaActual.getYear() + 1900 + "-" + (fechaActual.getMonth() + 1) + "-" + fechaActual.getDate();
			
			InfoCita c = null;
			
			for (int i=0; i<citas.size(); i++) {
				c = citas.get(i);
				
				Date fecha = c.getFecha();
				String d = fecha.getYear() + 1900 + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
				String h = c.getHora().toString();
				
				if (d.equals(dia) && h.equals(hora))
					break;
			}
			
			usoAgente.abrirVisita(c);
		}
	}
	
	private void opcion2WidgetSelected(SelectionEvent evt) {
		if (barraLateral.getSelectionIndex() == 0) {
			if (listadoPacientes.getSelectionIndex() == -1) {
				usoAgente.mostrarMensajeError("Debe seleccionar un paciente de la lista", "Paciente no seleccionado");
			} else {
				String paciente = listadoPacientes.getSelection()[0].substring(6);
				
				int j;
				
				for (j=0; j<pacientes.size(); j++) {
					InfoPaciente p = pacientes.get(j);
					if (paciente.equals(p.getNombre() + " " + p.getApellido1() + " " + p.getApellido2()))
						break;
				}
				
				usoAgente.abrirHistorial(pacientes.get(j).getUsuario());
			}
		} else {
			if (listaBusPaciente.getSelectionIndex() == -1) {
				usoAgente.mostrarMensajeError("Debe seleccionar un paciente de la lista", "Paciente no seleccionado");
			} else {
				usoAgente.abrirHistorial(filtro.get(listaBusPaciente.getSelectionIndex()).getUsuario());
			}
		}
	}
	
	private void tBusNombreModifyText(ModifyEvent evt) {
		actualizarLista();
	}
	
	private void tBusApellidoModifyText(ModifyEvent evt) {
		actualizarLista();
	}
	
	private void actualizarLista() {
		listaBusPaciente.removeAll();
		filtro = new ArrayList<InfoPaciente>();
		
		for (int i=0; i<pacientes.size(); i++) {
			InfoPaciente p = pacientes.get(i);
			
			String nombre = p.getNombre().toLowerCase();
			String apellidos = p.getApellido1() + " " + p.getApellido2();
			
			String filtroNombre = tBusNombre.getText().toLowerCase();
			String filtroApellido = tBusApellido.getText().toLowerCase();
			if (nombre.contains(filtroNombre) && apellidos.contains(filtroApellido)) {
				listaBusPaciente.add(p.getNombre() + " " + p.getApellido1() + " " + p.getApellido2());
				filtro.add(p);
			}
		}
			
	}
	
	private void actualizarCitas(Date f) {
		//citas = visualizador.getCitas();
		if (f == null)
			f = fechaActual;
		
		listadoPacientes.removeAll();
		String fe = fechaActual.getDate() + "-" + (fechaActual.getMonth()+1) + "-" + (fechaActual.getYear()+1900);
		listadoPacientes.add("Pacientes del dia " + fe);
		
		for (int i=0; i<citas.size(); i++) {
			InfoCita t = citas.get(i);
			
			if (t.getFecha().getDate() != f.getDate()
					|| t.getFecha().getMonth() != f.getMonth())
				continue;
			
			String horas = String.valueOf(t.getHora().getHours());
			if (horas.length() == 1)
				horas = "0"+horas;
			
			String minutos = String.valueOf(t.getHora().getMinutes());
			if (minutos.length() == 1)
				minutos = "0"+minutos;
			
			int j;
			
			for (j=0; j<pacientes.size(); j++)
				if (pacientes.get(j).getUsuario().equals(t.getUsuario()))
					break;
			
			if (j < pacientes.size()) {
				InfoPaciente p = pacientes.get(j);
				listadoPacientes.add(horas + ":" + minutos + " " + p.getNombre() + " " + p.getApellido1() + " " + p.getApellido2());
			}
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
	
	/**
	 * Lee el contenido de la pestaña Medicamentos remotamente. El agente medicamentos
	 * se ocupa de ella y aqui tan solo se muestra.
	 * @param c Composite SWT con el contenido
	 */
	public void mostrarTabMed(Composite c) {
		cMedicamentos = c;
		cTabMed.setControl(cMedicamentos);
	}
	
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) {
		((PanelMedicamentos) cMedicamentos).mostrarDatos(m);
	}
	
	public void mostrarMensajes(ArrayList<InfoMensaje> m) {
		mensajes = m;
		
		disp.asyncExec(new Runnable() {
            public void run() {
            	actualizarMensajes();
	       }
         });
	}

}