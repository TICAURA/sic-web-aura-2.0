package mx.com.consolida.entity.prestadoraservicio;
// Generated 6/05/2020 12:07:19 PM by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.seguridad.Persona;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 * PrestadoraServicioRepresentanteLegal generated by hbm2java
 */
@Entity
@Table(name = "prestadora_servicio_representante_legal")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioRepresentanteLegal.findAll", query = "SELECT c FROM PrestadoraServicioRepresentanteLegal c") })
public class PrestadoraServicioRepresentanteLegal implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_prestadora_servicio_representante_legal")
	private Long idPrestadoraServicioRepresentanteLegal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio", nullable = false)
	private PrestadoraServicio prestadoraServicio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona", nullable = false)
	private Persona persona;
	
	@Column(name = "contrasena_certificado", length = 45)
	private String contrasenaCertificado;
	
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

	public PrestadoraServicioRepresentanteLegal() {
	}
	
	public PrestadoraServicioRepresentanteLegal(Long idPrestadoraServicioRepresentanteLegal) {
		this.idPrestadoraServicioRepresentanteLegal = idPrestadoraServicioRepresentanteLegal;
	}

	public Long getIdPrestadoraServicioRepresentanteLegal() {
		return this.idPrestadoraServicioRepresentanteLegal;
	}

	public void setIdPrestadoraServicioRepresentanteLegal(Long idPrestadoraServicioRepresentanteLegal) {
		this.idPrestadoraServicioRepresentanteLegal = idPrestadoraServicioRepresentanteLegal;
	}

	
	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	
	public PrestadoraServicio getPrestadoraServicio() {
		return this.prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
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

	
	public String getContrasenaCertificado() {
		return this.contrasenaCertificado;
	}

	public void setContrasenaCertificado(String contrasenaCertificado) {
		this.contrasenaCertificado = contrasenaCertificado;
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

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prestadoraServicioRepresentanteLegal")
//	public Set getPrestadoraServicioRepresentanteLegalDocumentos() {
//		return this.prestadoraServicioRepresentanteLegalDocumentos;
//	}
//
//	public void setPrestadoraServicioRepresentanteLegalDocumentos(Set prestadoraServicioRepresentanteLegalDocumentos) {
//		this.prestadoraServicioRepresentanteLegalDocumentos = prestadoraServicioRepresentanteLegalDocumentos;
//	}

}
