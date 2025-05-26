package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Abel
 */
public class NominaPeriodicidadDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idNominaPeriodicidad;
    private Long idNomina;
    private Long idCatPeriodicidad;
    private Date fechaInicio;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Date fechaAlta;
    private Date fechaModificacion;

    public NominaPeriodicidadDto() {
    }

    public NominaPeriodicidadDto(Long idNominaPeriodicidad) {
        this.idNominaPeriodicidad = idNominaPeriodicidad;
    }

    public NominaPeriodicidadDto(Long idNominaPeriodicidad, Long idNomina, Long idCatPeriodicidad, Date fechaInicio, Long usuarioAlta, Date fechaAlta) {
        this.idNominaPeriodicidad = idNominaPeriodicidad;
        this.idNomina = idNomina;
        this.idCatPeriodicidad = idCatPeriodicidad;
        this.fechaInicio = fechaInicio;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
    }

    public Long getIdNominaPeriodicidad() {
        return idNominaPeriodicidad;
    }

    public void setIdNominaPeriodicidad(Long idNominaPeriodicidad) {
        this.idNominaPeriodicidad = idNominaPeriodicidad;
    }

    public Long getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Long idNomina) {
        this.idNomina = idNomina;
    }

    public Long getIdCatPeriodicidad() {
        return idCatPeriodicidad;
    }

    public void setIdCatPeriodicidad(Long idCatPeriodicidad) {
        this.idCatPeriodicidad = idCatPeriodicidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNominaPeriodicidad != null ? idNominaPeriodicidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaPeriodicidadDto)) {
            return false;
        }
        NominaPeriodicidadDto other = (NominaPeriodicidadDto) object;
        if ((this.idNominaPeriodicidad == null && other.idNominaPeriodicidad != null) || (this.idNominaPeriodicidad != null && !this.idNominaPeriodicidad.equals(other.idNominaPeriodicidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.NominaPeriodicidad[ idNominaPeriodicidad=" + idNominaPeriodicidad + " ]";
    }
    
}
