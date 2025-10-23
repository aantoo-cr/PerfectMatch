import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CuestionarioCrear, CuestionarioResp, PreguntaResp } from '../domain/dto';

@Injectable({ providedIn: 'root' })
export class CuestionarioService {
  private readonly baseUrl = '/api/onboarding/cuestionarios';

  constructor(private readonly http: HttpClient) {}

  crear(body: CuestionarioCrear): Observable<CuestionarioResp> {
    return this.http.post<CuestionarioResp>(this.baseUrl, body);
  }

  obtenerPreguntas(cueId: number): Observable<PreguntaResp[]> {
    return this.http.get<PreguntaResp[]>(`${this.baseUrl}/${cueId}/preguntas`);
  }

  validar(cueId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/${cueId}/validar`);
  }
}
