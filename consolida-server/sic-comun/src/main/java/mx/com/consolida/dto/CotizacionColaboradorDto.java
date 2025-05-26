package mx.com.consolida.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author Abel
 */
public class CotizacionColaboradorDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCotizacionColaborador;
    private String dgNombre;
    private String dgPuesto;
    private String dgPrimaDeRiesgo;
    private Date dgFechaDeAntIguedad;
    private BigDecimal dgporcIMSS;
    private BigDecimal dgporcPPP;
    private BigDecimal dgNetoBruto;
    private BigDecimal dgVSM;
    private BigDecimal dgMontoBrutoMensual;
    private BigDecimal dgporcBeneficioFiscal;
    private BigDecimal dDiferenciaNeto;
    private BigDecimal dDiferenciaCostoMixto;
    private BigDecimal dDiferenciaCostoSoloPPP;
    private BigDecimal assSD;
    private BigDecimal assSDI;
    private BigDecimal assSuedo;
    private BigDecimal assGravados;
    private BigDecimal assExentos;
    private BigDecimal assSubsidio;
    private BigDecimal assISR;
    private BigDecimal assCoImss;
    private BigDecimal assNetoNomina;
    private BigDecimal aasAsimialdos;
    private BigDecimal aasISR;
    private BigDecimal aasNetoAsimilados;
    private BigDecimal aOtros;
    private BigDecimal aNetoTotal;
    private BigDecimal acfaPercepcionTotal;
    private BigDecimal acfaCargaSocial;
    private BigDecimal acfaComision;
    private BigDecimal acfaSubtotalFactura;
    private BigDecimal acfaIVA;
    private BigDecimal acfaTotalFactura;
    private BigDecimal gsssSD;
    private BigDecimal gsssSDI;
    private BigDecimal gsssSuedo;
    private BigDecimal gsssExentos;
    private BigDecimal gsssSubsidio;
    private BigDecimal gsssISR;
    private BigDecimal gsssCoImss;
    private BigDecimal gsssNetoNomina;
    private BigDecimal gsePlanPensionesPrivada;
    private BigDecimal gsNetoTotal;
    private BigDecimal gscfmPercepcionTotal;
    private BigDecimal gscfmCargaSocial;
    private BigDecimal gscfmComision;
    private BigDecimal gscfmSubtotalFactura;
    private BigDecimal gscfmIVA;
    private BigDecimal gscfmTotalFactura;
    private BigDecimal gscfpppPercepcionNomina;
    private BigDecimal gscfpppCargaSocial;
    private BigDecimal gscfpppCostoNomina;
    private BigDecimal gscfpppPPP;
    private BigDecimal gscfpppComision;
    private BigDecimal gscfpppSubtotalFactura;
    private BigDecimal gscfpppIVA;
    private BigDecimal gscfpppTotalFactura;
    private BigDecimal gscfpppCostoTotal;
    private Long idUsuarioAlta;
    private Date fechaAlta;
    private Long indEstatus;
    private String cotizacionColaboradorcol;
    private CotizacionDto idCotizacion;
    private ColaboradoresTempDto idColaboradorTemp;
    
    private Date fechaModificacion;
    
    
    
    
    private BigDecimal assSDAnterior;
    boolean seguirCalculando;
    private BigDecimal dgporcCotizacionDeseado;
    
    int decimalesOcupar;
    int contador ;
    BigDecimal aNetoTotalAnterior ;
    BigDecimal vsmAnterior;
    

    public CotizacionColaboradorDto() {
    }

    public CotizacionColaboradorDto(Long idCotizacionColaborador) {
        this.idCotizacionColaborador = idCotizacionColaborador;
    }

    public CotizacionColaboradorDto(Long idCotizacionColaborador, String cotizacionColaboradorcol) {
        this.idCotizacionColaborador = idCotizacionColaborador;
        this.cotizacionColaboradorcol = cotizacionColaboradorcol;
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

	public String getCotizacionColaboradorcol() {
		return cotizacionColaboradorcol;
	}

	public void setCotizacionColaboradorcol(String cotizacionColaboradorcol) {
		this.cotizacionColaboradorcol = cotizacionColaboradorcol;
	}

	public CotizacionDto getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(CotizacionDto idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public ColaboradoresTempDto getIdColaboradorTemp() {
		return idColaboradorTemp;
	}

	public void setIdColaboradorTemp(ColaboradoresTempDto idColaboradorTemp) {
		this.idColaboradorTemp = idColaboradorTemp;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	
	

	public BigDecimal getAssSDAnterior() {
		return assSDAnterior;
	}

	public void setAssSDAnterior(BigDecimal assSDAnterior) {
		this.assSDAnterior = assSDAnterior;
	}
	

	public boolean isSeguirCalculando() {
		return seguirCalculando;
	}

	public void setSeguirCalculando(boolean seguirCalculando) {
		this.seguirCalculando = seguirCalculando;
	}

	public BigDecimal getDgporcCotizacionDeseado() {
		return dgporcCotizacionDeseado;
	}

	public void setDgporcCotizacionDeseado(BigDecimal dgporcCotizacionDeseado) {
		this.dgporcCotizacionDeseado = dgporcCotizacionDeseado;
	}

	public int getDecimalesOcupar() {
		return decimalesOcupar;
	}

	public void setDecimalesOcupar(int decimalesOcupar) {
		this.decimalesOcupar = decimalesOcupar;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public BigDecimal getaNetoTotalAnterior() {
		return aNetoTotalAnterior;
	}

	public void setaNetoTotalAnterior(BigDecimal aNetoTotalAnterior) {
		this.aNetoTotalAnterior = aNetoTotalAnterior;
	}

	public BigDecimal getVsmAnterior() {
		return vsmAnterior;
	}

	public void setVsmAnterior(BigDecimal vsmAnterior) {
		this.vsmAnterior = vsmAnterior;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacionColaborador != null ? idCotizacionColaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CotizacionColaboradorDto)) {
            return false;
        }
        CotizacionColaboradorDto other = (CotizacionColaboradorDto) object;
        if ((this.idCotizacionColaborador == null && other.idCotizacionColaborador != null) || (this.idCotizacionColaborador != null && !this.idCotizacionColaborador.equals(other.idCotizacionColaborador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.dto.CotizacionColaboradorDto[ idCotizacionColaborador=" + idCotizacionColaborador + " ]";
    }
    
}
