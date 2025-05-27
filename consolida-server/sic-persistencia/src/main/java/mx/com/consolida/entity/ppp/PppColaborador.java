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

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "ppp_colaborador")
@NamedQueries({ @NamedQuery(name = "PppColaborador.findAll", query = "SELECT c FROM PppColaborador c") })
public class PppColaborador implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ppp_colaborador")
	private Long idPppColaborador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ppp_nomina", nullable = false)
	private PppNomina pppNomina;
	
	@Column(name = "rfc", nullable = false)
	private String rfc;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@Column(name = "monto_ppp", nullable = false)
	private BigDecimal montoPpp;
	
	@Column(name = "reintentos_dispersion")
	private Long reintentosDispersion;
	
	@Column(name = "curp", nullable = false)
	private String curp;
	
	@Column(name = "numero_seguro_social")
	private String numeroSeguroSocial;
	
	@Column(name = "correo_electronico")
	private String correoElectronico;
	
	@Column(name = "institucion_contraparte")
	private String institucionContraparte;
	
	
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
	private Long indEstatus;
	
	@Column(name = "puesto")
	private String puesto;
	
	@Column(name = "codigo_postal")
	private String codigoPostal;
	
	@Column(name = "domicilio")
	private String domicilio;
	
	@Column(name = "numero_afiliacion")
	private String numeroAfiliacion;

	public Long getIdPppColaborador() {
		return idPppColaborador;
	}

	public void setIdPppColaborador(Long idPppColaborador) {
		this.idPppColaborador = idPppColaborador;
	}

	public PppNomina getPppNomina() {
		return pppNomina;
	}

	public void setPppNomina(PppNomina pppNomina) {
		this.pppNomina = pppNomina;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getMontoPpp() {
		return montoPpp;
	}

	public void setMontoPpp(BigDecimal montoPpp) {
		this.montoPpp = montoPpp;
	}

	public Long getReintentosDispersion() {
		return reintentosDispersion;
	}

	public void setReintentosDispersion(Long reintentosDispersion) {
		this.reintentosDispersion = reintentosDispersion;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
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

	public String getNumeroSeguroSocial() {
		return numeroSeguroSocial;
	}

	public void setNumeroSeguroSocial(String numeroSeguroSocial) {
		this.numeroSeguroSocial = numeroSeguroSocial;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getInstitucionContraparte() {
		return institucionContraparte;
	}

	public void setInstitucionContraparte(String institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getNumeroAfiliacion() {
		return numeroAfiliacion;
	}

	public void setNumeroAfiliacion(String numeroAfiliacion) {
		this.numeroAfiliacion = numeroAfiliacion;
	}
	
	
	
}
