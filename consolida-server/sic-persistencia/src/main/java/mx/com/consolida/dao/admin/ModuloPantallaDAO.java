package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.ModuloPantalla;

public interface ModuloPantallaDAO  extends DAO<ModuloPantalla,Long> {

	public List<PantallaDTO> getPantallaByIdModulo(Long idModulo);
}
