/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TokenDTO;
import br.com.alura.forum.config.security.TokenService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author João
 */
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm form) {
        
        try {
            UsernamePasswordAuthenticationToken dadosLogin = form.converter();
            final Authentication authenticate = authenticationManager.authenticate(dadosLogin);
            
            String token = tokenService.gerarToken(authenticate);
            
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
        
    }
    
}
