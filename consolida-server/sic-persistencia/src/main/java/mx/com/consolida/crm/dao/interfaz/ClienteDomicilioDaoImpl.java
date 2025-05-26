package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteDomicilio;

@Repository
public class ClienteDomicilioDaoImpl extends GenericDAO<ClienteDomicilio, Long> implements ClienteDomicilioDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteDomicilioDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public DomicilioComunDto convertirDomicilioADto(ClienteDomicilio clienteDomicilio) {
		DomicilioComunDto dto = new DomicilioComunDto();
		DomicilioDto domicilioDto = new DomicilioDto();
		
		domicilioDto.setCatEntidadFederativa(new CatGeneralDto());
		domicilioDto.getCatEntidadFederativa().setIdCatGeneral(clienteDomicilio.getDomicilio().getCatEntidadFederativa().getIdEntidadFederativa());
		domicilioDto.getCatEntidadFederativa().setDescripcion(clienteDomicilio.getDomicilio().getCatEntidadFederativa().getDescripcionEntidadFederativa());
		domicilioDto.getCatEntidadFederativa().setClave(clienteDomicilio.getDomicilio().getCatEntidadFederativa().getCveCatGeneral());
		domicilioDto.setIdEntidadFederativa(clienteDomicilio.getDomicilio().getCatEntidadFederativa().getIdEntidadFederativa());
		
		domicilioDto.setCatMunicipios(new CatGeneralDto());
		domicilioDto.getCatMunicipios().setIdCatGeneral(clienteDomicilio.getDomicilio().getCatMunicipios().getIdCatMunicipios());
		domicilioDto.getCatMunicipios().setClave(clienteDomicilio.getDomicilio().getCatMunicipios().getCveMunicipio());
		domicilioDto.getCatMunicipios().setDescripcion(clienteDomicilio.getDomicilio().getCatMunicipios().getDescripcion());
		
		if(clienteDomicilio.getDomicilio().getCatTipoDomicilio() != null) {
		domicilioDto.setCatTipoDomicilio(new CatGeneralDto());
		domicilioDto.getCatTipoDomicilio().setIdCatGeneral(clienteDomicilio.getDomicilio().getCatTipoDomicilio().getIdCatGeneral());
		domicilioDto.getCatTipoDomicilio().setClave(clienteDomicilio.getDomicilio().getCatTipoDomicilio().getClave());
		domicilioDto.getCatTipoDomicilio().setDescripcion(clienteDomicilio.getDomicilio().getCatTipoDomicilio().getDescripcion());
		}
		
		domicilioDto.setCalle(clienteDomicilio.getDomicilio().getCalle());
		domicilioDto.setCodigoPostal(clienteDomicilio.getDomicilio().getCodigoPostal());
		domicilioDto.setColonia(clienteDomicilio.getDomicilio().getColonia());
		domicilioDto.setNumeroExterior(clienteDomicilio.getDomicilio().getNumeroExterior());
		domicilioDto.setNumeroInterior(clienteDomicilio.getDomicilio().getNumeroInterior());
		domicilioDto.setIdDomicilio(clienteDomicilio.getDomicilio().getIdDomicilio());
		
