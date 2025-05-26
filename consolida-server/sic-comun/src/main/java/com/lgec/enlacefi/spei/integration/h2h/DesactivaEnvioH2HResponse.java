
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para desactivaEnvioH2HResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="desactivaEnvioH2HResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://h2h.integration.spei.enlacefi.lgec.com/}speiServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="configuracion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "desactivaEnvioH2HResponse", propOrder = {
    "configuracion"
})
public class DesactivaEnvioH2HResponse
    extends SpeiServiceResponse
{

    protected String configuracion;

    /**
     * Obtiene el valor de la propiedad configuracion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfiguracion() {
        return configuracion;
    }

    /**
     * Define el valor de la propiedad configuracion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfiguracion(String value) {
        this.configuracion = value;
    }

}
