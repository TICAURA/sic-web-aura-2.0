package mx.com.consolida.usuario;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.UsuarioDTO;



public class UserService  implements UserDetailsService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UsuarioBO usuarioBO;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		// This is a spring security user object
		UserDetailNomina user = null;
			try {
				// Replace loadUserByUsername with your Data access method to pull the user and encrypted password from the database
				UsuarioDTO userAut = loadUserByUsuarioDB(username);
				
				if(userAut != null){
					user = new UserDetailNomina();
					user.setUsername(username);
					user.setPassword(userAut.getPassword());
					user.setIdUsuario(userAut.getIdUsuario());
					
					//Si contiente la informacion de datos usuario se agregan los datos del nombre
					user.setNombre(userAut.getNombre());
					user.setPrimerApellido(userAut.getPrimerApellido());
					user.setSegundoApellido(userAut.getSegundoApellido());
					user.setUsuarioRols(userAut.getUsuarioRols());
					user.setRolSeleccionado(userAut.getUsuarioRols().get(0));
				}
				
			} catch (Exception e) {
				LOGGER.error("Error al consultar el usuario" , e);
			}
			
			return user;
	}
	
	public Collection<SimpleGrantedAuthority> getAuthorities(Integer access) {
		List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>(1);

		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		authList.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		
		return authList;	
	}
	
	private UsuarioDTO loadUserByUsuarioDB(String username) throws Exception {
		
		return  usuarioBO.usuarioAutenticacion(username);
	}



}
