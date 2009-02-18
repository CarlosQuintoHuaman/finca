package icaro.aplicaciones.recursos.visualizacionFicha.imp.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;


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
public class PanelCita extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label label1;
	private Text tApellido;
	private Label lApellido;
	private Button button4;
	private Button button3;
	private Button button2;
	private Text text2;
	private Text text1;
	private Label label2;
	private Button button1;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Dialog inside a new Shell.
	*/
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			PanelCita inst = new PanelCita(shell, SWT.NULL);
			inst.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PanelCita(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.CENTER;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.widthHint = 347;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					button1 = new Button(composite1, SWT.CHECK | SWT.LEFT);
					GridData button1LData = new GridData();
					button1LData.horizontalSpan = 3;
					button1.setLayoutData(button1LData);
					button1.setText("Paciente Nuevo");
				}
				{
					label1 = new Label(composite1, SWT.NONE);
					GridData label1LData = new GridData();
					label1LData.horizontalAlignment = GridData.FILL;
					label1LData.heightHint = 15;
					label1.setLayoutData(label1LData);
					label1.setText("Nombre:");
					label1.setSize(30, 15);
				}
				{
					GridData text1LData = new GridData();
					text1LData.horizontalAlignment = GridData.FILL;
					text1LData.grabExcessHorizontalSpace = true;
					text1LData.verticalAlignment = GridData.FILL;
					text1LData.horizontalSpan = 2;
					text1 = new Text(composite1, SWT.BORDER);
					text1.setLayoutData(text1LData);
				}
				{
					lApellido = new Label(composite1, SWT.NONE);
					lApellido.setText("Apellido:");
				}
				{
					GridData tApellidoLData = new GridData();
					tApellidoLData.horizontalSpan = 2;
					tApellidoLData.horizontalAlignment = GridData.FILL;
					tApellido = new Text(composite1, SWT.BORDER);
					tApellido.setLayoutData(tApellidoLData);
				}
				{
					label2 = new Label(composite1, SWT.NONE);
					label2.setText("Telefono:");
				}
				{
					GridData text2LData = new GridData();
					text2LData.heightHint = 15;
					text2LData.horizontalSpan = 2;
					text2LData.horizontalAlignment = GridData.FILL;
					text2 = new Text(composite1, SWT.BORDER);
					text2.setLayoutData(text2LData);
				}
				{
					button2 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					button2.setText("Buscar Paciente");
				}
				{
					button3 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					button3.setText("Aceptar");
				}
				{
					button4 = new Button(composite1, SWT.PUSH | SWT.CENTER);
					button4.setText("Cancelar");
				}
			}
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(366, 182);
			dialogShell.setText("Dar Cita");
			dialogShell.setLocation(getParent().toDisplay(100, 100));
			dialogShell.open();
			Display display = dialogShell.getDisplay();
//			while (!dialogShell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
