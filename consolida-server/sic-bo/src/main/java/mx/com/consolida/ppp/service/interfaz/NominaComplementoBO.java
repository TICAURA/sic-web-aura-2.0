package mx.com.consolida.ppp.service.interfaz;

import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;

public interface NominaComplementoBO {
	
	Boolean guargarNominaComplemento(NominaComplementoDto nominaComplementoDto, Long idUsuarioAplicativo);
	
	NominaComplementoDto getDatosNomComplByIdNomCompl(String claveNomina);
	
	Boolean autorizaFinanciamientoOperaciones(NominaComplementoDto nominaComplementoDto, Long idUsuarioAplicativo);
	
	NominaDto getNominaDtoByClave(String claveNomina);
	
}
