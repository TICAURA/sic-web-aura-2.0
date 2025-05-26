package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.entity.crm.ClienteRepresentanteLegal;

public interface ClienteRepresentanteLegalDao extends mx.com.consolida.dao.DAO<ClienteRepresentanteLegal, Long>{
	
	List<RepresentanteLegalDto> getListRepresentanteLegalByIdCliente(Long idCliente);

}
