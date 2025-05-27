package mx.com.consolida.crm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.service.interfaz.ReportesBO;
import mx.com.consolida.dao.reportes.ReportesDAO;
import mx.com.reporte.dto.ReporteDTO;


@Service
public class ReportesBOImpl implements ReportesBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesBOImpl.class);

	
	@Autowired
	private ReportesDAO reporteDAO;

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteEstimados(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro, String cveQuincena) {
		
		try {
			
			return reporteDAO.reporteEstimados(fechaInicioPeriodo, fechaFinPeriodo, listaCentro, cveQuincena);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteEstimados ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteOperaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo,String  listaCentro) {
		
		try {
			
			
			return reporteDAO.reporteOperaciones(fechaInicioPeriodo, fechaFinPeriodo, listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteOperaciones ", e);
			return Collections.emptyList();
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteConsar(Date fechaInicioPeriodo, Date fechaFinPeriodo,String  listaCentro) {
		
		try {
			
			
			return reporteDAO.reporteConsar(fechaInicioPeriodo, fechaFinPeriodo, listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteConsar ", e);
			return Collections.emptyList();
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteTesoOpera(Date fechaInicioPeriodo, Date fechaFinPeriodo,String  listaCentro) {
		
		try {
			
			
			return reporteDAO.reporteTesoOpera(fechaInicioPeriodo, fechaFinPeriodo, listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteTesoOpera ", e);
			return Collections.emptyList();
		}
		
	}
	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteProductos(String idProducto, String  listaCentro) {
		
		try {
			
			
			return reporteDAO.reporteProductos(idProducto, listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteProductos ", e);
			return Collections.emptyList();
		}
		
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteVariaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro, String cveQuincena) {
		try {
			
			
			return reporteDAO.reporteVariaciones(fechaInicioPeriodo, fechaFinPeriodo, listaCentro, cveQuincena);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteVariaciones ", e);
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<ReporteDTO> reporteColabFaltCrm(String listaCentro) {
		try {

			return reporteDAO.reporteColabFaltCrm(listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ReporteDTO> reporteFacturacionMensual(String listaCentro, String mes) {
		try {

			return reporteDAO.reporteFacturacionMensual(listaCentro,mes);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ReporteDTO> reporteFacturacion(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro) {
		try {

			return reporteDAO.reporteFacturacion(fechaInicioPeriodo,fechaFinPeriodo,listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ReporteDTO> reporteDispersion(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro) {
		try {

			return reporteDAO.reporteDispersion(fechaInicioPeriodo,fechaFinPeriodo,listaCentro);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en reporteColabFaltCrm ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ReporteDTO> reporteColaboradores(String listaCentro) {
		List<ReporteDTO> reporte =  reporteDAO.reporteColaboradores(listaCentro);
		return reporte;
	}

	@Override
	public List<ReporteDTO> reporteClientes(String listaCentro) {
		
		List<ReporteDTO> reporte = reporteDAO.reporteClientes(listaCentro);
		return reporte;
		
	}


}
