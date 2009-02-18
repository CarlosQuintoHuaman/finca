package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Arrays;





/**
 *  Representa el foco del sistema. Como no se puede acceder al contexto en la
 *  parte condicional de una regla, de momento se inserta en el motor de
 *  inferencias como un objeto para poder acceder al objetivo focalizado
 *
 *@author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class Focus {

    private Objective foco = null;
    /**
     *  Cola circular que guarda los focos anteriores
     */
    protected Objective[] focosAnteriores = null;
    /**
     *  Tama�o m�ximo de la cola circular
     */
    protected final static int TAM_COLA_FOCOS = 5;
    /**
     *  Indice de la cola circular
     */
    protected int indice = 0;
    
    protected ItfUsoRecursoTrazas trazas;


    /**
     *  Constructor for the Focus object
     *
     *@param  motor  Description of the Parameter
     */
    public Focus() {
        // Crea cola circular
        this.focosAnteriores = new Objective[TAM_COLA_FOCOS];
        Arrays.fill(this.focosAnteriores, null);
        this.indice = 0;
        
        try {
			trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) {
			e.printStackTrace();
		}
        

    }

    /**
     *  Fija el foco al objetivo obj
     *
     *@param  obj  Objetivo al cual apuntar� el foco
     */
    public void setFoco(Objective obj) {
    	trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+obj.getID(),NivelTraza.info));
        // Introduce el foco nuevo en la cola, siempre que no fuera el mismo objetivo que el anterior
        if (obj != this.foco) {

            this.foco = obj;

            this.indice = (this.indice + 1) % TAM_COLA_FOCOS;
            this.focosAnteriores[indice] = obj;


        }
    }

    /**
     *  Devolvemos la referencia al Objetivo al cual apunta el foco
     *
     *@return    Refernacia al objetivo
     */
    public Objective getFoco() {
        return this.foco;
    }

    /**
     *  Devolvemos la referencia al Objetivo al cual apuntaba el foco
     *  anteriormente
     *
     *@return    Refernacia al objetivo en el que estaba antes el foco
     */
    public Objective getFocoAnterior() {
        return this.focosAnteriores[(TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS];
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado Solo se
     *  puede refocalizar al objetivo inmediantamente anterior (memoria 1 s�lo
     *  paso)
     */
    public void refocus() {
        this.indice = (TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS;
        this.foco = this.focosAnteriores[this.indice];
        trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getID(),NivelTraza.info));
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado Solo se
     *  puede refocalizar al objetivo inmediantamente anterior (memoria 1 s�lo
     *  paso) Actualiza el objetivo que acabamos de re-focalizar al estado
     *  pending
     */
    public void refocusYCambiaAPending() {
        this.indice = (TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS;
        this.foco = this.focosAnteriores[this.indice];
        this.foco.setPending();
        trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getID(),NivelTraza.info));
    }

    /**
     *  Devuelve el contenido del foco como una cadena de texto
     *
     *@return    Description of the Return Value
     */
    public String toString() {
        return "(FOCO: focoActual= " + this.foco + " || focoAnterior= " + this.focosAnteriores[(this.indice + TAM_COLA_FOCOS - 1) % TAM_COLA_FOCOS] + " )";
    }

    /**
     *  Obtiene el contenido de la cola de focos anteriores
     *
     *@return    Description of the Return Value
     */
    public String toStringCola() {

        String res = "";
        res += "Cola de Objetivos focalizados:\n";
        for (int i = 0; i < TAM_COLA_FOCOS; i++) {
            res += "Posicion " + i + ": " + this.focosAnteriores[i] + '\n';
        }

        res += "Objetivo focalizado en posicion " + this.indice + ": " + this.focosAnteriores[this.indice];

        return res;
    }
}

