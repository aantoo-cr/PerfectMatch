package cl.ufro.dci.perfectmatchapi.onboarding.service;

import cl.ufro.dci.perfectmatchapi.onboarding.domain.Ubicacion;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.UbicacionDto;
import cl.ufro.dci.perfectmatchapi.onboarding.mapper.OnboardingMapper;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.PerfilRepository;
import cl.ufro.dci.perfectmatchapi.onboarding.repository.UbicacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final PerfilRepository perfilRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository, PerfilRepository perfilRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.perfilRepository = perfilRepository;
    }

    public List<UbicacionDto.Respuesta> listar() {
        return ubicacionRepository.findAll().stream()
                .map(OnboardingMapper::toDto)
                .toList();
    }

    @Transactional
    public UbicacionDto.Respuesta crear(UbicacionDto.Crear crear) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setUbiPais(crear.getUbiPais().trim());
        ubicacion.setUbiRegion(crear.getUbiRegion().trim());
        ubicacion.setUbiCiudad(crear.getUbiCiudad().trim());
        ubicacion.setUbiLatitud(crear.getUbiLatitud());
        ubicacion.setUbiLongitud(crear.getUbiLongitud());
        return OnboardingMapper.toDto(ubicacionRepository.save(ubicacion));
    }

    @Transactional
    public void eliminar(Long ubiId) {
        long usados = perfilRepository.countByPerUbicacion_UbiId(ubiId);
        if (usados > 0) {
            throw new IllegalStateException("Ubicación está asociada a perfiles");
        }
        ubicacionRepository.deleteById(ubiId);
    }
}
