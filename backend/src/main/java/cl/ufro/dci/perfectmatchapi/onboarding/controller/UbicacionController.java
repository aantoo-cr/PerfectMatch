package cl.ufro.dci.perfectmatchapi.onboarding.controller;

import cl.ufro.dci.perfectmatchapi.onboarding.dto.UbicacionDto;
import cl.ufro.dci.perfectmatchapi.onboarding.service.UbicacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding/ubicaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class UbicacionController {

    private final UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping
    public List<UbicacionDto.Respuesta> listar() {
        return ubicacionService.listar();
    }

    @PostMapping
    public ResponseEntity<UbicacionDto.Respuesta> crear(@Valid @RequestBody UbicacionDto.Crear crear) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionService.crear(crear));
    }

    @DeleteMapping("/{ubiId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long ubiId) {
        ubicacionService.eliminar(ubiId);
        return ResponseEntity.noContent().build();
    }
}
