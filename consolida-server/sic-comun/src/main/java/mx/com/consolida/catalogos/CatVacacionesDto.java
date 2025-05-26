package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */
public class CatVacacionesDto implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer idCatVacaciones;
    private Integer aniosInicio;
    private Integer aniosFin;
    private BigDecimal vacaciones;
    private Integer indEstatus;
    private Integer idUsuarioAlta;
    private Date fechaAlta;

    public CatVacacionesDto() {
    }

    public CatVacacionesDto(Integer idCatVacaciones) {
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
