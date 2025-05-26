package mx.com.consolida;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dto.CotizacionDto;

public class EmpleadoDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String nombre;
	private double edad;
	private String rfc;
	private BigDecimal salario;
	private BigDecimal salarioDiario;
	private BigDecimal gravados;
	private BigDecimal exentos;
	private Date fechaAntiguedad;
	private BigDecimal netoNomina;
	private BigDecimal asimilados;
	private BigDecimal aOtros;
	private String dgPrimaDeRiesgo;
	private CotizacionDto idCotizacion;
	private List<EmpleadoDTO> colaboradoresGuardados;
	private CatGeneralDto estatusColaborador;
	private BigDecimal soloPpp;

	//Variables para nomina
	private String apellidoPaterno;
	private String apellidoMaterno;
	private BigDecimal montoPPP;
	private BigDecimal numeroCuenta;
	private int estatus;
	private String descEstatus;
	private String descError;
	private Double totalMontoPPP;
	private int totalColaboradores;
	private Long idNomina;
	private Long idPppColaborador;
	private Long idEstatus;
	private String clabe;
	private String cveOrdenPago;
	private Integer idStp;
	private String descripcionErrorStp;
	private  Date fechaDispersion;
	private String curp;
	private String nombreCentroCostos;
	private Long idCmsExcel;
	private Long idCmsPdf;
	private Long idPpppColaboradorStp;
	private String nss;
	private String correoElectronico;
	private String institucionContraparte;
	private Boolean isEditarColaborador;
	private Long idPppColaboradorClabeInterbancaria;
	private String urlActual;
	private String clabeCuentaOrdenante;

	private String estado;
	private String causaDevolucion;


	public EmpleadoDTO() {
		super();
		salario = new BigDecimal(0);
		salarioDiario= new BigDecimal(0);
		gravados = new BigDecimal(0);
		exentos= new BigDecimal(0);
		netoNomina = new BigDecimal(0);
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getEdad() {
		return edad;
	}
	public void setEdad(double edad) {
		this.edad = edad;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	public BigDecimal getSalarioDiario() {
		return salarioDiario;
	}
	public void setSalarioDiario(BigDecimal salarioDiario) {
		this.salarioDiario = salarioDiario;
	}
	public BigDecimal getGravados() {
		return gravados;
	}
	public void setGravados(BigDecimal gravados) {
		this.gravados = gravados;
	}
	public BigDecimal getExentos() {
		return exentos;
	}
	public void setExentos(BigDecimal exentos) {
		this.exentos = exentos;
	}
	public Date getFechaAntiguedad() {
		return fechaAntiguedad;
	}
	public void setFechaAntiguedad(Date fechaAntiguedad) {
		this.fechaAntiguedad = fechaAntiguedad;
	}
	public BigDecimal getNetoNomina() {
		return netoNomina;
	}
	public void setNetoNomina(BigDecimal netoNomina) {
		this.netoNomina = netoNomina;
	}

	public BigDecimal getAsimilados() {
		return asimilados;
	}
	public void setAsimilados(BigDecimal asimilados) {
		this.asimilados = asimilados;
	}
	public BigDecimal getaOtros() {
		return aOtros;
	}
	public void setaOtros(BigDecimal aOtros) {
		this.aOtros = aOtros;
	}

	public BigDecimal getSoloPpp() {
		return soloPpp;
	}
	public void setSoloPpp(BigDecimal soloPpp) {
		this.soloPpp = soloPpp;
	}







	public CotizacionDto getIdCotizacion() {
		return idCotizacion;
	}
	public void setIdCotizacion(CotizacionDto idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	public String getDgPrimaDeRiesgo() {
		return dgPrimaDeRiesgo;
	}
	public void setDgPrimaDeRiesgo(String dgPrimaDeRiesgo) {
		this.dgPrimaDeRiesgo = dgPrimaDeRiesgo;
	}
	public List<EmpleadoDTO> getColaboradoresGuardados() {
		return colaboradoresGuardados;
	}
	public void setColaboradoresGuardados(List<EmpleadoDTO> colaboradoresGuardados) {
		this.colaboradoresGuardados = colaboradoresGuardados;
	}
	public BigDecimal getMontoPPP() {
		return montoPPP;
	}
	public void setMontoPPP(BigDecimal montoPPP) {
		this.montoPPP = montoPPP;
	}
	public BigDecimal getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(BigDecimal numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public Double getTotalMontoPPP() {
		return totalMontoPPP;
	}
	public void setTotalMontoPPP(Double totalMontoPPP) {
		this.totalMontoPPP = totalMontoPPP;
	}
	public int getTotalColaboradores() {
		return totalColaboradores;
	}
	public void setTotalColaboradores(int totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}
	public Long getIdNomina() {
		return idNomina;
	}
	public void setIdNomina(Long idNomina) {
		this.idNomina = idNomina;
	}
	public String getDescEstatus() {
		return descEstatus;
	}
	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}
	public Long getIdPppColaborador() {
		return idPppColaborador;
	}
	public void setIdPppColaborador(Long idPppColaborador) {
		this.idPppColaborador = idPppColaborador;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public Long getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public String getCveOrdenPago() {
		return cveOrdenPago;
	}
	public void setCveOrdenPago(String cveOrdenPago) {
		this.cveOrdenPago = cveOrdenPago;
	}
	public CatGeneralDto getEstatusColaborador() {
		return estatusColaborador;
	}
	public void setEstatusColaborador(CatGeneralDto estatusColaborador) {
		this.estatusColaborador = estatusColaborador;
	}
	public Integer getIdStp() {
		return idStp;
	}
	public void setIdStp(Integer idStp) {
		this.idStp = idStp;
	}
	public String getDescripcionErrorStp() {
		return descripcionErrorStp;
	}
	public void setDescripcionErrorStp(String descripcionErrorStp) {
		this.descripcionErrorStp = descripcionErrorStp;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
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
	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}
	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}
	public Long getIdCmsExcel() {
		return idCmsExcel;
	}
	public void setIdCmsExcel(Long idCmsExcel) {
		this.idCmsExcel = idCmsExcel;
	}
	public Long getIdCmsPdf() {
		return idCmsPdf;
	}
	public void setIdCmsPdf(Long idCmsPdf) {
		this.idCmsPdf = idCmsPdf;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getIdPpppColaboradorStp() {
		return idPpppColaboradorStp;
	}
	public void setIdPpppColaboradorStp(Long idPpppColaboradorStp) {
		this.idPpppColaboradorStp = idPpppColaboradorStp;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}


	public String getInstitucionContraparte() {
		return institucionContraparte;
	}


	public void setInstitucionContraparte(String institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}


	public Boolean getIsEditarColaborador() {
		return isEditarColaborador;
	}


	public void setIsEditarColaborador(Boolean isEditarColaborador) {
		this.isEditarColaborador = isEditarColaborador;
	}


	public Long getIdPppColaboradorClabeInterbancaria() {
		return idPppColaboradorClabeInterbancaria;
	}


	public void setIdPppColaboradorClabeInterbancaria(Long idPppColaboradorClabeInterbancaria) {
		this.idPppColaboradorClabeInterbancaria = idPppColaboradorClabeInterbancaria;
	}


	public String getUrlActual() {
		return urlActual;
	}


	public void setUrlActual(String urlActual) {
		this.urlActual = urlActual;
	}


	public String getClabeCuentaOrdenante() {
		return clabeCuentaOrdenante;
	}


	public void setClabeCuentaOrdenante(String clabeCuentaOrdenante) {
		this.clabeCuentaOrdenante = clabeCuentaOrdenante;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCausaDevolucion() {
		return causaDevolucion;
	}


	public void setCausaDevolucion(String causaDevolucion) {
		this.causaDevolucion = causaDevolucion;
	}


	public Date getFechaDispersion() {
		return fechaDispersion;
	}


	public void setFechaDispersion(Date fechaDispersion) {
		this.fechaDispersion = fechaDispersion;
	}




}
