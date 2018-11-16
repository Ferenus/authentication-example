package com.huro.security;

import com.huro.security.filter.JwtAuthenticationTokenFilter;
import com.huro.security.handler.AjaxAuthenticationFailureHandler;
import com.huro.security.handler.AjaxAuthenticationSuccessHandler;
import com.huro.security.handler.AjaxLogoutSuccessHandler;
import com.huro.security.handler.Http401UnauthorizedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
	private final AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
	private final AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;
	private final Http401UnauthorizedEntryPoint authenticationEntryPoint;
	private final AuthProviderService authProvider;
	private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	public WebSecurityConfig(AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler, AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler, AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler, Http401UnauthorizedEntryPoint authenticationEntryPoint, AuthProviderService authProvider, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
		this.ajaxAuthenticationSuccessHandler = ajaxAuthenticationSuccessHandler;
		this.ajaxAuthenticationFailureHandler = ajaxAuthenticationFailureHandler;
		this.ajaxLogoutSuccessHandler = ajaxLogoutSuccessHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authProvider = authProvider;
		this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
					.antMatchers("/api/users/register").permitAll()
					.antMatchers("/api/authenticate").permitAll()
					.antMatchers("/api/user").permitAll()
					.antMatchers("/").permitAll()
					.antMatchers("/favicon.ico").permitAll()
					.antMatchers("/static/**").permitAll()
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginProcessingUrl("/api/authentication")
					.successHandler(ajaxAuthenticationSuccessHandler)
					.failureHandler(ajaxAuthenticationFailureHandler)
					.usernameParameter("username")
					.passwordParameter("password")
				.and()
					.logout()
					.logoutUrl("/api/logout")
					.logoutSuccessHandler(ajaxLogoutSuccessHandler)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID");

		http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
     
}