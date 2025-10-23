package cl.ufro.dci.perfectmatchapi.onboarding.repository;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByPerUsuarioId(Long perUsuarioId);
    long countByPerUbicacion_UbiId(Long ubiId);
}
