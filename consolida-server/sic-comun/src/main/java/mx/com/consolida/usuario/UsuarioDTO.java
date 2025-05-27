package mx.com.consolida.usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.admin.RolDTO;


public class UsuarioDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	private Long idPersona;
	private Long idUsuarioRol;
	private Long idUsuarioCelula;
	private String usuario;
	private Date fechaAlta;
	private String password;
	private String confirmPassword;
	
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String indEstatus;
	private String correo;
	private String telefono;
	
	private String rfc;
	
	private Date fechaModificacion;
	private Integer reintentos;
	private PersonaDto persona;
	private List<UsuarioRolDto> usuarioRols;
	private RolDTO rol;
	private CelulaDto celula;
	
	
	private CanalVentaDto canalVentaDto;
	
	private String nombreCompleto;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Long idUsuario) {
		super();
		this.idUsuario = idUsuario;
	}

	public UsuarioDTO(Long idUsuario, Long idPersona, String usuario, Date fechaAlta,  String nombre, String primerApellido, String segundoApellido, String indEstatus) {
		super();
		this.idUsuario = idUsuario;
		this.idPersona = idPersona;
		this.usuario = usuario;
//		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.indEstatus = indEstatus;
	}
	
	
	public UsuarioDTO(Long idUsuario, Long idPersona, String usuario, Date fechaAlta,  String nombre, String primerApellido, String segundoApellido, String indEstatus, Long idUsuarioRol , Long idRol,String nombreRol, String descripcionRol) {
		super();
		this.idUsuario = idUsuario;
		this.idPersona = idPersona;
		this.usuario = usuario;
//		this.fechaAlta = fechaAlta;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.indEstatus = indEstatus;
		this.idUsuarioRol = idUsuarioRol;
		this.getRol().setIdRol(idRol);
		this.getRol().setNombre(nombreRol);
		this.getRol().setDescripcion(descripcionRol);
	}
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
//	public Date getFechaAlta() {
//		return fechaAlta;
//	}
//	public void setFechaAlta(Date fechaAlta) {
//		this.fechaAlta = fechaAlta;
//	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public RolDTO getRol() {
		
		if(rol == null) {
			this.rol = new RolDTO();
		}
		
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public Long getIdUsuarioRol() {
		return idUsuarioRol;
	}

	public void setIdUsuarioRol(Long idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public Long getIdUsuarioCelula() {
		return idUsuarioCelula;
	}

	public void setIdUsuarioCelula(Long idUsuarioCelula) {
		this.idUsuarioCelula = idUsuarioCelula;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getReintentos() {
		return reintentos;
	}

	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}

	public PersonaDto getPersona() {
		return persona;
	}

	public void setPersona(PersonaDto persona) {
		this.persona = persona;
	}


	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	

	public List<UsuarioRolDto> getUsuarioRols() {
		return usuarioRols;
	}

	public void setUsuarioRols(List<UsuarioRolDto> usuarioRols) {
		this.usuarioRols = usuarioRols;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public CanalVentaDto getCanalVentaDto() {
		return canalVentaDto;
	}

	public void setCanalVentaDto(CanalVentaDto canalVentaDto) {
		this.canalVentaDto = canalVentaDto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public CelulaDto getCelula() {
		return celula;
	}

	public void setCelula(CelulaDto celula) {
		this.celula = celula;
	}
	
		
}
