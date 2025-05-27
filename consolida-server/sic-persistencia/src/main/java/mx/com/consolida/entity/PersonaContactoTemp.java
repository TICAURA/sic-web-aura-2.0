package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="persona_contacto_temp")
@NamedQuery(name="PersonaContactoTemp.findAll", query="SELECT c FROM PersonaContactoTemp c")
public class PersonaContactoTemp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_persona_contacto_temp")
	private Long idPersonaContactoTemp;
	@Column(name="nombre_persona")
	private String nombrePersona;
	@Column(name="apellido_paterno")
	private String apellidoPaterno;
	@Column(name="apellido_materno")
	private String apellidoMaterno;
	@Column(name="correo_electronico")
	private String correoElectronico;
	@Column(name="telefono")
	private String telefono;
	@Column(name="fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	@Column(name="fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	@Column(name="usuario_alta")
	private Long usuarioAlta;
	@Column(name="usuario_modificacion")
	private Long usuarioModificacion;
	@Column(name="ind_estatus")
	private Long indEstatus;
	
	
	public Long getIdPersonaContactoTemp() {
		return idPersonaContactoTemp;
	}
	public void setIdPersonaContacto(Long idPersonaContactoTemp) {
		this.idPersonaContactoTemp = idPersonaContactoTemp;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
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
	public void setIdPersonaContactoTemp(Long idPersonaContactoTemp) {
		this.idPersonaContactoTemp = idPersonaContactoTemp;
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
	public Long getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

}
