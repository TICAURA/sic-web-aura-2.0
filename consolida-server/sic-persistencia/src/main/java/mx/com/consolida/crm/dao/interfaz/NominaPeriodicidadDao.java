package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.NominaPeriodicidadDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadFechasDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.NominaPeriodicidad;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface NominaPeriodicidadDao extends DAO<NominaPeriodicidad,Long>{
	public NominaPeriodicidad guardarNominaPeriodicidad(NominaPeriodicidad entity, UsuarioAplicativo us);
	public NominaPeriodicidad editarNominaPeriodicidad(NominaPeriodicidad entity, UsuarioAplicativo us);
	public NominaPeriodicidadDto obtenerNominaPeriodicidad(Long idNomina);	
}
