package ifac.si.com.ifac_si_api.model.Tag.Mapper;

import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TagMapper {

    // Converte de Tag para TagDTO
    public static TagDTO toTagDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getNome());
    }

    // Converte de TagDTO para Tag
    public static Tag toTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setNome(tagDTO.getNome());
        return tag;
    }

    // Converte de Lista<Tag> para Lista<TagDTO>
    public static List<TagDTO> toTagDTOList(List<Tag> tags) {
        return tags.stream()
                .map(TagMapper::toTagDTO)
                .collect(Collectors.toList());
    }

}
