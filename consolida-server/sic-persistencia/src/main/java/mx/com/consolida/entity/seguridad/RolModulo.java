package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rol_modulo database table.
 * 
 */
@Entity
@Table(name="rol_modulo")
@NamedQuery(name="RolModulo.findAll", query="SELECT r FROM RolModulo r")
public class RolModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_rol_modulo")
	private Long idRolModulo;

	@Column(name="ind_estatus")
	private String indEstatus;

	//bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name="id_modulo")
	private Modulo modulo;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;

	public RolModulo() {
	}

	public Long getIdRolModulo() {
		return this.idRolModulo;
	}

	public void setIdRolModulo(Long idRolModulo) {
		this.idRolModulo = idRolModulo;
	}

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}