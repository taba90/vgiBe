package it.volpini.vgi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import it.volpini.vgi.security.JWTAuthenticationFilter;
import it.volpini.vgi.security.JWTAuthorizationFilter;
import it.volpini.vgi.security.VgiUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
    VgiUserDetailsService userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/register").anonymous()
                .antMatchers("/model").anonymous()
                .antMatchers("/legenda/findAll").permitAll()
                .antMatchers("/location/new").permitAll()
                .antMatchers("/location/user").permitAll()
                .antMatchers(HttpMethod.POST, "/login").anonymous()
                .antMatchers("/user/delete").hasRole("ADMIN")
                .antMatchers("/place/addOwner").hasRole("OWNER")
                .antMatchers("/user/loggedUser", "product/findShops", "product/getCategories", 
                        "/place/{id}/offers", "/userLocation/**", "/user/createUserLocation", 
                        "/user/getAvailableUsers", "/user/updateUserLocation", 
                        "/user/deleteUserLocation", "/product/reserveOffer", "/product/list",
                        "/estate/getEstateOffer", "/estate", "/image/uploadImage", "/user/addImgUser").authenticated()
                .antMatchers("/product/**", "/place/**", "/user/**", "/place/my", "/offer/**","/estate/**", "/image/**").hasAnyRole("ADMIN", "OWNER")
                .anyRequest().authenticated()
                .and().addFilterBefore(new JWTAuthenticationFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl("/user/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.ACCEPTED)).permitAll();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("X-Vgi");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
