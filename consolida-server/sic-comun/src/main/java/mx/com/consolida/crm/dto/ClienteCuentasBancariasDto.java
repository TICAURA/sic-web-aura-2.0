package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;


public class ClienteCuentasBancariasDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CatGeneralDto> catBanco;
	private List<CatGeneralDto> catTipoCuenta;
	 private List<ClienteCuentaBancariaDto> clienteCuentasBancarias;
	 
	 
	public List<CatGeneralDto> getCatBanco() {
		return catBanco;
	}
	public void setCatBanco(List<CatGeneralDto> catBanco) {
		this.catBanco = catBanco;
	}
	public List<CatGeneralDto> getCatTipoCuenta() {
		return catTipoCuenta;
	}
	public void setCatTipoCuenta(List<CatGeneralDto> catTipoCuenta) {
		this.catTipoCuenta = catTipoCuenta;
	}
	public List<ClienteCuentaBancariaDto> getClienteCuentasBancarias() {
		return clienteCuentasBancarias;
	}
	public void setClienteCuentasBancarias(List<ClienteCuentaBancariaDto> clienteCuentasBancarias) {
		this.clienteCuentasBancarias = clienteCuentasBancarias;
	}	
}