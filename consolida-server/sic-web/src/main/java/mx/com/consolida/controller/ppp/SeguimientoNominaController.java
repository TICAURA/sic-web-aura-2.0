package mx.com.consolida.controller.ppp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.TimbradoColaboradoresDto;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.service.impl.DocumentoServiceBO;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.FacturaMsDTO;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.documento.DocumentoMSDto;
import mx.com.email.dto.EmailDTO;
import mx.com.email.dto.EmailFileDTO;
import mx.com.email.dto.EmailResponseDTO;
import mx.com.facturacion.factura.SeguimientoNominaDto;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class SeguimientoNominaController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanzasController.class);
	
	@Autowired
	private NominaBO nominaBO;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;
	
	@Value("${urlZuul}")
	private String urlZuulServer;
	
	@RequestMapping(value = "/seguimientoNomina/cargaInicial")
	@ResponseBody
	public Map<String, Object> cargaInicialNomina(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasFinanzasByCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()), 7L));
				dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/seguimientoNomina/buscarNominas")
	@ResponseBody
	public Map<String, Object> buscarNominas(@RequestBody SeguimientoNominaDto seguimientoNomina) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
				seguimientoNomina.setIdCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasSeguimiento(seguimientoNomina));
				dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en buscarNominas ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
		
	@RequestMapping(value="/seguimientoNomina/enviarCorreoColaborador")
	@ResponseBody
	public FacturaMsDTO enviarCorreoColaborador(@RequestBody TimbradoColaboradoresDto nomina ) {
		FacturaMsDTO facturaMsDTO = new FacturaMsDTO();
		DocumentoMSDto pdf = new DocumentoMSDto();
		 DocumentoMSDto xml= new DocumentoMSDto();
		
		try {
			
			EmpleadoDTO colaborador = nomina.getColaboradores().get(0);
			RestTemplate restTemplate = new RestTemplate();
			String serverUrl = urlZuulServer + "/api/email/enviarCorreo";
			
			xml =documentoServiceBO.obtenerDocumentoMSDto(colaborador.getIdCmsExcel());
			pdf = documentoServiceBO.obtenerDocumentoMSDto(colaborador.getIdCmsPdf());

			// Cambio de url a la de desarrollo local
//			String serverUrl = "http://localhost:8069" + "/enviarCorreo";

			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setTituloCorreo("CFDI");
			emailDTO.setBody(cuerpoCorreo(nomina));
			emailDTO.setDestinatario(colaborador.getCorreoElectronico() );

			EmailFileDTO pdfEF = new EmailFileDTO();
			pdfEF.setDocumentoBase64(pdf.getDocumentoBase64());
			pdfEF.setNombreAdjunto(pdf.getArchivo());

			EmailFileDTO xmlEF = new EmailFileDTO();
		
			xmlEF.setDocumentoBase64(xml.getDocumentoBase64());
			xmlEF.setNombreAdjunto(xml.getArchivo());

			List<EmailFileDTO> files = new ArrayList<>();
			files.add(pdfEF);
			files.add(xmlEF);

			emailDTO.setAdjuntos(files);

			EmailResponseDTO response = restTemplate.postForObject(serverUrl, emailDTO, EmailResponseDTO.class);

			response.getCode();
			response.getDescripcion();
			}catch(Exception e) {
				
				LOGGER.error(" Método nviarNotificacion: " + e.getMessage() );
				
			}

		return facturaMsDTO;
	}
	
	String cuerpoCorreo(TimbradoColaboradoresDto nomina) {
		
		EmpleadoDTO colaborador = nomina.getColaboradores().get(0);
		// Se arma el cuerpo del correo que se quiere enviar en el correo
		StringBuilder sb = new StringBuilder();
		sb.append(" <html xmlns='http://www.w3.org/1999/xhtml'> ");
		sb.append("             <head> ");
		sb.append("                 <meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> ");
		sb.append("                 <title>SIC</title> ");
		sb.append("                 <style> ");
		sb.append("                     body{ ");
		sb.append("                         color: #333333 /*737373*/;  ");
		sb.append("                         font-family:    Georgia, Times New Roman, Times, serif; ");
		sb.append("                         font-size: 16px;  ");
		sb.append("                         text-align: justify; ");
		sb.append("                     } ");
		sb.append("                     .contenedor{ ");
		sb.append("                         padding-top: 20px; ");
		sb.append("                         width:50% !important; ");
		sb.append("                     } ");
		sb.append("                     hr { ");
		sb.append("                         background: black none repeat scroll 0 0; ");
		sb.append("                         height: 5px; ");
		sb.append("                     } ");
		sb.append("                 </style> ");
		sb.append("             </head> ");
		sb.append("             <body> ");
		sb.append("                 <div align='center'> ");
		sb.append("                     <div class='contenedor' > ");
		sb.append("                         <hr/> ");
		sb.append("                         <span style='font-weight: bold; font-size: 28px;'>Notificaci&oacute;n de CFDI </span> ");
		sb.append("                         <hr/> ");
		sb.append("                         <div align='left'> ");
		sb.append(" 						<br/><br/> ");
		sb.append("                             <div> ");
		sb.append("                                 <span style='font-weight: bold;'><USUARIO></span> ");
		sb.append("                                 <br/><br/> ");
		sb.append("                                 <span style='font-weight: bold;'><MENSAJE></span> ");
		sb.append("                             </div> ");
		sb.append("                         </div> ");
		sb.append("                     </div> ");
		sb.append("                 </div> ");
		sb.append("             </body> ");
		sb.append("         </html> ");

		String cuerpoCorreo = sb.toString();
		String nombreCompleto = colaborador.getNombre() + " " + colaborador.getApellidoPaterno() ;
		if (colaborador.getApellidoMaterno()!=null &&  colaborador.getApellidoMaterno().trim().length()>0 ) {
			nombreCompleto +=  " " + colaborador.getApellidoMaterno().trim();
		}
		cuerpoCorreo = cuerpoCorreo.replaceAll("<USUARIO>", nombreCompleto);

		cuerpoCorreo = cuerpoCorreo.replaceAll("<MENSAJE>",
				"Le adjuntamos el Comprobante Fiscal Digital a traves de internet (CFDI) con folio fiscal " 
		+ colaborador.getUuid()
		+ " correspondiente al periodo "
		+ nomina.getNominaPPP().getFechaInicioNominaFormato() + " al " +
		 nomina.getNominaPPP().getFechaInicioNominaFormato());
		 			

		return cuerpoCorreo;
	}

}
