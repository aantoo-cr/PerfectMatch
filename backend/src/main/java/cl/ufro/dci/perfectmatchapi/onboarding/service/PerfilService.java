package cl.ufro.dci.perfectmatchapi.onboarding.service;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Perfil;
import cl.ufro.dci.perfectmatchapi.onboarding.domain.Ubicacion;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PerfilDto;
import cl.ufro.dci.perfectmatchapi.onboarding.mapper.OnboardingMapper;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.CuestionarioRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PerfilRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.UbicacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UbicacionRepository ubicacionRepository;
    private final CuestionarioRepository cuestionarioRepository;

    public PerfilService(PerfilRepository perfilRepository,
                         UbicacionRepository ubicacionRepository,
                         CuestionarioRepository cuestionarioRepository) {
        this.perfilRepository = perfilRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.cuestionarioRepository = cuestionarioRepository;
    }

    public PerfilDto.Respuesta crear(PerfilDto.Crear crear) {
        perfilRepository.findByPerUsuarioId(crear.getPerUsuarioId())
                .ifPresent(p -> {
                    throw new IllegalStateException("El usuario ya tiene un perfil");
                });

        Ubicacion ubicacion = ubicacionRepository.findById(crear.getUbiId())
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no existe"));

        Perfil perfil = new Perfil();
        perfil.setPerUsuarioId(crear.getPerUsuarioId());
        perfil.setPerFoto(crear.getPerFoto());
        perfil.setPerUbicacion(ubicacion);
        perfil.setPerFechaCreacion(LocalDate.now());
        perfil.setPerUltimaActualizacion(LocalDateTime.now());
        perfil.setPerEstadoCuestionario(false);

        return OnboardingMapper.toDto(perfilRepository.save(perfil));
    }

    public List<PerfilDto.Respuesta> listar() {
        return perfilRepository.findAll().stream()
                .map(OnboardingMapper::toDto)
                .toList();
    }

    public PerfilDto.Respuesta obtenerPorUsuario(Long perUsuarioId) {
        return perfilRepository.findByPerUsuarioId(perUsuarioId)
                .map(OnboardingMapper::toDto)
                .orElse(null);
    }

    public PerfilDto.Respuesta obtener(Long perId) {
        Perfil perfil = perfilRepository.findById(perId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no existe"));
        return OnboardingMapper.toDto(perfil);
    }

    public PerfilDto.Respuesta marcarCuestionarioCompleto(Long perId, Long cueId) {
        Perfil perfil = perfilRepository.findById(perId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no existe"));
        Cuestionario cuestionario = cuestionarioRepository.findById(cueId)
                .orElseThrow(() -> new IllegalArgumentException("Cuestionario no existe"));

        perfil.setPerEstadoCuestionario(true);
        perfil.setPerUltimaActualizacion(LocalDateTime.now());
        perfil.setPerCuestionario(cuestionario);
        cuestionario.setCueActivo(false);

        return OnboardingMapper.toDto(perfilRepository.save(perfil));
    }
}
