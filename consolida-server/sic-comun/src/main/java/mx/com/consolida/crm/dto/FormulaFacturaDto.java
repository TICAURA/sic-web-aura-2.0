package mx.com.consolida.crm.dto;

import mx.com.consolida.catalogos.CatGeneralDto;

public class FormulaFacturaDto {
	
	private Long idNominaCliente;
	private String nombreNomina;
	
	private String claveNomina;
	private ClienteDto clienteDto;
	private CatGeneralDto catProductoNomina;
	private String descripcionCompuesta;
	
	/// Honorario PPP
	private Long idNominaClienteHonorario;
	private CatGeneralDto formulaPPP;
	private CatGeneralDto formulaTipoIva;
	private CatGeneralDto formulaFactura;
	private String honorarioPPP;
	private String ivaComercial;
	
	public Long getIdNominaCliente() {
		return idNominaCliente;
	}
	public void setIdNominaCliente(Long idNominaCliente) {
		this.idNominaCliente = idNominaCliente;
	}
	public String getNombreNomina() {
		return nombreNomina;
	}
	public void setNombreNomina(String nombreNomina) {
		this.nombreNomina = nombreNomina;
	}
	public String getClaveNomina() {
		return claveNomina;
	}
	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
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
	public String getHonorarioPPP() {
		return honorarioPPP;
	}
	public void setHonorarioPPP(String honorarioPPP) {
		this.honorarioPPP = honorarioPPP;
	}
	public ClienteDto getClienteDto() {
		return clienteDto;
	}
	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}
	public CatGeneralDto getCatProductoNomina() {
		return catProductoNomina;
	}
	public void setCatProductoNomina(CatGeneralDto catProductoNomina) {
		this.catProductoNomina = catProductoNomina;
	}
	public String getDescripcionCompuesta() {
		return descripcionCompuesta;
	}
	public void setDescripcionCompuesta(String descripcionCompuesta) {
		this.descripcionCompuesta = descripcionCompuesta;
	}
	public String getIvaComercial() {
		return ivaComercial;
	}
	public void setIvaComercial(String ivaComercial) {
		this.ivaComercial = ivaComercial;
	}
	
	
}
