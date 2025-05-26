package mx.com.documento;

import java.io.Serializable;
import java.util.Date;

public class DocumentoMSDto implements Serializable {

	private Long idDocumentoMs;
	private String archivo;
	private String extension;
	private Date fechaAlta;
	private String indEstatus;
	private DefinicionDocumentoDto definicionDocumento;
	private String mimeType;
	private String documentoBase64;
	private String responseCode ="200";
	private String responseMessage ="El archivo se guardo exitosamente";

	public Long getIdDocumentoMs() {
		return this.idDocumentoMs;
	}

	public void setIdDocumentoMs(Long idDocumentoMs) {
		this.idDocumentoMs = idDocumentoMs;
	}

	public String getArchivo() {
		return this.archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public DefinicionDocumentoDto getDefinicionDocumento() {
		return this.definicionDocumento;
	}

	public void setDefinicionDocumento(DefinicionDocumentoDto definicionDocumento) {
		this.definicionDocumento = definicionDocumento;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getDocumentoBase64() {
		return documentoBase64;
	}

	public void setDocumentoBase64(String documentoBase64) {
		this.documentoBase64 = documentoBase64;
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
	
}