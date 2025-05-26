package mx.com.consolida.dto; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.generico.CatalogosCotizadorDTO;

/**
 *
 * @author Abel
 */
public class CotizacionDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCotizacion;
    private Integer porcentajeNomFis;
    private Integer porcentajePpp;
    private BigDecimal aguinaldo;
    private Integer diasVacaciones;
    private BigDecimal primaVacacional;
    private Integer tieneProvedor;
    private BigDecimal feeActual;
    private Date fechaAlta;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Long indEstatus;
    private CatGeneralDto idVacaciones;
    private CatGeneralDto idCosteoAsimilable;
    private CatGeneralDto idDias;
    private CatGeneralDto idImss;
    private CatTipoPagoDto idPeriodicidad;
    private CatGeneralDto idPrestaciones;
    private CatGeneralDto idTipo;
    private CatGeneralDto idTipoCotizacion;
    private CatGeneralDto idTipoSolCotizacion;
    private CatGeneralDto idZona;
    private ClienteTempDto idCliente;
    private CatGeneralDto idEstatus;
    private String observacionAutorizador;
    private BigDecimal comisionImss;
    private BigDecimal comisionPpp;
    private BigDecimal dgMontoBrutoMensual;
    private BigDecimal dgVSM;
    private BigDecimal dgporcCotizacionDeseado;
