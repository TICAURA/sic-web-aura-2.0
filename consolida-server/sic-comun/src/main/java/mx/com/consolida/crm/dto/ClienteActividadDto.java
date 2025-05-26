package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;

public class ClienteActividadDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Guardado
	private Long idClienteGiroComercial;
	private ClienteDto cliente;
	private Long idGiroComercial;
	private Long idSubGiroComercial;
	//Carga de catalogos
    private List<CatSubGiroComercialDto> catSubGiroComercial;
    private List<CatGeneralDto> girosComerciales;
    //Recuperado
    private List<ClienteActividadDto> listClienteActividad;
    private CatSubGiroComercialDto subgiroComercial;
    private CatGeneralDto catGiroComercial;
    
    
    
	public Long getIdClienteGiroComercial() {
		return idClienteGiroComercial;
	}
	public void setIdClienteGiroComercial(Long idClienteGiroComercial) {
		this.idClienteGiroComercial = idClienteGiroComercial;
	}
	public ClienteDto getCliente() {
		return cliente;
	}
	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
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
	public List<ClienteActividadDto> getListClienteActividad() {
		return listClienteActividad;
	}
	public void setListClienteActividad(List<ClienteActividadDto> listClienteActividad) {
		this.listClienteActividad = listClienteActividad;
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
