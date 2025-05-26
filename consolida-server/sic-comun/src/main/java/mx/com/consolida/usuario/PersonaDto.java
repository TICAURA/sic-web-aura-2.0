package mx.com.consolida.usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PersonaDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String rfc;
	private String curp;
	private Date fechaAlta;
	private Date fechaModificacion;
//	private String usuarioAlta;
//	private String usuarioModificacion;
	private String indEstatus;
//	private List<UsuarioDTO> usuarios;
	
	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
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
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
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
//	public String getUsuarioAlta() {
//		return usuarioAlta;
//	}
//	public void setUsuarioAlta(String usuarioAlta) {
//		this.usuarioAlta = usuarioAlta;
//	}
//	public String getUsuarioModificacion() {
//		return usuarioModificacion;
//	}
//	public void setUsuarioModificacion(String usuarioModificacion) {
//		this.usuarioModificacion = usuarioModificacion;
//	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
//	public List<UsuarioDTO> getUsuarios() {
//		return usuarios;
//	}
//	public void setUsuarios(List<UsuarioDTO> usuarios) {
//		this.usuarios = usuarios;
//	}
	
	
}
