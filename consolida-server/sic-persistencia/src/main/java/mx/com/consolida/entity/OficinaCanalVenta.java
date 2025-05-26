package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "oficina_canal_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OficinaCanalVenta.findAll", query = "SELECT o FROM OficinaCanalVenta o")
})
public class OficinaCanalVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oficina_canal_venta")
    private Long idOficinaCanalVenta;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "ind_estatus")
    private String indEstatus;

    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;

    @JoinColumn(name = "id_canal_venta", referencedColumnName = "id_canal_venta")
    @ManyToOne(optional = false)
    private CanalVenta idCanalVenta;

    @JoinColumn(name = "id_oficina", referencedColumnName = "id_oficina")
    @ManyToOne(optional = false)
    private Oficina idOficina;

    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;

    public OficinaCanalVenta() {
    }

    public OficinaCanalVenta(Long idOficinaCanalVenta) {
        this.idOficinaCanalVenta = idOficinaCanalVenta;
    }

    public OficinaCanalVenta(Long idOficinaCanalVenta, Date fechaAlta, String indEstatus) {
        this.idOficinaCanalVenta = idOficinaCanalVenta;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdOficinaCanalVenta() {
        return idOficinaCanalVenta;
    }

    public void setIdOficinaCanalVenta(Long idOficinaCanalVenta) {
        this.idOficinaCanalVenta = idOficinaCanalVenta;
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

    public Usuario getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public CanalVenta getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(CanalVenta idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public Oficina getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(Oficina idOficina) {
        this.idOficina = idOficina;
    }

    public Usuario getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Usuario usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OficinaCanalVenta)) {
            return false;
        }
        OficinaCanalVenta other = (OficinaCanalVenta) object;
        if ((this.idOficinaCanalVenta == null && other.idOficinaCanalVenta != null) || (this.idOficinaCanalVenta != null && !this.idOficinaCanalVenta.equals(other.idOficinaCanalVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.OficinaCanalVenta[ idOficinaCanalVenta=" + idOficinaCanalVenta + " ]";
    }

}
