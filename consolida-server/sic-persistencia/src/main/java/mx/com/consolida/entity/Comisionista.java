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

import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 *
 * @author Aurora
 */
@Entity
@Table(name = "comisionistas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comisionista.findAll", query = "SELECT c FROM Comisionista c")
})
public class Comisionista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comisionista")
    private Long idcomisionista;
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "rfc")
    private String rfc;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_modificaicon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificaicon;
    
    @Column(name = "ind_estatus")
    private String indEstatus;
    
    @JoinColumn(name = "id_tipo_persona", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idTipoPersona;
    @JoinColumn(name = "id_tipo_canal_venta", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idTipoCanalVenta;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral idPersona;
    @JoinColumn(name = "id_domicilio", referencedColumnName = "id_domicilio")
    @ManyToOne(optional = false)
    private Domicilio idDomicilio;
    @JoinColumn(name = "id_concepto_cfdi", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral conceptocfdi;
    @JoinColumn(name = "id_tipo_pago", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral tipoPago;
    @JoinColumn(name = "id_subtipo_pago", referencedColumnName = "id_cat_general")
    @ManyToOne(optional = false)
    private CatGeneral subtipoPago;
    
    @JoinColumn(name = "usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioAlta;
    @JoinColumn(name = "usuario_modificacion", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuarioModificacion;

    public Comisionista() {
    }

	public Long getIdcomisionista() {
		return idcomisionista;
	}

	public void setIdcomisionista(Long idcomisionista) {
		this.idcomisionista = idcomisionista;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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

	public CatGeneral getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(CatGeneral idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public CatGeneral getIdTipoCanalVenta() {
		return idTipoCanalVenta;
	}

	public void setIdTipoCanalVenta(CatGeneral idTipoCanalVenta) {
		this.idTipoCanalVenta = idTipoCanalVenta;
	}

	public CatGeneral getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(CatGeneral idPersona) {
		this.idPersona = idPersona;
	}



	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Domicilio getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Domicilio idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public CatGeneral getConceptocfdi() {
		return conceptocfdi;
	}

	public void setConceptocfdi(CatGeneral conceptocfdi) {
		this.conceptocfdi = conceptocfdi;
	}

	public CatGeneral getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(CatGeneral tipoPago) {
		this.tipoPago = tipoPago;
	}

	public CatGeneral getSubtipoPago() {
		return subtipoPago;
	}

	public void setSubtipoPago(CatGeneral subtipoPago) {
		this.subtipoPago = subtipoPago;
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

  
    
    
}
