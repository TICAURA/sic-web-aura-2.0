package mx.com.consolida.entity.crm;
// Generated 6/05/2020 12:07:19 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 * ClienteProducto generated by hbm2java
 */
@Entity
@Table(name = "cliente_servicio")
@NamedQueries({
	@NamedQuery(name = "ClienteServicio.findAll", query = "SELECT c FROM ClienteServicio c") })
public class ClienteServicio implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cliente_servicio")
	private Long idClienteServicio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prest_serv_producto", nullable = false)
	private PrestadoraServicioProducto prestadoraServicioProducto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	public ClienteServicio() {
	}

	public ClienteServicio(Long idClienteServicio) {
		this.idClienteServicio = idClienteServicio;
	}


	public Long getIdClienteServicio() {
		return idClienteServicio;
	}

	public void setIdClienteServicio(Long idClienteServicio) {
		this.idClienteServicio = idClienteServicio;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	
	public Long getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public PrestadoraServicioProducto getPrestadoraServicioProducto() {
		return prestadoraServicioProducto;
	}

	public void setPrestadoraServicioProducto(PrestadoraServicioProducto prestadoraServicioProducto) {
		this.prestadoraServicioProducto = prestadoraServicioProducto;
	}
}