		dto.setDomicilio(domicilioDto);
		dto.setIndEstatus(clienteDomicilio.getIndEstatus());
		dto.setFechaAlta(clienteDomicilio.getFechaAlta());
		dto.setIdClienteDomicilio(clienteDomicilio.getIdClienteDomicilio());
		return dto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DomicilioComunDto getDatosDomicilioByIdClienteDomicilio(Long IdClienteDomicilio) {
		try {
			
			StringBuilder sb = new StringBuilder();					
			sb.append(" select cd.id_cliente_domicilio, dom.id_domicilio, dom.calle,dom.codigo_postal, dom.colonia, dom.numero_exterior, dom.numero_interior, dom.ind_estatus, dom.fecha_alta, ");
			sb.append(" cef.id_entidad_federativa, cef.descripcion_entidad_federativa , cef.cve_entidad_federativa, ");
			sb.append(" cm.id_cat_municipios, cm.descripcion as descripcion_municipio, cm.cve_municipio, ");
			sb.append(" catma.descripcion, cg.id_cat_general as id_tipo_domicilio, cg.descripcion as desc_tipo_domicilio, cg.clave as clave_tipo_domicilio ");
			sb.append(" from sin.domicilio dom, sin.cliente_domicilio cd, sin.cat_entidad_federativa cef, sin.cat_municipios cm, ");
			sb.append(" sin.cat_maestro catma, sin.cat_general cg ");
			sb.append(" where cd.id_domicilio = dom.id_domicilio ");
			sb.append(" and cef.id_entidad_federativa = dom.id_entidad_federativa ");
			sb.append(" and cm.id_cat_municipios = dom.id_municipio ");
			sb.append(" and catma.id_cat_maestro = cg.id_cat_maestro ");
			sb.append(" and cg.id_cat_general = dom.id_tipo_domicilio ");
			sb.append(" and cd.id_cliente_domicilio = ? ");

			
			List<DomicilioComunDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {IdClienteDomicilio}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					DomicilioComunDto domicilioComun = new DomicilioComunDto();
					
					DomicilioDto domicilioDto = new DomicilioDto();
					
					domicilioDto.setCatEntidadFederativa(new CatGeneralDto());
					domicilioDto.getCatEntidadFederativa().setIdCatGeneral(rs.getLong("id_entidad_federativa"));
					domicilioDto.getCatEntidadFederativa().setDescripcion(rs.getString("descripcion_entidad_federativa"));
					domicilioDto.getCatEntidadFederativa().setClave(rs.getString("cve_entidad_federativa"));
					domicilioDto.setIdEntidadFederativa(rs.getLong("id_entidad_federativa"));
					
					domicilioDto.setCatMunicipios(new CatGeneralDto());
					domicilioDto.getCatMunicipios().setIdCatGeneral(rs.getLong("id_cat_municipios"));
					domicilioDto.getCatMunicipios().setClave(rs.getString("cve_municipio"));
					domicilioDto.getCatMunicipios().setDescripcion(rs.getString("descripcion_municipio"));
					
//					if(clienteDomicilio.getDomicilio().getCatTipoDomicilio() != null) {
					domicilioDto.setCatTipoDomicilio(new CatGeneralDto());
					domicilioDto.getCatTipoDomicilio().setIdCatGeneral(rs.getLong("id_tipo_domicilio"));
					domicilioDto.getCatTipoDomicilio().setClave(rs.getString("clave_tipo_domicilio"));
					domicilioDto.getCatTipoDomicilio().setDescripcion(rs.getString("desc_tipo_domicilio"));
//					}
					
					domicilioDto.setCalle(rs.getString("calle"));
					domicilioDto.setCodigoPostal(rs.getString("codigo_postal"));
					domicilioDto.setColonia(rs.getString("colonia"));
					domicilioDto.setNumeroExterior(rs.getString("numero_exterior"));
					domicilioDto.setNumeroInterior(rs.getString("numero_interior"));
					domicilioDto.setIdDomicilio(rs.getLong("id_domicilio"));
					
					domicilioComun.setDomicilio(domicilioDto);
					domicilioComun.setIndEstatus(rs.getLong("ind_estatus"));
					domicilioComun.setFechaAlta(rs.getDate("fecha_alta"));
					domicilioComun.setIdClienteDomicilio(rs.getLong("id_cliente_domicilio"));
					
					
					return domicilioComun;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			} else {
				return new DomicilioComunDto();
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return new DomicilioComunDto();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public Long getIdClienteDomicilioByIdDomicilio(Long idDomiclio) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select cliDomicilio.idClienteDomicilio from ClienteDomicilio cliDomicilio where cliDomicilio.domicilio.idDomicilio = :idDomiclio and cliDomicilio.indEstatus = 1");
			query.setParameter("idDomiclio", idDomiclio);
			
			if((Long) query.uniqueResult()!=null) {				
				return (Long) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getIdClienteDomicilioById ", e);
			return null;
		}		
	}

}
