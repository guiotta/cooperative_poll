package com.otta.cooperative.poll.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.otta.cooperative.poll.security.CustomAuthenticationProvider;

@Configuration
@ComponentScan
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private CustomAuthenticationProvider authenticationProvider;

    //private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    //private final MyUserDetailsService jwtUserDetailsService;
    //private final JwtRequestFilter jwtRequestFilter;

    /*public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            MyUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;

    }*/

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    @Autowired
    public void setAuthenticationProvider(CustomAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                // Não cheque essas requisições
                .authorizeRequests()
                .antMatchers("/api/v1/authentication", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                        "/configuration/**", "/swagger-ui.html", "/webjars/**", "/h2-console/**", "/console/**")
                .permitAll()
                .antMatchers("/api/v1/user/**", "/api/v1/meeting/**", "/api/v1/vote/option/**")
                .permitAll()
                //.antMatchers("/**").hasRole("USER")
                // Qualquer outra requisição deve ser checada
                .anyRequest().authenticated().and().exceptionHandling();
                //.anyRequest().authenticated().and().exceptionHandling();
                //.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable().headers().frameOptions().disable();
    }
}
