/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author João
 */

@EnableWebSecurity // Habilita o módulo de segurança na aplicação
@Configuration // Indica que essa classe vai possuir configurações do Spring. Assim ao iniciar o projeto, o spring lê as configurações dessa classe
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    // Só de fazer essas configurações, o spring security já bloqueia todas as url's do projeto
}
