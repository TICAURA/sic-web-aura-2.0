
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registraTraspaso complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registraTraspaso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="traspaso" type="{http://h2h.integration.spei.enlacefi.lgec.com/}traspasoWS" minOccurs="0"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraTraspaso", propOrder = {
    "traspaso",
    "token"
})
public class RegistraTraspaso {

    protected TraspasoWS traspaso;
    protected String token;

    /**
     * Obtiene el valor de la propiedad traspaso.
     * 
     * @return
     *     possible object is
     *     {@link TraspasoWS }
     *     
     */
    public TraspasoWS getTraspaso() {
        return traspaso;
    }

    /**
     * Define el valor de la propiedad traspaso.
     * 
     * @param value
     *     allowed object is
     *     {@link TraspasoWS }
     *     
     */
    public void setTraspaso(TraspasoWS value) {
        this.traspaso = value;
    }

    /**
     * Obtiene el valor de la propiedad token.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Define el valor de la propiedad token.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

}
