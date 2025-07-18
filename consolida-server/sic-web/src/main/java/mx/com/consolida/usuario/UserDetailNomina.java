package mx.com.consolida.usuario;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.com.consolida.usuario.UsuarioRolDto;

@SuppressWarnings("serial")
public class UserDetailNomina implements UserDetails {

	private Long idUsuario;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private List<UsuarioRolDto> usuarioRols;
	private UsuarioRolDto rolSeleccionado;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public int hashCode() {
		return username == null ? super.hashCode() : username.hashCode();
	}

	@Override
	public String toString() {
		return username == null ? super.toString() : username;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	public String getPassword() {
		
		return password;
	}

	public String getUsername() {
		
		return username;
	}

	public boolean isAccountNonExpired() {
		
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		
		return enabled;
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

	public UsuarioRolDto getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(UsuarioRolDto rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}
	
	
	
	
	
}