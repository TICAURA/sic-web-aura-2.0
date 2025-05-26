package mx.com.consolida.controller.stp;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.apache.cxf.frontend.ClientProxy;
//import org.apache.cxf.transport.http.HTTPConduit;
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;
import com.lgec.enlacefi.spei.integration.h2h.SpeiActualizaServices;
import com.lgec.enlacefi.spei.integration.h2h.SpeiActualizaServicesImpl;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceResponse;
import com.lgec.enlacefi.spei.integration.h2h.SpeiServiceWrapperResponse;

public class Main {
    private static String url="http://spei/webservices/SpeiActualizaServices";
    private static int connectionTimeout=5000;
    private static int receptionTimeout=15000;
    public static void main(String[] args) throws Exception {
        new Main().enviaPago();
    }
    private void enviaPago() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
        Client client;
        OrdenPagoWS ordenPagoWS=new OrdenPagoWS();
        ordenPagoWS.setEmpresa("FREYXENET");
        ordenPagoWS.setMonto(new BigDecimal("0.01"));
        ordenPagoWS.setInstitucionContraparte(846);
        ordenPagoWS.setClaveRastreo("Ras0011");
        ordenPagoWS.setReferenciaNumerica(1234567);
        ordenPagoWS.setNombreBeneficiario("Miguel Lopez");
        ordenPagoWS.setConceptoPago("Pago de prueba");
        ordenPagoWS.setCuentaBeneficiario("846180123400000001");
        ordenPagoWS.setTipoCuentaBeneficiario(40);
        ordenPagoWS.setTipoPago(1);
        ordenPagoWS.setCuentaOrdenante("646180146700000001");
        ordenPagoWS.setTipoCuentaOrdenante(40);
        ordenPagoWS.setTopologia("V");
        ordenPagoWS.setRfcCurpBeneficiario("LOJM860616H3A");
        ordenPagoWS.setInstitucionOperante(90646);	
        ordenPagoWS.setFirma(new CryptoHandler().firmar(ordenPagoWS));
        
        Gson gson = new Gson();
        String JSON = gson.toJson(ordenPagoWS);
        System.out.println("JSON: "+JSON);
        

        RestTemplate rs = new RestTemplateConfig().restTemplate();
        
        HttpEntity<OrdenPagoWS> request = new HttpEntity<>(ordenPagoWS);
        ResponseEntity<SpeiServiceWrapperResponse> response= rs.exchange("https://demo.stpmex.com:7024/speiws/rest/ordenPago/registra", HttpMethod.PUT, request, SpeiServiceWrapperResponse.class);
        System.out.println("resultado --> " + response.getBody());
        SpeiServiceWrapperResponse  speiServiceResponse =response.getBody();
        System.out.println("id: "+speiServiceResponse.getResultado().getId());
        System.out.println("Descripcion: "+speiServiceResponse.getResultado().getDescripcionError());
        
    }
    
    
    private static HttpComponentsClientHttpRequestFactory useApacheHttpClientWithSelfSignedSupport() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory useApacheHttpClient = new HttpComponentsClientHttpRequestFactory();
		useApacheHttpClient.setHttpClient(httpClient);
		return useApacheHttpClient;
	}
    
}


