package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rol_modulo database table.
 * 
 */
@Entity
@Table(name="rol_pantalla")
@NamedQuery(name="RolPantalla.findAll", query="SELECT r FROM RolPantalla r")
public class RolPantalla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_rol_pantalla")
	private Long idRolPantalla;

	@Column(name="ind_estatus")
	private String indEstatus;

	//bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name="id_pantalla")
	private Pantalla pantalla;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;

	public RolPantalla() {
	}

	public Long getIdRolPantalla() {
		return this.idRolPantalla;
	}

	public void setIdRolPantalla(Long idRolPantalla) {
		this.idRolPantalla = idRolPantalla;
	}

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Pantalla getPantalla() {
		return this.pantalla;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantalla = pantalla;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}