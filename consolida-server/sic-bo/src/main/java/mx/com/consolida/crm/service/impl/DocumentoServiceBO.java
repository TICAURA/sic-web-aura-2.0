package mx.com.consolida.crm.service.impl;

import java.io.IOException;
import java.util.Map;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.documento.DocumentoMSDto;

public interface DocumentoServiceBO {
	
	public Long guardarDocumentoCMS(DocumentoCSMDto documento , Map<String,String> contextos) throws IOException ;
	
	public DocumentoMSDto obtenerDocumentoMSDto(Long idCMS);
	
}
