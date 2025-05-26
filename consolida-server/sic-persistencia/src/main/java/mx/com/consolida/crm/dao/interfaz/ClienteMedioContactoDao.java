package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.ClienteMedioContactoDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteMedioContacto;

public interface ClienteMedioContactoDao extends DAO<ClienteMedioContacto,Long>{

	ClienteMedioContactoDto convertirMedioContactoADto(ClienteMedioContacto clienteMedioContacto);

}
