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
@Table(name="cat_vacaciones")
@NamedQueries({
    @NamedQuery(name = "CatVacaciones.findAll", query = "SELECT c FROM CatVacaciones c")})
public class CatVacaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id_cat_vacaciones")
    private Integer idCatVacaciones;
    @Column(name = "anios_inicio")
    private Integer aniosInicio;
    @Column(name = "anios_fin")
    private Integer aniosFin;
    @Column(name = "vacaciones")
    private BigDecimal vacaciones;
    @Column(name = "ind_estatus")
    private Integer indEstatus;
    @Column(name = "id_usuario_alta")
    private Integer idUsuarioAlta;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    public CatVacaciones() {
    }

    public CatVacaciones(Integer idCatVacaciones) {
        this.idCatVacaciones = idCatVacaciones;
    }

	public Integer getIdCatVacaciones() {
		return idCatVacaciones;
	}

	public void setIdCatVacaciones(Integer idCatVacaciones) {
		this.idCatVacaciones = idCatVacaciones;
	}

	public Integer getAniosInicio() {
		return aniosInicio;
	}

	public void setAniosInicio(Integer aniosInicio) {
		this.aniosInicio = aniosInicio;
	}

	public Integer getAniosFin() {
		return aniosFin;
	}

	public void setAniosFin(Integer aniosFin) {
		this.aniosFin = aniosFin;
	}

	public BigDecimal getVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(BigDecimal vacaciones) {
		this.vacaciones = vacaciones;
	}

	public Integer getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Integer indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Integer getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Integer idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
