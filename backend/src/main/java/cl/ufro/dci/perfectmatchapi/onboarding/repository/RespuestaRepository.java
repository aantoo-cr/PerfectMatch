package cl.ufro.dci.perfectmatchapi.onboarding.repository;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    long countByResCuestionario_CueIdAndResPregunta_PreObligatoriaTrue(Long cueId);
    boolean existsByResPerfil_PerIdAndResPregunta_PreIdAndResAlternativa_AltId(Long perId, Long preId, Long altId);
}
