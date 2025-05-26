package mx.com.consolida.dto;

public class CatSubGiroComercialDto {

	private Long idCatSubGiroComercial;
	private Long idGiroComercial;
	private String descripcion;
	
	public CatSubGiroComercialDto() {
		
	}
	
	public CatSubGiroComercialDto (Long idCatSubGiroComercial) {
		this.idCatSubGiroComercial = idCatSubGiroComercial;
	}
	
	public CatSubGiroComercialDto (Long idCatSubGiroComercial, String descripcion) {
		this.idCatSubGiroComercial = idCatSubGiroComercial;
		this.descripcion = descripcion;
	}
	
	public Long getIdCatSubGiroComercial() {
		return idCatSubGiroComercial;
	}
	public void setIdCatSubGiroComercial(Long idCatSubGiroComercial) {
		this.idCatSubGiroComercial = idCatSubGiroComercial;
	}
	public Long getIdGiroComercial() {
		return idGiroComercial;
	}
	public void setIdGiroComercial(Long idGiroComercial) {
		this.idGiroComercial = idGiroComercial;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
