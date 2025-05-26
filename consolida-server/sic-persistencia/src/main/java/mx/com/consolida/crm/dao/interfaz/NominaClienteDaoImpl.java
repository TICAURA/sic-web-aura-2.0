package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.FormulaFacturaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.ppp.dto.ValidaCreacionNominaDto;
import mx.com.consolida.util.ConstantesComunes;

@Repository
public class NominaClienteDaoImpl extends GenericDAO<NominaCliente, Long> implements NominaClienteDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaClienteDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<NominaClienteDto> listaNominaCliente(Long idCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();
//			sb.append(" select nc.id_nomina_cliente, nc.id_cliente, nc.nombre_nomina, nc.comision_ppp, nc.comision_ss, nc.clave_nomina, "); 
			sb.append(" select nc.id_nomina_cliente, nc.id_cliente, nc.nombre_nomina,  nc.clave_nomina, nc.requiere_factura, "); 
			sb.append(" nc.id_cat_producto_nomina,cg.descripcion as desc_producto_nomina, nc.id_nominista, celcli.id_celula ");
			sb.append(" ,nc.id_prestadora_servicio ");
			sb.append(" from sin.nomina_cliente nc, cliente cli, cat_maestro cm, cat_general cg, celula_cliente celcli ");
			sb.append(" where cli.id_cliente = nc.id_cliente  and cm.id_cat_maestro = cg.id_cat_maestro  ");
			sb.append(" and cg.id_cat_general = nc.id_cat_producto_nomina  and  nc.ind_estatus = 1 ");
			sb.append(" and celcli.id_cliente = nc.id_cliente and celcli.id_cliente = cli.id_cliente ");
			sb.append(" and nc.id_cliente =? ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
//					nominaClienteDto.setClienteDto(new ClienteDto(idCliente));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					nominaClienteDto.setClienteDto(clienteDto);
					nominaClienteDto.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
					nominaClienteDto.setUsuarioNominista(new NoministaDto());
					nominaClienteDto.getUsuarioNominista().setIdNominista(rs.getLong("id_nominista"));
					nominaClienteDto.setRequiereFactura(rs.getLong("requiere_factura"));
					
					return nominaClienteDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<NominaClienteDto> listaNominaCliente(Long idCliente , Long idNominista) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select ");
			sb.append(" 	nc.id_nomina_cliente, ");
			sb.append(" 	nc.id_cliente, ");
			sb.append(" 	nc.nombre_nomina, ");
			sb.append(" 	nc.clave_nomina, ");
			sb.append(" 	nc.id_cat_producto_nomina, ");
			sb.append(" 	cg.descripcion as desc_producto_nomina, ");
			sb.append(" 	nc.id_nominista, ");
			sb.append(" 	celcli.id_celula,  nch.iva_comercial , ");
			sb.append(" 	nch.id_nomina_cliente  , nch.honorario_ppp  , cf1.id_cat_formulas as idformulaPPP  , cf1.clave as claveFormulaPPP , cf1.descripcion as descripcionFormulaPPP , ");
			sb.append(" 	cf2.id_cat_formulas as id_cat_formulas_factura , cf2.clave as claveFormulaFactura , cf2.descripcion as descFormulaFactura ,cf3.id_cat_formulas as id_cat_formulaIVA  , cf3.clave as  claveIva , cf3.descripcion as descIva ,");
			sb.append(" 	np.id_nomina_periodicidad , ctp.id_tipo_pago , ctp.cve_tipo_pago , ctp.descripcion_tipo_pago ");
			sb.append(" 	, cli.razon_social, cli.nombre, cli.apellido_paterno, cli.apellido_materno, cli.rfc ");
			sb.append("     , (select nombre from celula where id_celula = celcli.id_celula) as nombre_celula");
			sb.append("     , (select razon_social from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)) as razon_social_prestadora "); 
			sb.append(" from ");
			sb.append(" 	nomina_cliente nc ");
			sb.append(" 	inner join  cliente cli on  	cli.id_cliente = nc.id_cliente ");
			sb.append(" 	inner join  cat_general cg on cg.id_cat_general = nc.id_cat_producto_nomina ");
			sb.append(" 	inner join  cat_maestro cm  on cm.id_cat_maestro = cg.id_cat_maestro  ");
			sb.append(" 	inner join  celula_cliente celcli on celcli.id_cliente = cli.id_cliente and celcli.id_cliente = nc.id_cliente ");
			sb.append(" 	left join nomina_cliente_honorario nch  on nc.id_nomina_cliente  = nch.id_nomina_cliente and nch.ind_estatus  = 1 ");
			sb.append(" 	left join cat_formulas cf1  on nch.id_cat_formula_ppp =cf1.id_cat_formulas   ");
			sb.append(" 	left join cat_formulas cf2  on nch.id_cat_formula_factura = cf2.id_cat_formulas ");
			sb.append(" 	left join cat_formulas cf3  on nch.id_cat_tipo_iva = cf3.id_cat_formulas ");
			sb.append(" 	left join nomina_periodicidad np on np.id_nomina  = nc.id_nomina_cliente ");
			sb.append("     left join cat_tipo_pago ctp on	ctp.id_tipo_pago  = np.id_cat_periodicidad ");
			sb.append(" where ");
			sb.append(" 	nc.ind_estatus = 1 ");
			sb.append(" 	and nc.id_cliente = ? ");
			
			if(idNominista != null) {
				sb.append(" 	and nc.id_nominista =  " + idNominista);
			}
			
			sb.append(" 	and nc.id_cat_producto_nomina = ? ");
			
			

			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente, TipoProductoEnum.PPP.getId()}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
//					nominaClienteDto.setClienteDto(new ClienteDto(idCliente));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setRazonSocial(rs.getString("razon_social"));
					clienteDto.setNombre(rs.getString("nombre"));
					clienteDto.setApellidoPaterno(rs.getString("apellido_paterno"));
					clienteDto.setApellidoMaterno(rs.getString("apellido_materno"));
					clienteDto.setRfc(rs.getString("rfc"));
					CelulaDto celula = new CelulaDto();
					celula.setIdCelula(rs.getLong("id_celula"));
					celula.setNombre(rs.getString("nombre_celula"));
					clienteDto.setCelula(celula);
					PrestadoraServicioDto prestadoraServicioFondo = new PrestadoraServicioDto();
					prestadoraServicioFondo.setRazonSocial(rs.getString("razon_social_prestadora"));
					clienteDto.setPrestadoraServicioFondo(prestadoraServicioFondo);
					nominaClienteDto.setClienteDto(clienteDto);
					nominaClienteDto.setUsuarioNominista(new NoministaDto());
					nominaClienteDto.getUsuarioNominista().setIdNominista(rs.getLong("id_nominista"));
					
				
					nominaClienteDto.setFormulaPPP(new CatGeneralDto());
					nominaClienteDto.getFormulaPPP().setIdCatGeneral(rs.getLong("idformulaPPP"));
					nominaClienteDto.getFormulaPPP().setClave(rs.getString("claveFormulaPPP"));
					nominaClienteDto.getFormulaPPP().setDescripcion(rs.getString( "descripcionFormulaPPP"));
					
					
					nominaClienteDto.setFormulaFactura(new CatGeneralDto());
					nominaClienteDto.getFormulaFactura().setIdCatGeneral(rs.getLong("id_cat_formulas_factura"));
					nominaClienteDto.getFormulaFactura().setClave(rs.getString("claveFormulaFactura"));
					nominaClienteDto.getFormulaFactura().setDescripcion(rs.getString("descFormulaFactura"));
					
					
					
					nominaClienteDto.setFormulaTipoIva(new CatGeneralDto());
					nominaClienteDto.getFormulaTipoIva().setIdCatGeneral(rs.getLong("id_cat_formulaIVA"));
					nominaClienteDto.getFormulaTipoIva().setClave(rs.getString("claveIva"));
					nominaClienteDto.getFormulaTipoIva().setDescripcion(rs.getString("descIva"));
					
					nominaClienteDto.setIvaComercial(rs.getString("iva_comercial"));
					
					CatGeneralDto periodicidad = new CatGeneralDto();
					periodicidad.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					periodicidad.setClave(rs.getString("cve_tipo_pago"));
					periodicidad.setDescripcion(rs.getString("descripcion_tipo_pago"));
					
					nominaClienteDto.setPeriodicidadNomina(periodicidad);
					
					nominaClienteDto.setHonorarioPPP(rs.getString("honorario_ppp"));
					
					return nominaClienteDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public FormulaFacturaDto formulaFactura( Long idNominaCliente) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select ");
			sb.append(" 	nc.id_nomina_cliente, ");
			sb.append(" 	nc.id_cliente, ");
			sb.append(" 	nc.nombre_nomina, ");
			sb.append(" 	nc.clave_nomina, ");
			sb.append(" 	nc.id_cat_producto_nomina, ");
			sb.append(" 	cg.descripcion as desc_producto_nomina, ");
			sb.append(" 	nc.id_nominista, ");
			sb.append(" 	celcli.id_celula,  nch.iva_comercial , ");
			sb.append(" 	nch.id_nomina_cliente  , nch.honorario_ppp  , cf1.id_cat_formulas as idformulaPPP  , cf1.clave as claveFormulaPPP , cf1.descripcion as descripcionFormulaPPP , ");
			sb.append(" 	cf2.id_cat_formulas as id_cat_formulas_factura , cf2.clave as claveFormulaFactura , cf2.descripcion as descFormulaFactura ,cf3.id_cat_formulas as id_cat_formulaIVA  , cf3.clave as  claveIva , cf3.descripcion as descIva ");
			sb.append(" from ");
			sb.append(" 	nomina_cliente nc ");
			sb.append(" 	inner join  cliente cli on  	cli.id_cliente = nc.id_cliente ");
			sb.append(" 	inner join  cat_general cg on cg.id_cat_general = nc.id_cat_producto_nomina ");
			sb.append(" 	inner join  cat_maestro cm  on cm.id_cat_maestro = cg.id_cat_maestro  ");
			sb.append(" 	inner join  celula_cliente celcli on celcli.id_cliente = cli.id_cliente and celcli.id_cliente = nc.id_cliente ");
			sb.append(" 	left join nomina_cliente_honorario nch  on nc.id_nomina_cliente  = nch.id_nomina_cliente and nch.ind_estatus  = 1 ");
			sb.append(" 	left join cat_formulas cf1  on nch.id_cat_formula_ppp =cf1.id_cat_formulas   ");
			sb.append(" 	left join cat_formulas cf2  on nch.id_cat_formula_factura = cf2.id_cat_formulas ");
			sb.append(" 	left join cat_formulas cf3  on nch.id_cat_tipo_iva = cf3.id_cat_formulas ");
			sb.append(" where ");
			sb.append(" 	nc.ind_estatus = 1 ");
			sb.append(" 	and nc.id_nomina_cliente = ? ");

			
			
			List<FormulaFacturaDto> datos = jdbcTemplate.query(sb.toString(), new Object[] {idNominaCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					FormulaFacturaDto formulaFactura = new FormulaFacturaDto();
					formulaFactura.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
//					nominaClienteDto.setClienteDto(new ClienteDto(idCliente));
					formulaFactura.setNombreNomina(rs.getString("nombre_nomina"));
					formulaFactura.setClaveNomina(rs.getString("clave_nomina"));
					formulaFactura.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					formulaFactura.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					formulaFactura.setClienteDto(clienteDto);
					
					
				
					formulaFactura.setFormulaPPP(new CatGeneralDto());
					formulaFactura.getFormulaPPP().setIdCatGeneral(rs.getLong("idformulaPPP"));
					formulaFactura.getFormulaPPP().setClave(rs.getString("claveFormulaPPP"));
					formulaFactura.getFormulaPPP().setDescripcion(rs.getString( "descripcionFormulaPPP"));
					
					
					formulaFactura.setFormulaFactura(new CatGeneralDto());
					formulaFactura.getFormulaFactura().setIdCatGeneral(rs.getLong("id_cat_formulas_factura"));
					formulaFactura.getFormulaFactura().setClave(rs.getString("claveFormulaFactura"));
					formulaFactura.getFormulaFactura().setDescripcion(rs.getString("descFormulaFactura"));
					String ivaComercial = rs.getString("iva_comercial");
					if(ivaComercial != null) {
						formulaFactura.setIvaComercial(ivaComercial);	
					}else {
						formulaFactura.setIvaComercial("16");
					}
					
					
					formulaFactura.setFormulaTipoIva(new CatGeneralDto());
					formulaFactura.getFormulaTipoIva().setIdCatGeneral(rs.getLong("id_cat_formulaIVA"));
					formulaFactura.getFormulaTipoIva().setClave(rs.getString("claveIva"));
					formulaFactura.getFormulaTipoIva().setDescripcion(rs.getString("descIva"));
					
					
					formulaFactura.setHonorarioPPP(rs.getString("honorario_ppp"));
					
					return formulaFactura;	
				}
			});
			
			if(datos != null && !datos.isEmpty()) {
				return datos.get(0);
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return new FormulaFacturaDto();
		}
		
		return new FormulaFacturaDto();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaClienteDto> listaDetalleNominaByIdCliente(Long idCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();					
			sb.append(" select  ncli.id_nomina_cliente, ncli.nombre_nomina, ncli.clave_nomina , ncli.comision_ppp, ncli.comision_ss, "); 
			sb.append(" (select cg.descripcion from sin.cat_maestro cm, sin.cat_general cg  "); 
			sb.append(" where cg.id_cat_maestro = cm.id_cat_maestro and cg.id_cat_general = ncli.id_cat_producto_nomina) as desc_producto_nomina, "); 
			sb.append(" (select cg.descripcion from sin.cat_maestro cm, sin.cat_general cg  "); 
			sb.append(" where cg.id_cat_maestro = cm.id_cat_maestro and cg.id_cat_general = ncli.id_prestadora_servicio_fondo) as desc_prestadora_servicio_fondo, "); 
			sb.append(" (select cg.descripcion from sin.cat_maestro cm, sin.cat_general cg  "); 
			sb.append(" where cg.id_cat_maestro = cm.id_cat_maestro and cg.id_cat_general = ncli.id_prestadora_servicio) as desc_prestadora_servicio "); 
			sb.append(" from sin.nomina_cliente ncli, cliente cli "); 
			sb.append(" where ncli.id_cliente = cli.id_cliente "); 
			sb.append(" and cli.id_cliente = ? "); 
			sb.append(" and cli.ind_estatus = 1 "); 
			sb.append(" and ncli.ind_estatus = 1 ");

			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					NominaClienteDto nominaDto = new NominaClienteDto();
					nominaDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaDto.setComisionPpp(rs.getLong("comision_ppp"));
					nominaDto.setComisionSs(rs.getLong("comision_ss"));
					nominaDto.setCatProductoNomina(new CatGeneralDto(rs.getString("desc_producto_nomina")));
					nominaDto.setPrestadoraServicio(new PrestadoraServicioDto(rs.getString("desc_prestadora_servicio")));
					nominaDto.setPrestadoraServicioFondo(new PrestadoraServicioDto(rs.getString("desc_prestadora_servicio_fondo")));
					return nominaDto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public NominaClienteDto nominaClientebyId(Long idNominaCliente) {
		try {
			
			StringBuilder sb = new StringBuilder();sb.append(" select nc.id_nomina_cliente, nc.id_cliente, nc.nombre_nomina,  nc.clave_nomina, "); 
			sb.append(" nc.id_cat_producto_nomina,cg.descripcion as desc_producto_nomina, nc.id_nominista, celcli.id_celula ");
			sb.append(" from sin.nomina_cliente nc, cliente cli, cat_maestro cm, cat_general cg, celula_cliente celcli ");
			sb.append(" where cli.id_cliente = nc.id_cliente  and cm.id_cat_maestro = cg.id_cat_maestro  ");
			sb.append(" and cg.id_cat_general = nc.id_cat_producto_nomina  and  nc.ind_estatus = 1 ");
			sb.append(" and celcli.id_cliente = nc.id_cliente and celcli.id_cliente = cli.id_cliente ");
			sb.append(" and nc.id_nomina_cliente =? ");
			
			List<NominaClienteDto> list = jdbcTemplate.query(sb.toString(), new Object[] {idNominaCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaClienteDto nominaClienteDto = new NominaClienteDto();
					nominaClienteDto.setIdNominaCliente(rs.getLong("id_nomina_cliente"));
					nominaClienteDto.setNombreNomina(rs.getString("nombre_nomina"));
					nominaClienteDto.setClaveNomina(rs.getString("clave_nomina"));
					nominaClienteDto.setCatProductoNomina(new CatGeneralDto(rs.getLong("id_cat_producto_nomina"), rs.getString("desc_producto_nomina")));
					nominaClienteDto.setDescripcionCompuesta(rs.getString("clave_nomina")+" - "+rs.getString("nombre_nomina")+" - "+rs.getString("desc_producto_nomina"));
					ClienteDto clienteDto = new ClienteDto();
					clienteDto.setIdCliente(rs.getLong("id_cliente"));
					clienteDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					nominaClienteDto.setClienteDto(clienteDto);
					nominaClienteDto.setUsuarioNominista(new NoministaDto());
					nominaClienteDto.getUsuarioNominista().setIdNominista(rs.getLong("id_nominista"));
					
					return nominaClienteDto;	
				}
			});
			if(list!=null && !list.isEmpty()) {
				return list.get(0);
			}
			return new NominaClienteDto();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return new NominaClienteDto();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public ValidaCreacionNominaDto validaSecciones(Long idCliente , Long idNominaCliente) {
		
		try {
			
			StringBuilder sb = new StringBuilder();			
			sb.append(" select if(cli.rfc is not null, \"ok\", \"* Favor de ingresar <strong>RFC</strong> en la sección \"\"Datos generales\"\" \") as rfc, ");
			sb.append(" if(cli.id_tipo_persona in (21), if(cli.nombre is not null, \"ok\", \"* Favor de ingresar <strong>Nombre, Apellido paterno</strong> en la sección \"\"Datos generales \"\"\"), if(cli.razon_social is not null, \"ok\", \"* Favor de <strong>Razón social</strong> en la sección \"\"Datos generales \"\"\") ) as razon_social, ");
			sb.append(" if((select id_domicilio from sin.domicilio where ind_estatus = 1 and id_tipo_domicilio = 34 and id_domicilio in (select id_domicilio  from cliente_domicilio where id_cliente = cli.id_cliente)) is not null,  \"ok\", \"* Favor de ingresar <strong>Domicilio fiscal</strong> en la sección \"\"Domicilio \"\"\") as domicilio, ");
			sb.append(" if((select id_cat_periodicidad from sin.nomina_periodicidad where id_nomina = (select id_nomina_cliente ");
			sb.append("																					from sin.nomina_cliente ");
			sb.append("																					where id_cliente = cli.id_cliente ");
			sb.append("																					and id_nomina_cliente = ? ");
			sb.append("																					and id_cat_producto_nomina = 304) and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Periodicidad</strong> en la sección \"\"Nóminas > Ver detalle de la nómina > Periodicidad \"\"\" ) as periodicidad, ");
			sb.append(" if((select id_nomina_cliente  from sin.nomina_cliente_honorario where id_nomina_cliente = (select id_nomina_cliente  ");
			sb.append("																								from sin.nomina_cliente ");
			sb.append("																								where id_cliente = cli.id_cliente ");
			sb.append("																								and id_nomina_cliente = ? ");
			sb.append("																								and id_cat_producto_nomina = 304)  and ind_estatus = 1)is not null, \"ok\", \"* Favor de ingresar <strong>Honorario</strong> en la sección \"\"Honorarios \"\"\" ) as honorario_ppp, ");
			sb.append(" if(cli.prefijo_stp is not null, \"ok\", \"* Favor de ingresar <strong>Prefijo STP</strong> en la sección \"\"Datos STP \"\"\") as prefijo_stp,	 ");
			sb.append(" if((select rfc from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1 ) is not null, \"ok\", \"* Favor de ingresar <strong>RFC</strong> en la sección \"\"Datos generales\"\"\") as rfc_prestadora,	 ");
			sb.append(" if((select nombre_corto from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente =  cli.id_cliente)  and ind_estatus = 1 ) is not null, \"ok\", \"* Favor de ingresar <strong>Nombre corto</strong> en la sección \"\"Datos generales\"\"\") as nombre_corto_prestadora, ");
			sb.append(" if((select razon_social from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente =  cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Razón social</strong> en la sección \"\"Datos generales\"\"\") as razon_social_prestadora, ");
			sb.append(" if((select id_consar from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente =  cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>ID consar</strong> en la sección \"\"Datos generales\"\"\") as id_consar_prestadora, ");
			sb.append(" if((select id_celula from sin.celula_prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Célula</strong> en la sección \"\"Datos generales\"\"\") as celula_prestadora, ");
			sb.append(" if((SELECT id_domicilio FROM sin.prestadora_servicio_domicilio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Domiclio fiscal</strong> en la sección \"\"Domicilio\"\"\") as domicilio_prestadora, ");
			sb.append(" if((SELECT nombre_centro_costos FROM sin.prestadora_servicio_stp where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Nombre centro de costos</strong> en la sección \"\"Datos STP\"\"\") as nomb_centro_costos_prestadora, ");
			sb.append(" if((SELECT clabe_cuenta_ordenante FROM sin.prestadora_servicio_stp where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>CLABE cuenta ordenante</strong> en la sección \"\"Datos STP\"\"\") as clabe_cuenta_ordenante_prestadora, ");
			sb.append(" if((SELECT psd.id_cms FROM definicion_documento dd LEFT JOIN ( SELECT psdi.* FROM prestadora_servicio_doctos psdi WHERE  psdi.id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) ");
			sb.append(" FROM sin.cliente_prestadora_servicio where id_cliente = ?) ) psd  ON dd.id_definicion_documento = psd.id_definicion_documento WHERE dd.id_definicion_documento IN (13)) is not null, \"ok\", \"* Favor de registrar <strong>Archivo KEY</strong> en la sección \"\"CSD\"\"\") as key_csd_prestadora, ");
			sb.append( "if((SELECT psd.id_cms FROM definicion_documento dd LEFT JOIN ( SELECT psdi.* FROM prestadora_servicio_doctos psdi WHERE  psdi.id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) ");
			sb.append(" FROM sin.cliente_prestadora_servicio where id_cliente = ?) ) psd  ON dd.id_definicion_documento = psd.id_definicion_documento WHERE dd.id_definicion_documento IN (14)) is not null, \"ok\", \"* Favor de registrar <strong>Archivo CSD</strong> en la sección \"\"CSD\"\"\") as cer_csd_prestadora, ");
			sb.append(" if((select password_csd from sin.prestadora_servicio where id_prestadora_servicio = (SELECT distinct(id_prestadora_servicio_fondo) FROM sin.cliente_prestadora_servicio where id_cliente = cli.id_cliente)  and ind_estatus = 1) is not null, \"ok\", \"* Favor de ingresar <strong>Contraseña certificado CSD</strong> en la sección \"\"CSD\"\"\") as pass_csd_prestadora ");
			sb.append(" from sin.cliente cli ");
			sb.append(" where cli.id_cliente = ? ");
			sb.append(" and cli.ind_estatus = 1 ");
			
			

			List<ValidaCreacionNominaDto> list =  jdbcTemplate.query(sb.toString(), new Object[] {idNominaCliente, idNominaCliente, idCliente, idCliente, idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String error = "";
					String errorPrestadora = "";
					ValidaCreacionNominaDto valida = new ValidaCreacionNominaDto();
					
					if(!ConstantesComunes.OK.equals(rs.getString("rfc"))) {
						valida.setEsCliente(true);
						error += rs.getString("rfc")+ "<br>";
						
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("razon_social"))) {
						valida.setEsCliente(true);
						error += rs.getString("razon_social")+ " <br>";
						
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("domicilio"))) {
						valida.setEsCliente(true);
						error += rs.getString("domicilio")+ " <br>";
						
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("periodicidad"))) {
						valida.setEsCliente(true);
						error += rs.getString("periodicidad")+ " <br>";
						
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("honorario_ppp"))) {
						valida.setEsCliente(true);
						error += rs.getString("honorario_ppp")+ " <br>";
						
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("prefijo_stp"))) {
						valida.setEsCliente(true);
						error += rs.getString("prefijo_stp");
					}
					
					valida.setModuloClienteError(error);
					
					// prestadora fondo
					if(!ConstantesComunes.OK.equals(rs.getString("rfc_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("rfc_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("nombre_corto_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("nombre_corto_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("razon_social_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("razon_social_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("id_consar_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("id_consar_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("celula_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("celula_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("domicilio_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("domicilio_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("nomb_centro_costos_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("nomb_centro_costos_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("clabe_cuenta_ordenante_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("clabe_cuenta_ordenante_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("key_csd_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("key_csd_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("cer_csd_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("cer_csd_prestadora")+ " <br>";
					}
					
					if(!ConstantesComunes.OK.equals(rs.getString("pass_csd_prestadora"))) {
						valida.setEsPrestadoraServicio(true);
						errorPrestadora += rs.getString("pass_csd_prestadora");
					}

					
					valida.setModuloPresatdoraError(errorPrestadora);

					
					return valida;	
				}
			});
			
			if(list!=null && !list.isEmpty()) {
				return list.get(0);
			}
			
			return null;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en validaSecciones ", e);
			return null;
		}
	}

}
