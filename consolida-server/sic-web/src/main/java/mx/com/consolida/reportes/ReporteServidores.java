package mx.com.consolida.reportes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.com.consolida.reporte.CotizadorReporteDTO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@Component
public class ReporteServidores {

	@Value("${urlReportes}")
	private String urlReportes;
	
	private JasperReport reporte;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReporteServidores.class);

	public void setReporteServidores() {
		try {
			File file = new File(urlReportes + "cotizador.jrxml");
//			InputStream jasperStream = this.getClass().getResourceAsStream("/reportes/servidores.jrxml");
			
			reporte = JasperCompileManager.compileReport(new FileInputStream(file));
		} catch (Exception e) {
			System.out.println("Compile Faied");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public byte[] generarReporteServidores(final CotizadorReporteDTO cotizador) {

		 try {
	            final StringBuilder json = new StringBuilder(JSONParser.convertirObjetoAJSON(cotizador));
	            ByteArrayInputStream jsonInput;
	            jsonInput = new ByteArrayInputStream(json.toString().getBytes("UTF-8"));

	            final JsonDataSource dataSource = new JsonDataSource(jsonInput);
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("SUBREPORT_DIR",urlReportes);
	          
	            final JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, params, dataSource);
	            final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
	            return pdfBytes;
	            
	        } catch (Exception e) {

	            e.printStackTrace();
	            return null;
	        }
	}
}
