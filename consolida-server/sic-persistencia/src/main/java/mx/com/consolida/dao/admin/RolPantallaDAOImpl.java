package mx.com.consolida.dao.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.RolPantalla;


@Repository
public class RolPantallaDAOImpl  extends GenericDAO<RolPantalla, Long> implements RolPantallaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<PantallaDTO> getPantallasByIdRol(Long idRol){
		
		StringBuilder sb = new StringBuilder();
		sb.append("   select p.id_pantalla ,p.ind_estatus, p.nombre ,p.ruta_pantalla ,p.icono,p.fecha_alta , rp.id_rol_pantalla   ");
		sb.append("  from pantalla p  ");
		sb.append("  left join rol_pantalla rp on p.id_pantalla = rp.id_pantalla  ");
		sb.append("  and rp.id_rol = "+ idRol);
		
		 return jdbcTemplate.query(
				 sb.toString(),
	                (rs, rowNum) ->
	                        new PantallaDTO(
	                                rs.getLong("id_pantalla"),
	                                rs.getString("ind_Estatus"),
	                                rs.getString("nombre"),
	                                rs.getString("ruta_pantalla"),
	                                rs.getString("icono"),
	                                rs.getDate("fecha_alta"),
	                                rs.getLong("id_rol_pantalla"),
	                                idRol,
	                                ((rs.getLong("id_rol_pantalla") != 0l)? true:false)
	                        )
	        );
		
	}
}
