package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CostoAdicionalDao;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CostoAdicionalDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.CostoAdicional;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class CostoAdicionalDaoImpl extends GenericDAO<CostoAdicional, Long> implements CostoAdicionalDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CostoAdicionalDaoImpl.class);

	@Override
	@Transactional
	public void guardarCostoAdicional(CostoAdicional costoAdicional) {
		if(costoAdicional.getIdCostoAdicional()!=null) {
			createOrUpdate(costoAdicional);
		}else {
			costoAdicional.setIdCostoAdicional(create(costoAdicional));
		}
		
	}
	
	@Override
	@Transactional
	public void borradoLogicoCostoAdicional(Long idCotizacion, Long idUsuarioAplicativo) {
		StringBuilder sb = new StringBuilder();
		sb.append("update sin.costo_adicional set ind_estatus = 0, fecha_modificacion = current_date(), usuario_modificacion = "+idUsuarioAplicativo+" where ind_estatus = 1 and id_cotizacion = " + idCotizacion);
		jdbcTemplate.execute(sb.toString());
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly=true)
	public CostoAdicional obtenerCostoAdicional(Long idCostoAdicional) {
		read(idCostoAdicional);
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly=true)
	public	
	CostoAdicionalDto obtenerCostoAdicionalByIdCotizacion(Long idCotizacion) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select ca.id_costo_adicional as idCostoAdicional, \r\n" + 
					"ca.fecha_alta as fechaAlta,\r\n" + 
					"ca.fecha_modificacion as fechaModificaicon,\r\n" + 
					"ca.id_cliente_temp as idClienteTemp,\r\n" + 
					"ca.id_cotizacion as idCotizacion, \r\n" + 
					"ca.id_porcentaje_costo_estrategia as idPorcentajeCostoEstrategia,\r\n" + 
					"cvd1.valor as valorEstrategia, \r\n" + 
					"ca.id_porcentaje_costos_indirectos as idPorcentajeCostosIndirectos,\r\n" + 
					"cvd2.valor as valorIndirectos, \r\n" + 
					"ca.id_porcentaje_promotor as idPorcentajePromotor,\r\n" + 
					"cvd3.valor as valorPromotor, \r\n" + 
					"ca.id_valor_timbre as idValorTimbre,\r\n" + 
					"cvd4.valor as valorTimbre, \r\n" + 
					"ca.id_valo_spei as idValoSpei,\r\n" + 
					"cvd5.valor as valorSpei, \r\n" + 
					"ca.id_porcentaje_promotor_imss as idPorcentajePromotorImss,\r\n" + 
					"cvd6.valor as valorPromotorImss, \r\n" + 
					"ca.ind_estatus as indEstatus,\r\n" + 
					"ca.monto_implant as montoImplant,\r\n" + 
					"ca.monto_sgmm as montoSgmm,\r\n" + 
					"ca.porcentaje_cliente_implant as porcentajeClienteImplant,\r\n" + 
					"ca.porcentaje_cliente_sgmm as porcentajeClienteSgmm,\r\n" + 
					"ca.porcentaje_corporativo_implant as porcentajeCorporativoImplant,\r\n" + 
					"ca.porcentaje_corporativo_sgmm as porcentajeCorporativoSgmm,\r\n" + 
					"ca.porcentaje_promotor_implant as porcentajePromotorImplant,\r\n" + 
					"ca.porcentaje_promotor_sgmm as porcentajePromotorSgmm,\r\n" + 
					"ca.usuario_alta as usuarioAlta,\r\n" + 
					"ca.usuario_modificacion as usuarioModificacion  \r\n" + 
					"from costo_adicional ca  \r\n" + 
					"left join cat_valor_default cvd1 on ca.id_porcentaje_costo_estrategia = cvd1.id_cat_valor_default \r\n" + 
					"left join cat_valor_default cvd2 on ca.id_porcentaje_costos_indirectos = cvd2.id_cat_valor_default \r\n" + 
					"left join cat_valor_default cvd3 on ca.id_porcentaje_promotor = cvd3.id_cat_valor_default \r\n" + 
					"left join cat_valor_default cvd4 on ca.id_valor_timbre = cvd4.id_cat_valor_default \r\n" + 
					"left join cat_valor_default cvd5 on ca.id_valo_spei = cvd5.id_cat_valor_default \r\n" + 
					"left join cat_valor_default cvd6 on ca.id_porcentaje_promotor_imss = cvd6.id_cat_valor_default "
					+ " where ca.ind_estatus = 1 and ca.id_cotizacion = "+ idCotizacion);
