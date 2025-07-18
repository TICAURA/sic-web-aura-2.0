package mx.com.consolida.entity.prestadoraservicio;
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

import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.seguridad.Usuario;

/**
 * PrestadoraServicioApoderadoLegalFacultad generated by hbm2java
 */
@Entity
@Table(name = "prestadora_servicio_apoderado_legal_facultad", catalog = "sin")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioApoderadoLegalFacultad.findAll", query = "SELECT c FROM PrestadoraServicioApoderadoLegalFacultad c") })
public class PrestadoraServicioApoderadoLegalFacultad implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_prestadora_servicio_apoderado_legal_facultad")
	private Long idPrestadoraServicioApoderadoLegalFacultad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_facultad", nullable = false)
	private CatGeneral catFacultad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio_apoderado_legal", nullable = false)
	private PrestadoraServicioApoderadoLegal prestadoraServicioApoderadoLegal;
	
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

	public PrestadoraServicioApoderadoLegalFacultad() {
	}

	public PrestadoraServicioApoderadoLegalFacultad(CatGeneral catFacultad,
			PrestadoraServicioApoderadoLegal prestadoraServicioApoderadoLegal, Usuario usuarioAlta, Date fechaAlta,
			Long indEstatus) {
		this.catFacultad = catFacultad;
		this.prestadoraServicioApoderadoLegal = prestadoraServicioApoderadoLegal;
		this.usuarioAlta = usuarioAlta;
		this.fechaAlta = fechaAlta;
		this.indEstatus = indEstatus;
	}

	public PrestadoraServicioApoderadoLegalFacultad(CatGeneral catFacultad,
			PrestadoraServicioApoderadoLegal prestadoraServicioApoderadoLegal, Usuario usuarioAlta, Date fechaAlta,
			Date fechaModificacion, Long indEstatus) {
		this.catFacultad = catFacultad;
		this.prestadoraServicioApoderadoLegal = prestadoraServicioApoderadoLegal;
		this.usuarioAlta = usuarioAlta;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaModificacion;
		this.indEstatus = indEstatus;
	}


	public Long getIdPrestadoraServicioApoderadoLegalFacultad() {
		return this.idPrestadoraServicioApoderadoLegalFacultad;
	}

	public void setIdPrestadoraServicioApoderadoLegalFacultad(Long idPrestadoraServicioApoderadoLegalFacultad) {
		this.idPrestadoraServicioApoderadoLegalFacultad = idPrestadoraServicioApoderadoLegalFacultad;
	}

	public CatGeneral getCatFacultad() {
		return catFacultad;
	}

	public void setCatFacultad(CatGeneral catFacultad) {
		this.catFacultad = catFacultad;
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

	public PrestadoraServicioApoderadoLegal getPrestadoraServicioApoderadoLegal() {
		return this.prestadoraServicioApoderadoLegal;
	}

	public void setPrestadoraServicioApoderadoLegal(PrestadoraServicioApoderadoLegal prestadoraServicioApoderadoLegal) {
		this.prestadoraServicioApoderadoLegal = prestadoraServicioApoderadoLegal;
	}

	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Column(name = "fecha_modificacion", length = 45)
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

}
