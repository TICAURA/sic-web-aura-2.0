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
@Table(name = "ppp_colaborador_documento")
@NamedQueries({ @NamedQuery(name = "PppColaboradorDocumento.findAll", query = "SELECT c FROM PppColaboradorDocumento c") })
public class PppColaboradorDocumento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_colaborador_documento")
	private Long idPppColaboradorDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_colaborador", nullable = false)
	private PppColaborador pppColaborador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_definicion_documento", nullable = false)
	private DefinicionDocumento definicionDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina_factura", nullable = false)
	private PppNominaFactura pppNominaFactura;
	
	@Column(name="id_CMS")
	private Long idCMS;
	
	@Column(name = "nombre_archivo")
	private String nombreArchivo;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "certificado_emisor")
	private String certificadoEmisor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_hora_certificacion", length = 19)
	private Date fechaHoraCertificacion;
	
	
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
	private String indEstatus;
	
	public PppColaboradorDocumento() {
		
	}
	
	public PppColaboradorDocumento(Long idPppColaboradorDocumento) {
		this.idPppColaboradorDocumento = idPppColaboradorDocumento;
	}

	public Long getIdPppColaboradorDocumento() {
		return idPppColaboradorDocumento;
	}

	public void setIdPppColaboradorDocumento(Long idPppColaboradorDocumento) {
		this.idPppColaboradorDocumento = idPppColaboradorDocumento;
	}

	public PppColaborador getPppColaborador() {
		return pppColaborador;
	}

	public void setPppColaborador(PppColaborador pppColaborador) {
		this.pppColaborador = pppColaborador;
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

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCertificadoEmisor() {
		return certificadoEmisor;
	}

	public void setCertificadoEmisor(String certificadoEmisor) {
		this.certificadoEmisor = certificadoEmisor;
	}

	public Date getFechaHoraCertificacion() {
		return fechaHoraCertificacion;
	}

	public void setFechaHoraCertificacion(Date fechaHoraCertificacion) {
		this.fechaHoraCertificacion = fechaHoraCertificacion;
	}

	public PppNominaFactura getPppNominaFactura() {
		return pppNominaFactura;
	}

	public void setPppNominaFactura(PppNominaFactura pppNominaFactura) {
		this.pppNominaFactura = pppNominaFactura;
	}

}
