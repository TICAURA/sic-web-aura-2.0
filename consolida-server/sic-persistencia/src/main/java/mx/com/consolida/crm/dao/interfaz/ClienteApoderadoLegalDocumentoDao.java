package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.crm.ClienteApoderadoLegalDocumento;

public interface ClienteApoderadoLegalDocumentoDao extends mx.com.consolida.dao.DAO<ClienteApoderadoLegalDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosApoderadoCliente(Long idCliente, Long idClienteApodLeg);

}
