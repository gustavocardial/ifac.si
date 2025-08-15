package ifac.si.com.ifac_si_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Profissional.Profissional;
import ifac.si.com.ifac_si_api.model.Profissional.DTO.ProfissionalDTO;
import ifac.si.com.ifac_si_api.model.Profissional.Mapper.ProfissionalMapper;
import ifac.si.com.ifac_si_api.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository repo;

    @Autowired 
    private MinIOService minIOService;

    public List<Profissional> get() {
        return repo.findAll();
    }

    public Profissional get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<ProfissionalDTO> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public Profissional save(ProfissionalDTO profissionalDTO, MultipartFile imagemPerfil) {
        Profissional profissional = ProfissionalMapper.toEntity(profissionalDTO);

        processarFotoPerfil(imagemPerfil).ifPresent(profissional::setPerfil);
        
        return repo.save(profissional);
    }

    public Profissional update(Profissional objeto) {
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private Optional<Imagem> processarFotoPerfil(MultipartFile capa) {
        if (capa == null) {
            return Optional.empty();
        }

        try {
            // Realiza o upload da imagem da capa para o MinIO
            String fullPath = minIOService.uploadFile(capa);
            // Extrair apenas o nome do arquivo da URL
            String fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);
            String url = minIOService.getFileUrl("imagens", fileName);
    
            // Retorna o objeto Imagem com as informações da imagem da capa
            Imagem imagemPerfil = Imagem.builder()
                    .nomeArquivo(fileName)
                    .url(url)
                    .tamanho(capa.getSize())
                    .dataUpload(LocalDate.now())
                    .build();

            return Optional.of(imagemPerfil);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar capa do post: " + e.getMessage());
        }
    }

}
