package mx.com.consolida.dto;

import java.math.BigDecimal;

import mx.com.consolida.catalogos.CatGeneralDto;

public class RentabilidadDto {

	private String periodicidad;
	private CatGeneralDto tipoProducto;
	private Long numColaboradores;
	private double dispersion;
	private double cargaSocial;
	private double sgmmImplant;
	private double comisionServicios;
	private double costoAdicionalesCliente;
	private double subtotalFactura;
	private double iva;
	private double totalFactura;
	private double pircing;
	private double comisionEstimadaPromotor;
	private double costoAdicionalesPromotor;
	private double costosDirectos;
	private double sgmm;
	private double timbrado;
	private double spei;
	private double costoEstrategia;
	private double implant;
	private double costosIndirectos;
	private double utilidades;
	private double rentabilidad;
	
	public RentabilidadDto() {
		super();
	}
	
	public RentabilidadDto(boolean inicializa_completo) {
		super();
		this.periodicidad = "";
		this.tipoProducto = new CatGeneralDto();
		this.numColaboradores = 0L;
		this.dispersion = 0;
		this.cargaSocial = 0;
		this.comisionServicios = 0;
		this.subtotalFactura = 0;
		this.iva = 0;
		this.totalFactura = 0;
		this.pircing = 0;
		this.comisionEstimadaPromotor = 0;
		this.costosDirectos = 0;
		this.sgmm = 0;
		this.timbrado = 0;
		this.spei = 0;
		this.costoEstrategia = 0;
		this.implant = 0;
		this.costosIndirectos = 0;
		this.utilidades = 0;
		this.rentabilidad = 0;
		/*
		this.dispersion = new BigDecimal(0);
		this.cargaSocial = new BigDecimal(0);
		this.comisionServicios = new BigDecimal(0);
		this.subtotalFactura = new BigDecimal(0);
		this.iva = new BigDecimal(0);
		this.totalFactura = new BigDecimal(0);
		this.pircing = new BigDecimal(0);
		this.comisionEstimadaPromotor = new BigDecimal(0);
		this.costosDirectos = new BigDecimal(0);
		this.sgmm = new BigDecimal(0);
		this.timbrado = new BigDecimal(0);
		this.spei = new BigDecimal(0);
		this.costoEstrategia = new BigDecimal(0);
		this.implant = new BigDecimal(0);
		this.costosIndirectos = new BigDecimal(0);
		this.utilidades = new BigDecimal(0);
		this.rentabilidad = new BigDecimal(0);
		*/
	}

	public String getPeriodicidad() {
		return periodicidad;
	}
	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}
	public CatGeneralDto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(CatGeneralDto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public Long getNumColaboradores() {
		return numColaboradores;
	}
	public void setNumColaboradores(Long numColaboradores) {
		this.numColaboradores = numColaboradores;
	}
	public double getDispersion() {
		return dispersion;
	}
	public void setDispersion(double dispersion) {
		this.dispersion = dispersion;
	}
	public double getCargaSocial() {
		return cargaSocial;
	}
	public void setCargaSocial(double cargaSocial) {
		this.cargaSocial = cargaSocial;
	}
	public double getComisionServicios() {
		return comisionServicios;
	}
	public void setComisionServicios(double comisionServicios) {
		this.comisionServicios = comisionServicios;
	}
	public double getSubtotalFactura() {
		return subtotalFactura;
	}
	public void setSubtotalFactura(double subtotalFactura) {
		this.subtotalFactura = subtotalFactura;
	}
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	public double getTotalFactura() {
		return totalFactura;
	}
	public void setTotalFactura(double totalFactura) {
		this.totalFactura = totalFactura;
	}
	public double getPircing() {
		return pircing;
	}
	public void setPircing(double pircing) {
		this.pircing = pircing;
	}
	public double getComisionEstimadaPromotor() {
		return comisionEstimadaPromotor;
	}
	public void setComisionEstimadaPromotor(double comisionEstimadaPromotor) {
		this.comisionEstimadaPromotor = comisionEstimadaPromotor;
	}
	public double getCostosDirectos() {
		return costosDirectos;
	}
	public void setCostosDirectos(double costosDirectos) {
		this.costosDirectos = costosDirectos;
	}
	public double getSgmm() {
		return sgmm;
	}
	public void setSgmm(double sgmm) {
		this.sgmm = sgmm;
	}
	public double getTimbrado() {
		return timbrado;
	}
	public void setTimbrado(double timbrado) {
		this.timbrado = timbrado;
	}
	public double getSpei() {
		return spei;
	}
	public void setSpei(double spei) {
		this.spei = spei;
	}
	public double getCostoEstrategia() {
		return costoEstrategia;
	}
	public void setCostoEstrategia(double costoEstrategia) {
		this.costoEstrategia = costoEstrategia;
	}
	public double getImplant() {
		return implant;
	}
	public void setImplant(double implant) {
		this.implant = implant;
	}
	public double getCostosIndirectos() {
		return costosIndirectos;
	}
	public void setCostosIndirectos(double costosIndirectos) {
		this.costosIndirectos = costosIndirectos;
	}
	public double getUtilidades() {
		return utilidades;
	}
	public void setUtilidades(double utilidades) {
		this.utilidades = utilidades;
	}
	public double getRentabilidad() {
		return rentabilidad;
	}
	public void setRentabilidad(double rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public double getSgmmImplant() {
		return sgmmImplant;
	}

	public void setSgmmImplant(double sgmmImplant) {
		this.sgmmImplant = sgmmImplant;
	}

	public double getCostoAdicionalesCliente() {
		return costoAdicionalesCliente;
	}

	public void setCostoAdicionalesCliente(double costoAdicionalesCliente) {
		this.costoAdicionalesCliente = costoAdicionalesCliente;
	}

	public double getCostoAdicionalesPromotor() {
		return costoAdicionalesPromotor;
	}

	public void setCostoAdicionalesPromotor(double costoAdicionalesPromotor) {
		this.costoAdicionalesPromotor = costoAdicionalesPromotor;
	}

    
}
