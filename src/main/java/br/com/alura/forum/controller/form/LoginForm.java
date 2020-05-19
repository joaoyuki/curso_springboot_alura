/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author João
 */
public class LoginForm {
    
    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;
    
    @NotEmpty(message = "A senha é obrigatória")
    private String senha;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
    
    
    
}
