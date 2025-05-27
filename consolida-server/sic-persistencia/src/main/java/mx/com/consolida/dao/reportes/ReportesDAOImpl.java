package mx.com.consolida.dao.reportes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.util.ConstantesComunes;
import mx.com.reporte.dto.ReporteDTO;

@Repository
public class ReportesDAOImpl implements ReportesDAO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteEstimados(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro, String cveQuincena) {
		
		try {

			
			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.nombre as celula_operacional, cli.rfc, ");
			sb.append(" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,  ");
			sb.append(" cg.descripcion_razon_social as nombre_comercial_cliente,  ");
			sb.append(" (select  ps.razon_social  ");
			sb.append(" from sin.cliente_prestadora_servicio cps, prestadora_servicio ps  ");
			sb.append(" where ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  ");
			sb.append(" and cps.id_prestadora_servicio is null  ");
			sb.append(" and ps.es_fondo = 1  ");
			sb.append(" and cps.id_cliente = cli.id_cliente  ");
			sb.append(" AND cps.ind_estatus = 1  ");
			sb.append(" and cps.id_cliente_prestadora_servicio =(select max(b.id_cliente_prestadora_servicio) from cliente_prestadora_servicio b where  b.id_cliente=cli.id_cliente )) as fondo, "); 		 
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  LEFT JOIN ppp_nomina_complemento pnc on  pn.id_ppp_nomina=pnc.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" AND pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23 and pne2.ind_estatus=1) ");
			sb.append(" WHERE nc.id_cliente  = cli.id_cliente ");
			sb.append(" and cast(pnc.fecha_facturacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
		
			/*if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pnf.fecha_alta as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString((calDia(fechaInicioPeriodo, 16, -1)), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMaxDias(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pnf.fecha_alta as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString((calDia(fechaInicioPeriodo,1, 0)), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(calDia(fechaFinPeriodo, 15, 0), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}*/
			sb.append(" ) as total_factura_ppp,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina LEFT JOIN ppp_nomina_complemento pnc on  pn.id_ppp_nomina=pnc.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" AND pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23 and pne2.ind_estatus=1) ");
			sb.append(" WHERE nc.id_cliente  = cli.id_cliente ");
			if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pnc.fecha_facturacion as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString((calDia(fechaInicioPeriodo, 16, -1)), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMaxDias(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pnc.fecha_facturacion as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString((calDia(fechaInicioPeriodo,1, 0)), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(calDia(fechaFinPeriodo, 15, 0), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}
			sb.append(" ) as total_factura_ppp_n_menos_tres_1,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  LEFT JOIN ppp_nomina_complemento pnc on  pn.id_ppp_nomina=pnc.id_ppp_nomina  ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" AND pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23 and pne2.ind_estatus=1) ");
			sb.append(" where nc.id_cliente  = cli.id_cliente ");
			if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pnc.fecha_facturacion as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(calDia(fechaInicioPeriodo, 1, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(calDia(fechaFinPeriodo, 15, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pnc.fecha_facturacion as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(calDia(fechaInicioPeriodo, 16, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMaxDias(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}
			sb.append(" ) as total_factura_ppp_n_menos_tres_2,  ");
			sb.append(" (select sum(pnf.total) ");
			sb.append(" from nomina_cliente nc ");
			sb.append(" inner join sin.ppp_nomina pn on ");
			sb.append(" pn.id_nomina_cliente  = nc.id_nomina_cliente  ");
			sb.append(" left join sin.ppp_nomima_factura pnf on  ");
			sb.append(" pnf.id_ppp_nomina = pn.id_ppp_nomina  LEFT JOIN ppp_nomina_complemento pnc on  pn.id_ppp_nomina=pnc.id_ppp_nomina ");
			sb.append(" inner join sin.ppp_nomina_estatus pne on ");
			sb.append(" pne.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" AND pne.id_ppp_nomina_estatus = (select max(pne2.id_ppp_nomina_estatus)  ");
			sb.append(" 									from sin.ppp_nomina_estatus pne2 ");
			sb.append(" 									where pne2.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" 									and pne2.id_cat_estatus_nomina = 23 and pne2.ind_estatus=1) ");
			sb.append(" WHERE nc.id_cliente  = cli.id_cliente ");
			if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pnc.fecha_facturacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(calDia(fechaInicioPeriodo, 16, -2), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMaxDias(fechaFinPeriodo, -2), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pnc.fecha_facturacion  as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(calDia(fechaInicioPeriodo, 1, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(calDia(fechaFinPeriodo, 15, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
		}
			sb.append(" ) as total_factura_ppp_n_menos_tres_3  ");
			sb.append(" from sin.cliente cli  ");
			sb.append(" join cliente_prestadora_servicio cps2 on cli.id_cliente = cps2.id_cliente ");
			sb.append(" left join celula_cliente ccli on  ");
			sb.append(" ccli.id_cliente = cli.id_cliente  ");
			sb.append(" left join celula cel on  ");
			sb.append(" cel.id_celula = ccli.id_celula  ");
			sb.append(" left join cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" where cli.ind_estatus = 1  ");
			sb.append("     and cli.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
				sb.append(" and cps2.id_prestadora_servicio_fondo in (").append(listaCentro).append(")"); 	
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
					report.setMontoTotalFacturaPpp(rs.getBigDecimal("total_factura_ppp")!=null ? rs.getBigDecimal("total_factura_ppp") : new BigDecimal("0"));
					
					BigDecimal monto1 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_1")!=null ? rs.getBigDecimal("total_factura_ppp_n_menos_tres_1") : new BigDecimal("0");
					BigDecimal monto2 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_2")!=null ? rs.getBigDecimal("total_factura_ppp_n_menos_tres_2") : new BigDecimal("0");
					BigDecimal monto3 = rs.getBigDecimal("total_factura_ppp_n_menos_tres_3")!=null ? rs.getBigDecimal("total_factura_ppp_n_menos_tres_3") : new BigDecimal("0");
					BigDecimal count= new BigDecimal(0);
					if(monto1.compareTo(BigDecimal.ZERO) != 0) {
						count=	count.add(new BigDecimal(1));
					}
					if(monto2.compareTo(BigDecimal.ZERO) != 0) {
						count=count.add(new BigDecimal(1));
					}
					if(monto3.compareTo(BigDecimal.ZERO) != 0) {
						count=count.add(new BigDecimal(1));
					}
					BigDecimal preMontoTotal = monto1.add(monto2);
					BigDecimal montoTotal = preMontoTotal.add(monto3);
					BigDecimal promedio=new BigDecimal(0);
					if(count.compareTo(BigDecimal.ZERO) != 0) {
					 promedio=montoTotal.divide(count,3, RoundingMode.CEILING);
						
					}
					report.setMontoTotalFacturaPppMesAnterior(promedio);
					
					if(fechaInicioPeriodo!= null && fechaFinPeriodo!=null) {
						String periodoAnt = "";
						String periodo = "";
					report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) +" - " + Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						
						/*if("PRIM_QUIN".equals(cveQuincena)) {	
							periodoAnt = Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 16, -1)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMaxDias(fechaFinPeriodo, -1));
								
						}else if("SEGUN_QUIN".equals(cveQuincena)){
							periodoAnt = Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo,1, 0)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaFinPeriodo, 15, 0));
						
						}*/
						
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
						if("PRIM_QUIN".equals(cveQuincena)) {	
							periodo = Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 16, -1)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMaxDias(fechaFinPeriodo, -1))
									+"  " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 1, -1))
									+" - " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaFinPeriodo, 15, -1))
									+"  " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 16, -2))
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMaxDias(fechaFinPeriodo, -2));
						}else if("SEGUN_QUIN".equals(cveQuincena)){
							periodo = Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo,1, 0)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaFinPeriodo, 15, 0))
									+"  " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 16, -1))
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMaxDias(fechaFinPeriodo, -1))
									+"  " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaInicioPeriodo, 1, -1))
									+" - " + Utilerias.convirteDateToStringMesEnLetra(calDia(fechaFinPeriodo, 15, -1));
						}
						//report.setPeriodoReporte(periodoAnt);
						report.setPeriodoReporteMesAnterior(periodo);
					}
					
