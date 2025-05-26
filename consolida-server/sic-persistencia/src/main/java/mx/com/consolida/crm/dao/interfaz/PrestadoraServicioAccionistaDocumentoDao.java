package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionistaDocumento;

public interface PrestadoraServicioAccionistaDocumentoDao extends mx.com.consolida.dao.DAO<PrestadoraServicioAccionistaDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosApoderadoPrestadora(Long idPrestadora, Long idPrestadoraServaccionista);

}
