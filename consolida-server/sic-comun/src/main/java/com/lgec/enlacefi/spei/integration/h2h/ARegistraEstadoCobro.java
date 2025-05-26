
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para aRegistraEstadoCobro complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="aRegistraEstadoCobro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serieCertificado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveInst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsSolicitudOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "aRegistraEstadoCobro", propOrder = {
    "cadenaOriginal",
    "serieCertificado",
    "claveInst",
    "tsSolicitudOriginal",
    "firma",
    "token"
})
public class ARegistraEstadoCobro {

    protected String cadenaOriginal;
    protected String serieCertificado;
    protected String claveInst;
    protected String tsSolicitudOriginal;
    protected String firma;
    protected String token;

    /**
     * Obtiene el valor de la propiedad cadenaOriginal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * Define el valor de la propiedad cadenaOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadenaOriginal(String value) {
        this.cadenaOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad serieCertificado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerieCertificado() {
        return serieCertificado;
    }

    /**
     * Define el valor de la propiedad serieCertificado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerieCertificado(String value) {
        this.serieCertificado = value;
    }

    /**
     * Obtiene el valor de la propiedad claveInst.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveInst() {
        return claveInst;
    }

    /**
     * Define el valor de la propiedad claveInst.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveInst(String value) {
        this.claveInst = value;
    }

    /**
     * Obtiene el valor de la propiedad tsSolicitudOriginal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsSolicitudOriginal() {
        return tsSolicitudOriginal;
    }

    /**
     * Define el valor de la propiedad tsSolicitudOriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsSolicitudOriginal(String value) {
        this.tsSolicitudOriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad firma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Define el valor de la propiedad firma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirma(String value) {
        this.firma = value;
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
