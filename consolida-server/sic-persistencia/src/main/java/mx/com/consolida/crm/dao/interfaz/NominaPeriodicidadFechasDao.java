package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.NominaPeriodicidadFechasDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.NominaPeriodicidadFechas;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface NominaPeriodicidadFechasDao extends DAO<NominaPeriodicidadFechas,Long> {
	public NominaPeriodicidadFechas guardarNominaPeriodicidad(NominaPeriodicidadFechas entity, UsuarioAplicativo us);
	public void eliminarNominaPeriodicidad(Long idNominaPeriodos);
	public List<NominaPeriodicidadFechasDto> obtenerPeriodosFechas(Long idNomina);
}
