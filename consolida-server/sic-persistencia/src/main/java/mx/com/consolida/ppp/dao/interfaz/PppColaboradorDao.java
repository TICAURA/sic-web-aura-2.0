package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppColaborador;

public interface PppColaboradorDao extends DAO<PppColaborador,Long>{

	void eliminarColaboradores(Long idPppNomina);
	List<EmpleadoDTO> obtenercolaboradoresParaDispersionStpByidNomina(Long idPppNomina);
	Long totalColaboradoresByIdPppNomina(Long idPppNomina, Long idCatEstatusColaborador, Long idCatEstatusColaborador2, Long idCatEstatusColaborador3);
	void eliminarColaborador(Long idPppColaborador);

}
