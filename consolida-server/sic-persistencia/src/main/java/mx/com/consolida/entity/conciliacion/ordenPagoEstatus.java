package mx.com.consolida.entity.conciliacion;

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
@Table(name = "orden_pago_estatus")
@NamedQueries({ @NamedQuery(name = "ordenPagoEstatus.findAll", query = "SELECT c FROM ordenPagoEstatus c") })
public class ordenPagoEstatus implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_orden_pago_estatus")
	private Long idOrdenPagoEstatus;
	
	@Column(name = "id_orden_pago")
	private Long idOrdenPago;
	
	@Column(name = "id_cat_estatus_orden_pago")
	private Long idCatEstatusOrdenPago;
	
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
	
	public ordenPagoEstatus() {
		
	}

	public Long getIdOrdenPagoEstatus() {
		return idOrdenPagoEstatus;
	}

	public void setIdOrdenPagoEstatus(Long idOrdenPagoEstatus) {
		this.idOrdenPagoEstatus = idOrdenPagoEstatus;
	}

	public Long getIdOrdenPago() {
		return idOrdenPago;
	}

	public void setIdOrdenPago(Long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}

	public Long getIdCatEstatusOrdenPago() {
		return idCatEstatusOrdenPago;
	}

	public void setIdCatEstatusOrdenPago(Long idCatEstatusOrdenPago) {
		this.idCatEstatusOrdenPago = idCatEstatusOrdenPago;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	

}
