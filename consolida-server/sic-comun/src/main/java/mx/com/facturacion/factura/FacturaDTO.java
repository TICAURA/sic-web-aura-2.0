package mx.com.facturacion.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.dto.NominasDTO;

@SuppressWarnings("serial")
public class FacturaDTO implements Serializable {
	
	private Long idPPPNominaFactura;
	private Long idFactura;
	private String fechaHoraEmision;
	private Long idPrestadoraServicio;
	private String consar;
	private Long idCliente;
	private Long idUsuarioAplicativo;
	private Long idPPPNomina;
	private String idConsar;
	private BigDecimal montoComprobantePago;
	private NominaEstatusEnum nominaEstatus;
	
	//1 emisor
	private EmpresaDTO empresa;
	private DomicilioDTO domicilioEmpresa;
	
	//2 receptor
	private ClienteDTO cliente;
	private DomicilioDTO domicilioCliente;
	private NominaDto nomina;
	
	//3 Datos facturacion
	private CatGeneralDto tipoFactura;
	private CatGeneralDto tipoComprobante; // se cmabia "String-tipoFactura" por  catTipoComprobante
	private Date fechaCreacionDate;
	private String fechaCreacion;
	private CatGeneralDto serie;
	
	private CatGeneralDto lugarExpedicion;
	
	//4 Datos de pago
	private CatGeneralDto moneda;
	private String tipoCambio;
	private CatGeneralDto formaPago;
	private CatGeneralDto metodoPago;
	private String digitosCuenta;
	private CatGeneralDto condicionPago;
	private Integer diasCredito;
	
	// selecion de proveddor timrado
	private CatGeneralDto pacTimbrado;
	
	//// NUEVOS ///////
	private CatGeneralDto regimenFiscal;
	private CatGeneralDto conceptoFacturacion;
	
	private CatGeneralDto usoCFDI;
	private BigDecimal totalIvaTrasladado16;
	
	
	//5 totales
	private ConceptoDTO concepto;
	private List<ConceptoDTO> conceptos;
	
	private List<ImpuestoFacturaDTO> impuestosFactura;
	
	//6 Totales
	private TotalDTO totales;
	
	//7 Extradata
	private String pass;
	private EmpresaLogoDTO empresaLogo;
	private String logoEmisor;
	
	//8 Complemento
	private ComplementoDTO complementoDTO;
	
	// certificado
	private DatosCertificadoDTO datosCertificadoDTO;
	
	//9 Complemento de facturas de nomina
	private ComplementoNominaDto complementoNominaDto;
	
	private String cadena;
	private String sello;
	private String urlFactura;
	private String urlPdf;
	
	private String montoLetra;
	

	//10 Notificaciones
	private NotificacionDTO notificacion;

	//11 Adicional nota
	private String notaFactura;
	
	
	//adicional para guardar el certitificado
	private String guardarCertificado;
	
	// documento
	private Long idCMS;
	private Long idCmsXmlFactura;
	private Long idCmsPdfFactura;
	private String nombreArchivoCarga;
	private String nombreArchivo;
	private String usuario;
	private DocumentoDTO documentoNuevo;
	private int totalDocumentosRegistrados;
	
	private String logoPrestadoraServicioString;
	
	private Long folio;
	
	/*multifactura*/
	private Long idNominaCliente;
	private Long totalNominas;
	private int isMultifactura;
	private String serieS;
	private List<ConceptoDTO> conceptosPlus;
	//private List<NominaDto> listNominasVincular;
	private List<NominasDTO> listNominasVincular;
	private ConceptoDTO conceptoPlus;
	private CelulaDto celula;
	private Long idDeposito;
	
	 //sindicato
	 private String titulo;
	 private Long percepciones;
	 private String clavePercepcion;
	 private String descripcion;
	 private Long estimbreSindicato;

	
	public BigDecimal getMontoDepositoMenosDiez() {
		return montoDepositoMenosDiez;
	}

	public void setMontoDepositoMenosDiez(BigDecimal montoDepositoMenosDiez) {
		this.montoDepositoMenosDiez = montoDepositoMenosDiez;
	}

	private BigDecimal montoDeposito;
	private BigDecimal montoDepositoMenosDiez;
	
