package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegalDocumento;

public interface PrestadoraServicioApoderadoLegalDocumentoDao extends mx.com.consolida.dao.DAO<PrestadoraServicioApoderadoLegalDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosApoderadoPrestadora(Long idPrestadora, Long idPrestadoraServApodLeg);

}
