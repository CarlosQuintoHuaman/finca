/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/*
import control.Controlador;
import persistencia.Persistencia;
*/
/**
 *
 * @author camilo
 */
public class panelFicha {
    private Shell padre;
    private Shell shell;
    private String nombre;
    private String apell1;
    private String apell2;
    private Integer telefono;
    private panelAgenda p;
   // private Persistencia p;
    //private Controlador c;
    //private panelFicha este = this;
    
public panelFicha(Shell padre, panelAgenda p){
    //	this.p = p;
    //	this.c = c;
	  this.padre=padre;
	  this.p=p;
	 mostrarVentana();
	}
    
    public void mostrarVentana(){
        shell = new Shell (padre,SWT.CLOSE | SWT.APPLICATION_MODAL);
        GridLayout layout = new GridLayout(2,false);
        final Group grupoIzq = new Group(shell, SWT.NONE);
        grupoIzq.setText("");
        grupoIzq.setLayout(new GridLayout(2,false));
        final Composite grupoDer = new Composite(shell, SWT.NONE);
        //grupoDer.setText("");
        grupoDer.setLayout(new GridLayout(1,false));
        grupoDer.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        
    	final Label  lNombre		= new Label (grupoIzq, SWT.LEFT);
		final Text   tNombre		= new Text  (grupoIzq, SWT.BORDER);
		//final Composite cTick		= new Composite(shell, 0);		
		final Label lTick			= new Label(grupoDer, 0);
		//lTick.setLayoutData(new GridData(GridData.FILL_BOTH));
		final Image	 tick			= new Image(padre.getDisplay(), "src/tick.png");
		lTick.setImage(tick);
		final Label  lApell1		= new Label (grupoIzq, SWT.LEFT);
		final Text   tApell1		= new Text  (grupoIzq, SWT.BORDER);
        final Label  lApell2		= new Label (grupoIzq, SWT.LEFT);
		final Text   tApell2		= new Text  (grupoIzq, SWT.BORDER);
        final Label  lDni           = new Label (grupoIzq, SWT.LEFT);
        final Text   tDni           = new Text  (grupoIzq, SWT.BORDER);
        final Label  lDireccion		= new Label (grupoIzq, SWT.LEFT);
		final Text   tDireccion		= new Text  (grupoIzq, SWT.BORDER);
        final Label  lTelf  		= new Label (grupoIzq, SWT.LEFT);
		final Text   tTelf  		= new Text  (grupoIzq, SWT.BORDER);
        final Label  lEMail			= new Label (grupoIzq, SWT.LEFT);
		final Text   tEMail			= new Text  (grupoIzq, SWT.BORDER);
        final Button bFNacimiento	= new Button(grupoIzq, SWT.PUSH);
		final Text   tFNacimiento	= new Text  (grupoIzq, SWT.BORDER | SWT.READ_ONLY);
        final Label  lSexo			= new Label (grupoIzq, SWT.LEFT);
		final Combo  cSexo			= new Combo (grupoIzq, SWT.BORDER | SWT.READ_ONLY);



		lNombre			.setText("Nombre");
		lApell1			.setText("1er Apellido");
        lApell2			.setText("2do Apellido");
        lDireccion		.setText("Direccion");
        lTelf           .setText("Telefono");
        lDni			.setText("Dni");
        lEMail			.setText("Email");
        lSexo			.setText("Sexo");


        lNombre		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tNombre		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		lApell1		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tApell1		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		lApell2		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tApell2		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
        lDni		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
        tDni		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
        lDireccion	.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tDireccion	.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
        lTelf   	.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tTelf   	.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
        lEMail		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		tEMail		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
        bFNacimiento.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,2,1));
		tFNacimiento.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,2,1));
		lSexo		.setLayoutData	(new GridData(SWT.LEFT,SWT.FILL,false,false,1,1));
		cSexo		.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));

        final Button bAceptar	= new Button(grupoIzq, SWT.PUSH);
        final Button bCancelar	= new Button(grupoIzq, SWT.PUSH);
        bAceptar.setText("Aceptar");
        bFNacimiento.setText("Fecha Nacimiento");
        bCancelar.setText("Cancelar");
        bAceptar.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,2,1));
        bCancelar.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,false,false,2,1));
        grupoIzq.setLayoutData	(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));

        cSexo.setItems (new String [] {	"Femenino","Masculino"});
        SelectionAdapter sabAceptar = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				
				nombre = tNombre.getText();
				apell1 = tApell1.getText();
				apell2 = tApell2.getText();				
				
				//p.getAgente().valida(nombre, apell1);
				
			//	if (c.validaDatosFicha(este)){
			//		p.guardarFicha(este);
			//	} else {
					MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
					messageBox.setText ("Error");
					messageBox.setMessage ("Hay que rellenar todos los campos");
				    messageBox.open();
			//	}
                
			}
		};

        SelectionAdapter sabCancelar = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
                shell.dispose();
			}
		};
        	bAceptar.addSelectionListener(sabAceptar);
            bCancelar.addSelectionListener(sabCancelar);

        shell.setText("Crear Ficha");
        shell.setSize(600,1000 );
		shell.setLayout(layout);

        shell.pack();
        shell.open();

    }
    
    public String getNombre() {
		return nombre;
    	
    }
    
    public String getApell1() {
		return apell1;
    	
    }
    
    public String getApell2() {
		return apell2;
    	
    }
    
    public Integer getTelf() {    	
		return telefono;
    	
    }
    
    public boolean isDisposed() {
		return padre.isDisposed();
	}


}
