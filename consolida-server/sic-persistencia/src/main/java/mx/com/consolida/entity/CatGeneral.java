package mx.com.consolida.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.com.consolida.catalogos.CatGeneralDto;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cat_general")
@NamedQueries({ @NamedQuery(name = "obtenerByIdMaestro", query = "SELECT c FROM CatGeneral c") })
@SqlResultSetMappings({ @SqlResultSetMapping(name = "obtenerGeneral", classes = {
		@ConstructorResult(targetClass = CatGeneralDto.class, columns = {
				@ColumnResult(name = "idCatGeneral", type = Long.class),
				@ColumnResult(name = "clave", type = String.class),
				@ColumnResult(name = "descripcion", type = String.class),
				@ColumnResult(name = "indEstatus", type = Long.class) }) }) })
public class CatGeneral implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "id_cat_general")
	private Long idCatGeneral;

	@Column(name = "clave")
	private String clave;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "ind_estatus")
	private Long indEstatus;
	
	@Column(name = "usuario_alta")
	private Long idUsuarioAlta;
	
	@Column(name = "usuario_modificacion")
	private Long idUsuarioModificacion;
	
	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	@JoinColumn(name = "id_cat_maestro", referencedColumnName = "id_cat_maestro")
	@OneToOne(targetEntity = CatMaestro.class)
	private CatMaestro catMaestro;

	public CatGeneral() {
	}

	public CatGeneral(Long idCatGeneral) {
		this.idCatGeneral = idCatGeneral;
	}

	public CatGeneral(CatGeneralDto dto) {
		this.idCatGeneral = dto.getIdCatGeneral();
		this.clave = dto.getClave();
		this.descripcion = dto.getDescripcion();
		this.indEstatus = dto.getIndEstatus();
	}

	public CatGeneral(String descripcion) {
		this.descripcion = descripcion;
	}

	public CatGeneral(Long idCatGeneral, String descripcion) {
		this.idCatGeneral = idCatGeneral;
		this.descripcion = descripcion;
	}

	public CatGeneral(Long idCatGeneral, String clave, String descripcion) {
		this.clave = clave;
		this.idCatGeneral = idCatGeneral;
		this.descripcion = descripcion;
	}

	public CatGeneral(Long idCatGeneral, String clave, String descripcion, Long indEstatus) {
		this.idCatGeneral = idCatGeneral;
		this.clave = clave;
		this.descripcion = descripcion;
		this.indEstatus = indEstatus;
	}

	public Long getIdCatGeneral() {
		return idCatGeneral;
	}

	public void setIdCatGeneral(Long idCatGeneral) {
		this.idCatGeneral = idCatGeneral;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public CatMaestro getCatMaestro() {
		return catMaestro;
	}

	public void setCatMaestro(CatMaestro catMaestro) {
		this.catMaestro = catMaestro;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCatGeneral != null ? idCatGeneral.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CatGeneral)) {
			return false;
		}
		CatGeneral other = (CatGeneral) object;
		if ((this.idCatGeneral == null && other.idCatGeneral != null)
				|| (this.idCatGeneral != null && !this.idCatGeneral.equals(other.idCatGeneral))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mx.com.consolida.entity.CatGeneral[ idCatGeneral=" + idCatGeneral + " ]";
	}

}
