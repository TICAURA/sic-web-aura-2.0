package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */
public class CatSubsidioDto implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long idCatSubsidio;
    private BigDecimal paraIngresosDe;
    private BigDecimal hastaIngresosDe;
    private BigDecimal subsidio;
    private Long indEstatus;
    private Long idUsuarioAlta;
    private Date fechaAlta;

    public CatSubsidioDto() {
    }

    public CatSubsidioDto(Long idCatSubsidio) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatSubsidio != null ? idCatSubsidio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatSubsidioDto)) {
            return false;
        }
        CatSubsidioDto other = (CatSubsidioDto) object;
        if ((this.idCatSubsidio == null && other.idCatSubsidio != null) || (this.idCatSubsidio != null && !this.idCatSubsidio.equals(other.idCatSubsidio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatSubsidioDto[ idCatSubsidio=" + idCatSubsidio + " ]";
    }
    
}
