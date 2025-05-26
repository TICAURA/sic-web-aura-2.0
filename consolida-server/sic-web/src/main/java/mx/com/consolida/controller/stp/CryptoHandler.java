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
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceWrapperResponse;
import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.controller.base.BaseController;

import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.ppp.dto.ResponseRastreoDto;
import mx.com.consolida.ppp.dto.ResultadoRastreoDto;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.reportes.JSONParser;

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

	@Value("${aliasKeyJks}")
	private String aliasKeyJks;

	@Value("${passwordKeyJks}")
	private String passwordKeyJks;

	@RequestMapping(value = "/finanzas/generaPagosSTP")
	@ResponseBody
	private void enviaPago(@RequestBody List<EmpleadoDTO> empleados)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		Boolean isContieneErrorSTP = false;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String fechaTexto = formatter.format(new Date());
		String JSON = "";
		String original = "";
		String metodoPago = "registra";
		
		System.out.println("=========================================Inicio Envio Pagos =============================================");


		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();
		if (empleados.get(0).getUrlActual().equals("172.23.1.163")
				|| empleados.get(0).getUrlActual().equals("148.228.8.248")) {
			for (EmpleadoDTO empleado : empleados) {
				if (empleado.getIdEstatus() != 5 && empleado.getIdEstatus() != 8 && empleado.getIdEstatus() != 12
						&& empleado.getIdEstatus() != 13) {

					try {

						ordenPagoWS = new OrdenPagoWS();

						ordenPagoWS.setEmpresa(empleados.get(0).getNombreCentroCostos()); // (empleados.get(0).getNombreCentroCostos());
																							// // NOMBRE CENTRO DE
																							// COSTOS
						ordenPagoWS.setMonto(new BigDecimal("0.01"));// empleado.getMontoPPP()
						ordenPagoWS.setInstitucionContraparte(846);// (Integer.parseInt(empleado.getClabe().substring(0,3)));//
																	// PRIMEROS 3 DIGITOS DE LA CLABE
						ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());// empleado.getCveOrdenPago()
						ordenPagoWS.setReferenciaNumerica(Integer.parseInt(fechaTexto)); // ddmmyy
						String nombreBeneficiario = empleado.getNombre() + " " + empleado.getApellidoPaterno();
						if (empleado.getApellidoMaterno() != null) {
							nombreBeneficiario = nombreBeneficiario + " " + empleado.getApellidoMaterno();
						}
						ordenPagoWS.setNombreBeneficiario(nombreBeneficiario);
						ordenPagoWS.setConceptoPago("PENSION"); // SIEMPRE VA PENSION
//           ordenPagoWS.setCuentaBeneficiario("846180123400000001");// empleado.getClabe()   846180123400000001
						if (empleado.getClabe() != null && empleado.getClabe().length() == 18) {
							ordenPagoWS.setCuentaBeneficiario(empleado.getClabe());
							ordenPagoWS.setTipoCuentaBeneficiario(40);
						} else {
							ordenPagoWS.setCuentaBeneficiario(empleado.getNumeroCuenta().toString());
							ordenPagoWS.setTipoCuentaBeneficiario(3);
						}
//           ordenPagoWS.setTipoCuentaBeneficiario(40); //40 = TRANSFERENCIA ELECTRONICA, 3 = TARJETA DE DEBITO, 10 = TELEFONO CELULAR
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
//						System.out.println("JSON: "+JSON);
						System.out.println("clave rastreo: " + ordenPagoWS.getClaveRastreo() + "id_Colaborador: "
								+ empleado.getIdPppColaborador() + "id_nomina: " + empleado.getIdNomina());

						RestTemplate rs = new RestTemplateConfig().restTemplate();

						HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);
						ResponseEntity<SpeiServiceWrapperResponse> response = rs.exchange(urlStp, HttpMethod.PUT,
								request, SpeiServiceWrapperResponse.class);
//           System.out.println("resultado --> " + response.getBody());
						SpeiServiceWrapperResponse speiServiceResponse = response.getBody();
//           System.out.println("id: "+speiServiceResponse.getResultado().getId());
//							System.out.println("Descripcion: " + speiServiceResponse.getResultado().getDescripcionError());

						System.out.println("id_stp: " + speiServiceResponse.getResultado().getId());
						if (speiServiceResponse.getResultado().getDescripcionError() != null) {
							System.out.println("descripción: " + speiServiceResponse.getResultado().getId());
						}

						if (speiServiceResponse.getResultado() != null
								&& speiServiceResponse.getResultado().getDescripcionError() == null) {
							empleado.setIdStp(speiServiceResponse.getResultado().getId());
							nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
									CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA_STP.getId());
						} else {
							isContieneErrorSTP = true;
							empleado.setDescripcionErrorStp(speiServiceResponse.getResultado().getDescripcionError());
							nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
									CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
						}

						System.out.println(
								"=========================================Termina orden Pago =============================================");
					} catch (Exception e) {
						System.out.println(
								"================================ ERROR Orden de Pago ====================    =======");
						System.out.println("Error al generar idStp: " + empleado.getIdPppColaborador() + " " + e);
						isContieneErrorSTP = true;
						System.out.println(
								"================================ FIN ERROR Orden de Pago ===========================");
						System.out.println(
								"=========================================Termina orden Pago =============================================");

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
		} else {
			for (EmpleadoDTO empleado : empleados) {

				if (empleado.getIdEstatus() != 5 && empleado.getIdEstatus() != 8 && empleado.getIdEstatus() != 12
						&& empleado.getIdEstatus() != 13) {

					try {

						System.out.println(
								"=========================================Inicio orden Pago =============================================");

						ordenPagoWS = new OrdenPagoWS();

						ordenPagoWS.setEmpresa(empleados.get(0).getNombreCentroCostos()); // (empleados.get(0).getNombreCentroCostos());
																							// // NOMBRE CENTRO DE
																							// COSTOS
						// TODO cambiar el monto para produccion que sean diferente de un centavo
						ordenPagoWS.setMonto(empleado.getMontoPPP().setScale(2, BigDecimal.ROUND_FLOOR)); // new
																											// BigDecimal("0.01"));
						ordenPagoWS.setInstitucionContraparte(Integer.parseInt(empleado.getInstitucionContraparte()));// (Integer.parseInt(empleado.getClabe().substring(0,3)));//
																														// PRIMEROS
																														// 3
																														// DIGITOS
																														// DE
																														// LA
																														// CLABE
						ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());// empleado.getCveOrdenPago()
						ordenPagoWS.setReferenciaNumerica(Integer.parseInt(fechaTexto)); // ddmmyy
						String nombreBeneficiario = empleado.getNombre() + " " + empleado.getApellidoPaterno();
						if (empleado.getApellidoMaterno() != null) {
							nombreBeneficiario = nombreBeneficiario + " " + empleado.getApellidoMaterno();
						}
						ordenPagoWS.setNombreBeneficiario(nombreBeneficiario);
						ordenPagoWS.setConceptoPago("PENSION"); // SIEMPRE VA PENSION
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
//           ordenPagoWS.setTipoCuentaBeneficiario(40); //40 = TRANSFERENCIA ELECTRONICA, 3 = TARJETA DE DEBITO, 10 = TELEFONO CELULAR
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
						original = cadenaOriginal(ordenPagoWS);
						ordenPagoWS.setFirma(firmar(ordenPagoWS));

						Gson gson = new Gson();
						JSON = gson.toJson(ordenPagoWS);
//						 System.out.println("JSON: "+JSON);
						System.out.println("Clave rastreo: " + ordenPagoWS.getClaveRastreo() + " id_Colaborador: "
								+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

						RestTemplate rs = new RestTemplateConfig().restTemplate();

						HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);
						ResponseEntity<SpeiServiceWrapperResponse> response = rs.exchange(urlStp + metodoPago,
								HttpMethod.PUT, request, SpeiServiceWrapperResponse.class);
//						 System.out.println("resultado --> " + response.getBody());
						SpeiServiceWrapperResponse speiServiceResponse = response.getBody();
//							System.out.println("id: " + speiServiceResponse.getResultado().getId());
//							System.out.println(
//									"Descripcion: " + speiServiceResponse.getResultado().getDescripcionError());

						System.out.println("id_stp: " + speiServiceResponse.getResultado().getId());
						if (speiServiceResponse.getResultado().getDescripcionError() != null
								&& speiServiceResponse.getResultado().getDescripcionError() != "0") {
							
							System.out.println("Descripcion: " + speiServiceResponse.getResultado().getId());
						}

						if (speiServiceResponse.getResultado() != null
								&& speiServiceResponse.getResultado().getDescripcionError() == null) {
							empleado.setIdStp(speiServiceResponse.getResultado().getId());
							empleado.setDescripcionErrorStp(
									"json: " + JSON + " Firma: " + ordenPagoWS.getFirma() + " CO: " + original);
							
							nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
									CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA_STP.getId());
						} else {
							isContieneErrorSTP = true;
							empleado.setDescError("json: " + JSON + " Firma: " + ordenPagoWS.getFirma() + "CO: " + original);
							empleado.setDescripcionErrorStp(speiServiceResponse.getResultado().getDescripcionError());
							nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
									CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
						}
						System.out.println(	"========================================= Termina orden Pago =============================================");


					} catch (Exception e) {
						System.out.println(
								"================================ ERROR Orden de Pago ===============================");
						System.out.println(
								"Error al generar idStp, idColaborador: " + empleado.getIdPppColaborador() + " " + e);
						empleado.setDescripcionErrorStp(
								"json: " + JSON + " Firma: " + ordenPagoWS.getFirma() + " CO: " + original);
						empleado.setDescError("Error stp no contesta");
						nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
								CatEstatusColaboradorEnum.ORDEN_PAGO_ERRO_STP.getId());
						isContieneErrorSTP = true;
						System.out.println(
								"================================ FIN ERROR Orden de Pago ===========================");
						System.out.println(
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
				
				System.out.println(
						"========================================= Termina Envio Pagos =============================================");
			}
		}

	}

	@RequestMapping(value = "/finanzas/consualtaOrdenes")
	@ResponseBody
	public void consultaOrdenes(@RequestBody EmpleadoDTO empleado) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoRastreo = "consOrdenesFech";
		String firma = "";
		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();

		try {

			System.out.println(
					"=========================================Inicio rastreo movimiento =============================================");
			ordenPagoWS.setEmpresa(empleado.getNombreCentroCostos()); // clave de rastreo del movimiento

			String fechaOperacion = formatter.format(empleado.getFechaDispersion()); // "20210226";
			ordenPagoWS.setFechaOperacion(Integer.valueOf(fechaOperacion));
			// 90 +
			// TRES
			// PRIMEROS DE
			// LA CUENTA
			// ORDENANTE

			firma = firmar(ordenPagoWS);

			ordenPagoWS.setFirma(firma);

			ordenPagoWS.setEstado("E");// enviados

			Gson gson = new Gson();
			JSON = gson.toJson(ordenPagoWS);

			System.out.println("Clave rastreo: " + empleado.getCveOrdenPago() + " id_Colaborador: "
					+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

			System.out.println("JSON: " + JSON);

			RestTemplate rs = new RestTemplateConfig().restTemplate();

			System.out.println("url: " + urlStp + metodoRastreo);

			HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);

			ResponseEntity<Object> response = rs.exchange(urlStp + metodoRastreo, HttpMethod.POST, request,
					Object.class);
			// System.out.println("resultado --> " + response.getBody());
			Object obj = response.getBody();
//							System.out.println("id: " + speiServiceResponse.getResultado().getId());
//							System.out.println(
//									"Descripcion: " + speiServiceResponse.getResultado().getDescripcionError());

			String resp = response.getStatusCode().toString();

			System.out.println("Response: " + response.toString());
			System.out.println("StatusCode: " + response.getStatusCode().toString());
			System.out.println("id_stp: " + obj.toString());

			String str = obj.getClass().getName();

			String jsonString = gson.toJson(obj);
			JsonParser parser = new JsonParser();
			JsonObject respuesta = (JsonObject) parser.parse(jsonString);
			System.out.println(respuesta.get("resultado").toString());
			JsonObject res = respuesta.get("resultado").getAsJsonObject();
			int idRespuesta = res.get("id").getAsInt();
			System.out.println("id Respuesta: " + idRespuesta);

			if (idRespuesta == 1) {
				JsonObject ordPag = res.get("ordenPago").getAsJsonObject();
				System.out.println(res.get("ordenPago").toString());
				String estOrd = ordPag.get("estado").getAsString();
				System.out.println("Estado orden: " + estOrd);

				if ("LQ".equals(estOrd) && empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.LIQUIDADA_STP.getCve_stp()) {
					System.out.println("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.LIQUIDADA_STP.getId());

				} else if (estOrd == "CD" && empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.CANCELACION.getCve_stp()) {
					System.out.println("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.CANCELACION.getId());
				} else if (estOrd == "D" && empleado.getCveOrdenPago() != CatEstatusColaboradorEnum.DEVOLUCION.getCve_stp()) {
					System.out.println("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.DEVOLUCION.getId());
				}else {
					System.out.println("Estado orden: " + estOrd);
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());
				}

			} else {

				System.out.println(
						"================================ ERROR  rastreo orden de Pago STP ==========================");
				String descripcionError = res.get("descripcionError").toString();
				System.out.println("Err: " + descripcionError);
				System.out.println(
						"================================ FIN ERROR rastreo orden de Pago ===========================");

			}

			System.out.println(
					"========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			System.out.println(
					"================================ ERROR rastreo orden de Pago ===============================");
			System.out.println("Error al realizar rastreo de movimiento, idColaborador: "
					+ empleado.getIdPppColaborador() + " " + e);
			System.out.println(
					"================================ FIN ERROR rastreo orden de Pago ===========================");
			System.out.println(
					"========================================= Termina rastreo orden Pago =============================================");

		}

	}

	@RequestMapping(value = "/finanzas/restreoMovimieno")
	@ResponseBody
	public void rastreo(@RequestBody EmpleadoDTO empleado) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String JSON = "";
		String metodoRastreo = "consOrdEnvRastreo";
		String firma = "";
		OrdenPagoWS ordenPagoWS = new OrdenPagoWS();

		try {

			System.out.println(
					"=========================================Inicio rastreo movimiento =============================================");
			ordenPagoWS.setEmpresa(empleado.getNombreCentroCostos()); // clave de rastreo del movimiento
			ordenPagoWS.setClaveRastreo(empleado.getCveOrdenPago());// empleado.getCveOrdenPago()
			String fechaOperacion = formatter.format(empleado.getFechaDispersion()); // "20210226";
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

			System.out.println("Clave rastreo: " + empleado.getCveOrdenPago() + " id_Colaborador: "
					+ empleado.getIdPppColaborador() + " id_nomina: " + empleado.getIdNomina());

//						System.out.println("JSON: " + JSON);

			RestTemplate rs = new RestTemplateConfig().restTemplate();

			//			System.out.println("url: " + urlStp + metodoRastreo);

			HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);

			ResponseEntity<ResponseRastreoDto>response = rs.exchange(urlStp + metodoRastreo, HttpMethod.POST, request,
					ResponseRastreoDto.class);

			JSON = gson.toJson(response);
//					System.out.println("Response: " + JSON);
			ResultadoRastreoDto rastreo = response.getBody().getResultado();


					JSON = gson.toJson(rastreo);
			//			System.out.println("Resultado: " + JSON);
			System.out.println("StatusCode: " + response.getStatusCode().toString());
			if ( rastreo.getId() ==  1) {
				OrdenPagoWS ordenPago = rastreo.getOrdenPago();
				//				JSON = gson.toJson(ordenPago);
				//				System.out.println("Rastreo: " + JSON);
				System.out.println("Estado orden: " + ordenPago.getEstado());

				System.out.println("Estado Actual: "+ empleado.getDescEstatus().toUpperCase() + " Estado Rastreo: " + CatEstatusColaboradorEnum.LIQUIDADA_STP.getDesc());

				if ("LQ".equals(ordenPago.getEstado())
						&& !empleado.getDescEstatus().toUpperCase().equals( CatEstatusColaboradorEnum.LIQUIDADA_STP.getDesc())) {
					System.out.println("Estado orden: " + ordenPago.getEstado());
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.LIQUIDADA_STP.getId());

				} else if (( "CD".equals(ordenPago.getEstado()) || "D".equals(ordenPago.getEstado())) && !empleado
						.getDescEstatus().equals(CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getDesc())) {
					System.out.println("Estado orden: " + ordenPago.getEstado());
					nominaBO.crearEstatusColaboradorSTP(empleado, getUser().getIdUsuario(),
							CatEstatusColaboradorEnum.ORDEN_PAGO_RECHAZADA_STP.getId());

				}

			} else {

				System.out.println("================================ ERROR  rastreo orden de Pago STP ==========================");
				System.out.println("Err: " + rastreo.getDescripcionError());
				System.out.println(
						"================================ FIN ERROR rastreo orden de Pago ===========================");

			}

			System.out.println("========================================= Termina rastreo orden de Pago =============================================");

		} catch (Exception e) {
			System.out.println(	"================================ ERROR rastreo orden de Pago ===============================");
			System.out.println("Error al realizar rastreo de movimiento, idColaborador: "
					+ empleado.getIdPppColaborador() + " Descripción error: " + e);
			System.out.println("================================ FIN ERROR rastreo orden de Pago ===========================");
			System.out.println("========================================= Termina rastreo orden Pago =============================================");

		}

	}

	private static HttpComponentsClientHttpRequestFactory useApacheHttpClientWithSelfSignedSupport() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory useApacheHttpClient = new HttpComponentsClientHttpRequestFactory();
		useApacheHttpClient.setHttpClient(httpClient);
		return useApacheHttpClient;
	}

	public String firmar(OrdenPagoWS oPW) {
		String firma = null;
		try {
			firma = sign(cadenaOriginal(oPW));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firma;
	}

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
//	System.out.println("Cadena original: " + cadena);
		return cadena;
	}

	@SuppressWarnings("restriction")
	public String sign(String cadena) throws Exception {
		String retVal;
		try {
			String data = cadena;
			Signature firma = Signature.getInstance("SHA256withRSA");
			RSAPrivateKey llavePrivada = getCertified(urlJks + "/" + nombreJKS, password, aliasKeyJks, passwordKeyJks);
			firma.initSign(llavePrivada);
			byte[] bytes = data.getBytes("UTF-8");
			firma.update(bytes, 0, bytes.length);
			retVal = Base64.getEncoder().encodeToString(firma.sign());
			System.out.println("Firma: " + retVal);
//            System.out.println("Firma: " + retVal);
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
//             System.out.println("Firma: " + retVal);
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

		System.out.println(tres.setScale(2, BigDecimal.ROUND_FLOOR));
	}

}
