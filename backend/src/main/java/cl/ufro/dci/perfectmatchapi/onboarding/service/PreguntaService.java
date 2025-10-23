package cl.ufro.dci.perfectmatchapi.onboarding.service;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Alternativa;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Pregunta;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PreguntaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.mapper.OnboardingMapper;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.AlternativaRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.CuestionarioRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PreguntaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final AlternativaRepository alternativaRepository;
    private final CuestionarioRepository cuestionarioRepository;

    public PreguntaService(PreguntaRepository preguntaRepository,
                           AlternativaRepository alternativaRepository,
                           CuestionarioRepository cuestionarioRepository) {
        this.preguntaRepository = preguntaRepository;
        this.alternativaRepository = alternativaRepository;
        this.cuestionarioRepository = cuestionarioRepository;
    }

    public PreguntaDto.Respuesta crear(PreguntaDto.Crear crear) {
        Cuestionario cuestionario = cuestionarioRepository.findById(crear.getCueId())
                .orElseThrow(() -> new IllegalArgumentException("Cuestionario no existe"));

        Pregunta pregunta = new Pregunta();
        pregunta.setPreCuestionario(cuestionario);
        pregunta.setPreEnunciado(crear.getPreEnunciado());
        pregunta.setPreObligatoria(crear.isPreObligatoria());
        pregunta = preguntaRepository.save(pregunta);

        if (crear.getAlternativas() != null) {
            for (String opcion : crear.getAlternativas()) {
                Alternativa alternativa = new Alternativa();
                alternativa.setAltOpcion(opcion.trim());
                alternativa.setAltPregunta(pregunta);
                alternativaRepository.save(alternativa);
            }
        }

        pregunta = preguntaRepository.findById(pregunta.getPreId()).orElseThrow();
        return PreguntaDto.Respuesta.builder()
                .preId(pregunta.getPreId())
                .preEnunciado(pregunta.getPreEnunciado())
                .preObligatoria(pregunta.isPreObligatoria())
                .alternativas(pregunta.getPreAlternativas().stream()
                        .map(alt -> PreguntaDto.AlternativaDto.builder()
                                .altId(alt.getAltId())
                                .altOpcion(alt.getAltOpcion())
                                .build())
                        .toList())
                .build();
    }

    public List<PreguntaDto.Respuesta> listarPorCuestionario(Long cueId) {
        Cuestionario cuestionario = cuestionarioRepository.findById(cueId)
                .orElseThrow(() -> new IllegalArgumentException("Cuestionario no existe"));
        return OnboardingMapper.toDto(cuestionario).getPreguntas();
    }
}
