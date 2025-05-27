package mx.com.facturacion.factura;

import java.io.Serializable;

import mx.com.consolida.catalogos.CatGeneralDto;


public class ClienteDTO implements Serializable {

	/**
	 * 
	 */
	
	private Long idCliente;
	private String rfc;
	private String razonSocial;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String contacto;
	private String nombreCompleto;
	private CatGeneralDto tipoPersona;
	private CatGeneralDto usoCFDI;
	private String curp;
	private String idEmpleadoSTP;
	private String correoElectronico;
	private String nss;
	private CatGeneralDto regimenFiscal;
	private String codigoPostal;
	private String domicilio;
	private String numAfiliacion;
	private String folio;
	private String clabeIntebancaria;
	

	
	public ClienteDTO(){
		
	}
	
	public ClienteDTO(String demo){
		
		this.idCliente = 1L;
		this.rfc = "PMU170911TH9";
		this.razonSocial = "PROSALUD MUTUUS, S.C. DE R.L DE C.V.";
		this.nombre = "Miguel Angele Lopez Jimenez";
		this.usoCFDI = new CatGeneralDto(1L, "G03", "Gastos en general");	
		this.correoElectronico ="monteromalj@gmail.com";
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public CatGeneralDto getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(CatGeneralDto tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public CatGeneralDto getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(CatGeneralDto usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getIdEmpleadoSTP() {
		return idEmpleadoSTP;
	}

	public void setIdEmpleadoSTP(String idEmpleadoSTP) {
		this.idEmpleadoSTP = idEmpleadoSTP;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public CatGeneralDto getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(CatGeneralDto regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getNumAfiliacion() {
		return numAfiliacion;
	}

	public void setNumAfiliacion(String numAfiliacion) {
		this.numAfiliacion = numAfiliacion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getClabeIntebancaria() {
		return clabeIntebancaria;
	}

	public void setClabeIntebancaria(String clabeIntebancaria) {
		this.clabeIntebancaria = clabeIntebancaria;
	}
	
	
	
	

}