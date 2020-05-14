/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author João
 */

@EnableWebSecurity // Habilita o módulo de segurança na aplicação
@Configuration // Indica que essa classe vai possuir configurações do Spring. Assim ao iniciar o projeto, o spring lê as configurações dessa classe
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    // Método que configura autenticação (controle de acesso/login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    // Configurações de autorização (Quem pode acessar cada URL / perfil de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/topicos").permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .anyRequest().authenticated()
            .and().formLogin()
                ;
    }

    // Configurações de recursos estáticos (requisiçoes para arquivos js, css, imagens e etc...)
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
