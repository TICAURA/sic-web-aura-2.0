package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BancoHistoricoDTO implements Serializable {

	private Date fechaOperacion;
	private Date fechaPago;
	private String fechaOperacionFormateada;
	private String empresa;
	private String cliente;
	private BigDecimal importePagado;
	private String tipoComprobante;
	private BigDecimal monto;
	private String cuentaBancaria;
	private String serie;
	private String folio;
	
	public BancoHistoricoDTO(){}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getFechaOperacionFormateada() {
		return fechaOperacionFormateada;
	}

	public void setFechaOperacionFormateada(String fechaOperacionFormateada) {
		this.fechaOperacionFormateada = fechaOperacionFormateada;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getImportePagado() {
		return importePagado;
	}

	public void setImportePagado(BigDecimal importePagado) {
		this.importePagado = importePagado;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

}
