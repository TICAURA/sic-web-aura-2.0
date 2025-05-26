package mx.com.consolida.usuario;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import mx.com.consolida.usuario.UsuarioMonitorDTO;

public class AjaxAuth extends LoginUrlAuthenticationEntryPoint
   implements AuthenticationFailureHandler, AuthenticationSuccessHandler {

   private static final String UTF_8 = StandardCharsets.UTF_8.name();

   private static Logger logger = LoggerFactory.getLogger(AjaxAuth.class);

//   @Value("${captcha.privateKey}")
   private String captchaPrivateKey;

//   @Value("${captcha.publicKey}")
   private String captchaPublicKey;

//   @Value("${recaptcha.url}")
   private String reCaptchaURL;

//   @Value("${captcha.enabled}")
   private Boolean enableCaptcha  = true;

   @Autowired
   private RestTemplate restTemplate;

   public AjaxAuth(final String loginFormUrl) {
      super(loginFormUrl);
   }

   @PostConstruct
   public void printConfig() {
      logger.trace("captchaPrivateKey: {}", captchaPrivateKey);
      logger.trace("captchaPublicKey: {}", captchaPublicKey);
      logger.trace("reCaptchaURL: {}", reCaptchaURL);
      logger.trace("enableCaptcha: {}", enableCaptcha);
   }

   @Override
   public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException authException) throws IOException, ServletException {

      if (request.getRequestURI().endsWith("json")) {
         response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      } else {
         super.commence(request, response, authException);
      }
   }

   @Override
   public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException exception) throws IOException, ServletException {

      if (exception instanceof LockedException) {
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      } else {
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
   }

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

      final UsuarioMonitorDTO usuario = (UsuarioMonitorDTO) authentication.getPrincipal();

      final String correoUsuario = usuario.getUsuario();

      if (enableCaptcha && !isCaptchaResponseValid(request.getHeader("captcha.response"))) {
         final String error = "Imposible ingresar: " + correoUsuario;
         logger.error(error);
         logout(request, response);
         return;
      }

      logger.trace("{} logged in", correoUsuario);
   }

   private boolean isCaptchaResponseValid(final String captchaResponse) {

      boolean validCaptchaResponse = false;

      if (captchaResponse != null) {

         final LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

         try {

            parameters.add("secret", URLEncoder.encode(captchaPrivateKey, UTF_8));
            parameters.add("response", URLEncoder.encode(captchaResponse, UTF_8));

            final String validationResponse = restTemplate.postForObject(reCaptchaURL, parameters, String.class);

            if (StringUtils.isEmpty(validationResponse) || validationResponse.contains("error-codes")) {
               logger.error(validationResponse);
            } else {
               validCaptchaResponse = true;
            }

         } catch (Exception e) {
            logger.error(e.getMessage(), e);
         }
      }

      return validCaptchaResponse;
   }

   private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {

      SecurityContextHolder.clearContext();

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      if (request != null) {

         final HttpSession session = request.getSession(false);

         if (session != null) {
            session.invalidate();
         }

         request.logout();
      }
   }
}
