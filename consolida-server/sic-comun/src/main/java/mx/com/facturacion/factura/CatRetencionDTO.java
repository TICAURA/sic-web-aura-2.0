package mx.com.facturacion.factura;

public class CatRetencionDTO {
	
	private Integer id;
	private String clave;
	private String descripcion;
	private CatPorcentajeDTO porcentajeRetencion;
	
	public CatRetencionDTO(){
		
	}
	
	public CatRetencionDTO(String demo){
		this.id = new Integer(1);
		this.clave = "Clave retención";
		this.descripcion = "Desc retención";
		this.porcentajeRetencion = new CatPorcentajeDTO();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CatPorcentajeDTO getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	public void setPorcentajeRetencion(CatPorcentajeDTO porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}
	
}
