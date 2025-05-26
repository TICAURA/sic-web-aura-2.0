package mx.com.consolida.crm.dto;

import java.util.Date;



public class ClienteNoministaDto implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idClienteNominista;
	private Long idCliente;
	private Long idUsuarioNominista;
	private Long idUsuarioAlta;
	private Long idUsuarioModificacion;
	private Date fechaAlta;
	private Date fechaModificacion;
	private Long indEstatus;
	public Long getIdClienteNominista() {
		return idClienteNominista;
	}
	public void setIdClienteNominista(Long idClienteNominista) {
		this.idClienteNominista = idClienteNominista;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdUsuarioNominista() {
		return idUsuarioNominista;
	}
	public void setIdUsuarioNominista(Long idUsuarioNominista) {
		this.idUsuarioNominista = idUsuarioNominista;
	}
	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}
	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}
	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}
	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
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
	public Long getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	


}