//    private List<ColaboradoresTempDto> colaboradoresTempList;
    private CatGeneralDto idTipoEsquema;
    
    private CatEstatusDto catEstatusCotizacion;
    private Integer totalColaboradores;
    private CatalogosCotizadorDTO catalogosCotizadorDTO;
    private String cveCotizacion;
 
    private String fechaAltaString;
    private Long tieneCostosAdicionales;
    private CostoAdicionalDto costoAdicional;
    private Long numColaboradores;
    
    private String observacionBitSolicitud;
    
    public CotizacionDto() {
    }

    public CotizacionDto(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getPorcentajeNomFis() {
        return porcentajeNomFis;
    }

    public void setPorcentajeNomFis(Integer porcentajeNomFis) {
        this.porcentajeNomFis = porcentajeNomFis;
    }

    public Integer getPorcentajePpp() {
        return porcentajePpp;
    }

    public void setPorcentajePpp(Integer porcentajePpp) {
        this.porcentajePpp = porcentajePpp;
    }

    public BigDecimal getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(BigDecimal aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Integer getDiasVacaciones() {
        return diasVacaciones;
    }

    public void setDiasVacaciones(Integer diasVacaciones) {
        this.diasVacaciones = diasVacaciones;
    }

    public BigDecimal getPrimaVacacional() {
        return primaVacacional;
    }

    public void setPrimaVacacional(BigDecimal primaVacacional) {
        this.primaVacacional = primaVacacional;
    }

    public Integer getTieneProvedor() {
        return tieneProvedor;
    }

    public void setTieneProvedor(Integer tieneProvedor) {
        this.tieneProvedor = tieneProvedor;
    }

    public BigDecimal getFeeActual() {
        return feeActual;
    }

    public void setFeeActual(BigDecimal feeActual) {
        this.feeActual = feeActual;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Long usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Long indEstatus) {
        this.indEstatus = indEstatus;
    }

//    public List<ColaboradoresTempDto> getColaboradoresTempList() {
//        return colaboradoresTempList;
//    }
//
//    public void setColaboradoresTempList(List<ColaboradoresTempDto> colaboradoresTempList) {
//        this.colaboradoresTempList = colaboradoresTempList;
//    }

    public CatGeneralDto getIdVacaciones() {
        return idVacaciones;
    }

    public void setIdVacaciones(CatGeneralDto idVacaciones) {
        this.idVacaciones = idVacaciones;
    }

    public CatGeneralDto getIdCosteoAsimilable() {
        return idCosteoAsimilable;
    }

    public void setIdCosteoAsimilable(CatGeneralDto idCosteoAsimilable) {
        this.idCosteoAsimilable = idCosteoAsimilable;
    }

    public CatGeneralDto getIdDias() {
        return idDias;
    }

    public void setIdDias(CatGeneralDto idDias) {
        this.idDias = idDias;
    }

    public CatGeneralDto getIdImss() {
        return idImss;
    }

    public void setIdImss(CatGeneralDto idImss) {
        this.idImss = idImss;
    }

    public CatTipoPagoDto getIdPeriodicidad() {
        return idPeriodicidad;
    }

    public void setIdPeriodicidad(CatTipoPagoDto idPeriodicidad) {
        this.idPeriodicidad = idPeriodicidad;
    }

    public CatGeneralDto getIdPrestaciones() {
        return idPrestaciones;
    }

    public void setIdPrestaciones(CatGeneralDto idPrestaciones) {
        this.idPrestaciones = idPrestaciones;
    }

    public CatGeneralDto getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(CatGeneralDto idTipo) {
        this.idTipo = idTipo;
    }

    public CatGeneralDto getIdTipoCotizacion() {
        return idTipoCotizacion;
    }

    public void setIdTipoCotizacion(CatGeneralDto idTipoCotizacion) {
        this.idTipoCotizacion = idTipoCotizacion;
    }

    public CatGeneralDto getIdZona() {
        return idZona;
    }

    public void setIdZona(CatGeneralDto idZona) {
        this.idZona = idZona;
    }

    public ClienteTempDto getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(ClienteTempDto idCliente) {
		this.idCliente = idCliente;
	}

	public CatGeneralDto getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(CatGeneralDto idEstatus) {
		this.idEstatus = idEstatus;
	}

	public CatalogosCotizadorDTO getCatalogosCotizadorDTO() {
		return catalogosCotizadorDTO;
	}

	public void setCatalogosCotizadorDTO(CatalogosCotizadorDTO catalogosCotizadorDTO) {
		this.catalogosCotizadorDTO = catalogosCotizadorDTO;
	}

	public Integer getTotalColaboradores() {
		return totalColaboradores;
	}

	public void setTotalColaboradores(Integer totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}

	public CatEstatusDto getCatEstatusCotizacion() {
		return catEstatusCotizacion;
	}

	public void setCatEstatusCotizacion(CatEstatusDto catEstatusCotizacion) {
		this.catEstatusCotizacion = catEstatusCotizacion;
	}

	public String getObservacionAutorizador() {
		return observacionAutorizador;
	}

	public void setObservacionAutorizador(String observacionAutorizador) {
		this.observacionAutorizador = observacionAutorizador;
	}

	public BigDecimal getComisionImss() {
		return comisionImss;
	}

	public void setComisionImss(BigDecimal comisionImss) {
		this.comisionImss = comisionImss;
	}

	public BigDecimal getComisionPpp() {
		return comisionPpp;
	}

	public void setComisionPpp(BigDecimal comisionPpp) {
		this.comisionPpp = comisionPpp;
	}

	public BigDecimal getDgMontoBrutoMensual() {
		return dgMontoBrutoMensual;
	}

	public void setDgMontoBrutoMensual(BigDecimal dgMontoBrutoMensual) {
		this.dgMontoBrutoMensual = dgMontoBrutoMensual;
	}

	public BigDecimal getDgVSM() {
		return dgVSM;
	}

	public void setDgVSM(BigDecimal dgVSM) {
		this.dgVSM = dgVSM;
	}

	public BigDecimal getDgporcCotizacionDeseado() {
		return dgporcCotizacionDeseado;
	}

	public void setDgporcCotizacionDeseado(BigDecimal dgporcCotizacionDeseado) {
		this.dgporcCotizacionDeseado = dgporcCotizacionDeseado;
	}

	public CatGeneralDto getIdTipoSolCotizacion() {
		return idTipoSolCotizacion;
	}

	public void setIdTipoSolCotizacion(CatGeneralDto idTipoSolCotizacion) {
		this.idTipoSolCotizacion = idTipoSolCotizacion;
	}

	public CatGeneralDto getIdTipoEsquema() {
		return idTipoEsquema;
	}

	public void setIdTipoEsquema(CatGeneralDto idTipoEsquema) {
		this.idTipoEsquema = idTipoEsquema;
	}

	public CostoAdicionalDto getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(CostoAdicionalDto costoAdicional) {
		this.costoAdicional = costoAdicional;
	}

	public String getFechaAltaString() {
		if(fechaAlta!=null) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		fechaAltaString = formatter.format(fechaAlta);
		}
		return fechaAltaString;
	}

	public void setFechaAltaString(String fechaAltaString) {
		this.fechaAltaString = fechaAltaString;
	}

	public String getCveCotizacion() {
		return cveCotizacion;
	}

	public void setCveCotizacion(String cveCotizacion) {
		this.cveCotizacion = cveCotizacion;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getTieneCostosAdicionales() {
		return tieneCostosAdicionales;
	}

	public void setTieneCostosAdicionales(Long tieneCostosAdicionales) {
		this.tieneCostosAdicionales = tieneCostosAdicionales;
	}

	public Long getNumColaboradores() {
		return numColaboradores;
	}

	public void setNumColaboradores(Long numColaboradores) {
		this.numColaboradores = numColaboradores;
	}

	public String getObservacionBitSolicitud() {
		return observacionBitSolicitud;
	}

	public void setObservacionBitSolicitud(String observacionBitSolicitud) {
		this.observacionBitSolicitud = observacionBitSolicitud;
	}

	
/*
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacion != null ? idCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CotizacionDto)) {
            return false;
        }
        CotizacionDto other = (CotizacionDto) object;
        if ((this.idCotizacion == null && other.idCotizacion != null) || (this.idCotizacion != null && !this.idCotizacion.equals(other.idCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.dto.CotizacionDto[ idCotizacion=" + idCotizacion + " ]";
    }
    */
}

