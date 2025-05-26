package mx.com.consolida.dao.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Rol;

@Repository
public class RolDAOImpl  extends GenericDAO<Rol, Long>  implements RolDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(RolDAOImpl.class);


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	public List<RolDTO> rolesCelula() {


			try {
				String inRolesString =
						RolUsuarioENUM.ADMINISTRADOR_CELULA.getId() +"," +
						//RolUsuarioENUM.GERENTE_CELULA.getId() +"," +
						RolUsuarioENUM.GERENTE_NOMINA.getId() +"," +
						RolUsuarioENUM.NOMINISTA.getId() +"," +
						//RolUsuarioENUM.IMPLEMENTADOR_CELULA.getId() +"," +
						RolUsuarioENUM.TESORERIA.getId() ;
						//RolUsuarioENUM.COORDINADOR_TESORERIA.getId() +"," +
						//RolUsuarioENUM.AUXILIAR_CONTABILIDAD.getId() +"," +
						//RolUsuarioENUM.COORDINADOR_CONTABILIDAD.getId()  ;


				StringBuilder sb = new StringBuilder();
				sb.append(" select id_rol,nombre, descripcion from rol where id_rol  in (" +inRolesString +") and ind_estatus = 1");

				return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

						RolDTO rol = new RolDTO();
						rol.setIdRol(rs.getLong("id_rol"));
						rol.setNombre(rs.getString("nombre"));
						rol.setDescripcion(rs.getString("descripcion"));


						return rol;

					}
				});

			}catch (Exception e) {
				LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
				return Collections.emptyList();
			}

		}
}
