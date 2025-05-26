package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cotizacion_colaborador")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "CotizacionColaborador.findAll", query = "SELECT c FROM CotizacionColaborador c") })
public class CotizacionColaborador implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cotizacion_colaborador")
	private Long idCotizacionColaborador;
	@Column(name = "dgNombre")
	private String dgNombre;
	@Column(name = "dgPuesto")
	private String dgPuesto;
	@Column(name = "dgPrimaDeRiesgo")
	private String dgPrimaDeRiesgo;
	@Column(name = "dgFechaDeAntIguedad")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dgFechaDeAntIguedad;
	@Column(name = "dgporcIMSS")
	private BigDecimal dgporcIMSS;
	@Column(name = "dgporcPPP")
	private BigDecimal dgporcPPP;
	@Column(name = "dgNetoBruto")
	private BigDecimal dgNetoBruto;
	@Column(name = "dgporcCotizacionDeseado")
	private BigDecimal dgporcCotizacionDeseado;
	@Column(name = "dgVSM")
	private BigDecimal dgVSM;
	@Column(name = "dgMontoBrutoMensual")
	private BigDecimal dgMontoBrutoMensual;
	@Column(name = "dgporcBeneficioFiscal")
	private BigDecimal dgporcBeneficioFiscal;
	@Column(name = "dDiferenciaNeto")
	private BigDecimal dDiferenciaNeto;
	@Column(name = "dDiferenciaCostoMixto")
	private BigDecimal dDiferenciaCostoMixto;
	@Column(name = "dDiferenciaCostoSoloPPP")
	private BigDecimal dDiferenciaCostoSoloPPP;
	@Column(name = "assSD")
	private BigDecimal assSD;
	@Column(name = "assSDI")
	private BigDecimal assSDI;
	@Column(name = "assSuedo")
	private BigDecimal assSuedo;
	@Column(name = "assGravados")
	private BigDecimal assGravados;
	@Column(name = "assExentos")
	private BigDecimal assExentos;
	@Column(name = "assSubsidio")
	private BigDecimal assSubsidio;
	@Column(name = "assISR")
	private BigDecimal assISR;
	@Column(name = "assCoImss")
	private BigDecimal assCoImss;
	@Column(name = "assNetoNomina")
	private BigDecimal assNetoNomina;
	@Column(name = "aasAsimialdos")
	private BigDecimal aasAsimialdos;
	@Column(name = "aasISR")
	private BigDecimal aasISR;
	@Column(name = "aasNetoAsimilados")
	private BigDecimal aasNetoAsimilados;
	@Column(name = "aOtros")
	private BigDecimal aOtros;
	@Column(name = "aNetoTotal")
	private BigDecimal aNetoTotal;
	@Column(name = "acfaPercepcionTotal")
	private BigDecimal acfaPercepcionTotal;
	@Column(name = "acfaCargaSocial")
	private BigDecimal acfaCargaSocial;
	@Column(name = "acfaComision")
	private BigDecimal acfaComision;
	@Column(name = "acfaSubtotalFactura")
	private BigDecimal acfaSubtotalFactura;
	@Column(name = "acfaIVA")
	private BigDecimal acfaIVA;
	@Column(name = "acfaTotalFactura")
	private BigDecimal acfaTotalFactura;
	@Column(name = "gsssSD")
	private BigDecimal gsssSD;
	@Column(name = "gsssSDI")
	private BigDecimal gsssSDI;
	@Column(name = "gsssSuedo")
	private BigDecimal gsssSuedo;
	@Column(name = "gsssExentos")
	private BigDecimal gsssExentos;
	@Column(name = "gsssSubsidio")
	private BigDecimal gsssSubsidio;
	@Column(name = "gsssISR")
	private BigDecimal gsssISR;
	@Column(name = "gsssCoImss")
	private BigDecimal gsssCoImss;
	@Column(name = "gsssNetoNomina")
	private BigDecimal gsssNetoNomina;
	@Column(name = "gsePlanPensionesPrivada")
	private BigDecimal gsePlanPensionesPrivada;
	@Column(name = "gsNetoTotal")
	private BigDecimal gsNetoTotal;
	@Column(name = "gscfmPercepcionTotal")
	private BigDecimal gscfmPercepcionTotal;
	@Column(name = "gscfmCargaSocial")
	private BigDecimal gscfmCargaSocial;
	@Column(name = "gscfmComision")
	private BigDecimal gscfmComision;
	@Column(name = "gscfmSubtotalFactura")
	private BigDecimal gscfmSubtotalFactura;
	@Column(name = "gscfmIVA")
	private BigDecimal gscfmIVA;
	@Column(name = "gscfmTotalFactura")
	private BigDecimal gscfmTotalFactura;
	@Column(name = "gscfpppPercepcionNomina")
	private BigDecimal gscfpppPercepcionNomina;
	@Column(name = "gscfpppCargaSocial")
	private BigDecimal gscfpppCargaSocial;
	@Column(name = "gscfpppCostoNomina")
	private BigDecimal gscfpppCostoNomina;
	@Column(name = "gscfpppPPP")
	private BigDecimal gscfpppPPP;
	@Column(name = "gscfpppComision")
	private BigDecimal gscfpppComision;
	@Column(name = "gscfpppSubtotalFactura")
	private BigDecimal gscfpppSubtotalFactura;
	@Column(name = "gscfpppIVA")
	private BigDecimal gscfpppIVA;
	@Column(name = "gscfpppTotalFactura")
	private BigDecimal gscfpppTotalFactura;
	@Column(name = "gscfpppCostoTotal")
	private BigDecimal gscfpppCostoTotal;
	@Column(name = "usuario_alta")
	private Long idUsuarioAlta;
	@Column(name = "usuario_modificacion")
	private Long idUsuarioModificacion;
	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	@Column(name = "ind_estatus")
	private Long indEstatus;
	@JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
	@ManyToOne(optional = false)
	private Cotizacion idCotizacion;
	@JoinColumn(name = "id_colaborador_temp", referencedColumnName = "id_colaborador_temp")
	@ManyToOne
	private ColaboradoresTemp idColaboradorTemp;
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	public CotizacionColaborador() {
	}



	public CotizacionColaborador(CotizacionColaboradorDto dto) {
		super();
		this.idCotizacionColaborador = dto.getIdCotizacionColaborador();
		this.dgNombre = dto.getDgNombre();
		this.dgPuesto = dto.getDgPuesto();
		this.dgPrimaDeRiesgo = dto.getDgPrimaDeRiesgo();
		this.dgFechaDeAntIguedad = dto.getDgFechaDeAntIguedad();
		this.dgporcIMSS = dto.getDgporcIMSS();
		this.dgporcPPP = dto.getDgporcPPP();
		this.dgNetoBruto = dto.getDgNetoBruto();
		this.dgporcCotizacionDeseado = dto.getDgporcCotizacionDeseado();
		this.dgVSM = dto.getDgVSM();
		this.dgMontoBrutoMensual = dto.getDgMontoBrutoMensual();
		this.dgporcBeneficioFiscal = dto.getDgporcBeneficioFiscal();
		this.dDiferenciaNeto = dto.getdDiferenciaNeto();
		this.dDiferenciaCostoMixto = dto.getdDiferenciaCostoMixto();
		this.dDiferenciaCostoSoloPPP = dto.getdDiferenciaCostoSoloPPP();
		this.assSD = dto.getAssSD();
		this.assSDI = dto.getAssSDI();
		this.assSuedo = dto.getAssSuedo();
		this.assGravados = dto.getAssGravados();
		this.assExentos = dto.getAssExentos();
		this.assSubsidio = dto.getAssSubsidio();
		this.assISR = dto.getAssISR();
		this.assCoImss = dto.getAssCoImss();
		this.assNetoNomina = dto.getAssNetoNomina();
		this.aasAsimialdos = dto.getAasAsimialdos();
		this.aasISR = dto.getAasISR();
		this.aasNetoAsimilados = dto.getAasNetoAsimilados();
		this.aOtros = dto.getaOtros();
		this.aNetoTotal = dto.getaNetoTotal();
		this.acfaPercepcionTotal = dto.getAcfaPercepcionTotal();
		this.acfaCargaSocial = dto.getAcfaCargaSocial();
		this.acfaComision = dto.getAcfaComision();
		this.acfaSubtotalFactura = dto.getAcfaSubtotalFactura();
		this.acfaIVA = dto.getAcfaIVA();
		this.acfaTotalFactura = dto.getAcfaTotalFactura();
		this.gsssSD = dto.getGsssSD();
		this.gsssSDI = dto.getGsssSDI();
		this.gsssSuedo = dto.getGsssSuedo();
		this.gsssExentos = dto.getGsssExentos();
		this.gsssSubsidio = dto.getGsssSubsidio();
		this.gsssISR = dto.getGsssISR();
		this.gsssCoImss = dto.getGsssCoImss();
		this.gsssNetoNomina = dto.getGsssNetoNomina();
		this.gsePlanPensionesPrivada = dto.getGsePlanPensionesPrivada();
		this.gsNetoTotal = dto.getGsNetoTotal();
		this.gscfmPercepcionTotal = dto.getGscfmPercepcionTotal();
		this.gscfmCargaSocial = dto.getGscfmCargaSocial();
		this.gscfmComision = dto.getGscfmComision();
		this.gscfmSubtotalFactura = dto.getGscfmSubtotalFactura();
		this.gscfmIVA = dto.getGscfmIVA();
		this.gscfmTotalFactura = dto.getGscfmTotalFactura();
		this.gscfpppPercepcionNomina = dto.getGscfpppPercepcionNomina();
		this.gscfpppCargaSocial = dto.getGscfpppCargaSocial();
		this.gscfpppCostoNomina = dto.getGscfpppCostoNomina();
		this.gscfpppPPP = dto.getGscfpppPPP();
		this.gscfpppComision = dto.getGscfpppComision();
		this.gscfpppSubtotalFactura = dto.getGscfpppSubtotalFactura();
		this.gscfpppIVA = dto.getGscfpppIVA();
		this.gscfpppTotalFactura = dto.getGscfpppTotalFactura();
		this.gscfpppCostoTotal = dto.getGscfpppCostoTotal();
		this.idUsuarioAlta = dto.getIdUsuarioAlta();
		this.fechaAlta = dto.getFechaAlta();
		this.indEstatus = dto.getIndEstatus();
		this.idCotizacion = new Cotizacion(dto.getIdCotizacion().getIdCotizacion());
		this.idColaboradorTemp = new ColaboradoresTemp(dto.getIdColaboradorTemp().getIdColaboradorTemp());
		this.fechaModificacion = dto.getFechaModificacion();
	}



	public CotizacionColaborador(Long idCotizacionColaborador) {
		this.idCotizacionColaborador = idCotizacionColaborador;
	}

	public Long getIdCotizacionColaborador() {
		return idCotizacionColaborador;
	}

	public void setIdCotizacionColaborador(Long idCotizacionColaborador) {
		this.idCotizacionColaborador = idCotizacionColaborador;
	}

	public String getDgNombre() {
		return dgNombre;
	}

	public void setDgNombre(String dgNombre) {
		this.dgNombre = dgNombre;
	}

	public String getDgPuesto() {
		return dgPuesto;
	}

	public void setDgPuesto(String dgPuesto) {
		this.dgPuesto = dgPuesto;
	}

	public String getDgPrimaDeRiesgo() {
		return dgPrimaDeRiesgo;
	}

	public void setDgPrimaDeRiesgo(String dgPrimaDeRiesgo) {
		this.dgPrimaDeRiesgo = dgPrimaDeRiesgo;
	}

	public Date getDgFechaDeAntIguedad() {
		return dgFechaDeAntIguedad;
	}

	public void setDgFechaDeAntIguedad(Date dgFechaDeAntIguedad) {
		this.dgFechaDeAntIguedad = dgFechaDeAntIguedad;
	}

	public BigDecimal getDgporcIMSS() {
		return dgporcIMSS;
	}

	public void setDgporcIMSS(BigDecimal dgporcIMSS) {
		this.dgporcIMSS = dgporcIMSS;
	}

	public BigDecimal getDgporcPPP() {
		return dgporcPPP;
	}

	public void setDgporcPPP(BigDecimal dgporcPPP) {
		this.dgporcPPP = dgporcPPP;
	}

	public BigDecimal getDgNetoBruto() {
		return dgNetoBruto;
	}

	public void setDgNetoBruto(BigDecimal dgNetoBruto) {
		this.dgNetoBruto = dgNetoBruto;
	}

	public BigDecimal getDgporcCotizacionDeseado() {
		return dgporcCotizacionDeseado;
	}

	public void setDgporcCotizacionDeseado(BigDecimal dgporcCotizacionDeseado) {
		this.dgporcCotizacionDeseado = dgporcCotizacionDeseado;
	}

	public BigDecimal getDgVSM() {
		return dgVSM;
	}

	public void setDgVSM(BigDecimal dgVSM) {
		this.dgVSM = dgVSM;
	}

	public BigDecimal getDgMontoBrutoMensual() {
		return dgMontoBrutoMensual;
	}

	public void setDgMontoBrutoMensual(BigDecimal dgMontoBrutoMensual) {
		this.dgMontoBrutoMensual = dgMontoBrutoMensual;
	}

	public BigDecimal getDgporcBeneficioFiscal() {
		return dgporcBeneficioFiscal;
	}

	public void setDgporcBeneficioFiscal(BigDecimal dgporcBeneficioFiscal) {
		this.dgporcBeneficioFiscal = dgporcBeneficioFiscal;
	}

	public BigDecimal getDDiferenciaNeto() {
		return dDiferenciaNeto;
	}

	public void setDDiferenciaNeto(BigDecimal dDiferenciaNeto) {
		this.dDiferenciaNeto = dDiferenciaNeto;
	}

	public BigDecimal getDDiferenciaCostoMixto() {
		return dDiferenciaCostoMixto;
	}

	public void setDDiferenciaCostoMixto(BigDecimal dDiferenciaCostoMixto) {
		this.dDiferenciaCostoMixto = dDiferenciaCostoMixto;
	}

	public BigDecimal getDDiferenciaCostoSoloPPP() {
		return dDiferenciaCostoSoloPPP;
	}

	public void setDDiferenciaCostoSoloPPP(BigDecimal dDiferenciaCostoSoloPPP) {
		this.dDiferenciaCostoSoloPPP = dDiferenciaCostoSoloPPP;
	}

	public BigDecimal getAssSD() {
		return assSD;
	}

	public void setAssSD(BigDecimal assSD) {
		this.assSD = assSD;
	}

	public BigDecimal getAssSDI() {
		return assSDI;
	}

	public void setAssSDI(BigDecimal assSDI) {
		this.assSDI = assSDI;
	}

	public BigDecimal getAssSuedo() {
		return assSuedo;
	}

	public void setAssSuedo(BigDecimal assSuedo) {
		this.assSuedo = assSuedo;
	}

	public BigDecimal getAssGravados() {
		return assGravados;
	}

	public void setAssGravados(BigDecimal assGravados) {
		this.assGravados = assGravados;
	}

	public BigDecimal getAssExentos() {
		return assExentos;
	}

	public void setAssExentos(BigDecimal assExentos) {
		this.assExentos = assExentos;
	}

	public BigDecimal getAssSubsidio() {
		return assSubsidio;
	}

	public void setAssSubsidio(BigDecimal assSubsidio) {
		this.assSubsidio = assSubsidio;
	}

	public BigDecimal getAssISR() {
		return assISR;
	}

	public void setAssISR(BigDecimal assISR) {
		this.assISR = assISR;
	}

	public BigDecimal getAssCoImss() {
		return assCoImss;
	}

	public void setAssCoImss(BigDecimal assCoImss) {
		this.assCoImss = assCoImss;
	}

	public BigDecimal getAssNetoNomina() {
		return assNetoNomina;
	}

	public void setAssNetoNomina(BigDecimal assNetoNomina) {
		this.assNetoNomina = assNetoNomina;
	}

	public BigDecimal getAasAsimialdos() {
		return aasAsimialdos;
	}

	public void setAasAsimialdos(BigDecimal aasAsimialdos) {
		this.aasAsimialdos = aasAsimialdos;
	}

	public BigDecimal getAasISR() {
		return aasISR;
	}

	public void setAasISR(BigDecimal aasISR) {
		this.aasISR = aasISR;
	}

	public BigDecimal getAasNetoAsimilados() {
		return aasNetoAsimilados;
	}

	public void setAasNetoAsimilados(BigDecimal aasNetoAsimilados) {
		this.aasNetoAsimilados = aasNetoAsimilados;
	}

	public BigDecimal getAOtros() {
		return aOtros;
	}

	public void setAOtros(BigDecimal aOtros) {
		this.aOtros = aOtros;
	}

	public BigDecimal getANetoTotal() {
		return aNetoTotal;
	}

	public void setANetoTotal(BigDecimal aNetoTotal) {
		this.aNetoTotal = aNetoTotal;
	}

	public BigDecimal getAcfaPercepcionTotal() {
		return acfaPercepcionTotal;
	}

	public void setAcfaPercepcionTotal(BigDecimal acfaPercepcionTotal) {
		this.acfaPercepcionTotal = acfaPercepcionTotal;
	}

	public BigDecimal getAcfaCargaSocial() {
		return acfaCargaSocial;
	}

	public void setAcfaCargaSocial(BigDecimal acfaCargaSocial) {
		this.acfaCargaSocial = acfaCargaSocial;
	}

	public BigDecimal getAcfaComision() {
		return acfaComision;
	}

	public void setAcfaComision(BigDecimal acfaComision) {
		this.acfaComision = acfaComision;
	}

	public BigDecimal getAcfaSubtotalFactura() {
		return acfaSubtotalFactura;
	}

	public void setAcfaSubtotalFactura(BigDecimal acfaSubtotalFactura) {
		this.acfaSubtotalFactura = acfaSubtotalFactura;
	}

	public BigDecimal getAcfaIVA() {
		return acfaIVA;
	}

	public void setAcfaIVA(BigDecimal acfaIVA) {
		this.acfaIVA = acfaIVA;
	}

	public BigDecimal getAcfaTotalFactura() {
		return acfaTotalFactura;
	}

	public void setAcfaTotalFactura(BigDecimal acfaTotalFactura) {
		this.acfaTotalFactura = acfaTotalFactura;
	}

	public BigDecimal getGsssSD() {
		return gsssSD;
	}

	public void setGsssSD(BigDecimal gsssSD) {
		this.gsssSD = gsssSD;
	}

	public BigDecimal getGsssSDI() {
		return gsssSDI;
	}

	public void setGsssSDI(BigDecimal gsssSDI) {
		this.gsssSDI = gsssSDI;
	}

	public BigDecimal getGsssSuedo() {
		return gsssSuedo;
	}

	public void setGsssSuedo(BigDecimal gsssSuedo) {
		this.gsssSuedo = gsssSuedo;
	}

	public BigDecimal getGsssExentos() {
		return gsssExentos;
	}

	public void setGsssExentos(BigDecimal gsssExentos) {
		this.gsssExentos = gsssExentos;
	}

	public BigDecimal getGsssSubsidio() {
		return gsssSubsidio;
	}

	public void setGsssSubsidio(BigDecimal gsssSubsidio) {
		this.gsssSubsidio = gsssSubsidio;
	}

	public BigDecimal getGsssISR() {
		return gsssISR;
	}

	public void setGsssISR(BigDecimal gsssISR) {
		this.gsssISR = gsssISR;
	}

	public BigDecimal getGsssCoImss() {
		return gsssCoImss;
	}

	public void setGsssCoImss(BigDecimal gsssCoImss) {
		this.gsssCoImss = gsssCoImss;
	}

	public BigDecimal getGsssNetoNomina() {
		return gsssNetoNomina;
	}

	public void setGsssNetoNomina(BigDecimal gsssNetoNomina) {
		this.gsssNetoNomina = gsssNetoNomina;
	}

	public BigDecimal getGsePlanPensionesPrivada() {
		return gsePlanPensionesPrivada;
	}

	public void setGsePlanPensionesPrivada(BigDecimal gsePlanPensionesPrivada) {
		this.gsePlanPensionesPrivada = gsePlanPensionesPrivada;
	}

	public BigDecimal getGsNetoTotal() {
		return gsNetoTotal;
	}

	public void setGsNetoTotal(BigDecimal gsNetoTotal) {
		this.gsNetoTotal = gsNetoTotal;
	}

	public BigDecimal getGscfmPercepcionTotal() {
		return gscfmPercepcionTotal;
	}

	public void setGscfmPercepcionTotal(BigDecimal gscfmPercepcionTotal) {
		this.gscfmPercepcionTotal = gscfmPercepcionTotal;
	}

	public BigDecimal getGscfmCargaSocial() {
		return gscfmCargaSocial;
	}

	public void setGscfmCargaSocial(BigDecimal gscfmCargaSocial) {
		this.gscfmCargaSocial = gscfmCargaSocial;
	}

	public BigDecimal getGscfmComision() {
		return gscfmComision;
	}

	public void setGscfmComision(BigDecimal gscfmComision) {
		this.gscfmComision = gscfmComision;
	}

	public BigDecimal getGscfmSubtotalFactura() {
		return gscfmSubtotalFactura;
	}

	public void setGscfmSubtotalFactura(BigDecimal gscfmSubtotalFactura) {
		this.gscfmSubtotalFactura = gscfmSubtotalFactura;
	}

	public BigDecimal getGscfmIVA() {
		return gscfmIVA;
	}

	public void setGscfmIVA(BigDecimal gscfmIVA) {
		this.gscfmIVA = gscfmIVA;
	}

	public BigDecimal getGscfmTotalFactura() {
		return gscfmTotalFactura;
	}

	public void setGscfmTotalFactura(BigDecimal gscfmTotalFactura) {
		this.gscfmTotalFactura = gscfmTotalFactura;
	}

	public BigDecimal getGscfpppPercepcionNomina() {
		return gscfpppPercepcionNomina;
	}

	public void setGscfpppPercepcionNomina(BigDecimal gscfpppPercepcionNomina) {
		this.gscfpppPercepcionNomina = gscfpppPercepcionNomina;
	}

	public BigDecimal getGscfpppCargaSocial() {
		return gscfpppCargaSocial;
	}

	public void setGscfpppCargaSocial(BigDecimal gscfpppCargaSocial) {
		this.gscfpppCargaSocial = gscfpppCargaSocial;
	}

	public BigDecimal getGscfpppCostoNomina() {
		return gscfpppCostoNomina;
	}

	public void setGscfpppCostoNomina(BigDecimal gscfpppCostoNomina) {
		this.gscfpppCostoNomina = gscfpppCostoNomina;
	}

	public BigDecimal getGscfpppPPP() {
		return gscfpppPPP;
	}

	public void setGscfpppPPP(BigDecimal gscfpppPPP) {
		this.gscfpppPPP = gscfpppPPP;
	}

	public BigDecimal getGscfpppComision() {
		return gscfpppComision;
	}

	public void setGscfpppComision(BigDecimal gscfpppComision) {
		this.gscfpppComision = gscfpppComision;
	}

	public BigDecimal getGscfpppSubtotalFactura() {
		return gscfpppSubtotalFactura;
	}

	public void setGscfpppSubtotalFactura(BigDecimal gscfpppSubtotalFactura) {
		this.gscfpppSubtotalFactura = gscfpppSubtotalFactura;
	}

	public BigDecimal getGscfpppIVA() {
		return gscfpppIVA;
	}

	public void setGscfpppIVA(BigDecimal gscfpppIVA) {
		this.gscfpppIVA = gscfpppIVA;
	}

	public BigDecimal getGscfpppTotalFactura() {
		return gscfpppTotalFactura;
	}

	public void setGscfpppTotalFactura(BigDecimal gscfpppTotalFactura) {
		this.gscfpppTotalFactura = gscfpppTotalFactura;
	}

	public BigDecimal getGscfpppCostoTotal() {
		return gscfpppCostoTotal;
	}

	public void setGscfpppCostoTotal(BigDecimal gscfpppCostoTotal) {
		this.gscfpppCostoTotal = gscfpppCostoTotal;
	}

	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Cotizacion getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Cotizacion idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public ColaboradoresTemp getIdColaboradorTemp() {
		return idColaboradorTemp;
	}

	public void setIdColaboradorTemp(ColaboradoresTemp idColaboradorTemp) {
		this.idColaboradorTemp = idColaboradorTemp;
	}

	public BigDecimal getdDiferenciaNeto() {
		return dDiferenciaNeto;
	}

	public void setdDiferenciaNeto(BigDecimal dDiferenciaNeto) {
		this.dDiferenciaNeto = dDiferenciaNeto;
	}

	public BigDecimal getdDiferenciaCostoMixto() {
		return dDiferenciaCostoMixto;
	}

	public void setdDiferenciaCostoMixto(BigDecimal dDiferenciaCostoMixto) {
		this.dDiferenciaCostoMixto = dDiferenciaCostoMixto;
	}

	public BigDecimal getdDiferenciaCostoSoloPPP() {
		return dDiferenciaCostoSoloPPP;
	}

	public void setdDiferenciaCostoSoloPPP(BigDecimal dDiferenciaCostoSoloPPP) {
		this.dDiferenciaCostoSoloPPP = dDiferenciaCostoSoloPPP;
	}

	public BigDecimal getaOtros() {
		return aOtros;
	}

	public void setaOtros(BigDecimal aOtros) {
		this.aOtros = aOtros;
	}

	public BigDecimal getaNetoTotal() {
		return aNetoTotal;
	}

	public void setaNetoTotal(BigDecimal aNetoTotal) {
		this.aNetoTotal = aNetoTotal;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCotizacionColaborador != null ? idCotizacionColaborador.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CotizacionColaborador)) {
			return false;
		}
		CotizacionColaborador other = (CotizacionColaborador) object;
		if ((this.idCotizacionColaborador == null && other.idCotizacionColaborador != null)
				|| (this.idCotizacionColaborador != null
						&& !this.idCotizacionColaborador.equals(other.idCotizacionColaborador))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mx.com.consolida.entity.CotizacionColaborador[ idCotizacionColaborador=" + idCotizacionColaborador
				+ " ]";
	}

}
