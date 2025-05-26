package mx.com.consolida.crm.dto;

import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;


public class DomicilioDto implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDomicilio;
	
	private Long idEntidadFederativa;
	
	private CatGeneralDto catTipoDomicilio;
	
	private CatGeneralDto catMunicipios;
	
	private CatGeneralDto catEntidadFederativa;
	
//	private UsuarioDTO usuarioByUsuarioModificacion;
//	
//	private UsuarioDTO usuarioByUsaurioAlta;
	
	private String calle;
	
	private String numeroExterior;
	
	private String numeroInterior;
	
	private String colonia;
	
	private String codigoPostal;
	
	private Date fechaAlta;
	
	private Date fechaModificacion;
	
	private Long indEstatus;

	public Long getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public Long getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Long idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public CatGeneralDto getCatTipoDomicilio() {
		return catTipoDomicilio;
	}

	public void setCatTipoDomicilio(CatGeneralDto catTipoDomicilio) {
		this.catTipoDomicilio = catTipoDomicilio;
	}

	public CatGeneralDto getCatMunicipios() {
		return catMunicipios;
	}

	public void setCatMunicipios(CatGeneralDto catMunicipios) {
		this.catMunicipios = catMunicipios;
	}

	public CatGeneralDto getCatEntidadFederativa() {
		return catEntidadFederativa;
	}

	public void setCatEntidadFederativa(CatGeneralDto catEntidadFederativa) {
		this.catEntidadFederativa = catEntidadFederativa;
	}

//	public UsuarioDTO getUsuarioByUsuarioModificacion() {
//		return usuarioByUsuarioModificacion;
//	}
//
//	public void setUsuarioByUsuarioModificacion(UsuarioDTO usuarioByUsuarioModificacion) {
//		this.usuarioByUsuarioModificacion = usuarioByUsuarioModificacion;
//	}
//
//	public UsuarioDTO getUsuarioByUsaurioAlta() {
//		return usuarioByUsaurioAlta;
//	}
//
//	public void setUsuarioByUsaurioAlta(UsuarioDTO usuarioByUsaurioAlta) {
//		this.usuarioByUsaurioAlta = usuarioByUsaurioAlta;
//	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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
