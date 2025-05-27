package mx.com.consolida.crm.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioStp;

@Repository
public class PrestadoraServicioStpDaoImpl extends GenericDAO<PrestadoraServicioStp, Long> implements PrestadoraServicioStpDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PrestadoraServicioStpDto> convertirPrestadoraServicioStpADto(
			List<PrestadoraServicioStp> prestadoraServicioStp) {
		List<PrestadoraServicioStpDto> listPrestadoraServicioStpDto = new ArrayList<PrestadoraServicioStpDto>();
		
		for(PrestadoraServicioStp ps: prestadoraServicioStp) {
			PrestadoraServicioStpDto psStpDto = new PrestadoraServicioStpDto();
			psStpDto.setIdPrestadoraServicioStp(ps.getIdPrestadoraServicioStp());
			psStpDto.setIdTipoDispersor(ps.getIdTipoDispersor().getIdCatGeneral());
			psStpDto.setDescripcionTipoDispersor(ps.getIdTipoDispersor().getDescripcion());
			psStpDto.setNombreCentroCostos(ps.getNombreCentroCostos());
			psStpDto.setClabeCuentaOrdenante(ps.getClabeCuentaOrdenante());
			psStpDto.setClientId(ps.getClientId());
			psStpDto.setIdCliente(ps.getIdCliente());
			psStpDto.setIdCuentaAhorro(ps.getIdCuentaAhorro());			
			psStpDto.setUserName(ps.getUserName());
			psStpDto.setPassword(ps.getPassword());
			psStpDto.setClientSecret(ps.getClientSecret());
			psStpDto.setApiKey(ps.getApiKey());
			psStpDto.setActivo(ps.getIndEstatus()==1L? Boolean.TRUE : Boolean.FALSE);
		
			listPrestadoraServicioStpDto.add(psStpDto);
		}
		return listPrestadoraServicioStpDto;
	}

	@Transactional
	public void actualizarPrestadora(Long idPrestadoraServicio) {
					String sql = "update prestadora_Servicio_stp set ind_estatus = 0 where id_prestadora_servicio = "+ idPrestadoraServicio;
			jdbcTemplate.execute(sql);
		}
		
		
	
	
}
