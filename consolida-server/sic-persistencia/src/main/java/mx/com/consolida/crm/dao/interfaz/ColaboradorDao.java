package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.Colaborador;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ColaboradorDao extends DAO<Colaborador,Long>{
	
	public ColaboradorDto guardarColaborador(ColaboradorDto dto, UsuarioAplicativo us);
	public ColaboradorDto editarColaborador(ColaboradorDto dto, UsuarioAplicativo us);
	public ColaboradorDto obtenerColaboradorById(Long idColaborador);
	public List<ColaboradorDto> obtenercolaboradoresByidNomina(Long idNomina);
	
}
