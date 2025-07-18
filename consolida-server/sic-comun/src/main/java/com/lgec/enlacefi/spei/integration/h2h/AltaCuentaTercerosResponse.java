
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para altaCuentaTercerosResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="altaCuentaTercerosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://h2h.integration.spei.enlacefi.lgec.com/}altaCuentaTercerosReturn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "altaCuentaTercerosResponse", propOrder = {
    "_return"
})
public class AltaCuentaTercerosResponse {

    @XmlElement(name = "return")
    protected AltaCuentaTercerosReturn _return;

    /**
     * Obtiene el valor de la propiedad return.
     * 
     * @return
     *     possible object is
     *     {@link AltaCuentaTercerosReturn }
     *     
     */
    public AltaCuentaTercerosReturn getReturn() {
        return _return;
    }

    /**
     * Define el valor de la propiedad return.
     * 
     * @param value
     *     allowed object is
     *     {@link AltaCuentaTercerosReturn }
     *     
     */
    public void setReturn(AltaCuentaTercerosReturn value) {
        this._return = value;
    }

}
