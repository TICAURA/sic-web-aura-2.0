package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.BeneficioAdicionalColaboradorDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.BeneficioAdicionalColaborador;

@Repository
public class BeneficioAdicionalDaoImpl extends GenericDAO<BeneficioAdicionalColaborador, Long> implements BeneficioAdicionalDao{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<BeneficioAdicionalColaboradorDto> obtenerBeneficioAdicionalByColab(Long idColaborador){
		
		String sql = "select "
				+ "bac.id_beneficio_adicional_colaborador, "
				+ "cg.id_cat_general as idCatGeneral, "
				+ "cg.clave, "
				+ "cg.descripcion, "
				+ "cg.ind_estatus as indEstatus \r\n" + 
				"from beneficio_adicional_colaborador bac \r\n" + 
				"join cat_general cg on cg.id_cat_general = bac.id_beneficio_adicional and bac.id_colaborador =  ?";
		List<BeneficioAdicionalColaboradorDto> list  = jdbcTemplate.query(sql, new Object[]{idColaborador}, (RowMapper<BeneficioAdicionalColaboradorDto>) (rs, rowNum) -> {
			BeneficioAdicionalColaboradorDto dto = new BeneficioAdicionalColaboradorDto();
			CatGeneralDto cat = new CatGeneralDto();
			cat.setIdCatGeneral(rs.getLong("idCatGeneral"));
			cat.setClave(rs.getString("clave"));
			cat.setDescripcion(rs.getString("descripcion"));
			cat.setIndEstatus(rs.getLong("indEstatus"));
			dto.setGeneral(cat);
			dto.setIdBeneficioAdicionalColaborador(rs.getLong("id_beneficio_adicional_colaborador"));
		    return dto;
		});
		return list;
	}
	
	public void borrarBeneficioAdicionalByColab(Long idColaborador) {
		String sql = " delete from beneficio_adicional_colaborador where id_colaborador = "+idColaborador;
		jdbcTemplate.execute(sql);
	}

	public void guardarBeneficioAdicional(BeneficioAdicionalColaborador entity) {
		create(entity);
	}
	
}
