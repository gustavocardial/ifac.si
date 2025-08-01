package ifac.si.com.ifac_si_api.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.service.PostService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@Tag(name = "Post", description = "Gerenciamento de Posts")
public class PostController{

    @Autowired
    private PostService servico;

    public PostController(PostService servico) {
        this.servico = servico;
    }

    @GetMapping("/getAllPostsPageable")
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        Page<Post> posts = servico.getAllPosts(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/")
    @Operation(summary = "Listar todos os posts")
    public ResponseEntity<Page<Post>> get(Pageable page) {
        Page<Post> registros = servico.get(page);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public List<Post> getByStatus(@RequestParam EStatus status) {
        List<Post> registros = servico.getByStatus(status);
        return new ResponseEntity<>(registros, HttpStatus.OK).getBody();
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Post>> getByTag(@PathVariable String tag) {
        List<Post> registros = servico.getByTag(tag);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar post pelo ID")
    public ResponseEntity<Post> get(@PathVariable("id") Long id) {
        Post registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca")
    @Operation(summary = "Buscar posts por um termo de busca")
    public ResponseEntity<Page<Post>> get(@RequestParam String termoBusca, Pageable page) {
        Page<Post> registros = servico.get(termoBusca, page);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Operation(summary = "Inserir novo post")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insert(PostRequestDTO objeto, @RequestParam(value = "imagemCapa", required = false) MultipartFile imagemCapaFile, @RequestParam(value = "file", required = false) List<MultipartFile> file) throws Exception {
        
        Post registro = servico.save(objeto, file, imagemCapaFile);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Post> getPostsPorCategoria(@PathVariable Long categoriaId) {
        List<Post> registros = servico.getPostsPorCategoria(categoriaId);
        return new ResponseEntity<>(registros ,HttpStatus.OK).getBody();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um post pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
       servico.delete(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Atualizar post existente")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, PostRequestDTO objeto,
        @RequestParam(value = "imagemCapa", required = false) MultipartFile imagemCapaFile, @RequestParam(value = "file", required = false) List<MultipartFile> file) throws Exception {
        Post registro = servico.update(id, objeto, file, imagemCapaFile);
        return ResponseEntity.ok(registro);
    }

    @DeleteMapping("/{postId}/imagens/{imagemId}")
    public ResponseEntity<Void> removerImagem(
            @PathVariable Long postId,
            @PathVariable Long imagemId) {
        servico.removerImagem(postId, imagemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do post pelo id")
    public ResponseEntity<Post> atualizarStatus(
            @PathVariable Long id,
            @RequestBody EStatus status) {
        Post updatedPost = servico.atualizarStatus(id, status);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping(value = "/{id}/rascunho", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Salvar rascunho baseado em um post existente")
    public ResponseEntity<?> salvarRascunho(
            @PathVariable Long id,
            PostRequestDTO objeto,
            @RequestParam(value = "imagemCapa", required = false) MultipartFile imagemCapaFile,
            @RequestParam(value = "file", required = false) List<MultipartFile> file) throws Exception {

        Post rascunho = servico.salvarRascunho(id, objeto, file, imagemCapaFile);
        return ResponseEntity.ok(rascunho);
    }

    @PostMapping("/{rascunhoId}/publicar")
    @Operation(summary = "Publicar um rascunho como post definitivo")
    public ResponseEntity<?> publicarRascunho(
            @PathVariable Long rascunhoId,
            @RequestBody(required = false) PostRequestDTO objeto) throws Exception {

        Post postPublicado = servico.publicarRascunho(rascunhoId, objeto, null, null);
        return ResponseEntity.ok(postPublicado);
    }

    @PostMapping(value = "/{rascunhoId}/publicar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Publicar um rascunho com atualizações como post definitivo")
    public ResponseEntity<?> publicarRascunhoComAtualizacoes(
            @PathVariable Long rascunhoId,
            PostRequestDTO objeto,
            @RequestParam(value = "imagemCapa", required = false) MultipartFile imagemCapaFile,
            @RequestParam(value = "file", required = false) List<MultipartFile> file) throws Exception {

        Post postPublicado = servico.publicarRascunho(rascunhoId, objeto, file, imagemCapaFile);
        return ResponseEntity.ok(postPublicado);
    }

    @GetMapping("/rascunhos/usuario/{usuarioId}")
    @Operation(summary = "Buscar todos os rascunhos de um usuário")
    public ResponseEntity<List<Post>> getRascunhosPorUsuario(@PathVariable Long usuarioId) {
        List<Post> rascunhos = servico.getRascunhosPorUsuario(usuarioId);
        return ResponseEntity.ok(rascunhos);
    }

    @GetMapping("/{postId}/rascunho")
    @Operation(summary = "Buscar rascunho associado a um post")
    public ResponseEntity<?> getRascunhoDePost(@PathVariable Long postId) {
        Optional<Post> rascunho = servico.getRascunhoDePost(postId);

        if (rascunho.isPresent()) {
            return ResponseEntity.ok(rascunho.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/rascunhos/{rascunhoId}")
    @Operation(summary = "Descartar um rascunho permanentemente")
    public ResponseEntity<?> descartarRascunho(@PathVariable Long rascunhoId) {
        servico.descartarRascunho(rascunhoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rascunhos")
    @Operation(summary = "Listar todos os rascunhos (paginado)")
    public ResponseEntity<Page<Post>> getAllRascunhos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Post> rascunhos = servico.getByStatus(EStatus.RASCUNHO);
        return ResponseEntity.ok((Page<Post>) rascunhos);
    }
    
}
