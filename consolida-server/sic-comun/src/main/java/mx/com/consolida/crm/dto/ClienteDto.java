package mx.com.consolida.crm.dto;

import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class ClienteDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCliente;
	
	private Long idEstatus;
	
	private CatGeneralDto catTipoPersona;
	
	private CatEstatusDto catEstatus;
	
	private List<CatGrupoDto> listaGrupos;
	
	private NoministaDto noministaDto;
	
	private CatGeneralDto catRegimenFiscal;
	
	private PrestadoraServicioDto prestadoraServicio;
	
	private PrestadoraServicioDto prestadoraServicioFondo;
	
	private List<NoministaDto> listaNoministas;
	
	private CatGrupoDto catGrupo;
	
	private CelulaDto celula;
	
	private CatGeneralDto catTipoPago;
	
	private CatGeneralDto catGiroComercialDto;
	
	private Long idGiroComercial;
	
	private CatSubGiroComercialDto catSubGiroComercialDto;
	
	private List<CatSubGiroComercialDto> listaSubGiroComercialDto;
	
	private ClientePersonaContactoDto clientePersonaContacto;
	
	private DomicilioDto domicilioDto;
	
	private Long idClienteTemp;
	
	private ClienteTempDto clienteTemp;
	
	private Long idPrestadoraServicio;
	
	private Long idCelulaCliente;
	
	private String rfc;

	private String razonSocial;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;
	
	private String nombreCompleto;
	
	private String nombreRazonSocial;

	private String nombreComercial;
	
	private Boolean esActivo;

	private Date fechaConstitucionEmpresa;
	
	private CatGeneralDto idTipoPersona;
	
	private CatGeneralDto catCategoria;
	
	private String cveRegistroPatronal;
	
	private String clave;
	
	private String descripcionGrupo;
	
	private String clienteAgrupador;
	
	private Date fechaAlta;

	private Date fechaModificacion;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;
	
	private Long indEstatus;
	
	private boolean esAgregarCilente;
	
	private List<ClienteActProfSatDto> listClienActProfSat;

	private List<ClienteApoderadoLegalDto> listClienApodeLegal;
	
	private Boolean esCompletoMesaCtrl;
	
	private String actividadEconomicaFinal;
	
	private String prefijoSTP;
	private String clabeCuentaOrdenante;
	///Domicilio
	private DomicilioComunDto clienteDomicilioDto;
	
	private List<CatGeneralDto> entidadFederativa;
    private List<CatGeneralDto> municipios;
    /////////////////////////////////////////////////
    
    ////// Cuenta bancaria
    private List<CatGeneralDto> catBanco;
	private List<CatGeneralDto> catTipoCuenta;
	 private List<ClienteCuentaBancariaDto> clienteCuentasBancarias;
	/////////////////////////////////////////////////
	 
	 
	 // filtros para datatable
	 private String descripcionRazonSocial;
	 private String descripcionTipoPersona;
	 
	 //sindicato
	 private String titulo;
	 private Long percepciones;
	 private String clavePercepcion;
	 private String descripcion;
	 private Long estimbreSindicato;

	 
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

	public ClienteDto() {
	}
	
	public ClienteDto(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdClienteTemp() {
		return idClienteTemp;
	}

	public void setIdClienteTemp(Long idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}

	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}

	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Date getFechaConstitucionEmpresa() {
		return fechaConstitucionEmpresa;
	}

	public void setFechaConstitucionEmpresa(Date fechaConstitucionEmpresa) {
		this.fechaConstitucionEmpresa = fechaConstitucionEmpresa;
	}

	public String getCveRegistroPatronal() {
		return cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(String cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public CatGeneralDto getCatTipoPersona() {
		return catTipoPersona;
	}

	public void setCatTipoPersona(CatGeneralDto catTipoPersona) {
		this.catTipoPersona = catTipoPersona;
	}

	public ClientePersonaContactoDto getClientePersonaContacto() {
		return clientePersonaContacto;
	}

	public void setClientePersonaContacto(ClientePersonaContactoDto clientePersonaContacto) {
		this.clientePersonaContacto = clientePersonaContacto;
	}

	public Long getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
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

	public List<ClienteActProfSatDto> getListClienActProfSat() {
		return listClienActProfSat;
	}

	public void setListClienActProfSat(List<ClienteActProfSatDto> listClienActProfSat) {
		this.listClienActProfSat = listClienActProfSat;
	}

	public List<ClienteApoderadoLegalDto> getListClienApodeLegal() {
		return listClienApodeLegal;
	}

	public void setListClienApodeLegal(List<ClienteApoderadoLegalDto> listClienApodeLegal) {
		this.listClienApodeLegal = listClienApodeLegal;
	}

	public CatEstatusDto getCatEstatus() {
		return catEstatus;
	}

	public void setCatEstatus(CatEstatusDto catEstatus) {
		this.catEstatus = catEstatus;
	}



	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public String getClienteAgrupador() {
		return clienteAgrupador;
	}

	public void setClienteAgrupador(String clienteAgrupador) {
		this.clienteAgrupador = clienteAgrupador;
	}

	public DomicilioDto getDomicilioDto() {
		return domicilioDto;
	}

	public void setDomicilioDto(DomicilioDto domicilioDto) {
		this.domicilioDto = domicilioDto;
	}

	public CatGrupoDto getCatGrupo() {
		return catGrupo;
	}

	public void setCatGrupo(CatGrupoDto catGrupo) {
		this.catGrupo = catGrupo;
	}

	public List<CatGrupoDto> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<CatGrupoDto> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public CatGeneralDto getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(CatGeneralDto idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public boolean isEsAgregarCilente() {
		return esAgregarCilente;
	}

	public void setEsAgregarCilente(boolean esAgregarCilente) {
		this.esAgregarCilente = esAgregarCilente;
	}

	public CelulaDto getCelula() {
		return celula;
	}

	public void setCelula(CelulaDto celula) {
		this.celula = celula;
	}

	public NoministaDto getNoministaDto() {
		return noministaDto;
	}

	public void setNoministaDto(NoministaDto noministaDto) {
		this.noministaDto = noministaDto;
	}

	public List<NoministaDto> getListaNoministas() {
		return listaNoministas;
	}

	public void setListaNoministas(List<NoministaDto> listaNoministas) {
		this.listaNoministas = listaNoministas;
	}

	public Long getIdCelulaCliente() {
		return idCelulaCliente;
	}

	public void setIdCelulaCliente(Long idCelulaCliente) {
		this.idCelulaCliente = idCelulaCliente;
	}

	public Boolean getEsCompletoMesaCtrl() {
		return esCompletoMesaCtrl;
	}

	public void setEsCompletoMesaCtrl(Boolean esCompletoMesaCtrl) {
		this.esCompletoMesaCtrl = esCompletoMesaCtrl;
	}

	public DomicilioComunDto getClienteDomicilioDto() {
		return clienteDomicilioDto;
	}

	public void setClienteDomicilioDto(DomicilioComunDto clienteDomicilioDto) {
		this.clienteDomicilioDto = clienteDomicilioDto;
	}

	public List<CatGeneralDto> getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(List<CatGeneralDto> entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public List<CatGeneralDto> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<CatGeneralDto> municipios) {
		this.municipios = municipios;
	}

	public ClienteTempDto getClienteTemp() {
		return clienteTemp;
	}

	public void setClienteTemp(ClienteTempDto clienteTemp) {
		this.clienteTemp = clienteTemp;
	}

	public CatGeneralDto getCatTipoPago() {
		return catTipoPago;
	}

	public void setCatTipoPago(CatGeneralDto catTipoPago) {
		this.catTipoPago = catTipoPago;
	}

	public CatGeneralDto getCatGiroComercialDto() {
		return catGiroComercialDto;
	}

	public void setCatGiroComercialDto(CatGeneralDto catGiroComercialDto) {
		this.catGiroComercialDto = catGiroComercialDto;
	}

	public CatSubGiroComercialDto getCatSubGiroComercialDto() {
		return catSubGiroComercialDto;
	}

	public void setCatSubGiroComercialDto(CatSubGiroComercialDto catSubGiroComercialDto) {
		this.catSubGiroComercialDto = catSubGiroComercialDto;
	}

	public Long getIdGiroComercial() {
		return idGiroComercial;
	}

	public void setIdGiroComercial(Long idGiroComercial) {
		this.idGiroComercial = idGiroComercial;
	}

	public List<CatSubGiroComercialDto> getListaSubGiroComercialDto() {
		return listaSubGiroComercialDto;
	}

	public void setListaSubGiroComercialDto(List<CatSubGiroComercialDto> listaSubGiroComercialDto) {
		this.listaSubGiroComercialDto = listaSubGiroComercialDto;
	}

	public List<CatGeneralDto> getCatBanco() {
		return catBanco;
	}

	public void setCatBanco(List<CatGeneralDto> catBanco) {
		this.catBanco = catBanco;
	}

	public List<CatGeneralDto> getCatTipoCuenta() {
		return catTipoCuenta;
	}

	public void setCatTipoCuenta(List<CatGeneralDto> catTipoCuenta) {
		this.catTipoCuenta = catTipoCuenta;
	}

	public List<ClienteCuentaBancariaDto> getClienteCuentasBancarias() {
		return clienteCuentasBancarias;
	}

	public void setClienteCuentasBancarias(List<ClienteCuentaBancariaDto> clienteCuentasBancarias) {
		this.clienteCuentasBancarias = clienteCuentasBancarias;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public PrestadoraServicioDto getPrestadoraServicioFondo() {
		return prestadoraServicioFondo;
	}

	public void setPrestadoraServicioFondo(PrestadoraServicioDto prestadoraServicioFondo) {
		this.prestadoraServicioFondo = prestadoraServicioFondo;
	}

	public Boolean getEsActivo() {
		return esActivo;
	}

	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	public CatGeneralDto getCatCategoria() {
		return catCategoria;
	}

	public void setCatCategoria(CatGeneralDto catCategoria) {
		this.catCategoria = catCategoria;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public CatGeneralDto getCatRegimenFiscal() {
		return catRegimenFiscal;
	}

	public void setCatRegimenFiscal(CatGeneralDto catRegimenFiscal) {
		this.catRegimenFiscal = catRegimenFiscal;
	}

	public String getPrefijoSTP() {
		return prefijoSTP;
	}

	public void setPrefijoSTP(String prefijoSTP) {
		this.prefijoSTP = prefijoSTP;
	}

	public String getClabeCuentaOrdenante() {
		return clabeCuentaOrdenante;
	}

	public void setClabeCuentaOrdenante(String clabeCuentaOrdenante) {
		this.clabeCuentaOrdenante = clabeCuentaOrdenante;
	}

	public String getActividadEconomicaFinal() {
		return actividadEconomicaFinal;
	}

	public void setActividadEconomicaFinal(String actividadEconomicaFinal) {
		this.actividadEconomicaFinal = actividadEconomicaFinal;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getDescripcionRazonSocial() {
		return descripcionRazonSocial;
	}

	public void setDescripcionRazonSocial(String descripcionRazonSocial) {
		this.descripcionRazonSocial = descripcionRazonSocial;
	}

	public String getDescripcionTipoPersona() {
		return descripcionTipoPersona;
	}

	public void setDescripcionTipoPersona(String descripcionTipoPersona) {
		this.descripcionTipoPersona = descripcionTipoPersona;
	}

	public String getClavePercepcion() {
		return clavePercepcion;
	}

	public void setClavePercepcion(String clavePercepcion) {
		this.clavePercepcion = clavePercepcion;
	}

	

	

}