/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Cascade;

import mx.com.consolida.dto.CotizacionDto;
/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cotizacion")
@NamedQueries({
    @NamedQuery(name = "Cotizacion.findAll", query = "SELECT c FROM Cotizacion c")})
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cotizacion")
    private Long idCotizacion;
    @Column(name = "porcentaje_nom_fis")
    private Integer porcentajeNomFis;
    @Column(name = "porcentaje_ppp")
    private Integer porcentajePpp;
    @Column(name = "aguinaldo")
    private BigDecimal aguinaldo;
    @Column(name = "dias_vacaciones")
    private Integer diasVacaciones;
    @Column(name = "prima_vacacional")
    private BigDecimal primaVacacional;
    @Column(name = "tiene_provedor")
    private Integer tieneProvedor;
    @Column(name = "fee_actual")
    private BigDecimal feeActual;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_alta")
    private Long usuarioAlta;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "ind_estatus")
    private Long indEstatus;    
    @JoinColumn(name = "id_vacaciones", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idVacaciones;
    @JoinColumn(name = "id_costeo_asimilable", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idCosteoAsimilable;
    @JoinColumn(name = "id_dias", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idDias;
    @JoinColumn(name = "id_imss", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idImss;
    @JoinColumn(name = "id_periodicidad", referencedColumnName = "id_tipo_pago")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatTipoPago idPeriodicidad;
    @JoinColumn(name = "id_prestaciones", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idPrestaciones;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idTipo;
    @JoinColumn(name = "id_tipo_cotizacion", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idTipoCotizacion;
    @JoinColumn(name = "id_tipo_sol_cotizacion", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idTipoSolCotizacion;
    @JoinColumn(name = "id_zona", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idZona;
    
    @JoinColumn(name = "id_cliente_temp", referencedColumnName = "id_cliente_temp")
    @ManyToOne(cascade = CascadeType.DETACH)
    private ClienteTemp idCliente;
    
    @JoinColumn(name = "id_estatus", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idEstatus;
    
    @Column(name = "observacion_autorizador")
    private String observacionAutorizador;
    
    @Column(name = "comision_imss")
    private BigDecimal comisionImss;
    
    @Column(name = "comision_ppp")
    private BigDecimal comisionPpp;
    
    @Column(name = "dg_MontoBrutoMensual")
    private BigDecimal dgMontoBrutoMensual;
    @Column(name = "dg_VSM")
    private BigDecimal dgVSM;
    @Column(name = "dg_porcCotizacionDeseado")
    private BigDecimal dgporcCotizacionDeseado;
    
    
    @Column(name = "cve_cotizacion")
    private String cveCotizacion;

//    @OneToMany(mappedBy = "idCotizacion",fetch = FetchType.EAGER)
//    private List<ColaboradoresTemp> colaboradoresTempList;
    
    @JoinColumn(name = "id_tipo_esquema", referencedColumnName = "id_cat_general")
    @ManyToOne(cascade = CascadeType.DETACH)
    private CatGeneral idTipoEsquema;
    
    @Column(name = "tiene_costos_adicionales")
    private Long tieneCostosAdicionales;
    
    public Cotizacion() {
    }
    
    
    
    

    public Cotizacion(CotizacionDto dto) {
		super();
		this.idCotizacion = dto.getIdCotizacion();
		this.porcentajeNomFis = dto.getPorcentajeNomFis();
		this.porcentajePpp = dto.getPorcentajePpp();
		this.aguinaldo = dto.getAguinaldo();
		this.diasVacaciones = dto.getDiasVacaciones();
		this.primaVacacional = dto.getPrimaVacacional();
		this.tieneProvedor = dto.getTieneProvedor();
		this.feeActual = dto.getFeeActual();
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificacion = null;
		this.usuarioAlta = dto.getUsuarioAlta();
		this.usuarioModificacion = dto.getUsuarioModificacion();
		this.indEstatus = dto.getIndEstatus();
		this.idVacaciones = dto.getIdVacaciones()!= null ? new CatGeneral(dto.getIdVacaciones()):null;
		this.idCosteoAsimilable = dto.getIdCosteoAsimilable()!=null ? new CatGeneral(dto.getIdCosteoAsimilable()):null ;
		this.idDias = dto.getIdDias()!=null ? new CatGeneral(dto.getIdDias()):null;
		this.idImss = dto.getIdImss()!=null ? new CatGeneral(dto.getIdImss()):null;
		this.idPeriodicidad = dto.getIdPeriodicidad() !=null ? new CatTipoPago(dto.getIdPeriodicidad().getIdTipoPago()):null;
		this.idPrestaciones = dto.getIdPrestaciones() !=null ? new CatGeneral(dto.getIdPrestaciones()) :null;
		this.idTipo = dto.getIdTipo()!=null && dto.getIdTipo().getIdCatGeneral() !=null ? new CatGeneral(dto.getIdTipo()) :null;
		this.idTipoCotizacion = dto.getIdTipoCotizacion() !=null ? new CatGeneral(dto.getIdTipoCotizacion()) :null;
		this.idTipoSolCotizacion = dto.getIdTipoSolCotizacion() !=null ? new CatGeneral(dto.getIdTipoSolCotizacion()) :null;
		this.idZona = dto.getIdZona() !=null ? new CatGeneral(dto.getIdZona()) :null;
		this.idCliente = dto.getIdCliente() !=null ? new ClienteTemp(dto.getIdCliente().getIdClienteTemp()) :null;
		this.idEstatus = dto.getIdEstatus() !=null ? new CatGeneral(dto.getIdEstatus()) :null;
		this.observacionAutorizador = dto.getObservacionAutorizador();
		this.comisionImss = dto.getComisionImss();
		this.comisionPpp = dto.getComisionPpp();
		this.dgMontoBrutoMensual = dto.getDgMontoBrutoMensual();
		this.dgVSM = dto.getDgVSM();
		this.dgporcCotizacionDeseado = dto.getDgporcCotizacionDeseado();
		this.cveCotizacion = dto.getCveCotizacion();
		this.idTipoEsquema = dto.getIdTipoEsquema()!= null ? new CatGeneral(dto.getIdTipoEsquema()):null;
		this.tieneCostosAdicionales = dto.getTieneCostosAdicionales();
	}





	public Cotizacion(Long idCotizacion) {
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

//    @XmlTransient
//    public List<ColaboradoresTemp> getColaboradoresTempList() {
//        return colaboradoresTempList;
//    }
//
//    public void setColaboradoresTempList(List<ColaboradoresTemp> colaboradoresTempList) {
//        this.colaboradoresTempList = colaboradoresTempList;
//    }

    public CatGeneral getIdVacaciones() {
        return idVacaciones;
    }

    public void setIdVacaciones(CatGeneral idVacaciones) {
        this.idVacaciones = idVacaciones;
    }

    public CatGeneral getIdCosteoAsimilable() {
        return idCosteoAsimilable;
    }

    public void setIdCosteoAsimilable(CatGeneral idCosteoAsimilable) {
        this.idCosteoAsimilable = idCosteoAsimilable;
    }

    public CatGeneral getIdDias() {
        return idDias;
    }

    public void setIdDias(CatGeneral idDias) {
        this.idDias = idDias;
    }

    public CatGeneral getIdImss() {
        return idImss;
    }

    public void setIdImss(CatGeneral idImss) {
        this.idImss = idImss;
    }

    public CatTipoPago getIdPeriodicidad() {
        return idPeriodicidad;
    }

    public void setIdPeriodicidad(CatTipoPago idPeriodicidad) {
        this.idPeriodicidad = idPeriodicidad;
    }

    public CatGeneral getIdPrestaciones() {
        return idPrestaciones;
    }

    public void setIdPrestaciones(CatGeneral idPrestaciones) {
        this.idPrestaciones = idPrestaciones;
    }

    public CatGeneral getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(CatGeneral idTipo) {
        this.idTipo = idTipo;
    }

    public CatGeneral getIdTipoCotizacion() {
        return idTipoCotizacion;
    }

    public void setIdTipoCotizacion(CatGeneral idTipoCotizacion) {
        this.idTipoCotizacion = idTipoCotizacion;
    }

    public CatGeneral getIdZona() {
        return idZona;
    }

    public void setIdZona(CatGeneral idZona) {
        this.idZona = idZona;
    }

    public ClienteTemp getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(ClienteTemp idCliente) {
		this.idCliente = idCliente;
	}

	public CatGeneral getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(CatGeneral idEstatus) {
		this.idEstatus = idEstatus;
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

	public CatGeneral getIdTipoSolCotizacion() {
		return idTipoSolCotizacion;
	}

	public void setIdTipoSolCotizacion(CatGeneral idTipoSolCotizacion) {
		this.idTipoSolCotizacion = idTipoSolCotizacion;
	}

	public CatGeneral getIdTipoEsquema() {
		return idTipoEsquema;
	}

	public void setIdTipoEsquema(CatGeneral idTipoEsquema) {
		this.idTipoEsquema = idTipoEsquema;
	}
	

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	
	
	
	
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public String getCveCotizacion() {
		return cveCotizacion;
	}

	public void setCveCotizacion(String cveCotizacion) {
		this.cveCotizacion = cveCotizacion;

	}
	
	public Long getTieneCostosAdicionales() {
		return tieneCostosAdicionales;
	}

	public void setTieneCostosAdicionales(Long tieneCostosAdicionales) {
		this.tieneCostosAdicionales = tieneCostosAdicionales;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCotizacion != null ? idCotizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cotizacion)) {
            return false;
        }
        Cotizacion other = (Cotizacion) object;
        if ((this.idCotizacion == null && other.idCotizacion != null) || (this.idCotizacion != null && !this.idCotizacion.equals(other.idCotizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.Cotizacion[ idCotizacion=" + idCotizacion + " ]";
    }

}
