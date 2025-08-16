package ifac.si.com.ifac_si_api.model.Profissional.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Profissional.Profissional;
import ifac.si.com.ifac_si_api.model.Profissional.DTO.ProfissionalDTO;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
import ifac.si.com.ifac_si_api.model.Tag.Mapper.TagMapper;

public class ProfissionalMapper {

    public static Profissional toEntity(ProfissionalDTO dto) {
        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());
        profissional.setDescricao(dto.getDescricao());
        return profissional;
    }

    public static ProfissionalDTO toProfissionalDTO(Profissional profissional) {
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setNome(profissional.getNome());
        dto.setDescricao(profissional.getDescricao());
        return dto;
    }

    public static void updateEntityFromDTO(ProfissionalDTO dto, Profissional profissional) {
        if (dto.getNome() != null) profissional.setNome(dto.getNome());
        if (dto.getDescricao() != null) profissional.setDescricao(dto.getDescricao());
    }

    public static List<ProfissionalDTO> toProfissionalDTOList(List<Profissional> profissionais) {
        return profissionais.stream()
                .map(ProfissionalMapper::toProfissionalDTO)
                .collect(Collectors.toList());
    }
}
