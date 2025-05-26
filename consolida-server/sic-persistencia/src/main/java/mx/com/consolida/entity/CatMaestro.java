package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Abel
 */
@Entity
@Table(name="cat_maestro")
@NamedQueries({
    @NamedQuery(name = "CatMaestro.findAll", query = "SELECT c FROM CatMaestro c")})
public class CatMaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat_maestro")
    private Long idCatMaestro;
    @Column(name = "clave")
    private String clave;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "ind_estatus")
    private Long indEstatus;
    @Column(name = "usuario_alta")
    private Long idUsuarioAlta;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_modificacion")
    private Long idUsuarioModificacion;
    


    public CatMaestro() {
    }

    public CatMaestro(Long idCatMaestro) {
        this.idCatMaestro = idCatMaestro;
    }

    public Long getIdCatMaestro() {
        return idCatMaestro;
    }

    public void setIdCatMaestro(Long idCatMaestro) {
        this.idCatMaestro = idCatMaestro;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatMaestro != null ? idCatMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatMaestro)) {
            return false;
        }
        CatMaestro other = (CatMaestro) object;
        if ((this.idCatMaestro == null && other.idCatMaestro != null) || (this.idCatMaestro != null && !this.idCatMaestro.equals(other.idCatMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CatMaestro[ idCatMaestro=" + idCatMaestro + " ]";
    }
    
}
