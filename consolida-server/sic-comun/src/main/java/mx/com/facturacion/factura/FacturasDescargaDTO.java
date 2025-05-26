package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class FacturasDescargaDTO implements Serializable{
	
	private List<FacturaPagoDTO> facturas;
	
	//Tipo descarga XML , PDF , AMBOS
	private String tipoDescarga;

	public List<FacturaPagoDTO> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaPagoDTO> facturas) {
		this.facturas = facturas;
	}

	public String getTipoDescarga() {
		return tipoDescarga;
	}

	public void setTipoDescarga(String tipoDescarga) {
		this.tipoDescarga = tipoDescarga;
	}
}