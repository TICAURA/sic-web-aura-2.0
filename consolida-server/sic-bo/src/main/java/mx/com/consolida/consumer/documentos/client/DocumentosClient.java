package mx.com.consolida.consumer.documentos.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.com.consolida.dto.DocumentoRegistroDto;
import mx.com.consolida.util.DocumentosConstants;

public class DocumentosClient {

	public List<DocumentoRegistroDto> saveDocs(String endPoint, HttpEntity entity) {
		StringBuilder uri = new StringBuilder();
		uri.append(DocumentosConstants.URI_DOCUMENTOS_API);
		uri.append(endPoint);

		HttpPost httpPost = new HttpPost(uri.toString());
		List<DocumentoRegistroDto> responseDocs = new ArrayList<>();
		Gson gson = new Gson();
		httpPost.setEntity(entity);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(httpPost);
			HttpEntity result = response.getEntity();

			Type collectionType = new TypeToken<Collection<DocumentoRegistroDto>>() {
			}.getType();
			String responseString = EntityUtils.toString(result, "UTF-8");
			return responseDocs = gson.fromJson(responseString, collectionType);
		} catch (IOException e) {
			e.printStackTrace();
			return responseDocs;
		}
	}

	public DocumentoRegistroDto getById(Long idDoc) {
		Gson gson = new Gson();
		StringBuilder uri = new StringBuilder();
		uri.append(DocumentosConstants.URI_DOCUMENTOS_API);
		uri.append(DocumentosConstants.GET_PARAM);
		if (idDoc <= 0)
			return null;
		uri.append(idDoc);
		HttpGet httpGet = new HttpGet(uri.toString());
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response;
		try {
			response = client.execute(httpGet);
			HttpEntity result = response.getEntity();

			String responseString = EntityUtils.toString(result, "UTF-8");
			DocumentoRegistroDto responseDocs = gson.fromJson(responseString, DocumentoRegistroDto.class);
			return responseDocs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String deleteDoc(Long idDoc) {
		StringBuilder uri = new StringBuilder();
		uri.append(DocumentosConstants.URI_DOCUMENTOS_API);
		uri.append(DocumentosConstants.DELETE_PARAM);
		if (idDoc <= 0)
			return null;
		uri.append(idDoc);
		HttpDelete httpDel = new HttpDelete(uri.toString());
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response;
		try {
			response = client.execute(httpDel);
			return response.getStatusLine().toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public DocumentoRegistroDto updateDoc(Long idDoc, HttpEntity entity) {
		Gson gson = new Gson();
		StringBuilder uri = new StringBuilder();
		uri.append(DocumentosConstants.URI_DOCUMENTOS_API);
		uri.append(DocumentosConstants.UPDATE_DOC);
		
		HttpPost httpPost = new HttpPost(uri.toString());
		httpPost.setEntity(entity);
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(httpPost);
			HttpEntity result = response.getEntity();

			Type collectionType = new TypeToken<DocumentoRegistroDto>() {}.getType();
			String responseString;
			responseString = EntityUtils.toString(result, "UTF-8");
			DocumentoRegistroDto responseDocs = gson.fromJson(responseString, collectionType);
			return responseDocs;
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
