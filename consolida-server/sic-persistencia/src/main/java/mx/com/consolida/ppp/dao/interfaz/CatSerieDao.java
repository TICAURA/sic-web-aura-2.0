package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.CatSerie;
import mx.com.consolida.ppp.dto.CatSerieDto;

public interface CatSerieDao  extends DAO<CatSerie,Long>{
	
	List<CatSerieDto> listaCatSerie();
	
	Long maxConsecutivo();

}
