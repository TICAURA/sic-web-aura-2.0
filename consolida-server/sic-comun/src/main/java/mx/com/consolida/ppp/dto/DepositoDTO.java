package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;


public class DepositoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDeposito;
	
	private BigDecimal montoDeposito;
	
	private BigDecimal montoDepositoMenosDiez;
	
	private Date fecha;
	
	private Boolean aSeleccionar =false;
	
	public Boolean getaSeleccionar() {
		return aSeleccionar;
	}

	public void setaSeleccionar(Boolean aSeleccionar) {
		this.aSeleccionar = aSeleccionar;
	}

	public BigDecimal getMontoDepositoMenosDiez() {
		return montoDepositoMenosDiez;
	}

	public void setMontoDepositoMenosDiez(BigDecimal montoDepositoMenosDiez) {
		this.montoDepositoMenosDiez = montoDepositoMenosDiez;
	}

	private String concepto;
	
	public DepositoDTO() {
		
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public BigDecimal getMontoDeposito() {
		return montoDeposito;
	}

	public void setMontoDeposito(BigDecimal montoDeposito) {
		this.montoDeposito = montoDeposito;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}


	
	
	
	
}
