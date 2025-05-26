package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.com.consolida.generico.CatGenericoDTO;

public class DomicilioDTO implements Serializable {

	private static final long serialVersionUID = -1913469152840919400L;

	private Integer idDomicilio;
	private String codigoPostal;
	private String localidad;
	private String calle;
	private String numExterior;
	private String numInterior;
	private String referencia;
	private String colonia;
	private CatMunicipioDTO catMunicipio;
	private CatGenericoDTO catEstadoPais;
	private CatGenericoDTO catPais;
	private CatAsentamientoDTO catAsentamiento;
	private List<CatGenericoDTO> listCatTipoAsentamiento;
	private String completo;

	public DomicilioDTO() {
		
	}
	public DomicilioDTO(String demo) {
		this.idDomicilio = 1;
		this.codigoPostal = "01020";
		this.localidad = "Localidad";
		this.calle = "Insurgentes Sur";
		this.numExterior = "1677";
		this.numInterior = "1006";
		this.referencia = "Referencia";
		this.colonia ="loma bonita";
		this.catMunicipio = new CatMunicipioDTO();
		this.catEstadoPais = new CatGenericoDTO(1, "CDMX", "Ciudad de México");
		this.catPais = new CatGenericoDTO(1, "MX", "México");
		this.catAsentamiento = new CatAsentamientoDTO();
		this.listCatTipoAsentamiento = new ArrayList<CatGenericoDTO>();
		this.listCatTipoAsentamiento.add(new CatGenericoDTO(1, "Clave list cat tipo asentamiento", "Desc list cat tipo asentamiento"));
		this.completo = "Completo, a lo mejor es la dirección";
	}

	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	

	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public CatGenericoDTO getCatEstadoPais() {
		return catEstadoPais;
	}

	public void setCatEstadoPais(CatGenericoDTO catEstadoPais) {
		this.catEstadoPais = catEstadoPais;
	}

	public CatGenericoDTO getCatPais() {
		return catPais;
	}

	public void setCatPais(CatGenericoDTO catPais) {
		this.catPais = catPais;
	}

	public CatMunicipioDTO getCatMunicipio() {
		return catMunicipio;
	}

	public void setCatMunicipio(CatMunicipioDTO catMunicipio) {
		this.catMunicipio = catMunicipio;
	}

	public List<CatGenericoDTO> getListCatTipoAsentamiento() {
		return listCatTipoAsentamiento;
	}

	public void setListCatTipoAsentamiento(
			List<CatGenericoDTO> listCatTipoAsentamiento) {
		this.listCatTipoAsentamiento = listCatTipoAsentamiento;
	}

	public CatAsentamientoDTO getCatAsentamiento() {
		return catAsentamiento;
	}

	public void setCatAsentamiento(CatAsentamientoDTO catAsentamiento) {
		this.catAsentamiento = catAsentamiento;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	

}
