package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the modulo database table.
 *
 */
@Entity
@NamedQuery(name="Modulo.findAll", query="SELECT m FROM Modulo m")
public class Modulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_modulo")
	private Long idModulo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Column(name="ind_estatus")
	private String indEstatus;

	private String nombre;

	//bi-directional many-to-one association to ModuloPantalla
//	@OneToMany(mappedBy="modulo",fetch = FetchType.EAGER)
//	private List<ModuloPantalla> moduloPantallas;

	//bi-directional many-to-one association to RolModulo
//	@OneToMany(mappedBy="modulo")
//	private List<RolModulo> rolModulos;

	public Modulo() {
	}

	public Long getIdModulo() {
		return this.idModulo;
	}

	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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

//	public List<ModuloPantalla> getModuloPantallas() {
//		return this.moduloPantallas;
//	}
//
//	public void setModuloPantallas(List<ModuloPantalla> moduloPantallas) {
//		this.moduloPantallas = moduloPantallas;
//	}
//
//	public ModuloPantalla addModuloPantalla(ModuloPantalla moduloPantalla) {
//		getModuloPantallas().add(moduloPantalla);
//		moduloPantalla.setModulo(this);
//
//		return moduloPantalla;
//	}
//
//	public ModuloPantalla removeModuloPantalla(ModuloPantalla moduloPantalla) {
//		getModuloPantallas().remove(moduloPantalla);
//		moduloPantalla.setModulo(null);
//
//		return moduloPantalla;
//	}
//
//	public List<RolModulo> getRolModulos() {
//		return this.rolModulos;
//	}
//
//	public void setRolModulos(List<RolModulo> rolModulos) {
//		this.rolModulos = rolModulos;
//	}

//	public RolModulo addRolModulo(RolModulo rolModulo) {
//		getRolModulos().add(rolModulo);
//		rolModulo.setModulo(this);
//
//		return rolModulo;
//	}
//
//	public RolModulo removeRolModulo(RolModulo rolModulo) {
//		getRolModulos().remove(rolModulo);
//		rolModulo.setModulo(null);
//
//		return rolModulo;
//	}

}
