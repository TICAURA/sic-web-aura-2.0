package mx.com.consolida.entity.ppp;

import java.io.Serializable;
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

import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_factura_documento")
@NamedQueries({ @NamedQuery(name = "PppFacturaDocumento.findAll", query = "SELECT c FROM PppFacturaDocumento c") })
public class PppFacturaDocumento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_factura_documento")
	private Long idPppFacturaDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_factura")
	private PppFacturas pppFactura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_definicion_documento", nullable = false)
	private DefinicionDocumento definicionDocumento;
	
	@Column(name="id_CMS")
	private Long idCMS;
	
	@Column(name = "nombre_archivo", length = 200)
	private String nombreArchivo;
	
	@Column(name = "uuid", length = 200)
	private String uuid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;

	@Column(name = "ind_estatus", nullable = false)
	private String indEstatus;


	public Long getIdPppFacturaDocumento() {
		return idPppFacturaDocumento;
	}

	public void setIdPppFacturaDocumento(Long idPppFacturaDocumento) {
		this.idPppFacturaDocumento = idPppFacturaDocumento;
	}

	public PppFacturas getPppFactura() {
		return pppFactura;
	}

	public void setPppFactura(PppFacturas pppFactura) {
		this.pppFactura = pppFactura;
	}

	public DefinicionDocumento getDefinicionDocumento() {
		return definicionDocumento;
	}

	public void setDefinicionDocumento(DefinicionDocumento definicionDocumento) {
		this.definicionDocumento = definicionDocumento;
	}

	public Long getIdCMS() {
		return idCMS;
	}

	public void setIdCMS(Long idCMS) {
		this.idCMS = idCMS;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	
	
	

}
