
package com.lgec.enlacefi.spei.integration.h2h;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ordenesTraspasos complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ordenesTraspasos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenesPago" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenPagoWS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="traspasosWS" type="{http://h2h.integration.spei.enlacefi.lgec.com/}traspasoWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordenesTraspasos", propOrder = {
    "ordenesPago",
    "traspasosWS"
})
public class OrdenesTraspasos {

    @XmlElement(nillable = true)
    protected List<OrdenPagoWS> ordenesPago;
    @XmlElement(nillable = true)
    protected List<TraspasoWS> traspasosWS;

    /**
     * Gets the value of the ordenesPago property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordenesPago property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdenesPago().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdenPagoWS }
     * 
     * 
     */
    public List<OrdenPagoWS> getOrdenesPago() {
        if (ordenesPago == null) {
            ordenesPago = new ArrayList<OrdenPagoWS>();
        }
        return this.ordenesPago;
    }

    /**
     * Gets the value of the traspasosWS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the traspasosWS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTraspasosWS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TraspasoWS }
     * 
     * 
     */
    public List<TraspasoWS> getTraspasosWS() {
        if (traspasosWS == null) {
            traspasosWS = new ArrayList<TraspasoWS>();
        }
        return this.traspasosWS;
    }

}
