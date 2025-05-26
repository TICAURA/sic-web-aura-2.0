package mx.com.consolida.controller.alquimia;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;


import com.google.gson.Gson;


import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.alquimia.AutorizacionTransaccionRequest;
import mx.com.consolida.alquimia.AutorizacionTransaccionResponse;
import mx.com.consolida.alquimia.RastreoTransferenciaRequest;
import mx.com.consolida.alquimia.RastreoTransferenciaResponse;
import mx.com.consolida.alquimia.TokenAlquimiaRequest;
import mx.com.consolida.alquimia.TokenAlquimiaResponse;
import mx.com.consolida.alquimia.TokenConsumoResponse;
import mx.com.consolida.alquimia.TransferenciaAlquimiaRequest;
import mx.com.consolida.alquimia.TransferenciaAlquimiaResponse;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.crm.service.impl.PrestadoraServicioStpBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.DatosDispersionAlquimia;
import mx.com.consolida.ppp.service.interfaz.NominaBO;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value = { ReferenciaSeguridad.USUARIO_APLICATIVO })
public class DispersorAlquimia extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DispersorAlquimia.class);

	@Value("${urlAlquimia}")
	private String urlAlquimia;

	@Autowired
	private PrestadoraServicioStpBO prestadoraStpBO;

	@Autowired
	private NominaBO nominaBO;

	private  String tokenAlquimia = "";
	private  String tokenConsumo = "";

	private  LocalDateTime tiempoTokenConsumo = LocalDateTime.now();
	private  LocalDateTime tiempoTokenAlquimia = LocalDateTime.now();
	static long diffConsumo = 0l;
	static long diffAlquimia = 0l;

	
	

	@RequestMapping(value = "/finanzas/dispersaAlquimia")
	@ResponseBody
	public void dispersorAlquimia(@RequestBody   DatosDispersionAlquimia datosAlquimia) {
		  PrestadoraServicioStpDto datosDisp= new PrestadoraServicioStpDto(); 
		  
		  	System.out.println("################### INICIO URL: " + urlAlquimia+" #################### ");
		  	LOGGER.info("####################INICIO URL :" + urlAlquimia+ "###########################");
			datosDisp = prestadoraStpBO.getPrestadoraServicioByIdStp(datosAlquimia.getIdPrestadoraServicioStp());
			
		//List<EmpleadoDTO> colaboradores = new ArrayList<>();
		String ref = "";
		Boolean errDisp = false;
		
		for (EmpleadoDTO co : datosAlquimia.getColaboradores()) {
			try {
			if (co.getIdEstatus() != 5 && co.getIdEstatus() != 8 && co.getIdEstatus() != 12
					&& co.getIdEstatus() != 13) {
			validarTokens( datosDisp);

			TransferenciaAlquimiaRequest transferencia = new TransferenciaAlquimiaRequest();

			transferencia.setApiKey(datosDisp.getApiKey());
			transferencia.setAuthorization(tokenConsumo);
			transferencia.setAuthorizationAlquimia(tokenAlquimia);
			transferencia.setCuentaOrigen(datosDisp.getIdCuentaAhorro());
			transferencia.setIdCliente(Integer.valueOf(datosDisp.getIdCliente()));
			transferencia.setCuentaDestino(co.getClabe()==null? co.getNumeroCuenta().toString():co.getClabe());
			transferencia.setCodigoBanco(co.getInstitucionContraparte());
			transferencia.setGuardaCuentaDestino("true");
			transferencia.setNombreBeneficiario(co.getApellidoMaterno() != null
					? co.getNombre() + " " + co.getApellidoPaterno() + " " + co.getApellidoMaterno()
					: co.getNombre() + " " + co.getApellidoPaterno());
			transferencia.setRfcBeneficiario("NA");
			transferencia.setEmailBeneficiario(co.getCorreoElectronico());
			transferencia.setConcepto("transferencia");
			ref=co.getCveOrdenPago().substring(co.getCveOrdenPago().length()-5	);
					
			transferencia.setNoReferencia(Long.valueOf(ref));
			transferencia.setImporte(Double.valueOf(co.getMontoPPP().toString()));
			transferencia.setMedioPago(4);

			TransferenciaAlquimiaResponse transResponse = transferencia(transferencia);
			if (transResponse.getError() == "false") {
				AutorizacionTransaccionRequest autorizar = new AutorizacionTransaccionRequest();

				autorizar.setApi_key(datosDisp.getApiKey());
				autorizar.setId_cuenta(transferencia.getCuentaOrigen());
				autorizar.setId_transaccion(transResponse.getId_transaccion());
				autorizar.setNo_referencia(transferencia.getNoReferencia().toString());
				autorizar.setToken_alquimia(tokenAlquimia);
				autorizar.setToken_consumo(tokenConsumo);
				AutorizacionTransaccionResponse autorizacion = autorizarTransferencia(autorizar);
				if (autorizacion.getError() == "false") {
					
					co.setIdStp(Integer.valueOf(autorizar.getId_transaccion()));
					nominaBO.crearEstatusColaboradorSTP(co, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA_STP.getId());
					System.out.println(
							"Transaccion:" + transferencia.getNoReferencia() + " " + transResponse.getId_transaccion());
					// cofirmamos transaccion
				}else {
					errDisp= true;
					co.setDescripcionErrorStp(autorizacion.getMessage());
					nominaBO.crearEstatusColaboradorSTP(co, getUser().getIdUsuario(),
					                            		CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
				}
					
				}else {
					errDisp= true;
					co.setDescripcionErrorStp(transResponse.getMessage());
					nominaBO.crearEstatusColaboradorSTP(co, getUser().getIdUsuario(),
					                            		CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
					
			}
			}

		}catch (Exception e ) {
			LOGGER.error(":::::::::::ERROR: " + e.getMessage());
			errDisp= true;
		
			
			
		}
		}
		if (errDisp) {
			nominaBO.cambiaEstatusNomina(datosAlquimia.getColaboradores().get(0).getIdNomina(), null,
                    NominaEstatusEnum.NOMINA_STP_GENERADA_ERRORES, getUser().getIdUsuario());
		}else {
			nominaBO.cambiaEstatusNomina(datosAlquimia.getColaboradores().get(0).getIdNomina(), null,
                    NominaEstatusEnum.NOMINA_STP_GENERADA_EXITO, getUser().getIdUsuario());
		}
		

	}

	private void validarTokens(PrestadoraServicioStpDto datosDisp) {
		LocalDateTime tiempoAhora = LocalDateTime.now();
		diffConsumo = ChronoUnit.MILLIS.between(tiempoTokenConsumo,tiempoAhora )/1000;
		diffAlquimia = ChronoUnit.MILLIS.between(tiempoTokenAlquimia,tiempoAhora )/1000;
		if (tokenConsumo == "" || diffConsumo > 550l) {
			tokenConsumo="";
			getTokenConsumo();
			diffConsumo=0;
			tiempoTokenConsumo= LocalDateTime.now();
		}

		if (tokenAlquimia == "" || diffAlquimia > 86300) {
			tokenAlquimia="";		
			getTokenAlquimia(datosDisp);
			tiempoTokenAlquimia=LocalDateTime.now();
		}
	}

	private  void getTokenConsumo() {

		String berer = "clv";
		String metodo = "http://localhost:8033/aura-alquimiapay/token-consumo";
		metodo = urlAlquimia+"token-consumo";
		
	  	System.out.println("################### INICIO URL: " + metodo+" #################### ");
	  	LOGGER.info("####################INICIO URL :" + metodo+ "###########################");
		RestTemplate restTemplate = new RestTemplate();

		TokenConsumoResponse response = restTemplate.postForObject(metodo, berer, TokenConsumoResponse.class);
		tokenConsumo = response.getAccess_token();

		System.out.println("token consumo: " + response.getAccess_token());

	}

	private  void getTokenAlquimia(PrestadoraServicioStpDto datosDisp) {

		String metodo = "http://localhost:8033/aura-alquimiapay/token-alquimia";
		metodo = urlAlquimia+"token-alquimia";
		System.out.println("################### INICIO URL: " + metodo+" #################### ");
	  	LOGGER.info("####################INICIO URL :" + metodo+ "###########################");
		RestTemplate restTemplate = new RestTemplate();
		tiempoTokenConsumo = LocalDateTime.now().plusSeconds(600);
		TokenAlquimiaRequest request = new TokenAlquimiaRequest();
		request.setUserName(datosDisp.getUserName());
		request.setPassword(datosDisp.getPassword());
		request.setClientId(datosDisp.getClientId());
		request.setClientSecret(datosDisp.getClientSecret());
		TokenAlquimiaResponse response = restTemplate.postForObject(metodo, request, TokenAlquimiaResponse.class);
		tokenAlquimia = response.getAccess_token();
		System.out.println("token Alquimia: " + response.getAccess_token());

	}

	private  TransferenciaAlquimiaResponse transferencia(TransferenciaAlquimiaRequest transferencia) {
		
		RestTemplate restTemplate = new RestTemplate();
		String metodo = urlAlquimia +"transferencia";
		System.out.println("################### INICIO URL: " + metodo+" #################### ");
	  	LOGGER.info("####################INICIO URL :" + metodo+ "###########################");
		tiempoTokenConsumo = LocalDateTime.now().plusSeconds(600);
		Gson gson = new Gson();
		String JSON = gson.toJson(transferencia);
		System.out.println(JSON);

		TransferenciaAlquimiaResponse response = restTemplate.postForObject( metodo, transferencia,
				TransferenciaAlquimiaResponse.class);
		JSON = gson.toJson(response);
		System.out.println(JSON);
		System.out.println("folio transferencia:" + response.getFolio_orden());

		return response;

	}

	private  AutorizacionTransaccionResponse autorizarTransferencia(AutorizacionTransaccionRequest autorizar) {

		RestTemplate restTemplate = new RestTemplate();
		String metodo = "http://localhost:8033/aura-alquimiapay/autorizacion";
		metodo =urlAlquimia+"autorizacion";
		System.out.println("################### INICIO URL: " + metodo+" #################### ");
	  	LOGGER.info("####################INICIO URL :" + metodo+ "###########################");

		Gson gson = new Gson();
		String JSON = gson.toJson(autorizar);
		System.out.println(JSON);
		AutorizacionTransaccionResponse response = restTemplate.postForObject(metodo, autorizar,
				AutorizacionTransaccionResponse.class);

		JSON = gson.toJson(response);
		System.out.println(JSON);
		System.out.println(response.getMessage());
		return response;

	}
	
	@RequestMapping(value = "/finanzas/rastreoAlquimia")
	@ResponseBody
	public void rastreoTransferencia(@RequestBody DatosDispersionAlquimia datosAlquimia) {
		PrestadoraServicioStpDto datosDisp = new PrestadoraServicioStpDto();
		
		tokenConsumo="";
		tokenAlquimia="";
		// EmpleadoDTO =datosAlquimia.getColaboradores().get(0);
		for (EmpleadoDTO co : datosAlquimia.getColaboradores()) {
			LocalDateTime tiempoAhora = LocalDateTime.now();
			LocalDateTime tiempoOperacion = LocalDateTime.of(co.getFechaOperacion().getYear(), co.getFechaOperacion().getMonth(), co.getFechaOperacion().getDay(), co.getFechaOperacion().getHours()
					, co.getFechaOperacion().getMinutes(), co.getFechaOperacion().getSeconds());
	
			long diffOperacion = 0l;
		
			diffOperacion = ChronoUnit.SECONDS.between( tiempoOperacion,tiempoAhora);
			
				
			
			if (co.getIdEstatus() == 7 || co.getIdEstatus() == 8) {
				if (diffOperacion>1800) {
				
				
				datosDisp = prestadoraStpBO.getPrestadoraServicioByIdStp(datosAlquimia.getIdPrestadoraServicioStp());
				validarTokens(datosDisp);
				RestTemplate restTemplate = new RestTemplate();
				String metodo = "http://localhost:8033/aura-alquimiapay/rastreo";
				metodo = urlAlquimia + "rastreo";
				System.out.println("################### INICIO URL: " + metodo + " #################### ");
				LOGGER.info("####################INICIO URL :" + metodo + "###########################");

				datosAlquimia.getColaboradores().get(0).getIdStp();

				RastreoTransferenciaRequest request = new RastreoTransferenciaRequest();
				request.setToken_consumo(tokenConsumo);
				request.setToken_alquimia(tokenAlquimia);
				request.setId_cuenta(datosDisp.getIdCuentaAhorro());
				request.setId_transaccion(datosAlquimia.getColaboradores().get(0).getIdStp().toString());

				Gson gson = new Gson();
				String JSON = gson.toJson(request);
				System.out.println(JSON);
				RastreoTransferenciaResponse response = restTemplate.postForObject(metodo, request,
						RastreoTransferenciaResponse.class);

				JSON = gson.toJson(response);
				System.out.println(JSON);
				System.out.println(response.getEstatus());
				String estoRespuesta= response.getEstatus().toString() ;
				if ("LIQUIDADA".equals(response.getEstatus()) && !co.getDescEstatus().toUpperCase()
						.equals(CatEstatusColaboradorEnum.LIQUIDADA_STP.getDesc())) {
					System.out.println("Estado orden: " + response.getEstatus());
				
					nominaBO.crearEstatusColaboradorSTP(co, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.LIQUIDADA_STP.getId());

				} else if( "ERROR DISPERSION".equals((estoRespuesta.trim()))){
					System.out.println("Estado orden: " + response.getEstatus());
					co.setDescError(response.getDetalle_proveedor().getMessage());
					co.setDescripcionErrorStp(response.getDetalle_proveedor().getMessage());
					
					nominaBO.crearEstatusColaboradorSTP(co, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());

				}
			}
			}
		}

	}

}
