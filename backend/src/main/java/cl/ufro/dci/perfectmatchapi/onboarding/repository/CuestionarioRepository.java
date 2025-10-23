package cl.ufro.dci.perfectmatchapi.onboarding.repository;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Long> {
}
