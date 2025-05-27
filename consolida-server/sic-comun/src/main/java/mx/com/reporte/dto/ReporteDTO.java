package mx.com.reporte.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.CelulaDto;

public class ReporteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	// consulta de reporte
	private CatGeneralDto catReporte;
	private CatGeneralDto catTipoPeriodo;
	private CatGeneralDto catListaProductos;
	private CelulaDto catCelula;
	private List<CelulaDto> listaCentroCostos;
	private Date fechaInicio;
	private Date fechaFin;
	private String mes;
	private String anio;
	private String claveProducto;
	private Integer totalPPP;
	private Integer totalSueldosYSalarios;
	private Integer totalMaquila;
	private Integer totalMixtoPPP;
	private Integer totalIrlab;
	private Integer totalQamm;
	
	// datos para imprimir
	private String descripcionCelula;
	
	private String rfc;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nomina;
	private String estatusColaborador;
	
	private String razonSocial;
	private String nombreComercialCliente;
	private String nombreFondo;
	private BigDecimal facturacionPeriodoAnterior;
	private BigDecimal facturacionPromedio;
	private String nombreEjecutivoNomina;
	private String porcentaje;
	private String numeroFacturaPpp;
	private String folio;
	private String serie;
	private String uuid;
	private String tipoComprobante;
	private Long totalColaboradores;
	private Long totalColaboradresPagados;
	private Long totalColaboradresPagadosMesAnterior;
	private BigDecimal montoDispersado;
	private BigDecimal montoHonorario;
	private BigDecimal montoSubTotalFactura;
	private BigDecimal montoIvaFactura;
	private BigDecimal montoTotalFacturaPpp;
	private BigDecimal montoTotalFacturaPppMesAnterior;
	private String periodoNomina;
	private String nominaOperativa;
	private String oficinaPromotor;
	private String giroComercial;
	
	private String actividadEconomica;
	private String periodoReporte;
	private String periodoReporteMesAnterior;
	private String nombreMesReporteAnterior;
	private BigDecimal monto_ppp;
	private String clabe_interbancaria;
	private String curp;
	private String num_seguro_social;
	private String institucion_contraparte;
	private String correo_electronico;
	private String fecha_operacion;
	private Date fecha_facturad;
	private String fecha_factura;
	private String fecha_timbrado;
	private String fecha_dispersado;
	private String metodoPago;
	private String formaPago;
	private String tipoNomina;
	private String claveNominaPadre;
	private String sexo;
	private String fecha_nacimiento;
	private Long edad;
	private Long aniosInscImss;
	private String modalidadImss;
	private BigDecimal q1;
	private BigDecimal q2;
	private BigDecimal q3;
	private BigDecimal q4;
	private BigDecimal q5;
	private BigDecimal q6;
	private BigDecimal q7;
	private BigDecimal q8;
	private BigDecimal q9;
	private BigDecimal q10;
	private BigDecimal q11;
	private BigDecimal q12;
	private BigDecimal q13;
	private BigDecimal q14;
	private BigDecimal q15;
	private BigDecimal q16;
	private BigDecimal q17;
	private BigDecimal q18;
	private BigDecimal q19;
	private BigDecimal q20;
	private BigDecimal q21;
	private BigDecimal q22;
	private BigDecimal q23;
	private BigDecimal q24;
	private BigDecimal total;
	
	/*Reporte productos*/
	private String ppp;
	private String prodMix;   
	private String  prodMaqu;  
	private String  prodMixIms;
	private String prodIrlab;
	private String prodQamm;   
	
	/*Reporte facturas**/
	
	private String descripcionRazonSocial;
	private String claveNomina;
	private String certificadoEmisor;
	private String fechaHoraCertificacion;
	private String subTotal;
	private String totalIvaTrasladado;
	private String idCms;
	private String archivo;
	private String idNomina;
	
	/*Reporte dispersion*/
	private String idColaborador;
	private String idStp;
	private String fechaDispersion;
	private String clabeInterbancaria;
	private String idCliente;
	
	/*Reporte Colaboradores */
	private String genero;
	private String anioImss;
	private String fechaNacimiento;
	private String domicilio;
	private String codigoPostal;
	
	/*Reporte Clientes*/
	private String tipoPersona;
	private String razonSocialCelula;
	private String dispersor;
	private String regimenFiscal;
	private String tipoDomicilio;
	private String calle;
	private String numInt;
	private String numExt;
	private String colonia;
	private String municipio;
	private String idEntidadFederativa;
	private String correo;
	private String telefono;
	private String producto;
	private String nombreNomina;
	private String periodicidadNomina;
	private String esquemaNomina;
	private String nominista;
	private String factura;
	private String estatusCliente;
	private String comision;
	private String canalVenta;
	private String comisionista;
	private String formulaComision;
	private String honorario;
	private String formulaHonorario;
	private String formulaIva;
	private String formulaFactura;
	private String banco;
	private String noCuentaBanco;
	private String clabe;
	private String funcionCuenta;
	private String noReferencia;
	private String esPrincipal;
	
	//reporte teso-operacion
	private String tipoGeneracion;
	private String fondo;
