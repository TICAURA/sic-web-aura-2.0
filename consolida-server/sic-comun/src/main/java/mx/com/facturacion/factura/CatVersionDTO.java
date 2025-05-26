package mx.com.facturacion.factura;

import java.io.Serializable;

public class CatVersionDTO implements Serializable{
	
	private Integer idCatVersion;
	private String version;
	private String indEstatus;
	public Integer getIdCatVersion() {
		return idCatVersion;
	}
	public void setIdCatVersion(Integer idCatVersion) {
		this.idCatVersion = idCatVersion;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	

}
