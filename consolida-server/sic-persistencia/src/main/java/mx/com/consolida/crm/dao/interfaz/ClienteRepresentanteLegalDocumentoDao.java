package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.crm.ClienteRepresentanteLegalDocumento;

public interface ClienteRepresentanteLegalDocumentoDao extends mx.com.consolida.dao.DAO<ClienteRepresentanteLegalDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosRepresentanteCliente(Long idCliente, Long idClienteRepLeg);
	
	List<DocumentoCSMDto> listDocumentosCerKeyRepresentanteCliente(Long idCliente, Long idClienteServRepLeg);

}
