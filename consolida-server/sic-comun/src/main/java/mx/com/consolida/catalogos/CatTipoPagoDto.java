package mx.com.consolida.catalogos; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */
public class CatTipoPagoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idTipoPago;
    private String cveTipoPago;
    private String descripcionTipoPago;
    private BigDecimal diasNaturales;
    private BigDecimal diasPeriodo;
    private BigDecimal porAnio;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Long indEstatus; 
    
    public CatTipoPagoDto() {
    }

    public CatTipoPagoDto(Long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }
    public CatTipoPagoDto(Long idTipoPago,String cveTipoPago) {
        this.idTipoPago = idTipoPago;
        this.cveTipoPago = cveTipoPago;
    }
    
    public CatTipoPagoDto(String descripcionTipoPago) {
        this.descripcionTipoPago = descripcionTipoPago;
    }

    public CatTipoPagoDto(Long idTipoPago, 
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
        if (!(object instanceof CatTipoPagoDto)) {
            return false;
        }
        CatTipoPagoDto other = (CatTipoPagoDto) object;
        if ((this.idTipoPago == null && other.idTipoPago != null) || (this.idTipoPago != null && !this.idTipoPago.equals(other.idTipoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatTipoPagoDto[ idTipoPago=" + idTipoPago + " ]";
    }
    
}
