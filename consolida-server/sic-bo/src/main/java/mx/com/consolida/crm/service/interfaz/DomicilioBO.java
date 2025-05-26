package mx.com.consolida.crm.service.interfaz;

import mx.com.consolida.crm.dto.DomicilioDto;

public interface DomicilioBO {

	
	public Long guardarDomicilio(DomicilioDto domicilioDto , Long idUsuarioAutenticado); 
}
