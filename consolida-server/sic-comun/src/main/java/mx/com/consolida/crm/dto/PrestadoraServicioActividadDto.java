package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;

public class PrestadoraServicioActividadDto implements Serializable {

	private static final long serialVersionUID = 1L;
	// Guardado
	private Long idPrestadoraGiroComercial;
	private PrestadoraServicioDto prestadoraDto;
	private Long idGiroComercial;
	private Long idSubGiroComercial;
	// Carga de catalogos
	private List<CatSubGiroComercialDto> catSubGiroComercial;
	private List<CatGeneralDto> girosComerciales;
	// Recuperado
	private List<PrestadoraServicioActividadDto> listPrestadorActividad;
	private CatSubGiroComercialDto subgiroComercial;
	private CatGeneralDto catGiroComercial;


	public Long getIdPrestadoraGiroComercial() {
		return idPrestadoraGiroComercial;
	}

	public void setIdPrestadoraGiroComercial(Long idPrestadoraGiroComercial) {
		this.idPrestadoraGiroComercial = idPrestadoraGiroComercial;
	}

	public PrestadoraServicioDto getPrestadoraDto() {
		return prestadoraDto;
	}

	public void setPrestadoraDto(PrestadoraServicioDto prestadoraDto) {
		this.prestadoraDto = prestadoraDto;
	}

	public Long getIdGiroComercial() {
		return idGiroComercial;
	}

	public void setIdGiroComercial(Long idGiroComercial) {
		this.idGiroComercial = idGiroComercial;
	}

	public Long getIdSubGiroComercial() {
		return idSubGiroComercial;
	}

	public void setIdSubGiroComercial(Long idSubGiroComercial) {
		this.idSubGiroComercial = idSubGiroComercial;
	}

	public List<CatSubGiroComercialDto> getCatSubGiroComercial() {
		return catSubGiroComercial;
	}

	public void setCatSubGiroComercial(List<CatSubGiroComercialDto> catSubGiroComercial) {
		this.catSubGiroComercial = catSubGiroComercial;
	}

	public List<CatGeneralDto> getGirosComerciales() {
		return girosComerciales;
	}

	public void setGirosComerciales(List<CatGeneralDto> girosComerciales) {
		this.girosComerciales = girosComerciales;
	}

	public List<PrestadoraServicioActividadDto> getListPrestadorActividad() {
		return listPrestadorActividad;
	}

	public void setListPrestadorActividad(List<PrestadoraServicioActividadDto> listPrestadorActividad) {
		this.listPrestadorActividad = listPrestadorActividad;
	}

	public CatSubGiroComercialDto getSubgiroComercial() {
		return subgiroComercial;
	}

	public void setSubgiroComercial(CatSubGiroComercialDto subgiroComercial) {
		this.subgiroComercial = subgiroComercial;
	}

	public CatGeneralDto getCatGiroComercial() {
		return catGiroComercial;
	}

	public void setCatGiroComercial(CatGeneralDto catGiroComercial) {
		this.catGiroComercial = catGiroComercial;
	}

}
