package mx.com.consolida.entity.crm;

import java.io.Serializable;
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
@Table(name = "cat_formulas")
@NamedQueries({ @NamedQuery(name = "CatFormulas.findAll", query = "SELECT c FROM CatFormulas c") })
public class CatFormulas implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cat_formulas", nullable = false)
	private Long idCatFormulas;
	
	@Column(name = "clave", nullable = false)
	private String clave;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", nullable = false, length = 19)
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", length = 19)
	private Date fechaModificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alta", nullable = false)
	private Usuario usuarioAlta;
	
	@Column(name = "ind_estatus", nullable = false)
	private Long indEstatus;

	
	public CatFormulas() {
		
	}

	public Long getIdCatFormulas() {
		return idCatFormulas;
	}

	public void setIdCatFormulas(Long idCatFormulas) {
		this.idCatFormulas = idCatFormulas;
	}

	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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


	public Usuario getUsuarioAlta() {
		return usuarioAlta;
	}


	public void setUsuarioAlta(Usuario usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}


	public Long getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}
	
	
}
