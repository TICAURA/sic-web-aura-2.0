package mx.com.consolida.crm.dto;

import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class NominaClienteDto implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idNominaCliente;
	
	private String nombreNomina;
	
	private String claveNomina;
	
	private String descripcionCompuesta;
	
	private Long comisionPpp;
	
	private Long comisionSs;
	
	private Long idCelula;
	
	private CatGeneralDto catProductoNomina;
	
	private CatGeneralDto catProductoNominaFondo;
	
	private ClienteDto clienteDto;
	
	private List<PrestadoraServicioDto> listaPrestadoraServicioFondo;
	
	private PrestadoraServicioDto prestadoraServicioFondo;
	
	private List<PrestadoraServicioDto> listaPrestadoraServicio;
	
	private PrestadoraServicioDto prestadoraServicio;
	
	private int totalColaboradores;
	
	private Double montoTotalColaboradores;
	
	private Double montoNoDispersado;
	
	private Double subtotalFacturado;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private Long indEstatus;
	
	private NoministaDto usuarioNominista;
	
	private CatGeneralDto periodicidadNomina;
	private String ivaComercial;
	
	
	/// Honorario
	private Long idNominaClienteHonorario;
	private CatGeneralDto formulaPPP;
	private CatGeneralDto formulaTipoIva;
	private CatGeneralDto formulaFactura;
	private String honorarioPPP;
	private Long requiereFactura;
	private Long requiereTimbre;
	
	
	public NominaClienteDto(){
		
	}
	
	public NominaClienteDto(Long idNominaCliente){
		this.idNominaCliente = idNominaCliente;
	}

	public Long getIdNominaCliente() {
		return idNominaCliente;
	}

	public void setIdNominaCliente(Long idNominaCliente) {
		this.idNominaCliente = idNominaCliente;
	}

	public String getNombreNomina() {
		return nombreNomina;
	}

	public void setNombreNomina(String nombreNomina) {
		this.nombreNomina = nombreNomina;
	}

	public String getClaveNomina() {
		return claveNomina;
	}

	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public List<PrestadoraServicioDto> getListaPrestadoraServicioFondo() {
		return listaPrestadoraServicioFondo;
	}

	public void setListaPrestadoraServicioFondo(List<PrestadoraServicioDto> listaPrestadoraServicioFondo) {
		this.listaPrestadoraServicioFondo = listaPrestadoraServicioFondo;
	}

	public PrestadoraServicioDto getPrestadoraServicioFondo() {
		return prestadoraServicioFondo;
	}

	public void setPrestadoraServicioFondo(PrestadoraServicioDto prestadoraServicioFondo) {
		this.prestadoraServicioFondo = prestadoraServicioFondo;
	}

	public List<PrestadoraServicioDto> getListaPrestadoraServicio() {
		return listaPrestadoraServicio;
	}

	public void setListaPrestadoraServicio(List<PrestadoraServicioDto> listaPrestadoraServicio) {
		this.listaPrestadoraServicio = listaPrestadoraServicio;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
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

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public Long getComisionPpp() {
		return comisionPpp;
	}

	public void setComisionPpp(Long comisionPpp) {
		this.comisionPpp = comisionPpp;
	}

	public Long getComisionSs() {
		return comisionSs;
	}

	public void setComisionSs(Long comisionSs) {
		this.comisionSs = comisionSs;
	}

	public CatGeneralDto getCatProductoNomina() {
		return catProductoNomina;
	}

	public void setCatProductoNomina(CatGeneralDto catProductoNomina) {
		this.catProductoNomina = catProductoNomina;
	}

	public CatGeneralDto getCatProductoNominaFondo() {
		return catProductoNominaFondo;
	}

	public void setCatProductoNominaFondo(CatGeneralDto catProductoNominaFondo) {
		this.catProductoNominaFondo = catProductoNominaFondo;
	}

	public Long getIdCelula() {
		return idCelula;
	}

	public void setIdCelula(Long idCelula) {
		this.idCelula = idCelula;
	}

	public String getDescripcionCompuesta() {
		return descripcionCompuesta;
	}

	public void setDescripcionCompuesta(String descripcionCompuesta) {
		this.descripcionCompuesta = descripcionCompuesta;
	}

	public int getTotalColaboradores() {
		return totalColaboradores;
	}

	public void setTotalColaboradores(int totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}

	public Double getMontoTotalColaboradores() {
		return montoTotalColaboradores;
	}

	public void setMontoTotalColaboradores(Double montoTotalColaboradores) {
		this.montoTotalColaboradores = montoTotalColaboradores;
	}

	public NoministaDto getUsuarioNominista() {
		return usuarioNominista;
	}

	public void setUsuarioNominista(NoministaDto usuarioNominista) {
		this.usuarioNominista = usuarioNominista;
	}

	public Long getIdNominaClienteHonorario() {
		return idNominaClienteHonorario;
	}

	public void setIdNominaClienteHonorario(Long idNominaClienteHonorario) {
		this.idNominaClienteHonorario = idNominaClienteHonorario;
	}

	public CatGeneralDto getFormulaPPP() {
		return formulaPPP;
	}

	public void setFormulaPPP(CatGeneralDto formulaPPP) {
		this.formulaPPP = formulaPPP;
	}

	public CatGeneralDto getFormulaTipoIva() {
		return formulaTipoIva;
	}

	public void setFormulaTipoIva(CatGeneralDto formulaTipoIva) {
		this.formulaTipoIva = formulaTipoIva;
	}

	public CatGeneralDto getFormulaFactura() {
		return formulaFactura;
	}

	public void setFormulaFactura(CatGeneralDto formulaFactura) {
		this.formulaFactura = formulaFactura;
	}

	public String getHonorarioPPP() {
		return honorarioPPP;
	}

	public void setHonorarioPPP(String honorarioPPP) {
		this.honorarioPPP = honorarioPPP;
	}

	public CatGeneralDto getPeriodicidadNomina() {
		return periodicidadNomina;
	}

	public void setPeriodicidadNomina(CatGeneralDto periodicidadNomina) {
		this.periodicidadNomina = periodicidadNomina;
	}

	public Long getRequiereFactura() {
		return requiereFactura;
	}

	public void setRequiereFactura(Long requiereFactura) {
		this.requiereFactura = requiereFactura;
	}

	public String getIvaComercial() {
		return ivaComercial;
	}

	public void setIvaComercial(String ivaComercial) {
		this.ivaComercial = ivaComercial;
	}

	public Double getMontoNoDispersado() {
		return montoNoDispersado;
	}

	public void setMontoNoDispersado(Double montoNoDispersado) {
		this.montoNoDispersado = montoNoDispersado;
	}

	public Double getSubtotalFacturado() {
		return subtotalFacturado;
	}

	public void setSubtotalFacturado(Double subtotalFacturado) {
		this.subtotalFacturado = subtotalFacturado;
	}

	public Long getRequiereTimbre() {
		return requiereTimbre;
	}

	public void setRequiereTimbre(Long requiereTimbre) {
		this.requiereTimbre = requiereTimbre;
	}
	
	
	
	
	
	
	

}
