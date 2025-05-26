package mx.com.consolida.generico;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CatGenericoDTO implements Serializable {
	
	private Integer id;
	private String clave;
	private String descripcion;
	
	public CatGenericoDTO() {
		
	}
	
	public CatGenericoDTO(Integer id, String clave, String descripcion) {
		this.id = id;
		this.clave = clave;
		this.descripcion = descripcion;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
