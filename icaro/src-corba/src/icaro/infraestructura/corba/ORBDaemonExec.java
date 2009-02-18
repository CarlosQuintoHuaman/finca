package icaro.infraestructura.corba;



import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ORBDaemonExec extends Thread {
	
	private static ORBDaemonExec instance;
	
	private String host;
	private String port;
	private final String ACTIVATIONPORT = "1059";
	private final String ORBD = "orbd";
	private final String INITIALPORT_OPT = "-ORBInitialPort";
	private final String INITIALHOST_OPT = "-ORBInitialHost";
	private final String ACTIVATIONPORT_OPT = "-port";
	
	private Process process;
	
	private Log logger = LogFactory.getLog(ORBDaemonExec.class);
	private int result;
	
	
	public static ORBDaemonExec instance() {	
		return instance;
	}
	
	
	public ORBDaemonExec(String host, String port) {
		this.setDaemon(true);
		this.host = host;
		this.port = port;
		result = 0;
		instance = this;
	}
	
	public int execute() throws InterruptedException, IOException{
		Runtime rt = Runtime.getRuntime();
		logger.debug("Comando a ejecutar: "+ORBD+" "+INITIALHOST_OPT+" "+host+" "+INITIALPORT_OPT+" "+port+" "+ACTIVATIONPORT_OPT+" "+ACTIVATIONPORT);
		process = rt.exec(ORBD+" "+INITIALHOST_OPT+" "+host+" "+INITIALPORT_OPT+" "+port+" "+ACTIVATIONPORT_OPT+" "+ACTIVATIONPORT);
		
		int result = 0;
		try {
			result = process.exitValue();
		}
		catch(IllegalThreadStateException e) {
			result = 0;
		}
		
	    return result;
	}
	
	public int getResult() {
		return result;
	}

	

	@Override
	public void run() {
		try {
			result = execute();
		} catch (InterruptedException e) {
			logger.error("Error al ejecutar el demonio orbd. Interrupción");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error de entrada/salida al intentar ejecutar el demonio orbd.");
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void finalize() {
		if (process != null) {
			process.destroy();
		}
	}
	
	public static ORBDaemonExec finalInstance() {
		return instance;
	}
	
	
	
}
