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

import mx.com.consolida.entity.conciliacion.OrdenPago;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_factura_deposito")
@NamedQueries({ @NamedQuery(name = "PppFacturaDeposito.findAll", query = "SELECT c FROM PppFacturaDeposito c") })
public class PppFacturaDeposito implements Serializable {
	
	public Long getIdPppFacturaDeposito() {
		return idPppFacturaDeposito;
	}



	public void setIdPppFacturaDeposito(Long idPppFacturaDeposito) {
		this.idPppFacturaDeposito = idPppFacturaDeposito;
	}



	public OrdenPago getIdOrdenPago() {
		return idOrdenPago;
	}



	public void setIdOrdenPago(OrdenPago idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_factura_deposito", nullable = false)
	private Long idPppFacturaDeposito;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_factura")
	private PppNominaFactura pppFactura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_orden_pago")
	private OrdenPago idOrdenPago;
	
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

	public PppFacturaDeposito() {
		
	}



public PppFacturaDeposito(Long idPppFacturaDeposito) {
	this.idPppFacturaDeposito= idPppFacturaDeposito;
}

	public PppNominaFactura getPppFactura() {
		return pppFactura;
	}

	public void setPppFactura(PppNominaFactura pppFactura) {
		this.pppFactura = pppFactura;
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
	
	
	
	
}
