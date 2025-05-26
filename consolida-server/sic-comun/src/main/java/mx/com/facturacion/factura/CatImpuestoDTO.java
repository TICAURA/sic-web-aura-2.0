package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CatImpuestoDTO implements Serializable{
	
	private Integer id;
	private String clave;
	private String descripcion;
	private CatPorcentajeDTO porcentajeImpuesto;
	
	public CatImpuestoDTO(){
		
	}
	
	public CatImpuestoDTO(String demo){
		this.id = new Integer(1);
		this.clave = "002";
		this.descripcion = "IVA desc";
		this.porcentajeImpuesto = new CatPorcentajeDTO();
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

	public CatPorcentajeDTO getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public void setPorcentajeImpuesto(CatPorcentajeDTO porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}
	
}