package mx.com.consolida.consumer.documentos.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import mx.com.documento.DocumentoMSDto;

public class DocumentosClienteV2 {


	 public static void main(String[] args) throws IOException {
//	        uploadSingleFile();

	        obtenerDocumento();
	    }


	    private static void obtenerDocumento() {

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("idCMS", "410");


	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	        String serverUrl = "http://172.23.1.163:8090/api/documento-service/obternerDocumentoByIdCMS/";
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<DocumentoMSDto> response = restTemplate.postForEntity(serverUrl, requestEntity, DocumentoMSDto.class);
	        System.out.println("Response code: " + response.getStatusCode());

//	        System.out.println("Documento"  +  response.getBody().getArchivo());
	        System.out.println(response.getBody().getMimeType() +  response.getBody().getDocumentoBase64());


	    }

	    private static void uploadSingleFile() throws IOException {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


	        Map<String,String> contextos = new HashMap<String,String>();
	        contextos.put("id_prestadora","124");
	        contextos.put("id_definicion", "1");

	        String valoresIteracion = "";
	        int i = 0;
	        for (Map.Entry<String,String> entry : contextos.entrySet())  {

	        	if(i==0) {
	        		valoresIteracion = entry.getValue();

	        	}else {
	        		valoresIteracion =valoresIteracion + ", " + entry.getValue();
	        	}

	        	i+=1;
	        }

	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("docs", getTestFile());
	        body.add("idDefinicionDocumento", "1");
	        body.add("contexto", valoresIteracion);


	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	        String serverUrl = "http://localhost:8086/documento-service/guardarDocumento/";
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
	        System.out.println("Response code: " + response.getStatusCode());

	    }


	    public static Resource getTestFile() throws IOException {
	        Path testFile = Files.createTempFile("test-file", ".txt");
	        System.out.println("Creating and Uploading Test File: " + testFile);
	        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
	        return new FileSystemResource(new File("/Users/miguellopez/Desktop/VistaPreviaFactura (factura PPD)-1.pdf"));
//	        return new FileSystemResource(testFile.toFile());
	    }
}
