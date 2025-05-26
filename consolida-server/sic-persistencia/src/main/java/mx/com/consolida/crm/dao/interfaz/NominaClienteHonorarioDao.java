package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteComisionHonorarioDto;
import mx.com.consolida.entity.crm.NominaClienteHonorario;

public interface NominaClienteHonorarioDao extends mx.com.consolida.dao.DAO<NominaClienteHonorario, Long>{

	List<ClienteComisionHonorarioDto> convertirNominaClienteHonorarioADto(
			List<NominaClienteHonorario> nominaClienteHonorarios);

}
