/*
 * IAvisos.java
 *
 * Creado en 22 de noviembre de 2007, 10:56
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public interface ItfAvisos {
    public void falloTemporal(String razon);
    public void error(String razon);
    public void terminarInterno();
}
