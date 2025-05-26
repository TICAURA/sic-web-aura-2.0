package mx.com.consolida.entity.conciliacion;

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

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "orden_pago")
@NamedQueries({ @NamedQuery(name = "OrdenPago.findAll", query = "SELECT c FROM OrdenPago c") })
public class OrdenPago implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_orden_pago")
	private Long idOrdenPago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_carga_orden_pago", nullable = false)
	private CargaOrdenPago cargaOrdenPago;
	
	@Column (name="id_orden_origen", nullable=false )
	private Long idOrdenOrigen;
	
	@Column (name="folio_orden", nullable=false )
	private Long folioOrden;
	
	@Column(name = "institucion", nullable = false)
	private String institucion;
	
	@Column(name = "concepto_pago")
	private String conceptoPago;
	
	@Column(name = "beneficiario")
	private String beneficiario;
	
	@Column(name = "cta_beneficiario", nullable= false)
	private String  ctaBeneficiario ;
	
	@Column(name = "contraparte")
	private String  contraparte;
	
	@Column(name = "monto", nullable = false)
	private BigDecimal monto;
	
	@Column(name = "rastreo", nullable = false)
	private String rastreo;
	
	@Column(name = "ordenante")
	private String ordenante;
	
	@Column(name = "cta_ordenante", nullable = false )
	private String ctaOrdenate;
	

	@Column(name = "fecha_operacion" )
	private Date fechaOperacion;

	

	@Column(name = "fecha_captura" )
	private Date fechaCaptura;
	
	
	@Column(name = "fecha_liquidacion")
	private Date fechaLiquidacion;
	
	@Column(name = "folio_orden_cep")
	private Long folioOrdenCep;
	
	@Column(name = "nombre_cliente_cep")
	private String nombreClienteCep;
	
	@Column(name = "causa_devolucion")
	private String causaDevolucion ;
	
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
	
	
	
	public OrdenPago() {
	
	}

	public OrdenPago(Long idOrdenPago) {
		this.idOrdenPago= idOrdenPago;
	}

	public Long getIdOrdenPago() {
		return idOrdenPago;
	}

	public void setIdOrdenPago(Long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}

	public CargaOrdenPago getCargaOrdenPago() {
		return cargaOrdenPago;
	}

	public void setCargaOrdenPago(CargaOrdenPago cargaOrdenPago) {
		this.cargaOrdenPago = cargaOrdenPago;
	}

	public Long getIdOrdenOrigen() {
		return idOrdenOrigen;
	}

	public void setIdOrdenOrigen(Long idOrdenOrigen) {
		this.idOrdenOrigen = idOrdenOrigen;
	}

	public Long getFolioOrden() {
		return folioOrden;
	}

	public void setFolioOrden(Long folioOrden) {
		this.folioOrden = folioOrden;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getCtaBeneficiario() {
		return ctaBeneficiario;
	}

	public void setCtaBeneficiario(String ctaBeneficiario) {
		this.ctaBeneficiario = ctaBeneficiario;
	}

	public String getContraparte() {
		return contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getRastreo() {
		return rastreo;
	}

	public void setRastreo(String rastreo) {
		this.rastreo = rastreo;
	}

	public String getOrdenante() {
		return ordenante;
	}

	public void setOrdenante(String ordenante) {
		this.ordenante = ordenante;
	}

	public String getCtaOrdenate() {
		return ctaOrdenate;
	}

	public void setCtaOrdenate(String ctaOrdenate) {
		this.ctaOrdenate = ctaOrdenate;
	}

	public Long getFolioOrdenCep() {
		return folioOrdenCep;
	}

	public void setFolioOrdenCep(Long folioOrdenCep) {
		this.folioOrdenCep = folioOrdenCep;
	}

	public String getNombreClienteCep() {
		return nombreClienteCep;
	}

	public void setNombreClienteCep(String nombreClienteCep) {
		this.nombreClienteCep = nombreClienteCep;
	}

	public String getCausaDevolucion() {
		return causaDevolucion;
	}

	public void setCausaDevolucion(String causaDevolucion) {
		this.causaDevolucion = causaDevolucion;
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

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}






	
	

	
}
