package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.generico.CatGenericoDTO;

public class FacturaPagoDTO implements Serializable {

	private Integer idFacturaPago;
	private Integer idFactura;
	private BigDecimal monto;
	private BigDecimal totalFactura;
	private Date fechaOperacion;
	private String fechaOperacionFormateada;
	private String fechaOperacionFormato;
	private CatGenericoDTO catTipoFactura;
	private CatGenericoDTO catEstatusfactura;
	private CuentaBancariaDTO cuentaBancaria;
	private EmpresaDTO empresaDTO;
	private CatGenericoDTO serie;
	private FolioDTO folio;
	private ClienteDTO cliente;
	private CatGenericoDTO catTipoComprobante;
	private BigDecimal montoRestantePorPagar;
	private BigDecimal importePagado;
	private Integer idCuentaBancaria;
	private String descripcion;
	private String fechaFacturaFormateada;
	private String fechaPago;
	private BigDecimal montoPagado;
	private boolean checked;
	private String uuid;
	private String pass;
	

	public FacturaPagoDTO() {

	}

	public Integer getIdFacturaPago() {
		return idFacturaPago;
	}

	public void setIdFacturaPago(Integer idFacturaPago) {
		this.idFacturaPago = idFacturaPago;
	}

	public CatGenericoDTO getCatTipoFactura() {
		return catTipoFactura;
	}

	public void setCatTipoFactura(CatGenericoDTO catTipoFactura) {
		this.catTipoFactura = catTipoFactura;
	}

	public CatGenericoDTO getCatEstatusfactura() {
		return catEstatusfactura;
	}

	public void setCatEstatusfactura(CatGenericoDTO catEstatusfactura) {
		this.catEstatusfactura = catEstatusfactura;
	}

	public CuentaBancariaDTO getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancariaDTO cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getFechaOperacionFormato() {
		return fechaOperacionFormato;
	}

	public void setFechaOperacionFormato(String fechaOperacionFormato) {
		this.fechaOperacionFormato = fechaOperacionFormato;
	}

	public EmpresaDTO getEmpresaDTO() {
		return empresaDTO;
	}

	public void setEmpresaDTO(EmpresaDTO empresaDTO) {
		this.empresaDTO = empresaDTO;
	}

	public String getFechaOperacionFormateada() {
		return fechaOperacionFormateada;
	}

	public void setFechaOperacionFormateada(String fechaOperacionFormateada) {
		this.fechaOperacionFormateada = fechaOperacionFormateada;
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

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public CatGenericoDTO getCatTipoComprobante() {
		return catTipoComprobante;
	}

	public void setCatTipoComprobante(CatGenericoDTO catTipoComprobante) {
		this.catTipoComprobante = catTipoComprobante;
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public BigDecimal getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(BigDecimal totalFactura) {
		this.totalFactura = totalFactura;
	}

	public BigDecimal getMontoRestantePorPagar() {
		return montoRestantePorPagar;
	}

	public void setMontoRestantePorPagar(BigDecimal montoRestantePorPagar) {
		this.montoRestantePorPagar = montoRestantePorPagar;
	}

	public BigDecimal getImportePagado() {
		return importePagado;
	}

	public void setImportePagado(BigDecimal importePagado) {
		this.importePagado = importePagado;
	}

	public Integer getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	public void setIdCuentaBancaria(Integer idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaFacturaFormateada() {
		return fechaFacturaFormateada;
	}

	public void setFechaFacturaFormateada(String fechaFacturaFormateada) {
		this.fechaFacturaFormateada = fechaFacturaFormateada;
	}

	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}