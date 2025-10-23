package cl.ufro.dci.perfectmatchapi.onboarding.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "perfil")
@Getter
@Setter
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private Long perId;

    @Column(name = "per_usuario_id", nullable = false)
    private Long perUsuarioId;

    @Column(name = "per_foto", columnDefinition = "TEXT")
    private String perFoto;

    @Column(name = "per_fecha_creacion", nullable = false)
    private LocalDate perFechaCreacion;

    @Column(name = "per_ultima_actualizacion", nullable = false)
    private LocalDateTime perUltimaActualizacion;

    @Column(name = "per_estado_cuestionario", nullable = false)
    private boolean perEstadoCuestionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_ubicacion_id", nullable = false)
    private Ubicacion perUbicacion;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cuePerfil")
    private Cuestionario perCuestionario;
}
