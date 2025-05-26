package mx.com.consolida.dto;

import java.util.Date;

public class CatEntidadFederativaDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long idEntidadFederativa;
	
	private String cveCatGeneral;
	
	private String cveEntidadFederativa;
	
	private String descripcionEntidadFederativa;
	
	private Date fechaAlta;
	
	private Date fechaModificacion;
	
	private Long usuarioAlta;
	
	private Long usuarioModificacion;
	
	private Long indEstatus;

	public CatEntidadFederativaDto() {
	}

	public CatEntidadFederativaDto(Long idEntidadFederativa, String cveEntidadFederativa,
			String descripcionEntidadFederativa, Date fechaAlta, Long usuarioAlta) {
		this.idEntidadFederativa = idEntidadFederativa;
		this.cveEntidadFederativa = cveEntidadFederativa;
		this.descripcionEntidadFederativa = descripcionEntidadFederativa;
		this.fechaAlta = fechaAlta;
		this.usuarioAlta = usuarioAlta;
	}

	public CatEntidadFederativaDto(Long idEntidadFederativa, String cveCatGeneral, String cveEntidadFederativa,
			String descripcionEntidadFederativa, Date fechaAlta, Date fechaModificacion, Long usuarioAlta,
			Long indEstatus) {
		this.idEntidadFederativa = idEntidadFederativa;
		this.cveCatGeneral = cveCatGeneral;
		this.cveEntidadFederativa = cveEntidadFederativa;
		this.descripcionEntidadFederativa = descripcionEntidadFederativa;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaModificacion;
		this.usuarioAlta = usuarioAlta;
		this.indEstatus = indEstatus;
	}


	public Long getIdEntidadFederativa() {
		return this.idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Long idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	
	public String getCveCatGeneral() {
		return this.cveCatGeneral;
	}

	public void setCveCatGeneral(String cveCatGeneral) {
		this.cveCatGeneral = cveCatGeneral;
	}

	
	public String getCveEntidadFederativa() {
		return this.cveEntidadFederativa;
	}

	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}

	
	public String getDescripcionEntidadFederativa() {
		return this.descripcionEntidadFederativa;
	}

	public void setDescripcionEntidadFederativa(String descripcionEntidadFederativa) {
		this.descripcionEntidadFederativa = descripcionEntidadFederativa;
	}


	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	
	public Long getUsuarioAlta() {
		return this.usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	
	public Long getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	


}
