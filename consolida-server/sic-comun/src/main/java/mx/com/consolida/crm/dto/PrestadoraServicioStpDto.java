package mx.com.consolida.crm.dto;

import java.io.Serializable;

public class PrestadoraServicioStpDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean activo;
	private String apiKey;
	private String clabeCuentaOrdenante;	
	private String clientId;
	private String clientSecret;
	private String descripcionTipoDispersor;
	private String claveTipoDispersor;
	private String idCliente;
	private String idCuentaAhorro;
	private Long idPrestadoraServicioStp;
	private Long idTipoDispersor;
	private String nombreCentroCostos;
	private String password;
	private PrestadoraServicioDto prestadoraServicioDto;	
	private String userName;
	private String dispersor;
	
	
	
	public Long getIdPrestadoraServicioStp() {
		return idPrestadoraServicioStp;
	}
	public void setIdPrestadoraServicioStp(Long idPrestadoraServicioStp) {
		this.idPrestadoraServicioStp = idPrestadoraServicioStp;
	}
	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}
	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
	}
		public Long getIdTipoDispersor() {
		return idTipoDispersor;
	}
	public void setIdTipoDispersor(Long idTipoDispersor) {
		this.idTipoDispersor = idTipoDispersor;
	}
	public String getClaveTipoDispersor() {
		return claveTipoDispersor;
	}
	public void setClaveTipoDispersor(String claveTipoDispersor) {
		this.claveTipoDispersor = claveTipoDispersor;
	}
	public String getDescripcionTipoDispersor() {
		return descripcionTipoDispersor;
	}
	public void setDescripcionTipoDispersor(String descripcionTipoDispersor) {
		this.descripcionTipoDispersor = descripcionTipoDispersor;
	}
	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}
	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}
	public String getClabeCuentaOrdenante() {
		return clabeCuentaOrdenante;
	}
	public void setClabeCuentaOrdenante(String clabeCuentaOrdenante) {
		this.clabeCuentaOrdenante = clabeCuentaOrdenante;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getIdCuentaAhorro() {
		return idCuentaAhorro;
	}
	public void setIdCuentaAhorro(String idCuentaAhorro) {
		this.idCuentaAhorro = idCuentaAhorro;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public String getDispersor() {
		return dispersor;
	}
	public void setDispersor(String dispersor) {
		this.dispersor = dispersor;
	}
	
	
	
	

}