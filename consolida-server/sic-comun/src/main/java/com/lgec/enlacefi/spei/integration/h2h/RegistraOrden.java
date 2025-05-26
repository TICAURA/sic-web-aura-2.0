
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para registraOrden complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="registraOrden">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenPago" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenPagoWS" minOccurs="0"/>
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
@XmlType(name = "registraOrden", propOrder = {
    "ordenPago",
    "token"
})
public class RegistraOrden {

    protected OrdenPagoWS ordenPago;
    protected String token;

    /**
     * Obtiene el valor de la propiedad ordenPago.
     * 
     * @return
     *     possible object is
     *     {@link OrdenPagoWS }
     *     
     */
    public OrdenPagoWS getOrdenPago() {
        return ordenPago;
    }

    /**
     * Define el valor de la propiedad ordenPago.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdenPagoWS }
     *     
     */
    public void setOrdenPago(OrdenPagoWS value) {
        this.ordenPago = value;
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
