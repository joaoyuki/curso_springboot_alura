package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
        //@RequestParam Indicamos par ao spring que esse parâmetro vem da URL e é obrigatório
	@GetMapping
        
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, 
                @RequestParam int pagina, 
                @RequestParam int quantidade,
                @RequestParam String nomeDoCampoParaOrdenacao) {
            System.out.println("METODO LISTA");
        return teste(pagina, quantidade, nomeDoCampoParaOrdenacao, nomeCurso);
	}

        @Cacheable(value = "ListaDeTopicos") // No value colocamos um indicador único para esse cache
    public Page<TopicoDto> teste(int pagina, int quantidade, String nomeDoCampoParaOrdenacao, String nomeCurso) {
        // Pageble é uma interface do spring
        Pageable paginacao = PageRequest.of(pagina, quantidade, Sort.Direction.ASC, nomeDoCampoParaOrdenacao);
        System.out.println("METODO TESTE");
        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }

        // Nesse exemplo, passamos direto um Pageable do spring e temos que habilitar na classe ForumApplication essa funcionalidade
        @GetMapping("/exemplo02")
        public Page<TopicoDto> listaUsandoPagebleNoRequest(
                @PageableDefault(sort = "id") Pageable paginacao) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);            
        }
	
	@PostMapping
	@Transactional
        @CacheEvict(value = "ListaDeTopicos", allEntries = true) // Sempre que esse método for chamado, vai invalidar o cache indicado no value
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
        @CacheEvict(value = "ListaDeTopicos", allEntries = true) // Sempre que esse método for chamado, vai invalidar o cache indicado no value
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
        @CacheEvict(value = "ListaDeTopicos", allEntries = true) // Sempre que esse método for chamado, vai invalidar o cache indicado no value
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}







