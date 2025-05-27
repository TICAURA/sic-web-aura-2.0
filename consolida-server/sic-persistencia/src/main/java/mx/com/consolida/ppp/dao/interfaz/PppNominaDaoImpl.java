package mx.com.consolida.ppp.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
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
import mx.com.consolida.catalogos.TipoProductoEnum;
import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.dto.NominasDTO;
import mx.com.consolida.util.ConstantesComunes;
import mx.com.facturacion.factura.SeguimientoNominaDto;

@Repository
public class PppNominaDaoImpl  extends GenericDAO<PppNomina, Long> implements PppNominaDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppNominaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaClientesNomina(Long idRol, Long idUsuarioAplicativo) {
		try {
			
			StringBuilder sb = new StringBuilder();					
			sb.append(" select");
			sb.append(" cn.id_nomina_cliente ,");
			sb.append(" 	cli.id_cliente,");
			sb.append(" 	cli.nombre_comercial,");
			sb.append(" 	cli.razon_social,");
			sb.append(" 	cli.nombre,");
			sb.append(" 	cli.apellido_paterno,");
			sb.append(" 	cli.apellido_materno,");
			sb.append(" 	cli.rfc,");
			sb.append(" 	(");
			sb.append(" 	select");
			sb.append(" 		count(ncli.id_cliente)");
			sb.append(" 	from");
			sb.append(" 		sin.nomina_cliente ncli");
			sb.append(" 	where");
			sb.append(" 		ncli.id_cliente = cli.id_cliente ");
			sb.append(" 		and ncli.id_cat_producto_nomina in (304, 9958)  ");
			sb.append(" 		and ncli.ind_estatus = 1 and ncli.id_nominista =?) total_nominas,");
			sb.append(" 	(");
			sb.append(" 	select");
			sb.append(" 		count(ncli.id_cliente)");
			sb.append(" 	from");
			sb.append(" 		sin.nomina_cliente ncli ,ppp_nomina pn , ppp_nomina_estatus pne , cat_estatus_nomina cen ");
			sb.append(" 	where	ncli.id_nomina_cliente  = pn.id_nomina_cliente ");
			sb.append(" 	and pne.id_ppp_nomina  = pn.id_ppp_nomina ");
			sb.append(" 	and cen.id_cat_estatus_nomina  = pne.id_cat_estatus_nomina ");
			sb.append(" 	AND ncli.id_nomina_cliente in (select cn2.id_nomina_cliente from sin.nomina_cliente cn2 where cn2.id_cliente = cli.id_cliente) ");
			sb.append(" 	and ncli.ind_estatus = 1");
			sb.append(" 	and pne.ind_estatus = 1 and ncli.id_nominista =?");
			sb.append(" 	and cen.clave  not in ('TIMBRADA_INC','TIMBRADA','CIERRE_NOM','NOM_CANC')) total_nominas_en_proceso , ");
			sb.append(" 	(select cg.descripcion_razon_social from cat_grupo cg ");
			sb.append(" where cli.id_cat_grupo  = cg.id_cat_grupo ) grupo ");
			sb.append(" from");
			sb.append(" 	sin.nomina_cliente cn,");
			sb.append(" 	sin.cliente cli,");
			sb.append(" 	sin.usuario usu,");
			sb.append(" 	sin.usuario_rol usur");
			sb.append(" 	,	sin.usuario_celula uc ");
			sb.append(" 	,  sin.celula_cliente cc ");
			sb.append(" where");
			sb.append(" 	cli.id_cliente = cn.id_cliente");
			sb.append(" 	and cn.id_nominista = usu.id_usuario");
			sb.append(" 	and usur.id_usuario = cn.id_nominista");
			
			sb.append(" 	and cli.id_cliente =cc.id_cliente ");
			sb.append(" 	and uc.id_usuario =usur.id_usuario ");
			sb.append(" 	and uc.id_celula = cc.id_celula ");
			
			sb.append(" 	and usur.id_rol = ?");
			sb.append(" 	and cli.ind_estatus = 1");
			sb.append(" 	and cn.ind_estatus = 1");
			sb.append(" 	and cn.id_nominista = ?");
		//	sb.append(" 	and cn.id_cat_producto_nomina in (304,9958) ");
			sb.append(" 	group by cli.id_cliente ");
			//Se toman en cuenta los estatus ('TIMBRADA_INC','TIMBRADA') ya que 

			
			return jdbcTemplate.query(sb.toString(), new Object[] {idUsuarioAplicativo, idUsuarioAplicativo, idRol, 
					idUsuarioAplicativo}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setTotalNominas(rs.getInt("total_nominas"));
					nominaDto.setTotalNominasEnProcesoNominista(rs.getInt("total_nominas_en_proceso"));
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setNombreComercial(rs.getString("grupo")!= null?rs.getString("grupo"):"");
					clienteDto.setRazonSocial(rs.getString("razon_social") != null ? rs.getString("razon_social"):"");
					clienteDto.setNombre(rs.getString("nombre")!= null ? rs.getString("nombre"):"");
					clienteDto.setApellidoPaterno(rs.getString("nombre")!=null?rs.getString("apellido_paterno"):"");
					clienteDto.setApellidoMaterno(rs.getString("apellido_materno")!=null ? rs.getString("apellido_materno") : "");
					
					String nombreCompleto = null;
					
					if(clienteDto.getNombre() != null && clienteDto.getApellidoPaterno() != null && clienteDto.getApellidoMaterno() != null) {
						 nombreCompleto =clienteDto.getNombre()+" "+clienteDto.getApellidoPaterno();	
					}else if(clienteDto.getNombre() != null && clienteDto.getApellidoPaterno() != null) {
						 nombreCompleto =clienteDto.getNombre()+" "+clienteDto.getApellidoPaterno()+" "+(clienteDto.getApellidoMaterno()!=null ? clienteDto.getApellidoMaterno() : "");
					}
					
					clienteDto.setNombreCompleto(nombreCompleto);
					clienteDto.setRfc(rs.getString("rfc"));
					
					nominaDto.setClienteDto(clienteDto);
					
					//Data para Grid a primer nivel
					nominaDto.setClienteNombreComercial(clienteDto.getNombreComercial());
					
					if(clienteDto.getRazonSocial() != null && ""!= clienteDto.getRazonSocial()) {
						nominaDto.setClienteRazonSocialONombre(clienteDto.getRazonSocial());	
					}else if (nombreCompleto != null){
						nominaDto.setClienteRazonSocialONombre(clienteDto.getNombreCompleto());
					}
					
					nominaDto.setClienteRFC(clienteDto.getRfc());

					return nominaDto;	
				}
			});
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return Collections.emptyList();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaClientesNominaByCelula(Long idCelula) {
		try {
			
			StringBuilder sb = new StringBuilder();					
			sb.append("  select ");
			sb.append(" 	cn.id_nomina_cliente , ");
			sb.append(" 	cli.id_cliente, ");
			sb.append(" 	cli.nombre_comercial, ");
			sb.append(" 	cli.razon_social, ");
			sb.append(" 	cli.nombre, ");
			sb.append(" 	cli.apellido_paterno, ");
			sb.append(" 	cli.apellido_materno, ");
			sb.append(" 	cli.rfc, ");
			sb.append(" 	p.rfc as rfcPrestadora, ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		count(ncli.id_cliente) ");
			sb.append(" 	from ");
			sb.append(" 		sin.nomina_cliente ncli ");
			sb.append(" 	where ");
			sb.append(" 		ncli.id_cliente = cli.id_cliente ");
		//	sb.append(" 		and ncli.id_cat_producto_nomina in (304, 9958)  ");
			sb.append(" 		and ncli.ind_estatus = 1 ) total_nominas, ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		count(ncli.id_cliente) ");
			sb.append(" 	from ");
			sb.append(" 		sin.nomina_cliente ncli , ppp_nomina pn , ppp_nomina_estatus pne , cat_estatus_nomina cen ");
			sb.append(" 	where ");
			sb.append(" 		ncli.id_nomina_cliente = pn.id_nomina_cliente ");
			sb.append(" 		and pne.id_ppp_nomina = pn.id_ppp_nomina ");
			sb.append(" 		and cen.id_cat_estatus_nomina = pne.id_cat_estatus_nomina ");
			sb.append(" 		and ncli.id_nomina_cliente in ( ");
			sb.append(" 		select ");
			sb.append(" 			cn2.id_nomina_cliente ");
			sb.append(" 		from ");
			sb.append(" 			sin.nomina_cliente cn2 ");
			sb.append(" 		where ");
			sb.append(" 			cn2.id_cliente = cli.id_cliente) ");
			sb.append(" 		and ncli.ind_estatus = 1 ");
			sb.append(" 		and pne.ind_estatus = 1 ");
			sb.append(" 		and cen.clave not in ('TIMBRADA_INC', 'TIMBRADA','CIERRE_NOM', 'NOM_CANC')) total_nominas_en_proceso , ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		cg.descripcion_razon_social ");
			sb.append(" 	from ");
			sb.append(" 		cat_grupo cg ");
			sb.append(" 	where ");
			sb.append(" 		cli.id_cat_grupo = cg.id_cat_grupo ) grupo ");
			sb.append(" from ");
			sb.append(" 	sin.nomina_cliente cn, ");
			sb.append(" 	sin.cliente cli ,  ");
			sb.append(" 	sin.celula_cliente cc,  ");
			sb.append(" 	sin.celula_prestadora_servicio cp,  ");
			sb.append(" 	sin.prestadora_servicio p  ");
			sb.append(" where ");
			sb.append(" 	cli.id_cliente = cn.id_cliente ");
			sb.append(" 	and cli.id_cliente  = cc.id_cliente  ");
			sb.append(" 	and cc.id_celula = cp.id_celula  ");
			sb.append(" 	and cp.id_prestadora_servicio = p.id_prestadora_servicio  ");
			if(idCelula != null) {
				sb.append(" 	and cc.id_celula = " + idCelula);
			}
			
			sb.append(" 	and cli.ind_estatus = 1 ");
			sb.append(" 	and cn.ind_estatus = 1 ");
		//	sb.append(" 	and cn.id_cat_producto_nomina in (304, 9958)  ");
			sb.append(" group by ");
			sb.append(" 	cli.id_cliente ");
			//Se toman en cuenta los estatus ('TIMBRADA_INC','TIMBRADA') ya que 

			
			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setTotalNominas(rs.getInt("total_nominas"));
					nominaDto.setTotalNominasEnProcesoNominista(rs.getInt("total_nominas_en_proceso"));
					nominaDto.setRfcPrestadora(rs.getString("rfcPrestadora"));
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setNombreComercial(rs.getString("grupo")!= null?rs.getString("grupo"):"");
					clienteDto.setRazonSocial(rs.getString("razon_social") != null ? rs.getString("razon_social"):"");
					clienteDto.setNombre(rs.getString("nombre")!= null ? rs.getString("nombre"):"");
					clienteDto.setApellidoPaterno(rs.getString("nombre")!=null?rs.getString("apellido_paterno"):"");
					clienteDto.setApellidoMaterno(rs.getString("apellido_materno")!=null ? rs.getString("apellido_materno") : "");
					String nombreCompleto = null;
					
					if(clienteDto.getNombre() != null && clienteDto.getApellidoPaterno() != null && clienteDto.getApellidoMaterno() != null) {
						 nombreCompleto =clienteDto.getNombre()+" "+clienteDto.getApellidoPaterno();	
					}else if(clienteDto.getNombre() != null && clienteDto.getApellidoPaterno() != null) {
						 nombreCompleto =clienteDto.getNombre()+" "+clienteDto.getApellidoPaterno()+" "+(clienteDto.getApellidoMaterno()!=null ? clienteDto.getApellidoMaterno() : "");
					}
					
					
					clienteDto.setNombreCompleto(nombreCompleto);
					clienteDto.setRfc(rs.getString("rfc"));
					nominaDto.setClienteDto(clienteDto);
					
					//Data para Grid a primer nivel
					nominaDto.setClienteNombreComercial(clienteDto.getNombreComercial());
					
					if(clienteDto.getRazonSocial() != null && ""!= clienteDto.getRazonSocial()) {
						nominaDto.setClienteRazonSocialONombre(clienteDto.getRazonSocial());	
					}else if (nombreCompleto != null){
						nominaDto.setClienteRazonSocialONombre(clienteDto.getNombreCompleto());
					}
					
					nominaDto.setClienteRFC(clienteDto.getRfc());

					return nominaDto;	
				}
			});
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public boolean existeNomina(String clave) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select max(nomina.idPppNomina) from PppNomina nomina where nomina.clave like :clave and nomina.indEstatus = 1");
			query.setString("clave", '%'+clave+'%');
			
			if((Long) query.uniqueResult()!=null) {
				return true;
			}else {
				return false;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeEnCliente ", e);
			return false;
		}		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public Integer maxConsecutivoByIdNomina(Long idNominaCliente) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select max(nomina.consecutivo) from PppNomina nomina where nomina.nominaCliente.idNominaCliente = :idNominaCliente and nomina.indEstatus = 1");
			query.setParameter("idNominaCliente", idNominaCliente);
			
			if((Integer) query.uniqueResult()!=null) {				
				return (Integer) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en maxConsecutivoByIdNomina ", e);
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominaEnProcesoByIdCliente(Long idCliente , Long idNominista) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select distinct");
			sb.append(" 	pppn.id_ppp_nomina, ");
			sb.append(" 	pppn.clave, ");
			sb.append(" 	pppn.fecha_inicio_nomina, ");
			sb.append(" 	pppn.fecha_fin_nomina, ");			
			sb.append(" 	nc.id_nomina_cliente, ");
			sb.append(" 	nc.id_cliente, ");
			sb.append(" 	cli.titulo, ");
			sb.append(" 	cli.percepciones, ");
			sb.append(" 	ctg.clave clavePercepcion, ");
			sb.append(" 	ctg.descripcion, ");		
			sb.append(" 	cli.estimbreSindicato, ");
			sb.append(" 	nc.nombre_nomina, ");
			sb.append(" 	nc.clave_nomina, ");
			sb.append(" 	nc.id_cat_producto_nomina, ");
			sb.append(" 	cg.descripcion as desc_producto_nomina, ");
			sb.append(" 	celcli.id_celula , ");
			sb.append(" 	nc.requiere_factura, ");
			sb.append(" 	nc.genera_timbre, ");
			sb.append(" 	cli.rfc, ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		count(*) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as total_colaboradores , ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		sum(monto_ppp) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as monto_total_colaboradores ");
			
			sb.append(" 	, cen.id_cat_estatus_nomina, cen.descripcion as desc_estatus_nomina ");
			sb.append(" 	, ps.id_prestadora_servicio, ps.razon_social as razon_social_ps , ps.id_consar, ps.es_fondo as es_fondo_ps, ps.logo ");
			sb.append(" 	, cli.id_cat_regimen_fiscal, reg.clave claveRegimen, cli.prefijo_stp , "); 
			
			sb.append("     	ctp.id_tipo_pago , "); 
			sb.append("     	ctp.cve_tipo_pago , "); 
			sb.append("     	ctp.descripcion_tipo_pago, ");
			sb.append("     	psstp.nombre_centro_costos, psstp.clabe_cuenta_ordenante ");
			sb.append("        , ifnull(pnc.id_ppp_nomina,0) complementaria , psstp.id_prestadora_servicio_stp  ");
			sb.append(" from ");
			sb.append(" 	sin.nomina_cliente nc, ");
			sb.append(" 	cliente cli ");
			sb.append(" 	left join cat_general ctg on cli.percepciones =ctg.id_cat_general  ");
			sb.append(" 	left join cat_general reg on cli.id_cat_regimen_fiscal =reg.id_cat_general , ");
			sb.append(" 	cat_maestro cm, ");
			sb.append(" 	cat_general cg , ");
			sb.append(" 	celula_cliente celcli, ");
			sb.append(" 	sin.ppp_nomina pppn left join ppp_nomina_complementaria pnc on pppn.id_ppp_nomina = pnc.id_ppp_nomina ");
			sb.append(" 	, sin.ppp_nomina_estatus ppne ");
			sb.append(" 	,  sin.cat_estatus_nomina cen  ");
			sb.append(" 	, sin.cliente_prestadora_servicio cps, sin.prestadora_servicio ps   "); 
			sb.append(" 	, cat_tipo_pago ctp ");
			sb.append(" 	, prestadora_servicio_stp psstp ");
			sb.append(" where ");
			sb.append(" 	cli.id_cliente = nc.id_cliente ");
			sb.append(" 	and cm.id_cat_maestro = cg.id_cat_maestro ");
			sb.append(" 	and cg.id_cat_general = nc.id_cat_producto_nomina ");
			sb.append(" 	and celcli.id_cliente = nc.id_cliente ");
			sb.append(" 	and celcli.id_cliente = cli.id_cliente ");
			sb.append(" 	and pppn.id_nomina_cliente = nc.id_nomina_cliente ");
			
			sb.append(" 	and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina  ");
			sb.append(" 	and pppn.id_ppp_nomina = ppne.id_ppp_nomina ");
			sb.append(" 	and ppne.id_ppp_nomina_estatus = (select max(ppne.id_ppp_nomina_estatus)  ");
			sb.append(" 										from sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen  ");
			sb.append(" 										where id_ppp_nomina = pppn.id_ppp_nomina  ");
			sb.append(" 										and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ");
			
			
			sb.append(" 	and ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  "); 
			sb.append(" 	and cps.id_cliente = cli.id_cliente  "); 
			sb.append("     and pppn.id_periodicidad  = ctp.id_tipo_pago ");
			sb.append("     and psstp.id_prestadora_servicio_stp  = cps.id_prestadora_servicio_stp ");
			sb.append("     and cen.id_cat_estatus_nomina <> 23  and cen.id_cat_estatus_nomina <> 21");
			sb.append(" 	and ps.es_fondo = 1  "); 
			sb.append(" 	and ps.ind_estatus = 1  "); 
			sb.append(" 	and psstp.ind_estatus=1  "); 
			sb.append(" 	and cps.ind_estatus = 1  ");
			sb.append(" 	and nc.ind_estatus = 1 ");
			sb.append(" 	and pppn.ind_estatus = 1 ");
			sb.append(" 	and nc.id_cliente = ? ");
			
			if(idNominista != null) {
				sb.append(" 	and nc.id_nominista = " + idNominista);	
			}
			
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					List<PrestadoraServicioStpDto> listPrestadoraServicioStp = new ArrayList<PrestadoraServicioStpDto>();
					PrestadoraServicioStpDto spStp= new PrestadoraServicioStpDto();
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setEsNominaComplementaria(rs.getLong("complementaria")==0? Boolean.FALSE: Boolean.TRUE  );
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_fin_nomina")));
					nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					nominaDto.setNecesitaTimbre(rs.getBoolean("genera_timbre"));
					
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("desc_estatus_nomina")));
					
					ClienteDto cliente=new ClienteDto();
					cliente.setIdCliente(idCliente);
					cliente.setRfc(rs.getString("rfc"));
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setClienteDto(cliente);
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
//					nominaClienteDto.setComisionPpp(rs.getLong("comision_ppp"));
//					nominaClienteDto.setComisionSs(rs.getLong("comision_ss"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
//					nominaClienteDto.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio"), rs.getString("rfc_prest_servicio"), rs.getString("nombre_corto_prest_servicio"), rs.getString("razon_social_prest_servicio")));
//					nominaClienteDto.setPrestadoraServicioFondo(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio_fondo"), rs.getString("rfc_prest_servicio_fondo"), rs.getString("nombre_corto_prest_servicio_fondo"), rs.getString("razon_social_prest_servicio_fondo")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
//					prestadoraServicioDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					
					if (rs.getString("logo")!=null) {
					String string = rs.getString("logo").trim();
					String[] parts = string.split(",");
					String part1 = parts[0]; // 123
					String part2 = parts[1]; // 654321
					
					prestadoraServicioDto.setArchivoRecuperadoLogotipo(part2);
					}
					
					spStp.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));
					spStp.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					listPrestadoraServicioStp.add(spStp);
					prestadoraServicioDto.setListprestadoraServicioStpDto(listPrestadoraServicioStp);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					CatGeneralDto catPeriodicidad = new CatGeneralDto();
					catPeriodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					catPeriodicidad.setClave(rs.getString("cve_tipo_pago"));
					catPeriodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					nominaDto.setPeriodicidadNomina(catPeriodicidad);
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					CatGeneralDto regimen =new CatGeneralDto();
					regimen.setIdCatGeneral(rs.getLong("id_cat_regimen_fiscal"));
					regimen.setClave(rs.getString("claveRegimen"));
					clienteDto.setCatRegimenFiscal(regimen);
					
					clienteDto.setTitulo(rs.getString("titulo"));
					clienteDto.setPercepciones(rs.getLong("percepciones"));
					clienteDto.setClavePercepcion(rs.getString("clavePercepcion"));
					clienteDto.setDescripcion(rs.getString("descripcion"));
					clienteDto.setEstimbreSindicato(rs.getLong("estimbreSindicato"));
					nominaDto.setClienteDto(clienteDto);
					
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					return nominaDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaEnProcesoByIdCliente ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaCierreNomina(Long idCliente , Long idNominista) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select copl.id_ppp_nomina,	copl.clave,	copl.fecha_inicio_nomina,	copl.fecha_fin_nomina,	copl.id_nomina_cliente,	copl.id_cliente\r\n " + 
					" ,copl.nombre_nomina,	copl.clave_nomina ,id_cat_producto_nomina,desc_producto_nomina ,id_celula,requiere_factura,genera_timbre\r\n" + 
					" , id_cat_estatus_nomina,desc_estatus_nomina	,id_prestadora_servicio,	razon_social_ps ,	id_consar,	es_fondo_ps	,id_cat_regimen_fiscal\r\n" + 
					" ,prefijo_stp ,id_tipo_pago ,	cve_tipo_pago ,	descripcion_tipo_pago ,nombre_centro_costos,	clabe_cuenta_ordenante\r\n" + 
					" ,total_colaboradores,monto_total_colaboradores, montoNoDispersadoNP ,montoOcupado,(montoNoDispersadoNP-montoOcupado) TOTAL_NO_DISPERSADO ,sub_total subtotal_facturado\r\n" + 
					" from (select npdre.id_ppp_nomina,	npdre.clave,	npdre.fecha_inicio_nomina,	npdre.fecha_fin_nomina,	npdre.id_nomina_cliente,npdre.id_cliente,\r\n" + 
					"        npdre.nombre_nomina,	npdre.clave_nomina , npdre.id_cat_producto_nomina,id_nominista,npdre.desc_producto_nomina, id_celula	,requiere_factura,genera_timbre\r\n" + 
					"       , id_cat_estatus_nomina,desc_estatus_nomina,id_prestadora_servicio,	razon_social_ps ,id_consar,	es_fondo_ps,npdre.id_cat_regimen_fiscal\r\n" + 
					"       ,npdre.prefijo_stp,npdre.id_tipo_pago ,	npdre.cve_tipo_pago,npdre.descripcion_tipo_pago,npdre.nombre_centro_costos,	npdre.clabe_cuenta_ordenante\r\n" + 
					"       ,total_colaboradores ,montoNomina monto_total_colaboradores, ifnull(montoNoDispersadoNP,0) montoNoDispersadoNP,ifnull(montoOcupado,0) montoOcupado, sub_total\r\n" + 
					"    from ( select pn.id_ppp_nomina,	pn.clave,	pn.fecha_inicio_nomina,	pn.fecha_fin_nomina, nc.id_nomina_cliente,	nc.id_cliente\r\n" + 
					"                ,nc.nombre_nomina,nc.clave_nomina, nc.id_cat_producto_nomina,nc.id_nominista, cg.descripcion desc_producto_nomina,cc.id_celula,nc.requiere_factura,nc.genera_timbre\r\n" + 
					"                ,cen.id_cat_estatus_nomina, cen.descripcion desc_estatus_nomina,ps.id_prestadora_servicio,	ps.razon_social as razon_social_ps ,	ps.id_consar\r\n" + 
					"                ,ps.es_fondo as es_fondo_ps,c.id_cat_regimen_fiscal,	c.prefijo_stp 	,ctp.id_tipo_pago ,	ctp.cve_tipo_pago \r\n" + 
					"                ,ctp.descripcion_tipo_pago descripcion_tipo_pago,psstp.nombre_centro_costos,	psstp.clabe_cuenta_ordenante, pnf.sub_total \r\n" + 
					"              from ppp_nomina  pn \r\n" + 
					"                   join ppp_nomina_estatus pne ON pn.id_ppp_nomina = pne.id_ppp_nomina\r\n" + 
					"                   join cat_estatus_nomina cen on pne.id_cat_estatus_nomina = cen.id_cat_estatus_nomina \r\n" + 
					"                   join nomina_cliente nc ON pn.id_nomina_cliente = nc.id_nomina_cliente \r\n" + 
					"                   join cat_general cg on	cg.id_cat_general = nc.id_cat_producto_nomina\r\n" + 
					"                   join cliente c on nc.id_cliente = c.id_cliente \r\n" + 
					"                   join celula_cliente cc on c.id_cliente = cc.id_cliente \r\n" + 
					"                   join cliente_prestadora_servicio cps on	cps.id_cliente = c.id_cliente\r\n" + 
					"                   join prestadora_servicio ps on	ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo\r\n" + 
					"                   join cat_tipo_pago ctp on	pn.id_periodicidad = ctp.id_tipo_pago\r\n" + 
					"                   join prestadora_servicio_stp psstp on	psstp.id_prestadora_servicio_stp = cps.id_prestadora_servicio_stp \r\n" + 
					"                   join ppp_nomima_factura pnf on pn.id_ppp_nomina = pnf.id_ppp_nomina \r\n" + 
					"              where pn.ind_estatus = '1'\r\n" + 
					"                and pne.ind_estatus ='1'\r\n" + 
					"                and cps.ind_estatus ='1'\r\n" + 
					"                and pne.id_cat_estatus_nomina in (19,23)\r\n" + 
					"                --	and pn.id_ppp_nomina = 3602	\r\n" + 
					"            ) npdre\r\n" + 
					"    join (select id_ppp_nomina, count(id_ppp_nomina) total_colaboradores, sum(monto_ppp ) montoNomina \r\n" + 
					"            from ppp_colaborador pc \r\n" + 
					"            group by id_ppp_nomina) nMonto \r\n" + 
					"       on  npdre.id_ppp_nomina= nMonto.id_ppp_nomina		\r\n" + 
					"    join (select	ppl.id_ppp_nomina, 	IFNULL(sum(ppl.monto_ppp),0) montoNoDispersadoNP\r\n" + 
					"            from ppp_colaborador ppl\r\n" + 
					"                join ppp_colaborador_estatus coe on	ppl.ID_PPP_COLABORADOR = coe.ID_PPP_COLABORADOR\r\n" + 
					"               and ppl.ind_estatus = 1\r\n" + 
					"               and coe.ind_estatus = 1\r\n" + 
					"               and COE.id_ppp_colaborador_estatus =(	select	max(id_ppp_colaborador_estatus)\r\n" + 
					"                                                            from			ppp_colaborador_estatus\r\n" + 
					"	                                                         where			ID_PPP_COLABORADOR = coe.ID_PPP_COLABORADOR\r\n" + 
					"                                                               and ind_estatus = 1 )\r\n" + 
					"           where	coe.id_cat_estatus_colaborador in (4,5, 9, 13,14,15)\r\n" + 
					"           group by ppl.id_ppp_nomina 	\r\n" + 
					"          ) mndnp	on npdre.id_ppp_nomina=mndnp.id_ppp_nomina and montoNoDispersadoNP>1\r\n" + 
					"     left join (select pnc.id_ppp_nomina_padre, ifnull(sum(pc.monto_ppp),0) montoOcupado\r\n" + 
					"                  from ppp_colaborador pc \r\n" + 
					"					join ppp_colaborador_estatus pce on pc.id_ppp_colaborador =pce.id_ppp_colaborador \r\n" + 
					"					join ppp_nomina pn ON pc.id_ppp_nomina = pn.id_ppp_nomina \r\n" + 
					"					join ppp_nomina_estatus pne on pn.id_ppp_nomina = pne.id_ppp_nomina \r\n" + 
					"					left join ppp_nomina_complementaria pnc on pc.id_ppp_nomina= pnc.id_ppp_nomina \r\n" + 
					"				where pce.id_ppp_colaborador_estatus =(	select	max(id_ppp_colaborador_estatus)\r\n" + 
					"                                                         from			ppp_colaborador_estatus\r\n" + 
					"                                                         where			ID_PPP_COLABORADOR = pce.ID_PPP_COLABORADOR\r\n" + 
					"                                                              and ind_estatus = 1 )\r\n" + 
					"                and pnc.id_ppp_nomina is not null\r\n" + 
					"                and pn.ind_estatus ='1'\r\n" + 
					"                and pc.ind_estatus ='1'\r\n" + 
					"                and pne.ind_estatus ='1'\r\n" + 
					"                and pce.id_cat_estatus_colaborador not in (4, 5, 9,	13, 14, 15)\r\n" + 
					"                and pne.id_cat_estatus_nomina not in  (21)\r\n" + 
					"                -- and pnc.id_ppp_nomina_padre = 3602	\r\n" + 
					"              group by pnc.id_ppp_nomina_padre) md on npdre.id_ppp_nomina = md.id_ppp_nomina_padre) copl\r\n" + 
					"    where 	(montoNoDispersadoNP-montoOcupado)>1\r\n" + 
					"       and id_cliente =?");


			if(idNominista != null) {
				sb.append(" 	and id_nominista = " + idNominista);	
			}
			
			
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					List<PrestadoraServicioStpDto> listPrestadoraServicioStp = new ArrayList<PrestadoraServicioStpDto>();
					PrestadoraServicioStpDto spStp= new PrestadoraServicioStpDto();
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_fin_nomina")));
					nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					nominaDto.setNecesitaTimbre(rs.getBoolean("genera_timbre"));
					
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("desc_estatus_nomina")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setClienteDto(new ClienteDto(idCliente));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
//					nominaClienteDto.setComisionPpp(rs.getLong("comision_ppp"));
//					nominaClienteDto.setComisionSs(rs.getLong("comision_ss"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
//					nominaClienteDto.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio"), rs.getString("rfc_prest_servicio"), rs.getString("nombre_corto_prest_servicio"), rs.getString("razon_social_prest_servicio")));
//					nominaClienteDto.setPrestadoraServicioFondo(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio_fondo"), rs.getString("rfc_prest_servicio_fondo"), rs.getString("nombre_corto_prest_servicio_fondo"), rs.getString("razon_social_prest_servicio_fondo")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					nominaClienteDto.setMontoNoDispersado(rs.getDouble("TOTAL_NO_DISPERSADO"));
					nominaClienteDto.setSubtotalFacturado(rs.getDouble("subtotal_facturado"));
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
//					prestadoraServicioDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					
					spStp.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));
					spStp.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					listPrestadoraServicioStp.add(spStp);
					prestadoraServicioDto.setListprestadoraServicioStpDto(listPrestadoraServicioStp);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					CatGeneralDto catPeriodicidad = new CatGeneralDto();
					catPeriodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					catPeriodicidad.setClave(rs.getString("cve_tipo_pago"));
					catPeriodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					nominaDto.setPeriodicidadNomina(catPeriodicidad);
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					clienteDto.setCatRegimenFiscal(new CatGeneralDto(rs.getLong("id_cat_regimen_fiscal")));
					nominaDto.setClienteDto(clienteDto);
					
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					return nominaDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCierreNomina ", e);
			return Collections.emptyList();
		}
	}
	
	/*Para obtener el id de la nomina complementaria*/
	@Override
	@Transactional(readOnly = true)
	public NominaDto getNominaComplemenaria(Long idNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();								
			sb.append("SELECT nom.id_ppp_nomina_padre, nom.fecha_alta, nom.id_ppp_nomina ");
			sb.append("FROM sin.ppp_nomina_complementaria nom WHERE nom.ID_PPP_NOMINA_PADRE=? ");
			sb.append("ORDER BY nom.fecha_alta DESC LIMIT 1 ");
			
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(idNomina);
					nominaDto.setIdNominaPPPComplementaria(rs.getLong("id_ppp_nomina"));
					nominaDto.setFechaAlta(rs.getDate("fecha_alta"));
					return nominaDto;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			} else {
				return new NominaDto();
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al obtener la lista complementaria ", e);
			return new NominaDto();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public NominaDto getDatosNominaByIdNomina(Long idNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();								
			/*sb.append(" select pppn.id_ppp_nomina, pppn.id_nomina_cliente, pppn.clave,  ");
			sb.append(" pppn.fecha_inicio_nomina, pppn.fecha_fin_nomina, nc.id_cliente, est.id_cat_estatus_nomina ");
			sb.append(" from sin.ppp_nomina pppn, sin.nomina_cliente nc, sin.ppp_nomina_estatus est ");
			sb.append(" where pppn.id_ppp_nomina = ? ");
			sb.append(" and nc.id_nomina_cliente = pppn.id_nomina_cliente ");
			sb.append(" and pppn.id_ppp_nomina=est.id_ppp_nomina  ");
			sb.append(" and  est.fecha_alta=( select MAX(FECHA_ALTA) from sin.ppp_nomina_estatus where id_ppp_nomina=?) ");
				*/
			sb.append(" select pppn.id_ppp_nomina, pppn.id_nomina_cliente, pppn.clave,  ");
			sb.append(" pppn.fecha_inicio_nomina, pppn.fecha_fin_nomina, nc.id_cliente, est.id_cat_estatus_nomina, comp.id_ppp_nomina_padre, nc.genera_timbre ");
			sb.append(" from sin.ppp_nomina pppn ");
			sb.append(" INNER JOIN sin.nomina_cliente nc ON nc.id_nomina_cliente = pppn.id_nomina_cliente ");
			sb.append(" INNER JOIN sin.ppp_nomina_estatus est ON  pppn.id_ppp_nomina = est.id_ppp_nomina ");
			sb.append(" LEFT JOIN sin.ppp_nomina_complementaria comp ON pppn.id_ppp_nomina=comp.id_ppp_nomina ");		
			sb.append(" where pppn.id_ppp_nomina = ? ");
			sb.append(" and est.id_ppp_nomina_estatus= (select max(ppne.id_ppp_nomina_estatus) from sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen  ");
			sb.append("	where id_ppp_nomina = ?  and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ");			
			
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNomina,idNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(idNomina);
					nominaDto.setNominaClienteDto(new NominaClienteDto(rs.getLong("id_nomina_cliente")));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina")));
					nominaDto.setIdNominaPPPPadre(rs.getLong("id_ppp_nomina_padre"));
					nominaDto.setEsNominaComplementaria(rs.getLong("id_ppp_nomina_padre")>0 ? Boolean.TRUE : Boolean.FALSE);
					nominaDto.setNecesitaTimbre(rs.getBoolean("genera_timbre"));
					return nominaDto;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			} else {
				return new NominaDto();
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return new NominaDto();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> getNominasParaAutorizarFinanciamiento(int idCatEstatusNomina) {
		try {

			StringBuilder sb = new StringBuilder();
			sb.append(" select  cli.id_cliente, cli.nombre_comercial, cli.razon_social, cli.nombre, cli.apellido_paterno, cli.apellido_materno, cli.rfc, ");
			sb.append(" pppn.id_ppp_nomina,  ");
			sb.append(" pppn.clave,  ");
			sb.append(" pppn.fecha_inicio_nomina,  ");
			sb.append(" pppn.fecha_fin_nomina,  ");
			sb.append(" nc.id_nomina_cliente,  ");
			sb.append(" nc.id_cliente,  ");
			sb.append(" nc.nombre_nomina,  ");
			sb.append(" nc.clave_nomina,  ");
			sb.append(" nc.id_cat_producto_nomina,  ");
			sb.append(" cg.descripcion as desc_producto_nomina,  ");
			sb.append(" celcli.id_celula , ");
			sb.append(" ( select count(*)  ");
			sb.append(" from sin.ppp_colaborador  ");
			sb.append(" where id_ppp_nomina = pppn.id_ppp_nomina) as total_colaboradores ,  ");
			sb.append(" ( select sum(monto_ppp)  ");
			sb.append(" from sin.ppp_colaborador  ");
			sb.append(" where id_ppp_nomina = pppn.id_ppp_nomina) as monto_total_colaboradores ,  ");
			sb.append(" (select descripcion  ");
			sb.append(" from sin.cat_estatus_nomina ");
			sb.append(" where id_cat_estatus_nomina = pne.id_cat_estatus_nomina) as desc_estatus_nomina ");
			sb.append(" from  ");
			sb.append(" sin.nomina_cliente nc,  ");
			sb.append(" cliente cli,  ");
			sb.append(" cat_maestro cm,  ");
			sb.append(" cat_general cg ,  ");
			sb.append(" celula_cliente celcli,  ");
			sb.append(" sin.ppp_nomina pppn  ");
			sb.append(" , sin.ppp_nomina_estatus pne ");
			sb.append(" where cli.id_cliente = nc.id_cliente  ");
			sb.append(" and cm.id_cat_maestro = cg.id_cat_maestro  ");
			sb.append(" and cg.id_cat_general = nc.id_cat_producto_nomina  ");
			sb.append(" and celcli.id_cliente = nc.id_cliente  ");
			sb.append(" and celcli.id_cliente = cli.id_cliente  ");
			sb.append(" and pppn.id_nomina_cliente = nc.id_nomina_cliente  ");
			sb.append(" and pne.id_ppp_nomina = pppn.id_ppp_nomina ");
			sb.append(" and pne.id_ppp_nomina_estatus = (select max(nest3.id_ppp_nomina_estatus)  ");
			sb.append(" 								from sin.ppp_nomina_estatus nest3 ");
			sb.append(" 								where nest3.id_cat_estatus_nomina = ").append(idCatEstatusNomina)   ;
			sb.append(" 								and nest3.id_ppp_nomina_estatus = pne.id_ppp_nomina_estatus ");
			sb.append(" 								and nest3.ind_estatus = 1) ");
			sb.append(" and nc.ind_estatus = 1  ");
			sb.append(" and pppn.ind_estatus = 1 ");
			sb.append(" and pne.ind_estatus = 1 ");

			return jdbcTemplate.query(sb.toString(), new Object[] { }, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_fin_nomina")));
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getString("desc_estatus_nomina")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));

					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					clienteDto.setRfc(rs.getString("rfc"));
					clienteDto.setNombreComercial(rs.getString("nombre_comercial"));
					if(rs.getString("apellido_materno") != null) {
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno")+" "+rs.getString("apellido_materno"));
					}else if(rs.getString("nombre")!=null && rs.getString("apellido_paterno")!=null){
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno"));
					}
					clienteDto.setRazonSocial(rs.getString("razon_social"));					
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					return nominaDto;
				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasParaAutorizarFinanciamiento ", e);
			return Collections.emptyList();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public NominaDto getNominaDtoByClave(String claveNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select cli.razon_social, cli.nombre, cli.apellido_paterno, cli.apellido_materno, ");
			sb.append(" 	pppn.id_ppp_nomina, ");
			sb.append(" 	pppn.clave, ");
			sb.append(" 	pppn.fecha_inicio_nomina, ");
			sb.append(" 	pppn.fecha_fin_nomina, ");			
			sb.append(" 	nc.id_nomina_cliente, ");
			sb.append(" 	nc.id_cliente, ");
			sb.append(" 	nc.nombre_nomina, ");
			sb.append(" 	nc.clave_nomina, ");
			sb.append(" 	nc.id_cat_producto_nomina, ");
			sb.append(" 	cg.descripcion as desc_producto_nomina, ");
			sb.append(" 	celcli.id_celula , ");
			sb.append(" 	nc.requiere_factura, ");
			sb.append(" 	nc.genera_timbre, ");
			
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		count(*) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as total_colaboradores , ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		sum(monto_ppp) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as monto_total_colaboradores ");
			sb.append(" 	, cen.id_cat_estatus_nomina, cen.descripcion as desc_estatus_nomina ");
			sb.append(" 	, (select pppnc.id_ppp_nomina_complemento  ");
			sb.append(" 	from sin.ppp_nomina_complemento pppnc  ");
			sb.append(" 	where  pppnc.id_ppp_nomina = pppn.id_ppp_nomina ) as id_nomina_complemneto  ");
			
			sb.append(" 	, ps.id_prestadora_servicio, ps.razon_social as razon_social_ps , ps.id_consar, ps.es_fondo as es_fondo_ps ");
			sb.append(" 	, cli.id_cat_regimen_fiscal, cli.prefijo_stp "); 
			sb.append(" 	, psstp.nombre_centro_costos, psstp.clabe_cuenta_ordenante ");
			
			sb.append(" from ");
			sb.append(" 	sin.nomina_cliente nc, ");
			sb.append(" 	cliente cli, ");
			sb.append(" 	cat_maestro cm, ");
			sb.append(" 	cat_general cg , ");
			sb.append(" 	celula_cliente celcli, ");
			sb.append(" 	sin.ppp_nomina pppn ");
			sb.append(" 	, sin.ppp_nomina_estatus ppne ");
			sb.append(" 	,  sin.cat_estatus_nomina cen  ");
			
			sb.append(" 	,sin.cliente_prestadora_servicio cps, sin.prestadora_servicio ps ");
			sb.append(" 	, sin.prestadora_servicio_stp psstp "); 
			
			sb.append(" where ");
			sb.append(" 	cli.id_cliente = nc.id_cliente ");
			sb.append(" 	and cm.id_cat_maestro = cg.id_cat_maestro ");
			sb.append(" 	and cg.id_cat_general = nc.id_cat_producto_nomina ");
			sb.append(" 	and celcli.id_cliente = nc.id_cliente ");
			sb.append(" 	and celcli.id_cliente = cli.id_cliente ");
			sb.append(" 	and pppn.id_nomina_cliente = nc.id_nomina_cliente ");
			sb.append(" 	and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina  ");
			sb.append(" 	and pppn.id_ppp_nomina = ppne.id_ppp_nomina ");
			sb.append(" 	and ppne.id_ppp_nomina_estatus = (select max(ppne.id_ppp_nomina_estatus)  ");
			sb.append(" 										from sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen  ");
			sb.append(" 										where id_ppp_nomina = pppn.id_ppp_nomina  ");
			sb.append(" 										and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ");
			
			sb.append(" 	and ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  "); 
			sb.append(" 	and cps.id_cliente = cli.id_cliente  "); 
			sb.append(" 	and psstp.id_prestadora_servicio =  ps.id_prestadora_servicio  "); 
			sb.append(" 	and ps.es_fondo = 1  "); 
			sb.append(" 	and ps.ind_estatus = 1  "); 
			sb.append(" 	and cps.ind_estatus = 1  "); 
			sb.append(" 	and psstp.ind_estatus = 1 "); 
			
			sb.append(" 	and nc.ind_estatus = 1 ");
			sb.append(" 	and pppn.ind_estatus = 1 ");
			sb.append(" 	and pppn.clave = ? ");
						
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {claveNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_fin_nomina")));
					nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					nominaDto.setNecesitaTimbre(rs.getBoolean("genera_timbre"));
					
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("desc_estatus_nomina")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					
					
					NominaComplementoDto nominaComplementoDto = new NominaComplementoDto();
					nominaComplementoDto.setIdNominaComplemento(rs.getLong("id_nomina_complemneto"));
					nominaDto.setNominaComplementoDto(nominaComplementoDto);
					
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					PrestadoraServicioStpDto prestadoraServicioStpDto = new PrestadoraServicioStpDto();
					prestadoraServicioStpDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioStpDto.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));	
					prestadoraServicioDto.setPrestadoraServicioStpDto(prestadoraServicioStpDto);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					clienteDto.setRazonSocial(rs.getString("razon_social"));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					if(rs.getString("apellido_materno")!=null) {
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno")+" "+rs.getString("apellido_materno"));
					}else if(rs.getString("nombre")!=null && rs.getString("apellido_paterno")!=null){
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno"));
					}
					clienteDto.setCatRegimenFiscal(new CatGeneralDto(rs.getLong("id_cat_regimen_fiscal")));
					nominaDto.setClienteDto(clienteDto);
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					return nominaDto;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			} else {
				return new NominaDto();
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominaDtoByClave ", e);
			return new NominaDto();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominasFinanzasByCelula(Long idCelula, Long idCatEstatusNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select distinct cli.razon_social, cli.nombre, cli.apellido_paterno, cli.apellido_materno, ");
			sb.append(" pppn.id_ppp_nomina, pppn.clave,    	 ");
			sb.append("  pppn.fecha_inicio_nomina,   	pppn.fecha_fin_nomina,   	 ");
			sb.append(" nc.id_nomina_cliente,   	nc.id_cliente,   	nc.nombre_nomina, nc.clave_nomina,   ");
			sb.append("   	nc.id_cat_producto_nomina,   	cg.descripcion as desc_producto_nomina,   	celcli.id_celula ,   	nc.requiere_factura,  	 ");
			sb.append("      (select count(*) ");
			sb.append("    	from sin.ppp_colaborador   	where id_ppp_nomina = pppn.id_ppp_nomina) as total_colaboradores ,   	 ");
			sb.append("      ( select sum(monto_ppp)   	from sin.ppp_colaborador   	where id_ppp_nomina = pppn.id_ppp_nomina) as monto_total_colaboradores,  ");
			sb.append("      cen.id_cat_estatus_nomina, cen.descripcion as desc_estatus_nomina,  	 ");
			sb.append("      (select count(pppnc.id_ppp_nomina_complemento) 	from sin.ppp_nomina_complemento pppnc   	where  pppnc.id_ppp_nomina = pppn.id_ppp_nomina ) as esComplemento,  ");
			sb.append("      ps.id_prestadora_servicio, ps.razon_social as razon_social_ps , ps.id_consar, ps.es_fondo as es_fondo_ps,  ");
			sb.append("      cli.id_cat_regimen_fiscal, cli.prefijo_stp, psstp.id_prestadora_servicio_stp, psstp.nombre_centro_costos, psstp.clabe_cuenta_ordenante,   	 ");
			sb.append("      ctd.clave clvTipoDispersor  	 ");
			sb.append("    ,  ctp.id_tipo_pago ,ctp.cve_tipo_pago , ctp.descripcion_tipo_pago   ");
			sb.append("     from    ");
			sb.append("  	sin.cliente cli  ");
			sb.append("     inner join sin.nomina_cliente nc on nc.id_cliente = cli.id_cliente    ");
			sb.append("  	inner join sin.cat_general cg on cg.id_cat_general = nc.id_cat_producto_nomina  ");
			sb.append("      inner join sin.cat_maestro cm on cm.id_cat_maestro = cg.id_cat_maestro   	 ");
			sb.append("  	left join sin.celula_cliente celcli on celcli.id_cliente = nc.id_cliente and celcli.id_cliente = cli.id_cliente ");
			sb.append("      inner join sin.cliente_prestadora_servicio cps on cps.id_cliente = cli.id_cliente  ");
			sb.append("     inner join sin.prestadora_servicio ps  on 	ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo  ");
			sb.append("     inner join sin.prestadora_servicio_stp psstp on psstp.id_prestadora_servicio_stp =  cps.id_prestadora_servicio_stp ");
			sb.append("     inner join sin.cat_general ctd  on psstp.id_tipo_dispersor =  ctd.id_cat_general ");
			sb.append("     inner join sin.ppp_nomina pppn  on pppn.id_nomina_cliente = nc.id_nomina_cliente  ");
			sb.append(" 	inner join sin.ppp_nomina_estatus ppne  on  ppne.id_ppp_nomina_estatus = (select max(ppne.id_ppp_nomina_estatus)    ");
			sb.append(" 																			  from sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen  ");
			sb.append(" 																			  where id_ppp_nomina = pppn.id_ppp_nomina  ");
			sb.append("                                                                               and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ");
			sb.append("    inner join sin.cat_tipo_pago ctp on pppn.id_periodicidad  = ctp.id_tipo_pago  ");
			sb.append(" 	inner join sin.cat_estatus_nomina cen  on cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina   	 ");
			sb.append("     where pppn.id_ppp_nomina = ppne.id_ppp_nomina ");
			sb.append(" 	and ps.es_fondo = 1   	 ");
			sb.append(" 	and ps.ind_estatus = 1   	 ");
			sb.append(" 	and psstp.ind_estatus = 1   	 ");
			sb.append("     and cps.ind_estatus = 1   	 ");
			sb.append("     and nc.ind_estatus = 1  ");
			sb.append(" 	and pppn.ind_estatus = 1 ");
			if(idCelula != null) {
			sb.append(" and celcli.id_celula = ").append(idCelula);
			}
			    if(idCatEstatusNomina != null) {
							if(idCatEstatusNomina == 7 || idCatEstatusNomina == 17) {
								sb.append(" 	and ppne.id_cat_estatus_nomina in (7, 17) "); 
							}else {
								sb.append(" 	and ppne.id_cat_estatus_nomina in (").append(idCatEstatusNomina).append(") "); 
							}
						}else {
						sb.append(" 	and ppne.id_cat_estatus_nomina in (7, 17, 15, 18, 19) "); 
						} 	
			sb.append(" order by pppn.id_ppp_nomina ");
			
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_fin_nomina")));
					nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_fin_nomina")));
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("desc_estatus_nomina")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					
					
					NominaComplementoDto nominaComplementoDto = new NominaComplementoDto();
					nominaComplementoDto.setTraeComplemento(rs.getLong("esComplemento")>0?Boolean.TRUE:Boolean.FALSE);
					nominaDto.setNominaComplementoDto(nominaComplementoDto);
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					
					PrestadoraServicioStpDto prestadoraServicioStpDto = new PrestadoraServicioStpDto();
					
					prestadoraServicioStpDto.setIdPrestadoraServicioStp(rs.getLong("id_prestadora_servicio_stp"));
					prestadoraServicioStpDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioStpDto.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));	
					prestadoraServicioStpDto.setClaveTipoDispersor(rs.getString("clvTipoDispersor"));
					prestadoraServicioDto.setPrestadoraServicioStpDto(prestadoraServicioStpDto);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					CatGeneralDto catPeriodicidad = new CatGeneralDto();
					catPeriodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					catPeriodicidad.setClave(rs.getString("cve_tipo_pago"));
					catPeriodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					nominaDto.setPeriodicidadNomina(catPeriodicidad);
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					if(idCelula != null) {
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					}
					clienteDto.setRazonSocial(rs.getString("razon_social"));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					if(rs.getString("apellido_materno")!=null) {
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno")+" "+rs.getString("apellido_materno"));
					}else if(rs.getString("nombre")!=null && rs.getString("apellido_paterno")!=null){
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno"));
					}
					clienteDto.setCatRegimenFiscal(new CatGeneralDto(rs.getLong("id_cat_regimen_fiscal")));
					
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setClienteDto(clienteDto);
					nominaDto.setNominaClienteDto(nominaClienteDto);
					if(clienteDto.getRazonSocial() != null && ""!= clienteDto.getRazonSocial()) {
						nominaDto.setClienteRazonSocialONombre(clienteDto.getRazonSocial());	
					}
					
					
					return nominaDto;	
				}
			});
			
		
			return nomina;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominasFinanzasByCelula ", e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominasSeguimiento(SeguimientoNominaDto segumientoNomina) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select ");
			sb.append(" 	distinct cli.razon_social, ");
			sb.append(" 	cli.nombre, ");
			sb.append(" 	cli.apellido_paterno, ");
			sb.append(" 	cli.apellido_materno, ");
			sb.append(" 	pppn.id_ppp_nomina, ");
			sb.append(" 	pppn.clave, ");
			sb.append(" 	pppn.fecha_inicio_nomina, ");
			sb.append(" 	pppn.fecha_fin_nomina, ");
			sb.append(" 	nc.id_nomina_cliente, ");
			sb.append(" 	nc.id_cliente, ");
			sb.append(" 	nc.nombre_nomina, ");
			sb.append(" 	nc.clave_nomina, ");
			sb.append(" 	nc.id_cat_producto_nomina, ");
			sb.append(" 	cg.descripcion as desc_producto_nomina, ");
			sb.append(" 	celcli.id_celula , ");
			sb.append(" 	nc.requiere_factura, ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		count(*) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as total_colaboradores , ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		sum(monto_ppp) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_colaborador ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina) as monto_total_colaboradores, ");
			sb.append(" 	cen.id_cat_estatus_nomina, ");
			sb.append(" 	cen.descripcion as desc_estatus_nomina, ");
			sb.append(" 	( ");
			sb.append(" 	select ");
			sb.append(" 		if(COUNT(pppnc.id_ppp_nomina_complemento)>0,1,0) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_nomina_complemento pppnc ");
			sb.append(" 	where ");
			sb.append(" 		pppnc.id_ppp_nomina = pppn.id_ppp_nomina ) as traeComplemento, ");
			sb.append(" 	ps.id_prestadora_servicio, ");
			sb.append(" 	ps.razon_social as razon_social_ps , ");
			sb.append(" 	ps.id_consar, ");
			sb.append(" 	ps.es_fondo as es_fondo_ps, ");
			sb.append(" 	cli.id_cat_regimen_fiscal, ");
			sb.append(" 	cli.prefijo_stp, ");
			sb.append(" 	psstp.nombre_centro_costos, ");
			sb.append(" 	psstp.clabe_cuenta_ordenante , ");
			sb.append(" 	ctp.id_tipo_pago , ");
			sb.append(" 	ctp.cve_tipo_pago , ");
			sb.append(" 	ctp.descripcion_tipo_pago ");
			sb.append(" from ");
			sb.append(" 	sin.cliente cli ");
			sb.append(" inner join sin.nomina_cliente nc on ");
			sb.append(" 	nc.id_cliente = cli.id_cliente ");
			sb.append(" inner join sin.cliente_prestadora_servicio cps on ");
			sb.append(" 	cps.id_cliente = cli.id_cliente ");
			sb.append(" inner join sin.prestadora_servicio ps on ");
			sb.append(" 	ps.id_prestadora_servicio = cps.id_prestadora_servicio_fondo ");
			sb.append(" inner join sin.prestadora_servicio_stp psstp on ");
			sb.append(" 	psstp.id_prestadora_servicio = cps.id_prestadora_servicio_stp ");
			sb.append(" inner join sin.cat_general cg on ");
			sb.append(" 	cg.id_cat_general = nc.id_cat_producto_nomina ");
			sb.append(" inner join sin.cat_maestro cm on ");
			sb.append(" 	cm.id_cat_maestro = cg.id_cat_maestro ");
			sb.append(" left join sin.celula_cliente celcli on ");
			sb.append(" 	celcli.id_cliente = nc.id_cliente ");
			sb.append(" inner join sin.ppp_nomina pppn on ");
			sb.append(" 	pppn.id_nomina_cliente = nc.id_nomina_cliente ");
			sb.append(" inner join sin.ppp_nomina_estatus ppne on ");
			sb.append(" 	ppne.id_ppp_nomina_estatus = ( ");
			sb.append(" 	select ");
			sb.append(" 		max(ppne.id_ppp_nomina_estatus) ");
			sb.append(" 	from ");
			sb.append(" 		sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen ");
			sb.append(" 	where ");
			sb.append(" 		id_ppp_nomina = pppn.id_ppp_nomina ");
			sb.append(" 		and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ");
			sb.append(" inner join sin.cat_estatus_nomina cen on ");
			sb.append(" 	cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina ");
			sb.append(" inner join sin.cat_tipo_pago ctp on ");
			sb.append(" 	pppn.id_periodicidad = ctp.id_tipo_pago ");
			sb.append(" where celcli.id_cliente = cli.id_cliente ");
			sb.append(" 	and ps.es_fondo = 1 ");
			sb.append(" 	and ps.ind_estatus = 1 ");
			sb.append(" 	and psstp.ind_estatus = 1   	 ");
			sb.append(" 	and cps.ind_estatus = 1 ");
			sb.append(" 	and nc.ind_estatus = 1 ");
			sb.append(" 	and pppn.ind_estatus = 1 ");
			
			if(segumientoNomina.getIdCelula() != null) {
			sb.append(" and celcli.id_celula = ").append(segumientoNomina.getIdCelula());
			}
			
		    if(segumientoNomina.getCatEstatusNomina() != null) {
		    	if(segumientoNomina.getCatEstatusNomina().getIdCatGeneral() == 7 || segumientoNomina.getCatEstatusNomina().getIdCatGeneral() == 17) {
					sb.append(" 	and cen.id_cat_estatus_nomina in (7, 17) "); 
				}else {
					sb.append(" 	and cen.id_cat_estatus_nomina in (").append(segumientoNomina.getCatEstatusNomina().getIdCatGeneral()).append(") "); 
				}
			}else {
			sb.append(" 	and cen.id_cat_estatus_nomina in (7, 17, 15, 18, 19) "); 
			} 
		    
		    if(segumientoNomina.getFechaInicio() != null && segumientoNomina.getFechaFin() != null) {
				sb.append(" 	and cast(pppn.fecha_inicio_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(segumientoNomina.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'").append(" and ").append("'").append(Utilerias.convirteDateToString(segumientoNomina.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
				sb.append(" 	and cast(pppn.fecha_fin_nomina as date) BETWEEN ").append("'").append(Utilerias.convirteDateToString(segumientoNomina.getFechaInicio(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'").append(" and ").append("'").append(Utilerias.convirteDateToString(segumientoNomina.getFechaFin(), ConstantesComunes.FORMATO_FECHA_yyy_MM_dd).trim()).append("'"); 
			}
			sb.append(" 	order by pppn.id_ppp_nomina "); 
			
			LOGGER.error("ERRORES DE SQL --> consulta " + sb.toString());
			
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToString(rs.getDate("fecha_fin_nomina")));
					nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("desc_estatus_nomina")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					
					
					NominaComplementoDto nominaComplementoDto = new NominaComplementoDto();
					
					nominaComplementoDto.setTraeComplemento(rs.getInt("traeComplemento")==1? Boolean.TRUE : Boolean.FALSE);
					nominaDto.setNominaComplementoDto(nominaComplementoDto);
					PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					PrestadoraServicioStpDto prestadoraServicioStpDto = new PrestadoraServicioStpDto();
					prestadoraServicioStpDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioStpDto.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));	
					prestadoraServicioDto.setPrestadoraServicioStpDto(prestadoraServicioStpDto);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					if(segumientoNomina.getIdCelula() != null) {
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					}
					clienteDto.setRazonSocial(rs.getString("razon_social"));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					
					if(rs.getString("nombre")!=null && rs.getString("apellido_paterno") != null &&  rs.getString("apellido_materno")!=null) {
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno")+" "+rs.getString("apellido_materno"));
					}else if(rs.getString("nombre")!=null && rs.getString("apellido_paterno")!=null){
						clienteDto.setNombreCompleto(rs.getString("nombre")+" "+rs.getString("apellido_paterno"));
					}
					
					CatGeneralDto catPeriodicidad = new CatGeneralDto();
					catPeriodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					catPeriodicidad.setClave(rs.getString("cve_tipo_pago"));
					catPeriodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					nominaDto.setPeriodicidadNomina(catPeriodicidad);
					
					clienteDto.setCatRegimenFiscal(new CatGeneralDto(rs.getLong("id_cat_regimen_fiscal")));
					
					nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setClienteDto(clienteDto);
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					
					if(clienteDto.getRazonSocial() != null && ""!= clienteDto.getRazonSocial()) {
						nominaDto.setClienteRazonSocialONombre(clienteDto.getRazonSocial());	
					}
					
					return nominaDto;	
				}
			});
			
		
			return nomina;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominasSeguimiento ", e);
			return Collections.emptyList();
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public boolean esNominaComplementaria(Long idNomina) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("SELECT  nom.id_ppp_nomina FROM sin.ppp_nomina nom, sin.ppp_nomina_complementaria comp WHERE nom.id_ppp_nomina= comp.id_ppp_nomina AND comp.id_ppp_nomina: idNomina AND nom.ind_estatus=1");
			query.setString("idNomina", "idNomina");
			
			if((Long) query.uniqueResult()!=null) {
				return true;
			}else {
				return false;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en esNominaComplementaria ", e);
			return false;
		}		
	}
	
	
	/*Para obtener el id de la nomina Padre*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public NominaDto getNominaPadre(Long idNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();								
			sb.append("SELECT nom.id_ppp_nomina_padre, nom.fecha_alta, nom.id_ppp_nomina,  nn.clave  ");
			sb.append(" FROM sin.ppp_nomina_complementaria nom  ");
			sb.append(" inner join sin.ppp_nomina nn on nom.id_ppp_nomina_padre =nn.id_ppp_nomina ");
			sb.append("  WHERE nom.id_ppp_nomina =? and nom.IND_ESTATUS=1 ");
			
			@SuppressWarnings("unchecked")
			List<NominaDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNominaPPPPadre(rs.getLong("id_ppp_nomina_padre"));
					nominaDto.setIdNominaPPPComplementaria(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaAlta(rs.getDate("fecha_alta"));
					return nominaDto;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			} else {
				return new NominaDto();
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error al obtener la nomina padre ", e);
			return new NominaDto();
		}
	}


	@Override
	public Long getIdNominaFacturaPadre(Long idNominaHija) {
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select pnf.id_ppp_nomina_factura from ppp_nomina pnh join \r\n"
					+ "ppp_nomina_complementaria pnc on  pnh.id_ppp_nomina = pnc.id_ppp_nomina\r\n"
					+ "join ppp_nomima_factura pnf ON  pnc.id_ppp_nomina_padre = pnf.id_ppp_nomina \r\n"
					+ "where pnh.id_ppp_nomina =" + idNominaHija + " \r\n" + "and  pnh.ind_estatus ='1'\r\n"
					+ "and pnf.ind_estatus ='1' ");

			return jdbcTemplate.queryForLong(sb.toString());
		} catch (Exception e ){
			LOGGER.error("Ocurrio un error al obtener la nomina padre ", e);
			return 0L ;
		}

		
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<NominasDTO> listaNominaAFacturar(String listClientesPatino, Long idCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select   a.id_ppp_nomina, a.clave,  	a.fecha_inicio_nomina,  	a.fecha_fin_nomina, a.total_colaboradores, a.monto_total_colaboradores, a.complementaria,  a.nombre_nomina, a.clave_nomina, a.id_cat_estatus_nomina , a.descripcion   from ( ");
			sb.append("  select distinct pn.id_ppp_nomina, pn.clave,  	pn.fecha_inicio_nomina,  	pn.fecha_fin_nomina,  ");
			sb.append("  (select  	count(*)  	from  ppp_colaborador  	where  id_ppp_nomina = pn.id_ppp_nomina) as total_colaboradores , "); 	
		//	sb.append("  (select  	sum(monto_ppp)  from  ppp_colaborador where id_ppp_nomina = pn.id_ppp_nomina) as monto_total_colaboradores , ");
			sb.append(" (select sub_total from ppp_nomima_factura where id_ppp_nomina = pn.id_ppp_nomina) as monto_total_colaboradores ,  ");
			sb.append("  ifnull(pnc.id_ppp_nomina,0) complementaria,  nc.nombre_nomina, nc.clave_nomina, c.id_cat_estatus_nomina , c.descripcion   ");
			sb.append("   from nomina_cliente nc ");
			sb.append("  inner join ppp_nomina pn on nc.id_nomina_cliente =pn.id_nomina_cliente ");
			sb.append("  inner join ppp_nomina_estatus pne on pne.id_ppp_nomina =pn.id_ppp_nomina ");
			sb.append("  left join ppp_nomina_complementaria pnc on pn.id_ppp_nomina = pnc.id_ppp_nomina ");
			sb.append("   left join cat_estatus_nomina c on pne.id_cat_estatus_nomina =c.id_cat_estatus_nomina  ");
			if(idCliente ==0l) {
				sb.append(" where nc.id_cliente in ( " +listClientesPatino +")");
			}
			else {
				sb.append(" where nc.id_cliente= " +idCliente );
			}
		
			sb.append(" and pne.id_cat_estatus_nomina =1 and pn.ind_estatus = 1 ");
			sb.append("		   and pne.id_ppp_nomina_estatus = (select max(ppne.id_ppp_nomina_estatus)  ");
			sb.append("	  from sin.ppp_nomina_estatus ppne, sin.cat_estatus_nomina cen  ");
			sb.append("	where id_ppp_nomina = pn.id_ppp_nomina ");
			sb.append("	and cen.id_cat_estatus_nomina = ppne.id_cat_estatus_nomina) ) a where total_colaboradores>=1 ");
					   
							
			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					//List<PrestadoraServicioStpDto> listPrestadoraServicioStp = new ArrayList<PrestadoraServicioStpDto>();
					//PrestadoraServicioStpDto spStp= new PrestadoraServicioStpDto();
					
					NominasDTO nominaDto = new NominasDTO();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setEsNominaComplementaria(rs.getLong("complementaria")==0? Boolean.FALSE: Boolean.TRUE  );
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaDto.setFechaInicioNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_inicio_nomina")));
					nominaDto.setFechaFinNominaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_fin_nomina")));
					//nominaDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					//nominaDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));		
					//nominaDto.setNecesitaFactura(rs.getBoolean("requiere_factura"));
					//nominaDto.setNecesitaTimbre(rs.getBoolean("genera_timbre"));
					
					nominaDto.setCatEstatusNomina(new CatGeneralDto(rs.getLong("id_cat_estatus_nomina"), rs.getString("descripcion")));
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					//nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					//nominaClienteDto.setClienteDto(new ClienteDto(idCliente));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
//					nominaClienteDto.setComisionPpp(rs.getLong("comision_ppp"));
//					nominaClienteDto.setComisionSs(rs.getLong("comision_ss"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					//aClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
//					nominaClienteDto.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio"), rs.getString("rfc_prest_servicio"), rs.getString("nombre_corto_prest_servicio"), rs.getString("razon_social_prest_servicio")));
//					nominaClienteDto.setPrestadoraServicioFondo(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio_fondo"), rs.getString("rfc_prest_servicio_fondo"), rs.getString("nombre_corto_prest_servicio_fondo"), rs.getString("razon_social_prest_servicio_fondo")));
					//nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					nominaClienteDto.setTotalColaboradores(rs.getInt("total_colaboradores"));
					nominaClienteDto.setMontoTotalColaboradores(rs.getDouble("monto_total_colaboradores"));
					
					/*PrestadoraServicioDto prestadoraServicioDto = new PrestadoraServicioDto();
					prestadoraServicioDto.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					prestadoraServicioDto.setIdConsar(rs.getString("id_consar"));
					prestadoraServicioDto.setRazonSocial(rs.getString("razon_social_ps"));
//					prestadoraServicioDto.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					prestadoraServicioDto.setEsFondo(rs.getBoolean("es_fondo_ps"));
					*/
				/*	spStp.setClabeCuentaOrdenante(rs.getString("clabe_cuenta_ordenante"));
					spStp.setNombreCentroCostos(rs.getString("nombre_centro_costos"));
					listPrestadoraServicioStp.add(spStp);
					prestadoraServicioDto.setListprestadoraServicioStpDto(listPrestadoraServicioStp);
					nominaDto.setPrestadoraServicioDto(prestadoraServicioDto);
				*/	
				/*	CatGeneralDto catPeriodicidad = new CatGeneralDto();
					catPeriodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					catPeriodicidad.setClave(rs.getString("cve_tipo_pago"));
					catPeriodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					nominaDto.setPeriodicidadNomina(catPeriodicidad);
					
				*/
				
				
				
		
				/*	ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					clienteDto.setPrefijoSTP(rs.getString("prefijo_stp"));
					clienteDto.setCatRegimenFiscal(new CatGeneralDto(rs.getLong("id_cat_regimen_fiscal")));
					nominaDto.setClienteDto(clienteDto);
				*/	
				//	nominaClienteDto.setClienteDto(clienteDto);
					
					nominaDto.setNominaClienteDto(nominaClienteDto);
					
					return nominaDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaaFacturar ", e);
			return Collections.emptyList();
		}
	}
	
	
	
}
