package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class NominaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNomina;
	private Long idNominaPPP;
	private Long idNominaPPPComplementaria;
	private Long idNominaPPPPadre;
	
	private ClienteDto clienteDto;
	
	private PrestadoraServicioDto prestadoraServicioDto;
	
	private NominaClienteDto nominaClienteDto;
	
	private NominaComplementoDto nominaComplementoDto;
	
	private CatGeneralDto catEstatusNomina;
	
	private CatGeneralDto periodicidadNomina;
	
	private CatGeneralDto catPacTimbrado;
	
	private String claveNomina;
	
	private String codigoSat; 
	
	private String descripcionSat;
	
	private String descripcionConcepto;
	
	private Date fechaInicioNomina;
	
	private Date fechaFinNomina;
	
    private Date sinformatofechaInicioNomina;
	
	private Date sinformatofechaFinNomina;
	
	private String fechaInicioNominaFormato;
	
	private String fechaFinNominaFormato;
	
	private int totalDocumentosRegistrados;
	
	private int totalNominas;
	private int totalNominasEnProcesoNominista;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;
	
	private String motivoRechazo;
	
	private Boolean necesitaFactura;
	private Boolean necesitaTimbre;
	
	
	private String idCmsFactura;
	
	//Data Grid a primer nivel 
	private String clienteNombreComercial;
	private String clienteRazonSocialONombre;
	private String clienteRFC;

	
	
	private Boolean esNominaComplementaria =false;
	private Double montoTotalPpp;
	
	private Double montoAComplementarPpp;

	private String colorNomina;
	private List<EmpleadoDTO> listColaboradores;
	
	private Boolean aFacturar =false;
	private Long idDeposito;
	private Long idFactura;
	private String rfcPrestadora;
	
	public NominaDto() {
		
	}
	
	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public NominaDto(Long idNomina) {
		this.idNomina = idNomina;
	}

	public Long getIdNomina() {
		return idNomina;
	}

	public void setIdNomina(Long idNomina) {
		this.idNomina = idNomina;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}

	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
	}

	public String getCodigoSat() {
		return codigoSat;
	}

	public void setCodigoSat(String codigoSat) {
		this.codigoSat = codigoSat;
	}

	public String getDescripcionSat() {
		return descripcionSat;
	}

	public void setDescripcionSat(String descripcionSat) {
		this.descripcionSat = descripcionSat;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public Date getFechaInicioNomina() {
		return fechaInicioNomina;
	}

	public void setFechaInicioNomina(Date fechaInicioNomina) {
		this.fechaInicioNomina = fechaInicioNomina;
	}

	public Date getFechaFinNomina() {
		return fechaFinNomina;
	}

	public void setFechaFinNomina(Date fechaFinNomina) {
		this.fechaFinNomina = fechaFinNomina;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public int getTotalNominas() {
		return totalNominas;
	}

	public void setTotalNominas(int totalNominas) {
		this.totalNominas = totalNominas;
	}

	public NominaClienteDto getNominaClienteDto() {
		return nominaClienteDto;
	}

	public void setNominaClienteDto(NominaClienteDto nominaClienteDto) {
		this.nominaClienteDto = nominaClienteDto;
	}

	public CatGeneralDto getCatEstatusNomina() {
		return catEstatusNomina;
	}

	public void setCatEstatusNomina(CatGeneralDto catEstatusNomina) {
		this.catEstatusNomina = catEstatusNomina;
	}

	public String getFechaInicioNominaFormato() {
		return fechaInicioNominaFormato;
	}

	public void setFechaInicioNominaFormato(String fechaInicioNominaFormato) {
		this.fechaInicioNominaFormato = fechaInicioNominaFormato;
	}

	public String getFechaFinNominaFormato() {
		return fechaFinNominaFormato;
	}

	public void setFechaFinNominaFormato(String fechaFinNominaFormato) {
		this.fechaFinNominaFormato = fechaFinNominaFormato;
	}

	public Long getIdNominaPPP() {
		return idNominaPPP;
	}

	public void setIdNominaPPP(Long idNominaPPP) {
		this.idNominaPPP = idNominaPPP;
	}

	public String getClaveNomina() {
		return claveNomina;
	}

	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public int getTotalNominasEnProcesoNominista() {
		return totalNominasEnProcesoNominista;
	}

	public void setTotalNominasEnProcesoNominista(int totalNominasEnProcesoNominista) {
		this.totalNominasEnProcesoNominista = totalNominasEnProcesoNominista;
	}

	public NominaComplementoDto getNominaComplementoDto() {
		return nominaComplementoDto;
	}

	public void setNominaComplementoDto(NominaComplementoDto nominaComplementoDto) {
		this.nominaComplementoDto = nominaComplementoDto;
	}

	public Boolean getNecesitaFactura() {
		return necesitaFactura;
	}

	public void setNecesitaFactura(Boolean necesitaFactura) {
		this.necesitaFactura = necesitaFactura;
	}

	public int getTotalDocumentosRegistrados() {
		return totalDocumentosRegistrados;
	}

	public void setTotalDocumentosRegistrados(int totalDocumentosRegistrados) {
		this.totalDocumentosRegistrados = totalDocumentosRegistrados;
	}

	public String getIdCmsFactura() {
		return idCmsFactura;
	}

	public void setIdCmsFactura(String idCmsFactura) {
		this.idCmsFactura = idCmsFactura;
	}

	public CatGeneralDto getPeriodicidadNomina() {
		return periodicidadNomina;
	}

	public void setPeriodicidadNomina(CatGeneralDto periodicidadNomina) {
		this.periodicidadNomina = periodicidadNomina;
	}

	public CatGeneralDto getCatPacTimbrado() {
		return catPacTimbrado;
	}

	public void setCatPacTimbrado(CatGeneralDto catPacTimbrado) {
		this.catPacTimbrado = catPacTimbrado;
	}

	public String getClienteNombreComercial() {
		return clienteNombreComercial;
	}

	public void setClienteNombreComercial(String clienteNombreComercial) {
		this.clienteNombreComercial = clienteNombreComercial;
	}

	public String getClienteRazonSocialONombre() {
		return clienteRazonSocialONombre;
	}

	public void setClienteRazonSocialONombre(String clienteRazonSocialONombre) {
		this.clienteRazonSocialONombre = clienteRazonSocialONombre;
	}

	public String getClienteRFC() {
		return clienteRFC;
	}

	public void setClienteRFC(String clienteRFC) {
		this.clienteRFC = clienteRFC;
	}

	public Boolean getEsNominaComplementaria() {
		return esNominaComplementaria;
	}

	public void setEsNominaComplementaria(Boolean esNominaComplementaria) {
		this.esNominaComplementaria = esNominaComplementaria;
	}

	public Long getIdNominaPPPComplementaria() {
		return idNominaPPPComplementaria;
	}

	public void setIdNominaPPPComplementaria(Long idNominaPPPComplementaria) {
		this.idNominaPPPComplementaria = idNominaPPPComplementaria;
	}

	public Long getIdNominaPPPPadre() {
		return idNominaPPPPadre;
	}

	public void setIdNominaPPPPadre(Long idNominaPPPPadre) {
		this.idNominaPPPPadre = idNominaPPPPadre;
	}

	public Double getMontoTotalPpp() {
		return montoTotalPpp;
	}

	public void setMontoTotalPpp(Double montoTotalPpp) {
		this.montoTotalPpp = montoTotalPpp;
	}

	public Double getMontoAComplementarPpp() {
		return montoAComplementarPpp;
	}

	public void setMontoAComplementarPpp(Double montoAComplementarPpp) {
		this.montoAComplementarPpp = montoAComplementarPpp;
	}

	public Date getSinformatofechaInicioNomina() {
		return sinformatofechaInicioNomina;
	}

	public void setSinformatofechaInicioNomina(Date sinformatofechaInicioNomina) {
		this.sinformatofechaInicioNomina = sinformatofechaInicioNomina;
	}

	public Date getSinformatofechaFinNomina() {
		return sinformatofechaFinNomina;
	}

	public void setSinformatofechaFinNomina(Date sinformatofechaFinNomina) {
		this.sinformatofechaFinNomina = sinformatofechaFinNomina;
	}
	
	public String getColorNomina() {
		return colorNomina;
	}

	public void setColorNomina(String colorNomina) {
		this.colorNomina = colorNomina;
	}

	public List<EmpleadoDTO> getListColaboradores() {
		return listColaboradores;
	}

	public void setListColaboradores(List<EmpleadoDTO> listColaboradores) {
		this.listColaboradores = listColaboradores;
	}

	public Boolean getNecesitaTimbre() {
		return necesitaTimbre;
	}

	public void setNecesitaTimbre(Boolean necesitaTimbre) {
		this.necesitaTimbre = necesitaTimbre;
	}

	public String getRfcPrestadora() {
		return rfcPrestadora;
	}

	public void setRfcPrestadora(String rfcPrestadora) {
		this.rfcPrestadora = rfcPrestadora;
	}

	public Boolean getaFacturar() {
		return aFacturar;
	}

	public void setaFacturar(Boolean aFacturar) {
		this.aFacturar = aFacturar;
	}

	
	
	
	

	
}
