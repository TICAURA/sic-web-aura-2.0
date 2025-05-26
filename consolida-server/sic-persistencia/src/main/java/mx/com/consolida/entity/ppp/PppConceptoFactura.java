package mx.com.consolida.entity.ppp;

import java.math.BigDecimal;
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
@Table(name = "ppp_conceptos_facturas")
@NamedQueries({ @NamedQuery(name = "PppConceptoFactura.findAll", query = "SELECT c FROM PppConceptoFactura c") })
public class PppConceptoFactura implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_concepto_factura")
	private Long idPppConceptoFactura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_factura")
	private PppFacturas pppFactura;
	
	@Column(name = "descripcion_concepto")
	private String descripcionConcepto;
	
	@Column(name = "precio_unitario")
	private BigDecimal precioUnitario;
	
	@Column(name = "importe")
	private BigDecimal importe;
	
	@Column(name = "iva_trasladado_16")
	private String ivaTrasladado16;
	
	@Column(name="iva_trasladado_16_monto")
	private BigDecimal ivaTrasladado16Monto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;

	@Column(name = "ind_estatus", nullable = false)
	private String indEstatus;

	public Long getIdPppConceptoFactura() {
		return idPppConceptoFactura;
	}

	public void setIdPppConceptoFactura(Long idPppConceptoFactura) {
		this.idPppConceptoFactura = idPppConceptoFactura;
	}

	public PppFacturas getPppFactura() {
		return pppFactura;
	}

	public void setPppFactura(PppFacturas pppFactura) {
		this.pppFactura = pppFactura;
	}


	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getIvaTrasladado16() {
		return ivaTrasladado16;
	}

	public void setIvaTrasladado16(String ivaTrasladado16) {
		this.ivaTrasladado16 = ivaTrasladado16;
	}

	public BigDecimal getIvaTrasladado16Monto() {
		return ivaTrasladado16Monto;
	}

	public void setIvaTrasladado16Monto(BigDecimal ivaTrasladado16Monto) {
		this.ivaTrasladado16Monto = ivaTrasladado16Monto;
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

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	
}
