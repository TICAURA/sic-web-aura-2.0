package mx.com.consolida.entity.crm;

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
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "nomina_cliente_comision")
@NamedQueries({ @NamedQuery(name = "NominaClienteComision.findAll", query = "SELECT c FROM NominaClienteComision c") })
public class NominaClienteComision {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_nomina_cliente_comision", nullable = false)
	private Long idNominaClienteComision;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nomina_cliente")
	private NominaCliente nominaCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_canal_venta", nullable = false)
	private CatGeneral canalVenta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_comisionista", nullable = false)
	private Usuario comisionista;
	
	@Column(name = "pricing")
	private String pricing;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_pricing")
	private CatFormulas catFormulaPricing;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_comision")
	private CatFormulas catFormulaComision;
	
	@Column(name = "comision")
	private String comision;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_porcentaje_comision_referidos")
	private CatGeneral porcentajeComision;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_esquema")
	private CatGeneral esquema;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio_pago")
	private Date fechaInicioPago;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_final_pago")
	private Date fechaFinalPago;
	
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

	public NominaClienteComision(Long idNominaClienteComision) {
		this.idNominaClienteComision = idNominaClienteComision;
	}
	
	public NominaClienteComision() {
		
	}
	
	public Long getIdNominaClienteComision() {
		return idNominaClienteComision;
	}

	public void setIdNominaClienteComision(Long idNominaClienteComision) {
		this.idNominaClienteComision = idNominaClienteComision;
	}

	public NominaCliente getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(NominaCliente nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

	public CatGeneral getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(CatGeneral canalVenta) {
		this.canalVenta = canalVenta;
	}

	public Usuario getComisionista() {
		return comisionista;
	}

	public void setComisionista(Usuario comisionista) {
		this.comisionista = comisionista;
	}

	public String getPricing() {
		return pricing;
	}

	public void setPricing(String pricing) {
		this.pricing = pricing;
	}

	public CatFormulas getCatFormulaPricing() {
		return catFormulaPricing;
	}

	public void setCatFormulaPricing(CatFormulas catFormulaPricing) {
		this.catFormulaPricing = catFormulaPricing;
	}

	public CatFormulas getCatFormulaComision() {
		return catFormulaComision;
	}

	public void setCatFormulaComision(CatFormulas catFormulaComision) {
		this.catFormulaComision = catFormulaComision;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
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

	public CatGeneral getEsquema() {
		return esquema;
	}

	public void setEsquema(CatGeneral esquema) {
		this.esquema = esquema;
	}

	public Date getFechaInicioPago() {
		return fechaInicioPago;
	}

	public void setFechaInicioPago(Date fechaInicioPago) {
		this.fechaInicioPago = fechaInicioPago;
	}

	public Date getFechaFinalPago() {
		return fechaFinalPago;
	}

	public void setFechaFinalPago(Date fechaFinalPago) {
		this.fechaFinalPago = fechaFinalPago;
	}

	public CatGeneral getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(CatGeneral porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}
	
}
