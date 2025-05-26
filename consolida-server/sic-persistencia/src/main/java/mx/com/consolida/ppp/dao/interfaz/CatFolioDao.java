package mx.com.consolida.ppp.dao.interfaz;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.ppp.CatFolio;
import mx.com.consolida.ppp.dto.CatFolioDto;

public interface CatFolioDao extends DAO<CatFolio,Long>{
	
	List<CatFolioDto> listaCatFolio();

}
