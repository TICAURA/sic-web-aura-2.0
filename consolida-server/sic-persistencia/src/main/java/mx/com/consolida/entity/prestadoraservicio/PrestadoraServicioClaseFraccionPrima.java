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
import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "prestadora_servicio_clase_fraccion_prima")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioClaseFraccionPrima.findAll", query = "SELECT c FROM PrestadoraServicioClaseFraccionPrima c") })
public class PrestadoraServicioClaseFraccionPrima {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_prestadora_servicio_clase_fraccion_prima")
	private Long idPrestadoraServicioClaseFraccionPrima;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio", nullable = false)
	private PrestadoraServicio prestadoraServicio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cat_clase", nullable = false)
	private CatGeneral catClase;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_cat_riesgo", nullable = false)
//	private CatGeneral catRiesgo;

	@Column(name = "prima")
	private String prima;

	@Column(name = "fraccion")
	private String fraccion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modificacion")
	private Usuario usuarioModificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;



	public Long getIdPrestadoraServicioClaseFraccionPrima() {
		return idPrestadoraServicioClaseFraccionPrima;
	}

	public void setIdPrestadoraServicioClaseFraccionPrima(Long idPrestadoraServicioClaseFraccionPrima) {
		this.idPrestadoraServicioClaseFraccionPrima = idPrestadoraServicioClaseFraccionPrima;
	}

	public PrestadoraServicio getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public CatGeneral getCatClase() {
		return catClase;
	}

	public void setCatClase(CatGeneral catClase) {
		this.catClase = catClase;
	}

	public String getPrima() {
		return prima;
	}

	public void setPrima(String prima) {
		this.prima = prima;
	}

	public String getFraccion() {
		return fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
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

//	public CatGeneral getCatRiesgo() {
//		return catRiesgo;
//	}
//
//	public void setCatRiesgo(CatGeneral catRiesgo) {
//		this.catRiesgo = catRiesgo;
//	}

}
