package cl.ufro.dci.perfectmatchapi.onboarding.repository;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}
