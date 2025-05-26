package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.generico.CatGenericoDTO;
import mx.com.consolida.usuario.UsuarioDTO;

public class NominaEstatusDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNominaEstatus;
	
	private NominaDto nominaDto;
	
	private CatGenericoDTO catEstatusNomina;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	public Long getIdNominaEstatus() {
		return idNominaEstatus;
	}

	public void setIdNominaEstatus(Long idNominaEstatus) {
		this.idNominaEstatus = idNominaEstatus;
	}

	public NominaDto getNominaDto() {
		return nominaDto;
	}

	public void setNominaDto(NominaDto nominaDto) {
		this.nominaDto = nominaDto;
	}

	public CatGenericoDTO getCatEstatusNomina() {
		return catEstatusNomina;
	}

	public void setCatEstatusNomina(CatGenericoDTO catEstatusNomina) {
		this.catEstatusNomina = catEstatusNomina;
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
	
	
}
