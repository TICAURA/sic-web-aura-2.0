package mx.com.consolida.service.interfaz;

import java.math.BigDecimal;

import mx.com.consolida.catalogos.CatImssDto;
import mx.com.consolida.catalogos.CatIsrDto;
import mx.com.consolida.catalogos.CatPrimaRiesgoDto;
import mx.com.consolida.catalogos.CatSalariosGeneralesDto;
import mx.com.consolida.catalogos.CatSubsidioDto;
import mx.com.consolida.catalogos.CatVacacionesDto;

public interface CatalogoCotizadorBO {

	public CatSalariosGeneralesDto obtenerSalarioGeneralByClave(String clave);
	public CatImssDto otenerImssByClave(String clave);
	public CatVacacionesDto obtenerVacacionesByAntiguedad(BigDecimal valor);
	public CatIsrDto otenerIsrByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor);
	public CatSubsidioDto otenerSubsidioByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor);
	public CatPrimaRiesgoDto otenerIsrByPrimaRiegoVar(String claveRiesgo);
}