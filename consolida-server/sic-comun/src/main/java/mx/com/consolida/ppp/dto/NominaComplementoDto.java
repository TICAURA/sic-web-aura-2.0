package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.usuario.UsuarioDTO;

public class NominaComplementoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNominaComplemento;

	private NominaDto nominaDto;

	private Date fechaFacturacion;

	private Date fechaTimbrado;

	private Date fechaDispersion;

	private Boolean requiereFianciamiento = false;

	private BigDecimal montoFinanciamiento;

	private BigDecimal montoTotalPpp;

	private Date fechaAlta;

	private String observaciones;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Boolean traeComplemento;

	// documento
	private Long idCMS;
	private String nombreArchivoCarga;
	private String nombreArchivo;
	private String usuario;
	private DocumentoDTO documentoNuevo;
	private Boolean nomComplementaria = false;

	private Boolean validaComprobante = false;

	public NominaComplementoDto() {

	}

	public NominaComplementoDto(Long idNominaComplemento) {
		this.idNominaComplemento = idNominaComplemento;
	}

	public Long getIdNominaComplemento() {
		return idNominaComplemento;
	}

	public void setIdNominaComplemento(Long idNominaComplemento) {
		this.idNominaComplemento = idNominaComplemento;
	}

	public NominaDto getNominaDto() {
		return nominaDto;
	}

	public void setNominaDto(NominaDto nominaDto) {
		this.nominaDto = nominaDto;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Date getFechaTimbrado() {
		return fechaTimbrado;
	}

	public void setFechaTimbrado(Date fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	public Date getFechaDispersion() {
		return fechaDispersion;
	}

	public void setFechaDispersion(Date fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}

	public Boolean getRequiereFianciamiento() {
		return requiereFianciamiento;
	}

	public void setRequiereFianciamiento(Boolean requiereFianciamiento) {
		this.requiereFianciamiento = requiereFianciamiento;
	}

	public BigDecimal getMontoFinanciamiento() {
		return montoFinanciamiento;
	}

	public void setMontoFinanciamiento(BigDecimal montoFinanciamiento) {
		this.montoFinanciamiento = montoFinanciamiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public DocumentoDTO getDocumentoNuevo() {
		return documentoNuevo;
	}

	public void setDocumentoNuevo(DocumentoDTO documentoNuevo) {
		this.documentoNuevo = documentoNuevo;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombreArchivoCarga() {
		return nombreArchivoCarga;
	}

	public void setNombreArchivoCarga(String nombreArchivoCarga) {
		this.nombreArchivoCarga = nombreArchivoCarga;
	}

	public BigDecimal getMontoTotalPpp() {
		return montoTotalPpp;
	}

	public void setMontoTotalPpp(BigDecimal montoTotalPpp) {
		this.montoTotalPpp = montoTotalPpp;
	}

	public Boolean getNomComplementaria() {
		return nomComplementaria;
	}

	public void setNomComplementaria(Boolean nomComplementaria) {
		this.nomComplementaria = nomComplementaria;
	}

	public Boolean getValidaComprobante() {
		return validaComprobante;
	}

	public void setValidaComprobante(Boolean validaComprobante) {
		this.validaComprobante = validaComprobante;
	}

	public Boolean getTraeComplemento() {
		return traeComplemento;
	}

	public void setTraeComplemento(Boolean traeComplemento) {
		this.traeComplemento = traeComplemento;
	}
	
	

}
