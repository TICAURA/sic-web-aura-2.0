
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para traspasoWS complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="traspasoWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="folioOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institucionCASFIM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institucionSpei" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="medioEntrega" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tipoTraspaso" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tsLiquidacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "traspasoWS", propOrder = {
    "claveRastreo",
    "estado",
    "fechaOperacion",
    "folioOrigen",
    "institucionCASFIM",
    "institucionSpei",
    "medioEntrega",
    "monto",
    "tipoTraspaso",
    "tsLiquidacion"
})
public class TraspasoWS {

    protected String claveRastreo;
    protected String estado;
    protected Integer fechaOperacion;
    protected String folioOrigen;
    protected String institucionCASFIM;
    protected Integer institucionSpei;
    protected Integer medioEntrega;
    protected BigDecimal monto;
    protected Integer tipoTraspaso;
    protected Long tsLiquidacion;

    /**
     * Obtiene el valor de la propiedad claveRastreo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveRastreo() {
        return claveRastreo;
    }

    /**
     * Define el valor de la propiedad claveRastreo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveRastreo(String value) {
        this.claveRastreo = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaOperacion.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * Define el valor de la propiedad fechaOperacion.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFechaOperacion(Integer value) {
        this.fechaOperacion = value;
    }

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

    /**
     * Obtiene el valor de la propiedad institucionCASFIM.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitucionCASFIM() {
        return institucionCASFIM;
    }

    /**
     * Define el valor de la propiedad institucionCASFIM.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitucionCASFIM(String value) {
        this.institucionCASFIM = value;
    }

    /**
     * Obtiene el valor de la propiedad institucionSpei.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucionSpei() {
        return institucionSpei;
    }

    /**
     * Define el valor de la propiedad institucionSpei.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucionSpei(Integer value) {
        this.institucionSpei = value;
    }

    /**
     * Obtiene el valor de la propiedad medioEntrega.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMedioEntrega() {
        return medioEntrega;
    }

    /**
     * Define el valor de la propiedad medioEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMedioEntrega(Integer value) {
        this.medioEntrega = value;
    }

    /**
     * Obtiene el valor de la propiedad monto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Define el valor de la propiedad monto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonto(BigDecimal value) {
        this.monto = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoTraspaso.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoTraspaso() {
        return tipoTraspaso;
    }

    /**
     * Define el valor de la propiedad tipoTraspaso.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoTraspaso(Integer value) {
        this.tipoTraspaso = value;
    }

    /**
     * Obtiene el valor de la propiedad tsLiquidacion.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTsLiquidacion() {
        return tsLiquidacion;
    }

    /**
     * Define el valor de la propiedad tsLiquidacion.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTsLiquidacion(Long value) {
        this.tsLiquidacion = value;
    }

}
