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
	private CelulaDto catCelula;
	private List<CelulaDto> listaCentroCostos;
	private Date fechaInicio;
	private Date fechaFin;
	private String mes;
	private String anio;


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

	public ReporteDTO () {

	}


	public CatGeneralDto getCatReporte() {
		return catReporte;
	}


	public void setCatReporte(CatGeneralDto catReporte) {
		this.catReporte = catReporte;
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


	public Long getTotalColaboradresPagados() {
		return totalColaboradresPagados;
	}


	public void setTotalColaboradresPagados(Long totalColaboradresPagados) {
		this.totalColaboradresPagados = totalColaboradresPagados;
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


	public CelulaDto getCatCelula() {
		return catCelula;
	}


	public void setCatCelula(CelulaDto catCelula) {
		this.catCelula = catCelula;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getPeriodoReporte() {
		return periodoReporte;
	}


	public void setPeriodoReporte(String periodoReporte) {
		this.periodoReporte = periodoReporte;
	}


	public CatGeneralDto getCatTipoPeriodo() {
		return catTipoPeriodo;
	}


	public void setCatTipoPeriodo(CatGeneralDto catTipoPeriodo) {
		this.catTipoPeriodo = catTipoPeriodo;
	}

	public String getPeriodoReporteMesAnterior() {
		return periodoReporteMesAnterior;
	}


	public void setPeriodoReporteMesAnterior(String periodoReporteMesAnterior) {
		this.periodoReporteMesAnterior = periodoReporteMesAnterior;
	}


	public Long getTotalColaboradresPagadosMesAnterior() {
		return totalColaboradresPagadosMesAnterior;
	}


	public void setTotalColaboradresPagadosMesAnterior(Long totalColaboradresPagadosMesAnterior) {
		this.totalColaboradresPagadosMesAnterior = totalColaboradresPagadosMesAnterior;
	}


	public BigDecimal getMontoTotalFacturaPppMesAnterior() {
		return montoTotalFacturaPppMesAnterior;
	}


	public void setMontoTotalFacturaPppMesAnterior(BigDecimal montoTotalFacturaPppMesAnterior) {
		this.montoTotalFacturaPppMesAnterior = montoTotalFacturaPppMesAnterior;
	}


	public String getNombreMesReporteAnterior() {
		return nombreMesReporteAnterior;
	}


	public void setNombreMesReporteAnterior(String nombreMesReporteAnterior) {
		this.nombreMesReporteAnterior = nombreMesReporteAnterior;
	}


	public List<CelulaDto> getListaCentroCostos() {
		return listaCentroCostos;
	}


	public void setListaCentroCostos(List<CelulaDto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
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


	public Date getFecha_facturad() {
		return fecha_facturad;
	}


	public void setFecha_facturad(Date fecha_facturad) {
		this.fecha_facturad = fecha_facturad;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public BigDecimal getMontoSubTotalFactura() {
		return montoSubTotalFactura;
	}


	public void setMontoSubTotalFactura(BigDecimal montoSubTotalFactura) {
		this.montoSubTotalFactura = montoSubTotalFactura;
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




}
