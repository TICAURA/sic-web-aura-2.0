package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;

public class PrestadoraServicioRegistroPatronalDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioRegistroPatronal;

	private String registroPatronal;

	private PrestadoraServicioDto prestadoraServicioDto;

	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Boolean esEliminar;

	private Long indEstatus;

	public PrestadoraServicioRegistroPatronalDto() {

	}

	public PrestadoraServicioRegistroPatronalDto(Long idPrestadoraServicioRegistroPatronal) {
		this.idPrestadoraServicioRegistroPatronal = idPrestadoraServicioRegistroPatronal;
	}

	public Long getIdPrestadoraServicioRegistroPatronal() {
		return idPrestadoraServicioRegistroPatronal;
	}

	public void setIdPrestadoraServicioRegistroPatronal(Long idPrestadoraServicioRegistroPatronal) {
		this.idPrestadoraServicioRegistroPatronal = idPrestadoraServicioRegistroPatronal;
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}

	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
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

	public Boolean getEsEliminar() {
		return esEliminar;
	}

	public void setEsEliminar(Boolean esEliminar) {
		this.esEliminar = esEliminar;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

}
