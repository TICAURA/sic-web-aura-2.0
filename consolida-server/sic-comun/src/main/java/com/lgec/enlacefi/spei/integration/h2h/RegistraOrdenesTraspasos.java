
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registraOrdenesTraspasos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registraOrdenesTraspasos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenesTraspasos" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenesTraspasos" minOccurs="0"/>
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
@XmlType(name = "registraOrdenesTraspasos", propOrder = {
    "ordenesTraspasos",
    "token"
})
public class RegistraOrdenesTraspasos {

    protected OrdenesTraspasos ordenesTraspasos;
    protected String token;

    /**
     * Obtiene el valor de la propiedad ordenesTraspasos.
     * 
     * @return
     *     possible object is
     *     {@link OrdenesTraspasos }
     *     
     */
    public OrdenesTraspasos getOrdenesTraspasos() {
        return ordenesTraspasos;
    }

    /**
     * Define el valor de la propiedad ordenesTraspasos.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdenesTraspasos }
     *     
     */
    public void setOrdenesTraspasos(OrdenesTraspasos value) {
        this.ordenesTraspasos = value;
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
