package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDomicilio;

public interface PrestadoraServicioDomicilioDao extends mx.com.consolida.dao.DAO<PrestadoraServicioDomicilio, Long>{

	DomicilioComunDto convertirPrestadoraServicioDomicilioADto(
			PrestadoraServicioDomicilio prestadoraServicioDomicilio);


}
