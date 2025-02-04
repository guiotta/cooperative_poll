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
                .antMatchers("/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**",
                        "/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/h2-console/**", "/console/**")
                .permitAll()
                .antMatchers("/api/v1/user/**", "/api/v1/meeting/**", "/api/v1/vote/option/**")
                .permitAll()
                // Qualquer outra requisição deve ser checada
                .anyRequest().authenticated().and().exceptionHandling();
        http.csrf().disable().headers().frameOptions().disable();
    }
}
