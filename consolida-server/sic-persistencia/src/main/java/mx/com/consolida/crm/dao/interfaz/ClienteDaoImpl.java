package mx.com.consolida.crm.dao.interfaz;

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
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClientePersonaContactoDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class ClienteDaoImpl extends GenericDAO<Cliente, Long> implements ClienteDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private CelulaClienteDao celulaClienteDao;
	
	@Autowired
	private ClientePrestadoraServicioDao clientePrestadoraServicioDao;
	
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ClienteDto> listarProspectosAutorizar() {
		try {
						
			StringBuilder sb = new StringBuilder();						
			sb.append(" select ct.id_cliente_temp, ct.rfc, ct.razon_social, ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.nombre_comercial, cte.id_cliente_temp_estatus, ct.ind_estatus as ind_estatus_cliente,  ");
			sb.append(" ct.fecha_alta as fecha_alta_cliente, ct.usuario_alta as usuario_alta_cliente,  ");
			sb.append(" cte.id_estatus, cest.descripcion_estatus,  ");
			sb.append(" cg.id_cat_general as id_tipo_persona,cg.descripcion as tipo_persona, cgr.id_cat_grupo, cgr.cve_grupo,cgr.rfc as rfc_grupo, cgr.descripcion_razon_social as descripcion_razon_social_grupo,  ");
			sb.append(" mct.id_medio_contacto_temp, mct.codigo_postal, mct.calle, mct.numero_calle, mct.numero_calle_int, mct.id_entidad_federativa, mct.colonia, cef.descripcion_entidad_federativa, cmun.id_cat_municipios, cmun.descripcion as descripcion_municipios, "); 
			sb.append(" pct.id_persona_contacto_temp, pct.nombre_persona as nombre_persona_contacto,  pct.apellido_paterno as apellido_paterno_persona_contacto, pct.apellido_materno as apellido_materno_persona_contacto,   ");
			sb.append(" pct.telefono as telefono_persona_contacto, pct.correo_electronico   as correo_persona_contacto ");
			sb.append(" , cg2.id_cat_general as id_giro_comercial, cg2.descripcion as desc_giro_comercial, csgc.id_cat_sub_giro_comercial, csgc.descripcion as desc_sub_giro_comercial ");
			sb.append(" from sin.cliente_temp ct, sin.cat_maestro cm, sin.cat_general cg, medio_contacto_temp mct, cat_entidad_federativa cef,  ");
			sb.append(" sin.persona_contacto_temp pct, sin.cat_grupo cgr,  ");
			sb.append(" sin.cliente_temp_estatus cte, sin.cat_estatus cest , cat_municipios cmun ");
			sb.append(" , sin.cat_maestro cm2, sin.cat_general cg2, sin.cat_sub_giro_comercial csgc ");
			sb.append(" where cm.id_cat_maestro = cg.id_cat_maestro ");
			sb.append(" and cm2.id_cat_maestro = cg2.id_cat_maestro  ");
			sb.append(" and ct.id_tipo_persona =  cg.id_cat_general  ");
			sb.append(" and ct.id_giro_comercial = cg2.id_cat_general ");
			sb.append(" and csgc.id_cat_sub_giro_comercial = ct.id_cat_sub_giro_comercial ");
			sb.append(" and mct.id_medio_contacto_temp = ct.id_medio_contacto   ");
			sb.append(" and cef.id_entidad_federativa = mct.id_entidad_federativa   ");
			sb.append(" and cmun.id_cat_municipios = mct.id_municipio  ");
			sb.append(" and pct.id_persona_contacto_temp = ct.id_persona_contacto_temp   ");
			sb.append(" and cgr.id_cat_grupo = ct.id_cat_grupo   ");
			sb.append(" and cte.id_cliente_temp = ct.id_cliente_temp  ");
//			sb.append(" and cte.id_cliente_temp_estatus = (SELECT cte2.id_cliente_temp_estatus   ");
//			sb.append(" 									FROM sin.cliente_temp_estatus cte2 "); 
//			sb.append(" 									where cte2.fecha_alta = (select max(cte3.fecha_alta)   ");
//			sb.append(" 															from sin.cliente_temp_estatus cte3  ");
//			sb.append(" 															where cte3.id_cliente_temp = ct.id_cliente_temp))  ");
			sb.append(" and ct.id_cliente_temp in (SELECT ct.id_cliente_temp  ");
			sb.append(" 							FROM cliente_temp ct  ");
			sb.append(" 							where NOT  exists (select * from sin.cliente cli where cli.id_cliente_temp = ct.id_cliente_temp))  ");
			sb.append(" and cest.id_estatus = cte.id_estatus  ");
			sb.append(" and cest.cve_estatus = 'PROS_AUTORIZADO'   ");
			sb.append(" and cte.ind_estatus = 1 ");
			sb.append(" and ct.ind_estatus = 1  ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClienteDto cliente = null;
					
						cliente = new ClienteDto();
						cliente.setIdClienteTemp(rs.getLong("id_cliente_temp"));
						cliente.setRfc(rs.getString("rfc"));
						cliente.setRazonSocial(rs.getString("razon_social"));
						cliente.setNombre(rs.getString("nombre"));
						cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
						cliente.setApellidoMaterno(rs.getString("apellido_materno"));
						cliente.setNombreComercial(rs.getString("nombre_comercial"));
						cliente.setDescripcionGrupo(rs.getString("descripcion_razon_social_grupo"));
						cliente.setFechaAlta(rs.getDate("fecha_alta_cliente"));
						cliente.setIndEstatus(rs.getLong("ind_estatus_cliente"));
						UsuarioDTO usuarioAlta = new UsuarioDTO();
						usuarioAlta.setIdUsuario(rs.getLong("usuario_alta_cliente"));
						cliente.setUsuarioAlta(usuarioAlta);
						
						
						CatGrupoDto catGrupo = new CatGrupoDto();
						catGrupo.setIdCatGrupo(rs.getLong("id_cat_grupo"));
						catGrupo.setCveGrupo(rs.getString("cve_grupo"));
						catGrupo.setRfc(rs.getString("rfc_grupo"));
						catGrupo.setDescripcionRazonSocial(rs.getString("descripcion_razon_social_grupo"));
						cliente.setCatGrupo(catGrupo);

						CatGeneralDto catGiroComercial = new CatGeneralDto();
						catGiroComercial.setIdCatGeneral(rs.getLong("id_giro_comercial"));
						catGiroComercial.setDescripcion(rs.getString("desc_giro_comercial"));
						cliente.setCatGiroComercialDto(catGiroComercial);
						
						CatSubGiroComercialDto catSubGiroComercialDto = new CatSubGiroComercialDto();
						catSubGiroComercialDto.setIdCatSubGiroComercial(rs.getLong("id_cat_sub_giro_comercial"));
						catSubGiroComercialDto.setDescripcion(rs.getString("desc_sub_giro_comercial"));
						catSubGiroComercialDto.setIdGiroComercial(rs.getLong("id_giro_comercial"));
						cliente.setCatSubGiroComercialDto(catSubGiroComercialDto);
						

						CatEstatusDto catEstatusDto = new CatEstatusDto();
						catEstatusDto.setIdEstatus(rs.getLong("id_estatus"));
						catEstatusDto.setDescripcionEstatus(rs.getString("descripcion_estatus"));
						cliente.setCatEstatus(catEstatusDto);

						CatGeneralDto catTipoPersona = new CatGeneralDto();
						catTipoPersona.setIdCatGeneral(rs.getLong("id_tipo_persona"));
						catTipoPersona.setDescripcion(rs.getString("tipo_persona"));
						cliente.setCatTipoPersona(catTipoPersona);
						
						// DOMICILIO
						DomicilioDto domicilioDto = new DomicilioDto();
						domicilioDto.setIdEntidadFederativa(rs.getLong("id_entidad_federativa"));
						domicilioDto.setCalle(rs.getString("calle"));
						domicilioDto.setNumeroExterior(rs.getString("numero_calle"));
						domicilioDto.setNumeroInterior(rs.getString("numero_calle_int"));
						domicilioDto.setColonia(rs.getString("colonia"));
						domicilioDto.setCodigoPostal(rs.getString("codigo_postal"));
						CatGeneralDto catMunicipios = new CatGeneralDto();
						catMunicipios.setIdCatGeneral(rs.getLong("id_cat_municipios"));
						domicilioDto.setCatMunicipios(catMunicipios);					
						cliente.setDomicilioDto(domicilioDto);
						

						// PERSONA CONTACTO
						ClientePersonaContactoDto personaContacto = new ClientePersonaContactoDto();
						personaContacto.setIdPersonaContactoTemp(rs.getLong("id_persona_contacto_temp"));
						personaContacto.setNombre(rs.getString("nombre_persona_contacto"));
						personaContacto.setApellidoPaterno(rs.getString("apellido_paterno_persona_contacto"));
						personaContacto.setApellidoMaterno(rs.getString("apellido_materno_persona_contacto"));
						personaContacto.setCorreoElectronico(rs.getString("correo_persona_contacto"));
						personaContacto.setTelefono(rs.getString("telefono_persona_contacto"));
						cliente.setClientePersonaContacto(personaContacto);

					return cliente;	
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarProspectosAutorizar ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean existeEnCliente(Long idClienteTemp) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select clien from Cliente clien where clien.clienteTemp.idClienteTemp = :idClienteTemp and clien.indEstatus = 1");
			query.setParameter("idClienteTemp", idClienteTemp);
			
			if((Cliente) query.uniqueResult()!=null) {
				return true;
			}else {
				return false;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeEnCliente ", e);
			return false;
		}		
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ClienteDto> listaProspectosAutorizadosByMesaControl(Long idCelula){
		
		try {
			
			StringBuilder sb = new StringBuilder();			
			sb.append(" select  cli.id_cliente, cli.id_cliente_temp, cli.rfc as rfc_cliente, cli.razon_social, cli.es_activo, "); 
			sb.append(" cli.nombre, cli.apellido_paterno, cli.apellido_materno, cli.nombre_comercial, cli.fecha_constitucion_empresa,  ");   
			sb.append(" cli.cve_registro_patronal, cg.id_cat_grupo, cg.descripcion_razon_social as desc_razon_social_cat_grupo, cg.rfc as rfc_cat_grupo,  ");
			sb.append(" cgen.id_cat_general as id_cat_tipo_persona, cgen.descripcion as descripcion_cat_tipo_persona, cli.usuario_alta, cli.ind_estatus, cli.fecha_alta,  "); 
			sb.append(" cli.id_cat_giro_comercial, cli.prefijo_stp, cli.actividad_economica_final,  "); 
			sb.append(" (select cg2.descripcion from sin.cat_maestro cm2, sin.cat_general cg2   "); 
			sb.append(" where cm2.id_cat_maestro = cg2.id_cat_maestro  "); 
			sb.append(" and cg2.id_cat_general = cli.id_cat_giro_comercial) as desc_cat_giro_comercial,  "); 
			
			sb.append(" cli.id_cat_sub_giro_comercial,  "); 
			sb.append(" (select cg3.descripcion from sin.cat_maestro cm3, sin.cat_general cg3  ");  
			sb.append(" where cm3.id_cat_maestro = cg3.id_cat_maestro   ");
			sb.append(" and cg3.id_cat_general = cli.id_cat_sub_giro_comercial) as desc_cat_sub_giro_comercial,  "); 
			
			sb.append(" cli.id_cat_tipo_pago,  "); 
			sb.append(" (select cg3.descripcion from sin.cat_maestro cm3, sin.cat_general cg3  ");  
			sb.append(" where cm3.id_cat_maestro = cg3.id_cat_maestro  "); 
			sb.append(" and cg3.id_cat_general = cli.id_cat_tipo_pago) as desc_cat_tipo_pago,  "); 
			
			sb.append(" cli.id_cat_categoria,  ");
			sb.append(" (select cg4.descripcion from sin.cat_maestro cm4, sin.cat_general cg4  ");    
			sb.append(" where cm4.id_cat_maestro = cg4.id_cat_maestro  ");   
			sb.append(" and cg4.id_cat_general = cli.id_cat_categoria) as desc_cat_categoria,  ");  
			
			sb.append(" cli.id_cat_regimen_fiscal,  ");
			sb.append(" (select cg5.descripcion from sin.cat_maestro cm5, sin.cat_general cg5  ");    
			sb.append(" where cm5.id_cat_maestro = cg5.id_cat_maestro  ");   
			sb.append(" and cg5.id_cat_general = cli.id_cat_regimen_fiscal) as desc_cat_regimen_fiscal  "); 
			sb.append(" from sin.cliente cli  ");
			sb.append(" LEFT JOIN celula_cliente celcli on  ");
			sb.append(" celcli.id_cliente = cli.id_cliente  ");
			sb.append(" LEFT JOIN celula cel on  ");
			sb.append(" cel.id_celula = celcli.id_celula  ");
			sb.append(" LEFT JOIN cat_grupo cg on  ");
			sb.append(" cg.id_cat_grupo = cli.id_cat_grupo  ");
			sb.append(" LEFT JOIN cat_general cgen on  ");
			sb.append(" cgen.id_cat_general = cli.id_tipo_persona   ");
			sb.append(" where cli.ind_estatus = 1  ");
			
			if(idCelula!=null) {
				sb.append(" and cel.id_celula = ?  ");
			}
			
			sb.append(" order by cli.id_cliente desc  ");
			
			Object[] object = null;
			if(idCelula!=null) {
				object = new Object[] {idCelula};
			}else {
				object = new Object[] {};
			}
			
			return jdbcTemplate.query(sb.toString(), object, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					ClienteDto cliente = new ClienteDto();
					cliente.setIdClienteTemp( rs.getLong("id_cliente_temp") > 0 ? rs.getLong("id_cliente_temp") : null);
					cliente.setIdCliente(rs.getLong("id_cliente"));
					cliente.setRfc(rs.getString("rfc_cliente"));
					cliente.setRazonSocial(rs.getString("razon_social")!=null ? rs.getString("razon_social") : null);
					cliente.setNombre(rs.getString("nombre"));
					cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
					cliente.setApellidoMaterno(rs.getString("apellido_materno"));
					cliente.setNombreComercial(rs.getString("nombre_comercial"));
					if(rs.getString("razon_social")!=null) {
						cliente.setNombreRazonSocial(rs.getString("razon_social"));
					}else if (rs.getString("nombre")!=null &&  rs.getString("apellido_paterno")!=null &&  rs.getString("apellido_materno")!=null){
						cliente.setNombreRazonSocial( rs.getString("nombre") + " "+ rs.getString("apellido_paterno") + " " + rs.getString("apellido_materno"));
					}else if (rs.getString("nombre")!=null &&  rs.getString("apellido_paterno")!=null){
						cliente.setNombreRazonSocial( rs.getString("nombre") + " "+ rs.getString("apellido_paterno"));
					}
					cliente.setFechaConstitucionEmpresa(rs.getDate("fecha_constitucion_empresa")!=null ? rs.getDate("fecha_constitucion_empresa") : null);
					cliente.setCveRegistroPatronal(rs.getString("cve_registro_patronal"));
					cliente.setFechaAlta(rs.getDate("fecha_alta"));
					cliente.setIndEstatus(rs.getLong("ind_estatus"));
					cliente.setEsActivo(rs.getBoolean("es_activo"));
					cliente.setPrefijoSTP(rs.getString("prefijo_stp"));
					cliente.setActividadEconomicaFinal(rs.getString("actividad_economica_final"));
					cliente.setPrestadoraServicioFondo(new PrestadoraServicioDto(clientePrestadoraServicioDao.getidFondoByIdCliente(rs.getLong("id_cliente"))));
					
					CelulaCliente celCli =celulaClienteDao.getCelulaByIdCliente(rs.getLong("id_cliente"));
					cliente.setIdCelulaCliente(celCli.getIdCelulaCliente()!=null ? celCli.getIdCelulaCliente() : null);
					CelulaDto celulaDto = new CelulaDto();
					celulaDto.setIdCelula(celCli.getCelula()!=null ? celCli.getCelula().getIdCelula() : null);
					celulaDto.setClave(celCli.getCelula()!=null ? celCli.getCelula().getClave()  : null);
					celulaDto.setNombre(celCli.getCelula()!=null ? celCli.getCelula().getNombre()  : null);
					cliente.setCelula(celulaDto);
					
					UsuarioDTO usuarioAlta = new UsuarioDTO();
					usuarioAlta.setIdUsuario(rs.getLong("usuario_alta"));
					cliente.setUsuarioAlta(usuarioAlta);
					
					CatGrupoDto catGrupoDto = new CatGrupoDto();
					catGrupoDto.setIdCatGrupo(rs.getLong("id_cat_grupo"));
					catGrupoDto.setDescripcionRazonSocial(rs.getString("desc_razon_social_cat_grupo"));
					catGrupoDto.setRfc(rs.getString("rfc_cat_grupo"));
					cliente.setCatGrupo(catGrupoDto);
					
					CatGeneralDto catTipoPersona = new CatGeneralDto();
					catTipoPersona.setIdCatGeneral(rs.getLong("id_cat_tipo_persona"));
					catTipoPersona.setDescripcion(rs.getString("descripcion_cat_tipo_persona"));
					cliente.setCatTipoPersona(catTipoPersona);
					
					CatGeneralDto catCategoria = new CatGeneralDto();
					catCategoria.setIdCatGeneral(rs.getLong("id_cat_categoria"));
					catCategoria.setDescripcion(rs.getString("desc_cat_categoria"));
					cliente.setCatCategoria(catCategoria);
					
					CatGeneralDto catRegimenFiscal = new CatGeneralDto();
					catRegimenFiscal.setIdCatGeneral(rs.getLong("id_cat_regimen_fiscal"));
					catRegimenFiscal.setDescripcion(rs.getString("desc_cat_regimen_fiscal"));
					cliente.setCatRegimenFiscal(catRegimenFiscal);
					
					// filtro data table
					cliente.setDescripcionRazonSocial(rs.getString("desc_razon_social_cat_grupo")!=null ? rs.getString("desc_razon_social_cat_grupo") : null);
					cliente.setDescripcionTipoPersona(rs.getString("descripcion_cat_tipo_persona")!=null ? rs.getString("descripcion_cat_tipo_persona") : null);


					if(rs.getLong("id_cliente")>0) {
						cliente.setEsCompletoMesaCtrl(true);
					}else {
						cliente.setEsCompletoMesaCtrl(false);
					}

					return cliente;
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaProspectosAutorizadosByMesa ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
	public ClienteDto getDatosGeneralesClienteBiIdCliente(Long idCliente) {
    	
    	try {
        	final List<ClienteDto> clientes = new ArrayList<ClienteDto>();
        	StringBuilder sb = new StringBuilder();
			sb.append(" select  cli.id_cliente, cli.id_cliente_temp, cli.rfc as rfc_cliente, cli.razon_social, cli.es_activo,  ");
			sb.append(" cli.nombre, cli.apellido_paterno, cli.apellido_materno, cli.nombre_comercial, cli.fecha_constitucion_empresa,    ");
			sb.append(" cli.cve_registro_patronal, cg.id_cat_grupo, cg.descripcion_razon_social as desc_razon_social_cat_grupo, cg.rfc as rfc_cat_grupo,  "); 
			sb.append(" cgen.id_cat_general as id_cat_tipo_persona, cgen.descripcion as descripcion_cat_tipo_persona, cli.usuario_alta, cli.ind_estatus, cli.fecha_alta, "); 
			sb.append(" cli.id_cat_giro_comercial, cli.prefijo_stp, actividad_economica_final, ");
			sb.append(" (select cg2.descripcion from sin.cat_maestro cm2, sin.cat_general cg2   ");
			sb.append(" where cm2.id_cat_maestro = cg2.id_cat_maestro  ");
			sb.append(" and cg2.id_cat_general = cli.id_cat_giro_comercial) as desc_cat_giro_comercial,  ");
			sb.append(" cli.id_cat_sub_giro_comercial,  ");
			sb.append(" (select cg3.descripcion from sin.cat_maestro cm3, sin.cat_general cg3   ");
			sb.append(" where cm3.id_cat_maestro = cg3.id_cat_maestro  ");
			sb.append(" and cg3.id_cat_general = cli.id_cat_sub_giro_comercial) as desc_cat_sub_giro_comercial,  ");
			sb.append(" cli.id_cat_tipo_pago,  ");
			sb.append(" (select cg3.descripcion from sin.cat_maestro cm3, sin.cat_general cg3   ");
			sb.append(" where cm3.id_cat_maestro = cg3.id_cat_maestro  ");
			sb.append(" and cg3.id_cat_general = cli.id_cat_tipo_pago) as desc_cat_tipo_pago,  ");
			sb.append(" cli.id_cat_categoria, ");
			sb.append(" (select cg4.descripcion from sin.cat_maestro cm4, sin.cat_general cg4     ");
			sb.append(" where cm4.id_cat_maestro = cg4.id_cat_maestro  ");  
			sb.append(" and cg4.id_cat_general = cli.id_cat_categoria) as desc_cat_categoria,   ");
			sb.append(" rfis.id_cat_general id_regimen_fiscal, ");
			sb.append(" rfis.clave  as clave_regimen_fiscal,   ");
			sb.append(" rfis.descripcion as descripcion_regimen_fiscal  ");  
			sb.append(" from sin.cliente cli, sin.cat_grupo cg, sin.cat_maestro cm, sin.cat_general cgen, sin.cat_general rfis   ");
			sb.append(" where cli.id_cat_grupo = cg.id_cat_grupo  ");  
			sb.append(" and cgen.id_cat_maestro = cm.id_cat_maestro    ");
			sb.append(" and cgen.id_cat_general = cli.id_tipo_persona   "); 
			sb.append(" and rfis.id_cat_general = cli.id_cat_regimen_fiscal   "); 
			sb.append(" and cli.ind_estatus = 1  ");
			sb.append(" and cli.id_cliente = ?  ");
    					
    			@SuppressWarnings("unchecked")
    			List<ClienteDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
    		          public ClienteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  ClienteDto cliente =  new ClienteDto();
    						cliente.setIdClienteTemp( rs.getLong("id_cliente_temp") > 0 ? rs.getLong("id_cliente_temp") : null);
    						cliente.setIdCliente(rs.getLong("id_cliente"));
    						cliente.setRfc(rs.getString("rfc_cliente"));
    						cliente.setRazonSocial(rs.getString("razon_social")!=null ? rs.getString("razon_social") : null);
    						cliente.setNombre(rs.getString("nombre"));
    						cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
    						cliente.setApellidoMaterno(rs.getString("apellido_materno"));
    						cliente.setNombreComercial(rs.getString("nombre_comercial"));
    						cliente.setFechaConstitucionEmpresa(rs.getDate("fecha_constitucion_empresa"));
    						cliente.setCveRegistroPatronal(rs.getString("cve_registro_patronal"));
    						cliente.setFechaAlta(rs.getDate("fecha_alta"));
    						cliente.setIndEstatus(rs.getLong("ind_estatus"));
    						cliente.setCatGiroComercialDto(new CatGeneralDto(rs.getLong("id_cat_giro_comercial"), rs.getString("desc_cat_giro_comercial")));
    						cliente.setCatSubGiroComercialDto(new CatSubGiroComercialDto(rs.getLong("id_cat_sub_giro_comercial"), rs.getString("desc_cat_sub_giro_comercial")));
    						cliente.setCatTipoPago(new CatGeneralDto(rs.getLong("id_cat_tipo_pago"), rs.getString("desc_cat_tipo_pago")));
    						
    						cliente.setEsActivo(rs.getBoolean("es_activo"));
    						cliente.setPrefijoSTP(rs.getString("prefijo_stp"));
    						cliente.setActividadEconomicaFinal(rs.getString("actividad_economica_final"));
    						cliente.setPrestadoraServicioFondo(new PrestadoraServicioDto(clientePrestadoraServicioDao.getidFondoByIdCliente(rs.getLong("id_cliente"))));
    						
    						CelulaCliente celCli =celulaClienteDao.getCelulaByIdCliente(rs.getLong("id_cliente"));
    						cliente.setIdCelulaCliente(celCli.getIdCelulaCliente()!=null ? celCli.getIdCelulaCliente() : null);
    						CelulaDto celulaDto = new CelulaDto();
    						celulaDto.setIdCelula(celCli.getCelula()!=null ? celCli.getCelula().getIdCelula() : null);
    						celulaDto.setClave(celCli.getCelula()!=null ? celCli.getCelula().getClave()  : null);
    						celulaDto.setNombre(celCli.getCelula()!=null ? celCli.getCelula().getNombre()  : null);
    						cliente.setCelula(celulaDto);
    						
    						UsuarioDTO usuarioAlta = new UsuarioDTO();
    						usuarioAlta.setIdUsuario(rs.getLong("usuario_alta"));
    						cliente.setUsuarioAlta(usuarioAlta);
    						
    						CatGrupoDto catGrupoDto = new CatGrupoDto();
    						catGrupoDto.setIdCatGrupo(rs.getLong("id_cat_grupo"));
    						catGrupoDto.setDescripcionRazonSocial(rs.getString("desc_razon_social_cat_grupo"));
    						catGrupoDto.setRfc(rs.getString("rfc_cat_grupo"));
    						cliente.setCatGrupo(catGrupoDto);
    						
    						CatGeneralDto catTipoPersona = new CatGeneralDto();
    						catTipoPersona.setIdCatGeneral(rs.getLong("id_cat_tipo_persona"));
    						catTipoPersona.setDescripcion(rs.getString("descripcion_cat_tipo_persona"));
    						cliente.setCatTipoPersona(catTipoPersona);
    						
    						CatGeneralDto catCategoria = new CatGeneralDto();
    						catCategoria.setIdCatGeneral(rs.getLong("id_cat_categoria"));
    						catCategoria.setDescripcion(rs.getString("desc_cat_categoria"));
    						cliente.setCatCategoria(catCategoria);
    						
    						CatGeneralDto catRegimenFiscal = new CatGeneralDto();
    						catRegimenFiscal.setIdCatGeneral(rs.getLong("id_regimen_fiscal"));
    						catRegimenFiscal.setClave(rs.getString("clave_regimen_fiscal"));
    						catRegimenFiscal.setDescripcion(rs.getString("descripcion_regimen_fiscal"));
    						cliente.setCatRegimenFiscal(catRegimenFiscal);
    						
    						if(rs.getLong("id_cliente")>0) {
    							cliente.setEsCompletoMesaCtrl(true);
    						}else {
    							cliente.setEsCompletoMesaCtrl(false);
    						}
    		        	  clientes.add(cliente);
    		        	  
    		          LOGGER.debug("getDatosGeneralesClienteBiIdCliente--> "+sb);
    				return cliente;
    		          
    			   }
    			   });
    			
    			   if(clientes.isEmpty()) {
    				   ClienteDto clienteVacio =  new ClienteDto();
    				   clientes.add(clienteVacio);
    			   }
    			   
    			   return clientes.get(0);
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return new ClienteDto();
		}
		}
	
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Long getIdClienteByRfc(String rfc) {
		
		try {
			
			if(rfc!=null && !"".equals(rfc.trim())) {
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("select clien.idCliente from Cliente clien where clien.rfc = :rfc and clien.indEstatus = 1");
				query.setParameter("rfc", rfc);
				
				return (Long) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getIdClienteByRfc", e);
			return null;
		}		
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public CelulaCliente getCelulaByIdCelulaRfc(Long idCelula, String rfc) {
		
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" select cel.*, celcli.id_celula_cliente ");
			sb.append(" from sin.celula cel, sin.celula_cliente celcli, cliente cli  ");
			sb.append(" where celcli.id_celula = cel.id_celula  ");
			sb.append(" and cli.id_cliente = celcli.id_cliente  ");
			sb.append(" and celcli.ind_estatus = 1  ");
			sb.append(" and cli.ind_estatus = 1  "); 
			sb.append(" and cli.rfc = ?  ");
			sb.append(" and celcli.id_celula = ?  ");
			
			
			List<CelulaCliente> result = jdbcTemplate.query(sb.toString(), new Object[] { rfc, idCelula},
					new RowMapper<CelulaCliente>() {
				
				public CelulaCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
					CelulaCliente celCli = new CelulaCliente();
					celCli.setIdCelulaCliente(rs.getLong("id_celula_cliente"));
					celCli.setCelula(new Celula(rs.getLong("id_celula"), rs.getString("clave"), rs.getString("nombre")));
					return celCli;
				}
			
			});
			
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			} else {
				return new CelulaCliente();
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCelulaByIdCelulaRfc", e);
			return null;
		}		
	}
	
	


	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
	public DomicilioComunDto obtenerEntidadFederativaXCP(String codigoPostal) {
    	
    	try {
        	final List<DomicilioComunDto> clientes = new ArrayList<DomicilioComunDto>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_entidad_federativa, id_municipio from sin.cat_codigo_postal where codigo_postal = ").append(codigoPostal);
    					
    			@SuppressWarnings("unchecked")
    			List<DomicilioComunDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    		          public DomicilioComunDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  DomicilioComunDto cliente =  new DomicilioComunDto();
    		        	  Long idEntidad = rs.getLong("id_entidad_federativa");
    		        	  Long idMunicipio = rs.getLong("id_municipio");
    		        	  
    		        	  cliente.setDomicilio(new DomicilioDto());
    		        	  cliente.getDomicilio().setCatMunicipios(new CatGeneralDto());
    		        	  
    		        	  cliente.getDomicilio().setIdEntidadFederativa(idEntidad);
    		        	  cliente.getDomicilio().getCatMunicipios().setIdCatGeneral(idMunicipio);
    		        	  clientes.add(cliente);
    		        	  
    		          LOGGER.debug("obtenerEntidadFederativaXCP--> "+sb);
    				return cliente;
    		          
    			   }
    			   });
    			
    			   if(clientes.isEmpty()) {
    				   DomicilioComunDto clienteVacio =  new DomicilioComunDto();
    				   clientes.add(clienteVacio);
    			   }
    			   
    			   return clientes.get(0);
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return new DomicilioComunDto();
		}
		}
	

}
