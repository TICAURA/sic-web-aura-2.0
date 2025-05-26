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

import mx.com.consolida.converter.TechnicalException;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CotizacionTotalDao;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.CotizacionTotalesDto;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.entity.CotizacionTotales;

@Repository
public class CotizacionTotalDaoImpl extends GenericDAO<CotizacionTotales, Long> implements CotizacionTotalDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CotizacionTotalDaoImpl.class);
	
	public CotizacionTotalesDto obtenerDtoCotizacionTotales(CotizacionTotales entity) {
		CotizacionTotalesDto dto = new CotizacionTotalesDto();
		dto.setIdCotizacionTotales(entity.getIdCotizacionTotales());
		dto.setTotaldDiferenciaNeto(entity.getTotaldDiferenciaNeto());
		dto.setTotaldDiferenciaCostoMixto(entity.getTotaldDiferenciaCostoMixto());
		dto.setTotaldDiferenciaCostoSoloPPP(entity.getTotaldDiferenciaCostoSoloPPP());
		dto.setTotalassSuedo(entity.getTotalassSuedo());
		dto.setTotalassGravados(entity.getTotalassGravados());
		dto.setTotalassExentos(entity.getTotalassExentos());
		dto.setTotalassSubsidio(entity.getTotalassSubsidio());
		dto.setTotalassISR(entity.getTotalassISR());
		dto.setTotalassCoImss(entity.getTotalassCoImss());
		dto.setTotalassNetoNomina(entity.getTotalassNetoNomina());
		dto.setTotalaasAsimialdos(entity.getTotalaasAsimialdos());
		dto.setTotalaasISR(entity.getTotalaasISR());
		dto.setTotalaasNetoAsimilados(entity.getTotalaasNetoAsimilados());
		dto.setTotalaOtros(entity.getTotalaOtros());
		dto.setTotalaNetoTotal(entity.getTotalaNetoTotal());
		dto.setTotalacfaPercepcionTotal(entity.getTotalacfaPercepcionTotal());
		dto.setTotalacfaCargaSocial(entity.getTotalacfaCargaSocial());
		dto.setTotalacfaComision(entity.getTotalacfaComision());
		dto.setTotalacfaSubtotalFactura(entity.getTotalacfaSubtotalFactura());
		dto.setTotalacfaIVA(entity.getTotalacfaIVA());
		dto.setTotalacfaTotalFactura(entity.getTotalacfaTotalFactura());
		dto.setTotalgsssSuedo(entity.getTotalgsssSuedo());
		dto.setTotalgsssExentos(entity.getTotalgsssExentos());
		dto.setTotalgsssSubsidio(entity.getTotalgsssSubsidio());
		dto.setTotalgsssISR(entity.getTotalgsssISR());
		dto.setTotalgsssCoImss(entity.getTotalgsssCoImss());
		dto.setTotalgsssNetoNomina(entity.getTotalgsssNetoNomina());
		dto.setTotalgsePlanPensionesPrivada(entity.getTotalgsePlanPensionesPrivada());
		dto.setTotalgsNetoTotal(entity.getTotalgsNetoTotal());
		dto.setTotalgscfmPercepcionTotal(entity.getTotalgscfmPercepcionTotal());
		dto.setTotalgscfmCargaSocial(entity.getTotalgscfmCargaSocial());
		dto.setTotalgscfmComision(entity.getTotalgscfmComision());
		dto.setTotalgscfmSubtotalFactura(entity.getTotalgscfmSubtotalFactura());
		dto.setTotalgscfmIVA(entity.getTotalgscfmIVA());
		dto.setTotalgscfmTotalFactura(entity.getTotalgscfmTotalFactura());
		dto.setTotalgscfpppPercepcionNomina(entity.getTotalgscfpppPercepcionNomina());
		dto.setTotalgscfpppCargaSocial(entity.getTotalgscfpppCargaSocial());
		dto.setTotalgscfpppCostoNomina(entity.getTotalgscfpppCostoNomina());
		dto.setTotalgscfpppComision(entity.getTotalgscfpppComision());
		dto.setTotalgscfpppSubtotalFactura(entity.getTotalgscfpppSubtotalFactura());
		dto.setTotalgscfpppIVA(entity.getTotalgscfpppIVA());
		dto.setTotalgscfpppTotalFactura(entity.getTotalgscfpppTotalFactura());
		dto.setTotalgscfpppCostoTotal(entity.getTotalgscfpppCostoTotal());
		dto.setIdUsuarioAlta(entity.getIdUsuarioAlta());
		dto.setFechaAlta(entity.getFechaAlta());
		dto.setIndEstatus(entity.getIndEstatus());
		dto.setIdCotizacion(entity.getIdCotizacion()!=null? new CotizacionDto(entity.getIdCotizacion().getIdCotizacion()):null);
		dto.setFechaModificacion(entity.getFechaModificacion());
		return dto;
	}
	@Transactional
	public CotizacionTotalesDto guardarcotizacionTotal(CotizacionTotalesDto cotizacionColaborador) {

		CotizacionTotales entity = new CotizacionTotales();
		Long idCotizacion = cotizacionColaborador.getIdCotizacion().getIdCotizacion();
		cotizacionColaborador.setIdCotizacion(null);
//		entity = conv().map(cotizacionColaborador,CotizacionTotales.class);
		entity = new CotizacionTotales(cotizacionColaborador);
		entity.setIdCotizacion(new Cotizacion(idCotizacion));
		entity.setFechaAlta(new Date());
		entity.setIndEstatus(1L);
		entity.setIdCotizacionTotales(create(entity));
//		return conv().map(entity,CotizacionTotalesDto.class);
		return obtenerDtoCotizacionTotales(entity);
	}
	
	@Transactional
	public void eliminadoLogicoCotizacionTotal(Long idCotizacion) {
		StringBuilder sb = new StringBuilder();
		sb.append("update sin.cotizacion_totales set ind_estatus = 0, fecha_modificacion = current_date() where ind_estatus = 1 and id_cotizacion = "+idCotizacion);
		jdbcTemplate.execute(sb.toString());
	}
	
	@Transactional
	public CotizacionTotalesDto obtenerTotalByIdCotizacion(Long idCotizacion) {
		List<CotizacionTotales> entity = new ArrayList<>();
		CotizacionTotalesDto recupera = new CotizacionTotalesDto();
		Session session = sessionFactory.openSession();
		try {
			Query qry = session.createQuery("from CotizacionTotales where ind_estatus = 1 and id_cotizacion = "+idCotizacion);
			entity = qry.list();
			CotizacionTotales entidad = (entity!=null && !entity.isEmpty()) ? entity.get(0):new CotizacionTotales();
			recupera = obtenerDtoCotizacionTotales(entidad);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
		return recupera;
	}

	
}