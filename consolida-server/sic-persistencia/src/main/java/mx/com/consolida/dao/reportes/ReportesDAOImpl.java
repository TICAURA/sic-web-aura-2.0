package mx.com.consolida.dao.reportes;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.util.ConstantesComunes;
import mx.com.reporte.dto.ReporteDTO;

@Repository
public class ReportesDAOImpl implements ReportesDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteEstimados(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro,
			String cveQuincena) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.nombre as celula_operacional, cli.rfc, ");
			sb.append(
					" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,  ");
			sb.append(" cg.descripcion_razon_social as nombre_comercial_cliente,  ");
			sb.append(" (select  ps.razon_social  ");
			sb.append(" from sin.cliente_prestadora_servicio cps, prestadora_servicio ps  ");
			sb.append(" where ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  ");
			sb.append(" and cps.id_prestadora_servicio is null  ");
			sb.append(" and ps.es_fondo = 1  ");
			sb.append(" and cps.id_cliente = cli.id_cliente  ");
			sb.append(" AND cps.ind_estatus = 1) as fondo,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" ) as total_factura_ppp,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			if ("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			} else if ("SEGUN_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			}
			sb.append(" ) as total_factura_ppp_n_menos_tres_1,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			if ("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			} else if ("SEGUN_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -2),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			}
			sb.append(" ) as total_factura_ppp_n_menos_tres_2,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			if ("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			} else if ("SEGUN_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -3),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			}
			sb.append(" ) as total_factura_ppp_n_menos_tres_3  ");
			sb.append(" from sin.cliente cli  ");
			sb.append(" left join celula_cliente ccli on  ");
			sb.append(" ccli.id_cliente = cli.id_cliente  ");
			sb.append(" left join celula cel on  ");
			sb.append(" cel.id_celula = ccli.id_celula  ");
			sb.append(" left join cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" where cli.ind_estatus = 1  ");
			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cel.id_celula in (").append(listaCentro).append(")");
			}

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setDescripcionCelula(rs.getString("celula_operacional"));
					report.setRfc(rs.getString("rfc"));
					report.setRazonSocial(rs.getString("razon_social"));
					report.setNombreComercialCliente(rs.getString("nombre_comercial_cliente"));
					report.setNombreFondo(rs.getString("fondo"));
					report.setMontoTotalFacturaPpp(
							rs.getBigDecimal("total_factura_ppp") != null ? rs.getBigDecimal("total_factura_ppp")
									: new BigDecimal("0"));

					BigDecimal monto1 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_1") != null
							? rs.getBigDecimal("total_factura_ppp_n_menos_tres_1")
							: new BigDecimal("0");
					BigDecimal monto2 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_2") != null
							? rs.getBigDecimal("total_factura_ppp_n_menos_tres_2")
							: new BigDecimal("0");
					BigDecimal monto3 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_3") != null
							? rs.getBigDecimal("total_factura_ppp_n_menos_tres_3")
							: new BigDecimal("0");
					BigDecimal preMontoTotal = monto1.add(monto2);
					BigDecimal montoTotal = preMontoTotal.add(monto3);
					report.setMontoTotalFacturaPppMesAnterior(montoTotal);

					if (fechaInicioPeriodo != null && fechaFinPeriodo != null) {
						String periodo = "";
						report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) + " - "
								+ Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
						if ("PRIM_QUIN".equals(cveQuincena)) {
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMes(fechaFinPeriodo, -1)) + "  "
									+ Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -2)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMes(fechaFinPeriodo, -2)) + "  "
									+ Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -3)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMes(fechaFinPeriodo, -3));
						} else if ("SEGUN_QUIN".equals(cveQuincena)) {
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -1))
									+ "  " + Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -2))
									+ " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -2))
									+ "  " + Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -3))
									+ " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -3));
						}

						report.setPeriodoReporteMesAnterior(periodo);
					}

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteEstimados ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteOperaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.nombre as celula_operacional,  ");
			sb.append(
					" concat(per.nombre, ' ', per.apellido_paterno, ' ',if( per.apellido_materno is null, '', per.apellido_materno)) as EJECUTIVO_NOMINA,    ");
			sb.append(" cli.rfc,  ");
			sb.append(
					" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,   ");
			sb.append(" cg.descripcion_razon_social as nombre_comercial_cliente,  ");
			sb.append(" nomclihono.honorario_ppp as porcentaje_honorarios,  ");
			sb.append(" ctp.descripcion_tipo_pago as periodo_nomina,  ");
			sb.append(" pppnom.fecha_inicio_nomina,  ");
			sb.append(" pppnom.fecha_fin_nomina,  ");
			sb.append(" (select  ps.razon_social  ");
			sb.append(" from sin.cliente_prestadora_servicio cps, prestadora_servicio ps  ");
			sb.append(" where ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  ");
			sb.append(" and cps.id_prestadora_servicio is null  ");
			sb.append(" and ps.es_fondo = 1  ");
			sb.append(" and cps.id_cliente = cli.id_cliente  ");
			sb.append(" AND cps.ind_estatus = 1) as fondo,  "); ////////////// FONDO
			/* sb.append(" pppnom.clave as num_factura_ppp,  "); */
			sb.append(" IFNULL(concat(se.nombre_serie, pppnomfact.folio ), 'Factura externa') as folio,");
			sb.append(" (select count(pc.id_ppp_colaborador)  ");
			sb.append(" from sin.ppp_colaborador pc  ");
			sb.append(" left join ppp_nomina pn on  ");
			sb.append(" pc.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" LEFT JOIN ppp_colaborador_estatus ce on  ");
			sb.append(" ce.id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" where ce.id_cat_estatus_colaborador = (select max(id_cat_estatus_colaborador)  ");
			sb.append(" 										from sin.ppp_colaborador_estatus  ");
			sb.append(" 										where id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" 										and id_cat_estatus_colaborador = 12  ");
			sb.append("                                         )  ");
			sb.append(" and pn.id_ppp_nomina = pppnom.id_ppp_nomina) as total_colaboradores_pagados,  "); ////////////// TOTAL
																											////////////// COLABORADORES
																											////////////// PAGADOS
			sb.append(" (select sum(pc.monto_ppp)  ");
			sb.append(" from sin.ppp_colaborador pc  ");
			sb.append(" LEFT JOIN ppp_nomina pn on  ");
			sb.append(" pc.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" LEFT JOIN ppp_colaborador_estatus ce on  ");
			sb.append(" ce.id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" where ce.id_cat_estatus_colaborador = (select max(id_cat_estatus_colaborador)  ");
			sb.append(" 										from sin.ppp_colaborador_estatus  ");
			sb.append(" 										where id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" 										and id_cat_estatus_colaborador = 12  ");
			sb.append("                                         )  ");
			sb.append(" and pn.id_ppp_nomina = pppnom.id_ppp_nomina) as ppp_monto_dispersado,  "); ////////////// PPP
																									////////////// MONTO
																									////////////// DISPERSADO
			sb.append(" pppnomfact.total_iva_trasladado_16, ");
			sb.append(" pppnomfact.total as total_factura_ppp,  ");
			sb.append(" pppnomfact.monto_total_honorario as monto_del_honorario,  ");
			sb.append(" concat(nomcli.nombre_nomina,' - ', nomcli.clave_nomina) as nomina_operativa,  ");
			sb.append(" pnc.fecha_facturacion, ");
			sb.append(" pnc.fecha_dispersion, ");
			sb.append(" pnc.fecha_timbrado ");
			sb.append(" from sin.cliente cli  ");
			sb.append(" LEFT JOIN celula_cliente ccli on  ");
			sb.append(" ccli.id_cliente = cli.id_cliente  ");
			sb.append(" LEFT JOIN celula cel on  ");
			sb.append(" cel.id_celula = ccli.id_celula  ");
			sb.append(" LEFT JOIN cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" LEFT JOIN nomina_cliente nomcli on  ");
			sb.append(" nomcli.id_cliente = cli.id_cliente  ");
			sb.append(" LEFT JOIN nomina_periodicidad nomper on ");
			sb.append(" nomper.id_nomina = nomcli.id_nomina_cliente ");
			sb.append(" LEFT JOIN cat_tipo_pago ctp on ");
			sb.append(" ctp.id_tipo_pago = nomper.id_cat_periodicidad ");
			sb.append(" LEFT JOIN usuario usu on  ");
			sb.append(" usu.id_usuario = nomcli.id_nominista  ");
			sb.append(" LEFT JOIN persona per on  ");
			sb.append(" per.id_persona = usu.id_persona  ");
			sb.append(" LEFT JOIN nomina_cliente_honorario nomclihono on  ");
			sb.append(" nomclihono.id_nomina_cliente = nomcli.id_nomina_cliente  ");
			sb.append(" LEFT JOIN ppp_nomina pppnom on  ");
			sb.append(" pppnom.id_nomina_cliente = nomcli.id_nomina_cliente  ");
			sb.append(" LEFT JOIN ppp_nomima_factura pppnomfact on  ");
			sb.append(" pppnomfact.id_ppp_nomina = pppnom.id_ppp_nomina  ");
			sb.append(" LEFT JOIN ppp_nomina_estatus pppnes on  ");
			sb.append(" pppnes.id_ppp_nomina = pppnom.id_ppp_nomina  ");
			sb.append(" LEFT JOIN  cat_serie se on pppnomfact.id_cat_serie=se.id_cat_serie  ");
			sb.append(" LEFT JOIN ppp_nomina_complemento pnc on  pppnom.id_ppp_nomina=pnc.id_ppp_nomina  ");
			sb.append(" where cli.ind_estatus = 1  ");
			sb.append(" and pppnes.id_cat_estatus_nomina = 23  ");
			sb.append(" and pppnes.ind_estatus = 1  ");
			if (fechaInicioPeriodo != null && fechaFinPeriodo != null) {
				sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias
						.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias
								.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd)
								.trim())
						.append("'");
				sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias
						.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias
								.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd)
								.trim())
						.append("'");
			}

			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cel.id_celula in (").append(listaCentro).append(")");
			}

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setDescripcionCelula(rs.getString("celula_operacional"));
					report.setNombreEjecutivoNomina(rs.getString("EJECUTIVO_NOMINA"));
					report.setRfc(rs.getString("rfc"));
					report.setRazonSocial(rs.getString("razon_social"));
					report.setNombreComercialCliente(rs.getString("nombre_comercial_cliente"));
					report.setPorcentaje(rs.getString("porcentaje_honorarios"));
					report.setPeriodoNomina(rs.getString("periodo_nomina"));
					report.setNombreFondo(rs.getString("fondo"));
					/* report.setNumeroFacturaPpp(rs.getString("num_factura_ppp")); */
					report.setNumeroFacturaPpp(rs.getString("folio"));
					report.setTotalColaboradresPagados(rs.getLong("total_colaboradores_pagados"));
					report.setMontoDispersado(
							rs.getBigDecimal("ppp_monto_dispersado") != null ? rs.getBigDecimal("ppp_monto_dispersado")
									: new BigDecimal("0"));
					report.setMontoIvaFactura(rs.getBigDecimal("total_iva_trasladado_16") != null
							? rs.getBigDecimal("total_iva_trasladado_16")
							: new BigDecimal("0"));
					report.setMontoTotalFacturaPpp(
							rs.getBigDecimal("total_factura_ppp") != null ? rs.getBigDecimal("total_factura_ppp")
									: new BigDecimal("0"));
					report.setMontoHonorario(
							rs.getBigDecimal("monto_del_honorario") != null ? rs.getBigDecimal("monto_del_honorario")
									: new BigDecimal("0"));
					report.setNominaOperativa(rs.getString("nomina_operativa"));
					if (fechaInicioPeriodo != null && fechaFinPeriodo != null) {
						report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) + " - "
								+ Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
					}
					report.setFecha_factura((Utilerias.convirteDateToString(rs.getDate("fecha_facturacion"),
							ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));
					report.setFecha_timbrado((Utilerias.convirteDateToString(rs.getDate("fecha_timbrado"),
							ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));
					report.setFecha_dispersado((Utilerias.convirteDateToString(rs.getDate("fecha_dispersion"),
							ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteOperaciones ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteVariaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro,
			String cveQuincena) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.nombre as celula_operacional, cli.rfc, ");
			sb.append(
					" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,  ");
			sb.append(" cg.descripcion_razon_social as nombre_comercial_cliente,  ");
			sb.append(" (select  ps.razon_social  ");
			sb.append(" from sin.cliente_prestadora_servicio cps, prestadora_servicio ps  ");
			sb.append(" where ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  ");
			sb.append(" and cps.id_prestadora_servicio is null  ");
			sb.append(" and ps.es_fondo = 1  ");
			sb.append(" and cps.id_cliente = cli.id_cliente  ");
			sb.append(" AND cps.ind_estatus = 1) as fondo,  ");
			sb.append(" cli.actividad_economica_final,  ");
			sb.append(" (select count(pppcol.id_ppp_colaborador)   ");
			sb.append(" from sin.ppp_colaborador_estatus coles  ");
			sb.append(" left join ppp_colaborador pppcol on  ");
			sb.append(" pppcol.id_ppp_colaborador = coles.id_ppp_colaborador  ");
			sb.append(" left join ppp_nomina pppnom on  ");
			sb.append(" pppnom.id_ppp_nomina = pppcol.id_ppp_nomina  ");
			sb.append(" left join nomina_cliente ncli on  ");
			sb.append(" ncli.id_nomina_cliente = pppnom.id_nomina_cliente  ");
			sb.append(" where coles.id_cat_estatus_colaborador = (select max(id_cat_estatus_colaborador)  ");
			sb.append(" 											from sin.ppp_colaborador_estatus  ");
			sb.append(
					" 											where id_ppp_colaborador = pppcol.id_ppp_colaborador  ");
			sb.append(" 											and id_cat_estatus_colaborador = 12)  ");
			sb.append(" and ncli.id_cliente = cli.id_cliente  ");
			sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" ) as total_colaboradores,  "); ////////////// TOTAL COLABORADORES
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
					.append(Utilerias
							.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'").append(" and ").append("'").append(Utilerias
							.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
					.append("'");
			sb.append(" ) as total_factura_ppp,  "); ////////////// TOTAL FACTURA PPP
			sb.append(" (select count(pppcol.id_ppp_colaborador)   ");
			sb.append(" from sin.ppp_colaborador_estatus coles  ");
			sb.append(" left join ppp_colaborador pppcol on  ");
			sb.append(" pppcol.id_ppp_colaborador = coles.id_ppp_colaborador  ");
			sb.append(" left join ppp_nomina pppnom on  ");
			sb.append(" pppnom.id_ppp_nomina = pppcol.id_ppp_nomina  ");
			sb.append(" left join nomina_cliente ncli on  ");
			sb.append(" ncli.id_nomina_cliente = pppnom.id_nomina_cliente  ");
			sb.append(" where coles.id_cat_estatus_colaborador = (select max(id_cat_estatus_colaborador)  ");
			sb.append(" 											from sin.ppp_colaborador_estatus  ");
			sb.append(
					" 											where id_ppp_colaborador = pppcol.id_ppp_colaborador  ");
			sb.append(" 											and id_cat_estatus_colaborador = 12)  ");
			sb.append(" and ncli.id_cliente = cli.id_cliente  ");
			if ("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			} else if ("SEGUN_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			}
			sb.append(" ) as total_colaboradores_n_menos_uno, "); ////////////// TOTAL COLABORADORES N MENOS UNO
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" where pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23) ");
			sb.append(" and nc.id_cliente  = cli.id_cliente ");
			if ("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			} else if ("SEGUN_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'")
						.append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'").append(" and ").append("'")
						.append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -1),
								ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())
						.append("'");
			}
			sb.append(" ) as total_factura_ppp_n_menos_uno  "); ////////////// TOTAL FACTURA N MENOS UNO
			sb.append(" from sin.cliente cli  ");
			sb.append(" left join celula_cliente ccli on  ");
			sb.append(" ccli.id_cliente = cli.id_cliente  ");
			sb.append(" left join celula cel on  ");
			sb.append(" cel.id_celula = ccli.id_celula  ");
			sb.append(" left join cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" where cli.ind_estatus = 1  ");
			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cel.id_celula in (").append(listaCentro).append(")");
			}

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setDescripcionCelula(rs.getString("celula_operacional"));
					report.setRfc(rs.getString("rfc"));
					report.setRazonSocial(rs.getString("razon_social"));
					report.setNombreComercialCliente(rs.getString("nombre_comercial_cliente"));
					report.setNombreFondo(rs.getString("fondo"));
					report.setActividadEconomica(rs.getString("actividad_economica_final"));
					report.setTotalColaboradresPagados(rs.getLong("total_colaboradores"));
					report.setTotalColaboradresPagadosMesAnterior(rs.getLong("total_colaboradores_n_menos_uno"));
					report.setMontoTotalFacturaPpp(
							rs.getBigDecimal("total_factura_ppp") != null ? rs.getBigDecimal("total_factura_ppp")
									: new BigDecimal("0"));
					report.setMontoTotalFacturaPppMesAnterior(rs.getBigDecimal("total_factura_ppp_n_menos_uno") != null
							? rs.getBigDecimal("total_factura_ppp_n_menos_uno")
							: new BigDecimal("0"));
					if (fechaInicioPeriodo != null && fechaFinPeriodo != null) {
						String periodo = "";
						report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) + " - "
								+ Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
						java.util.Date date = fechaInicioPeriodo;
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						int mes = cal.get(Calendar.MONTH);
						report.setNombreMesReporteAnterior(Utilerias.obtenMes(mes).toUpperCase());

						if ("PRIM_QUIN".equals(cveQuincena)) {
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -1));
						} else if ("SEGUN_QUIN".equals(cveQuincena)) {
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) + " - "
									+ Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -1));
						}
						report.setPeriodoReporteMesAnterior(periodo);
					}

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteVariaciones ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteColabFaltCrm(String listaCentro) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select cla.nombre  as celula,pc.rfc ,pc.nombre , pc.apellido_paterno , pc.apellido_materno ,");
			sb.append("cec.descripcion , pn.clave as nomina,    ");
			sb.append("pc.monto_ppp, pcci.clabe_interbancaria ,pc.curp, pc.numero_seguro_social, ");
			sb.append("pc.institucion_contraparte, pc.correo_electronico, pcs.fecha_alta as fecha_operacion ");
			sb.append(
					"from (select * from ppp_colaborador pc group by rfc ) pc left join colaborador c on pc.rfc = c.rfc   ");
			sb.append("left join ppp_colaborador_estatus pce on pce.id_ppp_colaborador  = pc.id_ppp_colaborador   ");
			sb.append(
					"inner join cat_estatus_colaborador cec on cec.id_cat_estatus_colaborador =pce.id_cat_estatus_colaborador    ");
			sb.append(
					"left join ppp_colaborador_clabe_interbancaria pcci on pc.id_ppp_colaborador =pcci.id_ppp_colaborador ");
			sb.append("left join ppp_colaborador_stp pcs on pc.id_ppp_colaborador=pcs.id_ppp_colaborador ");
			sb.append("left join ppp_nomina pn on pn.id_ppp_nomina  = pc.id_ppp_nomina   ");
			sb.append("inner join nomina_cliente nc on pn.id_nomina_cliente  = nc.id_nomina_cliente   ");
			sb.append("inner join cliente cl on cl.id_cliente  = nc.id_cliente   ");
			sb.append("inner join celula_cliente cc on cc.id_cliente  = cl.id_cliente   ");
			sb.append("inner join celula cla on cc.id_celula  = cla.id_celula   ");
			sb.append("where c.rfc is null  ");
			sb.append("and pce.ind_estatus = '1'  ");
			sb.append("and cec.clave = 'TIMBR'  ");
			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cla.id_celula in (").append(listaCentro).append(")");
			}
			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setDescripcionCelula(rs.getString("celula"));
					report.setRfc(rs.getString("rfc"));
					report.setNombre(rs.getString("nombre"));
					report.setApellidoPaterno(rs.getString("apellido_paterno"));
					report.setApellidoMaterno(rs.getString("apellido_materno"));
					report.setNomina(rs.getString("nomina"));
					report.setEstatusColaborador(rs.getString("descripcion"));
					report.setMonto_ppp(rs.getBigDecimal("monto_ppp"));
					report.setClabe_interbancaria(rs.getString("clabe_interbancaria"));
					report.setCurp(rs.getString("curp"));
					report.setNum_seguro_social(rs.getString("numero_seguro_social"));
					report.setInstitucion_contraparte(rs.getString("institucion_contraparte"));
					report.setCorreo_electronico(rs.getString("correo_electronico"));
					report.setFecha_operacion(Utilerias.convirteDateToString(rs.getDate("fecha_operacion"),
							ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim());

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}

	public List<ReporteDTO> reporteFacturacionMensual(String listaCentro, String mes) {

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select \r\n");
			sb.append("pnf.fecha_hora_certificacion as FECHA \r\n");
			sb.append(", pnf.uuid as UUID \r\n");
			sb.append(", s.nombre_serie as SERIE \r\n");
			sb.append(", pnf.folio as FOLIObnh \r\n");
			sb.append(", c.rfc as RFC\r\n");
			sb.append(", c.razon_social as RAZON_SOCIAL\r\n");
			sb.append(", cg.descripcion as TIPO\r\n");
			sb.append(", pnf.sub_total as SUPTOTAL_MXN\r\n");
			sb.append(", pnf.total_iva_trasladado_16 as IMPUESTOS\r\n");
			sb.append(", pnf.total as TOTAL_MXN \r\n");
			sb.append(", mp.descripcion metodoPago \r\n");
			sb.append(", fp.descripcion formaPago \r\n");
			sb.append("from  celula_cliente cc  join cliente c on cc.id_cliente = c.id_cliente  \r\n");
			sb.append("left join nomina_cliente	 nc on (nc.id_cliente=c.id_cliente)\r\n");
			sb.append("left join ppp_nomina pn on (pn.id_nomina_cliente=nc.id_nomina_cliente)\r\n");
			sb.append("left join ppp_nomima_factura pnf on (pnf.id_ppp_nomina=pn.id_ppp_nomina)\r\n");
			sb.append(" join cat_general cg on (cg.id_cat_general=pnf.id_cat_tipo_cfdi)\r\n");
			sb.append(" join cat_serie s on (s.id_cat_serie=pnf.id_cat_serie)\r\n");
			sb.append(" join cat_general  mp on pnf.id_cat_metodo_pago = mp.id_cat_general \r\n");
			sb.append(" join cat_general fp on pnf.id_cat_forma_pago = fp.id_cat_general \r\n");
			sb.append("where c.ind_estatus = 1 and pnf.certificado_emisor is not null\r\n");
			sb.append("   and concat( month (pnf.fecha_hora_certificacion),year(pnf.fecha_hora_certificacion))='");
			sb.append(mes);
			sb.append("'");
			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cc.id_celula in (").append(listaCentro).append(")");
			}
			sb.append("  order by s.nombre_serie,pnf.folio;");

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setSerie(rs.getString("SERIE"));
					report.setFolio(String.valueOf(rs.getInt("FOLIObnh")));
					report.setUuid(rs.getString("UUID"));
					report.setTipoComprobante(rs.getString("TIPO"));
					report.setRazonSocial(rs.getString("RAZON_SOCIAL"));
					report.setRfc(rs.getString("RFC"));
					report.setMontoSubTotalFactura(rs.getBigDecimal("SUPTOTAL_MXN"));
					report.setMontoIvaFactura(rs.getBigDecimal("IMPUESTOS"));
					report.setMontoTotalFacturaPpp(rs.getBigDecimal("TOTAL_MXN"));
					report.setFecha_operacion(Utilerias
							.convirteDateToString(rs.getDate("FECHA"), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd)
							.trim());
					report.setMetodoPago(rs.getString("metodoPago"));
					report.setFormaPago(rs.getString("formaPago"));

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}

	private Date addMes(Date fecha, int mesRestar) {
		Calendar cal = null;
		Date fechaAux = new Date();
		cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, mesRestar);
		fechaAux = cal.getTime();

		return fechaAux;
	}

	private Date addMesSegunda(Date fecha, int mesRestar) {
		Calendar cal = null;
		Date fechaAux = new Date();
		cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, mesRestar);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
		fechaAux = cal.getTime();
		return fechaAux;
	}

	private int getMes(Date fecha) {

		java.util.Date date = fecha;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mes = cal.get(Calendar.MONTH);

		int mesInt = 0;

		switch (mes) {
		case 1:
			mesInt = 1;
			break;
		case 2:
			mesInt = 2;
			break;
		case 3:
			mesInt = 3;
			break;
		case 4:
			mesInt = 4;
			break;
		case 5:
			mesInt = 5;
			break;
		case 6:
			mesInt = 6;
			break;
		case 7:
			mesInt = 7;
			break;
		case 8:
			mesInt = 8;
			break;
		case 9:
			mesInt = 9;
			break;
		case 10:
			mesInt = 10;
			break;
		case 11:
			mesInt = 11;
			break;
		case 12:
			mesInt = 12;
			break;

		default:
			mesInt = 0;
			break;
		}

		return mesInt;
	}

}
