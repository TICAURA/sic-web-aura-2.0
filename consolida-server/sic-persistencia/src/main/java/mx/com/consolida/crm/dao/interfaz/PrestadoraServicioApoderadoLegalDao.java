package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegal;

public interface PrestadoraServicioApoderadoLegalDao extends mx.com.consolida.dao.DAO<PrestadoraServicioApoderadoLegal, Long>{
	
	List<ApoderadoLegalDto> getListApoderadoLegalByIdPrestadoraServicio(Long idPrestadora);

}
