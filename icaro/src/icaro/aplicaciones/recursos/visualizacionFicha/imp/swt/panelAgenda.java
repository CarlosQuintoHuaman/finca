package icaro.aplicaciones.recursos.visualizacionFicha.imp.swt;

import icaro.aplicaciones.recursos.visualizacionFicha.imp.ClaseGeneradoraVisualizacionFicha;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.usuario.UsoAgenteFicha;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario.UsoAgenteSecretaria;

import java.sql.Date;

import com.cloudgarden.resource.SWTResourceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

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
public class panelAgenda extends Thread {
	private Menu menu1;
	private CTabItem tabItem1;
	private CTabItem tabItem2;
	private Composite composite2;
	private Button button2;
	private Text text2;
	private Button button1;
	private Text text1;
	private Composite composite3;
	private ToolItem toolItem3;
	private ToolItem toolItem2;
	private ToolItem toolItem1;
	private ToolBar toolBar1;
	private CTabFolder tabFolder1;
	private Group composite1;
	protected Date fechaAnt;
	protected Date fecha;

	private UsoAgenteFicha usoAgente; //comunicaciï¿½n con el agente (control)
	private Display disp;
	private Shell shell;
	
	public panelAgenda(ClaseGeneradoraVisualizacionFicha visualizador){		
		super("Agenda");		
		//initGUI(display);		
		
		
		usoAgente = new UsoAgenteFicha(visualizador);		
	}
	
	public void run() {
		initGUI();
		System.out.println("Abriendo VENTANAAAAAAAAAAAAA");
	}
	
         public void mostrar(){ 	
	/*
	 * This method initializes sShell
	 */
             //createSShell();
         }  
         public void ocultar(){ 	
	/*
	 * This method debe ocultar la ventana
	 */
              } 
        public void destruir(){
        	System.out.println("Cerrando VENTANAAAAAAAAAAAAA");
        	
        	disp.getDefault().asyncExec(new Runnable() {
               public void run() {
            	   shell.dispose();
  		       }
            });

	/*
	 * This method 
	 */ 
         }
        
    public UsoAgenteFicha getAgente() {
    	return usoAgente;
    }
    
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		disp = new Display();
		shell = new Shell(disp);
		GridLayout lShell = new GridLayout();
		
		{
			//Register as a resource user - SWTResourceManager will
			//handle the obtaining and disposing of resources
			SWTResourceManager.registerResourceUser(shell);
		}
		
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 3;
		shell.setLayout(shellLayout);

		shell.pack();
		shell.setLayout(lShell);
		shell.setSize(800, 600);
		shell.setText("Doctoris");
		{
			menu1 = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menu1);
			
			MenuItem archivoMenu = new MenuItem(menu1, SWT.CASCADE);
			archivoMenu.setText("&Archivo");
			
			Menu archivo = new Menu(shell, SWT.DROP_DOWN);
			archivoMenu.setMenu(archivo);
			
