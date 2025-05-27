package mx.com.consolida.entity.ppp;

import java.io.Serializable;
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
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_nomima_factura")
@NamedQueries({ @NamedQuery(name = "PppNominaFactura.findAll", query = "SELECT c FROM PppNominaFactura c") })
public class PppNominaFactura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_nomina_factura", nullable = false)
	private Long idPppNominaFactura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina")
	private PppNomina nominaCliente;
	
	@Column(name = "idcliente")
	private Long idcliente;
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_cliente")
//	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio")
	private PrestadoraServicio prestadoraServicio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_serie")
	private CatSerie catSerie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_folio")
	private CatFolio catFolio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_metodo_pago")
	private CatGeneral catMetodoPago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_forma_pago")
	private CatGeneral catFormaPago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_tipo_cfdi")
	private CatGeneral catTipoCfdi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_regimen_fiscal")
	private CatGeneral catRegimenFiscal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_moneda")
	private CatGeneral catMoneda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_uso_cfdi")
	private CatGeneral catUsoCfdi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_proveedor")
	private CatGeneral catProveedor;
	
	@Column(name = "sub_total")
	private BigDecimal subTotal;
	
	@Column(name = "total_iva_trasladado_16")
	private BigDecimal totalIvaTrasladado16;
	
	@Column(name = "total_iva_retenido_6")
	private BigDecimal totalIvaRetenido6;
	
	@Column(name = "total")
	private BigDecimal total;
	
	@Column(name = "iva_comercial")
	private BigDecimal ivaComercial;

	@Column(name = "honorario")
	private BigDecimal honorario;
	
	@Column(name = "monto_total_honorario")
	private BigDecimal montoTotalHonorario;
	
	@Column(name = "monto_total_colaboradores_ppp")
	private BigDecimal montoTotalColaboradoresPPP;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "monto_comprobante_pago")
	private BigDecimal montoComprobantePago;
	
//	@Column(name = "metodo_pago")
//	private String medtodoPago;
	
//	@Column(name = "forma_pago")
//	private String formaPago;
	
