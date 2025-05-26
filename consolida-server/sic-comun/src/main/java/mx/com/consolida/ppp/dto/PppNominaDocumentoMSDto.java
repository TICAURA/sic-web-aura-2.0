package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.Date;

public class PppNominaDocumentoMSDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPppNominaDocumento;

	private Long idPppNomina;

	private Long idDefinicionDocumento;
	
	private Long idCMS;
	
	private String nombreArchivo;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private Long usuarioAlta;

	private Long usuarioModificacion;

	private String indEstatus;

	public Long getIdPppNominaDocumento() {
		return idPppNominaDocumento;
	}

	public void setIdPppNominaDocumento(Long idPppNominaDocumento) {
		this.idPppNominaDocumento = idPppNominaDocumento;
	}

	public Long getIdPppNomina() {
		return idPppNomina;
	}

	public void setIdPppNomina(Long idPppNomina) {
		this.idPppNomina = idPppNomina;
	}

	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}

	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}

	public Long getIdCMS() {
		return idCMS;
	}

	public void setIdCMS(Long idCMS) {
		this.idCMS = idCMS;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

}
