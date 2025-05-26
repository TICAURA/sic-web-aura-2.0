package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioRegistroPatronalDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRegistroPatronal;

public interface PrestadoraServicioRegistroPatronalDao extends mx.com.consolida.dao.DAO<PrestadoraServicioRegistroPatronal, Long>{

	 List<PrestadoraServicioRegistroPatronalDto> getListRegistroPatronalByIdPrestadora(Long idPrestadora);
	
}
