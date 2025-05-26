package mx.com.consolida.security;

import mx.com.consolida.usuario.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	static final String LOGIN_FORM_URL = "/login.html";
	static final String TARGET_AFTER_SUCCESSFUL_LOGIN_PARAM = "target";
	static final String COLOUR_PARAM = "colour";

	protected CookieSecurityContextRepository cookieSecurityContextRepository;
	protected LoginWithTargetUrlAuthenticationEntryPoint loginWithTargetUrlAuthenticationEntryPoint;
	protected RedirectToOriginalUrlAuthenticationSuccessHandler redirectToOriginalUrlAuthenticationSuccessHandler;
	
	@Autowired
	protected UserService userService;

	protected WebSecurityConfig(CookieSecurityContextRepository cookieSecurityContextRepository,
                                LoginWithTargetUrlAuthenticationEntryPoint loginWithTargetUrlAuthenticationEntryPoint,
                                RedirectToOriginalUrlAuthenticationSuccessHandler redirectToOriginalUrlAuthenticationSuccessHandler) {
		super();
		this.cookieSecurityContextRepository = cookieSecurityContextRepository;
		this.loginWithTargetUrlAuthenticationEntryPoint = loginWithTargetUrlAuthenticationEntryPoint;
		this.redirectToOriginalUrlAuthenticationSuccessHandler = redirectToOriginalUrlAuthenticationSuccessHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// deactivate session creation
				.sessionManagement()
				.maximumSessions(1)
				.expiredUrl(LOGIN_FORM_URL)
				.maxSessionsPreventsLogin(false)
				.and()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)				
				.and().csrf().disable()
				
				// store SecurityContext in Cookie / delete Cookie on logout
				.securityContext()
				.securityContextRepository(cookieSecurityContextRepository).and().logout().permitAll()
				.deleteCookies(SignedUserInfoCookie.NAME)

				// deactivate RequestCache and append originally requested URL as query
				// parameter to login form request
				.and().requestCache().disable().exceptionHandling()
				.authenticationEntryPoint(loginWithTargetUrlAuthenticationEntryPoint)

				// configure form-based login
				.and().formLogin()
				.loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("j_username")
			    .passwordParameter("j_password")
				.loginPage(LOGIN_FORM_URL)
				// after successful log in forward user to originally requested URL
				.successHandler(redirectToOriginalUrlAuthenticationSuccessHandler)
				.and()
				.logout()
				.logoutSuccessUrl(LOGIN_FORM_URL)
				.logoutUrl("/j_spring_security_logout")
				.addLogoutHandler((request, response, auth) -> {
	                for (Cookie cookie : request.getCookies()) {
	                    String cookieName = cookie.getName();
	                    Cookie cookieToDelete = new Cookie(cookieName, null);
	                    cookieToDelete.setMaxAge(0);
	                    response.addCookie(cookieToDelete);
	                }
	            })
				.and().authorizeRequests()
				.antMatchers(LOGIN_FORM_URL,"/registro.html","/assets/**","/bower_components/**","/scripts/**","/images/**","/views/**","/j_spring_security_check","/login","favicon.ico")
				.permitAll()
				.antMatchers("/**").authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(new Md5PasswordEncoder());
		auth.authenticationProvider(provider);
	}

	
	
	
}
