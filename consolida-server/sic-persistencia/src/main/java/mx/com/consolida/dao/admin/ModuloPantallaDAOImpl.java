package mx.com.consolida.dao.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.ModuloPantalla;

@Repository
public class ModuloPantallaDAOImpl extends GenericDAO<ModuloPantalla, Long> implements ModuloPantallaDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<PantallaDTO> getPantallaByIdModulo(Long idModulo){
			
			StringBuilder sb = new StringBuilder();
			sb.append("  select p.id_pantalla , p.nombre ,p.ruta_pantalla ,p.icono , p.fecha_alta , mp.id_modulo_pantalla , mp.id_modulo ,p.ind_estatus from pantalla p  ");
			sb.append("  left join (select id_modulo_pantalla, id_modulo,id_pantalla from modulo_pantalla where id_modulo = " + idModulo + ") mp on p.id_pantalla = mp.id_pantalla   ");
			sb.append("  order by mp.id_modulo_pantalla desc , p.id_pantalla ");
			
			 
			 return jdbcTemplate.query(
					 sb.toString(),
		                (rs, rowNum) ->
		                        new PantallaDTO(
		                                rs.getLong("id_pantalla"),
		                                rs.getString("ind_estatus"),
		                                rs.getString("nombre"),
		                                rs.getString("ruta_pantalla"),
		                                rs.getString("icono"),
		                                rs.getDate("fecha_alta"),
		                                rs.getLong("id_modulo_pantalla"),
		                                ((rs.getLong("id_modulo_pantalla") != 0l)? true:false),
		                                rs.getLong("id_modulo")
		                         
		                        )
		        );
			
		}
}
