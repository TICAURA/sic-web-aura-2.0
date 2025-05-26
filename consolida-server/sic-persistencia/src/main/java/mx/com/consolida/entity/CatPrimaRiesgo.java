
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
@Table(name="cat_prima_riesgo")
@NamedQueries({
    @NamedQuery(name = "CatPrimaRiesgo.findAll", query = "SELECT c FROM CatPrimaRiesgo c")})
public class CatPrimaRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat_prima_riesgo")
    private Long idCatPrimaRiesgo;
    @Column(name = "clave_riesgo")
    private String claveRiesgo;
    @Column(name = "riesgo_trabajo")
    private BigDecimal riesgoTrabajo;

    @Column(name = "ind_estatus")
    private Long indEstatus;
    @Column(name = "usuario_alta")
    private Long idUsuarioAlta;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_modificacion")
    private Long idUsuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public CatPrimaRiesgo() {
    }

	public Long getIdCatPrimaRiesgo() {
		return idCatPrimaRiesgo;
	}

	public void setIdCatPrimaRiesgo(Long idCatPrimaRiesgo) {
		this.idCatPrimaRiesgo = idCatPrimaRiesgo;
	}

	public String getClaveRiesgo() {
		return claveRiesgo;
	}

	public void setClaveRiesgo(String claveRiesgo) {
		this.claveRiesgo = claveRiesgo;
	}

	public BigDecimal getRiesgoTrabajo() {
		return riesgoTrabajo;
	}

	public void setRiesgoTrabajo(BigDecimal riesgoTrabajo) {
		this.riesgoTrabajo = riesgoTrabajo;
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

}
