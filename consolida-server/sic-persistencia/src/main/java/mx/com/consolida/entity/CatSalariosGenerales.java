package mx.com.consolida.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Abel
 */
@Entity
@Table(name = "cat_salarios_generales")
@NamedQueries({ @NamedQuery(name = "CatSalariosGenerales.findAll", query = "SELECT c FROM CatSalariosGenerales c") })
public class CatSalariosGenerales implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "id_cat_salarios_generaleas")
	private Long idCatSalariosGeneraleas;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "valor")
	private BigDecimal valor;
	@Column(name = "ind_estatus")
	private Long indEstatus;
	@Column(name = "usuario_alta")
	private Long idUsuarioAlta;
	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;
	@Column(name = "usuario_modificacion")
	private Long idUsuarioModificacion;
	
	/// MAPEO HIBERNATE /////////
	private String clave;
	////////////

	public Long getIdCatSalariosGeneraleas() {
		return idCatSalariosGeneraleas;
	}

	public void setIdCatSalariosGeneraleas(Long idCatSalariosGeneraleas) {
		this.idCatSalariosGeneraleas = idCatSalariosGeneraleas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	/// MAPEO HIBERNATE /////////
	@Column(name = "clave", length = 45)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	////////////

}
