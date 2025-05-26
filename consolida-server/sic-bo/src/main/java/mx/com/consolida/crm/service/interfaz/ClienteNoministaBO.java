package mx.com.consolida.crm.service.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteNoministaDto;
import mx.com.consolida.crm.dto.NoministaDto;

public interface ClienteNoministaBO {
	
	List<NoministaDto> lsitaNoministasByCLiente(Long idCliente);
	
	ClienteNoministaDto getClienteNoministaByidClienteIdNominista(Long idCliente, Long idNominista);

}
