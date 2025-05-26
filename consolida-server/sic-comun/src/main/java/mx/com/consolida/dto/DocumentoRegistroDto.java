package mx.com.consolida.dto;

import java.util.Date;

public class DocumentoRegistroDto {

	private Long idDocumentoRegistro;
	private Long idDefinicionDocumento;
	private String idContentFile;
	private String nombreDocumento;
	private String ruta;
	private Long usuarioAlta;
	private Date fechaAlta;
	private Date fcehaModificacion;
	private Long indEstatus;
	public Long getIdDocumentoRegistro() {
		return idDocumentoRegistro;
	}
	public void setIdDocumentoRegistro(Long idDocumentoRegistro) {
		this.idDocumentoRegistro = idDocumentoRegistro;
	}
	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}
	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}
	public String getIdContentFile() {
		return idContentFile;
	}
	public void setIdContentFile(String idContentFile) {
		this.idContentFile = idContentFile;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Long getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFcehaModificacion() {
		return fcehaModificacion;
	}
	public void setFcehaModificacion(Date fcehaModificacion) {
		this.fcehaModificacion = fcehaModificacion;
	}
	public Long getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	
}
