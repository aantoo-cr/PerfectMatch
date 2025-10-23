package cl.ufro.dci.perfectmatchapi.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

public final class UbicacionDto {

    private UbicacionDto() {
    }

    @Value
    public static class Crear {
        @NotBlank String ubiPais;
        @NotBlank String ubiRegion;
        @NotBlank String ubiCiudad;
        Double ubiLatitud;
        Double ubiLongitud;
    }

    @Value
    @Builder
    public static class Respuesta {
        Long ubiId;
        String ubiPais;
        String ubiRegion;
        String ubiCiudad;
        Double ubiLatitud;
        Double ubiLongitud;
    }
}
