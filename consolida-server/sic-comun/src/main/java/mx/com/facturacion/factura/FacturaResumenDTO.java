package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.generico.CatGenericoDTO;

@SuppressWarnings("serial")
public class FacturaResumenDTO implements Serializable {
	private boolean checked;
	private Integer idFactura;
	private BigDecimal montoTotal;
	private Date fechaOperacion;
	private String fechaOperacionFormateada;
	
	private CatGenericoDTO tipoComprobante;
	
	private EmpresaDTO empresa;
	private ClienteDTO cliente;
	
	private String urlPdf;
	private String urlXML;
	
	private CatGenericoDTO serie;
	private FolioDTO folio;
	private CatGenericoDTO estatusFactura;
	
	private BigDecimal totalPagado;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Integer getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
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
	
	public CatGenericoDTO getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(CatGenericoDTO tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public EmpresaDTO getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}
	public ClienteDTO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	public String getUrlPdf() {
		return urlPdf;
	}
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	public String getUrlXML() {
		return urlXML;
	}
	public void setUrlXML(String urlXML) {
		this.urlXML = urlXML;
	}
	public CatGenericoDTO getSerie() {
		return serie;
	}
	public void setSerie(CatGenericoDTO serie) {
		this.serie = serie;
	}
	public FolioDTO getFolio() {
		return folio;
	}
	public void setFolio(FolioDTO folio) {
		this.folio = folio;
	}
	public CatGenericoDTO getEstatusFactura() {
		return estatusFactura;
	}
	public void setEstatusFactura(CatGenericoDTO estatusFactura) {
		this.estatusFactura = estatusFactura;
	}
	public BigDecimal getTotalPagado() {
		return totalPagado;
	}
	public void setTotalPagado(BigDecimal totalPagado) {
		this.totalPagado = totalPagado;
	}
}
