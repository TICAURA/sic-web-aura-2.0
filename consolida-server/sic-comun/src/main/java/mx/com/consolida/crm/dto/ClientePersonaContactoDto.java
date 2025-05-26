package mx.com.consolida.crm.dto;

import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;

public class ClientePersonaContactoDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPersonaContacto;

	private ClienteDto cliente;

	private Long idPersonaContactoTemp;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String correoElectronico;

	private String telefono;

	private Date fechaAlta;

	private Date fechaModificacion;

	private Long indEstatus;

	public Long getIdPersonaContacto() {
		return idPersonaContacto;
	}

	public void setIdPersonaContacto(Long idPersonaContacto) {
		this.idPersonaContacto = idPersonaContacto;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public Long getIdPersonaContactoTemp() {
		return idPersonaContactoTemp;
	}

	public void setIdPersonaContactoTemp(Long idPersonaContactoTemp) {
		this.idPersonaContactoTemp = idPersonaContactoTemp;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

}