			MenuItem editarMenu = new MenuItem(archivo, SWT.PUSH);
			editarMenu.setText("Abrir");

		}
		
		{
			toolBar1 = new ToolBar(shell, SWT.NONE);
			{
				toolItem1 = new ToolItem(toolBar1, SWT.NONE);
				toolItem1.setImage(new Image(disp, "src/icono.png"));
				toolItem1.setWidth(20);
				//toolItem1.setText("Texto");
				GridData toolGrid = new GridData();
				toolGrid.horizontalSpan = 3;
				toolGrid.grabExcessHorizontalSpace = true;
				toolGrid.horizontalAlignment = GridData.FILL;
				toolBar1.setLayoutData(toolGrid);
			}
			{
				toolItem2 = new ToolItem(toolBar1, SWT.NONE);
				toolItem2.setImage(new Image(disp, "src/icono.png"));
			}
			{
				toolItem3 = new ToolItem(toolBar1, SWT.NONE);
				toolItem3.setImage(new Image(disp, "src/icono.png"));
			}
		}
		
		{
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.FILL;
			gridData.heightHint = 400;
			//gridData.widthHint = 150;
			composite1 = new Group(shell, 0);
			composite1.setLayoutData(gridData);
			
			GridLayout composite1Layout = new GridLayout();
			
			composite1.setLayout(composite1Layout);
			composite1.setSize(150, 300);
			composite1.setText("Menu");
			composite1.setBackground(SWTResourceManager.getColor(220, 189, 244));

			Button uno = new Button(composite1,0);
			uno.setText("UNO MUY LARGO");
			GridData unoLData = new GridData();
			unoLData.widthHint = 89;
			unoLData.heightHint = 25;
			uno.setLayoutData(unoLData);
			uno.setBackground(SWTResourceManager.getColor(128, 128, 192));

			for (int loopIndex = 0; loopIndex < 5; loopIndex++) {
				GridData gridDataLabel = new GridData();
				gridDataLabel.horizontalAlignment = GridData.CENTER;
				
		      Label label = new Label(composite1, SWT.SHADOW_NONE);
		      Image icono = new Image(disp, "src/icono.png");
		      //label.setBackgroundImage(icono);
		      label.setLayoutData(gridDataLabel);
		      label.setImage(icono);
		      label.setSize(60, 60);
		      //label.setText("Label muy largo" + loopIndex);
		    }
		}

		{
			tabFolder1 = new CTabFolder(shell, SWT.NONE);
			GridData tabFolder1LData = new GridData();
			tabFolder1LData.horizontalAlignment = GridData.FILL;
			tabFolder1LData.verticalAlignment = GridData.FILL;
			tabFolder1LData.grabExcessHorizontalSpace = true;
			tabFolder1LData.grabExcessVerticalSpace = true;
			tabFolder1.setLayoutData(tabFolder1LData);
			tabFolder1.setSelection(0);
		}		

		{
			tabItem2 = new CTabItem(tabFolder1, SWT.NONE);
			tabItem2.setText("Uno");
			{
				composite3 = new Composite(tabFolder1, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				composite3.setLayout(composite3Layout);
				tabItem2.setControl(composite3);
				composite3.setBackground(SWTResourceManager.getColor(255, 255, 255));
				{
					button1 = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.widthHint = 53;
					button1LData.heightHint = 25;
					button1.setLayoutData(button1LData);
					button1.setText("09:00");
					button1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							button1WidgetSelected(evt);
						}
					});				
				}
				{
					text1 = new Text(composite3, SWT.NONE);
					text1.setText("Ecribo para que se alargue la barra");
					GridData text1LData = new GridData();
					text1LData.heightHint = 23;
					text1LData.horizontalAlignment = GridData.FILL;
					text1LData.grabExcessHorizontalSpace = true;
					text1.setLayoutData(text1LData);
					text1.setEditable(false);
					text1.setToolTipText("A ver");
				}
				{
					button2 = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData button2LData = new GridData();
					button2LData.widthHint = 54;
					button2LData.heightHint = 25;
					button2.setLayoutData(button2LData);
					button2.setText("10:00");
				}
				{
					text2 = new Text(composite3, SWT.NONE);
					text2.setText("Ecribo para que se alargue la barra");
					text2.setEditable(false);
					GridData text2LData = new GridData();
					text2LData.heightHint = 23;
					text2LData.horizontalAlignment = GridData.FILL;
					text2LData.grabExcessHorizontalSpace = true;
					text2.setLayoutData(text2LData);
					text2.setToolTipText("A ver");
				}
			}
			tabFolder1.setSelection(0);
		}
		{
			composite2 = new Composite(shell, SWT.NONE);
			GridLayout composite2Layout = new GridLayout();
			composite2Layout.makeColumnsEqualWidth = true;
			composite2.setLayout(composite2Layout);
			GridData composite2LData = new GridData();
			composite2LData.widthHint = 245;
			composite2LData.grabExcessVerticalSpace = true;
			composite2LData.verticalAlignment = GridData.FILL;
			composite2.setLayoutData(composite2LData);
		}
			//CalendarCombo cCombo = new CalendarCombo(composite2, SWT.READ_ONLY);
			
			//Establecemos el layout del shell
            lShell.numColumns = 3;

            //Introducimos los textos a los botones
            final Composite cAcepCanc = new Composite (composite2, SWT.BORDER);
            GridData cAcepCancLData = new GridData();
            cAcepCancLData.verticalAlignment = GridData.FILL;
            cAcepCancLData.horizontalAlignment = GridData.FILL;
            cAcepCanc.setLayoutData(cAcepCancLData);
            GridLayout lAcepCanc = new GridLayout();
            lAcepCanc.numColumns = 2;
            cAcepCanc.setLayout(lAcepCanc);
            
            final DateTime calendario = new DateTime (cAcepCanc, SWT.CALENDAR);
            calendario.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 2, 1));
            
            calendario.addMouseListener(new MouseListener () {
                    public void mouseDoubleClick(MouseEvent e) {
                            // TODO ¿Y esto por qué no va?
                            fecha = new Date(calendario.getYear(),calendario.getMonth(),calendario.getDay());
                            shell.dispose();
                    }
                    public void mouseUp(MouseEvent e) {};
                    public void mouseDown(MouseEvent e) {};
            });
            
            
            
            
            final Button bEnviar    = new Button(cAcepCanc, SWT.PUSH);
            bEnviar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
            bEnviar.setText("Seleccionar");
            //Introducimos los valores y eventos de Fecha Inicio
            bEnviar.addSelectionListener (new SelectionAdapter () {
					public void widgetSelected (SelectionEvent e) {
                            // TODO ¿Se puede evitar usar métodos obsoletos?
                            fecha = new Date(calendario.getYear()-1900,calendario.getMonth(),calendario.getDay());
                            fechaAnt = fecha;
                            shell.dispose();
                    }                               
            });
            
            final Button bCancelar  = new Button(cAcepCanc, SWT.PUSH);
            bCancelar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
            bCancelar.setText("Cancelar");
            //Introducimos los valores y eventos de Fecha Inicio
            bCancelar.addSelectionListener (new SelectionAdapter () {
                    public void widgetSelected (SelectionEvent e) {
            /*              if (fechaAnt!=null)
                                    fecha=fechaAnt;
                            else*/
                                    fecha = null;
                            shell.dispose();
                    }                               
            });
			
		
        shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {
		PanelCita f = new PanelCita(shell, SWT.NULL);
		f.open();
		//TODO add your code for button1.widgetSelected
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
//	public static void main(String[] args) {		
//			panelAgenda p = new panelAgenda();
//			
//
//	}

}
