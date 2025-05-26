package mx.com.consolida.dto; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */
public class ColaboradoresTempDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idColaboradorTemp;
    private BigDecimal salarioDiario;
    private BigDecimal salarioDiarioIntegral;
    private BigDecimal sueldo;
    private BigDecimal gravados;
    private BigDecimal exentos;
    private BigDecimal subsidio;
    private BigDecimal isr;
    private BigDecimal coImss;
    private BigDecimal netoNomina;
    private Date fechaAntiguedad;
    private Date fechaAlta;
    private Date fechaModificacion;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Long indEstatus;
    private CotizacionDto idCotizacion;
    
    private BigDecimal asimilados;
    private BigDecimal otros;
    private String primaDeRiesgo;

    public ColaboradoresTempDto() {
    }

    public ColaboradoresTempDto(Long idColaboradorTemp) {
        this.idColaboradorTemp = idColaboradorTemp;
    }

    public ColaboradoresTempDto(Long idColaboradorTemp, BigDecimal salarioDiario, BigDecimal gravados, 
    		BigDecimal exentos, Date fechaAntiguedad, BigDecimal netoNomina, BigDecimal asimilados, BigDecimal otros,String primaDeRiesgo) {
        this.idColaboradorTemp = idColaboradorTemp;
        this.salarioDiario = salarioDiario;
        this.gravados = gravados;
        this.exentos = exentos;
        this.fechaAntiguedad = fechaAntiguedad;
        this.netoNomina = netoNomina;
        this.asimilados = asimilados;
        this.otros = otros;
        this.primaDeRiesgo = primaDeRiesgo;
    }

    public Long getIdColaboradorTemp() {
        return idColaboradorTemp;
    }

    public void setIdColaboradorTemp(Long idColaboradorTemp) {
        this.idColaboradorTemp = idColaboradorTemp;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public BigDecimal getSalarioDiarioIntegral() {
        return salarioDiarioIntegral;
    }

    public void setSalarioDiarioIntegral(BigDecimal salarioDiarioIntegral) {
        this.salarioDiarioIntegral = salarioDiarioIntegral;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public BigDecimal getGravados() {
        return gravados;
    }

    public void setGravados(BigDecimal gravados) {
        this.gravados = gravados;
    }

    public BigDecimal getExentos() {
        return exentos;
    }

    public void setExentos(BigDecimal exentos) {
        this.exentos = exentos;
    }

    public BigDecimal getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(BigDecimal subsidio) {
        this.subsidio = subsidio;
    }

    public BigDecimal getIsr() {
        return isr;
    }

    public void setIsr(BigDecimal isr) {
        this.isr = isr;
    }

    public BigDecimal getCoImss() {
        return coImss;
    }

    public void setCoImss(BigDecimal coImss) {
        this.coImss = coImss;
    }

    public BigDecimal getNetoNomina() {
        return netoNomina;
    }

    public void setNetoNomina(BigDecimal netoNomina) {
        this.netoNomina = netoNomina;
    }

    public Date getFechaAntiguedad() {
		return fechaAntiguedad;
	}

	public void setFechaAntiguedad(Date fechaAntiguedad) {
		this.fechaAntiguedad = fechaAntiguedad;
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

    public Long getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Long indEstatus) {
        this.indEstatus = indEstatus;
    }

    public CotizacionDto getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(CotizacionDto idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public BigDecimal getAsimilados() {
		return asimilados;
	}

	public void setAsimilados(BigDecimal asimilados) {
		this.asimilados = asimilados;
	}

	public BigDecimal getOtros() {
		return otros;
	}

	public void setOtros(BigDecimal otros) {
		this.otros = otros;
	}

	public String getPrimaDeRiesgo() {
		return primaDeRiesgo;
	}

	public void setPrimaDeRiesgo(String primaDeRiesgo) {
		this.primaDeRiesgo = primaDeRiesgo;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idColaboradorTemp != null ? idColaboradorTemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ColaboradoresTempDto)) {
            return false;
        }
        ColaboradoresTempDto other = (ColaboradoresTempDto) object;
        if ((this.idColaboradorTemp == null && other.idColaboradorTemp != null) || (this.idColaboradorTemp != null && !this.idColaboradorTemp.equals(other.idColaboradorTemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.dto.ColaboradoresTempDto[ idColaboradorTemp=" + idColaboradorTemp + " ]";
    }
    
}