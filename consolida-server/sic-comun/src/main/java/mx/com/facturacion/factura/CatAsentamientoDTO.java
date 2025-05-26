package mx.com.facturacion.factura;

import java.io.Serializable;

import mx.com.consolida.generico.CatGenericoDTO;

public class CatAsentamientoDTO implements Serializable {

	private Integer idCatAsentamiento;
	private CatMunicipioDTO catMunicipio;
	private CatGenericoDTO catTipoAsentamiento;
	private String claveAsentamiento;
	private String nombreAsentamiento;
	
	public CatAsentamientoDTO(){
		this.idCatAsentamiento = 1;
		this.catMunicipio = new CatMunicipioDTO();
		this.catTipoAsentamiento = new CatGenericoDTO(1, "Aentamiento 1", "Asentamiento 1 desc");
		this.claveAsentamiento = "Clave Asentamiento";
		this.nombreAsentamiento = "Nombre asentamiento";
	}
	
	public CatAsentamientoDTO(String demo){
		this.idCatAsentamiento = 1;
		this.catMunicipio = new CatMunicipioDTO();
		this.catTipoAsentamiento = new CatGenericoDTO(1, "Aentamiento 1", "Asentamiento 1 desc");
		this.claveAsentamiento = "Clave Asentamiento";
		this.nombreAsentamiento = "Nombre asentamiento";
	}

	public Integer getIdCatAsentamiento() {
		return idCatAsentamiento;
	}

	public void setIdCatAsentamiento(Integer idCatAsentamiento) {
		this.idCatAsentamiento = idCatAsentamiento;
	}

	public String getClaveAsentamiento() {
		return claveAsentamiento;
	}

	public void setClaveAsentamiento(String claveAsentamiento) {
		this.claveAsentamiento = claveAsentamiento;
	}

	public String getNombreAsentamiento() {
		return nombreAsentamiento;
	}

	public void setNombreAsentamiento(String nombreAsentamiento) {
		this.nombreAsentamiento = nombreAsentamiento;
	}

	public CatMunicipioDTO getCatMunicipio() {
		return catMunicipio;
	}

	public void setCatMunicipio(CatMunicipioDTO catMunicipio) {
		this.catMunicipio = catMunicipio;
	}

	public CatGenericoDTO getCatTipoAsentamiento() {
		return catTipoAsentamiento;
	}

	public void setCatTipoAsentamiento(CatGenericoDTO catTipoAsentamiento) {
		this.catTipoAsentamiento = catTipoAsentamiento;
	}

}
