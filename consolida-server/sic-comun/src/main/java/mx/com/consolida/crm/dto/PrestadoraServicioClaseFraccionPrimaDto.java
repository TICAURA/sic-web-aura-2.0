package mx.com.consolida.crm.dto;

import java.io.Serializable;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class PrestadoraServicioClaseFraccionPrimaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioClaseFraccionPrima;

	private PrestadoraServicioDto prestadoraServicio;

	private CatGeneralDto catClase;
	
	private CatGeneralDto catRiesgo;

	private String prima;

	private String fraccion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Long indEstatus;

	public PrestadoraServicioClaseFraccionPrimaDto() {

	}

	public PrestadoraServicioClaseFraccionPrimaDto(Long idPrestadoraServicioClaseFraccionPrima) {
		this.idPrestadoraServicioClaseFraccionPrima = idPrestadoraServicioClaseFraccionPrima;
	}

	public Long getIdPrestadoraServicioClaseFraccionPrima() {
		return idPrestadoraServicioClaseFraccionPrima;
	}

	public void setIdPrestadoraServicioClaseFraccionPrima(Long idPrestadoraServicioClaseFraccionPrima) {
		this.idPrestadoraServicioClaseFraccionPrima = idPrestadoraServicioClaseFraccionPrima;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public CatGeneralDto getCatClase() {
		return catClase;
	}

	public void setCatClase(CatGeneralDto catClase) {
		this.catClase = catClase;
	}

	public String getPrima() {
		return prima;
	}

	public void setPrima(String prima) {
		this.prima = prima;
	}

	public String getFraccion() {
		return fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public CatGeneralDto getCatRiesgo() {
		return catRiesgo;
	}

	public void setCatRiesgo(CatGeneralDto catRiesgo) {
		this.catRiesgo = catRiesgo;
	}
	
	

}
