package mx.com.consolida.entity.ppp;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ppp_nomina_complemento")
@NamedQueries({ @NamedQuery(name = "PppNominaComplemento.findAll", query = "SELECT c FROM PppNominaComplemento c") })
public class PppNominaComplemento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_nomina_complemento")
	private Long idPppNominaComplemento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina")
	private PppNomina pppNomina;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_facturacion",  length = 19)
	private Date fechaFacturacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_timbrado",  length = 19)
	private Date fechaTimbrado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_dispersion",  length = 19)
	private Date fechaDispersion;
	
	@Column(name = "requiere_financiamiento")
	private Boolean requiereFianciamiento;
	
	@Column(name = "monto_financiamiento")
	private BigDecimal montoFinanciamiento;
	
	@Column(name = "id_definicion_documento")
	private Long idDefinicionDocumento;
	
	@Column(name="id_CMS")
	private Long idCMS;
	
	@Column(name = "nombre_archivo", length = 245)
	private String nombreArchivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;
	
	@Column(name = "observaciones", length = 250)
	private String observaciones;

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
	private Long indEstatus;
	
	public PppNominaComplemento() {
		
	}
	
	public PppNominaComplemento(Long idPppNominaComplemento) {
		this.idPppNominaComplemento= idPppNominaComplemento;
	}

	public Long getIdPppNominaComplemento() {
		return idPppNominaComplemento;
	}

	public void setIdPppNominaComplemento(Long idPppNominaComplemento) {
		this.idPppNominaComplemento = idPppNominaComplemento;
	}

	public PppNomina getPppNomina() {
		return pppNomina;
	}

	public void setPppNomina(PppNomina pppNomina) {
		this.pppNomina = pppNomina;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public Date getFechaTimbrado() {
		return fechaTimbrado;
	}

	public void setFechaTimbrado(Date fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	public Date getFechaDispersion() {
		return fechaDispersion;
	}

	public void setFechaDispersion(Date fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}

	public Boolean getRequiereFianciamiento() {
		return requiereFianciamiento;
	}

	public void setRequiereFianciamiento(Boolean requiereFianciamiento) {
		this.requiereFianciamiento = requiereFianciamiento;
	}

	public BigDecimal getMontoFinanciamiento() {
		return montoFinanciamiento;
	}

	public void setMontoFinanciamiento(BigDecimal montoFinanciamiento) {
		this.montoFinanciamiento = montoFinanciamiento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}

	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
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
	

}
