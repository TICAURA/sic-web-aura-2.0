package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TotalDTO implements Serializable{

	private BigDecimal importe;
	private BigDecimal sumaImpuestos;
	private BigDecimal subtotal;
	private BigDecimal sumaRetenciones;
	private BigDecimal sumaDescuentos;
	private BigDecimal total;
	private BigDecimal ivaComercial;
	private BigDecimal honorario;
	private BigDecimal montoTotalHonorario;
	private BigDecimal montoTotalColaboradoresPPP;
	
	public TotalDTO(){
		
	}
	
	public TotalDTO(String demo){
		
		this.subtotal = new BigDecimal(10000);
		this.sumaImpuestos = new BigDecimal("1600");
		this.sumaRetenciones =  new BigDecimal("600");
		
		this.sumaDescuentos = new BigDecimal(0);
		this.total = new BigDecimal(11000);
	}
	
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public BigDecimal getSumaImpuestos() {
		return sumaImpuestos;
	}
	public void setSumaImpuestos(BigDecimal sumaImpuestos) {
		this.sumaImpuestos = sumaImpuestos;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getSumaRetenciones() {
		return sumaRetenciones;
	}
	public void setSumaRetenciones(BigDecimal sumaRetenciones) {
		this.sumaRetenciones = sumaRetenciones;
	}
	public BigDecimal getSumaDescuentos() {
		return sumaDescuentos;
	}
	public void setSumaDescuentos(BigDecimal sumaDescuentos) {
		this.sumaDescuentos = sumaDescuentos;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getIvaComercial() {
		return ivaComercial;
	}

	public void setIvaComercial(BigDecimal ivaComercial) {
		this.ivaComercial = ivaComercial;
	}

	public BigDecimal getHonorario() {
		return honorario;
	}

	public void setHonorario(BigDecimal honorario) {
		this.honorario = honorario;
	}

	public BigDecimal getMontoTotalHonorario() {
		return montoTotalHonorario;
	}

	public void setMontoTotalHonorario(BigDecimal montoTotalHonorario) {
		this.montoTotalHonorario = montoTotalHonorario;
	}

	public BigDecimal getMontoTotalColaboradoresPPP() {
		return montoTotalColaboradoresPPP;
	}

	public void setMontoTotalColaboradoresPPP(BigDecimal montoTotalColaboradoresPPP) {
		this.montoTotalColaboradoresPPP = montoTotalColaboradoresPPP;
	}
	
	
	
	
}
