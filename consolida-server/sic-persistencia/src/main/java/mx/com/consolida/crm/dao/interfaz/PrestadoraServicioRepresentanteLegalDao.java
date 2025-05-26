package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegal;

public interface PrestadoraServicioRepresentanteLegalDao extends mx.com.consolida.dao.DAO<PrestadoraServicioRepresentanteLegal, Long>{
	
	List<RepresentanteLegalDto> getListRepresentanteLegalByIdPrestadoraServicio(Long idPrestadora);

}
