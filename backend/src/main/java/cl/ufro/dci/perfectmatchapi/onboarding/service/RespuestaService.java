package cl.ufro.dci.perfectmatchapi.onboarding.service;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Alternativa;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Perfil;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Pregunta;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Respuesta;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.RespuestaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.AlternativaRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.CuestionarioRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PerfilRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PreguntaRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.RespuestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final PerfilRepository perfilRepository;
    private final PreguntaRepository preguntaRepository;
    private final AlternativaRepository alternativaRepository;
    private final CuestionarioRepository cuestionarioRepository;
    private final CuestionarioService cuestionarioService;

    public RespuestaService(RespuestaRepository respuestaRepository,
                            PerfilRepository perfilRepository,
                            PreguntaRepository preguntaRepository,
                            AlternativaRepository alternativaRepository,
                            CuestionarioRepository cuestionarioRepository,
                            CuestionarioService cuestionarioService) {
        this.respuestaRepository = respuestaRepository;
        this.perfilRepository = perfilRepository;
        this.preguntaRepository = preguntaRepository;
        this.alternativaRepository = alternativaRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.cuestionarioService = cuestionarioService;
    }

    public void guardar(RespuestaDto.Guardar guardar) {
        Perfil perfil = perfilRepository.findById(guardar.getPerId())
                .orElseThrow(() -> new IllegalArgumentException("Perfil no existe"));
        Pregunta pregunta = preguntaRepository.findById(guardar.getPreId())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no existe"));
        Cuestionario cuestionario = cuestionarioRepository.findById(guardar.getCueId())
                .orElseThrow(() -> new IllegalArgumentException("Cuestionario no existe"));

        for (Long altId : guardar.getAltIds()) {
            if (respuestaRepository.existsByResPerfil_PerIdAndResPregunta_PreIdAndResAlternativa_AltId(perfil.getPerId(), pregunta.getPreId(), altId)) {
                continue;
            }
            Alternativa alternativa = alternativaRepository.findById(altId)
                    .orElseThrow(() -> new IllegalArgumentException("Alternativa no existe"));

            Respuesta respuesta = new Respuesta();
            respuesta.setResPerfil(perfil);
            respuesta.setResPregunta(pregunta);
            respuesta.setResAlternativa(alternativa);
            respuesta.setResCuestionario(cuestionario);
            respuesta.setResFechaGuardado(LocalDateTime.now());
            respuestaRepository.save(respuesta);
        }

        cuestionarioService.validarCompleto(cuestionario.getCueId());
    }
}
