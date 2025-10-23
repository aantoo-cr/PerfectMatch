package cl.ufro.dci.perfectmatchapi.onboarding.controller;

import cl.ufro.dci.perfectmatchapi.onboarding.dto.RespuestaDto;
import cl.ufro.dci.perfectmatchapi.onboarding.service.RespuestaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onboarding/respuestas")
@CrossOrigin(origins = "http://localhost:4200")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody RespuestaDto.Guardar guardar) {
        respuestaService.guardar(guardar);
        return ResponseEntity.ok().build();
    }
}
