package mx.com.consolida.entity.crm;
// Generated 6/05/2020 12:07:19 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

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

import mx.com.consolida.entity.seguridad.Usuario;

/**
 * ClienteConceptoFacturacion generated by hbm2java
 */
@Entity
@Table(name = "cliente_concepto_facturacion")
@NamedQueries({ @NamedQuery(name = "ClienteConceptoFacturacion.findAll", query = "SELECT c FROM ClienteConceptoFacturacion c") })
public class ClienteConceptoFacturacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_concepto_facturacion_cliente")
	private Long idConceptoFacturacionCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;
	
	@Column(name = "concepto_facturacion", nullable = false)
	private String conceptoFacturacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	public ClienteConceptoFacturacion() {
	}

	public ClienteConceptoFacturacion(Cliente cliente, Usuario usuarioAlta, Usuario usuarioModificacion,String conceptoFacturacion, Date fechaAlta) {
		this.cliente = cliente;
		this.usuarioAlta = usuarioAlta;
		this.usuarioModificacion = usuarioModificacion;
		this.conceptoFacturacion = conceptoFacturacion;
		this.fechaAlta = fechaAlta;
	}

	public ClienteConceptoFacturacion(Cliente cliente, Usuario usuarioAlta, Usuario usuarioModificacion, String conceptoFacturacion, Date fechaAlta,
			Date fechaModificacion) {
		this.cliente = cliente;
		this.usuarioAlta = usuarioAlta;
		this.usuarioModificacion = usuarioModificacion;
		this.conceptoFacturacion = conceptoFacturacion;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaModificacion;
	}

	public Long getIdConceptoFacturacionCliente() {
		return this.idConceptoFacturacionCliente;
	}

	public void setIdConceptoFacturacionCliente(Long idConceptoFacturacionCliente) {
		this.idConceptoFacturacionCliente = idConceptoFacturacionCliente;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getConceptoFacturacion() {
		return this.conceptoFacturacion;
	}

	public void setConceptoFacturacion(String conceptoFacturacion) {
		this.conceptoFacturacion = conceptoFacturacion;
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

}
