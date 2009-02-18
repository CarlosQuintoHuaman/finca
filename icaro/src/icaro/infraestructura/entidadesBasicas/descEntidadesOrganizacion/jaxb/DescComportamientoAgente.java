//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.18 at 11:40:39 AM CEST 
//


package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DescComportamientoAgente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescComportamientoAgente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="nombreComportamiento" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}NombreAgente" />
 *       &lt;attribute name="tipo" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}TipoAgente" />
 *       &lt;attribute name="rol" use="required" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}RolAgente" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescComportamientoAgente")
@XmlSeeAlso({
    DescComportamientoAgenteCognitivo.class,
    DescComportamientoAgenteReactivo.class
})
public class DescComportamientoAgente {

    @XmlAttribute(required = true)
    protected String nombreComportamiento;
    @XmlAttribute(required = true)
    protected TipoAgente tipo;
    @XmlAttribute(required = true)
    protected RolAgente rol;

    /**
     * Gets the value of the nombreComportamiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreComportamiento() {
        return nombreComportamiento;
    }

    /**
     * Sets the value of the nombreComportamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreComportamiento(String value) {
        this.nombreComportamiento = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoAgente }
     *     
     */
    public TipoAgente getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAgente }
     *     
     */
    public void setTipo(TipoAgente value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the rol property.
     * 
     * @return
     *     possible object is
     *     {@link RolAgente }
     *     
     */
    public RolAgente getRol() {
        return rol;
    }

    /**
     * Sets the value of the rol property.
     * 
     * @param value
     *     allowed object is
     *     {@link RolAgente }
     *     
     */
    public void setRol(RolAgente value) {
        this.rol = value;
    }

}
