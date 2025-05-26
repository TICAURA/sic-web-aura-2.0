package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.crm.dto.ColaboradorNominaDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ColaboradorNomina;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ColaboradorNominaDao extends DAO<ColaboradorNomina,Long>{
	
	public Long guardarColaboradorNomina(ColaboradorNomina entity, UsuarioAplicativo us);
	public ColaboradorNomina editarColaboradorNomina(ColaboradorNomina entity, UsuarioAplicativo us);
	public ColaboradorNominaDto obtenerColaboradorNomina(Long idNomina, Long idColaborador);
	
	
}