	public CelulaDto getCelula() {
		return celula;
	}

	public void setCelula(CelulaDto celula) {
		this.celula = celula;
	}

	public ConceptoDTO getConceptoPlus() {
		return conceptoPlus;
	}

	public void setConceptoPlus(ConceptoDTO conceptoPlus) {
		this.conceptoPlus = conceptoPlus;
	}

	public FacturaDTO(){
		
	}
	
	public FacturaDTO(String demo){
		this.idFactura = 1L;
		this.empresa = new EmpresaDTO("deo");
		this.domicilioEmpresa = new DomicilioDTO("demo");
		this.cliente = new ClienteDTO("demo");
		this.domicilioCliente = new DomicilioDTO("demo");
//		this.tipoFactura = "Tipo de factura";
		this.serie = new CatGeneralDto(1L, "SERIE CLAVE", "SERIE DESC");
		FolioDTO folio = new FolioDTO();
		folio.setIdFolio(1);
		folio.setFolio(1);
		
		this.lugarExpedicion = new CatGeneralDto(1L, "16780", "Desc lugar de expedición");
		this.moneda = new CatGeneralDto(1L, "MXN", "MXN desc");
		this.tipoCambio = "1";
		this.formaPago = new CatGeneralDto(1L, "03", "desc forma de pago");
		this.metodoPago = new CatGeneralDto(1L, "PUE", "Desc método de pago");
		this.digitosCuenta = "0123";
		this.condicionPago = new CatGeneralDto(1L, "Clave condicion de pago", "Desc condición de pago");
		this.diasCredito = 4;
		this.conceptos = new ArrayList<ConceptoDTO>();
		this.conceptos.add(new ConceptoDTO("demo"));
		this.totales = new TotalDTO("demo");
		this.tipoComprobante = new CatGeneralDto(1L,"I", "Ingreso");
		this.tipoFactura = new CatGeneralDto(1L,"I","FACTURA");
		this.regimenFiscal = new CatGeneralDto(1L,"601","1");
	}

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}

	public DomicilioDTO getDomicilioEmpresa() {
		return domicilioEmpresa;
	}

	public void setDomicilioEmpresa(DomicilioDTO domicilioEmpresa) {
		this.domicilioEmpresa = domicilioEmpresa;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public DomicilioDTO getDomicilioCliente() {
		return domicilioCliente;
	}

	public void setDomicilioCliente(DomicilioDTO domicilioCliente) {
		this.domicilioCliente = domicilioCliente;
	}




	public Date getFechaCreacionDate() {
		return fechaCreacionDate;
	}

	public void setFechaCreacionDate(Date fechaCreacionDate) {
		this.fechaCreacionDate = fechaCreacionDate;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public CatGeneralDto getSerie() {
		return serie;
	}

	public void setSerie(CatGeneralDto serie) {
		this.serie = serie;
	}



	public CatGeneralDto getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(CatGeneralDto lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	public CatGeneralDto getMoneda() {
		return moneda;
	}

	public void setMoneda(CatGeneralDto moneda) {
		this.moneda = moneda;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public CatGeneralDto getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(CatGeneralDto formaPago) {
		this.formaPago = formaPago;
	}

	public CatGeneralDto getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(CatGeneralDto metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getDigitosCuenta() {
		return digitosCuenta;
	}

	public void setDigitosCuenta(String digitosCuenta) {
		this.digitosCuenta = digitosCuenta;
	}

	public CatGeneralDto getCondicionPago() {
		return condicionPago;
	}

	public void setCondicionPago(CatGeneralDto condicionPago) {
		this.condicionPago = condicionPago;
	}

	public Integer getDiasCredito() {
		return diasCredito;
	}

	public void setDiasCredito(Integer diasCredito) {
		this.diasCredito = diasCredito;
	}

	public List<ConceptoDTO> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<ConceptoDTO> conceptos) {
		this.conceptos = conceptos;
	}

	public TotalDTO getTotales() {
		return totales;
	}

	public void setTotales(TotalDTO totales) {
		this.totales = totales;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getLogoEmisor() {
		return logoEmisor;
	}

	public void setLogoEmisor(String logoEmisor) {
		this.logoEmisor = logoEmisor;
	}

	public ComplementoDTO getComplementoDTO() {
		return complementoDTO;
	}

	public void setComplementoDTO(ComplementoDTO complementoDTO) {
		this.complementoDTO = complementoDTO;
	}

	public DatosCertificadoDTO getDatosCertificadoDTO() {
		return datosCertificadoDTO;
	}

	public void setDatosCertificadoDTO(DatosCertificadoDTO datosCertificadoDTO) {
		this.datosCertificadoDTO = datosCertificadoDTO;
	}

	

	public CatGeneralDto getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(CatGeneralDto tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getUrlFactura() {
		return urlFactura;
	}

	public void setUrlFactura(String urlFactura) {
		this.urlFactura = urlFactura;
	}


	public CatGeneralDto getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(CatGeneralDto tipoFactura) {
		this.tipoFactura = tipoFactura;
	}


	public NotificacionDTO getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionDTO notificacion) {
		this.notificacion = notificacion;
	}

	public String getUrlPdf() {
		return urlPdf;
	}

	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}

	public String getNotaFactura() {
		return notaFactura;
	}

	public void setNotaFactura(String notaFactura) {
		this.notaFactura = notaFactura;
	}

	public EmpresaLogoDTO getEmpresaLogo() {
		return empresaLogo;
	}

	public void setEmpresaLogo(EmpresaLogoDTO empresaLogo) {
		this.empresaLogo = empresaLogo;
	}

	public String getGuardarCertificado() {
		return guardarCertificado;
	}

	public void setGuardarCertificado(String guardarCertificado) {
		this.guardarCertificado = guardarCertificado;
	}

	public String getFechaHoraEmision() {
		return fechaHoraEmision;
	}

	public void setFechaHoraEmision(String fechaHoraEmision) {
		this.fechaHoraEmision = fechaHoraEmision;
	}

	public List<ImpuestoFacturaDTO> getImpuestosFactura() {
		return impuestosFactura;
	}

	public void setImpuestosFactura(List<ImpuestoFacturaDTO> impuestosFactura) {
		this.impuestosFactura = impuestosFactura;
	}

	public String getMontoLetra() {
		return montoLetra;
	}

	public void setMontoLetra(String montoLetra) {
		this.montoLetra = montoLetra;
	}

	public CatGeneralDto getConceptoFacturacion() {
		return conceptoFacturacion;
	}

	public void setConceptoFacturacion(CatGeneralDto conceptoFacturacion) {
		this.conceptoFacturacion = conceptoFacturacion;
	}

	public ConceptoDTO getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoDTO concepto) {
		this.concepto = concepto;
	}

	public CatGeneralDto getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(CatGeneralDto regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public NominaDto getNomina() {
		return nomina;
	}

	public void setNomina(NominaDto nomina) {
		this.nomina = nomina;
	}

	public Long getIdPPPNominaFactura() {
		return idPPPNominaFactura;
	}

	public void setIdPPPNominaFactura(Long idPPPNominaFactura) {
		this.idPPPNominaFactura = idPPPNominaFactura;
	}

	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}

	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public CatGeneralDto getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(CatGeneralDto usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public Long getIdUsuarioAplicativo() {
		return idUsuarioAplicativo;
	}

	public void setIdUsuarioAplicativo(Long idUsuarioAplicativo) {
		this.idUsuarioAplicativo = idUsuarioAplicativo;
	}

	public Long getIdPPPNomina() {
		return idPPPNomina;
	}

	public void setIdPPPNomina(Long idPPPNomina) {
		this.idPPPNomina = idPPPNomina;
	}

	public Long getIdCMS() {
		return idCMS;
	}

	public void setIdCMS(Long idCMS) {
		this.idCMS = idCMS;
	}

	public String getNombreArchivoCarga() {
		return nombreArchivoCarga;
	}

	public void setNombreArchivoCarga(String nombreArchivoCarga) {
		this.nombreArchivoCarga = nombreArchivoCarga;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public DocumentoDTO getDocumentoNuevo() {
		return documentoNuevo;
	}

	public void setDocumentoNuevo(DocumentoDTO documentoNuevo) {
		this.documentoNuevo = documentoNuevo;
	}

	public Long getIdCmsXmlFactura() {
		return idCmsXmlFactura;
	}

	public void setIdCmsXmlFactura(Long idCmsXmlFactura) {
		this.idCmsXmlFactura = idCmsXmlFactura;
	}

	public Long getIdCmsPdfFactura() {
		return idCmsPdfFactura;
	}

	public void setIdCmsPdfFactura(Long idCmsPdfFactura) {
		this.idCmsPdfFactura = idCmsPdfFactura;
	}

	public BigDecimal getTotalIvaTrasladado16() {
		return totalIvaTrasladado16;
	}

	public void setTotalIvaTrasladado16(BigDecimal totalIvaTrasladado16) {
		this.totalIvaTrasladado16 = totalIvaTrasladado16;
	}

	public int getTotalDocumentosRegistrados() {
		return totalDocumentosRegistrados;
	}

	public void setTotalDocumentosRegistrados(int totalDocumentosRegistrados) {
		this.totalDocumentosRegistrados = totalDocumentosRegistrados;
	}
	

	public ComplementoNominaDto getComplementoNominaDto() {
		return complementoNominaDto;
	}

	public void setComplementoNominaDto(ComplementoNominaDto complementoNominaDto) {
		this.complementoNominaDto = complementoNominaDto;
	}

	public String getIdConsar() {
		return idConsar;
	}

	public void setIdConsar(String idConsar) {
		this.idConsar = idConsar;
	}

	public BigDecimal getMontoComprobantePago() {
		return montoComprobantePago;
	}

	public void setMontoComprobantePago(BigDecimal montoComprobantePago) {
		this.montoComprobantePago = montoComprobantePago;
	}


	public String getLogoPrestadoraServicioString() {
		return logoPrestadoraServicioString;
	}

	public void setLogoPrestadoraServicioString(String logoPrestadoraServicioString) {
		this.logoPrestadoraServicioString = logoPrestadoraServicioString;
	}

	public CatGeneralDto getPacTimbrado() {
		return pacTimbrado;
	}

	public void setPacTimbrado(CatGeneralDto pacTimbrado) {
		this.pacTimbrado = pacTimbrado;
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public NominaEstatusEnum getNominaEstatus() {
		return nominaEstatus;
	}

	public void setNominaEstatus(NominaEstatusEnum nominaEstatus) {
		this.nominaEstatus = nominaEstatus;
	}

	public Long getIdNominaCliente() {
		return idNominaCliente;
	}

	public void setIdNominaCliente(Long idNominaCliente) {
		this.idNominaCliente = idNominaCliente;
	}

	public Long getTotalNominas() {
		return totalNominas;
	}

	public void setTotalNominas(Long totalNominas) {
		this.totalNominas = totalNominas;
	}

	public int getIsMultifactura() {
		return isMultifactura;
	}

	public void setIsMultifactura(int isMultifactura) {
		this.isMultifactura = isMultifactura;
	}

	public String getSerieS() {
		return serieS;
	}

	public void setSerieS(String serieS) {
		this.serieS = serieS;
	}

	public List<ConceptoDTO> getConceptosPlus() {
		return conceptosPlus;
	}

	public void setConceptosPlus(List<ConceptoDTO> conceptosPlus) {
		this.conceptosPlus = conceptosPlus;
	}

	public List<NominasDTO> getListNominasVincular() {
		return listNominasVincular;
	}

	public void setListNominasVincular(List<NominasDTO> listNominasVincular) {
		this.listNominasVincular = listNominasVincular;
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public BigDecimal getMontoDeposito() {
		return montoDeposito;
	}

	public void setMontoDeposito(BigDecimal montoDeposito) {
		this.montoDeposito = montoDeposito;
	}

	public String getConsar() {
		return consar;
	}

	public void setConsar(String consar) {
		this.consar = consar;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getPercepciones() {
		return percepciones;
	}

	public void setPercepciones(Long percepciones) {
		this.percepciones = percepciones;
	}

	public String getClavePercepcion() {
		return clavePercepcion;
	}

	public void setClavePercepcion(String clavePercepcion) {
		this.clavePercepcion = clavePercepcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getEstimbreSindicato() {
		return estimbreSindicato;
	}

	public void setEstimbreSindicato(Long estimbreSindicato) {
		this.estimbreSindicato = estimbreSindicato;
	}





	

	
	
}