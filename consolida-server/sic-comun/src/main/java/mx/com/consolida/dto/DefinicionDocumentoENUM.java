package mx.com.consolida.dto;

public enum DefinicionDocumentoENUM {

	PRESTADORA_COMPROBANTE_DOMICILIO(1L,"prestadora_comprobante_domiclio","COMPROBANTE_DOMICILIO","/prestadora"),
	PRESTADORA_DOCUMENTO_CONSAR(2L,"prestadora_documento_CONSAR","DOCUMENTO CONSAR","/prestadora"),
	PRESTADORA_ACTA_CONSTITUTIVA(3L,"prestadora_acta constitutiva","ACTA CONSTITUTIVA","/prestadora"),
	PRESTADORA_REGISTRO_PUBLICO_DE_LA_PROPIEDAD(4L,"prestadora_registro_publico_de_la_propiedad","REGISTRO PUBLICO DE LA PROPIEDAD","/prestadora"),
	PRESTADORA_RFC(5L,"prestadora_rfc","RFC","/prestadora"),
	PRESTADORA_IDENTIFICACION_REPRESENTANTE_LEGAL(6L,"prestadora_identificacion_representante_legal","IDENTIFICACION REPRESENTANTE LEGAL","/prestadora"),
	PRESTADORA_CUMPLIMIENTO_OBLIGACIONES_32D(7L,"prestadora_cumplimiento_obligaciones_32D","CUMPLIMIENTO OBLIGACIONES 32D","/prestadora"),
	PRESTADORA_TARJETA_IDENTIFICACION_PATRONAL(8L,"prestadora_tarjeta_identificacion_patronal","TARJETA IDENTIFICACION PATRONAL","/prestadora"),
	PRESTADORA_CONSTANCIA_SITUACION_FISCAL(9L,"prestadora_constancia_situacion_fiscal","CONSTANCIA SITUACION FISCAL","/prestadora"),
	PRESTADORA_COMPROBANTE_FISCAL(10L,"prestadora_comprobante_fiscal","COMPROBANTE FISCAL","/prestadora"),
	PRESTADORA_FIEL_KEY(11L, "prestadora_fiel_key", "KEY", "/prestadora"),
	PRESTADORA_FIEL_CER(12L, "prestadora_fiel_cer", "CER", "/prestadora"),
	PRESTADORA_CSD_KEY(13L, "prestadora_csd_key", "KEY", "/prestadora"),
	PRESTADORA_KEY_KEY(14L, "prestadora_csd_cer", "CER", "/prestadora"),
	PRESTADORA_ACCIONISTA_COMPROBANTE_DOMICILIO(15L, "prestadora_accionista_comprobante_domicilio", "COMPROBANTE DOMICILIO", "/prestadora"),
	PRESTADORA_ACCIONISTA_IDENTIFICACION_OFICIAL(16L, "prestadora_accionista_identificacion_oficial", "IDENTIFICACION_OFICIAL (IFE, PASAPORTE)", "/prestadora"),
	PRESTADORA_ACCIONISTA_IDENTIFICACION_CEDULA_RFC(17L, "prestadora_accionista_cedula_rfc", "CEDULA O RFC", "/prestadora"),
	PRESTADORA_RL_COMPROBANTE_DOMICILIO(18L, "prestadora_rl_comprobante_domicilio", "COMPROBANTE DE DOMICILIO", "/prestadora"),
	PRESTADORA_RL_IDENTIFICACION_OFICIAL(19L, "prestadora_rl_identificacion_oficial", "IDENTIFICACION OFICIAL (IFE, PASAPORTE)", "/prestadora"),
	PRESTADORA_RL_RFC(20L, "prestadora_rl_rfc", "RFC", "/prestadora"),
	PRESTADORA_RL_CURP(21L, "prestadora_rl_curp", "CURP", "/prestadora"),
	PRESTADORA_AL_COMPROBANTE_DOMICILIO(22L, "prestadora_al_comprobante_domicilio", "COMPROBANTE DE DOMICILIO", "/prestadora"),
	PRESTADORA_AL_IDENTIFICACION_OFICIAL(23L, "prestadora_al_identificacion_oficial", "IDENTIFICACION OFICIAL (IFE, PASAPORTE)", "/prestadora"),
	PRESTADORA_AL_RFC(24L, "prestadora_al_rfc", "RFC", "/prestadora"),
	PRESTADORA_AL_CURP(25L, "prestadora_al_curp", "CURP", "/prestadora"),
	CLIENTE_RL_COMPROBANTE_DOMICILIO(26L, "cliente_rl_comprobante_domicilio", "COMPROBANTE DE DOMICILIO", "/cliente"),
	CLIENTE_RL_IDENTIFICACION_OFICIAL(27L, "cliente_rl_identificacion_oficial", "IDENTIFICACION OFICIAL (IFE, PASAPORTE)", "/cliente"),
	CLIENTE_RL_RFC(28L, "cliente_rl_rfc", "RFC", "/cliente"),
	CLIENTE_RL_CURP(29L, "cliente_rl_curp", "CURP", "/cliente"),
	CLIENTE_ACTA_CONSTITUTIVA(30L, "cliente_acta_constitutiva", "ACTA CONSTITUTIVA", "/cliente"),
	CLIENTE_COMPROBANTE_DOMICILIO(31L, "cliente_comprobante_domicilio", "COMPROBANTE DE DOCMICILIO", "/cliente"),
	CLIENTE_COMPROBANTE_FISCAL(32L, "cliente_comprobante_fiscal", "COMPROBANTE FISCAL", "/cliente"),
	CLIENTE_CONSTANCIA_SITUACION_FISCAL(33L, "cliente_constancia_situacion_fiscal", "CONSTANCIA DE SITUACION FISCAL", "/cliente"),
	CLIENTE_CUMPLIMIENTO_OBLIGACIONES_32D(34L, "cliente_cumplimiento_obligaciones_32d", "CUMPLIMIENTO DE OBLIGACIONES 32D", "/cliente"),
	CLIENTE_IDENTIFICACION_REPRESENTANTE_LEGAL(35L, "cliente_identificacion_representante_legal", "IDENTIFICACIÓN DELE REPRESENTANTE LEGAL", "/cliente"),
	CLIENTE_REGISTRO_PUBLICO_PROPIEDAD(36L, "cliente_registro_publico_propiedad", "REGISTRO PÚBLICO DE LA PROPIEDAD", "/cliente"),
	CLIENTE_RFC(37L, "cliente_rfc", "RFC", "/cliente"),
	CLIENTE_TARJETA_IDENTIFICACION_PATRONAL(38L, "cliente_tarjeta_identificacion_patronal", "TARJETA DE IDENTIFICACION PATRONAL (IMSS)", "/cliente"),
	CLIENTE_AL_COMPROBANTE_DOMICILIO(39L, "cliente_al_comprobante_domicilio", "COMPROBANTE DE DOMICILIO", "/cliente"),
	CLIENTE_AL_IDENTIFICACION_OFICIAL(40L, "cliente_al_identificacion_oficial", "IDENTIFICACION OFICIAL (IFE, PASAPORTE)", "/cliente"),
	CLIENTE_AL_RFC(41L, "cliente_al_rfc", "RFC", "/cliente"),
	CLIENTE_AL_CURP(42L, "cliente_al_curp", "CURP", "/cliente"),
	NOMINA_CLIENTE_COLABORADOR_INFONAVIT(43L, "nomina_cliente_colaborador_infonavit", "INFONAVIT (EN CASO DE QUE TENGA)", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_FONACOT(44L, "nomina_cliente_colaborador_fonacot", "FONACOT (EN CASO DE QUE TENGA)", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_COMPROBANTE_PENSION_ALIMENTICIA(45L, "nomina_cliente_colaborador_comprobante_pension_alimenticia", "COMPROBANTE PENSIÓN ALIMENTICIA (EN CASO DE D", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_NSS(46L, "nomina_cliente_colaborador_nss", "NUMERO SEGURO SOCIAL (AFIL O MOVIMIENTO AFILI", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_COMPROBANTE_DOMICILIO(47L, "nomina_cliente_comprobante_domicilio", "COMPROBANTE DE DOMICILIO", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_IDENTIFICACION_OFICIAL(48L, "nomina_cliente_identificacion_oficial", "IDENTIFICACION OFICIAL (IFE, PASAPORTE)", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_ACTA_NACIMIENTO(49L, "nomina_cliente_acta_nacimiento", "ACTA DE NACIMIENTO", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_CURP(50L, "nomina_cliente_curp", "CURP", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_RFC(51L, "nomina_cliente_cedula_fiscal_rfc", "CÉDULA FISCAL O RFC", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_ESTADO_CUENTA_BANCARIA(52L, "nomina_cliente_estado_cuenta_bancaria", "ESTADO DE CUENTA_BANCARIA", "/nominaCliente"),
	NOMINA_CLIENTE_COLABORADOR_DOCUMENTO_MIGRATORIO_FM3(53L, "nomina_cliente_documento_migratorio_fm3", "DOCUMENTO MIGRATORIO FM3 (EN CASO DE SER EXTRANJERO)", "/nominaCliente"),
	CLIENTE_RL_CER(54L, "cliente_rl_cer", "CER", "/cliente"),
	CLIENTE_RL_KEY(55L, "cliente_rl_key", "KEY", "/cliente"),
	CLIENTE_AL_CER(56L, "cliente_al_cer", "CER", "/cliente"),
	CLIENTE_AL_KEY(57L, "cliente_al_key", "KEY", "/cliente"),
	PRESTADORA_RL_CER(58L, "prestadora_rl_cer", "CER", "/cliente"),
	PRESTADORA_RL_KEY(59L, "prestadora_rl_key", "KEY", "/cliente"),
	PRESTADORA_AL_CER(60L, "prestadora_al_cer", "CER", "/cliente"),
	PRESTADORA_AL_KEY(61L, "prestadora_al_key", "KEY", "/cliente"),
	PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO(62L, "ppp_docu_sust_finan", "DOCUMENTO SUSTENTO (FINANCIAMIENTO)", "/ppp"),
	FACTURA_NOMINA_PPP(63L, "ppp_factura_nomina", "FACTURA NOMINA PPP", "/ppp"),
	XML_FACTURA_NOMINA_PPP(64L, "ppp_xml_factura_nomina", "XML FACTURA NOMINA PPP", "/ppp"),
	COMPROBANTE_PAGO_NOMINISTA(65L, "ppp_comprobante_pago_nominista", "COMPROBANTE PAGO (NOMINISTA)", "/ppp"),
	FACTURA_COLABORADOR_PPP(69L, "ppp_colaborador_factura", "FACTURA COLABORADOR PPP", "/ppp"),
	XML_FACTURA_COLABORADOR_PPP(70L, "ppp_colaborador_xml_factura", "XML FACTURA COLABORADOR PPP", "/ppp")
	
	;

	private Long  idDefinicionDocumento;
	private String clave;
	private String descripcion;
	private String urlBase;
	
	private DefinicionDocumentoENUM(Long idDefinicionDocumento, String clave, String descripcion, String urlBase) {
		this.idDefinicionDocumento = idDefinicionDocumento;
		this.clave = clave;
		this.descripcion = descripcion;
		this.urlBase = urlBase;
	}
	
	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}
	public String getClave() {
		return clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getUrlBase() {
		return urlBase;
	}
	
}
