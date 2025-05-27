package mx.com.consolida.entity.crm;

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
@Table(name = "nomina_cliente_honorario")
@NamedQueries({
		@NamedQuery(name = "NominaClienteHonorario.findAll", query = "SELECT c FROM NominaClienteHonorario c") })
public class NominaClienteHonorario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_nomina_cliente_honorario", nullable = false)
	private Long idNominaClienteHonorario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nomina_cliente")
	private NominaCliente nominaCliente;
	
	@Column(name = "honorario_ppp")
	private String honorarioPpp;
	
	@Column(name = "iva_comercial")
	private String ivaComercial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_ppp")
	private CatFormulas catFormulaPpp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_tipo_iva")
	private CatFormulas catTipoIva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_factura")
	private CatFormulas catFormulaFactura;
	
	
	
	
	///////
	@Column(name = "honorario_ppp_ss")
	private String honorarioPppSs;
	
	@Column(name = "honorario_ppp_mixto")
	private String honorarioPppMixto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_ss")
	private CatFormulas catFormulaFacturaSS;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_mixto")
	private CatFormulas catFormulaFacturaMixto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_formula_maquila")
	private CatFormulas catFormulaFacturaMaquila;
	
	///////
	

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
	
	public NominaClienteHonorario() {
		
	}
	
	public NominaClienteHonorario(Long idNominaClienteHonorario) {
		this.idNominaClienteHonorario = idNominaClienteHonorario;
	}

	public Long getIdNominaClienteHonorario() {
		return idNominaClienteHonorario;
	}

	public void setIdNominaClienteHonorario(Long idNominaClienteHonorario) {
		this.idNominaClienteHonorario = idNominaClienteHonorario;
	}

	public NominaCliente getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(NominaCliente nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

	public String getHonorarioPpp() {
		return honorarioPpp;
	}

	public void setHonorarioPpp(String honorarioPpp) {
		this.honorarioPpp = honorarioPpp;
	}

	public CatFormulas getCatFormulaPpp() {
		return catFormulaPpp;
	}

	public void setCatFormulaPpp(CatFormulas catFormulaPpp) {
		this.catFormulaPpp = catFormulaPpp;
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

	public CatFormulas getCatTipoIva() {
		return catTipoIva;
	}

	public void setCatTipoIva(CatFormulas catTipoIva) {
		this.catTipoIva = catTipoIva;
	}

	public CatFormulas getCatFormulaFactura() {
		return catFormulaFactura;
	}

	public void setCatFormulaFactura(CatFormulas catFormulaFactura) {
		this.catFormulaFactura = catFormulaFactura;
	}

	public String getIvaComercial() {
		return ivaComercial;
	}

	public void setIvaComercial(String ivaComercial) {
		this.ivaComercial = ivaComercial;
	}

	public String getHonorarioPppSs() {
		return honorarioPppSs;
	}

	public void setHonorarioPppSs(String honorarioPppSs) {
		this.honorarioPppSs = honorarioPppSs;
	}

	public String getHonorarioPppMixto() {
		return honorarioPppMixto;
	}

	public void setHonorarioPppMixto(String honorarioPppMixto) {
		this.honorarioPppMixto = honorarioPppMixto;
	}

	public CatFormulas getCatFormulaFacturaSS() {
		return catFormulaFacturaSS;
	}

	public void setCatFormulaFacturaSS(CatFormulas catFormulaFacturaSS) {
		this.catFormulaFacturaSS = catFormulaFacturaSS;
	}

	public CatFormulas getCatFormulaFacturaMixto() {
		return catFormulaFacturaMixto;
	}

	public void setCatFormulaFacturaMixto(CatFormulas catFormulaFacturaMixto) {
		this.catFormulaFacturaMixto = catFormulaFacturaMixto;
	}

	public CatFormulas getCatFormulaFacturaMaquila() {
		return catFormulaFacturaMaquila;
	}

	public void setCatFormulaFacturaMaquila(CatFormulas catFormulaFacturaMaquila) {
		this.catFormulaFacturaMaquila = catFormulaFacturaMaquila;
	}

}
