package mx.com.consolida.service.admin;

import java.util.List;
import org.springframework.stereotype.Service;

import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Rol;

@Service
public interface RolBO {
	
	public List<Rol> getRoles();
	
	public Rol getRol(Long id);
	
	public List<Rol> guardarRol(Rol rol);
	
	public List<Rol> eliminarRol(Long idRol);
	
	public List<RolDTO> rolesCelula();
}
