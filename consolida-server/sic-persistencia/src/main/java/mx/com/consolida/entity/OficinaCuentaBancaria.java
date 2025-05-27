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
@Table(name = "oficina_cuenta_bancaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OficinaCuentaBancaria.findAll", query = "SELECT o FROM OficinaCuentaBancaria o")
})
public class OficinaCuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_oficina_cuenta_bancaria")
    private Long idOficinaCuentaBancaria;
    
    @Column(name = "numero_cuenta")
    private String numeroCuenta;
    
    @Column(name = "clabe_interbancaria")
    private String clabeInterbancaria;
    
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @Column(name = "ind_estatus")
    private Long indEstatus;
    
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;
    
    @JoinColumn(name = "id_banco", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idBanco;
    
    @JoinColumn(name = "id_oficina", referencedColumnName = "id_oficina")
    @ManyToOne(optional = false)
    private Oficina idOficina;
    
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idTipoCuenta;
    
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;

    public OficinaCuentaBancaria() {
    }

    public OficinaCuentaBancaria(Long idOficinaCuentaBancaria) {
        this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
    }

    public OficinaCuentaBancaria(Long idOficinaCuentaBancaria, String numeroCuenta, String clabeInterbancaria, Date fechaAlta, Long indEstatus) {
        this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
        this.numeroCuenta = numeroCuenta;
        this.clabeInterbancaria = clabeInterbancaria;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdOficinaCuentaBancaria() {
        return idOficinaCuentaBancaria;
    }

    public void setIdOficinaCuentaBancaria(Long idOficinaCuentaBancaria) {
        this.idOficinaCuentaBancaria = idOficinaCuentaBancaria;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getClabeInterbancaria() {
        return clabeInterbancaria;
    }

    public void setClabeInterbancaria(String clabeInterbancaria) {
        this.clabeInterbancaria = clabeInterbancaria;
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

    public Long getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(Long indEstatus) {
        this.indEstatus = indEstatus;
    }

    public Usuario getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public CatGeneral getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(CatGeneral idBanco) {
        this.idBanco = idBanco;
    }

    public Oficina getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(Oficina idOficina) {
        this.idOficina = idOficina;
    }

    public CatGeneral getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(CatGeneral idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
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
        if (!(object instanceof OficinaCuentaBancaria)) {
            return false;
        }
        OficinaCuentaBancaria other = (OficinaCuentaBancaria) object;
        if ((this.idOficinaCuentaBancaria == null && other.idOficinaCuentaBancaria != null) || (this.idOficinaCuentaBancaria != null && !this.idOficinaCuentaBancaria.equals(other.idOficinaCuentaBancaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.OficinaCuentaBancaria[ idOficinaCuentaBancaria=" + idOficinaCuentaBancaria + " ]";
    }
    
}
