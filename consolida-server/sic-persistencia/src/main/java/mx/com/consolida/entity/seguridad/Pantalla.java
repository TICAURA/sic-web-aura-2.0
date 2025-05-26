package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the pantalla database table.
 *
 */
@Entity
@NamedQuery(name="Pantalla.findAll", query="SELECT p FROM Pantalla p")
public class Pantalla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pantalla")
	private Long idPantalla;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Column(name="ind_estatus")
	private String indEstatus;

	private String nombre;

	@Column(name="ruta_pantalla")
	private String rutaPantalla;

	private String icono;

	//bi-directional many-to-one association to ModuloPantalla
//	@OneToMany(mappedBy="pantalla")
//	private List<ModuloPantalla> moduloPantallas;

	public Pantalla() {
	}

	public Long getIdPantalla() {
		return this.idPantalla;
	}

	public void setIdPantalla(Long idPantalla) {
		this.idPantalla = idPantalla;
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

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaPantalla() {
		return this.rutaPantalla;
	}

	public void setRutaPantalla(String rutaPantalla) {
		this.rutaPantalla = rutaPantalla;
	}

//	public List<ModuloPantalla> getModuloPantallas() {
//		return this.moduloPantallas;
//	}
//
//	public void setModuloPantallas(List<ModuloPantalla> moduloPantallas) {
//		this.moduloPantallas = moduloPantallas;
//	}

//	public ModuloPantalla addModuloPantalla(ModuloPantalla moduloPantalla) {
//		getModuloPantallas().add(moduloPantalla);
//		moduloPantalla.setPantalla(this);
//
//		return moduloPantalla;
//	}
//
//	public ModuloPantalla removeModuloPantalla(ModuloPantalla moduloPantalla) {
//		getModuloPantallas().remove(moduloPantalla);
//		moduloPantalla.setPantalla(null);
//
//		return moduloPantalla;
//	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}



}
