package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;

/**
 *
 * @author Abel
 */
@Entity
@Table(name="cat_tipo_pago")
@NamedQueries({
    @NamedQuery(name = "CatTipoPago.findAll", query = "SELECT c FROM CatTipoPago c")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "obtenerTipoPago", classes = {
        @ConstructorResult(targetClass = CatTipoPagoDto.class, columns = {
            @ColumnResult(name = "idTipoPago", type = Long.class)
            , @ColumnResult(name = "cveTipoPago", type = String.class)
            , @ColumnResult(name = "descripcionTipoPago", type = String.class)
            , @ColumnResult(name = "diasNaturales", type = BigDecimal.class)
            , @ColumnResult(name = "diasPeriodo", type = BigDecimal.class)
            , @ColumnResult(name = "porAnio", type = BigDecimal.class)
            , @ColumnResult(name = "fechaAlta", type = Date.class)
        })
    })
})
public class CatTipoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_pago")
    private Long idTipoPago;
    @Basic(optional = false)
    @Column(name = "cve_tipo_pago")
    private String cveTipoPago;
    @Basic(optional = false)
    @Column(name = "descripcion_tipo_pago")
    private String descripcionTipoPago;
    @Column(name = "dias_naturales")
    private BigDecimal diasNaturales;
    @Column(name = "dias_periodo")
    private BigDecimal diasPeriodo;
    @Column(name = "por_anio")
    private BigDecimal porAnio;
    @Basic(optional = false)
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private Long usuarioAlta;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "ind_Estatus")
    private Long indEstatus;

    
    public CatTipoPago() {
    }

    public CatTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }
    public CatTipoPago(Long idTipoPago,String cveTipoPago) {
        this.idTipoPago = idTipoPago;
        this.cveTipoPago = cveTipoPago;
    }
    
    public CatTipoPago(String descripcionTipoPago) {
        this.descripcionTipoPago = descripcionTipoPago;
    }

    public CatTipoPago(Long idTipoPago, 
    		String cveTipoPago, 
    		String descripcionTipoPago, 
    		BigDecimal diasNaturales,
    		BigDecimal diasPeriodo,
    		BigDecimal porAnio,
    		Date fechaAlta
    		) {
        this.idTipoPago = idTipoPago;
        this.cveTipoPago = cveTipoPago;
        this.descripcionTipoPago = descripcionTipoPago;
        this.diasNaturales = diasNaturales;
        this.diasPeriodo = diasPeriodo;
        this.porAnio = porAnio;
        this.fechaAlta = fechaAlta;
    }

    public Long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public String getCveTipoPago() {
        return cveTipoPago;
    }

    public void setCveTipoPago(String cveTipoPago) {
        this.cveTipoPago = cveTipoPago;
    }

    public String getDescripcionTipoPago() {
        return descripcionTipoPago;
    }

    public void setDescripcionTipoPago(String descripcionTipoPago) {
        this.descripcionTipoPago = descripcionTipoPago;
    }

    public BigDecimal getDiasNaturales() {
        return diasNaturales;
    }

    public void setDiasNaturales(BigDecimal diasNaturales) {
        this.diasNaturales = diasNaturales;
    }

    public BigDecimal getDiasPeriodo() {
        return diasPeriodo;
    }

    public void setDiasPeriodo(BigDecimal diasPeriodo) {
        this.diasPeriodo = diasPeriodo;
    }

    public BigDecimal getPorAnio() {
        return porAnio;
    }

    public void setPorAnio(BigDecimal porAnio) {
        this.porAnio = porAnio;
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

    public Long getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Long usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }
    
    public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPago != null ? idTipoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatTipoPago)) {
            return false;
        }
        CatTipoPago other = (CatTipoPago) object;
        if ((this.idTipoPago == null && other.idTipoPago != null) || (this.idTipoPago != null && !this.idTipoPago.equals(other.idTipoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CatTipoPago[ idTipoPago=" + idTipoPago + " ]";
    }
    
}
