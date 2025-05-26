package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteDomicilio;

public interface ClienteDomicilioDao extends DAO<ClienteDomicilio,Long>{

	DomicilioComunDto convertirDomicilioADto(ClienteDomicilio clienteDomicilio);
	
	DomicilioComunDto getDatosDomicilioByIdClienteDomicilio(Long IdClienteDomicilio);
	
	Long getIdClienteDomicilioByIdDomicilio(Long idDomiclio);
	
	

}
