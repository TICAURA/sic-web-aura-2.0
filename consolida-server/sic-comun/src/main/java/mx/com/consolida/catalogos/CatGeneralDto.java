package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Abel
 */

public class CatGeneralDto implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long idCatGeneral;
    private String clave;
    private String descripcion;
    private String idMasdescripcion;
    private Long indEstatus;
    private Long usuarioAlta;
    private Long usuarioModificacion;
    private Date fechaAlta;
    private Date fechaModificacion;
    
    public CatGeneralDto() {
    }
    
    public CatGeneralDto(Long idCatGeneral) {
        this.idCatGeneral = idCatGeneral;
    }
    
    public CatGeneralDto(String descripcion) {
    	this.descripcion = descripcion;
    }
    
    public CatGeneralDto(Long idCatGeneral, String descripcion) {
        this.idCatGeneral = idCatGeneral;
        this.descripcion = descripcion;
    }

    public CatGeneralDto(Long idCatGeneral, String clave, String descripcion) {
    	this.clave = clave;
        this.idCatGeneral = idCatGeneral;
        this.descripcion = descripcion;
    }
    
    public CatGeneralDto(Long idCatGeneral,String clave,String descripcion,Long indEstatus) {
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



    public Long getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(Long usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    

    public Long getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(Long usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public String getIdMasdescripcion() {
		return idMasdescripcion;
	}

	public void setIdMasdescripcion(String idMasdescripcion) {
		this.idMasdescripcion = idMasdescripcion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatGeneral != null ? idCatGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CatGeneralDto)) {
            return false;
        }
        CatGeneralDto other = (CatGeneralDto) object;
        if ((this.idCatGeneral == null && other.idCatGeneral != null) || (this.idCatGeneral != null && !this.idCatGeneral.equals(other.idCatGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.catalogos.CatGeneralDto[ idCatGeneral=" + idCatGeneral + " ]";
    }
    
}
