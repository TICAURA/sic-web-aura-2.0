package mx.com.consolida.crm.dto;

import java.io.Serializable;

import mx.com.consolida.catalogos.CatGeneralDto;


public class PrestadoraServicioCuentaBancariaDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioCuentaBancaria;
	private CatGeneralDto catBanco;
	private Long idPrestadoraServicio;
	private String numeroCuenta;
	private String clabeInterbancaria;
	private Boolean esPrincipal;
	private CatGeneralDto catTipoCuenta;
	private String numeroReferencia;
	
	
	public String getNumeroReferencia() {
		return numeroReferencia;
	}
	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}
	public Long getIdPrestadoraServicioCuentaBancaria() {
		return idPrestadoraServicioCuentaBancaria;
	}
	public void setIdPrestadoraServicioCuentaBancaria(Long idPrestadoraServicioCuentaBancaria) {
		this.idPrestadoraServicioCuentaBancaria = idPrestadoraServicioCuentaBancaria;
	}
	public CatGeneralDto getCatBanco() {
		return catBanco;
	}
	public void setCatBanco(CatGeneralDto catBanco) {
		this.catBanco = catBanco;
	}
	
	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}
	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}
	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
	}
	public Boolean getEsPrincipal() {
		return esPrincipal;
	}
	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
	public CatGeneralDto getCatTipoCuenta() {
		return catTipoCuenta;
	}
	public void setCatTipoCuenta(CatGeneralDto catTipoCuenta) {
		this.catTipoCuenta = catTipoCuenta;
	}
	
}