					return report;	
				}
			});
			
			return reporte;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteEstimados ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteOperaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo,String listaCentro) {
		try {
			
			StringBuilder sb = new StringBuilder();
		/*	sb.append(" select cel.nombre as celula_operacional,  ");
			sb.append(" concat(per.nombre, ' ', per.apellido_paterno, ' ',if( per.apellido_materno is null, '', per.apellido_materno)) as EJECUTIVO_NOMINA,    ");
			sb.append(" cli.rfc,  ");
			sb.append(" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,   ");
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
			sb.append(" AND cps.ind_estatus = 1 limit 1) as fondo,  "); ////////////// FONDO
			sb.append(" pppnom.clave as num_factura_ppp,  ");
			sb.append(" CASE ");
			sb.append(" WHEN com.id_ppp_nomina_padre IS NOT NULL THEN concat(secom.nombre_serie, pnfcom.folio) ");
			sb.append(" WHEN concat(se.nombre_serie, pppnomfact.folio) IS NULL THEN 'Factura Externa' ");
			sb.append("	else concat(se.nombre_serie, pppnomfact.folio)  END AS folio, ");
			sb.append(" (select count(pc.id_ppp_colaborador)  ");
			sb.append(" from sin.ppp_colaborador pc  ");
			sb.append(" left join ppp_nomina pn on  ");
			sb.append(" pc.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" LEFT JOIN ppp_colaborador_estatus ce on  ");
			sb.append(" ce.id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" where ce.id_ppp_colaborador_estatus = (select max(id_ppp_colaborador_estatus)  ");
			sb.append(" 										from sin.ppp_colaborador_estatus  ");
			sb.append(" 										where id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" 										and id_cat_estatus_colaborador = 3  ");
			sb.append("                                         )  ");
			sb.append(" and pn.id_ppp_nomina = pppnom.id_ppp_nomina) as total_colaboradores_pagados,  "); ////////////// TOTAL COLABORADORES PAGADOS
			sb.append(" (select sum(pc.monto_ppp)  ");
			sb.append(" from sin.ppp_colaborador pc  ");
			sb.append(" LEFT JOIN ppp_nomina pn on  ");
			sb.append(" pc.id_ppp_nomina = pn.id_ppp_nomina  ");
			sb.append(" LEFT JOIN ppp_colaborador_estatus ce on  ");
			sb.append(" ce.id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" where ce.id_ppp_colaborador_estatus = (select max(id_ppp_colaborador_estatus)  ");
			sb.append(" 										from sin.ppp_colaborador_estatus  ");
			sb.append(" 										where id_ppp_colaborador = pc.id_ppp_colaborador  ");
			sb.append(" 										and id_cat_estatus_colaborador = 3  ");
			sb.append("                                         )  ");
			sb.append(" and pn.id_ppp_nomina = pppnom.id_ppp_nomina) as ppp_monto_dispersado,  "); ////////////// PPP MONTO DISPERSADO
			sb.append(" pppnomfact.total_iva_trasladado_16, ");
			sb.append(" pppnomfact.total as total_factura_ppp,  ");
			sb.append(" pppnomfact.monto_total_honorario as monto_del_honorario,  ");
			sb.append(" concat(nomcli.nombre_nomina,' - ', nomcli.clave_nomina) as nomina_operativa,  ");
			sb.append(" CASE  WHEN  nomcli.requiere_factura ='0' THEN    pnc.fecha_facturacion ");
			sb.append("      WHEN (select  pnf.fecha_hora_certificacion   from ppp_nomima_factura pnf where pnf.id_ppp_nomina =pppnom.id_ppp_nomina and pnf.id_cat_forma_pago is not null) IS NULL  THEN    pnc.fecha_facturacion ");
			sb.append(" 	   else  (select  pnf.fecha_hora_certificacion   from ppp_nomima_factura pnf where pnf.id_ppp_nomina =pppnom.id_ppp_nomina and pnf.id_cat_forma_pago is not null)  ");
			sb.append(" 	   END AS fecha_facturacion, ");
			sb.append("( select pce.fecha_alta from ppp_colaborador cn left join ppp_colaborador_estatus pce  on cn.id_ppp_colaborador =pce.id_ppp_colaborador ");
			sb.append("	where cn.id_ppp_nomina =pppnom.id_ppp_nomina and pce.id_cat_estatus_colaborador =12 limit 1) as fecha_dispersion,	");
			sb.append("( select pce.fecha_alta from ppp_colaborador cn left join ppp_colaborador_estatus pce  on cn.id_ppp_colaborador =pce.id_ppp_colaborador ");
			sb.append("	where cn.id_ppp_nomina =pppnom.id_ppp_nomina and pce.id_cat_estatus_colaborador =3 limit 1) as fecha_timbrado,	");		
			sb.append(" CASE  WHEN com.id_ppp_nomina_padre IS NOT NULL THEN concat('COMPLEMENTARIA DE ', nomcom.clave) else 'ORIGINAL' end as tipoNomina ");	
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
			sb.append(" LEFT JOIN   ppp_nomina_complementaria com on pppnom.id_ppp_nomina =com.id_ppp_nomina ");
			sb.append("	LEFT JOIN  ppp_nomima_factura pnfcom on com.id_ppp_nomina_padre =pnfcom.id_ppp_nomina ");
			sb.append("	LEFT JOIN  cat_serie secom on pnfcom.id_cat_serie=secom.id_cat_serie  ");
			sb.append(" LEFT JOIN ppp_nomina nomcom on nomcom.id_ppp_nomina = com.id_ppp_nomina_padre ");
			sb.append(" where cli.ind_estatus = 1  ");
			sb.append(" and pppnes.id_cat_estatus_nomina = 23  ");	
			sb.append(" and pppnes.ind_estatus = 1  ");*/
			sb.append("select ");	
			sb.append("vro.nombre_corto_prest_serv,  ");
			sb.append("vro.Ejecutivo_Nomina,  ");
			sb.append("vro.rfc_cliente,  ");
			sb.append("vro.razon_social_cliente,  ");
			sb.append("vro.nombre_comercial_catGrupo,  ");
			sb.append("vro.porcentaje_honorario,  ");
			sb.append("vro.periodicidad,  ");
			sb.append("vro.razonSocial_Fondo,  ");
			sb.append("vro.clave,  ");
			sb.append("vro.Factura,  ");
			sb.append("vro.Es_complementaria,  ");
			sb.append("vro.clave_nomina_padre,  ");
			sb.append("vro.fecha_alta_xFiltro,  ");
			sb.append("vro.HEAD_COUNT,  ");
			sb.append("vro.head_count_dispersado,  ");
			sb.append("vro.ppp_monto_dispersado,  ");
			sb.append("vro.monto_total_honorario,  ");
			sb.append("vro.total_iva_trasladado_16,  ");
			sb.append("vro.total,  ");
			sb.append("vro.fecha_timbrado,  ");
			sb.append("vro.fecha_dispersion,  ");
			sb.append("vro.nombre_nomina   ");
			sb.append("from sin.v_rep_operaciones_con_left_colaborador vro where  ");
			
			if(fechaInicioPeriodo!=null && fechaFinPeriodo!=null) {
				sb.append(" cast(vro.fecha_alta_xFiltro as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
		}
			
			if(listaCentro!=null && !listaCentro.isEmpty()) {
				sb.append(" and vro.id_prestadora_servicio_fondo in (").append(listaCentro).append(")"); 	
			}

			
			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					report.setDescripcionCelula(rs.getString("nombre_corto_prest_serv"));
					report.setNombreEjecutivoNomina(rs.getString("Ejecutivo_Nomina"));
					report.setRfc(rs.getString("rfc_cliente"));
					report.setRazonSocial(rs.getString("razon_social_cliente"));
					report.setNombreComercialCliente(rs.getString("nombre_comercial_catGrupo"));
					report.setPorcentaje(rs.getString("porcentaje_honorario"));
					report.setPeriodoNomina(rs.getString("periodicidad"));
					report.setNombreFondo(rs.getString("razonSocial_Fondo"));
					report.setNumeroFacturaPpp(rs.getString("clave"));
					report.setFolio(rs.getString("Factura"));
					report.setTotalColaboradores(rs.getLong("HEAD_COUNT"));
					report.setTotalColaboradresPagados(rs.getLong("head_count_dispersado"));
					report.setMontoDispersado(rs.getBigDecimal("ppp_monto_dispersado")!=null ? rs.getBigDecimal("ppp_monto_dispersado") : new BigDecimal("0"));
					report.setMontoIvaFactura(rs.getBigDecimal("total_iva_trasladado_16")!=null ? rs.getBigDecimal("total_iva_trasladado_16") : new BigDecimal("0"));
					report.setMontoTotalFacturaPpp(rs.getBigDecimal("total")!=null ? rs.getBigDecimal("total") : new BigDecimal("0"));
					report.setMontoHonorario(rs.getBigDecimal("monto_total_honorario")!=null ? rs.getBigDecimal("monto_total_honorario") : new BigDecimal("0"));
					report.setNominaOperativa(rs.getString("nombre_nomina"));
					if(fechaInicioPeriodo!= null && fechaFinPeriodo!=null) {
						report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) +" - " + Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
					}
					report.setFecha_factura((Utilerias.convirteDateToString(rs.getDate("fecha_alta_xFiltro"), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));
					report.setFecha_timbrado((Utilerias.convirteDateToString(rs.getDate("fecha_timbrado"), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));
					report.setFecha_dispersado((Utilerias.convirteDateToString(rs.getDate("fecha_dispersion"), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()));
					report.setTipoNomina(rs.getString("Es_complementaria"));
					report.setClaveNominaPadre(rs.getString("clave_nomina_padre"));
					return report;	
				}
			});
			
			return reporte;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteOperaciones ", e);
			return Collections.emptyList();
		}
	}
	

	
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteConsar(Date fechaInicioPeriodo, Date fechaFinPeriodo,String listaCentro) {
		List<ReporteDTO> reporte = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select sin.f_rep_consar ('");
			sb.append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("', '"); 
			sb.append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim());
			sb.append("',");	
			if(listaCentro!=null && !listaCentro.isEmpty()) {
				sb.append(listaCentro).append(")"); 	
			}
				
			Object obj= jdbcTemplate.queryForObject(sb.toString(), String.class);				
		    if(obj !=null) {
			String sobj=obj.toString();	
			 JsonParser parser = new JsonParser();
		       JsonElement tradeElement = parser.parse(sobj);
		       JsonArray trade = tradeElement.getAsJsonArray();
		       reporte =new ArrayList<ReporteDTO>();
		       ReporteDTO report = null;
		   
		       for(Object js : trade){	    		
		    	   JsonObject json = (JsonObject) js;
		    	   report= new ReporteDTO();	                
	             report.setApellidoPaterno(json.get("apellido_paterno").getAsString());
	             report.setApellidoMaterno(json.get("apellido_materno").isJsonNull()== false ? json.get("apellido_materno").getAsString(): new String(" - "));
				 report.setNombre(json.get("nombre").getAsString());
				 report.setRfc(json.get("RFC").getAsString());
				 report.setCurp(json.get("CURP").getAsString());
				 report.setNum_seguro_social(json.get("NSS").getAsString());
				 report.setSexo(json.get("Sexo").getAsString());			
				 report.setFecha_nacimiento(json.get("Fecha de Nacimiento").getAsString());
				 report.setEdad(json.get("Edad").getAsLong());
				// report.setEdad(json.get("Edad").isJsonNull()== false ? json.get("Edad").getAsLong(): new Long(0));
				 report.setAniosInscImss(json.get("anioInscripcionIMSS").getAsLong());
				 report.setModalidadImss(json.get("ModalidadImss").getAsString());				 
				 report.setQ1(json.get("Q1").getAsBigDecimal());   
	             report.setQ2(json.get("Q2").getAsBigDecimal());  
	             report.setQ3(json.get("Q3").getAsBigDecimal());  
	             report.setQ4(json.get("Q4").getAsBigDecimal());  
	             report.setQ5(json.get("Q5").getAsBigDecimal());  
	             report.setQ6(json.get("Q6").getAsBigDecimal());  
	             report.setQ7(json.get("Q7").getAsBigDecimal());  
	             report.setQ8(json.get("Q8").getAsBigDecimal());  
	             report.setQ9(json.get("Q9").getAsBigDecimal());  
	             report.setQ10(json.get("Q10").getAsBigDecimal());  
	             report.setQ11(json.get("Q11").getAsBigDecimal());  
	             report.setQ12(json.get("Q12").getAsBigDecimal());  
	             report.setQ13(json.get("Q13").getAsBigDecimal());  
	             report.setQ14(json.get("Q14").getAsBigDecimal());  
	             report.setQ15(json.get("Q15").getAsBigDecimal());  
	             report.setQ16(json.get("Q16").getAsBigDecimal());  
	             report.setQ17(json.get("Q17").getAsBigDecimal());  
	             report.setQ18(json.get("Q18").getAsBigDecimal());  
	             report.setQ19(json.get("Q19").getAsBigDecimal());  
	             report.setQ20(json.get("Q20").getAsBigDecimal());  
	             report.setQ21(json.get("Q21").getAsBigDecimal());  
	             report.setQ22(json.get("Q22").getAsBigDecimal());  
	             report.setQ23(json.get("Q23").getAsBigDecimal());  
	             report.setQ24(json.get("Q24").getAsBigDecimal());  
	             report.setTotal(json.get("Total").getAsBigDecimal());
	             reporte.add(report);	           
		       }
		      
		       System.out.println("Lista ee----"+reporte.size());
				return reporte;	
				
		    }else {
		    	return Collections.emptyList();
		    }
		    
		}
	
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteConsar ", e);
			 return reporte;
		}
		
		
	}
	
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteTesoOpera(Date fechaInicioPeriodo, Date fechaFinPeriodo,String listaCentro) {
		List<ReporteDTO> reporte = null;
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select sin.f_rep_teso_operac ('");
			sb.append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("', '"); 
			sb.append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim());
			sb.append("',");	
			if(listaCentro!=null && !listaCentro.isEmpty()) {
				sb.append(listaCentro).append(")"); 	
			}
				
			Object obj= jdbcTemplate.queryForObject(sb.toString(), String.class);				
		    if(obj !=null) {
			String sobj=obj.toString();	
			 JsonParser parser = new JsonParser();
		       JsonElement tradeElement = parser.parse(sobj);
		       JsonArray trade = tradeElement.getAsJsonArray();
		       reporte =new ArrayList<ReporteDTO>();
		       ReporteDTO report = null;
		   
		       for(Object js : trade){	    		
		    	   JsonObject json = (JsonObject) js;
		    	   report= new ReporteDTO();	
		    	   report.setTipoGeneracion(json.get("tipo_factura").getAsString());
		    	   report.setFondo(json.get("nombre").isJsonNull()== false ? json.get("nombre").getAsString() : new String("-")); 
		    	   report.setRazonSocial(json.get("razon_social").isJsonNull()== false  ? json.get("razon_social").getAsString() : new String("-")); 
		    	   report.setRfc(json.get("rfc").isJsonNull()== false  ? json.get("rfc").getAsString() : new String("-")); 
		    	   report.setUuid(json.get("uuid").isJsonNull()== false  ? json.get("uuid").getAsString() : new String("-")); 
		    	   report.setSubtotal(json.get("sub_total").isJsonNull()== false  ? json.get("sub_total").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setIva(json.get("total_iva_trasladado_16").isJsonNull()== false  ? json.get("total_iva_trasladado_16").getAsBigDecimal() : new BigDecimal("0"));  
		    	   report.setTotal(json.get("total").isJsonNull()== false  ? json.get("total").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setFechaAlta(json.get("fecha_alta").isJsonNull()== false ?(Utilerias.convirteStringToString(json.get("fecha_alta").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()): new String ("-"));
		    	   report.setFechaCertificacion (json.get("fecha_hora_certificacion").isJsonNull()== false ?(Utilerias.convirteStringToString(json.get("fecha_hora_certificacion").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()): new String ("-"));
		    	   report.setUsuarioFactura(json.get("usuario").isJsonNull()== false  ? json.get("usuario").getAsString() : new String("-")); 
		    	   report.setIdStp(json.get("id_stp").isJsonNull()== false  ? json.get("id_stp").getAsString() : new String("-")); 
		    	   report.setBeneficiario(json.get("beneficiario").isJsonNull()== false  ? json.get("beneficiario").getAsString() : new String("-")); 
		    	   report.setCtaBeneficiario(json.get("contraparte").isJsonNull()== false  ? json.get("contraparte").getAsString() : new String("-")); 
		    	   report.setContraparte(json.get("rfc_curp_ordenante").isJsonNull()== false  ? json.get("rfc_curp_ordenante").getAsString() : new String("-")); 
		    	   report.setMonto(json.get("monto").isJsonNull()== false  ? json.get("monto").getAsBigDecimal() : new BigDecimal("0"));   
		    	   report.setRastreo(json.get("rastreo").isJsonNull()== false  ? json.get("rastreo").getAsString() : new String("-")); 
		    	   report.setFechaOperacion(json.get("fecha_operacion").isJsonNull()== false ?(Utilerias.convirteStringToString(json.get("fecha_operacion").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()): new String ("-"));
		    	   report.setFechaLiquidacion(json.get("fecha_liquidacion").isJsonNull()== false ?(Utilerias.convirteStringToString(json.get("fecha_liquidacion").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()): new String ("-"));
		    	   report.setNomina(json.get("clave").isJsonNull()== false  ? json.get("clave").getAsString() : new String("-")); 
		    	   report.setFechaRegistroNomina(json.get("fecha_nomina").isJsonNull()== false ?((Utilerias.convirteStringToString(json.get("fecha_nomina").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())): new String ("-"));
		    	   report.setColaboradores(json.get("total_colaboradores").isJsonNull()== false  ? json.get("total_colaboradores").getAsLong() : new Long("-")); 
		    	   report.setMontoOperacion(json.get("monto_operacion").isJsonNull()== false  ? json.get("monto_operacion").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setMontoDispersado(json.get("monto_dispersado").isJsonNull()== false ? json.get("monto_dispersado").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setMontoRechazos(json.get("monto_rechazado").isJsonNull()== false  ? json.get("monto_rechazado").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setUsuarioDispercion(json.get("usuario_dispersion").isJsonNull()== false  ? json.get("usuario_dispersion").getAsString() : new String("-")); 
		    	   report.setFechaDispersion(json.get("fecha_dispersion").isJsonNull()== false ?((Utilerias.convirteStringToString(json.get("fecha_dispersion").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())): new String ("-"));
		    	   report.setTimbres(json.get("timbres").isJsonNull()== false  ? json.get("timbres").getAsLong() : new Long("-")); 
		    	   report.setFechaTimbrado(json.get("fecha_timbre").isJsonNull()== false ?((Utilerias.convirteStringToString(json.get("fecha_timbre").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())): new String ("-"));
		    	   report.setDiferenciaDepositoDispersion(json.get("DIFERENICIA_DEPOSITO_VS_DISPERSION").isJsonNull()== false  ? json.get("DIFERENICIA_DEPOSITO_VS_DISPERSION").getAsBigDecimal() : new BigDecimal("0")); 
		    	   report.setFechaCierreNomina(json.get("fecha_cierre").isJsonNull()== false ?((Utilerias.convirteStringToString(json.get("fecha_cierre").getAsString(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim())): new String ("-"));
		    		
	             reporte.add(report);	           
		       }
		      
		       System.out.println("Lista ee----"+reporte.size());
				return reporte;	
				
		    }else {
		    	return Collections.emptyList();
		    }
		    
		}
	
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteTesoreriaOperacion ", e);
			 return reporte;
		}
		
		
	}
	
	
	/***
	 * Reporte de productos
	 * @param claveProducto
	 * @param listaCentro
	 * @return
	 */
	
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteProductos(String idProducto, String listaCentro) {
	
		try {        
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  cel.nombre,  cli.RFC, ");			
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente   from cliente_producto cp WHERE  cp.id_producto=304 and cp.id_cliente=cli.id_cliente) as 'PPP',");
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente from cliente_producto cp WHERE  cp.id_producto=306 and cp.id_cliente=cli.id_cliente) as 'PROD_MIX', ");
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente from cliente_producto cp WHERE  cp.id_producto=9949 and cp.id_cliente=cli.id_cliente) as 'PROD_MAQU', ");
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente from cliente_producto cp WHERE  cp.id_producto=9950 and cp.id_cliente=cli.id_cliente) as 'PROD_MIX_IM', ");
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente from cliente_producto cp WHERE  cp.id_producto=9958 and cp.id_cliente=cli.id_cliente) as 'IRLAB', ");
			sb.append("(select CASE  WHEN  id_cliente is not null THEN 'SI' end as cliente from cliente_producto cp WHERE  cp.id_producto=9962 and cp.id_cliente=cli.id_cliente) as 'QAMM', ");
			sb.append("( select IFNULL(count(cp.id_cliente),0) from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=304 ");
					if(listaCentro!=null && !listaCentro.isEmpty()) {
		    sb.append( " and cps2.id_prestadora_servicio_fondo  in  (").append(listaCentro).append(")"); 
					}
			sb.append( " ) as total_ppp, ");
			sb.append("( select IFNULL(count(cp.id_cliente),0) from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=306 ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
              sb.append( " and cps2.id_prestadora_servicio_fondo  in (").append(listaCentro).append(")"); 
		   	}
	        sb.append( " ) as total_totalSueldosYSalarios, ");
	    	sb.append("( select IFNULL(count(cp.id_cliente),0) from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=9949 ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
              sb.append( " and cps2.id_prestadora_servicio_fondo in (").append(listaCentro).append(")"); 
		   	}
	        sb.append( " ) as total_maquila, ");
	     	sb.append("( select IFNULL(count(cp.id_cliente), 0) from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=9950 ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
              sb.append( " and cps2.id_prestadora_servicio_fondo    in (").append(listaCentro).append(")"); 
		   	}
	        sb.append( " ) as total_mixtoPPP, ");
	    	sb.append("( select IFNULL(count(cp.id_cliente), 0) as id_cliente from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=9958 ");
				if(listaCentro!=null && !listaCentro.isEmpty()) {
	              sb.append( " and cps2.id_prestadora_servicio_fondo  in (").append(listaCentro).append(")"); 
			   	}
		   sb.append( " ) as total_irlab, ");
		   sb.append("( select IFNULL(count(cp.id_cliente), 0) from cliente_producto cp join cliente_prestadora_servicio cps2 on cp.id_cliente = cps2.id_cliente where cp.id_producto=9962 ");
				if(listaCentro!=null && !listaCentro.isEmpty()) {
	       sb.append( " and cps2.id_prestadora_servicio_fondo  in (").append(listaCentro).append(")"); 
			   	}
		        sb.append( " ) as total_qamm, ");
			sb.append(" cli.razon_social ");
		    sb.append("FROM cliente cli ");
		    sb.append("join cliente_prestadora_servicio cps on 	cli.id_cliente = cps.id_cliente ");
		    sb.append(" join celula_cliente ccli on cli.id_cliente = ccli.id_cliente ");
		    sb.append(" join celula cel on cel.id_celula = ccli.id_celula ");
		    sb.append("where cli.ind_estatus =1  ");
		    sb.append("     and cli.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
		    sb.append( " and cps.id_prestadora_servicio_fondo  in (").append(listaCentro).append(")"); 
			}
		    sb.append(" group by  cel.nombre, cli.RFC, cli.razon_social; ");
		
			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();	
				
					report.setDescripcionCelula(rs.getString("nombre"));
					report.setRfc(rs.getString("RFC"));
					report.setRazonSocial(rs.getString("razon_social"));	
					report.setPpp(rs.getString("PPP"));
					report.setProdMix(rs.getString("PROD_MIX"));
					report.setProdMaqu(rs.getString("PROD_MAQU"));
					report.setProdMixIms(rs.getString("PROD_MIX_IM"));
					report.setProdIrlab(rs.getString("IRLAB"));
					report.setProdQamm(rs.getString("QAMM"));
					report.setTotalPPP(rs.getInt("total_ppp"));
					report.setTotalSueldosYSalarios(rs.getInt("total_totalSueldosYSalarios"));
					report.setTotalMaquila(rs.getInt("total_maquila"));
					report.setTotalMixtoPPP(rs.getInt("total_mixtoPPP"));
					report.setTotalIrlab(rs.getInt("total_irlab"));
					report.setTotalQamm(rs.getInt("total_qamm"));
					return report;	
				}
			});
			
			return reporte;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteProductos ", e);
			return Collections.emptyList();
		}
	}
	

	
	

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteVariaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro, String cveQuincena) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.nombre as celula_operacional, cli.rfc, ");
			sb.append(" if(cli.id_tipo_persona = 22, cli.razon_social,  concat(cli.nombre, ' ', cli.apellido_paterno, ' ',if( cli.apellido_materno is null, '', cli.apellido_materno))) as razon_social,  ");
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
			sb.append(" 											where id_ppp_colaborador = pppcol.id_ppp_colaborador  ");
			sb.append(" 											and id_cat_estatus_colaborador = 12)  ");
			sb.append(" and ncli.id_cliente = cli.id_cliente  ");
			sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
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
			sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
			.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
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
			sb.append(" 											where id_ppp_colaborador = pppcol.id_ppp_colaborador  ");
			sb.append(" 											and id_cat_estatus_colaborador = 12)  ");
			sb.append(" and ncli.id_cliente = cli.id_cliente  "); 	
			if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pppnom.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				sb.append(" and cast(pppnom.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
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
			if("PRIM_QUIN".equals(cveQuincena)) {
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMes(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
			}else if("SEGUN_QUIN".equals(cveQuincena)){
				sb.append(" and cast(pn.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				sb.append(" and cast(pn.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(addMes(fechaInicioPeriodo, -1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
				.append(" and ").append("'").append(Utilerias.convirteDateToString(addMesSegunda(fechaFinPeriodo,-1), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'");
			}
			sb.append(" ) as total_factura_ppp_n_menos_uno  "); ////////////// TOTAL FACTURA N MENOS UNO
			sb.append(" from sin.cliente cli  ");
			sb.append(" join cliente_prestadora_servicio cps2 on cli.id_cliente =cps2.id_cliente  ");
			sb.append(" left join celula_cliente ccli on  ");
			sb.append(" ccli.id_cliente = cli.id_cliente  ");
			sb.append(" left join celula cel on  ");
			sb.append(" cel.id_celula = ccli.id_celula  ");
			sb.append(" left join cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" where cli.ind_estatus = 1  ");	
			  sb.append("     and cli.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
				sb.append(" and cps2.id_prestadora_servicio_fondo in (").append(listaCentro).append(")"); 	
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
					report.setMontoTotalFacturaPpp(rs.getBigDecimal("total_factura_ppp")!=null ? rs.getBigDecimal("total_factura_ppp") : new BigDecimal("0"));
					report.setMontoTotalFacturaPppMesAnterior(rs.getBigDecimal("total_factura_ppp_n_menos_uno")!=null ? rs.getBigDecimal("total_factura_ppp_n_menos_uno") : new BigDecimal("0"));
					if(fechaInicioPeriodo!= null && fechaFinPeriodo!=null) {
						String periodo = "";
						report.setPeriodoReporte(Utilerias.convirteDateToStringMesEnLetra(fechaInicioPeriodo) +" - " + Utilerias.convirteDateToStringMesEnLetra(fechaFinPeriodo));
						report.setFechaInicio(fechaInicioPeriodo);
						report.setFechaFin(fechaFinPeriodo);
						java.util.Date date = fechaInicioPeriodo;
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						int mes = cal.get(Calendar.MONTH);
						report.setNombreMesReporteAnterior(Utilerias.obtenMes(mes).toUpperCase());
						
						if("PRIM_QUIN".equals(cveQuincena)) {	
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -1));
						}else if("SEGUN_QUIN".equals(cveQuincena)){
							periodo = Utilerias.convirteDateToStringMesEnLetra(addMes(fechaInicioPeriodo, -1)) 
									+" - " + Utilerias.convirteDateToStringMesEnLetra(addMesSegunda(fechaFinPeriodo, -1));
						}
						report.setPeriodoReporteMesAnterior(periodo);
					}
					
					return report;	
				}
			});
			
			return reporte;
			
		}catch (Exception e) {
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
			sb.append("from (select * from ppp_colaborador pc group by rfc ) pc left join colaborador c on pc.rfc = c.rfc   ");
			sb.append("left join ppp_colaborador_estatus pce on pce.id_ppp_colaborador  = pc.id_ppp_colaborador   ");
			sb.append("inner join cat_estatus_colaborador cec on cec.id_cat_estatus_colaborador =pce.id_cat_estatus_colaborador    ");
			sb.append("left join ppp_colaborador_clabe_interbancaria pcci on pc.id_ppp_colaborador =pcci.id_ppp_colaborador ");
			sb.append("left join ppp_colaborador_stp pcs on pc.id_ppp_colaborador=pcs.id_ppp_colaborador ");			
			sb.append("left join ppp_nomina pn on pn.id_ppp_nomina  = pc.id_ppp_nomina   ");
			sb.append("inner join nomina_cliente nc on pn.id_nomina_cliente  = nc.id_nomina_cliente   ");
			sb.append("inner join cliente cl on cl.id_cliente  = nc.id_cliente   ");
			sb.append("inner join celula_cliente cc on cc.id_cliente  = cl.id_cliente   ");
			sb.append("inner join celula cla on cc.id_celula  = cla.id_celula   ");
			sb.append("where c.rfc is null  ");
			sb.append("     and cl.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) ");
			sb.append("and pce.ind_estatus = '1'  ");
			sb.append("and cec.clave = 'TIMBR'  ");
			if(listaCentro!=null && !listaCentro.isEmpty()) {
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
					report.setFecha_operacion(Utilerias.convirteDateToString(rs.getDate("fecha_operacion"), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim());
					
					return report;	
				}
			});
			
			return reporte;
			
		}catch (Exception e) {
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
	
	//calcular mes y medio atrasados
	private Date calDia(Date fecha, int dia, int mes) {
		Calendar cal = null;
		Date fechaAux = new Date();
		cal = Calendar.getInstance(); 
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, mes);
		cal.set(Calendar.DAY_OF_MONTH, dia);
		fechaAux = cal.getTime();

		return fechaAux;		
	}
	
	//calculo dias maximo de cada mes
	private Date addMaxDias(Date fecha, int mesRestar) {
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

	@Override
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
			sb.append("join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente\r\n");
			sb.append("left join nomina_cliente	 nc on (nc.id_cliente=c.id_cliente)\r\n");
			sb.append("left join ppp_nomina pn on (pn.id_nomina_cliente=nc.id_nomina_cliente)\r\n");
			sb.append("left join ppp_nomima_factura pnf on (pnf.id_ppp_nomina=pn.id_ppp_nomina)\r\n");
			sb.append(" join cat_general cg on (cg.id_cat_general=pnf.id_cat_tipo_cfdi)\r\n");
			sb.append(" join cat_serie s on (s.id_cat_serie=pnf.id_cat_serie)\r\n");
			sb.append(" join cat_general  mp on pnf.id_cat_metodo_pago = mp.id_cat_general \r\n");
			sb.append(" join cat_general fp on pnf.id_cat_forma_pago = fp.id_cat_general \r\n");
			sb.append("where c.ind_estatus = 1 and pnf.certificado_emisor is not null\r\n");
			sb.append("     and c.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) ");
			sb.append("   and concat( month (pnf.fecha_hora_certificacion),year(pnf.fecha_hora_certificacion))='");
			sb.append(mes);
			sb.append("'");
			if (listaCentro != null && !listaCentro.isEmpty()) {
				sb.append(" and cps.id_prestadora_servicio_fondo  in (").append(listaCentro).append(")");
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

	@Override
	public List<ReporteDTO> reporteFacturacion(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select\r\n" + 
					"pn.id_ppp_nomina \r\n" + 
					", cg.descripcion_razon_social \r\n" + 
					", pn.clave \r\n" + 
					", pnf.uuid \r\n" + 
					", concat('\\'', pnf.certificado_emisor)  certificado_emisor \r\n" + 
					", concat('\\'',cast(pnf.fecha_hora_certificacion as char) ) fecha_hora_certificacion    \r\n" + 
					", cs.nombre_serie \r\n" + 
					", pnf.folio \r\n" + 
					", c.rfc \r\n" + 
					", c.razon_social \r\n" + 
					", \"Emision\" as \"tipo_comprobante\"\r\n" + 
					", pnf.sub_total \r\n" + 
					", pnf.total_iva_trasladado_16 \r\n" + 
					", pnf.total \r\n" + 
					", pnfd.id_CMS\r\n" + 
					", dm.archivo \r\n" + 
					"from ppp_nomina  pn \r\n" + 
					"left join ppp_nomima_factura pnf on (pnf.id_ppp_nomina=pn.id_ppp_nomina)\r\n" + 
					"left join nomina_cliente nc ON (nc.id_nomina_cliente=pn.id_nomina_cliente)\r\n" + 
					"left join cliente c on (c.id_cliente=nc.id_cliente)\r\n" + 
					"join cliente_prestadora_servicio cps on (cps.id_cliente=c.id_cliente)\r\n" + 
					"join prestadora_servicio ps on (ps.id_prestadora_servicio=cps.id_prestadora_servicio_fondo)\r\n" + 
					"left join cat_grupo cg on (cg.id_cat_grupo=c.id_cat_grupo)\r\n" + 
					"left join cat_serie cs on (cs.id_cat_serie=pnf.id_cat_serie)\r\n" + 
					"left join cat_folio cf on (cf.id_cat_folio=pnf.id_cat_folio)\r\n" + 
					"join ppp_nomina_factura_documento pnfd on (pnfd.id_ppp_nomina_factura=pnf.id_ppp_nomina_factura)\r\n" + 
					"join documento_ms dm on (dm.id_documento_ms=pnfd.id_CMS)\r\n" + 
					"where c.id_cliente not in (113,206,237,205,335,336,388,427,428,860,861,869)\r\n");
					
			if (listaCentro != null && !listaCentro.isEmpty()) {
						sb.append(" and ps.id_prestadora_servicio in (").append(listaCentro).append(")");
					}
					
					sb.append(" and pnfd.id_definicion_documento =63\r\n");
					sb.append(" and pnf.fecha_hora_certificacion between ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
					.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); ;
			

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					
					report.setIdNomina(rs.getString("id_ppp_nomina"));
					report.setDescripcionRazonSocial(rs.getString("descripcion_razon_social"));
					report.setClaveNomina(rs.getString("clave"));
					report.setCertificadoEmisor(rs.getString("certificado_emisor"));
					report.setFechaHoraCertificacion(rs.getString("fecha_hora_certificacion"));
					
					report.setUuid(rs.getString("uuid"));
					report.setSerie(rs.getString("nombre_serie"));
					report.setFolio(String.valueOf(rs.getInt("folio")));
					report.setRfc(rs.getString("RFC"));
					report.setRazonSocial(rs.getString("RAZON_SOCIAL"));
					report.setTipoComprobante(rs.getString("tipo_comprobante"));
					
					report.setSubTotal(rs.getString("sub_total"));
					report.setTotalIvaTrasladado(rs.getString("total_iva_trasladado_16"));
					report.setTotal(rs.getBigDecimal("total"));
					report.setIdCms(rs.getString("id_cms"));
					report.setArchivo(rs.getString("archivo"));
				

					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}
	
	public List<ReporteDTO> reporteDispersion(Date fechaInicioPeriodo, Date fechaFinPeriodo, String listaCentro) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select \r\n" + 
					"c.id_cliente as identificadorCliente\r\n" + 
					",  case when c.razon_social is null then concat( c.nombre, ' ', c.apellido_paterno, ' ' ,c.apellido_materno)\r\n" + 
					" else c.razon_social end razon_social \r\n" + 
					", pc.id_ppp_colaborador as identificadorColaborador\r\n" + 
					", pc.nombre \r\n" + 
					", pc.apellido_paterno \r\n" + 
					", pc.apellido_materno \r\n" + 
					", pc.rfc \r\n" + 
					", pc.curp \r\n" + 
					", pc.monto_ppp \r\n" + 
					", pcs.id_stp \r\n" + 
					", concat('\\'',cast(pcs.fecha_alta as char) ) as fechaDispersion\r\n" + 
					", concat('\\'',  pcci.clabe_interbancaria  ) clabe_interbancaria\r\n" + 
					", pcd.uuid \r\n" + 
					", concat('\\'',pcd.certificado_emisor) certificado_emisor \r\n" + 
					",   concat('\\'',cast(pcd.fecha_hora_certificacion as char) ) fecha_hora_certificacion \r\n" + 
					", pn.clave \r\n" + 
					", pn.id_ppp_nomina \r\n" + 
					"from ppp_colaborador pc \r\n" + 
					"join ppp_nomina pn on (pn.id_ppp_nomina=pc.id_ppp_nomina)\r\n" + 
					"join nomina_cliente nc on (nc.id_nomina_cliente=pn.id_nomina_cliente)\r\n" + 
					"join cliente c on (c.id_cliente=nc.id_cliente)\r\n" + 
					"join cliente_prestadora_servicio cps on (cps.id_cliente=c.id_cliente)\r\n" + 
					"join prestadora_servicio ps on (ps.id_prestadora_servicio=cps.id_prestadora_servicio_fondo) \r\n" + 
					"join ppp_colaborador_stp pcs on (pcs.id_ppp_colaborador=pc.id_ppp_colaborador) \r\n" + 				
					"join ppp_colaborador_clabe_interbancaria pcci on (pcci.id_ppp_colaborador=pc.id_ppp_colaborador)\r\n" + 
					"left join ppp_colaborador_documento pcd on (pcd.id_ppp_colaborador=pc.id_ppp_colaborador \r\n" + 
					" and pcd.id_ppp_colaborador_documento in (select max(b.id_ppp_colaborador_documento) from ppp_colaborador_documento b where b.id_ppp_colaborador=pc.id_ppp_colaborador \r\n" + 
					" and (pcd.id_definicion_documento =69 or pcd.id_definicion_documento is null)))  \r\n" +
					"where c.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) \r\n" + 
					"and id_stp is not null \r\n" );
					
			if (listaCentro != null && !listaCentro.isEmpty()) {
						sb.append(" and ps.id_prestadora_servicio in (").append(listaCentro).append(")");
					}
			
					sb.append(" and pcs.fecha_alta between ").append("'").append(Utilerias.convirteDateToString(fechaInicioPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'")
					.append(" and ").append("'").append(Utilerias.convirteDateToString(fechaFinPeriodo, ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); ;
			

			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					
					report.setIdNomina(rs.getString("id_ppp_nomina"));
					report.setClaveNomina(rs.getString("clave"));
					report.setCertificadoEmisor(rs.getString("certificado_emisor"));
					report.setIdCliente(rs.getString("identificadorCliente"));
					report.setRazonSocial(rs.getString("razon_social"));
					report.setIdCliente(rs.getString("identificadorCliente"));
					report.setIdColaborador("identificadorColaborador");
					report.setRfc(rs.getString("RFC"));
					report.setCurp(rs.getString("curp"));
					report.setNombre(rs.getString("nombre"));
					report.setApellidoPaterno(rs.getString("apellido_paterno"));
					report.setApellidoMaterno(rs.getString("apellido_materno"));
					
					
					report.setMonto_ppp(rs.getBigDecimal("monto_ppp"));
					report.setIdStp(rs.getString("id_stp"));
					report.setFecha_dispersado(rs.getString("fechaDispersion"));
					report.setFechaHoraCertificacion(rs.getString("fecha_hora_certificacion"));
					report.setClabe_interbancaria(rs.getString("clabe_interbancaria"));
					report.setUuid(rs.getString("uuid"));
					
					
					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}
	
	public List<ReporteDTO> reporteColaboradores( String listaCentro) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select \r\n" + 
					"pc.id_ppp_colaborador as identificador\r\n" + 
					", pc.nombre \r\n" + 
					", pc.apellido_paterno \r\n" + 
					", pc.apellido_materno \r\n" + 
					", pc.rfc \r\n" + 
					", pc.curp \r\n" + 
					", concat(\'\\'', pc.numero_seguro_social)  numero_seguro_social\r\n" + 
					", substring(pc.curp,11,1) as genero\r\n" + 
					", concat(substring(pc.curp,9,2),'-',substring(pc.curp,7,2),'-',substring(pc.curp,5,2)) as fechaNacimiento\r\n" + 
					", pn.id_ppp_nomina as idNomina" +
					", pn.clave as claveNomina" +
					", c.razon_social as cliente\r\n" + 
					", c.id_cliente as idCliente\r\n" + 
					"from ppp_colaborador pc \r\n" + 
					"join ppp_nomina pn on (pn.id_ppp_nomina=pc.id_ppp_nomina)\r\n" + 
					"join nomina_cliente nc on (nc.id_nomina_cliente=pn.id_nomina_cliente)\r\n" + 
					"join cliente c on (c.id_cliente=nc.id_cliente)\r\n" + 
					"join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
					"where\r\n" + 
					" c.id_cliente not in (2,11,113,206,237,205,237,205,335,336,388,427,428,860,861,869)\r\n" );
					
			if (listaCentro != null && !listaCentro.isEmpty()) {
						sb.append(" and cps.id_prestadora_servicio_fondo in (").append(listaCentro).append(")");
					}
	
			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					
					report.setIdColaborador(rs.getString("identificador"));
					report.setRfc(rs.getString("RFC"));
					report.setCurp(rs.getString("curp"));
					report.setNombre(rs.getString("nombre"));
					report.setApellidoPaterno(rs.getString("apellido_paterno"));
					report.setApellidoMaterno(rs.getString("apellido_materno"));
					report.setNum_seguro_social(rs.getString("numero_seguro_social"));
					report.setGenero(rs.getString("genero"));
					report.setFechaNacimiento(rs.getString("fechaNacimiento"));
					/*
					 *  * report.setAnioImms(rs.getString("aniosIMSS"));
					 * report.setDomicilio(rs.getString("direccion"));
					 * report.setCodigoPostal(rs.getString("codigoPostal"));
					 
					report.setModalidadImss(rs.getString("modalidadIMSS"));*/
					report.setClaveNomina(rs.getString("claveNomina"));
					report.setRazonSocial(rs.getString("cliente"));
					report.setIdCliente(rs.getString("idCliente"));
					
					
					
					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColaboradores", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ReporteDTO> reporteClientes(String listaCentro) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select c.id_cliente idCliente \r\n" + 
					", CASE	WHEN c.razon_social is null \r\n" + 
					"	THEN (	    concat(c.nombre,' ',c.apellido_paterno,' ',c.apellido_paterno) )\r\n" + 
					"	Else c.razon_social \r\n" + 
					"	end as nombreRazonSocial\r\n" + 
					", c.rfc \r\n"+
					",case when c.es_activo=1 then  'Activo' else 'Inactivo' end  estatusCliente\r\n" + 
					", cg.descripcion tipoPersona\r\n" + 
					", ps.razon_social razonSocial\r\n" + 
					", case when cg6.descripcion_razon_social is null "
					+ "then 'SIN GRUPO' else cg6.descripcion_razon_social end  nombreComercialGrupo \r\n " +
					", cg1.descripcion as dispersor\r\n" + 
					", cg2.descripcion as regimenFiscal\r\n" + 
					", cg3.descripcion as tipoDomicilio\r\n" + 
					", d.calle \r\n" + 
					", d.numero_exterior numExt\r\n" + 
					", d.numero_interior numInt\r\n" + 
					", d.colonia \r\n"+
					", concat(\'\\'',d.codigo_postal) codigoPostal" + 					
					", cm.descripcion as municipio \r\n" + 
					", d.id_entidad_federativa idEntidadFederativa \r\n" + 
					", cmc.nombre  \r\n" + 
					", cmc.apellido_paterno  \r\n" + 
					", cmc.apellido_materno  \r\n" + 
					", cmc.correo  \r\n" + 
					", cmc.telefono \r\n" + 
					", cg4.descripcion producto \r\n" + 
					", concat(nc.clave_nomina,'-',nc.nombre_nomina) as nombreNomina\r\n" + 
					", ctp.descripcion_tipo_pago  periodicidadNomina\r\n" + 
					", cg5.descripcion esquemaNomina\r\n" + 
					", concat(p.nombre,' ',p.apellido_paterno,' ',p.apellido_materno) as nominista\r\n" + 
					", case \r\n" + 
					"	when nc.requiere_factura = 1 then \"SI\"\r\n" + 
					"	else \"NO\"\r\n" + 
					"end as factura\r\n" + 
					 ", ncc.comision \r\n" + 
					", concat(p.nombre,' ',p.apellido_paterno,' ',p.apellido_materno) as comisionista\r\n" + 
					", cv.descripcion  canalVenta\r\n" + 
					", cf.descripcion formulaComision \r\n"
					+ ", nch.honorario_ppp honorario\r\n" + 
					", cfh.descripcion formulaHonorario\r\n" + 
					", cfi.descripcion formulaIva\r\n" + 
					", cff.descripcion formulaFactura \r\n"
					+ ", concat('\\'' , ccb.numero_cuenta) numeroCuenta \r\n" + 
					", concat(\'\\'' ,ccb.clabe_interbancaria) clabe\r\n" + 
					", cb.descripcion  banco \r\n" + 
					", cfc.descripcion funcionCuenta\r\n"
					+ ", ccb.numero_referencia numeroReferencia \r\n" + 
					", CASE WHEN ccb.es_principal = '1' THEN 'SI' ELSE 'NO' END esPrincipal \r\n" + 
					"from cliente c \r\n" + 
					"join cliente_prestadora_servicio cps on (cps.id_cliente=c.id_cliente)\r\n" + 
					"join prestadora_servicio ps on (ps.id_prestadora_servicio=cps.id_prestadora_servicio_fondo)\r\n" + 
					"join cat_general cg on (cg.id_cat_general=c.id_tipo_persona)\r\n" + 
					"left join prestadora_servicio_stp pss on (pss.id_prestadora_servicio=ps.id_prestadora_servicio)\r\n" + 
					"join cat_general cg1 on (cg1.id_cat_general=pss.id_tipo_dispersor)\r\n" + 
					"left join cat_general cg2 on (cg2.id_cat_general=c.id_cat_regimen_fiscal)\r\n" + 
					"left join cliente_domicilio cd on (cd.id_cliente=c.id_cliente)\r\n" + 
					"left join domicilio d on (d.id_domicilio =cd.id_domicilio)\r\n" + 
					"left join cat_general cg3 on (cg3.id_cat_general=d.id_tipo_domicilio)\r\n" + 
					"join cat_municipios cm on (cm.id_cat_municipios=d.id_municipio)\r\n" + 
					"left join cliente_medio_contacto cmc on (cmc.id_cliente=c.id_cliente)\r\n" + 
					"left join cliente_producto cp on (cp.id_cliente=c.id_cliente)\r\n" + 
					"left join cat_general cg4 on (cg4.id_cat_general=cp.id_producto)\r\n" + 
					"left join nomina_cliente nc on (nc.id_cliente=c.id_cliente)\r\n" + 
					"left join nomina_periodicidad np on (np.id_nomina=nc.id_nomina_cliente) /*REVISAR*/\r\n" + 
					"left join cat_tipo_pago ctp on (ctp.id_tipo_pago=np.id_cat_periodicidad)\r\n" + 
					"left join cat_general cg5 on (cg5.id_cat_general=nc.id_cat_producto_nomina)\r\n" + 
					"left join usuario u on (u.id_usuario=nc.id_nominista)\r\n" + 
					"join persona p on (p.id_persona=u.id_persona)\r\n" + 
					"left join nomina_cliente_comision ncc on (ncc.id_nomina_cliente=nc.id_nomina_cliente)\r\n" + 
					"left join nomina_cliente_honorario nch on (nch.id_nomina_cliente=nc.id_nomina_cliente)\r\n"
					+ "left join cliente_temp ct  on c.id_cliente_temp = ct.id_cliente_temp\r\n" + 
					"left join cat_grupo cg6 on ct.id_cat_grupo = cg6.id_cat_grupo \r\n"
					+ "left join usuario uc on (uc.id_usuario=ncc.id_comisionista)\r\n" + 
					"left join persona pc on (pc.id_persona=uc.id_persona)\r\n" + 
					"left join cat_general cv on ncc.id_canal_venta = cv.id_cat_general \r\n" + 
					"left join cat_formulas cf on ncc.id_cat_formula_comision = cf.id_cat_formulas \r\n"
					+ "left  join cat_formulas cfh on nch.id_cat_formula_ppp  = cfh.id_cat_formulas \r\n" + 
					"left  join cat_formulas cfi on nch.id_cat_tipo_iva  = cfi.id_cat_formulas \r\n" + 
					"left  join cat_formulas cff on nch.id_cat_formula_factura  = cff.id_cat_formulas \r\n"
					+ "left join cliente_cuenta_bancaria ccb on c.id_cliente =ccb.id_cliente \r\n" + 
					"left join cat_general cb on ccb.id_banco = cb.id_cat_general \r\n" + 
					"left join cat_general cfc on ccb.id_tipo_cuenta = cfc.id_cat_general \r\n" + 
					"where d.id_tipo_domicilio = 34\r\n"+					
					"and c.id_cliente not in (2,11,113,206,237,205,335,336,388,427,428,860,861,869) " );
		
			if (listaCentro != null && !listaCentro.isEmpty()) {
						sb.append(" and cps.id_prestadora_servicio_fondo in (").append(listaCentro).append(")");
					}
	
			@SuppressWarnings("unchecked")
			List<ReporteDTO> reporte = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ReporteDTO report = new ReporteDTO();
					
					report.setIdCliente(rs.getString("idCliente"));
					report.setRazonSocial(rs.getString("nombreRazonSocial"));
					report.setNombreComercialCliente(rs.getString("nombreComercialGrupo"));
					report.setTipoPersona(rs.getString("tipoPersona"));
					report.setRazonSocialCelula(rs.getString("razonSocial"));
					report.setRfc(rs.getString("rfc"));
					report.setEstatusCliente(rs.getString("estatusCliente"));
					report.setDispersor(rs.getString("dispersor"));
					report.setRegimenFiscal(rs.getString("regimenFiscal"));
					report.setTipoDomicilio(rs.getString("tipoDomicilio"));
					report.setCalle(rs.getString("calle"));
					report.setNumInt(rs.getString("numInt"));
					report.setNumExt(rs.getString("numExt"));
					report.setColonia(rs.getString("colonia"));
					report.setCodigoPostal(rs.getString("codigoPostal"));	
					report.setMunicipio(rs.getString("municipio"));
					report.setIdEntidadFederativa(rs.getString("idEntidadFederativa"));
					report.setNombre(rs.getString("nombre"));
					report.setApellidoPaterno(rs.getString("apellido_paterno"));
					report.setApellidoMaterno(rs.getString("apellido_materno"));
					report.setCorreo(rs.getString("correo"));
					report.setTelefono(rs.getString("telefono"));
					report.setProducto(rs.getString("producto"));
					report.setNombreNomina(rs.getString("nombreNomina"));
					report.setPeriodicidadNomina(rs.getString("periodicidadNomina"));
					report.setEsquemaNomina(rs.getString("esquemaNomina"));
					report.setNominista(rs.getString("nominista"));
					report.setFactura(rs.getString("factura"));
					report.setComision(rs.getString("comision"));
					report.setCanalVenta(rs.getString("canalVenta"));
					report.setComisionista(rs.getString("comisionista"));
					report.setFormulaComision(rs.getString("formulaComision"));
					report.setHonorario(rs.getString("honorario"));
					report.setFormulaHonorario(rs.getString("formulaHonorario"));
					report.setFormulaIva(rs.getString("formulaIva"));
					report.setFormulaFactura(rs.getString("formulaFactura"));
					report.setBanco(rs.getString("banco"));
					report.setNoCuentaBanco(rs.getString("numeroCuenta"));
					report.setClabe(rs.getString("clabe"));
					report.setFuncionCuenta(rs.getString("funcionCuenta"));
					report.setNoReferencia(rs.getString("numeroReferencia"));
					report.setEsPrincipal(rs.getString("esPrincipal"));
					
			
					return report;
				}
			});

			return reporte;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteCClientes", e);
			return Collections.emptyList();
		}
	}
	
	

}
