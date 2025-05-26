package mx.com.consolida.crm.dto;

import java.util.List;

public class FichaTecnicaDto {
	private Long idPrestadoraServicio;
	private String idConsar;
	private Long isFondo;
	private String nombreCorto;
	private String razonSocial;
	private String logotipo;
	private String nombreCelula;
	private String claveCelula;
	private String descripcionRiesgo;
	private String descEntidad;
	private String descMunicipio;
	private Long totalClienteByGrupo;
	private Long totalClienteRozonSocial;
	private Long totalColaboradores;
	private String nombreCentroCostos;
	
	private List<DatosListaFichaDto> actividadEconomica;
	private List<DatosListaFichaDto> accionistas;
	private List<DatosListaFichaDto> documentos;
	private List<FichaTecnicaDto> prestadorasAsociadas;
	
	private List<DatosListaFichaDto> registroPatronales;
	private List<DatosListaFichaDto> representantesLegales;
	
	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}
	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}
	public Long getIsFondo() {
		return isFondo;
	}
	public void setIsFondo(Long isFondo) {
		this.isFondo = isFondo;
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
	public String getLogotipo() {
		return logotipo;
	}
	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}
	public String getNombreCelula() {
		return nombreCelula;
	}
	public void setNombreCelula(String nombreCelula) {
		this.nombreCelula = nombreCelula;
	}
	public String getClaveCelula() {
		return claveCelula;
	}
	public void setClaveCelula(String claveCelula) {
		this.claveCelula = claveCelula;
	}
	public String getDescripcionRiesgo() {
		return descripcionRiesgo;
	}
	public void setDescripcionRiesgo(String descripcionRiesgo) {
		this.descripcionRiesgo = descripcionRiesgo;
	}
	public String getDescEntidad() {
		return descEntidad;
	}
	public void setDescEntidad(String descEntidad) {
		this.descEntidad = descEntidad;
	}
	public String getDescMunicipio() {
		return descMunicipio;
	}
	public void setDescMunicipio(String descMunicipio) {
		this.descMunicipio = descMunicipio;
	}
	public Long getTotalClienteByGrupo() {
		return totalClienteByGrupo;
	}
	public void setTotalClienteByGrupo(Long totalClienteByGrupo) {
		this.totalClienteByGrupo = totalClienteByGrupo;
	}
	public Long getTotalClienteRozonSocial() {
		return totalClienteRozonSocial;
	}
	public void setTotalClienteRozonSocial(Long totalClienteRozonSocial) {
		this.totalClienteRozonSocial = totalClienteRozonSocial;
	}
	public List<DatosListaFichaDto> getActividadEconomica() {
		return actividadEconomica;
	}
	public void setActividadEconomica(List<DatosListaFichaDto> actividadEconomica) {
		this.actividadEconomica = actividadEconomica;
	}
	public List<DatosListaFichaDto> getAccionistas() {
		return accionistas;
	}
	public void setAccionistas(List<DatosListaFichaDto> accionistas) {
		this.accionistas = accionistas;
	}
	public List<DatosListaFichaDto> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<DatosListaFichaDto> documentos) {
		this.documentos = documentos;
	}
	public List<FichaTecnicaDto> getPrestadorasAsociadas() {
		return prestadorasAsociadas;
	}
	public void setPrestadorasAsociadas(List<FichaTecnicaDto> prestadorasAsociadas) {
		this.prestadorasAsociadas = prestadorasAsociadas;
	}
	public String getIdConsar() {
		return idConsar;
	}
	public void setIdConsar(String idConsar) {
		this.idConsar = idConsar;
	}
	public List<DatosListaFichaDto> getRegistroPatronales() {
		return registroPatronales;
	}
	public void setRegistroPatronales(List<DatosListaFichaDto> registroPatronales) {
		this.registroPatronales = registroPatronales;
	}
	public List<DatosListaFichaDto> getRepresentantesLegales() {
		return representantesLegales;
	}
	public void setRepresentantesLegales(List<DatosListaFichaDto> representantesLegales) {
		this.representantesLegales = representantesLegales;
	}
	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}
	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}
	public Long getTotalColaboradores() {
		return totalColaboradores;
	}
	public void setTotalColaboradores(Long totalColaboradores) {
		this.totalColaboradores = totalColaboradores;
	}
	
}

