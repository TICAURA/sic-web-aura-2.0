package mx.com.consolida.dto.admin;

import java.io.Serializable;

public class RolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long idRol;
	private String nombre;
	private String descripcion;
	private String indEstatus;
	
	public RolDTO() {
		
	}
	
	public RolDTO(Long idRol) {
		super();
		this.idRol = idRol;
	}
			
	public RolDTO(Long idRol, String nombre, String descripcion, String indEstatus) {
		super();
		this.idRol = idRol;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.indEstatus = indEstatus;
	}
	
	
	public Long getIdRol() {
		return idRol;
	}
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
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
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	 
}
