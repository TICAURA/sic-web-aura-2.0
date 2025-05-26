package mx.com.consolida.generico;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.usuario.UsuarioRolDto;

public class UsuarioAplicativo implements Serializable {
	
	private Long idUsuario;
	private String usuario;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private List<UsuarioRolDto> usuarioRols;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public List<UsuarioRolDto> getUsuarioRols() {
		return usuarioRols;
	}
	public void setUsuarioRols(List<UsuarioRolDto> usuarioRols) {
		this.usuarioRols = usuarioRols;
	}
	
}
