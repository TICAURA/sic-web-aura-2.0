package mx.com.consolida.entity.prestadoraservicio;

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
import mx.com.consolida.entity.CatSubGiroComercial;
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "prestadora_servicio_giro_comercial")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioGiroComercial.findAll", query = "SELECT c FROM PrestadoraServicioGiroComercial c") })
public class PrestadoraServicioGiroComercial implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prestadora_servicio_giro_comercial")
	private Long idPrestadoraServicioGiroComercial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_giro_comercial", nullable = false)
	private CatGeneral catGiroComercial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_sub_giro_comercial", nullable = false)
    private CatSubGiroComercial catSubGiroComercial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio", nullable = false)
	private PrestadoraServicio prestadoraServicio;

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

	public PrestadoraServicioGiroComercial() {

	}

	public PrestadoraServicioGiroComercial(Long idPrestadoraServicioGiroComercial) {
		this.idPrestadoraServicioGiroComercial = idPrestadoraServicioGiroComercial;
	}

	public Long getIdPrestadoraServicioGiroComercial() {
		return idPrestadoraServicioGiroComercial;
	}

	public void setIdPrestadoraServicioGiroComercial(Long idPrestadoraServicioGiroComercial) {
		this.idPrestadoraServicioGiroComercial = idPrestadoraServicioGiroComercial;
	}

	public CatGeneral getCatGiroComercial() {
		return catGiroComercial;
	}

	public void setCatGiroComercial(CatGeneral catGiroComercial) {
		this.catGiroComercial = catGiroComercial;
	}

	public CatSubGiroComercial getCatSubGiroComercial() {
		return catSubGiroComercial;
	}

	public void setCatSubGiroComercial(CatSubGiroComercial catSubGiroComercial) {
		this.catSubGiroComercial = catSubGiroComercial;
	}

	public PrestadoraServicio getPrestadoraServicio() {
		return prestadoraServicio;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}



}
