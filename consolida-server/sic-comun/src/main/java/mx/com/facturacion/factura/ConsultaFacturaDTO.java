package mx.com.facturacion.factura;

import java.io.Serializable;

public class ConsultaFacturaDTO implements Serializable {

	private Integer idempresa;
	private String nombreEmpresa;
	private Integer idCliente;
	private String nombreCliente;
	private String estatusFactura;

	public Integer getIdempresa() {
		return idempresa;
	}

	public void setIdempresa(Integer idempresa) {
		this.idempresa = idempresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getEstatusFactura() {
		return estatusFactura;
	}

	public void setEstatusFactura(String estatusFactura) {
		this.estatusFactura = estatusFactura;
	}
	
	

}
