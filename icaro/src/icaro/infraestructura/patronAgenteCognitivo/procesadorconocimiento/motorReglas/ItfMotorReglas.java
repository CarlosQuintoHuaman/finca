/*
 * ItfMotorReglas.java
 *
 * Creado 18 de abril de 2007, 11:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas;
import java.io.InputStream;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public interface ItfMotorReglas extends ItfEnvioHechos{
    public void dispararReglas();
    public void compilarReglas(InputStream fichero) throws Exception;
    public void agregarVariableGlobal(String nombre,Object object);
}
