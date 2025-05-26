package mx.com.consolida.crm.dto;

import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class ClienteActProfSatDto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idClienteActProfSat;

	private CatGeneralDto catActProfSat;

	private ClienteDto cliente;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Date fechaAlta;

	private Date fechaModificacion;

	private Long indEstatus;

	public Long getIdClienteActProfSat() {
		return idClienteActProfSat;
	}

	public void setIdClienteActProfSat(Long idClienteActProfSat) {
		this.idClienteActProfSat = idClienteActProfSat;
	}

	public CatGeneralDto getCatActProfSat() {
		return catActProfSat;
	}

	public void setCatActProfSat(CatGeneralDto catActProfSat) {
		this.catActProfSat = catActProfSat;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
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
