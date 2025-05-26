package mx.com.consolida.ppp.dto;

public class TimbradoResponseDto {
	
	private String responseCode ="200";
	private String responseMessage ="El timbrado se realizó exitosamente.";
	
	
	private int numColaboradoresTimbrar ;
	private int numColaboradoresTimbrados ;
	private int numColaboradoresConErrores;
	
	private int idEstatusNomina ;
	
	

	public TimbradoResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimbradoResponseDto(String responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getNumColaboradoresTimbrar() {
		return numColaboradoresTimbrar;
	}

	public void setNumColaboradoresTimbrar(int numColaboradoresTimbrar) {
		this.numColaboradoresTimbrar = numColaboradoresTimbrar;
	}

	public int getNumColaboradoresTimbrados() {
		return numColaboradoresTimbrados;
	}

	public void setNumColaboradoresTimbrados(int numColaboradoresTimbrados) {
		this.numColaboradoresTimbrados = numColaboradoresTimbrados;
	}

	public int getNumColaboradoresConErrores() {
		return numColaboradoresConErrores;
	}

	public void setNumColaboradoresConErrores(int numColaboradoresConErrores) {
		this.numColaboradoresConErrores = numColaboradoresConErrores;
	}

	public int getIdEstatusNomina() {
		return idEstatusNomina;
	}

	public void setIdEstatusNomina(int idEstatusNomina) {
		this.idEstatusNomina = idEstatusNomina;
	}

	

	
	
	

}
