package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;


public class ClienteCuentaBancariaDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idClienteCuentaBancaria;
	private CatGeneralDto catBanco;
	private Long idCliente;
	private String numeroCuenta;
	private String clabeInterbancaria;
	private Boolean esPrincipal;
	private CatGeneralDto catTipoCuenta;
	private String numeroReferencia;
	private Date fechalAlta;
	
	
	
	public Long getIdClienteCuentaBancaria() {
		return idClienteCuentaBancaria;
	}
	public void setIdClienteCuentaBancaria(Long idClienteCuentaBancaria) {
		this.idClienteCuentaBancaria = idClienteCuentaBancaria;
	}
	public CatGeneralDto getCatBanco() {
		return catBanco;
	}
	public void setCatBanco(CatGeneralDto catBanco) {
		this.catBanco = catBanco;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
	public String getNumeroReferencia() {
		return numeroReferencia;
	}
	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}
	public Date getFechalAlta() {
		return fechalAlta;
	}
	public void setFechalAlta(Date fechalAlta) {
		this.fechalAlta = fechalAlta;
	}
	
	
	
}