//	private String razonSocial;
//	private String rfc;
//	private String uuid;
	private BigDecimal subtotal;
	private BigDecimal iva;
//	private BigDecimal total;
	private String fechaAlta ;
	private String fechaCertificacion;
	private String usuarioFactura;
//	private Long idStp;
	private String beneficiario;
	private String ctaBeneficiario;
	private String contraparte;
	private BigDecimal monto;
	private String rastreo;
	private String fechaOperacion;
	private String fechaLiquidacion;
	//private String nomina;
	private String fechaRegistroNomina;
	private Long colaboradores;
	private BigDecimal montoOperacion;
	//private BigDecimal montoDispersado;
	private BigDecimal montoRechazos;
	private String usuarioDispercion;
	//private String fechaDispersion;
	private Long timbres;
	private String fechaTimbrado;
	private BigDecimal diferenciaDepositoDispersion;
	private String fechaCierreNomina;
		
	
	public ReporteDTO () {
		
	}

	public CatGeneralDto getCatReporte() {
		return catReporte;
	}

	public void setCatReporte(CatGeneralDto catReporte) {
		this.catReporte = catReporte;
	}

	public CatGeneralDto getCatTipoPeriodo() {
		return catTipoPeriodo;
	}

	public void setCatTipoPeriodo(CatGeneralDto catTipoPeriodo) {
		this.catTipoPeriodo = catTipoPeriodo;
	}

	public CatGeneralDto getCatListaProductos() {
		return catListaProductos;
	}

	public void setCatListaProductos(CatGeneralDto catListaProductos) {
		this.catListaProductos = catListaProductos;
	}

	public CelulaDto getCatCelula() {
		return catCelula;
	}

	public void setCatCelula(CelulaDto catCelula) {
		this.catCelula = catCelula;
	}

	public List<CelulaDto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CelulaDto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getClaveProducto() {
		return claveProducto;
	}

	public void setClaveProducto(String claveProducto) {
		this.claveProducto = claveProducto;
	}

	public Integer getTotalPPP() {
		return totalPPP;
	}

	public void setTotalPPP(Integer totalPPP) {
		this.totalPPP = totalPPP;
	}

	public Integer getTotalSueldosYSalarios() {
		return totalSueldosYSalarios;
	}

	public void setTotalSueldosYSalarios(Integer totalSueldosYSalarios) {
		this.totalSueldosYSalarios = totalSueldosYSalarios;
	}

	public Integer getTotalMaquila() {
		return totalMaquila;
	}

	public void setTotalMaquila(Integer totalMaquila) {
		this.totalMaquila = totalMaquila;
	}

	public Integer getTotalMixtoPPP() {
		return totalMixtoPPP;
	}

	public void setTotalMixtoPPP(Integer totalMixtoPPP) {
		this.totalMixtoPPP = totalMixtoPPP;
	}

	public Integer getTotalIrlab() {
		return totalIrlab;
	}

	public void setTotalIrlab(Integer totalIrlab) {
		this.totalIrlab = totalIrlab;
	}

	public Integer getTotalQamm() {
		return totalQamm;
	}

	public void setTotalQamm(Integer totalQamm) {
		this.totalQamm = totalQamm;
	}

	public String getDescripcionCelula() {
		return descripcionCelula;
	}

	public void setDescripcionCelula(String descripcionCelula) {
		this.descripcionCelula = descripcionCelula;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNomina() {
		return nomina;
	}

	public void setNomina(String nomina) {
		this.nomina = nomina;
	}

	public String getEstatusColaborador() {
		return estatusColaborador;
	}

	public void setEstatusColaborador(String estatusColaborador) {
		this.estatusColaborador = estatusColaborador;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercialCliente() {
		return nombreComercialCliente;
	}

	public void setNombreComercialCliente(String nombreComercialCliente) {
		this.nombreComercialCliente = nombreComercialCliente;
	}

	public String getNombreFondo() {
		return nombreFondo;
	}

	public void setNombreFondo(String nombreFondo) {
		this.nombreFondo = nombreFondo;
	}

	public BigDecimal getFacturacionPeriodoAnterior() {
		return facturacionPeriodoAnterior;
	}

	public void setFacturacionPeriodoAnterior(BigDecimal facturacionPeriodoAnterior) {
		this.facturacionPeriodoAnterior = facturacionPeriodoAnterior;
	}

	public BigDecimal getFacturacionPromedio() {
		return facturacionPromedio;
	}

	public void setFacturacionPromedio(BigDecimal facturacionPromedio) {
		this.facturacionPromedio = facturacionPromedio;
	}

	public String getNombreEjecutivoNomina() {
		return nombreEjecutivoNomina;
	}

	public void setNombreEjecutivoNomina(String nombreEjecutivoNomina) {
		this.nombreEjecutivoNomina = nombreEjecutivoNomina;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getNumeroFacturaPpp() {
		return numeroFacturaPpp;
	}

	public void setNumeroFacturaPpp(String numeroFacturaPpp) {
		this.numeroFacturaPpp = numeroFacturaPpp;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public Long getTotalColaboradores() {
		return totalColaboradores;
	}

	public void setTotalColaboradores(Long totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}

	public Long getTotalColaboradresPagados() {
		return totalColaboradresPagados;
	}

	public void setTotalColaboradresPagados(Long totalColaboradresPagados) {
		this.totalColaboradresPagados = totalColaboradresPagados;
	}

	public Long getTotalColaboradresPagadosMesAnterior() {
		return totalColaboradresPagadosMesAnterior;
	}

	public void setTotalColaboradresPagadosMesAnterior(Long totalColaboradresPagadosMesAnterior) {
		this.totalColaboradresPagadosMesAnterior = totalColaboradresPagadosMesAnterior;
	}

	public BigDecimal getMontoDispersado() {
		return montoDispersado;
	}

	public void setMontoDispersado(BigDecimal montoDispersado) {
		this.montoDispersado = montoDispersado;
	}

	public BigDecimal getMontoHonorario() {
		return montoHonorario;
	}

	public void setMontoHonorario(BigDecimal montoHonorario) {
		this.montoHonorario = montoHonorario;
	}

	public BigDecimal getMontoSubTotalFactura() {
		return montoSubTotalFactura;
	}

	public void setMontoSubTotalFactura(BigDecimal montoSubTotalFactura) {
		this.montoSubTotalFactura = montoSubTotalFactura;
	}

	public BigDecimal getMontoIvaFactura() {
		return montoIvaFactura;
	}

	public void setMontoIvaFactura(BigDecimal montoIvaFactura) {
		this.montoIvaFactura = montoIvaFactura;
	}

	public BigDecimal getMontoTotalFacturaPpp() {
		return montoTotalFacturaPpp;
	}

	public void setMontoTotalFacturaPpp(BigDecimal montoTotalFacturaPpp) {
		this.montoTotalFacturaPpp = montoTotalFacturaPpp;
	}

	public BigDecimal getMontoTotalFacturaPppMesAnterior() {
		return montoTotalFacturaPppMesAnterior;
	}

	public void setMontoTotalFacturaPppMesAnterior(BigDecimal montoTotalFacturaPppMesAnterior) {
		this.montoTotalFacturaPppMesAnterior = montoTotalFacturaPppMesAnterior;
	}

	public String getPeriodoNomina() {
		return periodoNomina;
	}

	public void setPeriodoNomina(String periodoNomina) {
		this.periodoNomina = periodoNomina;
	}

	public String getNominaOperativa() {
		return nominaOperativa;
	}

	public void setNominaOperativa(String nominaOperativa) {
		this.nominaOperativa = nominaOperativa;
	}

	public String getOficinaPromotor() {
		return oficinaPromotor;
	}

	public void setOficinaPromotor(String oficinaPromotor) {
		this.oficinaPromotor = oficinaPromotor;
	}

	public String getGiroComercial() {
		return giroComercial;
	}

	public void setGiroComercial(String giroComercial) {
		this.giroComercial = giroComercial;
	}

	public String getActividadEconomica() {
		return actividadEconomica;
	}

	public void setActividadEconomica(String actividadEconomica) {
		this.actividadEconomica = actividadEconomica;
	}

	public String getPeriodoReporte() {
		return periodoReporte;
	}

	public void setPeriodoReporte(String periodoReporte) {
		this.periodoReporte = periodoReporte;
	}

	public String getPeriodoReporteMesAnterior() {
		return periodoReporteMesAnterior;
	}

	public void setPeriodoReporteMesAnterior(String periodoReporteMesAnterior) {
		this.periodoReporteMesAnterior = periodoReporteMesAnterior;
	}

	public String getNombreMesReporteAnterior() {
		return nombreMesReporteAnterior;
	}

	public void setNombreMesReporteAnterior(String nombreMesReporteAnterior) {
		this.nombreMesReporteAnterior = nombreMesReporteAnterior;
	}

	public BigDecimal getMonto_ppp() {
		return monto_ppp;
	}

	public void setMonto_ppp(BigDecimal monto_ppp) {
		this.monto_ppp = monto_ppp;
	}

	public String getClabe_interbancaria() {
		return clabe_interbancaria;
	}

	public void setClabe_interbancaria(String clabe_interbancaria) {
		this.clabe_interbancaria = clabe_interbancaria;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNum_seguro_social() {
		return num_seguro_social;
	}

	public void setNum_seguro_social(String num_seguro_social) {
		this.num_seguro_social = num_seguro_social;
	}

	public String getInstitucion_contraparte() {
		return institucion_contraparte;
	}

	public void setInstitucion_contraparte(String institucion_contraparte) {
		this.institucion_contraparte = institucion_contraparte;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public String getFecha_operacion() {
		return fecha_operacion;
	}

	public void setFecha_operacion(String fecha_operacion) {
		this.fecha_operacion = fecha_operacion;
	}

	public Date getFecha_facturad() {
		return fecha_facturad;
	}

	public void setFecha_facturad(Date fecha_facturad) {
		this.fecha_facturad = fecha_facturad;
	}

	public String getFecha_factura() {
		return fecha_factura;
	}

	public void setFecha_factura(String fecha_factura) {
		this.fecha_factura = fecha_factura;
	}

	public String getFecha_timbrado() {
		return fecha_timbrado;
	}

	public void setFecha_timbrado(String fecha_timbrado) {
		this.fecha_timbrado = fecha_timbrado;
	}

	public String getFecha_dispersado() {
		return fecha_dispersado;
	}

	public void setFecha_dispersado(String fecha_dispersado) {
		this.fecha_dispersado = fecha_dispersado;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoNomina() {
		return tipoNomina;
	}

	public void setTipoNomina(String tipoNomina) {
		this.tipoNomina = tipoNomina;
	}

	public String getClaveNominaPadre() {
		return claveNominaPadre;
	}

	public void setClaveNominaPadre(String claveNominaPadre) {
		this.claveNominaPadre = claveNominaPadre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public Long getEdad() {
		return edad;
	}

	public void setEdad(Long edad) {
		this.edad = edad;
	}

	public Long getAniosInscImss() {
		return aniosInscImss;
	}

	public void setAniosInscImss(Long aniosInscImss) {
		this.aniosInscImss = aniosInscImss;
	}

	public String getModalidadImss() {
		return modalidadImss;
	}

	public void setModalidadImss(String modalidadImss) {
		this.modalidadImss = modalidadImss;
	}

	public BigDecimal getQ1() {
		return q1;
	}

	public void setQ1(BigDecimal q1) {
		this.q1 = q1;
	}

	public BigDecimal getQ2() {
		return q2;
	}

	public void setQ2(BigDecimal q2) {
		this.q2 = q2;
	}

	public BigDecimal getQ3() {
		return q3;
	}

	public void setQ3(BigDecimal q3) {
		this.q3 = q3;
	}

	public BigDecimal getQ4() {
		return q4;
	}

	public void setQ4(BigDecimal q4) {
		this.q4 = q4;
	}

	public BigDecimal getQ5() {
		return q5;
	}

	public void setQ5(BigDecimal q5) {
		this.q5 = q5;
	}

	public BigDecimal getQ6() {
		return q6;
	}

	public void setQ6(BigDecimal q6) {
		this.q6 = q6;
	}

	public BigDecimal getQ7() {
		return q7;
	}

	public void setQ7(BigDecimal q7) {
		this.q7 = q7;
	}

	public BigDecimal getQ8() {
		return q8;
	}

	public void setQ8(BigDecimal q8) {
		this.q8 = q8;
	}

	public BigDecimal getQ9() {
		return q9;
	}

	public void setQ9(BigDecimal q9) {
		this.q9 = q9;
	}

	public BigDecimal getQ10() {
		return q10;
	}

	public void setQ10(BigDecimal q10) {
		this.q10 = q10;
	}

	public BigDecimal getQ11() {
		return q11;
	}

	public void setQ11(BigDecimal q11) {
		this.q11 = q11;
	}

	public BigDecimal getQ12() {
		return q12;
	}

	public void setQ12(BigDecimal q12) {
		this.q12 = q12;
	}

	public BigDecimal getQ13() {
		return q13;
	}

	public void setQ13(BigDecimal q13) {
		this.q13 = q13;
	}

	public BigDecimal getQ14() {
		return q14;
	}

	public void setQ14(BigDecimal q14) {
		this.q14 = q14;
	}

	public BigDecimal getQ15() {
		return q15;
	}

	public void setQ15(BigDecimal q15) {
		this.q15 = q15;
	}

	public BigDecimal getQ16() {
		return q16;
	}

	public void setQ16(BigDecimal q16) {
		this.q16 = q16;
	}

	public BigDecimal getQ17() {
		return q17;
	}

	public void setQ17(BigDecimal q17) {
		this.q17 = q17;
	}

	public BigDecimal getQ18() {
		return q18;
	}

	public void setQ18(BigDecimal q18) {
		this.q18 = q18;
	}

	public BigDecimal getQ19() {
		return q19;
	}

	public void setQ19(BigDecimal q19) {
		this.q19 = q19;
	}

	public BigDecimal getQ20() {
		return q20;
	}

	public void setQ20(BigDecimal q20) {
		this.q20 = q20;
	}

	public BigDecimal getQ21() {
		return q21;
	}

	public void setQ21(BigDecimal q21) {
		this.q21 = q21;
	}

	public BigDecimal getQ22() {
		return q22;
	}

	public void setQ22(BigDecimal q22) {
		this.q22 = q22;
	}

	public BigDecimal getQ23() {
		return q23;
	}

	public void setQ23(BigDecimal q23) {
		this.q23 = q23;
	}

	public BigDecimal getQ24() {
		return q24;
	}

	public void setQ24(BigDecimal q24) {
		this.q24 = q24;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getPpp() {
		return ppp;
	}

	public void setPpp(String ppp) {
		this.ppp = ppp;
	}

	public String getProdMix() {
		return prodMix;
	}

	public void setProdMix(String prodMix) {
		this.prodMix = prodMix;
	}

	public String getProdMaqu() {
		return prodMaqu;
	}

	public void setProdMaqu(String prodMaqu) {
		this.prodMaqu = prodMaqu;
	}

	public String getProdMixIms() {
		return prodMixIms;
	}

	public void setProdMixIms(String prodMixIms) {
		this.prodMixIms = prodMixIms;
	}

	public String getProdIrlab() {
		return prodIrlab;
	}

	public void setProdIrlab(String prodIrlab) {
		this.prodIrlab = prodIrlab;
	}

	public String getProdQamm() {
		return prodQamm;
	}

	public void setProdQamm(String prodQamm) {
		this.prodQamm = prodQamm;
	}

	public String getDescripcionRazonSocial() {
		return descripcionRazonSocial;
	}

	public void setDescripcionRazonSocial(String descripcionRazonSocial) {
		this.descripcionRazonSocial = descripcionRazonSocial;
	}

	public String getCertificadoEmisor() {
		return certificadoEmisor;
	}

	public void setCertificadoEmisor(String certificadoEmisor) {
		this.certificadoEmisor = certificadoEmisor;
	}

	public String getFechaHoraCertificacion() {
		return fechaHoraCertificacion;
	}

	public void setFechaHoraCertificacion(String fechaHoraCertificacion) {
		this.fechaHoraCertificacion = fechaHoraCertificacion;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getTotalIvaTrasladado() {
		return totalIvaTrasladado;
	}

	public void setTotalIvaTrasladado(String totalIvaTrasladado) {
		this.totalIvaTrasladado = totalIvaTrasladado;
	}

	public String getClaveNomina() {
		return claveNomina;
	}

	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
	}

	public String getIdCms() {
		return idCms;
	}

	public void setIdCms(String idCms) {
		this.idCms = idCms;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getIdNomina() {
		return idNomina;
	}

	public void setIdNomina(String idNomina) {
		this.idNomina = idNomina;
	}

	public String getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(String idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getIdStp() {
		return idStp;
	}

	public void setIdStp(String idStp) {
		this.idStp = idStp;
	}

	public String getFechaDispersion() {
		return fechaDispersion;
	}

	public void setFechaDispersion(String fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}

	public String getClabeInterbancaria() {
		return clabeInterbancaria;
	}

	public void setClabeInterbancaria(String clabeInterbancaria) {
		this.clabeInterbancaria = clabeInterbancaria;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAnioImss() {
		return anioImss;
	}

	public void setAnioImss(String anioImms) {
		this.anioImss = anioImms;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getRazonSocialCelula() {
		return razonSocialCelula;
	}

	public void setRazonSocialCelula(String razonSocialCelula) {
		this.razonSocialCelula = razonSocialCelula;
	}

	public String getDispersor() {
		return dispersor;
	}

	public void setDispersor(String dispersor) {
		this.dispersor = dispersor;
	}

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public String getTipoDomicilio() {
		return tipoDomicilio;
	}

	public void setTipoDomicilio(String tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getNombreNomina() {
		return nombreNomina;
	}

	public void setNombreNomina(String nombreNomina) {
		this.nombreNomina = nombreNomina;
	}

	public String getPeriodicidadNomina() {
		return periodicidadNomina;
	}

	public void setPeriodicidadNomina(String periodicidadNomina) {
		this.periodicidadNomina = periodicidadNomina;
	}

	public String getEsquemaNomina() {
		return esquemaNomina;
	}

	public void setEsquemaNomina(String esquemaNomina) {
		this.esquemaNomina = esquemaNomina;
	}

	public String getNominista() {
		return nominista;
	}

	public void setNominista(String nominista) {
		this.nominista = nominista;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getEstatusCliente() {
		return estatusCliente;
	}

	public void setEstatusCliente(String estatusCliente) {
		this.estatusCliente = estatusCliente;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public String getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(String canalVenta) {
		this.canalVenta = canalVenta;
	}

	public String getComisionista() {
		return comisionista;
	}

	public void setComisionista(String comisionista) {
		this.comisionista = comisionista;
	}

	public String getFormulaComision() {
		return formulaComision;
	}

	public void setFormulaComision(String formulaComision) {
		this.formulaComision = formulaComision;
	}

	public String getHonorario() {
		return honorario;
	}

	public void setHonorario(String honorario) {
		this.honorario = honorario;
	}

	public String getFormulaHonorario() {
		return formulaHonorario;
	}

	public void setFormulaHonorario(String formulaHonorario) {
		this.formulaHonorario = formulaHonorario;
	}

	public String getFormulaIva() {
		return formulaIva;
	}

	public void setFormulaIva(String formulaIva) {
		this.formulaIva = formulaIva;
	}

	public String getFormulaFactura() {
		return formulaFactura;
	}

	public void setFormulaFactura(String formulaFactura) {
		this.formulaFactura = formulaFactura;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getNoCuentaBanco() {
		return noCuentaBanco;
	}

	public void setNoCuentaBanco(String noCuentaBanco) {
		this.noCuentaBanco = noCuentaBanco;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public String getFuncionCuenta() {
		return funcionCuenta;
	}

	public void setFuncionCuenta(String funcionCuenta) {
		this.funcionCuenta = funcionCuenta;
	}

	public String getNoReferencia() {
		return noReferencia;
	}

	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}

	public String getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(String esPrincipal) {
		this.esPrincipal = esPrincipal;
	}

	public String getTipoGeneracion() {
		return tipoGeneracion;
	}

	public void setTipoGeneracion(String tipoGeneracion) {
		this.tipoGeneracion = tipoGeneracion;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaCertificacion() {
		return fechaCertificacion;
	}

	public void setFechaCertificacion(String fechaCertificacion) {
		this.fechaCertificacion = fechaCertificacion;
	}

	public String getUsuarioFactura() {
		return usuarioFactura;
	}

	public void setUsuarioFactura(String usuarioFactura) {
		this.usuarioFactura = usuarioFactura;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getCtaBeneficiario() {
		return ctaBeneficiario;
	}

	public void setCtaBeneficiario(String ctaBeneficiario) {
		this.ctaBeneficiario = ctaBeneficiario;
	}

	public String getContraparte() {
		return contraparte;
	}

	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getRastreo() {
		return rastreo;
	}

	public void setRastreo(String rastreo) {
		this.rastreo = rastreo;
	}

	public String getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(String fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getFechaRegistroNomina() {
		return fechaRegistroNomina;
	}

	public void setFechaRegistroNomina(String fechaRegistroNomina) {
		this.fechaRegistroNomina = fechaRegistroNomina;
	}

	public Long getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(Long colaboradores) {
		this.colaboradores = colaboradores;
	}

	public BigDecimal getMontoOperacion() {
		return montoOperacion;
	}

	public void setMontoOperacion(BigDecimal montoOperacion) {
		this.montoOperacion = montoOperacion;
	}

	public BigDecimal getMontoRechazos() {
		return montoRechazos;
	}

	public void setMontoRechazos(BigDecimal montoRechazos) {
		this.montoRechazos = montoRechazos;
	}

	public String getUsuarioDispercion() {
		return usuarioDispercion;
	}

	public void setUsuarioDispercion(String usuarioDispercion) {
		this.usuarioDispercion = usuarioDispercion;
	}

	public Long getTimbres() {
		return timbres;
	}

	public void setTimbres(Long timbres) {
		this.timbres = timbres;
	}

	public String getFechaTimbrado() {
		return fechaTimbrado;
	}

	public void setFechaTimbrado(String fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	public BigDecimal getDiferenciaDepositoDispersion() {
		return diferenciaDepositoDispersion;
	}

	public void setDiferenciaDepositoDispersion(BigDecimal diferenciaDepositoDispersion) {
		this.diferenciaDepositoDispersion = diferenciaDepositoDispersion;
	}

	public String getFechaCierreNomina() {
		return fechaCierreNomina;
	}

	public void setFechaCierreNomina(String fechaCierreNomina) {
		this.fechaCierreNomina = fechaCierreNomina;
	}
	
	

	

}
