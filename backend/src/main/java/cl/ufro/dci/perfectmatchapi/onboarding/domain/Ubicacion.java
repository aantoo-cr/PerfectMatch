package cl.ufro.dci.perfectmatchapi.onboarding.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ubicacion")
@Getter
@Setter
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ubi_id")
    private Long ubiId;

    @Column(name = "ubi_pais", nullable = false, length = 80)
    private String ubiPais;

    @Column(name = "ubi_region", nullable = false, length = 80)
    private String ubiRegion;

    @Column(name = "ubi_ciudad", nullable = false, length = 80)
    private String ubiCiudad;

    @Column(name = "ubi_latitud")
    private Double ubiLatitud;

    @Column(name = "ubi_longitud")
    private Double ubiLongitud;
}
