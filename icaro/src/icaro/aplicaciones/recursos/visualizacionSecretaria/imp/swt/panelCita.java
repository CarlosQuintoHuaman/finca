package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;
import icaro.util.util;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;

public class panelCita extends Thread {

	// Variables
	//componentes de la ventana
	private Composite compoPrincipal;
	private Composite compoLinea1;
	private Composite compoLinea2;
	private Button bNuevo;
	private Button bCancelar;
	private Button bAceptar;
	private Text tAseguradora;
	private CLabel cAseguradora;
	private Text tTelefono2;
	private Text tApellidos;
	private CLabel cApellidos;
	private Text tTelefono1;
	private CLabel cTelefonos;
	private Text tPaciente;
	private CLabel cPaciente;
	private Button bPrimeraVez;
	private Text text1;
	private Text tHoraD;
	private Text tFecha;
	private Button bmenos;
	private Button bMas;
	private CLabel cTiempo;
	private CLabel cHoraA;
	private CLabel cHoraCita;
	private CLabel cFechaCita;

	//Variables globales
	private int intervalo=15;
	private int periodo;
	private HorasCita hora;

	final UsoAgenteSecretaria usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private panelCita este;
	private panelAgenda p;
	private DatosCitaSinValidar datos;
	private ClaseGeneradoraVisualizacionSecretaria vis;
	
