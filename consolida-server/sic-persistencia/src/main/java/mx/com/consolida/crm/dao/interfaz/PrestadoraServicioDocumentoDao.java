package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.PrestadoraServicioDocumentoDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDocumento;

public interface PrestadoraServicioDocumentoDao extends mx.com.consolida.dao.DAO<PrestadoraServicioDocumento, Long>{

	PrestadoraServicioDocumentoDto convertirPrestadoraServicioDocumentoADto(
			PrestadoraServicioDocumento prestadoraServicioDocumento);

}
