package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class CatSerieDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CatGeneralDto catCelula;
	private CatGeneralDto catTipoComprobante;
	private Long idCatSerie;
	private String indEstatus;
	private Date fechaInicioVigencia;
	private String fechaInicioVigenciaFormato;
	private Date fechaFinVigencia;
	private String fechaFinVigenciaFormato;
	private Long folioInicial;
	private String nombreSerie;
	private UsuarioDTO usuarioAlta;
	private Long idEstatusSerie; 
	
	

	
	
	
	public CatSerieDto (){}
	
	public CatSerieDto (Long idCatSerie){
		this.idCatSerie = idCatSerie;
	}
	

	public Long getIdCatSerie() {
		return idCatSerie;
	}

	public void setIdCatSerie(Long idCatSerie) {
		this.idCatSerie = idCatSerie;
	}

	public CatGeneralDto getCatTipoComprobante() {
		return catTipoComprobante;
	}

	public void setCatTipoComprobante(CatGeneralDto catTipoComprobante) {
		this.catTipoComprobante = catTipoComprobante;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	public Long getFolioInicial() {
		return folioInicial;
	}

	public void setFolioInicial(Long folioInicial) {
		this.folioInicial = folioInicial;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public CatGeneralDto getCatCelula() {
		return catCelula;
	}

	public void setCatCelula(CatGeneralDto catCelula) {
		this.catCelula = catCelula;
	}

	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public String getFechaInicioVigenciaFormato() {
		return fechaInicioVigenciaFormato;
	}

	public void setFechaInicioVigenciaFormato(String fechaInicioVigenciaFormato) {
		this.fechaInicioVigenciaFormato = fechaInicioVigenciaFormato;
	}

	public String getFechaFinVigenciaFormato() {
		return fechaFinVigenciaFormato;
	}

	public void setFechaFinVigenciaFormato(String fechaFinVigenciaFormato) {
		this.fechaFinVigenciaFormato = fechaFinVigenciaFormato;
	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getIdEstatusSerie() {
		return idEstatusSerie;
	}

	public void setIdEstatusSerie(Long idEstatusSerie) {
		this.idEstatusSerie = idEstatusSerie;
	}

	

}
