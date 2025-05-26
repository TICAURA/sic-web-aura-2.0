package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteConceptoFacaturacionDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteConceptoFacturacion;

public interface ClienteConceptoFacaturacionDao extends DAO<ClienteConceptoFacturacion,Long>{
	
	List<ClienteConceptoFacaturacionDto> getListConceptoFactruByCliente(Long idCliente);

}
