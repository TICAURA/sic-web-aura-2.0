package mx.com.consolida.entity.crm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "nomina_periodicidad_fechas")
public class NominaPeriodicidadFechas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nomina_periodicidad_fechas")
    private Long idNominaPeriodicidadFechas;

    @Basic(optional = false)
    @Column(name = "id_nomina_periodicidad")
    private Long idNominaPeriodicidad;

    @Basic(optional = false)
    @Column(name = "fecha_inicio_periodo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPeriodo;
    @Basic(optional = false)
    @Column(name = "fecha_fin_periodo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinPeriodo;
    @Basic(optional = false)
    @Column(name = "fecha_tentativa_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTentativaPago;
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private Long usuarioAlta;
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @Column(name = "ind_estatus")
    private String indEstatus;

    public NominaPeriodicidadFechas() {
    }

    public NominaPeriodicidadFechas(Long idNominaPeriodicidadFechas) {
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

	public void setFechaTentativaPago(Date fechaTentativaPago) {
		this.fechaTentativaPago = fechaTentativaPago;
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
        if (!(object instanceof NominaPeriodicidadFechas)) {
            return false;
        }
        NominaPeriodicidadFechas other = (NominaPeriodicidadFechas) object;
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
