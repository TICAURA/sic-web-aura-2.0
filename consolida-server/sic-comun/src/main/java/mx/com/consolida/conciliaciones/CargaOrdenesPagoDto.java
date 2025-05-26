package mx.com.consolida.conciliaciones;


import java.sql.Date;
import java.util.List;

public class CargaOrdenesPagoDto {
	private static final long serialVersionUID = 1L;
	
	
	private Long idCargaOrdenPago;
	private String  descCarga;
	private Date fechaCarga;
	private String usuarioAlta;
	private String usuarioModificacion;
	private Long idUsuarioAlta;
	private Long idUsuarioModificacion;
	private String estatus;
	private List<OrdenPagoDto> ordenesPago;
	
	
	public Long getIdCargaOrdenPago() {
		return idCargaOrdenPago;
	}
	public void setIdCargaOrdenPago(Long idCargaOrdenPago) {
		this.idCargaOrdenPago = idCargaOrdenPago;
	}
	public String getDescCarga() {
		return descCarga;
	}
	public void setDescCarga(String descCarga) {
		this.descCarga = descCarga;
	}
	public Date getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public Long getIdUsuarioAlta() {
		return idUsuarioAlta;
	}
	public void setIdUsuarioAlta(Long idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}
	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}
	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public List<OrdenPagoDto> getOrdenesPago() {
		return ordenesPago;
	}
	public void setOrdenesPago(List<OrdenPagoDto> ordenesPago) {
		this.ordenesPago = ordenesPago;
	}
	
	

}
