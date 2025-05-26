package mx.com.consolida.dto.admin;

import java.io.Serializable;

public class RolModuloPantallaDTO implements Serializable{
	
//	private RolDTO rol;
//	private List<ModuloDTO> modulos;
	
	private Long idRol;
	private String nombre;
	private String descripcion; 
	private Long totalModulos;
	private Long totalPantallas;
	
	

	public RolModuloPantallaDTO(Long idRol, String nombre, String descripcion, Long totalModulos, Long totalPantallas) {
		super();
		this.idRol = idRol;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.totalModulos = totalModulos;
		this.totalPantallas = totalPantallas;
	}

	public Long getTotalPantallas() {
		return totalPantallas;
	}

	public void setTotalPantallas(Long totalPantallas) {
		this.totalPantallas = totalPantallas;
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

	public Long getTotalModulos() {
		return totalModulos;
	}

	public void setTotalModulos(Long totalModulos) {
		this.totalModulos = totalModulos;
	}
	
	
}
