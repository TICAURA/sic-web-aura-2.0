package mx.com.consolida.conciliaciones;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import mx.com.consolida.crm.dto.CelulaDto;

public class OrdenPagoDto {
	
	private long idOrdenPago;
	private long idOrdenOrigen;
	private long folioOrden;
	private String institucion;
	private String conceptoPago;
	private String beneficiario;
	private String ctaBeneficiario;
	private String contraparte;
	private BigDecimal monto;
	private String rastreo;
	private String ordenante;
	private String ctaOrdenante;
	private Date fechaOperacion;
	private long folioOrdenCep;
	private String nombreClienteCep;
	private Date fechaCaptura;
	private Date fechaLiquidacion;
	private Time tiempoLiquidacion;
	private String causaDevolucion;
	private int estatus ;
	private String  descError;
	private String AnioMesDia;
	private String rfcOrdenante;
	private Double costo;
	private Double costoPromedio;
	private Date fechaInicio;
	private Date fechaFin;
	private String fechaInicioS;
	private String fechaFinS;
	private CelulaDto catCelula;
	private List<CelulaDto> listaCentroCostos;
	
	public long getIdOrdenPago() {
		return idOrdenPago;
	}
	public void setIdOrdenPago(long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}
	public long getIdOrdenOrigen() {
		return idOrdenOrigen;
	}
	public void setIdOrdenOrigen(long idOrdenOrigen) {
		this.idOrdenOrigen = idOrdenOrigen;
	}
	public long getFolioOrden() {
		return folioOrden;
	}
	public void setFolioOrden(long folioOrden) {
		this.folioOrden = folioOrden;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public String getConceptoPago() {
		return conceptoPago;
	}
	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
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
	public String getOrdenante() {
		return ordenante;
	}
	public void setOrdenante(String ordenante) {
		this.ordenante = ordenante;
	}
	public String getCtaOrdenante() {
		return ctaOrdenante;
	}
	public void setCtaOrdenante(String ctaOrdenante) {
		this.ctaOrdenante = ctaOrdenante;
	}
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public long getFolioOrdenCep() {
		return folioOrdenCep;
	}
	public void setFolioOrdenCep(long folioOrdenCep) {
		this.folioOrdenCep = folioOrdenCep;
	}
	public String getNombreClienteCep() {
		return nombreClienteCep;
	}
	public void setNombreClienteCep(String nombreClienteCep) {
		this.nombreClienteCep = nombreClienteCep;
	}
	public Date getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	public Time getTiempoLiquidacion() {
		return tiempoLiquidacion;
	}
	public void setTiempoLiquidacion(Time tiempoLiquidacion) {
		this.tiempoLiquidacion = tiempoLiquidacion;
	}
	public String getCausaDevolucion() {
		return causaDevolucion;
	}
	public void setCausaDevolucion(String causaDevolucion) {
		this.causaDevolucion = causaDevolucion;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public String getAnioMesDia() {
		return AnioMesDia;
	}
	public void setAnioMesDia(String anioMesDia) {
		AnioMesDia = anioMesDia;
	}
	public String getRfcOrdenante() {
		return rfcOrdenante;
	}
	public void setRfcOrdenante(String rfcOrdenante) {
		this.rfcOrdenante = rfcOrdenante;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public Double getCostoPromedio() {
		return costoPromedio;
	}
	public void setCostoPromedio(Double costoPromedio) {
		this.costoPromedio = costoPromedio;
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
	public String getFechaInicioS() {
		return fechaInicioS;
	}
	public void setFechaInicioS(String fechaInicioS) {
		this.fechaInicioS = fechaInicioS;
	}
	public String getFechaFinS() {
		return fechaFinS;
	}
	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
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
	
	
	
	
	
	
}
