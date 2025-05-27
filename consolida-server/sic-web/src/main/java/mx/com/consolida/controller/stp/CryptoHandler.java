package mx.com.consolida.controller.stp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceWrapperResponse;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.conciliaciones.ResumenSaldo;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.controller.tools.AESSymetricCrypto;
import mx.com.consolida.crm.dto.PrestadoraServicioSicofiDto;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.OrdenPago;
import mx.com.consolida.ppp.dto.RequestSaldo;
import mx.com.consolida.ppp.dto.ResponseRastreoDto;

import mx.com.consolida.ppp.dto.ResultadoRastreoDto;
import mx.com.consolida.ppp.service.interfaz.NominaBO;

/*
 * Clase para dispersines stp
 * 
 * 
 */
@Controller
@RequestMapping("ppp")
public class CryptoHandler extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoHandler.class);

	@Autowired
	private NominaBO nominaBO;

	@Value("${urlJks}")
	private String urlJks;

	@Value("${nombreJks}")
	private String nombreJKS;

	@Value("${passwordJKS}")
	private String password;

	@Value("${urlStp}")
	private String urlStp;

	@Value("${urlStpConsulta}")
	private String urlStpConsulta;

	@Value("${aliasKeyJks}")
	private String aliasKeyJks;

	@Value("${passwordKeyJks}")
	private String passwordKeyJks;

	@Value("${stpKey}")
	private String stpKey;

	private AESSymetricCrypto crypto;

	@Autowired
	private CelulaBO celulaBO;

	@RequestMapping(value = "/finanzas/generaPagosSTP")
	@ResponseBody
	private void enviaPago(@RequestBody List<EmpleadoDTO> empleados)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, Exception {

		Boolean isContieneErrorSTP = false;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		/*
		 * Incializar función de encriptación, para encriptar datos sensibles de los
		 * calboradores, para el seguimiento de las dispersiones los datos se imprimen
		 * al log "stp.log"
		 */

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		String fechaTexto = formatter.format(new Date());
		String JSON = "";
		String metodoPago = "registra";

		LOGGER.info(
				"========================================= Inicio Envio Pagos =============================================");

		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();
		if (empleados.size() > 0) {
			LOGGER.info("=== Id_nomina: " + empleados.get(0).getIdNomina().toString());
		} else {
			LOGGER.info("NO EXISTEN COLABORADORES");
		}
		for (EmpleadoDTO empleado : empleados) {

			if (empleado.getIdEstatus() != 5 && empleado.getIdEstatus() != 12 && empleado.getIdEstatus() != 13) {

				try {
					LOGGER.info(
							"========================================= Inicio orden Pago =============================================");
					/*
					 * El centro de costos solo viene el el primemer registro
					 */
					
					
					ordenPagoWS = new OrdenPagoWS();
					ordenPagoWS.setEmpresa(empleados.get(0).getNombreCentroCostos()); // (empleados.get(0).getNombreCentroCostos());
																						// NOMBRE CENTRO DE COSTOS
					// TODO cambiar el monto para produccion que sean diferente de un centavo
					ordenPagoWS.setMonto(empleado.getMontoPPP().setScale(2, BigDecimal.ROUND_FLOOR)); // new
																										// BigDecimal("0.01"));

					// (Integer.parseInt(empleado.getClabe().substring(0,3)));//
					// PRIMEROS 3 DIGITOS DE LA
					ordenPagoWS
							.setInstitucionContraparte(Integer.parseInt(empleado.getInstitucionContraparte().trim()));// CLABE
					ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());// empleado.getCveOrdenPago()
					fechaTexto = empleado.getFechaOperacion() != null ? formatter.format(empleado.getFechaOperacion())
							: fechaTexto;
					ordenPagoWS.setReferenciaNumerica(Integer.parseInt(fechaTexto)); // ddmmyy
					String nombreBeneficiario = empleado.getNombre() + " " + empleado.getApellidoPaterno();
					if (empleado.getApellidoMaterno() != null && empleado.getApellidoMaterno().length() > 0) {
						nombreBeneficiario = nombreBeneficiario + " " + empleado.getApellidoMaterno();
					}
					//nombreBeneficiario = nombreBeneficiario.trim();
					else {
						nombreBeneficiario = nombreBeneficiario + " .";
					}

					if (nombreBeneficiario.length() > 40) {
						nombreBeneficiario = nombreBeneficiario.toUpperCase().replace("Ñ", "N").substring(0, 40);

					} else {
						nombreBeneficiario = nombreBeneficiario.toUpperCase().replace("Ñ", "N");
					}

					ordenPagoWS.setNombreBeneficiario(nombreBeneficiario);
					if (empleados.get(0).getNombreCentroCostos().contains("_COMI")) {
						ordenPagoWS.setConceptoPago("PAGO FACTURA");
					}else {
						ordenPagoWS.setConceptoPago("PENSION"); // SIEMPRE VA PENSION para NOMINA
					}
					
					ordenPagoWS.setCuentaBeneficiario(empleado.getClabe());// empleado.getClabe()
																			// 846180123400000001

					if (empleado.getClabe() != null && empleado.getClabe().length() == 18) {
						ordenPagoWS.setCuentaBeneficiario(empleado.getClabe());// empleado.getClabe()
																				// 846180123400000001
						ordenPagoWS.setTipoCuentaBeneficiario(40);
					} else {
						ordenPagoWS.setCuentaBeneficiario(empleado.getNumeroCuenta().toString());// empleado.getClabe()
																									// 846180123400000001
						ordenPagoWS.setTipoCuentaBeneficiario(3);
					}
					// ordenPagoWS.setTipoCuentaBeneficiario(40); //40 = TRANSFERENCIA ELECTRONICA,
					// 3 = TARJETA DE DEBITO, 10 = TELEFONO CELULAR
					ordenPagoWS.setTipoPago(1); // TERCERO - TERCERO
					ordenPagoWS.setCuentaOrdenante(empleados.get(0).getClabeCuentaOrdenante()); /// VALIDACION
																								/// DE ESQUEMA
					ordenPagoWS.setTipoCuentaOrdenante(40); //// 40 = TRANSFERENCIA ELECTRONICA
					ordenPagoWS.setTopologia("V");
					ordenPagoWS.setRfcCurpBeneficiario(empleado.getRfc());
					String institucionOperante = "90" + ordenPagoWS.getCuentaOrdenante().substring(0, 3);
					ordenPagoWS.setInstitucionOperante(Integer.parseInt(institucionOperante));// 90 + TRES
																								// PRIMEROS DE
																								// LA CUENTA
																								// ORDENANTE

					ordenPagoWS.setFirma(firmar(ordenPagoWS));

					Gson gson = new Gson();
					JSON = gson.toJson(ordenPagoWS);
					System.out.println(JSON);

					LOGGER.info("JSON: " + crypto.encryp(JSON));
					LOGGER.info("Clave rastreo: " + ordenPagoWS.getClaveRastreo() + " id_Colaborador: "
							+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

					RestTemplate rs = new RestTemplateConfig().restTemplate();

					HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);
					ResponseEntity<SpeiServiceWrapperResponse> response = rs.exchange(urlStp + metodoPago,
							HttpMethod.PUT, request, SpeiServiceWrapperResponse.class);

					JSON = gson.toJson(response);
					System.out.println(JSON);
					LOGGER.info("respuesta STP: " + crypto.encryp(JSON));
					SpeiServiceWrapperResponse speiServiceResponse = response.getBody();

					LOGGER.info("id_stp: " + speiServiceResponse.getResultado().getId());
					if (speiServiceResponse.getResultado().getDescripcionError() != null
							&& speiServiceResponse.getResultado().getDescripcionError() != "0") {

						LOGGER.info("Descripcion: " + speiServiceResponse.getResultado().getId());
					}

					if (speiServiceResponse.getResultado() != null
							&& speiServiceResponse.getResultado().getDescripcionError() == null) {
						empleado.setIdStp(speiServiceResponse.getResultado().getId());
						// empleado.setDescripcionErrorStp( + " Firma: " + ordenPagoWS.getFirma() + "
						// CO: " + original);

						nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
								CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA_STP.getId());
					} else {
						isContieneErrorSTP = true;
						// empleado.setDescError( "json: " + JSON + " Firma: " + ordenPagoWS.getFirma()
						// + "CO: " + original);
						empleado.setDescripcionErrorStp(speiServiceResponse.getResultado().getDescripcionError());
						nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
								CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
					}
					LOGGER.info(
							"========================================= Termina orden Pago =============================================");

				} catch (Exception e) {
					LOGGER.info("================================ ERROR Orden de Pago ===============================");
					LOGGER.info("Error al generar idStp, idColaborador: " + empleado.getIdPppColaborador() + " " + e);
					/*
					 * empleado.setDescripcionErrorStp( "json: " + JSON + " Firma: " +
					 * ordenPagoWS.getFirma() + " CO: " + original);
					 */
					empleado.setDescError("Error stp no contesta");
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_ERRO_STP.getId());
					isContieneErrorSTP = true;
					LOGGER.info("================================ FIN ERROR Orden de Pago ===========================");
					LOGGER.info(
							"========================================= Termina orden Pago =============================================");

				}
			}

			if (isContieneErrorSTP) {
				nominaBO.cambiaEstatusNomina(empleados.get(0).getIdNomina(), null,
						NominaEstatusEnum.NOMINA_STP_GENERADA_ERRORES, getUser().getIdUsuario());
			} else {
				nominaBO.cambiaEstatusNomina(empleados.get(0).getIdNomina(), null,
						NominaEstatusEnum.NOMINA_STP_GENERADA_EXITO, getUser().getIdUsuario());
			}

		}

		LOGGER.info(
				"========================================= Termina Envio Pagos =============================================");
	}

	/**
	 * Consulta ordenes de dispersión en stp
	 * 
	 * @param empleado
	 * @throws Exception
	 */

	@RequestMapping(value = "/finanzas/consualtaOrdenes")
	@ResponseBody
	public void consultaOrdenes(@RequestBody EmpleadoDTO empleado) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String JSON = "";
		String metodoRastreo = "consOrdenesFech";
		String firma = "";
		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		try {

			LOGGER.info(
					"=========================================Inicio rastreo movimiento =============================================");
			ordenPagoWS.setEmpresa(empleado.getNombreCentroCostos()); // clave de rastreo del movimiento

			String fechaOperacion = formatter.format(empleado.getFechaDispersion()); // "20210226";
			ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion)); // 90 +
																			// TRES
																			// PRIMEROS DE
																			// LA CUENTA
																			// ORDENANTE
			firma = firmar(ordenPagoWS);
			ordenPagoWS.setFirma(firma);
			ordenPagoWS.setEstado("E");// enviados

			Gson gson = new Gson();
			JSON = gson.toJson(ordenPagoWS);

			LOGGER.info("Clave rastreo: " + empleado.getCveOrdenPago() + " id_Colaborador: "
					+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

			LOGGER.info("JSON: " + crypto.encryp(JSON));

			RestTemplate rs = new RestTemplateConfig().restTemplate();

			LOGGER.info("url: " + urlStp + metodoRastreo);

			HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);

			ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo, HttpMethod.POST, request,
					Object.class);
			Object obj = response.getBody();

			LOGGER.info("Response: " + response.toString());
			LOGGER.info("StatusCode: " + response.getStatusCode().toString());
			LOGGER.info("id_stp: " + obj.toString());

			String jsonString = gson.toJson(obj);
			JsonParser parser = new JsonParser();
			JsonObject respuesta = (JsonObject) parser.parse(jsonString);
			LOGGER.info(respuesta.get("resultado").toString());
			JsonObject res = respuesta.get("resultado").getAsJsonObject();
			int idRespuesta = res.get("id").getAsInt();
			LOGGER.info("id Respuesta: " + idRespuesta);

			if (idRespuesta == 1) {
				JsonObject ordPag = res.get("ordenPago").getAsJsonObject();
				LOGGER.info(res.get("ordenPago").toString());
				String estOrd = ordPag.get("estado").getAsString();
				LOGGER.info("Estado orden: " + estOrd);

				if (("LQ".equals(estOrd))
						&& empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.LIQUIDADA_STP.getCve_stp()) {
					LOGGER.info("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.LIQUIDADA_STP.getId());

				} else if (estOrd == "CD"
						&& empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.CANCELACION.getCve_stp()) {
					LOGGER.info("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.CANCELACION.getId());
				} else if (estOrd == "D"
						&& empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.DEVOLUCION.getCve_stp()) {
					LOGGER.info("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.DEVOLUCION.getId());
				} else {
					LOGGER.info("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
				}

			} else {

				LOGGER.info(
						"================================ ERROR  rastreo orden de Pago STP ==========================");
				String descripcionError = res.get("descripcionError").toString();
				LOGGER.info("Err: " + descripcionError);
				LOGGER.info(
						"================================ FIN ERROR rastreo orden de Pago ===========================");
			}

			LOGGER.info(
					"========================================== Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			LOGGER.info("================================ ERROR rastreo orden de Pago ===============================");
			LOGGER.info("Error al realizar rastreo de movimiento, idColaborador: " + empleado.getIdPppColaborador()
					+ " " + e);
			LOGGER.info("================================ FIN ERROR rastreo orden de Pago ===========================");
			LOGGER.info(
					"========================================= Termina rastreo orden Pago =============================================");

		}

	}

	/**
	 * Rastrea ordenes de pago con un id stp.
	 * 
	 * @param empleado
	 * @throws Exception
	 **/
	@RequestMapping(value = "/finanzas/restreoMovimieno")
	@ResponseBody
	public void rastreo(@RequestBody EmpleadoDTO empleado) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoRastreo = "consOrdEnvRastreo";
		String firma = "";
		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		try {

			LOGGER.info(
					"========================================= Inicio rastreo movimiento =============================================");
			ordenPagoWS.setEmpresa(empleado.getNombreCentroCostos()); // clave de rastreo del movimiento
			ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());// empleado.getCveOrdenPago()
			String fechaOperacion = formatter.format(empleado.getFechaOperacion()); // "20210226";
			ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion));
			String institucionOperante = "90" + empleado.getClabeCuentaOrdenante().substring(0, 3);
			ordenPagoWS.setInstitucionOperante(Integer.parseInt(institucionOperante));// 90 +
																						// TRES
																						// PRIMEROS DE
																						// LA CUENTA
																						// ORDENANTE

			firma = firmar(ordenPagoWS);
			ordenPagoWS.setFirma(firma);

			Gson gson = new Gson();
			JSON = gson.toJson(ordenPagoWS);

			LOGGER.info("Clave rastreo: " + empleado.getCveOrdenPago() + " id_Colaborador: "
					+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

			LOGGER.info("JSON: " + crypto.encryp(JSON));

			RestTemplate rs = new RestTemplateConfig().restTemplate();

			// LOGGER.info("url: " + urlStp + metodoRastreo);

			HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);

			ResponseEntity<ResponseRastreoDto> response = rs.exchange(urlStpConsulta + metodoRastreo, HttpMethod.POST,
					request, ResponseRastreoDto.class);
			// ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo,
			// HttpMethod.POST, request, Object.class);

			JSON = gson.toJson(response);
			LOGGER.info("Response: " + crypto.encryp(JSON));

			ResultadoRastreoDto rastreo = response.getBody().getResultado();

			// JSON = gson.toJson(rastreo); // LOGGER.info("Resultado: " + JSON);
			LOGGER.info("StatusCode: " + response.getStatusCode().toString());
			if (rastreo.getId() == 1) {
				OrdenPago ordenPago = rastreo.getOrdenPago(); //
				JSON = gson.toJson(ordenPago); // LOGGER.info("Rastreo: " + JSON);
				LOGGER.info("Estado orden: " + ordenPago.getEstado());

				LOGGER.info("Estado Actual: " + empleado.getDescEstatus().toUpperCase() + " Estado Rastreo: "
						+ response.getBody().getResultado().getOrdenPago().getEstado());
				if (("LQ".equals(ordenPago.getEstado()) || "TLQ".equals(ordenPago.getEstado())) && !empleado
						.getDescEstatus().toUpperCase().equals(CatEstatusColaboradorEnum.LIQUIDADA_STP.getDesc())) {
					LOGGER.info("Estado orden: " + ordenPago.getEstado());
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.LIQUIDADA_STP.getId());

				} else if (("CD".equals(ordenPago.getEstado()) || "D".equals(ordenPago.getEstado())
						|| "TCL".equals(ordenPago.getEstado()))
						&& !empleado.getDescEstatus()
								.equals(CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getDesc())) {
					LOGGER.info("Estado orden: " + ordenPago.getEstado());
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());

				}

			} else {
				LOGGER.info(
						"================================ ERROR  rastreo orden de Pago STP ==========================");
				LOGGER.info("Err: " + rastreo.getDescripcionError());
				LOGGER.info(
						"================================ FIN ERROR rastreo orden de Pago ===========================");
			}
			LOGGER.info(
					"========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			LOGGER.info("================================ ERROR rastreo orden de Pago ===============================");
			LOGGER.info("Error al realizar rastreo de movimiento, idColaborador: " + empleado.getIdPppColaborador()
					+ " Descripción error: " + e);
			LOGGER.info("================================ FIN ERROR rastreo orden de Pago ===========================");
			LOGGER.info(
					"========================================= Termina rastreo orden Pago ================================================");

		}

	}

	/**
	 * Obtencion de saldos Provision
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/finanzas/obtenerSaldosProvision")
	@ResponseBody
	public Map<String, Object> obtenerSaldosProvision(@RequestBody String claveProvision,String fondo, Model model)
			throws Exception {

		Map<String, Object> dataReturn = new HashMap<>();
		Double saldoTotal = 0.00;

		List<PrestadoraServicioSicofiDto> prestadoras = new ArrayList<>();
		
		dataReturn.put("prestadoras", prestadoras);
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldoTotal", saldoTotal);
		dataReturn.put("error", Boolean.FALSE);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoSaldo = "consultaSaldoCuenta";
		String firma = "";
		RequestSaldo reqSaldo = new RequestSaldo();

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		try {
			prestadoras = celulaBO.listaPrestadorasSicofiProvision(claveProvision,fondo);

			for (PrestadoraServicioSicofiDto p : prestadoras) {

				LOGGER.info(
						"========================================= Inicio rastreo movimiento =============================================");
				reqSaldo.setEmpresa(p.getNombreCentroCostos()); // clave de rastreo del movimiento
				// ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());//
				// empleado.getCveOrdenPago()
				// String fechaOperacion = formatter.format(empleado.getFechaOperacion()); //
				// "20210226";
				// ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion));
				// String institucionOperante = "90" + p.getClabeOrdenate().substring(0, 3);
				reqSaldo.setCuentaOrdenante(p.getClabeOrdenate());// 90 +
																	// TRES
																	// PRIMEROS DE
																	// LA CUENTA
																	// ORDENANTE

				firma = firmarSaldo(reqSaldo);
				reqSaldo.setFirma(firma);

				Gson gson = new Gson();
				JSON = gson.toJson(reqSaldo);

				// LOGGER.info("Clave rastreo: " + p.getCveOrdenPago() + " id_Colaborador: "
				// + p.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

				LOGGER.info("JSON: " + crypto.encryp(JSON));
				System.out.println("JSON: " + JSON);

				RestTemplate rs = new RestTemplateConfig().restTemplate();

				// LOGGER.info("url: " + urlStp + metodoRastreo);

				HttpEntity<RequestSaldo> request = new HttpEntity<>(reqSaldo);
				ResponseEntity<Object> response = null;
				try {
					response = rs.exchange(urlStpConsulta + metodoSaldo, HttpMethod.POST, request, Object.class);
					// ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo,
					// HttpMethod.POST, request, Object.class);

					JSON = gson.toJson(response);

					System.out.println(JSON);
					LOGGER.info("Response: " + crypto.encryp(JSON));
					JSON = gson.toJson(response.getBody());
					JsonParser parser = new JsonParser();
					JsonObject respuesta = (JsonObject) parser.parse(JSON);
					LOGGER.info(respuesta.get("respuesta").toString());
					JsonObject res = respuesta.get("respuesta").getAsJsonObject();
					int idRespuesta = respuesta.get("estado").getAsInt();

					if (idRespuesta == 0) {
						p.setSaldo(res.get("saldo").getAsDouble());
					}

					saldoTotal += p.getSaldo();
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
					System.out.println(e.getMessage());
					dataReturn.put("error", Boolean.TRUE);
					return dataReturn;
				}

				// SpeiServiceWrapperResponse reesponseSaldo = response.getBody();

				// ResultadoRastreoDto rastreo = response.getBody();

			}

			LOGGER.info(
					"========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			LOGGER.info("================================ ERROR rastreo orden de Pago ===============================");
			LOGGER.info("Error al realizar rastreo de movimiento, idColaborador: " + " Descripción error: " + e);
			LOGGER.info("================================ FIN ERROR rastreo orden de Pago ===========================");
			LOGGER.info(
					"========================================= Termina rastreo orden Pago ================================================");
			dataReturn.put("error", Boolean.TRUE);
			return dataReturn;
		}

		dataReturn.put("prestadoras", prestadoras);
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldoTotal", saldoTotal);
		return dataReturn;

	}

	/**
	 * Obtener saldos.
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/finanzas/resumenSaldos")
	@ResponseBody
	public Map<String, Object> resumenSaldos(Model model) throws Exception {

		Map<String, Object> dataReturn = new HashMap<>();

		Map<String, Object> saldosF = new HashMap<>();
		Map<String, Object> saldosIBF = new HashMap<>();
		Map<String, Object> saldosSCF = new HashMap<>();
		Map<String, Object> saldosCMF = new HashMap<>();
		
		Map<String, Object> saldosOI = new HashMap<>();
		Map<String, Object> saldosIBOI = new HashMap<>();
		Map<String, Object> saldosIFOI = new HashMap<>();
		Map<String, Object> saldosCSOI = new HashMap<>();
		Map<String, Object> saldosSCOI = new HashMap<>();
		
		
		List<ResumenSaldo> resumenSaldos = new ArrayList<>();
	
		
		ResumenSaldo  totalSaldo = new ResumenSaldo();
		ResumenSaldo  totalSaldoIB = new ResumenSaldo();
		ResumenSaldo  totalSaldoIF = new ResumenSaldo();
		ResumenSaldo  totalSaldoCS = new ResumenSaldo();
		ResumenSaldo  totalSaldoSC = new ResumenSaldo();
		ResumenSaldo  totalSaldoCM = new ResumenSaldo();
		ResumenSaldo  totalSaldoRe = new ResumenSaldo();
		
		
		totalSaldo.setId(1l);
		totalSaldo.setConcepto("Saldo");
		totalSaldo.setMontoFondo(0.00);
		totalSaldo.setMontoOtroIngreso(0.00);
		totalSaldoIB.setId(2l);
		totalSaldoIB.setConcepto("Ingreso Bruto");
		totalSaldoIB.setMontoFondo(0.00);
		totalSaldoIB.setMontoOtroIngreso(0.00);
		totalSaldoCS.setId(3l);
		totalSaldoCS.setConcepto("Carga Social");
		totalSaldoCS.setMontoFondo(0.00);
		totalSaldoCS.setMontoOtroIngreso(0.00);
		totalSaldoIF.setId(4l);
		totalSaldoIF.setConcepto("Impuesto Federal");
		totalSaldoIF.setMontoFondo(0.00);
		totalSaldoIF.setMontoOtroIngreso(0.00);
		totalSaldoSC.setId(5l);
		totalSaldoSC.setConcepto("Socios");
		totalSaldoSC.setMontoFondo(0.00);
		totalSaldoSC.setMontoOtroIngreso(0.00);
		totalSaldoCM.setId(6l);
		totalSaldoCM.setConcepto("Comisiones");
		totalSaldoCM.setMontoFondo(0.00);
		totalSaldoCM.setMontoOtroIngreso(0.00);
		totalSaldoRe.setId(7l);
		totalSaldoRe.setConcepto("Total");
		totalSaldoRe.setMontoFondo(0.00);
		totalSaldoRe.setMontoOtroIngreso(0.00);
		
		
		Double saldoTotalF =  0.00;
		Double saldoTotalOI =  0.00;
		Double saldoTotal =  0.00;
		
		dataReturn.put("fechaSaldo", new Date());
		saldosF = obtenerSaldosProvision("1", "1", model);
		if (saldosF.get("error") == Boolean.FALSE) {
			totalSaldo.setMontoFondo((Double) saldosF.get("saldoTotal"));
			saldosIBF = obtenerSaldosProvision("2", "1", model);
			if (saldosIBF.get("error") == Boolean.FALSE) {
				totalSaldoIB.setMontoFondo((Double) saldosIBF.get("saldoTotal"));
				saldosCMF = obtenerSaldosProvision("5", "1", model);
				if (saldosCMF.get("error") == Boolean.FALSE) {
					totalSaldoCM.setMontoFondo((Double) saldosCMF.get("saldoTotal"));
					saldosSCF = obtenerSaldosProvision("6", "1", model);
					if (saldosSCF.get("error") == Boolean.FALSE) {
						totalSaldoSC.setMontoFondo((Double) saldosSCF.get("saldoTotal"));
					} else {
						dataReturn.put("error", Boolean.TRUE);
						return dataReturn;
					}
				}
			} else {
				dataReturn.put("error", Boolean.TRUE);
				return dataReturn;
			}
		} else {
			dataReturn.put("error", Boolean.TRUE);
			return dataReturn;
		}
					
		saldosOI = obtenerSaldosProvision("1", "0", model);
		if (saldosOI.get("error") == Boolean.FALSE) {
			totalSaldo.setMontoOtroIngreso((Double) saldosOI.get("saldoTotal"));
			
			saldosIBOI = obtenerSaldosProvision("2","0", model);
			if (saldosIBOI.get("error") == Boolean.FALSE) {
				totalSaldoIB.setMontoOtroIngreso((Double)  saldosIBOI.get("saldoTotal"));
				saldosCSOI = obtenerSaldosProvision("3","0", model);
				if (saldosCSOI.get("error") == Boolean.FALSE) {
					totalSaldoCS.setMontoOtroIngreso((Double) saldosCSOI.get("saldoTotal"));
					saldosIFOI = obtenerSaldosProvision("4","0", model);
					if (saldosIFOI.get("error") == Boolean.FALSE) {
						totalSaldoIF.setMontoOtroIngreso((Double)  saldosIFOI.get("saldoTotal"));
						
							saldosSCOI = obtenerSaldosProvision("6","0", model);
							if (saldosSCOI.get("error") == Boolean.FALSE) {
								totalSaldoSC.setMontoOtroIngreso((Double) saldosSCOI.get("saldoTotal"));
							} else {
								dataReturn.put("error", Boolean.TRUE);
								return dataReturn;
							}
						}
					} else {
						dataReturn.put("error", Boolean.TRUE);
						return dataReturn;
					}
				} else {
					dataReturn.put("error", Boolean.TRUE);
					return dataReturn;
				}
			} else {
				dataReturn.put("error", Boolean.TRUE);
				return dataReturn;

			}

	
		
		
		saldoTotalF += totalSaldo.getMontoFondo();
		saldoTotalF += totalSaldoIB.getMontoFondo();
		saldoTotalF += totalSaldoCM.getMontoFondo();
		saldoTotalF += totalSaldoSC.getMontoFondo();
		
		saldoTotalOI += totalSaldo.getMontoOtroIngreso();
		saldoTotalOI += totalSaldoIB.getMontoOtroIngreso();
		saldoTotalOI += totalSaldoCS.getMontoOtroIngreso();
		saldoTotalOI += totalSaldoIF.getMontoOtroIngreso();

		saldoTotalOI += totalSaldoSC.getMontoOtroIngreso();
		
		saldoTotal = saldoTotalF + saldoTotalOI;
	
		totalSaldoRe.setMontoFondo(saldoTotalF);
		totalSaldoRe.setMontoOtroIngreso(saldoTotalOI);
		
		resumenSaldos.add(totalSaldo);
		resumenSaldos.add(totalSaldoIB);
		resumenSaldos.add(totalSaldoCS);
		resumenSaldos.add(totalSaldoIF);
		resumenSaldos.add(totalSaldoSC);
		resumenSaldos.add(totalSaldoCM);
		resumenSaldos.add(totalSaldoRe);
		
			
		dataReturn.put("saldos", resumenSaldos);
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldoTotal",saldoTotal);
		
		

		return dataReturn;

	}

	/**
	 * Obtener saldos.
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/finanzas/obtenerSaldos")
	@ResponseBody
	public Map<String, Object> obtenerSaldos(Model model) throws Exception {

		Map<String, Object> dataReturn = new HashMap<>();
		Double saldoTotal = 0.00;
		
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldoTotal", saldoTotal);

		List<PrestadoraServicioSicofiDto> prestadoras = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoSaldo = "consultaSaldoCuenta";
		String firma = "";
		RequestSaldo reqSaldo = new RequestSaldo();

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		try {
			prestadoras = celulaBO.listaPrestadorasSicofi();
			dataReturn.put("prestadoras", prestadoras);

			for (PrestadoraServicioSicofiDto p : prestadoras) {

				LOGGER.info(
						"========================================= Inicio rastreo movimiento =============================================");
				reqSaldo.setEmpresa(p.getNombreCentroCostos()); // clave de rastreo del movimiento
				// ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());//
				// empleado.getCveOrdenPago()
				// String fechaOperacion = formatter.format(empleado.getFechaOperacion()); //
				// "20210226";
				// ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion));
				// String institucionOperante = "90" + p.getClabeOrdenate().substring(0, 3);
				reqSaldo.setCuentaOrdenante(p.getClabeOrdenate());// 90 +
																	// TRES
																	// PRIMEROS DE
																	// LA CUENTA
																	// ORDENANTE

				firma = firmarSaldo(reqSaldo);
				reqSaldo.setFirma(firma);

				Gson gson = new Gson();
				JSON = gson.toJson(reqSaldo);

				// LOGGER.info("Clave rastreo: " + p.getCveOrdenPago() + " id_Colaborador: "
				// + p.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

				LOGGER.info("JSON: " + crypto.encryp(JSON));
				System.out.println("JSON: " + JSON);

				RestTemplate rs = new RestTemplateConfig().restTemplate();

				// LOGGER.info("url: " + urlStp + metodoRastreo);

				HttpEntity<RequestSaldo> request = new HttpEntity<>(reqSaldo);
				ResponseEntity<Object> response = null;
				try {
					response = rs.exchange(urlStpConsulta + metodoSaldo, HttpMethod.POST, request, Object.class);
					// ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo,
					// HttpMethod.POST, request, Object.class);

					JSON = gson.toJson(response);

					System.out.println(JSON);
					LOGGER.info("Response: " + crypto.encryp(JSON));
					JSON = gson.toJson(response.getBody());
					JsonParser parser = new JsonParser();
					JsonObject respuesta = (JsonObject) parser.parse(JSON);
					LOGGER.info(respuesta.get("respuesta").toString());
					JsonObject res = respuesta.get("respuesta").getAsJsonObject();
					int idRespuesta = respuesta.get("estado").getAsInt();

					if (idRespuesta == 0) {
						p.setSaldo(res.get("saldo").getAsDouble());
					}

					saldoTotal += p.getSaldo();
				} catch (Exception e) {
					dataReturn.put("error",  Boolean.TRUE);
					LOGGER.error(e.getMessage());
					System.out.println(e.getMessage());
					return dataReturn;
				}

				// SpeiServiceWrapperResponse reesponseSaldo = response.getBody();

				// ResultadoRastreoDto rastreo = response.getBody();

			}

			LOGGER.info(
					"========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			LOGGER.info("================================ ERROR rastreo orden de Pago ===============================");
			LOGGER.info("Error al realizar rastreo de movimiento, idColaborador: " + " Descripción error: " + e);
			LOGGER.info("================================ FIN ERROR rastreo orden de Pago ===========================");
			LOGGER.info(
					"========================================= Termina rastreo orden Pago ================================================");
			
			dataReturn.put("error", Boolean.TRUE);
			return dataReturn;	
		}

		dataReturn.put("prestadoras", prestadoras);
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldoTotal", saldoTotal);
		dataReturn.put("error", Boolean.FALSE);
		return dataReturn;	

	}
	
	
	/**
	 * Obtener saldos.
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/finanzas/obtenerSaldo")
	@ResponseBody
	public Map<String, Object> obtenerSaldo(@RequestBody PrestadoraServicioStpDto prestadora, Model model) throws Exception {

		Map<String, Object> dataReturn = new HashMap<>();
		Double saldo= 0.00;
		
		dataReturn.put("fechaSaldo", new Date());
		dataReturn.put("saldo", saldo);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoSaldo = "consultaSaldoCuenta";
		String firma = "";
		RequestSaldo reqSaldo = new RequestSaldo();

		crypto = new AESSymetricCrypto();
		crypto.cryptInit(stpKey);

		try {
		
				LOGGER.info(
						"========================================= Inicio Obtener Saldo =============================================");
				reqSaldo.setEmpresa(prestadora.getNombreCentroCostos()); // clave de rastreo del movimiento
				// ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());//
				// empleado.getCveOrdenPago()
				// String fechaOperacion = formatter.format(empleado.getFechaOperacion()); //
				// "20210226";
				// ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion));
				// String institucionOperante = "90" + p.getClabeOrdenate().substring(0, 3);
				reqSaldo.setCuentaOrdenante(prestadora.getClabeCuentaOrdenante());// 90 +
																	// TRES
																	// PRIMEROS DE
																	// LA CUENTA
																	// ORDENANTE

				firma = firmarSaldo(reqSaldo);
				reqSaldo.setFirma(firma);

				Gson gson = new Gson();
				JSON = gson.toJson(reqSaldo);

				// LOGGER.info("Clave rastreo: " + p.getCveOrdenPago() + " id_Colaborador: "
				// + p.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

				LOGGER.info("JSON: " + crypto.encryp(JSON));
				System.out.println("JSON: " + JSON);

				RestTemplate rs = new RestTemplateConfig().restTemplate();

				// LOGGER.info("url: " + urlStp + metodoRastreo);

				HttpEntity<RequestSaldo> request = new HttpEntity<>(reqSaldo);
				ResponseEntity<Object> response = null;
				try {
					response = rs.exchange(urlStpConsulta + metodoSaldo, HttpMethod.POST, request, Object.class);
					// ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo,
					// HttpMethod.POST, request, Object.class);

					JSON = gson.toJson(response);

					System.out.println(JSON);
					LOGGER.info("Response: " + crypto.encryp(JSON));
					JSON = gson.toJson(response.getBody());
					JsonParser parser = new JsonParser();
					JsonObject respuesta = (JsonObject) parser.parse(JSON);
					LOGGER.info(respuesta.get("respuesta").toString());
					JsonObject res = respuesta.get("respuesta").getAsJsonObject();
					int idRespuesta = respuesta.get("estado").getAsInt();

					if (idRespuesta == 0) {
							res.get("saldo");
							
							dataReturn.put("prestadora", prestadora);
							dataReturn.put("fechaSaldo", new Date());
							dataReturn.put("saldo", res.get("saldo").getAsDouble());
							dataReturn.put("error", Boolean.FALSE);
					}

				//	saldoTotal += p.getSaldo();
				} catch (Exception e) {
					dataReturn.put("error",  Boolean.TRUE);
					LOGGER.error(e.getMessage());
					System.out.println(e.getMessage());
					return dataReturn;
				}

				// SpeiServiceWrapperResponse reesponseSaldo = response.getBody();

				// ResultadoRastreoDto rastreo = response.getBody();

			

			LOGGER.info(
					"========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			LOGGER.info("================================ ERROR Al obtener Saldo  ===============================");
			LOGGER.info("Error obtner Saldo Prestador: " + prestadora + " Descripción error: " + e);
			LOGGER.info("================================ FIN ERROR Al obtner Saldo ===========================");
			
			dataReturn.put("error", Boolean.TRUE);
			return dataReturn;	
		}
		
		
		LOGGER.info("========================================= Termina obtener Saldo================================================");
		
		
		return dataReturn;	

	}

	/*
	 * Genera la fimra stp
	 */

	public String firmar(OrdenPagoWS oPW) {
		String firma = null;
		try {
			String cadenaOriginal = cadenaOriginal(oPW);
			System.out.println("cadena original:" + cadenaOriginal);
			LOGGER.info("Cadena original:" + crypto.encryp(cadenaOriginal));
			firma = sign(cadenaOriginal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firma;
	}

	/*
	 * Genera la fimra stp
	 */

	public String firmarSaldo(RequestSaldo oPW) {
		String firma = null;
		try {
			String cadenaOriginal = cadenaOriginalSaldo(oPW);
			System.out.println("cadena original:" + cadenaOriginal);
			LOGGER.info("Cadena original:" + crypto.encryp(cadenaOriginal));
			firma = sign(cadenaOriginal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firma;
	}

	/*
	 * Forma y regresa la cadena original para generar la firma stp
	 * 
	 */

	public String cadenaOriginal(OrdenPagoWS oPW) {
		StringBuilder sB = new StringBuilder();
		sB.append("||");
		sB.append(oPW.getInstitucionContraparte() == null ? "" : oPW.getInstitucionContraparte()).append("|");
		sB.append(oPW.getEmpresa()).append("|");
		sB.append(oPW.getFechaOperacion() == null ? "" : oPW.getFechaOperacion()).append("|");
		sB.append(oPW.getFolioOrigen() == null ? "" : oPW.getFolioOrigen()).append("|");
		sB.append(oPW.getClaveRastreo() == null ? "" : oPW.getClaveRastreo()).append("|");
		sB.append(oPW.getInstitucionOperante() == null ? "" : oPW.getInstitucionOperante()).append("|");
		sB.append(oPW.getMonto() == null ? "" : oPW.getMonto()).append("|");
		sB.append(oPW.getTipoPago() == null ? "" : oPW.getTipoPago()).append("|");
		sB.append(oPW.getTipoCuentaOrdenante() == null ? "" : oPW.getTipoCuentaOrdenante()).append("|");
		sB.append(oPW.getNombreOrdenante() == null ? "" : oPW.getNombreOrdenante()).append("|");
		sB.append(oPW.getCuentaOrdenante() == null ? "" : oPW.getCuentaOrdenante()).append("|");
		sB.append(oPW.getRfcCurpOrdenante() == null ? "" : oPW.getRfcCurpOrdenante()).append("|");
		sB.append(oPW.getTipoCuentaBeneficiario() == null ? "" : oPW.getTipoCuentaBeneficiario()).append("|");
		sB.append(oPW.getNombreBeneficiario() == null ? "" : oPW.getNombreBeneficiario()).append("|");
		sB.append(oPW.getCuentaBeneficiario() == null ? "" : oPW.getCuentaBeneficiario()).append("|");
		sB.append(oPW.getRfcCurpBeneficiario() == null ? "" : oPW.getRfcCurpBeneficiario()).append("|");
		sB.append(oPW.getEmailBeneficiario() == null ? "" : oPW.getEmailBeneficiario()).append("|");
		sB.append(oPW.getTipoCuentaBeneficiario2() == null ? "" : oPW.getTipoCuentaBeneficiario2()).append("|");
		sB.append(oPW.getNombreBeneficiario2() == null ? "" : oPW.getNombreBeneficiario2()).append("|");
		sB.append(oPW.getCuentaBeneficiario2() == null ? "" : oPW.getCuentaBeneficiario2()).append("|");
		sB.append(oPW.getRfcCurpBeneficiario2() == null ? "" : oPW.getRfcCurpBeneficiario2()).append("|");
		sB.append(oPW.getConceptoPago() == null ? "" : oPW.getConceptoPago()).append("|");
		sB.append(oPW.getConceptoPago2() == null ? "" : oPW.getConceptoPago2()).append("|");
		sB.append(oPW.getClaveCatUsuario1() == null ? "" : oPW.getClaveCatUsuario1()).append("|");
		sB.append(oPW.getClaveCatUsuario2() == null ? "" : oPW.getClaveCatUsuario2()).append("|");
		sB.append(oPW.getClavePago() == null ? "" : oPW.getClavePago()).append("|");
		sB.append(oPW.getReferenciaCobranza() == null ? "" : oPW.getReferenciaCobranza()).append("|");
		sB.append(oPW.getReferenciaNumerica() == null ? "" : oPW.getReferenciaNumerica()).append("|");
		sB.append(oPW.getTipoOperacion() == null ? "" : oPW.getTipoOperacion()).append("|");
		sB.append(oPW.getTopologia() == null ? "" : oPW.getTopologia()).append("|");
		sB.append(oPW.getUsuario() == null ? "" : oPW.getUsuario()).append("|");
		sB.append(oPW.getMedioEntrega() == null ? "" : oPW.getMedioEntrega()).append("|");
		sB.append(oPW.getPrioridad() == null ? "" : oPW.getPrioridad()).append("|");
		sB.append(oPW.getIva() == null ? "" : oPW.getIva()).append("||");
		String cadena = sB.toString();

		return cadena;
	}

	/*
	 * Forma y regresa la cadena original para generar la firma stp
	 */

	public String cadenaOriginalSaldo(RequestSaldo oPW) {
		StringBuilder sB = new StringBuilder();
		sB.append("||");

		sB.append(oPW.getEmpresa()).append("|");
		sB.append(oPW.getCuentaOrdenante() == null ? "" : oPW.getCuentaOrdenante()).append("|");

		sB.append("||");

		String cadena = sB.toString();

		return cadena;
	}

	/*
	 * Firma stp
	 */
	public String sign(String cadena) throws Exception {
		String retVal;
		try {
			String data = cadena;
			Signature firma = Signature.getInstance("SHA256withRSA");
			RSAPrivateKey llavePrivada = getCertified(urlJks + "/" + nombreJKS, password, aliasKeyJks, passwordKeyJks);
			firma.initSign(llavePrivada);
			byte[] bytes = data.getBytes("UTF-8");
			firma.update(bytes, 0, bytes.length);

			Base64 b64 = new Base64();
			retVal = b64.encodeAsString(firma.sign());

		} catch (NoSuchAlgorithmException e) {
			throw new Exception("NoSuchAlgorithmException", e);
		} catch (InvalidKeyException e) {
			throw new Exception("InvalidKeyException: ", e);
		} catch (SignatureException e) {
			throw new Exception("SignatureException", e);
		} catch (NoSuchProviderException e) {
			throw new Exception("NoSuchProviderException", e);
		}
		return retVal;
	}

	/*
	 * Lee el certificado jks
	 */

	private static RSAPrivateKey getCertified(String keystoreFilename, String passwordJKS, String aliasKeyJKS,
			String passwordKeyJKS) throws Exception {
		RSAPrivateKey privateKey;
		try {
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(keystoreFilename), passwordJKS.toCharArray());
			privateKey = (RSAPrivateKey) keystore.getKey(aliasKeyJKS, passwordKeyJKS.toCharArray());// password.toCharArray());
		} catch (FileNotFoundException ex) {
			throw new Exception("FileNotFoundException", ex);
		} catch (IOException ex) {
			throw new Exception("IOException", ex);
		} catch (Exception ex) {
			throw new Exception("Exception", ex);
		}
		return privateKey;
	}

	/*
	 * Prueba de firma
	 */

	public static void main(String[] args) throws Exception {
//    	 try {
//             String data = "hola mundo";
//             Signature firma = Signature.getInstance("SHA256withRSA");// FREYXENET
//             RSAPrivateKey llavePrivada = getCertified("/Users/miguellopez/Downloads/certificadoSTP/freyxenet.jks", "Fr3yX3n3t.2020", "freyxenet","sTp_Fr3yX3n3t");
//             firma.initSign(llavePrivada);
//             byte[] bytes = data.getBytes("UTF-8");
//             firma.update(bytes, 0, bytes.length);
//             BASE64Encoder b64 = new BASE64Encoder();
//             String retVal = b64.encode(firma.sign());
//             LOGGER.info("Firma: " + retVal);
//         } catch (NoSuchAlgorithmException e) {
//             throw new Exception("NoSuchAlgorithmException", e);
//         } catch (InvalidKeyException e) {
//             throw new Exception("InvalidKeyException: ", e);
//         } catch (SignatureException e) {
//             throw new Exception("SignatureException",e);
//         } catch (NoSuchProviderException e) {
//             throw new Exception("NoSuchProviderException", e);
//         }
		BigDecimal tres = new BigDecimal("3.1090");

		LOGGER.info(tres.setScale(2, BigDecimal.ROUND_FLOOR).toString());
	}

}
