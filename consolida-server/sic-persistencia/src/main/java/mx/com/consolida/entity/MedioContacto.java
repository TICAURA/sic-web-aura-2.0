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
@Table(name="medio_contacto")
@NamedQuery(name="MedioContacto.findAll", query="SELECT c FROM MedioContacto c")
public class MedioContacto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_medio_contacto")
	private int idMedioContacto;
	@Column(name="codigo_postal")
	private String codigoPostal;
	@Column(name="calle")
	private String calle;
	@Column(name="numero_calle")
	private String numeroCalle;
	@Column(name="estado")
	private String estado;
	@Column(name="alcaldia")
	private String alcaldia;
	@Column(name="colonia")
	private String colonia;
	@Column(name="correo_electronico")
	private String correoElectronico;
	@Column(name="fecha_alta")
	private Date fechaAlta;
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;
	@Column(name="usuario_alta")
	private String usuarioAlta;
	@Column(name="telefono")
	private String telefono;


	public int getIdMedioContacto() {
		return idMedioContacto;
	}
	public void setIdMedioContacto(int idMedioContacto) {
		this.idMedioContacto = idMedioContacto;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumeroCalle() {
		return numeroCalle;
	}
	public void setNumeroCalle(String numeroCalle) {
		this.numeroCalle = numeroCalle;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getAlcaldia() {
		return alcaldia;
	}
	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


}
