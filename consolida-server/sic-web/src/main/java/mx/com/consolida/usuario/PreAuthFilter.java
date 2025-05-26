package mx.com.consolida.usuario;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.consolida.generico.BusinessException;

public class PreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

   private static final String P_ATTR = "password";
   private static final String U_ATTR = "username";

   private static Logger preAuthFilterLogger = LoggerFactory.getLogger(PreAuthFilter.class);

   @Override
   protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {

      process(request);

      return request.getAttribute(U_ATTR);
   }

   @Override
   protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {

      process(request);

      return request.getAttribute(P_ATTR);
   }

   private void process(final HttpServletRequest request) {

      if (request.getAttribute(U_ATTR) == null) {

         try {

            final String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.toString());

//            final JsonNode tree = new ObjectMapper().readTree(body);

//            request.setAttribute(U_ATTR, tree.findValue(U_ATTR).asText());
            request.setAttribute(U_ATTR, "monteromalj@gmail.com");

            final String pwd = new String(Base64.decodeBase64("12345678"), StandardCharsets.UTF_8);

            request.setAttribute(P_ATTR, "12345678");//URLDecoder.decode("12345678", StandardCharsets.UTF_8.toString()));

         } catch (Exception e) {
            preAuthFilterLogger.error(e.getMessage(), e);
            throw new BusinessException(e);
         }
      }
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      super.doFilter(request, response, chain);
   }
}
