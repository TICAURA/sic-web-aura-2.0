package mx.com.consolida.service.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.Modulo;

public interface ModuloBO {
	
	public List<Modulo> getModulos();
	
	public Modulo getModulos(Long id);
	
	public List<Modulo> guardarModulo(Modulo pantalla);
	
	public List<Modulo> eliminarModulo(Long idModulo);
	
	public List<PantallaDTO> pantallaModulo(Long idModulo);
	
	public void guardarMododuloPantalla(PantallaDTO pantalla);
}
