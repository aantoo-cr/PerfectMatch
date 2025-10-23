import { CommonModule } from '@angular/common';
import { Component, OnInit, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, of, switchMap } from 'rxjs';
import {
  CuestionarioResp,
  PerfilCrear,
  PerfilResp,
  PreguntaResp,
  RespuestaGuardar,
  UbicacionResp,
} from '../../domain/dto';
import { UbicacionService } from '../../service/ubicacion.service';
import { PerfilService } from '../../service/perfil.service';
import { CuestionarioService } from '../../service/cuestionario.service';
import { RespuestaService } from '../../service/respuesta.service';

@Component({
  standalone: true,
  selector: 'pm-onboarding',
  imports: [CommonModule, FormsModule],
  templateUrl: './onboarding.component.html',
  styleUrls: ['./onboarding.component.css'],
})
export class OnboardingComponent implements OnInit {
  readonly cargando = signal(false);
  readonly errorMsg = signal<string | null>(null);
  readonly successMsg = signal<string | null>(null);

  readonly ubicaciones = signal<UbicacionResp[]>([]);
  ubiSeleccionada: number | null = null;
  perUsuarioId: number | null = null;

  readonly fotoPreview = signal<string | null>(null);

  readonly perfil = signal<PerfilResp | null>(null);
  readonly cuestionario = signal<CuestionarioResp | null>(null);
  readonly preguntas = signal<PreguntaResp[]>([]);
  readonly respuestas = signal<Record<number, number>>({});

  readonly puedeCrear = computed(() =>
    !!this.fotoPreview() && this.ubiSeleccionada !== null && this.perUsuarioId !== null
  );

  constructor(
    private readonly router: Router,
    private readonly ubicacionSrv: UbicacionService,
    private readonly perfilSrv: PerfilService,
    private readonly cuestionarioSrv: CuestionarioService,
    private readonly respuestaSrv: RespuestaService,
  ) {}

  ngOnInit(): void {
    const idGuardado = sessionStorage.getItem('pm_user_id') || localStorage.getItem('pm_user_id');
    this.perUsuarioId = idGuardado ? Number(idGuardado) : null;
    this.cargarUbicaciones();
  }

  private manejarError(error: any): void {
    console.error(error);
    const mensaje = error?.error?.message || error?.message || 'Error inesperado';
    this.errorMsg.set(mensaje);
    this.cargando.set(false);
  }

  private cargarUbicaciones(): void {
    this.cargando.set(true);
    this.ubicacionSrv.listar().subscribe({
      next: (datos) => {
        this.ubicaciones.set(datos);
        this.cargando.set(false);
      },
      error: (e) => this.manejarError(e),
    });
  }

  onFileChange(evento: Event): void {
    const input = evento.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) {
      return;
    }
    const reader = new FileReader();
    reader.onload = () => this.fotoPreview.set(reader.result as string);
    reader.onerror = () => this.errorMsg.set('No se pudo leer la imagen');
    reader.readAsDataURL(file);
  }

  crearPerfil(): void {
    this.errorMsg.set(null);
    this.successMsg.set(null);

    if (!this.puedeCrear() || this.perUsuarioId === null || this.ubiSeleccionada === null) {
      this.errorMsg.set('Debes seleccionar foto, ubicación y usuario.');
      return;
    }

    const body: PerfilCrear = {
      perFoto: this.fotoPreview(),
      perUsuarioId: this.perUsuarioId,
      ubiId: this.ubiSeleccionada,
    };

    this.cargando.set(true);
    this.perfilSrv
      .obtenerPorUsuario(this.perUsuarioId)
      .pipe(
        catchError((err) => {
          if (err.status === 404) {
            return of(null);
          }
          throw err;
        }),
        switchMap((perfilExistente) => {
          if (perfilExistente) {
            this.perfil.set(perfilExistente);
            return of(perfilExistente);
          }
          return this.perfilSrv.crear(body).pipe(
            catchError((err) => {
              if (err.status === 409) {
                this.errorMsg.set('Este usuario ya tiene un perfil.');
                this.cargando.set(false);
                return of(null);
              }
              this.manejarError(err);
              return of(null);
            })
          );
        }),
        switchMap((perfilCreado) => {
          if (!perfilCreado) {
            return of(null);
          }
          this.perfil.set(perfilCreado);
          return this.cuestionarioSrv.crear({ perId: perfilCreado.perId });
        }),
      )
      .subscribe({
        next: (cuestionario) => {
          if (!cuestionario) {
            return;
          }
          this.cuestionario.set(cuestionario);
          this.cargarPreguntas(cuestionario.cueId);
          this.successMsg.set('Perfil y cuestionario listos.');
          this.cargando.set(false);
        },
        error: (e) => this.manejarError(e),
      });
  }

  private cargarPreguntas(cueId: number): void {
    this.cargando.set(true);
    this.cuestionarioSrv.obtenerPreguntas(cueId).subscribe({
      next: (preguntas) => {
        this.preguntas.set(preguntas);
        this.cargando.set(false);
      },
      error: (e) => this.manejarError(e),
    });
  }

  seleccionar(preId: number, altId: number): void {
    const actual = { ...this.respuestas() };
    actual[preId] = altId;
    this.respuestas.set(actual);
  }

  guardar(): void {
    const perfil = this.perfil();
    const cuestionario = this.cuestionario();
    if (!perfil || !cuestionario) {
      this.errorMsg.set('Primero crea el perfil.');
      return;
    }
    const entradas = Object.entries(this.respuestas());
    if (!entradas.length) {
      this.errorMsg.set('Debes responder al menos una pregunta.');
      return;
    }

    this.cargando.set(true);
    const peticiones = entradas.map(([preId, altId]) => {
      const payload: RespuestaGuardar = {
        perId: perfil.perId,
        cueId: cuestionario.cueId,
        preId: Number(preId),
        altIds: [Number(altId)],
      };
      return this.respuestaSrv.guardar(payload);
    });

    let indice = 0;
    const enviar = () => {
      if (indice >= peticiones.length) {
        this.perfilSrv.marcarCuestionario(perfil.perId, cuestionario.cueId).subscribe({
          next: (pf) => {
            this.perfil.set(pf);
            this.successMsg.set('Perfil completado correctamente.');
            this.cargando.set(false);
            setTimeout(() => this.router.navigate(['/user']), 1500);
          },
          error: (e) => this.manejarError(e),
        });
        return;
      }
      peticiones[indice++].subscribe({ next: enviar, error: (e) => this.manejarError(e) });
    };
    enviar();
  }
}
