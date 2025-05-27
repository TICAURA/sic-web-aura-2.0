package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Cat_Tipo_Evento")
@NamedQuery(name = "CatTipoEvento.findAll", query = "SELECT c FROM CatTipoEvento c")
public class CatTipoEvento implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_Cat_Tipo_Evento")
	private Long idCatTipoEvento;

	@Column(name = "cve_tipo_evento")
	private String cveTipoEvento;

	@Basic(optional = false)
	@Column(name = "descripcion_tipo_evento")
	private String descripcionTipoEvento;

	@Basic(optional = false)
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@Basic(optional = false)
	@Column(name = "usuario_alta")
	private Long usuarioAlta;
	
	@Column(name = "usuario_modificacion")
	private Long usuarioModificacion;

	@Basic(optional = false)
	@Column(name = "ind_estatus")
	private Long indEstatus;
	
	

	public CatTipoEvento() {
		super();
	}

	public CatTipoEvento(Long idCatTipoEvento) {
		super();
		this.idCatTipoEvento = idCatTipoEvento;
	}

	public Long getIdCatTipoEvento() {
		return idCatTipoEvento;
	}

	public void setIdCatTipoEvento(Long idCatTipoEvento) {
		this.idCatTipoEvento = idCatTipoEvento;
	}

	public String getCveTipoEvento() {
		return cveTipoEvento;
	}

	public void setCveTipoEvento(String cveTipoEvento) {
		this.cveTipoEvento = cveTipoEvento;
	}

	public String getDescripcionTipoEvento() {
		return descripcionTipoEvento;
	}

	public void setDescripcionTipoEvento(String descripcionTipoEvento) {
		this.descripcionTipoEvento = descripcionTipoEvento;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	
	

}
