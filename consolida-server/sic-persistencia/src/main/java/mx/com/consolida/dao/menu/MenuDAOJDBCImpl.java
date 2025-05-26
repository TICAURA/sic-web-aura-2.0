package mx.com.consolida.dao.menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import mx.com.consolida.menu.MenuDTO;
import mx.com.consolida.menu.MenuUsuarioDTO;
import mx.com.consolida.menu.TipoMenuEnum;


@Repository
public class MenuDAOJDBCImpl implements MenuDAOJDBC{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Logger LOGGER = LoggerFactory.getLogger(MenuDAOJDBCImpl.class);
	
	public MenuUsuarioDTO consultarMenu(Long idRol){
		final Map<String,String> mapUsuarios = new HashMap<String, String>();
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select  m.id_modulo, m.nombre as nombreModulo, m.descripcion, p.id_pantalla, p.ruta_pantalla, p.icono, p.nombre as nombrePantalla  ");
		sb.append(" from rol_modulo rm , modulo_pantalla mp , modulo m, pantalla p ");
		sb.append(" where rm.id_modulo = mp.id_modulo ");
		sb.append(" and mp.id_modulo = m.id_modulo ");
		sb.append(" and mp.id_pantalla = p.id_pantalla ");
		sb.append(" and rm.id_rol = ? and mp.ind_estatus=1 order by rm.id_rol_modulo ,mp.id_modulo , p.id_pantalla ");
		
		MenuUsuarioDTO menuUsuarioDTO = new MenuUsuarioDTO();
		final List<MenuDTO> menus = new ArrayList<MenuDTO>();
		
		
		   @SuppressWarnings("unchecked")
		List<MenuDTO> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idRol}, new RowMapper() {
	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	  String nombreModulo = rs.getString("nombreModulo");
	        	  String data= mapUsuarios.get(nombreModulo);
	        	  
	        	  MenuDTO menuDTO = new MenuDTO();  
	        	  if(data == null){
	        		  mapUsuarios.put(nombreModulo,nombreModulo);
	        		  
	        		  menuDTO.setIdMenu(rs.getInt("id_modulo"));
		        	  menuDTO.setNombreMenu(rs.getString("descripcion"));
		        	  menuDTO.setUrlMenu(rs.getString("descripcion"));
		        	  menuDTO.setTipoMenu(TipoMenuEnum.MENU.name());
		        	  menus.add(menuDTO);
		        	  
		        	  MenuDTO menu2DTO = new MenuDTO();  
		        	  
		        	  menu2DTO.setIdMenu(rs.getInt("id_pantalla"));
		        	  menu2DTO.setNombreMenu(rs.getString("nombrePantalla"));
		        	  menu2DTO.setUrlMenu(rs.getString("ruta_pantalla"));
		        	  menu2DTO.setIcono(rs.getString("icono"));
		        	  
		        	  menu2DTO.setTipoMenu(TipoMenuEnum.SUBMENU.name());
		        	  
		        	  menus.add(menu2DTO); 
	        	  }else{
	        		  MenuDTO menu2DTO = new MenuDTO();  
		        	  
		        	  menu2DTO.setIdMenu(rs.getInt("id_pantalla"));
		        	  menu2DTO.setNombreMenu(rs.getString("nombrePantalla"));
		        	  menu2DTO.setUrlMenu(rs.getString("ruta_pantalla"));
		        	  menu2DTO.setIcono(rs.getString("icono"));
		        	  menu2DTO.setTipoMenu(TipoMenuEnum.SUBMENU.name());
		        	  
		        	  menus.add(menu2DTO);
	        	  }
	        	  
	        	  
	        	  
	          LOGGER.debug("Datos Menu--> ");
	          return menuDTO;
		   }
		   });
		   
		   menuUsuarioDTO.setMenus(menus);
		   
		   return menuUsuarioDTO;
	}
	
}

