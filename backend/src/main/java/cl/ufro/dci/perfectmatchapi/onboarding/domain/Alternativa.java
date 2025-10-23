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

@Entity
@Table(name = "alternativa")
@Getter
@Setter
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alt_id")
    private Long altId;

    @Column(name = "alt_opcion", nullable = false, length = 255)
    private String altOpcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alt_pregunta_id", nullable = false)
    private Pregunta altPregunta;
}
