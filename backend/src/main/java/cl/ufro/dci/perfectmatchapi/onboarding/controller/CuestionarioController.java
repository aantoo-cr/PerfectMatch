package cl.ufro.dci.perfectmatchapi.onboarding.controller;

import cl.ufro.dci.perfectmatchapi.onboarding.dto.CuestionarioDto;
import cl.ufro.dci.perfectmatchapi.onboarding.dto.PreguntaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.service.CuestionarioService;
import cl.ufro.dci.perfectmatchapi.onboarding.service.PreguntaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding/cuestionarios")
@CrossOrigin(origins = "http://localhost:4200")
public class CuestionarioController {

    private final CuestionarioService cuestionarioService;
    private final PreguntaService preguntaService;

    public CuestionarioController(CuestionarioService cuestionarioService, PreguntaService preguntaService) {
        this.cuestionarioService = cuestionarioService;
        this.preguntaService = preguntaService;
    }

    @PostMapping
    public ResponseEntity<CuestionarioDto.Respuesta> crear(@Valid @RequestBody CuestionarioDto.Crear crear) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioService.crear(crear));
    }

    @GetMapping
    public List<CuestionarioDto.Respuesta> listar() {
        return cuestionarioService.listar();
    }

    @GetMapping("/{cueId}/preguntas")
    public List<PreguntaDto.Respuesta> preguntas(@PathVariable Long cueId) {
        return preguntaService.listarPorCuestionario(cueId);
    }

    @GetMapping("/{cueId}/validar")
    public ResponseEntity<Boolean> validar(@PathVariable Long cueId) {
        return ResponseEntity.ok(cuestionarioService.validarCompleto(cueId));
    }
}
