package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.usuario.UsuarioDTO;


public class PrestadoraServicioDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicio;
	private UsuarioDTO usuarioAlta;
	private String rfc;
	private String nombreCorto;
	private String razonSocial;
	private Boolean esFondo = false;
	private Date fechaAlta;
	private Long indEstatus;
	private List<CatProductoDto> catProductoDto;
	private Integer totalProductosRegistrados;
	private String idConsar;
	private String clave;
	
	private List<CatGeneralDto> municipios;
	private DomicilioComunDto prestadoraServicioDomicilioDto;
	private CelulaPrestadoraServicioDto celulaPrestadoraServicioDto;
	private List<CatGeneralDto> entidadFederativa;
	
	private List<CelulaDto> catCelula;
	private List<CatGeneralDto> catBanco;
	private List<CatGeneralDto> catTipoCuenta;
	 private List<PrestadoraServicioCuentaBancariaDto> prestadoraServicioCuentaBancaria;
	private PrestadoraServicioDocumentoDto prestadoraServicioDocumento;
	private List<PrestadoraServicioProductoDto> prestadoraServicioProducto;
	private List<PrestadoraServicioStpDto> listprestadoraServicioStpDto;
	private PrestadoraServicioStpDto prestadoraServicioStpDto;
	
	private MensajeDTO mensajeDTO; 
	
	private Map <String ,Object > archivoLogotipo;
	private String archivoRecuperadoLogotipo;
	private String nombreArchivoLogotipo;
	
	private String passwordFiel;
	private String passwordCsd;
	
	public PrestadoraServicioDto () {
	}
	
	public PrestadoraServicioDto (Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}
	
	public PrestadoraServicioDto (String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public PrestadoraServicioDto(String rfc, String razonSocial, String idConsar, Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
		this.rfc = rfc;
		this.razonSocial = razonSocial;
		this.idConsar = idConsar;
	}
	
	public PrestadoraServicioDto(Long idPrestadoraServicio,String rfc, String nombreCorto,String razonSocial) {
		this.idPrestadoraServicio = idPrestadoraServicio;
		this.rfc = rfc;
		this.nombreCorto = nombreCorto;
		this.razonSocial = razonSocial;
	}
	
	public PrestadoraServicioDto (Long idPrestadoraServicio,UsuarioDTO usuarioAlta, String rfc, String nombreCorto, String razonSocial,
	 Boolean esFondo, Date fechaAlta, Long indEstatus) {
		this.idPrestadoraServicio = idPrestadoraServicio;
		this.usuarioAlta = usuarioAlta;
		this.rfc = rfc;
		this.nombreCorto = nombreCorto;
		this.razonSocial = razonSocial;
		this.esFondo = esFondo;
		this.fechaAlta = fechaAlta;
		this.indEstatus = indEstatus;
	}
	
	public List<CelulaDto> getCatCelula() {
		return catCelula;
	}

	public void setCatCelula(List<CelulaDto> catCelula) {
		this.catCelula = catCelula;
	}

	public DomicilioComunDto getPrestadoraServicioDomicilioDto() {
		return prestadoraServicioDomicilioDto;
	}

	public void setPrestadoraServicioDomicilioDto(DomicilioComunDto prestadoraServicioDomicilioDto) {
		this.prestadoraServicioDomicilioDto = prestadoraServicioDomicilioDto;
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

	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}

	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Boolean getEsFondo() {
		return esFondo;
	}

	public void setEsFondo(Boolean esFondo) {
		this.esFondo = esFondo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	
	public List<CatProductoDto> getCatProductoDto() {
		return catProductoDto;
	}

	public void setCatProductoDto(List<CatProductoDto> catProductoDto) {
		this.catProductoDto = catProductoDto;
	}

	public Integer getTotalProductosRegistrados() {
		return totalProductosRegistrados;
	}

	public void setTotalProductosRegistrados(Integer totalProductosRegistrados) {
		this.totalProductosRegistrados = totalProductosRegistrados;
	}

	public CelulaPrestadoraServicioDto getCelulaPrestadoraServicioDto() {
		return celulaPrestadoraServicioDto;
	}

	public void setCelulaPrestadoraServicioDto(CelulaPrestadoraServicioDto celulaPrestadoraServicioDto) {
		this.celulaPrestadoraServicioDto = celulaPrestadoraServicioDto;
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

	public List<PrestadoraServicioCuentaBancariaDto> getPrestadoraServicioCuentaBancaria() {
		return prestadoraServicioCuentaBancaria;
	}

	public void setPrestadoraServicioCuentaBancaria(
			List<PrestadoraServicioCuentaBancariaDto> prestadoraServicioCuentaBancaria) {
		this.prestadoraServicioCuentaBancaria = prestadoraServicioCuentaBancaria;
	}

	public PrestadoraServicioDocumentoDto getPrestadoraServicioDocumento() {
		return prestadoraServicioDocumento;
	}

	public void setPrestadoraServicioDocumento(PrestadoraServicioDocumentoDto prestadoraServicioDocumento) {
		this.prestadoraServicioDocumento = prestadoraServicioDocumento;
	}

	public MensajeDTO getMensajeDTO() {
		return mensajeDTO;
	}

	public void setMensajeDTO(MensajeDTO mensajeDTO) {
		this.mensajeDTO = mensajeDTO;
	}

	public String getIdConsar() {
		return idConsar;
	}

	public void setIdConsar(String idConsar) {
		this.idConsar = idConsar;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Map<String, Object> getArchivoLogotipo() {
		return archivoLogotipo;
	}

	public void setArchivoLogotipo(Map<String, Object> archivoLogotipo) {
		this.archivoLogotipo = archivoLogotipo;
	}

	public String getArchivoRecuperadoLogotipo() {
		return archivoRecuperadoLogotipo;
	}

	public void setArchivoRecuperadoLogotipo(String archivoRecuperadoLogotipo) {
		this.archivoRecuperadoLogotipo = archivoRecuperadoLogotipo;
	}

	public String getNombreArchivoLogotipo() {
		return nombreArchivoLogotipo;
	}

	public void setNombreArchivoLogotipo(String nombreArchivoLogotipo) {
		this.nombreArchivoLogotipo = nombreArchivoLogotipo;
	}

	public List<PrestadoraServicioProductoDto> getPrestadoraServicioProducto() {
		return prestadoraServicioProducto;
	}

	public void setPrestadoraServicioProducto(List<PrestadoraServicioProductoDto> prestadoraServicioProducto) {
		this.prestadoraServicioProducto = prestadoraServicioProducto;
	}

	public String getPasswordFiel() {
		return passwordFiel;
	}

	public void setPasswordFiel(String passwordFiel) {
		this.passwordFiel = passwordFiel;
	}

	public String getPasswordCsd() {
		return passwordCsd;
	}

	public void setPasswordCsd(String passwordCsd) {
		this.passwordCsd = passwordCsd;
	}

	public List<PrestadoraServicioStpDto> getListprestadoraServicioStpDto() {
		return listprestadoraServicioStpDto;
	}

	public void setListprestadoraServicioStpDto(List<PrestadoraServicioStpDto> listprestadoraServicioStpDto) {
		this.listprestadoraServicioStpDto = listprestadoraServicioStpDto;
	}

	public PrestadoraServicioStpDto getPrestadoraServicioStpDto() {
		return prestadoraServicioStpDto;
	}

	public void setPrestadoraServicioStpDto(PrestadoraServicioStpDto prestadoraServicioStpDto) {
		this.prestadoraServicioStpDto = prestadoraServicioStpDto;
	}
	
	

}
