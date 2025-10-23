package cl.ufro.dci.perfectmatchapi.onboarding.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuestionario")
@Getter
@Setter
public class Cuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cue_id")
    private Long cueId;

    @Column(name = "cue_nombre", nullable = false, length = 255)
    private String cueNombre;

    @Column(name = "cue_fecha_creacion", nullable = false)
    private LocalDateTime cueFechaCreacion;

    @Column(name = "cue_activo", nullable = false)
    private boolean cueActivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cue_perfil_id", nullable = false)
    private Perfil cuePerfil;

    @OneToMany(mappedBy = "preCuestionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> cuePreguntas = new ArrayList<>();

    public void agregarPregunta(Pregunta pregunta) {
        pregunta.setPreCuestionario(this);
        this.cuePreguntas.add(pregunta);
    }
}
