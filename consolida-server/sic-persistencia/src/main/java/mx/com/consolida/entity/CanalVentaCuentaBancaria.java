
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
@Table(name = "canal_venta_cuenta_bancaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CanalVentaCuentaBancaria.findAll", query = "SELECT c FROM CanalVentaCuentaBancaria c")
})
public class CanalVentaCuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal_venta_cuenta_bancaria")
    private Long idCanalVentaCuentaBancaria;

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
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idTipoCuenta;
    @JoinColumn(name = "id_canal_venta", referencedColumnName = "id_canal_venta")
    @ManyToOne(optional = false)
    private CanalVenta idCanalVenta;
    @JoinColumn(name = "id_banco", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idBanco;
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;

    public CanalVentaCuentaBancaria() {
    }

    public CanalVentaCuentaBancaria(Long idCanalVentaCuentaBancaria) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
    }

    public CanalVentaCuentaBancaria(Long idCanalVentaCuentaBancaria, String numeroCuenta, String clabeInterbancaria, Date fechaAlta, Long indEstatus) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
        this.numeroCuenta = numeroCuenta;
        this.clabeInterbancaria = clabeInterbancaria;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdCanalVentaCuentaBancaria() {
        return idCanalVentaCuentaBancaria;
    }

    public void setIdCanalVentaCuentaBancaria(Long idCanalVentaCuentaBancaria) {
        this.idCanalVentaCuentaBancaria = idCanalVentaCuentaBancaria;
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

    public CatGeneral getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(CatGeneral idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public CanalVenta getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(CanalVenta idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public CatGeneral getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(CatGeneral idBanco) {
        this.idBanco = idBanco;
    }

    public Usuario getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Usuario usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Usuario getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Usuario usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CanalVentaCuentaBancaria)) {
            return false;
        }
        CanalVentaCuentaBancaria other = (CanalVentaCuentaBancaria) object;
        if ((this.idCanalVentaCuentaBancaria == null && other.idCanalVentaCuentaBancaria != null) || (this.idCanalVentaCuentaBancaria != null && !this.idCanalVentaCuentaBancaria.equals(other.idCanalVentaCuentaBancaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CanalVentaCuentaBancaria[ idCanalVentaCuentaBancaria=" + idCanalVentaCuentaBancaria + " ]";
    }

}
