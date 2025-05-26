package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.generico.CatGenericoDTO;

@SuppressWarnings("serial")
public class EmpresaDTO implements Serializable {
	
	private Integer idEmpresa;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String razonSocial;
	private String rfc;
	private String claveSAT;
	private CatGenericoDTO regimen;
	private String giro;
	private String tipoPersona;
	private Integer idUsuarioPerfilEmpresa;
	private String nombreORazon;
	private String emailEmpresa;
	private String cvePlanEmpresa;
	
	private List<EmpresaRegimenFiscalDTO> empresaRegimenDTOs;
	private String exiteCerKey;
	
	public EmpresaDTO(){
		
	}
		
	public EmpresaDTO(String demo){
      this.setRfc("LAN7008173R5");
      this.setNombre("Marcos López Esperanza");
      this.setRegimen(new CatGenericoDTO(601,"601","General de Ley PersonasMorales"));
		
	}
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getClaveSAT() {
		return claveSAT;
	}
	public void setClaveSAT(String claveSAT) {
		this.claveSAT = claveSAT;
	}
	public CatGenericoDTO getRegimen() {
		return regimen;
	}
	public void setRegimen(CatGenericoDTO regimen) {
		this.regimen = regimen;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public Integer getIdUsuarioPerfilEmpresa() {
		return idUsuarioPerfilEmpresa;
	}
	public void setIdUsuarioPerfilEmpresa(Integer idUsuarioPerfilEmpresa) {
		this.idUsuarioPerfilEmpresa = idUsuarioPerfilEmpresa;
	}
	public String getNombreORazon() {
		return nombreORazon;
	}
	public void setNombreORazon(String nombreORazon) {
		this.nombreORazon = nombreORazon;
	}
	public List<EmpresaRegimenFiscalDTO> getEmpresaRegimenDTOs() {
		return empresaRegimenDTOs;
	}
	public void setEmpresaRegimenDTOs(List<EmpresaRegimenFiscalDTO> empresaRegimenDTOs) {
		this.empresaRegimenDTOs = empresaRegimenDTOs;
	}

	public String getEmailEmpresa() {
		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}

	public String getCvePlanEmpresa() {
		return cvePlanEmpresa;
	}

	public void setCvePlanEmpresa(String cvePlanEmpresa) {
		this.cvePlanEmpresa = cvePlanEmpresa;
	}

	public String getExiteCerKey() {
		return exiteCerKey;
	}

	public void setExiteCerKey(String exiteCerKey) {
		this.exiteCerKey = exiteCerKey;
	}
	
	
}