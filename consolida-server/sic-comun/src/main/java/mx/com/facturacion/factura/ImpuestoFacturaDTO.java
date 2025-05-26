package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class ImpuestoFacturaDTO implements Serializable {

	private Integer idImpuesto;
	private String impuesto;
	private BigDecimal monto; 
	
	
	public ImpuestoFacturaDTO(){
		
	}
	
	
	public ImpuestoFacturaDTO(Integer idImpuesto, String impuesto, BigDecimal monto) {
		super();
		this.idImpuesto = idImpuesto;
		this.impuesto = impuesto;
		this.monto = monto;
	}


	public Integer getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(Integer idImpuesto) {
		this.idImpuesto = idImpuesto;
	}

	public String getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
}