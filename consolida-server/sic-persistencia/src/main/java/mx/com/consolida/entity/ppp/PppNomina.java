package mx.com.consolida.entity.ppp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_nomina")
@NamedQueries({ @NamedQuery(name = "PppNomina.findAll", query = "SELECT c FROM PppNomina c") })
public class PppNomina implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_nomina")
	private Long idPppNomina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nomina_cliente", nullable = false)
	private NominaCliente nominaCliente;
	
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "consecutivo")
	private Integer consecutivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio_nomina", length = 19)
	private Date fechaInicioNomina;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin_nomina", length = 19)
	private Date fechaFinNomina;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;

	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;
	
	@Column(name="id_periodicidad")
	private Long idPeriodicidad;
	
	public PppNomina() {
		
	}
	
	public PppNomina(Long idPppNomina) {
		this.idPppNomina = idPppNomina;
	}

	public Long getIdPppNomina() {
		return idPppNomina;
	}

	public void setIdPppNomina(Long idPppNomina) {
		this.idPppNomina = idPppNomina;
	}

	public NominaCliente getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(NominaCliente nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicioNomina() {
		return fechaInicioNomina;
	}

	public void setFechaInicioNomina(Date fechaInicioNomina) {
		this.fechaInicioNomina = fechaInicioNomina;
	}

	public Date getFechaFinNomina() {
		return fechaFinNomina;
	}

	public void setFechaFinNomina(Date fechaFinNomina) {
		this.fechaFinNomina = fechaFinNomina;
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

	public Usuario getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Usuario usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Usuario getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Usuario usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Integer getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Long getIdPeriodicidad() {
		return idPeriodicidad;
	}

	public void setIdPeriodicidad(Long idPeriodicidad) {
		this.idPeriodicidad = idPeriodicidad;
	}
}
