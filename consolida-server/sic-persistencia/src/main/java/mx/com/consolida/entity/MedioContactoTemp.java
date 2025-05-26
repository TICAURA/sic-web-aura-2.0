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
@Table(name = "medio_contacto_temp")
@NamedQuery(name = "MedioContactoTemp.findAll", query = "SELECT c FROM MedioContactoTemp c")
public class MedioContactoTemp implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_medio_contacto_temp")
	private Long idMedioContactoTemp;
	@Column(name = "codigo_postal")
	private String codigoPostal;
	@Column(name = "calle")
	private String calle;
	@Column(name = "numero_calle")
	private String numeroCalle;

	@Column(name = "numero_calle_int")
	private String numeroCalleInt;

	@Column(name = "id_entidad_federativa")
	private String estado;

	@Column(name = "id_municipio")
	private Long alcaldia;

	@Column(name = "colonia")
	private String colonia;

	@Column(name = "correo_electronico")
	private String correoElectronico;
	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	@Column(name = "usuario_alta")
	private Long usuarioAlta;
	@Column(name = "usuario_modificacion")
	private Long usuarioModificacion;
	@Column(name = "telefono")
	private String telefono;

//// CATALOGOS NO MAPEADOS
//	private CatEntidadFederativa catEntidadFederativa;
//	private CatMunicipios catMunicipios;

	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	public Long getIdMedioContactoTemp() {
		return idMedioContactoTemp;
	}

	public void setIdMedioContacto(Long idMedioContactoTemp) {
		this.idMedioContactoTemp = idMedioContactoTemp;
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

	public Long getAlcaldia() {
		return alcaldia;
	}

	public void setAlcaldia(Long alcaldia) {
		this.alcaldia = alcaldia;
	}

	public void setIdMedioContactoTemp(Long idMedioContactoTemp) {
		this.idMedioContactoTemp = idMedioContactoTemp;
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

	public Long getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNumeroCalleInt() {
		return numeroCalleInt;
	}

	public void setNumeroCalleInt(String numeroCalleInt) {
		this.numeroCalleInt = numeroCalleInt;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}



}
