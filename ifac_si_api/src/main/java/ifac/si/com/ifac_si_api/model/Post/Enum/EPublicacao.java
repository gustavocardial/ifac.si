package ifac.si.com.ifac_si_api.model.Post.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EPublicacao {
    IMEDIATA,
    AGENDADA,
    RESTRITA;

    public static EPublicacao fromString(String publicacao) {
        if (publicacao == null) {
            return IMEDIATA;
        }

        try {
            return EPublicacao.valueOf(publicacao.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Dado de visibilidade inválido. Status permitidos: " +
                            Arrays.toString(EStatus.values())
            );
        }
    }

    public static List<String> getStatusPermitidos() {
        return Arrays.stream(EStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
