package mx.com.consolida.crm.service.interfaz;

import mx.com.consolida.generico.UsuarioAplicativo;

public interface CatGeneralBO {
	
	Boolean guardarCatGeneral(Long idCatMaestro, String clave, String descripcion, UsuarioAplicativo usuarioAplicativo); 
	

}
