package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class DescuentoDTO implements Serializable {
	
	private Integer idDescuento;
	private String motivo;
	private BigDecimal totaldescuento;
	
	public DescuentoDTO(){
		
	}
	
	public DescuentoDTO(String demo){
		this.idDescuento = new Integer(1);
		this.motivo = "Motivo descuento";
		this.totaldescuento = new BigDecimal(100);
	}
	
	public Integer getIdDescuento() {
		return idDescuento;
	}
	public void setIdDescuento(Integer idDescuento) {
		this.idDescuento = idDescuento;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public BigDecimal getTotaldescuento() {
		return totaldescuento;
	}
	public void setTotaldescuento(BigDecimal totaldescuento) {
		this.totaldescuento = totaldescuento;
	}
	
}