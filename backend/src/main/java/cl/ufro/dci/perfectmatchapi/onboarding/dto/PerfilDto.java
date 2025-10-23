package cl.ufro.dci.perfectmatchapi.onboarding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class PerfilDto {

    private PerfilDto() {
    }

    @Value
    public static class Crear {
        String perFoto;
        @NotNull @Positive Long perUsuarioId;
        @NotNull @Positive Long ubiId;
    }

    @Value
    @Builder
    public static class Respuesta {
        Long perId;
        Long perUsuarioId;
        String perFoto;
        LocalDate perFechaCreacion;
        LocalDateTime perUltimaActualizacion;
        boolean perEstadoCuestionario;
        Long ubiId;
        String ubiPais;
        String ubiRegion;
        String ubiCiudad;
        Double ubiLatitud;
        Double ubiLongitud;
        Long cueId;
        String cueEstado;
    }
}
