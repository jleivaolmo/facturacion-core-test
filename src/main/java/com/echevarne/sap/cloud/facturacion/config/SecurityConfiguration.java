
package com.echevarne.sap.cloud.facturacion.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.echevarne.sap.cloud.facturacion.config.MicrosConfig.SecretData;

import lombok.extern.slf4j.Slf4j;
import static com.echevarne.sap.cloud.facturacion.config.MicrosConfig.Param.*;

@Configuration
@Slf4j
@Profile("!test")
public class SecurityConfiguration {
	
	@Autowired
	private HeaderCheckerFilter headerCheckerFilter; 
	
	@Bean
	public UserDetailsService userDetailsService(MicrosConfig conf) {
		UserDetails singleUser;
		try {
			SecretData auth=conf.getSecret(MicrosConfig.Secret.MICROSERVICES_BASICAUTH);
			singleUser = User.builder()
					.username(auth.getUserName())
					.password("{noop}" + auth.getValue())
					.roles("USER").build();
		} catch (Exception e) {
			log.error(this.getClass().getSimpleName() + "::userDetailsService():" + e.getMessage());
			throw new RuntimeException(e);
		}
		return new InMemoryUserDetailsManager(singleUser);
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF protection
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests((authz) -> authz
 //               .anyRequest().authenticated()
                  .anyRequest().permitAll()     // Se permite sin autenticación, pero a través
                                                // de authCheckerFilter, deja rastro en logs y
                                                // zipkin, para detectar si no se está enviando
                                                // la cabecera con la autenticación
            )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(headerCheckerFilter, BasicAuthenticationFilter.class);        
        return http.build();
    }
/**    
/** En PRODUCCIÓN y PRE emplear este bloque en vez del anterior, para exigir autenticación
 *  y eliminar el filtro authCheckerFilter
 *
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable() // Disable CSRF protection 
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeHttpRequests((authz) -> authz
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults())   
//        return http.build();
//    }    
 * 
 */ 
}
