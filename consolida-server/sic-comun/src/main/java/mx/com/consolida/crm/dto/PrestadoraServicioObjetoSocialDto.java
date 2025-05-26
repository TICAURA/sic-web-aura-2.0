package mx.com.consolida.crm.dto;

import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class PrestadoraServicioObjetoSocialDto {

	private Long idPrestadoraServicioObjetoSocial;
	
	private String descripcion;

	private PrestadoraServicioDto prestadoraServicio;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Date fechaAlta;

	private Date fechaModificacion;
	
	private Boolean esEliminar;
	
	public PrestadoraServicioObjetoSocialDto() {
		
	}
	
	public PrestadoraServicioObjetoSocialDto(Long idPrestadoraServicioObjetoSocial) {
		this.idPrestadoraServicioObjetoSocial = idPrestadoraServicioObjetoSocial;
	}

	public Long getIdPrestadoraServicioObjetoSocial() {
		return idPrestadoraServicioObjetoSocial;
	}

	public void setIdPrestadoraServicioObjetoSocial(Long idPrestadoraServicioObjetoSocial) {
		this.idPrestadoraServicioObjetoSocial = idPrestadoraServicioObjetoSocial;
	}


	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEsEliminar() {
		return esEliminar;
	}

	public void setEsEliminar(Boolean esEliminar) {
		this.esEliminar = esEliminar;
	}


}
