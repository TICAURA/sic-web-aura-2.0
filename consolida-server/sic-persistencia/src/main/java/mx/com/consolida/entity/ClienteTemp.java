/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 
 */
@Entity
@Table(name = "cliente_temp")
@NamedQueries({
    @NamedQuery(name = "ClienteTemp.findAll", query = "SELECT c FROM ClienteTemp c")
    })
public class ClienteTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cliente_temp")
    private Long idClienteTemp;
    
    @Column(name = "rfc")
    private String rfc;
    
    @Column(name = "razon_social")
    private String razonSocial;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
        
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    
    @Column(name = "id_giro_comercial")
    private Long idGiroComercial;
    
    @Column(name = "id_cat_sub_giro_comercial")
    private Long idSubGiroComercial;
    
    @Basic(optional = false)
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private Long usuarioAlta;
    
    @Column(name = "usuario_modificacion")
    private Long usuarioModificacion;
    
    @Column(name = "ind_estatus")
    private Long indEstatus;
    
    //@JoinColumn(name = "id_canal_venta", referencedColumnName = "id_canal_venta")
    //@OneToOne
    @Column(name = "id_canal_venta") 
    private Long idCanalVenta;
    
    @JoinColumn(name = "id_tipo_persona", referencedColumnName = "id_cat_general")
    @OneToOne
    private CatGeneral idTipoPersonaTemp;
    
    @JoinColumn(name="id_medio_contacto", referencedColumnName= "id_medio_contacto_temp")
    @OneToOne(targetEntity = MedioContactoTemp.class, cascade=CascadeType.ALL)
    private MedioContactoTemp idMedioContactoTemp;
    
    @JoinColumn(name="id_persona_contacto_temp", referencedColumnName= "id_persona_contacto_temp")
    @OneToOne(targetEntity = PersonaContactoTemp.class, cascade=CascadeType.ALL)
    private PersonaContactoTemp idPersonaContactoTemp;
    
 // bi-directional many-to-one association to ClienteTempEstatus
    @OneToMany(mappedBy = "clienteTemp")
    @OrderBy("fechaAlta DESC")
    private List<ClienteTempEstatus> clienteTempEstatus;
    
 // bi-directional many-to-one association to ClienteTempBitacora
    @OneToMany(mappedBy = "clienteTemp")
    @OrderBy("fechaAlta DESC")
    private List<ClienteTempBitacora> clienteTempBitacora;
    
 // bi-directional one-to-one association to CatGrupo
    @JoinColumn(name="id_cat_grupo", referencedColumnName= "id_cat_grupo")
    @ManyToOne(targetEntity = CatGrupo.class)
    private CatGrupo catGrupo;
    
    // bi-directional one-to-one association to ClienteTempBitacoraSolicitudes
    @OneToMany(mappedBy = "clienteTemp")
    @OrderBy("fechaAlta DESC")
    private List<ClienteTempBitacoraSolicitudes> clienteTempBitacoraSolicitudes;
    

    public ClienteTemp() {
    }

    public ClienteTemp(Long idClienteTemp) {
        this.idClienteTemp = idClienteTemp;
    }

    public ClienteTemp(Long idClienteTemp, String rfc, String razonSocial, Date fechaAlta, Long usuarioAlta) {
        this.idClienteTemp = idClienteTemp;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }
    
    public ClienteTemp(ClienteTemp clt) {
        this.idClienteTemp = clt.getIdClienteTemp();
        this.rfc = clt.getRfc();
        this.razonSocial = clt.getRazonSocial();
        this.fechaAlta = clt.getFechaAlta();
        this.usuarioAlta = clt.getUsuarioAlta();
    }

    public Long getIdClienteTemp() {
        return idClienteTemp;
    }
    
    

    public List<ClienteTempEstatus> getClienteTempEstatus() {
		return clienteTempEstatus;
	}

	public void setClienteTempEstatus(List<ClienteTempEstatus> clienteTempEstatus) {
		this.clienteTempEstatus = clienteTempEstatus;
	}

	
	public List<ClienteTempBitacora> getClienteTempBitacora() {
		return clienteTempBitacora;
	}

	public void setClienteTempBitacora(List<ClienteTempBitacora> clienteTempBitacora) {
		this.clienteTempBitacora = clienteTempBitacora;
	}

	public void setIdClienteTemp(Long idClienteTemp) {
        this.idClienteTemp = idClienteTemp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Long usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public CatGeneral getIdTipoPersonaTemp() {
        return idTipoPersonaTemp;
    }

    public void setIdTipoPersonaTemp(CatGeneral idTipoPersonaTemp) {
        this.idTipoPersonaTemp = idTipoPersonaTemp;
    }
    
	public MedioContactoTemp getIdMedioContactoTemp() {
		return idMedioContactoTemp;
	}

	public void setIdMedioContactoTemp(MedioContactoTemp idMedioContactoTemp) {
		this.idMedioContactoTemp = idMedioContactoTemp;
	}

	public PersonaContactoTemp getIdPersonaContactoTemp() {
		return idPersonaContactoTemp;
	}

	public void setIdPersonaContactoTemp(PersonaContactoTemp idPersonaContactoTemp) {
		this.idPersonaContactoTemp = idPersonaContactoTemp;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public CatGrupo getCatGrupo() {
		return catGrupo;
	}

	public void setCatGrupo(CatGrupo catGrupo) {
		this.catGrupo = catGrupo;
	}
	
	public List<ClienteTempBitacoraSolicitudes> getClienteTempBitacoraSolicitudes() {
		return clienteTempBitacoraSolicitudes;
	}

	public void setClienteTempBitacoraSolicitudes(List<ClienteTempBitacoraSolicitudes> clienteTempBitacoraSolicitudes) {
		this.clienteTempBitacoraSolicitudes = clienteTempBitacoraSolicitudes;
	}
	

	public Long getIdGiroComercial() {
		return idGiroComercial;
	}

	public void setIdGiroComercial(Long idGiroComercial) {
		this.idGiroComercial = idGiroComercial;
	}
	
	

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getIdCanalVenta() {
		return idCanalVenta;
	}

	public void setIdCanalVenta(Long idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}
	
	public Long getIdSubGiroComercial() {
		return idSubGiroComercial;
	}

	public void setIdSubGiroComercial(Long idSubGiroComercial) {
		this.idSubGiroComercial = idSubGiroComercial;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idClienteTemp != null ? idClienteTemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteTemp)) {
            return false;
        }
        ClienteTemp other = (ClienteTemp) object;
        if ((this.idClienteTemp == null && other.idClienteTemp != null) || (this.idClienteTemp != null && !this.idClienteTemp.equals(other.idClienteTemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.entity.ClienteTemp[ idClienteTemp=" + idClienteTemp + " ]";
    }
    
}
