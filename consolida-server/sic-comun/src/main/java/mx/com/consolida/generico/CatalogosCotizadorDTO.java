package mx.com.consolida.generico;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.dto.CatValorDefaultDto;

@SuppressWarnings("serial")
public class CatalogosCotizadorDTO implements Serializable {
	
	private List<CatGeneralDto> catVacaciones;
	private List<CatGeneralDto> catZona;
	private List<CatGeneralDto> catTipoNomina;
	private List<CatGeneralDto> catDias;
	private List<CatGeneralDto> catTipoImss;
	private List<CatGeneralDto> catTipoPrestaciones;
	private List<CatGeneralDto> catCosteoAsimilable;
	private List<CatGeneralDto> catTipoCotizacion;
	private List<CatGeneralDto> catEstatus;
	private List<CatGeneralDto> catTipoPersona;
	private List<CatGeneralDto> catProveedor;
	private List<CatTipoPagoDto> catTipoPeriodicidad;
	private List<CatGeneralDto> catTipoEsquema;
	
	private CatValorDefaultDto catPorcPromotor;
	private CatValorDefaultDto catPorcPromotorImss;
	private List<CatValorDefaultDto> listCatPorcPromotor;
	private CatValorDefaultDto catPorcIndirectos;
	private CatValorDefaultDto catPorcEstrategia;
	private CatValorDefaultDto catValorTimbrado;
	private CatValorDefaultDto catValorSpei;
	
	
	public CatalogosCotizadorDTO() {
		
	}

	public List<CatGeneralDto> getCatVacaciones() {
		return catVacaciones;
	}

	public void setCatVacaciones(List<CatGeneralDto> catVacaciones) {
		this.catVacaciones = catVacaciones;
	}

	public List<CatGeneralDto> getCatZona() {
		return catZona;
	}

	public void setCatZona(List<CatGeneralDto> catZona) {
		this.catZona = catZona;
	}

	public List<CatGeneralDto> getCatTipoNomina() {
		return catTipoNomina;
	}

	public void setCatTipoNomina(List<CatGeneralDto> catTipoNomina) {
		this.catTipoNomina = catTipoNomina;
	}

	public List<CatGeneralDto> getCatDias() {
		return catDias;
	}

	public void setCatDias(List<CatGeneralDto> catDias) {
		this.catDias = catDias;
	}

	public List<CatGeneralDto> getCatTipoImss() {
		return catTipoImss;
	}

	public void setCatTipoImss(List<CatGeneralDto> catTipoImss) {
		this.catTipoImss = catTipoImss;
	}

	public List<CatGeneralDto> getCatTipoPrestaciones() {
		return catTipoPrestaciones;
	}

	public void setCatTipoPrestaciones(List<CatGeneralDto> catTipoPrestaciones) {
		this.catTipoPrestaciones = catTipoPrestaciones;
	}

	public List<CatGeneralDto> getCatCosteoAsimilable() {
		return catCosteoAsimilable;
	}

	public void setCatCosteoAsimilable(List<CatGeneralDto> catCosteoAsimilable) {
		this.catCosteoAsimilable = catCosteoAsimilable;
	}

	public List<CatGeneralDto> getCatTipoCotizacion() {
		return catTipoCotizacion;
	}

	public void setCatTipoCotizacion(List<CatGeneralDto> catTipoCotizacion) {
		this.catTipoCotizacion = catTipoCotizacion;
	}

	public List<CatGeneralDto> getCatEstatus() {
		return catEstatus;
	}

	public void setCatEstatus(List<CatGeneralDto> catEstatus) {
		this.catEstatus = catEstatus;
	}

	public List<CatGeneralDto> getCatTipoPersona() {
		return catTipoPersona;
	}

	public void setCatTipoPersona(List<CatGeneralDto> catTipoPersona) {
		this.catTipoPersona = catTipoPersona;
	}

	public List<CatGeneralDto> getCatProveedor() {
		return catProveedor;
	}

	public void setCatProveedor(List<CatGeneralDto> catProveedor) {
		this.catProveedor = catProveedor;
	}

	public List<CatTipoPagoDto> getCatTipoPeriodicidad() {
		return catTipoPeriodicidad;
	}

	public void setCatTipoPeriodicidad(List<CatTipoPagoDto> catTipoPeriodicidad) {
		this.catTipoPeriodicidad = catTipoPeriodicidad;
	}

	public List<CatGeneralDto> getCatTipoEsquema() {
		return catTipoEsquema;
	}

	public void setCatTipoEsquema(List<CatGeneralDto> catTipoEsquema) {
		this.catTipoEsquema = catTipoEsquema;
	}

	public CatValorDefaultDto getCatPorcPromotor() {
		return catPorcPromotor;
	}

	public void setCatPorcPromotor(CatValorDefaultDto catPorcPromotor) {
		this.catPorcPromotor = catPorcPromotor;
	}

	public CatValorDefaultDto getCatPorcPromotorImss() {
		return catPorcPromotorImss;
	}

	public void setCatPorcPromotorImss(CatValorDefaultDto catPorcPromotorImss) {
		this.catPorcPromotorImss = catPorcPromotorImss;
	}

	public CatValorDefaultDto getCatPorcIndirectos() {
		return catPorcIndirectos;
	}

	public void setCatPorcIndirectos(CatValorDefaultDto catPorcIndirectos) {
		this.catPorcIndirectos = catPorcIndirectos;
	}

	public CatValorDefaultDto getCatPorcEstrategia() {
		return catPorcEstrategia;
	}

	public void setCatPorcEstrategia(CatValorDefaultDto catPorcEstrategia) {
		this.catPorcEstrategia = catPorcEstrategia;
	}

	public CatValorDefaultDto getCatValorTimbrado() {
		return catValorTimbrado;
	}

	public void setCatValorTimbrado(CatValorDefaultDto catValorTimbrado) {
		this.catValorTimbrado = catValorTimbrado;
	}

	public CatValorDefaultDto getCatValorSpei() {
		return catValorSpei;
	}

	public void setCatValorSpei(CatValorDefaultDto catValorSpei) {
		this.catValorSpei = catValorSpei;
	}

	public List<CatValorDefaultDto> getListCatPorcPromotor() {
		return listCatPorcPromotor;
	}

	public void setListCatPorcPromotor(List<CatValorDefaultDto> listCatPorcPromotor) {
		this.listCatPorcPromotor = listCatPorcPromotor;
	}

	
}
