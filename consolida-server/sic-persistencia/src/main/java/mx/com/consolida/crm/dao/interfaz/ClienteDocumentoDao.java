package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.crm.ClienteDocumento;

public interface ClienteDocumentoDao extends mx.com.consolida.dao.DAO<ClienteDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosCliente(Long idCliente);

}
