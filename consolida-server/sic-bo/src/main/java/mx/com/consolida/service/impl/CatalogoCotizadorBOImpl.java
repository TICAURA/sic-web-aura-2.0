package mx.com.consolida.service.impl;


import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.catalogos.CatImssDto;
import mx.com.consolida.catalogos.CatIsrDto;
import mx.com.consolida.catalogos.CatPrimaRiesgoDto;
import mx.com.consolida.catalogos.CatSalariosGeneralesDto;
import mx.com.consolida.catalogos.CatSubsidioDto;
import mx.com.consolida.catalogos.CatVacacionesDto;
import mx.com.consolida.dao.interfaz.CatalogoCotizadorDao;
import mx.com.consolida.entity.CatImss;
import mx.com.consolida.entity.CatIsr;
import mx.com.consolida.entity.CatPrimaRiesgo;
import mx.com.consolida.entity.CatSalariosGenerales;
import mx.com.consolida.entity.CatSubsidio;
import mx.com.consolida.entity.CatVacaciones;
import mx.com.consolida.service.interfaz.CatalogoCotizadorBO;

@Service
public class CatalogoCotizadorBOImpl implements CatalogoCotizadorBO {

	@Autowired
	private CatalogoCotizadorDao catDao;
	
	@Transactional
	public CatSalariosGeneralesDto obtenerSalarioGeneralByClave(String clv){
		CatSalariosGeneralesDto catDto = new CatSalariosGeneralesDto();
		CatSalariosGenerales cat = catDao.obtenerSalarioGeneralByClave(clv);
		catDto.setDescripcion(cat.getDescripcion());
		catDto.setFechaAlta(cat.getFechaAlta());
		catDto.setIdCatSalariosGeneraleas(cat.getIdCatSalariosGeneraleas());
		catDto.setIndEstatus(cat.getIndEstatus());
		catDto.setValor(cat.getValor());
		return catDto;
	}
	@Transactional
	public CatImssDto otenerImssByClave(String clv){
		CatImssDto catDto = new CatImssDto();
		CatImss cat = catDao.otenerImssByClave(clv);
		catDto.setDescripcion(cat.getDescripcion());
		catDto.setFechaAlta(cat.getFechaAlta());
		catDto.setIdCatImss(cat.getIdCatImss());
		catDto.setIndEstatus(cat.getIndEstatus());
		catDto.setTipo(cat.getTipo());
		catDto.setValor(cat.getValor());
		return catDto;
	}
	
	@Transactional
	public CatVacacionesDto obtenerVacacionesByAntiguedad(BigDecimal valor) {
		CatVacacionesDto catDto = new CatVacacionesDto();
		CatVacaciones cat = catDao.obtenerVacacionesByAntiguedad(valor);
		catDto.setAniosFin(cat.getAniosFin());
		catDto.setAniosInicio(cat.getAniosInicio());
		catDto.setIdCatVacaciones(cat.getIdCatVacaciones());
		catDto.setVacaciones(cat.getVacaciones());
		return catDto;
	}
	
	@Transactional
	public CatIsrDto otenerIsrByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor) {
		CatIsrDto catDto = new CatIsrDto();
		CatIsr cat = catDao.otenerIsrByIdPeriodicidadVar(idPeriodicidad,valor);
		catDto.setCuotaFija(cat.getCuotaFija());
		catDto.setIdCatIsr(cat.getIdCatIsr());
		catDto.setLimiteInferior(cat.getLimiteInferior());
		catDto.setLimiteSuperior(cat.getLimiteSuperior());
		catDto.setPorcentajecatTipoPago(cat.getPorcentajecatTipoPago());
		return catDto;
	}
	@Transactional
	public CatSubsidioDto otenerSubsidioByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor) {
		CatSubsidioDto catDto = new CatSubsidioDto();
		CatSubsidio cat = catDao.otenerSubsidioByIdPeriodicidadVar(idPeriodicidad,valor);
		catDto.setHastaIngresosDe(cat.getHastaIngresosDe());
		catDto.setIdCatSubsidio(cat.getIdCatSubsidio());
		catDto.setParaIngresosDe(cat.getParaIngresosDe());
		catDto.setSubsidio(cat.getSubsidio());
		return catDto;
	}
	@Transactional
	public CatPrimaRiesgoDto otenerIsrByPrimaRiegoVar(String claveRiesgo) {
		CatPrimaRiesgoDto catDto = new CatPrimaRiesgoDto();
		CatPrimaRiesgo cat = catDao.otenerIsrByPrimaRiegoVar(claveRiesgo);
		catDto.setClaveRiesgo(cat.getClaveRiesgo());
		catDto.setIdCatPrimaRiesgo(cat.getIdCatPrimaRiesgo());
		catDto.setRiesgoTrabajo(cat.getRiesgoTrabajo());
		return catDto;
	}
	
}
