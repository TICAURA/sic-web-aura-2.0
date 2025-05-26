package mx.com.consolida.service.conciliacion;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.conciliaciones.CargaOrdenesPagoDto;
import mx.com.consolida.conciliaciones.ClientesDetalleDto;
import mx.com.consolida.conciliaciones.DetalleConciliacionesCCDto;
import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.conciliaciones.TotalesConciliacionesDto;
import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.dao.conciliacion.CargaOrdenPagoDao;
import mx.com.consolida.dao.conciliacion.OrdenPagoDao;
import mx.com.consolida.dao.conciliacion.OrdenPagoEstatusDao;
import mx.com.consolida.entity.conciliacion.CargaOrdenPago;
import mx.com.consolida.entity.conciliacion.OrdenPago;
import mx.com.consolida.entity.conciliacion.ordenPagoEstatus;
import mx.com.consolida.entity.ppp.PppFacturaDeposito;
import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.ppp.dao.interfaz.PppFacturaDepositoDao;
import mx.com.consolida.util.ConstantesComunes;

@Service
public class CargaOrdenPagoBOImpl implements CargaOrdenPagoBO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CargaOrdenPagoBOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OrdenPagoDao ordenPagoDao;

	@Autowired
	private CargaOrdenPagoDao cargaOrdenPagoDao;
	
	@Autowired
	private OrdenPagoEstatusDao ordenPagoEstatusDao;
	@Autowired
	private PppFacturaDepositoDao pppFacturaDepositoDao;

	@Override
	public List<OrdenPagoDto> getOrdenesPago() {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT id_orden_pago, id_carga_orden_pago, id_orden_origen");
			sb.append(" , folio_orden, institucion, concepto_pago, beneficiario, cta_beneficiario  ");
			sb.append(" , contraparte, monto, rastreo, ordenante, cta_ordenante, fecha_operacion  ");
			sb.append(" , folio_orden_cep, nombre_cliente_cep, fecha_captura, fecha_liquidacion  ");
			sb.append(" , ind_estatus  ");
			sb.append(" FROM orden_pago op   ");
			sb.append(" WHERE op.ind_estatus = 1 and op.id_carga_orden_pago =? ");

			List<OrdenPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public OrdenPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					OrdenPagoDto carga = new OrdenPagoDto();
					carga.setIdOrdenPago(rs.getLong("id_orden_pago"));
					carga.setIdOrdenOrigen(rs.getLong("id_orden_origen"));
					carga.setFolioOrden(rs.getLong("folio_orden"));
					carga.setInstitucion(rs.getString("institucion"));
					carga.setConceptoPago(rs.getString("concepto_pago"));
					carga.setBeneficiario(rs.getString("beneficiario"));
					carga.setCtaBeneficiario(rs.getString("cta_beneficiario"));
					carga.setContraparte(rs.getString("contraparte"));
					carga.setMonto(rs.getBigDecimal("monto"));
					carga.setRastreo(rs.getString("rastreo"));
					carga.setOrdenante(rs.getString("ordenante"));
					carga.setCtaOrdenante(rs.getString("cta_ordenante"));
					carga.setFechaOperacion(rs.getDate("fecha_operacion"));
					carga.setFolioOrdenCep(rs.getLong("folio_orden_cep"));
					carga.setNombreClienteCep(rs.getString("nombre_cliente_cep"));
					carga.setFechaCaptura(rs.getDate("fecha_captura"));
					carga.setFechaLiquidacion(rs.getDate("fecha_liquidacion"));
					carga.setDescError("ok");

					return carga;
				}
			});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<OrdenPagoDto>();
	}

	public BigDecimal getTotalIngresos(OrdenPagoDto orden) {

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ifnull(sum(monto),0.00) totalIngresos ");
		sb.append(" FROM orden_pago op   ");
		sb.append(" WHERE op.ind_estatus = 1 ");
		sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
		.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
		
		
		/*if (mesAnio.length() == 6) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m') ='" + mesAnio + "'");
		}

		if (mesAnio.length() == 4) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y') ='" + mesAnio + "'");
		}

		if (mesAnio.length() == 8) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01" + "'");
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m%d') <='" + mesAnio + "'");
		}*/

		BigDecimal prueba = jdbcTemplate.queryForObject(sb.toString(), BigDecimal.class);

		return prueba;

	}

	public TotalesConciliacionesDto getTotales(OrdenPagoDto orden) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" select  ifnull(round(sum(monto),2),0) depositado,  ifnull(round(sum(disponible),2),0) disponible \r\n");
			sb.append(", ifnull(sum(case when  cta_ordenante >0 then 0 else monto end ),0) sinIdentificar \r\n");
			sb.append("from (\r\n");
			sb.append("select ifnull(co.cta_ordenante,0) cta_ordenante, monto, ifnull( co.costo,0) costo\r\n");
			sb.append(", case when ifnull(costo,0)<= 0 then  monto else monto/(1+(costo/100)) end  disponible\r\n");
			sb.append(" from orden_pago op \r\n");
			sb.append(" left join cta_ordenante co on op.cta_ordenante  = co.cta_ordenante  \r\n");
			sb.append("WHERE op.ind_estatus = 1 \r\n");
			
			if (orden.getAnioMesDia().length() == 8) {
				sb.append("and  date_format( op.fecha_liquidacion , '%Y%m%d') >='"
						+ orden.getAnioMesDia().substring(0, 6) + "01" + "'\r\n");
				sb.append("and date_format( op.fecha_liquidacion , '%Y%m%d') <='" + orden.getAnioMesDia() + "'\r\n");
			}
			if (orden.getAnioMesDia().length() == 6) {
				sb.append("and date_format( op.fecha_liquidacion , '%Y%m')='" + orden.getAnioMesDia() + "'\r\n");
			}
			if (orden.getAnioMesDia().length() == 4) {
				sb.append("and date_format( op.fecha_liquidacion , '%Y')='" + orden.getAnioMesDia() + "'\r\n");
			}
			if (orden.getRfcOrdenante() != null && orden.getRfcOrdenante().length() > 0) {
				sb.append("and co.rfc ='" + orden.getRfcOrdenante() + "'\r\n");
			}
			sb.append(") d  ");

			List<TotalesConciliacionesDto> listaTotales = jdbcTemplate.query(sb.toString(), new Object[] {},
					new RowMapper() {
						public TotalesConciliacionesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							TotalesConciliacionesDto totales = new TotalesConciliacionesDto();
							totales.setTotalDepositos(rs.getDouble("depositado"));
							totales.setTotalDisponible(rs.getDouble("disponible"));
							totales.setTotalSinIdentificar(rs.getDouble("sinIdentificar"));
							totales.setTotalIdentificado(
									totales.getTotalDepositos() - totales.getTotalSinIdentificar());

							return totales;
						}

					});

			if (listaTotales.size() > 0) {
				return listaTotales.get(0);
			}
			return null;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
		return null;

	}
	
	public TotalesConciliacionesDto getTotalesDate(OrdenPagoDto orden) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" select  ifnull(round(sum(monto),2),0) depositado,  ifnull(round(sum(disponible),2),0) disponible \r\n");
			sb.append(", ifnull(sum(case when  cta_ordenante >0 then 0 else monto end ),0) sinIdentificar \r\n");
			sb.append("from (\r\n");
			sb.append("select ifnull(co.cta_ordenante,0) cta_ordenante, monto, ifnull( co.costo,0) costo\r\n");
			sb.append(", case when ifnull(costo,0)<= 0 then  monto else monto/(1+(costo/100)) end  disponible\r\n");
			sb.append(" from orden_pago op \r\n");
			sb.append(" left join cta_ordenante co on op.cta_ordenante  = co.cta_ordenante  \r\n");
			sb.append("WHERE op.ind_estatus = 1 \r\n");
			
			sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			
			sb.append(") d  ");

			List<TotalesConciliacionesDto> listaTotales = jdbcTemplate.query(sb.toString(), new Object[] {},
					new RowMapper() {
						public TotalesConciliacionesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							TotalesConciliacionesDto totales = new TotalesConciliacionesDto();
							totales.setTotalDepositos(rs.getDouble("depositado"));
							totales.setTotalDisponible(rs.getDouble("disponible"));
							totales.setTotalSinIdentificar(rs.getDouble("sinIdentificar"));
							totales.setTotalIdentificado(
									totales.getTotalDepositos() - totales.getTotalSinIdentificar());

							return totales;
						}

					});

			if (listaTotales.size() > 0) {
				return listaTotales.get(0);
			}
			return null;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
		return null;

	}

	public BigDecimal getTotalIngresosCliente(OrdenPagoDto orden) {

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ifnull(sum(monto),0) totalIngresos ");
		sb.append(" FROM orden_pago op   ");
		sb.append("  join cta_ordenante co on op.cta_ordenante = co.cta_ordenante ");
		sb.append(" WHERE op.ind_estatus = 1  ");
		sb.append("  and co.rfc= '" + orden.getRfcOrdenante() + "'");
		if (orden.getAnioMesDia().length() == 8) {
			sb.append(" and  date_format(op.fecha_liquidacion, '%Y%m%d')>='" + orden.getAnioMesDia().substring(0, 6)
					+ "01" + "' and date_format(op.fecha_liquidacion, '%Y%m%d')<='" + orden.getAnioMesDia() + "' \r\n");
		}
		if (orden.getAnioMesDia().length() == 6) {
			sb.append(" and date_format(op.fecha_liquidacion, '%Y%m') ='" + orden.getAnioMesDia() + "' \r\n");
		}
		if (orden.getAnioMesDia().length() == 4) {
			sb.append(" and date_format(op.fecha_liquidacion, '%Y') ='" + orden.getAnioMesDia() + "' \r\n");
		}

		return jdbcTemplate.queryForObject(sb.toString(), BigDecimal.class);

	}

	public Double getTotalEgresos(OrdenPagoDto orden) {

		StringBuilder sb = new StringBuilder();
		sb.append(" select ifnull(sum( pc.monto_ppp),0) totalEgreso  from cliente c\r\n"
				+ "join nomina_cliente nc ON c.id_cliente = nc.id_cliente \r\n"
				+ "join ppp_nomina pn  on nc.id_nomina_cliente = pn.id_nomina_cliente \r\n"
				+ "join ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n"
				+ "join ppp_colaborador_estatus pce on pc.id_ppp_colaborador = pce.id_ppp_colaborador \r\n"
				+ "join (select id_ppp_colaborador,  max(id_ppp_colaborador_estatus)  id_ppp_colaborador_estatus\r\n"
				+ "				from ppp_colaborador_estatus pce \r\n"
				+ "			group by id_ppp_colaborador ) pcem \r\n"
				+ "			on pce.id_ppp_colaborador_estatus =pcem.id_ppp_colaborador_estatus\r\n"
				+ " join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente\r\n" + 
				" join prestadora_servicio_stp pss on cps.id_prestadora_servicio_fondo= pss.id_prestadora_servicio_stp\r\n" + 
				" left join cliente_cuenta_bancaria ccb on c.id_cliente = ccb.clabe_interbancaria and ccb.ind_estatus =1 \r\n" + 
				"where pce.id_cat_estatus_colaborador in (12,3) " + " and c.ind_estatus =1  and nc.ind_estatus =1  "
				+ "and  pn.ind_estatus =1 and pce.ind_estatus=1 ");
		sb.append(" and cast(pce.fecha_alta  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
		.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
		
		/*if (mesAnio.length() == 6) {
			sb.append(" and date_format(pce.fecha_alta, '%Y%m') ='" + mesAnio + "'");
		}

		if (mesAnio.length() == 4) {
			sb.append("and date_format(pce.fecha_alta, '%Y') ='" + mesAnio + "'");
		}

		if (mesAnio.length() == 8) {
			sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01" + "'");
			sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') <='" + mesAnio + "'");
		}*/

		return jdbcTemplate.queryForObject(sb.toString(), Double.class);

	}

	public Double getTotalEgresosCliente(OrdenPagoDto orden) {

		StringBuilder sb = new StringBuilder();
		sb.append(" select ifnull( sum( pc.monto_ppp),0) totalEgreso  from cliente c\r\n"
				+ "join nomina_cliente nc ON c.id_cliente = nc.id_cliente \r\n"
				+ "join ppp_nomina pn  on nc.id_nomina_cliente = pn.id_nomina_cliente \r\n"
				+ "join ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n"
				+ "join ppp_colaborador_estatus pce on pc.id_ppp_colaborador = pce.id_ppp_colaborador \r\n"
				+ "join (select id_ppp_colaborador,  max(id_ppp_colaborador_estatus)  id_ppp_colaborador_estatus\r\n"
				+ "				from ppp_colaborador_estatus pce \r\n"
				+ "			group by id_ppp_colaborador ) pcem \r\n"
				+ "			on pce.id_ppp_colaborador_estatus =pcem.id_ppp_colaborador_estatus\r\n"
				+ "where pce.id_cat_estatus_colaborador in (12,3)" + "and c.ind_estatus =1  and nc.ind_estatus =1\r\n"
				+ "and  pn.ind_estatus and pce.ind_estatus=1 and c.id_cliente= '" + orden.getRfcOrdenante() + "'");
		if (orden.getAnioMesDia().length() == 8) {
			sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') ='" + orden.getAnioMesDia() + "' \r\n");
		}
		if (orden.getAnioMesDia().length() == 6) {
			sb.append(" and date_format(pce.fecha_alta, '%Y%m') ='" + orden.getAnioMesDia() + "' \r\n");
		}
		if (orden.getAnioMesDia().length() == 4) {
			sb.append(" and date_format(pce.fecha_alta, '%Y') ='" + orden.getAnioMesDia() + "' \r\n");
		}

		return jdbcTemplate.queryForObject(sb.toString(), Double.class);

	}

	public Long getClienteEgresos(OrdenPagoDto orden) {

//	Date fecha = new Date();

		StringBuilder sb = new StringBuilder();

		sb.append(" select count(cte.rfc) totalClientes from  	(select c.rfc  from cliente c \r\n");
		sb.append("join nomina_cliente nc ON c.id_cliente = nc.id_cliente \r\n");
		sb.append("join ppp_nomina pn  on nc.id_nomina_cliente = pn.id_nomina_cliente \r\n");
		sb.append("join ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n");
		sb.append("join ppp_colaborador_estatus pce on pc.id_ppp_colaborador = pce.id_ppp_colaborador \r\n");
		sb.append("join (select id_ppp_colaborador,  max(id_ppp_colaborador_estatus)  id_ppp_colaborador_estatus\r\n");
		sb.append("				from ppp_colaborador_estatus pce \r\n");
		sb.append("			group by id_ppp_colaborador ) pcem \r\n");
		sb.append("			on pce.id_ppp_colaborador_estatus =pcem.id_ppp_colaborador_estatus\r\n");
		sb.append("where pce.id_cat_estatus_colaborador in (12,3) ");
		sb.append(" and cast(pce.fecha_alta  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
		.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				
		/*if (mesAnio.length() == 6) {
			sb.append(" and date_format( pce.fecha_alta  , '%Y%m') ='" + mesAnio + "'");
		}
		if (mesAnio.length() == 4) {
			sb.append(" and date_format( pce.fecha_alta  , '%Y') ='" + mesAnio + "'");
		}
		if (mesAnio.length() == 8) {
			sb.append(" and date_format( pce.fecha_alta  , '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01" + "'");
			sb.append(" and date_format( pce.fecha_alta  , '%Y%m%d') <='" + mesAnio + "'");
		}
		sb.append(" and c.ind_estatus =1  and nc.ind_estatus =1 " + "and pn.ind_estatus and pce.ind_estatus=1 \r\n ");*/
		sb.append("group by c.rfc) cte");

		// jdbcTemplate.queryForObject(sb.toString(), Long.class);
		return jdbcTemplate.queryForObject(sb.toString(), Long.class);

	}

	public Long getClienteIngresos(OrdenPagoDto orden) {

		StringBuilder sb = new StringBuilder();
		sb.append("  select count(*) totalClientesIngresos from\r\n" + 
				" (select ifnull(c.rfc,\"Sin identificar\") totalClientesIngresos\r\n" + 
				" from orden_pago op  \r\n" + 
				" left join  cta_ordenante co on co.cta_ordenante = op.cta_ordenante  \r\n" + 
				" left join cliente c on co.rfc=c.rfc \r\n" + 
				" left join cliente_prestadora_servicio cps  on c.id_cliente = cps.id_cliente\r\n" + 
				" left join prestadora_servicio ps \r\n" + 
				" on cps.id_prestadora_servicio_fondo  = ps.id_prestadora_servicio\r\n" + 
				" where op.ind_estatus =1 \r\n");
		sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
		.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
					
		/*if (mesAnio.length() == 6) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m') = '" + mesAnio + "' ");
		}
		if (mesAnio.length() == 8) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m%d') >= '" + mesAnio.substring(0, 6) + "01" + "'");
			sb.append(" and date_format( op.fecha_liquidacion , '%Y%m%d') <= '" + mesAnio + "' ");
		}

		if (mesAnio.length() == 4) {
			sb.append(" and date_format( op.fecha_liquidacion , '%Y') = '" + mesAnio + "' ");
		}*/
		sb.append("		 group by  c.rfc) clientes ");

		

		return jdbcTemplate.queryForObject(sb.toString(), Long.class);

	}

	public List<CargaOrdenesPagoDto> getCargaOrdenesPagoByCarga(Long id_carga) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT c.id_carga_orden_pago, c.descripcion, c.usuario_alta, u.nombre, c.fecha_alta  ");
			sb.append(" c.fecha_modificacion   ");
			sb.append(" FROM carga_orden_pago c   ");
			sb.append(" join usuario u on c.usuario_alta = u.id_usuario   ");
			sb.append(" WHERE c.ind_estatus = 1 ");

			List<CargaOrdenesPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new Object[] {},
					new RowMapper() {
						public CargaOrdenesPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							CargaOrdenesPagoDto carga = new CargaOrdenesPagoDto();
							carga.setIdCargaOrdenPago(rs.getLong("id_carga_orden_pago"));
							carga.setDescCarga(rs.getString("descripcion"));
							carga.setUsuarioAlta(rs.getString("nombre"));
							carga.setIdUsuarioAlta(rs.getLong("usuario_alta"));
							carga.setFechaCarga(rs.getDate("fecha_alta"));

							return carga;
						}
					});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
		return new ArrayList<CargaOrdenesPagoDto>();

	}

	@Override
	@Transactional
	public void guardaOrdenesPagos(CargaOrdenesPagoDto cargaOrdenDto) {
		try {
			CargaOrdenPago cargaOrdenPago = new CargaOrdenPago();

			cargaOrdenPago.setDescripcion(cargaOrdenDto.getDescCarga());
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(cargaOrdenDto.getIdUsuarioAlta());
			cargaOrdenPago.setUsuarioAlta(usuario);
			cargaOrdenPago.setFechaAlta(new Date());
			cargaOrdenPago.setIndEstatus(1L);

			cargaOrdenPagoDao.createOrUpdate(cargaOrdenPago);

			for (OrdenPagoDto ordenDto : cargaOrdenDto.getOrdenesPago()) {
				if ("OK".equals(ordenDto.getDescError())) {
					Usuario usuarioalta = new Usuario();
					usuarioalta.setIdUsuario(cargaOrdenDto.getIdUsuarioAlta());
					OrdenPago orden = new OrdenPago();
					orden.setIdOrdenOrigen(ordenDto.getIdOrdenOrigen());
					orden.setFolioOrden(ordenDto.getFolioOrden());
					orden.setCargaOrdenPago(cargaOrdenPago);
					orden.setInstitucion(ordenDto.getInstitucion());
					orden.setConceptoPago(ordenDto.getConceptoPago());
					orden.setBeneficiario(ordenDto.getBeneficiario());
					orden.setCtaBeneficiario(ordenDto.getCtaBeneficiario());
					orden.setContraparte(ordenDto.getContraparte());
					orden.setMonto(ordenDto.getMonto());
					orden.setRastreo(ordenDto.getRastreo());
					orden.setOrdenante(ordenDto.getOrdenante());
					orden.setCtaOrdenate(ordenDto.getCtaOrdenante());
					orden.setFolioOrdenCep(ordenDto.getFolioOrdenCep());
					orden.setNombreClienteCep(ordenDto.getNombreClienteCep());
					orden.setCausaDevolucion(ordenDto.getCausaDevolucion());
					orden.setFechaOperacion(ordenDto.getFechaOperacion());
					orden.setFechaCaptura(ordenDto.getFechaCaptura());
					orden.setFechaLiquidacion(ordenDto.getFechaLiquidacion());
					orden.setFechaAlta(new Date());
					orden.setUsuarioAlta(usuario);
					orden.setIndEstatus(1L);

					ordenPagoDao.createOrUpdate(orden);
				}

			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarNomina ", e);
			// return Boolean.FALSE;
		}

	}

	@Override
	public List<OrdenPagoDto> getOrdenesPagoByIdCarga(long idCarga) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT id_orden_pago, id_carga_orden_pago, id_orden_origen");
			sb.append(" , folio_orden, institucion, concepto_pago, beneficiario, cta_beneficiario  ");
			sb.append(" , contraparte, monto, rastreo, ordenante, cta_ordenante, fecha_operacion  ");
			sb.append(" , folio_orden_cep, nombre_cliente_cep, fecha_captura, fecha_liquidacion  ");
			sb.append(" , causa_devolucion, ind_estatus  ");
			sb.append(" FROM orden_pago op   ");
			sb.append(" WHERE op.ind_estatus = 1 and op.id_carga_orden_pago =? ");

			List<OrdenPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new Object[] { idCarga },
					new RowMapper() {
						public OrdenPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							OrdenPagoDto carga = new OrdenPagoDto();
							carga.setIdOrdenPago(rs.getLong("id_orden_pago"));
							carga.setIdOrdenOrigen(rs.getLong("id_orden_origen"));
							carga.setFolioOrden(rs.getLong("folio_orden"));
							carga.setInstitucion(rs.getString("institucion"));
							carga.setConceptoPago(rs.getString("concepto_pago"));
							carga.setBeneficiario(rs.getString("beneficiario"));
							carga.setCtaBeneficiario(rs.getString("cta_beneficiario"));
							carga.setContraparte(rs.getString("contraparte"));
							carga.setMonto(rs.getBigDecimal("monto"));
							carga.setRastreo(rs.getString("rastreo"));
							carga.setOrdenante(rs.getString("ordenante"));
							carga.setCtaOrdenante(rs.getString("cta_ordenante"));
							carga.setFechaOperacion(rs.getDate("fecha_operacion"));
							carga.setFolioOrdenCep(rs.getLong("folio_orden_cep"));
							carga.setNombreClienteCep(rs.getString("nombre_cliente_cep"));
							carga.setFechaCaptura(rs.getDate("fecha_captura"));
							carga.setFechaLiquidacion(rs.getTimestamp("fecha_liquidacion"));
							carga.setTiempoLiquidacion(rs.getTime("fecha_liquidacion"));
							carga.setCausaDevolucion(rs.getString("causa_devolucion"));
							carga.setEstatus(1);
							carga.setDescError("ok");

							return carga;
						}
					});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<OrdenPagoDto>();
	}

	@Override
	public List<OrdenPagoDto> getDetalleIngresos(OrdenPagoDto orden) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" select op.beneficiario, op.cta_beneficiario, op.ordenante, op.cta_ordenante, monto, co.rfc, co.costo \r\n"
							+ " from orden_pago op  \r\n"
							+ " left join cta_ordenante co on co.cta_ordenante = op	.cta_ordenante "
							+ " where op.ind_estatus =1");
			sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
						
			/*if (mesAnio.length() == 4) {
				sb.append(" and date_format(op.fecha_liquidacion, '%Y') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 6) {
				sb.append(" and  date_format(op.fecha_liquidacion, '%Y%m') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 8) {
				sb.append(" and date_format(op.fecha_liquidacion, '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01'"
						+ " and   date_format(op.fecha_liquidacion, '%Y%m%d') <='" + mesAnio + "'");
			}*/

			List<OrdenPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new RowMapper() {
				public OrdenPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					OrdenPagoDto carga = new OrdenPagoDto();
					carga.setBeneficiario(rs.getString("beneficiario"));
					carga.setCtaBeneficiario(rs.getString("cta_beneficiario"));
					carga.setMonto(rs.getBigDecimal("monto"));
					carga.setOrdenante(rs.getString("ordenante"));
					carga.setCtaOrdenante(rs.getString("cta_ordenante"));
					carga.setRfcOrdenante(rs.getString("rfc"));
					carga.setCosto(rs.getDouble("costo"));

					return carga;
				}
			});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<OrdenPagoDto>();
	}

	@Override
	public List<OrdenPagoDto> getDetalleEgresos(OrdenPagoDto orden) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(
					"  select  ifnull(pss.nombre_centro_costos,'sin_identificar') beneficiario"
					+ ", ifnull(pss.clabe_cuenta_ordenante, 'sin identificar') cta_beneficiario"
					+ ", ifnull(c.razon_social, 'sin identificar') ordenante"
					+ ", ifnull(ccb.clabe_interbancaria, 'sin identificar') cta_ordenante, sum(pc.monto_ppp) monto "
					+ " from cliente c\r\n"
							+ "join nomina_cliente nc ON c.id_cliente = nc.id_cliente \r\n"
							+ "join ppp_nomina pn  on nc.id_nomina_cliente = pn.id_nomina_cliente \r\n"
							+ "join ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n"
							+ "join ppp_colaborador_estatus pce on pc.id_ppp_colaborador = pce.id_ppp_colaborador \r\n"
							+ "join (select id_ppp_colaborador,  max(id_ppp_colaborador_estatus)  id_ppp_colaborador_estatus\r\n"
							+ "				from ppp_colaborador_estatus pce \r\n"
							+ "			group by id_ppp_colaborador ) pcem \r\n"
							+ "			on pce.id_ppp_colaborador_estatus =pcem.id_ppp_colaborador_estatus\r\n"
							+ " join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
							"join prestadora_servicio_stp pss on cps.id_prestadora_servicio_fondo= pss.id_prestadora_servicio_stp\r\n" + 
							"left join cliente_cuenta_bancaria ccb on c.id_cliente = ccb.clabe_interbancaria and ccb.ind_estatus =1 \r\n" + 
							""
							+ "where pce.id_cat_estatus_colaborador in (12,3)\r\n");
			sb.append(" and cast(pce.fecha_alta  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
						
			/*if (mesAnio.length() == 6) {
				sb.append(" and date_format(pce.fecha_alta, '%Y%m') ='" + mesAnio + "' \r\n");
			}
			if (mesAnio.length() == 4) {
				sb.append(" and date_format(pce.fecha_alta, '%Y') ='" + mesAnio + "' \r\n");
			}

			if (mesAnio.length() == 8) {
				sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01" + "' \r\n");
				sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') <='" + mesAnio + "' \r\n");
			}*/

			sb.append(
					"and c.ind_estatus =1  and nc.ind_estatus =1\r\n" + "and  pn.ind_estatus and pce.ind_estatus=1 \r\n"
							+ "group by pss.nombre_centro_costos, pss.clabe_cuenta_ordenante, c.razon_social \r\n");

			List<OrdenPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new RowMapper() {
				public OrdenPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					OrdenPagoDto carga = new OrdenPagoDto();
					carga.setBeneficiario(rs.getString("beneficiario"));
					carga.setCtaBeneficiario(rs.getString("cta_beneficiario"));
					carga.setMonto(rs.getBigDecimal("monto"));
					carga.setOrdenante(rs.getString("ordenante"));
					carga.setCtaOrdenante(rs.getString("cta_ordenante"));

					return carga;
				}
			});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<OrdenPagoDto>();
	}

	@Override
	public List<CargaOrdenesPagoDto> getCargaOrdenesPago() {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(
					" SELECT c.id_carga_orden_pago, c.descripcion, c.usuario_alta, concat( p.nombre, ' ', p.apellido_paterno , ' ',p.apellido_materno ) nombre  , c.fecha_alta,  ");
			sb.append(" c.fecha_modificacion   ");
			sb.append(" FROM carga_orden_pago c   ");
			sb.append(" join usuario u on c.usuario_alta = u.id_usuario   ");
			sb.append(" join persona p on p.id_persona = u.id_persona  ");
			sb.append(" WHERE c.ind_estatus = 1 ");

			List<CargaOrdenesPagoDto> listaOrdenes = jdbcTemplate.query(sb.toString(), new Object[] {},
					new RowMapper() {
						public CargaOrdenesPagoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
							CargaOrdenesPagoDto carga = new CargaOrdenesPagoDto();
							carga.setIdCargaOrdenPago(rs.getLong("id_carga_orden_pago"));
							carga.setDescCarga(rs.getString("descripcion"));
							carga.setUsuarioAlta(rs.getString("nombre"));
							carga.setIdUsuarioAlta(rs.getLong("usuario_alta"));
							carga.setFechaCarga(rs.getDate("fecha_alta"));

							return carga;
						}
					});

			return listaOrdenes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialCargaOrdenesPago ", e);
		}
		return new ArrayList<CargaOrdenesPagoDto>();
	}

	@Override
	public List<ClientesDetalleDto> getDetalleClienteIngresos(OrdenPagoDto orden) {

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select ifnull(c.razon_social,'Sin identificar') razon_social\r\n");
			sb.append(", ifnull(c.rfc,'Sin identificar') rfc,ifnull(ps.nombre_corto,'Sin identificar') nombre_corto\r\n");
			sb.append(", op.fecha_operacion, sum(monto) monto\r\n");
			sb.append(" from orden_pago op  \r\n");
			sb.append(" left join  cta_ordenante co on co.cta_ordenante = op.cta_ordenante  \r\n");
			sb.append(" left join cliente c on co.rfc=c.rfc \r\n");
			sb.append(" left join cliente_prestadora_servicio cps  on c.id_cliente = cps.id_cliente\r\n");
			sb.append(" left join prestadora_servicio ps \r\n");
			sb.append(" on cps.id_prestadora_servicio_fondo  = ps.id_prestadora_servicio\r\n");
			sb.append(" where op.ind_estatus =1 \r\n");
			sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			
			/*
			if (mesAnio.length() == 4) {
				sb.append(" and date_format(op.fecha_liquidacion, '%Y') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 6) {
				sb.append(" and  date_format(op.fecha_liquidacion, '%Y%m') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 8) {
				sb.append(" and date_format(op.fecha_liquidacion, '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01'"
						+ " and   date_format(op.fecha_liquidacion, '%Y%m%d') <='" + mesAnio + "'");
			}
			*/
			sb.append(" group by c.razon_social, c.rfc,ps.nombre_corto, op.fecha_operacion");

			List<ClientesDetalleDto> listaclientes = jdbcTemplate.query(sb.toString(), new RowMapper() {
				public ClientesDetalleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClientesDetalleDto cliente = new ClientesDetalleDto();
					cliente.setRazonSocial(rs.getString("razon_social"));
					cliente.setRfc(rs.getString("rfc"));
					cliente.setPrestadoraServicio(rs.getString("nombre_corto"));
					cliente.setFechaOperacion(rs.getDate("fecha_operacion"));
					cliente.setMonto(rs.getDouble("monto"));

					return cliente;
				}
			});

			return listaclientes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<ClientesDetalleDto>();

	}

	public BigDecimal getCostoPromedio() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" select sum(costo) /  count(cta_ordenante)  costoPromedio \r\n" + " from cta_ordenante co  ");
		return jdbcTemplate.queryForObject(sb.toString(), BigDecimal.class);
	}

	@Override
	public List<ClientesDetalleDto> getDetalleClienteEgresos(OrdenPagoDto orden) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select razon_social, rfc, fecha_alta,nombre_corto, sum(totalEgreso) totalEgreso \r\n ");
			sb.append(" from (  select ifnull(c.razon_social,concat( c.nombre,' ', c.apellido_paterno,' ', c.apellido_materno)) razon_social  \r\n");
			sb.append("  , c.rfc,pce.fecha_alta,ps.nombre_corto  \r\n");
			sb.append("  , pc.monto_ppp totalEgreso \r\n");
			sb.append(" from cliente c\r\n");
			sb.append(" join nomina_cliente nc ON c.id_cliente = nc.id_cliente \r\n");
			sb.append("	join ppp_nomina pn  on nc.id_nomina_cliente = pn.id_nomina_cliente \r\n");
			sb.append(" join ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n");
			sb.append(" join ppp_colaborador_estatus pce on pc.id_ppp_colaborador = pce.id_ppp_colaborador \r\n");
			sb.append(" join (select id_ppp_colaborador,  max(id_ppp_colaborador_estatus)  id_ppp_colaborador_estatus\r\n");
			sb.append("				from ppp_colaborador_estatus pce \r\n");
			sb.append("			group by id_ppp_colaborador ) pcem \r\n");
			sb.append("			on pce.id_ppp_colaborador_estatus =pcem.id_ppp_colaborador_estatus\r\n");
			sb.append("join cliente_prestadora_servicio cps on c.id_cliente =cps.id_cliente \r\n");
			sb.append(" join prestadora_servicio ps on cps.id_prestadora_servicio_fondo = ps.id_prestadora_servicio \r\n");
			sb.append(" where pce.id_cat_estatus_colaborador in (12,3)\r\n");
			sb.append(" and cast(pce.fecha_alta  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
					
			/*if (mesAnio.length() == 4) {
				sb.append(" and date_format(pce.fecha_alta, '%Y') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 6) {
				sb.append(" and  date_format(pce.fecha_alta, '%Y%m') ='" + mesAnio + "'");
			}
			if (mesAnio.length() == 8) {
				sb.append(" and date_format(pce.fecha_alta, '%Y%m%d') >='" + mesAnio.substring(0, 6) + "01'"
						+ " and   date_format(pce.fecha_alta, '%Y%m%d') <='" + mesAnio + "'");
			}*/

			sb.append(" and c.ind_estatus =1  and nc.ind_estatus =1\r\n");
			sb.append(" and  pn.ind_estatus and pce.ind_estatus=1 \r\n");
			sb.append(" ) c group by razon_social, rfc, nombre_corto, fecha_alta ");
		
			List<ClientesDetalleDto> listaclientes = jdbcTemplate.query(sb.toString(), new RowMapper() {
				public ClientesDetalleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClientesDetalleDto cliente = new ClientesDetalleDto();
					cliente.setRazonSocial(rs.getString("razon_social"));
					cliente.setRfc(rs.getString("rfc"));
					cliente.setPrestadoraServicio(rs.getString("nombre_corto"));
					cliente.setFechaOperacion(rs.getDate("fecha_alta"));
					cliente.setMonto(rs.getDouble("totalEgreso"));

					return cliente;
				}
			});

			return listaclientes;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial CargaOrdenes ", e);
		}
		return new ArrayList<ClientesDetalleDto>();

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<DetalleConciliacionesCCDto> getlistDetalleCC (OrdenPagoDto orden){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(	" select cc, " + 
					" monto, " + 
					" disponible, " + 
					" ingresoBruto," + 
					" ingresoBruto*.18 utilidad, " + 
					" ingresoBruto*.18 gasto , " + 
					" ingresoBruto*.4 socios,  " + 
					" ingresoBruto*.60 comisiones " + 
					" from (select cc, sum(monto) monto,    sum(disponible) disponible, sum(monto) - sum(disponible) ingresoBruto " + 
					" from( " + 
					" select ifnull(cc.razon_social ,'Otro') cc, monto, ifnull( co.costo,0) costo" + 
					", case when ifnull(co.costo,0)<= 0 then  monto else monto/(1+(co.costo/100)) end  disponible" + 
					" from orden_pago op " + 
					" left join cta_ordenante co on op.cta_ordenante  = co.cta_ordenante  " + 
					" left join cta_ordenante cc on op.cta_beneficiario= cc.cta_ordenante" + 
					" WHERE op.ind_estatus = 1 " );
			sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("' ) r group by cc) rc"); 
		
			List<DetalleConciliacionesCCDto> listdetallecc = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {			
				public DetalleConciliacionesCCDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					DetalleConciliacionesCCDto detallecc = new DetalleConciliacionesCCDto();
				//	detallecc.setCc(rs.getLong("cc"));
					detallecc.setCentroCosto(rs.getString("cc").toUpperCase());
					detallecc.setDepositos(rs.getDouble("monto"));
					detallecc.setDisponible(rs.getDouble("disponible"));
					detallecc.setIngresoBruto(rs.getDouble("ingresoBruto"));
					/*detallecc.setUtilidad(rs.getDouble("utilidad"));
					detallecc.setGasto(rs.getDouble("gasto"));
					detallecc.setSocios(rs.getDouble("socios"));
					detallecc.setComisiones(rs.getDouble("comisiones"));*/
					
					return detallecc;
				}
			});

			return listdetallecc;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en carga del detalle por centro de costos", e);
		}
		return new ArrayList<DetalleConciliacionesCCDto>();				
				}
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<DetalleConciliacionesCCDto> getlistDispersionIB (OrdenPagoDto orden){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(	" select stp, " + 	  
					" concepto_pago," +
					" contraparte, " +
					" monto, " +
					 "ordenante, " +
					" cta_ordenante, " +
					 "fecha_operacion," +
					" fecha_liquidacion, " +
					"  monto - disponible as ingresoBruto " +
					 "FROM( " +
					" select " +
					"  id_orden_origen as stp, " +
					" concepto_pago, " +
					" contraparte, " +
					 "monto, " +
					" op.ordenante, " +
					 "op.cta_ordenante, " +
					" fecha_operacion, " +
					" fecha_liquidacion,  " +
					" ifnull( co.costo,0) costo " +
					", case when ifnull(co.costo,0)<= 0 " +
					" then  monto else monto/(1+(co.costo/100)) " +
					"end  disponible " +
					 "from orden_pago op " +
					 "left join cta_ordenante co on op.cta_ordenante  = co.cta_ordenante  " +
					 "left join cta_ordenante cc on op.cta_beneficiario= cc.cta_ordenante " +
					 "WHERE op.ind_estatus = 1 " );
		             sb.append(" and cast(op.fecha_liquidacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(orden.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			        .append(" and ").append("'").append(Utilerias.convirteDateToString(orden.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("' and cc.razon_social=").append(orden.getCosto()).append(") r"); 
			List<DetalleConciliacionesCCDto> listdetallecc = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {			
				public DetalleConciliacionesCCDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					DetalleConciliacionesCCDto detallecc = new DetalleConciliacionesCCDto();
				//	detallecc.setCc(rs.getLong("cc"));
					detallecc.setCentroCosto(rs.getString("cc").toUpperCase());
					detallecc.setDepositos(rs.getDouble("monto"));
					detallecc.setDisponible(rs.getDouble("disponible"));
					detallecc.setIngresoBruto(rs.getDouble("ingresoBruto"));
					/*detallecc.setUtilidad(rs.getDouble("utilidad"));
					detallecc.setGasto(rs.getDouble("gasto"));
					detallecc.setSocios(rs.getDouble("socios"));
					detallecc.setComisiones(rs.getDouble("comisiones"));*/
					
					return detallecc;
				}
			});

			return listdetallecc;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en carga del detalle por centro de costos", e);
		}
		return new ArrayList<DetalleConciliacionesCCDto>();				
				}
	

	@Transactional
	public Boolean crearEstatusDeposito(long idDeposito, Long idUsuario, Long idEstatus) {
		try {
			
			ordenPagoEstatus estatus= new ordenPagoEstatus();
			estatus.setIdOrdenPago(idDeposito);
			estatus.setIdCatEstatusOrdenPago(idEstatus);
			estatus.setFechaAlta(new Date());
			estatus.setUsuarioAlta(new Usuario(idUsuario));
			estatus.setObservacion("se agrega deposito");
			estatus.setIndEstatus(1l);
			ordenPagoEstatusDao.create(estatus);
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al guardar el estatus del deposito", e);
			return Boolean.FALSE;
		}
	}
		
	
	@Transactional
	public Boolean vinculaFacturaDeposito(long idDeposito, Long idUsuario, Long idFactura) {
		try {
			
			PppFacturaDeposito facdep= new PppFacturaDeposito();
			facdep.setPppFactura(new PppNominaFactura(idFactura));
			facdep.setIdOrdenPago(new OrdenPago(idDeposito));
			facdep.setIndEstatus("1");
			facdep.setFechaAlta(new Date());
			facdep.setUsuarioAlta(new Usuario(idUsuario));
		
			
			pppFacturaDepositoDao.create(facdep);
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al guardar el estatus del deposito", e);
			return Boolean.FALSE;
		}
	}


	

}
