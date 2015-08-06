package com.exadel.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Autowired
    private Http401UnauthorizedEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private AjaxAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select login, password, 'true' as enabled from authentification  where login = ?")
                .authoritiesByUsernameQuery(
                        "select an.login, us.role  from users us join authentification an on us.id = an.user_id where an.login = ?");


    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");

        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .addFilterAfter(new CSRFTokenGeneratorFilter(), CsrfFilter.class)
//                .addFilterAfter(new CSRFCookieGeneratorFilter(), CsrfFilter.class);

        http.csrf().disable();
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
        http.authorizeRequests().antMatchers("/loguser", "/logout", "/login", "/","/training/member").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/users/**").hasAuthority("0")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler)
                .loginProcessingUrl("http://localhost:9000/#/")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .permitAll()
                .logoutSuccessUrl("http://localhost:9000/#/");
//                .and()
//                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());


    }

}