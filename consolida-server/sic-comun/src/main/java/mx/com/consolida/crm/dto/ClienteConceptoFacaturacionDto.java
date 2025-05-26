package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;

public class ClienteConceptoFacaturacionDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idConceptoFacturacionCliente;
	
	private String descConceptoFacturacion;
	
	private ClienteDto clienteDto;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;
	
	private Boolean esEliminar;
	
	private Long indEstatus;
	
	public ClienteConceptoFacaturacionDto() {
		
	}
	
	public ClienteConceptoFacaturacionDto(Long idConceptoFacturacionCliente) {
		this.idConceptoFacturacionCliente = idConceptoFacturacionCliente;
	}

	public Long getIdConceptoFacturacionCliente() {
		return idConceptoFacturacionCliente;
	}

	public void setIdConceptoFacturacionCliente(Long idConceptoFacturacionCliente) {
		this.idConceptoFacturacionCliente = idConceptoFacturacionCliente;
	}

	public String getDescConceptoFacturacion() {
		return descConceptoFacturacion;
	}

	public void setDescConceptoFacturacion(String descConceptoFacturacion) {
		this.descConceptoFacturacion = descConceptoFacturacion;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
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

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Boolean getEsEliminar() {
		return esEliminar;
	}

	public void setEsEliminar(Boolean esEliminar) {
		this.esEliminar = esEliminar;
	}

	
}
