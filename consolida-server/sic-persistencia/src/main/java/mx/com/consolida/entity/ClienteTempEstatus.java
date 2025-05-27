package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cliente_temp_estatus")
@NamedQuery(name="ClienteTempEstatus.findAll", query="SELECT c FROM ClienteTempEstatus c")
public class ClienteTempEstatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cliente_temp_estatus")
	private Long idClienteTempEstatus;
	
	@JoinColumn(name="id_cliente_temp", referencedColumnName= "id_cliente_temp")
	@ManyToOne(targetEntity = ClienteTemp.class, cascade=CascadeType.ALL)
    private ClienteTemp clienteTemp;
	
	
	@JoinColumn(name="id_estatus", referencedColumnName= "id_estatus")
	@ManyToOne(targetEntity = CatEstatus.class, cascade=CascadeType.ALL)
    private CatEstatus catEstatus;
	
    @Column(name = "motivo")
	private String motivo;
	
	
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
    
    @Basic(optional = false)
    @Column(name = "ind_estatus")
    private Long indEstatus;


	public Long getIdClienteTempEstatus() {
		return idClienteTempEstatus;
	}

	public void setIdClienteTempEstatus(Long idClienteTempEstatus) {
		this.idClienteTempEstatus = idClienteTempEstatus;
	}

	public ClienteTemp getClienteTemp() {
		return clienteTemp;
	}

	public void setClienteTemp(ClienteTemp clienteTemp) {
		this.clienteTemp = clienteTemp;
	}

	public CatEstatus getCatEstatus() {
		return catEstatus;
	}

	public void setCatEstatus(CatEstatus catEstatus) {
		this.catEstatus = catEstatus;
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
    
    
    

}
