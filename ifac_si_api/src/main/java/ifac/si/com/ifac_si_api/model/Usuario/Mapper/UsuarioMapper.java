package ifac.si.com.ifac_si_api.model.Usuario.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
import ifac.si.com.ifac_si_api.model.Tag.Mapper.TagMapper;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioDTO;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioResponseDTO;
import ifac.si.com.ifac_si_api.model.Usuario.Enum.ECargo;

public class UsuarioMapper {
    public static UsuarioResponseDTO toUsuarioResponseDTO (Usuario usuario) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setNomeUsuario(usuario.getNomeUsuario());
        usuarioResponseDTO.setEmail(usuario.getEmail());
        usuarioResponseDTO.setCargo(usuario.getCargo().name());
        usuarioResponseDTO.setAtivo(usuario.isAtivo());
        usuarioResponseDTO.setId(usuario.getId());
        return usuarioResponseDTO;
    }

    // Converte de TagDTO para Tag
    public static Usuario toUsuario(UsuarioResponseDTO usuarioResponseDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioResponseDTO.getId());
        usuario.setNomeUsuario(usuarioResponseDTO.getNomeUsuario());
        usuario.setCargo(ECargo.fromString(usuarioResponseDTO.getCargo()));
        usuario.setAtivo(usuarioResponseDTO.isAtivo());
        return usuario;
    }

    // Converte de Lista<Tag> para Lista<TagDTO>
    public static List<UsuarioResponseDTO> toUsuarioResponseDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioMapper::toUsuarioResponseDTO)
                .collect(Collectors.toList());
    }
}
