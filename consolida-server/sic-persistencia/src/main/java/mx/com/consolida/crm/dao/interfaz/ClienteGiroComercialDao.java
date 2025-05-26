package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteActividadDto;
import mx.com.consolida.entity.crm.ClienteGiroComercial;

public interface ClienteGiroComercialDao extends mx.com.consolida.dao.DAO<ClienteGiroComercial, Long>{

	List<ClienteActividadDto> convertirGiroADto(List<ClienteGiroComercial> clienteGiroComercial);

}
