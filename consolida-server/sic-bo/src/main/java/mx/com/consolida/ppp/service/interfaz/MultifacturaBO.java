package mx.com.consolida.ppp.service.interfaz;

import java.util.List;

import mx.com.consolida.ppp.dto.NominaDto;


public interface MultifacturaBO {
	
	List<NominaDto> listaNominaColabCargadosByIdCliente(Long idCliente , Long idNominista);
	List<NominaDto> listaNominaVinculadasFactura(Long idFactura);
	


}
