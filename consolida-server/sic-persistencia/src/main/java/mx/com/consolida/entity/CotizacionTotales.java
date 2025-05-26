package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import mx.com.consolida.dto.CotizacionTotalesDto;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cotizacion_totales")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "CotizacionTotales.findAll", query = "SELECT c FROM CotizacionTotales c") })
public class CotizacionTotales implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cotizacion_totales")
	private Long idCotizacionTotales;
	@Column(name = "TotaldDiferenciaNeto")
	private BigDecimal totaldDiferenciaNeto;
	@Column(name = "TotaldDiferenciaCostoMixto")
	private BigDecimal totaldDiferenciaCostoMixto;
	@Column(name = "TotaldDiferenciaCostoSoloPPP")
	private BigDecimal totaldDiferenciaCostoSoloPPP;
	@Column(name = "TotalassSuedo")
	private BigDecimal totalassSuedo;
	@Column(name = "TotalassGravados")
	private BigDecimal totalassGravados;
	@Column(name = "TotalassExentos")
	private BigDecimal totalassExentos;
	@Column(name = "TotalassSubsidio")
	private BigDecimal totalassSubsidio;
	@Column(name = "TotalassISR")
	private BigDecimal totalassISR;
	@Column(name = "TotalassCoImss")
	private BigDecimal totalassCoImss;
	@Column(name = "TotalassNetoNomina")
	private BigDecimal totalassNetoNomina;
	@Column(name = "TotalaasAsimialdos")
	private BigDecimal totalaasAsimialdos;
	@Column(name = "TotalaasISR")
	private BigDecimal totalaasISR;
	@Column(name = "TotalaasNetoAsimilados")
	private BigDecimal totalaasNetoAsimilados;
	@Column(name = "TotalaOtros")
	private BigDecimal totalaOtros;
	@Column(name = "TotalaNetoTotal")
	private BigDecimal totalaNetoTotal;
	@Column(name = "TotalacfaPercepcionTotal")
	private BigDecimal totalacfaPercepcionTotal;
	@Column(name = "TotalacfaCargaSocial")
	private BigDecimal totalacfaCargaSocial;
	@Column(name = "TotalacfaComision")
	private BigDecimal totalacfaComision;
	@Column(name = "TotalacfaSubtotalFactura")
	private BigDecimal totalacfaSubtotalFactura;
	@Column(name = "TotalacfaIVA")
	private BigDecimal totalacfaIVA;
	@Column(name = "TotalacfaTotalFactura")
	private BigDecimal totalacfaTotalFactura;
	@Column(name = "TotalgsssSuedo")
	private BigDecimal totalgsssSuedo;
	@Column(name = "TotalgsssExentos")
	private BigDecimal totalgsssExentos;
	@Column(name = "TotalgsssSubsidio")
	private BigDecimal totalgsssSubsidio;
	@Column(name = "TotalgsssISR")
	private BigDecimal totalgsssISR;
	@Column(name = "TotalgsssCoImss")
	private BigDecimal totalgsssCoImss;
	@Column(name = "TotalgsssNetoNomina")
	private BigDecimal totalgsssNetoNomina;
	@Column(name = "TotalgsePlanPensionesPrivada")
	private BigDecimal totalgsePlanPensionesPrivada;
	@Column(name = "TotalgsNetoTotal")
	private BigDecimal totalgsNetoTotal;
	@Column(name = "TotalgscfmPercepcionTotal")
	private BigDecimal totalgscfmPercepcionTotal;
	@Column(name = "TotalgscfmCargaSocial")
	private BigDecimal totalgscfmCargaSocial;
	@Column(name = "TotalgscfmComision")
	private BigDecimal totalgscfmComision;
	@Column(name = "TotalgscfmSubtotalFactura")
	private BigDecimal totalgscfmSubtotalFactura;
	@Column(name = "TotalgscfmIVA")
	private BigDecimal totalgscfmIVA;
	@Column(name = "TotalgscfmTotalFactura")
	private BigDecimal totalgscfmTotalFactura;
	@Column(name = "TotalgscfpppPercepcionNomina")
	private BigDecimal totalgscfpppPercepcionNomina;
	@Column(name = "TotalgscfpppCargaSocial")
	private BigDecimal totalgscfpppCargaSocial;
	@Column(name = "TotalgscfpppCostoNomina")
	private BigDecimal totalgscfpppCostoNomina;
	@Column(name = "TotalgscfpppComision")
	private BigDecimal totalgscfpppComision;
	@Column(name = "TotalgscfpppSubtotalFactura")
	private BigDecimal totalgscfpppSubtotalFactura;
	@Column(name = "TotalgscfpppIVA")
	private BigDecimal totalgscfpppIVA;
	@Column(name = "TotalgscfpppTotalFactura")
	private BigDecimal totalgscfpppTotalFactura;
	@Column(name = "TotalgscfpppCostoTotal")
	private BigDecimal totalgscfpppCostoTotal;
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
	@ManyToOne
	private Cotizacion idCotizacion;
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;


	public CotizacionTotales() {
	}



	public CotizacionTotales(CotizacionTotalesDto dto) {
		super();
		this.idCotizacionTotales = dto.getIdCotizacionTotales();
		this.totaldDiferenciaNeto = dto.getTotaldDiferenciaNeto();
		this.totaldDiferenciaCostoMixto = dto.getTotaldDiferenciaCostoMixto();
		this.totaldDiferenciaCostoSoloPPP = dto.getTotaldDiferenciaCostoSoloPPP();
		this.totalassSuedo = dto.getTotalassSuedo();
		this.totalassGravados = dto.getTotalassGravados();
		this.totalassExentos = dto.getTotalassExentos();
		this.totalassSubsidio = dto.getTotalassSubsidio();
		this.totalassISR = dto.getTotalassISR();
		this.totalassCoImss = dto.getTotalassCoImss();
		this.totalassNetoNomina = dto.getTotalassNetoNomina();
		this.totalaasAsimialdos = dto.getTotalaasAsimialdos();
		this.totalaasISR = dto.getTotalaasISR();
		this.totalaasNetoAsimilados = dto.getTotalaasNetoAsimilados();
		this.totalaOtros = dto.getTotalaOtros();
		this.totalaNetoTotal = dto.getTotalaNetoTotal();
		this.totalacfaPercepcionTotal = dto.getTotalacfaPercepcionTotal();
		this.totalacfaCargaSocial = dto.getTotalacfaCargaSocial();
		this.totalacfaComision = dto.getTotalacfaComision();
		this.totalacfaSubtotalFactura = dto.getTotalacfaSubtotalFactura();
		this.totalacfaIVA = dto.getTotalacfaIVA();
		this.totalacfaTotalFactura = dto.getTotalacfaTotalFactura();
		this.totalgsssSuedo = dto.getTotalgsssSuedo();
		this.totalgsssExentos = dto.getTotalgsssExentos();
		this.totalgsssSubsidio = dto.getTotalgsssSubsidio();
		this.totalgsssISR = dto.getTotalgsssISR();
		this.totalgsssCoImss = dto.getTotalgsssCoImss();
		this.totalgsssNetoNomina = dto.getTotalgsssNetoNomina();
		this.totalgsePlanPensionesPrivada = dto.getTotalgsePlanPensionesPrivada();
		this.totalgsNetoTotal = dto.getTotalgsNetoTotal();
		this.totalgscfmPercepcionTotal = dto.getTotalgscfmPercepcionTotal();
		this.totalgscfmCargaSocial = dto.getTotalgscfmCargaSocial();
		this.totalgscfmComision = dto.getTotalgscfmComision();
		this.totalgscfmSubtotalFactura = dto.getTotalgscfmSubtotalFactura();
		this.totalgscfmIVA = dto.getTotalgscfmIVA();
		this.totalgscfmTotalFactura = dto.getTotalgscfmTotalFactura();
		this.totalgscfpppPercepcionNomina = dto.getTotalgscfpppPercepcionNomina();
		this.totalgscfpppCargaSocial = dto.getTotalgscfpppCargaSocial();
		this.totalgscfpppCostoNomina = dto.getTotalgscfpppCostoNomina();
		this.totalgscfpppComision = dto.getTotalgscfpppComision();
		this.totalgscfpppSubtotalFactura = dto.getTotalgscfpppSubtotalFactura();
		this.totalgscfpppIVA = dto.getTotalgscfpppIVA();
		this.totalgscfpppTotalFactura = dto.getTotalgscfpppTotalFactura();
		this.totalgscfpppCostoTotal = dto.getTotalgscfpppCostoTotal();
		this.idUsuarioAlta = dto.getIdUsuarioAlta();
		this.fechaAlta = dto.getFechaAlta();
		this.indEstatus = dto.getIndEstatus();
		this.idCotizacion = dto.getIdCotizacion()!=null? new Cotizacion(dto.getIdCotizacion().getIdCotizacion()):null;
		this.fechaModificacion = dto.getFechaModificacion();
	}



	public CotizacionTotales(Long idCotizacionTotales) {
		this.idCotizacionTotales = idCotizacionTotales;
	}

	public Long getIdCotizacionTotales() {
		return idCotizacionTotales;
	}

	public void setIdCotizacionTotales(Long idCotizacionTotales) {
		this.idCotizacionTotales = idCotizacionTotales;
	}

	public BigDecimal getTotaldDiferenciaNeto() {
		return totaldDiferenciaNeto;
	}

	public void setTotaldDiferenciaNeto(BigDecimal totaldDiferenciaNeto) {
		this.totaldDiferenciaNeto = totaldDiferenciaNeto;
	}

	public BigDecimal getTotaldDiferenciaCostoMixto() {
		return totaldDiferenciaCostoMixto;
	}

	public void setTotaldDiferenciaCostoMixto(BigDecimal totaldDiferenciaCostoMixto) {
		this.totaldDiferenciaCostoMixto = totaldDiferenciaCostoMixto;
	}

	public BigDecimal getTotaldDiferenciaCostoSoloPPP() {
		return totaldDiferenciaCostoSoloPPP;
	}

	public void setTotaldDiferenciaCostoSoloPPP(BigDecimal totaldDiferenciaCostoSoloPPP) {
		this.totaldDiferenciaCostoSoloPPP = totaldDiferenciaCostoSoloPPP;
	}

	public BigDecimal getTotalassSuedo() {
		return totalassSuedo;
	}

	public void setTotalassSuedo(BigDecimal totalassSuedo) {
		this.totalassSuedo = totalassSuedo;
	}

	public BigDecimal getTotalassGravados() {
		return totalassGravados;
	}

	public void setTotalassGravados(BigDecimal totalassGravados) {
		this.totalassGravados = totalassGravados;
	}

	public BigDecimal getTotalassExentos() {
		return totalassExentos;
	}

	public void setTotalassExentos(BigDecimal totalassExentos) {
		this.totalassExentos = totalassExentos;
	}

	public BigDecimal getTotalassSubsidio() {
		return totalassSubsidio;
	}

	public void setTotalassSubsidio(BigDecimal totalassSubsidio) {
		this.totalassSubsidio = totalassSubsidio;
	}

	public BigDecimal getTotalassISR() {
		return totalassISR;
	}

	public void setTotalassISR(BigDecimal totalassISR) {
		this.totalassISR = totalassISR;
	}

	public BigDecimal getTotalassCoImss() {
		return totalassCoImss;
	}

	public void setTotalassCoImss(BigDecimal totalassCoImss) {
		this.totalassCoImss = totalassCoImss;
	}

	public BigDecimal getTotalassNetoNomina() {
		return totalassNetoNomina;
	}

	public void setTotalassNetoNomina(BigDecimal totalassNetoNomina) {
		this.totalassNetoNomina = totalassNetoNomina;
	}

	public BigDecimal getTotalaasAsimialdos() {
		return totalaasAsimialdos;
	}

	public void setTotalaasAsimialdos(BigDecimal totalaasAsimialdos) {
		this.totalaasAsimialdos = totalaasAsimialdos;
	}

	public BigDecimal getTotalaasISR() {
		return totalaasISR;
	}

	public void setTotalaasISR(BigDecimal totalaasISR) {
		this.totalaasISR = totalaasISR;
	}

	public BigDecimal getTotalaasNetoAsimilados() {
		return totalaasNetoAsimilados;
	}

	public void setTotalaasNetoAsimilados(BigDecimal totalaasNetoAsimilados) {
		this.totalaasNetoAsimilados = totalaasNetoAsimilados;
	}

	public BigDecimal getTotalaOtros() {
		return totalaOtros;
	}

	public void setTotalaOtros(BigDecimal totalaOtros) {
		this.totalaOtros = totalaOtros;
	}

	public BigDecimal getTotalaNetoTotal() {
		return totalaNetoTotal;
	}

	public void setTotalaNetoTotal(BigDecimal totalaNetoTotal) {
		this.totalaNetoTotal = totalaNetoTotal;
	}

	public BigDecimal getTotalacfaPercepcionTotal() {
		return totalacfaPercepcionTotal;
	}

	public void setTotalacfaPercepcionTotal(BigDecimal totalacfaPercepcionTotal) {
		this.totalacfaPercepcionTotal = totalacfaPercepcionTotal;
	}

	public BigDecimal getTotalacfaCargaSocial() {
		return totalacfaCargaSocial;
	}

	public void setTotalacfaCargaSocial(BigDecimal totalacfaCargaSocial) {
		this.totalacfaCargaSocial = totalacfaCargaSocial;
	}

	public BigDecimal getTotalacfaComision() {
		return totalacfaComision;
	}

	public void setTotalacfaComision(BigDecimal totalacfaComision) {
		this.totalacfaComision = totalacfaComision;
	}

	public BigDecimal getTotalacfaSubtotalFactura() {
		return totalacfaSubtotalFactura;
	}

	public void setTotalacfaSubtotalFactura(BigDecimal totalacfaSubtotalFactura) {
		this.totalacfaSubtotalFactura = totalacfaSubtotalFactura;
	}

	public BigDecimal getTotalacfaIVA() {
		return totalacfaIVA;
	}

	public void setTotalacfaIVA(BigDecimal totalacfaIVA) {
		this.totalacfaIVA = totalacfaIVA;
	}

	public BigDecimal getTotalacfaTotalFactura() {
		return totalacfaTotalFactura;
	}

	public void setTotalacfaTotalFactura(BigDecimal totalacfaTotalFactura) {
		this.totalacfaTotalFactura = totalacfaTotalFactura;
	}

	public BigDecimal getTotalgsssSuedo() {
		return totalgsssSuedo;
	}

	public void setTotalgsssSuedo(BigDecimal totalgsssSuedo) {
		this.totalgsssSuedo = totalgsssSuedo;
	}

	public BigDecimal getTotalgsssExentos() {
		return totalgsssExentos;
	}

	public void setTotalgsssExentos(BigDecimal totalgsssExentos) {
		this.totalgsssExentos = totalgsssExentos;
	}

	public BigDecimal getTotalgsssSubsidio() {
		return totalgsssSubsidio;
	}

	public void setTotalgsssSubsidio(BigDecimal totalgsssSubsidio) {
		this.totalgsssSubsidio = totalgsssSubsidio;
	}

	public BigDecimal getTotalgsssISR() {
		return totalgsssISR;
	}

	public void setTotalgsssISR(BigDecimal totalgsssISR) {
		this.totalgsssISR = totalgsssISR;
	}

	public BigDecimal getTotalgsssCoImss() {
		return totalgsssCoImss;
	}

	public void setTotalgsssCoImss(BigDecimal totalgsssCoImss) {
		this.totalgsssCoImss = totalgsssCoImss;
	}

	public BigDecimal getTotalgsssNetoNomina() {
		return totalgsssNetoNomina;
	}

	public void setTotalgsssNetoNomina(BigDecimal totalgsssNetoNomina) {
		this.totalgsssNetoNomina = totalgsssNetoNomina;
	}

	public BigDecimal getTotalgsePlanPensionesPrivada() {
		return totalgsePlanPensionesPrivada;
	}

	public void setTotalgsePlanPensionesPrivada(BigDecimal totalgsePlanPensionesPrivada) {
		this.totalgsePlanPensionesPrivada = totalgsePlanPensionesPrivada;
	}

	public BigDecimal getTotalgsNetoTotal() {
		return totalgsNetoTotal;
	}

	public void setTotalgsNetoTotal(BigDecimal totalgsNetoTotal) {
		this.totalgsNetoTotal = totalgsNetoTotal;
	}

	public BigDecimal getTotalgscfmPercepcionTotal() {
		return totalgscfmPercepcionTotal;
	}

	public void setTotalgscfmPercepcionTotal(BigDecimal totalgscfmPercepcionTotal) {
		this.totalgscfmPercepcionTotal = totalgscfmPercepcionTotal;
	}

	public BigDecimal getTotalgscfmCargaSocial() {
		return totalgscfmCargaSocial;
	}

	public void setTotalgscfmCargaSocial(BigDecimal totalgscfmCargaSocial) {
		this.totalgscfmCargaSocial = totalgscfmCargaSocial;
	}

	public BigDecimal getTotalgscfmComision() {
		return totalgscfmComision;
	}

	public void setTotalgscfmComision(BigDecimal totalgscfmComision) {
		this.totalgscfmComision = totalgscfmComision;
	}

	public BigDecimal getTotalgscfmSubtotalFactura() {
		return totalgscfmSubtotalFactura;
	}

	public void setTotalgscfmSubtotalFactura(BigDecimal totalgscfmSubtotalFactura) {
		this.totalgscfmSubtotalFactura = totalgscfmSubtotalFactura;
	}

	public BigDecimal getTotalgscfmIVA() {
		return totalgscfmIVA;
	}

	public void setTotalgscfmIVA(BigDecimal totalgscfmIVA) {
		this.totalgscfmIVA = totalgscfmIVA;
	}

	public BigDecimal getTotalgscfmTotalFactura() {
		return totalgscfmTotalFactura;
	}

	public void setTotalgscfmTotalFactura(BigDecimal totalgscfmTotalFactura) {
		this.totalgscfmTotalFactura = totalgscfmTotalFactura;
	}

	public BigDecimal getTotalgscfpppPercepcionNomina() {
		return totalgscfpppPercepcionNomina;
	}

	public void setTotalgscfpppPercepcionNomina(BigDecimal totalgscfpppPercepcionNomina) {
		this.totalgscfpppPercepcionNomina = totalgscfpppPercepcionNomina;
	}

	public BigDecimal getTotalgscfpppCargaSocial() {
		return totalgscfpppCargaSocial;
	}

	public void setTotalgscfpppCargaSocial(BigDecimal totalgscfpppCargaSocial) {
		this.totalgscfpppCargaSocial = totalgscfpppCargaSocial;
	}

	public BigDecimal getTotalgscfpppCostoNomina() {
		return totalgscfpppCostoNomina;
	}

	public void setTotalgscfpppCostoNomina(BigDecimal totalgscfpppCostoNomina) {
		this.totalgscfpppCostoNomina = totalgscfpppCostoNomina;
	}

	public BigDecimal getTotalgscfpppComision() {
		return totalgscfpppComision;
	}

	public void setTotalgscfpppComision(BigDecimal totalgscfpppComision) {
		this.totalgscfpppComision = totalgscfpppComision;
	}

	public BigDecimal getTotalgscfpppSubtotalFactura() {
		return totalgscfpppSubtotalFactura;
	}

	public void setTotalgscfpppSubtotalFactura(BigDecimal totalgscfpppSubtotalFactura) {
		this.totalgscfpppSubtotalFactura = totalgscfpppSubtotalFactura;
	}

	public BigDecimal getTotalgscfpppIVA() {
		return totalgscfpppIVA;
	}

	public void setTotalgscfpppIVA(BigDecimal totalgscfpppIVA) {
		this.totalgscfpppIVA = totalgscfpppIVA;
	}

	public BigDecimal getTotalgscfpppTotalFactura() {
		return totalgscfpppTotalFactura;
	}

	public void setTotalgscfpppTotalFactura(BigDecimal totalgscfpppTotalFactura) {
		this.totalgscfpppTotalFactura = totalgscfpppTotalFactura;
	}

	public BigDecimal getTotalgscfpppCostoTotal() {
		return totalgscfpppCostoTotal;
	}

	public void setTotalgscfpppCostoTotal(BigDecimal totalgscfpppCostoTotal) {
		this.totalgscfpppCostoTotal = totalgscfpppCostoTotal;
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
		hash += (idCotizacionTotales != null ? idCotizacionTotales.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CotizacionTotales)) {
			return false;
		}
		CotizacionTotales other = (CotizacionTotales) object;
		if ((this.idCotizacionTotales == null && other.idCotizacionTotales != null)
				|| (this.idCotizacionTotales != null && !this.idCotizacionTotales.equals(other.idCotizacionTotales))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mx.com.consolida.entity.CotizacionTotales[ idCotizacionTotales=" + idCotizacionTotales + " ]";
	}

}
