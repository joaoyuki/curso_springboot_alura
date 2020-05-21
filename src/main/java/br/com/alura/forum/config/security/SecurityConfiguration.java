/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author João
 */

@EnableWebSecurity // Habilita o módulo de segurança na aplicação
@Configuration // Indica que essa classe vai possuir configurações do Spring. Assim ao iniciar o projeto, o spring lê as configurações dessa classe
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private AutenticacaoService autenticacaoService;
    
    @Autowired
    private TokenService tokenService;
    
    // Método que configura autenticação (controle de acesso/login)
    // new BCryptPasswordEncoder() é uma classe do spring que já implementa um hash para a senha usando o algorítmo BCrypt
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder())
                ;
    }

    // Configurações de autorização (Quem pode acessar cada URL / perfil de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/topicos").permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                ;
    }

    // Configurações de recursos estáticos (requisiçoes para arquivos js, css, imagens e etc...)
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
    
    
    
    // Código para transformar uma senha qualquer em formato BCrypt
//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }

    @Override
    @Bean // Indica para o spring que esse método devolve o authenticationManager, e assim, conseguimos injetar
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
}
