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
@Table(name="cat_subsidio")
@NamedQueries({
    @NamedQuery(name = "CatSubsidio.findAll", query = "SELECT c FROM CatSubsidio c")})
public class CatSubsidio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat_subsidio")
    private Long idCatSubsidio;
    @Column(name = "para_ingresos_de")
    private BigDecimal paraIngresosDe;
    @Column(name = "hasta_ingresos_de")
    private BigDecimal hastaIngresosDe;
    @Column(name = "subsidio")
    private BigDecimal subsidio;
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


    public CatSubsidio() {
    }
    public CatSubsidio(BigDecimal subsidio) {
    	this.subsidio = subsidio;
    }

    public CatSubsidio(Long idCatSubsidio) {
        this.idCatSubsidio = idCatSubsidio;
    }

    public Long getIdCatSubsidio() {
        return idCatSubsidio;
    }

    public void setIdCatSubsidio(Long idCatSubsidio) {
        this.idCatSubsidio = idCatSubsidio;
    }

    public BigDecimal getParaIngresosDe() {
        return paraIngresosDe;
    }

    public void setParaIngresosDe(BigDecimal paraIngresosDe) {
        this.paraIngresosDe = paraIngresosDe;
    }

    public BigDecimal getHastaIngresosDe() {
        return hastaIngresosDe;
    }

    public void setHastaIngresosDe(BigDecimal hastaIngresosDe) {
        this.hastaIngresosDe = hastaIngresosDe;
    }

    public BigDecimal getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(BigDecimal subsidio) {
        this.subsidio = subsidio;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatSubsidio != null ? idCatSubsidio.hashCode() : 0);
        return hash;
    }
//
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatSubsidio)) {
            return false;
        }
        CatSubsidio other = (CatSubsidio) object;
        if ((this.idCatSubsidio == null && other.idCatSubsidio != null) || (this.idCatSubsidio != null && !this.idCatSubsidio.equals(other.idCatSubsidio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CatSubsidio[ idCatSubsidio=" + idCatSubsidio + " ]";
    }

}
