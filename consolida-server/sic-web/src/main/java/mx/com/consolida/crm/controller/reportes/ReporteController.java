package mx.com.consolida.crm.controller.reportes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.reporte.CotizadorReporteDTO;
import mx.com.consolida.reporte.DemoColaboradorReporteDTO;
import mx.com.consolida.reportes.ReporteCotizador;


@Controller
@RequestMapping(value="/reporte")
public class ReporteController {

	@Autowired
	private ReporteCotizador reporteMonitor;
	
	
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReporteController.class);
    
	
	@RequestMapping(value = "/cotizador", method = RequestMethod.GET)
    public void reporteMonitor(HttpServletResponse response) {
		
		
		CotizadorReporteDTO cotizacion = new CotizadorReporteDTO();
		cotizacion.setTotalColaboradores("10");
		cotizacion.setTotalNomina("$100,000.00");
		
		List<DemoColaboradorReporteDTO> colaboradores = new ArrayList<DemoColaboradorReporteDTO>();
		
		DemoColaboradorReporteDTO colaborador1 = new DemoColaboradorReporteDTO();
		colaborador1.setNombre("Miguel Lopez Chavez");
		colaborador1.setMontoNomina("$2,000.00");
		colaborador1.setMontoPPP("$8,000.00");
		colaboradores.add(colaborador1);
		
		
		DemoColaboradorReporteDTO colaborador2 = new DemoColaboradorReporteDTO();
		colaborador2.setNombre("Marco Romero Cardenas");
		colaborador2.setMontoNomina("$2,000.00");
		colaborador2.setMontoPPP("$8,000.00");
		colaboradores.add(colaborador2);
		
		
		cotizacion.setColaboradores(colaboradores);
		
		try {
	      // get your file as InputStream
	      reporteMonitor.setReporteMonitor();
	      byte[] datos =	reporteMonitor.generarReporteMonitor(cotizacion);
	      InputStream is = new ByteArrayInputStream(datos); 
	      
	      // copy it to response's OutputStream
	      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	      response.flushBuffer();
	      
	    } catch (IOException ex) {
	    	LOGGER.info("Error writing file to output stream. Filename was '{}'","Reporte Monitor", ex);
	      throw new RuntimeException("IOError writing file to output stream");
	    }
	    
    }

}
