package cl.ufro.dci.perfectmatchapi.onboarding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

public final class CuestionarioDto {

    private CuestionarioDto() {
    }

    @Value
    public static class Crear {
        @NotNull @Positive Long perId;
        String cueNombre;
    }

    @Value
    @Builder
    public static class Respuesta {
        Long cueId;
        String cueNombre;
        LocalDateTime cueFechaCreacion;
        boolean cueActivo;
        Long perId;
        List<PreguntaDto.Respuesta> preguntas;
    }
}
