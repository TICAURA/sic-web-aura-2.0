
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

/**
 *
 * @author Abel
 */
@Entity
@Table(name="cat_isr")
@NamedQueries({
    @NamedQuery(name = "CatIsr.findAll", query = "SELECT c FROM CatIsr c")})
public class CatIsr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_cat_isr")
    private Long idCatIsr;
    @Column(name = "limite_inferior")
    private BigDecimal limiteInferior;
    @Column(name = "limite_superior")
    private BigDecimal limiteSuperior;
    @Column(name = "cuota_fija")
    private BigDecimal cuotaFija;
    @Column(name = "porcentajecat_tipo_pago")
    private BigDecimal porcentajecatTipoPago;
    @Column(name = "ind_estatus")
    private Long indEstatus;
    @Column(name = "usuario_alta")
    private Long idUsuarioAlta;
    @Column(name = "usuario_modificacion")
    private Long idUsuarioModificacion;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name ="id_tipo_pago")
    private Integer idTipoPago;
    
    public CatIsr() {
    }

    public CatIsr(Long idCatIsr) {
        this.idCatIsr = idCatIsr;
    }

    public Long getIdCatIsr() {
        return idCatIsr;
    }

    public void setIdCatIsr(Long idCatIsr) {
        this.idCatIsr = idCatIsr;
    }

    public BigDecimal getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(BigDecimal limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public BigDecimal getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(BigDecimal limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public BigDecimal getCuotaFija() {
        return cuotaFija;
    }

    public void setCuotaFija(BigDecimal cuotaFija) {
        this.cuotaFija = cuotaFija;
    }

    public BigDecimal getPorcentajecatTipoPago() {
        return porcentajecatTipoPago;
    }

    public void setPorcentajecatTipoPago(BigDecimal porcentajecatTipoPago) {
        this.porcentajecatTipoPago = porcentajecatTipoPago;
    }

    public Long getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Long indEstatus) {
        this.indEstatus = indEstatus;
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

    public Integer getIdTipoPago() {
		return idTipoPago;
	}

	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	
	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}
	
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatIsr != null ? idCatIsr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatIsr)) {
            return false;
        }
        CatIsr other = (CatIsr) object;
        if ((this.idCatIsr == null && other.idCatIsr != null) || (this.idCatIsr != null && !this.idCatIsr.equals(other.idCatIsr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CatIsr[ idCatIsr=" + idCatIsr + " ]";
    }
    
}
