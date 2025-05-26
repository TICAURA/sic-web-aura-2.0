package mx.com.consolida.dto.admin;

import java.io.Serializable;
import java.util.Date;


public class PantallaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPantalla;
	private String indEstatus;
	private String nombre;
	private String rutaPantalla;
	private String icono;
	private Date fechaAlta;
	private Long idRolPantalla;
	private Long idModuloPantalla;
	private Long idModulo;
	private Long idRol;
	private Boolean asignado;
	
	
	public PantallaDTO() {
		
	}
	
	public PantallaDTO(Long idPantalla, String indEstatus, String nombre, String rutaPantalla,String icono, Date fechaAlta,
			Long idRolPantalla  , Long idRol , Boolean asignado) {
		super();
		this.idPantalla = idPantalla;
		this.indEstatus = indEstatus;
		this.nombre = nombre;
		this.rutaPantalla = rutaPantalla;
		this.icono = icono;
		this.fechaAlta = fechaAlta;
		this.idRolPantalla = idRolPantalla;
		this.idRol = idRol;
		this.asignado = asignado;
	}
	
	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public PantallaDTO(Long idPantalla, String indEstatus, String nombre, String rutaPantalla,String icono, Date fechaAlta,
			 Long idModuloPantalla ,Boolean asignado, Long idModulo) {
		super();
		this.idPantalla = idPantalla;
		this.indEstatus = indEstatus;
		this.nombre = nombre;
		this.rutaPantalla = rutaPantalla;
		this.icono = icono;
		this.fechaAlta = fechaAlta;
		this.idModuloPantalla = idModuloPantalla;
		this.asignado = asignado;
		this.idModulo = idModulo;
		
	}
	
	public Long getIdPantalla() {
		return idPantalla;
	}
	public void setIdPantalla(Long idPantalla) {
		this.idPantalla = idPantalla;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRutaPantalla() {
		return rutaPantalla;
	}
	public void setRutaPantalla(String rutaPantalla) {
		this.rutaPantalla = rutaPantalla;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Long getIdRolPantalla() {
		return idRolPantalla;
	}
	public void setIdRolPantalla(Long idRolPantalla) {
		this.idRolPantalla = idRolPantalla;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public Long getIdModuloPantalla() {
		return idModuloPantalla;
	}

	public void setIdModuloPantalla(Long idModuloPantalla) {
		this.idModuloPantalla = idModuloPantalla;
	}

	public Boolean getAsignado() {
		return asignado;
	}

	public void setAsignado(Boolean asignado) {
		this.asignado = asignado;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	
	
}
