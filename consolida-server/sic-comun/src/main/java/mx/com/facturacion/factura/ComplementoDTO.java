package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ComplementoDTO implements Serializable {
    
    protected String version;
    protected String uuid;
    protected String fechaTimbrado;
    protected String selloCFD;
    protected String noCertificadoSAT;
    protected String selloSAT;
    protected String codigoQR;
    protected String cadenaOriginalSAT;
    
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFechaTimbrado() {
		return fechaTimbrado;
	}
	public void setFechaTimbrado(String fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}
	public String getSelloCFD() {
		return selloCFD;
	}
	public void setSelloCFD(String selloCFD) {
		this.selloCFD = selloCFD;
	}
	public String getNoCertificadoSAT() {
		return noCertificadoSAT;
	}
	public void setNoCertificadoSAT(String noCertificadoSAT) {
		this.noCertificadoSAT = noCertificadoSAT;
	}
	public String getSelloSAT() {
		return selloSAT;
	}
	public void setSelloSAT(String selloSAT) {
		this.selloSAT = selloSAT;
	}
	public String getCodigoQR() {
		return codigoQR;
	}
	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}
	public String getCadenaOriginalSAT() {
		return cadenaOriginalSAT;
	}
	public void setCadenaOriginalSAT(String cadenaOriginalSAT) {
		this.cadenaOriginalSAT = cadenaOriginalSAT;
	}
	
	
	
}
