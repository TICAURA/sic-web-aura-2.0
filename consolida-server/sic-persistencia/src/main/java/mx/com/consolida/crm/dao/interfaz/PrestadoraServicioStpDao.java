package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioStp;

public interface PrestadoraServicioStpDao extends mx.com.consolida.dao.DAO<PrestadoraServicioStp, Long>{

	List<PrestadoraServicioStpDto> convertirPrestadoraServicioStpADto(List<PrestadoraServicioStp> prestadoraServicioStp);

}
