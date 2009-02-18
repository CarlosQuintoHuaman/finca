package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.imp;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.ControlCognitivoAbstracto;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Creencia;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Evidencia;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfMotorReglas;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ControlCognitivoImp extends ControlCognitivoAbstracto {

	private ItfMotorReglas motorReglas;
	private ItfGestorTareas gestorTareas;

	private Log log = LogFactory.getLog(ControlCognitivoImp.class);

	public ControlCognitivoImp(ItfMotorReglas motorReglas,
			ItfGestorTareas gestorTareas) {
		this.motorReglas = motorReglas;
		this.gestorTareas = gestorTareas;
	}

	@Override
	public boolean aceptaEvidencia(Evidencia ev) {
		Creencia cre;
		cre = asimilarEvidencia(ev);
		if (cre != null) {
			motorReglas.agregarHecho(cre);
			return true;
		}
		return true;
	}

	public void arranca() {
		motorReglas.dispararReglas();
	}

	// Decide si creer o no la evidencia
	public Creencia asimilarEvidencia(Evidencia ev) {
		Creencia cre = new Creencia();
		cre.setContenido(ev.getContenido());
		cre.setEmisor(ev.getOrigen());
		return cre;
	}

}
