package mx.com.consolida.entity.crm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.crm.dto.NominaPeriodicidadDto;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "nomina_periodicidad")
public class NominaPeriodicidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_nomina_periodicidad")
    private Long idNominaPeriodicidad;
    @Basic(optional = false)
    @Column(name = "id_nomina")
    private Long idNomina;
    @Basic(optional = false)
    @Column(name = "id_cat_periodicidad")
    private Long idCatPeriodicidad;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private Long usuarioAlta;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Basic(optional = false)
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public NominaPeriodicidad() {
    }

    public NominaPeriodicidad(Long idNominaPeriodicidad) {
        this.idNominaPeriodicidad = idNominaPeriodicidad;
    }

    public NominaPeriodicidad(Long idNominaPeriodicidad, Long idNomina, Long idCatPeriodicidad, Date fechaInicio, Long usuarioAlta, Date fechaAlta) {
        this.idNominaPeriodicidad = idNominaPeriodicidad;
        this.idNomina = idNomina;
        this.idCatPeriodicidad = idCatPeriodicidad;
        this.fechaInicio = fechaInicio;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
    }
    
    public NominaPeriodicidad(NominaPeriodicidadDto dto) {
		this.idNominaPeriodicidad = dto.getIdNominaPeriodicidad();
		this.idNomina = dto.getIdNomina();
		this.idCatPeriodicidad = dto.getIdCatPeriodicidad();
		this.fechaInicio = dto.getFechaInicio();
		this.usuarioAlta = dto.getUsuarioAlta();
		this.usuarioModificacion = dto.getUsuarioModificacion();
		this.fechaAlta = dto.getFechaAlta();
		this.fechaModificacion = dto.getFechaModificacion();
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
        if (!(object instanceof NominaPeriodicidad)) {
            return false;
        }
        NominaPeriodicidad other = (NominaPeriodicidad) object;
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
