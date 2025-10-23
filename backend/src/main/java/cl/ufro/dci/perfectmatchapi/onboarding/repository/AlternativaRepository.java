package cl.ufro.dci.perfectmatchapi.onboarding.repository;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Alternativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlternativaRepository extends JpaRepository<Alternativa, Long> {
    List<Alternativa> findByAltPregunta_PreId(Long preId);
}
