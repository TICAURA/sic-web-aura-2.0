package mx.com.consolida.dao.interfaz;

import java.math.BigDecimal;

import mx.com.consolida.catalogos.CatIsrDto;
import mx.com.consolida.catalogos.CatPrimaRiesgoDto;
import mx.com.consolida.entity.CatImss;
import mx.com.consolida.entity.CatIsr;
import mx.com.consolida.entity.CatPrimaRiesgo;
import mx.com.consolida.entity.CatSalariosGenerales;
import mx.com.consolida.entity.CatSubsidio;
import mx.com.consolida.entity.CatVacaciones;

public interface CatalogoCotizadorDao {
	public CatSalariosGenerales obtenerSalarioGeneralByClave(String clv);
	public CatImss otenerImssByClave(String clv);
	public CatVacaciones obtenerVacacionesByAntiguedad(BigDecimal valor);
	public CatIsr otenerIsrByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor);
	public CatSubsidio otenerSubsidioByIdPeriodicidadVar(Integer idPeriodicidad, BigDecimal valor);
	public CatPrimaRiesgo otenerIsrByPrimaRiegoVar(String claveRiesgo);
}
