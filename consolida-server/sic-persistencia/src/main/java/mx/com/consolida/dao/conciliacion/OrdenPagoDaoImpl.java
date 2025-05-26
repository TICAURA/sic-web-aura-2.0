package mx.com.consolida.dao.conciliacion;


import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import mx.com.consolida.conciliaciones.OrdenPagoDto;

import mx.com.consolida.dao.GenericDAO;

import mx.com.consolida.entity.conciliacion.OrdenPago;


@Repository
public class OrdenPagoDaoImpl extends  GenericDAO<OrdenPago, Long> implements OrdenPagoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(OrdenPagoDaoImpl.class);
	
	@Transactional
	public List<OrdenPagoDto> getOrdenesPago(String clv){
		try {
			String sql = "select + "
					+ " from orden_pago_recibido where ind_Estatus = 1 "
					+ " ";
			List<OrdenPagoDto> data = (List<OrdenPagoDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestro ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<OrdenPagoDto> getOrdenPagoByIdCarga(Long idCarga) {
		try {
			String sql = "select + "
					+ " from orden_pago_recibido where ind_Estatus = 1 "
					+ " ";
			List<OrdenPagoDto> data = (List<OrdenPagoDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestro ", e);
			return Collections.emptyList();
		}
	}
	

	
}
