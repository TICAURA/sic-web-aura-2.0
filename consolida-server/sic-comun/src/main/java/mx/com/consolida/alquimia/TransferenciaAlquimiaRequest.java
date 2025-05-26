package mx.com.consolida.alquimia;

public class TransferenciaAlquimiaRequest {
	
	private String authorization;
	private String authorizationAlquimia;
	private String cuentaOrigen;
	private int  idCliente;
	private int medioPago;
	private Double importe;
	private String cuentaDestino;
	private String codigoBanco;
	private String guardaCuentaDestino;
	private String nombreBeneficiario;
	private String rfcBeneficiario;
	private String emailBeneficiario;
	private String concepto;
	private Long  noReferencia;
	private String apiKey;
	
	
	
	public TransferenciaAlquimiaRequest() {
		
		// TODO Auto-generated constructor stub
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public String getAuthorizationAlquimia() {
		return authorizationAlquimia;
	}
	public void setAuthorizationAlquimia(String authorizationAlquimia) {
		this.authorizationAlquimia = authorizationAlquimia;
	}
	public String getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(int medioPago) {
		this.medioPago = medioPago;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getCuentaDestino() {
		return cuentaDestino;
	}
	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	public String getCodigoBanco() {
		return codigoBanco;
	}
	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}
	public String getGuardaCuentaDestino() {
		return guardaCuentaDestino;
	}
	public void setGuardaCuentaDestino(String guardaCuentaDestino) {
		this.guardaCuentaDestino = guardaCuentaDestino;
	}
	
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getRfcBeneficiario() {
		return rfcBeneficiario;
	}
	public void setRfcBeneficiario(String rfcBeneficiario) {
		this.rfcBeneficiario = rfcBeneficiario;
	}
	public String getEmailBeneficiario() {
		return emailBeneficiario;
	}
	public void setEmailBeneficiario(String emailBeneficiario) {
		this.emailBeneficiario = emailBeneficiario;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Long getNoReferencia() {
		return noReferencia;
	}
	public void setNoReferencia(Long noReferencia) {
		this.noReferencia = noReferencia;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	

}
