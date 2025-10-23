package cl.ufro.dci.perfectmatchapi.onboarding.mapper;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Alternativa;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Perfil;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Pregunta;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Ubicacion;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.CuestionarioDto;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PerfilDto;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PreguntaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.UbicacionDto;

import java.util.Comparator;
import java.util.List;

public final class OnboardingMapper {

    private OnboardingMapper() {
    }

    public static UbicacionDto.Respuesta toDto(Ubicacion ubicacion) {
        return UbicacionDto.Respuesta.builder()
                .ubiId(ubicacion.getUbiId())
                .ubiPais(ubicacion.getUbiPais())
                .ubiRegion(ubicacion.getUbiRegion())
                .ubiCiudad(ubicacion.getUbiCiudad())
                .ubiLatitud(ubicacion.getUbiLatitud())
                .ubiLongitud(ubicacion.getUbiLongitud())
                .build();
    }

    public static PerfilDto.Respuesta toDto(Perfil perfil) {
        return PerfilDto.Respuesta.builder()
                .perId(perfil.getPerId())
                .perUsuarioId(perfil.getPerUsuarioId())
                .perFoto(perfil.getPerFoto())
                .perFechaCreacion(perfil.getPerFechaCreacion())
                .perUltimaActualizacion(perfil.getPerUltimaActualizacion())
                .perEstadoCuestionario(perfil.isPerEstadoCuestionario())
                .ubiId(perfil.getPerUbicacion().getUbiId())
                .ubiPais(perfil.getPerUbicacion().getUbiPais())
                .ubiRegion(perfil.getPerUbicacion().getUbiRegion())
                .ubiCiudad(perfil.getPerUbicacion().getUbiCiudad())
                .ubiLatitud(perfil.getPerUbicacion().getUbiLatitud())
                .ubiLongitud(perfil.getPerUbicacion().getUbiLongitud())
                .cueId(perfil.getPerCuestionario() != null ? perfil.getPerCuestionario().getCueId() : null)
                .build();
    }

    public static CuestionarioDto.Respuesta toDto(Cuestionario cuestionario) {
        return CuestionarioDto.Respuesta.builder()
                .cueId(cuestionario.getCueId())
                .cueNombre(cuestionario.getCueNombre())
                .cueFechaCreacion(cuestionario.getCueFechaCreacion())
                .cueActivo(cuestionario.isCueActivo())
                .perId(cuestionario.getCuePerfil() != null ? cuestionario.getCuePerfil().getPerId() : null)
                .preguntas(mapPreguntas(cuestionario.getCuePreguntas()))
                .build();
    }

    private static List<PreguntaDto.Respuesta> mapPreguntas(List<Pregunta> preguntas) {
        return preguntas.stream()
                .sorted(Comparator.comparing(Pregunta::getPreId))
                .map(pregunta -> PreguntaDto.Respuesta.builder()
                        .preId(pregunta.getPreId())
                        .preEnunciado(pregunta.getPreEnunciado())
                        .preObligatoria(pregunta.isPreObligatoria())
                        .alternativas(pregunta.getPreAlternativas().stream()
                                .sorted(Comparator.comparing(Alternativa::getAltId))
                                .map(alternativa -> PreguntaDto.AlternativaDto.builder()
                                        .altId(alternativa.getAltId())
                                        .altOpcion(alternativa.getAltOpcion())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }
}
