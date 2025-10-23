import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PerfilCrear, PerfilResp } from '../domain/dto';

@Injectable({ providedIn: 'root' })
export class PerfilService {
  private readonly baseUrl = '/api/onboarding/perfiles';

  constructor(private readonly http: HttpClient) {}

  crear(body: PerfilCrear): Observable<PerfilResp> {
    return this.http.post<PerfilResp>(this.baseUrl, body);
  }

  obtenerPorUsuario(perUsuarioId: number): Observable<PerfilResp> {
    return this.http.get<PerfilResp>(`${this.baseUrl}/usuario/${perUsuarioId}`);
  }

  marcarCuestionario(perId: number, cueId: number): Observable<PerfilResp> {
    return this.http.put<PerfilResp>(`${this.baseUrl}/${perId}/cuestionario/${cueId}`, {});
  }
}
