package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Abel
 */

public class CatMaestroDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer idCatMaestro;
    private String clave;
    private String descripcion;
    private Integer indEstatus;
    private Integer idUsuarioAlta;
    private Date fechaAlta;
    private List<CatGeneralDto> catGeneralList;

    public CatMaestroDto() {
    }

    public CatMaestroDto(Integer idCatMaestro) {
        this.idCatMaestro = idCatMaestro;
    }

    public Integer getIdCatMaestro() {
        return idCatMaestro;
    }

    public void setIdCatMaestro(Integer idCatMaestro) {
        this.idCatMaestro = idCatMaestro;
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

    public List<CatGeneralDto> getCatGeneralList() {
        return catGeneralList;
    }

    public void setCatGeneralList(List<CatGeneralDto> catGeneralList) {
        this.catGeneralList = catGeneralList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatMaestro != null ? idCatMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatMaestroDto)) {
            return false;
        }
        CatMaestroDto other = (CatMaestroDto) object;
        if ((this.idCatMaestro == null && other.idCatMaestro != null) || (this.idCatMaestro != null && !this.idCatMaestro.equals(other.idCatMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatMaestroDto[ idCatMaestro=" + idCatMaestro + " ]";
    }
    
}
