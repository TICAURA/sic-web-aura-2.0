package mx.com.consolida.dto;

import java.util.Date;

public class MedioContactoDto {

	private Long idMedioContacto;
	private String codigoPostal;
	private String calle;
	private String numeroCalle;
	private String numeroCalleInt;
	private String estado;
	private Integer alcaldia;
	private String colonia;
	private String correoElectronico;
	private Date fechaAlta;
	private Date fechaModificacion;
	private String usuarioAlta;
	private String telefono;
	private String nombreAlcaldia;
	private String nombreEstado;
	
	
	public Long getIdMedioContacto() {
		return idMedioContacto;
	}
	public void setIdMedioContacto(Long idMedioContacto) {
		this.idMedioContacto = idMedioContacto;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumeroCalle() {
		return numeroCalle;
	}
	public void setNumeroCalle(String numeroCalle) {
		this.numeroCalle = numeroCalle;
	}
	
	public String getNumeroCalleInt() {
		return numeroCalleInt;
	}
	public void setNumeroCalleInt(String numeroCalleInt) {
		this.numeroCalleInt = numeroCalleInt;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getAlcaldia() {
		return alcaldia;
	}
	public void setAlcaldia(Integer alcaldia) {
		this.alcaldia = alcaldia;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getNombreAlcaldia() {
		return nombreAlcaldia;
	}
	public void setNombreAlcaldia(String nombreAlcaldia) {
		this.nombreAlcaldia = nombreAlcaldia;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
}
