package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ImpuestoDTO implements Serializable {

	private Integer idImpuesto;
	private CatImpuestoDTO tipo;
	private BigDecimal totalImpuesto;
	
	
	public ImpuestoDTO(){
		
	}
	
	public ImpuestoDTO(String demo){
		this.idImpuesto = new Integer(1);
		this.tipo = new CatImpuestoDTO();
		tipo.setClave("002");
		tipo.setDescripcion("IVA Trasladado 16%");
		
		CatPorcentajeDTO porcentaje = new CatPorcentajeDTO();
		porcentaje.setPorcentaje(new BigDecimal("0.160000"));
		tipo.setPorcentajeImpuesto(porcentaje);
		
		this.totalImpuesto = new BigDecimal(1600);
	}
	
	public Integer getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(Integer idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public CatImpuestoDTO getTipo() {
		return tipo;
	}
	public void setTipo(CatImpuestoDTO tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getTotalImpuesto() {
		return totalImpuesto;
	}
	public void setTotalImpuesto(BigDecimal totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
}