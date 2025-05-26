package mx.com.documento;

import java.io.Serializable;

public class DefinicionDocumentoDto implements Serializable {
	
	private Long idDefinicionDocumento;
	private String claveDocumento;
	private String indEstatus;	
	private String nombreDocumento;
	private String urlBase;
	private String tiposDocumentos;
	
	public DefinicionDocumentoDto() {
		
	}
	
	public DefinicionDocumentoDto(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}
	
	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}
	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}
	public String getClaveDocumento() {
		return claveDocumento;
	}
	public void setClaveDocumento(String claveDocumento) {
		this.claveDocumento = claveDocumento;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getUrlBase() {
		return urlBase;
	}
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(String tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}
	
	
}
