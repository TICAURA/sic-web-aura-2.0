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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Cliente_Temp_Bitacora_solicitudes")
@NamedQuery(name = "ClienteTempBitacoraSolicitudes.findAll", query = "SELECT c FROM ClienteTempBitacoraSolicitudes c")
public class ClienteTempBitacoraSolicitudes implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Cliente_Temp_Bitacora_solicitudes")
	private Long idClienteTempBitacoraSolicitudes;

	@JoinColumn(name = "id_cliente_temp", referencedColumnName = "id_cliente_temp")
	@ManyToOne(targetEntity = ClienteTemp.class)
	private ClienteTemp clienteTemp;

	@JoinColumn(name = "id_estatus", referencedColumnName = "id_estatus")
	@ManyToOne(targetEntity = CatEstatus.class)
	private CatEstatus catEstatus;

	@JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
	@OneToOne(targetEntity = Cotizacion.class)
	private Cotizacion cotizacion;

	@Column(name = "observacion")
	private String observacion;

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

	@Column(name = "archivo")
	private byte[] archivo;


	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@Column(name = "nombre_archivo")
	private String nombreArchivo;


	public Long getIdClienteTempBitacoraSolicitudes() {
		return idClienteTempBitacoraSolicitudes;
	}

	public void setIdClienteTempBitacoraSolicitudes(Long idClienteTempBitacoraSolicitudes) {
		this.idClienteTempBitacoraSolicitudes = idClienteTempBitacoraSolicitudes;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}


}
