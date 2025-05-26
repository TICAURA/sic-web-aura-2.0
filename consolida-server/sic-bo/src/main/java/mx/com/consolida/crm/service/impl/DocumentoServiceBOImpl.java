package mx.com.consolida.crm.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.documento.DocumentoMSDto;

@Service
public class DocumentoServiceBOImpl implements DocumentoServiceBO{


//	@Value("${urlServicioDocumentos}")
//	private String urlMicroservicioDocumentos;// = "http://localhost:8086/documento-service";

//	@Value("${urlZuul}")
//	private String urlZuulServer;
	@Value("${url.ms.base}")
	private String urlBase;
	@Value("${url.documento.service}")
	protected String documentoService;

	public Long guardarDocumentoCMS(DocumentoCSMDto documento , Map<String,String> contextos) throws IOException {

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


	        String subdirectorios = "";
	        int i = 0;
	        for (Map.Entry<String,String> entry : contextos.entrySet())  {

	        	if(i==0) {
	        		subdirectorios = entry.getValue();

	        	}else {
	        		subdirectorios =subdirectorios + ", " + entry.getValue();
	        	}

	        	i+=1;
	        }

	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("nombreArchivo", documento.getDocumentoNuevo().getNombreArchivo());
	        body.add("mimeType", documento.getDocumentoNuevo().getMimeType());
	        body.add("docs", documento.getDocumentoNuevo().getArchivo());

	        //Tiene que ser string
	        body.add("idDefinicionDocumento", documento.getDefinicion().getIdDefinicionDocumento()+"");
	        body.add("contexto", subdirectorios);


	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

	        String serverUrl = urlBase+documentoService + "/guardarDocumento/";

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<DocumentoMSDto> response = restTemplate.postForEntity(serverUrl, requestEntity, DocumentoMSDto.class);

	        if(HttpStatus.OK.name().equals(response.getStatusCode().name())){
	        	return  response.getBody().getIdDocumentoMs();
	        }else {
	        	return null;
	        }
	}

	public DocumentoMSDto obtenerDocumentoMSDto(Long idCMS) {

		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("idCMS", String.valueOf(idCMS));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = urlBase+documentoService + "/obternerDocumentoByIdCMS?idCMS=" +idCMS;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DocumentoMSDto> response = restTemplate.getForEntity(serverUrl, DocumentoMSDto.class);//ForEntity(serverUrl, requestEntity, DocumentoMSDto.class);

        return response.getBody();
	}




}
