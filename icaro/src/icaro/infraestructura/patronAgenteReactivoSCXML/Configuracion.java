/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivoSCXML;

import java.io.InputStream;

/**
 *
 * @author carlos
 */
public class Configuracion {

        private InputStream acciones;
        private InputStream maquinaEstados;
        private boolean daemon;

    public InputStream getAcciones() {
        return acciones;
    }

    public void setAcciones(InputStream acciones) {
        this.acciones = acciones;
    }

    public InputStream getMaquinaEstados() {
        return maquinaEstados;
    }

    public void setMaquinaEstados(InputStream maquinaEstados) {
        this.maquinaEstados = maquinaEstados;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }
}
