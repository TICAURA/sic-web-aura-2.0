package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ColaboradorDocumento;

public interface ColaboradorDocumentosDao extends DAO<ColaboradorDocumento,Long>{
	
	public List<DocumentoCSMDto> obtenerDocumentos(Long idColaborador);
	public void eliminarDocumento(Long idColaboradorDocumento);
	
}
