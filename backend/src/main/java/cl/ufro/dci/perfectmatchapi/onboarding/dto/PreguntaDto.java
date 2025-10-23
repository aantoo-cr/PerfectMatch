package cl.ufro.dci.perfectmatchapi.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public final class PreguntaDto {

    private PreguntaDto() {
    }

    @Value
    public static class Crear {
        @NotNull @Positive Long cueId;
        @NotBlank String preEnunciado;
        boolean preObligatoria;
        List<String> alternativas;
    }

    @Value
    @Builder
    public static class Respuesta {
        Long preId;
        String preEnunciado;
        boolean preObligatoria;
        List<AlternativaDto> alternativas;
    }

    @Value
    @Builder
    public static class AlternativaDto {
        Long altId;
        String altOpcion;
    }
}
