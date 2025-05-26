package mx.com.consolida.dao.impl; 

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.converter.TechnicalException;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CotizacionColaboradorDao;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.CotizacionColaborador;
import mx.com.consolida.entity.CotizacionTotales;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;

@Repository
public class CotizacionColaboradorDaoImpl extends GenericDAO<CotizacionColaborador, Long> implements CotizacionColaboradorDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CotizacionColaboradorDaoImpl.class);
	
	@Transactional
	public CotizacionColaboradorDto guardarcotizacionColaborador(CotizacionColaboradorDto cotizacionColaborador, UsuarioAplicativo usuarioAplicativo) {
		CotizacionColaboradorDto dto = new CotizacionColaboradorDto();
		try {
			CotizacionColaborador entity = new CotizacionColaborador();
			entity = obtenerEntity(cotizacionColaborador);
			entity.setIdUsuarioAlta(usuarioAplicativo.getIdUsuario());
			entity.setFechaAlta(new Date());
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setIdUsuarioAlta(1L);
			entity.setIdCotizacionColaborador(create(entity).longValue());
			dto = obtenerDTO(entity);
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Ocurrio un error en guardarcotizacionColaborador ", e);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarcotizacionColaborador ", e);
			return new CotizacionColaboradorDto();
			
		}
		return dto;
	}
	
	@Transactional
	public void eliminadoLogicocotizacionColaborador(Long idCotizacion) {
		StringBuilder sb = new StringBuilder();
		sb.append("update sin.cotizacion_colaborador set ind_estatus = 0, fecha_modificacion = current_date() where ind_estatus = 1 and id_cotizacion = "+idCotizacion);
		jdbcTemplate.execute(sb.toString());
	}
	
	@Transactional
	public CotizacionColaboradorDto obtenerCotizacionColaboradorById(Long idCotizacionColaborador) {
		Session session = sessionFactory.openSession();//idColaboradorTemp
		String sql = "from CotizacionColaborador where idCotizacionColaborador = "+idCotizacionColaborador;
		Query qry = session.createQuery(sql);
		List<CotizacionColaborador> entity = qry.list();
		CotizacionColaborador entidad = (entity!=null && !entity.isEmpty()) ? entity.get(0) : new CotizacionColaborador();
		 session.flush();
		 session.clear();
		 session.close();
		return obtenerCcotizacionColaboradorDto(entidad);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CotizacionColaboradorDto> obtenerColaboradoresByIdCotizacion(Long idCotizacion){
		List<CotizacionColaboradorDto> list = new ArrayList<>(); 
		StringBuilder sb = new StringBuilder();
		sb.append(" select sum(dgNetoBruto) as dgNetoBruto, "
				+ "sum(dgporcCotizacionDeseado) as dgporcCotizacionDeseado, "
 				+ "sum(dgVSM) as dgVSM, sum(dgMontoBrutoMensual) as dgMontoBrutoMensual, sum(dgporcBeneficioFiscal) as dgporcBeneficioFiscal, "
				+ "sum(dDiferenciaNeto) as dDiferenciaNeto, sum(dDiferenciaCostoMixto) as dDiferenciaCostoMixto, sum(dDiferenciaCostoSoloPPP) as dDiferenciaCostoSoloPPP, "
				+ "sum(assSD) as assSD, sum(assSDI) as assSDI, sum(assSuedo) as assSuedo, sum(assGravados) as assGravados, sum(assExentos) as assExentos, sum(assSubsidio) as assSubsidio, "
				+ "sum(assISR) as assISR, sum(assCoImss) as assCoImss, sum(assNetoNomina) as assNetoNomina, sum(aasAsimialdos) as aasAsimialdos, sum(aasISR) as aasISR, sum(aasNetoAsimilados) as aasNetoAsimilados, "
				+ "sum(aOtros) as aOtros, sum(aNetoTotal) as aNetoTotal, sum(acfaPercepcionTotal) as acfaPercepcionTotal, sum(acfaCargaSocial) as acfaCargaSocial, "
				+ "sum(acfaComision) as acfaComision, sum(acfaSubtotalFactura) as acfaSubtotalFactura, sum(acfaIVA) as acfaIVA, sum(acfaTotalFactura) as acfaTotalFactura, sum(gsssSD) as gsssSD, sum(gsssSDI) as gsssSDI, "
				+ "sum(gsssSuedo) as gsssSuedo, sum(gsssExentos) as gsssExentos, sum(gsssSubsidio) as gsssSubsidio, sum(gsssISR) as gsssISR, sum(gsssCoImss) as gsssCoImss, sum(gsssNetoNomina) as gsssNetoNomina, sum(gsePlanPensionesPrivada) as gsePlanPensionesPrivada, "
				+ "sum(gsNetoTotal) as gsNetoTotal, sum(gscfmPercepcionTotal) as gscfmPercepcionTotal, sum(gscfmCargaSocial) as gscfmCargaSocial, sum(gscfmComision) as gscfmComision, "
				+ "sum(gscfmSubtotalFactura) as gscfmSubtotalFactura, sum(gscfmIVA) as gscfmIVA, sum(gscfmTotalFactura) as gscfmTotalFactura, sum(gscfpppPercepcionNomina) as gscfpppPercepcionNomina, sum(gscfpppCargaSocial) as gscfpppCargaSocial, "
				+ "sum(gscfpppCostoNomina) as gscfpppCostoNomina, sum(gscfpppPPP) as gscfpppPPP, sum(gscfpppComision) as gscfpppComision, sum(gscfpppSubtotalFactura) as gscfpppSubtotalFactura, "
				+ "sum(gscfpppIVA) as gscfpppIVA, sum(gscfpppTotalFactura) as gscfpppTotalFactura, sum(gscfpppCostoTotal) as gscfpppCostoTotal "
				+ "from cotizacion_colaborador where ind_estatus = 1 and id_cotizacion = " + idCotizacion);
//		sb.append("select id_cotizacion_colaborador, dgNombre, dgPuesto, dgPrimaDeRiesgo, dgFechaDeAntIguedad, dgporcIMSS, dgporcPPP, dgNetoBruto, dgporcCotizaciónDeseado as dgporcCotizacionDeseado, dgVSM, dgMontoBrutoMensual, dgporcBeneficioFiscal, dDiferenciaNeto, dDiferenciaCostoMixto, dDiferenciaCostoSoloPPP, assSD, assSDI, assSuedo, assGravados, assExentos, assSubsidio, assISR, assCoImss, assNetoNomina, aasAsimialdos, aasISR, aasNetoAsimilados, aOtros, aNetoTotal, acfaPercepcionTotal, acfaCargaSocial, acfaComision, acfaSubtotalFactura, acfaIVA, acfaTotalFactura, gsssSD, gsssSDI, gsssSuedo, gsssExentos, gsssSubsidio, gsssISR, gsssCoImss, gsssNetoNomina, gsePlanPensionesPrivada, gsNetoTotal, gscfmPercepcionTotal, gscfmCargaSocial, gscfmComision, gscfmSubtotalFactura, gscfmIVA, gscfmTotalFactura, gscfpppPercepcionNomina, gscfpppCargaSocial, gscfpppCostoNomina, gscfpppPPP, gscfpppComision, gscfpppSubtotalFactura, gscfpppIVA, gscfpppTotalFactura, gscfpppCostoTotal, id_colaborador_temp, id_cotizacion, fecha_alta, fecha_modificacion, usuario_alta, usuario_modificacion, ind_estatus from cotizacion_colaborador where ind_estatus = 1 and id_cotizacion = " + idCotizacion);
		list = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {

			public CotizacionColaboradorDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  CotizacionColaboradorDto dto = new CotizacionColaboradorDto();
//	      		dto.setIdCotizacionColaborador(rs.getLong("id_cotizacion_colaborador"));
//	      		dto.setDgNombre(rs.getString("dgNombre"));
//	      		dto.setDgPuesto(rs.getString("dgPuesto"));
//	      		dto.setDgPrimaDeRiesgo(rs.getString("dgPrimaDeRiesgo"));
//	      		dto.setDgFechaDeAntIguedad(rs.getDate("dgFechaDeAntIguedad"));
//	      		dto.setDgporcIMSS(rs.getBigDecimal("dgporcIMSS"));
//	      		dto.setDgporcPPP(rs.getBigDecimal("dgporcPPP"));
	      		dto.setDgNetoBruto(rs.getBigDecimal("dgNetoBruto"));
	      		dto.setDgporcCotizacionDeseado(rs.getBigDecimal("dgporcCotizacionDeseado"));
	      		dto.setDgVSM(rs.getBigDecimal("dgVSM"));
	      		dto.setDgMontoBrutoMensual(rs.getBigDecimal("dgMontoBrutoMensual"));
	      		dto.setDgporcBeneficioFiscal(rs.getBigDecimal("dgporcBeneficioFiscal"));
	      		dto.setdDiferenciaNeto(rs.getBigDecimal("dDiferenciaNeto"));
	      		dto.setdDiferenciaCostoMixto(rs.getBigDecimal("dDiferenciaCostoMixto"));
	      		dto.setdDiferenciaCostoSoloPPP(rs.getBigDecimal("dDiferenciaCostoSoloPPP"));
	      		dto.setAssSD(rs.getBigDecimal("assSD"));
	      		dto.setAssSDI(rs.getBigDecimal("assSDI"));
	      		dto.setAssSuedo(rs.getBigDecimal("assSuedo"));
	      		dto.setAssGravados(rs.getBigDecimal("assGravados"));
	      		dto.setAssExentos(rs.getBigDecimal("assExentos"));
	      		dto.setAssSubsidio(rs.getBigDecimal("assSubsidio"));
	      		dto.setAssISR(rs.getBigDecimal("assISR"));
	      		dto.setAssCoImss(rs.getBigDecimal("assCoImss"));
	      		dto.setAssNetoNomina(rs.getBigDecimal("assNetoNomina"));
	      		dto.setAasAsimialdos(rs.getBigDecimal("aasAsimialdos"));
	      		dto.setAasISR(rs.getBigDecimal("aasISR"));
	      		dto.setAasNetoAsimilados(rs.getBigDecimal("aasNetoAsimilados"));
	      		dto.setaOtros(rs.getBigDecimal("aOtros"));
	      		dto.setaNetoTotal(rs.getBigDecimal("aNetoTotal"));
	      		dto.setAcfaPercepcionTotal(rs.getBigDecimal("acfaPercepcionTotal"));
	      		dto.setAcfaCargaSocial(rs.getBigDecimal("acfaCargaSocial"));
	      		dto.setAcfaComision(rs.getBigDecimal("acfaComision"));
	      		dto.setAcfaSubtotalFactura(rs.getBigDecimal("acfaSubtotalFactura"));
	      		dto.setAcfaIVA(rs.getBigDecimal("acfaIVA"));
	      		dto.setAcfaTotalFactura(rs.getBigDecimal("acfaTotalFactura"));
	      		dto.setGsssSD(rs.getBigDecimal("gsssSD"));
	      		dto.setGsssSDI(rs.getBigDecimal("gsssSDI"));
	      		dto.setGsssSuedo(rs.getBigDecimal("gsssSuedo"));
	      		dto.setGsssExentos(rs.getBigDecimal("gsssExentos"));
	      		dto.setGsssSubsidio(rs.getBigDecimal("gsssSubsidio"));
	      		dto.setGsssISR(rs.getBigDecimal("gsssISR"));
	      		dto.setGsssCoImss(rs.getBigDecimal("gsssCoImss"));
	      		dto.setGsssNetoNomina(rs.getBigDecimal("gsssNetoNomina"));
	      		dto.setGsePlanPensionesPrivada(rs.getBigDecimal("gsePlanPensionesPrivada"));
	      		dto.setGsNetoTotal(rs.getBigDecimal("gsNetoTotal"));
	      		dto.setGscfmPercepcionTotal(rs.getBigDecimal("gscfmPercepcionTotal"));
	      		dto.setGscfmCargaSocial(rs.getBigDecimal("gscfmCargaSocial"));
	      		dto.setGscfmComision(rs.getBigDecimal("gscfmComision"));
	      		dto.setGscfmSubtotalFactura(rs.getBigDecimal("gscfmSubtotalFactura"));
	      		dto.setGscfmIVA(rs.getBigDecimal("gscfmIVA"));
	      		dto.setGscfmTotalFactura(rs.getBigDecimal("gscfmTotalFactura"));
	      		dto.setGscfpppPercepcionNomina(rs.getBigDecimal("gscfpppPercepcionNomina"));
	      		dto.setGscfpppCargaSocial(rs.getBigDecimal("gscfpppCargaSocial"));
	      		dto.setGscfpppCostoNomina(rs.getBigDecimal("gscfpppCostoNomina"));
	      		dto.setGscfpppPPP(rs.getBigDecimal("gscfpppPPP"));
	      		dto.setGscfpppComision(rs.getBigDecimal("gscfpppComision"));
	      		dto.setGscfpppSubtotalFactura(rs.getBigDecimal("gscfpppSubtotalFactura"));
	      		dto.setGscfpppIVA(rs.getBigDecimal("gscfpppIVA"));
	      		dto.setGscfpppTotalFactura(rs.getBigDecimal("gscfpppTotalFactura"));
	      		dto.setGscfpppCostoTotal(rs.getBigDecimal("gscfpppCostoTotal"));
//	      		dto.setIdUsuarioAlta(rs.getLong("usuario_alta"));
//	      		dto.setFechaAlta(rs.getDate("fecha_alta"));
//	      		dto.setIndEstatus(rs.getLong("ind_estatus"));
//	      		dto.setIdCotizacion(new CotizacionDto(rs.getLong("id_cotizacion")));
//	      		dto.setIdColaboradorTemp(new ColaboradoresTempDto(rs.getLong("id_colaborador_temp")));
//	      		dto.setFechaModificacion(rs.getDate("fecha_modificacion"));
	      		return dto;
			   }
			   });		
		return list;
	}
	
	
	public CotizacionColaboradorDto  obtenerCcotizacionColaboradorDto(CotizacionColaborador enty) {
		CotizacionColaboradorDto dto = new CotizacionColaboradorDto();
		dto.setIdCotizacionColaborador(enty.getIdCotizacionColaborador());
		dto.setDgNombre(enty.getDgNombre());
		dto.setDgPuesto(enty.getDgPuesto());
		dto.setDgPrimaDeRiesgo(enty.getDgPrimaDeRiesgo());
		dto.setDgFechaDeAntIguedad(enty.getDgFechaDeAntIguedad());
		dto.setDgporcIMSS(enty.getDgporcIMSS());
		dto.setDgporcPPP(enty.getDgporcPPP());
		dto.setDgNetoBruto(enty.getDgNetoBruto());
		dto.setDgporcCotizacionDeseado(enty.getDgporcCotizacionDeseado());
		dto.setDgVSM(enty.getDgVSM());
		dto.setDgMontoBrutoMensual(enty.getDgMontoBrutoMensual());
		dto.setDgporcBeneficioFiscal(enty.getDgporcBeneficioFiscal());
		dto.setdDiferenciaNeto(enty.getdDiferenciaNeto());
		dto.setdDiferenciaCostoMixto(enty.getdDiferenciaCostoMixto());
		dto.setdDiferenciaCostoSoloPPP(enty.getdDiferenciaCostoSoloPPP());
		dto.setAssSD(enty.getAssSD());
		dto.setAssSDI(enty.getAssSDI());
		dto.setAssSuedo(enty.getAssSuedo());
		dto.setAssGravados(enty.getAssGravados());
		dto.setAssExentos(enty.getAssExentos());
		dto.setAssSubsidio(enty.getAssSubsidio());
		dto.setAssISR(enty.getAssISR());
		dto.setAssCoImss(enty.getAssCoImss());
		dto.setAssNetoNomina(enty.getAssNetoNomina());
		dto.setAasAsimialdos(enty.getAasAsimialdos());
		dto.setAasISR(enty.getAasISR());
		dto.setAasNetoAsimilados(enty.getAasNetoAsimilados());
		dto.setaOtros(enty.getaOtros());
		dto.setaNetoTotal(enty.getaNetoTotal());
		dto.setAcfaPercepcionTotal(enty.getAcfaPercepcionTotal());
		dto.setAcfaCargaSocial(enty.getAcfaCargaSocial());
		dto.setAcfaComision(enty.getAcfaComision());
		dto.setAcfaSubtotalFactura(enty.getAcfaSubtotalFactura());
		dto.setAcfaIVA(enty.getAcfaIVA());
		dto.setAcfaTotalFactura(enty.getAcfaTotalFactura());
		dto.setGsssSD(enty.getGsssSD());
		dto.setGsssSDI(enty.getGsssSDI());
		dto.setGsssSuedo(enty.getGsssSuedo());
		dto.setGsssExentos(enty.getGsssExentos());
		dto.setGsssSubsidio(enty.getGsssSubsidio());
		dto.setGsssISR(enty.getGsssISR());
		dto.setGsssCoImss(enty.getGsssCoImss());
		dto.setGsssNetoNomina(enty.getGsssNetoNomina());
		dto.setGsePlanPensionesPrivada(enty.getGsePlanPensionesPrivada());
		dto.setGsNetoTotal(enty.getGsNetoTotal());
		dto.setGscfmPercepcionTotal(enty.getGscfmPercepcionTotal());
		dto.setGscfmCargaSocial(enty.getGscfmCargaSocial());
		dto.setGscfmComision(enty.getGscfmComision());
		dto.setGscfmSubtotalFactura(enty.getGscfmSubtotalFactura());
		dto.setGscfmIVA(enty.getGscfmIVA());
		dto.setGscfmTotalFactura(enty.getGscfmTotalFactura());
		dto.setGscfpppPercepcionNomina(enty.getGscfpppPercepcionNomina());
		dto.setGscfpppCargaSocial(enty.getGscfpppCargaSocial());
		dto.setGscfpppCostoNomina(enty.getGscfpppCostoNomina());
		dto.setGscfpppPPP(enty.getGscfpppPPP());
		dto.setGscfpppComision(enty.getGscfpppComision());
		dto.setGscfpppSubtotalFactura(enty.getGscfpppSubtotalFactura());
		dto.setGscfpppIVA(enty.getGscfpppIVA());
		dto.setGscfpppTotalFactura(enty.getGscfpppTotalFactura());
		dto.setGscfpppCostoTotal(enty.getGscfpppCostoTotal());
		dto.setIdUsuarioAlta(enty.getIdUsuarioAlta());
		dto.setFechaAlta(enty.getFechaAlta());
		dto.setIndEstatus(enty.getIndEstatus());
		dto.setIdCotizacion(enty.getIdCotizacion()!=null ? new CotizacionDto(enty.getIdCotizacion().getIdCotizacion()):null);
		dto.setIdColaboradorTemp(enty.getIdColaboradorTemp()!=null ? new ColaboradoresTempDto(enty.getIdColaboradorTemp().getIdColaboradorTemp()):null);
		dto.setFechaModificacion(enty.getFechaModificacion());
		return dto;
	}
}


