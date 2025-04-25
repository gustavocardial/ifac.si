package ifac.si.com.ifac_si_api.model.Post.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EStatus {
    RASCUNHO,
    PUBLICADO,
    ARQUIVADO;


    public static EStatus fromString(String status) {
        if (status == null) {
            return RASCUNHO;
        }

        try {
            return EStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Status inválido. Status permitidos: " +
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
