package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioActividadDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioGiroComercial;

public interface PrestadoraServicioGiroComercialDao extends mx.com.consolida.dao.DAO<PrestadoraServicioGiroComercial, Long>{
	
	List<PrestadoraServicioActividadDto> convertirGiroADto(List<PrestadoraServicioGiroComercial> prestadoraGiroComercial);
	
	List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro);

}