//	@Column(name = "tipo_cfdi")
//	private String tipoCfdi;
	
	@Column(name = "certificado_emisor")
	private String certificadoEmisor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_emision", length = 19)
	private Date fechaHoraEmision;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_certificacion", length = 19)
	private Date fechaHoraCertificacion;
	
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
	
	@Column(name = "folio" )
	private Long folio;
	
	public PppNominaFactura() {
		
	}
	
	public PppNominaFactura(Long idPppNominaFactura) {
		this.idPppNominaFactura= idPppNominaFactura;
	}

	public Long getIdPppNominaFactura() {
		return idPppNominaFactura;
	}

	public void setIdPppNominaFactura(Long idPppNominaFactura) {
		this.idPppNominaFactura = idPppNominaFactura;
	}

	public PppNomina getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(PppNomina nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

//	public Cliente getCliente() {
//		return cliente;
//	}
//
//	public void setCliente(Cliente cliente) {
//		this.cliente = cliente;
//	}

	public PrestadoraServicio getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public CatSerie getCatSerie() {
		return catSerie;
	}

	public void setCatSerie(CatSerie catSerie) {
		this.catSerie = catSerie;
	}

	public CatFolio getCatFolio() {
		return catFolio;
	}

	public void setCatFolio(CatFolio catFolio) {
		this.catFolio = catFolio;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

//	public String getMedtodoPago() {
//		return medtodoPago;
//	}
//
//	public void setMedtodoPago(String medtodoPago) {
//		this.medtodoPago = medtodoPago;
//	}

//	public String getFormaPago() {
//		return formaPago;
//	}
//
//	public void setFormaPago(String formaPago) {
//		this.formaPago = formaPago;
//	}
//
//	public String getTipoCfdi() {
//		return tipoCfdi;
//	}
//
//	public void setTipoCfdi(String tipoCfdi) {
//		this.tipoCfdi = tipoCfdi;
//	}

	public String getCertificadoEmisor() {
		return certificadoEmisor;
	}

	public void setCertificadoEmisor(String certificadoEmisor) {
		this.certificadoEmisor = certificadoEmisor;
	}

	public Date getFechaHoraEmision() {
		return fechaHoraEmision;
	}

	public void setFechaHoraEmision(Date fechaHoraEmision) {
		this.fechaHoraEmision = fechaHoraEmision;
	}

	public Date getFechaHoraCertificacion() {
		return fechaHoraCertificacion;
	}

	public void setFechaHoraCertificacion(Date fechaHoraCertificacion) {
		this.fechaHoraCertificacion = fechaHoraCertificacion;
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

	public CatGeneral getCatMetodoPago() {
		return catMetodoPago;
	}

	public void setCatMetodoPago(CatGeneral catMetodoPago) {
		this.catMetodoPago = catMetodoPago;
	}

	public CatGeneral getCatFormaPago() {
		return catFormaPago;
	}

	public void setCatFormaPago(CatGeneral catFormaPago) {
		this.catFormaPago = catFormaPago;
	}

	public CatGeneral getCatTipoCfdi() {
		return catTipoCfdi;
	}

	public void setCatTipoCfdi(CatGeneral catTipoCfdi) {
		this.catTipoCfdi = catTipoCfdi;
	}

	public CatGeneral getCatRegimenFiscal() {
		return catRegimenFiscal;
	}

	public void setCatRegimenFiscal(CatGeneral catRegimenFiscal) {
		this.catRegimenFiscal = catRegimenFiscal;
	}

	public CatGeneral getCatMoneda() {
		return catMoneda;
	}

	public void setCatMoneda(CatGeneral catMoneda) {
		this.catMoneda = catMoneda;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotalIvaTrasladado16() {
		return totalIvaTrasladado16;
	}

	public void setTotalIvaTrasladado16(BigDecimal totalIvaTrasladado16) {
		this.totalIvaTrasladado16 = totalIvaTrasladado16;
	}

	public BigDecimal getTotalIvaRetenido6() {
		return totalIvaRetenido6;
	}

	public void setTotalIvaRetenido6(BigDecimal totalIvaRetenido6) {
		this.totalIvaRetenido6 = totalIvaRetenido6;
	}

	public CatGeneral getCatUsoCfdi() {
		return catUsoCfdi;
	}

	public void setCatUsoCfdi(CatGeneral catUsoCfdi) {
		this.catUsoCfdi = catUsoCfdi;
	}

	public CatGeneral getCatProveedor() {
		return catProveedor;
	}

	public void setCatProveedor(CatGeneral catProveedor) {
		this.catProveedor = catProveedor;
	}

	public BigDecimal getMontoComprobantePago() {
		return montoComprobantePago;
	}

	public void setMontoComprobantePago(BigDecimal montoComprobantePago) {
		this.montoComprobantePago = montoComprobantePago;
	}

	public BigDecimal getIvaComercial() {
		return ivaComercial;
	}

	public void setIvaComercial(BigDecimal ivaComercial) {
		this.ivaComercial = ivaComercial;
	}

	public BigDecimal getHonorario() {
		return honorario;
	}

	public void setHonorario(BigDecimal honorario) {
		this.honorario = honorario;
	}

	public BigDecimal getMontoTotalHonorario() {
		return montoTotalHonorario;
	}

	public void setMontoTotalHonorario(BigDecimal montoTotalHonorario) {
		this.montoTotalHonorario = montoTotalHonorario;
	}

	public BigDecimal getMontoTotalColaboradoresPPP() {
		return montoTotalColaboradoresPPP;
	}

	public void setMontoTotalColaboradoresPPP(BigDecimal montoTotalColaboradoresPPP) {
		this.montoTotalColaboradoresPPP = montoTotalColaboradoresPPP;
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public Long getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(Long idcliente) {
		this.idcliente = idcliente;
	}
	
	


	
}
