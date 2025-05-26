package mx.com.consolida.dao.interfaz;

import mx.com.consolida.dto.CostoAdicionalDto;
import mx.com.consolida.entity.CostoAdicional;

public interface CostoAdicionalDao extends mx.com.consolida.dao.DAO<CostoAdicional, Long>{

	void guardarCostoAdicional(CostoAdicional costoAdicional);
	
	void borradoLogicoCostoAdicional(Long idCotizacion, Long idClienteTemp);

	CostoAdicional obtenerCostoAdicional(Long idCostoAdicional);
	
	CostoAdicionalDto obtenerCostoAdicionalByIdCotizacion(Long idCotizacion);

}
