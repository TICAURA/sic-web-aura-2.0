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

import mx.com.consolida.entity.crm.CatEntidadFederativa;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "canal_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CanalVenta.findAll", query = "SELECT c FROM CanalVenta c")
})
public class CanalVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal_venta")
    private Long idCanalVenta;
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "genera_cotizacion")
    private String generaCotizacion;
    @Column(name = "empresa_oficina")
    private String empresaOficina;
    @Column(name = "pago_oficina")
    private String pagoOficina;
    @Column(name = "ciudad_sede")
    private String ciudadSede;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificaicon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificaicon;

    @Column(name = "ind_estatus")
    private String indEstatus;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanalVenta")
    private List<OficinaCanalVenta> oficinaCanalVentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanalVenta")
    private List<CanalVentaCuentaBancaria> canalVentaCuentaBancariaList;
    @OneToMany(mappedBy = "idCanalVenta")
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "idCanalVenta")
    private List<ClienteTemp> clienteTempList;*/

    @JoinColumn(name = "id_entidad_federativa_sede", referencedColumnName = "id_entidad_federativa")
    @ManyToOne(optional = false)
    private CatEntidadFederativa idEntidadFederativaSede;
    @JoinColumn(name = "id_domicilio", referencedColumnName = "id_domicilio")
    @ManyToOne(optional = false)
    private Domicilio idDomicilio;
    @JoinColumn(name = "id_tipo_canal_venta", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idTipoCanalVenta;
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;
    @JoinColumn(name = "id_usuario_canal_venta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuarioCanalVenta;

    public CanalVenta() {
    }

    public CanalVenta(Long idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public CanalVenta(Long idCanalVenta, String razonSocial, String generaCotizacion, String empresaOficina, String pagoOficina, String ciudadSede, Date fechaAlta, String indEstatus) {
        this.idCanalVenta = idCanalVenta;
        this.razonSocial = razonSocial;
        this.generaCotizacion = generaCotizacion;
        this.empresaOficina = empresaOficina;
        this.pagoOficina = pagoOficina;
        this.ciudadSede = ciudadSede;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

    public Long getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(Long idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getGeneraCotizacion() {
        return generaCotizacion;
    }

    public void setGeneraCotizacion(String generaCotizacion) {
        this.generaCotizacion = generaCotizacion;
    }

    public String getEmpresaOficina() {
        return empresaOficina;
    }

    public void setEmpresaOficina(String empresaOficina) {
        this.empresaOficina = empresaOficina;
    }

    public String getPagoOficina() {
        return pagoOficina;
    }

    public void setPagoOficina(String pagoOficina) {
        this.pagoOficina = pagoOficina;
    }

    public String getCiudadSede() {
        return ciudadSede;
    }

    public void setCiudadSede(String ciudadSede) {
        this.ciudadSede = ciudadSede;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaModificaicon() {
        return fechaModificaicon;
    }

    public void setFechaModificaicon(Date fechaModificaicon) {
        this.fechaModificaicon = fechaModificaicon;
    }

    public String getIndEstatus() {
        return indEstatus;
    }

    public void setIndEstatus(String indEstatus) {
        this.indEstatus = indEstatus;
    }
/*
    @XmlTransient
    public List<OficinaCanalVenta> getOficinaCanalVentaList() {
        return oficinaCanalVentaList;
    }

    public void setOficinaCanalVentaList(List<OficinaCanalVenta> oficinaCanalVentaList) {
        this.oficinaCanalVentaList = oficinaCanalVentaList;
    }

    @XmlTransient
    public List<CanalVentaCuentaBancaria> getCanalVentaCuentaBancariaList() {
        return canalVentaCuentaBancariaList;
    }

    public void setCanalVentaCuentaBancariaList(List<CanalVentaCuentaBancaria> canalVentaCuentaBancariaList) {
        this.canalVentaCuentaBancariaList = canalVentaCuentaBancariaList;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<ClienteTemp> getClienteTempList() {
        return clienteTempList;
    }

    public void setClienteTempList(List<ClienteTemp> clienteTempList) {
        this.clienteTempList = clienteTempList;
    }*/

    public CatEntidadFederativa getIdEntidadFederativaSede() {
        return idEntidadFederativaSede;
    }

    public void setIdEntidadFederativaSede(CatEntidadFederativa idEntidadFederativaSede) {
        this.idEntidadFederativaSede = idEntidadFederativaSede;
    }

    public Domicilio getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Domicilio idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public CatGeneral getIdTipoCanalVenta() {
        return idTipoCanalVenta;
    }

    public void setIdTipoCanalVenta(CatGeneral idTipoCanalVenta) {
        this.idTipoCanalVenta = idTipoCanalVenta;
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

    public Usuario getIdUsuarioCanalVenta() {
        return idUsuarioCanalVenta;
    }

    public void setIdUsuarioCanalVenta(Usuario idUsuarioCanalVenta) {
        this.idUsuarioCanalVenta = idUsuarioCanalVenta;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CanalVenta)) {
            return false;
        }
        CanalVenta other = (CanalVenta) object;
        if ((this.idCanalVenta == null && other.idCanalVenta != null) || (this.idCanalVenta != null && !this.idCanalVenta.equals(other.idCanalVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.CanalVenta[ idCanalVenta=" + idCanalVenta + " ]";
    }

}
