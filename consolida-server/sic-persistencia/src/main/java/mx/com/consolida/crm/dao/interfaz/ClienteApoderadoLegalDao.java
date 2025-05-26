package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.entity.crm.ClienteApoderadoLegal;

public interface ClienteApoderadoLegalDao extends mx.com.consolida.dao.DAO<ClienteApoderadoLegal, Long>{
	
	List<ApoderadoLegalDto> getListApoderadoLegalByIdCliente(Long idCliente);

}
