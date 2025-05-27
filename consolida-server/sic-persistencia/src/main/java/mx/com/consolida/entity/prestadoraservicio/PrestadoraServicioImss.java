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

import mx.com.consolida.entity.seguridad.Usuario;

@Entity
@Table(name = "prestadora_servicio_imss")
@NamedQueries({ @NamedQuery(name = "PrestadoraServicioImss.findAll", query = "SELECT c FROM PrestadoraServicioImss c") })
public class PrestadoraServicioImss implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_prestadora_servicio_imss")
	private Long idPrestadoraServicioImss;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prestadora_servicio", nullable = false)
	private PrestadoraServicio prestadoraServicio;
	
	@Column(name = "usuario", nullable = false, length = 50)
	private String usuario;
	
	@Column(name = "password", nullable = false, length = 50)
	private String password;
		
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
	
	public PrestadoraServicioImss() {
		
	}
	
	public PrestadoraServicioImss(Long idPrestadoraServicioImss) {
		this.idPrestadoraServicioImss = idPrestadoraServicioImss;
	}

	public Long getIdPrestadoraServicioImss() {
		return idPrestadoraServicioImss;
	}

	public void setIdPrestadoraServicioImss(Long idPrestadoraServicioImss) {
		this.idPrestadoraServicioImss = idPrestadoraServicioImss;
	}

	public PrestadoraServicio getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicio prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
