
package mx.com.consolida.dto; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Abel
 */
public class CotizacionTotalesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private ResultadoDto resultado;
    private RentabilidadDto rentabilidadPPP;
    private RentabilidadDto rentabilidadImss;
    private RentabilidadDto rentabilidadTotal;
    private Long idCotizacionTotales;
    private BigDecimal totaldDiferenciaNeto;
    private BigDecimal totaldDiferenciaCostoMixto;
    private BigDecimal totaldDiferenciaCostoSoloPPP;
    private BigDecimal totalassSuedo;
    private BigDecimal totalassGravados;
    private BigDecimal totalassExentos;
    private BigDecimal totalassSubsidio;
    private BigDecimal totalassISR;
    private BigDecimal totalassCoImss;
    private BigDecimal totalassNetoNomina;
    private BigDecimal totalaasAsimialdos;
    private BigDecimal totalaasISR;
    private BigDecimal totalaasNetoAsimilados;
    private BigDecimal totalaOtros;
    private BigDecimal totalaNetoTotal;
    private BigDecimal totalacfaPercepcionTotal;
    private BigDecimal totalacfaCargaSocial;
    private BigDecimal totalacfaComision;
    private BigDecimal totalacfaSubtotalFactura;
    private BigDecimal totalacfaIVA;
    private BigDecimal totalacfaTotalFactura;
    private BigDecimal totalgsssSuedo;
    private BigDecimal totalgsssExentos;
    private BigDecimal totalgsssSubsidio;
    private BigDecimal totalgsssISR;
    private BigDecimal totalgsssCoImss;
    private BigDecimal totalgsssNetoNomina;
    private BigDecimal totalgsePlanPensionesPrivada;
    private BigDecimal totalgsNetoTotal;
    private BigDecimal totalgscfmPercepcionTotal;
    private BigDecimal totalgscfmCargaSocial;
    private BigDecimal totalgscfmComision;
    private BigDecimal totalgscfmSubtotalFactura;
    private BigDecimal totalgscfmIVA;
    private BigDecimal totalgscfmTotalFactura;
    private BigDecimal totalgscfpppPercepcionNomina;
    private BigDecimal totalgscfpppCargaSocial;
    private BigDecimal totalgscfpppCostoNomina;
    private BigDecimal totalgscfpppComision;
    private BigDecimal totalgscfpppSubtotalFactura;
    private BigDecimal totalgscfpppIVA;
    private BigDecimal totalgscfpppTotalFactura;
    private BigDecimal totalgscfpppCostoTotal;
    private Long idUsuarioAlta;
    private Date fechaAlta;
    private Long indEstatus;
    private CotizacionDto idCotizacion;
    private Date fechaModificacion;
    public CotizacionTotalesDto() {
		this.totaldDiferenciaNeto = new BigDecimal(0);
		this.totaldDiferenciaCostoMixto = new BigDecimal(0);
		this.totaldDiferenciaCostoSoloPPP = new BigDecimal(0);
		this.totalassSuedo = new BigDecimal(0);
		this.totalassGravados = new BigDecimal(0);
		this.totalassExentos = new BigDecimal(0);
		this.totalassSubsidio = new BigDecimal(0);
		this.totalassISR = new BigDecimal(0);
		this.totalassCoImss = new BigDecimal(0);
		this.totalassNetoNomina = new BigDecimal(0);
		this.totalaasAsimialdos = new BigDecimal(0);
		this.totalaasISR = new BigDecimal(0);
		this.totalaasNetoAsimilados = new BigDecimal(0);
		this.totalaOtros = new BigDecimal(0);
		this.totalaNetoTotal = new BigDecimal(0);
		this.totalacfaPercepcionTotal = new BigDecimal(0);
		this.totalacfaCargaSocial = new BigDecimal(0);
		this.totalacfaComision = new BigDecimal(0);
		this.totalacfaSubtotalFactura = new BigDecimal(0);
		this.totalacfaIVA = new BigDecimal(0);
		this.totalacfaTotalFactura = new BigDecimal(0);
		this.totalgsssSuedo = new BigDecimal(0);
		this.totalgsssExentos = new BigDecimal(0);
		this.totalgsssSubsidio = new BigDecimal(0);
		this.totalgsssISR = new BigDecimal(0);
		this.totalgsssCoImss = new BigDecimal(0);
		this.totalgsssNetoNomina = new BigDecimal(0);
		this.totalgsePlanPensionesPrivada = new BigDecimal(0);
		this.totalgsNetoTotal = new BigDecimal(0);
		this.totalgscfmPercepcionTotal = new BigDecimal(0);
		this.totalgscfmCargaSocial = new BigDecimal(0);
		this.totalgscfmComision = new BigDecimal(0);
		this.totalgscfmSubtotalFactura = new BigDecimal(0);
		this.totalgscfmIVA = new BigDecimal(0);
		this.totalgscfmTotalFactura = new BigDecimal(0);
		this.totalgscfpppPercepcionNomina = new BigDecimal(0);
		this.totalgscfpppCargaSocial = new BigDecimal(0);
		this.totalgscfpppCostoNomina = new BigDecimal(0);
		this.totalgscfpppComision = new BigDecimal(0);
		this.totalgscfpppSubtotalFactura = new BigDecimal(0);
		this.totalgscfpppIVA = new BigDecimal(0);
		this.totalgscfpppTotalFactura = new BigDecimal(0);
		this.totalgscfpppCostoTotal = new BigDecimal(0);
	}



	public CotizacionTotalesDto(Long idCotizacionTotales) {
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

    public CotizacionDto getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(CotizacionDto idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

    public ResultadoDto getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoDto resultado) {
        this.resultado = resultado;
    }

	public RentabilidadDto getRentabilidadPPP() {
		return rentabilidadPPP;
	}

	public void setRentabilidadPPP(RentabilidadDto rentabilidadPPP) {
		this.rentabilidadPPP = rentabilidadPPP;
	}

	public RentabilidadDto getRentabilidadImss() {
		return rentabilidadImss;
	}



	public void setRentabilidadImss(RentabilidadDto rentabilidadImss) {
		this.rentabilidadImss = rentabilidadImss;
	}



	public RentabilidadDto getRentabilidadTotal() {
		return rentabilidadTotal;
	}



	public void setRentabilidadTotal(RentabilidadDto rentabilidadTotal) {
		this.rentabilidadTotal = rentabilidadTotal;
	}



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacionTotales != null ? idCotizacionTotales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CotizacionTotalesDto)) {
            return false;
        }
        CotizacionTotalesDto other = (CotizacionTotalesDto) object;
        if ((this.idCotizacionTotales == null && other.idCotizacionTotales != null) || (this.idCotizacionTotales != null && !this.idCotizacionTotales.equals(other.idCotizacionTotales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.dto.CotizacionTotalesDto[ idCotizacionTotales=" + idCotizacionTotales + " ]";
    }
    
}
