package mx.com.consolida.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.ColaboradorTempDao;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.ColaboradoresTemp;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.entity.CotizacionColaborador;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class ColaboradorTempDaoImpl extends GenericDAO<ColaboradoresTemp, Long> implements ColaboradorTempDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ColaboradorTempDaoImpl.class);
	
	@Transactional
	public ColaboradoresTempDto guardarColaborador(ColaboradoresTempDto cotizacionColaborador,  UsuarioAplicativo usuarioAplicativo) {
		try {
			ColaboradoresTemp entity = new ColaboradoresTemp();
			Long idCotizacion = cotizacionColaborador.getIdCotizacion().getIdCotizacion();
			cotizacionColaborador.setIdCotizacion(null);
			
			entity = new ColaboradoresTemp(cotizacionColaborador);
			
			entity.setIdCotizacion(new Cotizacion(idCotizacion));
			entity.setFechaAlta(new Date());
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
			entity.setIdColaboradorTemp(create(entity).longValue());
			return obtenerDtoColaboradoresTemp(entity);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaborador ",e);
			return new ColaboradoresTempDto();
		}
	}
	
	public ColaboradoresTempDto obtenerDtoColaboradoresTemp(ColaboradoresTemp entity){
		ColaboradoresTempDto dto = new ColaboradoresTempDto();
		dto.setIdColaboradorTemp(entity.getIdColaboradorTemp());
		dto.setSalarioDiario(entity.getSalarioDiario());
		dto.setSalarioDiarioIntegral(entity.getSalarioDiarioIntegral());
		dto.setSueldo(entity.getSueldo());
		dto.setGravados(entity.getGravados());
		dto.setExentos(entity.getExentos());
		dto.setSubsidio(entity.getSubsidio());
		dto.setIsr(entity.getIsr());
		dto.setCoImss(entity.getCoImss());
		dto.setNetoNomina(entity.getNetoNomina());
		dto.setFechaAlta(entity.getFechaAlta());
		dto.setFechaModificacion(entity.getFechaModificacion());
		dto.setUsuarioAlta(entity.getUsuarioAlta());
		dto.setUsuarioModificacion(entity.getUsuarioModificacion());
		dto.setIndEstatus(entity.getIndEstatus());
		dto.setIdCotizacion(entity.getIdCotizacion()!=null ? new CotizacionDto(entity.getIdCotizacion().getIdCotizacion()):null);
		return dto;
	}
	
	@Transactional
	public List<ColaboradoresTempDto> obtenercotizacionesColaboradoresByIdCot(Long idCotizacion) {
		List<ColaboradoresTempDto> list = new ArrayList<>(); 
		StringBuilder sb = new StringBuilder();
		sb.append("select ct.*, cc.aasAsimialdos as asimilados, cc.aOtros as otros, cc.dgPrimaDeRiesgo as primaRiesgo " + 
				"from Colaboradores_Temp ct join cotizacion_colaborador cc on ct.id_colaborador_temp = cc.id_colaborador_temp  and cc.ind_estatus = 1 " + 
				"where ct.ind_estatus = 1 and ct.id_cotizacion = " + idCotizacion);
		list = jdbcTemplate.query(sb.toString(),(rs, rowNum) -> new ColaboradoresTempDto(
	                        		rs.getLong("id_colaborador_temp"),
	                        		rs.getBigDecimal("salario_diario"),
	                        		rs.getBigDecimal("gravados"),
	                        		rs.getBigDecimal("exentos"),
	                                rs.getDate("fecha_antiguedad"),
	                                rs.getBigDecimal("neto_nomina"),
	                                rs.getBigDecimal("asimilados"),
	                                rs.getBigDecimal("otros"),
	                                rs.getString("primaRiesgo")
	                        )
	        );
		return list;
	}
	
	@Transactional
	public void eliminadoLogicoEmpleados(Long idCotizacion) {
		StringBuilder sb = new StringBuilder();
		sb.append("update sin.colaboradores_temp set ind_estatus = 0, fecha_modificacion = current_date() where ind_estatus = 1 and id_cotizacion = "+idCotizacion);
		jdbcTemplate.execute(sb.toString());
	}

	
}

