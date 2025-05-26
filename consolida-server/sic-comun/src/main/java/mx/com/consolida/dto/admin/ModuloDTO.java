package mx.com.consolida.dto.admin;

import java.io.Serializable;
import java.util.Date;

public class ModuloDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idModulo;
	private String nombre;
	private String descripcion;
	private Date fechaAlta;
	private String indEstatus;
	private Long idRolModulo;
	private Long idRol;
	private Boolean asignado;
	
	
	public ModuloDTO() {
		
	}


	public ModuloDTO(Long idModulo, String nombre, String descripcion, Date fechaAlta, String indEstatus) {
		super();
		this.idModulo = idModulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.indEstatus = indEstatus;
	}
	
	
	public ModuloDTO(Long idModulo, String nombre, String descripcion, Date fechaAlta, String indEstatus, Long idRolModulo , Boolean asignado , Long idRol) {
		super();
		this.idModulo = idModulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.indEstatus = indEstatus;
		this.idRolModulo = idRolModulo;
		this.asignado = asignado;
		this.idRol = idRol;
	}
	
	public Long getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Long getIdRolModulo() {
		return idRolModulo;
	}
	
	public void setIdRolModulo(Long idRolModulo) {
		this.idRolModulo = idRolModulo;
	}

	public Boolean getAsignado() {
		return asignado;
	}

	public void setAsignado(Boolean asignado) {
		this.asignado = asignado;
	}


	public Long getIdRol() {
		return idRol;
	}


	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	
	
	
}
