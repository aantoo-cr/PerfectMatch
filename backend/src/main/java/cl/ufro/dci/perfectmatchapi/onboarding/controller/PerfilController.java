package cl.ufro.dci.perfectmatchapi.onboarding.controller;

import cl.ufro.dci.perfectmatchapi.onboarding.dto.PerfilDto;
import cl.ufro.dci.perfectmatchapi.onboarding.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding/perfiles")
@CrossOrigin(origins = "http://localhost:4200")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<PerfilDto.Respuesta> crear(@Valid @RequestBody PerfilDto.Crear crear) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.crear(crear));
    }

    @GetMapping
    public List<PerfilDto.Respuesta> listar() {
        return perfilService.listar();
    }

    @GetMapping("/{perId}")
    public PerfilDto.Respuesta obtener(@PathVariable Long perId) {
        return perfilService.obtener(perId);
    }

    @GetMapping("/usuario/{perUsuarioId}")
    public ResponseEntity<PerfilDto.Respuesta> obtenerPorUsuario(@PathVariable Long perUsuarioId) {
        PerfilDto.Respuesta respuesta = perfilService.obtenerPorUsuario(perUsuarioId);
        return respuesta != null ? ResponseEntity.ok(respuesta) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{perId}/cuestionario/{cueId}")
    public PerfilDto.Respuesta marcarCuestionario(@PathVariable Long perId, @PathVariable Long cueId) {
        return perfilService.marcarCuestionarioCompleto(perId, cueId);
    }
}
