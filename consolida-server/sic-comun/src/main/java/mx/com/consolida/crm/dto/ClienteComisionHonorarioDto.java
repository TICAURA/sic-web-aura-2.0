package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;


public class ClienteComisionHonorarioDto  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idNominaClienteComision;

	private NominaClienteDto nominaCliente;

	private CatGeneralDto canalVenta;
	
	private UsuarioDTO usuario;
	
	private String comisionPricing;
	
	private CatGeneralDto formulaPricing;
	
	private String comision;
	
	private CatGeneralDto formulaComision;
	
	private CatGeneralDto esquema;
	
	private CatGeneralDto porcentajeComision;
	
	private Date fechaInicioPago;
	
	private Date fechaFinPago;
	
	
	/// Honorario
	private Long idNominaClienteHonorario;
	private CatGeneralDto formulaPPP;
	private CatGeneralDto formulaTipoIva;
	private CatGeneralDto formulaFactura;
	private String honorarioPPP;
	private String ivaComercial;
	

	private String honorarioPPPSs;
	private String honorarioPPPMixto;
	private CatGeneralDto formulaPPPSs;
	private CatGeneralDto formulaPPPMixto;
	private CatGeneralDto formulaPPPMaquila;
	
	public ClienteComisionHonorarioDto() {
		
	}


	public Long getIdNominaClienteComision() {
		return idNominaClienteComision;
	}

	public void setIdNominaClienteComision(Long idNominaClienteComision) {
		this.idNominaClienteComision = idNominaClienteComision;
	}

	public NominaClienteDto getNominaCliente() {
		return nominaCliente;
	}

	public void setNominaCliente(NominaClienteDto nominaCliente) {
		this.nominaCliente = nominaCliente;
	}

	public CatGeneralDto getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(CatGeneralDto canalVenta) {
		this.canalVenta = canalVenta;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public CatGeneralDto getFormulaPricing() {
		return formulaPricing;
	}

	public void setFormulaPricing(CatGeneralDto formulaPricing) {
		this.formulaPricing = formulaPricing;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public CatGeneralDto getFormulaComision() {
		return formulaComision;
	}

	public void setFormulaComision(CatGeneralDto formulaComision) {
		this.formulaComision = formulaComision;
	}

	public String getComisionPricing() {
		return comisionPricing;
	}

	public void setComisionPricing(String comisionPricing) {
		this.comisionPricing = comisionPricing;
	}

	public CatGeneralDto getEsquema() {
		return esquema;
	}

	public void setEsquema(CatGeneralDto esquema) {
		this.esquema = esquema;
	}

	public Long getIdNominaClienteHonorario() {
		return idNominaClienteHonorario;
	}

	public void setIdNominaClienteHonorario(Long idNominaClienteHonorario) {
		this.idNominaClienteHonorario = idNominaClienteHonorario;
	}

	public CatGeneralDto getFormulaPPP() {
		return formulaPPP;
	}

	public void setFormulaPPP(CatGeneralDto formulaPPP) {
		this.formulaPPP = formulaPPP;
	}


	public String getHonorarioPPP() {
		return honorarioPPP;
	}

	public void setHonorarioPPP(String honorarioPPP) {
		this.honorarioPPP = honorarioPPP;
	}

	public CatGeneralDto getFormulaTipoIva() {
		return formulaTipoIva;
	}

	public void setFormulaTipoIva(CatGeneralDto formulaTipoIva) {
		this.formulaTipoIva = formulaTipoIva;
	}

	public CatGeneralDto getFormulaFactura() {
		return formulaFactura;
	}

	public void setFormulaFactura(CatGeneralDto formulaFactura) {
		this.formulaFactura = formulaFactura;
	}

	
	public Date getFechaInicioPago() {
		return fechaInicioPago;
	}

	public void setFechaInicioPago(Date fechaInicioPago) {
		this.fechaInicioPago = fechaInicioPago;
	}

	public Date getFechaFinPago() {
		return fechaFinPago;
	}

	public void setFechaFinPago(Date fechaFinPago) {
		this.fechaFinPago = fechaFinPago;
	}


	public CatGeneralDto getPorcentajeComision() {
		return porcentajeComision;
	}


	public void setPorcentajeComision(CatGeneralDto porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}


	public String getIvaComercial() {
		return ivaComercial;
	}


	public void setIvaComercial(String ivaComercial) {
		this.ivaComercial = ivaComercial;
	}


	public String getHonorarioPPPSs() {
		return honorarioPPPSs;
	}


	public void setHonorarioPPPSs(String honorarioPPPSs) {
		this.honorarioPPPSs = honorarioPPPSs;
	}


	public String getHonorarioPPPMixto() {
		return honorarioPPPMixto;
	}


	public void setHonorarioPPPMixto(String honorarioPPPMixto) {
		this.honorarioPPPMixto = honorarioPPPMixto;
	}


	public CatGeneralDto getFormulaPPPSs() {
		return formulaPPPSs;
	}

	public void setFormulaPPPSs(CatGeneralDto formulaPPPSs) {
		this.formulaPPPSs = formulaPPPSs;
	}

	public CatGeneralDto getFormulaPPPMixto() {
		return formulaPPPMixto;
	}

	public void setFormulaPPPMixto(CatGeneralDto formulaPPPMixto) {
		this.formulaPPPMixto = formulaPPPMixto;
	}

	public CatGeneralDto getFormulaPPPMaquila() {
		return formulaPPPMaquila;
	}

	public void setFormulaPPPMaquila(CatGeneralDto formulaPPPMaquila) {
		this.formulaPPPMaquila = formulaPPPMaquila;
	}
	
}
