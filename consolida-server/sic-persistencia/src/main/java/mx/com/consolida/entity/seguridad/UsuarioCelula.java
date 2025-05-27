package mx.com.consolida.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.entity.celula.Celula;


/**
 * The persistent class for the usuario_rol database table.
 * 
 */
@Entity
@Table(name="usuario_celula")
@NamedQuery(name="UsuarioCelula.findAll", query="SELECT u FROM UsuarioCelula u")
public class UsuarioCelula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_usuario_celula")
	private Long idUsuarioCelula;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Column(name="ind_estatus")
	private String indEstatus;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_celula")
	private Celula celula;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public UsuarioCelula() {
	}

	public Long getIdUsuarioCelula() {
		return idUsuarioCelula;
	}


	public void setIdUsuarioCelula(Long idUsuarioCelula) {
		this.idUsuarioCelula = idUsuarioCelula;
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


	public Celula getCelula() {
		return celula;
	}


	public void setCelula(Celula celula) {
		this.celula = celula;
	}


	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}