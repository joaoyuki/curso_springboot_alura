/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alura.forum;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author João
 */
@Component
public class AgendandoTarefasExemplo {
    
    private static final Logger log = LoggerFactory.getLogger(AgendandoTarefasExemplo.class);
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @CacheEvict(value = "ListaDeTopicos", allEntries = true) // Sempre que esse método for chamado, vai invalidar o cache indicado no value
    @Scheduled(fixedRate = 10000) //É o tempo entre a invocação do método
    public void exibirHorario() {
        log.info("Horário atual é: {}", dateFormat.format(new Date()));
    }
    
}
