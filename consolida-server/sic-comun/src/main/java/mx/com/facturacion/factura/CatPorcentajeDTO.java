package mx.com.facturacion.factura;

import java.math.BigDecimal;

public class CatPorcentajeDTO {
	
	private Integer id;
	private String clave;
	private String descripcion;
	private BigDecimal porcentaje;
	
	public CatPorcentajeDTO (){
		
	}
	public CatPorcentajeDTO (String demo){
		this.id = new Integer(1);
		this.clave = "Porcentaje clave";
		this.descripcion = "Desc porcentaje";
		this.porcentaje = new BigDecimal(16);
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
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}
