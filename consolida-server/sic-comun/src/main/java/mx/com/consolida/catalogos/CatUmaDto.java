package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Abel
 */

public class CatUmaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer idCatUma;
    private BigDecimal valorUma;
    private Integer indEstatus;
    private Integer idUsuarioAlta;
    private Date fechaAlta;

    public CatUmaDto() {
    }

    public CatUmaDto(Integer idCatUma) {
        this.idCatUma = idCatUma;
    }

    public Integer getIdCatUma() {
        return idCatUma;
    }

    public void setIdCatUma(Integer idCatUma) {
        this.idCatUma = idCatUma;
    }

    public BigDecimal getValorUma() {
        return valorUma;
    }

    public void setValorUma(BigDecimal valorUma) {
        this.valorUma = valorUma;
    }

    public Integer getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Integer indEstatus) {
        this.indEstatus = indEstatus;
    }

    public Integer getIdUsuarioAlta() {
        return idUsuarioAlta;
    }

    public void setIdUsuarioAlta(Integer idUsuarioAlta) {
        this.idUsuarioAlta = idUsuarioAlta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatUma != null ? idCatUma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatUmaDto)) {
            return false;
        }
        CatUmaDto other = (CatUmaDto) object;
        if ((this.idCatUma == null && other.idCatUma != null) || (this.idCatUma != null && !this.idCatUma.equals(other.idCatUma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatUmaDto[ idCatUma=" + idCatUma + " ]";
    }
    
}
