package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Abel
 */

public class CatVacacionesLeyDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer idCatVacacionesLey;
    private Integer anios;
    private Integer vacaciones;
    private Integer indEstatus;
    private Integer idUsuarioAlta;
    private Date fechaAlta;

    public CatVacacionesLeyDto() {
    }

    public CatVacacionesLeyDto(Integer idCatVacacionesLey) {
        this.idCatVacacionesLey = idCatVacacionesLey;
    }

    public Integer getIdCatVacacionesLey() {
        return idCatVacacionesLey;
    }

    public void setIdCatVacacionesLey(Integer idCatVacacionesLey) {
        this.idCatVacacionesLey = idCatVacacionesLey;
    }

    public Integer getAnios() {
        return anios;
    }

    public void setAnios(Integer anios) {
        this.anios = anios;
    }

    public Integer getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(Integer vacaciones) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatVacacionesLey != null ? idCatVacacionesLey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatVacacionesLeyDto)) {
            return false;
        }
        CatVacacionesLeyDto other = (CatVacacionesLeyDto) object;
        if ((this.idCatVacacionesLey == null && other.idCatVacacionesLey != null) || (this.idCatVacacionesLey != null && !this.idCatVacacionesLey.equals(other.idCatVacacionesLey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatVacacionesLeyDto[ idCatVacacionesLey=" + idCatVacacionesLey + " ]";
    }
    
}
