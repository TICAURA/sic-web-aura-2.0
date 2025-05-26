
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para speiServiceFolioOrigenResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="speiServiceFolioOrigenResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://h2h.integration.spei.enlacefi.lgec.com/}speiServiceResponse">
 *       &lt;sequence>
 *         &lt;element name="folioOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "speiServiceFolioOrigenResponse", propOrder = {
    "folioOrigen"
})
public class SpeiServiceFolioOrigenResponse
    extends SpeiServiceResponse
{

    protected String folioOrigen;

    /**
     * Obtiene el valor de la propiedad folioOrigen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioOrigen() {
        return folioOrigen;
    }

    /**
     * Define el valor de la propiedad folioOrigen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioOrigen(String value) {
        this.folioOrigen = value;
    }

}
