package mx.com.consolida.service.admin;

import java.util.List;

import mx.com.consolida.dto.admin.ModuloDTO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.dto.admin.RolModuloPantallaDTO;

public interface RolModuloPantallaBO {

	public List<RolModuloPantallaDTO> rolModulosPantallas();
	
	public List<ModuloDTO> getModulosByIdRol(Long idRol);
	
	public List<PantallaDTO> getPantallasByIdRol(Long idRol);
	
	public List<ModuloDTO> guardarRolModulo(ModuloDTO moduloDTO);
	
	public List<PantallaDTO> guardarRolPantalla(PantallaDTO pantallaDTO);
}
