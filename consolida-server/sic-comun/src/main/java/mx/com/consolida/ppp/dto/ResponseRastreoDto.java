package mx.com.consolida.ppp.dto;

public class ResponseRastreoDto {

	private ResultadoRastreoDto response;

	public ResponseRastreoDto() {
				// TODO Auto-geneerated constructor stub
		this.response =new ResultadoRastreoDto();
	}

	public ResultadoRastreoDto getResultado() {
		return response;
	}

	public void setResultado(ResultadoRastreoDto response) {
		this.response = response;
	}
	
	

}
