package mx.com.consolida.crm.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioStp;

@Repository
public class PrestadoraServicioStpDaoImpl extends GenericDAO<PrestadoraServicioStp, Long> implements PrestadoraServicioStpDao{

	@Override
	public List<PrestadoraServicioStpDto> convertirPrestadoraServicioStpADto(
			List<PrestadoraServicioStp> prestadoraServicioStp) {
		List<PrestadoraServicioStpDto> listPrestadoraServicioStpDto = new ArrayList<PrestadoraServicioStpDto>();
		
		for(PrestadoraServicioStp ps: prestadoraServicioStp) {
			PrestadoraServicioStpDto psStpDto = new PrestadoraServicioStpDto();
			psStpDto.setIdPrestadoraServicioStp(ps.getIdPrestadoraServicioStp());
			psStpDto.setClabeCuentaOrdenante(ps.getClabeCuentaOrdenante());
			psStpDto.setNombreCentroCostos(ps.getNombreCentroCostos());
			listPrestadoraServicioStpDto.add(psStpDto);
		}
		return listPrestadoraServicioStpDto;
	}
	
}