//			Long id = null;
			List<CostoAdicionalDto> request = new ArrayList<CostoAdicionalDto>();
			List<CostoAdicionalDto> list = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CostoAdicionalDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//					id=rs.getLong("id_costo_adicional");
					CostoAdicionalDto dto = new CostoAdicionalDto();
					dto.setIdCostoAdicional(rs.getLong("idCostoAdicional"));
					dto.setFechaAlta(rs.getDate("fechaAlta"));
					dto.setFechaModificaicon(rs.getDate("fechaModificaicon"));
					dto.setIdClienteTemp(new ClienteTempDto(rs.getLong("idClienteTemp")));
					dto.setIdCotizacion(new CotizacionDto(rs.getLong("idCotizacion")));
					
					dto.setIdPorcentajeCostoEstrategia(new CatValorDefaultDto(rs.getLong("idPorcentajeCostoEstrategia"),rs.getBigDecimal("valorEstrategia")));
					dto.setIdPorcentajeCostosIndirectos(new CatValorDefaultDto(rs.getLong("idPorcentajeCostosIndirectos"),rs.getBigDecimal("valorIndirectos")));
					dto.setIdPorcentajePromotor(new CatValorDefaultDto(rs.getLong("idPorcentajePromotor"),rs.getBigDecimal("valorPromotor")));
					dto.setIdValorTimbre(new CatValorDefaultDto(rs.getLong("idValorTimbre"),rs.getBigDecimal("valorTimbre")));
					dto.setIdValoSpei(new CatValorDefaultDto(rs.getLong("idValoSpei"),rs.getBigDecimal("valorSpei")));
					dto.setIdPorcentajePromotorImss(new CatValorDefaultDto(rs.getLong("idPorcentajePromotorImss"),rs.getBigDecimal("valorPromotorImss")));

					
					dto.setIndEstatus(rs.getLong("indEstatus"));
					dto.setMontoImplant(rs.getBigDecimal("montoImplant"));
					dto.setMontoSgmm(rs.getBigDecimal("montoSgmm"));
					dto.setPorcentajeClienteImplant(rs.getBigDecimal("porcentajeClienteImplant"));
					dto.setPorcentajeClienteSgmm(rs.getBigDecimal("porcentajeClienteSgmm"));
					dto.setPorcentajeCorporativoImplant(rs.getBigDecimal("porcentajeCorporativoImplant"));
					dto.setPorcentajeCorporativoSgmm(rs.getBigDecimal("porcentajeCorporativoSgmm"));
					dto.setPorcentajePromotorImplant(rs.getBigDecimal("porcentajePromotorImplant"));
					dto.setPorcentajePromotorSgmm(rs.getBigDecimal("porcentajePromotorSgmm"));
					dto.setUsuarioAlta(new UsuarioDTO(rs.getLong("usuarioAlta")));
					dto.setUsuarioModificacion(new UsuarioDTO(rs.getLong("usuarioModificacion")));
					request.add(dto);
					return dto;
				}
			});
			if(request !=null  && !request.isEmpty()) {
			 return request.get(0);
			}else {
				return new CostoAdicionalDto();
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatProducto ", e.getMessage());
			return new CostoAdicionalDto();
		}
	}

	
}

