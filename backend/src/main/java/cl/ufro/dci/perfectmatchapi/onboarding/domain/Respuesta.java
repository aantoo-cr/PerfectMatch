package cl.ufro.dci.perfectmatchapi.onboarding.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuesta")
@Getter
@Setter
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private Long resId;

    @Column(name = "res_fecha_guardado", nullable = false)
    private LocalDateTime resFechaGuardado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_perfil_id", nullable = false)
    private Perfil resPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_pregunta_id", nullable = false)
    private Pregunta resPregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_alternativa_id", nullable = false)
    private Alternativa resAlternativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_cuestionario_id", nullable = false)
    private Cuestionario resCuestionario;
}
