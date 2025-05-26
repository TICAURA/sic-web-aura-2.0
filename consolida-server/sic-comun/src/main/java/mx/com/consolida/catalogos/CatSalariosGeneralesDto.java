package mx.com.consolida.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Abel
 */

public class CatSalariosGeneralesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idCatSalariosGenerales;
    private String descripcion;
    private BigDecimal valor;
    private Long indEstatus;
    private Long idUsuarioAlta;
    private Date fechaAlta;
    
	public Long getIdCatSalariosGeneraleas() {
		return idCatSalariosGenerales;
	}
	public void setIdCatSalariosGeneraleas(Long idCatSalariosGenerales) {
		this.idCatSalariosGenerales = idCatSalariosGenerales;
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
    
}
