package ifac.si.com.ifac_si_api.model.Usuario.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Post.Enum.EVisibilidade;

public enum ECargo {
    AUTOR,
    EDITOR,
    ADMIN;

    public static ECargo fromString(String cargo) {
        try {
            return ECargo.valueOf(cargo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Dado de visibilidade inválido. visibilidades permitidas: " +
                            Arrays.toString(ECargo.values())
            );
        }
    }

    public static List<String> getCargosPermitidos() {
        return Arrays.stream(ECargo.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
