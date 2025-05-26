package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rol database table.
 *
 */
@Entity
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rol")
	private Long idRol;

	private String descripcion;

	@Column(name="ind_estatus")
	private String indEstatus;

	private String nombre;

	//bi-directional many-to-one association to RolModulo
//	@OneToMany(mappedBy="rol")
//	private List<RolModulo> rolModulos;

	//bi-directional many-to-one association to UsuarioRol
//	@OneToMany(mappedBy="rol")
//	private List<UsuarioRol> usuarioRols;

	public Rol() {
	}

	public Long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

//	public List<RolModulo> getRolModulos() {
//		return this.rolModulos;
//	}
//
//	public void setRolModulos(List<RolModulo> rolModulos) {
//		this.rolModulos = rolModulos;
//	}
//
//	public RolModulo addRolModulo(RolModulo rolModulo) {
//		getRolModulos().add(rolModulo);
//		rolModulo.setRol(this);
//
//		return rolModulo;
//	}
//
//	public RolModulo removeRolModulo(RolModulo rolModulo) {
//		getRolModulos().remove(rolModulo);
//		rolModulo.setRol(null);
//
//		return rolModulo;
//	}
//
//	public List<UsuarioRol> getUsuarioRols() {
//		return this.usuarioRols;
//	}
//
//	public void setUsuarioRols(List<UsuarioRol> usuarioRols) {
//		this.usuarioRols = usuarioRols;
//	}
//
//	public UsuarioRol addUsuarioRol(UsuarioRol usuarioRol) {
//		getUsuarioRols().add(usuarioRol);
//		usuarioRol.setRol(this);
//
//		return usuarioRol;
//	}
//
//	public UsuarioRol removeUsuarioRol(UsuarioRol usuarioRol) {
//		getUsuarioRols().remove(usuarioRol);
//		usuarioRol.setRol(null);
//
//		return usuarioRol;
//	}

}
