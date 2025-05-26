package mx.com.consolida.dao.admin;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.ModuloDTO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.dto.admin.RolModuloPantallaDTO;
import mx.com.consolida.entity.seguridad.RolModulo;

@Repository
public class RolModuloDAOImpl  extends GenericDAO<RolModulo, Long> implements RolModuloDAO{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<RolModuloPantallaDTO> getRolModulosPantalla(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("  select r.id_rol, r.nombre as nombreRol , r.descripcion as descRol, ");
		sb.append(" count( rm.id_rol_modulo) as total_modulos , count(rp.id_rol_pantalla) as total_pantallas ");
		sb.append("  from rol r  ");
		sb.append("  left join rol_modulo rm on r.id_rol = rm.id_rol  ");
		sb.append("  left join  rol_pantalla rp on r.id_rol = rp.id_rol ");
		sb.append("  group by r.id_rol, r.nombre , r.descripcion; ");
		
		 return jdbcTemplate.query(
				 sb.toString(),
	                (rs, rowNum) ->
	                        new RolModuloPantallaDTO(
	                                rs.getLong("id_rol"),
	                                rs.getString("nombreRol"),
	                                rs.getString("descRol"),
	                                rs.getLong("total_modulos"),
	                                rs.getLong("total_pantallas")
	                        )
	        );
		
	}
	
	public List<ModuloDTO> getModulosByIdRol(Long idRol){
		
		StringBuilder sb = new StringBuilder();
		sb.append("  select m.id_modulo , m.nombre , m.descripcion, m.ind_estatus ,m.fecha_alta, rm.id_rol_modulo , rm.id_rol");
		sb.append("   from modulo m ");
		sb.append("   left join rol_modulo rm on m.id_modulo = rm.id_modulo ");
		sb.append("   and rm.id_rol = " + idRol);
		
		 return jdbcTemplate.query(
				 sb.toString(),
	                (rs, rowNum) ->
	                        new ModuloDTO(
	                                rs.getLong("id_modulo"),
	                                rs.getString("nombre"),
	                                rs.getString("descripcion"),
	                                rs.getDate("fecha_alta"),
	                                rs.getString("ind_estatus"),
	                                rs.getLong("id_rol_modulo"),
	                                ((rs.getLong("id_rol_modulo") != 0l)? true:false),
	                                idRol
	                                
	                        )
	        );
		
	}
	

}
