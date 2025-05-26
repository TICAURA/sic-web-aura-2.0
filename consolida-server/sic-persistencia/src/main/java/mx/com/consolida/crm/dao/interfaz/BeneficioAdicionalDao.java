package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.BeneficioAdicionalColaboradorDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.BeneficioAdicionalColaborador;

public interface BeneficioAdicionalDao extends DAO<BeneficioAdicionalColaborador,Long>{
	
	public List<BeneficioAdicionalColaboradorDto> obtenerBeneficioAdicionalByColab(Long idColaborador);
	public void borrarBeneficioAdicionalByColab(Long idColaborador);
	public void guardarBeneficioAdicional(BeneficioAdicionalColaborador entity);
	

}
