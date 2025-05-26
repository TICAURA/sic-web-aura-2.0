
package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Abel
 */

public class CatPrimaRiesgoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idCatPrimaRiesgo;
    private String claveRiesgo;
    private BigDecimal riesgoTrabajo;
    private Long indEstatus;
    private Long idUsuarioAlta;
    private Date fechaAlta;

    public CatPrimaRiesgoDto() {
    }

	public Long getIdCatPrimaRiesgo() {
		return idCatPrimaRiesgo;
	}

	public void setIdCatPrimaRiesgo(Long idCatPrimaRiesgo) {
		this.idCatPrimaRiesgo = idCatPrimaRiesgo;
	}

	public String getClaveRiesgo() {
		return claveRiesgo;
	}

	public void setClaveRiesgo(String claveRiesgo) {
		this.claveRiesgo = claveRiesgo;
	}

	public BigDecimal getRiesgoTrabajo() {
		return riesgoTrabajo;
	}

	public void setRiesgoTrabajo(BigDecimal riesgoTrabajo) {
		this.riesgoTrabajo = riesgoTrabajo;
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

}
