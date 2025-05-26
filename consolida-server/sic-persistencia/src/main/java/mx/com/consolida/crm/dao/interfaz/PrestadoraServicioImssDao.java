package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.PrestadoraServicioImssDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioImss;

public interface PrestadoraServicioImssDao extends mx.com.consolida.dao.DAO<PrestadoraServicioImss, Long> {
	
	PrestadoraServicioImssDto getPresatdorServicioImssByIdPrestServicio(Long idPrestadora);

}
