//package mx.com.consolida.entity;
//
//import java.io.Serializable;
//import javax.persistence.*;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * The persistent class for the datos_usuario database table.
// * 
// */
//@Entity
//@Table(name="datos_usuario")
//@NamedQuery(name="DatosUsuario.findAll", query="SELECT d FROM DatosUsuario d")
//public class DatosUsuario implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="id_datos_usuario")
//	private Long idDatosUsuario;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_alta")
//	private Date fechaAlta;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="fecha_modificacion")
//	private Date fechaModificacion;
//
//	@Column(name="ind_estatus")
//	private String indEstatus;
//
//	private String nombre;
//
//	@Column(name="primer_apellido")
//	private String primerApellido;
//
//	@Column(name="segundo_apellido")
//	private String segundoApellido;
//
//	//bi-directional many-to-one association to Usuario
//	@OneToMany(mappedBy="datosUsuario")
//	private List<Usuario> usuarios;
//
//	public DatosUsuario() {
//	}
//
//	public Long getIdDatosUsuario() {
//		return this.idDatosUsuario;
//	}
//
//	public void setIdDatosUsuario(Long idDatosUsuario) {
//		this.idDatosUsuario = idDatosUsuario;
//	}
//
//	public Date getFechaAlta() {
//		return this.fechaAlta;
//	}
//
//	public void setFechaAlta(Date fechaAlta) {
//		this.fechaAlta = fechaAlta;
//	}
//
//	public Date getFechaModificacion() {
//		return this.fechaModificacion;
//	}
//
//	public void setFechaModificacion(Date fechaModificacion) {
//		this.fechaModificacion = fechaModificacion;
//	}
//
//	public String getIndEstatus() {
//		return this.indEstatus;
//	}
//
//	public void setIndEstatus(String indEstatus) {
//		this.indEstatus = indEstatus;
//	}
//
//	public String getNombre() {
//		return this.nombre;
//	}
//
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
//
//	public String getPrimerApellido() {
//		return this.primerApellido;
//	}
//
//	public void setPrimerApellido(String primerApellido) {
//		this.primerApellido = primerApellido;
//	}
//
//	public String getSegundoApellido() {
//		return this.segundoApellido;
//	}
//
//	public void setSegundoApellido(String segundoApellido) {
//		this.segundoApellido = segundoApellido;
//	}
//
//	public List<Usuario> getUsuarios() {
//		return this.usuarios;
//	}
//
//	public void setUsuarios(List<Usuario> usuarios) {
//		this.usuarios = usuarios;
//	}
//
//	public Usuario addUsuario(Usuario usuario) {
//		getUsuarios().add(usuario);
//		usuario.setDatosUsuario(this);
//
//		return usuario;
//	}
//
//	public Usuario removeUsuario(Usuario usuario) {
//		getUsuarios().remove(usuario);
//		usuario.setDatosUsuario(null);
//
//		return usuario;
//	}
//
//}