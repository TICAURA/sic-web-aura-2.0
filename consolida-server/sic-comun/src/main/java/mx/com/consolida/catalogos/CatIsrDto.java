
package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */
public class CatIsrDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idCatIsr;
    private BigDecimal limiteInferior;
    private BigDecimal limiteSuperior;
    private BigDecimal cuotaFija;
    private BigDecimal porcentajecatTipoPago;
    private Long indEstatus;
    private Long idUsuarioAlta;
    private Date fechaAlta;

    public CatIsrDto() {
    }

    public CatIsrDto(Long idCatIsr) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatIsr != null ? idCatIsr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatIsrDto)) {
            return false;
        }
        CatIsrDto other = (CatIsrDto) object;
        if ((this.idCatIsr == null && other.idCatIsr != null) || (this.idCatIsr != null && !this.idCatIsr.equals(other.idCatIsr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatIsrDto[ idCatIsr=" + idCatIsr + " ]";
    }
    
}
