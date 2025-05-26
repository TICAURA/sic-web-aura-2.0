package mx.com.facturacion.factura;

import java.io.Serializable;

public class FacturaUrlDTO implements Serializable {
	
	private Integer idFactura;
	private String urlFacturaXML;
	private String urlFacturaPDF;
	
	public Integer getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}
	public String getUrlFacturaXML() {
		return urlFacturaXML;
	}
	public void setUrlFacturaXML(String urlFacturaXML) {
		this.urlFacturaXML = urlFacturaXML;
	}
	public String getUrlFacturaPDF() {
		return urlFacturaPDF;
	}
	public void setUrlFacturaPDF(String urlFacturaPDF) {
		this.urlFacturaPDF = urlFacturaPDF;
	}
}
