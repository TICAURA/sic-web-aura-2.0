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

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_nomina_estatus")
@NamedQueries({ @NamedQuery(name = "PppNominaEstatus.findAll", query = "SELECT c FROM PppNominaEstatus c") })
public class PppNominaEstatus implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ppp_nomina_estatus")
	private Long idPppNominaEstatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina", nullable = false)
	private PppNomina pppNomina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_estatus_nomina", nullable = false)
	private CatEstatusNomina catEstatusNomina;

	@Column(name = "observacion", length = 1000)
	private String observacion;

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
	private String indEstatus;

	public Long getIdPppNominaEstatus() {
		return idPppNominaEstatus;
	}

	public void setIdPppNominaEstatus(Long idPppNominaEstatus) {
		this.idPppNominaEstatus = idPppNominaEstatus;
	}

	public PppNomina getPppNomina() {
		return pppNomina;
	}

	public void setPppNomina(PppNomina pppNomina) {
		this.pppNomina = pppNomina;
	}

	public CatEstatusNomina getCatEstatusNomina() {
		return catEstatusNomina;
	}

	public void setCatEstatusNomina(CatEstatusNomina catEstatusNomina) {
		this.catEstatusNomina = catEstatusNomina;
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

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



}
