package cl.ufro.dci.perfectmatchapi.onboarding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.util.List;

public final class RespuestaDto {

    private RespuestaDto() {
    }

    @Value
    public static class Guardar {
        @NotNull @Positive Long perId;
        @NotNull @Positive Long cueId;
        @NotNull @Positive Long preId;
        @NotNull List<@Positive Long> altIds;
    }
}
