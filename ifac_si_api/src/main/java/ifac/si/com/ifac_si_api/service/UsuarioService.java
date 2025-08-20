package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioResponseDTO;
import ifac.si.com.ifac_si_api.model.Usuario.Mapper.UsuarioMapper;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository repo;

    public List<UsuarioResponseDTO> getAll() {
        return UsuarioMapper.toUsuarioResponseDTOList(repo.findAll());
    }

    public UsuarioResponseDTO get(Long id) {
        return UsuarioMapper.toUsuarioResponseDTO(repo.findById(id).orElse(null));
    }

    public List<UsuarioResponseDTO> busca(String termoBusca){
        return UsuarioMapper.toUsuarioResponseDTOList(repo.busca(termoBusca));
    }

    public Usuario save(Usuario objeto) {
        return repo.save(objeto);
    }

    public Usuario update(Usuario objeto) {
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }    
}
