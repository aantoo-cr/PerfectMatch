package cl.ufro.dci.perfectmatchapi.onboarding.service;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Perfil;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Pregunta;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.CuestionarioDto;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PreguntaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.mapper.OnboardingMapper;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.CuestionarioRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PerfilRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.RespuestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CuestionarioService {

    private final CuestionarioRepository cuestionarioRepository;
    private final PerfilRepository perfilRepository;
    private final RespuestaRepository respuestaRepository;
    private final PreguntaService preguntaService;

    public CuestionarioService(CuestionarioRepository cuestionarioRepository,
                               PerfilRepository perfilRepository,
                               RespuestaRepository respuestaRepository,
                               PreguntaService preguntaService) {
        this.cuestionarioRepository = cuestionarioRepository;
        this.perfilRepository = perfilRepository;
        this.respuestaRepository = respuestaRepository;
        this.preguntaService = preguntaService;
    }

    public CuestionarioDto.Respuesta crear(CuestionarioDto.Crear crear) {
        Perfil perfil = perfilRepository.findById(crear.getPerId())
                .orElseThrow(() -> new IllegalArgumentException("Perfil no existe"));

        Cuestionario cuestionario = new Cuestionario();
        cuestionario.setCuePerfil(perfil);
        cuestionario.setCueNombre(crear.getCueNombre() != null ? crear.getCueNombre() : "Cuestionario de perfil");
        cuestionario.setCueFechaCreacion(LocalDateTime.now());
        cuestionario.setCueActivo(true);

        Cuestionario guardado = cuestionarioRepository.save(cuestionario);
        perfil.setPerCuestionario(guardado);
        perfilRepository.save(perfil);

        registrarPreguntasBase(guardado.getCueId());

        return OnboardingMapper.toDto(guardado);
    }

    private void registrarPreguntasBase(Long cueId) {
        DEFAULT_PREGUNTAS.forEach(pregunta ->
                preguntaService.crear(new PreguntaDto.Crear(cueId, pregunta.enunciado(), pregunta.obligatoria(), pregunta.alternativas())));
    }

    public List<CuestionarioDto.Respuesta> listar() {
        return cuestionarioRepository.findAll().stream()
                .map(OnboardingMapper::toDto)
                .toList();
    }

    public boolean validarCompleto(Long cueId) {
        Cuestionario cuestionario = cuestionarioRepository.findById(cueId)
                .orElseThrow(() -> new IllegalArgumentException("Cuestionario no existe"));

        long obligatorias = cuestionario.getCuePreguntas().stream()
                .filter(Pregunta::isPreObligatoria)
                .count();
        long respondidas = respuestaRepository.countByResCuestionario_CueIdAndResPregunta_PreObligatoriaTrue(cueId);

        if (obligatorias > 0 && respondidas >= obligatorias) {
            cuestionario.setCueActivo(false);
            cuestionarioRepository.save(cuestionario);
            Perfil perfil = cuestionario.getCuePerfil();
            perfil.setPerEstadoCuestionario(true);
            perfil.setPerCuestionario(cuestionario);
            perfilRepository.save(perfil);
            return true;
        }
        return obligatorias == 0 && respondidas == 0;
    }
    private record PreguntaBase(String enunciado, boolean obligatoria, List<String> alternativas) {
    }

    private static final List<PreguntaBase> DEFAULT_PREGUNTAS = List.of(
            new PreguntaBase("¿Qué tipo de actividades disfrutas más en tu tiempo libre?", true,
                    List.of("Hacer deporte o actividades al aire libre", "Leer o ver series/películas", "Pasar tiempo con amigos o familia")),
            new PreguntaBase("¿Cómo prefieres pasar un fin de semana ideal?", true,
                    List.of("Descansando en casa", "Explorando nuevos lugares", "Compartiendo con amigos")),
            new PreguntaBase("¿Qué tan importante es para ti la puntualidad?", true,
                    List.of("Es esencial, siempre llego a la hora", "Trato de ser puntual, pero no me estreso", "No me preocupa demasiado")),
            new PreguntaBase("¿Te consideras una persona más introvertida o extrovertida?", true,
                    List.of("Introvertida/o", "Extrovertida/o", "Depende del contexto")),
            new PreguntaBase("¿Qué te motiva más en la vida diaria?", true,
                    List.of("Lograr metas personales", "Ayudar a otros", "Aprender cosas nuevas")),
            new PreguntaBase("¿Qué tipo de música escuchas con mayor frecuencia?", false,
                    List.of("Pop o música comercial", "Rock o alternativo", "Instrumental o relajante")),
            new PreguntaBase("¿Cómo manejas los conflictos interpersonales?", true,
                    List.of("Intento dialogar y resolver pacíficamente", "Evito el conflicto siempre que puedo", "Enfrento el problema directamente")),
            new PreguntaBase("¿Qué valoras más en una amistad o relación?", true,
                    List.of("La confianza y sinceridad", "El sentido del humor", "El apoyo mutuo y la empatía")),
            new PreguntaBase("¿Te sientes más productivo/a en la mañana o en la noche?", false,
                    List.of("Definitivamente en la mañana", "A cualquier hora del día", "Más activo/a en la noche")),
            new PreguntaBase("¿Qué lugar elegirías para unas vacaciones perfectas?", false,
                    List.of("Una playa tranquila", "Una ciudad llena de cultura", "Una montaña o paisaje natural"))
    );
}
