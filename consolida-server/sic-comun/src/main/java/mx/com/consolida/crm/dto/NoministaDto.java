package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

public class NoministaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idNominista;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompletoNominista;
	private String descripcion;
	private Date fechaAlta;
	private String indEstatus;
	private Long idCelula;
	private Long idCliente;
	private Long idPrestadoraServicio;
	private Long idPersona;
	private Long idUsuario;
	private Boolean asignado;
	public Long getIdNominista() {
		return idNominista;
	}
	public void setIdNominista(Long idNominista) {
		this.idNominista = idNominista;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	public Long getIdCelula() {
		return idCelula;
	}
	public void setIdCelula(Long idCelula) {
		this.idCelula = idCelula;
	}
	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}
	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}
	public Boolean getAsignado() {
		return asignado;
	}
	public void setAsignado(Boolean asignado) {
		this.asignado = asignado;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCompletoNominista() {
		return nombreCompletoNominista;
	}
	public void setNombreCompletoNominista(String nombreCompletoNominista) {
		this.nombreCompletoNominista = nombreCompletoNominista;
	}

	
}
