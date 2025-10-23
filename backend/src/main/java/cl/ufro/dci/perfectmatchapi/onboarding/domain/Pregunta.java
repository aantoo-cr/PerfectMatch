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

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pregunta")
@Getter
@Setter
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_id")
    private Long preId;

    @Column(name = "pre_enunciado", nullable = false, length = 255)
    private String preEnunciado;

    @Column(name = "pre_obligatoria", nullable = false)
    private boolean preObligatoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_cuestionario_id", nullable = false)
    private Cuestionario preCuestionario;

    @OneToMany(mappedBy = "altPregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> preAlternativas = new ArrayList<>();

    @OneToMany(mappedBy = "resPregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> preRespuestas = new ArrayList<>();

    public void agregarAlternativa(Alternativa alternativa) {
        alternativa.setAltPregunta(this);
        this.preAlternativas.add(alternativa);
    }
}
