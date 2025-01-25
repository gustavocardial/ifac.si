package ifac.si.com.ifac_si_api.controller;

import java.awt.print.Pageable;
import java.util.List;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Post>> get() {
        List<Post> registros = servico.get();
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
    public ResponseEntity<List<Post>> get(@RequestParam String termoBusca) {
        List<Post> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Operation(summary = "Inserir novo post")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insert(PostRequestDTO objeto, @RequestParam(value = "file", required = false) List<MultipartFile> file) throws Exception {
        Post registro = servico.save(objeto, file);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Post> getPostsPorCategoria(@PathVariable Long categoriaId) {
        List<Post> registros = servico.getPostsPorCategoria(categoriaId);
        return new ResponseEntity<>(registros ,HttpStatus.OK).getBody();
    }


//    @PutMapping("/")
//    public ResponseEntity<Post> update(Post objeto) {
//        return null;
//    }

//    @Override
//    @PutMapping("/{postId}")
//    @Operation(summary = "Atualizar um post existente")
//    public ResponseEntity<Post> updatePost(
//            @PathVariable Long postId,         // ID do post a ser atualizado
//            @RequestBody PostRequestDTO postDto, // Dados atualizados do post
//            @RequestParam(required = false) List<MultipartFile> imagens) throws Exception {  // Imagens opcionais
//
//        // Chama o serviço para atualizar o post
////        Post updatedPost = servico.update(postId, postDto, imagens);
//
//        // Retorna o post atualizado com status OK
////        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
//        return null;
//    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um post pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
//        servico.delete(id);
//        return new ResponseEntity<>(HttpStatus.OK);

    return null;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um post existente")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestPart PostRequestDTO postDto,
                                           @RequestPart(required = false) List<MultipartFile> imagens) {
        Post updatedPost = servico.update(id, postDto, imagens);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}/imagens/{imagemId}")
    public ResponseEntity<Void> removerImagem(
            @PathVariable Long postId,
            @PathVariable Long imagemId) {
        servico.removerImagem(postId, imagemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Post> atualizarStatus(
            @PathVariable Long id,
            @RequestBody EStatus status) {
        Post updatedPost = servico.atualizarStatus(id, status);
        return ResponseEntity.ok(updatedPost);
    }
    
}
