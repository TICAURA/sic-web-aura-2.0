package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.PrestadoraServicioClaseFraccionPrimaDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioClaseFraccionPrima;

public interface PrestadoraServicioClaseFraccionPrimaDao extends mx.com.consolida.dao.DAO<PrestadoraServicioClaseFraccionPrima, Long>{
	
	PrestadoraServicioClaseFraccionPrimaDto getPresatdorServicioClaseFraccionByIdPrestServicio(Long idPrestadora);

}
