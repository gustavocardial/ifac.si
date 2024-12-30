package ifac.si.com.ifac_si_api.service;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Tag.Mapper.TagMapper;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.repository.TagRepository;

@Service
public class TagService{

    @Autowired
    private TagRepository repo;

    public List<TagDTO> get() {
        return TagMapper.toTagDTOList(repo.findAll());
    }

    public TagDTO get(Long id) {
        return TagMapper.toTagDTO(repo.findById(id).orElse(null));
    }

    public List<TagDTO> getAll(String termoBusca) {
        return TagMapper.toTagDTOList(repo.busca(termoBusca));

        //Ver no repositório porque precisa query, vou montar a estrutura básica primeiro
    }

    public TagDTO save(Tag objeto) {
        return TagMapper.toTagDTO(repo.save(objeto));
    }

    public Tag update(Tag objeto) {
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
