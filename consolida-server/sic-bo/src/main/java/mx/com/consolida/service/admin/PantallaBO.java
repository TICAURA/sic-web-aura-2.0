package mx.com.consolida.service.admin;

import java.util.List;

import mx.com.consolida.entity.seguridad.Pantalla;

public interface PantallaBO {

	
	public List<Pantalla> getPantallas();
	
	public Pantalla getPantallas(Long id);
	
	public List<Pantalla> guardarPantalla(Pantalla pantalla);
	
	public List<Pantalla> eliminarPantalla(Long idPantalla);
}
