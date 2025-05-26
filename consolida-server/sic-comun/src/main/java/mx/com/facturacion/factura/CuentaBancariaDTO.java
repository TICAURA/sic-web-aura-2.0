package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;

import mx.com.consolida.generico.CatGenericoDTO;

public class CuentaBancariaDTO implements Serializable {

	private Integer idCuentaBancaria;
	private String numeroCuenta;
	private String terminacionCuentaBancaria;
	private String numeroCheque;
	private String nombre;
	private String sucursal;
	private String clabeInterbancaria;
	private BigDecimal saldoInicial;
	private EmpresaDTO empresaDto;
	private CatGenericoDTO catMoneda;
	private CatGenericoDTO catBanco;
	private String completo;
	private String bancoPlus;
	private String fechaInicio;
	private String fechaFin;
	private boolean esCuentaPrincipal;
	
	public CuentaBancariaDTO(){ }
	
	public String getTerminacionCuentaBancaria() {
		return terminacionCuentaBancaria;
	}

	public void setTerminacionCuentaBancaria(String terminacionCuentaBancaria) {
		this.terminacionCuentaBancaria = terminacionCuentaBancaria;
	}

	public CatGenericoDTO getCatBanco() {
		return catBanco;
	}

	public void setCatBanco(CatGenericoDTO catBanco) {
		this.catBanco = catBanco;
	}

	public Integer getIdCuentaBancaria() {
		return idCuentaBancaria;
	}

	public void setIdCuentaBancaria(Integer idCuentaBancaria) {
		this.idCuentaBancaria = idCuentaBancaria;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}

	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public EmpresaDTO getEmpresaDto() {
		return empresaDto;
	}

	public void setEmpresaDto(EmpresaDTO empresaDto) {
		this.empresaDto = empresaDto;
	}

	public CatGenericoDTO getCatMoneda() {
		return catMoneda;
	}

	public void setCatMoneda(CatGenericoDTO catMoneda) {
		this.catMoneda = catMoneda;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getBancoPlus() {
		return bancoPlus;
	}

	public void setBancoPlus(String bancoPlus) {
		this.bancoPlus = bancoPlus;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isEsCuentaPrincipal() {
		return esCuentaPrincipal;
	}

	public void setEsCuentaPrincipal(boolean esCuentaPrincipal) {
		this.esCuentaPrincipal = esCuentaPrincipal;
	}
	
	
	
}