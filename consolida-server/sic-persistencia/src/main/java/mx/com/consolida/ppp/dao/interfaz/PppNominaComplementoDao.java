package mx.com.consolida.ppp.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNominaComplemento;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;

public interface PppNominaComplementoDao extends DAO<PppNominaComplemento,Long>{
	
	NominaComplementoDto getDatosNomComplByIdNomCompl(String claveNomina);

}
