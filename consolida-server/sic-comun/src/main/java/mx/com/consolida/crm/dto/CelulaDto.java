package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;


public class CelulaDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idCelula;
	private String clave;
	private String nombre;
	private Date fechaAlta;
	private String fechaAltaFormatter;
	private Date fechaModificacion;
	private Long usuarioAlta;
	private Long usuarioModificacion;
	private String indEstatus;
	
	private UsuarioDTO administrador;
	private UsuarioDTO usuario;
	
	public CelulaDto() {

	}
	
	public CelulaDto(Long idCelula) {
		this.idCelula = idCelula;
	}
	
	public CelulaDto(Long idCelula, String clave, String nombre) {
		this.idCelula = idCelula;
		this.clave = clave;
		this.nombre = nombre;
	}
	
	public Long getIdCelula() {
		return idCelula;
	}
	public void setIdCelula(Long idCelula) {
		this.idCelula = idCelula;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaAltaFormatter() {
		return fechaAltaFormatter;
	}
	public void setFechaAltaFormatter(String fechaAltaFormatter) {
		this.fechaAltaFormatter = fechaAltaFormatter;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Long getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public UsuarioDTO getAdministrador() {
		return administrador;
	}
	public void setAdministrador(UsuarioDTO administrador) {
		this.administrador = administrador;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	
	
}
