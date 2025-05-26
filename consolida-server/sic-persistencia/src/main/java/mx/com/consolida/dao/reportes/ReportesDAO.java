package mx.com.consolida.dao.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.reporte.dto.ReporteDTO;

public interface ReportesDAO {
	
	public List<ReporteDTO> reporteEstimados(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro, String cveQuincena);
	
	public List<ReporteDTO> reporteOperaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro);
	
	public List<ReporteDTO> reporteVariaciones(Date fechaInicioPeriodo, Date fechaFinPeriodo, String  listaCentro, String cveQuincena);

	public List<ReporteDTO> reporteColabFaltCrm(String listaCentro);
	
	public List<ReporteDTO> reporteFacturacionMensual(String listaCentro, String mes);
}
