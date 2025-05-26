package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.generico.CatGenericoDTO;

public class CatRenapoDTO implements Serializable {

	private Integer idCatRenapo;
	private String cp;
	private String pais;
	private String colonia;
	private String asentamiento;
	private String tipoAsentamiento;
	private String municipio;
	private String estadoPais;
	private String cTipoAsentamiento;
	private String zona;
	private CatGenericoDTO catEstadoPaisDTO;
	private CatMunicipioDTO catMunicipioDTO;
	private List<CatGenericoDTO> listCatTipoAsentamiento;
	private List<CatAsentamientoDTO> listaAsentamiento;

	public Integer getIdCatRenapo() {
		return idCatRenapo;
	}

	public void setIdCatRenapo(Integer idCatRenapo) {
		this.idCatRenapo = idCatRenapo;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getTipoAsentamiento() {
		return tipoAsentamiento;
	}

	public void setTipoAsentamiento(String tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstadoPais() {
		return estadoPais;
	}

	public void setEstadoPais(String estadoPais) {
		this.estadoPais = estadoPais;
	}

	public String getcTipoAsentamiento() {
		return cTipoAsentamiento;
	}

	public void setcTipoAsentamiento(String cTipoAsentamiento) {
		this.cTipoAsentamiento = cTipoAsentamiento;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public CatMunicipioDTO getCatMunicipioDTO() {
		return catMunicipioDTO;
	}

	public void setCatMunicipioDTO(CatMunicipioDTO catMunicipioDTO) {
		this.catMunicipioDTO = catMunicipioDTO;
	}

	public CatGenericoDTO getCatEstadoPaisDTO() {
		return catEstadoPaisDTO;
	}

	public void setCatEstadoPaisDTO(CatGenericoDTO catEstadoPaisDTO) {
		this.catEstadoPaisDTO = catEstadoPaisDTO;
	}

	public List<CatGenericoDTO> getListCatTipoAsentamiento() {
		return listCatTipoAsentamiento;
	}

	public void setListCatTipoAsentamiento(
			List<CatGenericoDTO> listCatTipoAsentamiento) {
		this.listCatTipoAsentamiento = listCatTipoAsentamiento;
	}

	public List<CatAsentamientoDTO> getListaAsentamiento() {
		return listaAsentamiento;
	}

	public void setListaAsentamiento(List<CatAsentamientoDTO> listaAsentamiento) {
		this.listaAsentamiento = listaAsentamiento;
	}

	public String getAsentamiento() {
		return asentamiento;
	}

	public void setAsentamiento(String asentamiento) {
		this.asentamiento = asentamiento;
	}

}
