package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.comunes.dto.AccionistaDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionista;

public interface PrestadoraServicioAccionistaDao extends mx.com.consolida.dao.DAO<PrestadoraServicioAccionista, Long>{
	
	List<AccionistaDto> getListAccionistaByIdPrestadoraServicio(Long idPrestadora);

}
