package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Abel
 */
public class NominaPeriodicidadFechasDto implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long idNominaPeriodicidadFechas;
    private Long idNominaPeriodicidad;
    private Date fechaInicioPeriodo;
    private Date fechaFinPeriodo;
    private Date fechaTentativaPago;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Date fechaAlta;
    private Date fechaModificacion;
    private String indEstatus;

    public NominaPeriodicidadFechasDto() {
    }

    public NominaPeriodicidadFechasDto(Long idNominaPeriodicidadFechas) {
        this.idNominaPeriodicidadFechas = idNominaPeriodicidadFechas;
    }

    
    public Long getIdNominaPeriodicidadFechas() {
		return idNominaPeriodicidadFechas;
	}

	public void setIdNominaPeriodicidadFechas(Long idNominaPeriodicidadFechas) {
		this.idNominaPeriodicidadFechas = idNominaPeriodicidadFechas;
	}

	public Long getIdNominaPeriodicidad() {
		return idNominaPeriodicidad;
	}

	public void setIdNominaPeriodicidad(Long idNominaPeriodicidad) {
		this.idNominaPeriodicidad = idNominaPeriodicidad;
	}

	public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}

	public Date getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}

	public void setFechaFinPeriodo(Date fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	public Date getFechaTentativaPago() {
		return fechaTentativaPago;
	}

	public void setFechaTentativaPago(Date fechaTentativa_pago) {
		this.fechaTentativaPago = fechaTentativa_pago;
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

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominaPeriodicidadFechas != null ? idNominaPeriodicidadFechas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaPeriodicidadFechasDto)) {
            return false;
        }
        NominaPeriodicidadFechasDto other = (NominaPeriodicidadFechasDto) object;
        if ((this.idNominaPeriodicidadFechas == null && other.idNominaPeriodicidadFechas != null) || (this.idNominaPeriodicidadFechas != null && !this.idNominaPeriodicidadFechas.equals(other.idNominaPeriodicidadFechas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.NominaPeriodicidadFechas[ idNominaPeriodicidadFechas=" + idNominaPeriodicidadFechas + " ]";
    }
    
}
