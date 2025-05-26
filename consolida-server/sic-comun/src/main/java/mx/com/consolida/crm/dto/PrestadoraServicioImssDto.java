package mx.com.consolida.crm.dto;

import java.io.Serializable;

import mx.com.consolida.usuario.UsuarioDTO;

public class PrestadoraServicioImssDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioImss;

	private PrestadoraServicioDto prestadoraServicio;

	private String usuario;

	private String password;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Long indEstatus;

	public PrestadoraServicioImssDto() {

	}

	public PrestadoraServicioImssDto(Long idPrestadoraServicioImss) {
		this.idPrestadoraServicioImss = idPrestadoraServicioImss;
	}

	public Long getIdPrestadoraServicioImss() {
		return idPrestadoraServicioImss;
	}

	public void setIdPrestadoraServicioImss(Long idPrestadoraServicioImss) {
		this.idPrestadoraServicioImss = idPrestadoraServicioImss;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

}