	/**
	 * Constructor de la ventana
	 * @param visualizador
	 */
	public panelCita(ClaseGeneradoraVisualizacionSecretaria visualizador){
		super("Agenda");
		este = this;
		vis=visualizador;
		usoAgente = new UsoAgenteSecretaria(visualizador);
	}
	/**
	 *  Contructor de la ventana que icorpora los datos que se deben mostrar en la ventana
	 * @param visualizador
	 * @param da
	 */
	public panelCita(ClaseGeneradoraVisualizacionSecretaria visualizador, DatosCitaSinValidar da){
		super("Agenda");
		este = this;
		datos=da;
		
		
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
	 *  Muestra la ventana con los datos que se le pasan como parametro en los campos que le corresponden
	 * @param dat datos a mostrar en la ventana
	 */
	public void mostrar(final DatosCitaSinValidar dat){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.open();
         	  String[]aux;
          	
          	aux=dat.tomaNombre().split(" ");
          	if (aux.length==0){
          		tPaciente.setText("");
              	tApellidos.setText("");
          	}else{
          		tPaciente.setText(aux[0]);
          		
          		tApellidos.setText(dat.tomaApell1());
          	}
          	tFecha.setText(dat.getFecha());
      		tTelefono1.setText(dat.tomaTelf());
      		tHoraD.setText(dat.tomaHora());
      		hora= new HorasCita(dat.tomaHora(),"");
      	
      		datos=new DatosCitaSinValidar(dat.tomaNombre(),dat.tomaApell1(),dat.getApell2(), dat.tomaTelf(), dat.tomaHora(),periodo);
      		calculaPeriodo(); 
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
			shell.setLayout(shellLayout);
			shell.setSize(492, 259);
			shell.setText("Dar Cita");
			
			{
				//Register as a resource user - SWTResourceManager will
				//handle the obtaining and disposing of resources
				SWTResourceManager.registerResourceUser(shell);
			}
			shell.addShellListener(new ShellAdapter() {
			    public void shellClosed(ShellEvent event) {
			    	usoAgente.cerrarVentanaCita();
			    }
			});
			periodo=1;
			{
				compoPrincipal = new Composite(shell, SWT.NONE);
				GridLayout compoPrincipalLayout = new GridLayout();
				compoPrincipalLayout.makeColumnsEqualWidth = true;
				GridData compoPrincipalLData = new GridData();
				compoPrincipalLData.verticalAlignment = GridData.BEGINNING;
				compoPrincipalLData.horizontalAlignment = GridData.BEGINNING;
				compoPrincipalLData.widthHint = 479;
				compoPrincipalLData.heightHint = 254;
				compoPrincipal.setLayoutData(compoPrincipalLData);
				compoPrincipal.setLayout(compoPrincipalLayout);
				{
					compoLinea1 = new Composite(compoPrincipal, SWT.NONE);
					GridLayout compoLinea1Layout = new GridLayout();
					compoLinea1Layout.numColumns = 9;
					GridData compoLinea1LData = new GridData();
					compoLinea1LData.verticalAlignment = GridData.BEGINNING;
					compoLinea1LData.horizontalAlignment = GridData.BEGINNING;
					compoLinea1LData.widthHint = 468;
					compoLinea1LData.heightHint = 38;
					compoLinea1LData.grabExcessVerticalSpace = true;
					compoLinea1LData.grabExcessHorizontalSpace = true;
					compoLinea1.setLayoutData(compoLinea1LData);
					compoLinea1.setLayout(compoLinea1Layout);
					{
						cFechaCita = new CLabel(compoLinea1, SWT.NONE);
						cFechaCita.setText("Fecha de Cita");
					}
					{
						GridData tFechaLData = new GridData();
						tFechaLData.verticalAlignment = GridData.BEGINNING;
						tFechaLData.horizontalAlignment = GridData.BEGINNING;
						tFechaLData.heightHint = 17;
						tFecha = new Text(compoLinea1, SWT.BORDER);
						tFecha.setLayoutData(tFechaLData);
						util f=new util(); 
						tFecha.setText("");
					}
					{
						cHoraCita = new CLabel(compoLinea1, SWT.NONE);
						cHoraCita.setText("Hora de Cita: De");
					}
					{
						GridData tHoraDLData = new GridData();
						tHoraDLData.verticalAlignment = GridData.BEGINNING;
						tHoraDLData.horizontalAlignment = GridData.BEGINNING;
						tHoraDLData.heightHint = 17;
						tHoraDLData.widthHint = 34;
						tHoraD = new Text(compoLinea1, SWT.BORDER);
						tHoraD.setLayoutData(tHoraDLData);
					}
					{
						cHoraA = new CLabel(compoLinea1, SWT.NONE);
						cHoraA.setText("A");
					}
					{
						GridData text1LData = new GridData();
						text1LData.verticalAlignment = GridData.BEGINNING;
						text1LData.horizontalAlignment = GridData.BEGINNING;
						text1LData.heightHint = 17;
						text1LData.widthHint = 34;
						text1 = new Text(compoLinea1, SWT.BORDER);
						text1.setLayoutData(text1LData);
					}
					{
						bmenos = new Button(compoLinea1, SWT.PUSH | SWT.CENTER);
						GridData bmenosLData = new GridData();
						bmenosLData.verticalAlignment = GridData.BEGINNING;
						bmenosLData.horizontalAlignment = GridData.BEGINNING;
						bmenosLData.heightHint = 23;
						bmenos.setLayoutData(bmenosLData);
						bmenosLData.heightHint = 23;
						bmenosLData.widthHint = 23;
						bmenos.setText("-");
						bmenos.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								bmenosWidgetSelected(evt);
							}
						});
					}
					{
						bMas = new Button(compoLinea1, SWT.PUSH | SWT.CENTER);
						GridData bMasLData = new GridData();
						bMasLData.verticalAlignment = GridData.BEGINNING;
						bMasLData.horizontalAlignment = GridData.BEGINNING;
						bMasLData.widthHint = 23;
						bMasLData.heightHint = 23;
						bMas.setLayoutData(bMasLData);
						bMas.setText("+");
						bMas.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								bMasWidgetSelected(evt);
							}
						});
					}
					{
						cTiempo = new CLabel(compoLinea1, SWT.NONE);
						cTiempo.setText("00:15");
					}
				}
				{
					compoLinea2 = new Composite(compoPrincipal, SWT.NONE);
					GridLayout compoLinea2Layout = new GridLayout();
					compoLinea2Layout.numColumns = 3;
					GridData compoLinea2LData = new GridData();
					compoLinea2LData.verticalAlignment = GridData.BEGINNING;
					compoLinea2LData.horizontalAlignment = GridData.BEGINNING;
					compoLinea2LData.widthHint = 469;
					compoLinea2LData.heightHint = 210;
					compoLinea2.setLayoutData(compoLinea2LData);
					compoLinea2.setLayout(compoLinea2Layout);
					{
						bPrimeraVez = new Button(compoLinea2, SWT.CHECK | SWT.LEFT);
						GridData bPrimeraVezLData = new GridData();
						bPrimeraVezLData.verticalAlignment = GridData.BEGINNING;
						bPrimeraVezLData.horizontalAlignment = GridData.BEGINNING;
						bPrimeraVezLData.horizontalSpan = 3;
						bPrimeraVez.setLayoutData(bPrimeraVezLData);
						bPrimeraVez.setText("Primera vez");
					}
					{
						cPaciente = new CLabel(compoLinea2, SWT.NONE);
						cPaciente.setText("Nombre");
					}
					{
						GridData tPacienteLData = new GridData();
						tPacienteLData.verticalAlignment = GridData.BEGINNING;
						tPacienteLData.horizontalAlignment = GridData.BEGINNING;
						tPacienteLData.heightHint = 17;
						tPacienteLData.horizontalSpan = 2;
						tPacienteLData.widthHint = 251;
						tPaciente = new Text(compoLinea2, SWT.BORDER);
						tPaciente.setLayoutData(tPacienteLData);
					}
					{
						cApellidos = new CLabel(compoLinea2, SWT.NONE);
						cApellidos.setText("Apellidos");
					}
					{
						GridData tApellidosLData = new GridData();
						tApellidosLData.verticalAlignment = GridData.BEGINNING;
						tApellidosLData.horizontalAlignment = GridData.BEGINNING;
						tApellidosLData.heightHint = 17;
						tApellidosLData.horizontalSpan = 2;
						tApellidosLData.widthHint = 357;
						tApellidos = new Text(compoLinea2, SWT.BORDER);
						tApellidos.setLayoutData(tApellidosLData);
					}
					{
						cTelefonos = new CLabel(compoLinea2, SWT.NONE);
						cTelefonos.setText("Telefonos");
					}
					{
						GridData tTelefono1LData = new GridData();
						tTelefono1LData.verticalAlignment = GridData.BEGINNING;
						tTelefono1LData.horizontalAlignment = GridData.BEGINNING;
						tTelefono1LData.heightHint = 17;
						tTelefono1LData.widthHint = 99;
						tTelefono1 = new Text(compoLinea2, SWT.BORDER);
						tTelefono1.setLayoutData(tTelefono1LData);
					}

					{
						GridData tTelefono2LData = new GridData();
						tTelefono2LData.verticalAlignment = GridData.BEGINNING;
						tTelefono2LData.horizontalAlignment = GridData.BEGINNING;
						tTelefono2LData.heightHint = 17;
						tTelefono2LData.widthHint = 99;
						tTelefono2 = new Text(compoLinea2, SWT.BORDER);
						tTelefono2.setLayoutData(tTelefono2LData);
					}
					{
						cAseguradora = new CLabel(compoLinea2, SWT.NONE);
						cAseguradora.setText("Aseguradora");
					}
					{
						GridData tAseguradoraLData = new GridData();
						tAseguradoraLData.verticalAlignment = GridData.BEGINNING;
						tAseguradoraLData.horizontalAlignment = GridData.BEGINNING;
						tAseguradoraLData.heightHint = 17;
						tAseguradoraLData.widthHint = 105;
						tAseguradoraLData.horizontalSpan = 2;
						tAseguradora = new Text(compoLinea2, SWT.BORDER);
						tAseguradora.setLayoutData(tAseguradoraLData);
					}
					{
						bAceptar = new Button(compoLinea2, SWT.PUSH | SWT.CENTER);
						GridData bAceptarLData = new GridData();
						bAceptarLData.verticalAlignment = GridData.BEGINNING;
						bAceptarLData.horizontalAlignment = GridData.BEGINNING;
						bAceptarLData.widthHint = 74;
						bAceptarLData.heightHint = 31;
						bAceptar.setLayoutData(bAceptarLData);
						bAceptar.setText("Aceptar");
						bAceptar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								bAceptarWidgetSelected(evt);
								usoAgente.cerrarVentanaCita();
								
							}
						});
					}
					{
						bCancelar = new Button(compoLinea2, SWT.PUSH | SWT.CENTER);
						GridData bCancelarLData = new GridData();
						bCancelarLData.verticalAlignment = GridData.BEGINNING;
						bCancelarLData.horizontalAlignment = GridData.BEGINNING;
						bCancelarLData.widthHint = 74;
						bCancelarLData.heightHint = 31;
						bCancelar.setLayoutData(bCancelarLData);
						bCancelar.setText("Cancelar");
						bCancelar.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								usoAgente.cerrarVentanaCita();
							}
						});
					}
					{
						bNuevo = new Button(compoLinea2, SWT.PUSH | SWT.CENTER);
						GridData bNuevoLData = new GridData();
						bNuevoLData.verticalAlignment = GridData.BEGINNING;
						bNuevoLData.horizontalAlignment = GridData.BEGINNING;
						bNuevoLData.heightHint = 31;
						bNuevoLData.widthHint = 105;
						bNuevo.setLayoutData(bNuevoLData);
						bNuevo.setText("Nuevo Paciente");
						bNuevo.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
//								if (!tPaciente.getText().equals("")){
									DatosCita d=new DatosCita(tPaciente.getText()+" "+tApellidos.getText(),tTelefono1.getText(),true,0);
									usoAgente.mostrarVentanaFicha(d);
/*								}
								else{
									usoAgente.mostrarVentanaFicha();
								}*/
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Metodos especificos de la ventana
	
	/**
	 * Evento del boton '-' de la ventana
	 * @param evt
	 */
	private void bmenosWidgetSelected(SelectionEvent evt){
		if (periodo>1)
			periodo--;
		calculaPeriodo();
	}
	/**
	 * Evento del boton '+' de la ventana
	 * @param evt
	 */
	private void bMasWidgetSelected(SelectionEvent evt) {
		periodo=periodo+1;
		
		
		calculaPeriodo();
		//disp.update();
		Boolean l=usoAgente.estaLibre(hora);
		l=false;
	}

	/**
	 * Evento del boton aceptar de la ventana
	 * @param evt
	 */
	private void bAceptarWidgetSelected(SelectionEvent evt){
		datos.setNombre(tPaciente.getText());
		datos.setApell1(tApellidos.getText());
		datos.setHora(tHoraD.getText());
		datos.setPeriodo(periodo);
		datos.setTelf(tTelefono1.getText());
		usoAgente.validaCita(datos);
		try {
			vis.cerrarVisualizadorExtra();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Funcion auxiliar que nos ayuda a calcular cuantas franjas horarias de la agenda ocupa un paciente a traves del periodo.
	 * Ejemplo: Si intervalo interconsulta= 15 min, periodo=2 y hora inicio cita paciente=9:00 entonces el paciente estara 
	 * 2 intervalos de 15 min en la consulta luego de 9:00 a 9:30.  
	 */
	public void calculaPeriodo(){
		int c=periodo;
		String y=datos.tomaHora().substring(3);
		int t=15;
		int h2=0;
		String aux2=String.valueOf(h2)+":";
		String h= datos.tomaHora().substring(0,2);
		int h1=Integer.valueOf(h);
		String aux=String.valueOf(h1)+":";
		int x=Integer.valueOf(y);
		while (c >0){
			x= x+intervalo;
			
			if (x==60){
				h1++;
				if (h1<10)
					aux="0"+String.valueOf(h1)+":";
				else{
					aux=String.valueOf(h1)+":";
				}
					x=00;
				
			}
			if (t==60){
				h2++;
				if (h2<10)
					aux2="0"+String.valueOf(h2)+":";
				else{
					aux2=String.valueOf(h2)+":";
				}
				t=0;
				
			}
			t=t+intervalo;
				c--;
		}
		t=t-15;
		if (x==0){
			text1.setText(aux+String.valueOf(x)+"0");
			hora.setHFin(aux+String.valueOf(x)+"0");
		}else{
			text1.setText(aux+String.valueOf(x));
			hora.setHFin(aux+String.valueOf(x));
		}if (t==0)
			cTiempo.setText(aux2+String.valueOf(t)+"0");
		else
			cTiempo.setText(aux2+String.valueOf(t));
		datos.setPeriodo(periodo);
	}

	
    public void cerrar() {
    	disp.asyncExec(new Runnable() {
            public void run() {
            	shell.dispose();
		    }
        });
    }

}