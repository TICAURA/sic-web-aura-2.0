package mx.com.consolida.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="persona_contacto")
@NamedQuery(name="PersonaContacto.findAll", query="SELECT c FROM PersonaContacto c")
public class PersonaContacto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_persona_contacto")
	private int idPersonaContacto;
	@Column(name="nombre_persona")
	private String nombrePersona;
	@Column(name="correo_electronico")
	private String correoElectronico;
	@Column(name="telefono")
	private String telefono;
	@Column(name="fecha_alta")
	private Date fechaAlta;
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;
	@Column(name="usuario_alta")
	private String usuarioAlta;
	
	public int getIdPersonaContacto() {
		return idPersonaContacto;
	}
	public void setIdPersonaContacto(int idPersonaContacto) {
		this.idPersonaContacto = idPersonaContacto;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
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
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	
}
