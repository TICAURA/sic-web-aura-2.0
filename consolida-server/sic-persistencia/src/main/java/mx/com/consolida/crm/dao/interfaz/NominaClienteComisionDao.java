package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteComisionHonorarioDto;
import mx.com.consolida.entity.crm.NominaClienteComision;

public interface NominaClienteComisionDao extends mx.com.consolida.dao.DAO<NominaClienteComision, Long>{

	List<ClienteComisionHonorarioDto> convertirNominaClienteComisionADto(List<NominaClienteComision> list);

}
