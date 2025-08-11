package ifac.si.com.ifac_si_api.model.Post.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EVisibilidade {
    PUBLICO,
    PRIVADO,
    RESTRITA;

    public static EVisibilidade fromString(String visibilidade) {
        if (visibilidade == null) {
            return PRIVADO;
        }

        try {
            return EVisibilidade.valueOf(visibilidade.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Dado de visibilidade inválido. visibilidades permitidas: " +
                            Arrays.toString(EVisibilidade.values())
            );
        }
    }

    public static List<String> getVisibilidadePermitidas() {
        return Arrays.stream(EVisibilidade.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
