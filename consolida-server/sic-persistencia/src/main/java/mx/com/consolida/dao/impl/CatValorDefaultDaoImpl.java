package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatValorDefaultDao;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.entity.CatValorDefault;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class CatValorDefaultDaoImpl extends GenericDAO<CatValorDefault, Long> implements CatValorDefaultDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CatValorDefaultDaoImpl.class);
	

    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public CatValorDefaultDto obtenerCatValorDefaultById(Long id) {
		try {
			final List<CatValorDefaultDto> list = new ArrayList<CatValorDefaultDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select cvd.id_cat_valor_default as id_cat_valor_default, "
					+ " cvd.valor as valor, cg.id_cat_general as id_cat_general, "
					+ " cg.clave as clave, cg.descripcion as descripcion, cvd.predeterminado from \r\n" + 
					" cat_valor_default cvd\r\n" + 
					" join cat_general cg on cg.id_cat_general = cvd.id_tipo_default\r\n" + 
					" where cvd.ind_estatus=1 and cvd.id_tipo_default = "+id);

			List<CatValorDefaultDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CatValorDefaultDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatValorDefaultDto producto = new CatValorDefaultDto();

					producto.setIdCatValorDefault(rs.getLong("id_cat_valor_default"));
					producto.setValor(rs.getBigDecimal("valor"));
					producto.setIdTipoDefault(new CatGeneralDto(rs.getLong("id_cat_general"), rs.getString("clave"), rs.getString("descripcion")));
					producto.setPredeterminado(rs.getLong("predeterminado"));
					list.add(producto);
					return producto;
				}
			});
			return list.get(0);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatProducto ", e.getMessage());
			return null;
		}
	}
    
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
    public CatValorDefaultDto obtenerCatValorDefaultByIdPrincipal(Long id) {
    	try {
			final List<CatValorDefaultDto> list = new ArrayList<CatValorDefaultDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select cvd.id_cat_valor_default as id_cat_valor_default, "
					+ " cvd.valor as valor, cg.id_cat_general as id_cat_general, "
					+ " cg.clave as clave, cg.descripcion as descripcion, cvd.predeterminado from \r\n" + 
					" cat_valor_default cvd\r\n" + 
					" join cat_general cg on cg.id_cat_general = cvd.id_tipo_default\r\n" + 
					" where cvd.predeterminado = 1 and cvd.ind_estatus=1 and cvd.id_tipo_default = "+id);

			List<CatValorDefaultDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CatValorDefaultDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatValorDefaultDto producto = new CatValorDefaultDto();

					producto.setIdCatValorDefault(rs.getLong("id_cat_valor_default"));
					producto.setValor(rs.getBigDecimal("valor"));
					producto.setIdTipoDefault(new CatGeneralDto(rs.getLong("id_cat_general"), rs.getString("clave"), rs.getString("descripcion")));
					producto.setPredeterminado(rs.getLong("predeterminado"));
					list.add(producto);
					return producto;
				}
			});
			return list.get(0);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatProducto ", e.getMessage());
			return null;
		}
    }
    
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
    public List<CatValorDefaultDto> obtenerListCatValorDefaultById(Long id,Long indEstatus){
    	try {
			final List<CatValorDefaultDto> list = new ArrayList<CatValorDefaultDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select cvd.id_cat_valor_default as id_cat_valor_default, "
					+ " cvd.valor as valor, cg.id_cat_general as id_cat_general, "
					+ " cg.clave as clave, cg.descripcion as descripcion, cvd.predeterminado from \r\n" + 
					" cat_valor_default cvd\r\n" + 
					" join cat_general cg on cg.id_cat_general = cvd.id_tipo_default\r\n");
			
			sb.append(" where ");
			if(indEstatus != null) {
				sb.append(" cvd.ind_estatus="+indEstatus+" and ");
			}
			
			sb.append(" cvd.id_tipo_default = "+id);

			List<CatValorDefaultDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CatValorDefaultDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatValorDefaultDto producto = new CatValorDefaultDto();

					producto.setIdCatValorDefault(rs.getLong("id_cat_valor_default"));
					producto.setValor(rs.getBigDecimal("valor"));
					producto.setIdTipoDefault(new CatGeneralDto(rs.getLong("id_cat_general"),rs.getString("clave"), rs.getString("descripcion")));
					producto.setPredeterminado(rs.getLong("predeterminado"));
					list.add(producto);
					return producto;
				}
			});
			return list;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatProducto ", e.getMessage());
			return null;
		}
    }
    
    @Override
    @Transactional(readOnly=true)
    public CatValorDefaultDto guardarCatValorDefault(CatValorDefaultDto dto, UsuarioAplicativo us) {
    	if(dto.getIdCatValorDefault()!=null) {
    		apagarEstatusLogicoByidCatValorDefault(dto.getIdCatValorDefault());
    	}
    	CatValorDefault entity = new CatValorDefault(dto);
    	entity.setIdCatValorDefault(null);
    	entity.setFechaAlta(new Date());
    	entity.setIndEstatus(1L);
    	entity.setUsuarioAlta(new Usuario(us.getIdUsuario()));

    	dto.setIdCatValorDefault(create(entity));
    	return dto;
    }
    
    @Transactional(readOnly=true)
    public void apagarEstatusLogicoByidCatValorDefault(Long idCatValorDefault){
		String slq = "update cat_valor_default set ind_estatus = 0 where id_cat_valor_default = "+idCatValorDefault;
		jdbcTemplate.execute(slq.toString());
	}
	
}
