package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegalDocumento;

public interface PrestadoraServicioRepresentanteLegalDocumentoDao extends mx.com.consolida.dao.DAO<PrestadoraServicioRepresentanteLegalDocumento, Long>{
	
	List<DocumentoCSMDto> listDocumentosRepresentantePrestadora(Long idPrestadora);

	List<DocumentoCSMDto> listPrestadoraDocumentosRepresentanteCerKey(Long idPrestadoraServRepLeg,
			Long idPrestadora);
	
	List<DocumentoCSMDto> listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(Long idPrestadora, Long idPrestadoraServRepLeg);

}
