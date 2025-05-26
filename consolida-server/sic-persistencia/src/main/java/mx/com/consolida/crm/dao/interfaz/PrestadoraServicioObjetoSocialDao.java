package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioObjetoSocialDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioObjetoSocial;

public interface PrestadoraServicioObjetoSocialDao extends mx.com.consolida.dao.DAO<PrestadoraServicioObjetoSocial, Long>{
	
	public List<PrestadoraServicioObjetoSocialDto> getListObjetoSocialByIdPrestadora(Long idPrestadora);

}
