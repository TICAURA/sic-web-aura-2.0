package mx.com.consolida.ppp.service.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.ppp.dao.interfaz.PppNominaDao;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.MultifacturaBO;


@Service
public class MultifacturaBOImpl implements MultifacturaBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MultifacturaBOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PppNominaDao nomimnaDao;
	

	@Override
	public List<NominaDto> listaNominaColabCargadosByIdCliente(Long idCliente, Long idNominista) {
		// TODO Auto-generated method stub
		return null;
	}


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominaVinculadasFactura(Long idFactura) {		
     try {
			
			StringBuilder sb = new StringBuilder();					
			sb.append("SELECT nom.id_ppp_nomina,  nom.clave, estnom.id_cat_estatus_nomina  ,estnom.descripcion, ");
			sb.append("(select ifnull(sum(monto_ppp), 0) from ppp_colaborador where id_ppp_nomina  IN (SELECT f.id_ppp_nomina FROM ppp_nomima_factura f WHERE f.id_ppp_nomina =facnom.id_ppp_nomina)) as monto ");
			sb.append(" FROM  ppp_factura_nomina facnom  ");
			sb.append(" LEFT JOIN PPP_NOMINA nom ON facnom.id_ppp_nomina=nom.id_ppp_nomina ");
			sb.append(" LEFT JOIN PPP_NOMINA_ESTATUS nomest ON nom.id_ppp_nomina=nomest.id_ppp_nomina ");
			sb.append(" LEFT JOIN CAT_ESTATUS_NOMINA estnom ON nomest.id_cat_estatus_nomina= estnom.id_cat_estatus_nomina ");
			sb.append(" WHERE facnom.id_ppp_factura= ? " );
			sb.append(" AND  nomest.ind_estatus =1 ");
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idFactura}, new RowMapper() {
				public NominaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNominaPPP(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setMontoTotalPpp(rs.getDouble("monto"));
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("descripcion")));
					
					return nominaDto;	
				}
			});
			
			
			return nomina;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
	return new ArrayList<NominaDto>();	
	}

	


	
	


}
