package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.CelulaPrestadoraServicioDto;
import mx.com.consolida.entity.celula.CelulaPrestadoraServicio;

public interface CelulaPrestadoraServicioDao extends mx.com.consolida.dao.DAO<CelulaPrestadoraServicio, Long>{

	CelulaPrestadoraServicioDto convertirCelulaPrestadoraServicioADto(CelulaPrestadoraServicio celulaPrestadoraServicio);
	
	CelulaPrestadoraServicio getCelulaPrestByIdPrestadora(Long idPrestadora);


}
