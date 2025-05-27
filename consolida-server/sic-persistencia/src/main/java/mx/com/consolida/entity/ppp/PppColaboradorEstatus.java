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
@Table(name = "ppp_colaborador_estatus")
@NamedQueries({ @NamedQuery(name = "PppColaboradorEstatus.findAll", query = "SELECT c FROM PppColaboradorEstatus c") })
public class PppColaboradorEstatus implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_colaborador_estatus")
	private Long idPppColaboradorEstatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_colaborador", nullable = false)
	private PppColaborador pppColaborador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_estatus_colaborador", nullable = false)
	private CatEstatusColaborador catEstatusColaborador;
	
	@Column(name = "observacion")
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
	private Long indEstatus;

	public Long getIdPppColaboradorEstatus() {
		return idPppColaboradorEstatus;
	}

	public void setIdPppColaboradorEstatus(Long idPppColaboradorEstatus) {
		this.idPppColaboradorEstatus = idPppColaboradorEstatus;
	}

	public PppColaborador getPppColaborador() {
		return pppColaborador;
	}

	public void setPppColaborador(PppColaborador pppColaborador) {
		this.pppColaborador = pppColaborador;
	}

	public CatEstatusColaborador getCatEstatusColaborador() {
		return catEstatusColaborador;
	}

	public void setCatEstatusColaborador(CatEstatusColaborador catEstatusColaborador) {
		this.catEstatusColaborador = catEstatusColaborador;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
