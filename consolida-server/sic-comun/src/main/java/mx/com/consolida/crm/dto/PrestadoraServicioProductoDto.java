package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;


public class PrestadoraServicioProductoDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioProducto;
	private PrestadoraServicioDto prestadoraServicioDto;
	private List<CatGeneralDto> catSat;
	private String descripcionProductoConsolida;
	private UsuarioDTO usuarioAlta;
	private Date fechaAlta;
	private Boolean indEstatus;
	
	private String clave;
	private String descripcion;
	private Long idCatGeneral;
	
	
	public PrestadoraServicioProductoDto() {
	}


	public Long getIdPrestadoraServicioProducto() {
		return idPrestadoraServicioProducto;
	}


	public void setIdPrestadoraServicioProducto(Long idPrestadoraServicioProducto) {
		this.idPrestadoraServicioProducto = idPrestadoraServicioProducto;
	}


	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}


	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
	}


	public String getDescripcionProductoConsolida() {
		return descripcionProductoConsolida;
	}


	public void setDescripcionProductoConsolida(String descripcionProductoConsolida) {
		this.descripcionProductoConsolida = descripcionProductoConsolida;
	}


	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}


	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Boolean getIndEstatus() {
		return indEstatus;
	}


	public void setIndEstatus(Boolean indEstatus) {
		this.indEstatus = indEstatus;
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


	public Long getIdCatGeneral() {
		return idCatGeneral;
	}


	public void setIdCatGeneral(Long idCatGeneral) {
		this.idCatGeneral = idCatGeneral;
	}


	public List<CatGeneralDto> getCatSat() {
		return catSat;
	}


	public void setCatSat(List<CatGeneralDto> catSat) {
		this.catSat = catSat;
	}


}