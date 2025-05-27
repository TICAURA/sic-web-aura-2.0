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

import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.crm.ClienteConceptoFacturacion;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_nomina_factura_concepto")
@NamedQueries({ @NamedQuery(name = "PppNominaFacturaConcepto.findAll", query = "SELECT c FROM PppNominaFacturaConcepto c") })
public class PppNominaFacturaConcepto implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_nomina_factura_concepto")
	private Long idPppNominaFacturaConcepto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina_factura")
	private PppNominaFactura pppNominaFactura;
	
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidad_medida")
	private CatGeneral unidadMedida;
	
	@Column(name = "codigo_sat")
	private String codigoSat;
	
	@Column(name = "descripcion_sat")
	private String descripcionSat;
	
	@Column(name = "descripcion_concepto")
	private String descripcionConcepto;
	
	@Column(name = "descripcion_concepto_adicional")
	private String descripcionConceptoAdicional;
	
	@Column(name = "precio_unitario")
	private BigDecimal precioUnitario;
	
	@Column(name = "importe")
	private BigDecimal importe;
	
	@Column(name = "iva_trasladado_16")
	private String ivaTrasladado16;
	
	@Column(name="iva_trasladado_16_monto")
	private BigDecimal ivaTrasladado16Monto;
	
	@Column(name = "iva_retenido_6")
	private String ivaRetenido6;
	
	@Column(name="iva_retenido_6_monto")
	private BigDecimal ivaRetenido6Monto;
	
	
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

	public Long getIdPppNominaFacturaConcepto() {
		return idPppNominaFacturaConcepto;
	}

	public void setIdPppNominaFacturaConcepto(Long idPppNominaFacturaConcepto) {
		this.idPppNominaFacturaConcepto = idPppNominaFacturaConcepto;
	}

	public PppNominaFactura getPppNominaFactura() {
		return pppNominaFactura;
	}

	public void setPppNominaFactura(PppNominaFactura pppNominaFactura) {
		this.pppNominaFactura = pppNominaFactura;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public CatGeneral getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(CatGeneral unidadMedida) {
		this.unidadMedida = unidadMedida;
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

	public String getIvaRetenido6() {
		return ivaRetenido6;
	}

	public void setIvaRetenido6(String ivaRetenido6) {
		this.ivaRetenido6 = ivaRetenido6;
	}

	public BigDecimal getIvaRetenido6Monto() {
		return ivaRetenido6Monto;
	}

	public void setIvaRetenido6Monto(BigDecimal ivaRetenido6Monto) {
		this.ivaRetenido6Monto = ivaRetenido6Monto;
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

	public String getCodigoSat() {
		return codigoSat;
	}

	public void setCodigoSat(String codigoSat) {
		this.codigoSat = codigoSat;
	}

	public String getDescripcionSat() {
		return descripcionSat;
	}

	public void setDescripcionSat(String descripcionSat) {
		this.descripcionSat = descripcionSat;
	}

	public String getDescripcionConceptoAdicional() {
		return descripcionConceptoAdicional;
	}

	public void setDescripcionConceptoAdicional(String descripcionConceptoAdicional) {
		this.descripcionConceptoAdicional = descripcionConceptoAdicional;
	}

	
}
