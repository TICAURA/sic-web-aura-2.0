package mx.com.facturacion.factura;

import java.io.Serializable;

import mx.com.consolida.generico.CatGenericoDTO;

public class CatMunicipioDTO implements Serializable{
	
	private Integer idCatMunicipio;
	private String clave;
	private String municipio;
	private CatGenericoDTO catEstadoPaisDTO;
	
	public CatMunicipioDTO(){
		
	}
	public CatMunicipioDTO(String demo){
		this.idCatMunicipio = 1;
		this.clave = "CDMX";
		this.municipio = "avaro Obregon";
		this.catEstadoPaisDTO = new CatGenericoDTO(1, "MX", "Mexico");
	}
	
	public Integer getIdCatMunicipio() {
		return idCatMunicipio;
	}
	public void setIdCatMunicipio(Integer idCatMunicipio) {
		this.idCatMunicipio = idCatMunicipio;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public CatGenericoDTO getCatEstadoPaisDTO() {
		return catEstadoPaisDTO;
	}
	public void setCatEstadoPaisDTO(CatGenericoDTO catEstadoPaisDTO) {
		this.catEstadoPaisDTO = catEstadoPaisDTO;
	}

}
