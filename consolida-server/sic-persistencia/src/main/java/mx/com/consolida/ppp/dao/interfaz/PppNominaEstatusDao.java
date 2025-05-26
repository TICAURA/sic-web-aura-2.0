package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.ppp.dto.HistoricoNominaDto;

public interface PppNominaEstatusDao extends DAO<PppNominaEstatus,Long>{
	
	public List<PppNominaEstatus> getPppNominaEstatusActivo(Long idNomina);
	
	List<HistoricoNominaDto> getHistoricoByIdPppNomina(Long idPppNomina);

	public List<CatGeneralDto> obtenerCatEstatusNomina();
}
