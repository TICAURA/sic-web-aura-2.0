package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.usuario.UsuarioDTO;

public class CatFolioDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idCatFolio;
	
	private List<CatSerieDto> listaCatSerie;
	
	private CatSerieDto catSerieDto;
	
	private String numeroFolio;
	
	private UsuarioDTO usuarioAlta;
	
	public CatFolioDto() {
		
	}
	
	public CatFolioDto(Long idCatFolio) {
		this.idCatFolio = idCatFolio;
	}

	public Long getIdCatFolio() {
		return idCatFolio;
	}

	public void setIdCatFolio(Long idCatFolio) {
		this.idCatFolio = idCatFolio;
	}

	public List<CatSerieDto> getListaCatSerie() {
		return listaCatSerie;
	}

	public void setListaCatSerie(List<CatSerieDto> listaCatSerie) {
		this.listaCatSerie = listaCatSerie;
	}

	public CatSerieDto getCatSerieDto() {
		return catSerieDto;
	}

	public void setCatSerieDto(CatSerieDto catSerieDto) {
		this.catSerieDto = catSerieDto;
	}

	public String getNumeroFolio() {
		return numeroFolio;
	}

	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	
	
	
}
